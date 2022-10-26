package Asset;
import PartAmount.*;
import TransactionHistory.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

public class Asset {
    private int principalAmount; //so tien chinh
    private int amountSpent; //so tien da dung
    private int amountAvailable; //so tien co san

    PartAmount[] arrayParts; //ds 6 cai hu
    double[] ratios; //ds ti le tuong ung voi tung hu
    ArrayList<TransactionHistory> arrayHistory; //ds lich su cua tai khoan

    //constructor
    public Asset(int principalAmount, int amountSpent, PartAmount[] arrayParts, double[] ratios, ArrayList<TransactionHistory> arrayHistory){
        this.principalAmount = principalAmount;
        this.amountSpent = amountSpent;
        this.amountAvailable = principalAmount - amountSpent;
        this.arrayParts = arrayParts;
        this.ratios = ratios;
        this.arrayHistory = arrayHistory;
    }

    public Asset(){
        this.principalAmount = 0;
        this.amountSpent = 0;
        this.amountAvailable = 0;

        //6 cai hu
        arrayParts = new PartAmount[6];
        arrayParts[0] = new PartAmount("necessary", 0);
        arrayParts[1] = new PartAmount("education", 0);
        arrayParts[2] = new PartAmount("saving", 0);
        arrayParts[3] = new PartAmount("enjoy", 0);
        arrayParts[4] = new PartAmount("invest", 0);
        arrayParts[5] = new PartAmount("kindness", 0);

        //ti le
        ratios = new double[6];
        ratios[0] = 0.55;
        ratios[1] = 0.1;
        ratios[2] = 0.1;
        ratios[3] = 0.1;
        ratios[4] = 0.1;
        ratios[5] = 0.05;

        //lich su giao dich
        this.arrayHistory = null;
    }

    public Asset(int principalAmount, double[] ratios) {
        this.principalAmount = principalAmount;
        this.amountSpent = 0;
        this.amountAvailable = principalAmount;

        //tao 6 cai hu
        arrayParts = new PartAmount[6];
        int necessary = (int)(ratios[0] * principalAmount);
        int education = (int)(ratios[1] * principalAmount);
        int saving = (int)(ratios[2] * principalAmount);
        int enjoy = (int)(ratios[3] * principalAmount);
        int invest = (int)(ratios[4] * principalAmount);
        int kindness = principalAmount - necessary - education - saving - enjoy -invest;

        arrayParts[0] = new PartAmount("necessary", necessary);
        arrayParts[1] = new PartAmount("education", education);
        arrayParts[2] = new PartAmount("saving", saving);
        arrayParts[3] = new PartAmount("enjoy", enjoy);
        arrayParts[4] = new PartAmount("invest", invest);
        arrayParts[5] = new PartAmount("kindness", kindness);

        //ti le
        this.ratios = ratios;

        //lich su giao dich
        this.arrayHistory = null;
    }

    public Asset(int principalAmount) {
        this.principalAmount = principalAmount;
        this.amountSpent = 0;
        this.amountAvailable = principalAmount;

        //tao 6 cai hu
        arrayParts = new PartAmount[6];

        int necessary = (int)(0.55 * principalAmount);
        int equal = (int)(0.1 * principalAmount);

        arrayParts[0] = new PartAmount("necessary", necessary);
        arrayParts[1] = new PartAmount("education", equal);
        arrayParts[2] = new PartAmount("saving", equal);
        arrayParts[3] = new PartAmount("enjoy", equal);
        arrayParts[4] = new PartAmount("invest", equal);
        arrayParts[5] = new PartAmount("kindness", principalAmount - necessary - equal*4);

        //ti le
        ratios = new double[6];
        ratios[0] = 0.55;
        ratios[1] = 0.1;
        ratios[2] = 0.1;
        ratios[3] = 0.1;
        ratios[4] = 0.1;
        ratios[5] = 0.05;

        //lich su giao dich
        this.arrayHistory = null;
    }

    public Asset(Asset item){
        this.principalAmount = item.principalAmount;
        this.amountSpent = item.amountSpent;
        this.amountAvailable = item.amountAvailable;
        this.arrayParts = item.arrayParts;
        this.ratios = item.ratios;
        this.arrayHistory = item.arrayHistory;
    }

    // ham dung tien -> tuy theo loai hu
    public boolean spendMoney(String partName, int transactionAmount, Date date, String note, String typeOfExpenditure){
        int type = 0;

        switch (partName){
            case "necessary": type = 0; break;
            case "education": type = 1; break;
            case "saving": type = 2; break;
            case "enjoy": type = 3; break;
            case "invest": type = 4; break;
            case "kindness": type = 5; break;
            default: return false;
        }

        if (this.arrayParts[type].spendMoney(transactionAmount, date, note, typeOfExpenditure)){
            this.amountSpent += transactionAmount;
            this.amountAvailable = this.principalAmount - this.amountSpent;

            TransactionHistory item = new TransactionSpendingHistory(partName, transactionAmount, date, note, typeOfExpenditure, 1);
            arrayHistory.add(item);

            return true;
        }

        return false;
    }

    //chuyen hu
    public boolean transfer(int transactionAmount, Date date, String note, String departure, String destination){
        if (!departure.equals(destination)){
            int depar, des = 0;

            switch (departure){
                case "necessary": depar = 0; break;
                case "education": depar = 1; break;
                case "saving": depar = 2; break;
                case "enjoy": depar = 3; break;
                case "invest": depar = 4; break;
                case "kindness": depar = 5; break;
                default: return false;
            }

            switch (destination){
                case "necessary": des = 0; break;
                case "education": des = 1; break;
                case "saving": des = 2; break;
                case "enjoy": des = 3; break;
                case "invest": des = 4; break;
                case "kindness": des = 5; break;
                default: return false;
            }

            this.arrayParts[depar].transfer(transactionAmount, date, note, departure, destination);
            this.arrayParts[des].receiveMoney(transactionAmount, date, note, departure, destination);

            TransactionHistory item = new TransferTransactionHistory(departure, transactionAmount, date, note, departure, destination, 2);
            arrayHistory.add(item);
            item = new TransferTransactionHistory(destination, transactionAmount, date, note, departure, destination, 2);
            arrayHistory.add(item);

            return true;
        }

        return false;
    }

    //thay doi ti le giua cac hu
    public boolean changeRatios(double[] ratios){
        double sum = 0;

        if (ratios.length != 6)
            return false;
        else {
            for (int i = 0; i < ratios.length; i++){
                sum += ratios[i];
            }

            if (sum == 1)
            {
                this.ratios = ratios;
                return true;
            }
            else {
                return false;
            }
        }
    }

    //ham nap tien vao tai khoan
    public boolean topUpAccount(int transactionAmount, Date date, String note, String title, double[] ratios){

        if (changeRatios(ratios)){
            ArrayList<Integer> array = new ArrayList<Integer>();
            for(int i = 0; i < ratios.length; i++){
                if(ratios[i] != 0){
                    array.add(i);
                }
            }

            int inputMoney = 0;
            int sum = 0;
            String partName = null;
            int index = 0;
            TransactionHistory item = null;

            this.principalAmount += transactionAmount;
            this.amountAvailable = this.principalAmount - this.amountSpent;


            for (int i = 0; i < array.size(); i++){
                index = array.get(i);

                if (i != array.size() - 1){
                    inputMoney = (int) (transactionAmount * this.ratios[index]);
                    sum += inputMoney;
                }
                else{
                    inputMoney = transactionAmount - sum;
                }

                arrayParts[index].topUpAccount(inputMoney, date, note, title);

                partName = convertNamePartToString(index);

                item = new DepositTransactionsHistory(partName, inputMoney, date, note, title, 3);
                arrayHistory.add(item);
            }

            return true;
        }

        return false;
    }

    //nap tien vao mot hu
    public boolean topUpAccountToPartAmount(String namePartAmount, int transactionAmount, Date date, String note, String title){
        int type = convertNamePart(namePartAmount);

        if(type != -1){

            this.principalAmount += transactionAmount;
            this.amountAvailable = this.principalAmount - this.amountSpent;

            this.arrayParts[type].topUpAccount(transactionAmount, date, note, title);
            TransactionHistory item = new DepositTransactionsHistory(namePartAmount, transactionAmount, date, note, title, 3);
            arrayHistory.add(item);

            return true;
        }

        return false;
    }

    public void display(){
        System.out.println("Tien chinh: " + this.principalAmount);
        System.out.println("Da dung: " + this.amountSpent);
        System.out.println("Co san: " + this.amountAvailable);
        System.out.print("Ti le: ");
        for(int i = 0; i < ratios.length; i++)
        {
            System.out.print(ratios[i] + " ");
        }

        System.out.println();

        for(int i = 0; i < arrayParts.length; i++){
            System.out.println("-----------------------");
            arrayParts[i].display();
        }

        System.out.println("LICH SU GIAO DICH: ");

        for(int i = 0; i < arrayHistory.size(); i++){
            arrayHistory.get(i).display();
            System.out.println("---");
        }
    }

    public ArrayList<TransactionHistory> getFullTransactionHistory(){
        return this.arrayHistory;
    }

    public int convertNamePart(String name){
        int type = -1;

        switch (name){
            case "necessary": type = 0; break;
            case "education": type = 1; break;
            case "saving": type = 2; break;
            case "enjoy": type = 3; break;
            case "invest": type = 4; break;
            case "kindness": type = 5;
        }

        return type;
    }

    public String convertNamePartToString(int type){

        String name = null;

        switch (type){
            case 0: name = "necessary"; break;
            case 1: name = "education"; break;
            case 2: name = "saving"; break;
            case 3: name = "enjoy"; break;
            case 4: name = "invest"; break;
            case 5: name = "kindness";
        }

        return name;
    }

    public ArrayList<TransactionHistory> getTransactionHistoryByName(String name){
        int type = convertNamePart(name);

        return arrayParts[type].getHistory();
    }

    //getter
    //getter
    public int getAmountAvailable(){
        return this.amountAvailable;
    }

    public int getPrincipalAmount(){
        return this.principalAmount;
    }

    public int amountSpent(){
        return this.amountSpent;
    }

    public PartAmount getPart(String namePart){
        return this.arrayParts[convertNamePart(namePart)];
    }

    public int[] getAmountsPartByName(String name){
        int type = convertNamePart(name);

        int[] amounts = new int[3];

        amounts[0] = arrayParts[type].getPrincipalAmount();
        amounts[1] = arrayParts[type].getAmountSpent();
        amounts[2] = arrayParts[type].getAmountAvailable();

        return amounts;
    }
    
    public double[] getRatios(){
        return this.ratios;
    }

    public boolean topUpAccountByMoney(int transactionAmount, int[] amounts, Date date, String note, String title){

        if(amounts.length != 6){
            return false;
        }

        int sum = 0;
        for(int i = 0; i < amounts.length; i++){
            sum += amounts[i];
        }

        if(sum != transactionAmount){
            return false;
        }
        else{
            String partName = null;
            TransactionHistory item = null;

            for(int i = 0; i < amounts.length; i++){
                if (amounts[i] != 0){
                    arrayParts[i].topUpAccount(amounts[i], date, note, title);

                    partName = convertNamePartToString(i);

                    item = new DepositTransactionsHistory(partName, amounts[i], date, note, title, 3);
                    arrayHistory.add(item);
                }
            }

            return true;
        }
    }

}