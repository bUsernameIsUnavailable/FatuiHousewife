package com.discord.bot.services;

import java.util.Set;


public interface FilesKeywordsService {
    Set<String> getFileUrlsFor(final String keyword, final boolean isNsfw);
}
