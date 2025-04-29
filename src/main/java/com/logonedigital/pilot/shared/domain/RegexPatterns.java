package com.logonedigital.pilot.shared.domain;

public final class RegexPatterns {
    public static final String EMAIL = "^[A-Za-z0-9+_.-]+@(.+)$";
    public static final String MATRICULE = "^\\d{3}TKDM\\d{4}$";

    private RegexPatterns() {}
}
