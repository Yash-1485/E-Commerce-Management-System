import DS.ProductList;

import java.util.*;
import java.sql.*;

public class Product {
    static Scanner sc = new Scanner(System.in);
    int product_Id;
    String productName;
    String category;
    String status;
    double rate;
    int quantity;
    int supplier_id;
    int purchaseQuantity;
    ProductList<Product> products;
    Connection con;

    public Product() throws Exception {
        con = DBConnection.getConnection();
        products = new ProductList<>();
    }

    public Product(int product_Id, String productName, String category, String status, double rate, int quantity,
            int supplier_id) {
        this.product_Id = product_Id;
        this.productName = productName;
        this.category = category;
        this.status = status;
        this.rate = rate;
        this.quantity = quantity;
        this.supplier_id = supplier_id;
        this.purchaseQuantity = 0;
    }

    public int getProduct_Id() {
        return product_Id;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
    }

    public double getRate() {
        return rate;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setProduct_Id(int product_Id) {
        this.product_Id = product_Id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(int purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public String getProductDetails() {
        String details = "Product Id: " + getProduct_Id() + "\n" + "Product Name: " + getProductName() + "\n"
                + "Rate: "
                + getRate() + "\n" + "Quantity: " + getQuantity() + "\n" + "Category: " + getCategory() + "\n"
                + "Status: " + getStatus();
        return details;
    }

    public String getMinorDetails(){
        return "Product Id: " + getProduct_Id() + "\n" + "Product Name: " + getProductName() + "\n"+"Category: " + getCategory() + "\n"+"Rate: "+getRate();
    }

    public ProductList<Product> getAllProducts() throws Exception {
        String query = "select * from product;";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            Product product = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                    rs.getDouble(5),
                    rs.getInt(6), rs.getInt(7));
            products.add(product);
        }
        return products;
    }

    public void displayAllProducts(ProductList<Product> products) throws Exception {
        System.out.println("-----------------------------------");
        System.out.println("        Product list");
        System.out.println("-----------------------------------");
        for (Product product : products) {
            System.out.println("--------------------------------------------------");
            System.out.println(product.getProductDetails());
            System.out.println("--------------------------------------------------");
        }
        System.out.println("-----------------------------------");
    }

    public boolean validateProductStatus(int product_Id,Connection con) throws Exception {
        con=DBConnection.getConnection();
        String query = "select status from product where product_Id = " + product_Id;
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            if(rs.getString("status").equals("Available")){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    public boolean validateQuantity(int product_Id, int quantity,Connection con) throws Exception {
        con=DBConnection.getConnection();
        boolean flag=false;
        String query="Select quantity from product where product_Id = " + product_Id;
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            int availableQuantity = rs.getInt("quantity");
            if (availableQuantity >= quantity) {
                flag = true;
            }else{
                flag=false;
            }
        }
        return flag;
    }

    public void getProductById() throws Exception {
        String query = "select * from product where product_id=?";
        PreparedStatement pst = con.prepareStatement(query);
        System.out.println("Enter Product_id: ");
        product_Id = sc.nextInt();
        pst.setInt(1, product_Id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            Product product = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
                    rs.getInt(6), rs.getInt(7));
            System.out.println(product.getProductDetails());
        } else {
            System.out.println("Product Not Found");
        }
    }

    public void getProductById(int product_Id) throws Exception {
        String query = "select * from product where product_id=?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, product_Id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            Product product = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
                    rs.getInt(6), rs.getInt(7));
            System.out.println(product.getProductDetails());
        } else {
            System.out.println("Product Not Found");
        }
    }

    public void getProductByName() throws Exception {
        String query = "select * from product where productname=?";
        PreparedStatement pst = con.prepareStatement(query);
        System.out.println("Enter Product Name: ");
        productName = sc.nextLine();
        pst.setString(1, productName);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            Product product = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
                    rs.getInt(6), rs.getInt(7));
            System.out.println(product.getProductDetails());
        } else {
            System.out.println("Product Not Found");
        }
    }

    public void getProductByName(String productName) throws Exception {
        boolean flag=false;
        String query = "select * from product where productname=?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, productName);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            flag=true;
            Product product = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
                    rs.getInt(6), rs.getInt(7));
            System.out.println("--------------------------------------");
            System.out.println("------------Product Details-----------");
            System.out.println("--------------------------------------");
            System.out.println(product.getProductDetails());
            System.out.println("--------------------------------------");
        }
        if (!flag) {
            System.out.println("Product Not Found");
        }
    }

    public void getProductByCategory() throws Exception {
        String query = "select * from product where category=?";
        PreparedStatement pst = con.prepareStatement(query);
        System.out.println("Enter Product Category: ");
        category = sc.nextLine();
        pst.setString(1, category);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            Product product = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
                    rs.getInt(6), rs.getInt(7));
            System.out.println(product.getProductDetails());
        } else {
            System.out.println("Product Not Found");
        }
    }

    public void getProductByCategory(String category) throws Exception {
        boolean flag=false;
        String query = "select * from product where category=?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, category);
        ResultSet rs = pst.executeQuery();
        while(rs.next()) {
            flag=true;
            Product product = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
                    rs.getInt(6), rs.getInt(7));
            System.out.println("--------------------------------------");
            System.out.println("------------Product Details-----------");
            System.out.println("--------------------------------------");
            System.out.println(product.getProductDetails());
            System.out.println("--------------------------------------");
        }
        if (!flag) {
            System.out.println("Product Not Found");
        }
    }

    public boolean isProductById() throws Exception {
        System.out.println("Enter Product_id: ");
        product_Id = sc.nextInt();
        String query = "select * from product where product_id=?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, product_Id);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }

    public boolean isProductById(int product_Id) throws Exception {
        String query = "select * from product where product_id=?;";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, product_Id);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }

    public boolean isProductByName() throws Exception {
        System.out.println("Enter productname: ");
        productName = sc.nextLine();
        String query = "select * from product where productname=?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, productName);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }

    public boolean isProductByName(String productName) throws Exception {
        String query = "select * from product where productname=?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, productName);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }

    public boolean isProductByCategory() throws Exception {
        System.out.println("Enter Category: ");
        category = sc.nextLine();
        String query = "select * from product where category=?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, category);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }

    public boolean isProductByCategory(String category) throws Exception {
        String query = "select * from product where category=?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, category);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }

    public Product addProductById(int product_Id) throws Exception {
        String query = "select * from product where product_id=?";
        PreparedStatement pst = this.con.prepareStatement(query);
        pst.setInt(1, product_Id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            Product product = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
                    rs.getInt(6), rs.getInt(7));
            return product;
        } else {
            System.out.println("No Product found for id: " + product_Id);
            return null;
        }
    }

    public Product addProductByName(String productName) throws Exception {
        String query = "select * from product where productname=?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, productName);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            Product product = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
                    rs.getInt(6), rs.getInt(7));
            return product;
        } else {
            System.out.println("No Product found for id: " + product_Id);
            return null;
        }
    }

    public Product addProductByCategory(String category) throws Exception {
        String query = "select * from product where category=?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, productName);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            Product product = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
                    rs.getInt(6), rs.getInt(7));
            return product;
        } else {
            System.out.println("No Product found for id: " + product_Id);
            return null;
        }
    }

    public void updateProductQuantityN(int product_Id, int quantity,Connection con) throws Exception {
        this.con=con;
        Product product=this.addProductById(product_Id);
        int updatedQuantity=product.getQuantity()-quantity;
        String query = "update product set quantity=? where product_id=?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, updatedQuantity);
        pst.setInt(2, product_Id);
        int n=pst.executeUpdate();
    }

    public void updateProductQuantityP(int product_Id, int quantity,Connection con) throws Exception {
        this.con=con;
        Product product=this.addProductById(product_Id);
        int updatedQuantity=product.getQuantity()+quantity;
        String query = "update product set quantity=? where product_id=?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, updatedQuantity);
        pst.setInt(2, product_Id);
        int n=pst.executeUpdate();
    }

    public Product selectProductById(int product_Id)throws Exception{
        String query = "select * from product where product_id=?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, product_Id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            Product product = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
                    rs.getInt(6), rs.getInt(7));
            return product;
        } else {
            return null;
        }
    }

    public String setProductStatus(int quantity){
        if(quantity>0){
            status="Available";
        }else if (quantity==0){
            status="Out of Stock";
        }
        return status;
    }

    public int setProductQuantity(){
        while(true){
            System.out.println("Enter Quantity: ");
            quantity = sc.nextInt();
            if(quantity>=0){
                break;
            }
        }
        return quantity;
    }

    public void addProduct()throws Exception{
        System.out.println("Enter Product Details: ");
        System.out.println("Enter Name: ");
        productName = sc.nextLine();
        chooseProductCategory();
        System.out.println("Enter Rate: ");
        rate = sc.nextDouble();
        quantity=setProductQuantity();
        status=setProductStatus(quantity);
        while(true){
            System.out.println("Enter Supplier ID: ");
            supplier_id = sc.nextInt();
            boolean flag=new Supplier().checkSupplierId(supplier_id);
            if(flag){
                break;
            }else{
                System.out.println("Invalid supplier Id.Enter Valid Supplier Id.");
            }
        }
        String query="Insert into Product(productname,category,status,rate,quantity,supplier_id) values(?,?,?,?,?,?);";
        PreparedStatement pst = this.con.prepareStatement(query);
        pst.setString(1, productName);
        pst.setString(2, category);
        pst.setString(3, status);
        pst.setDouble(4, rate);
        pst.setInt(5, quantity);
        pst.setInt(6, supplier_id);
        int n=pst.executeUpdate();
        if(n>0){
            System.out.println("Product Added Successfully");
        }else{
            System.out.println("Product Not Added Successfully");
        }
    }

    public void updateProduct()throws Exception{
        boolean flag=true;
        while(flag){
            System.out.println("""
                    --------------------------------------------------
                    Press 1 to Update Product Name
                    Press 2 to Update Category
                    Press 3 to Update Status
                    Press 4 to Update Rate
                    Press 5 to Update Quantity
                    Press 6 to Update Supplier
                    Press 7 to Exit
                    --------------------------------------------------
                    Enter Your Choice:""");
            int choice=sc.nextInt();
            switch(choice){
                case 1:{
                    updateProductName();
                    break;
                }
                case 2:{
                    updateProductCategory();
                    break;
                }
                case 3:{
                    updateProductStatus();
                    break;
                }
                case 4:{
                    updateProductRate();
                    break;
                }
                case 5:{
                    updateProductQuantity();
                    break;
                }
                case 6:{
                    updateProductSupplier();
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

    public void deleteProduct()throws Exception{
        try{
            System.out.println("Enter Product Id to Delete Product");
            System.out.println("Enter Product Id: ");
            int product_Id = sc.nextInt();
            con.setAutoCommit(false);

            String query="delete from product where product_id=?";
            PreparedStatement pst = this.con.prepareStatement(query);
            pst.setInt(1, product_Id);
            int n=pst.executeUpdate();
            if(n>0){
                sc.nextLine();
                System.out.println("Are you really want to Delete Product(Ans in Yes/No): ");
                String ans=sc.nextLine();
                if(ans.equals("Yes")){
                    System.out.println("Product Deleted Successfully");
                    con.commit();
                }else{
                    System.out.println("Product Not Deleted Successfully");
                    con.rollback();
                }
            }else{
                con.rollback();
            }
        } catch (Exception e) {
            System.out.println("You can't delete this product");
            con.rollback();
        }
    }

    public void updateProductName()throws Exception{
        Product product=new Product();
        System.out.println("Enter Product Id: ");
        product_Id = sc.nextInt();
        boolean isProduct=isProductById(product_Id);
        if(isProduct){
            product=selectProductById(product_Id);
            System.out.println("Old Product Name: "+product.getProductName());
            sc.nextLine();
            System.out.println("Enter Product Name: ");
            String productName=sc.nextLine();
            String query="Update product set productname=? where product_id=?";
            PreparedStatement pst = this.con.prepareStatement(query);
            pst.setString(1, productName);
            pst.setInt(2, product_Id);
            int n=pst.executeUpdate();
            if(n>0){
                System.out.println("Product Name Updated Successfully");
            }
        }else{
            System.out.println("Invalid Product_Id.Enter Valid Product Id.");
        }
    }

    public void updateProductCategory()throws Exception{
        Product product=new Product();
        System.out.println("Enter Product Id: ");
        product_Id = sc.nextInt();
        boolean isProduct=isProductById(product_Id);
        if(isProduct){
            product=selectProductById(product_Id);
            System.out.println("Old Product Category: "+product.getCategory());
            chooseProductCategory();
            String query="Update product set category=? where product_id=?";
            PreparedStatement pst = this.con.prepareStatement(query);
            pst.setString(1, category);
            pst.setInt(2, product_Id);
            int n=pst.executeUpdate();
            if(n>0){
                System.out.println("Product Category Updated Successfully");
            }
        }else{
            System.out.println("Invalid Product_Id.Enter Valid Product Id.");
        }
    }

    public void updateProductRate()throws Exception{
        Product product=new Product();
        System.out.println("Enter Product Id: ");
        product_Id = sc.nextInt();
        boolean isProduct=isProductById(product_Id);
        if(isProduct){
            product=selectProductById(product_Id);
            System.out.println("Old Product Rate: "+product.getRate());
            System.out.println("Enter Product Rate: ");
            rate = sc.nextDouble();
            String query="Update product set rate=? where product_id=?";
            PreparedStatement pst = this.con.prepareStatement(query);
            pst.setDouble(1, rate);
            pst.setInt(2, product_Id);
            int n=pst.executeUpdate();
            if(n>0){
                System.out.println("Product Rate Updated Successfully");
            }
            else{
                System.out.println("Product Rate not Updated Successfully");
            }
        }else{
            System.out.println("Invalid Product_Id.Enter Valid Product Id.");
        }
    }

    public void updateProductQuantity()throws Exception{
        Product product=new Product();
        System.out.println("Enter Product Id: ");
        product_Id=sc.nextInt();
        boolean isProduct=isProductById(product_Id);
        if(isProduct){
            product=selectProductById(product_Id);
            System.out.println("Old Product Quantity: "+product.getQuantity());
            System.out.println("Enter Product Quantity: ");
            quantity = sc.nextInt();
            String query="Update product set quantity=? where product_id=?";
            PreparedStatement pst = this.con.prepareStatement(query);
            pst.setDouble(1, quantity);
            pst.setInt(2, product_Id);
            int n=pst.executeUpdate();
            if(n>0){
                System.out.println("Product Quantity Updated Successfully");
            }else{
                System.out.println("Product Quantity not Updated Successfully");
            }
        }else{
            System.out.println("Invalid Product_Id.Enter Valid Product Id.");
        }
    }

    public void updateProductStatus()throws Exception{
        Product product=new Product();
        System.out.println("Enter Product Id: ");
        product_Id=sc.nextInt();
        boolean isProduct=isProductById(product_Id);
        if(isProduct){
            product=selectProductById(product_Id);
            System.out.println("Old Product Status: "+product.getStatus());
            sc.nextLine();
            System.out.println("Enter Product Status: ");
            status = sc.nextLine();
            String query="Update product set status=? where product_id=?";
            PreparedStatement pst = this.con.prepareStatement(query);
            pst.setString(1, status);
            pst.setInt(2, product_Id);
            int n=pst.executeUpdate();
            if(n>0){
                System.out.println("Product Status Updated Successfully");
            }else{
                System.out.println("Product Status not Updated Successfully");
            }
        }else{
            System.out.println("Invalid Product_Id.Enter Valid Product Id.");
        }
    }

    public void updateProductSupplier()throws Exception{
        Product product=new Product();
        System.out.println("Enter Product Id: ");
        product_Id=sc.nextInt();
        boolean isProduct=isProductById(product_Id);
        if(isProduct){
            product=selectProductById(product_Id);
            System.out.println("Old Product Supplier_Id: "+product.getSupplier_id());
            System.out.println("Enter Product Supplier Id: ");
            while(true){
                supplier_id=sc.nextInt();
                boolean flag=new Supplier().checkSupplierId(supplier_id);
                if(flag){
                    break;
                }else{
                    System.out.println("Invalid Supplier_Id.Enter Valid Supplier Id.");
                }
            }
            String query="Update product set supplier_id=? where product_id=?";
            PreparedStatement pst = this.con.prepareStatement(query);
            pst.setInt(1, supplier_id);
            pst.setInt(2, product_Id);
            int n=pst.executeUpdate();
            if(n>0){
                System.out.println("Product Supplier Updated Successfully");
            }
            else{
                System.out.println("Product Supplier not Updated Successfully");
            }
        }else{
            System.out.println("Invalid Product_Id.Enter Valid Product Id.");
        }
    }

    public String chooseProductCategory(){
        boolean flag = true;
        while (flag) {
            System.out.println("Select Product Category:");
            System.out.println("1. Electronics");
            System.out.println("2. Clothing");
            System.out.println("3. HomeAppliances");
            System.out.println("4. Books");
            System.out.println("5. Furniture");
            System.out.println("6. Toys");
            System.out.println("7. Sports");
            System.out.println("8. Health");
            System.out.println("9. Automotive");
            System.out.println("10. Groceries");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    category = "Electronics";
                    flag = false;
                    break;
                case 2:
                    category = "Clothing";
                    flag = false;
                    break;
                case 3:
                    category = "HomeAppliances";
                    flag = false;
                    break;
                case 4:
                    category = "Books";
                    flag = false;
                    break;
                case 5:
                    category = "Furniture";
                    flag = false;
                    break;
                case 6:
                    category = "Toys";
                    flag = false;
                    break;
                case 7:
                    category = "Sports";
                    flag = false;
                    break;
                case 8:
                    category = "Health";
                    flag = false;
                    break;
                case 9:
                    category = "Automotive";
                    flag = false;
                    break;
                case 10:
                    category = "Groceries";
                    flag = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }
        return category;
    }
}
