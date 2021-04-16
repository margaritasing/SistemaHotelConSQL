-- --------------------------------------------------------
-- Host:                         localhost
-- Versión del servidor:         8.0.23 - MySQL Community Server - GPL
-- SO del servidor:              Win64
-- HeidiSQL Versión:             11.2.0.6213
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para hotel
CREATE DATABASE IF NOT EXISTS `hotel` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `hotel`;

-- Volcando estructura para tabla hotel.habitaciones
CREATE TABLE IF NOT EXISTS `habitaciones` (
  `idHabitacion` int NOT NULL AUTO_INCREMENT,
  `capacidad` int NOT NULL DEFAULT '0',
  `nroHabitacion` varchar(10) COLLATE utf8_spanish_ci DEFAULT NULL,
  `codReserva` int DEFAULT NULL,
  `disponible` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`idHabitacion`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- Volcando datos para la tabla hotel.habitaciones: ~10 rows (aproximadamente)
/*!40000 ALTER TABLE `habitaciones` DISABLE KEYS */;
REPLACE INTO `habitaciones` (`idHabitacion`, `capacidad`, `nroHabitacion`, `codReserva`, `disponible`) VALUES
	(1, 2, '1A', 3, 0),
	(2, 4, '1B', 2, 0),
	(3, 6, '1C', NULL, 1),
	(4, 2, '2A', 1, 0),
	(5, 4, '2B', 88, 0),
	(6, 6, '2C', NULL, 1),
	(7, 2, '3A', NULL, 1),
	(8, 4, '3B', NULL, 1),
	(9, 6, '3C', NULL, 1),
	(10, 8, 'Penthouse', NULL, 1);
/*!40000 ALTER TABLE `habitaciones` ENABLE KEYS */;

-- Volcando estructura para tabla hotel.habitacionesxreserva
CREATE TABLE IF NOT EXISTS `habitacionesxreserva` (
  `codReserva` int NOT NULL,
  `idHabitacion` int NOT NULL,
  PRIMARY KEY (`codReserva`,`idHabitacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- Volcando datos para la tabla hotel.habitacionesxreserva: ~3 rows (aproximadamente)
/*!40000 ALTER TABLE `habitacionesxreserva` DISABLE KEYS */;
REPLACE INTO `habitacionesxreserva` (`codReserva`, `idHabitacion`) VALUES
	(1, 4),
	(2, 1),
	(2, 2),
	(3, 1);
/*!40000 ALTER TABLE `habitacionesxreserva` ENABLE KEYS */;

-- Volcando estructura para tabla hotel.huespedes
CREATE TABLE IF NOT EXISTS `huespedes` (
  `idHuesped` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `email` varchar(50) COLLATE utf8_spanish_ci DEFAULT NULL,
  `telefono` varchar(25) CHARACTER SET utf8 COLLATE utf8_spanish_ci DEFAULT NULL,
  `codReserva` int DEFAULT NULL,
  `idHabitacion` int DEFAULT NULL,
  PRIMARY KEY (`idHuesped`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- Volcando datos para la tabla hotel.huespedes: ~6 rows (aproximadamente)
/*!40000 ALTER TABLE `huespedes` DISABLE KEYS */;
REPLACE INTO `huespedes` (`idHuesped`, `nombre`, `email`, `telefono`, `codReserva`, `idHabitacion`) VALUES
	(1, 'pablo', 'mail@empresa.com', '213123', 1, 4),
	(2, 'jazmin', 'asdf@mail.com', '23333', 2, 2),
	(3, 'luz', 'ffff@empre.sa', '2323', 3, 1),
	(4, 'asdf', 'asdfff', 'ffff', NULL, NULL),
	(5, 'pepe', 'pepe@empresa.com', '1111-2233', NULL, NULL),
	(6, 'otro pepe', 'asdf@ar.icm', 'asdffff', NULL, NULL),
	(7, 'test !', 'test', 'test 2', 88, NULL),
	(8, 'test 2!', 'test', 'test 2', 88, NULL),
	(9, 'huesped de prueba', 'test', 'test 2', 29, NULL),
	(10, 'probando', 'probando@nuevo.metodo', '23434343', 8, NULL),
	(11, 'a ver si funciona', 'seis@enuna.habitacion', '2029339', 9, NULL);
/*!40000 ALTER TABLE `huespedes` ENABLE KEYS */;

-- Volcando estructura para tabla hotel.huespedesxreserva
CREATE TABLE IF NOT EXISTS `huespedesxreserva` (
  `codReserva` int NOT NULL,
  `idHuesped` int NOT NULL,
  PRIMARY KEY (`codReserva`,`idHuesped`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- Volcando datos para la tabla hotel.huespedesxreserva: ~3 rows (aproximadamente)
/*!40000 ALTER TABLE `huespedesxreserva` DISABLE KEYS */;
REPLACE INTO `huespedesxreserva` (`codReserva`, `idHuesped`) VALUES
	(1, 1),
	(2, 2),
	(3, 3),
	(9, 11),
	(88, 89);
/*!40000 ALTER TABLE `huespedesxreserva` ENABLE KEYS */;

-- Volcando estructura para tabla hotel.reservas
CREATE TABLE IF NOT EXISTS `reservas` (
  `codReserva` int NOT NULL AUTO_INCREMENT,
  `estado` int NOT NULL COMMENT 'ENUM',
  `cantidadHuespedes` int NOT NULL,
  `cantidadHabitaciones` int NOT NULL,
  PRIMARY KEY (`codReserva`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- Volcando datos para la tabla hotel.reservas: ~7 rows (aproximadamente)
/*!40000 ALTER TABLE `reservas` DISABLE KEYS */;
REPLACE INTO `reservas` (`codReserva`, `estado`, `cantidadHuespedes`, `cantidadHabitaciones`) VALUES
	(1, 1, 1, 1),
	(2, 2, 2, 1),
	(3, 1, 1, 1),
	(4, 1, 3, 1),
	(5, 1, 4, 1),
	(6, 1, 8, 1),
	(7, 1, 8, 1),
	(8, 1, 8, 2),
	(9, 1, 6, 1);
/*!40000 ALTER TABLE `reservas` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
