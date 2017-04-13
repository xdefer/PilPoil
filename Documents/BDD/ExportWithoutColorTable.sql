-- phpMyAdmin SQL Dump
-- version 4.4.10
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Mer 02 Mars 2016 à 23:53
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
(1, 'Chat perdu près du veto à Mérignac le 08/01 à 18H', NULL, '08/01/2016', NULL, NULL, '9 avenue de la libération', NULL, '33700', 'Mérignac', 'France', '-0.6455985999999712', '44.8429936', 1, 3, 6),
(2, 'Mon chat à sauté du balcon le 20/09/2015', NULL, '21/09/2015', NULL, NULL, '18 Avenue du Chut', 'Domaine de Valois', '33700', 'Mérignac', 'France', '-0.6597447000000329', '44.84913179999999', 3, 2, 6),
(3, 'Mon chien a fait un trou dans le grillage pendant la nuit et c''est enfuit', NULL, '20/01/2016', NULL, NULL, '420 Rue du Grand Barrail', NULL, '33127', 'Saint-Jean-d''Illac', 'France', '-0.8493601000000126', '44.8068572', 1, 7, 8),
(4, 'Chat retrouvé avenue de la libération, je l''ai trouvé en pleine nuit et l''ai amené chez moi', b'1', '01/03/2016', NULL, '', '30 avenue de la libération', '', '33700', 'Mérignac', 'France', '-0.6447370999999293', '44.84533239999999', 2, 8, 9),
(5, NULL, NULL, '01/03/2016', NULL, NULL, '391 Rue de Vaugirard', NULL, '75015', 'Paris', 'France', '2.292319799999973', '48.8347839', 2, 9, NULL),
(6, NULL, NULL, '10/01/2016', '0669553011', NULL, '18 Rue des Bouleaux', NULL, '33600', 'Pessac', 'France', '-0.688220699999988', '44.7973314', 2, 10, NULL),
(7, NULL, b'1', '29/02/2014', '0669553011', 'thibault@free.fr', '30 avenue de la libération', NULL, '33700', 'Mérignac', 'France', '-0.6447370999999293', '44.84533239999999', 2, 11, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `ad_type`
--

CREATE TABLE `ad_type` (
  `id` bigint(20) NOT NULL,
  `label` varchar(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `animal`
--

INSERT INTO `animal` (`id`, `name`, `age`, `sexe`, `tatoo`, `chip`, `photo`, `colors`, `animal_type_id`, `breed_id`, `user_id`) VALUES
(1, 'Lumia', 'Jeune', 'Male', 'TATOO123ED', NULL, 'http://static.wamiz.fr/images/news/medium/chat-tabby.jpg', 'Tigré gris noir', 1, 13, 5),
(2, 'Gollum', 'Adulte', 'Male', 'TATOO145TG', NULL, NULL, 'Beige, blanc', 1, 8, 6),
(3, 'Marie', 'Adulte', 'Femelle', 'TATOO156TY', NULL, 'http://fr.cdn.v5.futura-sciences.com/sources/images/dossier/rte/magic/4111_je_siamois.jpg', 'Beige, blanc', 1, 8, 6),
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

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
('00000000000001', 'jhipster', 'classpath:config/liquibase/changelog/00000000000000_initial_schema.xml', '2016-03-02 20:03:54', 1, 'EXECUTED', '7:db3ea54c411eee3e3a699d954d8ce557', 'createTable, createIndex (x2), createTable (x2), addPrimaryKey, createTable, addForeignKeyConstraint (x3), loadData, dropDefaultValue, loadData (x2), createTable (x2), addPrimaryKey, createIndex (x2), addForeignKeyConstraint', '', NULL, '3.4.1', NULL, NULL),
('20151211215522', 'jhipster', 'classpath:config/liquibase/changelog/20151211215522_added_entity_AdType.xml', '2016-03-02 20:03:54', 2, 'EXECUTED', '7:0b16ce727cc4529ff935efbf2582f3fa', 'createTable', '', NULL, '3.4.1', NULL, NULL),
('20151211215522', 'jhipster', 'classpath:config/liquibase/changelog/20151211215522_added_entity_AnimalType.xml', '2016-03-02 20:03:54', 3, 'EXECUTED', '7:99cb1d62ee189db53da38c12d8a6061f', 'createTable', '', NULL, '3.4.1', NULL, NULL),
('20151211215522', 'jhipster', 'classpath:config/liquibase/changelog/20151211215522_added_entity_Breed.xml', '2016-03-02 20:03:54', 4, 'EXECUTED', '7:d60909f15f976aa4751cd78b8e2db135', 'createTable, addForeignKeyConstraint', '', NULL, '3.4.1', NULL, NULL),
('20151211215522', 'jhipster', 'classpath:config/liquibase/changelog/20151211215522_added_entity_Animal.xml', '2016-03-02 20:03:54', 5, 'EXECUTED', '7:2e0a04b82d8e636abe604c082703dedc', 'createTable, addForeignKeyConstraint (x2)', '', NULL, '3.4.1', NULL, NULL),
('20151211215522', 'jhipster', 'classpath:config/liquibase/changelog/20151211215522_added_entity_Ad.xml', '2016-03-02 20:03:54', 6, 'EXECUTED', '7:24485ffbb55c65b5ce65cb7f07cf3246', 'createTable, addForeignKeyConstraint (x2)', '', NULL, '3.4.1', NULL, NULL),
('20151211215522', 'yanick', 'classpath:config/liquibase/changelog/20151211232200_added_user_column.xml', '2016-03-02 20:03:54', 7, 'EXECUTED', '7:01c96c9aee0abe42fd9b33b8a9c06400', 'addColumn (x2)', '', NULL, '3.4.1', NULL, NULL),
('20151212111000', 'yanick', 'classpath:config/liquibase/changelog/20151212111000_added_user_relationship.xml', '2016-03-02 20:03:54', 8, 'EXECUTED', '7:cf6f44b1b788b865c979ef0932052cbc', 'addColumn, addForeignKeyConstraint, addColumn, addForeignKeyConstraint', '', NULL, '3.4.1', NULL, NULL);

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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `jhi_persistent_audit_event`
--

INSERT INTO `jhi_persistent_audit_event` (`event_id`, `principal`, `event_date`, `event_type`) VALUES
(1, 'admin', '2016-03-02 19:05:44', 'AUTHENTICATION_SUCCESS'),
(2, 'admin', '2016-03-02 19:05:44', 'AUTHENTICATION_SUCCESS'),
(3, 'admin', '2016-03-02 19:05:44', 'AUTHENTICATION_SUCCESS'),
(4, 'admin', '2016-03-02 20:09:43', 'AUTHENTICATION_SUCCESS'),
(5, 'admin', '2016-03-02 20:16:55', 'AUTHENTICATION_SUCCESS'),
(6, 'admin', '2016-03-02 20:16:55', 'AUTHENTICATION_SUCCESS'),
(7, 'admin', '2016-03-02 20:16:55', 'AUTHENTICATION_SUCCESS'),
(8, 'admin', '2016-03-02 21:02:41', 'AUTHENTICATION_SUCCESS'),
(9, 'admin', '2016-03-02 21:23:34', 'AUTHENTICATION_SUCCESS'),
(10, 'admin', '2016-03-02 21:23:34', 'AUTHENTICATION_SUCCESS'),
(11, 'admin', '2016-03-02 21:23:34', 'AUTHENTICATION_SUCCESS'),
(12, 'admin', '2016-03-02 21:27:06', 'AUTHENTICATION_SUCCESS'),
(13, 'admin', '2016-03-02 21:27:06', 'AUTHENTICATION_SUCCESS'),
(14, 'admin', '2016-03-02 21:27:06', 'AUTHENTICATION_SUCCESS'),
(15, 'admin', '2016-03-02 21:28:39', 'AUTHENTICATION_SUCCESS'),
(16, 'admin', '2016-03-02 21:28:39', 'AUTHENTICATION_SUCCESS'),
(17, 'admin', '2016-03-02 21:28:39', 'AUTHENTICATION_SUCCESS'),
(18, 'admin', '2016-03-02 21:33:41', 'AUTHENTICATION_SUCCESS'),
(19, 'admin', '2016-03-02 21:33:41', 'AUTHENTICATION_SUCCESS'),
(20, 'admin', '2016-03-02 21:33:41', 'AUTHENTICATION_SUCCESS'),
(21, 'admin', '2016-03-02 21:52:15', 'AUTHENTICATION_SUCCESS'),
(22, 'admin', '2016-03-02 21:56:10', 'AUTHENTICATION_SUCCESS'),
(23, 'admin', '2016-03-02 22:00:26', 'AUTHENTICATION_SUCCESS'),
(24, 'admin', '2016-03-02 22:03:18', 'AUTHENTICATION_SUCCESS'),
(25, 'admin', '2016-03-02 22:06:28', 'AUTHENTICATION_SUCCESS'),
(26, 'admin', '2016-03-02 22:18:28', 'AUTHENTICATION_SUCCESS');

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
(1, 'sessionId', 'D850C13288B8315A68EABC6C8208592F'),
(2, 'remoteAddress', '127.0.0.1'),
(2, 'sessionId', 'D850C13288B8315A68EABC6C8208592F'),
(3, 'remoteAddress', '127.0.0.1'),
(3, 'sessionId', 'D850C13288B8315A68EABC6C8208592F'),
(4, 'remoteAddress', '127.0.0.1'),
(4, 'sessionId', 'AEC56F138BF4A6BA2CAF9454F296E22E'),
(5, 'remoteAddress', '127.0.0.1'),
(5, 'sessionId', 'FE9BAC385FC3DA80A225278504852FC9'),
(6, 'remoteAddress', '127.0.0.1'),
(6, 'sessionId', 'FE9BAC385FC3DA80A225278504852FC9'),
(7, 'remoteAddress', '127.0.0.1'),
(7, 'sessionId', 'FE9BAC385FC3DA80A225278504852FC9'),
(8, 'remoteAddress', '127.0.0.1'),
(8, 'sessionId', 'C9F70F482D16E4B9D6D6BDD5B468C787'),
(9, 'remoteAddress', '127.0.0.1'),
(9, 'sessionId', '2E26A0CBE837CA76949414F7BE49A34A'),
(10, 'remoteAddress', '127.0.0.1'),
(10, 'sessionId', '2E26A0CBE837CA76949414F7BE49A34A'),
(11, 'remoteAddress', '127.0.0.1'),
(11, 'sessionId', '2E26A0CBE837CA76949414F7BE49A34A'),
(12, 'remoteAddress', '127.0.0.1'),
(12, 'sessionId', '2413AC4D15EB5C194058902C0D543F14'),
(13, 'remoteAddress', '127.0.0.1'),
(13, 'sessionId', '2413AC4D15EB5C194058902C0D543F14'),
(14, 'remoteAddress', '127.0.0.1'),
(14, 'sessionId', '2413AC4D15EB5C194058902C0D543F14'),
(15, 'remoteAddress', '127.0.0.1'),
(15, 'sessionId', 'D46F4CFD4871AA007735A6B73C9BF33A'),
(16, 'remoteAddress', '127.0.0.1'),
(16, 'sessionId', 'D46F4CFD4871AA007735A6B73C9BF33A'),
(17, 'remoteAddress', '127.0.0.1'),
(17, 'sessionId', 'D46F4CFD4871AA007735A6B73C9BF33A'),
(18, 'remoteAddress', '127.0.0.1'),
(18, 'sessionId', 'B05EA41C4D8B3179A2D637241A9989AA'),
(19, 'remoteAddress', '127.0.0.1'),
(19, 'sessionId', 'B05EA41C4D8B3179A2D637241A9989AA'),
(20, 'remoteAddress', '127.0.0.1'),
(20, 'sessionId', 'B05EA41C4D8B3179A2D637241A9989AA'),
(21, 'remoteAddress', '127.0.0.1'),
(21, 'sessionId', 'DD09FA357C31E1E19F6CA0EA2CD06998'),
(22, 'remoteAddress', '127.0.0.1'),
(22, 'sessionId', '22A40AA1D3AE0A9F7F16F0BAC606FF42'),
(23, 'remoteAddress', '127.0.0.1'),
(23, 'sessionId', 'B42603A1C2D076CB3D3B1EABA805950B'),
(24, 'remoteAddress', '127.0.0.1'),
(24, 'sessionId', 'C7C1A145A3BABAF073CA5D23E5AB3EBD'),
(25, 'remoteAddress', '127.0.0.1'),
(25, 'sessionId', '102E45D228B30AFF822FBC4CCB354086'),
(26, 'remoteAddress', '127.0.0.1'),
(26, 'sessionId', '77E48AEF65CA956A60BDC4DEF80B7219');

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
('sTORgLQOHZw7GIXZAjGkWw==', 3, 'BY6k9pF19ThTGI9IrtR3/w==', '2016-03-02', '127.0.0.1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36');

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
(1, 'system', '$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG', 'System', 'System', 'system@localhost', b'1', 'en', NULL, NULL, 'system', '2016-03-02 19:03:54', NULL, NULL, NULL, '', 20),
(2, 'anonymousUser', '$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO', 'Anonymous', 'User', 'anonymous@localhost', b'1', 'en', NULL, NULL, 'system', '2016-03-02 19:03:54', NULL, NULL, NULL, '', 20),
(3, 'admin', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', 'Administrator', 'Administrator', 'admin@localhost', b'1', 'en', NULL, NULL, 'system', '2016-03-02 19:03:54', NULL, NULL, NULL, '', 20),
(4, 'user', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'User', 'User', 'user@localhost', b'1', 'en', NULL, NULL, 'system', '2016-03-02 19:03:54', NULL, NULL, NULL, '', 20),
(5, 'yanick', '$2a$10$0HQwmldUl.DWsoqxPMSq4uQkhpqb.or61NBzPsHDCEBnc3LMCB8Ii', NULL, NULL, 'yanick.servant@free.fr', b'1', 'en', '11286308224961853646', NULL, 'anonymousUser', '2016-03-02 20:16:48', NULL, 'admin', '2016-03-02 21:16:06', NULL, 20),
(6, 'xavier', '$2a$10$Wwjvcp5AkzUjaPm6cjNh4erwyGN6RdJzVzOIZLGGTEEv0W65UJNHu', NULL, NULL, 'xavier@gmail.com', b'1', 'en', '08866584719554116417', NULL, 'anonymousUser', '2016-03-02 21:23:29', NULL, 'admin', '2016-03-02 21:23:38', NULL, 20),
(7, 'thomas', '$2a$10$oyc7Pws4EBjSREHRqT9YZ.l3wlBWVpiAoMmQT0zoxcJ.rcgwn5Uk2', NULL, NULL, 'thomas@gmail.com', b'1', 'en', '35866555979295612805', NULL, 'anonymousUser', '2016-03-02 21:27:00', NULL, 'admin', '2016-03-02 21:27:16', NULL, 20),
(8, 'lucas', '$2a$10$j.2fdtG3oGlKPaJfrQxGI.gxTuOWvUljbCx.e4Di92shjgxgRm3a.', NULL, NULL, 'lucas@free.fr', b'1', 'en', '47782451794605067749', NULL, 'anonymousUser', '2016-03-02 21:28:33', NULL, 'admin', '2016-03-02 21:33:50', NULL, 20),
(9, 'marion', '$2a$10$wus6wzcKGWtmn6H38q43QebjiW2MekGCZOpK6S1Pcp11oN9VpTSxG', NULL, NULL, 'marion@hotmail.fr', b'1', 'en', '00385403215466897083', NULL, 'anonymousUser', '2016-03-02 21:44:12', NULL, 'admin', '2016-03-02 21:33:50', '0667655444', 20);

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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT pour la table `animal`
--
ALTER TABLE `animal`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT pour la table `animal_type`
--
ALTER TABLE `animal_type`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT pour la table `breed`
--
ALTER TABLE `breed`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT pour la table `jhi_persistent_audit_event`
--
ALTER TABLE `jhi_persistent_audit_event`
  MODIFY `event_id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=27;
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
  ADD CONSTRAINT `fk_user_ad_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`),
  ADD CONSTRAINT `fk_ad_adtype_id` FOREIGN KEY (`ad_type_id`) REFERENCES `ad_type` (`id`),
  ADD CONSTRAINT `fk_ad_animal_id` FOREIGN KEY (`animal_id`) REFERENCES `animal` (`id`);

--
-- Contraintes pour la table `animal`
--
ALTER TABLE `animal`
  ADD CONSTRAINT `fk_user_animal_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`),
  ADD CONSTRAINT `fk_animal_animaltype_id` FOREIGN KEY (`animal_type_id`) REFERENCES `animal_type` (`id`),
  ADD CONSTRAINT `fk_animal_breed_id` FOREIGN KEY (`breed_id`) REFERENCES `breed` (`id`);

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
-- Contraintes pour la table `jhi_persistent_token`
--
ALTER TABLE `jhi_persistent_token`
  ADD CONSTRAINT `fk_user_persistent_token` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`);

--
-- Contraintes pour la table `jhi_user_authority`
--
ALTER TABLE `jhi_user_authority`
  ADD CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`),
  ADD CONSTRAINT `fk_authority_name` FOREIGN KEY (`authority_name`) REFERENCES `jhi_authority` (`name`);
