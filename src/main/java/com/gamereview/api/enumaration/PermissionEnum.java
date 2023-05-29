package com.gamereview.api.enumaration;

public enum PermissionEnum {

    ADMIN(1, "ROLE_ADMIN"),
    USER(2, "ROLE_USER");

    private int cod;
    private String description;

    PermissionEnum(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescription() {
        return description;
    }

    public static PermissionEnum toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }

        for (PermissionEnum x : PermissionEnum.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Invalid id: " + cod);
    }
}
