package com.gamereview.api.enumaration;

public enum PlatformEnum {

    PC(1),
    PS4(2),
    XBOX_ONE(3),
    NINTENDO_SWITCH(4),
    PS5(5),
    XBOX_SERIES_X(6),
    XBOX_SERIES_S(7);

    private final int id;

    PlatformEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
