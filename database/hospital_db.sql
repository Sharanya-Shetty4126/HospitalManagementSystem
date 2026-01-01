-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: hospital_db
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `ADDRESS_ID` int NOT NULL AUTO_INCREMENT,
  `STREET_NAME` varchar(100) DEFAULT NULL,
  `HOUSE_NAME` varchar(100) DEFAULT NULL,
  `CITY` varchar(100) NOT NULL,
  `STATE` varchar(100) NOT NULL,
  `COUNTRY` varchar(100) NOT NULL,
  `PIN_CODE` varchar(10) NOT NULL,
  PRIMARY KEY (`ADDRESS_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'MG Road','Green Villa','Chennai','Tamil Nadu','India','600001');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medical_history`
--

DROP TABLE IF EXISTS `medical_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medical_history` (
  `HISTORY_ID` int NOT NULL,
  `PATIENT_ID` int NOT NULL,
  `pat_description` varchar(200) DEFAULT NULL,
  `record_date` date DEFAULT NULL,
  `category` varchar(200) DEFAULT NULL,
  `dis_status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`HISTORY_ID`),
  KEY `PATIENT_ID` (`PATIENT_ID`),
  CONSTRAINT `medical_history_ibfk_1` FOREIGN KEY (`PATIENT_ID`) REFERENCES `patient` (`PATIENT_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medical_history`
--

LOCK TABLES `medical_history` WRITE;
/*!40000 ALTER TABLE `medical_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `medical_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient` (
  `PATIENT_ID` int NOT NULL AUTO_INCREMENT,
  `PATIENT_NAME` varchar(50) NOT NULL,
  `GENDER` enum('MALE','FEMALE','OTHER') NOT NULL,
  `PHONE_NO` varchar(10) DEFAULT NULL,
  `EMAIL` varchar(150) DEFAULT NULL,
  `BLOOD_GROUP` varchar(10) DEFAULT NULL,
  `DATE_OF_BIRTH` date NOT NULL,
  `ADDRESS_ID` int DEFAULT NULL,
  PRIMARY KEY (`PATIENT_ID`),
  UNIQUE KEY `PHONE_NO` (`PHONE_NO`),
  UNIQUE KEY `EMAIL` (`EMAIL`),
  KEY `FK_ADDRESS_PATIENT` (`ADDRESS_ID`),
  CONSTRAINT `FK_ADDRESS_PATIENT` FOREIGN KEY (`ADDRESS_ID`) REFERENCES `address` (`ADDRESS_ID`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-28 12:31:04
