package com.discord.bot.services.controllers;

import com.discord.bot.entities.Keyword;
import com.discord.bot.repositories.KeywordRepository;
import com.discord.bot.services.KeywordService;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Component
public class KeywordController implements KeywordService {
    private final KeywordRepository repository;

    protected KeywordController(final KeywordRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Set<String> getAllWords() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(Keyword::getWord)
                .collect(Collectors.toSet());
    }
}
