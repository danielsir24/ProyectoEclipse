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
  `pokedollars` int(11) DEFAULT 1000,
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
(1, 'Destructor', 'NORMAL', NULL, NULL, NULL, 40, 35, 'ATAQUE', 'Golpea con extremidades largas', 20),
(2, 'Golpe Karate', 'LUCHA', NULL, NULL, NULL, 50, 25, 'ATAQUE', 'Ataque con alta probabilidad de crítico', 25),
(3, 'Doblebofetón', 'NORMAL', NULL, NULL, NULL, 15, 10, 'ATAQUE', 'Bofetadas que golpean de 2 a 5 veces', 15),
(4, 'Puño Cometa', 'NORMAL', NULL, NULL, NULL, 18, 15, 'ATAQUE', 'Puñetazos que golpean de 2 a 5 veces', 18),
(5, 'Megapuño', 'NORMAL', NULL, NULL, NULL, 80, 20, 'ATAQUE', 'Un puñetazo de gran potencia', 40),
(6, 'Día de Pago', 'NORMAL', NULL, NULL, NULL, 40, 20, 'ATAQUE', 'Lanza monedas que luego se recuperan', 20),
(7, 'Puño Fuego', 'FUEGO', NULL, NULL, NULL, 75, 15, 'ATAQUE', 'Puñetazo ardiente con posibilidad de quemar', 35),
(8, 'Puño Hielo', 'HIELO', NULL, NULL, NULL, 75, 15, 'ATAQUE', 'Puñetazo helado con posibilidad de congelar', 35),
(9, 'Puño Trueno', 'ELECTRICO', NULL, NULL, NULL, 75, 15, 'ATAQUE', 'Puñetazo eléctrico con posibilidad de paralizar', 35),
(10, 'Arañazo', 'NORMAL', NULL, NULL, NULL, 40, 35, 'ATAQUE', 'Araña con afiladas garras', 20),
(11, 'Agarre', 'NORMAL', NULL, NULL, NULL, 55, 30, 'ATAQUE', 'Pinzas que atrapan al enemigo', 25),
(12, 'Guillotina', 'NORMAL', NULL, NULL, NULL, 190, 5, 'ATAQUE', 'Ataque fulminante de un solo golpe', 95),
(13, 'Viento Cortante', 'NORMAL', NULL, NULL, NULL, 80, 10, 'ATAQUE', 'Viento que corta en el segundo turno', 40),
(14, 'Danza Espada', 'NORMAL', 'ATAQUE', NULL, 2, 0, 30, 'MEJORA', 'Aumenta mucho el ataque propio', 20),
(15, 'Corte', 'NORMAL', NULL, NULL, NULL, 50, 30, 'ATAQUE', 'Cuchillada básica', 25),
(16, 'Tornado', 'VOLADOR', NULL, NULL, NULL, 40, 35, 'ATAQUE', 'Crea un fuerte viento', 20),
(17, 'Ataque Ala', 'VOLADOR', NULL, NULL, NULL, 60, 35, 'ATAQUE', 'Golpea con las alas', 30),
(18, 'Remolino', 'NORMAL', NULL, NULL, NULL, 0, 20, 'ESTADO', 'Expulsa al enemigo del combate', 15),
(19, 'Vuelo', 'VOLADOR', NULL, NULL, NULL, 90, 15, 'ATAQUE', 'Vuela y ataca al siguiente turno', 45),
(20, 'Atadura', 'NORMAL', NULL, NULL, 3, 15, 20, 'ATAQUE', 'Atrapa y daña varios turnos', 15),
(21, 'Portazo', 'NORMAL', NULL, NULL, NULL, 80, 20, 'ATAQUE', 'Golpea con una extremidad larga', 40),
(22, 'Látigo Cepa', 'PLANTA', NULL, NULL, NULL, 45, 25, 'ATAQUE', 'Azota con finas lianas', 22),
(23, 'Pisotón', 'NORMAL', NULL, NULL, NULL, 65, 20, 'ATAQUE', 'Aplasta con el pie', 30),
(24, 'Doble Patada', 'LUCHA', NULL, NULL, NULL, 30, 30, 'ATAQUE', 'Dos patadas seguidas', 30),
(25, 'Megapatada', 'NORMAL', NULL, NULL, NULL, 120, 5, 'ATAQUE', 'Patada de fuerza extrema', 60),
(26, 'Patada Salto', 'LUCHA', NULL, NULL, NULL, 70, 25, 'ATAQUE', 'Si falla, el usuario se daña', 35),
(27, 'Patada Salto Alta', 'LUCHA', NULL, NULL, NULL, 85, 20, 'ATAQUE', 'Patada muy arriesgada', 40),
(28, 'Ataque Arena', 'TIERRA', 'PRECISION', NULL, 3, 0, 15, 'ESTADO', 'Lanza arena para bajar precisión', 15),
(29, 'Golpe Cabeza', 'NORMAL', NULL, NULL, NULL, 70, 15, 'ATAQUE', 'Cabezazo que puede hacer retroceder', 35),
(30, 'Cornada', 'NORMAL', NULL, NULL, NULL, 65, 25, 'ATAQUE', 'Ataca con los cuernos', 32),
(31, 'Ataque Furia', 'NORMAL', NULL, NULL, NULL, 15, 20, 'ATAQUE', 'Cornadas múltiples', 20),
(32, 'Perforador', 'NORMAL', NULL, NULL, NULL, 190, 5, 'ATAQUE', 'Taladro fulminante', 95),
(33, 'Placaje', 'NORMAL', NULL, NULL, NULL, 40, 35, 'ATAQUE', 'Carga con todo el cuerpo', 20),
(34, 'Golpe Cuerpo', 'NORMAL', NULL, 'PARALIZADO', NULL, 85, 15, 'ATAQUE', 'Choque que puede paralizar', 42),
(35, 'Constricción', 'NORMAL', NULL, NULL, 3, 10, 35, 'ATAQUE', 'Aprieta al rival', 10),
(36, 'Derribo', 'NORMAL', NULL, NULL, NULL, 90, 20, 'ATAQUE', 'Carga que daña al usuario', 45),
(37, 'Golpe', 'NORMAL', NULL, NULL, 3, 120, 10, 'ATAQUE', 'Ataque feroz que confunde al usuario', 60),
(38, 'Doble Filo', 'NORMAL', NULL, NULL, NULL, 120, 15, 'ATAQUE', 'Ataque suicida muy potente', 60),
(39, 'Látigo', 'NORMAL', 'DEFENSA', NULL, 3, 0, 30, 'ESTADO', 'Mueve la cola para bajar defensa', 10),
(40, 'Picotazo Veneno', 'VENENO', NULL, 'ENVENENADO', NULL, 15, 35, 'ATAQUE', 'Pinchazo con veneno', 15),
(41, 'Dobleataque', 'BICHO', NULL, NULL, NULL, 25, 20, 'ATAQUE', 'Dos aguijonazos', 25),
(42, 'Pin Misil', 'BICHO', NULL, NULL, NULL, 25, 20, 'ATAQUE', 'Lanza ráfaga de pinchos', 25),
(43, 'Malicioso', 'NORMAL', 'DEFENSA', NULL, 3, 0, 30, 'ESTADO', 'Mirada que baja la defensa', 10),
(44, 'Mordisco', 'NORMAL', NULL, NULL, NULL, 60, 25, 'ATAQUE', 'Muerde con colmillos', 30),
(45, 'Gruñido', 'NORMAL', 'ATAQUE', NULL, 3, 0, 40, 'ESTADO', 'Baja el ataque del rival', 10),
(46, 'Rugido', 'NORMAL', NULL, NULL, NULL, 0, 20, 'ESTADO', 'Ahuyenta al rival', 15),
(47, 'Canto', 'NORMAL', NULL, 'DORMIDO', 3, 0, 15, 'ESTADO', 'Canción que duerme', 25),
(48, 'Supersónico', 'NORMAL', NULL, 'CONFUNDIDO', 3, 0, 20, 'ESTADO', 'Ondas que confunden', 20),
(49, 'Bomba Sónica', 'NORMAL', NULL, NULL, NULL, 20, 20, 'ATAQUE', 'Daño fijo de 20 PS', 20),
(50, 'Anulación', 'NORMAL', NULL, NULL, 3, 0, 20, 'ESTADO', 'Anula el último movimiento rival', 25),
(51, 'Ácido', 'VENENO', 'DEFENSA', NULL, NULL, 40, 30, 'ATAQUE', 'Rociada de ácido corrosivo', 20),
(52, 'Ascuas', 'FUEGO', NULL, NULL, NULL, 40, 25, 'ATAQUE', 'Pequeñas llamas', 20),
(53, 'Lanzallamas', 'FUEGO', NULL, NULL, NULL, 90, 15, 'ATAQUE', 'Gran ráfaga de fuego', 45),
(54, 'Neblina', 'HIELO', 'ESTADO', NULL, 5, 0, 30, 'MEJORA', 'Protege los stats propios', 25),
(55, 'Pistola Agua', 'AGUA', NULL, NULL, NULL, 40, 25, 'ATAQUE', 'Dispara agua a presión', 20),
(56, 'Hidrobomba', 'AGUA', NULL, NULL, NULL, 110, 5, 'ATAQUE', 'Cañón de agua masivo', 55),
(57, 'Surf', 'AGUA', NULL, NULL, NULL, 90, 15, 'ATAQUE', 'Gran ola inundante', 45),
(58, 'Rayo Hielo', 'HIELO', NULL, NULL, NULL, 90, 10, 'ATAQUE', 'Rayo que puede congelar', 45),
(59, 'Ventisca', 'HIELO', NULL, NULL, NULL, 110, 5, 'ATAQUE', 'Tormenta de nieve', 55),
(60, 'Rayo Confuso', 'FANTASMA', NULL, 'CONFUNDIDO', 3, 0, 10, 'ESTADO', 'Siniestro rayo que confunde', 25),
(61, 'Burbuja', 'AGUA', 'VELOCIDAD', NULL, NULL, 40, 30, 'ATAQUE', 'Burbujas que bajan velocidad', 20),
(62, 'Rayo Aurora', 'HIELO', 'ATAQUE', NULL, NULL, 65, 20, 'ATAQUE', 'Rayo multicolor', 32),
(63, 'Hiperrayo', 'NORMAL', NULL, NULL, NULL, 150, 5, 'ATAQUE', 'Necesita recargar turno', 75),
(64, 'Picotazo', 'VOLADOR', NULL, NULL, NULL, 35, 35, 'ATAQUE', 'Ataque con pico afilado', 18),
(65, 'Pico Taladro', 'VOLADOR', NULL, NULL, NULL, 80, 20, 'ATAQUE', 'Ataque giratorio', 40),
(66, 'Sumisión', 'LUCHA', NULL, NULL, NULL, 80, 25, 'ATAQUE', 'Golpe con retroceso', 40),
(67, 'Patada Baja', 'LUCHA', NULL, NULL, NULL, 50, 20, 'ATAQUE', 'Derriba por el peso', 25),
(68, 'Contador', 'LUCHA', NULL, NULL, NULL, 1, 20, 'ATAQUE', 'Devuelve el doble de daño físico', 30),
(69, 'Movimiento Sísmico', 'LUCHA', NULL, NULL, NULL, 10, 20, 'ATAQUE', 'Daño igual al nivel', 25),
(70, 'Fuerza', 'NORMAL', NULL, NULL, NULL, 80, 15, 'ATAQUE', 'Potencia física pura', 40),
(71, 'Absorber', 'PLANTA', 'VIDA', NULL, NULL, 20, 25, 'ATAQUE', 'Roba vida al rival', 25),
(72, 'Megaagotar', 'PLANTA', 'VIDA', NULL, NULL, 40, 15, 'ATAQUE', 'Roba mucha vida', 40),
(73, 'Drenadoras', 'PLANTA', 'VIDA', NULL, 5, 0, 10, 'ESTADO', 'Roba vida cada turno', 30),
(74, 'Crecimiento', 'NORMAL', 'ATAQUE_ESP', NULL, 3, 0, 40, 'MEJORA', 'Aumenta el ataque especial', 20),
(75, 'Hoja Afilada', 'PLANTA', NULL, NULL, NULL, 55, 25, 'ATAQUE', 'Hojas con mucho crítico', 28),
(76, 'Rayo Solar', 'PLANTA', NULL, NULL, NULL, 120, 10, 'ATAQUE', 'Carga un turno', 60),
(77, 'Polvo Veneno', 'VENENO', NULL, 'ENVENENADO', 5, 0, 35, 'ESTADO', 'Nube tóxica', 25),
(78, 'Paralizador', 'PLANTA', NULL, 'PARALIZADO', 3, 0, 30, 'ESTADO', 'Polen paralizante', 25),
(79, 'Somnífero', 'PLANTA', NULL, 'DORMIDO', 3, 0, 15, 'ESTADO', 'Polen que duerme', 30),
(80, 'Danza Pétalo', 'PLANTA', NULL, NULL, 3, 120, 10, 'ATAQUE', 'Ataque que confunde al usuario', 60),
(81, 'Disparo Demora', 'BICHO', 'VELOCIDAD', NULL, 3, 0, 40, 'ESTADO', 'Baja velocidad con seda', 15),
(82, 'Furia Dragón', 'DRAGON', NULL, NULL, NULL, 40, 10, 'ATAQUE', 'Daño fijo 40 PS', 40),
(83, 'Giro Fuego', 'FUEGO', NULL, NULL, 3, 35, 15, 'ATAQUE', 'Atrapa en llamas', 30),
(84, 'Impactrueno', 'ELECTRICO', NULL, 'PARALIZADO', NULL, 40, 30, 'ATAQUE', 'Descarga eléctrica', 20),
(85, 'Rayo', 'ELECTRICO', NULL, 'PARALIZADO', NULL, 90, 15, 'ATAQUE', 'Gran potencia eléctrica', 45),
(86, 'Onda Trueno', 'ELECTRICO', NULL, 'PARALIZADO', 4, 0, 20, 'ESTADO', 'Paraliza al 100%', 35),
(87, 'Trueno', 'ELECTRICO', NULL, 'PARALIZADO', NULL, 110, 10, 'ATAQUE', 'Ataque eléctrico máximo', 55),
(88, 'Lanzarrocas', 'ROCA', NULL, NULL, NULL, 50, 15, 'ATAQUE', 'Tira una piedra', 25),
(89, 'Terremoto', 'TIERRA', NULL, NULL, NULL, 100, 10, 'ATAQUE', 'Sacudida total', 50),
(90, 'Fisura', 'TIERRA', NULL, NULL, NULL, 190, 5, 'ATAQUE', 'Grieta fulminante', 95),
(91, 'Excavar', 'TIERRA', NULL, NULL, NULL, 80, 10, 'ATAQUE', 'Se oculta un turno', 40),
(92, 'Tóxico', 'VENENO', NULL, 'ENVENENADO', 6, 0, 10, 'ESTADO', 'Veneno que aumenta daño', 40),
(93, 'Confusión', 'PSIQUICO', NULL, 'CONFUNDIDO', NULL, 50, 25, 'ATAQUE', 'Onda mental suave', 25),
(94, 'Psíquico', 'PSIQUICO', 'DEFENSA_ESP', NULL, NULL, 90, 10, 'ATAQUE', 'Poderosa fuerza mental', 45),
(95, 'Hipnosis', 'PSIQUICO', NULL, 'DORMIDO', 3, 0, 20, 'ESTADO', 'Duerme al rival', 30),
(96, 'Meditar', 'PSIQUICO', 'ATAQUE', NULL, 3, 0, 40, 'MEJORA', 'Aumenta ataque propio', 15),
(97, 'Agilidad', 'PSIQUICO', 'VELOCIDAD', NULL, 3, 0, 30, 'MEJORA', 'Aumenta velocidad propia', 15),
(98, 'Ataque Rápido', 'NORMAL', NULL, NULL, NULL, 40, 30, 'ATAQUE', 'Siempre golpea primero', 20),
(99, 'Furia', 'NORMAL', 'ATAQUE', NULL, NULL, 20, 20, 'ATAQUE', 'Sube el ataque al ser golpeado', 20),
(100, 'Teletransporte', 'PSIQUICO', NULL, NULL, NULL, 0, 20, 'ESTADO', 'Huye del combate', 10),
(101, 'Tinieblas', 'FANTASMA', NULL, NULL, NULL, 10, 15, 'ATAQUE', 'Daño igual al nivel', 25),
(102, 'Mimético', 'NORMAL', NULL, NULL, NULL, 0, 10, 'ESTADO', 'Copia un movimiento rival', 30),
(103, 'Chirrido', 'NORMAL', 'DEFENSA', NULL, 3, 0, 40, 'ESTADO', 'Baja mucho la defensa', 15),
(104, 'Doble Equipo', 'NORMAL', 'EVASION', NULL, 3, 0, 15, 'MEJORA', 'Aumenta la evasión', 30),
(105, 'Recuperación', 'NORMAL', 'VIDA', NULL, NULL, 0, 20, 'MEJORA', 'Recupera 50% de vida', 40),
(106, 'Fortaleza', 'NORMAL', 'DEFENSA', NULL, 3, 0, 30, 'MEJORA', 'Aumenta defensa propia', 15),
(107, 'Reducción', 'NORMAL', 'EVASION', NULL, 3, 0, 20, 'MEJORA', 'Se hace pequeño para esquivar', 30),
(108, 'Pantalla Humo', 'NORMAL', 'PRECISION', NULL, 3, 0, 20, 'ESTADO', 'Baja la precisión', 15),
(109, 'Rayo Confuso', 'FANTASMA', NULL, 'CONFUNDIDO', 3, 0, 10, 'ESTADO', 'Confunde al rival', 25),
(110, 'Refugio', 'AGUA', 'DEFENSA', NULL, 3, 0, 40, 'MEJORA', 'Se mete en la concha', 15),
(111, 'Rizo Defensa', 'NORMAL', 'DEFENSA', NULL, 3, 0, 40, 'MEJORA', 'Se hace bola', 15),
(112, 'Barrera', 'PSIQUICO', 'DEFENSA', NULL, 3, 0, 30, 'MEJORA', 'Aumenta mucho la defensa', 25),
(113, 'Pantalla Luz', 'PSIQUICO', 'DEFENSA_ESP', NULL, 5, 0, 30, 'MEJORA', 'Reduce daño especial', 30),
(114, 'Niebla', 'HIELO', 'ESTADO', NULL, NULL, 0, 30, 'MEJORA', 'Limpia cambios de estado', 20),
(115, 'Reflejo', 'PSIQUICO', 'DEFENSA', NULL, 5, 0, 20, 'MEJORA', 'Reduce daño físico', 30),
(116, 'Foco Energía', 'NORMAL', 'CRITICO', NULL, 3, 0, 30, 'MEJORA', 'Aumenta crítico', 20),
(117, 'Venganza', 'NORMAL', NULL, NULL, 2, 1, 10, 'ATAQUE', 'Devuelve el doble de daño recibido', 40),
(118, 'Metrónomo', 'NORMAL', NULL, NULL, NULL, 0, 10, 'ESTADO', 'Usa un ataque al azar', 40),
(119, 'Espejo', 'VOLADOR', NULL, NULL, NULL, 0, 20, 'ESTADO', 'Copia el ataque rival', 35),
(120, 'Autodestrucción', 'NORMAL', NULL, NULL, NULL, 200, 5, 'ATAQUE', 'El usuario se debilita', 100),
(121, 'Bomba Huevo', 'NORMAL', NULL, NULL, NULL, 100, 10, 'ATAQUE', 'Lanza un huevo explosivo', 50),
(122, 'Lengüetazo', 'FANTASMA', NULL, 'PARALIZADO', NULL, 20, 30, 'ATAQUE', 'Puede paralizar', 15),
(123, 'Polución', 'VENENO', NULL, 'ENVENENADO', NULL, 20, 20, 'ATAQUE', 'Lanza gas tóxico', 15),
(124, 'Residuos', 'VENENO', NULL, 'ENVENENADO', NULL, 65, 20, 'ATAQUE', 'Lodo tóxico', 32),
(125, 'Hueso Palo', 'TIERRA', NULL, NULL, NULL, 65, 20, 'ATAQUE', 'Golpea con un hueso', 32),
(126, 'Llamarada', 'FUEGO', NULL, 'QUEMADO', NULL, 120, 5, 'ATAQUE', 'Fuego de máxima potencia', 60),
(127, 'Cascada', 'AGUA', NULL, NULL, NULL, 80, 15, 'ATAQUE', 'Golpe de agua ascendente', 40),
(128, 'Tenaza', 'AGUA', NULL, NULL, 3, 35, 10, 'ATAQUE', 'Atrapa con pinzas', 25),
(129, 'Rapidez', 'NORMAL', NULL, NULL, NULL, 60, 20, 'ATAQUE', 'Nunca falla', 30),
(130, 'Cabezazo', 'NORMAL', 'DEFENSA', NULL, 2, 100, 15, 'ATAQUE', 'Carga un turno y ataca', 50),
(131, 'Clavo Cañón', 'NORMAL', NULL, NULL, NULL, 20, 15, 'ATAQUE', 'Dispara 2 a 5 veces', 20),
(132, 'Restricción', 'NORMAL', 'VELOCIDAD', NULL, NULL, 10, 35, 'ATAQUE', 'Baja velocidad', 10),
(133, 'Amnesia', 'PSIQUICO', 'DEFENSA_ESP', NULL, 3, 0, 20, 'MEJORA', 'Sube mucho defensa especial', 20),
(134, 'Kinético', 'PSIQUICO', 'PRECISION', NULL, 3, 0, 15, 'ESTADO', 'Baja la precisión', 15),
(135, 'Amortiguador', 'NORMAL', 'VIDA', NULL, NULL, 0, 10, 'MEJORA', 'Recupera vida', 45),
(136, 'Patada Salto Alta', 'LUCHA', NULL, NULL, NULL, 130, 10, 'ATAQUE', 'Muy potente pero peligrosa', 65),
(137, 'Deslumbrar', 'NORMAL', NULL, 'PARALIZADO', 3, 0, 30, 'ESTADO', 'Paraliza con la mirada', 20),
(138, 'Comepueblos', 'PSIQUICO', 'VIDA', NULL, NULL, 100, 15, 'ATAQUE', 'Daña si el rival duerme', 50),
(139, 'Gas Venenoso', 'VENENO', NULL, 'ENVENENADO', 5, 0, 40, 'ESTADO', 'Envenena al rival', 20),
(140, 'Presa', 'NORMAL', NULL, NULL, 3, 15, 20, 'ATAQUE', 'Daña varios turnos', 15),
(141, 'Vampirismo', 'BICHO', 'VIDA', NULL, NULL, 20, 15, 'ATAQUE', 'Roba vida', 15),
(142, 'Beso Dulce', 'NORMAL', NULL, 'CONFUNDIDO', 3, 0, 10, 'ESTADO', 'Confunde con un beso', 15),
(143, 'Movimiento Espejo', 'VOLADOR', NULL, NULL, NULL, 0, 20, 'ESTADO', 'Devuelve el ataque', 30),
(144, 'Transformación', 'NORMAL', NULL, NULL, NULL, 0, 10, 'ESTADO', 'Se transforma en el rival', 50),
(145, 'Burbuja', 'AGUA', 'VELOCIDAD', NULL, NULL, 20, 30, 'ATAQUE', 'Burbujas lentas', 15),
(146, 'Puño Mareo', 'NORMAL', NULL, 'CONFUNDIDO', NULL, 70, 10, 'ATAQUE', 'Puede confundir', 35),
(147, 'Espora', 'PLANTA', NULL, 'DORMIDO', 3, 0, 15, 'ESTADO', 'Duerme al 100%', 40),
(148, 'Destello', 'NORMAL', 'PRECISION', NULL, 3, 0, 20, 'ESTADO', 'Baja la precisión', 15),
(149, 'Psicoonda', 'PSIQUICO', NULL, NULL, NULL, 1, 15, 'ATAQUE', 'Daño variable', 30),
(150, 'Salpicadura', 'AGUA', NULL, NULL, NULL, 0, 40, 'ESTADO', 'No hace nada...', 5),
(151, 'Armadura Ácida', 'VENENO', 'DEFENSA', NULL, 3, 0, 40, 'MEJORA', 'Sube mucho la defensa', 20),
(152, 'Martillazo', 'AGUA', NULL, NULL, NULL, 100, 10, 'ATAQUE', 'Gran golpe de pinza', 50),
(153, 'Explosión', 'NORMAL', NULL, NULL, NULL, 250, 5, 'ATAQUE', 'El usuario explota', 100),
(154, 'Golpes Furia', 'NORMAL', NULL, NULL, NULL, 18, 15, 'ATAQUE', 'Arañazos múltiples', 20),
(155, 'Huesomerang', 'TIERRA', NULL, NULL, NULL, 50, 10, 'ATAQUE', 'Golpea dos veces', 40),
(156, 'Descanso', 'PSIQUICO', 'ESTADO', 'DORMIDO', 2, 0, 10, 'MEJORA', 'Se duerme y se cura total', 50),
(157, 'Avalancha', 'ROCA', NULL, NULL, NULL, 75, 10, 'ATAQUE', 'Puede amedrentar', 38),
(158, 'Hipercolmillo', 'NORMAL', NULL, NULL, NULL, 80, 15, 'ATAQUE', 'Muerde con fuerza', 40),
(159, 'Afilar', 'NORMAL', 'ATAQUE', NULL, 3, 0, 30, 'MEJORA', 'Aumenta el ataque', 15),
(160, 'Conversión', 'NORMAL', NULL, NULL, NULL, 0, 30, 'ESTADO', 'Cambia tipo al del rival', 30),
(161, 'Triataque', 'NORMAL', NULL, 'ESTADO', NULL, 80, 10, 'ATAQUE', 'Puede quemar, helar o paralizar', 40),
(162, 'Superdiente', 'NORMAL', NULL, NULL, NULL, 1, 10, 'ATAQUE', 'Quita la mitad de vida', 45),
(163, 'Acuchillar', 'NORMAL', NULL, NULL, NULL, 70, 20, 'ATAQUE', 'Cuchillada de alto crítico', 35),
(164, 'Sustituto', 'NORMAL', NULL, NULL, NULL, 0, 10, 'ESTADO', 'Crea un señuelo', 50),
(165, 'Combate', 'NORMAL', NULL, NULL, NULL, 50, 1, 'ATAQUE', 'Solo se usa sin PP', 25);
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
INSERT INTO `pokedex` (`num_Pokedex`, `nombre`, `tipo1`, `tipo2`, `img_Back`, `sonido`, `img_Frontal`) VALUES
(1, 'Bulbasaur', 'PLANTA', 'VENENO', NULL, NULL, 'resources/spritesPokemons/Front/1.png'),
(2, 'Ivysaur', 'PLANTA', 'VENENO', NULL, NULL, 'resources/spritesPokemons/Front/2.png'),
(3, 'Venusaur', 'PLANTA', 'VENENO', NULL, NULL, 'resources/spritesPokemons/Front/3.png'),
(4, 'Charmander', 'FUEGO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/4.png'),
(5, 'Charmeleon', 'FUEGO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/5.png'),
(6, 'Charizard', 'FUEGO', 'VOLADOR', NULL, NULL, 'resources/spritesPokemons/Front/6.png'),
(7, 'Squirtle', 'AGUA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/7.png'),
(8, 'Wartortle', 'AGUA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/8.png'),
(9, 'Blastoise', 'AGUA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/9.png'),
(10, 'Caterpie', 'BICHO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/10.png'),
(11, 'Metapod', 'BICHO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/11.png'),
(12, 'Butterfree', 'BICHO', 'VOLADOR', NULL, NULL, 'resources/spritesPokemons/Front/12.png'),
(13, 'Weedle', 'BICHO', 'VENENO', NULL, NULL, 'resources/spritesPokemons/Front/13.png'),
(14, 'Kakuna', 'BICHO', 'VENENO', NULL, NULL, 'resources/spritesPokemons/Front/14.png'),
(15, 'Beedrill', 'BICHO', 'VENENO', NULL, NULL, 'resources/spritesPokemons/Front/15.png'),
(16, 'Pidgey', 'NORMAL', 'VOLADOR', NULL, NULL, 'resources/spritesPokemons/Front/16.png'),
(17, 'Pidgeotto', 'NORMAL', 'VOLADOR', NULL, NULL, 'resources/spritesPokemons/Front/17.png'),
(18, 'Pidgeot', 'NORMAL', 'VOLADOR', NULL, NULL, 'resources/spritesPokemons/Front/18.png'),
(19, 'Rattata', 'NORMAL', NULL, NULL, NULL, 'resources/spritesPokemons/Front/19.png'),
(20, 'Raticate', 'NORMAL', NULL, NULL, NULL, 'resources/spritesPokemons/Front/20.png'),
(21, 'Spearow', 'NORMAL', 'VOLADOR', NULL, NULL, 'resources/spritesPokemons/Front/21.png'),
(22, 'Fearow', 'NORMAL', 'VOLADOR', NULL, NULL, 'resources/spritesPokemons/Front/22.png'),
(23, 'Ekans', 'VENENO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/23.png'),
(24, 'Arbok', 'VENENO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/24.png'),
(25, 'Pikachu', 'ELECTRICO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/25.png'),
(26, 'Raichu', 'ELECTRICO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/26.png'),
(27, 'Sandshrew', 'TIERRA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/27.png'),
(28, 'Sandslash', 'TIERRA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/28.png'),
(29, 'Nidoran♀', 'VENENO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/29.png'),
(30, 'Nidorina', 'VENENO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/30.png'),
(31, 'Nidoqueen', 'VENENO', 'TIERRA', NULL, NULL, 'resources/spritesPokemons/Front/31.png'),
(32, 'Nidoran♂', 'VENENO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/32.png'),
(33, 'Nidorino', 'VENENO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/33.png'),
(34, 'Nidoking', 'VENENO', 'TIERRA', NULL, NULL, 'resources/spritesPokemons/Front/34.png'),
(35, 'Clefairy', 'NORMAL', NULL, NULL, NULL, 'resources/spritesPokemons/Front/35.png'),
(36, 'Clefable', 'NORMAL', NULL, NULL, NULL, 'resources/spritesPokemons/Front/36.png'),
(37, 'Vulpix', 'FUEGO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/37.png'),
(38, 'Ninetales', 'FUEGO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/38.png'),
(39, 'Jigglypuff', 'NORMAL', NULL, NULL, NULL, 'resources/spritesPokemons/Front/39.png'),
(40, 'Wigglytuff', 'NORMAL', NULL, NULL, NULL, 'resources/spritesPokemons/Front/40.png'),
(41, 'Zubat', 'VENENO', 'VOLADOR', NULL, NULL, 'resources/spritesPokemons/Front/41.png'),
(42, 'Golbat', 'VENENO', 'VOLADOR', NULL, NULL, 'resources/spritesPokemons/Front/42.png'),
(43, 'Oddish', 'PLANTA', 'VENENO', NULL, NULL, 'resources/spritesPokemons/Front/43.png'),
(44, 'Gloom', 'PLANTA', 'VENENO', NULL, NULL, 'resources/spritesPokemons/Front/44.png'),
(45, 'Vileplume', 'PLANTA', 'VENENO', NULL, NULL, 'resources/spritesPokemons/Front/45.png'),
(46, 'Paras', 'BICHO', 'PLANTA', NULL, NULL, 'resources/spritesPokemons/Front/46.png'),
(47, 'Parasect', 'BICHO', 'PLANTA', NULL, NULL, 'resources/spritesPokemons/Front/47.png'),
(48, 'Venonat', 'BICHO', 'VENENO', NULL, NULL, 'resources/spritesPokemons/Front/48.png'),
(49, 'Venomoth', 'BICHO', 'VENENO', NULL, NULL, 'resources/spritesPokemons/Front/49.png'),
(50, 'Diglett', 'TIERRA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/50.png'),
(51, 'Dugtrio', 'TIERRA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/51.png'),
(52, 'Meowth', 'NORMAL', NULL, NULL, NULL, 'resources/spritesPokemons/Front/52.png'),
(53, 'Persian', 'NORMAL', NULL, NULL, NULL, 'resources/spritesPokemons/Front/53.png'),
(54, 'Psyduck', 'AGUA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/54.png'),
(55, 'Golduck', 'AGUA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/55.png'),
(56, 'Mankey', 'LUCHA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/56.png'),
(57, 'Primeape', 'LUCHA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/57.png'),
(58, 'Growlithe', 'FUEGO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/58.png'),
(59, 'Arcanine', 'FUEGO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/59.png'),
(60, 'Poliwag', 'AGUA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/60.png'),
(61, 'Poliwhirl', 'AGUA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/61.png'),
(62, 'Poliwrath', 'AGUA', 'LUCHA', NULL, NULL, 'resources/spritesPokemons/Front/62.png'),
(63, 'Abra', 'PSIQUICO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/63.png'),
(64, 'Kadabra', 'PSIQUICO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/64.png'),
(65, 'Alakazam', 'PSIQUICO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/65.png'),
(66, 'Machop', 'LUCHA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/66.png'),
(67, 'Machoke', 'LUCHA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/67.png'),
(68, 'Machamp', 'LUCHA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/68.png'),
(69, 'Bellsprout', 'PLANTA', 'VENENO', NULL, NULL, 'resources/spritesPokemons/Front/69.png'),
(70, 'Weepinbell', 'PLANTA', 'VENENO', NULL, NULL, 'resources/spritesPokemons/Front/70.png'),
(71, 'Victreebel', 'PLANTA', 'VENENO', NULL, NULL, 'resources/spritesPokemons/Front/71.png'),
(72, 'Tentacool', 'AGUA', 'VENENO', NULL, NULL, 'resources/spritesPokemons/Front/72.png'),
(73, 'Tentacruel', 'AGUA', 'VENENO', NULL, NULL, 'resources/spritesPokemons/Front/73.png'),
(74, 'Geodude', 'ROCA', 'TIERRA', NULL, NULL, 'resources/spritesPokemons/Front/74.png'),
(75, 'Graveler', 'ROCA', 'TIERRA', NULL, NULL, 'resources/spritesPokemons/Front/75.png'),
(76, 'Golem', 'ROCA', 'TIERRA', NULL, NULL, 'resources/spritesPokemons/Front/76.png'),
(77, 'Ponyta', 'FUEGO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/77.png'),
(78, 'Rapidash', 'FUEGO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/78.png'),
(79, 'Slowpoke', 'AGUA', 'PSIQUICO', NULL, NULL, 'resources/spritesPokemons/Front/79.png'),
(80, 'Slowbro', 'AGUA', 'PSIQUICO', NULL, NULL, 'resources/spritesPokemons/Front/80.png'),
(81, 'Magnemite', 'ELECTRICO', 'ACERO', NULL, NULL, 'resources/spritesPokemons/Front/81.png'),
(82, 'Magneton', 'ELECTRICO', 'ACERO', NULL, NULL, 'resources/spritesPokemons/Front/82.png'),
(83, 'Farfetchd', 'NORMAL', 'VOLADOR', NULL, NULL, 'resources/spritesPokemons/Front/83.png'),
(84, 'Doduo', 'NORMAL', 'VOLADOR', NULL, NULL, 'resources/spritesPokemons/Front/84.png'),
(85, 'Dodrio', 'NORMAL', 'VOLADOR', NULL, NULL, 'resources/spritesPokemons/Front/85.png'),
(86, 'Seel', 'AGUA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/86.png'),
(87, 'Dewgong', 'AGUA', 'HIELO', NULL, NULL, 'resources/spritesPokemons/Front/87.png'),
(88, 'Grimer', 'VENENO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/88.png'),
(89, 'Muk', 'VENENO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/89.png'),
(90, 'Shellder', 'AGUA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/90.png'),
(91, 'Cloyster', 'AGUA', 'HIELO', NULL, NULL, 'resources/spritesPokemons/Front/91.png'),
(92, 'Gastly', 'FANTASMA', 'VENENO', NULL, NULL, 'resources/spritesPokemons/Front/92.png'),
(93, 'Haunter', 'FANTASMA', 'VENENO', NULL, NULL, 'resources/spritesPokemons/Front/93.png'),
(94, 'Gengar', 'FANTASMA', 'VENENO', NULL, NULL, 'resources/spritesPokemons/Front/94.png'),
(95, 'Onix', 'ROCA', 'TIERRA', NULL, NULL, 'resources/spritesPokemons/Front/95.png'),
(96, 'Drowzee', 'PSIQUICO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/96.png'),
(97, 'Hypno', 'PSIQUICO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/97.png'),
(98, 'Krabby', 'AGUA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/98.png'),
(99, 'Kingler', 'AGUA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/99.png'),
(100, 'Voltorb', 'ELECTRICO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/100.png'),
(101, 'Electrode', 'ELECTRICO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/101.png'),
(102, 'Exeggcute', 'PLANTA', 'PSIQUICO', NULL, NULL, 'resources/spritesPokemons/Front/102.png'),
(103, 'Exeggutor', 'PLANTA', 'PSIQUICO', NULL, NULL, 'resources/spritesPokemons/Front/103.png'),
(104, 'Cubone', 'TIERRA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/104.png'),
(105, 'Marowak', 'TIERRA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/105.png'),
(106, 'Hitmonlee', 'LUCHA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/106.png'),
(107, 'Hitmonchan', 'LUCHA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/107.png'),
(108, 'Lickitung', 'NORMAL', NULL, NULL, NULL, 'resources/spritesPokemons/Front/108.png'),
(109, 'Koffing', 'VENENO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/109.png'),
(110, 'Weezing', 'VENENO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/110.png'),
(111, 'Rhyhorn', 'TIERRA', 'ROCA', NULL, NULL, 'resources/spritesPokemons/Front/111.png'),
(112, 'Rhydon', 'TIERRA', 'ROCA', NULL, NULL, 'resources/spritesPokemons/Front/112.png'),
(113, 'Chansey', 'NORMAL', NULL, NULL, NULL, 'resources/spritesPokemons/Front/113.png'),
(114, 'Tangela', 'PLANTA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/114.png'),
(115, 'Kangaskhan', 'NORMAL', NULL, NULL, NULL, 'resources/spritesPokemons/Front/115.png'),
(116, 'Horsea', 'AGUA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/116.png'),
(117, 'Seadra', 'AGUA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/117.png'),
(118, 'Goldeen', 'AGUA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/118.png'),
(119, 'Seaking', 'AGUA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/119.png'),
(120, 'Staryu', 'AGUA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/120.png'),
(121, 'Starmie', 'AGUA', 'PSIQUICO', NULL, NULL, 'resources/spritesPokemons/Front/121.png'),
(122, 'Mr. Mime', 'PSIQUICO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/122.png'),
(123, 'Scyther', 'BICHO', 'VOLADOR', NULL, NULL, 'resources/spritesPokemons/Front/123.png'),
(124, 'Jynx', 'HIELO', 'PSIQUICO', NULL, NULL, 'resources/spritesPokemons/Front/124.png'),
(125, 'Electabuzz', 'ELECTRICO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/125.png'),
(126, 'Magmar', 'FUEGO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/126.png'),
(127, 'Pinsir', 'BICHO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/127.png'),
(128, 'Tauros', 'NORMAL', NULL, NULL, NULL, 'resources/spritesPokemons/Front/128.png'),
(129, 'Magikarp', 'AGUA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/129.png'),
(130, 'Gyarados', 'AGUA', 'VOLADOR', NULL, NULL, 'resources/spritesPokemons/Front/130.png'),
(131, 'Lapras', 'AGUA', 'HIELO', NULL, NULL, 'resources/spritesPokemons/Front/131.png'),
(132, 'Ditto', 'NORMAL', NULL, NULL, NULL, 'resources/spritesPokemons/Front/132.png'),
(133, 'Eevee', 'NORMAL', NULL, NULL, NULL, 'resources/spritesPokemons/Front/133.png'),
(134, 'Vaporeon', 'AGUA', NULL, NULL, NULL, 'resources/spritesPokemons/Front/134.png'),
(135, 'Jolteon', 'ELECTRICO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/135.png'),
(136, 'Flareon', 'FUEGO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/136.png'),
(137, 'Porygon', 'NORMAL', NULL, NULL, NULL, 'resources/spritesPokemons/Front/137.png'),
(138, 'Omanyte', 'ROCA', 'AGUA', NULL, NULL, 'resources/spritesPokemons/Front/138.png'),
(139, 'Omastar', 'ROCA', 'AGUA', NULL, NULL, 'resources/spritesPokemons/Front/139.png'),
(140, 'Kabuto', 'ROCA', 'AGUA', NULL, NULL, 'resources/spritesPokemons/Front/140.png'),
(141, 'Kabutops', 'ROCA', 'AGUA', NULL, NULL, 'resources/spritesPokemons/Front/141.png'),
(142, 'Aerodactyl', 'ROCA', 'VOLADOR', NULL, NULL, 'resources/spritesPokemons/Front/142.png'),
(143, 'Snorlax', 'NORMAL', NULL, NULL, NULL, 'resources/spritesPokemons/Front/143.png'),
(144, 'Articuno', 'HIELO', 'VOLADOR', NULL, NULL, 'resources/spritesPokemons/Front/144.png'),
(145, 'Zapdos', 'ELECTRICO', 'VOLADOR', NULL, NULL, 'resources/spritesPokemons/Front/145.png'),
(146, 'Moltres', 'FUEGO', 'VOLADOR', NULL, NULL, 'resources/spritesPokemons/Front/146.png'),
(147, 'Dratini', 'DRAGON', NULL, NULL, NULL, 'resources/spritesPokemons/Front/147.png'),
(148, 'Dragonair', 'DRAGON', NULL, NULL, NULL, 'resources/spritesPokemons/Front/148.png'),
(149, 'Dragonite', 'DRAGON', 'VOLADOR', NULL, NULL, 'resources/spritesPokemons/Front/149.png'),
(150, 'Mewtwo', 'PSIQUICO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/150.png'),
(151, 'Mew', 'PSIQUICO', NULL, NULL, NULL, 'resources/spritesPokemons/Front/151.png');
-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pokemon`
--

CREATE TABLE `pokemon` (
  `id_Pokemon` int(11) AUTO_INCREMENT NOT NULL ,
  `num_Pokedex` int(11) NOT NULL,
  `id_Entrenador` int(11) DEFAULT NULL,
  `id_Objeto` int(11) DEFAULT NULL,
  `mote` varchar(50) DEFAULT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `vitalidad` int(11) NOT NULL,
  `vitalidadMaxima` int(11) NOT NULL,
  `ataque` int(11) NOT NULL,
  `defensa` int(11) NOT NULL,
  `ataq_Especial` int(11) NOT NULL,
  `velocidad` int(11) NOT NULL,
  `def_Especial` int(11) NOT NULL,
  `fertilidad` int(11) DEFAULT 5,
  `nivel` int(11) DEFAULT 1,
  `experiencia` int(11) DEFAULT 0,
  `estado` varchar(20) DEFAULT NULL,
  `ubicacion` int(11) DEFAULT NULL,
  `sexo` char(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--Tabla que servirá para qu ecada pokemon tenga unos ataques basicos
CREATE TABLE `pokedex_movimiento` (
	`num_Pokedex` int(11) NOT NULL,
	`id_Movimiento` int(11) NOT NULL,
	PRIMARY KEY (`num_Pokedex`, `id_Movimiento`),
	FOREIGN KEY (`num_Pokedex`) REFERENCES `pokedex` (`num_Pokedex`),
	FOREIGN KEY (`id_Movimiento`) REFERENCES `movimiento` (`id_Movimiento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--	Inserts de ataques basicos de cada Pokemon
-- Hechos con IA porque yo para tanto no doy
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
