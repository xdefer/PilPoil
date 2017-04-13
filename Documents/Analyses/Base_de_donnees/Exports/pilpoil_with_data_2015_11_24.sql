-- phpMyAdmin SQL Dump
-- version 4.2.10
-- http://www.phpmyadmin.net
--
-- Client :  localhost:8889
-- Généré le :  Mar 24 Novembre 2015 à 15:24
-- Version du serveur :  5.5.38
-- Version de PHP :  5.6.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `pilpoil`
--

-- --------------------------------------------------------

--
-- Structure de la table `animal`
--

CREATE TABLE `animal` (
`id` bigint(20) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `age` varchar(255) DEFAULT NULL,
  `sexe` varchar(255) DEFAULT NULL,
  `tatouage` varchar(255) DEFAULT NULL,
  `puce` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `races_id` bigint(20) DEFAULT NULL,
  `type_animals_id` bigint(20) DEFAULT NULL,
  `utilisateurs_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `animal`
--

INSERT INTO `animal` (`id`, `nom`, `age`, `sexe`, `tatouage`, `puce`, `photo`, `races_id`, `type_animals_id`, `utilisateurs_id`) VALUES
(1, 'Médore', '4', 'M', '394J489', '30495JJJ5', 'medore.png', 1, 1, 1),
(2, 'Gollum', '2', 'M', NULL, NULL, 'gogo.png', 3, 2, 2),
(3, 'Titi', '', '', NULL, NULL, NULL, NULL, 3, NULL),
(4, 'Milou', '4', 'M', NULL, NULL, NULL, NULL, 2, 3),
(5, 'Ratatouille', '5', NULL, NULL, NULL, NULL, NULL, 4, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `animal_couleurs`
--

CREATE TABLE `animal_couleurs` (
  `couleurss_id` bigint(20) NOT NULL,
  `animals_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `animal_couleurs`
--

INSERT INTO `animal_couleurs` (`couleurss_id`, `animals_id`) VALUES
(1, 2),
(3, 1),
(3, 2);

-- --------------------------------------------------------

--
-- Structure de la table `annonce`
--

CREATE TABLE `annonce` (
`id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `recuperer` bit(1) DEFAULT NULL,
  `date_evenement` varchar(255) DEFAULT NULL,
  `telephone_annonceur` varchar(255) DEFAULT NULL,
  `mail_annonceur` varchar(255) DEFAULT NULL,
  `adresse` varchar(255) DEFAULT NULL,
  `complement_adresse` varchar(255) DEFAULT NULL,
  `code_postal` varchar(255) DEFAULT NULL,
  `ville` varchar(255) DEFAULT NULL,
  `pays` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `lattitude` varchar(255) DEFAULT NULL,
  `type_annonces_id` bigint(20) DEFAULT NULL,
  `utilisateurs_id` bigint(20) DEFAULT NULL,
  `animals_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `annonce`
--

INSERT INTO `annonce` (`id`, `description`, `recuperer`, `date_evenement`, `telephone_annonceur`, `mail_annonceur`, `adresse`, `complement_adresse`, `code_postal`, `ville`, `pays`, `longitude`, `lattitude`, `type_annonces_id`, `utilisateurs_id`, `animals_id`) VALUES
(1, 'Perdu chien type labrador au alentour de l''ancien stade à 19h', NULL, '22/11/2015', '0669059404', NULL, 'Stade Jacques Chaban-Delmas', NULL, '33000', 'BORDEAUX', 'FRANCE', '44.8292363', '-0.5983086', 1, NULL, 1),
(2, 'Chat peureux type siamois croisé blanc et crème', NULL, '18/11/2015', '0650504835', NULL, '18 AVENUE DU CHUT', NULL, '33700', 'MERIGNAC', 'FRANCE', '44.8491356', '-0.6619387', 1, 2, 2),
(3, 'Trouvé chat à Mérignac centre tatoué. Je le garde un mois. Contactez-moi pour plus d''info', b'1', '24/11/2015', '0649382938', 'jeanlucmichou@gmail.com', 'Avenue de l''Yser', NULL, '33700', 'MERIGNAC', 'FRANCE', '44.8416228', '-0.6595285', 2, NULL, NULL),
(4, 'Perroquet Rouge et bleu. Aile droite légèrement abimée. Appartient à ma fille. Merci', b'0', '10/01/2014', '', NULL, '3 Rue du Général Blaise', 'Apt 3', '75011', 'PARIS', 'FRANCE', '48.8611078', '2.3766316', 1, NULL, 3);

-- --------------------------------------------------------

--
-- Structure de la table `couleur`
--

CREATE TABLE `couleur` (
`id` bigint(20) NOT NULL,
  `libelle` varchar(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `couleur`
--

INSERT INTO `couleur` (`id`, `libelle`) VALUES
(1, 'Blanc'),
(2, 'Brun'),
(3, 'Beige'),
(4, 'Rouge'),
(5, 'Gris');

-- --------------------------------------------------------

--
-- Structure de la table `couleur_type_animals`
--

CREATE TABLE `couleur_type_animals` (
  `type_animalss_id` bigint(20) NOT NULL,
  `couleurs_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
('00000000000001', 'jhipster', 'classpath:config/liquibase/changelog/00000000000000_initial_schema.xml', '2015-10-28 15:19:45', 1, 'EXECUTED', '7:db3ea54c411eee3e3a699d954d8ce557', 'createTable, createIndex (x2), createTable (x2), addPrimaryKey, createTable, addForeignKeyConstraint (x3), loadData, dropDefaultValue, loadData (x2), createTable (x2), addPrimaryKey, createIndex (x2), addForeignKeyConstraint', '', NULL, '3.4.1', NULL, NULL),
('20151023142100', 'jhipster', 'classpath:config/liquibase/changelog/20151023142100_added_entity_Race.xml', '2015-10-28 15:19:45', 2, 'EXECUTED', '7:55cb8c0d1b41ef4f4cb30c793dbbcc30', 'createTable', '', NULL, '3.4.1', NULL, NULL),
('20151027181338', 'jhipster', 'classpath:config/liquibase/changelog/20151027181338_added_entity_TypeAnnonce.xml', '2015-10-28 15:19:45', 3, 'EXECUTED', '7:f8ed251472eedac5cf388f1760a09eb9', 'createTable', '', NULL, '3.4.1', NULL, NULL),
('20151023142100', 'jhipster', 'classpath:config/liquibase/changelog/20151023142100_added_entity_TypeAnimal.xml', '2015-10-28 15:19:46', 4, 'EXECUTED', '7:db10201ea8e1e5917ea3380d1ef85261', 'createTable (x2), addPrimaryKey, addForeignKeyConstraint (x2)', '', NULL, '3.4.1', NULL, NULL),
('20151023142100', 'jhipster', 'classpath:config/liquibase/changelog/20151023142100_added_entity_Utilisateur.xml', '2015-10-28 15:19:46', 5, 'EXECUTED', '7:cea70fa831a30dc3d14017dedbf47217', 'createTable', '', NULL, '3.4.1', NULL, NULL),
('20151027181338', 'jhipster', 'classpath:config/liquibase/changelog/20151027181338_added_entity_Couleur.xml', '2015-10-28 15:19:47', 6, 'EXECUTED', '7:fd025d81d35c18afdbf5b649f0197b15', 'createTable (x2), addPrimaryKey, addForeignKeyConstraint (x2)', '', NULL, '3.4.1', NULL, NULL),
('20151028125306', 'jhipster', 'classpath:config/liquibase/changelog/20151028125306_added_entity_Animal.xml', '2015-10-28 15:19:48', 7, 'EXECUTED', '7:1b0b90f91681e176ea2eea348d2a9a11', 'createTable, addForeignKeyConstraint (x3), createTable, addPrimaryKey, addForeignKeyConstraint (x2)', '', NULL, '3.4.1', NULL, NULL),
('20151028125306', 'jhipster', 'classpath:config/liquibase/changelog/20151028125306_added_entity_Annonce.xml', '2015-10-28 15:19:49', 8, 'EXECUTED', '7:9666e59e497db9e467ac3cbccc813b17', 'createTable, addForeignKeyConstraint (x3)', '', NULL, '3.4.1', NULL, NULL);

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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `jhi_persistent_audit_event`
--

INSERT INTO `jhi_persistent_audit_event` (`event_id`, `principal`, `event_date`, `event_type`) VALUES
(1, 'admin', '2015-10-28 14:20:17', 'AUTHENTICATION_SUCCESS'),
(2, 'admin', '2015-10-28 14:20:18', 'AUTHENTICATION_SUCCESS'),
(3, 'admin', '2015-10-28 14:20:18', 'AUTHENTICATION_SUCCESS'),
(4, 'admin', '2015-10-29 08:05:54', 'AUTHENTICATION_SUCCESS'),
(5, 'admin', '2015-10-29 08:33:19', 'AUTHENTICATION_SUCCESS'),
(6, 'admin', '2015-10-29 08:46:52', 'AUTHENTICATION_SUCCESS'),
(7, 'admin', '2015-10-29 08:49:56', 'AUTHENTICATION_SUCCESS'),
(8, 'admin', '2015-10-29 08:52:14', 'AUTHENTICATION_SUCCESS'),
(9, 'admin', '2015-10-29 08:56:13', 'AUTHENTICATION_SUCCESS'),
(10, 'admin', '2015-10-29 09:06:16', 'AUTHENTICATION_SUCCESS'),
(11, 'admin', '2015-10-29 14:00:33', 'AUTHENTICATION_SUCCESS'),
(12, 'admin', '2015-10-29 14:49:01', 'AUTHENTICATION_SUCCESS'),
(13, 'admin', '2015-10-29 16:00:24', 'AUTHENTICATION_SUCCESS'),
(14, 'admin', '2015-10-29 16:24:30', 'AUTHENTICATION_SUCCESS'),
(15, 'admin', '2015-10-31 09:00:45', 'AUTHENTICATION_SUCCESS'),
(16, 'admin', '2015-11-09 10:09:16', 'AUTHENTICATION_SUCCESS'),
(17, 'admin', '2015-11-10 13:31:20', 'AUTHENTICATION_SUCCESS'),
(18, 'admin', '2015-11-10 14:46:18', 'AUTHENTICATION_SUCCESS'),
(19, 'admin', '2015-11-10 16:04:23', 'AUTHENTICATION_SUCCESS'),
(20, 'pilpoil', '2015-11-10 16:05:22', 'AUTHENTICATION_FAILURE'),
(21, 'admin', '2015-11-10 16:05:40', 'AUTHENTICATION_SUCCESS'),
(22, 'admin', '2015-11-10 16:05:40', 'AUTHENTICATION_SUCCESS'),
(23, 'admin', '2015-11-10 16:05:40', 'AUTHENTICATION_SUCCESS'),
(24, 'admin', '2015-11-11 23:04:36', 'AUTHENTICATION_SUCCESS'),
(25, 'admin', '2015-11-12 08:17:37', 'AUTHENTICATION_SUCCESS'),
(26, 'admin', '2015-11-12 13:47:27', 'AUTHENTICATION_SUCCESS'),
(27, 'admin', '2015-11-12 23:10:44', 'AUTHENTICATION_SUCCESS'),
(28, 'admin', '2015-11-13 09:17:40', 'AUTHENTICATION_SUCCESS'),
(29, 'admin', '2015-11-24 13:23:15', 'AUTHENTICATION_SUCCESS');

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
(1, 'remoteAddress', '0:0:0:0:0:0:0:1'),
(1, 'sessionId', '6A1029DF753010FCF4B0C5D8DC4DC1EE'),
(2, 'remoteAddress', '0:0:0:0:0:0:0:1'),
(2, 'sessionId', '6A1029DF753010FCF4B0C5D8DC4DC1EE'),
(3, 'remoteAddress', '0:0:0:0:0:0:0:1'),
(3, 'sessionId', '6A1029DF753010FCF4B0C5D8DC4DC1EE'),
(4, 'remoteAddress', '0:0:0:0:0:0:0:1'),
(4, 'sessionId', '0AE4A95D0CDB13CC1552685DB1D44F40'),
(5, 'remoteAddress', '0:0:0:0:0:0:0:1'),
(5, 'sessionId', '1893980860AA3B13432913A3A295B85B'),
(6, 'remoteAddress', '0:0:0:0:0:0:0:1'),
(6, 'sessionId', 'D75907C425AF2FDE0952D1C2A882960A'),
(7, 'remoteAddress', '0:0:0:0:0:0:0:1'),
(7, 'sessionId', '1CB9F62E50689A2F8D74A58E55EA21E6'),
(8, 'remoteAddress', '0:0:0:0:0:0:0:1'),
(8, 'sessionId', 'E7D29D679DB47EBF543396856E52FF81'),
(9, 'remoteAddress', '0:0:0:0:0:0:0:1'),
(9, 'sessionId', '7083A76F8561713112E0DDB048AC572C'),
(10, 'remoteAddress', '0:0:0:0:0:0:0:1'),
(10, 'sessionId', '693430348DA11A81EB77B777B0AF8F84'),
(11, 'remoteAddress', '0:0:0:0:0:0:0:1'),
(11, 'sessionId', 'DF47323838FD0877C72735C8F0F26337'),
(12, 'remoteAddress', '0:0:0:0:0:0:0:1'),
(12, 'sessionId', '59BBFCF4A3BA3D61438327D073A98383'),
(13, 'remoteAddress', '0:0:0:0:0:0:0:1'),
(13, 'sessionId', '9F7FBE7E25CCA530A61AF8A4FD3309EA'),
(14, 'remoteAddress', '0:0:0:0:0:0:0:1'),
(14, 'sessionId', '1CAA5062649E951A08F2CD4B1467719F'),
(15, 'remoteAddress', '0:0:0:0:0:0:0:1'),
(15, 'sessionId', '5915A22DDAF72033F4E63A97C0AC8DAF'),
(16, 'remoteAddress', '0:0:0:0:0:0:0:1'),
(16, 'sessionId', 'BA86A8EE43936783339EE5DA60677341'),
(17, 'remoteAddress', '0:0:0:0:0:0:0:1'),
(17, 'sessionId', '82BE1F617CF1239F3BB9FEAA6FBB0F19'),
(18, 'remoteAddress', '0:0:0:0:0:0:0:1'),
(18, 'sessionId', '85F034346E84E1439C3C572DE46358B5'),
(19, 'remoteAddress', '0:0:0:0:0:0:0:1'),
(19, 'sessionId', '55D359EA6CE9E1F868EECFCAFDA22780'),
(20, 'message', 'Bad credentials'),
(20, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(21, 'remoteAddress', '0:0:0:0:0:0:0:1'),
(21, 'sessionId', '598A1AD170CC33BF3B3DAC52A66BDEA6'),
(22, 'remoteAddress', '0:0:0:0:0:0:0:1'),
(22, 'sessionId', '598A1AD170CC33BF3B3DAC52A66BDEA6'),
(23, 'remoteAddress', '0:0:0:0:0:0:0:1'),
(23, 'sessionId', '598A1AD170CC33BF3B3DAC52A66BDEA6'),
(24, 'remoteAddress', '0:0:0:0:0:0:0:1'),
(24, 'sessionId', '5C13C196D898043C55623875D56FA34C'),
(25, 'remoteAddress', '0:0:0:0:0:0:0:1'),
(25, 'sessionId', 'F02343D2205852685154C7243176DB8C'),
(26, 'remoteAddress', '0:0:0:0:0:0:0:1'),
(26, 'sessionId', '2072C1BDC424998FC7659D169AC5A777'),
(27, 'remoteAddress', '0:0:0:0:0:0:0:1'),
(27, 'sessionId', 'DC6229BF11DB29D3108519E6B6C5EA85'),
(28, 'remoteAddress', '0:0:0:0:0:0:0:1'),
(28, 'sessionId', '25D69D0DEB33EE4080E4F6C1CC7A607D'),
(29, 'remoteAddress', '0:0:0:0:0:0:0:1'),
(29, 'sessionId', 'AF6F6466BA09749FACC5146A44AF0166');

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
('aakuhrVyPC2qOWRJR455BQ==', 3, 'QzH2EBoIYLmSxnIid2Rvtg==', '2015-11-24', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36');

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
  `last_modified_date` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `jhi_user`
--

INSERT INTO `jhi_user` (`id`, `login`, `PASSWORD`, `first_name`, `last_name`, `email`, `activated`, `lang_key`, `activation_key`, `reset_key`, `created_by`, `created_date`, `reset_date`, `last_modified_by`, `last_modified_date`) VALUES
(1, 'system', '$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG', 'System', 'System', 'system@localhost', b'1', 'en', NULL, NULL, 'system', '2015-10-28 14:19:44', NULL, NULL, NULL),
(2, 'anonymousUser', '$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO', 'Anonymous', 'User', 'anonymous@localhost', b'1', 'en', NULL, NULL, 'system', '2015-10-28 14:19:44', NULL, NULL, NULL),
(3, 'admin', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', 'Administrator', 'Administrator', 'admin@localhost', b'1', 'en', NULL, NULL, 'system', '2015-10-28 14:19:44', NULL, NULL, NULL),
(4, 'user', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'User', 'User', 'user@localhost', b'1', 'en', NULL, NULL, 'system', '2015-10-28 14:19:44', NULL, NULL, NULL);

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
(4, 'ROLE_USER');

-- --------------------------------------------------------

--
-- Structure de la table `race`
--

CREATE TABLE `race` (
`id` bigint(20) NOT NULL,
  `libelle` varchar(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `race`
--

INSERT INTO `race` (`id`, `libelle`) VALUES
(1, 'Labrador'),
(2, 'Berger Allemand'),
(3, 'Siamois'),
(4, 'Persan');

-- --------------------------------------------------------

--
-- Structure de la table `type_animal`
--

CREATE TABLE `type_animal` (
`id` bigint(20) NOT NULL,
  `libelle` varchar(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `type_animal`
--

INSERT INTO `type_animal` (`id`, `libelle`) VALUES
(1, 'Chien'),
(2, 'Chat'),
(3, 'Perroquet'),
(4, 'Rat');

-- --------------------------------------------------------

--
-- Structure de la table `type_animal_races`
--

CREATE TABLE `type_animal_races` (
  `racess_id` bigint(20) NOT NULL,
  `type_animals_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `type_animal_races`
--

INSERT INTO `type_animal_races` (`racess_id`, `type_animals_id`) VALUES
(1, 1),
(2, 1),
(3, 2),
(4, 2);

-- --------------------------------------------------------

--
-- Structure de la table `type_annonce`
--

CREATE TABLE `type_annonce` (
`id` bigint(20) NOT NULL,
  `libelle` varchar(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `type_annonce`
--

INSERT INTO `type_annonce` (`id`, `libelle`) VALUES
(1, 'Perdu'),
(2, 'Trouvé');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
`id` bigint(20) NOT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `distance_alerte` int(11) DEFAULT NULL,
  `actif` bit(1) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `prenom`, `mail`, `telephone`, `distance_alerte`, `actif`) VALUES
(1, 'Yanick', 'yanick@pilpoil.com', '0684948493', 20, b'1'),
(2, 'Xavier', 'xavier@pilpoil.com', '0650653324', 10, b'1'),
(3, 'Lucas', 'lucas@pilpoil.com', '0619209384', 5, b'1'),
(4, 'Thomas', 'thomas@pilpoil.com', '0749382910', 10, NULL);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `animal`
--
ALTER TABLE `animal`
 ADD PRIMARY KEY (`id`), ADD KEY `fk_animal_races_id` (`races_id`), ADD KEY `fk_animal_typeanimals_id` (`type_animals_id`), ADD KEY `fk_animal_utilisateurs_id` (`utilisateurs_id`);

--
-- Index pour la table `animal_couleurs`
--
ALTER TABLE `animal_couleurs`
 ADD PRIMARY KEY (`animals_id`,`couleurss_id`), ADD KEY `fk_couleur_couleurs_animal_id` (`couleurss_id`);

--
-- Index pour la table `annonce`
--
ALTER TABLE `annonce`
 ADD PRIMARY KEY (`id`), ADD KEY `fk_annonce_typeannonces_id` (`type_annonces_id`), ADD KEY `fk_annonce_utilisateurs_id` (`utilisateurs_id`), ADD KEY `fk_annonce_animals_id` (`animals_id`);

--
-- Index pour la table `couleur`
--
ALTER TABLE `couleur`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `couleur_type_animals`
--
ALTER TABLE `couleur_type_animals`
 ADD PRIMARY KEY (`couleurs_id`,`type_animalss_id`), ADD KEY `fk_typeanimal_typeanimals_couleur_id` (`type_animalss_id`);

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
 ADD PRIMARY KEY (`event_id`), ADD KEY `idx_persistent_audit_event` (`principal`,`event_date`);

--
-- Index pour la table `jhi_persistent_audit_evt_data`
--
ALTER TABLE `jhi_persistent_audit_evt_data`
 ADD PRIMARY KEY (`event_id`,`name`), ADD KEY `idx_persistent_audit_evt_data` (`event_id`);

--
-- Index pour la table `jhi_persistent_token`
--
ALTER TABLE `jhi_persistent_token`
 ADD PRIMARY KEY (`series`), ADD KEY `fk_user_persistent_token` (`user_id`);

--
-- Index pour la table `jhi_user`
--
ALTER TABLE `jhi_user`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `login` (`login`), ADD UNIQUE KEY `idx_user_login` (`login`), ADD UNIQUE KEY `email` (`email`), ADD UNIQUE KEY `idx_user_email` (`email`);

--
-- Index pour la table `jhi_user_authority`
--
ALTER TABLE `jhi_user_authority`
 ADD PRIMARY KEY (`user_id`,`authority_name`), ADD KEY `fk_authority_name` (`authority_name`);

--
-- Index pour la table `race`
--
ALTER TABLE `race`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `type_animal`
--
ALTER TABLE `type_animal`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `type_animal_races`
--
ALTER TABLE `type_animal_races`
 ADD PRIMARY KEY (`type_animals_id`,`racess_id`), ADD KEY `fk_race_races_typeanimal_id` (`racess_id`);

--
-- Index pour la table `type_annonce`
--
ALTER TABLE `type_annonce`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
 ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `animal`
--
ALTER TABLE `animal`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT pour la table `annonce`
--
ALTER TABLE `annonce`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT pour la table `couleur`
--
ALTER TABLE `couleur`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT pour la table `jhi_persistent_audit_event`
--
ALTER TABLE `jhi_persistent_audit_event`
MODIFY `event_id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=30;
--
-- AUTO_INCREMENT pour la table `jhi_user`
--
ALTER TABLE `jhi_user`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT pour la table `race`
--
ALTER TABLE `race`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT pour la table `type_animal`
--
ALTER TABLE `type_animal`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT pour la table `type_annonce`
--
ALTER TABLE `type_annonce`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `animal`
--
ALTER TABLE `animal`
ADD CONSTRAINT `fk_animal_races_id` FOREIGN KEY (`races_id`) REFERENCES `race` (`id`),
ADD CONSTRAINT `fk_animal_typeanimals_id` FOREIGN KEY (`type_animals_id`) REFERENCES `type_animal` (`id`),
ADD CONSTRAINT `fk_animal_utilisateurs_id` FOREIGN KEY (`utilisateurs_id`) REFERENCES `utilisateur` (`id`);

--
-- Contraintes pour la table `animal_couleurs`
--
ALTER TABLE `animal_couleurs`
ADD CONSTRAINT `fk_animal_couleurs_couleur_id` FOREIGN KEY (`animals_id`) REFERENCES `animal` (`id`),
ADD CONSTRAINT `fk_couleur_couleurs_animal_id` FOREIGN KEY (`couleurss_id`) REFERENCES `couleur` (`id`);

--
-- Contraintes pour la table `annonce`
--
ALTER TABLE `annonce`
ADD CONSTRAINT `fk_annonce_animals_id` FOREIGN KEY (`animals_id`) REFERENCES `animal` (`id`),
ADD CONSTRAINT `fk_annonce_typeannonces_id` FOREIGN KEY (`type_annonces_id`) REFERENCES `type_annonce` (`id`),
ADD CONSTRAINT `fk_annonce_utilisateurs_id` FOREIGN KEY (`utilisateurs_id`) REFERENCES `utilisateur` (`id`);

--
-- Contraintes pour la table `couleur_type_animals`
--
ALTER TABLE `couleur_type_animals`
ADD CONSTRAINT `fk_couleur_typeanimals_typeanimal_id` FOREIGN KEY (`couleurs_id`) REFERENCES `couleur` (`id`),
ADD CONSTRAINT `fk_typeanimal_typeanimals_couleur_id` FOREIGN KEY (`type_animalss_id`) REFERENCES `type_animal` (`id`);

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

--
-- Contraintes pour la table `type_animal_races`
--
ALTER TABLE `type_animal_races`
ADD CONSTRAINT `fk_race_races_typeanimal_id` FOREIGN KEY (`racess_id`) REFERENCES `race` (`id`),
ADD CONSTRAINT `fk_typeanimal_races_race_id` FOREIGN KEY (`type_animals_id`) REFERENCES `type_animal` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
