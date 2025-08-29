package com.yotsume.Notes;

import java.time.LocalDate;

public abstract class Note {
    private static long counter = 0;
    protected long ID;
    protected String text;
    protected String title;
    protected LocalDate createdAt;

    public Note(String text, String title) {
        this.text = text;
        this.title = title;
        this.ID = ++counter;
        this.createdAt = LocalDate.now();
    }

    public long getID() {
        return ID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public abstract String getType();

    @Override
    public String toString() {
        return "[" + ID + "] (" + getType() + ") " + title + " | " + text + " | " + createdAt;
    }
}
