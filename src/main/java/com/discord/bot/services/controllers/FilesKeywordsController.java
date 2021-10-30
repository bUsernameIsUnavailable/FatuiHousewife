package com.discord.bot.services.controllers;

import com.discord.bot.entities.File;
import com.discord.bot.repositories.FilesKeywordsRepository;
import com.discord.bot.services.FilesKeywordsService;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class FilesKeywordsController implements FilesKeywordsService {
    private final FilesKeywordsRepository repository;

    protected FilesKeywordsController(final FilesKeywordsRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Set<String> getFileUrlsFor(final String keyword, final boolean isNsfw) {
        final var probability = ThreadLocalRandom.current().nextFloat();

        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .filter(it -> it.getWord().equals(keyword) && it.getProbability() > probability)
                .flatMap(it -> it.getKeyword().getFiles().stream())
                .filter(file -> !file.isNsfw() || isNsfw)
                .map(File::getUrl)
                .collect(Collectors.toSet());
    }
}
