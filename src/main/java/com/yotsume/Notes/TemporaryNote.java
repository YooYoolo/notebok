package com.yotsume.Notes;

import java.time.LocalDateTime;

public class TemporaryNote extends Note {

    private LocalDateTime expiresAt;

    public TemporaryNote(String content, String title, LocalDateTime expiresAt) {
        super(content, title);
        this.expiresAt = expiresAt;
    }

    public TemporaryNote(String content, String title) {
        this(content, title, LocalDateTime.now().plusMinutes(3)); // По умолчанию истекает через день
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    @Override
    public String getType(){
        return "Temporary Note";
    }

    @Override
    public String toString(){
        return super.toString() + " | Expires at: " + expiresAt.toString();
    }
}
