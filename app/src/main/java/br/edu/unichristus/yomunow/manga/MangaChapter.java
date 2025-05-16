package br.edu.unichristus.yomunow.manga;

import java.util.List;

public class MangaChapter {
    String number;
    String title;
    List<String> paginas;

    public MangaChapter(String number, String title, List<String> paginas) {
        this.number = number;
        this.title = title;
        this.paginas = paginas;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getPaginas() {
        return paginas;
    }

    public void setPaginas(List<String> paginas) {
        this.paginas = paginas;
    }
}
