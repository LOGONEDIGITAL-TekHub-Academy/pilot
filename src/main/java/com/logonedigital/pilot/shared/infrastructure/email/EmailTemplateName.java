package com.logonedigital.pilot.shared.infrastructure.email;

import lombok.Getter;

@Getter
public enum EmailTemplateName {
    ACTIVATE_ACCOUNT("activate_account"),
    REPORT("report")
    ;

    EmailTemplateName(String name) {
        this.name = name;
    }

    private final String name;

}
