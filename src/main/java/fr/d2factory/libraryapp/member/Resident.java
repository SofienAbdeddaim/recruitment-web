package fr.d2factory.libraryapp.member;

public class Resident extends Member {
    public Resident() {
    }

    @Override
    public void payBook(int numberOfDays) {
        setWallet(priceToPay(numberOfDays,15, 15,false));
        if(numberOfDays > 60) setLate(true);
        else setLate(false);
    }
}
