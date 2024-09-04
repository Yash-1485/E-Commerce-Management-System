-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 24, 2024 at 05:33 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `e-commercems`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateSupplierCompanyAddress` (IN `supId` INT, IN `supCA` VARCHAR(100))   BEGIN
Update supplier SET companyAddress=supCA WHERE supplier_id=supId;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateSupplierCompanyName` (IN `supId` INT, IN `supCN` VARCHAR(100))   BEGIN
Update supplier SET companyname=supCN where supplier_id=supId;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateSupplierEmailId` (IN `supId` INT, IN `supEId` VARCHAR(100))   BEGIN
Update supplier set emailid=supEId where supplier_id=supId;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateSupplierMobileNo` (IN `supId` INT, IN `supMB` VARCHAR(100))   BEGIN
Update supplier SET mobileno=supMB WHERE supplier_id=supId;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateSupplierName` (IN `supId` INT, IN `supName` VARCHAR(100))   BEGIN
Update supplier set fullname=supName where supplier_id=supId;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateSupplierPassword` (IN `supId` INT, IN `supPwd` VARCHAR(20))   BEGIN
Update supplier SET password=supPwd WHERE supplier_id=supId;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL,
  `EmailId` varchar(100) NOT NULL,
  `Password` varchar(10) NOT NULL,
  `FullName` varchar(100) NOT NULL,
  `MobileNo` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`admin_id`, `EmailId`, `Password`, `FullName`, `MobileNo`) VALUES
(1, 'yashparekh@gmail.com', 'yash@12345', 'Yash Parekh', '8160924672'),
(2, 'arunparekh@gmail.com', 'arun@12345', 'Arun Parekh', '9825481780'),
(3, 'poojaparekh@gmail.com', 'pooj@12345', 'Pooja', '7456982130');

--
-- Triggers `admin`
--
DELIMITER $$
CREATE TRIGGER `insertAdminLog` AFTER INSERT ON `admin` FOR EACH ROW Insert into log_admin(admin_id,ACTION,act_time) values(NEW.admin_id,"Inserted",Now())
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `updateAdminLog` AFTER UPDATE ON `admin` FOR EACH ROW Insert into log_admin(admin_id,ACTION,act_time) values(OLD.admin_id,"Updated",Now())
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `adminprofile1`
-- (See below for the actual view)
--
CREATE TABLE `adminprofile1` (
`admin_id` int(11)
,`emailid` varchar(100)
,`fullname` varchar(100)
,`mobileno` varchar(20)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `adminprofile3`
-- (See below for the actual view)
--
CREATE TABLE `adminprofile3` (
`admin_id` int(11)
,`emailid` varchar(100)
,`fullname` varchar(100)
,`mobileno` varchar(20)
);

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `cust_id` int(11) NOT NULL,
  `Emailid` varchar(100) NOT NULL,
  `Password` varchar(10) NOT NULL,
  `FullName` varchar(100) NOT NULL,
  `MobileNo` varchar(20) NOT NULL,
  `Address` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`cust_id`, `Emailid`, `Password`, `FullName`, `MobileNo`, `Address`) VALUES
(1, 'yashparekh@gmail.com', 'yash@12345', 'Yash Parekh', '8160924672', 'Bopal'),
(2, 'janedoe@gmail.com', 'janedoe@12', 'Jane Doe', '7531598254', 'Sarkhej'),
(3, 'johndoe@gmail.com', 'johndoe@12', 'John Doe', '7546982137', 'Bopal'),
(4, 'aaravgaming@hotmail.com', '@arav12345', 'Aarav Patel', '7412356890', 'New Mumbai'),
(5, 'priyasharma@gmail.com', 'priy@12345', 'Priya Sharma', '7415963280', 'New Delhi'),
(6, 'rajeshverma@outlook.com', 'raje$h1234', 'Rajesh Verma', '9638527410', 'Kolkata'),
(25, 'yash@gmail.com', 'yashpare1@', 'Yash Parekh', '1234567890', 'Bopal'),
(27, 'hitarth@gmail.com', 'hitarth@12', 'Hitarth Doshi', '7895153210', 'Navrangpura');

--
-- Triggers `customer`
--
DELIMITER $$
CREATE TRIGGER `insertCustomerLog` AFTER INSERT ON `customer` FOR EACH ROW Insert into log_customer(cust_id,ACTION,act_time) values(NEW.cust_id,"Inserted",Now())
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `updateCustomerLog` AFTER UPDATE ON `customer` FOR EACH ROW Insert into log_customer(cust_id,ACTION,act_time) values(OLD.cust_id,"Updated",Now())
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `customerprofile1`
-- (See below for the actual view)
--
CREATE TABLE `customerprofile1` (
`cust_id` int(11)
,`emailid` varchar(100)
,`fullname` varchar(100)
,`address` text
,`mobileno` varchar(20)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `customerprofile2`
-- (See below for the actual view)
--
CREATE TABLE `customerprofile2` (
`cust_id` int(11)
,`emailid` varchar(100)
,`fullname` varchar(100)
,`address` text
,`mobileno` varchar(20)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `customerprofile5`
-- (See below for the actual view)
--
CREATE TABLE `customerprofile5` (
`cust_id` int(11)
,`emailid` varchar(100)
,`fullname` varchar(100)
,`address` text
,`mobileno` varchar(20)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `customerprofile27`
-- (See below for the actual view)
--
CREATE TABLE `customerprofile27` (
`cust_id` int(11)
,`emailid` varchar(100)
,`fullname` varchar(100)
,`address` text
,`mobileno` varchar(20)
);

-- --------------------------------------------------------

--
-- Table structure for table `log_admin`
--

CREATE TABLE `log_admin` (
  `log_id` int(11) NOT NULL,
  `admin_id` int(11) NOT NULL,
  `Action` varchar(50) NOT NULL,
  `Act_Time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `log_admin`
--

INSERT INTO `log_admin` (`log_id`, `admin_id`, `Action`, `Act_Time`) VALUES
(1, 2, 'Inserted', '2024-08-19 08:26:45'),
(2, 3, 'Inserted', '2024-08-23 17:27:30'),
(3, 3, 'Updated', '2024-08-23 17:28:43'),
(4, 3, 'Updated', '2024-08-23 17:29:25');

-- --------------------------------------------------------

--
-- Table structure for table `log_customer`
--

CREATE TABLE `log_customer` (
  `log_id` int(11) NOT NULL,
  `cust_id` int(11) NOT NULL,
  `Action` varchar(50) NOT NULL,
  `Act_Time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `log_customer`
--

INSERT INTO `log_customer` (`log_id`, `cust_id`, `Action`, `Act_Time`) VALUES
(29, 2, 'Inserted', '2024-08-19 07:13:56'),
(30, 3, 'Inserted', '2024-08-19 07:13:56'),
(31, 4, 'Inserted', '2024-08-19 07:13:56'),
(32, 5, 'Inserted', '2024-08-19 07:13:56'),
(33, 6, 'Inserted', '2024-08-19 07:13:56'),
(34, 25, 'Inserted', '2024-08-21 04:01:43'),
(35, 27, 'Inserted', '2024-08-23 16:28:54');

-- --------------------------------------------------------

--
-- Table structure for table `log_product`
--

CREATE TABLE `log_product` (
  `log_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `Action` varchar(50) NOT NULL,
  `Act_Time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `log_product`
--

INSERT INTO `log_product` (`log_id`, `product_id`, `Action`, `Act_Time`) VALUES
(1, 1, 'Updated', '2024-08-19 08:14:19'),
(2, 3, 'Inserted', '2024-08-19 08:18:32'),
(3, 4, 'Inserted', '2024-08-19 08:18:32'),
(4, 5, 'Inserted', '2024-08-19 08:18:32'),
(5, 6, 'Inserted', '2024-08-19 08:25:11'),
(6, 7, 'Inserted', '2024-08-19 08:25:11'),
(7, 8, 'Inserted', '2024-08-19 08:25:11'),
(8, 9, 'Inserted', '2024-08-19 08:25:11'),
(9, 10, 'Inserted', '2024-08-19 08:25:11'),
(10, 8, 'Updated', '2024-08-19 08:25:53'),
(11, 5, 'Updated', '2024-08-19 08:36:22'),
(12, 1, 'Updated', '2024-08-19 08:36:22'),
(13, 7, 'Updated', '2024-08-19 08:42:16'),
(14, 8, 'Updated', '2024-08-19 08:42:16'),
(15, 7, 'Updated', '2024-08-19 08:44:25'),
(16, 8, 'Updated', '2024-08-19 08:44:36'),
(17, 7, 'Updated', '2024-08-19 08:47:07'),
(18, 8, 'Updated', '2024-08-19 08:47:07'),
(19, 7, 'Updated', '2024-08-19 08:55:25'),
(20, 8, 'Updated', '2024-08-19 08:55:25'),
(21, 8, 'Updated', '2024-08-19 08:55:25'),
(22, 9, 'Updated', '2024-08-19 08:55:25'),
(23, 2, 'Updated', '2024-08-19 11:11:33'),
(24, 3, 'Updated', '2024-08-19 11:11:33'),
(25, 6, 'Updated', '2024-08-19 11:11:33'),
(26, 6, 'Updated', '2024-08-19 11:13:39'),
(27, 3, 'Updated', '2024-08-19 11:40:27'),
(28, 5, 'Updated', '2024-08-19 11:40:27'),
(29, 6, 'Updated', '2024-08-19 11:40:27'),
(30, 8, 'Updated', '2024-08-19 11:41:23'),
(31, 9, 'Updated', '2024-08-19 11:41:58'),
(32, 1, 'Updated', '2024-08-19 13:20:08'),
(33, 2, 'Updated', '2024-08-19 13:20:08'),
(34, 3, 'Updated', '2024-08-19 13:20:08'),
(35, 4, 'Updated', '2024-08-19 13:20:08'),
(36, 5, 'Updated', '2024-08-19 13:20:08'),
(37, 1, 'Updated', '2024-08-19 13:50:27'),
(38, 2, 'Updated', '2024-08-19 13:50:27'),
(39, 3, 'Updated', '2024-08-19 13:50:27'),
(40, 4, 'Updated', '2024-08-19 13:50:27'),
(41, 5, 'Updated', '2024-08-19 13:50:27'),
(42, 1, 'Updated', '2024-08-19 14:25:16'),
(43, 2, 'Updated', '2024-08-19 14:25:16'),
(44, 3, 'Updated', '2024-08-19 14:25:16'),
(45, 4, 'Updated', '2024-08-19 14:25:16'),
(46, 5, 'Updated', '2024-08-19 14:25:16'),
(47, 1, 'Updated', '2024-08-20 06:05:44'),
(48, 2, 'Updated', '2024-08-20 06:05:44'),
(49, 3, 'Updated', '2024-08-20 06:05:44'),
(50, 4, 'Updated', '2024-08-20 06:05:44'),
(51, 1, 'Updated', '2024-08-20 06:20:29'),
(52, 7, 'Updated', '2024-08-20 06:20:29'),
(53, 1, 'Updated', '2024-08-20 06:24:09'),
(57, 9, 'Updated', '2024-08-20 11:00:09'),
(58, 3, 'Updated', '2024-08-20 11:04:25'),
(59, 4, 'Updated', '2024-08-20 11:04:25'),
(61, 9, 'Updated', '2024-08-20 11:35:37'),
(62, 3, 'Updated', '2024-08-23 16:40:29'),
(63, 3, 'Updated', '2024-08-23 16:42:21'),
(64, 5, 'Updated', '2024-08-23 16:42:21'),
(65, 6, 'Updated', '2024-08-23 16:42:21'),
(66, 7, 'Updated', '2024-08-23 16:42:21'),
(67, 3, 'Updated', '2024-08-23 17:17:32'),
(68, 11, 'Inserted', '2024-08-23 17:37:35'),
(69, 11, 'Updated', '2024-08-23 17:38:14'),
(70, 11, 'Updated', '2024-08-23 17:38:28'),
(71, 11, 'Updated', '2024-08-23 17:38:38'),
(72, 11, 'Updated', '2024-08-23 17:45:14'),
(73, 11, 'Updated', '2024-08-24 00:37:22'),
(74, 11, 'Updated', '2024-08-24 00:37:28'),
(75, 12, 'Inserted', '2024-08-24 00:44:29'),
(76, 13, 'Inserted', '2024-08-24 00:44:29'),
(77, 14, 'Inserted', '2024-08-24 00:44:29'),
(78, 15, 'Inserted', '2024-08-24 00:45:28'),
(79, 16, 'Inserted', '2024-08-24 00:45:28'),
(80, 17, 'Inserted', '2024-08-24 00:45:28'),
(81, 18, 'Inserted', '2024-08-24 00:46:18'),
(82, 19, 'Inserted', '2024-08-24 00:46:18'),
(83, 20, 'Inserted', '2024-08-24 00:46:18'),
(84, 19, 'Updated', '2024-08-24 00:47:00'),
(85, 16, 'Updated', '2024-08-24 00:47:04'),
(86, 13, 'Updated', '2024-08-24 00:47:11'),
(87, 9, 'Updated', '2024-08-24 00:47:19'),
(88, 21, 'Inserted', '2024-08-24 00:48:00'),
(89, 22, 'Inserted', '2024-08-24 00:48:00'),
(90, 23, 'Inserted', '2024-08-24 00:48:00'),
(91, 24, 'Inserted', '2024-08-24 00:48:41'),
(92, 25, 'Inserted', '2024-08-24 00:48:41'),
(93, 26, 'Inserted', '2024-08-24 00:48:41'),
(94, 27, 'Inserted', '2024-08-24 00:49:27'),
(95, 28, 'Inserted', '2024-08-24 00:49:27'),
(96, 29, 'Inserted', '2024-08-24 00:49:27'),
(97, 30, 'Inserted', '2024-08-24 00:50:00'),
(98, 31, 'Inserted', '2024-08-24 00:50:00'),
(99, 32, 'Inserted', '2024-08-24 00:50:00'),
(100, 2, 'Updated', '2024-08-24 01:00:08');

-- --------------------------------------------------------

--
-- Table structure for table `log_supplier`
--

CREATE TABLE `log_supplier` (
  `log_id` int(11) NOT NULL,
  `supplier_id` int(11) NOT NULL,
  `Action` varchar(50) NOT NULL,
  `Act_Time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `log_supplier`
--

INSERT INTO `log_supplier` (`log_id`, `supplier_id`, `Action`, `Act_Time`) VALUES
(1, 3, 'Inserted', '2024-08-19 07:57:56'),
(2, 4, 'Inserted', '2024-08-19 07:57:56'),
(3, 5, 'Inserted', '2024-08-19 07:57:56'),
(4, 6, 'Inserted', '2024-08-19 07:57:56'),
(5, 7, 'Inserted', '2024-08-19 07:57:56'),
(6, 8, 'Inserted', '2024-08-19 07:57:56'),
(7, 9, 'Inserted', '2024-08-19 07:57:56'),
(8, 10, 'Inserted', '2024-08-19 07:57:56'),
(9, 11, 'Inserted', '2024-08-19 07:57:56'),
(10, 12, 'Inserted', '2024-08-19 07:57:56'),
(11, 13, 'Inserted', '2024-08-19 08:11:41'),
(12, 14, 'Inserted', '2024-08-19 08:11:41'),
(13, 15, 'Inserted', '2024-08-19 08:11:41'),
(14, 16, 'Inserted', '2024-08-19 08:11:41'),
(15, 17, 'Inserted', '2024-08-19 08:11:41'),
(16, 18, 'Inserted', '2024-08-19 08:11:41'),
(17, 19, 'Inserted', '2024-08-19 08:11:41'),
(18, 20, 'Inserted', '2024-08-19 08:11:41'),
(19, 2, 'Updated', '2024-08-19 08:27:24'),
(20, 4, 'Updated', '2024-08-19 08:28:34'),
(21, 1, 'Updated', '2024-08-21 14:53:16'),
(22, 1, 'Updated', '2024-08-21 15:11:39'),
(23, 1, 'Updated', '2024-08-21 15:16:26'),
(24, 1, 'Updated', '2024-08-21 15:16:48'),
(25, 1, 'Updated', '2024-08-21 15:18:05'),
(26, 1, 'Updated', '2024-08-21 15:18:26');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL,
  `cust_id` int(11) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `order_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`order_id`, `cust_id`, `amount`, `order_time`) VALUES
(27, 6, 76297, '2024-08-19 08:36:22'),
(28, 3, 2696, '2024-08-19 08:42:16'),
(29, 3, 2696, '2024-08-19 08:47:07'),
(30, 1, 4246, '2024-08-19 08:55:25'),
(31, 5, 66696, '2024-08-19 11:11:33'),
(32, 1, 3897, '2024-08-19 11:40:27'),
(33, 1, 138297, '2024-08-19 13:20:08'),
(34, 1, 138297, '2024-08-19 13:50:26'),
(35, 1, 138297, '2024-08-19 14:25:16'),
(36, 1, 199497, '2024-08-20 06:05:44'),
(37, 1, 439198, '2024-08-20 06:20:29'),
(41, 2, 3490, '2024-08-20 11:00:09'),
(42, 2, 1748, '2024-08-20 11:04:25'),
(44, 2, 1745, '2024-08-20 11:35:37'),
(45, 27, 999, '2024-08-23 16:40:29'),
(46, 27, 4496, '2024-08-23 16:42:21'),
(47, 1, 62000, '2024-08-24 01:00:08');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `product_id` int(11) NOT NULL,
  `productname` varchar(100) NOT NULL,
  `category` enum('Electronics','Clothing','HomeAppliances','Books','Furniture','Toys','Sports','Health','Automotive','Groceries') NOT NULL,
  `status` enum('Available','Out Of Stock') NOT NULL,
  `rate` double NOT NULL,
  `quantity` int(11) NOT NULL,
  `supplier_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`product_id`, `productname`, `category`, `status`, `rate`, `quantity`, `supplier_id`) VALUES
(1, 'Assemble Computer', 'Electronics', 'Available', 73000, 27, 1),
(2, 'HP Victus Laptop', 'Electronics', 'Available', 62000, 17, 1),
(3, 'Dell Keyboard 124', 'Electronics', 'Available', 999, 31, 1),
(4, 'HP Mouse 555', 'Electronics', 'Available', 749, 19, 1),
(5, 'Ant Airstroke CPU Cabinet', 'Electronics', 'Available', 1549, 14, 1),
(6, 'Hoody', 'Clothing', 'Available', 1349, 8, 3),
(7, 'T-Shirt', 'Clothing', 'Available', 599, 45, 4),
(8, 'Pants', 'Clothing', 'Available', 1649, 28, 3),
(9, 'Shorts', 'Clothing', 'Available', 349, 12, 4),
(10, 'Socks', 'Clothing', 'Available', 99, 25, 4),
(11, 'BP Machine', 'Health', 'Available', 1599, 32, 2),
(12, 'The Great Gatsby', 'Books', 'Available', 10.99, 50, 8),
(13, '1984', 'Books', 'Available', 8.99, 18, 7),
(14, 'To Kill a Mockingbird', 'Books', 'Available', 12.5, 30, 8),
(15, 'Air Conditioner', 'HomeAppliances', 'Available', 299.99, 15, 5),
(16, 'Microwave Oven', 'HomeAppliances', 'Available', 89.99, 15, 6),
(17, 'Refrigerator', 'HomeAppliances', 'Available', 499.99, 10, 5),
(18, 'Building Blocks Set', 'Toys', 'Available', 29.99, 40, 11),
(19, 'Remote Control Car', 'Toys', 'Available', 49.99, 15, 12),
(20, 'Dollhouse', 'Toys', 'Available', 39.99, 25, 11),
(21, 'Football', 'Sports', 'Available', 19.99, 100, 13),
(22, 'Tennis Racket', 'Sports', 'Available', 59.99, 15, 14),
(23, 'Basketball Hoop', 'Sports', 'Available', 120, 10, 13),
(24, 'Vitamin C Tablets', 'Health', 'Available', 15.99, 200, 15),
(25, 'First Aid Kit', 'Health', 'Available', 25.5, 20, 16),
(26, 'Hand Sanitizer', 'Health', 'Available', 5.99, 150, 15),
(27, 'Engine Oil', 'Automotive', 'Available', 29.99, 80, 17),
(28, 'Car Tire Set', 'Automotive', 'Available', 250, 20, 18),
(29, 'Brake Pads', 'Automotive', 'Available', 45.75, 60, 18),
(30, 'Organic Apples', 'Groceries', 'Available', 3.99, 500, 19),
(31, 'Whole Wheat Bread', 'Groceries', 'Available', 2.49, 14, 20),
(32, 'Almond Milk', 'Groceries', 'Available', 4.25, 300, 20);

--
-- Triggers `product`
--
DELIMITER $$
CREATE TRIGGER `insertProductLog` AFTER INSERT ON `product` FOR EACH ROW Insert into log_product(product_id,ACTION,act_time) values(NEW.product_id,"Inserted",Now())
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `updateProductLog` AFTER UPDATE ON `product` FOR EACH ROW Insert into log_product(product_id,ACTION,act_time) values(OLD.product_id,"Updated",Now())
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `updateProductStatus` BEFORE UPDATE ON `product` FOR EACH ROW BEGIN
    IF NEW.quantity = 0 THEN
        SET NEW.status = 'Out of Stock';
    ELSEIF NEW.quantity > 0 THEN
        SET NEW.status = 'Available';
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `purchased_product`
--

CREATE TABLE `purchased_product` (
  `order_id` int(11) NOT NULL,
  `cust_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `transaction_id` int(11) DEFAULT NULL,
  `rate` double NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `timeOfPurchasing` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `purchased_product`
--

INSERT INTO `purchased_product` (`order_id`, `cust_id`, `product_id`, `transaction_id`, `rate`, `quantity`, `amount`, `timeOfPurchasing`) VALUES
(27, 6, 5, 26, 1549, 1, 1549, '2024-08-19 08:36:22'),
(27, 6, 1, 26, 73000, 1, 73000, '2024-08-19 08:36:22'),
(28, 3, 7, 27, 599, 18, 10782, '2024-08-19 08:42:16'),
(28, 3, 8, 27, 1649, 30, 49470, '2024-08-19 08:42:16'),
(29, 3, 7, 28, 599, 1, 599, '2024-08-19 08:47:07'),
(29, 3, 8, 28, 1649, 1, 1649, '2024-08-19 08:47:07'),
(30, 1, 7, 29, 599, 1, 599, '2024-08-19 08:55:25'),
(30, 1, 8, 29, 1649, 1, 1649, '2024-08-19 08:55:25'),
(30, 1, 8, 29, 1649, 1, 1649, '2024-08-19 08:55:25'),
(30, 1, 9, 29, 349, 1, 349, '2024-08-19 08:55:25'),
(31, 5, 2, 30, 62000, 1, 62000, '2024-08-19 11:11:33'),
(31, 5, 3, 30, 999, 2, 1998, '2024-08-19 11:11:33'),
(31, 5, 6, 30, 1349, 2, 2698, '2024-08-19 11:11:33'),
(32, 1, 3, 31, 999, 1, 999, '2024-08-19 11:40:27'),
(32, 1, 5, 31, 1549, 1, 1549, '2024-08-19 11:40:27'),
(32, 1, 6, 31, 1349, 1, 1349, '2024-08-19 11:40:27'),
(33, 1, 1, 32, 73000, 1, 73000, '2024-08-19 13:20:08'),
(33, 1, 2, 32, 62000, 1, 62000, '2024-08-19 13:20:08'),
(33, 1, 3, 32, 999, 1, 999, '2024-08-19 13:20:08'),
(33, 1, 4, 32, 749, 1, 749, '2024-08-19 13:20:08'),
(33, 1, 5, 32, 1549, 1, 1549, '2024-08-19 13:20:08'),
(34, 1, 1, 33, 73000, 1, 73000, '2024-08-19 13:50:26'),
(34, 1, 2, 33, 62000, 1, 62000, '2024-08-19 13:50:26'),
(34, 1, 3, 33, 999, 1, 999, '2024-08-19 13:50:26'),
(34, 1, 4, 33, 749, 1, 749, '2024-08-19 13:50:26'),
(34, 1, 5, 33, 1549, 1, 1549, '2024-08-19 13:50:26'),
(35, 1, 1, 34, 73000, 1, 73000, '2024-08-19 14:25:16'),
(35, 1, 2, 34, 62000, 1, 62000, '2024-08-19 14:25:16'),
(35, 1, 3, 34, 999, 1, 999, '2024-08-19 14:25:16'),
(35, 1, 4, 34, 749, 1, 749, '2024-08-19 14:25:16'),
(35, 1, 5, 34, 1549, 1, 1549, '2024-08-19 14:25:16'),
(36, 1, 1, 35, 73000, 1, 73000, '2024-08-20 06:05:44'),
(36, 1, 2, 35, 62000, 2, 124000, '2024-08-20 06:05:44'),
(36, 1, 3, 35, 999, 1, 999, '2024-08-20 06:05:44'),
(36, 1, 4, 35, 749, 2, 1498, '2024-08-20 06:05:44'),
(37, 1, 1, 36, 73000, 6, 438000, '2024-08-20 06:20:29'),
(37, 1, 7, 36, 599, 2, 1198, '2024-08-20 06:20:29'),
(41, 2, 9, 40, 349, 10, 3490, '2024-08-20 11:00:09'),
(42, 2, 3, 41, 999, 1, 999, '2024-08-20 11:04:25'),
(42, 2, 4, 41, 749, 1, 749, '2024-08-20 11:04:25'),
(44, 2, 9, 43, 349, 5, 1745, '2024-08-20 11:35:37'),
(45, 27, 3, 44, 999, 1, 999, '2024-08-23 16:40:29'),
(46, 27, 3, 45, 999, 1, 999, '2024-08-23 16:42:21'),
(46, 27, 5, 45, 1549, 1, 1549, '2024-08-23 16:42:21'),
(46, 27, 6, 45, 1349, 1, 1349, '2024-08-23 16:42:21'),
(46, 27, 7, 45, 599, 1, 599, '2024-08-23 16:42:21'),
(47, 1, 2, 46, 62000, 1, 62000, '2024-08-24 01:00:08');

-- --------------------------------------------------------

--
-- Table structure for table `returned_product`
--

CREATE TABLE `returned_product` (
  `return_order_id` int(11) DEFAULT NULL,
  `order_id` int(11) NOT NULL,
  `cust_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `return_transaction_id` int(11) DEFAULT NULL,
  `rate` double DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `timeOfPurchasing` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `timeOfReturning` timestamp NOT NULL DEFAULT current_timestamp(),
  `timeGap` bigint(20) NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `returned_product`
--

INSERT INTO `returned_product` (`return_order_id`, `order_id`, `cust_id`, `product_id`, `return_transaction_id`, `rate`, `quantity`, `amount`, `timeOfPurchasing`, `timeOfReturning`, `timeGap`) VALUES
(14, 31, 5, 2, 13, 1349, 2, 2698, '2024-08-19 11:11:33', '2024-08-19 11:13:40', 0),
(15, 30, 1, 7, 14, 1649, 1, 1649, '2024-08-19 08:55:25', '2024-08-19 11:41:23', 0),
(16, 30, 1, 7, 15, 349, 1, 349, '2024-08-19 08:55:25', '2024-08-19 11:41:58', 0),
(17, 37, 1, 1, 16, 73000, 6, 438000, '2024-08-20 06:20:29', '2024-08-20 06:24:09', 0),
(18, 45, 27, 3, 17, 999, 1, 999, '2024-08-23 16:40:29', '2024-08-23 17:17:32', 0);

-- --------------------------------------------------------

--
-- Table structure for table `returnorders`
--

CREATE TABLE `returnorders` (
  `return_order_id` int(11) NOT NULL,
  `cust_id` int(11) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `return_order_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `returnorders`
--

INSERT INTO `returnorders` (`return_order_id`, `cust_id`, `order_id`, `amount`, `return_order_time`) VALUES
(14, 5, 31, 2698, '2024-08-19 11:13:40'),
(15, 1, 30, 1649, '2024-08-19 11:41:23'),
(16, 1, 30, 349, '2024-08-19 11:41:58'),
(17, 1, 37, 438000, '2024-08-20 06:24:09'),
(18, 27, 45, 999, '2024-08-23 17:17:32');

-- --------------------------------------------------------

--
-- Table structure for table `returntransaction`
--

CREATE TABLE `returntransaction` (
  `return_transaction_id` int(11) NOT NULL,
  `return_order_id` int(11) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `return_trsn_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `returntransaction`
--

INSERT INTO `returntransaction` (`return_transaction_id`, `return_order_id`, `amount`, `return_trsn_time`) VALUES
(13, 14, 14, '2024-08-19 11:13:40'),
(14, 15, 15, '2024-08-19 11:41:23'),
(15, 16, 16, '2024-08-19 11:41:58'),
(16, 17, 438000, '2024-08-20 06:24:09'),
(17, 18, 999, '2024-08-23 17:17:32');

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `supplier_Id` int(11) NOT NULL,
  `EmailId` varchar(100) NOT NULL,
  `Password` varchar(10) NOT NULL,
  `CompanyName` varchar(100) NOT NULL,
  `CompanyAddress` text NOT NULL,
  `FullName` varchar(100) NOT NULL,
  `MobileNo` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`supplier_Id`, `EmailId`, `Password`, `CompanyName`, `CompanyAddress`, `FullName`, `MobileNo`) VALUES
(1, 'supplier1@gmail.com', '123456789@', 'Lenovo India Ltd.', 'New Mumbai', 'supplier-1', '7531598254'),
(2, 'sup2@gmail.com', 'sup2@12345', 'HP', 'New Mumbai', 'sup2', '7531598255'),
(3, 'sup3@gmail.com', 'sup3@12345', 'H & M', 'Sindhubhavan', 'Supplier-3', '7894561230'),
(4, 'sup4@gmail.com', 'sup4@12345', 'Levi\'s', 'Chennai', 'Supplier-4', '7456321891'),
(5, 'sup5@gmail.com', 'sup5@12345', 'Prestige', 'New Mumbai', 'Supplier-5', '7456123890'),
(6, 'sup6@gmail.com', 'sup6@12345', 'Usha', 'New Mumbai', 'Supplier-6', '7536982140'),
(7, 'sup7@gmail.com', 'sup7@12345', 'Navneet Pvt Ltd.', 'Chandigrah', 'Supplier-7', '9875641230'),
(8, 'sup8@gmail.com', 'sup8@12345', 'NCERT', 'Chennai', 'Supplier-8', '8521463702'),
(9, 'sup9@gmail.com', 'sup9@12345', 'Kanan Furniture Pvt Ltd.', 'Chandigrah', 'Supplier-9', '8754123690'),
(10, 'sup10@gmail.com', 'sup10@1234', 'Gautam Furniture Pvt Ltd.', 'Gorakhpur', 'Supplier-10', '8521479630'),
(11, 'sup11@hotmail.com', 'sup11@1234', 'Funskool India Limited', 'Madhyapradesh', 'Supplier-12', '8654123790'),
(12, 'sup12@hotmail.com', 'sup12@1234', 'Clever ClubsLLP', 'Karnataka', 'Supplier-12', '8794563120'),
(13, 'sup13@hotmail.com', 'sup13@1234', 'SG India Pvt Ltd.', 'Jalandhar', 'Supplier-13', '8521634790'),
(14, 'sup14@outlook.com', 'sup14@1234', 'Cosco India Limited.', 'Uttarakhand', 'Supplier-14', '7531598264'),
(15, 'sup15@gmail.com', 'sup15@1234', 'Emcure Pharmaceuticals', 'Pune', 'Supplier-15', '7456321890'),
(16, 'sup16@outlook.com', 'sup16@1234', 'Omega Healthcare Pvt. Ltd.', 'New Mumbai', 'Supplier-16', '9658741230'),
(17, 'sup17@yahoo.com', 'sup17@1234', 'Bosch India Pvt. Ltd.', 'New Delhi', 'Supplier-17', '8754213690'),
(18, 'sup18@gmail.com', 'sup18@1234', 'MRF India Pvt. Ltd.', 'Banglore', 'Supplier-18', '9754681230'),
(19, 'sup19@gmail.com', 'sup19@1234', 'Reliance Fresh Pvt. Ltd.', 'Jamnagar', 'Supplier-19', '7856941230'),
(20, 'sup20@gmail.com', 'sup20@1234', 'Big Basket Pvt. Ltd.', 'Arunachal Pradesh', 'Supplier-20', '7456893201');

--
-- Triggers `supplier`
--
DELIMITER $$
CREATE TRIGGER `insertSupplierLog` AFTER INSERT ON `supplier` FOR EACH ROW Insert into log_supplier(supplier_Id,ACTION,act_time) values(NEW.supplier_id,"Inserted",Now())
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `updateSupplierLog` AFTER UPDATE ON `supplier` FOR EACH ROW Insert into log_supplier(supplier_Id,ACTION,act_time) values(OLD.supplier_id,"Updated",Now())
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `supplierprofile1`
-- (See below for the actual view)
--
CREATE TABLE `supplierprofile1` (
`supplier_id` int(11)
,`fullname` varchar(100)
,`emailid` varchar(100)
,`mobileno` varchar(20)
,`companyname` varchar(100)
,`companyaddress` text
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `supplierprofile2`
-- (See below for the actual view)
--
CREATE TABLE `supplierprofile2` (
`supplier_id` int(11)
,`fullname` varchar(100)
,`emailid` varchar(100)
,`mobileno` varchar(20)
,`companyname` varchar(100)
,`companyaddress` text
);

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `transaction_id` int(11) NOT NULL,
  `cust_id` int(11) DEFAULT NULL,
  `order_id` int(11) NOT NULL,
  `amount` double DEFAULT NULL,
  `trsn_type` varchar(50) NOT NULL,
  `trsn_time` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`transaction_id`, `cust_id`, `order_id`, `amount`, `trsn_type`, `trsn_time`) VALUES
(26, 6, 27, 76297, 'Card', '2024-08-19 08:36:22'),
(27, 3, 28, 2696, 'UPI', '2024-08-19 08:42:16'),
(28, 3, 29, 2696, 'Card', '2024-08-19 08:47:07'),
(29, 1, 30, 4246, 'Cash', '2024-08-19 08:55:25'),
(30, 5, 31, 66696, 'Card', '2024-08-19 11:11:33'),
(31, 1, 32, 3897, 'UPI', '2024-08-19 11:40:27'),
(32, 1, 33, 138297, 'Cash', '2024-08-19 13:20:08'),
(33, 1, 34, 138297, 'Card', '2024-08-19 13:50:26'),
(34, 1, 35, 138297, 'UPI', '2024-08-19 14:25:16'),
(35, 1, 36, 199497, 'Card', '2024-08-20 06:05:44'),
(36, 1, 37, 439198, 'Card', '2024-08-20 06:20:29'),
(40, 2, 41, 3490, 'Cash', '2024-08-20 11:00:09'),
(41, 2, 42, 1748, 'UPI', '2024-08-20 11:04:25'),
(43, 2, 44, 1745, 'UPI', '2024-08-20 11:35:37'),
(44, 27, 45, 999, 'Card', '2024-08-23 16:40:29'),
(45, 27, 46, 4496, 'UPI', '2024-08-23 16:42:21'),
(46, 1, 47, 62000, 'Cash', '2024-08-24 01:00:08');

-- --------------------------------------------------------

--
-- Structure for view `adminprofile1`
--
DROP TABLE IF EXISTS `adminprofile1`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `adminprofile1`  AS SELECT `admin`.`admin_id` AS `admin_id`, `admin`.`EmailId` AS `emailid`, `admin`.`FullName` AS `fullname`, `admin`.`MobileNo` AS `mobileno` FROM `admin` WHERE `admin`.`admin_id` = 1 ;

-- --------------------------------------------------------

--
-- Structure for view `adminprofile3`
--
DROP TABLE IF EXISTS `adminprofile3`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `adminprofile3`  AS SELECT `admin`.`admin_id` AS `admin_id`, `admin`.`EmailId` AS `emailid`, `admin`.`FullName` AS `fullname`, `admin`.`MobileNo` AS `mobileno` FROM `admin` WHERE `admin`.`admin_id` = 3 ;

-- --------------------------------------------------------

--
-- Structure for view `customerprofile1`
--
DROP TABLE IF EXISTS `customerprofile1`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `customerprofile1`  AS SELECT `customer`.`cust_id` AS `cust_id`, `customer`.`Emailid` AS `emailid`, `customer`.`FullName` AS `fullname`, `customer`.`Address` AS `address`, `customer`.`MobileNo` AS `mobileno` FROM `customer` WHERE `customer`.`cust_id` = 1 ;

-- --------------------------------------------------------

--
-- Structure for view `customerprofile2`
--
DROP TABLE IF EXISTS `customerprofile2`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `customerprofile2`  AS SELECT `customer`.`cust_id` AS `cust_id`, `customer`.`Emailid` AS `emailid`, `customer`.`FullName` AS `fullname`, `customer`.`Address` AS `address`, `customer`.`MobileNo` AS `mobileno` FROM `customer` WHERE `customer`.`cust_id` = 2 ;

-- --------------------------------------------------------

--
-- Structure for view `customerprofile5`
--
DROP TABLE IF EXISTS `customerprofile5`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `customerprofile5`  AS SELECT `customer`.`cust_id` AS `cust_id`, `customer`.`Emailid` AS `emailid`, `customer`.`FullName` AS `fullname`, `customer`.`Address` AS `address`, `customer`.`MobileNo` AS `mobileno` FROM `customer` WHERE `customer`.`cust_id` = 5 ;

-- --------------------------------------------------------

--
-- Structure for view `customerprofile27`
--
DROP TABLE IF EXISTS `customerprofile27`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `customerprofile27`  AS SELECT `customer`.`cust_id` AS `cust_id`, `customer`.`Emailid` AS `emailid`, `customer`.`FullName` AS `fullname`, `customer`.`Address` AS `address`, `customer`.`MobileNo` AS `mobileno` FROM `customer` WHERE `customer`.`cust_id` = 27 ;

-- --------------------------------------------------------

--
-- Structure for view `supplierprofile1`
--
DROP TABLE IF EXISTS `supplierprofile1`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `supplierprofile1`  AS SELECT `supplier`.`supplier_Id` AS `supplier_id`, `supplier`.`FullName` AS `fullname`, `supplier`.`EmailId` AS `emailid`, `supplier`.`MobileNo` AS `mobileno`, `supplier`.`CompanyName` AS `companyname`, `supplier`.`CompanyAddress` AS `companyaddress` FROM `supplier` WHERE `supplier`.`supplier_Id` = 1 ;

-- --------------------------------------------------------

--
-- Structure for view `supplierprofile2`
--
DROP TABLE IF EXISTS `supplierprofile2`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `supplierprofile2`  AS SELECT `supplier`.`supplier_Id` AS `supplier_id`, `supplier`.`FullName` AS `fullname`, `supplier`.`EmailId` AS `emailid`, `supplier`.`MobileNo` AS `mobileno`, `supplier`.`CompanyName` AS `companyname`, `supplier`.`CompanyAddress` AS `companyaddress` FROM `supplier` WHERE `supplier`.`supplier_Id` = 2 ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`admin_id`),
  ADD UNIQUE KEY `MobileNo` (`MobileNo`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`cust_id`),
  ADD UNIQUE KEY `MobileNo` (`MobileNo`);

--
-- Indexes for table `log_admin`
--
ALTER TABLE `log_admin`
  ADD PRIMARY KEY (`log_id`),
  ADD KEY `admin_id` (`admin_id`);

--
-- Indexes for table `log_customer`
--
ALTER TABLE `log_customer`
  ADD PRIMARY KEY (`log_id`),
  ADD KEY `cust_id` (`cust_id`);

--
-- Indexes for table `log_product`
--
ALTER TABLE `log_product`
  ADD PRIMARY KEY (`log_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `log_supplier`
--
ALTER TABLE `log_supplier`
  ADD PRIMARY KEY (`log_id`),
  ADD KEY `supplier_id` (`supplier_id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `cust_id` (`cust_id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`product_id`),
  ADD KEY `supplier_id` (`supplier_id`);

--
-- Indexes for table `purchased_product`
--
ALTER TABLE `purchased_product`
  ADD KEY `order_id` (`order_id`),
  ADD KEY `cust_id` (`cust_id`),
  ADD KEY `product_id` (`product_id`),
  ADD KEY `transaction_id` (`transaction_id`);

--
-- Indexes for table `returned_product`
--
ALTER TABLE `returned_product`
  ADD KEY `return_order_id` (`return_order_id`),
  ADD KEY `cust_id` (`cust_id`),
  ADD KEY `product_id` (`product_id`),
  ADD KEY `return_transaction_id` (`return_transaction_id`),
  ADD KEY `order_id` (`order_id`);

--
-- Indexes for table `returnorders`
--
ALTER TABLE `returnorders`
  ADD PRIMARY KEY (`return_order_id`),
  ADD KEY `cust_id` (`cust_id`),
  ADD KEY `order_id` (`order_id`);

--
-- Indexes for table `returntransaction`
--
ALTER TABLE `returntransaction`
  ADD PRIMARY KEY (`return_transaction_id`),
  ADD KEY `return_order_id` (`return_order_id`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`supplier_Id`),
  ADD UNIQUE KEY `MobileNo` (`MobileNo`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`transaction_id`),
  ADD KEY `cust_id` (`cust_id`),
  ADD KEY `order_id` (`order_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `admin_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `cust_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `log_admin`
--
ALTER TABLE `log_admin`
  MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `log_customer`
--
ALTER TABLE `log_customer`
  MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT for table `log_product`
--
ALTER TABLE `log_product`
  MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=101;

--
-- AUTO_INCREMENT for table `log_supplier`
--
ALTER TABLE `log_supplier`
  MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `returnorders`
--
ALTER TABLE `returnorders`
  MODIFY `return_order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `returntransaction`
--
ALTER TABLE `returntransaction`
  MODIFY `return_transaction_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `supplier`
--
ALTER TABLE `supplier`
  MODIFY `supplier_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `transaction_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `log_admin`
--
ALTER TABLE `log_admin`
  ADD CONSTRAINT `log_admin_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`admin_id`);

--
-- Constraints for table `log_customer`
--
ALTER TABLE `log_customer`
  ADD CONSTRAINT `log_customer_ibfk_1` FOREIGN KEY (`cust_id`) REFERENCES `customer` (`cust_id`);

--
-- Constraints for table `log_product`
--
ALTER TABLE `log_product`
  ADD CONSTRAINT `log_product_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`);

--
-- Constraints for table `log_supplier`
--
ALTER TABLE `log_supplier`
  ADD CONSTRAINT `log_supplier_ibfk_1` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`supplier_Id`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`cust_id`) REFERENCES `customer` (`cust_id`);

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `product_ibfk_1` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`supplier_Id`);

--
-- Constraints for table `purchased_product`
--
ALTER TABLE `purchased_product`
  ADD CONSTRAINT `purchased_product_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  ADD CONSTRAINT `purchased_product_ibfk_2` FOREIGN KEY (`cust_id`) REFERENCES `customer` (`cust_id`),
  ADD CONSTRAINT `purchased_product_ibfk_3` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  ADD CONSTRAINT `purchased_product_ibfk_4` FOREIGN KEY (`transaction_id`) REFERENCES `transaction` (`transaction_id`);

--
-- Constraints for table `returned_product`
--
ALTER TABLE `returned_product`
  ADD CONSTRAINT `returned_product_ibfk_1` FOREIGN KEY (`return_order_id`) REFERENCES `returnorders` (`return_order_id`),
  ADD CONSTRAINT `returned_product_ibfk_2` FOREIGN KEY (`cust_id`) REFERENCES `customer` (`cust_id`),
  ADD CONSTRAINT `returned_product_ibfk_3` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  ADD CONSTRAINT `returned_product_ibfk_4` FOREIGN KEY (`return_transaction_id`) REFERENCES `returntransaction` (`return_transaction_id`),
  ADD CONSTRAINT `returned_product_ibfk_5` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`);

--
-- Constraints for table `returnorders`
--
ALTER TABLE `returnorders`
  ADD CONSTRAINT `returnorders_ibfk_1` FOREIGN KEY (`cust_id`) REFERENCES `customer` (`cust_id`),
  ADD CONSTRAINT `returnorders_ibfk_2` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  ADD CONSTRAINT `returnorders_ibfk_3` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`);

--
-- Constraints for table `returntransaction`
--
ALTER TABLE `returntransaction`
  ADD CONSTRAINT `returntransaction_ibfk_1` FOREIGN KEY (`return_order_id`) REFERENCES `returnorders` (`return_order_id`),
  ADD CONSTRAINT `returntransaction_ibfk_2` FOREIGN KEY (`return_order_id`) REFERENCES `returnorders` (`return_order_id`);

--
-- Constraints for table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`cust_id`) REFERENCES `customer` (`cust_id`),
  ADD CONSTRAINT `transaction_ibfk_2` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
