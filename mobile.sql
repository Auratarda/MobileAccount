-- MySQL dump 10.13  Distrib 5.6.22, for Win32 (x86)
--
-- Host: localhost    Database: mobile
-- ------------------------------------------------------
-- Server version	5.6.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client` (
  `CLIENT_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ADDRESS` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DATE_OF_BIRTH` datetime DEFAULT NULL,
  `EMAIL` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FIRST_NAME` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LAST_NAME` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PASSPORT` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PASSWORD` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`CLIENT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (1,'Lenina, 5','2015-02-25 16:20:29','ivan@yandex.ru','Ivan','Ivanov','RUS','ivan'),(2,'Shotmana, 5','2015-02-25 16:21:57','auraacra@yandex.ru','Stanislav','Vasilevskii','RUS','admin'),(3,'Beregovaya, 5','2015-02-25 16:24:22','petr@yandex.ru','Petrov','Petr','RUS','petr'),(4,'Beregovaya, 5','2015-02-25 23:05:57','petr@yandex.ru','Sidorov','Sidor','RUS','petr');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contract`
--

DROP TABLE IF EXISTS `contract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contract` (
  `CONTRACT_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BLOCKED_BY_CLIENT` bit(1) DEFAULT NULL,
  `BLOCKED_BY_OPERATOR` bit(1) DEFAULT NULL,
  `CONTRACT_NUMBER` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CLIENT_ID` bigint(20) DEFAULT NULL,
  `TARIFF_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`CONTRACT_ID`),
  KEY `FK_hxx77el4m0mqka6p6uca9qxo4` (`CLIENT_ID`),
  KEY `FK_lpr1ow4rioj17w7sajnno10sk` (`TARIFF_ID`),
  CONSTRAINT `FK_hxx77el4m0mqka6p6uca9qxo4` FOREIGN KEY (`CLIENT_ID`) REFERENCES `client` (`CLIENT_ID`),
  CONSTRAINT `FK_lpr1ow4rioj17w7sajnno10sk` FOREIGN KEY (`TARIFF_ID`) REFERENCES `tariff` (`TARIFF_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contract`
--

LOCK TABLES `contract` WRITE;
/*!40000 ALTER TABLE `contract` DISABLE KEYS */;
INSERT INTO `contract` VALUES (1,'\0','\0','9041234567',1,1),(2,'\0','\0','9046352962',2,2),(3,'\0','','9048888888',3,3);
/*!40000 ALTER TABLE `contract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contract_options`
--

DROP TABLE IF EXISTS `contract_options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contract_options` (
  `OPTION_ID` bigint(20) NOT NULL,
  `CONTRACT_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`OPTION_ID`,`CONTRACT_ID`),
  KEY `FK_svyt3xdvaauv9sqe57w57huy2` (`CONTRACT_ID`),
  CONSTRAINT `FK_2tlt6yl7rg5q3ftx8hylrgs65` FOREIGN KEY (`OPTION_ID`) REFERENCES `contract` (`CONTRACT_ID`),
  CONSTRAINT `FK_svyt3xdvaauv9sqe57w57huy2` FOREIGN KEY (`CONTRACT_ID`) REFERENCES `options` (`OPTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contract_options`
--

LOCK TABLES `contract_options` WRITE;
/*!40000 ALTER TABLE `contract_options` DISABLE KEYS */;
/*!40000 ALTER TABLE `contract_options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `incompatible_options`
--

DROP TABLE IF EXISTS `incompatible_options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `incompatible_options` (
  `INC_OPTION_ID` bigint(20) NOT NULL,
  `CURRENT_OPTION_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`INC_OPTION_ID`,`CURRENT_OPTION_ID`),
  KEY `FK_qwvp9xjrhyhghgqy0lydihbg9` (`CURRENT_OPTION_ID`),
  CONSTRAINT `FK_j38r7w8iunxn67ov3fjc754ie` FOREIGN KEY (`INC_OPTION_ID`) REFERENCES `options` (`OPTION_ID`),
  CONSTRAINT `FK_qwvp9xjrhyhghgqy0lydihbg9` FOREIGN KEY (`CURRENT_OPTION_ID`) REFERENCES `options` (`OPTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incompatible_options`
--

LOCK TABLES `incompatible_options` WRITE;
/*!40000 ALTER TABLE `incompatible_options` DISABLE KEYS */;
/*!40000 ALTER TABLE `incompatible_options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `options`
--

DROP TABLE IF EXISTS `options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `options` (
  `OPTION_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CONNECTION_COST` bigint(20) DEFAULT NULL,
  `OPTION_NAME` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `OPTION_PRICE` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`OPTION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `options`
--

LOCK TABLES `options` WRITE;
/*!40000 ALTER TABLE `options` DISABLE KEYS */;
INSERT INTO `options` VALUES (1,50,'Skryt\' nomer',20),(2,100,'Mayachok',10),(3,10,'Perezvoni mne',5);
/*!40000 ALTER TABLE `options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `required_options`
--

DROP TABLE IF EXISTS `required_options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `required_options` (
  `REQ_OPTION_ID` bigint(20) NOT NULL,
  `CURRENT_OPTION_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`REQ_OPTION_ID`,`CURRENT_OPTION_ID`),
  KEY `FK_c4i3ybwf53ofdecpx564r9qow` (`CURRENT_OPTION_ID`),
  CONSTRAINT `FK_c4i3ybwf53ofdecpx564r9qow` FOREIGN KEY (`CURRENT_OPTION_ID`) REFERENCES `options` (`OPTION_ID`),
  CONSTRAINT `FK_e0qn4b45b5h9k9xk9hcwnq7fo` FOREIGN KEY (`REQ_OPTION_ID`) REFERENCES `options` (`OPTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `required_options`
--

LOCK TABLES `required_options` WRITE;
/*!40000 ALTER TABLE `required_options` DISABLE KEYS */;
/*!40000 ALTER TABLE `required_options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tariff`
--

DROP TABLE IF EXISTS `tariff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tariff` (
  `TARIFF_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TARIFF_NAME` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TARIFF_PRICE` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`TARIFF_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tariff`
--

LOCK TABLES `tariff` WRITE;
/*!40000 ALTER TABLE `tariff` DISABLE KEYS */;
INSERT INTO `tariff` VALUES (1,'Nashi',350),(2,'Strike',300),(3,'Delovoi',700);
/*!40000 ALTER TABLE `tariff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tariff_options`
--

DROP TABLE IF EXISTS `tariff_options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tariff_options` (
  `OPTION_ID` bigint(20) NOT NULL,
  `TARIFF_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`OPTION_ID`,`TARIFF_ID`),
  KEY `FK_f2q35a82i8mhjaq6f2pwywfv9` (`TARIFF_ID`),
  CONSTRAINT `FK_f2q35a82i8mhjaq6f2pwywfv9` FOREIGN KEY (`TARIFF_ID`) REFERENCES `options` (`OPTION_ID`),
  CONSTRAINT `FK_q3oy8ic3ofnb4wqmjyfoyuuet` FOREIGN KEY (`OPTION_ID`) REFERENCES `tariff` (`TARIFF_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tariff_options`
--

LOCK TABLES `tariff_options` WRITE;
/*!40000 ALTER TABLE `tariff_options` DISABLE KEYS */;
/*!40000 ALTER TABLE `tariff_options` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-02-25 23:52:15
