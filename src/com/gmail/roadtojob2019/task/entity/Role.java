package com.gmail.roadtojob2019.task.entity;

public enum Role {
    USER (1), CUSTOMER(1), ADMIN (2), PROVIDER(2), SUPER_ADMIN (3);

    private int level;

    Role(int level) {
        this.level=level;
    }

    public int getLevel() {
        return level;
    }
}
