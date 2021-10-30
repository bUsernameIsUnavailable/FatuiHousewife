package com.discord.bot.entities;

import com.discord.bot.entities.keys.FilesKeywordsKey;

import javax.persistence.*;

@Entity(name = "Files_Keywords")
@Table(name = "\"Files_Keywords\"")
public class FilesKeywords {
    @EmbeddedId
    private FilesKeywordsKey compositeKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"keyword\"", insertable = false, updatable = false)
    private Keyword keyword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"file\"", insertable = false, updatable = false)
    private File file;

    @Column(name = "\"probability\"", nullable = false)
    private float probability = 1.0f;

    protected FilesKeywords() {}

    public String getWord() {
        return compositeKey.getKeyword();
    }

    public String getUrl() {
        return compositeKey.getFile();
    }

    public Keyword getKeyword() {
        return keyword;
    }

    public File getFile() {
        return file;
    }

    public float getProbability() {
        return probability;
    }
}
