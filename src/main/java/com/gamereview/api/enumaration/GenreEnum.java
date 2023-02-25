package com.gamereview.api.enumaration;

public enum GenreEnum {

    ACTION(1, "Ação", "ACTION"),
    ADVENTURE(2, "Aventura", "ADVENTURE"),
    RPG(3, "RPG", "RPG"),
    SOULS_LIKE(4, "Souls Like", "SOULS_LIKE"),
    ACTION_ADVENTURE(5, "Ação e Aventura", "ACTION_ADVENTURE"),
    PUZZLE(6, "Puzzle", "PUZZLE"),
    MMO(7, "MMO", "MMO"),
    SPORTS(8, "Esportes", "SPORTS"),

    STRATEGY(9, "Estratégia", "STRATEGY"),
    SIMULATION(10, "Simulação", "SIMULATION"),
    RACING(11, "Corrida", "RACING"),
    FIGHTING(12, "Luta", "FIGHTING"),
    SHOOTER(13, "Tiro", "SHOOTER"),
    HORROR(14, "Terror", "HORROR"),
    PLATFORMER(15, "Plataforma", "PLATFORMER"),
    ARCADE(16, "Arcade", "ARCADE"),
    CASUAL(17, "Casual", "CASUAL"),
    INDIE(18, "Indie", "INDIE"),
    FAMILY(19, "Família", "FAMILY"),
    VIRTUAL_REALITY(20, "Realidade Virtual", "VIRTUAL_REALITY"),

    RHYTHM_GAMES(21, "Ritmo", "RHYTHM_GAMES"),

    OPEN_WORLD(22, "Mundo Aberto", "OPEN_WORLD"),

    ROGUE_LIKE(23, "Rogue Like", "ROGUE_LIKE"),

    HACK_AND_SLASH(24, "Hack and Slash", "HACK_AND_SLASH");

    private final int id;
    private final String name;

    private final String enumName;

    GenreEnum(int id, String name, String enumName) {
        this.id = id;
        this.name = name;
        this.enumName = enumName;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getEnumName() {
        return enumName;
    }

    public static String getNameById(int id) {
        for (GenreEnum genre : GenreEnum.values()) {
            if (genre.getId() == id) {
                return genre.getName();
            }
        }
        return null;
    }

    public static GenreEnum fromId(Integer id) {
        for (GenreEnum genre : GenreEnum.values()) {
            if (id.equals(genre.getId())) {
                return genre;
            }
        }
        throw new IllegalArgumentException("Id inválido: " + id);
    }
}