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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-13 22:06:28
