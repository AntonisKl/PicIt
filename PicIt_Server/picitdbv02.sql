-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: picit
-- ------------------------------------------------------
-- Server version	5.7.19-log

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
-- Table structure for table `picture`
--

DROP TABLE IF EXISTS `picture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `picture` (
  `PictureId` int(11) NOT NULL AUTO_INCREMENT,
  `Time` datetime NOT NULL,
  `User_UserId` int(11) NOT NULL,
  `PictureName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`PictureId`,`User_UserId`),
  KEY `fk_Picture_User_idx` (`User_UserId`),
  CONSTRAINT `fk_Picture_User` FOREIGN KEY (`User_UserId`) REFERENCES `user` (`UserId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `picture`
--

LOCK TABLES `picture` WRITE;
/*!40000 ALTER TABLE `picture` DISABLE KEYS */;
/*!40000 ALTER TABLE `picture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `picture_depicts_product`
--

DROP TABLE IF EXISTS `picture_depicts_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `picture_depicts_product` (
  `Picture_PictureId` int(11) NOT NULL,
  `Product_ProductId` int(11) NOT NULL,
  PRIMARY KEY (`Picture_PictureId`,`Product_ProductId`),
  KEY `fk_Picture_has_Product_Product1_idx` (`Product_ProductId`),
  KEY `fk_Picture_has_Product_Picture1_idx` (`Picture_PictureId`),
  CONSTRAINT `fk_Picture_has_Product_Picture1` FOREIGN KEY (`Picture_PictureId`) REFERENCES `picture` (`PictureId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Picture_has_Product_Product1` FOREIGN KEY (`Product_ProductId`) REFERENCES `product` (`ProductId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `picture_depicts_product`
--

LOCK TABLES `picture_depicts_product` WRITE;
/*!40000 ALTER TABLE `picture_depicts_product` DISABLE KEYS */;
/*!40000 ALTER TABLE `picture_depicts_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `ProductId` int(11) NOT NULL AUTO_INCREMENT,
  `ProductName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ProductId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Lenovo Laptop'),(2,'Samsung Galaxy 5'),(3,'Harry Potter');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_has_tags`
--

DROP TABLE IF EXISTS `product_has_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_has_tags` (
  `Tags_TagId` int(11) NOT NULL,
  `Product_ProductId` int(11) NOT NULL,
  PRIMARY KEY (`Tags_TagId`,`Product_ProductId`),
  KEY `fk_Tags_has_Product_Product1_idx` (`Product_ProductId`),
  KEY `fk_Tags_has_Product_Tags1_idx` (`Tags_TagId`),
  CONSTRAINT `fk_Tags_has_Product_Product1` FOREIGN KEY (`Product_ProductId`) REFERENCES `product` (`ProductId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Tags_has_Product_Tags1` FOREIGN KEY (`Tags_TagId`) REFERENCES `tags` (`TagId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_has_tags`
--

LOCK TABLES `product_has_tags` WRITE;
/*!40000 ALTER TABLE `product_has_tags` DISABLE KEYS */;
INSERT INTO `product_has_tags` VALUES (1,1),(3,1),(1,2),(3,2),(4,3);
/*!40000 ALTER TABLE `product_has_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store`
--

DROP TABLE IF EXISTS `store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `store` (
  `StoreId` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  `Url` varchar(45) DEFAULT NULL,
  `Logo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`StoreId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store`
--

LOCK TABLES `store` WRITE;
/*!40000 ALTER TABLE `store` DISABLE KEYS */;
INSERT INTO `store` VALUES (1,'Media Markt','www.mediamarkt.gr','ph250x250.png');
/*!40000 ALTER TABLE `store` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store_has_product`
--

DROP TABLE IF EXISTS `store_has_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `store_has_product` (
  `Store_StoreId` int(11) NOT NULL,
  `Product_ProductId` int(11) NOT NULL,
  `price` int(11) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `available_quantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`Store_StoreId`,`Product_ProductId`),
  KEY `fk_Store_has_Product_Product1_idx` (`Product_ProductId`),
  KEY `fk_Store_has_Product_Store1_idx` (`Store_StoreId`),
  CONSTRAINT `fk_Store_has_Product_Product1` FOREIGN KEY (`Product_ProductId`) REFERENCES `product` (`ProductId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Store_has_Product_Store1` FOREIGN KEY (`Store_StoreId`) REFERENCES `store` (`StoreId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store_has_product`
--

LOCK TABLES `store_has_product` WRITE;
/*!40000 ALTER TABLE `store_has_product` DISABLE KEYS */;
INSERT INTO `store_has_product` VALUES (1,1,10,'Lorem Ipsum',5),(1,2,20,'dolor sit amet',8);
/*!40000 ALTER TABLE `store_has_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tags`
--

DROP TABLE IF EXISTS `tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tags` (
  `TagId` int(11) NOT NULL AUTO_INCREMENT,
  `TagName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`TagId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tags`
--

LOCK TABLES `tags` WRITE;
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
INSERT INTO `tags` VALUES (1,'Phone'),(2,'Computer'),(3,'Technology'),(4,'Book');
/*!40000 ALTER TABLE `tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `UserId` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`UserId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1),(2),(3);
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

-- Dump completed on 2017-10-15  3:16:57
