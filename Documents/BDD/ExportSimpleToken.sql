-- phpMyAdmin SQL Dump
-- version 4.4.10
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Dim 13 Mars 2016 à 17:49
-- Version du serveur :  5.5.42
-- Version de PHP :  7.0.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Base de données :  `pilpoilweb`
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
(1, 'Chat perdu près du veto à Mérignac le 08/01 à 18H', NULL, '28/02/2016', NULL, NULL, '9 avenue de la libération', NULL, '33700', 'Mérignac', 'France', '-0.6455985999999712', '44.8429936', 1, 3, 6),
(2, 'Mon chat à sauté du balcon le 20/09/2015', NULL, '21/09/2015', NULL, NULL, '18 Avenue du Chut', 'Domaine de Valois', '33700', 'Mérignac', 'France', '-0.6597447000000329', '44.84913179999999', 3, 2, 6),
(3, 'Mon chien a fait un trou dans le grillage pendant la nuit et c''est enfuit', NULL, '20/02/2016', NULL, NULL, '420 Rue du Grand Barrail', NULL, '33127', 'Saint-Jean-d''Illac', 'France', '-0.8493601000000126', '44.8068572', 1, 7, 8),
(4, 'Chat retrouvé avenue de la libération, je l''ai trouvé en pleine nuit et l''ai amené chez moi', b'1', '01/03/2016', NULL, '', '30 avenue de la libération', '', '33700', 'Mérignac', 'France', '-0.6447370999999293', '44.84533239999999', 2, 8, 9),
(5, NULL, NULL, '01/03/2016', NULL, NULL, '391 Rue de Vaugirard', NULL, '75015', 'Paris', 'France', '2.292319799999973', '48.8347839', 2, 9, NULL),
(6, NULL, NULL, '28/02/2016', '0669553011', NULL, '18 Rue des Bouleaux', NULL, '33600', 'Pessac', 'France', '-0.688220699999988', '44.7973314', 2, 10, NULL),
(7, NULL, b'1', '29/02/2014', '0669553011', 'thibault@free.fr', '30 avenue de la libération', NULL, '33700', 'Mérignac', 'France', '-0.6447370999999293', '44.84533239999999', 2, 11, NULL);

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
  `colors` varchar(255) DEFAULT NULL,
  `animal_type_id` bigint(20) DEFAULT NULL,
  `breed_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `animal`
--

INSERT INTO `animal` (`id`, `name`, `age`, `sexe`, `tatoo`, `chip`, `photo`, `colors`, `animal_type_id`, `breed_id`, `user_id`) VALUES
(1, 'Lumia', 'Jeune', 'Male', 'TATOO123ED', NULL, 'http://static.wamiz.fr/images/news/medium/chat-tabby.jpg', 'Tigré gris noir', 1, 13, 5),
(2, 'Gollum', 'Jeune', 'Male', 'TATOO145TG2', 'pucetest2', NULL, 'Beige, blanc', 1, 10, 6),
(3, 'MarieE', 'Adulte', 'Femelle', 'TATOO156TY', '', 'http://www.siamoisclub.com/wp-content/uploads/chat-siamois.jpg', 'Beige, blanc', 1, 8, 6),
(4, 'Pascal', NULL, NULL, NULL, NULL, NULL, 'Rouge', 4, NULL, 6),
(5, 'LeChien', 'Adulte', 'Male', NULL, NULL, 'http://static.wamiz.fr/images/news/additional/2013-05/large/berger-australien-choix-race-chien.jpg', NULL, 2, 5, 7),
(6, 'ChatChat', NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, 8),
(7, 'ChienChien', NULL, NULL, NULL, NULL, NULL, NULL, 2, NULL, 8),
(8, NULL, NULL, 'Femelle', NULL, NULL, 'https://upload.wikimedia.org/wikipedia/commons/thumb/4/48/Chronos_4_mois.jpg/220px-Chronos_4_mois.jpg', NULL, 1, 8, NULL),
(9, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL),
(10, NULL, 'Jeune', 'Male', NULL, NULL, NULL, NULL, 1, 9, NULL),
(11, NULL, 'Adulte', 'Male', 'TATEE125ED', NULL, 'http://www.chatsnoirs.com/medias/images/europeen-noir.jpg', NULL, 1, NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `animal_type`
--

CREATE TABLE `animal_type` (
  `id` bigint(20) NOT NULL,
  `label` varchar(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `animal_type`
--

INSERT INTO `animal_type` (`id`, `label`) VALUES
(1, 'Chat'),
(2, 'Chien'),
(3, 'Autre'),
(4, 'Poisson'),
(5, 'Lapin');

-- --------------------------------------------------------

--
-- Structure de la table `breed`
--

CREATE TABLE `breed` (
  `id` bigint(20) NOT NULL,
  `label` varchar(255) DEFAULT NULL,
  `animal_type_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `breed`
--

INSERT INTO `breed` (`id`, `label`, `animal_type_id`) VALUES
(1, 'Berger Allemand', 2),
(2, 'Carlin', 2),
(3, 'Golden Retriever', 2),
(4, 'Cavalier King Charles', 2),
(5, 'Akita Inu', 2),
(7, 'Sacré de Birmanie', 1),
(8, 'Siamois', 1),
(9, 'Bengal', 1),
(10, 'Maine coon', 1),
(11, 'Persan', 1),
(12, 'Norvégien', 1),
(13, 'Européen', 1);

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
('00000000000001', 'jhipster', 'classpath:config/liquibase/changelog/00000000000000_initial_schema.xml', '2016-03-09 21:26:21', 1, 'EXECUTED', '7:6d234d695d2332d8f7f7f175f8841cbc', 'createTable, createIndex (x2), createTable (x2), addPrimaryKey, addForeignKeyConstraint (x2), loadData, dropDefaultValue, loadData (x2), createTable (x2), addPrimaryKey, createIndex (x2), addForeignKeyConstraint', '', NULL, '3.4.2', NULL, NULL),
('20160309192142', 'jhipster', 'classpath:config/liquibase/changelog/20160309192142_added_entity_AdType.xml', '2016-03-09 21:26:21', 2, 'EXECUTED', '7:0b16ce727cc4529ff935efbf2582f3fa', 'createTable', '', NULL, '3.4.2', NULL, NULL),
('20160309192143', 'jhipster', 'classpath:config/liquibase/changelog/20160309192143_added_entity_AnimalType.xml', '2016-03-09 21:26:21', 3, 'EXECUTED', '7:99cb1d62ee189db53da38c12d8a6061f', 'createTable', '', NULL, '3.4.2', NULL, NULL),
('20160309192141', 'jhipster', 'classpath:config/liquibase/changelog/20160309192141_added_entity_Breed.xml', '2016-03-09 21:26:21', 4, 'EXECUTED', '7:d60909f15f976aa4751cd78b8e2db135', 'createTable, addForeignKeyConstraint', '', NULL, '3.4.2', NULL, NULL),
('20160309192145', 'jhipster', 'classpath:config/liquibase/changelog/20160309192145_added_entity_Animal.xml', '2016-03-09 21:26:22', 5, 'EXECUTED', '7:2e0a04b82d8e636abe604c082703dedc', 'createTable, addForeignKeyConstraint (x2)', '', NULL, '3.4.2', NULL, NULL),
('20160309192144', 'jhipster', 'classpath:config/liquibase/changelog/20160309192144_added_entity_Ad.xml', '2016-03-09 21:26:22', 6, 'EXECUTED', '7:24485ffbb55c65b5ce65cb7f07cf3246', 'createTable, addForeignKeyConstraint (x2)', '', NULL, '3.4.2', NULL, NULL),
('20151211215522', 'yanick', 'classpath:config/liquibase/changelog/20151211232200_added_user_column.xml', '2016-03-09 21:26:22', 7, 'EXECUTED', '7:01c96c9aee0abe42fd9b33b8a9c06400', 'addColumn (x2)', '', NULL, '3.4.2', NULL, NULL),
('20151212111000', 'yanick', 'classpath:config/liquibase/changelog/20151212111000_added_user_relationship.xml', '2016-03-09 21:26:22', 8, 'EXECUTED', '7:cf6f44b1b788b865c979ef0932052cbc', 'addColumn, addForeignKeyConstraint, addColumn, addForeignKeyConstraint', '', NULL, '3.4.2', NULL, NULL);

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
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `jhi_persistent_audit_event`
--

INSERT INTO `jhi_persistent_audit_event` (`event_id`, `principal`, `event_date`, `event_type`) VALUES
(1, 'admin', '2016-03-09 20:26:57', 'AUTHENTICATION_SUCCESS'),
(2, 'xavier', '2016-03-09 20:33:39', 'AUTHENTICATION_SUCCESS'),
(3, 'xavier', '2016-03-09 20:35:05', 'AUTHENTICATION_SUCCESS'),
(4, 'admin', '2016-03-09 20:37:12', 'AUTHENTICATION_SUCCESS'),
(5, 'yanick', '2016-03-09 20:41:54', 'AUTHENTICATION_SUCCESS'),
(6, 'yanick', '2016-03-09 20:42:56', 'AUTHENTICATION_SUCCESS'),
(7, 'admin', '2016-03-09 20:50:34', 'AUTHENTICATION_SUCCESS'),
(8, 'xavier', '2016-03-12 08:39:00', 'AUTHENTICATION_SUCCESS'),
(9, 'admin', '2016-03-12 08:39:17', 'AUTHENTICATION_SUCCESS'),
(10, 'xavier', '2016-03-12 09:17:53', 'AUTHENTICATION_SUCCESS'),
(11, 'xavier', '2016-03-12 09:18:34', 'AUTHENTICATION_SUCCESS'),
(12, 'admin', '2016-03-12 09:21:36', 'AUTHENTICATION_SUCCESS'),
(13, 'xavier', '2016-03-12 09:33:10', 'AUTHENTICATION_SUCCESS'),
(14, 'xavier', '2016-03-12 09:40:36', 'AUTHENTICATION_SUCCESS'),
(15, 'xavier', '2016-03-12 09:44:01', 'AUTHENTICATION_SUCCESS'),
(16, 'xavier', '2016-03-12 09:48:40', 'AUTHENTICATION_SUCCESS'),
(17, 'xavier', '2016-03-12 09:49:26', 'AUTHENTICATION_SUCCESS'),
(18, 'xavier', '2016-03-12 09:51:48', 'AUTHENTICATION_SUCCESS'),
(19, 'xavier', '2016-03-12 09:56:22', 'AUTHENTICATION_SUCCESS'),
(20, 'xavier', '2016-03-12 09:57:29', 'AUTHENTICATION_SUCCESS'),
(21, 'xavier', '2016-03-12 09:57:31', 'AUTHENTICATION_SUCCESS'),
(22, 'xavier', '2016-03-12 09:58:32', 'AUTHENTICATION_SUCCESS'),
(23, 'xavier', '2016-03-12 10:02:11', 'AUTHENTICATION_SUCCESS'),
(24, 'xavier', '2016-03-12 10:03:51', 'AUTHENTICATION_SUCCESS'),
(25, 'admin', '2016-03-12 10:05:56', 'AUTHENTICATION_SUCCESS'),
(26, 'ezdze', '2016-03-12 10:06:29', 'AUTHENTICATION_FAILURE'),
(27, 'admin', '2016-03-12 10:06:47', 'AUTHENTICATION_SUCCESS'),
(28, 'xavier', '2016-03-12 10:12:46', 'AUTHENTICATION_SUCCESS'),
(29, 'xavier', '2016-03-12 10:17:54', 'AUTHENTICATION_SUCCESS'),
(30, 'xavier', '2016-03-12 10:23:07', 'AUTHENTICATION_SUCCESS'),
(31, 'xavier', '2016-03-12 10:24:38', 'AUTHENTICATION_SUCCESS'),
(32, 'admin', '2016-03-12 10:25:34', 'AUTHENTICATION_SUCCESS'),
(33, 'xavier', '2016-03-12 10:28:19', 'AUTHENTICATION_SUCCESS'),
(34, 'xavier', '2016-03-12 10:29:07', 'AUTHENTICATION_SUCCESS'),
(35, 'xavier', '2016-03-12 10:36:11', 'AUTHENTICATION_SUCCESS'),
(36, 'xavier', '2016-03-12 10:37:01', 'AUTHENTICATION_SUCCESS'),
(37, 'xavier', '2016-03-12 10:41:51', 'AUTHENTICATION_SUCCESS'),
(38, 'admin', '2016-03-12 10:44:17', 'AUTHENTICATION_SUCCESS'),
(39, 'xavier', '2016-03-12 10:45:04', 'AUTHENTICATION_SUCCESS'),
(40, 'admin', '2016-03-12 10:47:21', 'AUTHENTICATION_SUCCESS'),
(41, 'xavier', '2016-03-12 10:52:00', 'AUTHENTICATION_SUCCESS'),
(42, 'xavier', '2016-03-12 10:53:31', 'AUTHENTICATION_SUCCESS'),
(43, 'xavier', '2016-03-12 10:57:33', 'AUTHENTICATION_SUCCESS'),
(44, 'xavier', '2016-03-12 10:59:33', 'AUTHENTICATION_SUCCESS'),
(45, 'xavier', '2016-03-12 11:03:22', 'AUTHENTICATION_SUCCESS'),
(46, 'xavier', '2016-03-12 16:46:37', 'AUTHENTICATION_SUCCESS'),
(47, 'xavier', '2016-03-12 16:49:33', 'AUTHENTICATION_SUCCESS'),
(48, 'xavier', '2016-03-12 16:54:59', 'AUTHENTICATION_SUCCESS'),
(49, 'xavier', '2016-03-12 17:05:38', 'AUTHENTICATION_SUCCESS'),
(50, 'xavier', '2016-03-12 17:07:48', 'AUTHENTICATION_SUCCESS'),
(51, 'xavier', '2016-03-12 17:11:17', 'AUTHENTICATION_SUCCESS'),
(52, 'xavier', '2016-03-12 17:14:11', 'AUTHENTICATION_SUCCESS'),
(53, 'xavier', '2016-03-12 17:18:53', 'AUTHENTICATION_SUCCESS'),
(54, 'xavier', '2016-03-12 17:23:38', 'AUTHENTICATION_SUCCESS'),
(55, 'xavier', '2016-03-12 17:26:00', 'AUTHENTICATION_SUCCESS'),
(56, 'xavier', '2016-03-12 17:30:56', 'AUTHENTICATION_SUCCESS'),
(57, 'xavier', '2016-03-12 17:33:56', 'AUTHENTICATION_SUCCESS'),
(58, 'xavier', '2016-03-12 17:36:25', 'AUTHENTICATION_SUCCESS'),
(59, 'xavier', '2016-03-12 17:42:15', 'AUTHENTICATION_SUCCESS'),
(60, 'xavier', '2016-03-12 17:50:27', 'AUTHENTICATION_SUCCESS'),
(61, 'xavier', '2016-03-12 17:56:08', 'AUTHENTICATION_SUCCESS'),
(62, 'xavier', '2016-03-12 17:58:50', 'AUTHENTICATION_SUCCESS'),
(63, 'xavier', '2016-03-12 18:06:04', 'AUTHENTICATION_SUCCESS'),
(64, 'xavier', '2016-03-12 18:08:01', 'AUTHENTICATION_SUCCESS'),
(65, 'xavier', '2016-03-12 18:14:07', 'AUTHENTICATION_SUCCESS'),
(66, 'xavier', '2016-03-12 18:18:12', 'AUTHENTICATION_SUCCESS'),
(67, 'xavier', '2016-03-12 18:21:13', 'AUTHENTICATION_SUCCESS'),
(68, 'xavier', '2016-03-12 18:25:25', 'AUTHENTICATION_SUCCESS'),
(69, 'xavier', '2016-03-12 18:27:18', 'AUTHENTICATION_SUCCESS'),
(70, 'admin', '2016-03-12 18:30:13', 'AUTHENTICATION_SUCCESS'),
(71, 'xavier', '2016-03-12 18:32:36', 'AUTHENTICATION_SUCCESS'),
(72, 'xavier', '2016-03-12 18:43:27', 'AUTHENTICATION_SUCCESS'),
(73, 'xavier', '2016-03-12 18:56:35', 'AUTHENTICATION_SUCCESS'),
(74, 'xavier', '2016-03-12 18:58:41', 'AUTHENTICATION_SUCCESS'),
(75, 'xavier', '2016-03-12 19:00:20', 'AUTHENTICATION_SUCCESS'),
(76, 'admin', '2016-03-12 19:01:10', 'AUTHENTICATION_SUCCESS'),
(77, 'xavier', '2016-03-12 19:11:02', 'AUTHENTICATION_SUCCESS'),
(78, 'xavier', '2016-03-12 19:16:48', 'AUTHENTICATION_SUCCESS'),
(79, 'xavier', '2016-03-12 19:26:57', 'AUTHENTICATION_SUCCESS'),
(80, 'yanick', '2016-03-13 14:00:23', 'AUTHENTICATION_SUCCESS'),
(81, 'admin', '2016-03-13 14:02:09', 'AUTHENTICATION_SUCCESS'),
(82, 'xavier', '2016-03-13 14:09:05', 'AUTHENTICATION_SUCCESS'),
(83, 'xavier', '2016-03-13 14:11:37', 'AUTHENTICATION_SUCCESS'),
(84, 'xavier', '2016-03-13 14:14:29', 'AUTHENTICATION_SUCCESS'),
(85, 'xavier', '2016-03-13 14:15:45', 'AUTHENTICATION_SUCCESS'),
(86, 'xavier', '2016-03-13 14:20:58', 'AUTHENTICATION_SUCCESS'),
(87, 'xavier', '2016-03-13 14:23:20', 'AUTHENTICATION_SUCCESS'),
(88, 'yanick', '2016-03-13 14:23:51', 'AUTHENTICATION_SUCCESS'),
(89, 'xavier', '2016-03-13 14:25:28', 'AUTHENTICATION_SUCCESS'),
(90, 'xavier', '2016-03-13 14:28:28', 'AUTHENTICATION_SUCCESS'),
(91, 'xavier', '2016-03-13 14:30:37', 'AUTHENTICATION_SUCCESS'),
(92, 'xavier', '2016-03-13 14:35:58', 'AUTHENTICATION_SUCCESS'),
(93, 'xavier', '2016-03-13 14:37:28', 'AUTHENTICATION_SUCCESS'),
(94, 'xavier', '2016-03-13 14:44:39', 'AUTHENTICATION_SUCCESS'),
(95, 'xavier', '2016-03-13 14:48:14', 'AUTHENTICATION_SUCCESS'),
(96, 'xavier', '2016-03-13 14:53:04', 'AUTHENTICATION_SUCCESS'),
(97, 'xavier', '2016-03-13 15:03:46', 'AUTHENTICATION_SUCCESS'),
(98, 'admin', '2016-03-13 15:06:00', 'AUTHENTICATION_SUCCESS'),
(99, 'xavier', '2016-03-13 15:10:44', 'AUTHENTICATION_SUCCESS'),
(100, 'xavier', '2016-03-13 15:15:18', 'AUTHENTICATION_SUCCESS'),
(101, 'xavier', '2016-03-13 15:20:25', 'AUTHENTICATION_SUCCESS'),
(102, 'xavier', '2016-03-13 15:25:13', 'AUTHENTICATION_SUCCESS'),
(103, 'xavier', '2016-03-13 16:32:37', 'AUTHENTICATION_SUCCESS'),
(104, 'xavier', '2016-03-13 16:38:56', 'AUTHENTICATION_SUCCESS'),
(105, 'xavier', '2016-03-13 16:42:20', 'AUTHENTICATION_SUCCESS'),
(106, 'admin', '2016-03-13 16:46:42', 'AUTHENTICATION_SUCCESS'),
(107, 'admin', '2016-03-13 16:46:47', 'AUTHENTICATION_SUCCESS'),
(108, 'admin', '2016-03-13 16:46:52', 'AUTHENTICATION_SUCCESS'),
(109, 'admin', '2016-03-13 16:46:54', 'AUTHENTICATION_SUCCESS');

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
(26, 'message', 'Bad credentials'),
(26, 'type', 'org.springframework.security.authentication.BadCredentialsException');

-- --------------------------------------------------------

--
-- Structure de la table `jhi_user`
--

CREATE TABLE `jhi_user` (
  `id` bigint(20) NOT NULL,
  `login` varchar(50) NOT NULL,
  `password_hash` varchar(60) DEFAULT NULL,
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

INSERT INTO `jhi_user` (`id`, `login`, `password_hash`, `first_name`, `last_name`, `email`, `activated`, `lang_key`, `activation_key`, `reset_key`, `created_by`, `created_date`, `reset_date`, `last_modified_by`, `last_modified_date`, `phone`, `distance`) VALUES
(1, 'system', '$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG', 'System', 'System', 'system@localhost', b'1', 'en', NULL, NULL, 'system', '2016-03-09 20:26:21', NULL, NULL, NULL, '', 20),
(2, 'anonymousUser', '$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO', 'Anonymous', 'User', 'anonymous@localhost', b'1', 'en', NULL, NULL, 'system', '2016-03-09 20:26:21', NULL, NULL, NULL, '', 20),
(3, 'admin', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', 'Administrator', 'Administrator', 'admin@localhost', b'1', 'en', NULL, NULL, 'system', '2016-03-09 20:26:21', NULL, NULL, NULL, '', 20),
(4, 'user', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'User', 'User', 'user@localhost', b'1', 'en', NULL, NULL, 'system', '2016-03-09 20:26:21', NULL, NULL, NULL, '', 20),
(5, 'yanick', '$2a$10$0HQwmldUl.DWsoqxPMSq4uQkhpqb.or61NBzPsHDCEBnc3LMCB8Ii', NULL, NULL, 'yanick.servant@free.fr', b'1', 'en', '11286308224961853646', NULL, 'anonymousUser', '2016-03-02 19:16:48', NULL, 'admin', '2016-03-02 20:16:06', NULL, 20),
(6, 'xavier', '$2a$10$Wwjvcp5AkzUjaPm6cjNh4erwyGN6RdJzVzOIZLGGTEEv0W65UJNHu', NULL, NULL, 'xavier@gmail.com', b'1', 'en', '08866584719554116417', NULL, 'anonymousUser', '2016-03-02 20:23:29', NULL, 'admin', '2016-03-02 20:23:38', NULL, 20),
(7, 'thomas', '$2a$10$oyc7Pws4EBjSREHRqT9YZ.l3wlBWVpiAoMmQT0zoxcJ.rcgwn5Uk2', NULL, NULL, 'thomas@gmail.com', b'1', 'en', '35866555979295612805', NULL, 'anonymousUser', '2016-03-02 20:27:00', NULL, 'admin', '2016-03-02 20:27:16', NULL, 20),
(8, 'lucas', '$2a$10$j.2fdtG3oGlKPaJfrQxGI.gxTuOWvUljbCx.e4Di92shjgxgRm3a.', NULL, NULL, 'lucas@free.fr', b'1', 'en', '47782451794605067749', NULL, 'anonymousUser', '2016-03-02 20:28:33', NULL, 'admin', '2016-03-02 20:33:50', NULL, 20),
(9, 'marion', '$2a$10$wus6wzcKGWtmn6H38q43QebjiW2MekGCZOpK6S1Pcp11oN9VpTSxG', NULL, NULL, 'marion@hotmail.fr', b'1', 'en', '00385403215466897083', NULL, 'anonymousUser', '2016-03-02 20:44:12', NULL, 'admin', '2016-03-02 20:33:50', '0667655444', 20);

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
  ADD KEY `fk_animal_animaltype_id` (`animal_type_id`),
  ADD KEY `fk_animal_breed_id` (`breed_id`),
  ADD KEY `fk_user_animal_id` (`user_id`);

--
-- Index pour la table `animal_type`
--
ALTER TABLE `animal_type`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `breed`
--
ALTER TABLE `breed`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_breed_animaltype_id` (`animal_type_id`);

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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT pour la table `animal_type`
--
ALTER TABLE `animal_type`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT pour la table `breed`
--
ALTER TABLE `breed`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT pour la table `jhi_persistent_audit_event`
--
ALTER TABLE `jhi_persistent_audit_event`
  MODIFY `event_id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=110;
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
-- Contraintes pour la table `breed`
--
ALTER TABLE `breed`
  ADD CONSTRAINT `fk_breed_animaltype_id` FOREIGN KEY (`animal_type_id`) REFERENCES `animal_type` (`id`);

--
-- Contraintes pour la table `jhi_persistent_audit_evt_data`
--
ALTER TABLE `jhi_persistent_audit_evt_data`
  ADD CONSTRAINT `fk_evt_pers_audit_evt_data` FOREIGN KEY (`event_id`) REFERENCES `jhi_persistent_audit_event` (`event_id`);

--
-- Contraintes pour la table `jhi_user_authority`
--
ALTER TABLE `jhi_user_authority`
  ADD CONSTRAINT `fk_authority_name` FOREIGN KEY (`authority_name`) REFERENCES `jhi_authority` (`name`),
  ADD CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`);
