-- phpMyAdmin SQL Dump
-- version 4.4.10
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Lun 01 Février 2016 à 11:19
-- Version du serveur :  5.5.42
-- Version de PHP :  7.0.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Base de données :  `pilpoil`
--

-- --------------------------------------------------------

--
-- Structure de la table `ad`
--

CREATE TABLE `ad` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `recover` bit(1) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `adress` varchar(255) DEFAULT NULL,
  `complement` varchar(255) DEFAULT NULL,
  `postal_code` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `lattitude` varchar(255) DEFAULT NULL,
  `ad_type_id` bigint(20) DEFAULT NULL,
  `animal_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `ad`
--

INSERT INTO `ad` (`id`, `description`, `recover`, `date`, `phone`, `email`, `adress`, `complement`, `postal_code`, `city`, `country`, `longitude`, `lattitude`, `ad_type_id`, `animal_id`, `user_id`) VALUES
(1, 'Chat perdu près du veto à Mérignac le 07/01 à 18H', NULL, '07/01/2016', NULL, NULL, '9 avenue de la libération', NULL, '33700', 'Mérignac', 'France', '-0.6455985999999712', '44.8429936', 1, 3, 8),
(2, 'Mon chat à sauté du balcon le 20/09/2015', NULL, '21/09/2015', NULL, NULL, '18 Avenue du Chut', 'Domaine de Valois', '33700', 'Mérignac', 'France', '-0.6597447000000329', '44.84913179999999', 3, 2, 8),
(3, 'Mon chien a fait un trou dans le grillage pendant la nuit et c''est enfuit', NULL, '20/01/2016', NULL, NULL, '420 Rue du Grand Barrail', NULL, '33127', 'Saint-Jean-d''Illac', 'France', '-0.8493601000000126', '44.8068572', 1, 7, 7),
(4, 'Chat retrouvé avenue de la libération, je l''ai trouvé en pleine nuit et l''ai amené chez moi', b'1', '08/01/2016', NULL, '', '30 avenue de la libération', '', '33700', 'Mérignac', 'France', '-0.6447370999999293', '44.84533239999999', 2, 8, 9),
(5, NULL, NULL, '20/01/2016', NULL, NULL, '391 Rue de Vaugirard', NULL, '75015', 'Paris', 'France', '2.292319799999973', '48.8347839', 2, 9, NULL),
(6, NULL, NULL, '10/01/2016', '0669553011', NULL, '18 Rue des Bouleaux', NULL, '33600', 'Pessac', 'France', '-0.688220699999988', '44.7973314', 2, 10, NULL),
(7, NULL, b'1', '20/01/2014', '0669553011', 'thibault@free.fr', '30 avenue de la libération', NULL, '33700', 'Mérignac', 'France', '-0.6447370999999293', '44.84533239999999', 2, 11, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `ad_type`
--

CREATE TABLE `ad_type` (
  `id` bigint(20) NOT NULL,
  `label` varchar(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `ad_type`
--

INSERT INTO `ad_type` (`id`, `label`) VALUES
(1, 'Perdu'),
(2, 'Trouvé'),
(3, 'Archivé');

-- --------------------------------------------------------

--
-- Structure de la table `animal`
--

CREATE TABLE `animal` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `age` varchar(255) DEFAULT NULL,
  `sexe` varchar(255) DEFAULT NULL,
  `tatoo` varchar(255) DEFAULT NULL,
  `chip` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `breed_id` bigint(20) DEFAULT NULL,
  `animal_type_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `animal`
--

INSERT INTO `animal` (`id`, `name`, `age`, `sexe`, `tatoo`, `chip`, `photo`, `breed_id`, `animal_type_id`, `user_id`) VALUES
(1, 'Lumia', 'Jeune', 'Male', 'TATOO123ED', NULL, 'http://static.wamiz.fr/images/news/medium/chat-tabby.jpg', 9, 1, 5),
(2, 'Gollum', 'Adulte', 'Male', 'TATOO145TG', NULL, NULL, 3, 1, 8),
(3, 'Chatchat', 'Adulte', 'Femelle', 'TATOO156TY', NULL, 'http://fr.cdn.v5.futura-sciences.com/sources/images/dossier/rte/magic/4111_je_siamois.jpg', 3, 1, 8),
(4, 'Poisson', '', NULL, NULL, NULL, NULL, NULL, 3, 8),
(5, 'ChienChien', 'Adulte', 'Male', NULL, NULL, 'http://static.wamiz.fr/images/news/additional/2013-05/large/berger-australien-choix-race-chien.jpg', 14, 2, 6),
(6, 'LeChat', NULL, NULL, NULL, NULL, NULL, NULL, 1, 7),
(7, 'LeChien', NULL, NULL, NULL, NULL, NULL, NULL, 2, 7),
(8, NULL, NULL, 'Femelle', NULL, NULL, 'https://upload.wikimedia.org/wikipedia/commons/thumb/4/48/Chronos_4_mois.jpg/220px-Chronos_4_mois.jpg', 3, 1, NULL),
(9, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL),
(10, NULL, 'Jeune', 'Male', NULL, NULL, NULL, 6, 1, NULL),
(11, NULL, 'Adulte', 'Male', 'TATEE125ED', NULL, 'http://www.chatsnoirs.com/medias/images/europeen-noir.jpg', NULL, 1, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `animal_color`
--

CREATE TABLE `animal_color` (
  `colors_id` bigint(20) NOT NULL,
  `animals_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `animal_color`
--

INSERT INTO `animal_color` (`colors_id`, `animals_id`) VALUES
(1, 2),
(1, 3),
(1, 8),
(2, 5),
(2, 11),
(3, 2),
(3, 3),
(3, 4),
(3, 8),
(4, 1),
(4, 10),
(6, 1),
(6, 10),
(7, 5);

-- --------------------------------------------------------

--
-- Structure de la table `animal_type`
--

CREATE TABLE `animal_type` (
  `id` bigint(20) NOT NULL,
  `label` varchar(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `animal_type`
--

INSERT INTO `animal_type` (`id`, `label`) VALUES
(1, 'Chat'),
(2, 'Chien'),
(3, 'Autre');

-- --------------------------------------------------------

--
-- Structure de la table `animal_type_breed`
--

CREATE TABLE `animal_type_breed` (
  `breeds_id` bigint(20) NOT NULL,
  `animal_types_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `animal_type_breed`
--

INSERT INTO `animal_type_breed` (`breeds_id`, `animal_types_id`) VALUES
(1, 2),
(2, 2),
(3, 1),
(4, 1),
(5, 1),
(6, 1),
(7, 1),
(8, 1),
(9, 1),
(10, 1),
(11, 1),
(12, 1),
(13, 2),
(14, 2),
(15, 2),
(16, 2),
(17, 2),
(18, 2),
(19, 2),
(20, 2),
(21, 2);

-- --------------------------------------------------------

--
-- Structure de la table `breed`
--

CREATE TABLE `breed` (
  `id` bigint(20) NOT NULL,
  `label` varchar(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `breed`
--

INSERT INTO `breed` (`id`, `label`) VALUES
(1, 'Chihuahua'),
(2, 'Teckel'),
(3, 'Siamois'),
(4, 'Isabelle'),
(5, 'Maine Coon'),
(6, 'Bengal'),
(7, 'Angora'),
(8, 'Ragdoll'),
(9, 'Européen'),
(10, 'Chartreux'),
(11, 'Persan'),
(12, 'Birman'),
(13, 'Akita Inu'),
(14, 'Berger Blanc'),
(15, 'Golden Retriever'),
(16, 'Beauceron'),
(17, 'Bouledogue'),
(18, 'Yorkshire'),
(19, 'Pinscher'),
(20, 'Boxer'),
(21, 'Jack Russell');

-- --------------------------------------------------------

--
-- Structure de la table `color`
--

CREATE TABLE `color` (
  `id` bigint(20) NOT NULL,
  `label` varchar(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `color`
--

INSERT INTO `color` (`id`, `label`) VALUES
(1, 'Beige'),
(2, 'Noir'),
(3, 'Blanc'),
(4, 'Gris'),
(5, 'Vert'),
(6, 'Tigré'),
(7, 'Roux'),
(8, 'Jaune');

-- --------------------------------------------------------

--
-- Structure de la table `color_animal_type`
--

CREATE TABLE `color_animal_type` (
  `animal_types_id` bigint(20) NOT NULL,
  `colors_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `color_animal_type`
--

INSERT INTO `color_animal_type` (`animal_types_id`, `colors_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 6),
(1, 7),
(2, 1),
(2, 2),
(2, 3),
(2, 4),
(3, 1),
(3, 2),
(3, 3),
(3, 4),
(3, 5),
(3, 8);

-- --------------------------------------------------------

--
-- Structure de la table `DATABASECHANGELOG`
--

CREATE TABLE `DATABASECHANGELOG` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `DATABASECHANGELOG`
--

INSERT INTO `DATABASECHANGELOG` (`ID`, `AUTHOR`, `FILENAME`, `DATEEXECUTED`, `ORDEREXECUTED`, `EXECTYPE`, `MD5SUM`, `DESCRIPTION`, `COMMENTS`, `TAG`, `LIQUIBASE`, `CONTEXTS`, `LABELS`) VALUES
('00000000000001', 'jhipster', 'classpath:config/liquibase/changelog/00000000000000_initial_schema.xml', '2016-01-16 23:13:34', 1, 'EXECUTED', '7:db3ea54c411eee3e3a699d954d8ce557', 'createTable, createIndex (x2), createTable (x2), addPrimaryKey, createTable, addForeignKeyConstraint (x3), loadData, dropDefaultValue, loadData (x2), createTable (x2), addPrimaryKey, createIndex (x2), addForeignKeyConstraint', '', NULL, '3.4.1', NULL, NULL),
('20151211215522', 'jhipster', 'classpath:config/liquibase/changelog/20151211215522_added_entity_Breed.xml', '2016-01-16 23:13:34', 2, 'EXECUTED', '7:ce2ef532ebbffdb8b0e438970a730f3d', 'createTable', '', NULL, '3.4.1', NULL, NULL),
('20151211215522', 'jhipster', 'classpath:config/liquibase/changelog/20151211215522_added_entity_AdType.xml', '2016-01-16 23:13:34', 3, 'EXECUTED', '7:0b16ce727cc4529ff935efbf2582f3fa', 'createTable', '', NULL, '3.4.1', NULL, NULL),
('20151211215522', 'jhipster', 'classpath:config/liquibase/changelog/20151211215522_added_entity_AnimalType.xml', '2016-01-16 23:13:34', 4, 'EXECUTED', '7:3d5d3370cad121a6371fe34730355eca', 'createTable (x2), addPrimaryKey, addForeignKeyConstraint (x2)', '', NULL, '3.4.1', NULL, NULL),
('20151211215522', 'jhipster', 'classpath:config/liquibase/changelog/20151211215522_added_entity_Color.xml', '2016-01-16 23:13:34', 5, 'EXECUTED', '7:6af6a2b5119f6dd91caaed3fb656c4c3', 'createTable (x2), addPrimaryKey, addForeignKeyConstraint (x2)', '', NULL, '3.4.1', NULL, NULL),
('20151211215522', 'jhipster', 'classpath:config/liquibase/changelog/20151211215522_added_entity_Animal.xml', '2016-01-16 23:13:35', 6, 'EXECUTED', '7:0d24396c1d409222d3fd57acdfcac759', 'createTable, addForeignKeyConstraint (x2), createTable, addPrimaryKey, addForeignKeyConstraint (x2)', '', NULL, '3.4.1', NULL, NULL),
('20151211215522', 'jhipster', 'classpath:config/liquibase/changelog/20151211215522_added_entity_Ad.xml', '2016-01-16 23:13:35', 7, 'EXECUTED', '7:24485ffbb55c65b5ce65cb7f07cf3246', 'createTable, addForeignKeyConstraint (x2)', '', NULL, '3.4.1', NULL, NULL),
('20151211215522', 'yanick', 'classpath:config/liquibase/changelog/20151211232200_added_user_column.xml', '2016-01-16 23:13:35', 8, 'EXECUTED', '7:01c96c9aee0abe42fd9b33b8a9c06400', 'addColumn (x2)', '', NULL, '3.4.1', NULL, NULL),
('20151212111000', 'yanick', 'classpath:config/liquibase/changelog/20151212111000_added_user_relationship.xml', '2016-01-16 23:13:35', 9, 'EXECUTED', '7:cf6f44b1b788b865c979ef0932052cbc', 'addColumn, addForeignKeyConstraint, addColumn, addForeignKeyConstraint', '', NULL, '3.4.1', NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `DATABASECHANGELOGLOCK`
--

CREATE TABLE `DATABASECHANGELOGLOCK` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `DATABASECHANGELOGLOCK`
--

INSERT INTO `DATABASECHANGELOGLOCK` (`ID`, `LOCKED`, `LOCKGRANTED`, `LOCKEDBY`) VALUES
(1, b'0', NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `jhi_authority`
--

CREATE TABLE `jhi_authority` (
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `jhi_authority`
--

INSERT INTO `jhi_authority` (`name`) VALUES
('ROLE_ADMIN'),
('ROLE_USER');

-- --------------------------------------------------------

--
-- Structure de la table `jhi_persistent_audit_event`
--

CREATE TABLE `jhi_persistent_audit_event` (
  `event_id` bigint(20) NOT NULL,
  `principal` varchar(255) NOT NULL,
  `event_date` timestamp NULL DEFAULT NULL,
  `event_type` varchar(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=194 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `jhi_persistent_audit_event`
--

INSERT INTO `jhi_persistent_audit_event` (`event_id`, `principal`, `event_date`, `event_type`) VALUES
(1, 'admin', '2016-01-16 22:14:20', 'AUTHENTICATION_SUCCESS'),
(2, 'admin', '2016-01-16 22:14:20', 'AUTHENTICATION_SUCCESS'),
(3, 'admin', '2016-01-16 22:14:20', 'AUTHENTICATION_SUCCESS'),
(4, 'admin', '2016-01-18 10:26:06', 'AUTHENTICATION_SUCCESS'),
(5, 'admin', '2016-01-18 10:37:56', 'AUTHENTICATION_SUCCESS'),
(6, 'admin', '2016-01-18 10:37:56', 'AUTHENTICATION_SUCCESS'),
(7, 'admin', '2016-01-18 10:37:56', 'AUTHENTICATION_SUCCESS'),
(8, 'yanick', '2016-01-18 10:48:45', 'AUTHENTICATION_SUCCESS'),
(9, 'yanick', '2016-01-18 10:48:45', 'AUTHENTICATION_SUCCESS'),
(10, 'yanick', '2016-01-18 10:48:45', 'AUTHENTICATION_SUCCESS'),
(11, 'yanick', '2016-01-18 10:57:17', 'AUTHENTICATION_SUCCESS'),
(12, 'yanick', '2016-01-18 10:57:17', 'AUTHENTICATION_SUCCESS'),
(13, 'yanick', '2016-01-18 10:57:17', 'AUTHENTICATION_SUCCESS'),
(14, 'yanick', '2016-01-18 10:59:23', 'AUTHENTICATION_SUCCESS'),
(15, 'yanick', '2016-01-18 10:59:23', 'AUTHENTICATION_SUCCESS'),
(16, 'yanick', '2016-01-18 10:59:23', 'AUTHENTICATION_SUCCESS'),
(17, 'yanick', '2016-01-18 11:01:09', 'AUTHENTICATION_SUCCESS'),
(18, 'yanick', '2016-01-18 11:01:09', 'AUTHENTICATION_SUCCESS'),
(19, 'yanick', '2016-01-18 11:01:09', 'AUTHENTICATION_SUCCESS'),
(20, 'yanick', '2016-01-18 11:03:55', 'AUTHENTICATION_SUCCESS'),
(21, 'yanick', '2016-01-18 11:03:55', 'AUTHENTICATION_SUCCESS'),
(22, 'yanick', '2016-01-18 11:03:55', 'AUTHENTICATION_SUCCESS'),
(23, 'yanick', '2016-01-18 11:05:30', 'AUTHENTICATION_SUCCESS'),
(24, 'yanick', '2016-01-18 11:05:30', 'AUTHENTICATION_SUCCESS'),
(25, 'yanick', '2016-01-18 11:05:30', 'AUTHENTICATION_SUCCESS'),
(26, 'yanick', '2016-01-18 11:11:12', 'AUTHENTICATION_SUCCESS'),
(27, 'yanick', '2016-01-18 11:11:12', 'AUTHENTICATION_SUCCESS'),
(28, 'yanick', '2016-01-18 11:11:12', 'AUTHENTICATION_SUCCESS'),
(29, 'admin', '2016-01-23 14:36:35', 'AUTHENTICATION_SUCCESS'),
(30, 'yanick', '2016-01-23 14:38:31', 'AUTHENTICATION_SUCCESS'),
(31, 'yanick', '2016-01-23 14:38:31', 'AUTHENTICATION_SUCCESS'),
(32, 'yanick', '2016-01-23 14:38:31', 'AUTHENTICATION_SUCCESS'),
(33, 'admin', '2016-01-23 14:40:26', 'AUTHENTICATION_SUCCESS'),
(34, 'admin', '2016-01-23 14:40:26', 'AUTHENTICATION_SUCCESS'),
(35, 'admin', '2016-01-23 14:40:26', 'AUTHENTICATION_SUCCESS'),
(36, 'admin', '2016-01-23 15:08:26', 'AUTHENTICATION_SUCCESS'),
(37, 'admin', '2016-01-23 15:13:09', 'AUTHENTICATION_SUCCESS'),
(38, 'admin', '2016-01-23 15:13:09', 'AUTHENTICATION_SUCCESS'),
(39, 'admin', '2016-01-23 15:13:09', 'AUTHENTICATION_SUCCESS'),
(40, 'admin', '2016-01-23 15:15:45', 'AUTHENTICATION_SUCCESS'),
(41, 'admin', '2016-01-23 15:15:45', 'AUTHENTICATION_SUCCESS'),
(42, 'admin', '2016-01-23 15:15:45', 'AUTHENTICATION_SUCCESS'),
(43, 'admin', '2016-01-23 16:00:53', 'AUTHENTICATION_SUCCESS'),
(44, 'admin', '2016-01-23 16:00:53', 'AUTHENTICATION_SUCCESS'),
(45, 'admin', '2016-01-23 16:00:53', 'AUTHENTICATION_SUCCESS'),
(46, 'admin', '2016-01-23 16:34:26', 'AUTHENTICATION_SUCCESS'),
(47, 'yanick', '2016-01-23 16:35:15', 'AUTHENTICATION_SUCCESS'),
(48, 'yanick', '2016-01-23 16:35:15', 'AUTHENTICATION_SUCCESS'),
(49, 'yanick', '2016-01-23 16:35:15', 'AUTHENTICATION_SUCCESS'),
(50, 'yanick', '2016-01-23 16:38:29', 'AUTHENTICATION_SUCCESS'),
(51, 'yanick', '2016-01-23 16:38:29', 'AUTHENTICATION_SUCCESS'),
(52, 'yanick', '2016-01-23 16:38:29', 'AUTHENTICATION_SUCCESS'),
(53, 'yanick', '2016-01-23 16:41:40', 'AUTHENTICATION_SUCCESS'),
(54, 'yanick', '2016-01-23 16:41:40', 'AUTHENTICATION_SUCCESS'),
(55, 'yanick', '2016-01-23 16:41:40', 'AUTHENTICATION_SUCCESS'),
(56, 'xavier', '2016-01-23 16:44:39', 'AUTHENTICATION_SUCCESS'),
(57, 'xavier', '2016-01-23 16:44:39', 'AUTHENTICATION_SUCCESS'),
(58, 'xavier', '2016-01-23 16:44:39', 'AUTHENTICATION_SUCCESS'),
(59, 'xavier', '2016-01-23 16:45:02', 'AUTHENTICATION_SUCCESS'),
(60, 'xavier', '2016-01-23 16:45:02', 'AUTHENTICATION_SUCCESS'),
(61, 'xavier', '2016-01-23 16:45:02', 'AUTHENTICATION_SUCCESS'),
(62, 'admin', '2016-01-23 16:45:58', 'AUTHENTICATION_SUCCESS'),
(63, 'admin', '2016-01-23 16:45:58', 'AUTHENTICATION_SUCCESS'),
(64, 'admin', '2016-01-23 16:45:58', 'AUTHENTICATION_SUCCESS'),
(65, 'xavier', '2016-01-23 16:46:21', 'AUTHENTICATION_SUCCESS'),
(66, 'xavier', '2016-01-23 16:46:21', 'AUTHENTICATION_SUCCESS'),
(67, 'xavier', '2016-01-23 16:46:21', 'AUTHENTICATION_SUCCESS'),
(68, 'thomas', '2016-01-23 16:46:52', 'AUTHENTICATION_SUCCESS'),
(69, 'thomas', '2016-01-23 16:46:52', 'AUTHENTICATION_SUCCESS'),
(70, 'thomas', '2016-01-23 16:46:52', 'AUTHENTICATION_SUCCESS'),
(71, 'xavier', '2016-01-23 16:47:33', 'AUTHENTICATION_SUCCESS'),
(72, 'xavier', '2016-01-23 16:47:33', 'AUTHENTICATION_SUCCESS'),
(73, 'xavier', '2016-01-23 16:47:33', 'AUTHENTICATION_SUCCESS'),
(74, 'lucas', '2016-01-23 16:47:48', 'AUTHENTICATION_SUCCESS'),
(75, 'lucas', '2016-01-23 16:47:48', 'AUTHENTICATION_SUCCESS'),
(76, 'lucas', '2016-01-23 16:47:48', 'AUTHENTICATION_SUCCESS'),
(77, 'lucas', '2016-01-23 16:49:15', 'AUTHENTICATION_SUCCESS'),
(78, 'lucas', '2016-01-23 16:49:15', 'AUTHENTICATION_SUCCESS'),
(79, 'lucas', '2016-01-23 16:49:15', 'AUTHENTICATION_SUCCESS'),
(80, 'lucas', '2016-01-23 16:54:45', 'AUTHENTICATION_SUCCESS'),
(81, 'lucas', '2016-01-23 16:54:45', 'AUTHENTICATION_SUCCESS'),
(82, 'lucas', '2016-01-23 16:54:45', 'AUTHENTICATION_SUCCESS'),
(83, 'lucas', '2016-01-23 16:57:44', 'AUTHENTICATION_SUCCESS'),
(84, 'lucas', '2016-01-23 16:57:44', 'AUTHENTICATION_SUCCESS'),
(85, 'lucas', '2016-01-23 16:57:44', 'AUTHENTICATION_SUCCESS'),
(86, 'xavier', '2016-01-23 16:58:39', 'AUTHENTICATION_SUCCESS'),
(87, 'xavier', '2016-01-23 16:58:39', 'AUTHENTICATION_SUCCESS'),
(88, 'xavier', '2016-01-23 16:58:39', 'AUTHENTICATION_SUCCESS'),
(89, 'xavier', '2016-01-23 17:00:59', 'AUTHENTICATION_SUCCESS'),
(90, 'xavier', '2016-01-23 17:00:59', 'AUTHENTICATION_SUCCESS'),
(91, 'xavier', '2016-01-23 17:00:59', 'AUTHENTICATION_SUCCESS'),
(92, 'lucas', '2016-01-23 17:35:47', 'AUTHENTICATION_SUCCESS'),
(93, 'lucas', '2016-01-23 17:35:47', 'AUTHENTICATION_SUCCESS'),
(94, 'lucas', '2016-01-23 17:35:47', 'AUTHENTICATION_SUCCESS'),
(95, 'admin', '2016-01-23 17:36:35', 'AUTHENTICATION_SUCCESS'),
(96, 'admin', '2016-01-23 17:42:35', 'AUTHENTICATION_SUCCESS'),
(97, 'lucas', '2016-01-23 17:51:17', 'AUTHENTICATION_SUCCESS'),
(98, 'lucas', '2016-01-23 17:51:17', 'AUTHENTICATION_SUCCESS'),
(99, 'lucas', '2016-01-23 17:51:17', 'AUTHENTICATION_SUCCESS'),
(100, 'lucas', '2016-01-23 18:02:41', 'AUTHENTICATION_SUCCESS'),
(101, 'lucas', '2016-01-23 18:02:41', 'AUTHENTICATION_SUCCESS'),
(102, 'lucas', '2016-01-23 18:02:41', 'AUTHENTICATION_SUCCESS'),
(103, 'xavier', '2016-01-23 18:26:15', 'AUTHENTICATION_FAILURE'),
(104, 'xavier', '2016-01-23 18:26:30', 'AUTHENTICATION_SUCCESS'),
(105, 'xavier', '2016-01-23 18:26:30', 'AUTHENTICATION_SUCCESS'),
(106, 'xavier', '2016-01-23 18:26:30', 'AUTHENTICATION_SUCCESS'),
(107, 'xavier', '2016-01-23 18:30:17', 'AUTHENTICATION_SUCCESS'),
(108, 'xavier', '2016-01-23 18:30:17', 'AUTHENTICATION_SUCCESS'),
(109, 'xavier', '2016-01-23 18:30:17', 'AUTHENTICATION_SUCCESS'),
(110, 'xavier', '2016-01-23 18:35:34', 'AUTHENTICATION_SUCCESS'),
(111, 'xavier', '2016-01-23 18:35:34', 'AUTHENTICATION_SUCCESS'),
(112, 'xavier', '2016-01-23 18:35:34', 'AUTHENTICATION_SUCCESS'),
(113, 'xavier', '2016-01-23 18:49:47', 'AUTHENTICATION_SUCCESS'),
(114, 'xavier', '2016-01-23 18:49:48', 'AUTHENTICATION_SUCCESS'),
(115, 'xavier', '2016-01-23 18:49:48', 'AUTHENTICATION_SUCCESS'),
(116, 'admin', '2016-01-23 18:50:33', 'AUTHENTICATION_SUCCESS'),
(117, 'xavier', '2016-01-23 18:54:33', 'AUTHENTICATION_SUCCESS'),
(118, 'xavier', '2016-01-23 18:54:33', 'AUTHENTICATION_SUCCESS'),
(119, 'xavier', '2016-01-23 18:54:33', 'AUTHENTICATION_SUCCESS'),
(120, 'xavier', '2016-01-23 19:11:42', 'AUTHENTICATION_SUCCESS'),
(121, 'xavier', '2016-01-23 19:11:42', 'AUTHENTICATION_SUCCESS'),
(122, 'xavier', '2016-01-23 19:11:42', 'AUTHENTICATION_SUCCESS'),
(123, 'xavier', '2016-01-23 19:15:14', 'AUTHENTICATION_SUCCESS'),
(124, 'xavier', '2016-01-23 19:15:14', 'AUTHENTICATION_SUCCESS'),
(125, 'xavier', '2016-01-23 19:15:14', 'AUTHENTICATION_SUCCESS'),
(126, 'xavier', '2016-01-23 19:19:01', 'AUTHENTICATION_SUCCESS'),
(127, 'xavier', '2016-01-23 19:19:01', 'AUTHENTICATION_SUCCESS'),
(128, 'xavier', '2016-01-23 19:19:01', 'AUTHENTICATION_SUCCESS'),
(129, 'xavier', '2016-01-23 19:32:42', 'AUTHENTICATION_SUCCESS'),
(130, 'xavier', '2016-01-23 19:32:42', 'AUTHENTICATION_SUCCESS'),
(131, 'xavier', '2016-01-23 19:32:42', 'AUTHENTICATION_SUCCESS'),
(132, 'admin', '2016-01-23 19:33:16', 'AUTHENTICATION_SUCCESS'),
(133, 'xavier', '2016-01-23 19:36:55', 'AUTHENTICATION_SUCCESS'),
(134, 'xavier', '2016-01-23 19:36:55', 'AUTHENTICATION_SUCCESS'),
(135, 'xavier', '2016-01-23 19:36:55', 'AUTHENTICATION_SUCCESS'),
(136, 'xavier', '2016-01-23 19:39:01', 'AUTHENTICATION_SUCCESS'),
(137, 'xavier', '2016-01-23 19:39:01', 'AUTHENTICATION_SUCCESS'),
(138, 'xavier', '2016-01-23 19:39:01', 'AUTHENTICATION_SUCCESS'),
(139, 'admin', '2016-01-23 20:11:33', 'AUTHENTICATION_SUCCESS'),
(140, 'xavier', '2016-01-23 20:12:23', 'AUTHENTICATION_SUCCESS'),
(141, 'xavier', '2016-01-23 20:12:23', 'AUTHENTICATION_SUCCESS'),
(142, 'xavier', '2016-01-23 20:12:23', 'AUTHENTICATION_SUCCESS'),
(143, 'xavier', '2016-01-23 20:22:38', 'AUTHENTICATION_SUCCESS'),
(144, 'xavier', '2016-01-23 20:22:38', 'AUTHENTICATION_SUCCESS'),
(145, 'xavier', '2016-01-23 20:22:38', 'AUTHENTICATION_SUCCESS'),
(146, 'xavier', '2016-01-23 20:25:29', 'AUTHENTICATION_SUCCESS'),
(147, 'xavier', '2016-01-23 20:25:29', 'AUTHENTICATION_SUCCESS'),
(148, 'xavier', '2016-01-23 20:25:29', 'AUTHENTICATION_SUCCESS'),
(149, 'xavier', '2016-01-23 20:36:09', 'AUTHENTICATION_SUCCESS'),
(150, 'xavier', '2016-01-23 20:36:09', 'AUTHENTICATION_SUCCESS'),
(151, 'xavier', '2016-01-23 20:36:09', 'AUTHENTICATION_SUCCESS'),
(152, 'xavier', '2016-01-23 20:52:19', 'AUTHENTICATION_SUCCESS'),
(153, 'xavier', '2016-01-23 20:52:19', 'AUTHENTICATION_SUCCESS'),
(154, 'xavier', '2016-01-23 20:52:19', 'AUTHENTICATION_SUCCESS'),
(155, 'xavier', '2016-01-23 21:10:20', 'AUTHENTICATION_SUCCESS'),
(156, 'xavier', '2016-01-23 21:10:20', 'AUTHENTICATION_SUCCESS'),
(157, 'xavier', '2016-01-23 21:10:20', 'AUTHENTICATION_SUCCESS'),
(158, 'xavier', '2016-01-23 21:19:36', 'AUTHENTICATION_SUCCESS'),
(159, 'xavier', '2016-01-23 21:19:36', 'AUTHENTICATION_SUCCESS'),
(160, 'xavier', '2016-01-23 21:19:36', 'AUTHENTICATION_SUCCESS'),
(161, 'yanick', '2016-01-23 21:28:14', 'AUTHENTICATION_SUCCESS'),
(162, 'yanick', '2016-01-23 21:28:14', 'AUTHENTICATION_SUCCESS'),
(163, 'yanick', '2016-01-23 21:28:14', 'AUTHENTICATION_SUCCESS'),
(164, 'xavier', '2016-01-24 13:56:37', 'AUTHENTICATION_SUCCESS'),
(165, 'xavier', '2016-01-24 13:56:37', 'AUTHENTICATION_SUCCESS'),
(166, 'xavier', '2016-01-24 13:56:37', 'AUTHENTICATION_SUCCESS'),
(167, 'xavier', '2016-01-24 14:01:15', 'AUTHENTICATION_SUCCESS'),
(168, 'xavier', '2016-01-24 14:01:15', 'AUTHENTICATION_SUCCESS'),
(169, 'xavier', '2016-01-24 14:01:15', 'AUTHENTICATION_SUCCESS'),
(170, 'admin', '2016-01-24 14:16:35', 'AUTHENTICATION_SUCCESS'),
(171, 'xavier', '2016-01-26 17:57:14', 'AUTHENTICATION_SUCCESS'),
(172, 'xavier', '2016-01-26 17:57:14', 'AUTHENTICATION_SUCCESS'),
(173, 'xavier', '2016-01-26 17:57:14', 'AUTHENTICATION_SUCCESS'),
(174, 'xavier', '2016-01-26 17:58:27', 'AUTHENTICATION_SUCCESS'),
(175, 'xavier', '2016-01-26 17:58:27', 'AUTHENTICATION_SUCCESS'),
(176, 'xavier', '2016-01-26 17:58:27', 'AUTHENTICATION_SUCCESS'),
(177, 'xavier', '2016-01-26 18:03:14', 'AUTHENTICATION_SUCCESS'),
(178, 'xavier', '2016-01-26 18:03:14', 'AUTHENTICATION_SUCCESS'),
(179, 'xavier', '2016-01-26 18:03:14', 'AUTHENTICATION_SUCCESS'),
(180, 'xavier', '2016-01-30 19:40:43', 'AUTHENTICATION_SUCCESS'),
(181, 'xavier', '2016-01-30 19:40:43', 'AUTHENTICATION_SUCCESS'),
(182, 'xavier', '2016-01-30 19:40:43', 'AUTHENTICATION_SUCCESS'),
(183, 'admin', '2016-01-30 19:58:26', 'AUTHENTICATION_SUCCESS'),
(184, 'admin', '2016-01-30 20:16:13', 'AUTHENTICATION_SUCCESS'),
(185, 'xavier', '2016-01-30 20:24:46', 'AUTHENTICATION_SUCCESS'),
(186, 'xavier', '2016-01-30 20:24:46', 'AUTHENTICATION_SUCCESS'),
(187, 'xavier', '2016-01-30 20:24:46', 'AUTHENTICATION_SUCCESS'),
(188, 'xavier', '2016-02-01 10:13:01', 'AUTHENTICATION_SUCCESS'),
(189, 'xavier', '2016-02-01 10:13:01', 'AUTHENTICATION_SUCCESS'),
(190, 'xavier', '2016-02-01 10:13:01', 'AUTHENTICATION_SUCCESS'),
(191, 'lucas', '2016-02-01 10:19:05', 'AUTHENTICATION_SUCCESS'),
(192, 'lucas', '2016-02-01 10:19:05', 'AUTHENTICATION_SUCCESS'),
(193, 'lucas', '2016-02-01 10:19:05', 'AUTHENTICATION_SUCCESS');

-- --------------------------------------------------------

--
-- Structure de la table `jhi_persistent_audit_evt_data`
--

CREATE TABLE `jhi_persistent_audit_evt_data` (
  `event_id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `value` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `jhi_persistent_audit_evt_data`
--

INSERT INTO `jhi_persistent_audit_evt_data` (`event_id`, `name`, `value`) VALUES
(1, 'remoteAddress', '127.0.0.1'),
(1, 'sessionId', 'B4657408E6BC80F39EDABA9CA3B3FB9E'),
(2, 'remoteAddress', '127.0.0.1'),
(2, 'sessionId', 'B4657408E6BC80F39EDABA9CA3B3FB9E'),
(3, 'remoteAddress', '127.0.0.1'),
(3, 'sessionId', 'B4657408E6BC80F39EDABA9CA3B3FB9E'),
(4, 'remoteAddress', '127.0.0.1'),
(4, 'sessionId', '776CF448872F6EC84BEE5337FD3708F8'),
(5, 'remoteAddress', '127.0.0.1'),
(5, 'sessionId', '0D21DDCB8AA3F934A6BC3A4436DD29EC'),
(6, 'remoteAddress', '127.0.0.1'),
(6, 'sessionId', '0D21DDCB8AA3F934A6BC3A4436DD29EC'),
(7, 'remoteAddress', '127.0.0.1'),
(7, 'sessionId', '0D21DDCB8AA3F934A6BC3A4436DD29EC'),
(8, 'remoteAddress', '192.168.8.57'),
(8, 'sessionId', 'A6E422ADE077817528907F03012D7954'),
(9, 'remoteAddress', '192.168.8.57'),
(9, 'sessionId', 'A6E422ADE077817528907F03012D7954'),
(10, 'remoteAddress', '192.168.8.57'),
(10, 'sessionId', 'A6E422ADE077817528907F03012D7954'),
(11, 'remoteAddress', '192.168.8.57'),
(11, 'sessionId', '175E0408D176652461A16D6B35E07105'),
(12, 'remoteAddress', '192.168.8.57'),
(12, 'sessionId', '175E0408D176652461A16D6B35E07105'),
(13, 'remoteAddress', '192.168.8.57'),
(13, 'sessionId', '175E0408D176652461A16D6B35E07105'),
(14, 'remoteAddress', '192.168.8.57'),
(14, 'sessionId', 'A7966CA981CAC6F80627A669E15F1460'),
(15, 'remoteAddress', '192.168.8.57'),
(15, 'sessionId', 'A7966CA981CAC6F80627A669E15F1460'),
(16, 'remoteAddress', '192.168.8.57'),
(16, 'sessionId', 'A7966CA981CAC6F80627A669E15F1460'),
(17, 'remoteAddress', '192.168.8.57'),
(17, 'sessionId', '8AAA11EC6708981407AE900F696F4D6D'),
(18, 'remoteAddress', '192.168.8.57'),
(18, 'sessionId', '8AAA11EC6708981407AE900F696F4D6D'),
(19, 'remoteAddress', '192.168.8.57'),
(19, 'sessionId', '8AAA11EC6708981407AE900F696F4D6D'),
(20, 'remoteAddress', '192.168.8.57'),
(20, 'sessionId', 'DA8897D1AE97549E267FAB3BFD0DB4FD'),
(21, 'remoteAddress', '192.168.8.57'),
(21, 'sessionId', 'DA8897D1AE97549E267FAB3BFD0DB4FD'),
(22, 'remoteAddress', '192.168.8.57'),
(22, 'sessionId', 'DA8897D1AE97549E267FAB3BFD0DB4FD'),
(23, 'remoteAddress', '192.168.8.57'),
(23, 'sessionId', '1E659CEC2A4B4B6C1C8F0DDDDE32CFB7'),
(24, 'remoteAddress', '192.168.8.57'),
(24, 'sessionId', '1E659CEC2A4B4B6C1C8F0DDDDE32CFB7'),
(25, 'remoteAddress', '192.168.8.57'),
(25, 'sessionId', '1E659CEC2A4B4B6C1C8F0DDDDE32CFB7'),
(26, 'remoteAddress', '192.168.8.57'),
(26, 'sessionId', '4065E56CA177BF73CED5CC2F66D7249E'),
(27, 'remoteAddress', '192.168.8.57'),
(27, 'sessionId', '4065E56CA177BF73CED5CC2F66D7249E'),
(28, 'remoteAddress', '192.168.8.57'),
(28, 'sessionId', '4065E56CA177BF73CED5CC2F66D7249E'),
(29, 'remoteAddress', '127.0.0.1'),
(29, 'sessionId', '78457D1DB9A3B33E636C3460F06DC495'),
(30, 'remoteAddress', '127.0.0.1'),
(30, 'sessionId', 'A48FAED70E17CF1498341AF473F12BE4'),
(31, 'remoteAddress', '127.0.0.1'),
(31, 'sessionId', 'A48FAED70E17CF1498341AF473F12BE4'),
(32, 'remoteAddress', '127.0.0.1'),
(32, 'sessionId', 'A48FAED70E17CF1498341AF473F12BE4'),
(33, 'remoteAddress', '127.0.0.1'),
(33, 'sessionId', 'F068899BE557CABAD52BFF933C823862'),
(34, 'remoteAddress', '127.0.0.1'),
(34, 'sessionId', 'F068899BE557CABAD52BFF933C823862'),
(35, 'remoteAddress', '127.0.0.1'),
(35, 'sessionId', 'F068899BE557CABAD52BFF933C823862'),
(36, 'remoteAddress', '127.0.0.1'),
(36, 'sessionId', '97F40944DBCA0B5BC6B166EE8F87ACED'),
(37, 'remoteAddress', '127.0.0.1'),
(37, 'sessionId', '6B1388CE0964C439F9010EB1EA498D58'),
(38, 'remoteAddress', '127.0.0.1'),
(38, 'sessionId', '6B1388CE0964C439F9010EB1EA498D58'),
(39, 'remoteAddress', '127.0.0.1'),
(39, 'sessionId', '6B1388CE0964C439F9010EB1EA498D58'),
(40, 'remoteAddress', '127.0.0.1'),
(40, 'sessionId', '7CD2B90DB9F1CECDD81B0847C2D74DFC'),
(41, 'remoteAddress', '127.0.0.1'),
(41, 'sessionId', '7CD2B90DB9F1CECDD81B0847C2D74DFC'),
(42, 'remoteAddress', '127.0.0.1'),
(42, 'sessionId', '7CD2B90DB9F1CECDD81B0847C2D74DFC'),
(43, 'remoteAddress', '127.0.0.1'),
(43, 'sessionId', '4E25F84B064BE09F758D277D75F5F501'),
(44, 'remoteAddress', '127.0.0.1'),
(44, 'sessionId', '4E25F84B064BE09F758D277D75F5F501'),
(45, 'remoteAddress', '127.0.0.1'),
(45, 'sessionId', '4E25F84B064BE09F758D277D75F5F501'),
(46, 'remoteAddress', '127.0.0.1'),
(46, 'sessionId', '7DDA9172F60C259DC96AE7FA1CF562FD'),
(47, 'remoteAddress', '192.168.0.23'),
(47, 'sessionId', '7AEAC4799158FC2FC42810159CE122E1'),
(48, 'remoteAddress', '192.168.0.23'),
(48, 'sessionId', '7AEAC4799158FC2FC42810159CE122E1'),
(49, 'remoteAddress', '192.168.0.23'),
(49, 'sessionId', '7AEAC4799158FC2FC42810159CE122E1'),
(50, 'remoteAddress', '192.168.0.23'),
(50, 'sessionId', 'B4CB4A7196ED51AFF75BE9253F3E6C16'),
(51, 'remoteAddress', '192.168.0.23'),
(51, 'sessionId', 'B4CB4A7196ED51AFF75BE9253F3E6C16'),
(52, 'remoteAddress', '192.168.0.23'),
(52, 'sessionId', 'B4CB4A7196ED51AFF75BE9253F3E6C16'),
(53, 'remoteAddress', '192.168.0.23'),
(53, 'sessionId', '32B01A532BDA7E70D23F759F5F2B1E4A'),
(54, 'remoteAddress', '192.168.0.23'),
(54, 'sessionId', '32B01A532BDA7E70D23F759F5F2B1E4A'),
(55, 'remoteAddress', '192.168.0.23'),
(55, 'sessionId', '32B01A532BDA7E70D23F759F5F2B1E4A'),
(56, 'remoteAddress', '192.168.0.23'),
(56, 'sessionId', 'F41D68876FB137869269C1E08B57DD4D'),
(57, 'remoteAddress', '192.168.0.23'),
(57, 'sessionId', 'F41D68876FB137869269C1E08B57DD4D'),
(58, 'remoteAddress', '192.168.0.23'),
(58, 'sessionId', 'F41D68876FB137869269C1E08B57DD4D'),
(59, 'remoteAddress', '192.168.0.23'),
(59, 'sessionId', '25100D9B5D5013EEE9954FD52F4A9382'),
(60, 'remoteAddress', '192.168.0.23'),
(60, 'sessionId', '25100D9B5D5013EEE9954FD52F4A9382'),
(61, 'remoteAddress', '192.168.0.23'),
(61, 'sessionId', '25100D9B5D5013EEE9954FD52F4A9382'),
(62, 'remoteAddress', '192.168.0.23'),
(62, 'sessionId', '58C4353C3C4A0424445BD190B8136692'),
(63, 'remoteAddress', '192.168.0.23'),
(63, 'sessionId', '58C4353C3C4A0424445BD190B8136692'),
(64, 'remoteAddress', '192.168.0.23'),
(64, 'sessionId', '58C4353C3C4A0424445BD190B8136692'),
(65, 'remoteAddress', '192.168.0.23'),
(65, 'sessionId', '51EA62C984FCF609C2A06000C55EF58D'),
(66, 'remoteAddress', '192.168.0.23'),
(66, 'sessionId', '51EA62C984FCF609C2A06000C55EF58D'),
(67, 'remoteAddress', '192.168.0.23'),
(67, 'sessionId', '51EA62C984FCF609C2A06000C55EF58D'),
(68, 'remoteAddress', '192.168.0.23'),
(68, 'sessionId', '3D80609D818A2CCB83648CD967A82A96'),
(69, 'remoteAddress', '192.168.0.23'),
(69, 'sessionId', '3D80609D818A2CCB83648CD967A82A96'),
(70, 'remoteAddress', '192.168.0.23'),
(70, 'sessionId', '3D80609D818A2CCB83648CD967A82A96'),
(71, 'remoteAddress', '192.168.0.23'),
(71, 'sessionId', 'AD22F1C769ABB0D0D884BAF00AA8E785'),
(72, 'remoteAddress', '192.168.0.23'),
(72, 'sessionId', 'AD22F1C769ABB0D0D884BAF00AA8E785'),
(73, 'remoteAddress', '192.168.0.23'),
(73, 'sessionId', 'AD22F1C769ABB0D0D884BAF00AA8E785'),
(74, 'remoteAddress', '192.168.0.23'),
(74, 'sessionId', '410412FD90DEDFEE3178E9101E0C8C51'),
(75, 'remoteAddress', '192.168.0.23'),
(75, 'sessionId', '410412FD90DEDFEE3178E9101E0C8C51'),
(76, 'remoteAddress', '192.168.0.23'),
(76, 'sessionId', '410412FD90DEDFEE3178E9101E0C8C51'),
(77, 'remoteAddress', '192.168.0.23'),
(77, 'sessionId', '44787A0FCBCE01E537F74397D7D8E057'),
(78, 'remoteAddress', '192.168.0.23'),
(78, 'sessionId', '44787A0FCBCE01E537F74397D7D8E057'),
(79, 'remoteAddress', '192.168.0.23'),
(79, 'sessionId', '44787A0FCBCE01E537F74397D7D8E057'),
(80, 'remoteAddress', '192.168.0.23'),
(80, 'sessionId', 'EBCE8270A4A66E26D272F9068EEE284D'),
(81, 'remoteAddress', '192.168.0.23'),
(81, 'sessionId', 'EBCE8270A4A66E26D272F9068EEE284D'),
(82, 'remoteAddress', '192.168.0.23'),
(82, 'sessionId', 'EBCE8270A4A66E26D272F9068EEE284D'),
(83, 'remoteAddress', '192.168.0.23'),
(83, 'sessionId', 'FA1F6A94E9958AF37216A36C77CB08AA'),
(84, 'remoteAddress', '192.168.0.23'),
(84, 'sessionId', 'FA1F6A94E9958AF37216A36C77CB08AA'),
(85, 'remoteAddress', '192.168.0.23'),
(85, 'sessionId', 'FA1F6A94E9958AF37216A36C77CB08AA'),
(86, 'remoteAddress', '192.168.0.23'),
(86, 'sessionId', 'CD86398685E78AE4E0E909F0F2BE6832'),
(87, 'remoteAddress', '192.168.0.23'),
(87, 'sessionId', 'CD86398685E78AE4E0E909F0F2BE6832'),
(88, 'remoteAddress', '192.168.0.23'),
(88, 'sessionId', 'CD86398685E78AE4E0E909F0F2BE6832'),
(89, 'remoteAddress', '192.168.0.23'),
(89, 'sessionId', 'EE3007B664348C0A46A65A0A0FEC6973'),
(90, 'remoteAddress', '192.168.0.23'),
(90, 'sessionId', 'EE3007B664348C0A46A65A0A0FEC6973'),
(91, 'remoteAddress', '192.168.0.23'),
(91, 'sessionId', 'EE3007B664348C0A46A65A0A0FEC6973'),
(92, 'remoteAddress', '192.168.0.23'),
(92, 'sessionId', 'F24BAAC64B013E43EC767F019AED9D54'),
(93, 'remoteAddress', '192.168.0.23'),
(93, 'sessionId', 'F24BAAC64B013E43EC767F019AED9D54'),
(94, 'remoteAddress', '192.168.0.23'),
(94, 'sessionId', 'F24BAAC64B013E43EC767F019AED9D54'),
(95, 'remoteAddress', '127.0.0.1'),
(95, 'sessionId', '4D0B3F1D66CD69386B27BA19E523EA12'),
(96, 'remoteAddress', '127.0.0.1'),
(96, 'sessionId', 'A4511D01C8E9D458C3D88A5D21D41010'),
(97, 'remoteAddress', '192.168.0.23'),
(97, 'sessionId', '15420D2B2019F0853CEE61394A7413F5'),
(98, 'remoteAddress', '192.168.0.23'),
(98, 'sessionId', '15420D2B2019F0853CEE61394A7413F5'),
(99, 'remoteAddress', '192.168.0.23'),
(99, 'sessionId', '15420D2B2019F0853CEE61394A7413F5'),
(100, 'remoteAddress', '192.168.0.23'),
(100, 'sessionId', 'FE675A0AB68D3D334C80B09FC6854E20'),
(101, 'remoteAddress', '192.168.0.23'),
(101, 'sessionId', 'FE675A0AB68D3D334C80B09FC6854E20'),
(102, 'remoteAddress', '192.168.0.23'),
(102, 'sessionId', 'FE675A0AB68D3D334C80B09FC6854E20'),
(103, 'message', 'Bad credentials'),
(103, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(104, 'remoteAddress', '192.168.0.23'),
(104, 'sessionId', '74869209CAC16DC50906234CD702C0A6'),
(105, 'remoteAddress', '192.168.0.23'),
(105, 'sessionId', '74869209CAC16DC50906234CD702C0A6'),
(106, 'remoteAddress', '192.168.0.23'),
(106, 'sessionId', '74869209CAC16DC50906234CD702C0A6'),
(107, 'remoteAddress', '192.168.0.23'),
(107, 'sessionId', '26FBAE2EB4C7106995EA4018C923E804'),
(108, 'remoteAddress', '192.168.0.23'),
(108, 'sessionId', '26FBAE2EB4C7106995EA4018C923E804'),
(109, 'remoteAddress', '192.168.0.23'),
(109, 'sessionId', '26FBAE2EB4C7106995EA4018C923E804'),
(110, 'remoteAddress', '192.168.0.23'),
(110, 'sessionId', '24D6534850575E92587E52B3EB57851C'),
(111, 'remoteAddress', '192.168.0.23'),
(111, 'sessionId', '24D6534850575E92587E52B3EB57851C'),
(112, 'remoteAddress', '192.168.0.23'),
(112, 'sessionId', '24D6534850575E92587E52B3EB57851C'),
(113, 'remoteAddress', '192.168.0.23'),
(113, 'sessionId', '8D8D82887F289363B6F9DDC83C05B606'),
(114, 'remoteAddress', '192.168.0.23'),
(114, 'sessionId', '8D8D82887F289363B6F9DDC83C05B606'),
(115, 'remoteAddress', '192.168.0.23'),
(115, 'sessionId', '8D8D82887F289363B6F9DDC83C05B606'),
(116, 'remoteAddress', '127.0.0.1'),
(116, 'sessionId', '73009B9F0D4AA15E84AD0012E79DD63D'),
(117, 'remoteAddress', '192.168.0.23'),
(117, 'sessionId', 'CAE531FAA699D2DFB0C66FB9DDD2348A'),
(118, 'remoteAddress', '192.168.0.23'),
(118, 'sessionId', 'CAE531FAA699D2DFB0C66FB9DDD2348A'),
(119, 'remoteAddress', '192.168.0.23'),
(119, 'sessionId', 'CAE531FAA699D2DFB0C66FB9DDD2348A'),
(120, 'remoteAddress', '192.168.0.23'),
(120, 'sessionId', '3BA2BFCE3EBFDA272B8612CF4E9622D7'),
(121, 'remoteAddress', '192.168.0.23'),
(121, 'sessionId', '3BA2BFCE3EBFDA272B8612CF4E9622D7'),
(122, 'remoteAddress', '192.168.0.23'),
(122, 'sessionId', '3BA2BFCE3EBFDA272B8612CF4E9622D7'),
(123, 'remoteAddress', '192.168.0.23'),
(123, 'sessionId', 'DFBC876EB228D1F5EC9CD8C6F9376CDE'),
(124, 'remoteAddress', '192.168.0.23'),
(124, 'sessionId', 'DFBC876EB228D1F5EC9CD8C6F9376CDE'),
(125, 'remoteAddress', '192.168.0.23'),
(125, 'sessionId', 'DFBC876EB228D1F5EC9CD8C6F9376CDE'),
(126, 'remoteAddress', '192.168.0.23'),
(126, 'sessionId', 'A6971C6B907D8FEFFABE89480685705E'),
(127, 'remoteAddress', '192.168.0.23'),
(127, 'sessionId', 'A6971C6B907D8FEFFABE89480685705E'),
(128, 'remoteAddress', '192.168.0.23'),
(128, 'sessionId', 'A6971C6B907D8FEFFABE89480685705E'),
(129, 'remoteAddress', '192.168.0.23'),
(129, 'sessionId', '61C8D75513A8DEAA6518A46D6325EE14'),
(130, 'remoteAddress', '192.168.0.23'),
(130, 'sessionId', '61C8D75513A8DEAA6518A46D6325EE14'),
(131, 'remoteAddress', '192.168.0.23'),
(131, 'sessionId', '61C8D75513A8DEAA6518A46D6325EE14'),
(132, 'remoteAddress', '127.0.0.1'),
(132, 'sessionId', 'BDF6740A16C1F1B37CD4DA34EBDBCA1E'),
(133, 'remoteAddress', '192.168.0.23'),
(133, 'sessionId', '9297A58D6B7E344D6F593099803C351F'),
(134, 'remoteAddress', '192.168.0.23'),
(134, 'sessionId', '9297A58D6B7E344D6F593099803C351F'),
(135, 'remoteAddress', '192.168.0.23'),
(135, 'sessionId', '9297A58D6B7E344D6F593099803C351F'),
(136, 'remoteAddress', '192.168.0.23'),
(136, 'sessionId', '9483D2295280134D06C117BC425C7302'),
(137, 'remoteAddress', '192.168.0.23'),
(137, 'sessionId', '9483D2295280134D06C117BC425C7302'),
(138, 'remoteAddress', '192.168.0.23'),
(138, 'sessionId', '9483D2295280134D06C117BC425C7302'),
(139, 'remoteAddress', '127.0.0.1'),
(139, 'sessionId', '54B54836ABFADB0008206B651404CA34'),
(140, 'remoteAddress', '192.168.0.23'),
(140, 'sessionId', 'BB35CADA8E0231FC3D24CA85207048C0'),
(141, 'remoteAddress', '192.168.0.23'),
(141, 'sessionId', 'BB35CADA8E0231FC3D24CA85207048C0'),
(142, 'remoteAddress', '192.168.0.23'),
(142, 'sessionId', 'BB35CADA8E0231FC3D24CA85207048C0'),
(143, 'remoteAddress', '192.168.0.23'),
(143, 'sessionId', '0019820061AE0E491109D4C08417A8C2'),
(144, 'remoteAddress', '192.168.0.23'),
(144, 'sessionId', '0019820061AE0E491109D4C08417A8C2'),
(145, 'remoteAddress', '192.168.0.23'),
(145, 'sessionId', '0019820061AE0E491109D4C08417A8C2'),
(146, 'remoteAddress', '192.168.0.23'),
(146, 'sessionId', '410A25BC11A87E620CA6B750944A471D'),
(147, 'remoteAddress', '192.168.0.23'),
(147, 'sessionId', '410A25BC11A87E620CA6B750944A471D'),
(148, 'remoteAddress', '192.168.0.23'),
(148, 'sessionId', '410A25BC11A87E620CA6B750944A471D'),
(149, 'remoteAddress', '192.168.0.23'),
(149, 'sessionId', 'B70793BB09B030BCFD423F9163E21CF2'),
(150, 'remoteAddress', '192.168.0.23'),
(150, 'sessionId', 'B70793BB09B030BCFD423F9163E21CF2'),
(151, 'remoteAddress', '192.168.0.23'),
(151, 'sessionId', 'B70793BB09B030BCFD423F9163E21CF2'),
(152, 'remoteAddress', '192.168.0.23'),
(152, 'sessionId', 'D718FC9FE1D895C75C4CED6948C46690'),
(153, 'remoteAddress', '192.168.0.23'),
(153, 'sessionId', 'D718FC9FE1D895C75C4CED6948C46690'),
(154, 'remoteAddress', '192.168.0.23'),
(154, 'sessionId', 'D718FC9FE1D895C75C4CED6948C46690'),
(155, 'remoteAddress', '192.168.0.23'),
(155, 'sessionId', '1F74913BDB2976EFD3EB3FC855A7859B'),
(156, 'remoteAddress', '192.168.0.23'),
(156, 'sessionId', '1F74913BDB2976EFD3EB3FC855A7859B'),
(157, 'remoteAddress', '192.168.0.23'),
(157, 'sessionId', '1F74913BDB2976EFD3EB3FC855A7859B'),
(158, 'remoteAddress', '192.168.0.24'),
(158, 'sessionId', '4218A39CF4468F9DF4CAE8647F64C8C4'),
(159, 'remoteAddress', '192.168.0.24'),
(159, 'sessionId', '4218A39CF4468F9DF4CAE8647F64C8C4'),
(160, 'remoteAddress', '192.168.0.24'),
(160, 'sessionId', '4218A39CF4468F9DF4CAE8647F64C8C4'),
(161, 'remoteAddress', '192.168.0.24'),
(161, 'sessionId', 'EF34695764980B5F553D0DA85375FC39'),
(162, 'remoteAddress', '192.168.0.24'),
(162, 'sessionId', 'EF34695764980B5F553D0DA85375FC39'),
(163, 'remoteAddress', '192.168.0.24'),
(163, 'sessionId', 'EF34695764980B5F553D0DA85375FC39'),
(164, 'remoteAddress', '192.168.0.23'),
(164, 'sessionId', '930836B49ECB0A4C80DCBF96240766C5'),
(165, 'remoteAddress', '192.168.0.23'),
(165, 'sessionId', '930836B49ECB0A4C80DCBF96240766C5'),
(166, 'remoteAddress', '192.168.0.23'),
(166, 'sessionId', '930836B49ECB0A4C80DCBF96240766C5'),
(167, 'remoteAddress', '192.168.0.23'),
(167, 'sessionId', 'E127ECAA7102F88A3C20F824D4700D4D'),
(168, 'remoteAddress', '192.168.0.23'),
(168, 'sessionId', 'E127ECAA7102F88A3C20F824D4700D4D'),
(169, 'remoteAddress', '192.168.0.23'),
(169, 'sessionId', 'E127ECAA7102F88A3C20F824D4700D4D'),
(170, 'remoteAddress', '127.0.0.1'),
(170, 'sessionId', 'DF96E7C357ECED7A22593F3F0FD3E45B'),
(171, 'remoteAddress', '192.168.0.23'),
(171, 'sessionId', '5DC8F04600AC64C447BBCDDA22FA6742'),
(172, 'remoteAddress', '192.168.0.23'),
(172, 'sessionId', '5DC8F04600AC64C447BBCDDA22FA6742'),
(173, 'remoteAddress', '192.168.0.23'),
(173, 'sessionId', '5DC8F04600AC64C447BBCDDA22FA6742'),
(174, 'remoteAddress', '192.168.0.23'),
(174, 'sessionId', 'CEF80F4672726F551BD163C9A2F54CDF'),
(175, 'remoteAddress', '192.168.0.23'),
(175, 'sessionId', 'CEF80F4672726F551BD163C9A2F54CDF'),
(176, 'remoteAddress', '192.168.0.23'),
(176, 'sessionId', 'CEF80F4672726F551BD163C9A2F54CDF'),
(177, 'remoteAddress', '192.168.0.23'),
(177, 'sessionId', '4E25B869B15A84BC11D90AC0BBFAAE3A'),
(178, 'remoteAddress', '192.168.0.23'),
(178, 'sessionId', '4E25B869B15A84BC11D90AC0BBFAAE3A'),
(179, 'remoteAddress', '192.168.0.23'),
(179, 'sessionId', '4E25B869B15A84BC11D90AC0BBFAAE3A'),
(180, 'remoteAddress', '192.168.56.101'),
(180, 'sessionId', '4C65A001618B76F8032FAF7F126996BC'),
(181, 'remoteAddress', '192.168.56.101'),
(181, 'sessionId', '4C65A001618B76F8032FAF7F126996BC'),
(182, 'remoteAddress', '192.168.56.101'),
(182, 'sessionId', '4C65A001618B76F8032FAF7F126996BC'),
(183, 'remoteAddress', '127.0.0.1'),
(183, 'sessionId', '6586AF3B8D1E575116646199B9DD8806'),
(184, 'remoteAddress', '127.0.0.1'),
(184, 'sessionId', '86BD87887CBFBEAE676A701E3C75E2BF'),
(185, 'remoteAddress', '192.168.56.101'),
(185, 'sessionId', '23A859659225019430910BFCAF4559EC'),
(186, 'remoteAddress', '192.168.56.101'),
(186, 'sessionId', '23A859659225019430910BFCAF4559EC'),
(187, 'remoteAddress', '192.168.56.101'),
(187, 'sessionId', '23A859659225019430910BFCAF4559EC'),
(188, 'remoteAddress', '192.168.56.101'),
(188, 'sessionId', '134D13A417D67CF706E45BA094C0D1FA'),
(189, 'remoteAddress', '192.168.56.101'),
(189, 'sessionId', '134D13A417D67CF706E45BA094C0D1FA'),
(190, 'remoteAddress', '192.168.56.101'),
(190, 'sessionId', '134D13A417D67CF706E45BA094C0D1FA'),
(191, 'remoteAddress', '192.168.56.101'),
(191, 'sessionId', '0DA4C39F9D9B7F236E3F059A2B2DFC64'),
(192, 'remoteAddress', '192.168.56.101'),
(192, 'sessionId', '0DA4C39F9D9B7F236E3F059A2B2DFC64'),
(193, 'remoteAddress', '192.168.56.101'),
(193, 'sessionId', '0DA4C39F9D9B7F236E3F059A2B2DFC64');

-- --------------------------------------------------------

--
-- Structure de la table `jhi_persistent_token`
--

CREATE TABLE `jhi_persistent_token` (
  `series` varchar(255) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `token_value` varchar(255) NOT NULL,
  `token_date` date DEFAULT NULL,
  `ip_address` varchar(39) DEFAULT NULL,
  `user_agent` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `jhi_persistent_token`
--

INSERT INTO `jhi_persistent_token` (`series`, `user_id`, `token_value`, `token_date`, `ip_address`, `user_agent`) VALUES
('C6wPkhmM0eETm7LJd6VanA==', 3, 'FN9p6xLmdwcUZugkOR6Hyw==', '2016-01-23', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.111 Safari/537.36'),
('OuTdvpjHwtkJqLt8Ja3BBQ==', 3, 'bMADUdA8+VlO66/cYsxL8Q==', '2016-01-30', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.97 Safari/537.36'),
('so5jKhiLCEFqB9vxaiUL3w==', 3, 'LTmgOWgRtYUP/BYEwQf50A==', '2016-01-23', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.111 Safari/537.36');

-- --------------------------------------------------------

--
-- Structure de la table `jhi_user`
--

CREATE TABLE `jhi_user` (
  `id` bigint(20) NOT NULL,
  `login` varchar(50) NOT NULL,
  `PASSWORD` varchar(60) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `activated` bit(1) NOT NULL,
  `lang_key` varchar(5) DEFAULT NULL,
  `activation_key` varchar(20) DEFAULT NULL,
  `reset_key` varchar(20) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NOT NULL,
  `reset_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  `phone` varchar(10) DEFAULT '',
  `distance` int(11) DEFAULT '20'
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `jhi_user`
--

INSERT INTO `jhi_user` (`id`, `login`, `PASSWORD`, `first_name`, `last_name`, `email`, `activated`, `lang_key`, `activation_key`, `reset_key`, `created_by`, `created_date`, `reset_date`, `last_modified_by`, `last_modified_date`, `phone`, `distance`) VALUES
(1, 'system', '$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG', 'System', 'System', 'system@localhost', b'1', 'en', NULL, NULL, 'system', '2016-01-16 22:13:34', NULL, NULL, NULL, '', 20),
(2, 'anonymousUser', '$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO', 'Anonymous', 'User', 'anonymous@localhost', b'1', 'en', NULL, NULL, 'system', '2016-01-16 22:13:34', NULL, NULL, NULL, '', 20),
(3, 'admin', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', 'Administrator', 'Administrator', 'admin@localhost', b'1', 'en', NULL, NULL, 'system', '2016-01-16 22:13:34', NULL, NULL, NULL, '', 20),
(4, 'user', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'User', 'User', 'user@localhost', b'1', 'en', NULL, NULL, 'system', '2016-01-16 22:13:34', NULL, NULL, NULL, '', 20),
(5, 'yanick', '$2a$10$0diU.C4M2vK32VpHh2WiAOyVm6as3Ts0gmvd6FlYpc9wVfAmTEFiG', NULL, NULL, 'yanick.servant@free.fr', b'1', 'en', '54840147449915308583', NULL, 'anonymousUser', '2016-01-18 10:37:46', NULL, 'admin', '2016-01-18 10:38:03', NULL, 20),
(6, 'thomas', '$2a$10$h7ynRiQSMeV9eOd5fiCb6OC4w3d/PELUHAhwUz.dy4zyp/BHTs8CC', NULL, NULL, 'thomas@gmail.com', b'1', 'en', '61179914111544557775', NULL, 'anonymousUser', '2016-01-23 14:39:28', NULL, 'admin', '2016-01-23 14:40:33', NULL, 20),
(7, 'lucas', '$2a$10$EJbSQ5PkARlWSbH0WExqCeLL1mhMGOu/hNKR2nqq4rRWIZdnnFRW6', NULL, NULL, 'lucas@gmail.com', b'1', 'en', '91768501613086885174', NULL, 'anonymousUser', '2016-01-23 14:39:52', NULL, 'admin', '2016-01-23 14:40:35', NULL, 20),
(8, 'xavier', '$2a$10$aY1D4oz.ek3kTc3ly9lKluvm55ShVNhQrUxO1yhSZlgeNb16d0d3W', NULL, NULL, 'xavier@gmail.com', b'1', 'en', '71147561523167168692', NULL, 'anonymousUser', '2016-01-23 14:40:12', NULL, 'admin', '2016-01-23 14:40:36', NULL, 20),
(9, 'marion', '$2a$10$8E6Az9AUYbSHZTMmhvLrw.NcWftaRHUEuBQgyKd3bUjNKlHqfOIz6', NULL, NULL, 'marion@free.fr', b'1', 'en', '38970109234698124651', NULL, 'anonymousUser', '2016-01-23 16:00:45', NULL, 'admin', '2016-01-23 16:01:06', NULL, 20);

-- --------------------------------------------------------

--
-- Structure de la table `jhi_user_authority`
--

CREATE TABLE `jhi_user_authority` (
  `user_id` bigint(20) NOT NULL,
  `authority_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `jhi_user_authority`
--

INSERT INTO `jhi_user_authority` (`user_id`, `authority_name`) VALUES
(1, 'ROLE_ADMIN'),
(3, 'ROLE_ADMIN'),
(1, 'ROLE_USER'),
(3, 'ROLE_USER'),
(4, 'ROLE_USER'),
(5, 'ROLE_USER'),
(6, 'ROLE_USER'),
(7, 'ROLE_USER'),
(8, 'ROLE_USER'),
(9, 'ROLE_USER');

--
-- Index pour les tables exportées
--

--
-- Index pour la table `ad`
--
ALTER TABLE `ad`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_ad_adtype_id` (`ad_type_id`),
  ADD KEY `fk_ad_animal_id` (`animal_id`),
  ADD KEY `fk_user_ad_id` (`user_id`);

--
-- Index pour la table `ad_type`
--
ALTER TABLE `ad_type`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `animal`
--
ALTER TABLE `animal`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_animal_breed_id` (`breed_id`),
  ADD KEY `fk_animal_animaltype_id` (`animal_type_id`),
  ADD KEY `fk_user_animal_id` (`user_id`);

--
-- Index pour la table `animal_color`
--
ALTER TABLE `animal_color`
  ADD PRIMARY KEY (`animals_id`,`colors_id`),
  ADD KEY `fk_color_color_animal_id` (`colors_id`);

--
-- Index pour la table `animal_type`
--
ALTER TABLE `animal_type`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `animal_type_breed`
--
ALTER TABLE `animal_type_breed`
  ADD PRIMARY KEY (`animal_types_id`,`breeds_id`),
  ADD KEY `fk_breed_breed_animaltype_id` (`breeds_id`);

--
-- Index pour la table `breed`
--
ALTER TABLE `breed`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `color`
--
ALTER TABLE `color`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `color_animal_type`
--
ALTER TABLE `color_animal_type`
  ADD PRIMARY KEY (`colors_id`,`animal_types_id`),
  ADD KEY `fk_animaltype_animaltype_color_id` (`animal_types_id`);

--
-- Index pour la table `DATABASECHANGELOGLOCK`
--
ALTER TABLE `DATABASECHANGELOGLOCK`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `jhi_authority`
--
ALTER TABLE `jhi_authority`
  ADD PRIMARY KEY (`name`);

--
-- Index pour la table `jhi_persistent_audit_event`
--
ALTER TABLE `jhi_persistent_audit_event`
  ADD PRIMARY KEY (`event_id`),
  ADD KEY `idx_persistent_audit_event` (`principal`,`event_date`);

--
-- Index pour la table `jhi_persistent_audit_evt_data`
--
ALTER TABLE `jhi_persistent_audit_evt_data`
  ADD PRIMARY KEY (`event_id`,`name`),
  ADD KEY `idx_persistent_audit_evt_data` (`event_id`);

--
-- Index pour la table `jhi_persistent_token`
--
ALTER TABLE `jhi_persistent_token`
  ADD PRIMARY KEY (`series`),
  ADD KEY `fk_user_persistent_token` (`user_id`);

--
-- Index pour la table `jhi_user`
--
ALTER TABLE `jhi_user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `login` (`login`),
  ADD UNIQUE KEY `idx_user_login` (`login`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `idx_user_email` (`email`);

--
-- Index pour la table `jhi_user_authority`
--
ALTER TABLE `jhi_user_authority`
  ADD PRIMARY KEY (`user_id`,`authority_name`),
  ADD KEY `fk_authority_name` (`authority_name`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `ad`
--
ALTER TABLE `ad`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT pour la table `ad_type`
--
ALTER TABLE `ad_type`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT pour la table `animal`
--
ALTER TABLE `animal`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT pour la table `animal_type`
--
ALTER TABLE `animal_type`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT pour la table `breed`
--
ALTER TABLE `breed`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=22;
--
-- AUTO_INCREMENT pour la table `color`
--
ALTER TABLE `color`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT pour la table `jhi_persistent_audit_event`
--
ALTER TABLE `jhi_persistent_audit_event`
  MODIFY `event_id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=194;
--
-- AUTO_INCREMENT pour la table `jhi_user`
--
ALTER TABLE `jhi_user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=10;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `ad`
--
ALTER TABLE `ad`
  ADD CONSTRAINT `fk_ad_adtype_id` FOREIGN KEY (`ad_type_id`) REFERENCES `ad_type` (`id`),
  ADD CONSTRAINT `fk_ad_animal_id` FOREIGN KEY (`animal_id`) REFERENCES `animal` (`id`),
  ADD CONSTRAINT `fk_user_ad_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`);

--
-- Contraintes pour la table `animal`
--
ALTER TABLE `animal`
  ADD CONSTRAINT `fk_animal_animaltype_id` FOREIGN KEY (`animal_type_id`) REFERENCES `animal_type` (`id`),
  ADD CONSTRAINT `fk_animal_breed_id` FOREIGN KEY (`breed_id`) REFERENCES `breed` (`id`),
  ADD CONSTRAINT `fk_user_animal_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`);

--
-- Contraintes pour la table `animal_color`
--
ALTER TABLE `animal_color`
  ADD CONSTRAINT `fk_animal_color_color_id` FOREIGN KEY (`animals_id`) REFERENCES `animal` (`id`),
  ADD CONSTRAINT `fk_color_color_animal_id` FOREIGN KEY (`colors_id`) REFERENCES `color` (`id`);

--
-- Contraintes pour la table `animal_type_breed`
--
ALTER TABLE `animal_type_breed`
  ADD CONSTRAINT `fk_animaltype_breed_breed_id` FOREIGN KEY (`animal_types_id`) REFERENCES `animal_type` (`id`),
  ADD CONSTRAINT `fk_breed_breed_animaltype_id` FOREIGN KEY (`breeds_id`) REFERENCES `breed` (`id`);

--
-- Contraintes pour la table `color_animal_type`
--
ALTER TABLE `color_animal_type`
  ADD CONSTRAINT `fk_animaltype_animaltype_color_id` FOREIGN KEY (`animal_types_id`) REFERENCES `animal_type` (`id`),
  ADD CONSTRAINT `fk_color_animaltype_animaltype_id` FOREIGN KEY (`colors_id`) REFERENCES `color` (`id`);

--
-- Contraintes pour la table `jhi_persistent_audit_evt_data`
--
ALTER TABLE `jhi_persistent_audit_evt_data`
  ADD CONSTRAINT `fk_evt_pers_audit_evt_data` FOREIGN KEY (`event_id`) REFERENCES `jhi_persistent_audit_event` (`event_id`);

--
-- Contraintes pour la table `jhi_persistent_token`
--
ALTER TABLE `jhi_persistent_token`
  ADD CONSTRAINT `fk_user_persistent_token` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`);

--
-- Contraintes pour la table `jhi_user_authority`
--
ALTER TABLE `jhi_user_authority`
  ADD CONSTRAINT `fk_authority_name` FOREIGN KEY (`authority_name`) REFERENCES `jhi_authority` (`name`),
  ADD CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`);
