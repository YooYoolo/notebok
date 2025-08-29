package com.yotsume.Notes;

public class TodoNote extends Note {

    private boolean done;

    public TodoNote(String content, String title) {
        super(content, title);
        this.done = false;
    }

    public void isDone() {
        this.done = true;
    }

    @Override
    public String getType(){
        return "ToDoNote";
    }

    @Override
    public String toString() {
        return super.toString() + " | Done: " + this.done;
    }
}
