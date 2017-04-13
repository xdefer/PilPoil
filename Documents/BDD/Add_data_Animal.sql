-- phpMyAdmin SQL Dump
-- version 4.2.10
-- http://www.phpmyadmin.net
--
-- Client :  localhost:8889
-- Généré le :  Sam 13 Février 2016 à 23:39
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
  `name` varchar(255) DEFAULT NULL,
  `age` varchar(255) DEFAULT NULL,
  `sexe` varchar(255) DEFAULT NULL,
  `tatoo` varchar(255) DEFAULT NULL,
  `chip` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `breed_id` bigint(20) DEFAULT NULL,
  `animal_type_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

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
(11, NULL, 'Adulte', 'Male', 'TATEE125ED', NULL, 'http://www.chatsnoirs.com/medias/images/europeen-noir.jpg', NULL, 1, NULL),
(12, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 2, NULL),
(13, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL),
(14, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL),
(15, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL),
(16, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL),
(17, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL),
(18, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL),
(19, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL),
(20, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `animal`
--
ALTER TABLE `animal`
 ADD PRIMARY KEY (`id`), ADD KEY `fk_animal_breed_id` (`breed_id`), ADD KEY `fk_animal_animaltype_id` (`animal_type_id`), ADD KEY `fk_user_animal_id` (`user_id`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `animal`
--
ALTER TABLE `animal`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=21;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `animal`
--
ALTER TABLE `animal`
ADD CONSTRAINT `fk_animal_animaltype_id` FOREIGN KEY (`animal_type_id`) REFERENCES `animal_type` (`id`),
ADD CONSTRAINT `fk_animal_breed_id` FOREIGN KEY (`breed_id`) REFERENCES `breed` (`id`),
ADD CONSTRAINT `fk_user_animal_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
