-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 13-02-2026 a las 11:04:39
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `proyecto_pokemon`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `entrenador`
--

DROP TABLE IF EXISTS `entrenador`;
CREATE TABLE `entrenador` (
  `id_Entrenador` int(11) NOT NULL,
  `nom_Entrenador` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `img_Entrenador` varchar(255) DEFAULT NULL,
  `pokedollars` int(11) DEFAULT 0,
  `tipo_Entrenador` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mochila`
--

DROP TABLE IF EXISTS `mochila`;
CREATE TABLE `mochila` (
  `id_Entrenador` int(11) NOT NULL,
  `id_Objeto` int(11) NOT NULL,
  `cantidad` int(11) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movimiento`
--

DROP TABLE IF EXISTS `movimiento`;
CREATE TABLE `movimiento` (
  `id_Movimiento` int(11) NOT NULL,
  `nom_Movimiento` varchar(50) NOT NULL,
  `tipo` varchar(20) NOT NULL,
  `mejora` int(11) DEFAULT NULL,
  `estado` varchar(20) DEFAULT NULL,
  `num_Turnos` int(11) DEFAULT NULL,
  `potencia` int(11) DEFAULT NULL,
  `puntos_Poder` int(11) DEFAULT NULL,
  `clase_Movimiento` varchar(20) DEFAULT NULL,
  `desc_Movimiento` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `objeto`
--

DROP TABLE IF EXISTS `objeto`;
CREATE TABLE `objeto` (
  `id_Objeto` int(11) NOT NULL,
  `nom_Objeto` varchar(50) NOT NULL,
  `ataque` int(11) DEFAULT 0,
  `defensa` int(11) DEFAULT 0,
  `velocidad` int(11) DEFAULT 0,
  `ataq_Especial` int(11) DEFAULT 0,
  `defEspecial` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pokedex`
--

DROP TABLE IF EXISTS `pokedex`;
CREATE TABLE `pokedex` (
  `num_Pokedex` int(11) NOT NULL,
  `tipo1` varchar(20) NOT NULL,
  `tipo2` varchar(20) DEFAULT NULL,
  `img_Back` varchar(255) DEFAULT NULL,
  `sonido` varchar(255) DEFAULT NULL,
  `img_Frontal` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pokemon`
--

DROP TABLE IF EXISTS `pokemon`;
CREATE TABLE `pokemon` (
  `id_Pokemon` int(11) NOT NULL,
  `num_Pokedex` int(11) NOT NULL,
  `id_Entrenador` int(11) DEFAULT NULL,
  `id_Objeto` int(11) DEFAULT NULL,
  `mote` varchar(50) DEFAULT NULL,
  `vitalidad` int(11) NOT NULL,
  `ataque` int(11) NOT NULL,
  `defensa` int(11) NOT NULL,
  `ataq_Especial` int(11) NOT NULL,
  `velocidad` int(11) NOT NULL,
  `def_Especial` int(11) NOT NULL,
  `fertilidad` int(11) DEFAULT 5,
  `nivel` int(11) DEFAULT 1,
  `estado` varchar(20) DEFAULT NULL,
  `ubicacion` int(11) DEFAULT NULL,
  `sexo` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pokemon_movimiento`
--

DROP TABLE IF EXISTS `pokemon_movimiento`;
CREATE TABLE `pokemon_movimiento` (
  `id_Pokemon` int(11) NOT NULL,
  `id_Movimiento` int(11) NOT NULL,
  `activo` tinyint(1) DEFAULT 1,
  `puntos_Poder` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `entrenador`
--
ALTER TABLE `entrenador`
  ADD PRIMARY KEY (`id_Entrenador`);

--
-- Indices de la tabla `mochila`
--
ALTER TABLE `mochila`
  ADD PRIMARY KEY (`id_Entrenador`,`id_Objeto`),
  ADD KEY `id_Objeto` (`id_Objeto`);

--
-- Indices de la tabla `movimiento`
--
ALTER TABLE `movimiento`
  ADD PRIMARY KEY (`id_Movimiento`);

--
-- Indices de la tabla `objeto`
--
ALTER TABLE `objeto`
  ADD PRIMARY KEY (`id_Objeto`);

--
-- Indices de la tabla `pokedex`
--
ALTER TABLE `pokedex`
  ADD PRIMARY KEY (`num_Pokedex`);

--
-- Indices de la tabla `pokemon`
--
ALTER TABLE `pokemon`
  ADD PRIMARY KEY (`id_Pokemon`),
  ADD KEY `num_Pokedex` (`num_Pokedex`),
  ADD KEY `id_Entrenador` (`id_Entrenador`),
  ADD KEY `id_Objeto` (`id_Objeto`);

--
-- Indices de la tabla `pokemon_movimiento`
--
ALTER TABLE `pokemon_movimiento`
  ADD PRIMARY KEY (`id_Pokemon`,`id_Movimiento`),
  ADD KEY `id_Movimiento` (`id_Movimiento`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `entrenador`
--
ALTER TABLE `entrenador`
  MODIFY `id_Entrenador` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `movimiento`
--
ALTER TABLE `movimiento`
  MODIFY `id_Movimiento` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `objeto`
--
ALTER TABLE `objeto`
  MODIFY `id_Objeto` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `pokemon`
--
ALTER TABLE `pokemon`
  MODIFY `id_Pokemon` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `mochila`
--
ALTER TABLE `mochila`
  ADD CONSTRAINT `mochila_ibfk_1` FOREIGN KEY (`id_Entrenador`) REFERENCES `entrenador` (`id_Entrenador`),
  ADD CONSTRAINT `mochila_ibfk_2` FOREIGN KEY (`id_Objeto`) REFERENCES `objeto` (`id_Objeto`);

--
-- Filtros para la tabla `pokemon`
--
ALTER TABLE `pokemon`
  ADD CONSTRAINT `pokemon_ibfk_1` FOREIGN KEY (`num_Pokedex`) REFERENCES `pokedex` (`num_Pokedex`),
  ADD CONSTRAINT `pokemon_ibfk_2` FOREIGN KEY (`id_Entrenador`) REFERENCES `entrenador` (`id_Entrenador`),
  ADD CONSTRAINT `pokemon_ibfk_3` FOREIGN KEY (`id_Objeto`) REFERENCES `objeto` (`id_Objeto`);

--
-- Filtros para la tabla `pokemon_movimiento`
--
ALTER TABLE `pokemon_movimiento`
  ADD CONSTRAINT `pokemon_movimiento_ibfk_1` FOREIGN KEY (`id_Pokemon`) REFERENCES `pokemon` (`id_Pokemon`),
  ADD CONSTRAINT `pokemon_movimiento_ibfk_2` FOREIGN KEY (`id_Movimiento`) REFERENCES `movimiento` (`id_Movimiento`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
