package com.tc.embed;

public abstract class EmbedButton {
    private final String id;
    private final String label;

    protected EmbedButton(String id, String label) {
        this.id = id;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }
}
