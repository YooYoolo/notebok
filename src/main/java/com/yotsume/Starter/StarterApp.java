package com.yotsume.Starter;

import com.yotsume.Notes.Note;
import com.yotsume.Notes.TemporaryNote;
import com.yotsume.Notes.TextNote;
import com.yotsume.Notes.TodoNote;
import com.yotsume.Repository.NoteRepository;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.logging.Logger;

public class StarterApp {

    private static final Logger logger = Logger.getLogger(StarterApp.class.getName());
    private final NoteRepository repo;
    private final Scanner scanner;

    public StarterApp(){
        repo = new NoteRepository();
        scanner = new Scanner(System.in);
    }

    public void start(){
        logger.info("Welcome to Note Repository");

        while(true){
            printMenu();
            String command = scanner.nextLine().trim();
            switch(command){
                case "1" -> addNote();
                case "2" -> listNotes();
                case "3" -> updateNote();
                case "4" -> deleteNote();
                case "5" -> getNote();
                case "0" -> {
                    logger.info("Exiting application...");
                    return;
                }
                default -> logger.info("Invalid command");
            }
        }
    }

    private void printMenu(){
        System.out.println("\n=== Меню блокнота ===");
        System.out.println("1. Добавить заметку");
        System.out.println("2. Показать все заметки");
        System.out.println("3. Обновить заметку");
        System.out.println("4. Удалить заметку");
        System.out.println("5. Найти заметку по ID");
        System.out.println("0. Выход");
        System.out.print("Ваш выбор: ");
    }

    private void addNote(){
        System.out.println("Выберите тип заметки:");
        System.out.println("1. Текстовая");
        System.out.println("2. ToDo");
        System.out.println("3. Временная (самоудаление)");

        String type = scanner.nextLine().trim();
        System.out.println("Enter title: ");
        String title = scanner.nextLine().trim();
        System.out.println("Enter content: ");
        String content = scanner.nextLine().trim();

        Note note = switch (type){
            case "1" -> new TextNote(content, title);
            case "2" -> new TodoNote(content, title);
            case "3" -> new TemporaryNote(content, title, LocalDateTime.now()); // живёт 1 день
            default -> null;
        };

        if (note != null) {
            repo.add(note);
            System.out.println("Заметка добавлена: " + note);
        } else {
            System.out.println("Ошибка: неверный тип заметки.");
        }
    }

    private void listNotes() {
        if (repo.getAll().isEmpty()) {
            System.out.println("Заметок нет.");
        } else {
            repo.getAll().forEach(System.out::println);
        }
    }

    private void updateNote() {
        System.out.print("Введите ID заметки: ");
        long id = Long.parseLong(scanner.nextLine());

        System.out.print("Введите новый текст: ");
        String text = scanner.nextLine();

        repo.update(id, text);
    }

    private void deleteNote() {
        System.out.print("Введите ID заметки для удаления: ");
        long id = Long.parseLong(scanner.nextLine());
        repo.remove(id);
    }

    private void getNote() {
        System.out.print("Введите ID заметки: ");
        long id = Long.parseLong(scanner.nextLine());
        Note note = repo.get(id);

        if (note != null) System.out.println(note);
        else System.out.println("Заметка не найдена.");
    }
}
