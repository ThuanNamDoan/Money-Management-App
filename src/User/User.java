package User;

import Asset.Asset;
import PartAmount.PartAmount;
import TransactionHistory.TransactionHistory;

import java.util.ArrayList;
import java.util.Date;

public class User {
    private String name;
    private String phoneNumber;
    private String email;
    private Date dateOfBirth;
    //tai khoan
    public Asset asset;

    public User(){
        name = null;
        phoneNumber = null;
        email = null;
        dateOfBirth = null;
        asset = null;
    }

    public User(String name, String phoneNumber, String email, Date dateOfBirth, Asset asset){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.asset = asset;
    }

    public User(User item){
        this.name = item.name;
        this.phoneNumber = item.phoneNumber;
        this.email = item.email;
        this.dateOfBirth = item.dateOfBirth;
        this.asset = item.asset;
    }

    //chuyen khoan
    public boolean transfer(int transactionAmount, Date date, String note, String departure, String destination){
        if (this.asset.transfer(transactionAmount, date, note, departure, destination))
            return true;

        return false;
    }

    //chi tieu
    public boolean spendMoney(String partName, int transactionAmount, Date date, String note, String typeOfExpenditure){
        if (this.asset.spendMoney(partName, transactionAmount, date,  note, typeOfExpenditure))
            return true;

        return false;
    }

    //nap tien
    public boolean topUpAccount(int transactionAmount, Date date, String note, String title, double[] ratios){
        if(asset.topUpAccount(transactionAmount, date, note, title, ratios))
            return true;

        return false;
    }

    public boolean topUpAccountToPartAmount(String namePartAmount, int transactionAmount, Date date, String note, String title){
        if(asset.topUpAccountToPartAmount(namePartAmount, transactionAmount, date, note, title))
            return true;

        return false;
    }

    public void display(){

        System.out.println("Ten: " + name);
        System.out.println("SDT: " + phoneNumber);
        System.out.println("Email: " + email);
        System.out.println("Ngay sinh: " + dateOfBirth);
        asset.display();
    }

    public void setAllProperties(String name, String phoneNumber, String email, Date dateOfBirth){

        if(name != null)
            this.name = name;

        if(phoneNumber != null)
            this.phoneNumber = phoneNumber;

        if(email != null)
            this.email = email;

        if(dateOfBirth != null)
            this.dateOfBirth = dateOfBirth;

    }

    public ArrayList<TransactionHistory> getFullTransactionHistory(){
        return this.asset.getFullTransactionHistory();
    }

    public ArrayList<TransactionHistory> getTransactionHistoryByName(String name){
        return this.asset.getTransactionHistoryByName(name);
    }

    public PartAmount getPar(String namePart){
        return asset.getPart(namePart);
    }

    public int getPrincipalAmount(){
        return asset.getPrincipalAmount();
    }

    public int getAmountAvailable(){
        return asset.getAmountAvailable();
    }

    public int[] getAmountsPartByName(String name){
        return asset.getAmountsPartByName(name);
    }

    public double[] getRatios(){
        return this.asset.getRatios();
    }

    public boolean updateInformationUser(String name, String phoneNumber, String email, Date date){

        if(name == null && phoneNumber == null && email == null && date == null){
            return false;
        }
        else{
            if(name != null){
                this.name = name;
            }

            if(phoneNumber != null){
                this.phoneNumber = phoneNumber;
            }

            if(email != null)
            {
                this.email = email;
            }

            if(date != null){
                this.dateOfBirth = date;
            }

            return true;
        }
    }

    public boolean topUpAccountByMoney(int transactionAmount, int[] amounts, Date date, String note, String title){

        if(this.asset.topUpAccountByMoney(transactionAmount, amounts, date, note, title)){
            return true;
        }

        return false;
    }

    public String getName(){
        return this.name;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public String getEmail(){
        return this.email;
    }

    public Date getDateOfBirth(){
        return this.dateOfBirth;
    }
}