package com.example.springintro;

import com.example.springintro.model.entity.Book;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final BufferedReader bufferedReader;


    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService
            , BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();

        System.out.println("Please, enter number from 1 to 4 for review task:");
        int task = Integer.parseInt(bufferedReader.readLine());

        switch (task) {
            case 1 -> printAllBooksAfter2000(2000);
            case 2 -> printAllAuthorsNamesWithBooksReleaseDateBeforeYear(1999);
            case 3 -> printAllAuthorsAndNumbersOfTheirBooks();
            case 4 -> printAllBooksByAuthorNameOrderByReleaseDate("George", "Powell");
        }

    }

    private void printAllBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
        .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumbersOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);

    }

    private void printAllAuthorsNamesWithBooksReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfter2000(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }
}







