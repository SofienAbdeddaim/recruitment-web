package fr.d2factory.libraryapp.library;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.book.ISBN;
import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.member.Resident;
import fr.d2factory.libraryapp.member.Student;
import org.junit.Before;
import org.junit.Test;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class LibraryTest {
    private Library library ;
    private BookRepository bookRepository;

    @Before
    public void setup(){
        //TODO instantiate the library and the repository
        BookRepository bookRepository = new BookRepository();
        LibraryImpl library = new LibraryImpl();
        //TODO add some test books (use BookRepository#addBooks)
        //TODO to help you a file called books.json is available in src/test/resources
    }

    @Test
    public void member_can_borrow_a_book_if_book_is_available(){
        Book book1 = new Book("Harry Potter", "J.K. Rowling", new ISBN(Long.valueOf("46578964513")));
        Book book2 = new Book("Around the world in 80 days", "Jules Verne", new ISBN(Long.valueOf("3326456467846")));
        Book book3 = new Book("Catch 22", "Joseph Heller", new ISBN(Long.valueOf("968787565445")));
        Member member = new Student(3);
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        bookRepository.addBooks(books);
        Book borrowedBook = library.borrowBook(Long.valueOf("968787565445"), member, LocalDate.now());
        assertEquals(borrowedBook, book3);
    }

    @Test
    public void borrowed_book_is_no_longer_available(){
        Book book1 = new Book("Harry Potter", "J.K. Rowling", new ISBN(Long.valueOf("46578964513")));
        Member member1 = new Student(3);
        Member member2 = new Student(2);
        List<Book> books = new ArrayList<>();
        books.add(book1);
        bookRepository.addBooks(books);
        Book borrowedBook = library.borrowBook(Long.valueOf("968787565445"), member1, LocalDate.now());
        assertNull(library.borrowBook(Long.valueOf("968787565445"), member2, LocalDate.now()));
    }

    @Test
    public void residents_are_taxed_10cents_for_each_day_they_keep_a_book(){
        Member member = new Resident();
        member.payBook(3);
        Boolean aBoolean = (3 * (float)10) == member.getWallet();
        assertTrue(aBoolean);
    }

    @Test
    public void students_pay_10_cents_the_first_30days() {
        Member member = new Student(3);
        member.payBook(3);
        Boolean aBoolean = (3 * (float)10) == member.getWallet();
        assertTrue(aBoolean);
    }

    @Test
    public void students_in_1st_year_are_not_taxed_for_the_first_15days(){
        Member member = new Student(1);
        member.payBook(3);
        Boolean aBoolean = 0 == member.getWallet();
        assertTrue(aBoolean);
    }

    @Test
    public void students_pay_15cents_for_each_day_they_keep_a_book_after_the_initial_30days(){
        Member member = new Student(3);
        member.payBook(33);
        Float studentPrice = (30 * (float)10) + (3 * (float)15);
        Boolean aBoolean = studentPrice == member.getWallet();
        assertTrue(aBoolean);
    }

    @Test
    public void residents_pay_20cents_for_each_day_they_keep_a_book_after_the_initial_60days(){
        Member member = new Resident();
        member.payBook(63);
        Float Residentprice = (60 * (float)10) + (3 * (float)20);
        Boolean aBoolean = Residentprice == member.getWallet();
        assertTrue(aBoolean);
    }

    @Test(expected=HasLateBooksException.class)
    public void members_cannot_borrow_book_if_they_have_late_books(){
        Book book = new Book("Catch 22", "Joseph Heller", new ISBN(Long.valueOf("968787565445")));
        Member member = new Student(4);
        member.setLate(true);
        library.borrowBook(Long.valueOf("968787565445"), member, LocalDate.now());
    }
}
