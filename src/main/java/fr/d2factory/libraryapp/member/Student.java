package fr.d2factory.libraryapp.member;

public class Student extends Member {
    private int year;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public void payBook(int numberOfDays) {
        setWallet(priceToPay(numberOfDays,15, 15,year == 1));
        if(numberOfDays > 30) setLate(true);
        else setLate(false);
    }
}
