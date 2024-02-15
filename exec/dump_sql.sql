CREATE DATABASE  IF NOT EXISTS `dagak`;
USE `dagak`;
-- MySQL dump 10.13  Distrib 8.1.0, for macos13 (arm64)
--
-- Host: 127.0.0.1    Database: dagak
-- ------------------------------------------------------
-- Server version	8.1.0


--
-- Table structure for table `alarm`
--

DROP TABLE IF EXISTS `alarm`;
CREATE TABLE `alarm` (
  `alarm_id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(20) NOT NULL,
  `tag_id` int NOT NULL,
  `is_checked` tinyint DEFAULT '0',
  `requested_user_id` varchar(20) NOT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`alarm_id`)
) ;

--
-- Dumping data for table `alarm`
--

LOCK TABLES `alarm` WRITE;
/*!40000 ALTER TABLE `alarm` DISABLE KEYS */;
INSERT INTO `alarm` VALUES (1,'anya',2,0,'ssafy','2024-01-27 05:44:17'),(2,'gill',2,0,'ssafy','2024-01-27 05:44:17'),(3,'sinyoung',2,1,'ssafy','2024-01-27 05:44:17'),(4,'dongjea',4,0,'ssafy','2024-01-27 05:44:17'),(5,'youngjoo',4,1,'ssafy','2024-01-27 05:44:17'),(6,'jeaseok',5,1,'ssafy','2024-01-27 05:44:17'),(7,'sinyoung',6,1,'ssafy','2024-01-27 05:44:17'),(8,'joungkook',6,0,'ssafy','2024-01-27 05:44:17'),(9,'kirua',6,0,'ssafy','2024-01-27 05:44:17'),(10,'gon',6,0,'ssafy','2024-01-27 05:44:17'),(11,'gill',6,0,'ssafy','2024-01-27 05:44:17'),(12,'sinyoung',7,1,'ssafy','2024-01-27 05:44:17'),(13,'anya',4,1,'sinyoung',NULL),(14,'dohee',4,0,'sinyoung',NULL),(15,'youngjoo',4,0,'sinyoung',NULL),(16,'gon',4,1,'sinyoung',NULL),(17,'sinyoung',5,1,'gon',NULL),(18,'ssafy123',4,0,'sinyoung',NULL),(20,'sinyoung',4,0,'kirua',NULL),(21,'kirua',4,1,'sinyoung',NULL),(22,'anya',4,1,'kirua',NULL),(23,'sinyoung',5,0,'anya',NULL),(24,'kirua',5,0,'anya',NULL),(25,'anya',4,1,'anya',NULL),(32,'anya',4,0,'anya',NULL),(33,'anya',4,1,'gaeto',NULL),(34,'gaeto',5,0,'anya',NULL),(36,'anya',3,1,'gon',NULL),(37,'gon',2,0,'ssafy',NULL),(38,'ssafy',1,0,'sinyoung','2024-02-13 15:14:25'),(39,'ssafy',4,0,'dana','2024-02-13 15:14:25'),(40,'ssafy',4,1,'gojo','2024-02-13 15:14:25'),(41,'ssafy',5,0,'youngjin','2024-02-13 15:14:25'),(42,'ssafy',5,1,'donjae','2024-02-13 15:14:25'),(43,'ssafy',6,0,'gaeto','2024-02-13 15:14:25');
/*!40000 ALTER TABLE `alarm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alarm_tag`
--

DROP TABLE IF EXISTS `alarm_tag`;
CREATE TABLE `alarm_tag` (
  `alarm_tag_id` int NOT NULL AUTO_INCREMENT,
  `alarm_tag_name` varchar(20) NOT NULL,
  PRIMARY KEY (`alarm_tag_id`)
) ;

--
-- Dumping data for table `alarm_tag`
--

LOCK TABLES `alarm_tag` WRITE;
/*!40000 ALTER TABLE `alarm_tag` DISABLE KEYS */;
INSERT INTO `alarm_tag` VALUES (1,'게시글댓글'),(2,'모꼬지요청'),(3,'모꼬지승인'),(4,'친구요청'),(5,'친구승인'),(6,'질문답변'),(7,'DM');
/*!40000 ALTER TABLE `alarm_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `answer`
--

DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer` (
  `answer_id` varchar(40) NOT NULL,
  `question_id` varchar(40) NOT NULL,
  `user_id` varchar(20) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `answer_content` varchar(200) NOT NULL,
  `session` varchar(45) NOT NULL,
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`answer_id`)
) ;

--
-- Dumping data for table `answer`
--

LOCK TABLES `answer` WRITE;
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
INSERT INTO `answer` VALUES ('0139de03-bf09-449a-a6a5-701997138be9','9672bfa7-615f-4214-bcf5-d39cd35c232e','sinyoung','2024-02-10 11:48:46','test','SQLD3','2024-02-10 11:48:46'),('0b3c66c2-0777-49c9-8a4a-f63d03da3e97','0a6f0061-cbb6-49f0-b6e1-7b37dbc192e7','sinyoung','2024-02-10 10:20:22','ㅇㅇㅇㅇㅇㅇ','SQLD3','2024-02-10 10:20:22'),('0e924f5f-d8eb-4c2a-b5e7-357121af2a05','32e88ac0-7e4c-4f9b-868c-dca03166d50d','sinyoung','2024-02-10 10:49:48','저요','SQLD3','2024-02-10 10:49:48'),('0fe44a0d-35b1-42b0-8c12-da003723f120','b6f37028-a425-4abd-bccd-3ebe80d897fb','sinyoung','2024-02-10 14:13:10','답','SQLD3','2024-02-10 14:13:10'),('11432d34-2bf9-4663-bdf3-9859f2f1d8e5','412e86ac-f6ec-4497-8764-6a863d4fd6fa','sinyoung','2024-02-10 11:15:18','주무세요^^','SQLD3','2024-02-10 11:15:18'),('1d98f256-28f3-40c1-8084-63fa2dbbf003','5c804bac-0669-45f6-9775-dfd59c626099','gon','2024-02-10 17:01:33','ㅏㅏ','SQLD2','2024-02-10 17:01:33'),('23af8919-e6c4-4436-affe-1ab829f38b55','d5c8a011-6687-4903-9227-14146c13baa1','sinyoung','2024-02-10 10:24:05','답젼','SQLD3','2024-02-10 10:24:05'),('317e7202-71ed-4dc9-897a-fb617abbdf2b','a5ec71ef-7c25-4e5f-b1f9-d14761a8b0cf','sinyoung','2024-02-10 08:59:09','erge','SQLD2','2024-02-10 08:59:09'),('31a611f6-37c8-4864-b157-4294a83aad02','41b44b8f-3245-410d-b74e-19b5fe9dd6a8','sinyoung','2024-02-10 10:46:52','잘 해보세요','SQLD3','2024-02-10 10:46:52'),('4568c286-6701-41ac-979d-f914f5b15c69','0894a696-e662-49e5-87c4-1e8c8e9f9ccc','sinyoung','2024-02-10 13:25:59','ㄹㄹ','SQLD3','2024-02-10 13:25:59'),('52841f36-0baa-4a90-b6f2-80369def221c','26546940-fbae-4fcb-92de-183ee9f6a4e1','ssafy','2024-02-10 08:54:57','이투스북에서 만든 \'워드마스터\' 괜찮더라구요','SQLD2','2024-02-13 14:41:24'),('52f0ee5f-5d9e-4b1b-9796-ed9504cf0821','41b44b8f-3245-410d-b74e-19b5fe9dd6a8','sinyoung','2024-02-10 10:44:42','답변','SQLD3','2024-02-10 10:44:42'),('602cf725-45d8-4bb2-86ab-5135885b3388','b6f37028-a425-4abd-bccd-3ebe80d897fb','sinyoung','2024-02-10 14:29:30','wefwef','SQLD3','2024-02-10 14:29:30'),('65957db1-6c94-4dae-a2e8-ad99d395e781','4cbd28fd-8c45-4102-9cb7-73711be06680','sinyoung','2024-02-10 11:18:13','ㄷㄹㅈㅈ','SQLD3','2024-02-10 11:18:13'),('65cd6902-f20e-48d8-8375-b86542b1a5d9','2810c670-74be-4b4a-9c3b-87083991be94','sinyoung','2024-02-10 10:28:52','다볒ㅇㅇ','SQLD3','2024-02-10 10:28:52'),('676920ff-daed-4d24-ad8e-e1b40a561e79','416f5949-2173-473a-840d-7ad6db09745c','ssafy','2024-02-10 10:07:22','답입니다~','SQLD3','2024-02-10 10:07:22'),('68cf6ff7-97ef-44ba-b7f5-e82a5071a278','7f322473-f2cf-4d46-9afd-f824d1bdb1a2','sinyoung','2024-02-10 10:42:11','wefwe','SQLD3','2024-02-10 10:42:11'),('6abde99c-d10b-40ac-9daa-a2b616838e3b','6832bb16-0787-47c1-90fe-5b01a951949c','sinyoung','2024-02-10 10:28:05','anejkf','SQLD3','2024-02-10 10:28:05'),('710df4e8-dd92-423a-ae86-7d896e6a9e8d','fbaa05c5-7fd4-4808-8ee9-f6d0963f6a6a','sinyoung','2024-02-10 10:41:47','wef','SQLD3','2024-02-10 10:41:47'),('738c9129-0322-4844-bb56-a65f78253df5','70c3aef4-f65c-4103-8369-5d95efefd220','sinyoung','2024-02-10 11:18:34','ㅈㄷㄹㅈ3','SQLD3','2024-02-10 11:18:34'),('78daaaa4-7182-49b1-a9c3-b6d492d5d25e','731dd5c5-24ca-43b3-bfc1-6dd3f31e1a5b','sinyoung','2024-02-13 13:41:05','wefwe','korean1','2024-02-13 13:41:05'),('7e790b89-8579-4ccd-baab-ade8361b6957','40522d97-0832-4050-b596-f89b5696f0c6','sinyoung','2024-02-10 11:14:47','질문 답변드립니다','SQLD3','2024-02-10 11:14:47'),('81c3866a-1fbd-4905-a63f-3ed168db5826','30ec9c50-df49-49aa-b66a-de70b25762ea','gon','2024-02-10 17:02:14','ㅓㅠㅏㅠㅡ','SQLD2','2024-02-10 17:02:14'),('883e7278-f55e-4e9e-a603-9271429bfaf7','32e88ac0-7e4c-4f9b-868c-dca03166d50d','sinyoung','2024-02-10 10:50:00','친추 해주시면 드릴게요','SQLD3','2024-02-10 10:50:00'),('a3df6124-0cc3-41f0-9f9b-523049b01074','26546940-fbae-4fcb-92de-183ee9f6a4e1','sinyoung','2024-02-10 08:55:25','wekjf','SQLD2','2024-02-10 08:55:25'),('a4d3d03e-c155-41dc-8eab-6b2b86335a7d','b6f37028-a425-4abd-bccd-3ebe80d897fb','sinyoung','2024-02-10 13:57:37','답변이요','SQLD3','2024-02-10 13:57:37'),('ab89e9b3-435d-47e1-b304-c3604cb8f560','83ec5998-05da-4dd1-84f0-7a27d5676d89','ssafy','2024-02-10 10:08:21','답입니다~','SQLD3','2024-02-10 10:08:21'),('b80b84de-d3b1-4f03-9dfb-96be777a2fce','7f322473-f2cf-4d46-9afd-f824d1bdb1a2','sinyoung','2024-02-10 10:42:50','swe','SQLD3','2024-02-10 10:42:50'),('b984c2ec-f153-4d8a-a7ef-7d0b77a31d5f','7d3c00ed-69ce-4e5e-9f9a-69bf9085858a','sinyoung','2024-02-10 10:43:06','wefw','SQLD3','2024-02-10 10:43:06'),('ca950863-3cbf-4cbc-a56a-70a4f7a23592','0a6f0061-cbb6-49f0-b6e1-7b37dbc192e7','ssafy','2024-02-10 10:22:02','{x|x<=A} = {0(공집합), {1}, {2}, {{3}}, {1, 2}, {1, {3}}, {2, {3}}, {1, 2, {3}} } 입니다.','SQLD3','2024-02-13 14:39:46'),('d131a36c-b2a0-41f9-b28c-2a5d100f3535','38a4bd85-0324-4411-8f67-8884f211f618','sinyoung','2024-02-10 10:43:41','wefwfe','SQLD3','2024-02-10 10:43:41'),('d20f92aa-afd5-41be-8cb0-853680027c78','13331456-2a59-4132-9845-058e71bce2dd','sinyoung','2024-02-10 10:26:31','ㅏㅘ','SQLD3','2024-02-10 10:26:31'),('d3bfa5f1-619e-4b03-8343-2fdf974e3ed9','5c804bac-0669-45f6-9775-dfd59c626099','gon','2024-02-10 17:02:02','ㅗㅓ포','SQLD2','2024-02-10 17:02:02'),('e3def4d3-d726-46c0-9974-800443f7525f','fa8df732-37bb-4051-8eee-7e75148dd9fd','sinyoung','2024-02-10 11:52:17','답변입니다','SQLD3','2024-02-10 11:52:17'),('f3abdea8-cd08-484a-ba79-1dc8142e634d','41b44b8f-3245-410d-b74e-19b5fe9dd6a8','sinyoung','2024-02-10 10:48:46','답변이 잘 달리는지 확인 중입니다. 사실은 SQLD 공부와 발표준비까지 해야되는데 걱정이 이만 저만이 아닙니다','SQLD3','2024-02-10 10:48:46'),('f68ee670-64f7-44de-b017-30b33ce0138e','fbaa05c5-7fd4-4808-8ee9-f6d0963f6a6a','sinyoung','2024-02-10 10:37:49','ㄷㅈㄹㄷㅈ','SQLD3','2024-02-10 10:37:49'),('fe2f7449-980c-46ff-8015-7f58188045ca','fbaa05c5-7fd4-4808-8ee9-f6d0963f6a6a','sinyoung','2024-02-10 10:31:20','다ㅓㄹ절','SQLD3','2024-02-10 10:31:20');
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
CREATE TABLE `board` (
  `board_id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(20) NOT NULL,
  `tag_id` int NOT NULL,
  `board_title` varchar(40) DEFAULT NULL,
  `board_content` varchar(2000) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`board_id`)
) ;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
INSERT INTO `board` VALUES (1,'sinyoung',1,'여러분 그거 알아요?  싸이버거 맛있어요','케이준 감자랑 같이 먹으면 얼마나 맛있게요','2024-01-27 06:25:59','2024-02-13 15:20:31'),(2,'gojo',1,'SQLD 자료집 공유합니다','http://i10a404.p.ssafy.io 로 들어오면 다운로드 받을 수 있어요~ 같이 공부해요ㅎㅎ','2024-01-27 06:25:59','2024-02-13 15:23:46'),(3,'gon',2,'백모처럼 탈색한 친구 구해요.','최근에 초록으로 탈색했는데 정보 좀 받고싶어서요ㅜㅜ','2024-01-27 06:25:59','2024-02-13 15:20:30'),(4,'ssafy',2,'SSAFY 다니시는 분??','서울 캠퍼스 2학기 지내고 있는데 같이 알고리즘 스터디 할 사람 구해요 친추해주세요 ','2024-02-13 15:23:46','2024-02-13 15:26:03'),(5,'dongjae',1,'JLPT N3 합격 후기','궁금하면 \'A404\' 찾아서 모꼬지 신청해주세요^^ 공유해드려요','2024-02-13 15:26:03','2024-02-13 15:28:37');
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board_comment`
--

DROP TABLE IF EXISTS `board_comment`;
CREATE TABLE `board_comment` (
  `comment_id` int NOT NULL AUTO_INCREMENT,
  `board_id` int NOT NULL,
  `user_id` varchar(20) NOT NULL,
  `comment` char(100) NOT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`comment_id`)
) ;

--
-- Dumping data for table `board_comment`
--

LOCK TABLES `board_comment` WRITE;
/*!40000 ALTER TABLE `board_comment` DISABLE KEYS */;
INSERT INTO `board_comment` VALUES (1,1,'sinyoung','뭘 좀 아시는 분이군','2024-01-27 06:31:32','2024-01-27 06:31:32'),(2,2,'gaeto','옥상으로 따라와','2024-01-27 06:31:32','2024-01-27 06:31:32'),(3,2,'gon','멋지다..!','2024-01-27 06:31:32','2024-01-27 06:31:32'),(4,3,'sinyoung','갈머는 안 되나요..','2024-01-27 06:31:32','2024-01-27 06:31:32'),(5,3,'jungkook','Golden Circle~~','2024-01-27 06:31:32','2024-01-27 06:31:32');
/*!40000 ALTER TABLE `board_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board_tag`
--

DROP TABLE IF EXISTS `board_tag`;
CREATE TABLE `board_tag` (
  `board_tag_id` int NOT NULL AUTO_INCREMENT,
  `board_tag_name` varchar(20) NOT NULL,
  PRIMARY KEY (`board_tag_id`)
) ;

--
-- Dumping data for table `board_tag`
--

LOCK TABLES `board_tag` WRITE;
/*!40000 ALTER TABLE `board_tag` DISABLE KEYS */;
INSERT INTO `board_tag` VALUES (1,'정보'),(2,'구해요');
/*!40000 ALTER TABLE `board_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `calendar`
--

DROP TABLE IF EXISTS `calendar`;
CREATE TABLE `calendar` (
  `calendar_dagak_id` int NOT NULL AUTO_INCREMENT,
  `dagak_id` int NOT NULL COMMENT '오늘의 공부루틴',
  `user_id` varchar(20) NOT NULL COMMENT '등록한 유저',
  `calendar_date` date NOT NULL COMMENT '등록된 날짜',
  PRIMARY KEY (`calendar_dagak_id`)
) ;

--
-- Dumping data for table `calendar`
--

LOCK TABLES `calendar` WRITE;
/*!40000 ALTER TABLE `calendar` DISABLE KEYS */;
INSERT INTO `calendar` VALUES (1,1,'ssafy','2024-01-25'),(2,1,'ssafy','2024-01-26'),(3,2,'ssafy','2024-01-27'),(4,1,'ssafy','2024-01-28'),(6,5,'sinyoung','2024-02-10'),(8,6,'sinyoung','2024-02-13');
/*!40000 ALTER TABLE `calendar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(10) NOT NULL,
  PRIMARY KEY (`category_id`)
) ;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Korean'),(2,'English'),(3,'Math'),(4,'Society'),(5,'Science'),(6,'Athletic'),(7,'Japanese'),(8,'Chinese'),(9,'IT');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dagak`
--

DROP TABLE IF EXISTS `dagak`;
CREATE TABLE `dagak` (
  `dagak_id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(20) NOT NULL,
  `total_time` int NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `dagak_name` varchar(20) NOT NULL,
  PRIMARY KEY (`dagak_id`)
) ;

--
-- Dumping data for table `dagak`
--

LOCK TABLES `dagak` WRITE;
/*!40000 ALTER TABLE `dagak` DISABLE KEYS */;
INSERT INTO `dagak` VALUES (2,'sinyoung',120,'2024-01-26 23:59:47','2024-02-13 14:53:28','신영이 dagak 1'),(4,'ssafy',160,'2024-02-09 14:50:50','2024-02-13 14:50:13','오늘은 조금만 각'),(5,'ssafy',270,'2024-02-10 05:35:58','2024-02-13 14:47:46','달리는 날'),(6,'ssafy',150,'2024-02-11 12:25:04','2024-02-13 14:50:13','영어뿌시는 다각');
/*!40000 ALTER TABLE `dagak` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friend`
--

DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend` (
  `user_id` varchar(20) NOT NULL,
  `user_id2` varchar(20) NOT NULL,
  `is_friend` tinyint DEFAULT NULL,
  PRIMARY KEY (`user_id`,`user_id2`)
) ;

--
-- Dumping data for table `friend`
--

LOCK TABLES `friend` WRITE;
/*!40000 ALTER TABLE `friend` DISABLE KEYS */;
INSERT INTO `friend` VALUES ('anya','kirua',1),('anya','sinyoung',1),('gaeto','anya',1),('gaeto','gill',0),('gill','gaeto',1),('gon','sinyoung',1),('kirua','anya',0),('kirua','gon',1),('kirua','sinyoung',0),('sinyoung','anya',0),('sinyoung','dohee',0),('sinyoung','gon',0),('sinyoung','ssafy123',0),('sinyoung','youngjoo',0),('ssafy','dana',1),('ssafy','dongjae',1),('ssafy','sinyoung',1),('ssafy','youngjoo',1),('ssafy123','ssafy124',1),('ssafy124','ssafy125',1),('ssafy125','ssafy123',1),('youngjin','ssafy',1);
/*!40000 ALTER TABLE `friend` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gak`
--

DROP TABLE IF EXISTS `gak`;
CREATE TABLE `gak` (
  `gak_id` bigint NOT NULL AUTO_INCREMENT,
  `dagak_id` int NOT NULL,
  `user_id` varchar(20) NOT NULL,
  `category_id` int NOT NULL,
  `gak_order` int NOT NULL,
  `running_time` int NOT NULL,
  PRIMARY KEY (`gak_id`)
) ;

--
-- Dumping data for table `gak`
--

LOCK TABLES `gak` WRITE;
/*!40000 ALTER TABLE `gak` DISABLE KEYS */;
INSERT INTO `gak` VALUES (4,2,'sinyoung',3,1,60),(5,2,'sinyoung',2,2,60),(6,4,'ssafy',1,1,80),(7,4,'ssafy',2,2,80),(8,5,'ssafy',2,1,80),(9,5,'ssafy',2,2,120),(10,5,'ssafy',3,3,70),(11,6,'ssafy',2,1,10),(12,6,'ssafy',1,2,10),(13,6,'ssafy',2,3,50),(14,6,'ssafy',1,4,30),(15,6,'ssafy',2,5,50);
/*!40000 ALTER TABLE `gak` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gak_history`
--

DROP TABLE IF EXISTS `gak_history`;
CREATE TABLE `gak_history` (
  `history_id` int NOT NULL AUTO_INCREMENT,
  `category_id` int NOT NULL,
  `gak_id` int NOT NULL,
  `calendar_id` int NOT NULL,
  `user_id` varchar(20) NOT NULL,
  `memory_time` int DEFAULT '0',
  `created_date` date NOT NULL,
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`history_id`)
) ;

--
-- Dumping data for table `gak_history`
--

LOCK TABLES `gak_history` WRITE;
/*!40000 ALTER TABLE `gak_history` DISABLE KEYS */;
INSERT INTO `gak_history` VALUES (1,1,1,1,'ssafy',60,'2024-01-27','2024-01-27 01:10:27'),(2,2,2,1,'ssafy',90,'2024-01-27','2024-01-27 01:10:27'),(3,3,3,1,'ssafy',10,'2024-01-27','2024-01-27 01:10:27'),(4,1,4,2,'ssafy',20,'2024-01-27','2024-01-27 01:31:29'),(5,1,11,8,'sinyoung',63,'2024-02-13','2024-02-12 15:00:00'),(6,0,0,0,'ssafy',4,'2024-02-13','2024-02-12 15:00:00');
/*!40000 ALTER TABLE `gak_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
CREATE TABLE `inventory` (
  `inventory_id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(20) NOT NULL,
  `product_id` int NOT NULL,
  `is_wearing` tinyint DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`inventory_id`)
) ;
--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` VALUES (1,'ssafy',1,1,'2024-01-27 05:06:24'),(2,'ssafy',2,0,'2024-01-27 05:06:24'),(3,'ssafy',3,0,'2024-01-27 05:06:24'),(4,'sinyoung',1,1,'2024-02-10 07:49:05'),(5,'sinyoung',2,1,'2024-02-10 07:49:14'),(6,'sinyoung',3,1,'2024-02-10 07:49:17');
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login_history`
--

DROP TABLE IF EXISTS `login_history`;
CREATE TABLE `login_history` (
  `login_history_id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(20) NOT NULL,
  `user_ip` char(20) NOT NULL,
  `try_login_count` int DEFAULT '0',
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`login_history_id`)
) ;

--
-- Dumping data for table `login_history`
--

LOCK TABLES `login_history` WRITE;
/*!40000 ALTER TABLE `login_history` DISABLE KEYS */;
INSERT INTO `login_history` VALUES (1,'dana123','0:0:0:0:0:0:0:1',1,'2024-02-09 14:38:32','2024-02-09 14:38:32');
/*!40000 ALTER TABLE `login_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `message_id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(200) NOT NULL,
  `from_user_id` varchar(20) NOT NULL,
  `to_user_id` varchar(20) NOT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`message_id`)
) ;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mokkoji`
--

DROP TABLE IF EXISTS `mokkoji`;
CREATE TABLE `mokkoji` (
  `mokkoji_id` int NOT NULL AUTO_INCREMENT,
  `mokkoji_name` varchar(15) DEFAULT NULL,
  `leader_id` varchar(20) DEFAULT NULL,
  `mokkoji_status` char(60) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`mokkoji_id`)
) ;

--
-- Dumping data for table `mokkoji`
--

LOCK TABLES `mokkoji` WRITE;
/*!40000 ALTER TABLE `mokkoji` DISABLE KEYS */;
INSERT INTO `mokkoji` VALUES (1,'A404','sinyoung','찾을 수 없는 페이지따위 존재하지 않음','2024-01-27 01:33:25','2024-02-13 15:28:56'),(2,'Hunter X Hunter','gon','헌터 협회 라이센스 필수입니다~ ^^','2024-01-27 01:35:34','2024-01-27 01:35:49'),(3,'BTS','jungkook','안녕하세요 BTS입니다. (짭아님)','2024-01-27 01:35:34','2024-01-27 01:35:34'),(4,'주술회전','gojo','주술회전 과몰입ㅈㅅ','2024-01-27 01:35:34','2024-01-27 01:35:34'),(5,'무한도전','haha','무한~도전','2024-01-27 04:44:25','2024-01-27 04:44:25');
/*!40000 ALTER TABLE `mokkoji` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mokkoji_category`
--

DROP TABLE IF EXISTS `mokkoji_category`;
CREATE TABLE `mokkoji_category` (
  `mokkoji_category_id` bigint NOT NULL AUTO_INCREMENT,
  `category_id` int DEFAULT NULL,
  `mokkoji_id` int DEFAULT NULL,
  PRIMARY KEY (`mokkoji_category_id`),
  KEY `FKoknemq8bo71mrayx8xyg2er0m` (`category_id`),
  KEY `FK7c8axbua2xpmpm0dpxw5t2427` (`mokkoji_id`)
) ;

--
-- Dumping data for table `mokkoji_category`
--

LOCK TABLES `mokkoji_category` WRITE;
/*!40000 ALTER TABLE `mokkoji_category` DISABLE KEYS */;
INSERT INTO `mokkoji_category` VALUES (13,1,1),(14,2,1),(15,3,2),(16,2,3),(17,1,4),(18,1,5);
/*!40000 ALTER TABLE `mokkoji_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `mokkoji_rankings`
--

DROP TABLE IF EXISTS `mokkoji_rankings`;
/*!50001 DROP VIEW IF EXISTS `mokkoji_rankings`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `mokkoji_rankings` AS SELECT 
 1 AS `mokkoji_id`,
 1 AS `mokkoji_name`,
 1 AS `leader_id`,
 1 AS `total_memory_time`,
 1 AS `categories`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `product_category_id` int NOT NULL,
  `product_name` varchar(15) NOT NULL,
  `product_description` varchar(40) NOT NULL,
  `product_price` int NOT NULL,
  `product_image` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,1,'왕관의 무게','왕관을 쓰려는자.. 무게를 견뎌라',200,'crown'),(2,2,'천사의 날개','하늘에서 떨어진 무언가가 남기고 간 물건이다',100,'wing'),(3,3,'귀여운 볼터치','홍조인가?',50,'blush'),(4,1,'홍대피플 캡모자','어째선지 홍대에서 많이 본듯한 디자인이다',100,'ballcap'),(5,1,'탐험 모자','탐험을 할 수 있을 것만 같다',200,'adventure'),(6,2,'닭다리','한손에 닭다리를 쥔다면',300,'chicken'),(7,1,'꼬깔 모자','생일 축하합니다',200,'cone'),(8,1,'헤드폰','헤드폰을 끼고있는 멋진 나..',300,'headphone'),(9,2,'불끈불끈','힘+1000',200,'muscle'),(10,2,'파란 펜','공부가 잘 될 것만 같은 펜이다',100,'pen'),(11,3,'초록색 목도리','목도리를 두른다면..?',200,'scarf'),(12,3,'검정 선글라스','기본적인 선글라스다',100,'sunglass1'),(13,3,'노랑 선글라스','노란색 선글라스다',200,'sunglass2'),(14,3,'파랑 선글라스','파란색 선글라스다',200,'sunglass3'),(15,3,'주황 선글라스','주황색 선글라스다',200,'sunglass4');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_category`
--

DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category` (
  `product_category_id` int NOT NULL AUTO_INCREMENT,
  `product_category_name` varchar(15) NOT NULL,
  PRIMARY KEY (`product_category_id`)
) ;

--
-- Dumping data for table `product_category`
--

LOCK TABLES `product_category` WRITE;
/*!40000 ALTER TABLE `product_category` DISABLE KEYS */;
INSERT INTO `product_category` VALUES (1,'모자'),(2,'망토'),(3,'얼굴');
/*!40000 ALTER TABLE `product_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `question_id` varchar(40) NOT NULL,
  `user_id` varchar(20) NOT NULL,
  `question_content` char(100) NOT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `session` varchar(45) NOT NULL,
  PRIMARY KEY (`question_id`)
) ;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES ('0894a696-e662-49e5-87c4-1e8c8e9f9ccc','sinyoung','ㅎㅎㅎ',NULL,NULL,'SQLD3'),('0a6f0061-cbb6-49f0-b6e1-7b37dbc192e7','ssafy','A={1, 2, {3}}일 때 모든 부분집합을 구하라는데 너무 헷갈립니다. 답이 뭔가요?',NULL,'2024-02-13 14:36:49','SQLD3'),('13331456-2a59-4132-9845-058e71bce2dd','ssafy','멱집합 P({a}, {b}) , 이거 어떻게 푸나요,,',NULL,'2024-02-13 14:36:49','SQLD3'),('26546940-fbae-4fcb-92de-183ee9f6a4e1','ssafy','고3인데 영어 단어장 추천해주세요',NULL,'2024-02-13 14:36:49','SQLD2'),('2810c670-74be-4b4a-9c3b-87083991be94','ssafy','4형식을 3형식으로 바꾸는 거 어떻게 했었죠..?',NULL,'2024-02-13 14:36:49','SQLD3'),('30ec9c50-df49-49aa-b66a-de70b25762ea','gon','ㅏㅏ',NULL,NULL,'SQLD2'),('32e88ac0-7e4c-4f9b-868c-dca03166d50d','ssafy','SQLD 자료집 있으신분?',NULL,'2024-02-13 14:20:41','SQLD3'),('38a4bd85-0324-4411-8f67-8884f211f618','sinyoung','wefw',NULL,NULL,'SQLD3'),('40522d97-0832-4050-b596-f89b5696f0c6','ssafy','늦은 저녁까지 다들 고생하십니다^^',NULL,'2024-02-13 14:42:45','SQLD3'),('412e86ac-f6ec-4497-8764-6a863d4fd6fa','sinyoung','졸료요',NULL,'2024-02-13 14:42:45','SQLD3'),('416f5949-2173-473a-840d-7ad6db09745c','sinyoung','질문질질문',NULL,'2024-02-13 14:42:45','SQLD3'),('41b44b8f-3245-410d-b74e-19b5fe9dd6a8','sinyoung','wefw',NULL,'2024-02-13 14:42:45','SQLD3'),('4cbd28fd-8c45-4102-9cb7-73711be06680','sinyoung','자ㅓㄹ자ㅣ더리ㅏㅈㄷ',NULL,NULL,'SQLD3'),('5c804bac-0669-45f6-9775-dfd59c626099','gon','ㅎㅎ',NULL,NULL,'SQLD2'),('6832bb16-0787-47c1-90fe-5b01a951949c','sinyoung','dd',NULL,'2024-02-13 14:42:45','SQLD3'),('70c3aef4-f65c-4103-8369-5d95efefd220','sinyoung','ㅈㄷㄹㅈ',NULL,'2024-02-13 14:42:45','SQLD3'),('731dd5c5-24ca-43b3-bfc1-6dd3f31e1a5b','sinyoung','efwe',NULL,NULL,'korean1'),('7d3c00ed-69ce-4e5e-9f9a-69bf9085858a','sinyoung','wefwe',NULL,NULL,'SQLD3'),('7f322473-f2cf-4d46-9afd-f824d1bdb1a2','sinyoung','wefw',NULL,NULL,'SQLD3'),('83ec5998-05da-4dd1-84f0-7a27d5676d89','sinyoung','질문질질문',NULL,NULL,'SQLD3'),('86c49d4f-8a6a-41c7-9da5-d9e28461cf9a','sinyoung','wefwe',NULL,NULL,'SQLD2'),('8bf032d2-a072-46bd-adb5-ed4de22464ff','sinyoung','질문질질문',NULL,'2024-02-13 14:42:45','SQLD2'),('9672bfa7-615f-4214-bcf5-d39cd35c232e','sinyoung','그러자 재화형이랑 나랑 오늘 로또 샀는데 결과좀 확인하고 올게',NULL,'2024-02-13 14:42:45','SQLD3'),('a5ec71ef-7c25-4e5f-b1f9-d14761a8b0cf','sinyoung','sss',NULL,NULL,'SQLD2'),('b17d92c1-52bb-436b-b634-09ea222d253a','sinyoung','wrg',NULL,NULL,'SQLD2'),('b6f37028-a425-4abd-bccd-3ebe80d897fb','sinyoung','악',NULL,NULL,'SQLD3'),('d5c8a011-6687-4903-9227-14146c13baa1','sinyoung','ㄴㅇㅇ',NULL,NULL,'SQLD3'),('f58e67d1-330c-4fe1-93df-e62cd4ea66c6','sinyoung','질문질질문',NULL,'2024-02-13 14:43:04','SQLD2'),('fa8df732-37bb-4051-8eee-7e75148dd9fd','sinyoung','ㅇㅇ',NULL,NULL,'SQLD3'),('fbaa05c5-7fd4-4808-8ee9-f6d0963f6a6a','sinyoung','질물',NULL,NULL,'SQLD3');
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `total_time_per_mokkoji`
--

DROP TABLE IF EXISTS `total_time_per_mokkoji`;
/*!50001 DROP VIEW IF EXISTS `total_time_per_mokkoji`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `total_time_per_mokkoji` AS SELECT 
 1 AS `mokkoji_id`,
 1 AS `total_memory_time`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` varchar(20) NOT NULL,
  `mokkoji_id` int DEFAULT NULL,
  `user_name` varchar(20) NOT NULL,
  `modify_user_password_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_password` varchar(200) NOT NULL,
  `user_phonenumber` varchar(20) DEFAULT NULL,
  `user_birthday` varchar(10) DEFAULT NULL,
  `user_email` varchar(20) NOT NULL,
  `user_nickname` char(10) DEFAULT NULL,
  `user_picture` char(100) DEFAULT NULL,
  `user_point` int NOT NULL DEFAULT '0',
  `today_dagak_id` int DEFAULT NULL,
  `user_status_message` char(65) DEFAULT NULL,
  `user_total_study_time` int DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `kakao_email` varchar(20) DEFAULT NULL,
  `google_email` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('anya',2,'아냐','2024-02-13 15:01:21','0d97ce5cef8cda1de432bce822adaf291734554ae97469d9c96168a1c323695b','010-1234-1234','0000-00-00','anya@spyfamily.com','스칼라 도둑',NULL,0,NULL,NULL,NULL,'2024-01-26 23:23:48',NULL,NULL),('dana',1,'김다나','2024-01-27 04:43:09','5309d695be90d77d9ddf21979ad3e0e1b90cc69ebf9d533e28c9b0321fce1d77','010-1234-1234','0000-00-00','ssafy@ssafy.com','다나짱',NULL,0,NULL,NULL,NULL,'2024-01-26 23:16:49',NULL,NULL),('dohee',1,'김도희','2024-01-27 04:43:09','7af75a8596e4216f705b87a878fa0549b9db4ec88cf650fa5ba165bab7887a05','010-1234-1234','0000-00-00','ssafy@ssafy.com','도희희',NULL,0,NULL,NULL,NULL,'2024-01-26 23:19:02',NULL,NULL),('dongjae',1,'이동재','2024-01-27 04:43:09','0ad3d93d7bbc6d38718484838c784779c8c5dd0e4c336fb580563dbba522b680','010-1234-1234','0000-00-00','ssafy@ssafy.com','movingLee',NULL,0,NULL,NULL,NULL,'2024-01-26 23:17:14',NULL,NULL),('gaeto',4,'김게토','2024-02-13 15:03:22','0e57657eb1ff3185fdab6194c579128b8777f19fc6234ba01c2b652e8219dadf','010-1234-1234','0000-00-00','gaeto@jujutsu.com','고조 친구',NULL,0,NULL,NULL,NULL,'2024-01-26 23:46:29',NULL,NULL),('gill',5,'길','2024-01-27 04:47:12','534cf6212c3f2dc7088906000babc262b90257c0adca27905e28f7c341562993','010-1234-1234','0000-00-00','gill@moohan.com','길길이',NULL,0,NULL,NULL,NULL,'2024-01-26 23:49:22',NULL,NULL),('gojo',4,'오고조','2024-02-13 15:03:22','f17a36958af701d7c9781b320cfe71f9aeaa3e46b0132ad04752a6cae58cd87b','010-1234-1234','0000-00-00','gojo@spyfamily.com','세계관 최강자',NULL,0,NULL,NULL,NULL,'2024-01-26 23:24:18',NULL,NULL),('gon',2,'곤프릭스','2024-02-13 15:03:22','1493158d6f7842f9a7b141bbf8b407daac9d2f276255317e1e786ec1c3686690','010-1234-1234','0000-00-00','gon@hunter.com','곤육맨',NULL,0,NULL,NULL,NULL,'2024-01-26 23:50:27',NULL,NULL),('haha',5,'하동훈','2024-01-27 04:47:12','67a23ae5e58f62c8df8f2fe28aacea0dc0be9b3d64c8df4f8b3ee5f77bc613a1','010-1234-1234','0000-00-00','haha@moohan.com','하하',NULL,0,NULL,NULL,NULL,'2024-01-26 23:48:37',NULL,NULL),('jeaseok',5,'유재석','2024-01-27 04:47:12','f15a2c22dde4669d2eafdeec0c7917db567b0da7743997bdb67a629ae5a3cab5','010-1234-1234','0000-00-00','jeaseok@moohan.com','메뚜기',NULL,0,NULL,NULL,NULL,'2024-01-26 23:49:09',NULL,NULL),('jungkook',3,'전정국','2024-01-27 04:47:12','9365e0200f6ce0fd9834f921ae4e5a4fc57b2da20ca72da1fdc2726426effa95','010-1234-1234','0000-00-00','jungkook@bts.com','정구기',NULL,0,NULL,NULL,NULL,'2024-01-26 23:20:08',NULL,NULL),('kirua',2,'키르아','2024-01-27 04:43:46','f85147d3c2ece7cb799b3681f8bb04aef641bf6a9aa8adefcbfb6f9986340426','010-1234-1234','0000-00-00','kirua@hunter.com','번개맨',NULL,0,NULL,NULL,NULL,'2024-01-26 23:50:10',NULL,NULL),('sinyoung',1,'김신영','2024-02-10 16:45:16','47cd0e46b6d2810878a4b56b2e38a5f93671d300aff808fbbaa633934c947c04','010-1234-1234','0000-00-00','ssafy@ssafy.com','록바이슨',NULL,9250,NULL,'메롱',NULL,'2024-01-26 23:16:39',NULL,'kseenyoung@gmail.com'),('ssafy',NULL,'김싸피','2024-02-13 15:03:43','5aa0247dc7f199e9f5bc79a3cc870dfcb8754b1a468d72daa5b87f5daa58b8df','010-1234-1234','0000-00-00','ssafy@ssafy.com','싸피','https://s3.ap-northeast-2.amazonaws.com/dagak/profile/ssafy.png',350,NULL,'1등 하는 그 날까지 run',NULL,'2024-01-26 23:15:42',NULL,NULL),('ssafy123',NULL,'박싸피','2024-02-13 15:03:22','4ee6d26ac2ec3e741cde25881dc6ff0289db4562c7d0aa239efe49c0f886e113','010-1234-1234','0000-00-00','ssafy@ssafy.com','싸피 팍',NULL,0,NULL,NULL,NULL,'2024-01-26 23:16:01',NULL,NULL),('ssafy124',NULL,'이싸피','2024-02-13 15:03:22','a5c6b5056584a6bd8c8f10dee7a59046466db1cfcc73c2a23119d40a59a79ac1','010-1234-1234','0000-00-00','ssafy@ssafy.com','싸피 리',NULL,0,NULL,NULL,NULL,'2024-01-26 23:16:12',NULL,NULL),('ssafy125',NULL,'최싸피','2024-02-13 15:03:22','7cd795e3b3db01b834e7b961862267e1b442972e7b0733b90c0cc6d6ff894bfc','010-1234-1234','0000-00-00','ssafy@ssafy.com','싸피 췌',NULL,0,NULL,NULL,NULL,'2024-01-26 23:16:23',NULL,NULL),('yoongi',3,'민윤기','2024-01-27 04:47:12','9c6ca8211b84fa51d4d0680c9dfb2072b4596b80f8f6ba7ecbf363ad4eb85779','010-1234-1234','0000-00-00','yoongi@bts.com','미뉸기',NULL,0,NULL,NULL,NULL,'2024-01-26 23:47:35',NULL,NULL),('youngjin',1,'최영진','2024-01-27 04:43:09','963199db3092638b18edd2a3463d433b8af4ea2356f77c29398e6a5dc120ce42','010-1234-1234','0000-00-00','ssafy@ssafy.com','영지니',NULL,0,NULL,NULL,NULL,'2024-01-26 23:17:46',NULL,NULL),('youngjoo',1,'오영주','2024-01-27 04:43:09','fb53296b4bb37016d2f5f8a9d0ed3a21a4a7524b57e0bedc5c9eb6b71c95e7e9','010-1234-1234','0000-00-00','ssafy@ssafy.com','오존',NULL,0,NULL,NULL,NULL,'2024-01-26 23:10:19',NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `user_rank`
--

DROP TABLE IF EXISTS `user_rank`;
/*!50001 DROP VIEW IF EXISTS `user_rank`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `user_rank` AS SELECT 
 1 AS `user_id`,
 1 AS `user_name`,
 1 AS `user_total_study_time`,
 1 AS `user_rank`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `mokkoji_rankings`
--

/*!50001 DROP VIEW IF EXISTS `mokkoji_rankings`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `mokkoji_rankings` AS select `m`.`mokkoji_id` AS `mokkoji_id`,`m`.`mokkoji_name` AS `mokkoji_name`,`m`.`leader_id` AS `leader_id`,`t`.`total_memory_time` AS `total_memory_time`,group_concat(`c`.`category_id` separator ',') AS `categories` from (((`mokkoji` `m` join `total_time_per_mokkoji` `t` on((`m`.`mokkoji_id` = `t`.`mokkoji_id`))) left join `mokkoji_category` `mc` on((`m`.`mokkoji_id` = `mc`.`mokkoji_id`))) left join `category` `c` on((`mc`.`category_id` = `c`.`category_id`))) group by `m`.`mokkoji_id` order by `t`.`total_memory_time` desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `total_time_per_mokkoji`
--

/*!50001 DROP VIEW IF EXISTS `total_time_per_mokkoji`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `total_time_per_mokkoji` AS select `u`.`mokkoji_id` AS `mokkoji_id`,sum(`g`.`memory_time`) AS `total_memory_time` from (`user` `u` join `gak_history` `g` on((`u`.`user_id` = `g`.`user_id`))) where (`u`.`mokkoji_id` is not null) group by `u`.`mokkoji_id` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `user_rank`
--

/*!50001 DROP VIEW IF EXISTS `user_rank`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `user_rank` AS select `u1`.`user_id` AS `user_id`,`u1`.`user_name` AS `user_name`,`u1`.`user_total_study_time` AS `user_total_study_time`,(select (count(0) + 1) from `user` `u2` where (`u2`.`user_total_study_time` > `u1`.`user_total_study_time`)) AS `user_rank` from `user` `u1` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;


-- 다각의 총 시간을 위한 트리거

CREATE TRIGGER update_total_time
AFTER UPDATE ON gak
FOR EACH ROW
    UPDATE Dagak 
    SET Dagak.total_time = (
        SELECT SUM(gak.running_time)
        FROM gak 
        WHERE gak.dagak_id = NEW.dagak_id
    )
    WHERE dagak.dagak_id = NEW.dagak_id;              

CREATE TRIGGER update_total_time_after_insert
AFTER INSERT ON gak
FOR EACH ROW
    UPDATE Dagak d
    SET d.total_time = (
        SELECT SUM(g.running_time)
        FROM gak g
        WHERE g.dagak_id = NEW.dagak_id
    )
    WHERE d.dagak_id = NEW.dagak_id;


CREATE TRIGGER update_total_time_after_delete
AFTER DELETE ON gak
FOR EACH ROW
    UPDATE Dagak d
    SET d.total_time = (
        SELECT SUM(g.running_time)
        FROM gak g
        WHERE g.dagak_id = OLD.dagak_id
    )
    WHERE d.dagak_id = OLD.dagak_id;


-- 유저의 순공시간을 위한 트리거
CREATE TRIGGER update_user_total_study_time_after_insert
AFTER INSERT ON gak_history
FOR EACH ROW
    UPDATE user
    SET user_total_study_time = (
        SELECT SUM(memory_time)
        FROM gak_history
        WHERE user_id = NEW.user_id
    )
    WHERE user_id = NEW.user_id;

CREATE TRIGGER update_user_total_study_time_after_delete
AFTER DELETE ON gak_history
FOR EACH ROW
    UPDATE user
    SET user_total_study_time = (
        SELECT SUM(memory_time)
        FROM gak_history
        WHERE user_id = OLD.user_id
    )
    WHERE user_id = OLD.user_id;


CREATE TRIGGER update_user_total_study_time_after_update
AFTER UPDATE ON gak_history
FOR EACH ROW
    UPDATE user
    SET user_total_study_time = (
        SELECT SUM(memory_time)
        FROM gak_history
        WHERE user_id = NEW.user_id
    )
    WHERE user_id = NEW.user_id;




-- Dump completed on 2024-02-14  0:47:52
CREATE DATABASE  IF NOT EXISTS `security`;
USE `security`;
-- MySQL dump 10.13  Distrib 8.1.0, for macos13 (arm64)
--
-- Host: 127.0.0.1    Database: security
-- ------------------------------------------------------
-- Server version	8.1.0

--
-- Table structure for table `user_security`
--

DROP TABLE IF EXISTS `user_security`;
CREATE TABLE `user_security` (
  `user_id` varchar(20) NOT NULL,
  `salt` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ;

--
-- Dumping data for table `user_security`
--

LOCK TABLES `user_security` WRITE;
/*!40000 ALTER TABLE `user_security` DISABLE KEYS */;
INSERT INTO `user_security` VALUES ('anya','5c8ca2d6-0a05-4d7d-97a1-55bb15dde6c3'),('dana','8207f01b-8fe4-4757-b94e-00630b1b833e'),('dohee','94e394a4-ead6-4078-aa4a-a69b920019f2'),('dongjae','86f3f13a-46b6-466a-94e4-d2e5a03c3ee9'),('gaeto','0faba127-ee8a-4da2-a1d3-2c50d2492ba5'),('gill','5736cd9e-34c9-4dcd-b942-9b3e518b4b5f'),('gojo','acafa6a5-9562-4532-8977-a15d9e3915ad'),('gon','a413f2bf-2474-4c8c-ac02-5bb9a544605f'),('haha','a8bb5522-0394-4c57-9f68-5b25941b1b5c'),('jeaseok','d233f745-74e0-4294-af1e-287fd13333ee'),('jungkook','f14ca52c-39d2-4d43-88ab-4f44b66aad2e'),('kirua','d621a58b-8d05-4ffb-a242-d8daae62fac8'),('sinyoung','146ad19a-305f-4aff-bf14-41433b946ce8'),('ssafy','56177a07-109d-4067-8a0f-cb2971fd5f3b'),('ssafy123','a494ee52-1b22-42be-be96-a0219dc1cfa8'),('ssafy124','14d01ed8-0e8e-4475-9b48-834ae08d2e98'),('ssafy125','298ce38d-d70b-4860-9e64-d092479cc38f'),('yongbok','20cf23c3-fbe2-4dce-b8a6-447a3a57b3d5'),('yoongi','8195f71b-d4fb-4183-968e-6b40f6e28fd2'),('youngjin','a19ef248-82e5-4b1d-8b70-e15c34fb1ae0'),('youngjoo','49da1dd2-2a25-49e3-a78c-93ac1c68f0d7');
/*!40000 ALTER TABLE `user_security` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

-- Dump completed on 2024-02-14  0:47:52

