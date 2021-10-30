package com.discord.bot.repositories;

import com.discord.bot.entities.File;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends CrudRepository<File, String> {}
