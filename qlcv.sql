-- MySQL dump 10.13  Distrib 5.5.18, for Linux (i686)
--
-- Host: localhost    Database: sqtt
-- ------------------------------------------------------
-- Server version	5.5.18-log

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
-- Table structure for table `ChucVu`
--

DROP TABLE IF EXISTS `ChucVu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ChucVu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ten` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ChucVu`
--

LOCK TABLES `ChucVu` WRITE;
/*!40000 ALTER TABLE `ChucVu` DISABLE KEYS */;
INSERT INTO `ChucVu` VALUES (1,'Hiệu trưởng'),(2,'Hiệu phó'),(3,'Trưởng ban'),(4,'Trưởng phòng'),(5,'Thư ký'),(6,'Nhân viên');
/*!40000 ALTER TABLE `ChucVu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ChuyenMuc`
--

DROP TABLE IF EXISTS `ChuyenMuc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ChuyenMuc` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ten` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ChuyenMuc`
--

LOCK TABLES `ChuyenMuc` WRITE;
/*!40000 ALTER TABLE `ChuyenMuc` DISABLE KEYS */;
/*!40000 ALTER TABLE `ChuyenMuc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CommentCongVanDen`
--

DROP TABLE IF EXISTS `CommentCongVanDen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CommentCongVanDen` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `noiDung` varchar(255) DEFAULT NULL,
  `congVanDen_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKF4C9990EE43D057E` (`congVanDen_id`),
  KEY `FKF4C9990E47140EFE` (`user_id`),
  CONSTRAINT `FKF4C9990E47140EFE` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`),
  CONSTRAINT `FKF4C9990EE43D057E` FOREIGN KEY (`congVanDen_id`) REFERENCES `CongVanDen` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CommentCongVanDen`
--

LOCK TABLES `CommentCongVanDen` WRITE;
/*!40000 ALTER TABLE `CommentCongVanDen` DISABLE KEYS */;
INSERT INTO `CommentCongVanDen` VALUES (1,'2011-12-04 14:48:24','Em gửi phòng hành chính rồi, các anh làm đi nhé:x',1,4),(2,'2011-12-04 14:49:49','đã phân công người xử lý:o',1,5),(3,'2011-12-04 14:50:54','đã xong, anh check lại hộ em.',1,7),(4,'2011-12-04 14:51:22','làm như shit, làm lại đi.',1,5),(5,'2011-12-04 14:51:50','thế là tốt rồi, anh còn đỏi hỏi gì nữa, kệ anh.',1,7),(6,'2011-12-05 09:00:37','Đã có ý kiến chỉ huy, đề nghị phòng hành chính thực hiện đúng thời hạn giải quyết',2,4),(7,'2011-12-05 09:01:46','Đã phân công đồng chí Nguyễn Tuấn Linh thực hiện, đồng chí Nguyễn Trung hiếu hỗ trợ',2,5),(8,'2011-12-05 09:03:32','đã thực hiện xong và báo cáo. Đề nghị đồng chí trưởng phòng xác nhận.',2,7),(9,'2011-12-05 09:04:05','Xác nhận đồng chí Nguyễn Tuấn Linh đã hoàn thành công việc',2,5),(10,'2011-12-05 09:04:43','Đóng quá trình xử lý công văn',2,7),(11,'2011-12-05 14:13:09','Đã gửi tới phòng ban.',4,4),(12,'2011-12-05 14:15:30','Đã giao cho đồng chí Việt Anh thực hiện, đồng chí Linh hỗ trợ. Đề nghị các đồng chí thực hiện đúng thời gian.',4,9),(13,'2011-12-05 14:17:31','Đã thực hiện xong. Đề nghị đồng chí trưởng phòng xác nhận.',4,1),(14,'2011-12-05 14:18:29','Xác nhận đã hoành thành công việc, có thể đóng luồng công văn.',4,9),(15,'2011-12-06 16:26:52','comment 1',4,1),(16,'2011-12-06 16:38:50','đã ngu còn nói nhiều, ông Công im mồm nhé :)',1,1);
/*!40000 ALTER TABLE `CommentCongVanDen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CommentCongVanDen_FileDinhKem`
--

DROP TABLE IF EXISTS `CommentCongVanDen_FileDinhKem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CommentCongVanDen_FileDinhKem` (
  `commentCongVanDen_id` bigint(20) NOT NULL,
  `fileDinhKem_id` bigint(20) NOT NULL,
  UNIQUE KEY `FileDinhKem_id` (`fileDinhKem_id`),
  KEY `FK7C33BDE71381C476` (`commentCongVanDen_id`),
  KEY `FK7C33BDE72D45E8D6` (`fileDinhKem_id`),
  CONSTRAINT `FK7C33BDE71381C476` FOREIGN KEY (`commentCongVanDen_id`) REFERENCES `CommentCongVanDen` (`id`),
  CONSTRAINT `FK7C33BDE72D45E8D6` FOREIGN KEY (`fileDinhKem_id`) REFERENCES `FileDinhKem` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CommentCongVanDen_FileDinhKem`
--

LOCK TABLES `CommentCongVanDen_FileDinhKem` WRITE;
/*!40000 ALTER TABLE `CommentCongVanDen_FileDinhKem` DISABLE KEYS */;
/*!40000 ALTER TABLE `CommentCongVanDen_FileDinhKem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CommentCongVanDi`
--

DROP TABLE IF EXISTS `CommentCongVanDi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CommentCongVanDi` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `noiDung` varchar(255) DEFAULT NULL,
  `congVanDi_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKB550D36447140EFE` (`user_id`),
  KEY `FKB550D3647AFB5036` (`congVanDi_id`),
  CONSTRAINT `FKB550D36447140EFE` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`),
  CONSTRAINT `FKB550D3647AFB5036` FOREIGN KEY (`congVanDi_id`) REFERENCES `CongVanDi` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CommentCongVanDi`
--

LOCK TABLES `CommentCongVanDi` WRITE;
/*!40000 ALTER TABLE `CommentCongVanDi` DISABLE KEYS */;
/*!40000 ALTER TABLE `CommentCongVanDi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CommentCongVanDi_FileDinhKem`
--

DROP TABLE IF EXISTS `CommentCongVanDi_FileDinhKem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CommentCongVanDi_FileDinhKem` (
  `commentCongVanDi_id` bigint(20) NOT NULL,
  `fileDinhKem_id` bigint(20) NOT NULL,
  UNIQUE KEY `FileDinhKem_id` (`fileDinhKem_id`),
  KEY `FK79B8273D1126D23E` (`commentCongVanDi_id`),
  KEY `FK79B8273D2D45E8D6` (`fileDinhKem_id`),
  CONSTRAINT `FK79B8273D1126D23E` FOREIGN KEY (`commentCongVanDi_id`) REFERENCES `CommentCongVanDi` (`id`),
  CONSTRAINT `FK79B8273D2D45E8D6` FOREIGN KEY (`fileDinhKem_id`) REFERENCES `FileDinhKem` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CommentCongVanDi_FileDinhKem`
--

LOCK TABLES `CommentCongVanDi_FileDinhKem` WRITE;
/*!40000 ALTER TABLE `CommentCongVanDi_FileDinhKem` DISABLE KEYS */;
/*!40000 ALTER TABLE `CommentCongVanDi_FileDinhKem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CongVanDen`
--

DROP TABLE IF EXISTS `CongVanDen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CongVanDen` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `CongVanDinhKem` varchar(255) DEFAULT NULL,
  `loaiVanBan` int(11) NOT NULL,
  `maHoSo` varchar(255) DEFAULT NULL,
  `mucDoKhan` int(11) NOT NULL,
  `mucDoMat` int(11) NOT NULL,
  `ngayDen` datetime DEFAULT NULL,
  `ngayThangVanBan` datetime DEFAULT NULL,
  `soKyHieu` varchar(255) DEFAULT NULL,
  `soThuTu` bigint(20) DEFAULT NULL,
  `soTrang` int(11) NOT NULL,
  `tacGia` varchar(255) DEFAULT NULL,
  `thoiHanGiaiQuyet` datetime DEFAULT NULL,
  `tieuDe` varchar(255) DEFAULT NULL,
  `trangThai` int(11) DEFAULT NULL,
  `trichYeu` longtext,
  `yKienPhanPhoi` varchar(255) DEFAULT NULL,
  `nhanVienThucHien_id` bigint(20) DEFAULT NULL,
  `phongBan_id` bigint(20) DEFAULT NULL,
  `thuTruongPheDuyet_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKBC92958F92E7445E` (`phongBan_id`),
  KEY `FKBC92958F59C86C5A` (`nhanVienThucHien_id`),
  KEY `FKBC92958FB48A6EAD` (`thuTruongPheDuyet_id`),
  CONSTRAINT `FKBC92958F59C86C5A` FOREIGN KEY (`nhanVienThucHien_id`) REFERENCES `User` (`id`),
  CONSTRAINT `FKBC92958F92E7445E` FOREIGN KEY (`phongBan_id`) REFERENCES `PhongBan` (`id`),
  CONSTRAINT `FKBC92958FB48A6EAD` FOREIGN KEY (`thuTruongPheDuyet_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CongVanDen`
--

LOCK TABLES `CongVanDen` WRITE;
/*!40000 ALTER TABLE `CongVanDen` DISABLE KEYS */;
INSERT INTO `CongVanDen` VALUES (1,'6f5c2a8a-0bbe-4881-8c60-7afd1d9baec6|application/pdf',0,'024893009',0,2,'2011-12-04 00:00:00','2011-12-04 00:00:00','132454153',37219873,3,'Công ty AIC','2011-12-18 00:00:00','Đề nghị cung cấp tài liệu quản lý công văn *******',3,'Business logic is managed in the domain model layer. As a client (typically a web browser) cannot directly invoke this code, the functionality of a domain object is exposed as resources represented by URIs.','Phòng hành chính làm nhé;) dn',7,3,6),(2,'3c8f36ef-55cf-4ee5-ab36-b7510e7be937|application/pdf',23,'abctrya34',2,1,'2011-12-04 00:00:00','2011-12-04 00:00:00','132454153',37219873,3,'Công ty AIC','2011-12-19 00:00:00','Đề nghị cung cấp tài liệu quản lý công văn',3,'Every Industrial Designer, Interaction Designer, and Human Factors guru would agree (I hope). To me, that sums what product design should be quite nicely. But, I wonder if the innovation that Don Norman is talking about is for the designers own satisfaction, or for the consumers satisfaction. Consumers, though they might want feature upon feature in a new device, what they need in the end is to be able to use the device effectively – does that mean the device has to be innovative? No, might be as simple and conforming as the next product on the shelf. I think, for Apple, the innovation came when they decided to focus more on basic design (aesthetics) as well as the Interaction Design early on in their products development.','Phòng hành chính thực hiện',7,3,6),(3,'33eae57e-0a71-4a80-8763-61538605d940|application/pdf',0,'3094809329',0,0,'2011-12-04 00:00:00','2011-12-04 00:00:00','132454153',37219873,3,'Công ty AIC','2011-12-19 00:00:00','Đề nghị cung cấp tài liệu quản lý công văn',1,'Đề nghị cung cấp tài liệu quản lý công văn Đề nghị cung cấp tài liệu quản lý công văn Đề nghị cung cấp tài liệu quản lý công văn Đề nghị cung cấp tài liệu quản lý công văn Đề nghị cung cấp tài liệu quản lý công văn Đề nghị cung cấp tài liệu quản lý công văn Đề nghị cung cấp tài liệu quản lý công văn Đề nghị cung cấp tài liệu quản lý công vănĐề nghị cung cấp tài liệu quản lý công vănĐề nghị cung cấp tài liệu quản lý công văn','Phòng KHCN và Môi trường xử lý',NULL,2,6),(4,'99d6cebd-ee22-4c52-a50e-f53388ddf509|application/pdf',0,'3094809329',0,0,'2011-12-05 00:00:00','2011-12-04 00:00:00','132454153',37219873,3,'Công ty AIC','2011-12-19 00:00:00','Đề nghị cung cấp tài liệu quản lý công văn trường SQCTBN',3,'Đề nghị cung cấp tài liệu quản lý công văn trường SQCTBN Đề nghị cung cấp tài liệu quản lý công văn trường SQCTBN Đề nghị cung cấp tài liệu quản lý công văn trường SQCTBN Đề nghị cung cấp tài liệu quản lý công văn trường SQCTBN Đề nghị cung cấp tài liệu quản lý công văn trường SQCTBN Đề nghị cung cấp tài liệu quản lý công văn trường SQCTBN Đề nghị cung cấp tài liệu quản lý công văn trường SQCTBN.','Giao cho phòng KHCN và Môi trường giải quyết',1,2,6),(5,'52638e5d-f5ef-4c1e-b580-f47c0e2dc809|application/msword',0,'sadfsdaf',0,0,'2011-12-24 00:00:00','2011-12-24 00:00:00','123123123',123123123,3,'ssdaf','2011-12-24 00:00:00','asdf',1,'asdfasd fasdf sadf asdf asdf asdf dfsb xczbxcbzxcbzxcb','asdfsdafr',2,1,NULL);
/*!40000 ALTER TABLE `CongVanDen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CongVanDenUser`
--

DROP TABLE IF EXISTS `CongVanDenUser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CongVanDenUser` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `isRead` bit(1) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `congVanDen_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5F4795FAE43D057E` (`congVanDen_id`),
  KEY `FK5F4795FA47140EFE` (`user_id`),
  CONSTRAINT `FK5F4795FA47140EFE` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`),
  CONSTRAINT `FK5F4795FAE43D057E` FOREIGN KEY (`congVanDen_id`) REFERENCES `CongVanDen` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CongVanDenUser`
--

LOCK TABLES `CongVanDenUser` WRITE;
/*!40000 ALTER TABLE `CongVanDenUser` DISABLE KEYS */;
INSERT INTO `CongVanDenUser` VALUES (1,'\0',0,1,6),(2,'',1,1,5),(3,'\0',2,1,7),(4,'\0',3,1,8),(5,'',3,1,1),(6,'\0',0,2,6),(7,'',1,2,5),(8,'',2,2,7),(9,'\0',3,2,8),(10,'\0',0,3,6),(11,'\0',1,3,9),(12,'\0',0,4,6),(13,'',1,4,9),(14,'',2,4,1),(15,'\0',3,4,7),(16,'',2,5,2),(17,'',3,5,1);
/*!40000 ALTER TABLE `CongVanDenUser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CongVanDi`
--

DROP TABLE IF EXISTS `CongVanDi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CongVanDi` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `CongVanDinhKem` varchar(255) DEFAULT NULL,
  `loaiCongVan` varchar(255) DEFAULT NULL,
  `mucDoKhan` int(11) DEFAULT NULL,
  `mucDoMat` int(11) DEFAULT NULL,
  `ngayThangGui` datetime DEFAULT NULL,
  `noiNhan` varchar(255) DEFAULT NULL,
  `soKyHieu` varchar(255) DEFAULT NULL,
  `soTrang` int(11) NOT NULL,
  `tieuDe` varchar(255) DEFAULT NULL,
  `trichYeu` longtext,
  `nguoiPheDuyet_id` bigint(20) DEFAULT NULL,
  `nguoiTao_id` bigint(20) DEFAULT NULL,
  `trangThai` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4825C2C3A72A6889` (`nguoiPheDuyet_id`),
  KEY `FK4825C2C3DF7EE7FD` (`nguoiTao_id`),
  CONSTRAINT `FK4825C2C3A72A6889` FOREIGN KEY (`nguoiPheDuyet_id`) REFERENCES `User` (`id`),
  CONSTRAINT `FK4825C2C3DF7EE7FD` FOREIGN KEY (`nguoiTao_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CongVanDi`
--

LOCK TABLES `CongVanDi` WRITE;
/*!40000 ALTER TABLE `CongVanDi` DISABLE KEYS */;
INSERT INTO `CongVanDi` VALUES (1,'5a3acb8a-064b-42b0-8b17-e3d4843f4ce5|application/vnd.openxmlformats-officedocument.wordprocessingml.document','11231233',1,2,NULL,'Nha Tule','123432432',5,'Họp báo bất thường','Cuộc họp bất thường chiều 22/12 của ban chấp hành VFF đã bác bỏ đơn xin từ chức của Tổng thư ký Trần Quốc Tuấn. Ban chấp hành cũng biểu quyết sa thải HLV Falko Goetz và cho rằng ông là người phải chịu trách nhiệm về thất bại của U23 Việt Nam tại SEA Games 26. \r\nChúng tôi vô cùng thương tiếc báo tin này.',2,1,4);
/*!40000 ALTER TABLE `CongVanDi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CongVanDiUser`
--

DROP TABLE IF EXISTS `CongVanDiUser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CongVanDiUser` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `isRead` bit(1) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `congVanDi_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6659092E47140EFE` (`user_id`),
  KEY `FK6659092E7AFB5036` (`congVanDi_id`),
  CONSTRAINT `FK6659092E47140EFE` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`),
  CONSTRAINT `FK6659092E7AFB5036` FOREIGN KEY (`congVanDi_id`) REFERENCES `CongVanDi` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CongVanDiUser`
--

LOCK TABLES `CongVanDiUser` WRITE;
/*!40000 ALTER TABLE `CongVanDiUser` DISABLE KEYS */;
INSERT INTO `CongVanDiUser` VALUES (1,'',0,1,1),(2,'\0',1,1,9),(3,'\0',2,1,2),(4,'\0',2,1,6),(5,'',3,1,4),(6,'\0',3,1,10);
/*!40000 ALTER TABLE `CongVanDiUser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ElasticSearchSampleModel`
--

DROP TABLE IF EXISTS `ElasticSearchSampleModel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ElasticSearchSampleModel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `field1` varchar(255) DEFAULT NULL,
  `field2` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ElasticSearchSampleModel`
--

LOCK TABLES `ElasticSearchSampleModel` WRITE;
/*!40000 ALTER TABLE `ElasticSearchSampleModel` DISABLE KEYS */;
/*!40000 ALTER TABLE `ElasticSearchSampleModel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FileDinhKem`
--

DROP TABLE IF EXISTS `FileDinhKem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FileDinhKem` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `FileDinhKem` varchar(255) DEFAULT NULL,
  `fileName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FileDinhKem`
--

LOCK TABLES `FileDinhKem` WRITE;
/*!40000 ALTER TABLE `FileDinhKem` DISABLE KEYS */;
/*!40000 ALTER TABLE `FileDinhKem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `HoSo`
--

DROP TABLE IF EXISTS `HoSo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `HoSo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `daLuuTru` bit(1) NOT NULL,
  `hanCheSuDung` varchar(255) DEFAULT NULL,
  `HoSoDaKetThuc` bit(1) NOT NULL,
  `maHoSo` varchar(255) DEFAULT NULL,
  `soLuongTo` int(11) NOT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `thoiGianBatDau` varchar(255) DEFAULT NULL,
  `thoiGianKetThuc` varchar(255) DEFAULT NULL,
  `thoiHanBaoQuan` varchar(255) DEFAULT NULL,
  `tieuDe` varchar(255) DEFAULT NULL,
  `UserId` tinyblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `HoSo`
--

LOCK TABLES `HoSo` WRITE;
/*!40000 ALTER TABLE `HoSo` DISABLE KEYS */;
/*!40000 ALTER TABLE `HoSo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Lich`
--

DROP TABLE IF EXISTS `Lich`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Lich` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `chutri` varchar(255) DEFAULT NULL,
  `diadiem` varchar(255) DEFAULT NULL,
  `end` varchar(255) DEFAULT NULL,
  `noidung` longtext,
  `start` varchar(255) DEFAULT NULL,
  `thanhphan` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Lich`
--

LOCK TABLES `Lich` WRITE;
/*!40000 ALTER TABLE `Lich` DISABLE KEYS */;
INSERT INTO `Lich` VALUES (31,'Design','Illuminated','2011-12-23T13:00:00.000+07:00','Software','2011-12-23T12:30:00.000+07:00','Architecture'),(32,'so may thang','y vay nhi','2011-12-23T14:45:00.000+07:00','Barca','2011-12-23T14:15:00.000+07:00','sao lai');
/*!40000 ALTER TABLE `Lich` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LichTuan`
--

DROP TABLE IF EXISTS `LichTuan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LichTuan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `FileDinhKem` tinyblob,
  `PhongBan` tinyblob,
  `thoiGianBatDau` datetime DEFAULT NULL,
  `thoiGianKetThuc` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LichTuan`
--

LOCK TABLES `LichTuan` WRITE;
/*!40000 ALTER TABLE `LichTuan` DISABLE KEYS */;
/*!40000 ALTER TABLE `LichTuan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PhongBan`
--

DROP TABLE IF EXISTS `PhongBan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PhongBan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ten` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PhongBan`
--

LOCK TABLES `PhongBan` WRITE;
/*!40000 ALTER TABLE `PhongBan` DISABLE KEYS */;
INSERT INTO `PhongBan` VALUES (1,'Ban Giám hiệu'),(2,'Phòng KHCN & Môi trường'),(3,'Văn Phòng');
/*!40000 ALTER TABLE `PhongBan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TinNhan`
--

DROP TABLE IF EXISTS `TinNhan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TinNhan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `noiDung` varchar(255) DEFAULT NULL,
  `nguoiGui_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK14EB6B80CA6703C4` (`nguoiGui_id`),
  CONSTRAINT `FK14EB6B80CA6703C4` FOREIGN KEY (`nguoiGui_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TinNhan`
--

LOCK TABLES `TinNhan` WRITE;
/*!40000 ALTER TABLE `TinNhan` DISABLE KEYS */;
INSERT INTO `TinNhan` VALUES (1,'2011-12-04 14:54:40','Hello world',7),(2,'2011-12-04 14:57:24','hrhr',1),(3,'2011-12-04 14:58:06','hehe',1),(4,'2011-12-05 07:18:28','xin chào.',1),(5,'2011-12-05 14:19:48','Xin chào',1);
/*!40000 ALTER TABLE `TinNhan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TinNhan_FileDinhKem`
--

DROP TABLE IF EXISTS `TinNhan_FileDinhKem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TinNhan_FileDinhKem` (
  `tinNhan_id` bigint(20) NOT NULL,
  `dinhKem_id` bigint(20) NOT NULL,
  UNIQUE KEY `dinhKem_id` (`dinhKem_id`),
  KEY `FK248375595502BE36` (`tinNhan_id`),
  KEY `FK248375595DFD9E5A` (`dinhKem_id`),
  CONSTRAINT `FK248375595502BE36` FOREIGN KEY (`tinNhan_id`) REFERENCES `TinNhan` (`id`),
  CONSTRAINT `FK248375595DFD9E5A` FOREIGN KEY (`dinhKem_id`) REFERENCES `FileDinhKem` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TinNhan_FileDinhKem`
--

LOCK TABLES `TinNhan_FileDinhKem` WRITE;
/*!40000 ALTER TABLE `TinNhan_FileDinhKem` DISABLE KEYS */;
/*!40000 ALTER TABLE `TinNhan_FileDinhKem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TinNhan_NguoiNhan`
--

DROP TABLE IF EXISTS `TinNhan_NguoiNhan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TinNhan_NguoiNhan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `isRead` bit(1) NOT NULL,
  `nguoiNhan_id` bigint(20) DEFAULT NULL,
  `tinNhan_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKC47E931E5502BE36` (`tinNhan_id`),
  KEY `FKC47E931E82BA286C` (`nguoiNhan_id`),
  CONSTRAINT `FKC47E931E5502BE36` FOREIGN KEY (`tinNhan_id`) REFERENCES `TinNhan` (`id`),
  CONSTRAINT `FKC47E931E82BA286C` FOREIGN KEY (`nguoiNhan_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TinNhan_NguoiNhan`
--

LOCK TABLES `TinNhan_NguoiNhan` WRITE;
/*!40000 ALTER TABLE `TinNhan_NguoiNhan` DISABLE KEYS */;
INSERT INTO `TinNhan_NguoiNhan` VALUES (2,'',7,1),(5,'\0',7,3),(7,'',9,5);
/*!40000 ALTER TABLE `TinNhan_NguoiNhan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `capBac` int(11) DEFAULT NULL,
  `ho` varchar(255) DEFAULT NULL,
  `isActive` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phanQuyen` int(11) DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `tenDem` varchar(255) DEFAULT NULL,
  `Username` varchar(255) DEFAULT NULL,
  `chucVu_id` bigint(20) DEFAULT NULL,
  `phongBan_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK285FEB598F4BE` (`chucVu_id`),
  KEY `FK285FEB92E7445E` (`phongBan_id`),
  CONSTRAINT `FK285FEB598F4BE` FOREIGN KEY (`chucVu_id`) REFERENCES `ChucVu` (`id`),
  CONSTRAINT `FK285FEB92E7445E` FOREIGN KEY (`phongBan_id`) REFERENCES `PhongBan` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,5,'Tô','','e10adc3949ba59abbe56e057f20f883e',4,'Anh','Việt','anhtv',6,2),(2,12,'Trần','','e10adc3949ba59abbe56e057f20f883e',1,'B','Văn','tranvanb',2,1),(3,0,'Nguyễn','','e10adc3949ba59abbe56e057f20f883e',4,'A','Văn','nguyenvana',6,3),(4,8,'Nguyễn','','e10adc3949ba59abbe56e057f20f883e',0,'Trang','Minh','vanthu',6,3),(5,12,'Lê','','e10adc3949ba59abbe56e057f20f883e',2,'Công','Văn','truongphong',4,3),(6,13,'Nguyễn','','e10adc3949ba59abbe56e057f20f883e',1,'Tùng','Văn','chihuy',1,1),(7,7,'Nguyễn','','e10adc3949ba59abbe56e057f20f883e',4,'Linh','Tuấn','nhanvien',6,3),(8,5,'Nguyễn','','e10adc3949ba59abbe56e057f20f883e',4,'Hiếu','Trung','nhanvien2',6,3),(9,13,'Lê','','e10adc3949ba59abbe56e057f20f883e',2,'Làm','Văn','levanlam',4,2),(10,13,'Phùng','','e10adc3949ba59abbe56e057f20f883e',0,'Thiết','Văn','phungvanthiet',1,1);
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-01-06 13:26:46
