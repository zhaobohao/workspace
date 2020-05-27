DROP TABLE IF EXISTS `member`;

CREATE TABLE `members` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `app_id` varchar(255) DEFAULT NULL,
  `group_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;

INSERT INTO `members` (`id`, `create_time`, `update_time`, `ip`, `name`, `app_id`, `group_id`)
VALUES
	(1,'2018-03-19 14:37:26','2018-03-19 14:37:28','192.168.1.75','maida','wolf','1'),
	(2,'2018-03-19 14:37:26','2018-03-19 14:37:28','192.168.1.121','mingyi','mingyi','1');

/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table member_group
# ------------------------------------------------------------

DROP TABLE IF EXISTS `member_group`;

CREATE TABLE `member_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `group_id` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `member_group` WRITE;
/*!40000 ALTER TABLE `member_group` DISABLE KEYS */;

INSERT INTO `member_group` (`id`, `create_time`, `update_time`, `group_id`, `name`)
VALUES
	(1,'2018-03-19 14:37:08','2018-03-19 14:37:10','1','group1');

/*!40000 ALTER TABLE `member_group` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table permission
# ------------------------------------------------------------

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `group_id` varchar(255) DEFAULT NULL,
  `operation` tinyint(4) NOT NULL,
  `public_key` varchar(255) DEFAULT NULL,
  `table_name` varchar(255) DEFAULT NULL,
  `permission_type` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;

INSERT INTO `permission` (`id`, `create_time`, `update_time`, `group_id`, `operation`, `public_key`, `table_name`, `permission_type`)
VALUES
	(1,'2018-03-19 14:37:26','2018-03-19 14:37:26','1',2,'*','message',2);

/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;