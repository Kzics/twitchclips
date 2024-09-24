package com.tc.embed;

public class LinkButton extends EmbedButton {
    private final String url;

    public LinkButton(String id, String label, String url) {
        super(id, label);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}