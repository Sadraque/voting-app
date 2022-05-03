package com.votingapp.enumeration;

import lombok.Getter;

import java.util.List;

@Getter
public enum EmailLayout {

    PASSWORD_RECOVERY(1L, "Password Recovery", "passwordRecovery");

    EmailLayout(Long id, String description, String templateName) {
        this.id = id;
        this.description = description;
        this.templateName = templateName;
    }

    private final Long id;
    private final String description;
    private final String templateName;

    public static List<EmailLayout> getList() {
        return List.of(EmailLayout.values());
    }
}
