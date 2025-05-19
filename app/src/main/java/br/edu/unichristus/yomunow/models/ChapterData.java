package br.edu.unichristus.yomunow.models;


import java.util.List;

public class ChapterData {
    private int chapterNumber;
    private String chapterTitle;
    private List<String> pages;

    public ChapterData(int chapterNumber, String chapterTitle, List<String> pages) {
        this.chapterNumber = chapterNumber;
        this.chapterTitle = chapterTitle;
        this.pages = pages;
    }

    public int getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public List<String> getPages() {
        return pages;
    }

    public void setPages(List<String> pages) {
        this.pages = pages;
    }
}
