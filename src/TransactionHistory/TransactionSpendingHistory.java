package TransactionHistory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class TransactionSpendingHistory extends TransactionHistory{
    private String typeOfExpenditure; //loai giao dich - ten giao dich

    //constructor
    public TransactionSpendingHistory(String namePartAmount, int transactionAmount, Date transactionDate, String note, String typeOfExpenditure, int typeTransaction){
        super(namePartAmount, transactionAmount, transactionDate, note, typeTransaction);
        this.typeOfExpenditure = typeOfExpenditure;
    }

    public TransactionSpendingHistory(TransactionSpendingHistory item){
        super(item);
        this.typeOfExpenditure = item.typeOfExpenditure;
    }

    //lay thong tin lich su
    public Hashtable<String, String> getTransactionInformation() {
        Hashtable<String, String> history = super.getTransactionInformation();

        //gan them truong trong hashtable
        history.put("typeOfExpenditure", this.typeOfExpenditure);

        return history;
    }

    public void display(){
        super.display();
        System.out.println("Ten GD" + typeOfExpenditure);
    }
}