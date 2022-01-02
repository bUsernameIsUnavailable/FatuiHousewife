package com.discord.bot.listeners.commands;

import com.discord.bot.listeners.FilesKeywordsListener;
import com.discord.bot.services.FilesKeywordsService;
import com.discord.bot.services.KeywordService;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;


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

                    sendFileUrl(messageCreateEvent.getMessageContent(), channel);
                }
        );
    }

    private void sendFileUrl(final String message, final ServerTextChannel channel) {
        final var matcher = Pattern.compile(
                "(" + String.join("|", keywordService.getAllWords()) + ")"
        ).matcher(message.replaceAll("\\s+|­*", "").toLowerCase());

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
