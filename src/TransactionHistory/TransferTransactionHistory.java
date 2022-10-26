package TransactionHistory;
import java.util.Date;
import java.util.Hashtable;

public class TransferTransactionHistory extends TransactionHistory{
    String departure; //noi gui
    String destination; //noi den

    //constructor
    public TransferTransactionHistory(String namePartAmount, int transactionAmount, Date transactionDate, String note, String departure, String destination, int typeTransaction){
        super(namePartAmount, transactionAmount, transactionDate, note, typeTransaction);
        this.departure = departure;
        this.destination = destination;
    }

    public TransferTransactionHistory(TransferTransactionHistory item){
        super(item);
        this.departure = item.departure;
        this.destination = item.destination;
    }

    //luu thon tin lich su
    public Hashtable<String, String> getTransactionInformation() {
        Hashtable<String, String> history = super.getTransactionInformation();

        //gan them truong trong hashtable
        history.put("departure", this.departure);
        history.put("destination", this.destination);

        return history;
    }

    public void display(){
        super.display();
        System.out.println(departure + " -> " + destination);
    }
}