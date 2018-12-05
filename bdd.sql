-- --------------------------------------------------------
-- Hôte :                        localhost
-- Version du serveur:           5.7.19 - MySQL Community Server (GPL)
-- SE du serveur:                Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Export de la structure de la base pour projet_2018
CREATE DATABASE IF NOT EXISTS `projet_2018` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `projet_2018`;

-- Export de la structure de la table projet_2018. classes
CREATE TABLE IF NOT EXISTS `classes` (
  `id_class` int(11) NOT NULL AUTO_INCREMENT,
  `titre_class` varchar(50) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_class`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- Les données exportées n'étaient pas sélectionnées.
-- Export de la structure de la table projet_2018. etudiants
CREATE TABLE IF NOT EXISTS `etudiants` (
  `id_etudiant` int(11) NOT NULL AUTO_INCREMENT,
  `nom_etud` varchar(50) DEFAULT NULL,
  `prenom_etud` varchar(50) DEFAULT NULL,
  `naissance_etud` varchar(50) DEFAULT NULL,
  `age_etud` int(11) DEFAULT NULL,
  `sexe_etud` enum('H','F') DEFAULT 'H',
  `classe_etud` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_etudiant`),
  KEY `FK_etudiants_classes` (`classe_etud`),
  CONSTRAINT `FK_etudiants_classes` FOREIGN KEY (`classe_etud`) REFERENCES `classes` (`id_class`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;

-- Les données exportées n'étaient pas sélectionnées.
-- Export de la structure de la table projet_2018. matieres
CREATE TABLE IF NOT EXISTS `matieres` (
  `id_matiere` int(11) NOT NULL AUTO_INCREMENT,
  `libelle_mat` varchar(50) NOT NULL,
  `coef_mat` int(11) NOT NULL,
  PRIMARY KEY (`id_matiere`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

-- Les données exportées n'étaient pas sélectionnées.
-- Export de la structure de la table projet_2018. notes
CREATE TABLE IF NOT EXISTS `notes` (
  `id_mat` int(11) NOT NULL,
  `id_etud` int(11) NOT NULL,
  `note_obtenu` double NOT NULL,
  KEY `FK_notes_etudiants` (`id_etud`),
  KEY `FK_notes_matieres` (`id_mat`),
  CONSTRAINT `FK_notes_etudiants` FOREIGN KEY (`id_etud`) REFERENCES `etudiants` (`id_etudiant`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_notes_matieres` FOREIGN KEY (`id_mat`) REFERENCES `matieres` (`id_matiere`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Les données exportées n'étaient pas sélectionnées.
-- Export de la structure de la table projet_2018. utilisateurs
CREATE TABLE IF NOT EXISTS `utilisateurs` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `pseudo` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- Les données exportées n'étaient pas sélectionnées.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
