-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: projectjavafx
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill` (
                        `billID` int NOT NULL AUTO_INCREMENT,
                        `total` int DEFAULT NULL,
                        `orderID` int DEFAULT NULL,
                        `billDate` date DEFAULT NULL,
                        `userID` int DEFAULT NULL,
                        PRIMARY KEY (`billID`),
                        KEY `userID` (`userID`),
                        KEY `orderID` (`orderID`),
                        CONSTRAINT `bill_ibfk_2` FOREIGN KEY (`userID`) REFERENCES `user` (`id`),
                        CONSTRAINT `bill_ibfk_3` FOREIGN KEY (`orderID`) REFERENCES `order` (`OrderID`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES (1,34000,1,'2024-11-02',1),(2,45000,2,'2024-11-02',1),(3,40000,3,'2024-11-01',1),(4,29000,4,'2024-11-03',1),(5,15000,5,'2024-11-03',1),(6,54000,6,'2024-11-03',1),(7,44000,7,'2024-11-03',1),(8,166000,8,'2024-11-05',1),(9,67000,14,'2024-11-06',1),(10,40000,15,'2024-11-06',1),(11,5000,18,'2024-11-06',1),(12,64000,19,'2024-11-07',1),(13,95000,20,'2024-11-07',1),(14,40000,21,'2024-11-08',1),(15,40000,22,'2024-11-08',1),(16,20000,23,'2024-11-08',2),(17,40000,24,'2024-11-08',2),(18,35000,25,'2024-11-08',2),(19,100000,26,'2024-11-08',2),(20,30000,27,'2024-11-08',1),(21,105000,28,'2024-11-09',1),(22,20000,29,'2024-11-09',1),(23,40000,30,'2024-11-09',1),(28,30000,32,'2024-11-13',1),(29,40000,33,'2024-11-13',1),(30,40000,34,'2024-11-13',1),(31,15000,35,'2024-11-13',1),(32,20000,36,'2024-11-13',1),(33,32000,37,'2024-11-13',1),(34,40000,38,'2024-11-13',1),(35,20000,39,'2024-11-13',1);
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
                         `OrderID` int NOT NULL AUTO_INCREMENT,
                         `OrderDate` date DEFAULT NULL,
                         PRIMARY KEY (`OrderID`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1,'2024-11-02'),(2,'2024-11-02'),(3,'2024-11-01'),(4,'2024-11-03'),(5,'2024-11-03'),(6,'2024-11-03'),(7,'2024-11-03'),(8,'2024-11-05'),(14,'2024-11-06'),(15,'2024-11-06'),(18,'2024-11-06'),(19,'2024-11-07'),(20,'2024-11-07'),(21,'2024-11-08'),(22,'2024-11-08'),(23,'2024-11-08'),(24,'2024-11-08'),(25,'2024-11-08'),(26,'2024-11-08'),(27,'2024-11-08'),(28,'2024-11-09'),(29,'2024-11-09'),(30,'2024-11-09'),(32,'2024-11-13'),(33,'2024-11-13'),(34,'2024-11-13'),(35,'2024-11-13'),(36,'2024-11-13'),(37,'2024-11-13'),(38,'2024-11-13'),(39,'2024-11-13');
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_detail`
--

DROP TABLE IF EXISTS `order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_detail` (
                                `orderDetailID` int NOT NULL AUTO_INCREMENT,
                                `orderID` int DEFAULT NULL,
                                `productID` int DEFAULT NULL,
                                `quantity` int DEFAULT NULL,
                                PRIMARY KEY (`orderDetailID`),
                                KEY `orderID` (`orderID`),
                                KEY `productID` (`productID`),
                                CONSTRAINT `order_detail_ibfk_1` FOREIGN KEY (`orderID`) REFERENCES `order` (`OrderID`),
                                CONSTRAINT `order_detail_ibfk_2` FOREIGN KEY (`productID`) REFERENCES `product` (`productID`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_detail`
--

LOCK TABLES `order_detail` WRITE;
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
INSERT INTO `order_detail` VALUES (1,1,4,2),(2,1,5,2),(3,2,1,2),(4,2,4,3),(5,3,2,20),(6,4,2,1),(7,4,3,1),(8,4,6,2),(9,5,4,3),(11,6,2,1),(12,6,5,2),(13,6,4,2),(16,7,2,2),(17,7,6,2),(18,8,1,2),(19,8,5,3),(20,8,2,4),(21,8,4,2),(22,8,8,2),(32,14,3,3),(33,14,4,1),(34,14,2,1),(35,14,5,1),(36,14,1,1),(38,15,9,2),(41,18,8,1),(42,19,5,2),(44,19,2,1),(45,19,1,1),(46,19,4,1),(47,20,1,1),(49,20,9,1),(50,20,2,1),(51,20,9,1),(52,20,9,1),(53,21,1,2),(54,21,4,2),(55,22,9,2),(56,23,9,1),(57,24,2,2),(58,25,2,1),(59,25,1,1),(60,26,2,2),(61,26,9,2),(63,26,9,1),(64,27,1,2),(65,28,1,3),(66,28,9,3),(67,29,10,2),(68,30,2,2),(81,32,4,1),(82,32,8,1),(83,32,2,1),(84,33,2,2),(85,34,9,2),(86,35,1,1),(87,36,2,1),(88,37,15,2),(89,38,2,2),(92,39,4,1),(93,39,1,1);
/*!40000 ALTER TABLE `order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
                           `productID` int NOT NULL AUTO_INCREMENT,
                           `productName` varchar(50) DEFAULT NULL,
                           `price` int DEFAULT NULL,
                           `photo` varchar(50) DEFAULT NULL,
                           `typeID` int DEFAULT NULL,
                           `status` varchar(20) DEFAULT 'Có',
                           PRIMARY KEY (`productID`),
                           KEY `typeID` (`typeID`),
                           CONSTRAINT `product_ibfk_1` FOREIGN KEY (`typeID`) REFERENCES `type_product` (`typeID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Burger',15000,'burger.jpg',2,'Có'),(2,'Pizza',20000,'pizza.jpg',1,'Có'),(3,'Coke',5000,'default.jpg',2,'Xoá'),(4,'Pepsi',5000,'pepsi.jpg',1,'Có'),(5,'Salad',12000,'salad.jpg',1,'Có'),(6,'Water',2000,'water.jpg',2,'Có'),(8,'Coca',5000,'coca.jpg',2,'Có'),(9,'Matcha',20000,'matcha.jpg',2,'Có'),(10,'Cafe Đá',10000,'cafe.jpg',2,'Có'),(12,'Cafe sữa',14000,'milk-cafe.jpg',2,'Có'),(15,'Bạc xiu',16000,'bạc xỉu.jpg',2,'Có');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table`
--

DROP TABLE IF EXISTS `table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `table` (
                         `tableID` int NOT NULL AUTO_INCREMENT,
                         `stt` int DEFAULT NULL,
                         `status` tinyint(1) DEFAULT NULL,
                         PRIMARY KEY (`tableID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table`
--

LOCK TABLES `table` WRITE;
/*!40000 ALTER TABLE `table` DISABLE KEYS */;
INSERT INTO `table` VALUES (1,1,0),(2,2,0),(3,3,0),(4,4,0),(5,5,0),(6,6,0),(7,7,0),(8,8,0),(9,9,0),(10,10,0);
/*!40000 ALTER TABLE `table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type_product`
--

DROP TABLE IF EXISTS `type_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_product` (
                                `typeID` int NOT NULL AUTO_INCREMENT,
                                `typeName` varchar(50) DEFAULT NULL,
                                PRIMARY KEY (`typeID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type_product`
--

LOCK TABLES `type_product` WRITE;
/*!40000 ALTER TABLE `type_product` DISABLE KEYS */;
INSERT INTO `type_product` VALUES (1,'Food'),(2,'Drink');
/*!40000 ALTER TABLE `type_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `email` varchar(25) NOT NULL,
                        `pwd` varchar(25) NOT NULL,
                        `name` varchar(20) NOT NULL,
                        `role` varchar(15) DEFAULT 'staff',
                        `phone` varchar(15) DEFAULT NULL,
                        `status` varchar(15) DEFAULT 'Còn',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'dat@gmail.com','123456','Dat','nhân viên','0834023573','Còn'),(2,'test@gmail.com','123123123','test','nhân viên','08008685','Còn'),(3,'admin@gmail.com','admin','Admin','admin',NULL,'Còn'),(4,'jino@gmail.com','123456','Jino','nhân viên','0834023574','Còn');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-13 22:07:07
