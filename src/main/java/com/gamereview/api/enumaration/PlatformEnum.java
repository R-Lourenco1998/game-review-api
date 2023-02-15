package com.gamereview.api.enumaration;

public enum PlatformEnum {

    PC(1, "PC"),
    PS4(2, "PS4"),
    XBOX_ONE(3, "Xbox One"),
    NINTENDO_SWITCH(4, "Nintendo Switch"),
    PS5(5, "PS5"),
    XBOX_SERIES_X(6, "Xbox Series X"),
    XBOX_SERIES_S(7, "Xbox Series S");

    private final int id;
    private final String name;

    PlatformEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static PlatformEnum fromId(Integer id) {
        for (PlatformEnum platform : PlatformEnum.values()) {
            if (id.equals(platform.getId())) {
                return platform;
            }
        }
        throw new IllegalArgumentException("ID inválido para Plataforma: " + id);
    }
}
