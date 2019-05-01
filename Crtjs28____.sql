-- phpMyAdmin SQL Dump
-- version 4.0.10.17
-- https://www.phpmyadmin.net
--
-- 主机: mysql.dur.ac.uk
-- 生成日期: 2019-04-29 06:39:30
-- 服务器版本: 10.1.19-MariaDB
-- PHP 版本: 5.3.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `Crtjs28_...`
--

-- --------------------------------------------------------

--
-- 表的结构 `administrators`
--

CREATE TABLE IF NOT EXISTS `administrators` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `password` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- 转存表中的数据 `administrators`
--

INSERT INTO `administrators` (`id`, `username`, `password`) VALUES
(1, 'chang', '123');

-- --------------------------------------------------------

--
-- 表的结构 `authors`
--

CREATE TABLE IF NOT EXISTS `authors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `surname` text,
  `idbook` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idbook` (`idbook`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=50 ;

--
-- 转存表中的数据 `authors`
--

INSERT INTO `authors` (`id`, `name`, `surname`, `idbook`) VALUES
(36, 'J K', 'Rowling', 642436),
(44, 'R', 'Stewart', 811986),
(47, 'Lederer', 'Helen', 303344),
(48, 'King', 'Andrew', 961613),
(49, 'Lim', 'Montgomery', 848029);

-- --------------------------------------------------------

--
-- 表的结构 `books`
--

CREATE TABLE IF NOT EXISTS `books` (
  `id` int(11) NOT NULL,
  `title` text NOT NULL,
  `year` int(11) DEFAULT NULL,
  `publisher` text,
  `copies` int(11) NOT NULL DEFAULT '0',
  `publication_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- 转存表中的数据 `books`
--

INSERT INTO `books` (`id`, `title`, `year`, `publisher`, `copies`, `publication_date`) VALUES
(303344, 'The worry website', 2009, 'Corgi Yearing', 5, '2009-11-30'),
(642436, 'Harry Potter I', 1999, 'Wizard Editions', 2, '1999-01-01'),
(811986, 'Calculus', 2011, 'McGraw-Hill', 10, '2002-02-02'),
(848029, 'The story girl', 2005, 'Penguin', 5, '1996-04-03'),
(961613, 'Creating a website', 2008, 'Pretice Hall', 7, '1994-04-03');

-- --------------------------------------------------------

--
-- 表的结构 `loans`
--

CREATE TABLE IF NOT EXISTS `loans` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_user` int(11) NOT NULL,
  `id_book` int(11) NOT NULL,
  `borrowed_date` date NOT NULL,
  `due_date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_user` (`id_user`,`id_book`),
  KEY `id_book` (`id_book`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=27 ;

--
-- 转存表中的数据 `loans`
--

INSERT INTO `loans` (`id`, `id_user`, `id_book`, `borrowed_date`, `due_date`) VALUES
(17, 16, 303344, '2019-04-28', '2019-05-28'),
(18, 16, 642436, '2019-04-28', '2019-05-28'),
(19, 16, 848029, '2019-04-28', '2019-05-28'),
(20, 17, 848029, '2019-04-28', '2019-05-28'),
(21, 17, 642436, '2019-04-28', '2019-05-28'),
(22, 18, 303344, '2019-04-28', '2019-05-28'),
(23, 18, 961613, '2019-04-28', '2019-05-28'),
(24, 19, 811986, '2019-04-28', '2019-05-28'),
(25, 20, 848029, '2019-04-28', '2019-05-28'),
(26, 22, 848029, '2019-04-28', '2019-05-28');

-- --------------------------------------------------------

--
-- 表的结构 `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `password` text NOT NULL,
  `firstname` text,
  `surname` text NOT NULL,
  `house_number` text,
  `street_name` text NOT NULL,
  `postcode` tinytext NOT NULL,
  `email` varchar(64) NOT NULL,
  `birthday` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`,`email`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=23 ;

--
-- 转存表中的数据 `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `firstname`, `surname`, `house_number`, `street_name`, `postcode`, `email`, `birthday`) VALUES
(16, 'ChangTan', '123123', 'chang', 'tan', '208D', 'newcastle road', 'DH14GD', 'chang.tan2@durham.ac.uk', '1994-11-30'),
(17, 'Michael', '123123', 'Ming', 'Li', '108C', 'Ave road', 'DH12JE', 'Michael@durham.ac.uk', '1994-09-28'),
(18, 'Bigrabbit', '123456', 'Cheng', 'Chang', '108D', 'ranny', 'DH15GD', 'chengchang@durham.ac.uk', '1995-04-22'),
(19, 'Philip', '123456', 'John', 'Phil', '304C', 'dragon', 'DH15GC', 'Johnphil@durham.ac.uk', '1996-06-27'),
(20, 'Compton', '123456', 'Barrett', 'Compton', '307B', 'manchester road', 'DH13PE', 'Barret@durham.ac.uk', '1993-11-09'),
(22, 'Kellie', '123456', 'Sia', 'Lyun', '540', 'renming', 'DE54FC', 'sia@durham.ac.uk', '1994-08-24');

--
-- 限制导出的表
--

--
-- 限制表 `authors`
--
ALTER TABLE `authors`
  ADD CONSTRAINT `FK_authors_book` FOREIGN KEY (`idbook`) REFERENCES `books` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- 限制表 `loans`
--
ALTER TABLE `loans`
  ADD CONSTRAINT `FK_books_loan` FOREIGN KEY (`id_book`) REFERENCES `books` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_users_loan` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
