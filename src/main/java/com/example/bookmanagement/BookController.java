package com.example.bookmanagement;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping("/books")
public class BookController {
    private final List<Book> books = new ArrayList<>();
    private final AtomicInteger counter = new AtomicInteger(1);

    public BookController() {
        books.add(new Book(counter.getAndIncrement(), "Clean Code", "Robert C. Martin", 450));
        books.add(new Book(counter.getAndIncrement(), "Effective Java", "Joshua Bloch", 520));
    }

    @GetMapping
    public String viewBooks(Model model) {
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/add")
    public String showForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-book";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute("book") Book book) {
        book.setId(counter.getAndIncrement());
        books.add(book);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        books.removeIf(b -> b.getId() == id);
        return "redirect:/books";
    }
}
