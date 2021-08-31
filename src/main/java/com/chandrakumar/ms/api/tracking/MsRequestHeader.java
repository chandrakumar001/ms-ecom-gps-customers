package com.chandrakumar.ms.api.tracking;

public enum MsRequestHeader {

    LANGUAGE("lang"),
    EXTERNAL_ID("X-External-ID"),
    REQUEST_ID("X-Request-ID"),
    BUSINESS_ID("X-BusinessTx-ID"),
    JWT_TOKEN("jwt");

    private final String headerName;

    MsRequestHeader(final String headerName) {
        this.headerName = headerName;
    }

    public String getHeaderName() {
        return headerName;
    }
}