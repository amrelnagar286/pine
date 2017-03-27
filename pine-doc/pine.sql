-- MySQL dump 10.13  Distrib 5.6.27, for FreeBSD10.1 (amd64)
--
-- Host: localhost    Database: pine
-- ------------------------------------------------------
-- Server version	5.6.27

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
-- Table structure for table `pi_broker`
--

DROP TABLE IF EXISTS `pi_broker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pi_broker` (
  `OID` char(36) NOT NULL,
  `ID` varchar(50) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `BK_USERNAME` varchar(24) DEFAULT NULL,
  `BK_PASSWORD` varchar(24) DEFAULT NULL,
  `BK_PORT` varchar(5) NOT NULL,
  `BK_WEBSOCKET_PORT` varchar(5) NOT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`ID`),
  KEY `IDX_1` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pi_broker`
--

LOCK TABLES `pi_broker` WRITE;
/*!40000 ALTER TABLE `pi_broker` DISABLE KEYS */;
INSERT INTO `pi_broker` VALUES ('88f3d585-24eb-41ae-af1f-34ca8e21b220','BK001','Test server!',NULL,NULL,'1991','8881','This is first item!','admin','2017-03-23 20:24:59',NULL,NULL);
/*!40000 ALTER TABLE `pi_broker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pi_publish`
--

DROP TABLE IF EXISTS `pi_publish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pi_publish` (
  `OID` char(36) NOT NULL,
  `CLIENT_ID` varchar(12) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `TOPIC` varchar(150) NOT NULL,
  `QOS` varchar(1) NOT NULL DEFAULT '2',
  `BK_BROKER_ADDR` varchar(100) NOT NULL,
  `BK_USERNAME` varchar(24) DEFAULT NULL,
  `BK_PASSWORD` varchar(24) DEFAULT NULL,
  `CONTENT` varchar(4000) DEFAULT NULL,
  `CONTENT_EXPR` varchar(4000) DEFAULT NULL,
  `EVENT_ID` varchar(12) DEFAULT NULL,
  `SCRIPT_TYPE` varchar(10) DEFAULT NULL,
  `SCRIPT_ID` varchar(20) DEFAULT NULL,
  `INTERVAL_SEC` varchar(5) NOT NULL DEFAULT '86400',
  `FIRST_ON_START` varchar(1) NOT NULL DEFAULT 'Y',
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`CLIENT_ID`),
  KEY `IDX_1` (`NAME`),
  KEY `IDX_2` (`TOPIC`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pi_publish`
--

LOCK TABLES `pi_publish` WRITE;
/*!40000 ALTER TABLE `pi_publish` DISABLE KEYS */;
/*!40000 ALTER TABLE `pi_publish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_account`
--

DROP TABLE IF EXISTS `tb_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_account` (
  `OID` char(36) NOT NULL,
  `ACCOUNT` varchar(24) NOT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  `ON_JOB` varchar(50) NOT NULL DEFAULT 'Y',
  `CUSERID` varchar(24) DEFAULT NULL,
  `CDATE` datetime DEFAULT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`,`ACCOUNT`),
  UNIQUE KEY `UK_1` (`ACCOUNT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_account`
--

LOCK TABLES `tb_account` WRITE;
/*!40000 ALTER TABLE `tb_account` DISABLE KEYS */;
INSERT INTO `tb_account` VALUES ('0','admin','b7f1acbdc67d3b2a68d36a5d7a29b228','Y','admin','2012-11-11 10:56:23','admin','2014-04-19 11:32:04'),('15822da5-25dc-490c-bdfb-be75f5ff4843','tester','5c06e78482c0a8b9cefb34e5cad64941','Y','admin','2015-04-23 11:26:53','admin','2015-08-29 17:54:08'),('52cb274e-388d-419f-a81e-67ca599bfb63','steven','953703bc964d40ce93dc90e2e012062e','Y','admin','2015-09-11 10:33:53',NULL,NULL),('9c239d19-3646-41db-b394-d34c5bf34671','tiffany','a4c1cfdf17b58a01e778a8b4c5701ea5','Y','admin','2015-09-11 10:15:29',NULL,NULL);
/*!40000 ALTER TABLE `tb_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sys_code`
--

DROP TABLE IF EXISTS `tb_sys_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sys_code` (
  `OID` char(36) NOT NULL,
  `CODE` varchar(25) NOT NULL,
  `TYPE` varchar(10) NOT NULL,
  `NAME` varchar(100) NOT NULL,
  `PARAM1` varchar(100) DEFAULT NULL,
  `PARAM2` varchar(100) DEFAULT NULL,
  `PARAM3` varchar(100) DEFAULT NULL,
  `PARAM4` varchar(100) DEFAULT NULL,
  `PARAM5` varchar(100) DEFAULT NULL,
  `CUSERID` varchar(24) NOT NULL,
  `CDATE` datetime NOT NULL,
  `UUSERID` varchar(24) DEFAULT NULL,
  `UDATE` datetime DEFAULT NULL,
  PRIMARY KEY (`OID`),
  UNIQUE KEY `UK_1` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sys_code`
--

LOCK TABLES `tb_sys_code` WRITE;
/*!40000 ALTER TABLE `tb_sys_code` DISABLE KEYS */;
INSERT INTO `tb_sys_code` VALUES ('10046e27-4c0a-40ed-89c5-6530525b421f','MSG_DOS0006','MSG','insert data fail!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30','admin','2014-07-18 11:41:37'),('328849f7-446d-415e-a598-9477caa5b69c','MSG_UOS0002','MSG','parameter is incorrect!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('44bf5446-b972-47a2-b0a1-9556861af07e','MSG_DOS0003','MSG','update data success!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('45cab4b8-add1-44be-8cab-7627403c389c','MSG_DOS0001','MSG','data no exist!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('520a61eb-4bb0-4d0f-a0d5-4bbdc56f4202','MSG_DOS0005','MSG','insert data success!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('6cec1a23-d2b9-48ac-b720-13fcf9f8d39b','MSG_UOS0001','MSG','parameter cann\'t blank!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('6fb72e96-43fe-45b3-8a45-9f5898f61b4f','MSG_DOS0009','MSG','search no data!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('750b16c0-9058-435c-b76f-9cb312d2f383','MSG_STD0004','MSG','Please upload a image file!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('76a4cc16-e324-4a94-8c62-9c78643f074a','MSG_STD0005','MSG','Data errors!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('7e8c56a4-d3dc-4768-9b2b-77dd03554814','MSG_DOS0008','MSG','delete data fail!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('8a2a8d70-99f8-42bd-a531-a9e4350fa753','MSG_UOS0004','MSG','Please try again!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('959f6d8a-cbae-4f52-950d-1c7de962ca36','MSG_DOS0004','MSG','update data fail!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('96c3a7cb-966a-406a-8365-0e79a5862bf2','MSG_DOS0010','MSG','Data to be used, and can not be deleted!',NULL,NULL,NULL,NULL,NULL,'admin','2012-11-12 12:42:16',NULL,NULL),('a714afb2-e28c-4450-8f16-725ee2a3df22','MSG_UOS0003','MSG','object null!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30','admin','2015-04-27 21:16:19'),('c5a5da5b-27c3-46cc-b5d3-ced1a168df0c','MSG_STD0003','MSG','Please select a file!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('c81bfed6-2814-4ada-82e2-f6d25e065eb0','MSG_STD0002','MSG','Upload a file type error!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('cca41808-0781-4d11-8303-93369471e901','MSG_DOS0002','MSG','data is exist!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('cec13700-4687-49c0-a89e-ae444388a318','MSG_STD0001','MSG','Login fail!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('d1322f32-12a3-4b1b-be98-e01acc6180bc','MSG_DOS0007','MSG','delete data success!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('dc0f2f81-8f04-47ca-b1fb-7810e4fe9d81','MSG_BSE0002','MSG','No sign-on system access denied!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('f0e2ee47-38df-490c-bbae-aba7b0b61dcc','MSG_BSE0001','MSG','No permission!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL),('fc3e6505-e0eb-491d-9c79-8d9f18f47e38','MSG_BSE0003','MSG','error. dozer mapper id blank!',NULL,NULL,NULL,NULL,NULL,'admin','2012-09-22 15:06:30',NULL,NULL);
/*!40000 ALTER TABLE `tb_sys_code` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-27 18:39:18
