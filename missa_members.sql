-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: missa
-- ------------------------------------------------------
-- Server version	8.0.17

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
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `members` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(30) DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
INSERT INTO `members` VALUES (2,'mis','mis@cc.ncu.edu.tw','123465','2019-08-30 09:36:18','2019-08-30 09:36:18'),(3,'im','im@cc.ncu.edu.tw','123456','2019-08-30 09:37:54','2019-08-30 09:37:54'),(4,'test','test@cc.ncu.edu.tw','test','2019-08-30 09:40:22','2019-08-30 09:40:22'),(5,'123','123@cc','454','2019-08-30 09:40:39','2019-08-30 09:40:39'),(7,'zx','zx','zx','2019-08-30 09:43:33','2019-08-30 09:43:33'),(9,'ab','abc','ab','2019-08-30 09:46:15','2019-08-30 09:46:15'),(10,'123','13','213','2019-08-30 09:47:16','2019-08-30 09:47:16'),(11,'879','9879','879879','2019-08-30 09:48:51','2019-08-30 09:48:51'),(14,'TEst','test   @yahoo.com.tw','H123','2019-09-05 06:12:04','2019-09-05 06:12:04'),(15,'test','test  @gmail.com','test','2019-09-05 06:12:25','2019-09-05 06:12:25'),(16,'test','test中@gmail.com','test','2019-09-05 06:12:43','2019-09-05 06:12:43'),(17,'test','tets @gmail.,com','test','2019-09-05 06:14:13','2019-09-05 06:14:13'),(19,'測試人員','test3@gmail.com','testtest1','2019-09-05 06:23:22','2019-09-05 06:23:22'),(20,'abcdeabcdeabcdeabcdeabcdeabcde','test4@gmail.com','abcde123456','2019-09-05 06:40:15','2019-09-05 06:40:15'),(21,'test','test@123.com','testetet54','2019-09-05 15:06:52','2019-09-05 15:06:52'),(22,'iplab','iplab@cc.ncu.edu.tw','H9183149316','2019-09-18 07:06:45','2019-09-18 07:06:45');
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-16 21:05:08
