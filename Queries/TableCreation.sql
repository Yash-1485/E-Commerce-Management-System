--Queries to Create Tables
--Customer Table Create Query
CREATE TABLE Customer (
      cust_id INT AUTO_INCREMENT PRIMARY KEY,
      emailid VARCHAR(100) NOT NULL,
      password VARCHAR(10) NOT NULL,
      fullname VARCHAR(100) NOT NULL,
      mobileno VARCHAR(20) NOT NULL,
      address TEXT
);

--Admin Table Create Query
CREATE TABLE Admin (
       admin_id INT AUTO_INCREMENT PRIMARY KEY,
       emailid VARCHAR(100) NOT NULL,
       password VARCHAR(10) NOT NULL,
       fullname VARCHAR(100) NOT NULL,
       mobileno VARCHAR(20) NOT NULL
);

--Supplier Table Create Query
CREATE TABLE Supplier (
      supplier_id INT AUTO_INCREMENT PRIMARY KEY,
      emailid VARCHAR(100) NOT NULL,
      password VARCHAR(10) NOT NULL,
      companyname VARCHAR(100) NOT NULL,
      companyaddress TEXT NOT NULL,
      fullname VARCHAR(100) NOT NULL,
      mobileno VARCHAR(20) NOT NULL
);

--Product Table Create Query
CREATE TABLE Product (
     product_id INT AUTO_INCREMENT PRIMARY KEY,
     productname VARCHAR(100) NOT NULL,
     category VARCHAR(100) NOT NULL,
     status VARCHAR(50) NOT NULL,
     rate DOUBLE NOT NULL,
     quantity INT NOT NULL,
     supplier_id INT NOT NULL,
     FOREIGN KEY (supplier_id) REFERENCES Supplier(supplier_id)
);

--Order Table Create Query
CREATE TABLE Orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    cust_id INT NOT NULL,
    amount DOUBLE NOT NULL,
    order_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cust_id) REFERENCES Customer(cust_id)
);

--Transaction Table Create Query
CREATE TABLE Product (
     product_id INT AUTO_INCREMENT PRIMARY KEY,
     productname VARCHAR(255) NOT NULL,
     category ENUM('Electronics', 'Clothing', 'HomeAppliances','Books', 'Furniture', 'Toys', 'Sports', 'Health', 'Automotive','Groceries') NOT NULL,
     status ENUM('Available', 'Out of Stock') NOT NULL,
     rate DOUBLE NOT NULL,
     quantity INT NOT NULL,
     supplier_id INT NOT NULL,
     FOREIGN KEY (supplier_id) REFERENCES Supplier(supplier_id)
);

--Purchased_Product Table Create Query
CREATE TABLE Purchased_Product (
       order_id INT NOT NULL,
       cust_id INT NOT NULL,
       product_id INT NOT NULL,
       transaction_id INT NOT NULL,
       rate DOUBLE NOT NULL,
       quantity INT NOT NULL,
       amount DOUBLE NOT NULL,
       timeofpurchasing TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       FOREIGN KEY (order_id) REFERENCES Orders(order_id),
       FOREIGN KEY (cust_id) REFERENCES Customer(cust_id),
       FOREIGN KEY (product_id) REFERENCES Product(product_id),
       FOREIGN KEY (transaction_id) REFERENCES Transaction(transaction_id)
);

--Return_Order Create Query
CREATE TABLE ReturnOrders (
      return_order_id INT AUTO_INCREMENT PRIMARY KEY,
      cust_id INT NOT NULL,
      order_id INT NOT NULL,
      amount DOUBLE NOT NULL,
      return_order_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      FOREIGN KEY (cust_id) REFERENCES Customer(cust_id),
      FOREIGN KEY (order_id) REFERENCES Orders(order_id)
);

--Return_Transaction Table Create Query
CREATE TABLE ReturnTransaction (
       return_transaction_id INT AUTO_INCREMENT PRIMARY KEY,
       return_order_id INT NOT NULL,
       amount DOUBLE NOT NULL,
       return_trsn_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       FOREIGN KEY (return_order_id) REFERENCES ReturnOrders(return_order_id)
);

--Returned_Product Table Create Query
CREATE TABLE Returned_Product (
      return_order_id INT NOT NULL,
      order_id INT NOT NULL,
      cust_id INT NOT NULL,
      product_id INT NOT NULL,
      return_transaction_id INT NOT NULL,
      rate DOUBLE NOT NULL,
      quantity INT NOT NULL,
      amount DOUBLE NOT NULL,
      timeOfPurchasing TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      timeOfReturning TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      timeGap BIGINT NOT NULL,
      FOREIGN KEY (return_order_id) REFERENCES ReturnOrders(return_order_id),
      FOREIGN KEY (order_id) REFERENCES Orders(order_id),
      FOREIGN KEY (cust_id) REFERENCES Customer(cust_id),
      FOREIGN KEY (product_id) REFERENCES Product(product_id),
      FOREIGN KEY (return_transaction_id) REFERENCES ReturnTransaction(return_transaction_id)
);

--log_customer Table Create Query
CREATE TABLE log_customer(
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    cust_id INT NOT NULL,
    Action VARCHAR(50) NOT NULL,
    Act_Time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cust_id) REFERENCES Customer(cust_id);
);

--log_admin Table Create Query
CREATE TABLE log_admin(
     log_id INT AUTO_INCREMENT PRIMARY KEY,
     admin_id INT NOT NULL,
     Action VARCHAR(50) NOT NULL,
     Act_Time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     FOREIGN KEY (admin_id) REFERENCES Admin(admin_id);
);

--log_supplier Table Create Query
CREATE TABLE log_supplier(
     log_id INT AUTO_INCREMENT PRIMARY KEY,
     supplier_id INT NOT NULL,
     Action VARCHAR(50) NOT NULL,
     Act_Time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     FOREIGN KEY (supplier_id) REFERENCES Supplier(supplier_id);
);

--log_product Table Create Query
CREATE TABLE log_customer(
     log_id INT AUTO_INCREMENT PRIMARY KEY,
     cust_id INT NOT NULL,
     Action VARCHAR(50) NOT NULL,
     Act_Time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     FOREIGN KEY (cust_id) REFERENCES Customer(cust_id);
);