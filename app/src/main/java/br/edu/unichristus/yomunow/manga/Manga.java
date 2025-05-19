package br.edu.unichristus.yomunow.manga;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mangas")
public class Manga {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String author;
    private String synopsis;
    private String status;
    private String category;
    private int isReading;
    private String coverUrl;
    private String bannerUrl;
    private String chaptersJsonLink;

    public Manga() {
    }

    public Manga(String title, String author, String synopsis, String status, String category, int isReading, String coverUrl) {
        this.title = title;
        this.author = author;
        this.synopsis = synopsis;
        this.status = MangaStatus.fromString(status);
        this.category = MangaCategory.fromString(category);
        this.isReading = isReading;
        this.coverUrl = coverUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getIsReading() {
        return isReading;
    }

    public void setIsReading(int isReading) {
        this.isReading = isReading;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getChaptersJsonLink() {
        return chaptersJsonLink;
    }

    public void setChaptersJsonLink(String chaptersJsonLink) {
        this.chaptersJsonLink = chaptersJsonLink;
    }
}
