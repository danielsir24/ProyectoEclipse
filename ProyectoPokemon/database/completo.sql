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
  `ataque` decimal(4,2) DEFAULT 1.00,
  `defensa` decimal(4,2) DEFAULT 1.00,
  `velocidad` decimal(4,2) DEFAULT 1.00,
  `ataq_Especial` decimal(4,2) DEFAULT 1.00,
  `defEspecial` decimal(4,2) DEFAULT 1.00,
  `estamina` decimal(4,2) DEFAULT 1.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `objeto`
--

INSERT INTO `objeto` (`id_Objeto`, `nom_Objeto`, `ataque`, `defensa`, `velocidad`, `ataq_Especial`, `defEspecial`, `estamina`) VALUES
(1, 'Pesa', 1.20, 1.20, 0.80, 1.00, 1.00, 1.00),
(2, 'Pluma', 1.00, 0.80, 1.30, 1.00, 0.80, 1.00),
(3, 'Chaleco', 0.85, 1.20, 0.85, 1.00, 1.20, 1.00),
(4, 'Bastón', 1.00, 1.00, 0.85, 1.00, 1.00, 1.20),
(5, 'Pilas', 1.00, 1.00, 1.00, 1.00, 0.70, 1.50),
(6, 'Pesa', 1.20, 1.20, 0.80, 1.00, 1.00, 1.00),
(7, 'Pluma', 1.00, 0.80, 1.30, 1.00, 0.80, 1.00),
(8, 'Chaleco', 0.85, 1.20, 0.85, 1.00, 1.20, 1.00),
(9, 'Bastón', 1.00, 1.00, 0.85, 1.00, 1.00, 1.20),
(10, 'Pilas', 1.00, 1.00, 1.00, 1.00, 0.70, 1.50);

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
(1, 'Bulbasaur', 'PLANTA', 'VENENO', NULL, NULL, NULL),
(2, 'Ivysaur', 'PLANTA', 'VENENO', NULL, NULL, NULL),
(3, 'Venusaur', 'PLANTA', 'VENENO', NULL, NULL, NULL),
(4, 'Charmander', 'FUEGO', NULL, NULL, NULL, NULL),
(5, 'Charmeleon', 'FUEGO', NULL, NULL, NULL, NULL),
(6, 'Charizard', 'FUEGO', 'VOLADOR', NULL, NULL, NULL),
(7, 'Squirtle', 'AGUA', NULL, NULL, NULL, NULL),
(8, 'Wartortle', 'AGUA', NULL, NULL, NULL, NULL),
(9, 'Blastoise', 'AGUA', NULL, NULL, NULL, NULL),
(10, 'Caterpie', 'BICHO', NULL, NULL, NULL, NULL),
(11, 'Metapod', 'BICHO', NULL, NULL, NULL, NULL),
(12, 'Butterfree', 'BICHO', 'VOLADOR', NULL, NULL, NULL),
(13, 'Weedle', 'BICHO', 'VENENO', NULL, NULL, NULL),
(14, 'Kakuna', 'BICHO', 'VENENO', NULL, NULL, NULL),
(15, 'Beedrill', 'BICHO', 'VENENO', NULL, NULL, NULL),
(16, 'Pidgey', 'NORMAL', 'VOLADOR', NULL, NULL, NULL),
(17, 'Pidgeotto', 'NORMAL', 'VOLADOR', NULL, NULL, NULL),
(18, 'Pidgeot', 'NORMAL', 'VOLADOR', NULL, NULL, NULL),
(19, 'Rattata', 'NORMAL', NULL, NULL, NULL, NULL),
(20, 'Raticate', 'NORMAL', NULL, NULL, NULL, NULL),
(21, 'Spearow', 'NORMAL', 'VOLADOR', NULL, NULL, NULL),
(22, 'Fearow', 'NORMAL', 'VOLADOR', NULL, NULL, NULL),
(23, 'Ekans', 'VENENO', NULL, NULL, NULL, NULL),
(24, 'Arbok', 'VENENO', NULL, NULL, NULL, NULL),
(25, 'Pikachu', 'ELECTRICO', NULL, NULL, NULL, NULL),
(26, 'Raichu', 'ELECTRICO', NULL, NULL, NULL, NULL),
(27, 'Sandshrew', 'TIERRA', NULL, NULL, NULL, NULL),
(28, 'Sandslash', 'TIERRA', NULL, NULL, NULL, NULL),
(29, 'Nidoran♀', 'VENENO', NULL, NULL, NULL, NULL),
(30, 'Nidorina', 'VENENO', NULL, NULL, NULL, NULL),
(31, 'Nidoqueen', 'VENENO', 'TIERRA', NULL, NULL, NULL),
(32, 'Nidoran♂', 'VENENO', NULL, NULL, NULL, NULL),
(33, 'Nidorino', 'VENENO', NULL, NULL, NULL, NULL),
(34, 'Nidoking', 'VENENO', 'TIERRA', NULL, NULL, NULL),
(35, 'Clefairy', 'NORMAL', NULL, NULL, NULL, NULL),
(36, 'Clefable', 'NORMAL', NULL, NULL, NULL, NULL),
(37, 'Vulpix', 'FUEGO', NULL, NULL, NULL, NULL),
(38, 'Ninetales', 'FUEGO', NULL, NULL, NULL, NULL),
(39, 'Jigglypuff', 'NORMAL', NULL, NULL, NULL, NULL),
(40, 'Wigglytuff', 'NORMAL', NULL, NULL, NULL, NULL),
(41, 'Zubat', 'VENENO', 'VOLADOR', NULL, NULL, NULL),
(42, 'Golbat', 'VENENO', 'VOLADOR', NULL, NULL, NULL),
(43, 'Oddish', 'PLANTA', 'VENENO', NULL, NULL, NULL),
(44, 'Gloom', 'PLANTA', 'VENENO', NULL, NULL, NULL),
(45, 'Vileplume', 'PLANTA', 'VENENO', NULL, NULL, NULL),
(46, 'Paras', 'BICHO', 'PLANTA', NULL, NULL, NULL),
(47, 'Parasect', 'BICHO', 'PLANTA', NULL, NULL, NULL),
(48, 'Venonat', 'BICHO', 'VENENO', NULL, NULL, NULL),
(49, 'Venomoth', 'BICHO', 'VENENO', NULL, NULL, NULL),
(50, 'Diglett', 'TIERRA', NULL, NULL, NULL, NULL),
(51, 'Dugtrio', 'TIERRA', NULL, NULL, NULL, NULL),
(52, 'Meowth', 'NORMAL', NULL, NULL, NULL, NULL),
(53, 'Persian', 'NORMAL', NULL, NULL, NULL, NULL),
(54, 'Psyduck', 'AGUA', NULL, NULL, NULL, NULL),
(55, 'Golduck', 'AGUA', NULL, NULL, NULL, NULL),
(56, 'Mankey', 'LUCHA', NULL, NULL, NULL, NULL),
(57, 'Primeape', 'LUCHA', NULL, NULL, NULL, NULL),
(58, 'Growlithe', 'FUEGO', NULL, NULL, NULL, NULL),
(59, 'Arcanine', 'FUEGO', NULL, NULL, NULL, NULL),
(60, 'Poliwag', 'AGUA', NULL, NULL, NULL, NULL),
(61, 'Poliwhirl', 'AGUA', NULL, NULL, NULL, NULL),
(62, 'Poliwrath', 'AGUA', 'LUCHA', NULL, NULL, NULL),
(63, 'Abra', 'PSIQUICO', NULL, NULL, NULL, NULL),
(64, 'Kadabra', 'PSIQUICO', NULL, NULL, NULL, NULL),
(65, 'Alakazam', 'PSIQUICO', NULL, NULL, NULL, NULL),
(66, 'Machop', 'LUCHA', NULL, NULL, NULL, NULL),
(67, 'Machoke', 'LUCHA', NULL, NULL, NULL, NULL),
(68, 'Machamp', 'LUCHA', NULL, NULL, NULL, NULL),
(69, 'Bellsprout', 'PLANTA', 'VENENO', NULL, NULL, NULL),
(70, 'Weepinbell', 'PLANTA', 'VENENO', NULL, NULL, NULL),
(71, 'Victreebel', 'PLANTA', 'VENENO', NULL, NULL, NULL),
(72, 'Tentacool', 'AGUA', 'VENENO', NULL, NULL, NULL),
(73, 'Tentacruel', 'AGUA', 'VENENO', NULL, NULL, NULL),
(74, 'Geodude', 'ROCA', 'TIERRA', NULL, NULL, NULL),
(75, 'Graveler', 'ROCA', 'TIERRA', NULL, NULL, NULL),
(76, 'Golem', 'ROCA', 'TIERRA', NULL, NULL, NULL),
(77, 'Ponyta', 'FUEGO', NULL, NULL, NULL, NULL),
(78, 'Rapidash', 'FUEGO', NULL, NULL, NULL, NULL),
(79, 'Slowpoke', 'AGUA', 'PSIQUICO', NULL, NULL, NULL),
(80, 'Slowbro', 'AGUA', 'PSIQUICO', NULL, NULL, NULL),
(81, 'Magnemite', 'ELECTRICO', 'ACERO', NULL, NULL, NULL),
(82, 'Magneton', 'ELECTRICO', 'ACERO', NULL, NULL, NULL),
(83, 'Farfetchd', 'NORMAL', 'VOLADOR', NULL, NULL, NULL),
(84, 'Doduo', 'NORMAL', 'VOLADOR', NULL, NULL, NULL),
(85, 'Dodrio', 'NORMAL', 'VOLADOR', NULL, NULL, NULL),
(86, 'Seel', 'AGUA', NULL, NULL, NULL, NULL),
(87, 'Dewgong', 'AGUA', 'HIELO', NULL, NULL, NULL),
(88, 'Grimer', 'VENENO', NULL, NULL, NULL, NULL),
(89, 'Muk', 'VENENO', NULL, NULL, NULL, NULL),
(90, 'Shellder', 'AGUA', NULL, NULL, NULL, NULL),
(91, 'Cloyster', 'AGUA', 'HIELO', NULL, NULL, NULL),
(92, 'Gastly', 'FANTASMA', 'VENENO', NULL, NULL, NULL),
(93, 'Haunter', 'FANTASMA', 'VENENO', NULL, NULL, NULL),
(94, 'Gengar', 'FANTASMA', 'VENENO', NULL, NULL, NULL),
(95, 'Onix', 'ROCA', 'TIERRA', NULL, NULL, NULL),
(96, 'Drowzee', 'PSIQUICO', NULL, NULL, NULL, NULL),
(97, 'Hypno', 'PSIQUICO', NULL, NULL, NULL, NULL),
(98, 'Krabby', 'AGUA', NULL, NULL, NULL, NULL),
(99, 'Kingler', 'AGUA', NULL, NULL, NULL, NULL),
(100, 'Voltorb', 'ELECTRICO', NULL, NULL, NULL, NULL),
(101, 'Electrode', 'ELECTRICO', NULL, NULL, NULL, NULL),
(102, 'Exeggcute', 'PLANTA', 'PSIQUICO', NULL, NULL, NULL),
(103, 'Exeggutor', 'PLANTA', 'PSIQUICO', NULL, NULL, NULL),
(104, 'Cubone', 'TIERRA', NULL, NULL, NULL, NULL),
(105, 'Marowak', 'TIERRA', NULL, NULL, NULL, NULL),
(106, 'Hitmonlee', 'LUCHA', NULL, NULL, NULL, NULL),
(107, 'Hitmonchan', 'LUCHA', NULL, NULL, NULL, NULL),
(108, 'Lickitung', 'NORMAL', NULL, NULL, NULL, NULL),
(109, 'Koffing', 'VENENO', NULL, NULL, NULL, NULL),
(110, 'Weezing', 'VENENO', NULL, NULL, NULL, NULL),
(111, 'Rhyhorn', 'TIERRA', 'ROCA', NULL, NULL, NULL),
(112, 'Rhydon', 'TIERRA', 'ROCA', NULL, NULL, NULL),
(113, 'Chansey', 'NORMAL', NULL, NULL, NULL, NULL),
(114, 'Tangela', 'PLANTA', NULL, NULL, NULL, NULL),
(115, 'Kangaskhan', 'NORMAL', NULL, NULL, NULL, NULL),
(116, 'Horsea', 'AGUA', NULL, NULL, NULL, NULL),
(117, 'Seadra', 'AGUA', NULL, NULL, NULL, NULL),
(118, 'Goldeen', 'AGUA', NULL, NULL, NULL, NULL),
(119, 'Seaking', 'AGUA', NULL, NULL, NULL, NULL),
(120, 'Staryu', 'AGUA', NULL, NULL, NULL, NULL),
(121, 'Starmie', 'AGUA', 'PSIQUICO', NULL, NULL, NULL),
(122, 'Mr. Mime', 'PSIQUICO', NULL, NULL, NULL, NULL),
(123, 'Scyther', 'BICHO', 'VOLADOR', NULL, NULL, NULL),
(124, 'Jynx', 'HIELO', 'PSIQUICO', NULL, NULL, NULL),
(125, 'Electabuzz', 'ELECTRICO', NULL, NULL, NULL, NULL),
(126, 'Magmar', 'FUEGO', NULL, NULL, NULL, NULL),
(127, 'Pinsir', 'BICHO', NULL, NULL, NULL, NULL),
(128, 'Tauros', 'NORMAL', NULL, NULL, NULL, NULL),
(129, 'Magikarp', 'AGUA', NULL, NULL, NULL, NULL),
(130, 'Gyarados', 'AGUA', 'VOLADOR', NULL, NULL, NULL),
(131, 'Lapras', 'AGUA', 'HIELO', NULL, NULL, NULL),
(132, 'Ditto', 'NORMAL', NULL, NULL, NULL, NULL),
(133, 'Eevee', 'NORMAL', NULL, NULL, NULL, NULL),
(134, 'Vaporeon', 'AGUA', NULL, NULL, NULL, NULL),
(135, 'Jolteon', 'ELECTRICO', NULL, NULL, NULL, NULL),
(136, 'Flareon', 'FUEGO', NULL, NULL, NULL, NULL),
(137, 'Porygon', 'NORMAL', NULL, NULL, NULL, NULL),
(138, 'Omanyte', 'ROCA', 'AGUA', NULL, NULL, NULL),
(139, 'Omastar', 'ROCA', 'AGUA', NULL, NULL, NULL),
(140, 'Kabuto', 'ROCA', 'AGUA', NULL, NULL, NULL),
(141, 'Kabutops', 'ROCA', 'AGUA', NULL, NULL, NULL),
(142, 'Aerodactyl', 'ROCA', 'VOLADOR', NULL, NULL, NULL),
(143, 'Snorlax', 'NORMAL', NULL, NULL, NULL, NULL),
(144, 'Articuno', 'HIELO', 'VOLADOR', NULL, NULL, NULL),
(145, 'Zapdos', 'ELECTRICO', 'VOLADOR', NULL, NULL, NULL),
(146, 'Moltres', 'FUEGO', 'VOLADOR', NULL, NULL, NULL),
(147, 'Dratini', 'DRAGON', NULL, NULL, NULL, NULL),
(148, 'Dragonair', 'DRAGON', NULL, NULL, NULL, NULL),
(149, 'Dragonite', 'DRAGON', 'VOLADOR', NULL, NULL, NULL),
(150, 'Mewtwo', 'PSIQUICO', NULL, NULL, NULL, NULL),
(151, 'Mew', 'PSIQUICO', NULL, NULL, NULL, NULL);

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
