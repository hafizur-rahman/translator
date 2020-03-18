package com.jdreamer.tutor.model;

import java.util.Objects;

public class ParseSiteReq {
    private String url;

    public ParseSiteReq() {
    }

    public ParseSiteReq(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParseSiteReq that = (ParseSiteReq) o;
        return Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
