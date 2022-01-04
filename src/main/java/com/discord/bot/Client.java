package com.discord.bot;

import com.discord.bot.listeners.commands.FilesKeywordsCommand;
import org.javacord.api.AccountType;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.intent.Intent;
import org.javacord.api.util.logging.ExceptionLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;


@SpringBootApplication
public class Client {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiscordShards.class);

    private static String token = null;

    private final FilesKeywordsCommand filesKeywordsCommand;

    protected Client(final FilesKeywordsCommand filesKeywordsCommand) {
        this.filesKeywordsCommand = filesKeywordsCommand;
    }

    public static void main(String[] args) {
        SpringApplication.run(Client.class, args);
    }

    @Bean
    @ConfigurationProperties(value = "discord-api")
    protected DiscordShards buildDiscordApi() {
        return new DiscordShards();
    }

    private static String getToken() {
        return token;
    }

    @Value("${TOKEN}")
    private void setToken(final String token) {
        Client.token = token;
    }

    private final class DiscordShards {
        @PostConstruct
        private void initialise() {
            new DiscordApiBuilder().setToken(getToken())
                    .setAccountType(AccountType.BOT)
                    .setWaitForServersOnStartup(false)
                    .setWaitForUsersOnStartup(false)
                    .setIntents(Intent.GUILDS, Intent.GUILD_MESSAGES)
                    .setRecommendedTotalShards()
                    .join()
                    .loginAllShards()
                    .forEach(shard -> shard
                            .thenAccept(this::onShardLogin)
                            .exceptionally(ExceptionLogger.get())
                    );
        }

        private void onShardLogin(final DiscordApi api) {
            LOGGER.info("Shard " + api.getCurrentShard() + " is online!");

            api.setMessageCacheSize(0, 0);

            api.addMessageCreateListener(filesKeywordsCommand);
        }
    }
}
