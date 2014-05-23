DROP DATABASE IF EXISTS `eruserlist`;
CREATE DATABASE `eruserlist` DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;
USE `eruserlist`;

CREATE TABLE `userlist` (
  `id` int(11) NOT NULL auto_increment,
  `emailaddress` varchar(255) NOT NULL,
  `added_on` datetime NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `salt` varchar(255) NOT NULL,
PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci AUTO_INCREMENT = 4;

INSERT INTO `userlist` VALUES (1, 'michael.chen1992@gmail.com', now(), 'test', 'test', 'test' );
