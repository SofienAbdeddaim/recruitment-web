package fr.d2factory.libraryapp.member;

public class Resident extends Member {
    @Override
    public void payBook(int numberOfDays) {
        addToWallet(priceToPay(numberOfDays,15, 15,false));
        if(numberOfDays > 60) setLate(true);
        else setLate(false);
    }
}
