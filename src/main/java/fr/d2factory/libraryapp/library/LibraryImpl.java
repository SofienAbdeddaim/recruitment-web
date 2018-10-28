package fr.d2factory.libraryapp.library;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.book.ISBN;
import fr.d2factory.libraryapp.member.Member;

import java.time.LocalDate;
import java.time.Period;

public class LibraryImpl implements Library {

    BookRepository bookRepository = new BookRepository();

    public int daysBorrowed(LocalDate dateBefore, LocalDate dateAfter) {
        Period intervalPeriod = Period.between(dateBefore, dateAfter);
        return intervalPeriod.getDays();
    }

    @Override
    public Book borrowBook(long isbnCode, Member member, LocalDate borrowedAt) throws HasLateBooksException {
        member.getBorrowedBooks().forEach( book -> {
            member.payBook(daysBorrowed(bookRepository.findBorrowedBookDate(book), LocalDate.now()));
        });
        if(member.isLate()) {
            throw new HasLateBooksException("You are late, please return your borrowed books");
        } else {
            Book book = bookRepository.findBook(isbnCode);
            if(book != null) {
                bookRepository.saveBookBorrow(book, borrowedAt);
                member.addBorrowedBook(book);
            }
            return book;
        }
    }

    @Override
    public void returnBook(Book book, Member member) {
        bookRepository.returnBookBorrow(book);
        member.removeBorrowedBook(book);
    }
}
