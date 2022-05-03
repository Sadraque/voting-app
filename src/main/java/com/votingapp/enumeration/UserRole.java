package com.votingapp.enumeration;

import lombok.Getter;

import java.util.List;

@Getter
public enum UserRole {
    ADMIN(1L, "ROLE_ADMIN"),
    CLIENT(2L, "ROLE_CLIENT");

    private final Long id;
    private final String role;

    UserRole(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public static List<UserRole> getList() {
        return List.of(UserRole.values());
    }

}
