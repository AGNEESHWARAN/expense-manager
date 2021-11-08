-- MySQL dump 10.13  Distrib 8.0.27, for Linux (x86_64)
--
-- Host: localhost    Database: splitwise
-- ------------------------------------------------------
-- Server version	8.0.27-0ubuntu0.20.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Bill`
--

DROP TABLE IF EXISTS `Bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Bill` (
  `bill_id` int NOT NULL AUTO_INCREMENT,
  `note` varchar(100) DEFAULT NULL,
  `event_id` int DEFAULT NULL,
  `bill_type` varchar(20) DEFAULT NULL,
  `total` double(7,1) DEFAULT NULL,
  `share_type` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`bill_id`),
  KEY `event_id` (`event_id`),
  CONSTRAINT `Bill_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `Events_table` (`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Events_table`
--

DROP TABLE IF EXISTS `Events_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Events_table` (
  `event_id` int NOT NULL AUTO_INCREMENT,
  `event_name` varchar(50) DEFAULT NULL,
  `event_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Expenses`
--

DROP TABLE IF EXISTS `Expenses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Expenses` (
  `expense_id` int NOT NULL AUTO_INCREMENT,
  `bill_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `amount_payed` double(7,1) DEFAULT NULL,
  `debt` decimal(7,1) DEFAULT NULL,
  `credit` decimal(7,1) DEFAULT NULL,
  PRIMARY KEY (`expense_id`),
  KEY `bill_id` (`bill_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `Expenses_ibfk_1` FOREIGN KEY (`bill_id`) REFERENCES `Bill` (`bill_id`),
  CONSTRAINT `Expenses_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Grants_table`
--

DROP TABLE IF EXISTS `Grants_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Grants_table` (
  `grant_id` int NOT NULL AUTO_INCREMENT,
  `total_amount` decimal(7,1) DEFAULT NULL,
  `bill_id` int DEFAULT NULL,
  PRIMARY KEY (`grant_id`),
  KEY `bill_id` (`bill_id`),
  CONSTRAINT `Grants_table_ibfk_1` FOREIGN KEY (`bill_id`) REFERENCES `Bill` (`bill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Group_events`
--

DROP TABLE IF EXISTS `Group_events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Group_events` (
  `group_event_id` int NOT NULL AUTO_INCREMENT,
  `group_id` int DEFAULT NULL,
  `event_id` int DEFAULT NULL,
  PRIMARY KEY (`group_event_id`),
  KEY `group_id` (`group_id`),
  KEY `event_id` (`event_id`),
  CONSTRAINT `Group_events_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `Groups_table` (`group_id`),
  CONSTRAINT `Group_events_ibfk_2` FOREIGN KEY (`event_id`) REFERENCES `Events_table` (`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Group_members`
--

DROP TABLE IF EXISTS `Group_members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Group_members` (
  `entry_id` int NOT NULL AUTO_INCREMENT,
  `group_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`entry_id`),
  KEY `group_id` (`group_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `Group_members_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `Groups_table` (`group_id`),
  CONSTRAINT `Group_members_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Groups_table`
--

DROP TABLE IF EXISTS `Groups_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Groups_table` (
  `group_id` int NOT NULL AUTO_INCREMENT,
  `group_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Individual_events`
--

DROP TABLE IF EXISTS `Individual_events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Individual_events` (
  `individual_event_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `event_id` int DEFAULT NULL,
  PRIMARY KEY (`individual_event_id`),
  KEY `user_id` (`user_id`),
  KEY `event_id` (`event_id`),
  CONSTRAINT `Individual_events_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`),
  CONSTRAINT `Individual_events_ibfk_2` FOREIGN KEY (`event_id`) REFERENCES `Events_table` (`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Purchase`
--

DROP TABLE IF EXISTS `Purchase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Purchase` (
  `purchase_id` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(50) DEFAULT NULL,
  `total_amount` decimal(7,1) DEFAULT NULL,
  `bill_id` int DEFAULT NULL,
  PRIMARY KEY (`purchase_id`),
  KEY `bill_id` (`bill_id`),
  CONSTRAINT `Purchase_ibfk_1` FOREIGN KEY (`bill_id`) REFERENCES `Bill` (`bill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Split`
--

DROP TABLE IF EXISTS `Split`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Split` (
  `split_id` int NOT NULL AUTO_INCREMENT,
  `payee` int DEFAULT NULL,
  `payer` int DEFAULT NULL,
  `amount` double(7,1) DEFAULT NULL,
  `bill_id` int DEFAULT NULL,
  PRIMARY KEY (`split_id`),
  KEY `payee` (`payee`),
  KEY `payer` (`payer`),
  KEY `bill_id` (`bill_id`),
  CONSTRAINT `Split_ibfk_1` FOREIGN KEY (`payee`) REFERENCES `User` (`user_id`),
  CONSTRAINT `Split_ibfk_2` FOREIGN KEY (`payer`) REFERENCES `User` (`user_id`),
  CONSTRAINT `Split_ibfk_3` FOREIGN KEY (`bill_id`) REFERENCES `Bill` (`bill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `phone_number` varchar(12) DEFAULT NULL,
  `gender` char(9) DEFAULT NULL,
  `email` varchar(35) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-11-07 23:18:05
