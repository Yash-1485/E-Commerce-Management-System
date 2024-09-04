import java.sql.*;
import java.util.*;

public class Supplier {
    static Scanner sc = new Scanner(System.in);
    int supplier_id;
    String emailId;
    String pwd;
    String fullName;
    String mobileNo;
    String companyName;
    String companyAddress;
    Connection con;

    public Supplier() throws Exception {
        con = DBConnection.getConnection();
    }

    public Supplier(int supplier_id, String emailId, String pwd, String fullName, String mobileNo, String companyName,
            String companyAddress) {
        this.supplier_id = supplier_id;
        this.emailId = emailId;
        this.pwd = pwd;
        this.fullName = fullName;
        this.mobileNo = mobileNo;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
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

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public String getemailId() {
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

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void registerSupplier() throws Exception {
        validateemailId();
        validatepwd();
        validateMobileNo();
        System.out.println("Enter Full Name: ");
        fullName = sc.nextLine();
        System.out.println("Enter Company Name: ");
        companyName = sc.nextLine();
        System.out.println("Enter Company Address: ");
        companyAddress = sc.nextLine();
        String query = "insert into supplier(emailid,password,mobileno,fullname,companyname,companyaddress) values(?,?,?,?,?,?);";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, emailId);
        pst.setString(2, pwd);
        pst.setString(3, mobileNo);
        pst.setString(4, fullName);
        pst.setString(5, companyName);
        pst.setString(6, companyAddress);
        int n = pst.executeUpdate();
        if (n > 0) {
            System.out.println("Registration Successfully");
        } else {
            throw new Exception("Registration Failed");
        }
    }

    public boolean isSupplier() throws Exception {
        // to verify supplier
        System.out.println("Enter E-Mail Id: ");
        emailId = sc.nextLine();
        System.out.println("Enter Password: ");
        pwd = sc.nextLine();

        String query = "select * from supplier where emailid=? and password=?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, emailId);
        pst.setString(2, pwd);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return true;
        } else {
            throw new Exception(
                    "Not a valid supplier\nIf you didn't registered, then register yourself first by Admin Person");
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

    public boolean checkSupplierId(int supplier_id)throws Exception{
        boolean flag = false;
        String query="Select supplier_id from Supplier;";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            if(rs.getInt("supplier_id")==supplier_id){
                flag=true;
            }
        }
        return flag;
    }

    public Supplier getSupplier(String emailId,String pwd)throws Exception{
        String query="Select * from supplier where emailid=? and password=?;";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, emailId);
        pst.setString(2, pwd);
        ResultSet rs = pst.executeQuery();
        if(rs.next()) {
            return new Supplier(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7));
        }else{
            return null;
        }
    }

    public void handleSupplierOperations()throws Exception{
        this.con=DBConnection.getConnection();
        boolean flag = true;
        while (flag) {
            System.out.println("===============================================");
            System.out.println("|              MAIN MENU                     |");
            System.out.println("===============================================");
            System.out.println("| 1. View Profile                            |");
            System.out.println("| 2. Update Profile                          |");
            System.out.println("| 3. View Supplied Products                  |");
            System.out.println("| 4. Log Out                                 |");
            System.out.println("===============================================");
            System.out.println("| Please enter your choice:                  |");
            System.out.println("===============================================");
            int choice=sc.nextInt();
            switch(choice) {
                case 1:{
                    supplierProfileDetails();
                    break;
                }
                case 2:{
                    updateProfile();
                    break;
                }
                case 3:{
                    viewSuppliedProducts();
                    break;
                }
                case 4:{
                    flag = false;
                    break;
                }
                default:{
                    System.out.println("Enter Valid Option");
                }
            }
        }
    }

    public void updateProfile()throws Exception{
        boolean flag = true;
        while (flag) {
            System.out.println("===============================================");
            System.out.println("|               UPDATE MENU                   |");
            System.out.println("===============================================");
            System.out.println("| 1. Update Name                              |");
            System.out.println("| 2. Update Email                             |");
            System.out.println("| 3. Update Password                          |");
            System.out.println("| 4. Update Mobile Number                     |");
            System.out.println("| 5. Update Company Name                      |");
            System.out.println("| 6. Update Company Address                   |");
            System.out.println("| 7. Exit                                     |");
            System.out.println("===============================================");
            System.out.println("| Please enter your choice:                   |");
            System.out.println("===============================================");
            int choice=sc.nextInt();
            switch(choice) {
                case 1:{
                    String query="{call updateSupplierName(?,?)}";
                    CallableStatement cst=con.prepareCall(query);
                    cst.setInt(1, supplier_id);
                    sc.nextLine();
                    System.out.println("Enter New Name:  ");
                    fullName=sc.nextLine();
                    cst.setString(2, fullName);
                    int n=cst.executeUpdate();
                    System.out.println((n>0)?"Supplier Name Updated":"Supplier Name Not Updated");
                    break;
                }
                case 2 :{
                    String query="{call updateSupplierEmailId(?,?)}";
                    CallableStatement cst=con.prepareCall(query);
                    sc.nextLine();
                    System.out.println("Enter New EmailId");
                    validateemailId();
                    cst.setInt(1,supplier_id);
                    cst.setString(2, emailId);
                    int n=cst.executeUpdate();
                    System.out.println((n>0)?"Supplier EmailId Updated":"Supplier EmailId Not Updated");
                    break;
                }
                case 3 :{
                    String query="{call updateSupplierPassword(?,?)}";
                    CallableStatement cst=con.prepareCall(query);
                    sc.nextLine();
                    System.out.println("Enter New Password");
                    validatepwd();
                    cst.setInt(1,supplier_id);
                    cst.setString(2, pwd);
                    int n=cst.executeUpdate();
                    System.out.println((n>0)?"Supplier Password Updated":"Supplier Password Not Updated");
                    break;
                }
                case 4:{
                    String query="{call updateSupplierMobileNo(?,?)}";
                    CallableStatement cst=con.prepareCall(query);
                    sc.nextLine();
                    System.out.println("Enter New Mobile No");
                    validateMobileNo();
                    cst.setInt(1,supplier_id);
                    cst.setString(2, mobileNo);
                    int n=cst.executeUpdate();
                    System.out.println((n>0)?"Supplier Mobile No Updated":"Supplier Mobile No Not Updated");
                    break;
                }
                case 5:{
                    String query="{call updateSupplierCompanyName(?,?)}";
                    CallableStatement cst=con.prepareCall(query);
                    sc.nextLine();
                    System.out.println("Enter New Company Name: ");
                    companyName=sc.nextLine();
                    cst.setInt(1,supplier_id);
                    cst.setString(2, companyName);
                    int n=cst.executeUpdate();
                    System.out.println((n>0)?"Supplier Company Name Updated":"Supplier Company Name Not Updated");
                    break;
                }
                case 6:{
                    String query="{call updateSupplierCompanyAddress(?,?)}";
                    CallableStatement cst=con.prepareCall(query);
                    sc.nextLine();
                    System.out.println("Enter New Company Address: ");
                    companyAddress=sc.nextLine();
                    cst.setInt(1,supplier_id);
                    cst.setString(2, companyAddress);
                    int n=cst.executeUpdate();
                    System.out.println((n>0)?"Supplier Company Address Updated":"Supplier Company Address Not Updated");
                    break;
                }
                case 7:{
                    flag = false;
                    break;
                }
                default:{
                    System.out.println("Enter Valid Option");
                    break;
                }
            }
        }
    }

    public void supplierProfileDetails()throws Exception{
        System.out.println("----------------------------------------------------");
        System.out.println("Profile: ");
        System.out.println("----------------------------------------------------");
        String query1="Create View if not exists SupplierProfile"+getSupplier_id()+" as select supplier_id,fullname,emailid,mobileno,companyname,companyaddress from supplier where supplier_id="+getSupplier_id()+";";
        String query2="Select * from SupplierProfile"+getSupplier_id();
        Statement st=con.createStatement();
        st.executeUpdate(query1);
        ResultSet rs=st.executeQuery(query2);
        if(rs.next()) {
            System.out.println("Supplier_Id: "+rs.getInt(1));
            System.out.println("Name: "+rs.getString(2));
            System.out.println("EmailId: "+rs.getString(3));
            mobileNo=rs.getString(4);
            System.out.println("MobileNo: "+getEncrytedMobileNo());
            System.out.println("Company Name: "+rs.getString(5));
            System.out.println("Company Address: "+rs.getString(6));
        }else{
            System.out.println("View Profile Operation Failed");
        }
        System.out.println("----------------------------------------------------");
    }

    public String getEncrytedMobileNo() {
        String encmb=getMobileNo().substring(0,6).concat("****");
        return encmb;
    }

    public void viewSuppliedProducts()throws Exception{
        String query="Select p.product_Id,p.productName,p.category,p.rate from Product p Inner Join Supplier s on p.supplier_id=s.supplier_id where p.supplier_id="+getSupplier_id()+";";
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(query);
        System.out.println("============================================================================================");
        System.out.println("                                  Supplied Products                ");
        System.out.println("============================================================================================");
        System.out.printf("%-15s %-25s %-20s %-15s%n", "Product_Id", "Product Name", "Category", "Rate");
        System.out.println("--------------------------------------------------------------------------------------------");
        while (rs.next()) {
            System.out.printf("%-15d %-25s %-20s %-15.2f%n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4));
        }
        System.out.println("============================================================================================");
    }
}
