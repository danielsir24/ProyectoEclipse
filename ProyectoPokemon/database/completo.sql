-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 14-02-2026 a las 13:20:42
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

SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `pokemon_movimiento`;
DROP TABLE IF EXISTS `mochila`;
DROP TABLE IF EXISTS `pokemon`;
DROP TABLE IF EXISTS `entrenador`;
DROP TABLE IF EXISTS `movimiento`;
DROP TABLE IF EXISTS `objeto`;
DROP TABLE IF EXISTS `pokedex`;
SET FOREIGN_KEY_CHECKS=1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `entrenador`
--

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

CREATE TABLE `mochila` (
  `id_Entrenador` int(11) NOT NULL,
  `id_Objeto` int(11) NOT NULL,
  `cantidad` int(11) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movimiento`
--

CREATE TABLE `movimiento` (
  `id_Movimiento` int(11) NOT NULL,
  `nom_Movimiento` varchar(50) NOT NULL,
  `tipo` varchar(20) NOT NULL,
  `mejora` varchar(20) DEFAULT NULL,
  `estado` varchar(20) DEFAULT NULL,
  `num_Turnos` int(11) DEFAULT NULL,
  `potencia` int(11) DEFAULT NULL,
  `puntos_Poder` int(11) DEFAULT NULL,
  `clase_Movimiento` enum('ATAQUE','ESTADO','MEJORA') NOT NULL,
  `desc_Movimiento` text DEFAULT NULL,
  `coste_estamina` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `movimiento`
--

INSERT INTO `movimiento` (`id_Movimiento`, `nom_Movimiento`, `tipo`, `mejora`, `estado`, `num_Turnos`, `potencia`, `puntos_Poder`, `clase_Movimiento`, `desc_Movimiento`, `coste_estamina`) VALUES
(1, 'Placaje', 'NORMAL', NULL, NULL, NULL, 40, NULL, 'ATAQUE', 'Ataque básico de contacto', 20),
(2, 'Arañazo', 'NORMAL', NULL, NULL, NULL, 40, NULL, 'ATAQUE', 'Araña al enemigo con garras afiladas', 20),
(3, 'Destructor', 'NORMAL', NULL, NULL, NULL, 40, NULL, 'ATAQUE', 'Ataque destructivo básico', 20),
(4, 'Ataque Rápido', 'NORMAL', NULL, NULL, NULL, 40, NULL, 'ATAQUE', 'Ataque veloz que siempre golpea primero', 20),
(5, 'Mega Puño', 'NORMAL', NULL, NULL, NULL, 80, NULL, 'ATAQUE', 'Puñetazo muy potente', 40),
(6, 'Hiper Rayo', 'NORMAL', NULL, NULL, NULL, 150, NULL, 'ATAQUE', 'Ataque devastador de gran potencia', 75),
(7, 'Ascuas', 'FUEGO', NULL, NULL, NULL, 40, NULL, 'ATAQUE', 'Lanza pequeñas llamas al enemigo', 20),
(8, 'Lanzallamas', 'FUEGO', NULL, NULL, NULL, 90, NULL, 'ATAQUE', 'Potente llamarada', 45),
(9, 'Giro Fuego', 'FUEGO', NULL, NULL, NULL, 100, NULL, 'ATAQUE', 'Envuelve al enemigo en un torbellino de fuego', 50),
(10, 'Pistola Agua', 'AGUA', NULL, NULL, NULL, 40, NULL, 'ATAQUE', 'Dispara un chorro de agua', 20),
(11, 'Burbuja', 'AGUA', NULL, NULL, NULL, 40, NULL, 'ATAQUE', 'Dispara burbujas al enemigo', 20),
(12, 'Hidrobomba', 'AGUA', NULL, NULL, NULL, 110, NULL, 'ATAQUE', 'Bombardeo masivo de agua', 55),
(13, 'Surf', 'AGUA', NULL, NULL, NULL, 90, NULL, 'ATAQUE', 'Gran ola que golpea al enemigo', 45),
(14, 'Impactrueno', 'ELECTRICO', NULL, NULL, NULL, 40, NULL, 'ATAQUE', 'Descarga eléctrica básica', 20),
(15, 'Rayo', 'ELECTRICO', NULL, NULL, NULL, 90, NULL, 'ATAQUE', 'Potente rayo eléctrico', 45),
(16, 'Trueno', 'ELECTRICO', NULL, NULL, NULL, 110, NULL, 'ATAQUE', 'Descarga eléctrica devastadora', 55),
(17, 'Látigo Cepa', 'PLANTA', NULL, NULL, NULL, 45, NULL, 'ATAQUE', 'Azota con lianas', 23),
(18, 'Hoja Afilada', 'PLANTA', NULL, NULL, NULL, 55, NULL, 'ATAQUE', 'Corta con hojas afiladas', 28),
(19, 'Rayo Solar', 'PLANTA', NULL, NULL, NULL, 120, NULL, 'ATAQUE', 'Absorbe luz solar y ataca', 60),
(20, 'Rayo Hielo', 'HIELO', NULL, NULL, NULL, 90, NULL, 'ATAQUE', 'Rayo de hielo', 45),
(21, 'Ventisca', 'HIELO', NULL, NULL, NULL, 110, NULL, 'ATAQUE', 'Tormenta de nieve', 55),
(22, 'Patada Baja', 'LUCHA', NULL, NULL, NULL, 50, NULL, 'ATAQUE', 'Patada a las piernas', 25),
(23, 'Puño Karate', 'LUCHA', NULL, NULL, NULL, 50, NULL, 'ATAQUE', 'Golpe de karate', 25),
(24, 'Picotazo Veneno', 'VENENO', NULL, NULL, NULL, 40, NULL, 'ATAQUE', 'Picotazo tóxico', 20),
(25, 'Bomba Lodo', 'VENENO', NULL, NULL, NULL, 90, NULL, 'ATAQUE', 'Lanza lodo venenoso', 45),
(26, 'Excavar', 'TIERRA', NULL, NULL, NULL, 80, NULL, 'ATAQUE', 'Cava y ataca desde el subsuelo', 40),
(27, 'Terremoto', 'TIERRA', NULL, NULL, NULL, 100, NULL, 'ATAQUE', 'Terremoto de gran magnitud', 50),
(28, 'Picotazo', 'VOLADOR', NULL, NULL, NULL, 35, NULL, 'ATAQUE', 'Picotazo con el pico', 18),
(29, 'Ataque Ala', 'VOLADOR', NULL, NULL, NULL, 60, NULL, 'ATAQUE', 'Golpea con las alas', 30),
(30, 'Confusión', 'PSIQUICO', NULL, NULL, NULL, 50, NULL, 'ATAQUE', 'Ataque mental confuso', 25),
(31, 'Psíquico', 'PSIQUICO', NULL, NULL, NULL, 90, NULL, 'ATAQUE', 'Potente ataque mental', 45),
(32, 'Picadura', 'BICHO', NULL, NULL, NULL, 60, NULL, 'ATAQUE', 'Muerde al enemigo', 30),
(33, 'Lanzarrocas', 'ROCA', NULL, NULL, NULL, 50, NULL, 'ATAQUE', 'Lanza rocas al enemigo', 25),
(34, 'Lametazo', 'FANTASMA', NULL, NULL, NULL, 30, NULL, 'ATAQUE', 'Lame al enemigo', 15),
(35, 'Paralizador', 'ELECTRICO', NULL, 'PARALIZADO', 3, NULL, NULL, 'ESTADO', 'Paraliza al enemigo', 30),
(36, 'Hipnosis', 'PSIQUICO', NULL, 'DORMIDO', 3, NULL, NULL, 'ESTADO', 'Duerme al enemigo', 30),
(37, 'Polvo Veneno', 'VENENO', NULL, 'ENVENENADO', 5, NULL, NULL, 'ESTADO', 'Envenena al enemigo', 50),
(38, 'Onda Trueno', 'ELECTRICO', NULL, 'PARALIZADO', 4, NULL, NULL, 'ESTADO', 'Paraliza con electricidad', 40),
(39, 'Fortaleza', 'NORMAL', 'DEFENSA', NULL, 3, NULL, NULL, 'MEJORA', 'Aumenta la defensa', 30),
(40, 'Agilidad', 'PSIQUICO', 'VELOCIDAD', NULL, 3, NULL, NULL, 'MEJORA', 'Aumenta la velocidad', 30),
(41, 'Meditación', 'PSIQUICO', 'ATAQUE', NULL, 3, NULL, NULL, 'MEJORA', 'Aumenta el ataque', 30),
(42, 'Amnesia', 'PSIQUICO', 'DEF_ESPECIAL', NULL, 2, NULL, NULL, 'MEJORA', 'Aumenta defensa especial', 20),
(43, 'Danza Espada', 'NORMAL', 'ATAQUE', NULL, 2, NULL, NULL, 'MEJORA', 'Aumenta mucho el ataque', 20);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `objeto`
--

CREATE TABLE `objeto` (
  `id_Objeto` int(11) NOT NULL,
  `nom_Objeto` varchar(50) NOT NULL,
  `bonus_Ataque` decimal(4,2) DEFAULT 1.00,
  `bonus_Defensa` decimal(4,2) DEFAULT 1.00,
  `penalizacion_Ataque` decimal(4,2) DEFAULT 1.00,
  `penalizacion_Defensa` decimal(4,2) DEFAULT 1.00,
  `bonus_Ataque_Especial` decimal(4,2) DEFAULT 1.00,
  `penalizacion_Ataque_Especial` decimal(4,2) DEFAULT 1.00,
  `bonus_Defensa_Especial` decimal(4,2) DEFAULT 1.00,
  `penalizacion_Defensa_Especial` decimal(4,2) DEFAULT 1.00,
  `bonus_Velocidad` decimal(4,2) DEFAULT 1.00,
  `penalizacion_Velocidad` decimal(4,2) DEFAULT 1.00
  

  
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `objeto`
--

INSERT INTO `objeto` (`id_Objeto`, `nom_Objeto`, `bonus_Ataque`, `bonus_Defensa`, `penalizacion_Ataque`, `penalizacion_Defensa`, `bonus_Ataque_Especial`, `penalizacion_Ataque_Especial`, `bonus_Defensa_Especial`, `penalizacion_Defensa_Especial`, `bonus_Velocidad`, `penalizacion_Velocidad`) VALUES
(1, 'Pesa', 1.20, 1.20, 0.80, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00),
(2, 'Pluma', 1.00, 0.80, 1.30, 1.00, 0.80, 1.00, 1.00, 1.00, 1.00, 1.00),
(3, 'Chaleco', 0.85, 1.20, 0.85, 1.00, 1.20, 1.00, 1.00, 1.00, 1.00, 1.00),
(4, 'Bastón', 1.00, 1.00, 0.85, 1.00, 1.00, 1.20, 1.00, 1.00, 1.00, 1.00),
(5, 'Pilas', 1.00, 1.00, 1.00, 1.00, 0.70, 1.50, 1.00, 1.00, 1.00, 1.00),
(6, 'Pesa', 1.20, 1.20, 0.80, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00),
(7, 'Pluma', 1.00, 0.80, 1.30, 1.00, 0.80, 1.00, 1.00, 1.00, 1.00, 1.00),
(8, 'Chaleco', 0.85, 1.20, 0.85, 1.00, 1.20, 1.00, 1.00, 1.00, 1.00, 1.00),
(9, 'Bastón', 1.00, 1.00, 0.85, 1.00, 1.00, 1.20, 1.00, 1.00, 1.00, 1.00),
(10, 'Pilas', 1.00, 1.00, 1.00, 1.00, 0.70, 1.50, 1.00, 1.00, 1.00, 1.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pokedex`
--

CREATE TABLE `pokedex` (
  `num_Pokedex` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `tipo1` varchar(20) NOT NULL,
  `tipo2` varchar(20) DEFAULT NULL,
  `img_Back` varchar(255) DEFAULT NULL,
  `sonido` varchar(255) DEFAULT NULL,
  `img_Frontal` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pokedex`
--
INSERT INTO `pokedex` (`num_Pokedex`, `nombre`, `tipo1`, `tipo2`, `img_Back`, `sonido`, `img_Frontal`) VALUES
(1, 'Bulbasaur', 'PLANTA', 'VENENO', NULL, NULL, '/images/spritesPokemons/Front/1.png'),
(2, 'Ivysaur', 'PLANTA', 'VENENO', NULL, NULL, '/images/spritesPokemons/Front/2.png'),
(3, 'Venusaur', 'PLANTA', 'VENENO', NULL, NULL, '/images/spritesPokemons/Front/3.png'),
(4, 'Charmander', 'FUEGO', NULL, NULL, NULL, '/images/spritesPokemons/Front/4.png'),
(5, 'Charmeleon', 'FUEGO', NULL, NULL, NULL, '/images/spritesPokemons/Front/5.png'),
(6, 'Charizard', 'FUEGO', 'VOLADOR', NULL, NULL, '/images/spritesPokemons/Front/6.png'),
(7, 'Squirtle', 'AGUA', NULL, NULL, NULL, '/images/spritesPokemons/Front/7.png'),
(8, 'Wartortle', 'AGUA', NULL, NULL, NULL, '/images/spritesPokemons/Front/8.png'),
(9, 'Blastoise', 'AGUA', NULL, NULL, NULL, '/images/spritesPokemons/Front/9.png'),
(10, 'Caterpie', 'BICHO', NULL, NULL, NULL, '/images/spritesPokemons/Front/10.png'),
(11, 'Metapod', 'BICHO', NULL, NULL, NULL, '/images/spritesPokemons/Front/11.png'),
(12, 'Butterfree', 'BICHO', 'VOLADOR', NULL, NULL, '/images/spritesPokemons/Front/12.png'),
(13, 'Weedle', 'BICHO', 'VENENO', NULL, NULL, '/images/spritesPokemons/Front/13.png'),
(14, 'Kakuna', 'BICHO', 'VENENO', NULL, NULL, '/images/spritesPokemons/Front/14.png'),
(15, 'Beedrill', 'BICHO', 'VENENO', NULL, NULL, '/images/spritesPokemons/Front/15.png'),
(16, 'Pidgey', 'NORMAL', 'VOLADOR', NULL, NULL, '/images/spritesPokemons/Front/16.png'),
(17, 'Pidgeotto', 'NORMAL', 'VOLADOR', NULL, NULL, '/images/spritesPokemons/Front/17.png'),
(18, 'Pidgeot', 'NORMAL', 'VOLADOR', NULL, NULL, '/images/spritesPokemons/Front/18.png'),
(19, 'Rattata', 'NORMAL', NULL, NULL, NULL, '/images/spritesPokemons/Front/19.png'),
(20, 'Raticate', 'NORMAL', NULL, NULL, NULL, '/images/spritesPokemons/Front/20.png'),
(21, 'Spearow', 'NORMAL', 'VOLADOR', NULL, NULL, '/images/spritesPokemons/Front/21.png'),
(22, 'Fearow', 'NORMAL', 'VOLADOR', NULL, NULL, '/images/spritesPokemons/Front/22.png'),
(23, 'Ekans', 'VENENO', NULL, NULL, NULL, '/images/spritesPokemons/Front/23.png'),
(24, 'Arbok', 'VENENO', NULL, NULL, NULL, '/images/spritesPokemons/Front/24.png'),
(25, 'Pikachu', 'ELECTRICO', NULL, NULL, NULL, '/images/spritesPokemons/Front/25.png'),
(26, 'Raichu', 'ELECTRICO', NULL, NULL, NULL, '/images/spritesPokemons/Front/26.png'),
(27, 'Sandshrew', 'TIERRA', NULL, NULL, NULL, '/images/spritesPokemons/Front/27.png'),
(28, 'Sandslash', 'TIERRA', NULL, NULL, NULL, '/images/spritesPokemons/Front/28.png'),
(29, 'Nidoran♀', 'VENENO', NULL, NULL, NULL, '/images/spritesPokemons/Front/29.png'),
(30, 'Nidorina', 'VENENO', NULL, NULL, NULL, '/images/spritesPokemons/Front/30.png'),
(31, 'Nidoqueen', 'VENENO', 'TIERRA', NULL, NULL, '/images/spritesPokemons/Front/31.png'),
(32, 'Nidoran♂', 'VENENO', NULL, NULL, NULL, '/images/spritesPokemons/Front/32.png'),
(33, 'Nidorino', 'VENENO', NULL, NULL, NULL, '/images/spritesPokemons/Front/33.png'),
(34, 'Nidoking', 'VENENO', 'TIERRA', NULL, NULL, '/images/spritesPokemons/Front/34.png'),
(35, 'Clefairy', 'NORMAL', NULL, NULL, NULL, '/images/spritesPokemons/Front/35.png'),
(36, 'Clefable', 'NORMAL', NULL, NULL, NULL, '/images/spritesPokemons/Front/36.png'),
(37, 'Vulpix', 'FUEGO', NULL, NULL, NULL, '/images/spritesPokemons/Front/37.png'),
(38, 'Ninetales', 'FUEGO', NULL, NULL, NULL, '/images/spritesPokemons/Front/38.png'),
(39, 'Jigglypuff', 'NORMAL', NULL, NULL, NULL, '/images/spritesPokemons/Front/39.png'),
(40, 'Wigglytuff', 'NORMAL', NULL, NULL, NULL, '/images/spritesPokemons/Front/40.png'),
(41, 'Zubat', 'VENENO', 'VOLADOR', NULL, NULL, '/images/spritesPokemons/Front/41.png'),
(42, 'Golbat', 'VENENO', 'VOLADOR', NULL, NULL, '/images/spritesPokemons/Front/42.png'),
(43, 'Oddish', 'PLANTA', 'VENENO', NULL, NULL, '/images/spritesPokemons/Front/43.png'),
(44, 'Gloom', 'PLANTA', 'VENENO', NULL, NULL, '/images/spritesPokemons/Front/44.png'),
(45, 'Vileplume', 'PLANTA', 'VENENO', NULL, NULL, '/images/spritesPokemons/Front/45.png'),
(46, 'Paras', 'BICHO', 'PLANTA', NULL, NULL, '/images/spritesPokemons/Front/46.png'),
(47, 'Parasect', 'BICHO', 'PLANTA', NULL, NULL, '/images/spritesPokemons/Front/47.png'),
(48, 'Venonat', 'BICHO', 'VENENO', NULL, NULL, '/images/spritesPokemons/Front/48.png'),
(49, 'Venomoth', 'BICHO', 'VENENO', NULL, NULL, '/images/spritesPokemons/Front/49.png'),
(50, 'Diglett', 'TIERRA', NULL, NULL, NULL, '/images/spritesPokemons/Front/50.png'),
(51, 'Dugtrio', 'TIERRA', NULL, NULL, NULL, '/images/spritesPokemons/Front/51.png'),
(52, 'Meowth', 'NORMAL', NULL, NULL, NULL, '/images/spritesPokemons/Front/52.png'),
(53, 'Persian', 'NORMAL', NULL, NULL, NULL, '/images/spritesPokemons/Front/53.png'),
(54, 'Psyduck', 'AGUA', NULL, NULL, NULL, '/images/spritesPokemons/Front/54.png'),
(55, 'Golduck', 'AGUA', NULL, NULL, NULL, '/images/spritesPokemons/Front/55.png'),
(56, 'Mankey', 'LUCHA', NULL, NULL, NULL, '/images/spritesPokemons/Front/56.png'),
(57, 'Primeape', 'LUCHA', NULL, NULL, NULL, '/images/spritesPokemons/Front/57.png'),
(58, 'Growlithe', 'FUEGO', NULL, NULL, NULL, '/images/spritesPokemons/Front/58.png'),
(59, 'Arcanine', 'FUEGO', NULL, NULL, NULL, '/images/spritesPokemons/Front/59.png'),
(60, 'Poliwag', 'AGUA', NULL, NULL, NULL, '/images/spritesPokemons/Front/60.png'),
(61, 'Poliwhirl', 'AGUA', NULL, NULL, NULL, '/images/spritesPokemons/Front/61.png'),
(62, 'Poliwrath', 'AGUA', 'LUCHA', NULL, NULL, '/images/spritesPokemons/Front/62.png'),
(63, 'Abra', 'PSIQUICO', NULL, NULL, NULL, '/images/spritesPokemons/Front/63.png'),
(64, 'Kadabra', 'PSIQUICO', NULL, NULL, NULL, '/images/spritesPokemons/Front/64.png'),
(65, 'Alakazam', 'PSIQUICO', NULL, NULL, NULL, '/images/spritesPokemons/Front/65.png'),
(66, 'Machop', 'LUCHA', NULL, NULL, NULL, '/images/spritesPokemons/Front/66.png'),
(67, 'Machoke', 'LUCHA', NULL, NULL, NULL, '/images/spritesPokemons/Front/67.png'),
(68, 'Machamp', 'LUCHA', NULL, NULL, NULL, '/images/spritesPokemons/Front/68.png'),
(69, 'Bellsprout', 'PLANTA', 'VENENO', NULL, NULL, '/images/spritesPokemons/Front/69.png'),
(70, 'Weepinbell', 'PLANTA', 'VENENO', NULL, NULL, '/images/spritesPokemons/Front/70.png'),
(71, 'Victreebel', 'PLANTA', 'VENENO', NULL, NULL, '/images/spritesPokemons/Front/71.png'),
(72, 'Tentacool', 'AGUA', 'VENENO', NULL, NULL, '/images/spritesPokemons/Front/72.png'),
(73, 'Tentacruel', 'AGUA', 'VENENO', NULL, NULL, '/images/spritesPokemons/Front/73.png'),
(74, 'Geodude', 'ROCA', 'TIERRA', NULL, NULL, '/images/spritesPokemons/Front/74.png'),
(75, 'Graveler', 'ROCA', 'TIERRA', NULL, NULL, '/images/spritesPokemons/Front/75.png'),
(76, 'Golem', 'ROCA', 'TIERRA', NULL, NULL, '/images/spritesPokemons/Front/76.png'),
(77, 'Ponyta', 'FUEGO', NULL, NULL, NULL, '/images/spritesPokemons/Front/77.png'),
(78, 'Rapidash', 'FUEGO', NULL, NULL, NULL, '/images/spritesPokemons/Front/78.png'),
(79, 'Slowpoke', 'AGUA', 'PSIQUICO', NULL, NULL, '/images/spritesPokemons/Front/79.png'),
(80, 'Slowbro', 'AGUA', 'PSIQUICO', NULL, NULL, '/images/spritesPokemons/Front/80.png'),
(81, 'Magnemite', 'ELECTRICO', 'ACERO', NULL, NULL, '/images/spritesPokemons/Front/81.png'),
(82, 'Magneton', 'ELECTRICO', 'ACERO', NULL, NULL, '/images/spritesPokemons/Front/82.png'),
(83, 'Farfetchd', 'NORMAL', 'VOLADOR', NULL, NULL, '/images/spritesPokemons/Front/83.png'),
(84, 'Doduo', 'NORMAL', 'VOLADOR', NULL, NULL, '/images/spritesPokemons/Front/84.png'),
(85, 'Dodrio', 'NORMAL', 'VOLADOR', NULL, NULL, '/images/spritesPokemons/Front/85.png'),
(86, 'Seel', 'AGUA', NULL, NULL, NULL, '/images/spritesPokemons/Front/86.png'),
(87, 'Dewgong', 'AGUA', 'HIELO', NULL, NULL, '/images/spritesPokemons/Front/87.png'),
(88, 'Grimer', 'VENENO', NULL, NULL, NULL, '/images/spritesPokemons/Front/88.png'),
(89, 'Muk', 'VENENO', NULL, NULL, NULL, '/images/spritesPokemons/Front/89.png'),
(90, 'Shellder', 'AGUA', NULL, NULL, NULL, '/images/spritesPokemons/Front/90.png'),
(91, 'Cloyster', 'AGUA', 'HIELO', NULL, NULL, '/images/spritesPokemons/Front/91.png'),
(92, 'Gastly', 'FANTASMA', 'VENENO', NULL, NULL, '/images/spritesPokemons/Front/92.png'),
(93, 'Haunter', 'FANTASMA', 'VENENO', NULL, NULL, '/images/spritesPokemons/Front/93.png'),
(94, 'Gengar', 'FANTASMA', 'VENENO', NULL, NULL, '/images/spritesPokemons/Front/94.png'),
(95, 'Onix', 'ROCA', 'TIERRA', NULL, NULL, '/images/spritesPokemons/Front/95.png'),
(96, 'Drowzee', 'PSIQUICO', NULL, NULL, NULL, '/images/spritesPokemons/Front/96.png'),
(97, 'Hypno', 'PSIQUICO', NULL, NULL, NULL, '/images/spritesPokemons/Front/97.png'),
(98, 'Krabby', 'AGUA', NULL, NULL, NULL, '/images/spritesPokemons/Front/98.png'),
(99, 'Kingler', 'AGUA', NULL, NULL, NULL, '/images/spritesPokemons/Front/99.png'),
(100, 'Voltorb', 'ELECTRICO', NULL, NULL, NULL, '/images/spritesPokemons/Front/100.png'),
(101, 'Electrode', 'ELECTRICO', NULL, NULL, NULL, '/images/spritesPokemons/Front/101.png'),
(102, 'Exeggcute', 'PLANTA', 'PSIQUICO', NULL, NULL, '/images/spritesPokemons/Front/102.png'),
(103, 'Exeggutor', 'PLANTA', 'PSIQUICO', NULL, NULL, '/images/spritesPokemons/Front/103.png'),
(104, 'Cubone', 'TIERRA', NULL, NULL, NULL, '/images/spritesPokemons/Front/104.png'),
(105, 'Marowak', 'TIERRA', NULL, NULL, NULL, '/images/spritesPokemons/Front/105.png'),
(106, 'Hitmonlee', 'LUCHA', NULL, NULL, NULL, '/images/spritesPokemons/Front/106.png'),
(107, 'Hitmonchan', 'LUCHA', NULL, NULL, NULL, '/images/spritesPokemons/Front/107.png'),
(108, 'Lickitung', 'NORMAL', NULL, NULL, NULL, '/images/spritesPokemons/Front/108.png'),
(109, 'Koffing', 'VENENO', NULL, NULL, NULL, '/images/spritesPokemons/Front/109.png'),
(110, 'Weezing', 'VENENO', NULL, NULL, NULL, '/images/spritesPokemons/Front/110.png'),
(111, 'Rhyhorn', 'TIERRA', 'ROCA', NULL, NULL, '/images/spritesPokemons/Front/111.png'),
(112, 'Rhydon', 'TIERRA', 'ROCA', NULL, NULL, '/images/spritesPokemons/Front/112.png'),
(113, 'Chansey', 'NORMAL', NULL, NULL, NULL, '/images/spritesPokemons/Front/113.png'),
(114, 'Tangela', 'PLANTA', NULL, NULL, NULL, '/images/spritesPokemons/Front/114.png'),
(115, 'Kangaskhan', 'NORMAL', NULL, NULL, NULL, '/images/spritesPokemons/Front/115.png'),
(116, 'Horsea', 'AGUA', NULL, NULL, NULL, '/images/spritesPokemons/Front/116.png'),
(117, 'Seadra', 'AGUA', NULL, NULL, NULL, '/images/spritesPokemons/Front/117.png'),
(118, 'Goldeen', 'AGUA', NULL, NULL, NULL, '/images/spritesPokemons/Front/118.png'),
(119, 'Seaking', 'AGUA', NULL, NULL, NULL, '/images/spritesPokemons/Front/119.png'),
(120, 'Staryu', 'AGUA', NULL, NULL, NULL, '/images/spritesPokemons/Front/120.png'),
(121, 'Starmie', 'AGUA', 'PSIQUICO', NULL, NULL, '/images/spritesPokemons/Front/121.png'),
(122, 'Mr. Mime', 'PSIQUICO', NULL, NULL, NULL, '/images/spritesPokemons/Front/122.png'),
(123, 'Scyther', 'BICHO', 'VOLADOR', NULL, NULL, '/images/spritesPokemons/Front/123.png'),
(124, 'Jynx', 'HIELO', 'PSIQUICO', NULL, NULL, '/images/spritesPokemons/Front/124.png'),
(125, 'Electabuzz', 'ELECTRICO', NULL, NULL, NULL, '/images/spritesPokemons/Front/125.png'),
(126, 'Magmar', 'FUEGO', NULL, NULL, NULL, '/images/spritesPokemons/Front/126.png'),
(127, 'Pinsir', 'BICHO', NULL, NULL, NULL, '/images/spritesPokemons/Front/127.png'),
(128, 'Tauros', 'NORMAL', NULL, NULL, NULL, '/images/spritesPokemons/Front/128.png'),
(129, 'Magikarp', 'AGUA', NULL, NULL, NULL, '/images/spritesPokemons/Front/129.png'),
(130, 'Gyarados', 'AGUA', 'VOLADOR', NULL, NULL, '/images/spritesPokemons/Front/130.png'),
(131, 'Lapras', 'AGUA', 'HIELO', NULL, NULL, '/images/spritesPokemons/Front/131.png'),
(132, 'Ditto', 'NORMAL', NULL, NULL, NULL, '/images/spritesPokemons/Front/132.png'),
(133, 'Eevee', 'NORMAL', NULL, NULL, NULL, '/images/spritesPokemons/Front/133.png'),
(134, 'Vaporeon', 'AGUA', NULL, NULL, NULL, '/images/spritesPokemons/Front/134.png'),
(135, 'Jolteon', 'ELECTRICO', NULL, NULL, NULL, '/images/spritesPokemons/Front/135.png'),
(136, 'Flareon', 'FUEGO', NULL, NULL, NULL, '/images/spritesPokemons/Front/136.png'),
(137, 'Porygon', 'NORMAL', NULL, NULL, NULL, '/images/spritesPokemons/Front/137.png'),
(138, 'Omanyte', 'ROCA', 'AGUA', NULL, NULL, '/images/spritesPokemons/Front/138.png'),
(139, 'Omastar', 'ROCA', 'AGUA', NULL, NULL, '/images/spritesPokemons/Front/139.png'),
(140, 'Kabuto', 'ROCA', 'AGUA', NULL, NULL, '/images/spritesPokemons/Front/140.png'),
(141, 'Kabutops', 'ROCA', 'AGUA', NULL, NULL, '/images/spritesPokemons/Front/141.png'),
(142, 'Aerodactyl', 'ROCA', 'VOLADOR', NULL, NULL, '/images/spritesPokemons/Front/142.png'),
(143, 'Snorlax', 'NORMAL', NULL, NULL, NULL, '/images/spritesPokemons/Front/143.png'),
(144, 'Articuno', 'HIELO', 'VOLADOR', NULL, NULL, '/images/spritesPokemons/Front/144.png'),
(145, 'Zapdos', 'ELECTRICO', 'VOLADOR', NULL, NULL, '/images/spritesPokemons/Front/145.png'),
(146, 'Moltres', 'FUEGO', 'VOLADOR', NULL, NULL, '/images/spritesPokemons/Front/146.png'),
(147, 'Dratini', 'DRAGON', NULL, NULL, NULL, '/images/spritesPokemons/Front/147.png'),
(148, 'Dragonair', 'DRAGON', NULL, NULL, NULL, '/images/spritesPokemons/Front/148.png'),
(149, 'Dragonite', 'DRAGON', 'VOLADOR', NULL, NULL, '/images/spritesPokemons/Front/149.png'),
(150, 'Mewtwo', 'PSIQUICO', NULL, NULL, NULL, '/images/spritesPokemons/Front/150.png'),
(151, 'Mew', 'PSIQUICO', NULL, NULL, NULL, '/images/spritesPokemons/Front/151.png');
-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pokemon`
--

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
  MODIFY `id_Movimiento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- AUTO_INCREMENT de la tabla `objeto`
--
ALTER TABLE `objeto`
  MODIFY `id_Objeto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

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
