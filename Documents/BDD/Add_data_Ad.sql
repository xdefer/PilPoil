-- phpMyAdmin SQL Dump
-- version 4.2.10
-- http://www.phpmyadmin.net
--
-- Client :  localhost:8889
-- Généré le :  Sam 13 Février 2016 à 23:40
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
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8;

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
(7, NULL, b'1', '20/01/2014', '0669553011', 'thibault@free.fr', '30 avenue de la libération', NULL, '33700', 'Mérignac', 'France', '-0.6447370999999293', '44.84533239999999', 2, 11, NULL),
(8, NULL, b'0', '06/02/2016', NULL, NULL, '6 Place Rohan', NULL, '33000', 'Bordeaux', 'France', '-0.5791783333333334', '44.83778833333334', 2, 12, NULL),
(23, 'Chat vu sur Mérignac', NULL, '10/03/2016', '', NULL, '29 Avenue de Bedat', NULL, '33700', 'Mérignac', 'France', '-0.64812899', '44.8487192', 2, 13, NULL),
(24, 'Vu sur Pessac', NULL, '10/03/2016', NULL, NULL, '45 Avenue Pasteur', NULL, '33600', 'Pessac', 'France', '-0.63420296', '44.80574202', 2, 14, NULL),
(25, 'Vu à Libourne', NULL, '10/03/2016', NULL, NULL, '14 Rue Paul Bert', NULL, '33500', 'Libourne', 'France', '-0.24496078', '44.91339841', 2, 15, NULL),
(37, 'Trouvé chat à Floirac', NULL, '20/03/2016', NULL, NULL, '31 Rue Jules Guesde', NULL, '33270', 'Floirac', 'France', '-0.5299024', '44.8312385', 2, 16, NULL),
(38, 'Chat vu sur Cestas', NULL, '20/03/2016', NULL, NULL, 'Chemin de Seguin', NULL, '33610', 'Cestas', 'France', '-0.6928435', '44.719556', 2, 17, NULL),
(39, 'Chat vu sur Saint-Loubès', NULL, '20/03/2016', NULL, NULL, 'Route des Estuaires', NULL, '33440', 'Saint-Vincent-de-Paul', 'France', '-0.4635084', '44.9495661', 2, 18, NULL),
(40, 'Vu à Margaux', NULL, '20/03/2016', NULL, NULL, '29-32 D105', NULL, '33460', 'Margaux', 'France', '-0.685925', '45.038652', 2, 19, NULL),
(41, 'Vu sur Le Temple', b'0', '20/03/2016', NULL, NULL, '2 Route de Blagon', NULL, '33680', 'Le Temple', 'France', '-0.989356', '44.880230', 2, 20, NULL);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `ad`
--
ALTER TABLE `ad`
 ADD PRIMARY KEY (`id`), ADD KEY `fk_ad_adtype_id` (`ad_type_id`), ADD KEY `fk_ad_animal_id` (`animal_id`), ADD KEY `fk_user_ad_id` (`user_id`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `ad`
--
ALTER TABLE `ad`
MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=91;
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

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
