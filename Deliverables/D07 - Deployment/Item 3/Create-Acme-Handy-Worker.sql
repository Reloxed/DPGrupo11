start transaction;

drop database if exists `Acme-Handy-Worker`;
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
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `actor_message_boxes`
--

DROP TABLE IF EXISTS `actor_message_boxes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor_message_boxes` (
  `actor` int(11) NOT NULL,
  `message_boxes` int(11) NOT NULL,
  UNIQUE KEY `UK_gsokk7rk6i6vd87e6dvdxfapu` (`message_boxes`),
  CONSTRAINT `FK_gsokk7rk6i6vd87e6dvdxfapu` FOREIGN KEY (`message_boxes`) REFERENCES `message_box` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor_message_boxes`
--

LOCK TABLES `actor_message_boxes` WRITE;
/*!40000 ALTER TABLE `actor_message_boxes` DISABLE KEYS */;
INSERT INTO `actor_message_boxes` VALUES (32,35),(32,36),(32,37),(32,38);
/*!40000 ALTER TABLE `actor_message_boxes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actor_social_profiles`
--

DROP TABLE IF EXISTS `actor_social_profiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor_social_profiles` (
  `actor` int(11) NOT NULL,
  `social_profiles` int(11) NOT NULL,
  UNIQUE KEY `UK_4suhrykpl9af1ubs85ycbyt6q` (`social_profiles`),
  CONSTRAINT `FK_4suhrykpl9af1ubs85ycbyt6q` FOREIGN KEY (`social_profiles`) REFERENCES `social_profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor_social_profiles`
--

LOCK TABLES `actor_social_profiles` WRITE;
/*!40000 ALTER TABLE `actor_social_profiles` DISABLE KEYS */;
/*!40000 ALTER TABLE `actor_social_profiles` ENABLE KEYS */;
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
  `is_suspicious` bit(1) NOT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_7ohwsa2usmvu0yxb44je2lge` (`user_account`),
  CONSTRAINT `FK_7ohwsa2usmvu0yxb44je2lge` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
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
  `customer_comment` varchar(255) DEFAULT NULL,
  `handy_worker_comment` varchar(255) DEFAULT NULL,
  `offered_price` double NOT NULL,
  `registered_moment` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `applicant` int(11) NOT NULL,
  `credit_card` int(11) DEFAULT NULL,
  `fix_up_task` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_b9pgpk7ukytb61k2nhmw4y8a5` (`applicant`),
  KEY `FK_qrtc8d7gr5htxb67vvyl92du7` (`credit_card`),
  KEY `FK_i544pbdabjdesit0c4afue6pl` (`fix_up_task`),
  CONSTRAINT `FK_i544pbdabjdesit0c4afue6pl` FOREIGN KEY (`fix_up_task`) REFERENCES `fix_up_task` (`id`),
  CONSTRAINT `FK_b9pgpk7ukytb61k2nhmw4y8a5` FOREIGN KEY (`applicant`) REFERENCES `handy_worker` (`id`),
  CONSTRAINT `FK_qrtc8d7gr5htxb67vvyl92du7` FOREIGN KEY (`credit_card`) REFERENCES `credit_card` (`id`)
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
  `parent_category` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_lhvwmxrdrjma5forcymeuolqn` (`parent_category`),
  CONSTRAINT `FK_lhvwmxrdrjma5forcymeuolqn` FOREIGN KEY (`parent_category`) REFERENCES `category` (`id`)
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
  `category` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `name_key` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`category`,`name_key`),
  CONSTRAINT `FK_n4pwmvmxwxd91xyngs4w968sq` FOREIGN KEY (`category`) REFERENCES `category` (`id`)
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
  `fix_up_task` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_jlpo668tu0b8mmsjsg8g13inu` (`ticker`),
  KEY `FK_2st2n707bxgd2pp9w7wkpptt0` (`fix_up_task`),
  CONSTRAINT `FK_2st2n707bxgd2pp9w7wkpptt0` FOREIGN KEY (`fix_up_task`) REFERENCES `fix_up_task` (`id`)
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
-- Table structure for table `credit_card`
--

DROP TABLE IF EXISTS `credit_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `credit_card` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `cvv` int(11) NOT NULL,
  `brand_name` varchar(255) DEFAULT NULL,
  `expiration_month` int(11) NOT NULL,
  `expiration_year` int(11) NOT NULL,
  `holder_name` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credit_card`
--

LOCK TABLES `credit_card` WRITE;
/*!40000 ALTER TABLE `credit_card` DISABLE KEYS */;
/*!40000 ALTER TABLE `credit_card` ENABLE KEYS */;
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
  `personal_record` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_of5n83aytom6e52721o6k6ae` (`personal_record`),
  UNIQUE KEY `UK_3ai7h3tynp97g8r0g93r84m8w` (`ticker`),
  CONSTRAINT `FK_of5n83aytom6e52721o6k6ae` FOREIGN KEY (`personal_record`) REFERENCES `personal_record` (`id`)
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
-- Table structure for table `curriculum_education_records`
--

DROP TABLE IF EXISTS `curriculum_education_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum_education_records` (
  `curriculum` int(11) NOT NULL,
  `education_records` int(11) NOT NULL,
  UNIQUE KEY `UK_mglbvuj28iptdf2n2wliy5dne` (`education_records`),
  KEY `FK_f6du7wj59ct8k4fkxo6p6avc` (`curriculum`),
  CONSTRAINT `FK_f6du7wj59ct8k4fkxo6p6avc` FOREIGN KEY (`curriculum`) REFERENCES `curriculum` (`id`),
  CONSTRAINT `FK_mglbvuj28iptdf2n2wliy5dne` FOREIGN KEY (`education_records`) REFERENCES `education_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum_education_records`
--

LOCK TABLES `curriculum_education_records` WRITE;
/*!40000 ALTER TABLE `curriculum_education_records` DISABLE KEYS */;
/*!40000 ALTER TABLE `curriculum_education_records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum_endorser_records`
--

DROP TABLE IF EXISTS `curriculum_endorser_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum_endorser_records` (
  `curriculum` int(11) NOT NULL,
  `endorser_records` int(11) NOT NULL,
  UNIQUE KEY `UK_pnjnunpdx94xdol4lxbq5aphh` (`endorser_records`),
  KEY `FK_lb4478p6kh3k7ps8fbdiqvds` (`curriculum`),
  CONSTRAINT `FK_lb4478p6kh3k7ps8fbdiqvds` FOREIGN KEY (`curriculum`) REFERENCES `curriculum` (`id`),
  CONSTRAINT `FK_pnjnunpdx94xdol4lxbq5aphh` FOREIGN KEY (`endorser_records`) REFERENCES `endorser_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum_endorser_records`
--

LOCK TABLES `curriculum_endorser_records` WRITE;
/*!40000 ALTER TABLE `curriculum_endorser_records` DISABLE KEYS */;
/*!40000 ALTER TABLE `curriculum_endorser_records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum_miscellaneous_records`
--

DROP TABLE IF EXISTS `curriculum_miscellaneous_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum_miscellaneous_records` (
  `curriculum` int(11) NOT NULL,
  `miscellaneous_records` int(11) NOT NULL,
  UNIQUE KEY `UK_hbex6yqhywe93w3clw8y1od2q` (`miscellaneous_records`),
  KEY `FK_fxsf5ohw20jbm0wuny6j1nnc9` (`curriculum`),
  CONSTRAINT `FK_fxsf5ohw20jbm0wuny6j1nnc9` FOREIGN KEY (`curriculum`) REFERENCES `curriculum` (`id`),
  CONSTRAINT `FK_hbex6yqhywe93w3clw8y1od2q` FOREIGN KEY (`miscellaneous_records`) REFERENCES `miscellaneous_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum_miscellaneous_records`
--

LOCK TABLES `curriculum_miscellaneous_records` WRITE;
/*!40000 ALTER TABLE `curriculum_miscellaneous_records` DISABLE KEYS */;
/*!40000 ALTER TABLE `curriculum_miscellaneous_records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum_professional_records`
--

DROP TABLE IF EXISTS `curriculum_professional_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum_professional_records` (
  `curriculum` int(11) NOT NULL,
  `professional_records` int(11) NOT NULL,
  UNIQUE KEY `UK_7354sjhp7ih49qku2slvgb166` (`professional_records`),
  KEY `FK_amicw5c3dbgqi4vafcy7wi02i` (`curriculum`),
  CONSTRAINT `FK_amicw5c3dbgqi4vafcy7wi02i` FOREIGN KEY (`curriculum`) REFERENCES `curriculum` (`id`),
  CONSTRAINT `FK_7354sjhp7ih49qku2slvgb166` FOREIGN KEY (`professional_records`) REFERENCES `professional_record` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum_professional_records`
--

LOCK TABLES `curriculum_professional_records` WRITE;
/*!40000 ALTER TABLE `curriculum_professional_records` DISABLE KEYS */;
/*!40000 ALTER TABLE `curriculum_professional_records` ENABLE KEYS */;
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
  `is_suspicious` bit(1) NOT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  `score` double NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_mbvdes9ypo1yu76so76owiyqx` (`user_account`),
  CONSTRAINT `FK_mbvdes9ypo1yu76so76owiyqx` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
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
-- Table structure for table `customer_complaints`
--

DROP TABLE IF EXISTS `customer_complaints`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_complaints` (
  `customer` int(11) NOT NULL,
  `complaints` int(11) NOT NULL,
  UNIQUE KEY `UK_qu0xxd86p5xxthj5ykhwj338m` (`complaints`),
  KEY `FK_k8yvskwtg79opmjrobefm9u4s` (`customer`),
  CONSTRAINT `FK_k8yvskwtg79opmjrobefm9u4s` FOREIGN KEY (`customer`) REFERENCES `customer` (`id`),
  CONSTRAINT `FK_qu0xxd86p5xxthj5ykhwj338m` FOREIGN KEY (`complaints`) REFERENCES `complaint` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_complaints`
--

LOCK TABLES `customer_complaints` WRITE;
/*!40000 ALTER TABLE `customer_complaints` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_complaints` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_credit_cards`
--

DROP TABLE IF EXISTS `customer_credit_cards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_credit_cards` (
  `customer` int(11) NOT NULL,
  `credit_cards` int(11) NOT NULL,
  UNIQUE KEY `UK_g7om03d1f4trqs9r1o9wgq3aa` (`credit_cards`),
  KEY `FK_ia41rxe6w6iafboij0725qfpp` (`customer`),
  CONSTRAINT `FK_ia41rxe6w6iafboij0725qfpp` FOREIGN KEY (`customer`) REFERENCES `customer` (`id`),
  CONSTRAINT `FK_g7om03d1f4trqs9r1o9wgq3aa` FOREIGN KEY (`credit_cards`) REFERENCES `credit_card` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_credit_cards`
--

LOCK TABLES `customer_credit_cards` WRITE;
/*!40000 ALTER TABLE `customer_credit_cards` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_credit_cards` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_fix_up_tasks`
--

DROP TABLE IF EXISTS `customer_fix_up_tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_fix_up_tasks` (
  `customer` int(11) NOT NULL,
  `fix_up_tasks` int(11) NOT NULL,
  UNIQUE KEY `UK_psqf2cbws8l6hrs05kkmo4t6f` (`fix_up_tasks`),
  KEY `FK_5p35y62n78kja3clklj2axyy3` (`customer`),
  CONSTRAINT `FK_5p35y62n78kja3clklj2axyy3` FOREIGN KEY (`customer`) REFERENCES `customer` (`id`),
  CONSTRAINT `FK_psqf2cbws8l6hrs05kkmo4t6f` FOREIGN KEY (`fix_up_tasks`) REFERENCES `fix_up_task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_fix_up_tasks`
--

LOCK TABLES `customer_fix_up_tasks` WRITE;
/*!40000 ALTER TABLE `customer_fix_up_tasks` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_fix_up_tasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `education_record`
--

DROP TABLE IF EXISTS `education_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `education_record` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `diploma_title` varchar(255) DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `institution_name` varchar(255) DEFAULT NULL,
  `link_attachment` varchar(255) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `education_record`
--

LOCK TABLES `education_record` WRITE;
/*!40000 ALTER TABLE `education_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `education_record` ENABLE KEYS */;
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
  `published_moment` datetime DEFAULT NULL,
  `recipient` int(11) NOT NULL,
  `sender` int(11) NOT NULL,
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
-- Table structure for table `endorser_record`
--

DROP TABLE IF EXISTS `endorser_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `endorser_record` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `linkedin_link` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `endorser_record`
--

LOCK TABLES `endorser_record` WRITE;
/*!40000 ALTER TABLE `endorser_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `endorser_record` ENABLE KEYS */;
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
  `end_moment` datetime DEFAULT NULL,
  `key_word` varchar(255) DEFAULT NULL,
  `price_high` double NOT NULL,
  `price_low` double NOT NULL,
  `search_moment` datetime DEFAULT NULL,
  `start_moment` datetime DEFAULT NULL,
  `category` int(11) DEFAULT NULL,
  `warranty` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_n9t1ayk0x7h5vrsfuhygo043j` (`category`),
  KEY `FK_fsgvely8c4othsty26jul4qfl` (`warranty`),
  CONSTRAINT `FK_fsgvely8c4othsty26jul4qfl` FOREIGN KEY (`warranty`) REFERENCES `warranty` (`id`),
  CONSTRAINT `FK_n9t1ayk0x7h5vrsfuhygo043j` FOREIGN KEY (`category`) REFERENCES `category` (`id`)
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
  `finder` int(11) NOT NULL,
  `fixuptask` int(11) NOT NULL,
  UNIQUE KEY `UK_26473j0dfpkedmaynxja438b4` (`fixuptask`),
  KEY `FK_9bwv1jeyprrgqvp3vgx33j99o` (`finder`),
  CONSTRAINT `FK_9bwv1jeyprrgqvp3vgx33j99o` FOREIGN KEY (`finder`) REFERENCES `finder` (`id`),
  CONSTRAINT `FK_26473j0dfpkedmaynxja438b4` FOREIGN KEY (`fixuptask`) REFERENCES `fix_up_task` (`id`)
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
-- Table structure for table `fix_up_task`
--

DROP TABLE IF EXISTS `fix_up_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fix_up_task` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_moment` datetime DEFAULT NULL,
  `max_price` double NOT NULL,
  `published_moment` datetime DEFAULT NULL,
  `start_moment` datetime DEFAULT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `category` int(11) NOT NULL,
  `warranty` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1ucy18fywvpk17lfcafstl8p` (`ticker`),
  KEY `FK_rraseqm4xixdwpi08ac3s0wo5` (`category`),
  KEY `FK_eeisx1c0ohidkpqgqbib91s6x` (`warranty`),
  CONSTRAINT `FK_eeisx1c0ohidkpqgqbib91s6x` FOREIGN KEY (`warranty`) REFERENCES `warranty` (`id`),
  CONSTRAINT `FK_rraseqm4xixdwpi08ac3s0wo5` FOREIGN KEY (`category`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fix_up_task`
--

LOCK TABLES `fix_up_task` WRITE;
/*!40000 ALTER TABLE `fix_up_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `fix_up_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `handy_worker`
--

DROP TABLE IF EXISTS `handy_worker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `handy_worker` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_suspicious` bit(1) NOT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  `make` varchar(255) DEFAULT NULL,
  `score` double NOT NULL,
  `curriculum` int(11) DEFAULT NULL,
  `finder` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_s80hn9dk7bcwsqotvtoo6wxr3` (`finder`),
  UNIQUE KEY `UK_jpa4nvxb706tgsd90160obc6r` (`user_account`),
  KEY `FK_qilb1l0o66abqy9o4fk8accvs` (`curriculum`),
  CONSTRAINT `FK_jpa4nvxb706tgsd90160obc6r` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_qilb1l0o66abqy9o4fk8accvs` FOREIGN KEY (`curriculum`) REFERENCES `curriculum` (`id`),
  CONSTRAINT `FK_s80hn9dk7bcwsqotvtoo6wxr3` FOREIGN KEY (`finder`) REFERENCES `finder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `handy_worker`
--

LOCK TABLES `handy_worker` WRITE;
/*!40000 ALTER TABLE `handy_worker` DISABLE KEYS */;
/*!40000 ALTER TABLE `handy_worker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `handy_worker_tutorial`
--

DROP TABLE IF EXISTS `handy_worker_tutorial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `handy_worker_tutorial` (
  `handy_worker` int(11) NOT NULL,
  `tutorial` int(11) NOT NULL,
  UNIQUE KEY `UK_qndmyssym6it2rsqopag92dq0` (`tutorial`),
  KEY `FK_ciubwtrjwekfpxdh50dqvx8lk` (`handy_worker`),
  CONSTRAINT `FK_ciubwtrjwekfpxdh50dqvx8lk` FOREIGN KEY (`handy_worker`) REFERENCES `handy_worker` (`id`),
  CONSTRAINT `FK_qndmyssym6it2rsqopag92dq0` FOREIGN KEY (`tutorial`) REFERENCES `tutorial` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `handy_worker_tutorial`
--

LOCK TABLES `handy_worker_tutorial` WRITE;
/*!40000 ALTER TABLE `handy_worker_tutorial` DISABLE KEYS */;
/*!40000 ALTER TABLE `handy_worker_tutorial` ENABLE KEYS */;
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
INSERT INTO `hibernate_sequences` VALUES ('domain_entity',1);
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
  `is_spam` bit(1) NOT NULL,
  `priority` varchar(255) DEFAULT NULL,
  `send_moment` datetime DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `recipient` int(11) NOT NULL,
  `sender` int(11) NOT NULL,
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
-- Table structure for table `message_box`
--

DROP TABLE IF EXISTS `message_box`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_box` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `is_predefined` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_box`
--

LOCK TABLES `message_box` WRITE;
/*!40000 ALTER TABLE `message_box` DISABLE KEYS */;
INSERT INTO `message_box` VALUES (35,0,'','In box'),(36,0,'','Out box'),(37,0,'','Trash box'),(38,0,'','Spam box');
/*!40000 ALTER TABLE `message_box` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_box_messages`
--

DROP TABLE IF EXISTS `message_box_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_box_messages` (
  `message_box` int(11) NOT NULL,
  `messages` int(11) NOT NULL,
  KEY `FK_4ydibw5107qpqjwmw37t3cx5c` (`messages`),
  KEY `FK_i9fsy1u5e0dyn977c4uhdupur` (`message_box`),
  CONSTRAINT `FK_i9fsy1u5e0dyn977c4uhdupur` FOREIGN KEY (`message_box`) REFERENCES `message_box` (`id`),
  CONSTRAINT `FK_4ydibw5107qpqjwmw37t3cx5c` FOREIGN KEY (`messages`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_box_messages`
--

LOCK TABLES `message_box_messages` WRITE;
/*!40000 ALTER TABLE `message_box_messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `message_box_messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_message_boxes`
--

DROP TABLE IF EXISTS `message_message_boxes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_message_boxes` (
  `message` int(11) NOT NULL,
  `message_boxes` int(11) NOT NULL,
  KEY `FK_9e3s75h2409iiuiugj5h00enq` (`message_boxes`),
  KEY `FK_pg69wrq7o02j8baa8d56f1x0q` (`message`),
  CONSTRAINT `FK_pg69wrq7o02j8baa8d56f1x0q` FOREIGN KEY (`message`) REFERENCES `message` (`id`),
  CONSTRAINT `FK_9e3s75h2409iiuiugj5h00enq` FOREIGN KEY (`message_boxes`) REFERENCES `message_box` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_message_boxes`
--

LOCK TABLES `message_message_boxes` WRITE;
/*!40000 ALTER TABLE `message_message_boxes` DISABLE KEYS */;
/*!40000 ALTER TABLE `message_message_boxes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `miscellaneous_record`
--

DROP TABLE IF EXISTS `miscellaneous_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `miscellaneous_record` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `link_attachment` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `miscellaneous_record`
--

LOCK TABLES `miscellaneous_record` WRITE;
/*!40000 ALTER TABLE `miscellaneous_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `miscellaneous_record` ENABLE KEYS */;
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
  `customer_comment` varchar(255) DEFAULT NULL,
  `handy_worker_comment` varchar(255) DEFAULT NULL,
  `published_moment` datetime DEFAULT NULL,
  `referee_comment` varchar(255) DEFAULT NULL,
  `report` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_jk2abb4f3s4qx304y1uwq59pi` (`report`),
  CONSTRAINT `FK_jk2abb4f3s4qx304y1uwq59pi` FOREIGN KEY (`report`) REFERENCES `report` (`id`)
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
-- Table structure for table `personal_record`
--

DROP TABLE IF EXISTS `personal_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personal_record` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `linkedin_link` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personal_record`
--

LOCK TABLES `personal_record` WRITE;
/*!40000 ALTER TABLE `personal_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `personal_record` ENABLE KEYS */;
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
  `end_moment` datetime DEFAULT NULL,
  `start_moment` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `fix_up_task` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_cu5vf3n12srsghjtjckiuysvk` (`fix_up_task`),
  CONSTRAINT `FK_cu5vf3n12srsghjtjckiuysvk` FOREIGN KEY (`fix_up_task`) REFERENCES `fix_up_task` (`id`)
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
-- Table structure for table `professional_record`
--

DROP TABLE IF EXISTS `professional_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `professional_record` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `attachment` varchar(255) DEFAULT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `professional_record`
--

LOCK TABLES `professional_record` WRITE;
/*!40000 ALTER TABLE `professional_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `professional_record` ENABLE KEYS */;
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
  `is_suspicious` bit(1) NOT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_303c1oipw0t6mbnnpvtfv70w5` (`user_account`),
  CONSTRAINT `FK_303c1oipw0t6mbnnpvtfv70w5` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
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
-- Table structure for table `referee_complaints`
--

DROP TABLE IF EXISTS `referee_complaints`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `referee_complaints` (
  `referee` int(11) NOT NULL,
  `complaints` int(11) NOT NULL,
  UNIQUE KEY `UK_d95lqe5qv8w8g4c39y84b5oiy` (`complaints`),
  KEY `FK_781160v3ukyqlgcxre4gsjwvn` (`referee`),
  CONSTRAINT `FK_781160v3ukyqlgcxre4gsjwvn` FOREIGN KEY (`referee`) REFERENCES `referee` (`id`),
  CONSTRAINT `FK_d95lqe5qv8w8g4c39y84b5oiy` FOREIGN KEY (`complaints`) REFERENCES `complaint` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `referee_complaints`
--

LOCK TABLES `referee_complaints` WRITE;
/*!40000 ALTER TABLE `referee_complaints` DISABLE KEYS */;
/*!40000 ALTER TABLE `referee_complaints` ENABLE KEYS */;
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
  `is_final` bit(1) NOT NULL,
  `published_moment` datetime DEFAULT NULL,
  `complaint` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_48rqaecflpcs8unotw4drrtfw` (`complaint`),
  CONSTRAINT `FK_48rqaecflpcs8unotw4drrtfw` FOREIGN KEY (`complaint`) REFERENCES `complaint` (`id`)
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
-- Table structure for table `report_notes`
--

DROP TABLE IF EXISTS `report_notes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report_notes` (
  `report` int(11) NOT NULL,
  `notes` int(11) NOT NULL,
  UNIQUE KEY `UK_m22isl38uqck3fp3rmsmbnh5k` (`notes`),
  KEY `FK_o7r37khw24dlpe07md1qqakce` (`report`),
  CONSTRAINT `FK_o7r37khw24dlpe07md1qqakce` FOREIGN KEY (`report`) REFERENCES `report` (`id`),
  CONSTRAINT `FK_m22isl38uqck3fp3rmsmbnh5k` FOREIGN KEY (`notes`) REFERENCES `note` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report_notes`
--

LOCK TABLES `report_notes` WRITE;
/*!40000 ALTER TABLE `report_notes` DISABLE KEYS */;
/*!40000 ALTER TABLE `report_notes` ENABLE KEYS */;
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
-- Table structure for table `social_profile`
--

DROP TABLE IF EXISTS `social_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `social_profile` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `link` varchar(255) DEFAULT NULL,
  `nick` varchar(255) DEFAULT NULL,
  `social_network` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `social_profile`
--

LOCK TABLES `social_profile` WRITE;
/*!40000 ALTER TABLE `social_profile` DISABLE KEYS */;
/*!40000 ALTER TABLE `social_profile` ENABLE KEYS */;
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
  `is_suspicious` bit(1) NOT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_du2w5ldt8rvlvxvtr7vyxk7g3` (`user_account`),
  CONSTRAINT `FK_du2w5ldt8rvlvxvtr7vyxk7g3` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
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
-- Table structure for table `sponsor_credit_cards`
--

DROP TABLE IF EXISTS `sponsor_credit_cards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sponsor_credit_cards` (
  `sponsor` int(11) NOT NULL,
  `credit_cards` int(11) NOT NULL,
  UNIQUE KEY `UK_h9b6u6a5m3u1ws54f9ugon66q` (`credit_cards`),
  KEY `FK_fe8fch9vkseoorfg8hk5rme35` (`sponsor`),
  CONSTRAINT `FK_fe8fch9vkseoorfg8hk5rme35` FOREIGN KEY (`sponsor`) REFERENCES `sponsor` (`id`),
  CONSTRAINT `FK_h9b6u6a5m3u1ws54f9ugon66q` FOREIGN KEY (`credit_cards`) REFERENCES `credit_card` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sponsor_credit_cards`
--

LOCK TABLES `sponsor_credit_cards` WRITE;
/*!40000 ALTER TABLE `sponsor_credit_cards` DISABLE KEYS */;
/*!40000 ALTER TABLE `sponsor_credit_cards` ENABLE KEYS */;
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
  `target_page` varchar(255) DEFAULT NULL,
  `credit_card` int(11) NOT NULL,
  `sponsor` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_b1c71pwhg9slb986j8kl7uul1` (`credit_card`),
  KEY `FK_huglhkud0ihqdljyou4eshra6` (`sponsor`),
  CONSTRAINT `FK_huglhkud0ihqdljyou4eshra6` FOREIGN KEY (`sponsor`) REFERENCES `sponsor` (`id`),
  CONSTRAINT `FK_b1c71pwhg9slb986j8kl7uul1` FOREIGN KEY (`credit_card`) REFERENCES `credit_card` (`id`)
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
-- Table structure for table `system_configuration`
--

DROP TABLE IF EXISTS `system_configuration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_configuration` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `vat` double NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `country_code` varchar(255) DEFAULT NULL,
  `list_credit_card_makes` varchar(255) DEFAULT NULL,
  `max_results` int(11) NOT NULL,
  `negative_words` varchar(255) DEFAULT NULL,
  `positive_words` varchar(255) DEFAULT NULL,
  `spam_words` varchar(255) DEFAULT NULL,
  `system_name` varchar(255) DEFAULT NULL,
  `time_results_cached` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_configuration`
--

LOCK TABLES `system_configuration` WRITE;
/*!40000 ALTER TABLE `system_configuration` DISABLE KEYS */;
INSERT INTO `system_configuration` VALUES (34,0,0.21,'https://irp-cdn.multiscreensite.com/3737b2b6/dms3rep/multi/desktop/4-2000x889.jpg','+034','VISA,MASTER,DINNERS,AMEX',10,'bad,horrendous,horrible,disgusting,malo,horrendo,horroroso','fantastic,awesome,good,amusing,amazing,fantastico,fantástico,genial,bueno,increible,increíble,divertido','sex,viagra,cialis,one million,you\'ve been selected,nigeria,sexo,un millón,un millon,has sido seleccionado','Acme Handy Worker',1);
/*!40000 ALTER TABLE `system_configuration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_configuration_welcome_message`
--

DROP TABLE IF EXISTS `system_configuration_welcome_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_configuration_welcome_message` (
  `system_configuration` int(11) NOT NULL,
  `welcome_message` varchar(255) DEFAULT NULL,
  `welcome_message_key` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`system_configuration`,`welcome_message_key`),
  CONSTRAINT `FK_afo2sg9l0wrwhjs6s7qjxwgjg` FOREIGN KEY (`system_configuration`) REFERENCES `system_configuration` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_configuration_welcome_message`
--

LOCK TABLES `system_configuration_welcome_message` WRITE;
/*!40000 ALTER TABLE `system_configuration_welcome_message` DISABLE KEYS */;
INSERT INTO `system_configuration_welcome_message` VALUES (34,'Welcome to Acme-HandyWorker! price,quality,and trust in a single place','English'),(34,'¡Bienvenidos a Acme-HandyWorker! precio,calidad y confianza en el mismo sitio','Español');
/*!40000 ALTER TABLE `system_configuration_welcome_message` ENABLE KEYS */;
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
  `last_update` datetime DEFAULT NULL,
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
-- Table structure for table `tutorial_sections`
--

DROP TABLE IF EXISTS `tutorial_sections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tutorial_sections` (
  `tutorial` int(11) NOT NULL,
  `sections` int(11) NOT NULL,
  UNIQUE KEY `UK_fam9vwtnrx0m7vmnqngwoekdo` (`sections`),
  KEY `FK_8sl8cpfc93exnk3nv9a6okamu` (`tutorial`),
  CONSTRAINT `FK_8sl8cpfc93exnk3nv9a6okamu` FOREIGN KEY (`tutorial`) REFERENCES `tutorial` (`id`),
  CONSTRAINT `FK_fam9vwtnrx0m7vmnqngwoekdo` FOREIGN KEY (`sections`) REFERENCES `section` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutorial_sections`
--

LOCK TABLES `tutorial_sections` WRITE;
/*!40000 ALTER TABLE `tutorial_sections` DISABLE KEYS */;
/*!40000 ALTER TABLE `tutorial_sections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutorial_sponsorships`
--

DROP TABLE IF EXISTS `tutorial_sponsorships`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tutorial_sponsorships` (
  `tutorial` int(11) NOT NULL,
  `sponsorships` int(11) NOT NULL,
  KEY `FK_ro1nou3bvjujnw7jrkgq2phy2` (`sponsorships`),
  KEY `FK_ja6vbw0v14lwf4fq40r7eluyq` (`tutorial`),
  CONSTRAINT `FK_ja6vbw0v14lwf4fq40r7eluyq` FOREIGN KEY (`tutorial`) REFERENCES `tutorial` (`id`),
  CONSTRAINT `FK_ro1nou3bvjujnw7jrkgq2phy2` FOREIGN KEY (`sponsorships`) REFERENCES `sponsorship` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutorial_sponsorships`
--

LOCK TABLES `tutorial_sponsorships` WRITE;
/*!40000 ALTER TABLE `tutorial_sponsorships` DISABLE KEYS */;
/*!40000 ALTER TABLE `tutorial_sponsorships` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account`
--

DROP TABLE IF EXISTS `user_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `is_banned` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_castjbvpeeus0r8lbpehiu0e4` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account`
--

LOCK TABLES `user_account` WRITE;
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
INSERT INTO `user_account` VALUES (33,0,'\0','21232f297a57a5a743894a0e4a801fc3','admin');
/*!40000 ALTER TABLE `user_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account_authorities`
--

DROP TABLE IF EXISTS `user_account_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account_authorities` (
  `user_account` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_pao8cwh93fpccb0bx6ilq6gsl` (`user_account`),
  CONSTRAINT `FK_pao8cwh93fpccb0bx6ilq6gsl` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account_authorities`
--

LOCK TABLES `user_account_authorities` WRITE;
/*!40000 ALTER TABLE `user_account_authorities` DISABLE KEYS */;
INSERT INTO `user_account_authorities` VALUES (33,'ADMINISTRATOR');
/*!40000 ALTER TABLE `user_account_authorities` ENABLE KEYS */;
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
  `is_final` bit(1) NOT NULL,
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

-- Dump completed on 2019-01-17 17:19:07

commit;
