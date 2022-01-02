package com.discord.bot.entities;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;


@Entity(name = "Keyword")
@Table(name = "\"Keyword\"")
public class Keyword {
    @Id
    @Column(name = "\"word\"", nullable = false)
    private String word;

    @OneToMany(mappedBy = "keyword", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FilesKeywords> files;

    protected Keyword() {}

    public String getWord() {
        return word;
    }

    public Set<File> getFiles() {
        return files.stream().map(FilesKeywords::getFile).collect(Collectors.toSet());
    }
}
