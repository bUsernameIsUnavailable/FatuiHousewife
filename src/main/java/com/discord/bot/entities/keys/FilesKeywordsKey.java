package com.discord.bot.entities.keys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FilesKeywordsKey implements Serializable {
    @Column(name = "\"keyword\"", nullable = false)
    private String keyword;

    @Column(name = "\"file\"", nullable = false)
    private String file;

    protected FilesKeywordsKey() {}

    public String getKeyword() {
        return keyword;
    }

    public String getFile() {
        return file;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        FilesKeywordsKey otherKey = (FilesKeywordsKey) other;
        return keyword.equals(otherKey.keyword) && file.equals(otherKey.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyword, file);
    }
}
