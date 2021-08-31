package com.chandrakumar.ms.api.tracking;

import java.util.Locale;


public enum MsRequestLanguage {

    FR(Locale.FRENCH),
    EN(Locale.ENGLISH);

    private final Locale locale;

    MsRequestLanguage(final Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }
}