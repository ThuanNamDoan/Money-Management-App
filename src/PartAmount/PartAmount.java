package PartAmount;
import TransactionHistory.*;
import java.util.Date;
import java.util.ArrayList;

public class PartAmount {
    private String namePartAmount; //ten cua hu
    private int principalAmount; //tien chinh trong hu
    private int amountSpent; //tien da dung
    private int amountAvailable; //tien co san

    ArrayList<TransactionHistory> arrayHistory; //lich su cua hu

    //khoi tao hu voi SO TIEN MAC DINH
    public PartAmount(String namePartAmount, int principalAmount, int amountSpent, ArrayList<TransactionHistory> arrayHistory){
        this.namePartAmount = namePartAmount;
        this.principalAmount = principalAmount;
        this.amountSpent = amountSpent;

        if (principalAmount > amountSpent)
            this.amountAvailable = principalAmount - amountSpent;
        else
            this.amountAvailable = 0;

        this.arrayHistory = arrayHistory;
    }

    //constructor NAME
    public PartAmount(String namePartAmount){
        this.namePartAmount = namePartAmount;
        this.principalAmount = 0;
        this.amountSpent = 0;
        this.amountAvailable = 0;
        this.arrayHistory = null;
    }

    //khoi tao voi day du cac thuoc tinhs
    public PartAmount(String namePartAmount, int principalAmount){
        this.namePartAmount = namePartAmount;
        this.principalAmount = principalAmount;
        this.amountSpent = 0;
        this.amountAvailable = principalAmount;
        this.arrayHistory = null;
    }

    //khoi tao voi so tien có san
    public PartAmount(PartAmount item){
        this.namePartAmount = item.namePartAmount;
        this.principalAmount = item.principalAmount;
        this.amountAvailable = item.amountAvailable;
        this.amountSpent = item.amountSpent;
        this.arrayHistory = item.arrayHistory;
    }

    //giao dich tieu dung
    public boolean spendMoney(int transactionAmount, Date date, String note, String typeOfExpenditure){
        //System.out.println(amountAvailable);
        //sSystem.out.println(transactionAmount);

        if (this.amountAvailable >= transactionAmount){

            this.amountAvailable -= transactionAmount;
            this.amountSpent += transactionAmount;

            TransactionHistory item = new TransactionSpendingHistory(this.namePartAmount, transactionAmount, date, note, typeOfExpenditure, 1);
            arrayHistory.add(item);

            return true;
        }

        return false;
    }

    //giao dich chuyen huu
    public boolean transfer(int transactionAmount, Date date, String note, String departure, String destination){
        if (this.amountAvailable >= transactionAmount){

            this.amountAvailable -= transactionAmount;
            this.amountSpent += transactionAmount;

            TransactionHistory item = new TransferTransactionHistory(this.namePartAmount, transactionAmount, date, note, departure, destination, 2);
            arrayHistory.add(item);

            return true;
        }

        return false;
    }

    //nhan tien
    public boolean receiveMoney(int transactionAmount, Date date, String note, String departure, String destination){
        if (!destination.equals(departure)){

            this.principalAmount += transactionAmount;
            this.amountAvailable += transactionAmount;

            TransactionHistory item = new TransferTransactionHistory(this.namePartAmount, transactionAmount, date, note, departure, destination, 2);
            arrayHistory.add(item);

            return true;
        }

        return false;
    }

    //giao dich nap tien
    public boolean topUpAccount(int transactionAmount, Date date, String note, String title){

        if (transactionAmount != 0) {
            this.principalAmount += transactionAmount;
            this.amountAvailable += transactionAmount;

            TransactionHistory item = new DepositTransactionsHistory(this.namePartAmount, transactionAmount, date, note, title, 3);
            arrayHistory.add(item);

            return true;
        }

        return false;
    }

    public  int getAmountSpent(){
        return amountSpent;
    }

    public void display(){
        System.out.println("Ten hu: " + namePartAmount);
        System.out.println("Tien chinh: " + principalAmount);
        System.out.println("Da dung: " + amountSpent);
        System.out.println("Co san: " + amountAvailable);
        System.out.println("LSGD: ");

        for(int i = 0; i < arrayHistory.size(); i++){
            arrayHistory.get(i).display();
        }
    }

    public ArrayList<TransactionHistory> getHistory(){
        return this.arrayHistory;
    }

    //getter
    public int getAmountAvailable() {
        return this.amountAvailable;
    }

    public int getPrincipalAmount(){
        return this.principalAmount;
    }

    public int amountSpent(){
        return this.amountSpent;
    }
}