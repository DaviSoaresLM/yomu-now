package br.edu.unichristus.yomunow.models;

import java.util.List;

public class ChapterIndex {
    private List<String> chapters;

    public ChapterIndex(List<String> chapters) {
        this.chapters = chapters;
    }

    public List<String> getChapters() {
        return chapters;
    }
}
