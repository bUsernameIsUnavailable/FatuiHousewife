package com.discord.bot.listeners.commands;

import com.discord.bot.listeners.FilesKeywordsListener;
import com.discord.bot.services.FilesKeywordsService;
import com.discord.bot.services.KeywordService;
import emoji4j.EmojiUtils;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Component
public class FilesKeywordsCommand implements FilesKeywordsListener {
    private final FilesKeywordsService service;
    private final KeywordService keywordService;

    protected FilesKeywordsCommand(final FilesKeywordsService service, final KeywordService keywordService) {
        this.service = service;
        this.keywordService = keywordService;
    }

    @Override
    public void onMessageCreate(final MessageCreateEvent messageCreateEvent) {
        messageCreateEvent.getServerTextChannel().ifPresent(
                channel -> {
                    final var author = messageCreateEvent.getMessageAuthor();
                    if (author.isYourself() || author.isWebhook())
                        return;

                    sendFileUrl(
                            channel,
                            messageCreateEvent.getMessageContent() + getJoinedFileNames(messageCreateEvent)
                    );
                }
        );
    }

    private String getJoinedFileNames(final MessageCreateEvent messageCreateEvent) {
        return messageCreateEvent.getMessageAttachments().stream()
                .map(MessageAttachment::getFileName).collect(Collectors.joining());
    }

    private void sendFileUrl(final ServerTextChannel channel, final String rawContent) {
        final var matcher = Pattern.compile(
                "(" + String.join("|", keywordService.getAllWords()) + ")"
        ).matcher(EmojiUtils.shortCodify(rawContent.replaceAll("\\s+|­*", "").toLowerCase()));

        while (matcher.find()) {
            final var urls = service.getFileUrlsFor(matcher.group(), channel.isNsfw());

            if (!urls.isEmpty()) {
                final var randomIndex = ThreadLocalRandom.current().nextInt(urls.size());
                final var iterator = urls.iterator();

                for (var setIndex = 0; setIndex < randomIndex; ++setIndex) {
                    iterator.next();
                }
                channel.sendMessage(iterator.next());
                break;
            }
        }
    }
}
