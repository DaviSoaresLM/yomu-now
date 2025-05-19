package br.edu.unichristus.yomunow.manga;

public enum MangaCategory {
    POPULAR, UPDATING;

    public static String fromString(String category) {
        for (MangaCategory value : values()) {
            if (value.name().equalsIgnoreCase(category)) {
                return value.toString();
            }
        }
        return null;
    }
}
