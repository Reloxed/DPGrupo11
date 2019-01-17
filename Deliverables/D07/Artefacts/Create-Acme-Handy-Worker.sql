start transaction;

create database `Acme-Handy-Worker`;

create user 'acme-user'@'%' 
	identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';

create user 'acme-manager'@'%' 
	identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';

grant select, insert, update, delete 
	on `Acme-Handy-Worker`.* to 'acme-user'@'%';

grant select, insert, update, delete, create, drop, references, index, alter, 
        create temporary tables, lock tables, create view, create routine, 
        alter routine, execute, trigger, show view
    on `Acme-Handy-Worker`.* to 'acme-manager'@'%';

-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: Acme-Handy-Worker
-- ------------------------------------------------------
-- Server version	5.5.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;

commit;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `actor_messagebox`
--

DROP TABLE IF EXISTS `actor_messagebox`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor_messagebox` (
  `Actor_id` int(11) NOT NULL,
  `messageBoxes_id` int(11) NOT NULL,
  UNIQUE KEY `UK_m9y27myodm745igcqhgymcb7e` (`messageBoxes_id`),
  CONSTRAINT `FK_m9y27myodm745igcqhgymcb7e` FOREIGN KEY (`messageBoxes_id`) REFERENCES `messagebox` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor_messagebox`
--

LOCK TABLES `actor_messagebox` WRITE;
/*!40000 ALTER TABLE `actor_messagebox` DISABLE KEYS */;
INSERT INTO `actor_messagebox` VALUES (32,35),(32,36),(32,37),(32,38);
/*!40000 ALTER TABLE `actor_messagebox` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actor_socialprofile`
--

DROP TABLE IF EXISTS `actor_socialprofile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor_socialprofile` (
  `Actor_id` int(11) NOT NULL,
  `socialProfiles_id` int(11) NOT NULL,
  UNIQUE KEY `UK_3nxm8g17fhfj1lmup7t2jimod` (`socialProfiles_id`),
  CONSTRAINT `FK_3nxm8g17fhfj1lmup7t2jimod` FOREIGN KEY (`socialProfiles_id`) REFERENCES `socialprofile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor_socialprofile`
--

LOCK TABLES `actor_socialprofile` WRITE;
/*!40000 ALTER TABLE `actor_socialprofile` DISABLE KEYS */;
/*!40000 ALTER TABLE `actor_socialprofile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `isSuspicious` bit(1) NOT NULL,
  `middleName` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_idt4b4u259p6vs4pyr9lax4eg` (`userAccount_id`),
  CONSTRAINT `FK_idt4b4u259p6vs4pyr9lax4eg` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (32,0,NULL,'lucapabat@mail.com','\0',NULL,'Lucía',NULL,NULL,'Aparicio Bataller',33);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `customerComment` varchar(255) DEFAULT NULL,
  `handyWorkerComment` varchar(255) DEFAULT NULL,
  `offeredPrice` double NOT NULL,
  `registeredMoment` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `applicant_id` int(11) NOT NULL,
  `creditCard_id` int(11) DEFAULT NULL,
  `fixUpTask_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_5ho5khuj5b6xfls5l7h5969xo` (`applicant_id`),
  KEY `FK_itesolowt3qc6vnn6e7gf6e33` (`creditCard_id`),
  KEY `FK_oj0mit9g97gndas4sv85440o9` (`fixUpTask_id`),
  CONSTRAINT `FK_oj0mit9g97gndas4sv85440o9` FOREIGN KEY (`fixUpTask_id`) REFERENCES `fixuptask` (`id`),
  CONSTRAINT `FK_5ho5khuj5b6xfls5l7h5969xo` FOREIGN KEY (`applicant_id`) REFERENCES `handyworker` (`id`),
  CONSTRAINT `FK_itesolowt3qc6vnn6e7gf6e33` FOREIGN KEY (`creditCard_id`) REFERENCES `creditcard` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
/*!40000 ALTER TABLE `application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `parentCategory_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_3v34vcvwua46xp9jd0bj7rk78` (`parentCategory_id`),
  CONSTRAINT `FK_3v34vcvwua46xp9jd0bj7rk78` FOREIGN KEY (`parentCategory_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (39,0,NULL),(40,0,39),(41,0,39),(42,0,39),(43,0,40),(44,0,41),(45,0,41),(46,0,41),(47,0,41),(48,0,41),(49,0,40),(50,0,41),(51,0,41),(52,0,40),(53,0,41),(54,0,41),(55,0,41),(56,0,40),(57,0,41),(58,0,41),(59,0,41),(60,0,41),(61,0,40),(62,0,40);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category_name`
--

DROP TABLE IF EXISTS `category_name`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category_name` (
  `Category_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `name_KEY` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`Category_id`,`name_KEY`),
  CONSTRAINT `FK_gfpr8qik7nus975fg98hte8q5` FOREIGN KEY (`Category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_name`
--

LOCK TABLES `category_name` WRITE;
/*!40000 ALTER TABLE `category_name` DISABLE KEYS */;
INSERT INTO `category_name` VALUES (39,'CATEGORY','English'),(39,'CATEGORIA','Español'),(40,'Repairs','English'),(40,'Reparaciones','Español'),(41,'Housing','English'),(41,'Hogar','Español'),(42,'Carpentry','English'),(42,'Carpintería','Español'),(43,'Ceiling repair','English'),(43,'Reparación de techo','Español'),(44,'Cleaning','English'),(44,'Limpieza','Español'),(45,'Repairs','English'),(45,'Trabajos especiales','Español'),(46,'Doors','English'),(46,'Puertas','Español'),(47,'Electrical wiring','English'),(47,'Cableado eléctrico','Español'),(48,'Fan installation','English'),(48,'Instalación de aire acondicionado','Español'),(49,'Fence fixing','English'),(49,'Reparación de vallas','Español'),(50,'Home security systems','English'),(50,'Equipo de seguridad en casas','Español'),(51,'Insulation installation','English'),(51,'Aislamiento térmico','Español'),(52,'Lamp repairs','English'),(52,'Reparación de lámparas','Español'),(53,'Moving','English'),(53,'Mudanzas','Español'),(54,'Painting','English'),(54,'Pintura','Español'),(55,'Pest control','English'),(55,'Control de plagas','Español'),(56,'Plumbing repairs','English'),(56,'Fontanería','Español'),(57,'Roofing','English'),(57,'Tejados','Español'),(58,'Shelf installation','English'),(58,'Instalación de estanterías','Español'),(59,'Solar panels','English'),(59,'Paneles solares','Español'),(60,'Soundproofing','English'),(60,'Insonorización','Español'),(61,'Sprinkler repair','English'),(61,'Reparación de riego','Español'),(62,'Window repair','English'),(62,'Reparación de ventanas','Español');
/*!40000 ALTER TABLE `category_name` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `complaint`
--

DROP TABLE IF EXISTS `complaint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `complaint` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `attachments` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `fixUpTask_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_as3xbajj29f648jcbwwn56e0` (`ticker`),
  KEY `FK_kbwu2es6kianvlgoffiqa639v` (`fixUpTask_id`),
  CONSTRAINT `FK_kbwu2es6kianvlgoffiqa639v` FOREIGN KEY (`fixUpTask_id`) REFERENCES `fixuptask` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complaint`
--

LOCK TABLES `complaint` WRITE;
/*!40000 ALTER TABLE `complaint` DISABLE KEYS */;
/*!40000 ALTER TABLE `complaint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `creditcard`
--

DROP TABLE IF EXISTS `creditcard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `creditcard` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `CVV` int(11) NOT NULL,
  `brandName` varchar(255) DEFAULT NULL,
  `expirationMonth` int(11) NOT NULL,
  `expirationYear` int(11) NOT NULL,
  `holderName` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `creditcard`
--

LOCK TABLES `creditcard` WRITE;
/*!40000 ALTER TABLE `creditcard` DISABLE KEYS */;
/*!40000 ALTER TABLE `creditcard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum`
--

DROP TABLE IF EXISTS `curriculum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `personalRecord_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_mabrm092fo1ae2t2m5a3ww4en` (`personalRecord_id`),
  UNIQUE KEY `UK_cefwvqc4ixh7s51wfd1yi77yg` (`ticker`),
  CONSTRAINT `FK_mabrm092fo1ae2t2m5a3ww4en` FOREIGN KEY (`personalRecord_id`) REFERENCES `personalrecord` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum`
--

LOCK TABLES `curriculum` WRITE;
/*!40000 ALTER TABLE `curriculum` DISABLE KEYS */;
/*!40000 ALTER TABLE `curriculum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum_educationrecord`
--

DROP TABLE IF EXISTS `curriculum_educationrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum_educationrecord` (
  `Curriculum_id` int(11) NOT NULL,
  `educationRecords_id` int(11) NOT NULL,
  UNIQUE KEY `UK_pl4rfqren0agff76y04l3y611` (`educationRecords_id`),
  KEY `FK_oldens4o8aaajbroxyv11fo6g` (`Curriculum_id`),
  CONSTRAINT `FK_oldens4o8aaajbroxyv11fo6g` FOREIGN KEY (`Curriculum_id`) REFERENCES `curriculum` (`id`),
  CONSTRAINT `FK_pl4rfqren0agff76y04l3y611` FOREIGN KEY (`educationRecords_id`) REFERENCES `educationrecord` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum_educationrecord`
--

LOCK TABLES `curriculum_educationrecord` WRITE;
/*!40000 ALTER TABLE `curriculum_educationrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `curriculum_educationrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum_endorserrecord`
--

DROP TABLE IF EXISTS `curriculum_endorserrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum_endorserrecord` (
  `Curriculum_id` int(11) NOT NULL,
  `endorserRecords_id` int(11) NOT NULL,
  UNIQUE KEY `UK_bbelpqhyh6qocyclhn133ndyh` (`endorserRecords_id`),
  KEY `FK_o79rcsgjmwauctqvj9b7i4ukb` (`Curriculum_id`),
  CONSTRAINT `FK_o79rcsgjmwauctqvj9b7i4ukb` FOREIGN KEY (`Curriculum_id`) REFERENCES `curriculum` (`id`),
  CONSTRAINT `FK_bbelpqhyh6qocyclhn133ndyh` FOREIGN KEY (`endorserRecords_id`) REFERENCES `endorserrecord` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum_endorserrecord`
--

LOCK TABLES `curriculum_endorserrecord` WRITE;
/*!40000 ALTER TABLE `curriculum_endorserrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `curriculum_endorserrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum_miscellaneousrecord`
--

DROP TABLE IF EXISTS `curriculum_miscellaneousrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum_miscellaneousrecord` (
  `Curriculum_id` int(11) NOT NULL,
  `miscellaneousRecords_id` int(11) NOT NULL,
  UNIQUE KEY `UK_i87gspje3t3kah740fj2aup8` (`miscellaneousRecords_id`),
  KEY `FK_qq8tboyasceyejxb5gtnyhlbx` (`Curriculum_id`),
  CONSTRAINT `FK_qq8tboyasceyejxb5gtnyhlbx` FOREIGN KEY (`Curriculum_id`) REFERENCES `curriculum` (`id`),
  CONSTRAINT `FK_i87gspje3t3kah740fj2aup8` FOREIGN KEY (`miscellaneousRecords_id`) REFERENCES `miscellaneousrecord` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum_miscellaneousrecord`
--

LOCK TABLES `curriculum_miscellaneousrecord` WRITE;
/*!40000 ALTER TABLE `curriculum_miscellaneousrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `curriculum_miscellaneousrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum_professionalrecord`
--

DROP TABLE IF EXISTS `curriculum_professionalrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum_professionalrecord` (
  `Curriculum_id` int(11) NOT NULL,
  `professionalRecords_id` int(11) NOT NULL,
  UNIQUE KEY `UK_fmlxwyi8ktscvd4hflcg2xerj` (`professionalRecords_id`),
  KEY `FK_cxve92cmijecu4e9ev01pp3wc` (`Curriculum_id`),
  CONSTRAINT `FK_cxve92cmijecu4e9ev01pp3wc` FOREIGN KEY (`Curriculum_id`) REFERENCES `curriculum` (`id`),
  CONSTRAINT `FK_fmlxwyi8ktscvd4hflcg2xerj` FOREIGN KEY (`professionalRecords_id`) REFERENCES `professionalrecord` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum_professionalrecord`
--

LOCK TABLES `curriculum_professionalrecord` WRITE;
/*!40000 ALTER TABLE `curriculum_professionalrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `curriculum_professionalrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `isSuspicious` bit(1) NOT NULL,
  `middleName` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  `score` double NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pwmktpkay2yx7v00mrwmuscl8` (`userAccount_id`),
  CONSTRAINT `FK_pwmktpkay2yx7v00mrwmuscl8` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_complaint`
--

DROP TABLE IF EXISTS `customer_complaint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_complaint` (
  `Customer_id` int(11) NOT NULL,
  `complaints_id` int(11) NOT NULL,
  UNIQUE KEY `UK_5k44pusv1xovnmkbaktee04mp` (`complaints_id`),
  KEY `FK_mmhd2tljaplod9uirq2xo7dar` (`Customer_id`),
  CONSTRAINT `FK_mmhd2tljaplod9uirq2xo7dar` FOREIGN KEY (`Customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `FK_5k44pusv1xovnmkbaktee04mp` FOREIGN KEY (`complaints_id`) REFERENCES `complaint` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_complaint`
--

LOCK TABLES `customer_complaint` WRITE;
/*!40000 ALTER TABLE `customer_complaint` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_complaint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_creditcard`
--

DROP TABLE IF EXISTS `customer_creditcard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_creditcard` (
  `Customer_id` int(11) NOT NULL,
  `creditCards_id` int(11) NOT NULL,
  UNIQUE KEY `UK_1ccgohnrah7g665ndbl1vdqgh` (`creditCards_id`),
  KEY `FK_h2ciytevbkctxgx839stph85m` (`Customer_id`),
  CONSTRAINT `FK_h2ciytevbkctxgx839stph85m` FOREIGN KEY (`Customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `FK_1ccgohnrah7g665ndbl1vdqgh` FOREIGN KEY (`creditCards_id`) REFERENCES `creditcard` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_creditcard`
--

LOCK TABLES `customer_creditcard` WRITE;
/*!40000 ALTER TABLE `customer_creditcard` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_creditcard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_fixuptask`
--

DROP TABLE IF EXISTS `customer_fixuptask`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_fixuptask` (
  `Customer_id` int(11) NOT NULL,
  `fixUpTasks_id` int(11) NOT NULL,
  UNIQUE KEY `UK_s7fatjo56dst8mxd2wi9jkthn` (`fixUpTasks_id`),
  KEY `FK_8m2sdbjsvh5ph5hmc2x7g0lw9` (`Customer_id`),
  CONSTRAINT `FK_8m2sdbjsvh5ph5hmc2x7g0lw9` FOREIGN KEY (`Customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `FK_s7fatjo56dst8mxd2wi9jkthn` FOREIGN KEY (`fixUpTasks_id`) REFERENCES `fixuptask` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_fixuptask`
--

LOCK TABLES `customer_fixuptask` WRITE;
/*!40000 ALTER TABLE `customer_fixuptask` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_fixuptask` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `educationrecord`
--

DROP TABLE IF EXISTS `educationrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `educationrecord` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `diplomaTitle` varchar(255) DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `institutionName` varchar(255) DEFAULT NULL,
  `linkAttachment` varchar(255) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `educationrecord`
--

LOCK TABLES `educationrecord` WRITE;
/*!40000 ALTER TABLE `educationrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `educationrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `endorsement`
--

DROP TABLE IF EXISTS `endorsement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `endorsement` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `publishedMoment` datetime DEFAULT NULL,
  `recipient_id` int(11) NOT NULL,
  `sender_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `endorsement`
--

LOCK TABLES `endorsement` WRITE;
/*!40000 ALTER TABLE `endorsement` DISABLE KEYS */;
/*!40000 ALTER TABLE `endorsement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `endorserrecord`
--

DROP TABLE IF EXISTS `endorserrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `endorserrecord` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fullName` varchar(255) DEFAULT NULL,
  `linkedinLink` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `endorserrecord`
--

LOCK TABLES `endorserrecord` WRITE;
/*!40000 ALTER TABLE `endorserrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `endorserrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finder`
--

DROP TABLE IF EXISTS `finder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `endMoment` datetime DEFAULT NULL,
  `keyWord` varchar(255) DEFAULT NULL,
  `priceHigh` double NOT NULL,
  `priceLow` double NOT NULL,
  `searchMoment` datetime DEFAULT NULL,
  `startMoment` datetime DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `warranty_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_aooiblceyn0pqnbn26i3ayghg` (`category_id`),
  KEY `FK_smi4oov1mheg34xdf50g2uym4` (`warranty_id`),
  CONSTRAINT `FK_smi4oov1mheg34xdf50g2uym4` FOREIGN KEY (`warranty_id`) REFERENCES `warranty` (`id`),
  CONSTRAINT `FK_aooiblceyn0pqnbn26i3ayghg` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder`
--

LOCK TABLES `finder` WRITE;
/*!40000 ALTER TABLE `finder` DISABLE KEYS */;
/*!40000 ALTER TABLE `finder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finder_fixuptask`
--

DROP TABLE IF EXISTS `finder_fixuptask`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder_fixuptask` (
  `Finder_id` int(11) NOT NULL,
  `fixuptask_id` int(11) NOT NULL,
  UNIQUE KEY `UK_9qm743oni139k2yafum635uy5` (`fixuptask_id`),
  KEY `FK_jeo1c8r3c5qh8nllo828oiahm` (`Finder_id`),
  CONSTRAINT `FK_jeo1c8r3c5qh8nllo828oiahm` FOREIGN KEY (`Finder_id`) REFERENCES `finder` (`id`),
  CONSTRAINT `FK_9qm743oni139k2yafum635uy5` FOREIGN KEY (`fixuptask_id`) REFERENCES `fixuptask` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder_fixuptask`
--

LOCK TABLES `finder_fixuptask` WRITE;
/*!40000 ALTER TABLE `finder_fixuptask` DISABLE KEYS */;
/*!40000 ALTER TABLE `finder_fixuptask` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fixuptask`
--

DROP TABLE IF EXISTS `fixuptask`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fixuptask` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `endMoment` datetime DEFAULT NULL,
  `maxPrice` double NOT NULL,
  `publishedMoment` datetime DEFAULT NULL,
  `startMoment` datetime DEFAULT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `category_id` int(11) NOT NULL,
  `warranty_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_32fj3ui438c6jxq0h7kepk0vx` (`ticker`),
  KEY `FK_mjyofe6ljadoi67pl81608pss` (`category_id`),
  KEY `FK_m0terj4l027y6n3o035efh9cu` (`warranty_id`),
  CONSTRAINT `FK_m0terj4l027y6n3o035efh9cu` FOREIGN KEY (`warranty_id`) REFERENCES `warranty` (`id`),
  CONSTRAINT `FK_mjyofe6ljadoi67pl81608pss` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fixuptask`
--

LOCK TABLES `fixuptask` WRITE;
/*!40000 ALTER TABLE `fixuptask` DISABLE KEYS */;
/*!40000 ALTER TABLE `fixuptask` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `handyworker`
--

DROP TABLE IF EXISTS `handyworker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `handyworker` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `isSuspicious` bit(1) NOT NULL,
  `middleName` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  `make` varchar(255) DEFAULT NULL,
  `score` double NOT NULL,
  `curriculum_id` int(11) DEFAULT NULL,
  `finder_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_3khdsa3pi7r4vecmoiag2efmh` (`userAccount_id`),
  KEY `FK_n7684ly7qmnrqcravh0jqvaia` (`curriculum_id`),
  KEY `FK_690kquhis55nv3hqkv7ms5um8` (`finder_id`),
  CONSTRAINT `FK_3khdsa3pi7r4vecmoiag2efmh` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`),
  CONSTRAINT `FK_690kquhis55nv3hqkv7ms5um8` FOREIGN KEY (`finder_id`) REFERENCES `finder` (`id`),
  CONSTRAINT `FK_n7684ly7qmnrqcravh0jqvaia` FOREIGN KEY (`curriculum_id`) REFERENCES `curriculum` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `handyworker`
--

LOCK TABLES `handyworker` WRITE;
/*!40000 ALTER TABLE `handyworker` DISABLE KEYS */;
/*!40000 ALTER TABLE `handyworker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `handyworker_tutorial`
--

DROP TABLE IF EXISTS `handyworker_tutorial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `handyworker_tutorial` (
  `HandyWorker_id` int(11) NOT NULL,
  `tutorial_id` int(11) NOT NULL,
  UNIQUE KEY `UK_mw7u5eq3q6tw7w7w2ljhfaxoo` (`tutorial_id`),
  KEY `FK_latkqinsla47w2xtr5inp8t6a` (`HandyWorker_id`),
  CONSTRAINT `FK_latkqinsla47w2xtr5inp8t6a` FOREIGN KEY (`HandyWorker_id`) REFERENCES `handyworker` (`id`),
  CONSTRAINT `FK_mw7u5eq3q6tw7w7w2ljhfaxoo` FOREIGN KEY (`tutorial_id`) REFERENCES `tutorial` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `handyworker_tutorial`
--

LOCK TABLES `handyworker_tutorial` WRITE;
/*!40000 ALTER TABLE `handyworker_tutorial` DISABLE KEYS */;
/*!40000 ALTER TABLE `handyworker_tutorial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('DomainEntity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `isSpam` bit(1) NOT NULL,
  `priority` varchar(255) DEFAULT NULL,
  `sendMoment` datetime DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `sender_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_actor`
--

DROP TABLE IF EXISTS `message_actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_actor` (
  `Message_id` int(11) NOT NULL,
  `recipients_id` int(11) NOT NULL,
  KEY `FK_2340xdahcha0b5cyr6bxhq6ji` (`Message_id`),
  CONSTRAINT `FK_2340xdahcha0b5cyr6bxhq6ji` FOREIGN KEY (`Message_id`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_actor`
--

LOCK TABLES `message_actor` WRITE;
/*!40000 ALTER TABLE `message_actor` DISABLE KEYS */;
/*!40000 ALTER TABLE `message_actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messagebox`
--

DROP TABLE IF EXISTS `messagebox`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messagebox` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `isPredefined` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messagebox`
--

LOCK TABLES `messagebox` WRITE;
/*!40000 ALTER TABLE `messagebox` DISABLE KEYS */;
INSERT INTO `messagebox` VALUES (35,0,'','In box'),(36,0,'','Out box'),(37,0,'','Trash box'),(38,0,'','Spam box');
/*!40000 ALTER TABLE `messagebox` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messagebox_message`
--

DROP TABLE IF EXISTS `messagebox_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messagebox_message` (
  `messageBoxes_id` int(11) NOT NULL,
  `messages_id` int(11) NOT NULL,
  KEY `FK_mmqfoc2tgep7vo47d19toa8fx` (`messages_id`),
  KEY `FK_nbo3iq7g3yfeq3bkdscuy6rkq` (`messageBoxes_id`),
  CONSTRAINT `FK_nbo3iq7g3yfeq3bkdscuy6rkq` FOREIGN KEY (`messageBoxes_id`) REFERENCES `messagebox` (`id`),
  CONSTRAINT `FK_mmqfoc2tgep7vo47d19toa8fx` FOREIGN KEY (`messages_id`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messagebox_message`
--

LOCK TABLES `messagebox_message` WRITE;
/*!40000 ALTER TABLE `messagebox_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `messagebox_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `miscellaneousrecord`
--

DROP TABLE IF EXISTS `miscellaneousrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `miscellaneousrecord` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `linkAttachment` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `miscellaneousrecord`
--

LOCK TABLES `miscellaneousrecord` WRITE;
/*!40000 ALTER TABLE `miscellaneousrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `miscellaneousrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `note`
--

DROP TABLE IF EXISTS `note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `note` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `customerComment` varchar(255) DEFAULT NULL,
  `handyWorkerComment` varchar(255) DEFAULT NULL,
  `publishedMoment` datetime DEFAULT NULL,
  `refereeComment` varchar(255) DEFAULT NULL,
  `report_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_m4g5f19ppjtmti2jhajkmeuia` (`report_id`),
  CONSTRAINT `FK_m4g5f19ppjtmti2jhajkmeuia` FOREIGN KEY (`report_id`) REFERENCES `report` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `note`
--

LOCK TABLES `note` WRITE;
/*!40000 ALTER TABLE `note` DISABLE KEYS */;
/*!40000 ALTER TABLE `note` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personalrecord`
--

DROP TABLE IF EXISTS `personalrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personalrecord` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fullName` varchar(255) DEFAULT NULL,
  `linkedinLink` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personalrecord`
--

LOCK TABLES `personalrecord` WRITE;
/*!40000 ALTER TABLE `personalrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `personalrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phase`
--

DROP TABLE IF EXISTS `phase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phase` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `endMoment` datetime DEFAULT NULL,
  `startMoment` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `fixUpTask_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ftobe580303iyg1p5psrowdyk` (`fixUpTask_id`),
  CONSTRAINT `FK_ftobe580303iyg1p5psrowdyk` FOREIGN KEY (`fixUpTask_id`) REFERENCES `fixuptask` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phase`
--

LOCK TABLES `phase` WRITE;
/*!40000 ALTER TABLE `phase` DISABLE KEYS */;
/*!40000 ALTER TABLE `phase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `professionalrecord`
--

DROP TABLE IF EXISTS `professionalrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `professionalrecord` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `attachment` varchar(255) DEFAULT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `companyName` varchar(255) DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professionalrecord`
--

LOCK TABLES `professionalrecord` WRITE;
/*!40000 ALTER TABLE `professionalrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `professionalrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `referee`
--

DROP TABLE IF EXISTS `referee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `referee` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `isSuspicious` bit(1) NOT NULL,
  `middleName` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_poaxqb6i26ly4p9nkegehjagr` (`userAccount_id`),
  CONSTRAINT `FK_poaxqb6i26ly4p9nkegehjagr` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `referee`
--

LOCK TABLES `referee` WRITE;
/*!40000 ALTER TABLE `referee` DISABLE KEYS */;
/*!40000 ALTER TABLE `referee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `referee_complaint`
--

DROP TABLE IF EXISTS `referee_complaint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `referee_complaint` (
  `Referee_id` int(11) NOT NULL,
  `complaints_id` int(11) NOT NULL,
  UNIQUE KEY `UK_qfhqs4bc6aqejd3ugrgd0ptba` (`complaints_id`),
  KEY `FK_gumdsl0oadwpovx0f4h1q3exi` (`Referee_id`),
  CONSTRAINT `FK_gumdsl0oadwpovx0f4h1q3exi` FOREIGN KEY (`Referee_id`) REFERENCES `referee` (`id`),
  CONSTRAINT `FK_qfhqs4bc6aqejd3ugrgd0ptba` FOREIGN KEY (`complaints_id`) REFERENCES `complaint` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `referee_complaint`
--

LOCK TABLES `referee_complaint` WRITE;
/*!40000 ALTER TABLE `referee_complaint` DISABLE KEYS */;
/*!40000 ALTER TABLE `referee_complaint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `attachments` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `isFinal` bit(1) NOT NULL,
  `publishedMoment` datetime DEFAULT NULL,
  `complaint_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_n0ca18b8tvyjjx2fli6m6julc` (`complaint_id`),
  CONSTRAINT `FK_n0ca18b8tvyjjx2fli6m6julc` FOREIGN KEY (`complaint_id`) REFERENCES `complaint` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report_note`
--

DROP TABLE IF EXISTS `report_note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report_note` (
  `Report_id` int(11) NOT NULL,
  `notes_id` int(11) NOT NULL,
  UNIQUE KEY `UK_otu17kmub3od3a6r2u2tu5rgj` (`notes_id`),
  KEY `FK_dtwbmetcd3yc4vsqnt8lxjsnp` (`Report_id`),
  CONSTRAINT `FK_dtwbmetcd3yc4vsqnt8lxjsnp` FOREIGN KEY (`Report_id`) REFERENCES `report` (`id`),
  CONSTRAINT `FK_otu17kmub3od3a6r2u2tu5rgj` FOREIGN KEY (`notes_id`) REFERENCES `note` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report_note`
--

LOCK TABLES `report_note` WRITE;
/*!40000 ALTER TABLE `report_note` DISABLE KEYS */;
/*!40000 ALTER TABLE `report_note` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `section`
--

DROP TABLE IF EXISTS `section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `section` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `number` int(11) NOT NULL,
  `pictures` varchar(255) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `section`
--

LOCK TABLES `section` WRITE;
/*!40000 ALTER TABLE `section` DISABLE KEYS */;
/*!40000 ALTER TABLE `section` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `socialprofile`
--

DROP TABLE IF EXISTS `socialprofile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `socialprofile` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `link` varchar(255) DEFAULT NULL,
  `nick` varchar(255) DEFAULT NULL,
  `socialNetwork` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `socialprofile`
--

LOCK TABLES `socialprofile` WRITE;
/*!40000 ALTER TABLE `socialprofile` DISABLE KEYS */;
/*!40000 ALTER TABLE `socialprofile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sponsor`
--

DROP TABLE IF EXISTS `sponsor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sponsor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `isSuspicious` bit(1) NOT NULL,
  `middleName` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_okfx8h0cn4eidh8ng563vowjc` (`userAccount_id`),
  CONSTRAINT `FK_okfx8h0cn4eidh8ng563vowjc` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sponsor`
--

LOCK TABLES `sponsor` WRITE;
/*!40000 ALTER TABLE `sponsor` DISABLE KEYS */;
/*!40000 ALTER TABLE `sponsor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sponsor_creditcard`
--

DROP TABLE IF EXISTS `sponsor_creditcard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sponsor_creditcard` (
  `Sponsor_id` int(11) NOT NULL,
  `creditCards_id` int(11) NOT NULL,
  UNIQUE KEY `UK_lj7g2v3y8octrcfp617mo02uc` (`creditCards_id`),
  KEY `FK_r9xk14d49xmq2a0nik3h2ck2d` (`Sponsor_id`),
  CONSTRAINT `FK_r9xk14d49xmq2a0nik3h2ck2d` FOREIGN KEY (`Sponsor_id`) REFERENCES `sponsor` (`id`),
  CONSTRAINT `FK_lj7g2v3y8octrcfp617mo02uc` FOREIGN KEY (`creditCards_id`) REFERENCES `creditcard` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sponsor_creditcard`
--

LOCK TABLES `sponsor_creditcard` WRITE;
/*!40000 ALTER TABLE `sponsor_creditcard` DISABLE KEYS */;
/*!40000 ALTER TABLE `sponsor_creditcard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sponsorship`
--

DROP TABLE IF EXISTS `sponsorship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sponsorship` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `targetPage` varchar(255) DEFAULT NULL,
  `creditCard_id` int(11) NOT NULL,
  `sponsor_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_pp2g6qyk49qojvpry77wsm8b3` (`creditCard_id`),
  KEY `FK_e3idyfyffpufog3sjl3c2ulun` (`sponsor_id`),
  CONSTRAINT `FK_e3idyfyffpufog3sjl3c2ulun` FOREIGN KEY (`sponsor_id`) REFERENCES `sponsor` (`id`),
  CONSTRAINT `FK_pp2g6qyk49qojvpry77wsm8b3` FOREIGN KEY (`creditCard_id`) REFERENCES `creditcard` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sponsorship`
--

LOCK TABLES `sponsorship` WRITE;
/*!40000 ALTER TABLE `sponsorship` DISABLE KEYS */;
/*!40000 ALTER TABLE `sponsorship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `systemconfiguration`
--

DROP TABLE IF EXISTS `systemconfiguration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `systemconfiguration` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `VAT` double NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `countryCode` varchar(255) DEFAULT NULL,
  `listCreditCardMakes` varchar(255) DEFAULT NULL,
  `maxResults` int(11) NOT NULL,
  `negativeWords` varchar(255) DEFAULT NULL,
  `positiveWords` varchar(255) DEFAULT NULL,
  `spamWords` varchar(255) DEFAULT NULL,
  `systemName` varchar(255) DEFAULT NULL,
  `timeResultsCached` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `systemconfiguration`
--

LOCK TABLES `systemconfiguration` WRITE;
/*!40000 ALTER TABLE `systemconfiguration` DISABLE KEYS */;
INSERT INTO `systemconfiguration` VALUES (34,0,0.21,'https://irp-cdn.multiscreensite.com/3737b2b6/dms3rep/multi/desktop/4-2000x889.jpg','+034','VISA,MASTER,DINNERS,AMEX',10,'bad,horrendous,horrible,disgusting,malo,horrendo,horroroso','fantastic,awesome,good,amusing,amazing,fantastico,fantástico,genial,bueno,increible,increíble,divertido','sex,viagra,cialis,one million,you\'ve been selected,nigeria,sexo,un millón,un millon,has sido seleccionado','Acme Handy Worker',1);
/*!40000 ALTER TABLE `systemconfiguration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `systemconfiguration_welcomemessage`
--

DROP TABLE IF EXISTS `systemconfiguration_welcomemessage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `systemconfiguration_welcomemessage` (
  `SystemConfiguration_id` int(11) NOT NULL,
  `welcomeMessage` varchar(255) DEFAULT NULL,
  `welcomeMessage_KEY` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`SystemConfiguration_id`,`welcomeMessage_KEY`),
  CONSTRAINT `FK_jwdyxms6e36fcd321e0qlyhb` FOREIGN KEY (`SystemConfiguration_id`) REFERENCES `systemconfiguration` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `systemconfiguration_welcomemessage`
--

LOCK TABLES `systemconfiguration_welcomemessage` WRITE;
/*!40000 ALTER TABLE `systemconfiguration_welcomemessage` DISABLE KEYS */;
INSERT INTO `systemconfiguration_welcomemessage` VALUES (34,'Welcome to Acme-HandyWorker! price,quality,and trust in a single place','English'),(34,'¡Bienvenidos a Acme-HandyWorker! precio,calidad y confianza en el mismo sitio','Español');
/*!40000 ALTER TABLE `systemconfiguration_welcomemessage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutorial`
--

DROP TABLE IF EXISTS `tutorial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tutorial` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  `pictures` varchar(255) DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutorial`
--

LOCK TABLES `tutorial` WRITE;
/*!40000 ALTER TABLE `tutorial` DISABLE KEYS */;
/*!40000 ALTER TABLE `tutorial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutorial_section`
--

DROP TABLE IF EXISTS `tutorial_section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tutorial_section` (
  `Tutorial_id` int(11) NOT NULL,
  `sections_id` int(11) NOT NULL,
  UNIQUE KEY `UK_2n8kv2i696vifunnuwcel2fy3` (`sections_id`),
  KEY `FK_bpjd4gwvm5i0c9cd5yc3cdgxm` (`Tutorial_id`),
  CONSTRAINT `FK_bpjd4gwvm5i0c9cd5yc3cdgxm` FOREIGN KEY (`Tutorial_id`) REFERENCES `tutorial` (`id`),
  CONSTRAINT `FK_2n8kv2i696vifunnuwcel2fy3` FOREIGN KEY (`sections_id`) REFERENCES `section` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutorial_section`
--

LOCK TABLES `tutorial_section` WRITE;
/*!40000 ALTER TABLE `tutorial_section` DISABLE KEYS */;
/*!40000 ALTER TABLE `tutorial_section` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutorial_sponsorship`
--

DROP TABLE IF EXISTS `tutorial_sponsorship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tutorial_sponsorship` (
  `Tutorial_id` int(11) NOT NULL,
  `sponsorships_id` int(11) NOT NULL,
  KEY `FK_ct34qpnq4ydhysfhilw1dx7e0` (`sponsorships_id`),
  KEY `FK_119m2xl7srvx241nvexhfftwt` (`Tutorial_id`),
  CONSTRAINT `FK_119m2xl7srvx241nvexhfftwt` FOREIGN KEY (`Tutorial_id`) REFERENCES `tutorial` (`id`),
  CONSTRAINT `FK_ct34qpnq4ydhysfhilw1dx7e0` FOREIGN KEY (`sponsorships_id`) REFERENCES `sponsorship` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutorial_sponsorship`
--

LOCK TABLES `tutorial_sponsorship` WRITE;
/*!40000 ALTER TABLE `tutorial_sponsorship` DISABLE KEYS */;
/*!40000 ALTER TABLE `tutorial_sponsorship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount`
--

DROP TABLE IF EXISTS `useraccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `isBanned` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_csivo9yqa08nrbkog71ycilh5` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount`
--

LOCK TABLES `useraccount` WRITE;
/*!40000 ALTER TABLE `useraccount` DISABLE KEYS */;
INSERT INTO `useraccount` VALUES (33,0,'\0','21232f297a57a5a743894a0e4a801fc3','admin');
/*!40000 ALTER TABLE `useraccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount_authorities`
--

DROP TABLE IF EXISTS `useraccount_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount_authorities` (
  `UserAccount_id` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_b63ua47r0u1m7ccc9lte2ui4r` (`UserAccount_id`),
  CONSTRAINT `FK_b63ua47r0u1m7ccc9lte2ui4r` FOREIGN KEY (`UserAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount_authorities`
--

LOCK TABLES `useraccount_authorities` WRITE;
/*!40000 ALTER TABLE `useraccount_authorities` DISABLE KEYS */;
INSERT INTO `useraccount_authorities` VALUES (33,'ADMINISTRATOR');
/*!40000 ALTER TABLE `useraccount_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warranty`
--

DROP TABLE IF EXISTS `warranty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `warranty` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `isFinal` bit(1) NOT NULL,
  `laws` varchar(255) DEFAULT NULL,
  `terms` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warranty`
--

LOCK TABLES `warranty` WRITE;
/*!40000 ALTER TABLE `warranty` DISABLE KEYS */;
/*!40000 ALTER TABLE `warranty` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-17  0:28:11
