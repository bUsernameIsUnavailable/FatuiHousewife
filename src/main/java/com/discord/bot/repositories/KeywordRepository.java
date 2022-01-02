package com.discord.bot.repositories;

import com.discord.bot.entities.Keyword;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface KeywordRepository extends CrudRepository<Keyword, String> {}
