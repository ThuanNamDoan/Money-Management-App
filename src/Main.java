import Controller.Controller;
import TransactionHistory.TransactionHistory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

public class Main {
    public static void main(String[] agrs) throws Exception {
        Controller a = new Controller();
        //a.logIn("123", "123");
        //a.loadData();

        //a.display();
/*
        ArrayList<TransactionHistory> b = a.getTransactionHistoryByName("necessary");

        Hashtable<String, String> hash = b.get(0).getTransactionInformation();

        for (Map.Entry<String, String> e : hash.entrySet())
            System.out.println(e.getKey() + " " + e.getValue());


        if(a.spendMoney("education", 1, "note", "loai chi tieu")){
            System.out.println("OK");
        }
        else{
            System.out.println("DEOS!");
        }
         */

        /*
        if(a.transferMoney(1, "noteNEw", "education", "invest")){
            System.out.println("OK");
        }
        else {
            System.out.println("not OK");
        }*/

        /*
        double[] r = new double[6];
        r[0] = 0.55;
        r[1] = r[2] = r[3] = r[4] = 0.1;
        r[5] = 0.05;

        if(a.topUpAccount(100, "nua", "nua", r))
            System.out.println("OK");
        else
            System.out.println("DEOS");

         */
        /*
        Date date = null;

        if (a.register("g", "123", "t", date, "t", "t"))
        {
            System.out.println("OK");
        }
        else {
            System.out.println("FAIL");
        }*/

        /*
        Date date = null;
        SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
        try{
            date = DateFor.parse("21/07/2000");
            System.out.println("Date : "+date);
        }catch (ParseException e) {e.printStackTrace();}

        if(a.spendMoney("necessary", 1, date,"note", "type")){
            System.out.println("OK");
        }*/
        /*
        Date date = null;
        SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
        try{
            date = DateFor.parse("21/07/2000");
            System.out.println("Date : "+date);
        }catch (ParseException e) {e.printStackTrace();}

        a.updateInformationUser("Đoàn Nam Thuận", "1234", "thuan@gmail.com", date, "123");

         */
        //a.register("thuan", "thuan", "thuan", null, null, null);

        Date date = null;
        SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
        try{
            date = DateFor.parse("21/07/2000");
            //System.out.println("Date : "+date);
        }catch (ParseException e) {e.printStackTrace();}

        a.logIn("thuan", "thuan");
        a.loadData();
        //a.display();

        double[] ratios = new double[6];
        ratios[0] = 0.55;
        ratios[1] = 0.1;
        ratios[2] = 0.1;
        ratios[3] = 0.1;
        ratios[4] = 0.1;
        ratios[5] = 0.05;

        //note, title, loai giao dich(tieu dung) CO THE NULL

        //a.topUpAccount(1000, date, null, null, ratios);
        //a.topUpAccountToPartAmount("kindness", 10, date, null, null);

        //a.transferMoney(10, date, null, "kindness", "necessary");

        //a.spendMoney("necessary", 10, date, null, null);

        //int[] b = a.getAmountsPartByName("necessary");
        //System.out.println(b[0] + " - " + b[1] + " - " + b[2]);

        //double[] b = a.getRatios();
        //for(int i = 0; i < b.length; i++)
        //    System.out.println(b[i]);

        //a.updateInformationUser(null, null, "haha@gmail.com", null, null);

        //xem thong tin lich su
        /*
        ArrayList<TransactionHistory> b = a.getTransactionHistoryByName("necessary");
        Hashtable<String, String> hash = null;

        for(int i = 0; i < b.size(); i++){
            hash = b.get(i).getTransactionInformation();

            for (Map.Entry<String, String> e : hash.entrySet())
                System.out.println(e.getKey() + " " + e.getValue());
        }*/

        ArrayList<TransactionHistory> b = a.getFullTransactionHistory();
        Hashtable<String, String> hash = null;

        for(int i = 0; i < b.size(); i++) {
            hash = b.get(i).getTransactionInformation();

            System.out.println(hash.get("namePartAmount"));

            //for (Map.Entry<String, String> e : hash.entrySet())
                //System.out.println(e.getKey() + " " + e.getValue());
        }
        //a.display();

    }
}