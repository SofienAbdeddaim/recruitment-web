package fr.d2factory.libraryapp.book;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The book repository emulates a database via 2 HashMaps
 */
public class BookRepository {
    private Map<ISBN, Book> availableBooks = new HashMap<>();
    private Map<Book, LocalDate> borrowedBooks = new HashMap<>();

    public void addBooks(List<Book> books){
        books.forEach( book -> {
            availableBooks.put(new ISBN((long)1), book);
        });
    }

    public Book findBook(long isbnCode) {
        return availableBooks.get(isbnCode);
    }

    public void saveBookBorrow(Book book, LocalDate borrowedAt){
        borrowedBooks.put(book, borrowedAt);
        availableBooks.remove(book.isbn);
    }

    public void returnBookBorrow(Book book){
        borrowedBooks.remove(book);
        availableBooks.put(book.isbn, book);
    }

    public LocalDate findBorrowedBookDate(Book book) {
        return borrowedBooks.get(book);
    }
}
