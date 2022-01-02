package com.discord.bot.entities;

import javax.persistence.*;


@Entity(name = "File")
@Table(name = "\"File\"")
public class File {
    @Id
    @Column(name = "\"url\"", nullable = false)
    private String url;

    @Column(name = "\"is_nsfw\"", nullable = false)
    private boolean nsfw = false;

    protected File() {}

    public String getUrl() {
        return url;
    }

    public boolean isNsfw() {
        return nsfw;
    }
}
