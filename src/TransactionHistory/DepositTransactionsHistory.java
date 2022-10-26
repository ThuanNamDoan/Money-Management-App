package TransactionHistory;

import java.util.Date;
import java.util.Hashtable;

public class DepositTransactionsHistory extends TransactionHistory{
    String title;

    //constructor
    public DepositTransactionsHistory(String namePartAmount, int transactionAmount, Date transactionDate, String note, String title, int typeTransaction){
        super(namePartAmount, transactionAmount, transactionDate, note, typeTransaction);
        this.title = title;
    }

    public DepositTransactionsHistory(DepositTransactionsHistory item){
        super(item);
        this.title = item.title;
    }

    //luu thong tin lich su
    public Hashtable<String, String> getTransactionInformation() {
        Hashtable<String, String> history = super.getTransactionInformation();

        //gan them truong trong hashtable
        history.put("title", this.title);

        return history;
    }

    public void display(){
        super.display();
        System.out.println("title: " + this.title);
    }
}

