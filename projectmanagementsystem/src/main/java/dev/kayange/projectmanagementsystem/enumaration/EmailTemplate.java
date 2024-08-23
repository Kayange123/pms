package dev.kayange.projectmanagementsystem.enumaration;

import lombok.Getter;

@Getter
public enum EmailTemplate {
    ACTIVATE_ACCOUNT("activate_account"),
    RESET_PASSWORD("reset_password"),
    INVITATION("invitation");

    private final String templateName;

    EmailTemplate(String templateName) {
        this.templateName = templateName;
    }
}
