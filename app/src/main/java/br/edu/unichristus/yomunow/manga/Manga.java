package br.edu.unichristus.yomunow.manga;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mangas")
public class Manga {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String synopsis;
    private String coverUrl;
    private String buttonHex;
    private String buttonTextHex;

    public Manga(String name, String synopsis, String coverUrl, String buttonHex, String buttonTextHex) {
        this.name = name;
        this.synopsis = synopsis;
        this.coverUrl = coverUrl;
        this.buttonHex = buttonHex;
        this.buttonTextHex = buttonTextHex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getButtonHex() {
        return buttonHex;
    }

    public void setButtonHex(String buttonHex) {
        this.buttonHex = buttonHex;
    }

    public String getButtonTextHex() {
        return buttonTextHex;
    }

    public void setButtonTextHex(String buttonTextHex) {
        this.buttonTextHex = buttonTextHex;
    }
}
