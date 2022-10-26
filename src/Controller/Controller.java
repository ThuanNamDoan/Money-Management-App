package Controller;
import Asset.Asset;
import PartAmount.PartAmount;
import TransactionHistory.*;
import User.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class Controller {
    User user;
    int userId;
    int AssetId;
    int necessaryId;
    int educationId;
    int savingId;
    int enjoyId;
    int investId;
    int kindnessId;
    ArrayList<Integer> historyId = new ArrayList<Integer>(); //CHUA TAO constructor de khai bao ArrayList

    static final String DB_URL = "jdbc:sqlserver://DESKTOP-15VLH02;databaseName=QLTC;integratedSecurity=true";

    public Controller() throws ParseException {
    }

    public boolean logIn(String userName, String password){
        Connection conn = null;

        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();

            //truy van NGUOI DUNG
            String sql = "select MaND from NguoiDung as ND where ND.TenDangNhap = '" + userName + "' and ND.MatKhau = '" + password + "';";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                this.userId = rs.getInt("MaND");
            }

            //truy van TAI KHOAN
            sql = "select MaTK from TaiKhoan as TK where TK.MaND = " + this.userId + ";";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                this.AssetId = rs.getInt("MaTK");
            }

            //truy van HU TIEN (necessary)
            sql = "select MaHT from HuTien as HT where HT.MaTK = " + this.AssetId + " and HT.LoaiHT = 'necessary';";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                this.necessaryId = rs.getInt("MaHT");
            }

            //truy van HU TIEN (education)
            sql = "select MaHT from HuTien as HT where HT.MaTK = " + this.AssetId + " and HT.LoaiHT = 'education';";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                this.educationId = rs.getInt("MaHT");
            }

            //truy van HU TIEN (saving)
            sql = "select MaHT from HuTien as HT where HT.MaTK = " + this.AssetId + " and HT.LoaiHT = 'saving';";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                this.savingId = rs.getInt("MaHT");
            }

            //truy van HU TIEN (enjoy)
            sql = "select MaHT from HuTien as HT where HT.MaTK = " + this.AssetId + " and HT.LoaiHT = 'enjoy';";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                this.enjoyId = rs.getInt("MaHT");
            }

            //truy van HU TIEN (invest)
            sql = "select MaHT from HuTien as HT where HT.MaTK = " + this.AssetId + " and HT.LoaiHT = 'invest';";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                this.investId = rs.getInt("MaHT");
            }

            //truy van HU TIEN (kindness)
            sql = "select MaHT from HuTien as HT where HT.MaTK = " + this.AssetId + " and HT.LoaiHT = 'kindness';";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                this.kindnessId = rs.getInt("MaHT");
            }

            //truy van GIAODICH
            sql = "select MaGD from GiaoDich as GD where GD.MaHT = " + this.necessaryId + " or GD.MaHT = " + this.educationId + " or GD.MaHT = " + this.savingId + " or GD.MaHT = " + this.enjoyId + " or GD.MaHT = " + this.investId + " or GD.MaHT = " + this.kindnessId + ";";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                this.historyId.add(rs.getInt("MaGD"));
            }

            rs.close();
            stmt.close();
            conn.close();

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
            return false;
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
            return false;
        }finally{
            //finally block used to close resources
            try{
                if(conn != null)
                    conn.close();
            }
            catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try

        return true;
    }

    public String getNamePartAmountWithId(int id){
        if (id == this.necessaryId){
            return "necessary";
        }
        else {
            if (id == this.educationId)
                return "education";
            else{
                if (id == this.savingId)
                    return "saving";
                else{
                    if (id == this.enjoyId)
                        return "enjoy";
                    else{
                        if (id == this.investId)
                            return "invest";
                    }
                }
            }
        }

        return "kindness";
    }

    public TransactionHistory createTransaction(String typeTransaction, int transactionAmount, Date transactionDate, String note, String typeOfExpenditure, int destination, int departure, int partAmountId){
        String depar, des;
        String namePart = getNamePartAmountWithId(partAmountId);
        String noteFix = "";

        if(note != null)
            noteFix = note.trim();

        String typeOfExpenditureFix = "";
        if(typeOfExpenditure != null)
            typeOfExpenditureFix = typeOfExpenditure.trim();

        switch (typeTransaction.trim()){
            case "GDCT":
                //them giao dich
                return new TransactionSpendingHistory(namePart, transactionAmount, transactionDate, noteFix, typeOfExpenditureFix, 1);
            case "GDCH":

                depar = getNamePartAmountWithId(departure);
                des = getNamePartAmountWithId(destination);

                //them giao dich
                return  new TransferTransactionHistory(namePart, transactionAmount, transactionDate, noteFix, depar, des, 2);

            case "GDNT":

                //them giao dich
                return new DepositTransactionsHistory(namePart, transactionAmount, transactionDate, noteFix, typeOfExpenditureFix, 3);
        }

        return null;
    }
    
    public ArrayList<TransactionHistory> mergeArrayHistory(ArrayList<TransactionHistory> arr1, ArrayList<TransactionHistory> arr2, ArrayList<TransactionHistory> arr3, ArrayList<TransactionHistory> arr4, ArrayList<TransactionHistory> arr5, ArrayList<TransactionHistory> arr6){
        ArrayList<TransactionHistory> arr = new ArrayList<TransactionHistory>();

        arr.addAll(arr1);
        arr.addAll(arr2);
        arr.addAll(arr3);
        arr.addAll(arr4);
        arr.addAll(arr5);
        arr.addAll(arr6);

        return arr;
    }

    public ArrayList<TransactionHistory> sortArrayHistory(ArrayList<TransactionHistory> items){

        Collections.sort(items, new Comparator<TransactionHistory>() {
            public int compare(TransactionHistory obj1, TransactionHistory obj2) {
                return obj2.getTransactionDate().compareTo(obj1.getTransactionDate());
            }
        });

        return items;
    }

    public ArrayList<TransactionHistory> createTransactionHistory(ResultSet rs) throws SQLException {
        ArrayList<TransactionHistory> history = new ArrayList<TransactionHistory>(); // khai bao lich su giao dich
        TransactionHistory item = null;

        while(rs.next()){
            item = createTransaction(rs.getString("LoaiGD").trim(), rs.getInt("SoTienGD"), rs.getDate("NgayGD"), rs.getString("GhiChu"), rs.getString("TenGD"), rs.getInt("NoiNhan"), rs.getInt("NoiGui"), rs.getInt("MaHT"));
            history.add(item);
        }

        return history;
    }

    public PartAmount createPartAmount(String name, ArrayList<TransactionHistory> history, ResultSet rs) throws SQLException{
        PartAmount partAmount = null;
        while(rs.next()){
            partAmount = new PartAmount(name.trim(), rs.getInt("TienGoc"), rs.getInt("DaDung"), history);
        }
        return partAmount;
    }

    public Asset createAsset(PartAmount nec, PartAmount edu, PartAmount sav, PartAmount enj, PartAmount inv, PartAmount kin, ResultSet rs) throws SQLException{
        Asset asset = null;
        double[] ratios = new double[6];
        int amountSpent = 0;
        int amountAvailable = 0;
        int principalAmount = 0;

        PartAmount[] arrayParts = new PartAmount[6];
        arrayParts[0] = nec;
        arrayParts[1] = edu;
        arrayParts[2] = sav;
        arrayParts[3] = enj;
        arrayParts[4] = inv;
        arrayParts[5] = kin;

        for (int i = 0; i < 6; i++){
            amountAvailable += arrayParts[i].getAmountAvailable();
        }

        while(rs.next()){
            principalAmount = rs.getInt("TienGoc");

            ratios[0] = rs.getDouble("ThietYeu");
            ratios[1] = rs.getDouble("GiaoDuc");
            ratios[2] = rs.getDouble("TietKiem");
            ratios[3] = rs.getDouble("HuongThu");
            ratios[4] = rs.getDouble("Dautu");
            ratios[5] = rs.getDouble("ThienTam");

            amountSpent = principalAmount - amountAvailable;

            //ArrayList<TransactionHistory> items = mergeArrayHistory(nec.getHistory(), edu.getHistory(), sav.getHistory(), enj.getHistory(), inv.getHistory(), kin.getHistory());

            ArrayList<TransactionHistory> items = sortArrayHistory(mergeArrayHistory(nec.getHistory(), edu.getHistory(), sav.getHistory(), enj.getHistory(), inv.getHistory(), kin.getHistory()));

            asset = new Asset(principalAmount, amountSpent, arrayParts, ratios, items);
        }

        return asset;
    }

    public User createUser(Asset asset, ResultSet rs) throws SQLException {
        User user = null;
        String name = null;
        String phoneNumber = null;
        String email = null;
        Date dateOfBirth = null;

        while(rs.next()){
            name = rs.getString("HoTen");
            phoneNumber = rs.getString("SDT");
            email = rs.getString("Email");
            dateOfBirth = rs.getDate("NgaySinh");

            user = new User(name, phoneNumber, email, dateOfBirth, asset);
        }

        return user;
    }

    public boolean loadData() {
        Connection conn = null;

        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();

            //truy van lich su giao dich necessary
            String sql = "select * from GiaoDich where MaHT = " + this.necessaryId + ";";
            ResultSet rs = stmt.executeQuery(sql);

            //lay lich su giao dich cua hu necessary
            ArrayList<TransactionHistory> necessaryHistory = createTransactionHistory(rs); // khai bao lich su giao dich

            //lay lich su giao dich cua hu education
            sql = "select * from GiaoDich where MaHT = " + this.educationId + ";";
            rs = stmt.executeQuery(sql);
            ArrayList<TransactionHistory> educationHistory = createTransactionHistory(rs); // khai bao lich su giao dich

            //lay lich su giao dich cua hu saving
            sql = "select * from GiaoDich where MaHT = " + this.savingId + ";";
            rs = stmt.executeQuery(sql);
            ArrayList<TransactionHistory> savingHistory = createTransactionHistory(rs); // khai bao lich su giao dich

            //lay lich su giao dich cua hu enjoy
            sql = "select * from GiaoDich where MaHT = " + this.enjoyId + ";";
            rs = stmt.executeQuery(sql);
            ArrayList<TransactionHistory> enjoyHistory = createTransactionHistory(rs); // khai bao lich su giao dich

            //lay lich su giao dich cua hu invest
            sql = "select * from GiaoDich where MaHT = " + this.investId + ";";
            rs = stmt.executeQuery(sql);
            ArrayList<TransactionHistory> investHistory = createTransactionHistory(rs); // khai bao lich su giao dich

            //lay lich su giao dich cua hu kindness
            sql = "select * from GiaoDich where MaHT = " + this.kindnessId + ";";
            rs = stmt.executeQuery(sql);
            ArrayList<TransactionHistory> kindnessHistory = createTransactionHistory(rs); // khai bao lich su giao dich

            //tao hu necessary
            sql = "select * from HuTien where MaHT = " + necessaryId + ";";
            rs = stmt.executeQuery(sql);
            PartAmount necessaryPartAmount = createPartAmount("necessary", necessaryHistory, rs);

            //tao hu education
            sql = "select * from HuTien where MaHT = " + educationId + ";";
            rs = stmt.executeQuery(sql);
            PartAmount educationPartAmount = createPartAmount("education", educationHistory, rs);

            //tao hu saving
            sql = "select * from HuTien where MaHT = " + savingId + ";";
            rs = stmt.executeQuery(sql);
            PartAmount savingPartAmount = createPartAmount("saving", savingHistory, rs);

            //tao hu enjoy
            sql = "select * from HuTien where MaHT = " + enjoyId + ";";
            rs = stmt.executeQuery(sql);
            PartAmount enjoyPartAmount = createPartAmount("enjoys", enjoyHistory, rs);;

            //tao hu invest
            sql = "select * from HuTien where MaHT = " + investId + ";";
            rs = stmt.executeQuery(sql);
            PartAmount investPartAmount = createPartAmount("invest", investHistory, rs);;

            //tao hu kindness
            sql = "select * from HuTien where MaHT = " + kindnessId + ";";
            rs = stmt.executeQuery(sql);
            PartAmount kindnessPartAmount = createPartAmount("kindness", kindnessHistory, rs);;

            //tao tai khoan
            sql = "select * from TaiKhoan as TK where TK.MaTK = " + this.AssetId + ";";
            rs = stmt.executeQuery(sql);
            Asset asset = createAsset(necessaryPartAmount, educationPartAmount, savingPartAmount, enjoyPartAmount, investPartAmount, kindnessPartAmount, rs);

            //tao user
            sql = "select * from NguoiDung where MaND = " + userId + ";";
            rs = stmt.executeQuery(sql);
            this.user = createUser(asset, rs);

            rs.close();
            stmt.close();
            conn.close();

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
            return false;
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
            return false;
        }finally{
            //finally block used to close resources
            try{
                if(conn != null)
                    conn.close();
            }
            catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try

        return true;
    }

    ////////////////////////////////
    public ArrayList<TransactionHistory> getFullTransactionHistory(){
        return this.user.getFullTransactionHistory();
    }

    public ArrayList<TransactionHistory> getTransactionHistoryByName(String name){
        return this.user.getTransactionHistoryByName(name);
    }

    public int convertNamePart(String name){
        int type = 0;

        switch (name){
            case "necessary": type = this.necessaryId; break;
            case "education": type = this.educationId; break;
            case "saving": type = this.savingId; break;
            case "enjoy": type = this.enjoyId; break;
            case "invest": type = this.investId; break;
            case "kindness": type = this.kindnessId; break;
        }

        return type;
    }

    //ham display
    public void display(){
        /*
        System.out.println("userId: " + userId);
        System.out.println("AssetId: " + AssetId);
        System.out.println("necessaryId: " + necessaryId);
        System.out.println("educationId: " + educationId);
        System.out.println("savingId: " + savingId);
        System.out.println("enjoyId: " + enjoyId);
        System.out.println("investId: " + investId);
        System.out.println("kindnessId: " + kindnessId);
        System.out.println("historyId: ");
        for (int i = 0; i < historyId.size(); i++){
            System.out.print(historyId.get(i) + " ");
        }*/

        //this.user.display();

        if (this.user != null)
            this.user.display();

    }

    // Sau day la code thuc hien 1 so chuc nan
    //ham chi tieu
    public boolean spendMoney(String namePart, int transactionAmount, Date date, String note, String typeOfExpenditure) throws ParseException {

        if(user.spendMoney(namePart, transactionAmount, date, note, typeOfExpenditure)){
            int type = convertNamePart(namePart);
            int amountAvailable = user.getPar(namePart).getAmountAvailable();
            int amountSpent = user.getPar(namePart).getAmountSpent();

            //format date
            String strDate = null;
            if(date != null){
                SimpleDateFormat dateFor = new SimpleDateFormat("MM/dd/yyyy");
                strDate = "'" + dateFor.format(date) + "'";
            }

            if(note != null){
                note = "'" + note + "'";
            }

            if(typeOfExpenditure != null){
                typeOfExpenditure = "'" + typeOfExpenditure + "'";
            }

            //query update database
            String sqlUpdate = "UPDATE HuTien SET DaDung = " + amountSpent + " ,SoDu = " + amountAvailable + " WHERE MaHT = " + type;
            String sqlInsert = "INSERT INTO GiaoDich VALUES ('GDCT', " + transactionAmount + ", " + strDate + ", " + note + ", " + typeOfExpenditure + ", NULL, NULL, " + type + ");";

            //excute
            Connection conn = null;
            try {
                conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();

                stmt.executeUpdate(sqlInsert);
                stmt.executeUpdate(sqlUpdate);

                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return true;
        }

        return false;
    }

    //ham transfer
    public boolean transferMoney(int transactionAmount, Date date, String note, String departure, String destination) throws ParseException {

        if(this.user.transfer(transactionAmount, date, note, departure, destination)){

            int depar = convertNamePart(departure);
            int des = convertNamePart(destination);

            int amountAvailable = user.getPar(departure).getAmountAvailable();
            int amountSpent = user.getPar(departure).getAmountSpent();

            //format date
            String strDate = null;
            if(date != null){
                SimpleDateFormat dateFor = new SimpleDateFormat("MM/dd/yyyy");
                strDate = "'" + dateFor.format(date) + "'";
            }

            if(note != null){
                note = "'" + note + "'";
            }

            //update database
            String sqlUpdate = "UPDATE HuTien SET DaDung = " + amountSpent + " ,SoDu = " + amountAvailable + " WHERE MaHT = " + depar;
            String sqlInsert = "INSERT INTO GiaoDich VALUES ('GDCH', " + transactionAmount + ", " + strDate + ", " + note + ", 'NULL', " + des + ", " + depar + ", " + depar + ");";

            System.out.println(sqlUpdate);

            Connection conn = null;
            try {
                conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();

                stmt.executeUpdate(sqlInsert);
                stmt.executeUpdate(sqlUpdate);

                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            amountAvailable = user.getPar(destination).getAmountAvailable();
            int principalAmount = user.getPar(destination).getPrincipalAmount();

            //update database
            sqlUpdate = "UPDATE HuTien SET TienGoc = " + principalAmount + " ,SoDu = " + amountAvailable + " WHERE MaHT = " + des;
            sqlInsert = "INSERT INTO GiaoDich VALUES ('GDCH', " + transactionAmount + ", " + strDate + ", " + note + ", 'NULL', " + des + ", " + depar + ", " + des + ");";

            //System.out.println(sqlUpdate);

            conn = null;
            try {
                conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();

                stmt.executeUpdate(sqlInsert);
                stmt.executeUpdate(sqlUpdate);

                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return true;

        }

        return false;
    }

    //ham nap tien vao tai khoan
    public boolean topUpAccount(int transactionAmount, Date date, String note, String title, double[] ratios) throws ParseException {

        if(user.topUpAccount(transactionAmount, date, note, title, ratios)){
            //format date
            String strDate = null;
            if(date != null){
                SimpleDateFormat dateFor = new SimpleDateFormat("MM/dd/yyyy");
                strDate = "'" + dateFor.format(date) + "'";
            }

            if(note != null){
                note = "'" + note + "'";
            }

            if(title != null){
                title = "'" + title + "'";
            }

            //update bang ti le
            String updateRatios = "UPDATE TaiKhoan SET TienGoc = " + user.getPrincipalAmount() + ", ThietYeu = " + ratios[0] + ", GiaoDuc = " + ratios[1] + ", TietKiem = " + ratios[2] + ", HuongThu = " + ratios[3] + ", DauTu = " + ratios[4] + ", ThienTam = " + ratios[5] + " WHERE MaTK = " + this.AssetId + ";";

            //update tung cai hu
            int amountAvailable = 0;
            int amountPrincipal = 0;

            //
            String[] sqlUpdate = new String[6];
            ArrayList<Integer> array = new ArrayList<Integer>();

            //update necessary

            if(ratios[0] != 0){
                array.add(0);
                amountAvailable = user.getPar("necessary").getAmountAvailable();
                amountPrincipal = user.getPar("necessary").getPrincipalAmount();
                sqlUpdate[0] = "UPDATE HuTien SET TienGoc = " + amountPrincipal + " ,SoDu = " + amountAvailable + " WHERE MaHT = " + this.necessaryId;

                System.out.println("TTTTTTTTTTTTTT: " + sqlUpdate[0]);

            }

            //update education
            if(ratios[1] != 0){
                array.add(1);
                amountAvailable = user.getPar("education").getAmountAvailable();
                amountPrincipal = user.getPar("education").getPrincipalAmount();
                sqlUpdate[1] = "UPDATE HuTien SET TienGoc = " + amountPrincipal + " ,SoDu = " + amountAvailable + " WHERE MaHT = " + this.educationId;
            }

            //update saving
            if(ratios[2] != 0){
                array.add(2);
                amountAvailable = user.getPar("saving").getAmountAvailable();
                amountPrincipal = user.getPar("saving").getPrincipalAmount();
                sqlUpdate[2] = "UPDATE HuTien SET TienGoc = " + amountPrincipal + " ,SoDu = " + amountAvailable + " WHERE MaHT = " + this.savingId;
            }

            //update enjoy
            if(ratios[3] != 0){
                array.add(3);
                amountAvailable = user.getPar("enjoy").getAmountAvailable();
                amountPrincipal = user.getPar("enjoy").getPrincipalAmount();
                sqlUpdate[3] = "UPDATE HuTien SET TienGoc = " + amountPrincipal + " ,SoDu = " + amountAvailable + " WHERE MaHT = " + this.enjoyId;
            }

            //update invest
            if(ratios[4] != 0){
                array.add(4);
                amountAvailable = user.getPar("invest").getAmountAvailable();
                amountPrincipal = user.getPar("invest").getPrincipalAmount();
                sqlUpdate[4] = "UPDATE HuTien SET TienGoc = " + amountPrincipal + " ,SoDu = " + amountAvailable + " WHERE MaHT = " + this.investId;
            }

            //update kindness
            if(ratios[5] != 0){
                array.add(5);
                amountAvailable = user.getPar("kindness").getAmountAvailable();
                amountPrincipal = user.getPar("kindness").getPrincipalAmount();
                sqlUpdate[5] = "UPDATE HuTien SET TienGoc = " + amountPrincipal + " ,SoDu = " + amountAvailable + " WHERE MaHT = " + this.kindnessId;
            }

            Connection conn = null;
            try {
                conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();

                stmt.executeUpdate(updateRatios);

                for(int i = 0; i < sqlUpdate.length; i++){
                    if(sqlUpdate[i] != null){
                        stmt.executeUpdate(sqlUpdate[i]);
                    }
                }

                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }

            int inputMoney = 0;
            int sum = 0;
            String partName;
            int type = 0;
            String strInsert = null;
            int index = 0;

            //insert lich su giao dich
            for (int i = 0; i < array.size(); i++){
                index = array.get(i);

                //them giao dich vao cho moi hu tien
                switch (index){
                    case 0: partName = "necessary"; type = this.necessaryId; break;
                    case 1: partName = "education"; type = this.educationId; break;
                    case 2: partName = "saving"; type = this.savingId; break;
                    case 3: partName = "enjoy"; type = this.enjoyId; break;
                    case 4: partName = "invest"; type = this.investId; break;
                    case 5: partName = "kindness"; type = this.kindnessId; break;
                    default: return false;
                }

                if(i != array.size() - 1){
                    inputMoney = (int) (transactionAmount * ratios[index]);
                    sum += inputMoney;
                }
                else {
                    inputMoney = transactionAmount - sum;
                }


                //System.out.println(transactionAmount + " " + " " + ratios[i] +  inputMoney);

                strInsert = "INSERT INTO GiaoDich VALUES ('GDNT', " + inputMoney + ", " + strDate + ", " + note + ", " + title + ", NULL, NULL, " + type + ");";
                conn = null;
                try {
                    conn = DriverManager.getConnection(DB_URL);
                    Statement stmt = conn.createStatement();

                    stmt.executeUpdate(strInsert);

                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    //dang ky tai khoan
    public boolean register(String username, String password, String name, Date date, String email, String phone) throws SQLException {

        String sqlSelect = "select * from NguoiDung where TenDangNhap = '" + username + "';";
        //format date
        String strDate = null;
        if(date != null){
            SimpleDateFormat dateFor = new SimpleDateFormat("MM/dd/yyyy");
            strDate = "'" + dateFor.format(date) + "'";
        }

        if(email != null){
            email = "'" + email + "'";
        }

        if(phone != null){
            phone = "'" + phone + "'";
        }

        Connection conn = null;
        ResultSet rs = null;
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlSelect);

            if(!rs.next()){

                String sqlInsert = "insert into NguoiDung values (N'" + name + "', " + strDate + ", " + email + ", " + phone + ", '" + username + "', '" + password + "');";
                stmt.executeUpdate(sqlInsert);


                //truy van NGUOI DUNG
                sqlSelect = "select MaND from NguoiDung as ND where ND.TenDangNhap = '" + username + "' and ND.MatKhau = '" + password + "';";
                rs = stmt.executeQuery(sqlSelect);
                while(rs.next()){
                    this.userId = rs.getInt("MaND");
                }

                //them tai khoan cua nguoi dung
                sqlInsert = "insert into TaiKhoan values (0, 0.55, 0.1, 0.1, 0.1, 0.1, 0.05, " + this.userId + ");";
                stmt.executeUpdate(sqlInsert);

                //truy van TAI KHOAN
                sqlSelect = "select MaTK from TaiKhoan as TK where TK.MaND = " + this.userId + ";";
                rs = stmt.executeQuery(sqlSelect);
                while(rs.next()){
                    this.AssetId = rs.getInt("MaTK");
                }

                //them hu necessary
                sqlInsert = "insert into HuTien values ('necessary', 0, 0, 0, " + this.AssetId + ");";
                stmt.executeUpdate(sqlInsert);


                //truy van HU TIEN (necessary)
                sqlSelect = "select MaHT from HuTien as HT where HT.MaTK = " + this.AssetId + " and HT.LoaiHT = 'necessary';";
                rs = stmt.executeQuery(sqlSelect);
                while(rs.next()){
                    this.necessaryId = rs.getInt("MaHT");
                }


                //them hu education
                sqlInsert = "insert into HuTien values ('education', 0, 0, 0, " + this.AssetId + ");";
                stmt.executeUpdate(sqlInsert);

                //truy van HU TIEN (education)
                sqlSelect = "select MaHT from HuTien as HT where HT.MaTK = " + this.AssetId + " and HT.LoaiHT = 'education';";
                rs = stmt.executeQuery(sqlSelect);
                while(rs.next()){
                    this.educationId = rs.getInt("MaHT");
                }

                //them hu saving
                sqlInsert = "insert into HuTien values ('saving', 0, 0, 0, " + this.AssetId + ");";
                stmt.executeUpdate(sqlInsert);

                //truy van HU TIEN (saving)
                sqlSelect = "select MaHT from HuTien as HT where HT.MaTK = " + this.AssetId + " and HT.LoaiHT = 'saving';";
                rs = stmt.executeQuery(sqlSelect);
                while(rs.next()){
                    this.savingId = rs.getInt("MaHT");
                }

                //them hu enjoy
                sqlInsert = "insert into HuTien values ('enjoy', 0, 0, 0, " + this.AssetId + ");";
                stmt.executeUpdate(sqlInsert);

                //truy van HU TIEN (enjoy)
                sqlSelect = "select MaHT from HuTien as HT where HT.MaTK = " + this.AssetId + " and HT.LoaiHT = 'enjoy';";
                rs = stmt.executeQuery(sqlSelect);
                while(rs.next()){
                    this.enjoyId = rs.getInt("MaHT");
                }

                //them hu invest
                sqlInsert = "insert into HuTien values ('invest', 0, 0, 0, " + this.AssetId + ");";
                stmt.executeUpdate(sqlInsert);

                //truy van HU TIEN (invest)
                sqlSelect = "select MaHT from HuTien as HT where HT.MaTK = " + this.AssetId + " and HT.LoaiHT = 'invest';";
                rs = stmt.executeQuery(sqlSelect);
                while(rs.next()){
                    this.investId = rs.getInt("MaHT");
                }

                //them hu kindness
                sqlInsert = "insert into HuTien values ('kindness', 0, 0, 0, " + this.AssetId + ");";
                stmt.executeUpdate(sqlInsert);

                //truy van HU TIEN (kindness)
                sqlSelect = "select MaHT from HuTien as HT where HT.MaTK = " + this.AssetId + " and HT.LoaiHT = 'kindness';";
                rs = stmt.executeQuery(sqlSelect);
                while(rs.next()){
                    this.kindnessId = rs.getInt("MaHT");
                }

                if (!loadData()){
                    return false;
                }

                System.out.println("chua ton tai");
                return true;
            }

            stmt.close();
            System.out.println("ton tai");
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //get amounts cho tung loai hu
    public int[] getAmountsPartByName(String name){
        return this.user.getAmountsPartByName(name);
    }

    //get ratios
    public double[] getRatios(){
        return this.user.getRatios();
    }

    //thay doi thong tin nguoi dung
    public boolean updateInformationUser(String name, String phoneNumber, String email, Date date, String password){

        if(name == null && phoneNumber == null && email == null && date == null && password == null){
            return false;
        }
        else {
            String sqlUpdate = "update NguoiDung set";

            if (name != null){
                sqlUpdate += " HoTen = N'" + name + "',";
            }

            if(phoneNumber != null){
                sqlUpdate += " SDT = '" + phoneNumber + "',";
            }

            if(email != null){
                sqlUpdate += " Email = '" + email + "',";
            }

            if(password != null){
                sqlUpdate += " MatKhau = '" + password + "',";
            }

            //format date

            if(date != null){
                SimpleDateFormat dateFor = new SimpleDateFormat("MM/dd/yyyy");
                String strDate = dateFor.format(date);

                sqlUpdate += " NgaySinh = '" + strDate + "',";
            }

            sqlUpdate = sqlUpdate.substring(0, sqlUpdate.length() - 1);

            sqlUpdate += " where MaND = " + this.userId + ";";

            System.out.println(sqlUpdate);

            Connection conn = null;
            try {
                conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();

                stmt.executeUpdate(sqlUpdate);

                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //cho nay co the sua lai
        this.user.setAllProperties(name, phoneNumber, email, date);

        return true;
    }

    //nap tien vao tai khoan tung loai hu tien
    public boolean topUpAccountToPartAmount(String namePartAmount, int transactionAmount, Date date, String note, String title){
        int type = 0;

        switch (namePartAmount){
            case "necessary": type = this.necessaryId; break;
            case "education": type = this.educationId; break;
            case "saving": type = this.savingId; break;
            case "enjoy": type = this.enjoyId; break;
            case "invest": type = this.investId; break;
            case "kindness": type = this.kindnessId; break;
            default: return false;
        }

        if(this.user.topUpAccountToPartAmount(namePartAmount, transactionAmount, date, note, title)){
            //format date
            String strDate = null;
            if(date != null){
                SimpleDateFormat dateFor = new SimpleDateFormat("MM/dd/yyyy");
                strDate = "'" + dateFor.format(date) + "'";
            }

            if(note != null){
                note = "'" + note + "'";
            }

            if(title != null){
                title = "'" + title + "'";
            }

            String sqlUpdateAsset = "update TaiKhoan set TienGoc = " + this.user.getPrincipalAmount() + " where MaTK = " + this.AssetId + ";";
            String sqlUpdatePart = "UPDATE HuTien SET TienGoc = " + user.getPar(namePartAmount).getPrincipalAmount() + " ,SoDu = " + user.getPar(namePartAmount).getPrincipalAmount() + " WHERE MaHT = " + type;
            String sqlInsert = "INSERT INTO GiaoDich VALUES ('GDNT', " + transactionAmount + ", " + strDate + ", " + note + ", " + title + ", NULL, NULL, " + type + ");";

            //System.out.println(sqlUpdateAsset);

            Connection conn = null;
            try {
                conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();

                stmt.executeUpdate(sqlUpdateAsset);
                stmt.executeUpdate(sqlUpdatePart);
                stmt.executeUpdate(sqlInsert);

                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }

        }

        return true;
    }

    //nap tien vao tai khoan theo tien mat
    public boolean topUpAccountByMoney(int transactionAmount, int[] amounts, Date date, String note, String title){

        if(this.user.topUpAccountByMoney(transactionAmount, amounts, date, note, title)){

            return true;
        }

        return false;
    }

    //ham lay so du
    public int getAmountAvailable(){
        return this.user.getAmountAvailable();
    }

    public String getName(){
        return this.user.getName();
    }

    public String getPhoneNumber(){
        return this.user.getPhoneNumber();
    }

    public String getEmail(){
        return this.user.getEmail();
    }

    public Date getDateOfBirth(){
        return this.user.getDateOfBirth();
    }

}