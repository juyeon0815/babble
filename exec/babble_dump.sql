-- Server version       5.6.51

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
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'sports'),(2,'cooking'),(3,'handcraft'),(4,'music'),(5,'finance'),(6,'game'),(7,'movie'),(8,'drawing'),(9,'book'),(10,'pet');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hashtag`
--

DROP TABLE IF EXISTS `hashtag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hashtag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hashtag`
--

LOCK TABLES `hashtag` WRITE;
/*!40000 ALTER TABLE `hashtag` DISABLE KEYS */;
INSERT INTO `hashtag` VALUES (1,'냥꽁이'),(2,'고양이'),(3,'집사'),(4,'츄르'),(5,'손뜨개'),(6,'권상우'),(7,'소라게'),(8,'토트넘'),(9,'맨시티'),(10,'축구'),(11,'아무나'),(12,'닭볶음탕'),(13,'        소통'),(14,'하정우'),(15,'존맛탱'),(16,'백종원'),(17,'블랙위도우'),(18,'후기'),(19,'마피아'),(20,'게임'),(21,'레고'),(22,'Lego'),(23,'조립'),(24,'악기'),(25,'우쿠렐레'),(26,'음악'),(27,'독        후감'),(28,'독후평'),(29,'카카오'),(30,'주식'),(31,'가즈아'),(32,'장투'),(33,'투자'),(34,'애완동물'),(35,'싸피'),(36,'SSAFY'),(37,'프로그래밍'),(38,'알고리즘'),(39,'영화'),(40,'햄스터'),(41        ,'커피'),(42,'원두');
/*!40000 ALTER TABLE `hashtag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `room` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `host_id` bigint(20) DEFAULT NULL,
  `is_activate` bit(1) NOT NULL,
  `max_view` bigint(20) DEFAULT NULL,
  `thumbnail_url` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKllkgnps1iryk3347aokxwbxxm` (`category_id`),
  CONSTRAINT `FKllkgnps1iryk3347aokxwbxxm` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,'집사님들 고양이 자랑하러 들어오세요~','2021-08-18 01:21:35.026000',2,'',0,'https://babble-test-zimin.s3.ap-northeast-2.amazonaws.com/pet.jpg','고양이 다 드루와        ',10),(2,'입문자를 위한 코바느질 시작부터','2021-08-18 01:26:25.296000',3,'',0,'https://babble-test-zimin.s3.ap-northeast-2.amazonaws.com/%EB%9C%A8%EA%B0%9C%EC%A7%88.jpg','손뜨개 강연',3),(        3,'같이 축구볼사람!','2021-08-18 01:30:48.555000',4,'',0,'https://babble-test-zimin.s3.ap-northeast-2.amazonaws.com/sports.PNG','[축구] 토트넘 vs 맨시티',1),(4,'밥먹으면서 이야기 나눠요!','        2021-08-18 01:32:14.033000',5,'',0,'https://babble-test-zimin.s3.ap-northeast-2.amazonaws.com/cooking.jpg','닭볶음탕 먹방 & 소통해요~',2),(5,'블랙 위도우 본사람?','2021-08-18 01:36:02.59500        0',6,'',0,'https://babble-test-zimin.s3.ap-northeast-2.amazonaws.com/movie.jpg','[오늘 개봉] 블랙 위도우',7),(6,'마피아 할 사람 구합니당~','2021-08-18 01:44:57.950000',7,'\0',11,'https://ba        bble-test-zimin.s3.ap-northeast-2.amazonaws.com/img.jpeg','마피아 할 사람~~',6),(7,'레고 조립 ','2021-08-18 01:46:44.852000',7,'\0',5,'default','레고 만들기',3),(8,'우쿠렐레 악기를 사랑하시        는 분들을 위한 방','2021-08-18 01:48:29.081000',7,'\0',7,'default','우쿠렐레 취미 모임',4),(9,'베르나르 베르베르 독후평','2021-08-18 01:49:28.739000',7,'\0',1,'default','베르나르 베르베르         독후평',9),(10,'카카오 가즈아!!!','2021-08-18 01:50:42.291000',7,'\0',3,'default','카카오 대주주 모임',5),(11,'오늘 개봉한 영화 자유롭게 이야기해요','2021-08-18 03:38:54.986000',12,'\0',2,'        default','[오늘 개봉] 냥꽁이의 대모험 후기',7),(12,'','2021-08-18 09:44:36.234000',12,'\0',1,'default','커피 이야기',2);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_hashtag`
--

DROP TABLE IF EXISTS `room_hashtag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `room_hashtag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `hashtag_id` bigint(20) DEFAULT NULL,
  `room_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp06kcrlklma2xkbpm5sfls61a` (`hashtag_id`),
  KEY `FKrt05bqu3i8m3ob0ho905tomj7` (`room_id`),
  CONSTRAINT `FKp06kcrlklma2xkbpm5sfls61a` FOREIGN KEY (`hashtag_id`) REFERENCES `hashtag` (`id`),
  CONSTRAINT `FKrt05bqu3i8m3ob0ho905tomj7` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_hashtag`
--

LOCK TABLES `room_hashtag` WRITE;
/*!40000 ALTER TABLE `room_hashtag` DISABLE KEYS */;
INSERT INTO `room_hashtag` VALUES (1,1,1),(2,2,1),(3,3,1),(4,4,1),(5,5,2),(6,6,2),(7,7,2),(8,8,3),(9,9,3),(10,10,3),(11,11,3),(12,12,4),(13,13,4),(14,14,4),(15,15,4),(16,16,4),(17,17,5),(18        ,18,5),(19,13,5);
/*!40000 ALTER TABLE `room_hashtag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_history`
--

DROP TABLE IF EXISTS `room_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `room_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `end_time` datetime(6) DEFAULT NULL,
  `start_time` datetime(6) DEFAULT NULL,
  `room_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5s7nb81wsyejhfl9pc9k47t1r` (`room_id`),
  KEY `FK5f4co2fj1hmx3rysh52e4jfvv` (`user_id`),
  CONSTRAINT `FK5f4co2fj1hmx3rysh52e4jfvv` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK5s7nb81wsyejhfl9pc9k47t1r` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_history`
--

LOCK TABLES `room_history` WRITE;
/*!40000 ALTER TABLE `room_history` DISABLE KEYS */;
INSERT INTO `room_history` VALUES (1,'2021-08-18 01:46:01.829000','2021-08-18 01:45:06.973000',6,2),(2,'2021-08-18 01:47:05.237000','2021-08-18 01:46:53.214000',7,2),(3,'2021-08-18 01:48:46        .351000','2021-08-18 01:48:33.660000',8,2),(4,'2021-08-18 01:50:54.935000','2021-08-18 01:50:48.833000',10,2),(5,'2021-08-18 02:22:05.761000','2021-08-18 01:57:20.706000',1,2),(6,'2021-08-1        8 02:22:06.024000','2021-08-18 01:57:29.966000',1,8),(8,NULL,'2021-08-18 11:31:55.631000',3,2);
/*!40000 ALTER TABLE `room_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `alarm` bit(1) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `provider` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'\0','ppko1233@gmail.com','$2a$10$LdJ99UdLY0qXU.YJY5jWAO.uqDoh6hIPPf7FSZM.DmEVKWssyjieC','default','babble'),(2,'','ssafy1@ssafy.com','$2a$10$vsJGREVDl7t0TpThWh        w8qud8Azx2ggi8zYIRG7/KdHt0F78W9.0Mu','https://babble-test-zimin.s3.ap-northeast-2.amazonaws.com/20190828001023_231178.png','babble'),(3,'\0','ssafy2@ssafy.com','$2a$10$aEPyv4wUpofS5xHF9uvtO        .VCtOpUil.8/mrCs3MZ.cfUQg7ejB.sG','default','babble'),(4,'\0','ssafy3@ssafy.com','$2a$10$SoTjyMZ3mw2ycl0GsgNV6OSkCO5VG77ScAfzUO2p8warH76Y2FCcW','default','babble'),(5,'\0','ssafy4@ssafy.com        ','$2a$10$R..bE9yJKNwvmXNRbkbdD.dHvhTBwdKR5b8e0gQQp..PmWVqz/V3m','default','babble'),(6,'\0','ssafy5@ssafy.com','$2a$10$rYfnoYE9n72U.nKiaOhCBukuF7kfmRt.B9jmekmNZqLktoPkJnkPm','default','bab        ble'),(7,'','test@test.com','$2a$10$De1RKPn2yTn56LNx9l.uN.t5Q6JTh2.GPM.g7lqsE6w7JkDAqe/gG','https://babble-test-zimin.s3.ap-northeast-2.amazonaws.com/20190828001023_231178.png','babble'),(8        ,'\0','ehrud2357@nate.com',NULL,'default','kakao'),(9,'\0','',NULL,'default','google'),(11,'\0','j828h@kakao.com',NULL,'default','kakao'),(13,'\0','j828h@naver.com','$2a$10$eoQnN0O6FAsD0kwL        0YPkFOlsYsQa84gf5RDhFvCC2x8EMvot3Wcha','default','babble');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_hashtag`
--

DROP TABLE IF EXISTS `user_hashtag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_hashtag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `hashtag_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd4pxqsvhut9urowkhi8r82p0p` (`hashtag_id`),
  KEY `FKrsm0i2rgcen9i077dd1baceyn` (`user_id`),
  CONSTRAINT `FKd4pxqsvhut9urowkhi8r82p0p` FOREIGN KEY (`hashtag_id`) REFERENCES `hashtag` (`id`),
  CONSTRAINT `FKrsm0i2rgcen9i077dd1baceyn` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_hashtag`
--

LOCK TABLES `user_hashtag` WRITE;
/*!40000 ALTER TABLE `user_hashtag` DISABLE KEYS */;
INSERT INTO `user_hashtag` VALUES (1,2,2),(2,34,2),(3,35,7),(4,36,7),(5,37,7),(6,38,7);
/*!40000 ALTER TABLE `user_hashtag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_room`
--

DROP TABLE IF EXISTS `user_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_room` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `room_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt69dqc3yclx55jpu6lal8xna8` (`room_id`),
  KEY `FK1cpknce05q8qk8vdddrf97r2u` (`user_id`),
  CONSTRAINT `FK1cpknce05q8qk8vdddrf97r2u` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKt69dqc3yclx55jpu6lal8xna8` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_room`
--

LOCK TABLES `user_room` WRITE;
/*!40000 ALTER TABLE `user_room` DISABLE KEYS */;
INSERT INTO `user_room` VALUES (8,3,NULL),(9,3,2);
/*!40000 ALTER TABLE `user_room` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
