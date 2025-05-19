package br.edu.unichristus.yomunow.manga;

public enum MangaStatus {
    ONGOING, COMPLETED, HIATUS;

    public static String fromString(String status) {
        for (MangaStatus value : values()) {
            if (value.name().equalsIgnoreCase(status)) {
                return value.toString();
            }
        }
        return null;
    }
}
