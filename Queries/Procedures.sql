--Routines-Procedures to Update Supplier Table
--Procedure to Update Supplier Name
DELIMITER //
CREATE PROCEDURE updateSupplierName(IN supId INT, IN supName VARCHAR(50))
BEGIN
UPDATE Supplier SET companyname = supName WHERE supplier_id = supId;
END //
DELIMITER ;

--Procedure to Update Supplier Password
DELIMITER //
CREATE PROCEDURE updateSupplierPassword(IN supId INT, IN supPwd VARCHAR(20))
BEGIN
UPDATE Supplier SET password = supPwd WHERE supplier_id = supId;
END //
DELIMITER ;

--Procedure to Update Supplier EmailId
DELIMITER //
CREATE PROCEDURE updateSupplierEmailId(IN supId INT, IN supEID VARCHAR(100))
BEGIN
UPDATE Supplier SET emailid = supEID WHERE supplier_id = supId;
END //
DELIMITER ;

--Procedure to Update Supplier Password
DELIMITER //
CREATE PROCEDURE updateSupplierMobileNo(IN supId INT, IN supMB VARCHAR(100))
BEGIN
UPDATE Supplier SET mobileno=supMB WHERE supplier_id = supId;
END //
DELIMITER ;

--Procedure to Update Supplier Company Name
DELIMITER //
CREATE PROCEDURE updateSupplierCompanyName(IN supId INT, IN supCN VARCHAR(100))
BEGIN
UPDATE Supplier SET companyname = supCN WHERE supplier_id = supId;
END //
DELIMITER ;

--Procedure to Update Supplier Company Address
DELIMITER //
CREATE PROCEDURE updateSupplierCompanyAddress(IN supId INT, IN supCA VARCHAR(100))
BEGIN
UPDATE Supplier SET companyaddress=supCA WHERE supplier_id = supId;
END //
DELIMITER ;