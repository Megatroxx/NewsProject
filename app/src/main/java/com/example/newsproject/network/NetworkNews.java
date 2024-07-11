package com.example.newsproject.network;

import com.example.newsproject.database.DatabaseNews;

public class NetworkNews {
    private final String name;
    private final String title;
    private final String description;
    private final String publishedAt;

    public NetworkNews(String name, String title, String description, String publishedAt) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.publishedAt = publishedAt;
    }


    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }


    public DatabaseNews toDatabaseNews() {
        return new DatabaseNews(
                0,
                name,
                title,
                description,
                publishedAt
        );
    }
}

