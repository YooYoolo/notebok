package com.yotsume.Repository;


import com.yotsume.Notes.Note;
import com.yotsume.Notes.TemporaryNote;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class NoteRepository {

    private static final Logger logger = Logger.getLogger(NoteRepository.class.getName());
    private final ScheduledExecutorService cleanupExecutor;
    private final LinkedHashMap<Long, Note> notes;

    public NoteRepository() {
        this.notes = new LinkedHashMap<>();
        this.cleanupExecutor = Executors.newSingleThreadScheduledExecutor();
        startCleanTask();
    }

    public int removeExpiredNotes(){

        synchronized (this.notes) {

            List<Long> IdToRemove = notes.values().stream()
                    .filter(note -> note instanceof TemporaryNote)
                    .filter(note -> ((TemporaryNote) note).isExpired())
                    .map(Note::getID)
                    .toList();

            IdToRemove.forEach(id -> {
                this.notes.remove(id);
                logger.info("Removed: " + id);
            });

            return IdToRemove.size();
        }
    }

    private void startCleanTask() {
        cleanupExecutor.scheduleAtFixedRate(
                () -> {
                    try {
                        int removed = removeExpiredNotes();

                        if (removed > 0) {
                            logger.info("Removed: " + removed);
                        }
                    } catch (Exception e) {
                        logger.severe("Error: " + e);
                    }
                }, 1, 3, TimeUnit.MINUTES
        );
    }

    public void add(Note note) {
        if (note != null) notes.put(note.getID(), note);
        else logger.warning("Note is null");
    }

    public void remove(long id) {
        if (notes.containsKey(id)) notes.remove(id);
        else logger.warning("Note with id " + id + " does not exist");
    }

    public void update(long id, String newContent) {
        if (notes.containsKey(id)) {
            Note note = notes.get(id);
            if (note != null) {
                note.setText(newContent);
            }
            else logger.warning("Note with id " + id + " does not exist");
        }
        else logger.warning("Note with id " + id + " does not exist");

    }

    public Note get(long id) {
        if (notes.containsKey(id)) return notes.get(id);
        else{
            logger.warning("Note with id " + id + " does not exist");
            return null;
        }
    }

    public Collection<Note> getAll() {
        return notes.values();
    }
}
