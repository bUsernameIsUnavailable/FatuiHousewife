package com.discord.bot.repositories;

import com.discord.bot.entities.FilesKeywords;
import com.discord.bot.entities.keys.FilesKeywordsKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FilesKeywordsRepository extends CrudRepository<FilesKeywords, FilesKeywordsKey> {}
