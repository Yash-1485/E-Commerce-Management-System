import java.util.*;
import java.sql.*;

public class Admin {
    static Scanner sc = new Scanner(System.in);
    int admin_id;
    String emailId;
    String pwd;
    String fullName;
    String mobileNo;
    Connection con;

    public Admin(int admin_id, String emailId, String pwd, String fullName, String mobileNo) {
        this.admin_id = admin_id;
        this.emailId = emailId;
        this.pwd = pwd;
        this.fullName = fullName;
        this.mobileNo = mobileNo;
    }

    public Admin() throws Exception {
        con = DBConnection.getConnection();
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public void setemailId(String emailId) {
        this.emailId = emailId;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getPwd() {
        return pwd;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void registerAdmin() throws Exception {
        validateemailId();
        validatepwd();
        validateMobileNo();
        System.out.println("Enter Full Name: ");
        fullName = sc.nextLine();
        String query = "insert into admin(emailid,password,fullname,mobileno) values(?,?,?,?);";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, emailId);
        pst.setString(2, pwd);
        pst.setString(3, fullName);
        pst.setString(4, mobileNo);
        int n = pst.executeUpdate();
        if (n > 0) {
            System.out.println("Registration Successfully");
        } else {
            throw new Exception("Registration Failed");
        }
    }

    public Admin getAdmin(String emailId,String pwd) throws Exception {
        String query="Select * from admin where emailid=? and password=?;";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, emailId);
        pst.setString(2, pwd);
        ResultSet rs = pst.executeQuery();
        if(rs.next()) {
            return new Admin(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
        }else{
            return null;
        }
    }

    public boolean isAdmin() throws Exception {
        // to verify admin
        System.out.println("Enter E-Mail Id: ");
        emailId = sc.nextLine();
        System.out.println("Enter Password: ");
        pwd = sc.nextLine();

        String query = "select * from admin where emailid=? and password=?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, emailId);
        pst.setString(2, pwd);
        ResultSet rs = pst.executeQuery();
        if (!rs.next()) {
            throw new Exception(
                    "Not a valid admin\nIf you didn't registered, then register yourself first by Admin Person");
        } else {
            return true;
        }
    }

    public void validateemailId() throws Exception {
        while (true) {
            // sc.nextLine();
            System.out.println("Enter Emaild Id: ");
            emailId = sc.nextLine();
            boolean isExists = isemailIdExists(emailId);
            if (!isExists) {
                if (emailId.contains("@gmail.com") || emailId.contains("@hotmail.com") || emailId.contains("@yahoo.com")
                        || emailId.contains("@outlook.com")) {
                    if(emailId.equalsIgnoreCase("@gmail.com")){
                        System.out.println("Enter valid E-Mail id. Add something before @gmail.com");
                        continue;
                    }else if(emailId.equalsIgnoreCase("@hotmail.com")){
                        System.out.println("Enter valid E-Mail id. Add something before @hotmail.com");
                        continue;
                    }else if(emailId.equalsIgnoreCase("@outlook.com")){
                        System.out.println("Enter valid E-Mail id. Add something before @outlook.com");
                        continue;
                    }else if(emailId.equalsIgnoreCase("@yahoo.com")){
                        System.out.println("Enter valid E-Mail id. Add something before @yahoo.com");
                        continue;
                    }else{
                        return;
                    }
                } else {
                    System.out.println("Enter valid E-Mail id");
                    continue;
                }
            } else {
                System.out.println("You've already logged in");
                throw new Exception("EmailId already exists");
            }
        }
    }

    public boolean isemailIdExists(String emailId) throws Exception {
        String query = "select emailId from admin where emailId=?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, emailId);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }

    public boolean ispwdCorrect(String emailId, String pwd) throws Exception {
        String query = "Select password from admin where emailId = ?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, emailId);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            if (rs.getString(1).equals(pwd)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean validateAdmin() throws Exception {
        System.out.println("Enter Admin Id: ");
        admin_id = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter E-Mail Id: ");
        emailId = sc.nextLine();
        System.out.println("Enter Password: ");
        pwd = sc.nextLine();
        String query = "select * from admin where admin_id=? and emailid=? and password=?;";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, admin_id);
        pst.setString(2, emailId);
        pst.setString(3, pwd);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }

    public void validatepwd() {
        while (true) {
            System.out.println("Enter New Password: ");
            pwd = sc.nextLine();
            if (checkpwd(pwd)) {
                System.out.println("Enter Confirm Password: ");
                String cp = sc.nextLine();
                if (cp.equals(pwd)) {
                    System.out.println("Password verifed");
                    return;
                } else {
                    System.out.println("Failed to match confirm password with given pwd");
                    System.out.println("Please enter again");
                    continue;
                }
            } else {
                System.out.println("Please Enter Password again");
            }
        }
    }

    public boolean checkpwd(String pwd) {
        if (pwd.length() != 10) {
            System.out.println("Password must be of 10 characters");
            return false;
        }

        if (!(pwd.contains("@") || pwd.contains("_") || pwd.contains("#") || pwd.contains("$"))) {
            System.out.println("Password must contain at least one special character: @, _, #, $");
            return false;
        }

        boolean flag = true;
        int specialCharCount = 0;

        for (int i = 0; i < pwd.length(); i++) {
            char c = pwd.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9')) {
            } else if (c == '@' || c == '_' || c == '#' || c == '$') {
                specialCharCount++;
                if (specialCharCount > 1) {
                    System.out.println("Special characters more than once are not allowed");
                    flag = false;
                    break;
                }
            } else {
                System.out.println(
                        "Characters of the Password must be between a to z, 0 to 9, and one special character (@, _, #, $)");
                flag = false;
                break;
            }
        }
        return flag;
    }

    public void validateMobileNo() {
        while (true) {
            System.out.println("Enter Mobile No.: ");
            mobileNo = sc.nextLine();
            if (mobileNo.length() == 10) {
                boolean flag = true;
                for (int i = 0; i < mobileNo.length(); i++) {
                    if (mobileNo.charAt(i) < '0' || mobileNo.charAt(i) > '9') {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    System.out.println("Mobile No. validated");
                    return;
                } else {
                    System.out.println("Kindly enter digits only");
                }
            } else {
                System.out.println("Enter only 10 Digits");
                continue;
            }
        }
    }

    public void handleAdminOperations()throws Exception {
        boolean flag=true;
        this.con=DBConnection.getConnection();
        while(flag){
            System.out.println("""
                    ----------------------------------------------------------------------------------------------------
                    ------------------------------------------Admin Services--------------------------------------------
                    ----------------------------------------------------------------------------------------------------
                    |                                Press 0 to View Account Details                                   |
                    |                                Press 1 for Customer Services                                     |
                    |                                Press 2 for Supplier Services                                     |
                    |                                Press 3 to View All Transactions                                  |
                    |                                Press 4 for Product Services                                      |
                    |                                Press 5 to Log out                                                |
                    |                                Enter Your Choice:                                                |
                    ----------------------------------------------------------------------------------------------------""");
            int choice = sc.nextInt();
            switch (choice) {
                case 0:{
                    accountOperations();
                    break;
                }
                case 1:{
                    customerServices();
                    break;
                }
                case 2:{
                    supplierServices();
                    break;
                }
                case 3:{
                    viewAllTransactions();
                    break;
                }
                case 4:{
                    productServices();
                    break;
                }
                case 5:{
                    System.out.println("Operations Closed");
                    flag=false;
                    break;
                }
                default:{
                    System.out.println("Enter Valid Option");
                }
            }
        }
    }

    public void accountOperations()throws Exception {
        boolean flag = true;
        while(flag){
            System.out.println("""
                    ----------------------------------------------------------------------------------------------------
                    -------------------------------------------Account Services-----------------------------------------
                    ----------------------------------------------------------------------------------------------------
                   |                                         Press 1 View Profile                                      |
                   |                                         Press 2 Update Profile                                    |
                   |                                         Press 3 Exit                                              |
                   |                                         Enter Your Choice:                                        |
                    ----------------------------------------------------------------------------------------------------""");
            int choice=sc.nextInt();
            switch (choice) {
                case 1:{
                    adminProfileDetails();
                    break;
                }
                case 2:{
                    updateAdminProfileDetails();
                    break;
                }
                case 3:{
                    flag=false;
                    break;
                }
                default:{
                    System.out.println("Enter Valid Option");
                }
            }
        }
    }

    public void customerServices()throws Exception{
        boolean flag=true;
        while(flag){
            System.out.println("""
                    
                    ----------------------------------------------------------------------------------------------------
                    -----------------------------------------Customer Services------------------------------------------
                    ----------------------------------------------------------------------------------------------------
                    |                                   Press 1 to View All Customers                                  |
                    |                                   Press 2 to Register a Customer                                 |
                    |                                   Press 3 to Exit                                                |
                    ----------------------------------------------------------------------------------------------------""");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:{
                    viewAllCustomers();
                    break;
                }
                case 2:{
                    try{
                        new Customer().registerCustomer();
                    } catch (Exception e) {
                        System.out.println("Registration Failed");
                    }
                    break;
                }
                case 3:{
                    flag=false;
                    break;
                }
                default:{
                    System.out.println("Enter Valid Option");
                }
            }
        }
    }

    public void supplierServices()throws Exception{
        boolean flag=true;
        while(flag){
            System.out.println("""
                    ----------------------------------------------------------------------------------------------------
                    -----------------------------------------Supplier Services------------------------------------------
                    ----------------------------------------------------------------------------------------------------
                    |                                    Press 1 to View All Supplier                                  |
                    |                                    Press 2 to Register a Supplier                                |
                    |                                    Press 3 to Exit                                               |
                    ----------------------------------------------------------------------------------------------------""");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:{
                    viewAllSuppliers();
                    break;
                }
                case 2:{
                    try{
                        new Supplier().registerSupplier();
                    } catch (Exception e) {
                        System.out.println("Registration Failed");
                    }
                    break;
                }
                case 3:{
                    flag=false;
                    break;
                }
                default:{
                    System.out.println("Enter Valid Option");
                }
            }
        }
    }

    public void adminProfileDetails()throws Exception {
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("------------------------------------------Profile---------------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------------------------");
        String query1="Create View if not exists AdminProfile"+getAdmin_id()+" as select admin_id,emailid,fullname,mobileno from admin where admin_id="+getAdmin_id()+";";
        String query2="Select * from AdminProfile"+getAdmin_id();
        Statement st=con.createStatement();
        st.executeUpdate(query1);
        ResultSet rs=st.executeQuery(query2);
        if(rs.next()) {
            System.out.println("Admin_Id: "+rs.getInt(1));
            System.out.println("EmailId: "+rs.getString(2));
            System.out.println("Name: "+rs.getString(3));
            mobileNo=rs.getString(4);
            System.out.println("MobileNo: "+getEncrytedMobileNo());
        }else{
            System.out.println("View Profile Operation Failed");
        }
        System.out.println("----------------------------------------------------------------------------------------------------");
    }

    public String getEncrytedMobileNo() {
        String encmb=getMobileNo().substring(0,6).concat("****");
        return encmb;
    }

    public void updateAdminProfileDetails()throws Exception {
        boolean flag=true;
        while(flag) {
            System.out.println("""
                    ----------------------------------------------------------------------------------------------------
                    ---------------------------------------Admin Update Panel-------------------------------------------
                    ----------------------------------------------------------------------------------------------------
                    |                                    Press 1 Update Name                                           |
                    |                                    Press 2 Update Mobile No                                      |
                    |                                    Press 3 Update Password                                       |
                    |                                    Press 4 Exit                                                  |
                    |                                    Enter Your Choice:                                            |
                    ----------------------------------------------------------------------------------------------------""");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:{
                    sc.nextLine();
                    System.out.println("-----------------------------------------");
                    System.out.println("Your Current Name: "+getFullName());
                    System.out.println("-----------------------------------------");
                    System.out.println("Do you really want to update your Name: ");
                    String ans=sc.nextLine();
                    if(ans.equalsIgnoreCase("Yes")){
                        System.out.println("Enter Your Name: ");
                        String name=sc.nextLine();

                        String query="Update admin set fullname=? where fullname=?";
                        PreparedStatement pst=con.prepareStatement(query);
                        pst.setString(1, name);
                        pst.setString(2, getFullName());
                        int n=pst.executeUpdate();
                        if(n>0){
                            System.out.println("Name is updated successfully");
                            fullName=name;
                        }else{
                            System.out.println("Name is not updated successfully");
                        }
                    }
                    break;
                }
                case 2:{
                    sc.nextLine();
                    System.out.println("-----------------------------------------");
                    System.out.println("Your Current Mobile No.: "+getMobileNo());
                    System.out.println("-----------------------------------------");
                    System.out.println("Do your really want to update your Mobile No: ");
                    String ans=sc.nextLine();
                    if(ans.equalsIgnoreCase("Yes")){
                        validateMobileNo();
                        String query="Update admin set mobileno=? where admin_id=?";
                        PreparedStatement pst=con.prepareStatement(query);
                        pst.setString(1, getMobileNo());
                        pst.setInt(2, getAdmin_id());
                        int n=pst.executeUpdate();
                        if(n>0){
                            System.out.println("Mobile No is updated successfully");
                        }else{
                            System.out.println("Mobile No is not updated successfully");
                        }
                    }
                    break;
                }
                case 3:{
                    sc.nextLine();
                    System.out.println("Enter Your Old Password: ");
                    String old=sc.nextLine();
                    if(ispwdCorrect(getEmailId(),old)){
                        validatepwd();
                        String query="Update admin set password=? where admin_id=?";
                        PreparedStatement pst=con.prepareStatement(query);
                        pst.setString(1, getPwd());
                        pst.setInt(2, getAdmin_id());
                        int n=pst.executeUpdate();
                        if(n>0){
                            System.out.println("Old Password is updated successfully");
                        }else{
                            System.out.println("Old Password is not updated successfully");
                        }
                    }else{
                        System.out.println("Invalid Password");
                    }
                    break;
                }
                case 4:{
                    flag=false;
                    break;
                }
                default:{
                    System.out.println("Enter Valid Option");
                }
            }
        }
    }

    public void viewAllCustomers()throws Exception {
        String query="Select cust_id,emailid,fullname,mobileno,address from Customer;";
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(query);
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("-----------------------------------All Customer Details---------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.printf("%-20s%-30s%-20s%-20s%-20s","Customer_ID","EmailId","FullName","MobileNo","Address");
        System.out.println();
        while(rs.next()){
            System.out.printf("%-20d%-30s%-20s%-20s%-20s",rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
            System.out.println();
        }
        System.out.println("----------------------------------------------------------------------------------------------------");
    }

    public void viewAllSuppliers()throws Exception {
        String query="Select supplier_id,emailid,fullname,mobileno,companyname,companyaddress from Supplier;";
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(query);
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("--------------------------------------All Supplier Details------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.printf("%-20s%-30s%-20s%-20s%-20s%-20s","Supplier_ID","EmailId","FullName","MobileNo","Company Name","Company Address");
        System.out.println();
        while(rs.next()){
            System.out.printf("%-20d%-30s%-20s%-20s%-20s%-20s",rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
            System.out.println();
        }
        System.out.println("----------------------------------------------------------------------------------------------------");
    }

    public void viewAllTransactions()throws Exception{
        double amount=0;
        String query="Select * from Transaction order by order_id;";
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(query);
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("--------------------------------------All Transaction Details---------------------------------------");
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s","Transaction_ID","Customer_ID","Order_ID","Amount","Transaction_Type","Transaction Time");
        System.out.println();
        while(rs.next()){
            System.out.printf("%-20d%-20d%-20d%-20f%-20s%-20s",rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getDouble(4),rs.getString(5),rs.getTimestamp(6));
            amount+=rs.getDouble(4);
            System.out.println();
        }
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("Total Amount: "+amount);
        System.out.println("----------------------------------------------------------------------------------------------------");
    }

    public void productServices()throws Exception{
        boolean flag=true;
        Product product=new Product();
        while(flag){
            System.out.println("""
                    ----------------------------------------------------------------------------------------------------
                    -------------------------------------------Product Services-----------------------------------------
                    ----------------------------------------------------------------------------------------------------
                    |                                        Press 1 Add Product                                       |
                    |                                        Press 2 Update Product                                    |
                    |                                        Press 3 Delete Product                                    |
                    |                                        Press 4 View Products By ID                               |
                    |                                        Press 5 View Products By Name                             |
                    |                                        Press 6 View Products By Category                         |
                    |                                        Press 7 View Ordered Products                             |
                    |                                        Press 8 View All Products                                 |
                    |                                        Press 9 View Returned Products                            |
                    |                                        Press 10 Exit                                             |
                    ----------------------------------------------------------------------------------------------------""");
            int choice=sc.nextInt();

            switch(choice){
                case 1:{
                    product.addProduct();
                    break;
                }
                case 2:{
                    product.updateProduct();
                    break;
                }
                case 3:{
                    product.deleteProduct();
                    break;
                }
                case 4:{
                    product.getProductById();
                    break;
                }
                case 5:{
                    product.getProductByName();
                    break;
                }
                case 6:{
                    product.getProductByCategory();
                    break;
                }
                case 7:{
                    viewAllOrderedProducts();
                    break;
                }
                case 8:{
//                    viewAllProducts();
                    product.displayAllProducts(product.getAllProducts());
                    break;
                }
                case 9:{
                    viewAllReturnedProducts();
                    break;
                }
                case 10:{
                    flag=false;
                    break;
                }
                default:{
                    System.out.println("Enter Valid Option");
                }
            }
        }
    }

    public void viewAllProducts()throws Exception{
        String query="Select * from Product;";
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(query);
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("------------------------------------------Product Details-------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s","Product_ID","Product Name","Category","Status","Rate","Quantity","Supplier_Id");
        System.out.println();
        while(rs.next()){
            System.out.printf("%-20d%-20s%-20s%-20s%-20f%-20d%-20d",rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDouble(5),rs.getInt(6),rs.getInt(7));
            System.out.println();
        }
        System.out.println("----------------------------------------------------------------------------------------------------");
    }

    public void viewAllOrderedProducts()throws Exception{
        String query="Select * from purchased_product order by order_id;";
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(query);
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("------------------------------------Ordered Product Details-----------------------------------------");
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s","Order_ID","Cust_Id","Product_Id","Quantity","Amount","Time Of Purchasing");
        System.out.println();
        while(rs.next()){
            System.out.printf("%-20d%-20d%-20d%-20d%-20f%-20s",rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(6),rs.getDouble(7),rs.getTimestamp(8));
            Product product=new Product();
            product=product.selectProductById(rs.getInt(3));
            System.out.println();
            System.out.println(product.getMinorDetails());
            System.out.println();
        }
        System.out.println("----------------------------------------------------------------------------------------------------");
    }

    public void viewAllReturnedProducts() throws Exception{
        String query="Select * from returned_product order by return_order_id;";
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(query);
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("---------------------------------------Returned Product Details-------------------------------------");
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.printf("%-25s%-20s%-20s%-20s%-25s%-20s%-20s%-20s%-25s%-25s%-20s","Return_Order_Id","Order_Id","Customer_Id","Product_Id","Return_Transaction_Id","Rate","Quantity","Amount","Time of Purchasing","Time of Returning","Time Gap(Days)");
        System.out.println();
        System.out.println("----------------------------------------------------------------------------------------------------");
        while(rs.next()){
            System.out.printf("%-25d%-20d%-20d%-20d%-25d%-20f%-20d%-20f%-25s%-25s%-20d",rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getDouble(6),rs.getInt(7),rs.getDouble(8),rs.getTimestamp(9),rs.getTimestamp(10),rs.getLong(11));
            Product product=new Product();
            product=product.selectProductById(rs.getInt(4));
            System.out.println();
            System.out.println(product.getMinorDetails());
            System.out.println("----------------------------------------------------------------------------------------------------");
        }
    }
}