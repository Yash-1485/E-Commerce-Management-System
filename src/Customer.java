import DS.CartList;
import DS.SearchStack;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import javax.swing.*;
import java.sql.*;

import java.util.*;
import java.io.*;

public class Customer {
    Scanner sc = new Scanner(System.in);
    SearchStack<String> search;
    int cust_Id;
    String emailId;
    String pwd;
    String fullName;
    String mobileNo;
    String address;
    Connection con;
    Cart cart;

    public Customer() throws Exception {
        con = DBConnection.getConnection();
        search = new SearchStack<>();
        cart=new Cart();
    }

    public void setCust_Id(int cust_Id) {
        this.cust_Id = cust_Id;
    }

    public void setEmailId(String emailId) {
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

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCust_Id() {
        return cust_Id;
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

    public String getAddress() {
        return address;
    }

    public Customer(int cust_Id, String emailId, String pwd, String fullName, String mobileNo, String address)throws Exception {
        this.cust_Id = cust_Id;
        this.emailId = emailId;
        this.pwd = pwd;
        this.fullName = fullName;
        this.mobileNo = mobileNo;
        this.address = address;
        con = DBConnection.getConnection();
        search = new SearchStack<>();
        cart=new Cart();
    }

    public String minorCustomerDetails() {
        return "Customer details: \nCust_Id: "+getCust_Id()+"\nName: "+getFullName()+"\nAddress: "+getAddress()+"\nMobile No.: "+getMobileNo()+"\nEmail Id: "+getEmailId();
    }

//    public Customer getCustomer(){
//        return this;
//    }

    public Customer getCustomer(String emailId,String pwd) throws Exception{
        String query="select * from customer where emailid=? and password=?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, emailId);
        pst.setString(2, pwd);
        ResultSet rs = pst.executeQuery();
        Customer customer=new Customer();
        if(rs.next()) {
            customer=new Customer(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
        }
        return customer;
    }

    public void registerCustomer() throws Exception {
        validateEmailId();
        validatepwd();
        validateMobileNo();
        System.out.println("Enter Your FullName: ");
        fullName = sc.nextLine();
        System.out.println("Enter Address: ");
        address = sc.nextLine();

        String query = "insert into customer(emailid,password,fullname,mobileno,address) values(?,?,?,?,?);";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, emailId);
        pst.setString(2, pwd);
        pst.setString(3, fullName);
        pst.setString(4, mobileNo);
        pst.setString(5, address);
        int n = pst.executeUpdate();
        if (n > 0) {
            System.out.println("Registration Successfully");
        } else {
            throw new Exception("Registration Failed");
        }
    }

    public void getCustomerById() throws Exception {

    }

    public void validateEmailId() throws Exception {
        while (true) {
            // sc.nextLine();
            System.out.println("Enter Emaild Id: ");
            emailId = sc.nextLine();
            boolean isExists = isEmailidExists(emailId);
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

    public boolean isEmailidExists(String emailId) throws Exception {
        String query = "Select emailid from customer where emailid=?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, emailId);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }

    public boolean ispwdCorrect(String emailid, String pwd) throws Exception {
        String query = "Select password from customer where emailid = ?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, emailid);
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
                    System.out.println("Kindally enter digits only");
                }
            } else {
                System.out.println("Enter only 10 Digits");
                continue;
            }
        }
    }

    public boolean isCustomer() throws Exception {
        System.out.println("Enter E-Mail Id: ");
        emailId = sc.nextLine();
        System.out.println("Enter Password: ");
        pwd = sc.nextLine();

        String query = "select * from customer where emailid=? and password=?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, emailId);
        pst.setString(2, pwd);
        ResultSet rs = pst.executeQuery();
        if (!rs.next()) {
            throw new Exception("Not a valid customer\nIf you didn't registered, then register yourself first");
        } else {
            return true;
        }
    }

    public void handleCustomerOperations() throws Exception {
        boolean flag=true;
        Product p = new Product();
        p.con=this.con=DBConnection.getConnection();
        while (flag) {
            System.out.println("""
                    ----------------------------------------------------------------------------------------------------
                    ========================================Customer Services===========================================
                    ----------------------------------------------------------------------------------------------------
                    |                                Press 0 for View Account Details                                  |
                    |                                Press 1 for View All Products                                     |
                    |                                Press 2 for Search Product By Name                                |
                    |                                Press 3 for Search Product By Category                            |
                    |                                Press 4 for Add Product to Cart By ProductId                      |
                    |                                Press 5 for View Cart                                             |
                    |                                Press 6 for Update Cart                                           |
                    |                                Press 7 for Transaction & Purchase                                |
                    |                                Press 8 for Return Purchased Product                              |
                    |                                Press 9 for Log Out                                               |
                    ----------------------------------------------------------------------------------------------------""");
            int choice = sc.nextInt();

            switch (choice) {
                case 0:{
                    accountOperations();
                    break;
                }
                case 1: {
                    p.displayAllProducts(p.getAllProducts());
                    break;
                }
                case 2: {
                    sc.nextLine();
                    System.out.println("Enter Product Name to Search: ");
                    String productName = sc.nextLine();
                    boolean isProduct = p.isProductByName(productName);
                    if (isProduct) {
                        p.getProductByName(productName);
                        search.push(productName);
                        searchHistory();
                    } else {
                        search.push(productName);
                        searchHistory();
                        System.out.println("Product not found!!");
                    }
                    break;
                }
                case 3: {
                    sc.nextLine();
                    System.out.println("Enter Product Category: ");
                    String category = sc.nextLine();
                    boolean isProduct = p.isProductByCategory(category);
                    if (isProduct) {
                        p.getProductByCategory(category);
                        search.push(category);
                        searchHistory();
                    } else {
                        search.push(category);
                        searchHistory();
                        System.out.println("Product not found!!");
                    }
                    break;
                }
                case 4: {
                    System.out.println("Enter Product Id: ");
                    int product_Id = sc.nextInt();
                    boolean isProduct = p.isProductById(product_Id);
                    if (isProduct) {
                        boolean isAvailable=p.validateProductStatus(product_Id,this.con);
                        if(!isAvailable) {
                            System.out.println("Sorry, we apologise to you,  Product is not available");
                            System.out.println("Product will be available soon");
                        }else{
                            boolean isInCart=isAlredyInCart(product_Id);
                            if(isInCart) {
                                System.out.println("Product is already in Cart");
                                sc.nextLine();
                                System.out.println("Do you want to update product Quantity(Yes/No): ");
                                String ans=sc.nextLine();
                                if(ans.equalsIgnoreCase("Yes")) {
                                    updateCartItemQuantity(product_Id);
                                }else{
                                    System.out.println("--------------------------------------");
                                }
                            }else{
                                int quantity=validateProductPurchaseQuantity(product_Id);
                                if(quantity>0) {
                                    CartItem item=new CartItem(p.addProductById(product_Id),quantity);
                                    cart.items.add(item);
                                    System.out.println("Product added to cart");
                                }else{
                                    System.out.println("Product is not added to cart as quantity is 0 or less than it");
                                }
                            }
                        }
                    } else {
                        System.out.println("Product Not Found!!!");
                    }
                    break;
                }
                case 5:{
                    System.out.println(getCust_Id());
                    cart.viewCart();
                    break;
                }
                case 6:{
                    updateCart();
                    break;
                }
                case 7:{
                    if(cart.items.isEmpty()){
                        System.out.println("Buy at least a product");
                    }else{
                        this.purchase();
                    }
                    break;
                }
                case 8:{
                    returnPurchasedOrder();
                    break;
                }
                case 9: {
                    System.out.println("Operations Closed");
                    flag=false;
                    break;
                }
                default: {
                    System.out.println("Enter Valid Option");
                }
            }
        }
    }

    public int validateProductPurchaseQuantity(int product_Id)throws Exception{
        System.out.println("If you enters 0 then it product will not be conducted");
        System.out.println("Enter Product Quantity: ");
        int quantity=0;
        while(true){
            quantity = sc.nextInt();
            Product p=new Product();
            p.con=this.con=DBConnection.getConnection();
            p= p.addProductById(product_Id);
            boolean isOkay=p.validateQuantity(product_Id,quantity,this.con);
            if(isOkay) {
                break;
            }else{
                System.out.println("Please re-enter quantity between 1 and "+p.getQuantity());
            }
        }
        return quantity;
    }

    public boolean isAlredyInCart(int product_Id)throws Exception{
        Product p=new Product();
        p.con=DBConnection.getConnection();
        boolean isProduct = p.isProductById(product_Id);
        if(isProduct){
            boolean isInCart=false;
            CartList<CartItem> cartList=cart.getItems();
            for(int i=0;i<cartList.size();i++){
                if(cartList.get(i).getProduct().getProduct_Id()==product_Id){
                    isInCart=true;
                    break;
                }
            }
            return isInCart;
        }else {
            return false;
        }
    }

    public void updateCartItemQuantity(int product_Id) throws Exception{
        Product p=new Product();
        p.con=DBConnection.getConnection();
        boolean isProduct = p.isProductById(product_Id);
        if(isProduct){
            boolean isInCart=false;
            CartList<CartItem> cartList=cart.getItems();
            for(int i=0;i<cartList.size();i++){
                if(cartList.get(i).getProduct().getProduct_Id()==product_Id){
                    isInCart=true;
                    System.out.println("Previous Product Quantity: "+cartList.get(i).getQuantity());
                    System.out.println("Enter New Quantity of Product: ");
                    int new_quantity = validateProductPurchaseQuantity(product_Id);
                    if(new_quantity>0) {
                        cartList.get(i).setQuantity(new_quantity);
                        System.out.println("Quantity of product updated");
                    }else{
                        boolean isRemoved= cart.removeItem(product_Id);
                        if(isRemoved){
                            System.out.println("Product is removed from cart as you entered quantity 0");
                        }
                    }
                    break;
                }
            }
            if(!isInCart){
                System.out.println("Product is not in the cart");
                return;
            }
        }else{
            System.out.println("Product Not Found!!!");
            return;
        }
    }

    public void searchHistory() throws Exception {
        System.out.println("-------------Search History-------------");
        for (int i = search.size() - 1; i >= 0; i--) {
            System.out.println(search.get(i));
        }
        System.out.println("----------------------------------------");
    }

    public void updateCart()throws Exception{
        boolean flag=true;
        Product p = new Product();
        p.con=DBConnection.getConnection();
        while (flag) {
            System.out.println("---------------------------------------------------------------------------------------");
            System.out.println("-----------------------------------Cart Operations-------------------------------------");
            System.out.println("---------------------------------------------------------------------------------------");
            System.out.println("|                             Press 1 to Show Cart                                    |");
            System.out.println("|                             Press 2 to Add Product                                  |");
            System.out.println("|                             Press 3 to Remove Product                               |");
            System.out.println("|                             Press 4 to Update Product Quantity                      |");
            System.out.println("|                             Press 5 to Search Product                               |");
            System.out.println("|                             Press 6 to View Previous Orders                         |");
            System.out.println("|                             Press 7 to Exit                                         |");
            System.out.println("---------------------------------------------------------------------------------------");
            int choice = sc.nextInt();
            System.out.println("----------------------------------------------------------------------------------------");
            switch (choice) {
                case 1: {
                    cart.viewCart();
                    break;
                }
                case 2:{
                    System.out.println("Enter Product Id: ");
                    int product_Id = sc.nextInt();
                    boolean isProduct = p.isProductById(product_Id);
                    if (isProduct) {
                        boolean isAvailable=p.validateProductStatus(product_Id,this.con);
                        if(!isAvailable) {
                            System.out.println("Sorry, we apologise to you,  Product is not available");
                        }else{
                            boolean isInCart=isAlredyInCart(product_Id);
                            if(isInCart) {
                                System.out.println("Product is already in Cart");
                                sc.nextLine();
                                System.out.println("Do you want to update product Quantity(Yes/No): ");
                                String ans=sc.nextLine();
                                if(ans.equalsIgnoreCase("Yes")) {
                                    updateCartItemQuantity(product_Id);
                                }else{
                                    System.out.println("--------------------------------------");
                                }
                            }else{
                                int quantity=validateProductPurchaseQuantity(product_Id);
                                if(quantity>0) {
                                    CartItem item=new CartItem(p.addProductById(product_Id),quantity);
                                    cart.items.add(item);
                                    System.out.println("Product added to cart");
                                }else{
                                    System.out.println("Product is not added to cart as quantity is 0 or less than it");
                                }
                            }
                        }
                    } else {
                        System.out.println("Product Not Found!!!");
                    }
                    break;
                }
                case 3:{
                    System.out.println("Enter Product Id: ");
                    int product_Id = sc.nextInt();
                    boolean isProduct = p.isProductById(product_Id);
                    if(isProduct){
                        boolean isRemoved= cart.removeItem(product_Id);
                        if(isRemoved){
                            System.out.println("Product removed from the cart");
                        }else{
                            System.out.println("Product doesn't exists in the cart");
                        }
                    }else{
                        System.out.println("Product not found!!");
                    }
                    break;
                }
                case 4:{
                    System.out.println("Enter Product Id: ");
                    int product_Id = sc.nextInt();
                    boolean isProduct = p.isProductById(product_Id);
                    if(isProduct){
                        boolean isInCart=false;
                        CartList<CartItem> cartList=cart.getItems();
                        for(int i=0;i<cartList.size();i++){
                            if(cartList.get(i).getProduct().getProduct_Id()==product_Id){
                                isInCart=true;
                                System.out.println("Previous Product Quantity: "+cartList.get(i).getQuantity());
                                System.out.println("Enter New Quantity of Product: ");
                                int quantity = sc.nextInt();
                                cartList.get(i).setQuantity(quantity);
                                System.out.println("Quantity of product updated");
                                break;
                            }
                        }
                        if(!isInCart){
                            System.out.println("Product is not in the cart");
                        }
                    }else{
                        System.out.println("Product Not Found!!!");
                    }
                    break;
                }
                case 5:{
                    System.out.println("Enter Product Id: ");
                    int product_Id = sc.nextInt();
                    boolean isProduct = p.isProductById(product_Id);
                    if(isProduct){
                        boolean isInCart=false;
                        CartList<CartItem> cartList=cart.getItems();
                        for(int i=0;i<cartList.size();i++){
                            if(cartList.get(i).getProduct().getProduct_Id()==product_Id){
                                isInCart=true;
                            }
                        }
                        if(isInCart){
                            System.out.println("Product is already in the cart");
                        }else{
                            System.out.println("Product is not in the cart");
                        }
                    }else {
                        System.out.println("Product Not Found!!!");
                    }
                    break;
                }
                case 6:{
                    viewOrders();
                    break;
                }
                case 7:{
                    flag=false;
                    break;
                }
                default:{
                    System.out.println("Enter Valid Option");
                }
            }
            System.out.println("----------------------------------------------------------------------------------------");
        }
    }

    public void purchase()throws Exception{
        try{
            con.setAutoCommit(false);
            cart.viewCart();
            double total=cart.getTotal();
            System.out.println("Total payable amount is: "+total);
            CartList<CartItem> cartList=cart.getItems();
            sc.nextLine();
            System.out.println("Do you want to do transaction?(Yes/No)");
            String ans=sc.nextLine();
            if(ans.equalsIgnoreCase("Yes")){
                String trsn_type=selectTransactionType();
                Timestamp trsn_time=new Timestamp(System.currentTimeMillis());
                int order_id=orders(cust_Id,total,trsn_time);
                File file=new File("Bills/Customer-"+getCust_Id()+"-OrderNo-"+order_id+".txt");
                BufferedWriter bw=new BufferedWriter(new FileWriter(file));
                boolean isOkay=transaction(getCust_Id(),order_id,total,trsn_type);
                int transaction_id=getTransactionId(order_id,cust_Id);
                bw.write("----------------------------------Bill No.: "+order_id+"----------------------------------");
                bw.newLine();
                bw.write(minorCustomerDetails());
                bw.newLine();
                bw.write("------------------------------------------------------------------------------------------");
                bw.newLine();
                bw.flush();
                if(isOkay){
                    bw.write("-------------------------------------Description------------------------------------------");
                    bw.newLine();
                    bw.write("------------------------------------------------------------------------------------------");
                    bw.newLine();
                    bw.write(String.format("%-20s%-40s%-20s%-20s%-20s%-20s", "Product_Id", "Product_Name", "Category", "Rate",
                            "Quantity", "Amount"));
                    bw.newLine();
                    bw.write("------------------------------------------------------------------------------------------");
                    bw.newLine();
                    for(int i=0;i<cartList.size();i++){
                        CartItem item=cartList.get(i);
                        Product product=item.getProduct();
                        int productId=product.getProduct_Id();
                        int quantity= item.getQuantity();
                        bw.write(String.format("%-20d%-40s%-20s%-20f%-20d%-20f", product.getProduct_Id(), product.getProductName(), product.getCategory(), product.getRate(),
                                quantity, quantity*product.getRate()));
                        bw.newLine();
                        bw.write("------------------------------------------------------------------------------------------");
                        bw.newLine();
                        bw.flush();
                        setPurchasedProducts(order_id,getCust_Id(),productId,transaction_id,quantity,product.getRate(),trsn_time);
                        product.updateProductQuantityN(productId,quantity,con);
                    }
                    bw.write("------------------------------------------------------------------------------------------");
                    bw.newLine();
                    bw.write("Total Amount: "+cart.getTotal());
                    bw.newLine();
                    bw.write("------------------------------------------------------------------------------------------");
                    bw.newLine();
                    bw.write("Payment Mode: "+trsn_type);
                    bw.newLine();
                    bw.write("------------------------------------------------------------------------------------------");
                    bw.newLine();
                    bw.write("Transaction Id: "+transaction_id);
                    bw.newLine();
                    bw.write("------------------------------------------------------------------------------------------");
                    bw.newLine();
                    bw.write("-----------------------------------Thank You, Visit Again---------------------------------");
                    bw.newLine();
                    bw.write("------------------------------------------------------------------------------------------");
                    bw.flush();
                    try{
                        System.out.println("Please wait 5 seconds for Confirmation");
                        Thread.sleep(5000);
                        System.out.println("Commiting Changes");
                        System.out.println("Transaction successfull");
                        con.commit();
                        cart.clear();
                        bw.close();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.println("Transaction Failed");
                        System.out.println("Changes rolled back");
                        file.delete();
                        con.rollback();
                    }
                }else{
                    System.out.println("Transaction Failed");
                    file.delete();
                    con.rollback();
                }
            }else{
                System.out.println("Changes rolled back");
                con.rollback();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Transaction Failed");
            System.out.println("Changes rolled back");
            con.rollback();
        }
    }

//    public void generateBills()throws Exception{
//
//    }

    public String selectTransactionType() throws Exception{
        boolean flag=true;
        String type="";
        while(flag){
            System.out.println("""
                    ----------------------------------------------------------------------------------------------------
                    --------------------------------------Transaction Methods-------------------------------------------
                    ----------------------------------------------------------------------------------------------------
                    |                               1 - Cash Payment(Cash On Delivery)                                 |
                    |                               2 - Card Payment                                                   |
                    |                               3 - UPI Payment                                                    |
                    ----------------------------------------------------------------------------------------------------
                    Enter Valid Option""");
            int choice = sc.nextInt();
            switch (choice) {
                case 1: {
                    type="Cash";
                    flag=false;
                    break;
                }
                case 2:{
                    type="Card";
                    validateCardPayment();
                    flag=false;
                    break;
                }
                case 3:{
                    type="UPI";
                    validateUPIPayment();
                    flag=false;
                    break;
                }
                default:{
                    System.out.println("Enter Valid Option");
                }
            }
        }
        return type;
    }

    public void validateCardPayment()throws Exception{
        boolean flag=true;
        sc.nextLine();
        System.out.println("Enter 12 Digit Card Number: ");
        String card="";
        while(flag){
            card=sc.nextLine();
            if(card.length()!=12){
                System.out.println("Kindly Enter 12 Digits Card Number");
                continue;
            }else{
                for(int i=0;i<card.length();i++){
                    if(!Character.isDigit(card.charAt(i))){
                        System.out.println("Kindly enter only digits");
                        flag=false;
                        break;
                    }
                }
                if(flag){
                    int otp=(int)(Math.random()*Math.pow(10,4));
                    JOptionPane.showMessageDialog(null,"OTP: "+otp,"OTP Window",JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("Enter OTP Shown in window: ");
                    int ans=sc.nextInt();
                    if(ans==otp){
                        System.out.println("Transaction Successful");
                        flag=false;
                        break;
                    }else{
                        throw new TransactionFailedException("Transaction Failed to proceed");
                    }
                }else{
                    flag=true;
                    System.out.println("Kindly enter 12 digit card number");
                }
            }
        }
    }

    public void validateUPIPayment()throws Exception{
        boolean flag=true;
        sc.nextLine();
        System.out.println("Enter UPI Pin: ");
        while(flag){
            String pin=sc.nextLine();
            if(pin.length()!=4){
                System.out.println("Kindly Enter 4 digit UPI Pin Number");
            }else{
                flag=false;
                System.out.println("UPI pin validated");
                break;
            }
        }
    }

    public boolean transaction(int cust_id,int order_id,double amount,String trsn_type)throws Exception{
        String query="insert into transaction(cust_id,order_id,amount,trsn_type,trsn_time) values(?,?,?,?,?);";
        PreparedStatement pst=con.prepareStatement(query);
        pst.setInt(1, cust_id);
        pst.setInt(2, order_id);
        pst.setDouble(3, amount);
        Timestamp trsn_time=new Timestamp(System.currentTimeMillis());
        pst.setString(4,trsn_type);
        pst.setTimestamp(5, trsn_time);
        int n=pst.executeUpdate();
        if(n>0){
            return true;
        }else{
            return false;
        }
    }

    public int getTransactionId(int order_id,int cust_Id)throws Exception{
        String query="select transaction_id from transaction where order_id=? and cust_id=?;";
        PreparedStatement pst=con.prepareStatement(query);
        pst.setInt(1, order_id);
        pst.setInt(2, cust_Id);
        ResultSet rs=pst.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public int orders(int cust_Id,double amount,Timestamp time)throws Exception{
        String query1="insert into orders(cust_id,amount,order_time) values(?,?,?);";
        PreparedStatement pst1=con.prepareStatement(query1);
        pst1.setInt(1, cust_Id);
        pst1.setDouble(2, amount);
        pst1.setTimestamp(3, time);
        int n=pst1.executeUpdate();

        if(n>0) {
            String query2 = "select order_id from orders where cust_id=? and order_time=?;";
            PreparedStatement pst2 = con.prepareStatement(query2);
            pst2.setInt(1, cust_Id);
            pst2.setTimestamp(2, time);
            ResultSet rs = pst2.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }else{
            return 0;
        }
    }

    public void setPurchasedProducts(int order_id,int cust_id,int product_id,int transaction_id,int quantity,double rate,Timestamp order_time)throws Exception{
        String query="insert into purchased_product values(?,?,?,?,?,?,?,?);";
        PreparedStatement pst=con.prepareStatement(query);
        double amount=quantity*rate;
        pst.setInt(1, order_id);
        pst.setInt(2, cust_id);
        pst.setInt(3, product_id);
        pst.setInt(4, transaction_id);
        pst.setDouble(5,rate);
        pst.setInt(6, quantity);
        pst.setDouble(7, amount);
        pst.setTimestamp(8, order_time);
        pst.executeUpdate();
    }

    public void accountOperations()throws Exception{
        boolean flag=true;
        while(flag){
            System.out.println("""
                    ----------------------------------------------------------------------------------------------------
                    -----------------------------------------Account Services-------------------------------------------
                    ----------------------------------------------------------------------------------------------------
                    |                                    Press 1 View Profile                                          |
                    |                                    Press 2 Update Profile                                        |
                    |                                    Press 3 View Orders                                           |
                    |                                    Press 4 View Transactions                                     |
                    |                                    Press 5 View Total Transacted Amounts with Products           |
                    |                                    Press 6 View Returned Products                                |
                    |                                    Press 7 Exit                                                  |
                    ----------------------------------------------------------------------------------------------------
                    Enter Your Choice:""");
            int choice=sc.nextInt();

            switch(choice){
                case 1:{
                    customerProfileDetails();
                    break;
                }
                case 2:{
                    updateCustomerProfileDetails();
                    break;
                }
                case 3:{
                    viewOrders();
                    break;
                }
                case 4:{
                    viewTransactions();
                    break;
                }
                case 5:{
                    totalTransactions();
                    break;
                }
                case 6:{
                    getReturnedProducts();
                    break;
                }
                case 7:{
                    flag=false;
                    break;
                }
                default:{
                    System.out.println("Enter Valid Option");
                }
            }
        }
    }

    public void customerProfileDetails()throws Exception{
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("------------------------------------------Profile---------------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------------------------");
        String query1="Create View if not exists CustomerProfile"+getCust_Id()+" as select cust_id,emailid,fullname,address,mobileno from customer where cust_id="+getCust_Id()+";";
        String query2="Select * from CustomerProfile"+getCust_Id();
        Statement st=con.createStatement();
        st.executeUpdate(query1);
        ResultSet rs=st.executeQuery(query2);
        if(rs.next()) {
            System.out.println("Customer_Id: "+rs.getInt(1));
            System.out.println("EmailId: "+rs.getString(2));
            System.out.println("Name: "+rs.getString(3));
            System.out.println("Address: "+rs.getString(4));
            mobileNo=rs.getString(5);
            System.out.println("Mobile No: "+getEncrytedMobileNo());
        }else{
            System.out.println("View Profile Operation Failed");
        }
        System.out.println("----------------------------------------------------------------------------------------------------");
    }

    public String getEncrytedMobileNo() {
        String encmb=getMobileNo().substring(0,6).concat("****");
        return encmb;
    }

    public void updateCustomerProfileDetails()throws Exception{
        boolean flag=true;
        while(flag){
            System.out.println("""
                    ----------------------------------------------------------------------------------------------------
                    --------------------------------------Customer Update Panel-----------------------------------------
                    ----------------------------------------------------------------------------------------------------
                    |                                     1 - Update Name                                              |
                    |                                     2 - Update Address                                           |
                    |                                     3 - Update Mobile No                                         |
                    |                                     4 - Update Password                                          |
                    |                                     5 - Exit                                                     |
                    ----------------------------------------------------------------------------------------------------
                    Enter Your Choice:""");
            int choice=sc.nextInt();

            switch(choice){
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

                        String query="Update customer set fullname=? where fullname=?";
                        PreparedStatement pst=con.prepareStatement(query);
                        pst.setString(1, name);
                        pst.setString(2, getFullName());
                        int n=pst.executeUpdate();
                        if(n>0){
                            System.out.println("Name is updated successfully");
                            this.fullName=name;
                        }else{
                            System.out.println("Name is not updated successfully");
                        }
                    }
                    break;
                }
                case 2:{
                    sc.nextLine();
                    System.out.println("-----------------------------------------");
                    System.out.println("Your Current Address: "+getAddress());
                    System.out.println("-----------------------------------------");
                    System.out.println("Do your really want to update your Address: ");
                    String ans=sc.nextLine();
                    if(ans.equalsIgnoreCase("Yes")){
                        System.out.println("Enter Your Address: ");
                        String address=sc.nextLine();
                        String query="Update customer set address=? where address=?";
                        PreparedStatement pst=con.prepareStatement(query);
                        pst.setString(1, address);
                        pst.setString(2, getAddress());
                        int n=pst.executeUpdate();
                        if(n>0){
                            System.out.println("Address is updated successfully");
                            this.address=address;
                        }else{
                            System.out.println("Address is not updated successfully");
                        }
                    }
                    break;
                }
                case 3:{
                    sc.nextLine();
                    System.out.println("-----------------------------------------");
                    System.out.println("Your Current Mobile No.: "+getMobileNo());
                    System.out.println("-----------------------------------------");
                    System.out.println("Do your really want to update your Mobile No: ");
                    String ans=sc.nextLine();
                    if(ans.equalsIgnoreCase("Yes")){
                        validateMobileNo();
                        String query="Update customer set mobileno=? where cust_id=?";
                        PreparedStatement pst=con.prepareStatement(query);
                        pst.setString(1, getMobileNo());
                        pst.setInt(2, getCust_Id());
                        int n=pst.executeUpdate();
                        if(n>0){
                            System.out.println("Mobile No is updated successfully");
                        }else{
                            System.out.println("Mobile No is not updated successfully");
                        }
                    }
                    break;
                }
                case 4:{
                    sc.nextLine();
                    System.out.println("Enter Your Old Password: ");
                    String old=sc.nextLine();
                    if(ispwdCorrect(getEmailId(),old)){
                        validatepwd();
                        String query="Update customer set password=? where cust_id=?";
                        PreparedStatement pst=con.prepareStatement(query);
                        pst.setString(1, getPwd());
                        pst.setInt(2, getCust_Id());
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
                case 5:{
                    flag=false;
                    break;
                }
                default:{
                    System.out.println("Invalid Option");
                }
            }
        }
    }

    public void viewTransactions()throws Exception{
        boolean flag=false;
        String query="Select transaction_id,order_id,amount,trsn_type,trsn_time from Transaction where cust_id=? order by order_id;";
        PreparedStatement pst=con.prepareStatement(query);
        pst.setInt(1, getCust_Id());
        ResultSet rs=pst.executeQuery();
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("--------------------------------------Transaction Details-------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.printf("%-15s%-15s%-25s%-20s%-20s","Transaction_Id","Order_Id","Amount","Transaction_Type","Transaction Time");
        System.out.println();
        while(rs.next()){
            flag=true;
            System.out.printf("%-15d%-15d%-25f%-15s%-20s",rs.getInt(1),rs.getInt(2),rs.getDouble(3),rs.getString(4),rs.getTimestamp(5));
            System.out.println();
        }
       if(!flag){
           System.out.println("No Transactions Found");
       }
        System.out.println("----------------------------------------------------------------------------------------------------");
    }

    public void viewOrders()throws Exception{
        List<Product> orders=new LinkedList<>();
        List<Double> quantity=new LinkedList<>();
        List<Integer> orderids=new LinkedList<>();
        boolean flag=false;
        String query="Select product_id,quantity,order_id from purchased_product where cust_id=? order by order_id;";
        PreparedStatement pst=con.prepareStatement(query);
        pst.setInt(1, getCust_Id());
        ResultSet rs=pst.executeQuery();
        while(rs.next()){
            flag=true;
            orders.add(new Product().addProductById(rs.getInt(1)));
            quantity.add(rs.getDouble(2));
            orderids.add(rs.getInt(3));
        }
        System.out.println("----------------------------------------------------------------------------------------------------");
        if(flag){
            System.out.println("Orders: ");
            for(int i=0;i<orders.size();i++){
                System.out.println("----------------------------------------------------------------------------------------------------");
                System.out.println("Order Id: "+orderids.get(i));
                System.out.println(orders.get(i).getMinorDetails());
                System.out.println("Quantity: "+quantity.get(i));
                System.out.println("----------------------------------------------------------------------------------------------------");
            }
            System.out.println("----------------------------------------------------------------------------------------------------");
        }
        else{
            System.out.println("No Orders Found");
        }
        System.out.println("----------------------------------------------------------------------------------------------------");
    }


    public void viewOrdersToReturn()throws Exception{
        List<Product> orders=new LinkedList<>();
        List<Double> quantity=new LinkedList<>();
        List<Integer> orderids=new LinkedList<>();
        boolean flag=false;
        String query="Select product_id,quantity,order_id from purchased_product where cust_id=? and order_id not in (select order_id from returned_product) order by order_id asc;";
        //        String query="Select p.product_id,p.quantity from purchased_product p where p.order_id not in (select order_id from returned_product where order_id=? and cust_id=?);";
        PreparedStatement pst=con.prepareStatement(query);
        pst.setInt(1, getCust_Id());
        ResultSet rs=pst.executeQuery();
        while(rs.next()){
            flag=true;
            orders.add(new Product().addProductById(rs.getInt(1)));
            quantity.add(rs.getDouble(2));
            orderids.add(rs.getInt(3));
        }
        System.out.println("----------------------------------------------------------------------------------------------------");
        if(flag){
            System.out.println("Orders: ");
            for(int i=0;i<orders.size();i++){
                System.out.println("----------------------------------------------------------------------------------------------------");
                System.out.println("Order Id: "+orderids.get(i));
                System.out.println(orders.get(i).getMinorDetails());
                System.out.println("Quantity: "+quantity.get(i));
                System.out.println("----------------------------------------------------------------------------------------------------");
            }
            System.out.println("----------------------------------------------------------------------------------------------------");
        }
        else{
//            System.out.println("No Orders Found to be returned");
            throw new Exception("No Orders Found to be returned");
        }
        System.out.println("----------------------------------------------------------------------------------------------------");
    }

    public void totalTransactions()throws Exception{
        String query1="Select p.product_id,p.productname,p.category,p_p.rate,p_p.quantity,p_p.amount,p_p.order_id,p_p.transaction_id,date(p_p.timeOfPurchasing) from Product p inner join Purchased_Product p_p on p.product_id=p_p.product_id where p_p.cust_id="+getCust_Id()+" order by p_p.order_id asc;";
        String query2="Select sum(amount) from transaction where cust_id=?";
        PreparedStatement pst1=con.prepareStatement(query1);
        ResultSet rs=pst1.executeQuery();
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("--------------------------------------Purchased Product Details-------------------------------------");
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s","Product_Id","Product Name","Category","Rate","Quantity","Amount","Order_Id","Transaction_Id","Time Of Purchasing");
        System.out.println();
        while(rs.next()){
            System.out.printf("%-20d%-20s%-20s%-20f%-20d%-20f%-20d%-20d%-20s",rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDouble(4),rs.getInt(5),rs.getDouble(6),rs.getInt(7),rs.getInt(8),rs.getTimestamp(9));
            System.out.println();
        }
        System.out.println("----------------------------------------------------------------------------------------------------");
        PreparedStatement pst2=con.prepareStatement(query2);
        pst2.setInt(1, getCust_Id());
        ResultSet rs2=pst2.executeQuery();
        if(rs2.next()){
            System.out.println("Total Transacted Amount: "+rs2.getDouble(1));
        }else{
            System.out.println("No Transaction Amount Found");
        }
    }

    public void returnPurchasedOrder()throws Exception{
        try{
            con.setAutoCommit(false);
            boolean flag=validateBoughtProducts();
            if(!flag){
                throw new Exception("At least buy a product first");
            }
            viewOrdersToReturn();
            sc.nextLine();
            System.out.println("You can return only once for a particular Order");
            System.out.println("Enter Below Details to Return a Order");
            System.out.println("Enter OrderId: ");
            int orderid=Integer.parseInt(sc.nextLine());
            boolean isOrderId=validateOrderIdForReturn(orderid);
            if(!isOrderId){
                throw new Exception("Invalid OrderId.\nProduct return process failed");
            }
            getProductToBeReturned(orderid);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Rolling back changes");
            con.rollback();
        }finally {
            con.setAutoCommit(true);
        }
    }

    public boolean validateBoughtProducts()throws Exception{
        String query="Select Count(*) from purchased_product where cust_id="+getCust_Id();
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(query);
        int count=0;
        boolean flag=false;
        if(rs.next()){
            count=rs.getInt(1);
            if(count>0){
                flag=true;
            }else{
                flag=false;
            }
        }else{
            return false;
        }
        return flag;
    }

    public boolean validateOrderIdForReturn(int order_id)throws Exception{
        boolean flag=false;
        String query="Select order_id,order_time from orders where order_id not in (Select order_id from returned_product) and cust_id="+getCust_Id()+";";
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(query);
        while(rs.next()){
            if(rs.getInt(1)==order_id){
                Timestamp order_time=rs.getTimestamp(2);
                if(getTimeGap(order_time,new Timestamp(System.currentTimeMillis()))>30){
                    throw new Exception("Order time "+order_time+" is above 30 days.You can't return it.");
                }else{
                    flag=true;
                }
                break;
            }
        }
        return flag;
    }

    public void getProductToBeReturned(int order_id)throws Exception{
        LinkedList<Integer> productids=new LinkedList<>();
        LinkedList<Integer> returnprids=new LinkedList<>();
        LinkedList<Double> rate =new LinkedList<>();
        LinkedList<Integer> quantity=new LinkedList<>();
        LinkedList<Double> amount=new LinkedList<>();
        LinkedList<Integer> transactionids=new LinkedList<>();
        Product product=new Product();
        String query="Select product_id,quantity from purchased_product where order_id=?";
        PreparedStatement pst=con.prepareStatement(query);
        pst.setInt(1, order_id);
        ResultSet rs=pst.executeQuery();
        System.out.println("----------------------------------------------------------------------------------------------------");
        while(rs.next()){
            product=new Product().addProductById(rs.getInt(1));
            productids.add(rs.getInt(1));
            System.out.println(product.getMinorDetails());
            System.out.println("Quantity: "+rs.getInt(2));
            System.out.println("----------------------------------------------------------------------------------------------------");
        }

        System.out.println("Enter Product Ids that you want to return(Enter Done when you completed): ");
        while(true){
            String ans=sc.nextLine();
            if(ans.equalsIgnoreCase("Done")){
                break;
            }
            int n=Integer.parseInt(ans);
            if(productids.contains(n)){
                if(!returnprids.contains(n)){
                    returnprids.add(n);
                }
                else{
                    System.out.println("Product Id already added");
                }
            }else{
                System.out.println("Enter Valid Product Id");
            }
        }

        if(returnprids.isEmpty()){
            throw new Exception("No Product to be return Found");
        }
        double total_return_amount=0;
        for(int i=0;i<returnprids.size();i++){
            String query1="Select rate,quantity,amount,transaction_id from purchased_product where order_id=? and product_id=?";
            PreparedStatement pst1=con.prepareStatement(query1);
            pst1.setInt(1, order_id);
            pst1.setInt(2, returnprids.get(i));
            ResultSet rs1=pst1.executeQuery();
            if(rs1.next()){
                rate.add(rs1.getDouble(1));
                quantity.add(rs1.getInt(2));
                amount.add(rs1.getDouble(3));
                transactionids.add(rs1.getInt(4));
            }
            total_return_amount+=amount.get(i);
            Product product1=new Product().addProductById(returnprids.get(i));
            product1.updateProductQuantityP(returnprids.get(i),quantity.get(i),con);
        }
        Timestamp returnTime=new Timestamp(System.currentTimeMillis());
        int returnOrderId=addToReturnOrder(order_id,total_return_amount,returnTime);
        int returnTrsnId=addToReturnTransaction(returnOrderId,total_return_amount);
        File file=new File("return_Bills/Customer-"+getCust_Id()+"-ReturnOrderNo-"+returnOrderId+".txt");
        BufferedWriter bw=new BufferedWriter(new FileWriter(file));
        bw.write("--------------------------------Return Bill No.: "+returnOrderId+"----------------------------------");
        bw.newLine();
        bw.write(minorCustomerDetails());
        bw.newLine();
        bw.write("----------------------------------------------------------------------------------------------------");
        bw.newLine();
        bw.write("------------------------------------------Description-----------------------------------------------");
        bw.newLine();
        bw.write("----------------------------------------------------------------------------------------------------");
        bw.newLine();
        bw.write(String.format("%-20s%-40s%-20s%-20s%-20s%-20s", "Product_Id", "Product_Name", "Category", "Rate",
                "Quantity", "Return Amount"));
        bw.newLine();
        bw.write("----------------------------------------------------------------------------------------------------");
        bw.newLine();
        bw.flush();
        for(int i=0;i<returnprids.size();i++){
            Timestamp purchaseTime=getTimeOfPurchasingOrder(order_id,transactionids.get(i),productids.get(i));
            Long timegap=getTimeGap(returnTime,purchaseTime);
            addToReturnedProduct(returnOrderId,order_id,productids.get(i),returnTrsnId,rate.get(i),quantity.get(i),amount.get(i),purchaseTime,returnTime,timegap);
            Product product1=new Product().addProductById(returnprids.get(i));
            bw.write(String.format("%-20d%-40s%-20s%-20f%-20d%-20f", product1.getProduct_Id(), product1.getProductName(), product1.getCategory(), product1.getRate(),
                    quantity.get(i), quantity.get(i)*product1.getRate()));
            bw.newLine();
            bw.write("----------------------------------------------------------------------------------------------------");
            bw.newLine();
            bw.flush();
        }
        bw.write("----------------------------------------------------------------------------------------------------");
        bw.newLine();
        bw.write("Total Return Amount: "+total_return_amount);
        bw.newLine();
        bw.write("----------------------------------------------------------------------------------------------------");
        bw.newLine();
        bw.write("Return Transaction Id: "+returnTrsnId);
        bw.newLine();
        bw.write("----------------------------------------------------------------------------------------------------");
        bw.newLine();
        bw.write("-----------------------------------------Thank You, Visit Again-------------------------------------");
        bw.newLine();
        bw.write("----------------------------------------------------------------------------------------------------");
        bw.flush();
        try{
            System.out.println("Money will be transfer to you within 10 working days");
            System.out.println("Please wait for 5 seconds for confirmation");
            Thread.sleep(5000);
            System.out.println("Commiting Changes");
            System.out.println("Product Return Process Finished");
            con.commit();
            bw.close();
        } catch (Exception e) {
            System.out.println("Order return process failed");
            System.out.println("Rolling back changes");
            file.delete();
            con.rollback();
        }
    }

    public int addToReturnOrder(int order_Id,double totalAmount,Timestamp returnTime) throws Exception{
        int returnorderId=0;
        String query="Insert into returnOrders(cust_id,order_id,amount,return_order_time) values(?,?,?,?);";
        PreparedStatement pst=con.prepareStatement(query);
        pst.setInt(1, getCust_Id());
        pst.setInt(2, order_Id);
        pst.setDouble(3,totalAmount);
        pst.setTimestamp(4,returnTime);
        int n=pst.executeUpdate();
        if(n>0){
            String query1="Select return_order_id from returnOrders where cust_id=? and order_id=? and return_order_time=?";
            PreparedStatement pst1=con.prepareStatement(query1);
            pst1.setInt(1, getCust_Id());
            pst1.setInt(2, order_Id);
            pst1.setTimestamp(3, returnTime);
            ResultSet rs1=pst1.executeQuery();
            if(rs1.next()){
                returnorderId=rs1.getInt(1);
            }
            return returnorderId;
        }
        return returnorderId;
    }

    public int addToReturnTransaction(int return_order_Id,double totalAmount) throws Exception{
        int returnTransactionId=0;
        Timestamp trsnTime=new Timestamp(System.currentTimeMillis());
        String query="Insert into returntransaction(return_order_id,amount,return_trsn_time) values(?,?,?);";
        PreparedStatement pst=con.prepareStatement(query);
        pst.setInt(1, return_order_Id);
        pst.setDouble(2, totalAmount);
        pst.setTimestamp(3, trsnTime);
        int n=pst.executeUpdate();
        if(n>0){
            String query1="Select return_transaction_id from returnTransaction where return_order_id=? and amount=? and return_trsn_time=?;";
            PreparedStatement pst1=con.prepareStatement(query1);
            pst1.setInt(1, return_order_Id);
            pst1.setDouble(2, totalAmount);
            pst1.setTimestamp(3, trsnTime);
            ResultSet rs1=pst1.executeQuery();
            if(rs1.next()){
                returnTransactionId=rs1.getInt(1);
                return returnTransactionId;
            }
        }
        return returnTransactionId;
    }

    public boolean addToReturnedProduct(int return_order_id,int order_id,int product_id,int return_transaction_id,double rate,int quantity,double amount,Timestamp timeOfPurchasing,Timestamp timeOfReturning,Long timegap) throws Exception{
        String query="Insert into returned_product values(?,?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement pst=con.prepareStatement(query);
        pst.setInt(1, return_order_id);
        pst.setInt(2, order_id);
        pst.setInt(3, getCust_Id());
        pst.setInt(4, product_id);
        pst.setInt(5, return_transaction_id);
        pst.setDouble(6, rate);
        pst.setInt(7, quantity);
        pst.setDouble(8, amount);
        pst.setTimestamp(9, timeOfPurchasing);
        pst.setTimestamp(10, timeOfReturning);
        pst.setLong(11, timegap);
        int n=pst.executeUpdate();
        if(n>0){
            return true;
        }
        return false;
    }

    public Timestamp getTimeOfPurchasingOrder(int order_id,int transaction_id,int product_id) throws Exception{
        String query="Select timeOfPurchasing from purchased_product where order_id=? and cust_id=? and product_id=? and transaction_id=?";
        PreparedStatement pst=con.prepareStatement(query);
        pst.setInt(1, order_id);
        pst.setInt(2, getCust_Id());
        pst.setInt(3, product_id);
        pst.setInt(4, transaction_id);
        ResultSet rs=pst.executeQuery();
        if(rs.next()){
            return rs.getTimestamp(1);
        }else{
            return null;
        }
    }

    public Long getTimeGap(Timestamp starting_time,Timestamp ending_time) throws Exception{
        String query="Select Datediff(?,?);";
        PreparedStatement pst=con.prepareStatement(query);
        pst.setTimestamp(1, starting_time);
        pst.setTimestamp(2, ending_time);
        ResultSet rs=pst.executeQuery();
        if(rs.next()){
            return rs.getLong(1);
        }else{
            return null;
        }
    }

//    public void getAccountNumber() throws Exception{
//
//    }

    public void getReturnedProducts()throws Exception{
        boolean flag=false;
        String query="Select * from returned_product where cust_id="+getCust_Id()+";";
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(query);
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("----------------------------------------Returned Products-------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.printf("%-25s%-20s%-20s%-25s%-20s%-20s%-20s%-25s%-25s%-20s","Return_Order_Id","Order_Id","Product_Id","Return_Transaction_Id","Rate","Quantity","Amount","Time of Purchasing","Time of Returning","Time Gap(Days)");
        System.out.println();
        System.out.println("----------------------------------------------------------------------------------------------------");
        while(rs.next()){
            flag=true;
            System.out.printf("%-25d%-20d%-20d%-25d%-20f%-20d%-20f%-25s%-25s%-20d",rs.getInt(1),rs.getInt(2),rs.getInt(4),rs.getInt(5),rs.getDouble(6),rs.getInt(7),rs.getDouble(8),rs.getTimestamp(9),rs.getTimestamp(10),rs.getLong(11));
            Product product=new Product();
            product=product.selectProductById(rs.getInt(4));
            System.out.println();
            System.out.println(product.getMinorDetails());
            System.out.println("----------------------------------------------------------------------------------------------------");
        }
        if(!flag){
            System.out.println("No Product Found returned");
            System.out.println("----------------------------------------------------------------------------------------------------");
        }
    }
}

class TransactionFailedException extends RuntimeException{
    TransactionFailedException(){
        super();
    }
    TransactionFailedException(String message){
        super(message);
    }
}