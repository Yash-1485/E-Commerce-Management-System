import java.util.*;

public class EShop {
    static Scanner sc = new Scanner(System.in);
    static String userType;

    public EShop() throws Exception {
        try {
            while (true) {
                System.out.println("""
                        ------------------------------------------------------------------------------------------------
                        ==================================Welcome to The E - Shop=======================================
                        ------------------------------------------------------------------------------------------------
                        |                                         Homepage                                             |
                        ------------------------------------------------------------------------------------------------
                        |                                  Press 1 For Login                                           |
                        |                                  Press 2 For Registration                                    |
                        |                                  Press 3 For Exit                                            |
                        ------------------------------------------------------------------------------------------------
                        |                                  Enter from Above Options                                    |
                        ------------------------------------------------------------------------------------------------""");
                if(sc.hasNextInt()){
                    int choice = sc.nextInt();
                    switch (choice) {
                        case 1: {
                            userType = selectUserType();
                            // System.out.println(userType);
                            if (userType.equals("Customer")) {
                                Customer customer = new Customer();
                                boolean isCustomer = customer.isCustomer();
                                if (isCustomer) {
                                    customer=customer.getCustomer(customer.getEmailId(), customer.getPwd());
                                    System.out.println("Login Successfully as " + userType);
                                    customer.handleCustomerOperations();
                                }
                            } else if (userType.equals("Admin")) {
                                Admin admin=new Admin();
                                boolean isAdmin = admin.isAdmin();
                                if (isAdmin) {
                                    admin=admin.getAdmin(admin.getEmailId(), admin.getPwd());
                                    System.out.println("Login Successfully as " + userType);
                                    admin.handleAdminOperations();
                                }
                            } else {
                                Supplier supplier=new Supplier();
                                boolean isSupplier = supplier.isSupplier();
                                if (isSupplier) {
                                    supplier=supplier.getSupplier(supplier.getemailId(),supplier.getPwd());
                                    System.out.println("Login Successfully as " + userType);
                                    supplier.handleSupplierOperations();
                                }
                            }
                            break;
                        }
                        case 2: {
                            userType = selectUserType();
                            if (userType.equals("Customer")) {
                                new Customer().registerCustomer();
                            } else if (userType.equals("Admin")) {
                                System.out.println("Only previous Admin can register for New Admin");
                                Admin admin = new Admin();
                                boolean isAdmin = admin.validateAdmin();
                                if (isAdmin) {
                                    System.out.println("Admin Validated");
                                    System.out.println("Enter Details to register a New Admin");
                                    admin.registerAdmin();
                                } else {
                                    throw new Exception("Failed to verify Admin\nPlease Try Again");
                                }
                            } else {
                                System.out.println("Only previous Admin can register for New Supplier");
                                Admin admin = new Admin();
                                boolean isAdmin = admin.validateAdmin();
                                if (isAdmin) {
                                    System.out.println("Admin Validated");
                                    System.out.println("Enter Details to register a New Supplier");
                                    new Supplier().registerSupplier();
                                } else {
                                    throw new Exception("Failed to verify Admin\nPlease Try Again");
                                }
                            }
                            break;
                        }
                        case 3: {
                            System.out.println("Thank You for Visit");
                            System.exit(0);
                        }
                        default:
                            break;
                    }
                }
                else{
                    System.out.println("Please enter your choice in number input only");
                    sc.next();
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Please enter your inputs in numbers only");
            new EShop();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            new EShop();
        }
    }

    public static String selectUserType() {
        boolean isNotSelected = true;
        while (isNotSelected) {
            System.out.println("---------------------------------");
            System.out.println("Who is operating the Application?");
            System.out.println("---------------------------------");
            System.out.println("Press 1 for Customer");
            System.out.println("Press 2 for Admin");
            System.out.println("Press 3 for Supplier");
            System.out.println("---------------------------------");
            System.out.println("Enter from above options only");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    userType = "Customer";
                    isNotSelected = false;
                    break;
                case 2:
                    userType = "Admin";
                    isNotSelected = false;
                    break;
                case 3:
                    userType = "Supplier";
                    isNotSelected = false;
                    break;
                default:
                    System.out.println("Enter Valid Option");
            }
        }
        return userType;
    }
}
