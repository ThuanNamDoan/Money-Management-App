package TransactionHistory;
import java.util.*;
import java.util.Date;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class TransactionHistory {
    
    private String namePartAmount; //ten hu trong giao dich
    private int transactionAmount; //so tien giao dich
    private Date transactionDate; //thoi gian giao dich
    private String note; //ghi chu
    private int typeTransaction; //loai giao dich: 1-chi tieu; 2-chuyen khoan; 3-nap tien

    TransactionHistory(String namePartAmount, int transactionAmount, Date transactionDate, String note, int typeTransaction){
        this.namePartAmount = namePartAmount;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
        this.note = note;
        this.typeTransaction = typeTransaction;
    }

    TransactionHistory(TransactionHistory item){
        this.namePartAmount = item.namePartAmount;
        this.transactionAmount = item.transactionAmount;
        this.transactionDate = item.transactionDate;
        this.note = item.note;
        this.typeTransaction = item.typeTransaction;
    }

    public Hashtable<String, String> getTransactionInformation() {
        //khai bao tao hashtable
        Hashtable<String, String> history = new Hashtable<String, String>();

        //TRUONG 1
        history.put("namePartAmount", this.namePartAmount);

        //TRUONG 2
        history.put("transactionAmount", String.valueOf(this.transactionAmount));

        //TRUONG 3
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = dateFormat.format(this.transactionDate);
        history.put("transactionDate", strDate);

        //TRUONG 4
        history.put("note", this.note);

        //TRUONG 5
        if (this.typeTransaction == 1){
            history.put("typeTransaction", "1");
        }
        else {
            if(this.typeTransaction == 2){
                history.put("typeTransaction", "2");
            }
            else{
                history.put("typeTransaction", "3");
            }
        }

        return history;
    }

    public void display(){

        System.out.println("---Giao dich---");
        System.out.println("Ten hu GD: " + namePartAmount);
        System.out.println("So tien GD: " + transactionAmount);
        System.out.println("Ngay GD: " + transactionDate);
        System.out.println("Note: " + note);

        if (typeTransaction == 1){
            System.out.println("GDCT");
        }
        else{
            if(typeTransaction == 2){
                System.out.println("GDCH");
            }
            else {
                if(typeTransaction == 3){
                    System.out.println("GDNT");
                }
            }
        }
    }

    public Date getTransactionDate(){
        return transactionDate;
    }
}