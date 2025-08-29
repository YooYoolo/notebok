package com.yotsume.Notes;

public class TextNote extends Note {

    public TextNote(String text, String title){
        super(text, title);
    }

    @Override
    public String getType() {
        return "Notes.TextNote";
    }
}
