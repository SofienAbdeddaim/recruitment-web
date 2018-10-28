package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.ISBN;
import fr.d2factory.libraryapp.library.Library;

import java.util.List;

/**
 * A member is a person who can borrow and return books to a {@link Library}
 * A member can be either a student or a resident
 */
public abstract class Member {
    /**
     * An initial sum of money the member has
     */
    private float wallet = 0;
    private boolean late;
    List<Book> borrowedBooks;

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void addBorrowedBook(Book book) {
        borrowedBooks.add(book);
    }

    public void removeBorrowedBook(Book book) {
        borrowedBooks.remove(book);
    }

    public boolean isLate() {
        return late;
    }

    public void setLate(boolean late) {
        this.late = late;
    }

    /**
     * The member should pay their books when they are returned to the library
     *
     * @param numberOfDays the number of days they kept the book
     */
    public abstract void payBook(int numberOfDays);

    public float getWallet() {
        return wallet;
    }

    public void setWallet(float wallet) {
        this.wallet = wallet;
    }

    public void addToWallet(float wallet) {
        this.wallet += wallet;
    }

    public float priceToPay(int numberOfDays, int maxDay, float priceLate, boolean firstYear) {
        int freeDay;
        if (firstYear == true) {
            freeDay = 15;
        } else {
            freeDay = 0;
        }
        float price = 0;
        int daysLate = 0;
        if (numberOfDays > maxDay)
            daysLate = numberOfDays - maxDay;
        price += daysLate * priceLate;
        int normalBorrowDay = numberOfDays - daysLate - freeDay;
        price += 10 * normalBorrowDay;
        return price;
    }
}
