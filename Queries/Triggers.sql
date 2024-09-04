--Triggers

--Triggers on Customer Table
--When insertion occurs on Customer Table
DELIMITER //
CREATE TRIGGER insertCustomerLog AFTER INSERT ON Customer FOR EACH ROW
BEGIN
    INSERT INTO log_customer (cust_id, ACTION, Act_time) VALUES (NEW.cust_id, 'Inserted', Now());
END //
DELIMITER ;

--When update occurs on Customer Table
DELIMITER //
CREATE TRIGGER updateCustomerLog AFTER UPDATE ON Customer FOR EACH ROW
BEGIN
    INSERT INTO log_customer (cust_id, ACTION, Act_time) VALUES (OLD.cust_id, 'Updated', Now());
END //
DELIMITER ;

--Triggers on Admin Table
--When insertion occurs on Admin Table
DELIMITER //
CREATE TRIGGER insertAdminLog AFTER INSERT ON Admin FOR EACH ROW
BEGIN
    INSERT INTO log_admin (admin_id, ACTION, Act_time) VALUES (NEW.admin_id, 'Inserted', Now());
END //
DELIMITER ;

--When update occurs on Admin Table
DELIMITER //
CREATE TRIGGER updateAdminLog AFTER UPDATE ON Admin FOR EACH ROW
BEGIN
    INSERT INTO log_admin (admin_id, ACTION, Act_time) VALUES (OLD.admin_id, 'Updated', Now());
END //
DELIMITER ;

--Triggers on Supplier Table
--When insertion occurs on Supplier Table
DELIMITER //
CREATE TRIGGER insertSupplierLog AFTER INSERT ON Supplier FOR EACH ROW
BEGIN
    INSERT INTO log_supplier (supplier_id, ACTION, Act_time) VALUES (NEW.supplier_id, 'Inserted', Now());
END //
DELIMITER ;

--When update occurs on Supplier Table
DELIMITER //
CREATE TRIGGER updateSupplierLog AFTER UPDATE ON Supplier FOR EACH ROW
BEGIN
    INSERT INTO log_supplier (supplier_id, ACTION, Act_time) VALUES (OLD.supplier_id, 'Updated', Now());
END //
DELIMITER ;

--Triggers on Product Table
--When insertion occurs on Product Table
DELIMITER //
CREATE TRIGGER insertProductLog AFTER INSERT ON Product FOR EACH ROW
BEGIN
    INSERT INTO log_Product (product_id, ACTION, Act_time) VALUES (NEW.product_id, 'Inserted', Now());
END //
DELIMITER ;

--When update occurs on Product Table
DELIMITER //
CREATE TRIGGER updateProductLog AFTER UPDATE ON Product FOR EACH ROW
BEGIN
    INSERT INTO log_product (product_id, ACTION, Act_time) VALUES (OLD.product_id, 'Updated', Now());
END //
DELIMITER ;

--When product goes to out of stock condition
DELIMITER //
CREATE TRIGGER updateProductStatus BEFORE UPDATE ON Product FOR EACH ROW
BEGIN
IF NEW.quantity = 0 THEN
    SET NEW.status = 'Out of Stock';
ELSEIF NEW.quantity > 0 THEN
    SET NEW.status = 'Available';
END IF;
END //
DELIMITER ;