DROP DATABASE IF EXISTS KITSURA_DB;
CREATE DATABASE IF NOT EXISTS KITSURA_DB;
USE KITSURA_DB;

-- USUARIO Y ADMINISTRADOR
CREATE TABLE Usuario (
	
    id_usuario      INT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario  VARCHAR(100) NOT NULL,
    correo          VARCHAR(255) NOT NULL UNIQUE,
    contrasena      VARCHAR(255) NOT NULL,
    -- No se ha ingresado el rol de "invitado" porque no se guardará su progreso dentro del programa
    rol             ENUM('administrador', 'usuario') NOT NULL DEFAULT 'usuario',
    -- Debe de ingresarse la ruta donde se encuentra la imagen de perfil
    imagen_perfil   VARCHAR(255),
    -- Se define YYYY-MM-DD HH:MM:SS del momento preciso que se ha registrado el usuario
    fecha_registro  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado          ENUM('activo','inactivo') NOT NULL DEFAULT 'activo'
);

-- ------------------------------------
-- USUARIO ADMINISTRADOR
-- Se definen los datos utilizados para administrador 
INSERT INTO Usuario (
	-- Nombre del administrador
    nombre_usuario,
    -- correo predefinido para administrador
    correo,
    -- llave de acceso para ingresar al panel de administrador
    contrasena,
    -- Indica si es usuario/ administrador
    rol
) VALUES (
    'admin',
    'admin@kitsura.com',
    'KITSURA',
    'administrador'
);
-- ------------------------------------

CREATE TABLE Sesion (
	-- Llave primaria de la tabla/entidad 
    id_sesion        INT AUTO_INCREMENT PRIMARY KEY,
    -- Llave foranea 
    id_usuario       INT NOT NULL,
    token            VARCHAR(255) NOT NULL UNIQUE,
    fecha_inicio     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_expiracion TIMESTAMP NOT NULL,

    FOREIGN KEY (id_usuario)
        REFERENCES Usuario(id_usuario)
        -- Cuando una tabla principal elimina un dato, si existen FK en otras tablas, se elimina automáticamente ese registro
        ON DELETE CASCADE
);

CREATE TABLE Minijuego (
    id_minijuego    INT AUTO_INCREMENT PRIMARY KEY,
    
    -- Nombre que recibe el minijuego
    nombre          VARCHAR(255) NOT NULL UNIQUE,

    -- Si el minijuego pertenece a la tabla de estadisticas
    ranking_activo  BOOLEAN DEFAULT TRUE,
    
    -- Al momento de eliminar un elemento de la tabla, puede causar error por la relación que tiene con otras tablas
    -- dependiendo del contexto. Se desactiva, para que no aparezca en el juego y así, evitar errores
    estado          ENUM('activo','inactivo') NOT NULL DEFAULT 'activo'
);

INSERT INTO Minijuego (nombre) VALUES
('Hidden Fox'),
('Fox Jump!'),
('Maulwurf Rennt');

-- Se crea  la tabla categoría
CREATE TABLE Categoria (
	id_categoria INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    id_minijuego INT NOT NULL,
    nombre  VARCHAR (50) NOT NULL,
	
    FOREIGN KEY (id_minijuego)
    REFERENCES Minijuego (id_minijuego)
    ON DELETE CASCADE,
    
    UNIQUE KEY uq_categoria
(id_minijuego, nombre)
);

INSERT INTO Categoria (id_minijuego, nombre) VALUES
-- MINIJUEGO 1: Hidden Fox
(1, 'Animales'),
(1, 'Territorios'),
(1, 'Caricaturas'),
-- MINIJUEGO 2: Fox Jump!
(2, 'Animales'),
(2, 'Plantas'),
(2, 'Hábitats'),
-- MINIJUEGO 3: Maulwurf Rennt
(3, 'Operaciones Básicas'),
(3, 'Operaciones Avanzadas'), 
(3, 'Científicos Matemáticos');

-- Nivel de dificultad
CREATE TABLE Configuracion_nivel (
	id_nivel INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    
    id_categoria INT NOT NULL,
    
    dificultad ENUM ('Fácil', 'Intermedio', 'Difícil') NOT NULL,
    
	-- Es el tiempo predefinido para cada nivel de las categorias de minijuegos
    tiempo_limite   SMALLINT NOT NULL,
    
    vidas INT NOT NULL DEFAULT 3, 
    
    FOREIGN KEY (id_categoria)
    REFERENCES Categoria (id_categoria)
    ON DELETE CASCADE,
    
    UNIQUE KEY uq_categoria_dificultad
        (id_categoria, dificultad)
);



-- Se insertan los tres tipos de niveles de dificultad
INSERT INTO Configuracion_nivel (id_categoria, dificultad, tiempo_limite) VALUES
-- MINIJUEGO 1: Hidden Fox
-- CATEGORÍA 1: ANIMALES
(1, 'Fácil', 25),
(1, 'Intermedio', 20),
(1, 'Difícil', 15),
-- CATEGORÍA 2: TERRITORIOS
(2, 'Fácil', 25),
(2, 'Intermedio', 20),
(2, 'Difícil', 15),
-- CATEGORÍA 3: Caricaturas
(3, 'Fácil', 25),
(3, 'Intermedio', 20),
(3, 'Difícil', 15),
-- MINIJUEGO 2: Fox Jump!
-- CATEGORÍA 4: ANIMALES
(4, 'Fácil', 30),
(4, 'Intermedio', 25),
(4, 'Difícil', 20),
-- CATEGORÍA 5: PLANTAS
(5, 'Fácil', 30),
(5, 'Intermedio', 25),
(5, 'Difícil', 20),
-- CATEGORÍA 6: HÁBITATS
(6, 'Fácil', 30),
(6, 'Intermedio', 25),
(6, 'Difícil', 20),
-- MINIJUEGO 3: Maulwurf Rennt
-- CATEGORÍA 7: OPERACIONES BÁSICAS
(7, 'Fácil', 25),
(7, 'Intermedio', 20),
(7, 'Difícil', 15),
-- CATEGORÍA 8: OPERACIONES AVANZADAS
(8, 'Fácil', 25),
(8, 'Intermedio', 20),
(8, 'Difícil', 15),
-- CATEGORÍA 9: CIENTÍFICOS MATEMÁTICOS
(9, 'Fácil', 25),
(9, 'Intermedio', 20),
(9, 'Difícil', 15);

CREATE TABLE Pregunta (
    id_pregunta       INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    -- Permite visualizar el minijuego perteneciente - FK
    
    -- Nivel de dificultad
    id_nivel INT NOT NULL, 
    
    -- Se ingresa la pregunta a utilizar. Favor de utilizar signos de interrogación (inicio/final¿?)
    pregunta           TEXT NOT NULL,
    
    -- Son los puntos predefinidos que tienen cada nivel, se suman y aparecen en la estadistica
    puntos_base        SMALLINT NOT NULL DEFAULT 100,
    
    -- Al momento de eliminar un elemento de la tabla, puede causar error por la relación que tiene con otras tablas
    -- dependiendo del contexto. Se desactiva, para que no aparezca en el juego y así, evitar errores
    estado             ENUM('activo','inactivo') NOT NULL DEFAULT 'activo',
	
	-- Se muestra la imagen de la sombra
	imagen_sombra VARCHAR(255) NULL,
	
    -- Se muestra la imagen a color
    imagen_color VARCHAR(255) NULL,
    
    FOREIGN KEY (id_nivel)
    REFERENCES Configuracion_nivel (id_nivel)
    ON DELETE CASCADE
);

CREATE TABLE Opcion_respuesta(
	id_opcion     INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    
    -- Permite visualizar la pregunta perteneciente a las respuestas dadas en esta tabla
    id_pregunta       INT NOT NULL,
    
    -- Se ingresa las posibles respuesta a la pregunta 
	texto_opcion VARCHAR(255) NOT NULL,
    
    -- Deben de ingresar las respuestas correctas, de acuerdo al minijuego
    -- Minijuego 1: 3 respuestas incorrectas por cada nivel
    -- Minijuego 2: 1 respuesta incorrecta por cada nivel (Falso)
    -- Minijuego 3: Varía de acuerdo a la dificultad:
    -- Nivel Fácil: 4 respuestas incorrectas
    -- Nivel Intermedio: 5 respuestas incorrectas
    -- Nivel Dificil: 6 respuestas incorrectas
    es_correcta BOOLEAN NOT NULL DEFAULT FALSE,
    
    FOREIGN KEY (id_pregunta)
    REFERENCES Pregunta(id_pregunta)
    ON DELETE CASCADE
);

CREATE TABLE Ayuda (
    id_ayuda             INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    
    -- Llave foranea
    id_pregunta          INT NOT NULL,
    
    -- La pista/ayuda varia de acuerdo al contexto de la pregunta
    tipo                 ENUM('audio','texto') NOT NULL,
    
    -- Si se ha elegido el tipo 'texto' se agregará el contenido
    contenido            TEXT NULL,
    
    -- Si se ha elegido el tipo 'audio' se guardará aquí:
    audio VARCHAR(200) NULL,
    
    -- Si el usuario utiliza la ayuda, se le restaran 10 puntos
    penalizacion_puntos  SMALLINT NOT NULL DEFAULT 10,
    
    -- Al momento de eliminar un elemento de la tabla, puede causar error por la relación que tiene con otras tablas
    -- dependiendo del contexto. Se desactiva, para que no aparezca en el juego y así, evitar errores
    estado               ENUM('activo','inactivo') NOT NULL DEFAULT 'activo',

    FOREIGN KEY (id_pregunta)
        REFERENCES Pregunta(id_pregunta)
        ON DELETE CASCADE
);

CREATE TABLE Partida (
    id_partida               INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario               INT NOT NULL,
    id_minijuego             INT NOT NULL,
    
    -- Hace referencia al iniciar una partida, el usuario tiene un total de 0 pts, mientras avanza
    -- de nivel, se aumentaran los puntos dados por las pregunatas
    puntuacion               INT DEFAULT 0,
    
    -- Si se ha actualizado la cantidad de vidas en el minijuego por medio de un jugador
    -- Es la memoria que indica que anteriormente tenían 3 vidas
    vidas_iniciales_snapshot TINYINT NOT NULL,
    
    -- Segun la cantidad de tiempo utilizado por pregunta, se irá acumulando para tener el resultado final
    -- Ejemplo: N1 la persona finaliza la pregunta en 10 segundos, y el tiempo indicado para la pregunta es de 25 segundos
    tiempo_jugado            INT NOT NULL DEFAULT 0,
    
    -- Al momento de eliminar un elemento de la tabla, puede causar error por la relación que tiene con otras tablas
    -- dependiendo del contexto. Se desactiva, para que no aparezca en el juego y así, evitar errores
    estado                   ENUM('en_curso','completada','abandonada') NOT NULL DEFAULT 'en_curso',

    FOREIGN KEY (id_usuario)
        REFERENCES Usuario(id_usuario)
        ON DELETE CASCADE,

    FOREIGN KEY (id_minijuego)
        REFERENCES Minijuego(id_minijuego)
        ON DELETE CASCADE
);

CREATE TABLE Detalle_partida (
    id_detalle               INT AUTO_INCREMENT PRIMARY KEY,
    -- FK
    id_partida               INT NOT NULL,
    
    -- FK
    id_pregunta              INT NOT NULL,
    
    -- Al final la partida, se contara los puntos obtenidos por las preguntas
    -- Puntuación perfecta: No tuvo ningún error y no utilizó ninguna pista
    -- Puntuación moderada: Obtuvo un error en la partida, no respondió una pregunta porque se acabó el tiempo o utilizó una pista
    puntos_obtenidos         SMALLINT NOT NULL DEFAULT 0,
    
    -- 
    tiempo_respuesta         SMALLINT DEFAULT NULL,
    
    respondio_correctamente BOOLEAN NOT NULL,
    
    FOREIGN KEY (id_partida)
        REFERENCES Partida(id_partida)
        ON DELETE CASCADE,

    FOREIGN KEY (id_pregunta)
        REFERENCES Pregunta(id_pregunta)
        ON DELETE RESTRICT
);

CREATE TABLE Estadistica (
    id_estadistica   INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario       INT NOT NULL,
    id_minijuego     INT NOT NULL,
    
    -- Se comparan las puntuaciones obtenidas en cada uno de los minijuegos y la más alta, será catalogada en esta sección
    mejor_puntuacion INT NOT NULL DEFAULT 0,
    
    -- Es la suma de todos los puntajes obtenidos en su perfil por todos los minijuegos
    puntuacion_total INT NOT NULL DEFAULT 0,
    
    -- Cada vez que el jugador ingrese a un minijuego, se iran contando
    partidas_jugadas INT NOT NULL DEFAULT 0,

    -- Es la cantidad de tiempo estimada que lleva el jugador dentro de las partidas
    tiempo_total     INT NOT NULL DEFAULT 0,
	
    -- Evita que se vuelva a crear nuevamente un usuario con el mismo ID en la tabla
    -- Unificando los puntajes y sumandolos, así aparecerá en la tabla de estadísticas
    UNIQUE KEY uq_estadistica (id_usuario, id_minijuego),

    FOREIGN KEY (id_usuario)
        REFERENCES Usuario(id_usuario)
        ON DELETE CASCADE,

    FOREIGN KEY (id_minijuego)
        REFERENCES Minijuego(id_minijuego)
        ON DELETE CASCADE
);

-- Búsqueda agilizada, comparando cada registro ingresado
-- Filtra las búsquedas
CREATE INDEX idx_partida_usuario
    ON Partida(id_usuario);

CREATE INDEX idx_partida_minijuego
    ON Partida(id_minijuego);

CREATE INDEX idx_detalle_partida
    ON Detalle_partida(id_partida);

CREATE INDEX idx_pregunta_nivel
    ON Pregunta(id_nivel);

CREATE INDEX idx_ayuda_pregunta
    ON Ayuda(id_pregunta);

CREATE INDEX idx_estadistica_ranking
    ON Estadistica(id_minijuego, mejor_puntuacion DESC);
    
-- INSERTAR 
-- N1_C1_M1
INSERT INTO Pregunta (id_nivel, pregunta, imagen_sombra, imagen_color) VALUES
-- MINIJUEGO 1 CATEGORÍA 1 NIVEL 1

-- 1. BUHO NIVAL
(1, '¿Qué animal representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Sombra_N1_C1_M1/N1_C1_M1_S_BuhoNival.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Color_N1_C1_M1/N1_C1_M1_C_BuhoNival.png'),

-- 2. CANGREJO
(1, '¿Qué animal representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Sombra_N1_C1_M1/N1_C1_M1_S_Cangrejo.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Color_N1_C1_M1/N1_C1_M1_C_Cangrejo.png'),

-- 3. CAPIBARA
(1, '¿Qué animal representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Sombra_N1_C1_M1/N1_C1_M1_S_Capibara.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Color_N1_C1_M1/N1_C1_M1_C_Capibara.png'),

-- 4. COBRA
(1, '¿Qué animal representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Sombra_N1_C1_M1/N1_C1_M1_S_Cobra.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Color_N1_C1_M1/N1_C1_M1_C_Cobra.png'),

-- 5. COCODRILO
(1, '¿Qué animal representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Sombra_N1_C1_M1/N1_C1_M1_S_Cocodrilo.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Color_N1_C1_M1/N1_C1_M1_C_Cocodrilo.png'),

-- 6. COLIBRÍ
(1, '¿Qué animal representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Sombra_N1_C1_M1/N1_C1_M1_S_Colibri.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Color_N1_C1_M1/N1_C1_M1_C_Colibri.png'),

-- 7. LECHUZA
(1, '¿Qué animal representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Sombra_N1_C1_M1/N1_C1_M1_S_Lechuza.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Color_N1_C1_M1/N1_C1_M1_C_Lechuza.png'),

-- 8. MANTIS
(1, '¿Qué animal representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Sombra_N1_C1_M1/N1_C1_M1_S_Mantis.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Color_N1_C1_M1/N1_C1_M1_C_Mantis.png'),

-- 9. MEDUSA
(1, '¿Qué animal representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Sombra_N1_C1_M1/N1_C1_M1_S_Medusa.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Color_N1_C1_M1/N1_C1_M1_C_Medusa.png'),

-- 10. ORNITORRINCO
(1, '¿Qué animal representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Sombra_N1_C1_M1/N1_C1_M1_S_Ornitorrinco.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Color_N1_C1_M1/N1_C1_M1_C_Ornitorrinco.png'),

-- 11. PATO
(1, '¿Qué animal representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Sombra_N1_C1_M1/N1_C1_M1_S_Pato.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Color_N1_C1_M1/N1_C1_M1_C_Pato.png'),

-- 12. PEZ LUNA
(1, '¿Qué animal representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Sombra_N1_C1_M1/N1_C1_M1_S_PezLuna.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Color_N1_C1_M1/N1_C1_M1_C_PezLuna.png'),

-- 13. PINGÜINO
(1, '¿Qué animal representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Sombra_N1_C1_M1/N1_C1_M1_S_Pinguino.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Color_N1_C1_M1/N1_C1_M1_C_Pinguino.png'),

-- 14. TIBURÓN MARTILLO
(1, '¿Qué animal representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Sombra_N1_C1_M1/N1_C1_M1_S_TiburonMartillo.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Color_N1_C1_M1/N1_C1_M1_C_TiburonMartillo.png'),

-- 15. TORTUGA MARINA
(1, '¿Qué animal representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Sombra_N1_C1_M1/N1_C1_M1_S_TortugaMarina.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Imagen_N1_C1_M1/Color_N1_C1_M1/N1_C1_M1_C_TortugaMarina.png'),

-- MINIJUEGO 1 CATEGORÍA 1 NIVEL 2

-- 16. ARCHAEOPTERYX
(2, '¿Qué animal prehistórico representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Sombra_N2_C1_M1/N2_C1_M1_S_Archaeopteryx.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Color_N2_C1_M1/N2_C1_M1_C_Archaeopteryx.png'),

-- 17. CARNOTAURUS
(2, '¿Qué animal prehistórico representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Sombra_N2_C1_M1/N2_C1_M1_S_Carnotaurus.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Color_N2_C1_M1/N2_C1_M1_C_Carnotaurus.png'),

-- 18. DIPLODOCUS
(2, '¿Qué animal prehistórico representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Sombra_N2_C1_M1/N2_C1_M1_S_Diplodocus.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Color_N2_C1_M1/N2_C1_M1_C_Diplodocus.png'),

-- 19. ICTIOSAURIOS
(2, '¿Qué animal prehistórico representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Sombra_N2_C1_M1/N2_C1_M1_S_Ictiosaurios.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Color_N2_C1_M1/N2_C1_M1_C_Ictiosaurios.png'),

-- 20. IGUANODON
(2, '¿Qué animal prehistórico representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Sombra_N2_C1_M1/N2_C1_M1_S_Iguanodon.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Color_N2_C1_M1/N2_C1_M1_C_Iguanodon.png'),

-- 21. MEGALODÓN
(2, '¿Qué animal prehistórico representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Sombra_N2_C1_M1/N2_C1_M1_S_Megalodon.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Color_N2_C1_M1/N2_C1_M1_C_Megalodon.png'),

-- 22. MOSASAURUS
(2, '¿Qué animal prehistórico representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Sombra_N2_C1_M1/N2_C1_M1_S_Mosasaurus.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Color_N2_C1_M1/N2_C1_M1_C_Mosasaurus.png'),

-- 23. PARASAUROLOPHUS
(2, '¿Qué animal prehistórico representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Sombra_N2_C1_M1/N2_C1_M1_S_Parasaurolophus.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Color_N2_C1_M1/N2_C1_M1_C_Parasaurolophus.png'),

-- 24. PLESIOSAURIO
(2, '¿Qué animal prehistórico representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Sombra_N2_C1_M1/N2_C1_M1_S_Plesiosaurio.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Color_N2_C1_M1/N2_C1_M1_C_Plesiosaurio.png'),

-- 25. PTEROSAURIO
(2, '¿Qué animal prehistórico representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Sombra_N2_C1_M1/N2_C1_M1_S_Pterosaurio.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Color_N2_C1_M1/N2_C1_M1_C_Pterosaurio.png'),

-- 26. SAUROSUCHUS
(2, '¿Qué animal prehistórico representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Sombra_N2_C1_M1/N2_C1_M1_S_Saurosuchus.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Color_N2_C1_M1/N2_C1_M1_C_Saurosuchus.png'),

-- 27. STEGOSAURUS
(2, '¿Qué animal prehistórico representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Sombra_N2_C1_M1/N2_C1_M1_S_Stegosaurus.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Color_N2_C1_M1/N2_C1_M1_C_Stegosaurus.png'),

-- 28. T-REX
(2, '¿Qué animal prehistórico representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Sombra_N2_C1_M1/N2_C1_M1_S_T-Rex.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Color_N2_C1_M1/N2_C1_M1_C_T-Rex.png'),

-- 29. TRICERAPTOP
(2, '¿Qué animal prehistórico representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Sombra_N2_C1_M1/N2_C1_M1_S_Triceraptop.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Color_N2_C1_M1/N2_C1_M1_C_Triceraptop.png'),

-- 30. VELOCIRAPTOR
(2, '¿Qué animal prehistórico representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Sombra_N2_C1_M1/N2_C1_M1_S_Velociraptor.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Imagen_N2_C1_M1/Color_N2_C1_M1/N2_C1_M1_C_Velociraptor.png'),

-- MINIJUEGO 1 CATEGORÍA 1 NIVEL 3

-- 31. ANANSI
(3, '¿Qué criatura representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Sombra_N3_C1_M1/N3_C1_M1_S_Anansi.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Color_N3_C1_M1/N3_C1_M1_C_Anansi.png'),

-- 32. CERBERO
(3, '¿Qué criatura representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Sombra_N3_C1_M1/N3_C1_M1_S_Cerbero.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Color_N3_C1_M1/N3_C1_M1_C_Cerbero.png'),

-- 33. CENTAURO
(3, '¿Qué criatura representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Sombra_N3_C1_M1/N3_C1_M1_S_Centauro.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Color_N3_C1_M1/N3_C1_M1_C_Centauro.png'),

-- 34. FÉNIX
(3, '¿Qué criatura representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Sombra_N3_C1_M1/N3_C1_M1_S_Fenix.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Color_N3_C1_M1/N3_C1_M1_C_Fenix.png'),

-- 35. GÁRGOLA
(3, '¿Qué criatura representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Sombra_N3_C1_M1/N3_C1_M1_S_Gargola.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Color_N3_C1_M1/N3_C1_M1_C_Gargola.png'),

-- 36. GRIFO
(3, '¿Qué criatura representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Sombra_N3_C1_M1/N3_C1_M1_S_Grifo.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Color_N3_C1_M1/N3_C1_M1_C_Grifo.png'),

-- 37. HYDRA
(3, '¿Qué criatura representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Sombra_N3_C1_M1/N3_C1_M1_S_Hydra.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Color_N3_C1_M1/N3_C1_M1_C_Hydra.png'),

-- 38. KRAKEN
(3, '¿Qué criatura representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Sombra_N3_C1_M1/N3_C1_M1_S_Kraken.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Color_N3_C1_M1/N3_C1_M1_C_Kraken.png'),

-- 39. LEVIATÁN
(3, '¿Qué criatura representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Sombra_N3_C1_M1/N3_C1_M1_S_Leviatan.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Color_N3_C1_M1/N3_C1_M1_C_Leviatan.png'),

-- 40. MANTÍCORA
(3, '¿Qué criatura representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Sombra_N3_C1_M1/N3_C1_M1_S_Manticora.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Color_N3_C1_M1/N3_C1_M1_C_Manticora.png'),

-- 41. QUIMERA
(3, '¿Qué criatura representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Sombra_N3_C1_M1/N3_C1_M1_S_Quimera.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Color_N3_C1_M1/N3_C1_M1_C_Quimera.png'),

-- 42. MEDUSA
(3, '¿Qué criatura representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Sombra_N3_C1_M1/N3_C1_M1_S_Medusa.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Color_N3_C1_M1/N3_C1_M1_C_Medusa.png'),

-- 43. MINOTAURO
(3, '¿Qué criatura representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Sombra_N3_C1_M1/N3_C1_M1_S_Minotauro.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Color_N3_C1_M1/N3_C1_M1_C_Minotauro.png'),

-- 44. NESSI
(3, '¿Qué criatura representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Sombra_N3_C1_M1/N3_C1_M1_S_Nessi.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Color_N3_C1_M1/N3_C1_M1_C_Nessi.png'),

-- 45. YETI
(3, '¿Qué criatura representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Sombra_N3_C1_M1/N3_C1_M1_S_Yeti.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Imagen_N3_C1_M1/Color_N3_C1_M1/N3_C1_M1_C_Yeti.png'),

-- MINIJUEGO 1 CATEGORÍA 2 NIVEL 1
-- 46. ALASKA
(4, '¿Qué país representa esta silueta?', 
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Imagen_N1_C2_M1/Sombra_N1_C2_M1/N1_C2_M1_S_Alaska.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Imagen_N1_C2_M1/Color_N1_C2_M1/N1_C2_M1_C_Alaska.png'),

-- 47. BELICE
(4, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Imagen_N1_C2_M1/Sombra_N1_C2_M1/N1_C2_M1_S_Belice.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Imagen_N1_C2_M1/Color_N1_C2_M1/N1_C2_M1_C_Belice.png'),

-- 48. CANADÁ
(4, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Imagen_N1_C2_M1/Sombra_N1_C2_M1/N1_C2_M1_S_Canada.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Imagen_N1_C2_M1/Color_N1_C2_M1/N1_C2_M1_C_Canada.png'),

-- 49. COSTA RICA
(4, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Imagen_N1_C2_M1/Sombra_N1_C2_M1/N1_C2_M1_S_CostaRica.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Imagen_N1_C2_M1/Color_N1_C2_M1/N1_C2_M1_C_CostaRica.png'),

-- 50. EL SALVADOR
(4, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Imagen_N1_C2_M1/Sombra_N1_C2_M1/N1_C2_M1_S_ElSalvador.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Imagen_N1_C2_M1/Color_N1_C2_M1/N1_C2_M1_C_ElSalvador.png'),

-- 51. ESTADOS UNIDOS
(4, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Imagen_N1_C2_M1/Sombra_N1_C2_M1/N1_C2_M1_S_EstadosUnidos.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Imagen_N1_C2_M1/Color_N1_C2_M1/N1_C2_M1_C_EstadosUnidos.png'),

-- 52. GROENLANDIA
(4, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Imagen_N1_C2_M1/Sombra_N1_C2_M1/N1_C2_M1_S_Groenlandia.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Imagen_N1_C2_M1/Color_N1_C2_M1/N1_C2_M1_C_Groenlandia.png'),

-- 53. GUATEMALA
(4, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Imagen_N1_C2_M1/Sombra_N1_C2_M1/N1_C2_M1_S_Guatemala.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Imagen_N1_C2_M1/Color_N1_C2_M1/N1_C2_M1_C_Guatemala.png'),

-- 54. HONDURAS
(4, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Imagen_N1_C2_M1/Sombra_N1_C2_M1/N1_C2_M1_S_Honduras.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Imagen_N1_C2_M1/Color_N1_C2_M1/N1_C2_M1_C_Honduras.png'),

-- 55. MÉXICO
(4, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Imagen_N1_C2_M1/Sombra_N1_C2_M1/N1_C2_M1_S_Mexico.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Imagen_N1_C2_M1/Color_N1_C2_M1/N1_C2_M1_C_Mexico.png'),

-- 56. NICARAGUA
(4, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Imagen_N1_C2_M1/Sombra_N1_C2_M1/N1_C2_M1_S_Nicaragua.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Imagen_N1_C2_M1/Color_N1_C2_M1/N1_C2_M1_C_Nicaragua.png'),

-- 57. PANAMÁ
(4, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Imagen_N1_C2_M1/Sombra_N1_C2_M1/N1_C2_M1_S_Panama.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Imagen_N1_C2_M1/Color_N1_C2_M1/N1_C2_M1_C_Panama.png'),

-- MINIJUEGO 1 CATEGORÍA 2 NIVEL 2

-- 58. ARGENTINA
(5, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Sombra_N2_C2_M1/N2_C2_M1_S_Argentina.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Color_N2_C2_M1/N2_C2_M1_C_Argentina.png'),

-- 59. ARMENIA
(5, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Sombra_N2_C2_M1/N2_C2_M1_S_Armenia.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Color_N2_C2_M1/N2_C2_M1_C_Armenia.png'),

-- 60. BOLIVIA
(5, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Sombra_N2_C2_M1/N2_C2_M1_S_Bolivia.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Color_N2_C2_M1/N2_C2_M1_C_Bolivia.png'),

-- 61. BULGARIA
(5, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Sombra_N2_C2_M1/N2_C2_M1_S_Bulgaria.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Color_N2_C2_M1/N2_C2_M1_C_Bulgaria.png'),

-- 62. CHIPRE
(5, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Sombra_N2_C2_M1/N2_C2_M1_S_Chipre.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Color_N2_C2_M1/N2_C2_M1_C_Chipre.png'),

-- 63. COLOMBIA
(5, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Sombra_N2_C2_M1/N2_C2_M1_S_Colombia.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Color_N2_C2_M1/N2_C2_M1_C_Colombia.png'),

-- 64. ECUADOR
(5, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Sombra_N2_C2_M1/N2_C2_M1_S_Ecuador.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Color_N2_C2_M1/N2_C2_M1_C_Ecuador.png'),

-- 65. ESLOVAQUIA
(5, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Sombra_N2_C2_M1/N2_C2_M1_S_Eslovaquia.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Color_N2_C2_M1/N2_C2_M1_C_Eslovaquia.png'),

-- 66. ESTONIA
(5, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Sombra_N2_C2_M1/N2_C2_M1_S_Estonia.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Color_N2_C2_M1/N2_C2_M1_C_Estonia.png'),

-- 67. MONGOLIA
(5, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Sombra_N2_C2_M1/N2_C2_M1_S_Mongolia.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Color_N2_C2_M1/N2_C2_M1_C_Mongolia.png'),

-- 68. PARAGUAY
(5, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Sombra_N2_C2_M1/N2_C2_M1_S_Paraguay.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Color_N2_C2_M1/N2_C2_M1_C_Paraguay.png'),

-- 69. PERÚ
(5, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Sombra_N2_C2_M1/N2_C2_M1_S_Peru.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Color_N2_C2_M1/N2_C2_M1_C_Peru.png'),

-- 70. POLONIA
(5, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Sombra_N2_C2_M1/N2_C2_M1_S_Polonia.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Color_N2_C2_M1/N2_C2_M1_C_Polonia.png'),

-- 71. RUMANÍA
(5, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Sombra_N2_C2_M1/N2_C2_M1_S_Rumania.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Color_N2_C2_M1/N2_C2_M1_C_Rumania.png'),

-- 72. TURQUÍA
(5, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Sombra_N2_C2_M1/N2_C2_M1_S_Turquia.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Imagen_N2_C2_M1/Color_N2_C2_M1/N2_C2_M1_C_Turquia.png'),

-- MINIJUEGO 1 CATEGORÍA 2 NIVEL 3

-- 73. CUBA
(6, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Sombra_N3_C2_M1/N3_C2_M1_S_Cuba.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Color_N3_C2_M1/N3_C2_M1_C_Cuba.png'),

-- 74. CURACAO
(6, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Sombra_N3_C2_M1/N3_C2_M1_S_Curacao.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Color_N3_C2_M1/N3_C2_M1_C_Curacao.png'),

-- 75. EGIPTO
(6, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Sombra_N3_C2_M1/N3_C2_M1_S_Egipto.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Color_N3_C2_M1/N3_C2_M1_C_Egipto.png'),

-- 76. ETIOPÍA
(6, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Sombra_N3_C2_M1/N3_C2_M1_S_Etiopia.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Color_N3_C2_M1/N3_C2_M1_C_Etiopia.png'),

-- 77. FIJI
(6, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Sombra_N3_C2_M1/N3_C2_M1_S_Fiji.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Color_N3_C2_M1/N3_C2_M1_C_Fiji.png'),

-- 78. MARSHALL
(6, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Sombra_N3_C2_M1/N3_C2_M1_S_Marshall.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Color_N3_C2_M1/N3_C2_M1_C_Marshall.png'),

-- 79. MALAWI
(6, '¿Qué país representa esta silueta?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Sombra_N3_C2_M1/N3_C2_M1_S_Malawi.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Color_N3_C2_M1/N3_C2_M1_C_Malawi.png'),

-- 80. MALI
(6, '¿Qué país representa esta silueta?', 
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Sombra_N3_C2_M1/N3_C2_M1_S_Mali.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Color_N3_C2_M1/N3_C2_M1_C_Mali.png'),

-- 81. NUEVA ZELANDA
(6, '¿Qué país representa esta silueta?', 
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Sombra_N3_C2_M1/N3_C2_M1_S_NuevaZelanda.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Color_N3_C2_M1/N3_C2_M1_C_NuevaZelanda.png'),

-- 82. PAPÚA NUEVA GUINEA
(6, '¿Qué país representa esta silueta?', 
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Sombra_N3_C2_M1/N3_C2_M1_S_PapuaNuevaGuinea.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Color_N3_C2_M1/N3_C2_M1_C_PapuaNuevaGuinea.png'),

-- 83. REPÚBLICA DOMINICANA
(6, '¿Qué país representa esta silueta?', 
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Sombra_N3_C2_M1/N3_C2_M1_S_RepDominicana.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Color_N3_C2_M1/N3_C2_M1_C_RepDominicana.png'),

-- 84. SAMOA
(6, '¿Qué país representa esta silueta?', 
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Sombra_N3_C2_M1/N3_C2_M1_S_Samoa.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Color_N3_C2_M1/N3_C2_M1_C_Samoa.png'),

-- 85. SAN CRISTÓBAL Y NIEVES
(6, '¿Qué país representa esta silueta?', 
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Sombra_N3_C2_M1/N3_C2_M1_S_SanCristobalNieves.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Color_N3_C2_M1/N3_C2_M1_C_SanCristobalNieves.png'),

-- 86. TRINIDAD Y TOBAGO
(6, '¿Qué país representa esta silueta?', 
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Sombra_N3_C2_M1/N3_C2_M1_S_TrinidadTobago.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Color_N3_C2_M1/N3_C2_M1_C_TrinidadTobago.png'),

-- 87. ZAMBIA
(6, '¿Qué país representa esta silueta?', 
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Sombra_N3_C2_M1/N3_C2_M1_S_Zambia.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Imagen_N3_C2_M1/Color_N3_C2_M1/N3_C2_M1_C_Zambia.png'),

-- MINIJUEGO 1 CATEGORÍA 3 NIVEL 1
-- 88. ANAIS
(7, '¿Qué personaje representa esta sombra?', 
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Sombra_N1_C3_M1/N1_C3_M1_S_Anais.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Color_N1_C3_M1/N1_C3_M1_C_Anais.png'),

-- 89. AUSTIN
(7, '¿Qué personaje representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Sombra_N1_C3_M1/N1_C3_M1_S_Austin.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Color_N1_C3_M1/N1_C3_M1_C_Austin.png'),

-- 90. BELLOTA
(7, '¿Qué personaje representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Sombra_N1_C3_M1/N1_C3_M1_S_Bellota.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Color_N1_C3_M1/N1_C3_M1_C_Bellota.png'),

-- 91. BOBSPONJA
(7, '¿Qué personaje representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Sombra_N1_C3_M1/N1_C3_M1_S_BobSponja.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Color_N1_C3_M1/N1_C3_M1_C_BobSponja.png'),

-- 92. BURBUJA
(7, '¿Qué personaje representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Sombra_N1_C3_M1/N1_C3_M1_S_Burbuja.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Color_N1_C3_M1/N1_C3_M1_C_Burbuja.png'),

-- 93. DARWIN
(7, '¿Qué personaje representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Sombra_N1_C3_M1/N1_C3_M1_S_Darwin.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Color_N1_C3_M1/N1_C3_M1_C_Darwin.png'),

-- 94. DON CANGREJO
(7, '¿Qué personaje representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Sombra_N1_C3_M1/N1_C3_M1_S_Don_cangrejo.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Color_N1_C3_M1/N1_C3_M1_C_Don_cangrejo.png'),

-- 95. GUMBALL
(7, '¿Qué personaje representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Sombra_N1_C3_M1/N1_C3_M1_S_Gumball.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Color_N1_C3_M1/N1_C3_M1_C_Gumball.png'),

-- 96. JERRY
(7, '¿Qué personaje representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Sombra_N1_C3_M1/N1_C3_M1_S_Jerry.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Color_N1_C3_M1/N1_C3_M1_C_Jerry.png'),

-- 97. PABLO
(7, '¿Qué personaje representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Sombra_N1_C3_M1/N1_C3_M1_S_Pablo.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Color_N1_C3_M1/N1_C3_M1_C_Pablo.png'),

-- 98. PATRICIO
(7, '¿Qué personaje representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Sombra_N1_C3_M1/N1_C3_M1_S_Patricio.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Color_N1_C3_M1/N1_C3_M1_C_Patricio.png'),

-- 99. RICHARD WATTERSON
(7, '¿Qué personaje representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Sombra_N1_C3_M1/N1_C3_M1_S_RichardWatterson.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Color_N1_C3_M1/N1_C3_M1_C_RichardWatterson.png'),

-- 100. TASHA
(7, '¿Qué personaje representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Sombra_N1_C3_M1/N1_C3_M1_S_Tasha.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Color_N1_C3_M1/N1_C3_M1_C_Tasha.png'),

-- 101. TOM
(7, '¿Qué personaje representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Sombra_N1_C3_M1/N1_C3_M1_S_Tom.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Color_N1_C3_M1/N1_C3_M1_C_Tom.png'),

-- 102. TYRONE
(7, '¿Qué personaje representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Sombra_N1_C3_M1/N1_C3_M1_S_Tyrone.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Imagen_N1_C3_M1/Color_N1_C3_M1/N1_C3_M1_C_Tyrone.png'),

-- MINIJUEGO 1 CATEGORIA 3 NIVEL 2
-- 103. DAPHNE
(8, '¿Qué personaje representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Sombra_N2_C3_M1/N2_C3_M1_S_Daphne.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Color_N2_C3_M1/N2_C3_M1_C_Daphne.png'),

-- 104. DIPPER
(8, '¿Qué personaje representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Sombra_N2_C3_M1/N2_C3_M1_S_Dipper.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Color_N2_C3_M1/N2_C3_M1_C_Dipper.png'),

-- 105. GARNET
(8, '¿Qué personaje representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Sombra_N2_C3_M1/N2_C3_M1_S_Garnet.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Color_N2_C3_M1/N2_C3_M1_C_Garnet.png'),

-- 106. GUNTER
(8, '¿Qué personaje representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Sombra_N2_C3_M1/N2_C3_M1_S_Gunter.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Color_N2_C3_M1/N2_C3_M1_C_Gunter.png'),

-- 107. JAKE
(8, '¿Qué personaje representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Sombra_N2_C3_M1/N2_C3_M1_S_Jake.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Color_N2_C3_M1/N2_C3_M1_C_Jake.png'),

-- 108. MABEL GRAVITY FALLS
(8, '¿Qué personaje representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Sombra_N2_C3_M1/N2_C3_M1_S_MabelGravity.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Color_N2_C3_M1/N2_C3_M1_C_MabelGravity.png'),

-- 109. PATO GRAVITY FALLS
(8, '¿Qué personaje representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Sombra_N2_C3_M1/N2_C3_M1_S_PatoGravity.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Color_N2_C3_M1/N2_C3_M1_C_PatoGravity.png'),

-- 110. PERRY ORNITORRINCO
(8, '¿Qué personaje representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Sombra_N2_C3_M1/N2_C3_M1_S_PerryOrnitorrinco.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Color_N2_C3_M1/N2_C3_M1_C_PerryOrnitorrinco.png'),

-- 111. PINKIE PIE
(8, '¿Qué personaje representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Sombra_N2_C3_M1/N2_C3_M1_S_PinkiePie.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Color_N2_C3_M1/N2_C3_M1_C_PinkiePie.png'),

-- 112. RAINBOW DASH
(8, '¿Qué personaje representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Sombra_N2_C3_M1/N2_C3_M1_S_RainbowDash.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Color_N2_C3_M1/N2_C3_M1_C_RainbowDash.png'),

-- 113. SCOOBY DOO
(8, '¿Qué personaje representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Sombra_N2_C3_M1/N2_C3_M1_S_Scooby.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Color_N2_C3_M1/N2_C3_M1_C_Scooby.png'),

-- 114. SOOS GRAVITY
(8, '¿Qué personaje representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Sombra_N2_C3_M1/N2_C3_M1_S_SoosGravity.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Color_N2_C3_M1/N2_C3_M1_C_SoosGravity.png'),

-- 115. STEVEN UNIVERSE
(8, '¿Qué personaje representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Sombra_N2_C3_M1/N2_C3_M1_S_StevenUniverse.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Color_N2_C3_M1/N2_C3_M1_C_StevenUniverse.png'),

-- 116. TIO STAN
(8, '¿Qué personaje representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Sombra_N2_C3_M1/N2_C3_M1_S_TioStan.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Color_N2_C3_M1/N2_C3_M1_C_TioStan.png'),

-- 117. TWILIGHT SPARKLE
(8, '¿Qué personaje representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Sombra_N2_C3_M1/N2_C3_M1_S_TwilightSparkle.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Imagen_N2_C3_M1/Color_N2_C3_M1/N2_C3_M1_C_TwilightSparkle.png'),

-- MINIJUEGO 1 CATEGORIA 3 NIVEL 3
-- 118. BULBASAUR
(9, '¿Qué Pokémon representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Sombra_N3_C3_M1/N3_C3_M1_S_Bulbasaur.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Color_N3_C3_M1/N3_C3_M1_C_Bulbasaur.png'),

-- 119. CHIKORITA
(9, '¿Qué Pokémon representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Sombra_N3_C3_M1/N3_C3_M1_S_Chikorita.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Color_N3_C3_M1/N3_C3_M1_C_Chikorita.png'),

-- 120. EEVEE
(9, '¿Qué Pokémon representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Sombra_N3_C3_M1/N3_C3_M1_S_Eevee.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Color_N3_C3_M1/N3_C3_M1_C_Eevee.png'),

-- 121. GENGAR
(9, '¿Qué Pokémon representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Sombra_N3_C3_M1/N3_C3_M1_S_Gengar.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Color_N3_C3_M1/N3_C3_M1_C_Gengar.png'),

-- 122. GRENINJA
(9, '¿Qué Pokémon representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Sombra_N3_C3_M1/N3_C3_M1_S_Greninja.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Color_N3_C3_M1/N3_C3_M1_C_Greninja.png'),

-- 123. LATIOS
(9, '¿Qué Pokémon representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Sombra_N3_C3_M1/N3_C3_M1_S_Latios.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Color_N3_C3_M1/N3_C3_M1_C_Latios.png'),

-- 124. MEWTWO
(9, '¿Qué Pokémon representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Sombra_N3_C3_M1/N3_C3_M1_S_Mewtwo.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Color_N3_C3_M1/N3_C3_M1_C_Mewtwo.png'),

-- 125. MOLTRES
(9, '¿Qué Pokémon representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Sombra_N3_C3_M1/N3_C3_M1_S_Moltres.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Color_N3_C3_M1/N3_C3_M1_C_Moltres.png'),

-- 126. PIKACHU
(9, '¿Qué Pokémon representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Sombra_N3_C3_M1/N3_C3_M1_S_Pikachu.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Color_N3_C3_M1/N3_C3_M1_C_Pikachu.png'),

-- 127. RALTS
(9, '¿Qué Pokémon representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Sombra_N3_C3_M1/N3_C3_M1_S_Ralts.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Color_N3_C3_M1/N3_C3_M1_C_Ralts.png'),

-- 128. SKAMORY
(9, '¿Qué Pokémon representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Sombra_N3_C3_M1/N3_C3_M1_S_Skamory.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Color_N3_C3_M1/N3_C3_M1_C_Skamory.png'),

-- 129. SLAKING
(9, '¿Qué Pokémon representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Sombra_N3_C3_M1/N3_C3_M1_S_Slaking.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Color_N3_C3_M1/N3_C3_M1_C_Slaking.png'),

-- 130. SNORLAX
(9, '¿Qué Pokémon representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Sombra_N3_C3_M1/N3_C3_M1_S_Snorlax.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Color_N3_C3_M1/N3_C3_M1_C_Snorlax.png'),

-- 131. SPRIGATITO
(9, '¿Qué Pokémon representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Sombra_N3_C3_M1/N3_C3_M1_S_Sprigatito.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Color_N3_C3_M1/N3_C3_M1_C_Sprigatito.png'),

-- 132. SQUIRTLE
(9, '¿Qué Pokémon representa esta sombra?',
-- Sombra
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Sombra_N3_C3_M1/N3_C3_M1_S_Squirtle.png',
-- Color
'/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Imagen_N3_C3_M1/Color_N3_C3_M1/N3_C3_M1_C_Squirtle.png');



INSERT INTO Opcion_respuesta(id_pregunta, texto_opcion, es_correcta) VALUES
-- MINIJUEGO 1 CATEGORÍA 1 NIVEL 1
-- BUHO NIVAL
(1, 'Búho Nival', true),
(1, 'Pelícano', false),
(1, 'Koala', false),
(1, 'Gato', false),

-- CANGREJO
(2, 'Cangrejo', true),
(2, 'Tortuga', false),
(2, 'Escorpión', false),
(2, 'Escarabajo', false),

-- CAPIBARA
(3, 'Capibara', true),
(3, 'Jabalí', false),
(3, 'Wombat', false),
(3, 'Castor', false),

-- COBRA
(4, 'Cobra', true),
(4, 'Suricata',false),
(4, 'Víbora', false),
(4, 'Anguila', false),

-- COCODRILO
(5, 'Cocodrilo', true),
(5, 'Lagartija',false),
(5, 'Salamandra', false),
(5, 'Bagre', false),

-- COLIBRÍ
(6, 'Colibrí', true),
(6, 'Camarón',false),
(6, 'Libélula', false),
(6, 'Pez Aguja', false),

-- LECHUZA
(7, 'Lechuza', true),
(7, 'Ardilla Voladora',false),
(7, 'Paloma Bravía', false),
(7, 'Oso Perezoso', false),

-- MANTIS
(8, 'Mantis', true),
(8, 'Insecto Palo',false),
(8, 'Saltamontes', false),
(8, 'Cigarra', false),

-- MEDUSA
(9, 'Medusa', true),
(9, 'Pulpo',false),
(9, 'Calamar', false),
(9, 'Anémona de Mar', false),

-- ORNITORRINCO
(10, 'Ornitorrinco', true),
(10, 'Castor',false),
(10, 'Pato Joyuyo', false),
(10, 'Nutria Marina', false),

-- PATO
(11, 'Pato', true),
(11, 'Gaviota',false),
(11, 'Paloma Torcaz', false),
(11, 'Ornitorrinco', false),

-- PEZ LUNA
(12, 'Pez Luna', true),
(12, 'Tiburón Blanco',false),
(12, 'Mantarraya', false),
(12, 'Lenguado', false),

-- PINGÜINO
(13, 'Pingüino', true),
(13, 'Foca',false),
(13, 'Suricata', false),
(13, 'Alca Gigante', false),

-- TIBURÓN MARTILLO
(14, 'Tiburón Martillo', true),
(14, 'Pez Espalda',false),
(14, 'Rémora', false),
(14, 'Calamar Gigante', false),

-- TORTUGA MARINA
(15, 'Tortuga Marina', true),
(15, 'Cangrejo Cacerola',false),
(15, 'Escarabajo Pelotero', false),
(15, 'Cangrejo Nadador', false),

-- MINIJUEGO 1 CATEGORÍA 1 NIVEL 2
-- ARCHAEOPTERYX
(16, 'Archaeopteryx', true),
(16, 'Yi Qi', false),
(16, 'Anchiornis', false),
(16, 'Confuciusornis', false),

-- CARNOTAURUS
(17, 'Carnotaurus', true),
(17, 'Majungasaurus', false),
(17, 'Rajasaurus', false),
(17, 'Aucasaurus', false),

-- DIPLODOCUS
(18, 'Diplodocus', true),
(18, 'Apatosaurus', false),
(18, 'Barosaurus', false),
(18, 'Mamenchisaurus', false),

-- ICTIOSAURIOS
(19, 'Ictiosaurios', true),
(19, 'Metriorhynchus', false),
(19, 'Clidastes', false),
(19, 'Mixosaurus', false),

-- IGUANODON
(20, 'Iguanodon', true),
(20, 'Ouranosaurus', false),
(20, 'Hadrosaurus', false),
(20, 'Tenontosaurus', false),

-- MEGALODON
(21, 'Megalodón', true),
(21, 'Cretoxyrhina', false),
(21, 'Otodus', false),
(21, 'Cardabiodon', false),

-- MOSASAURUS
(22, 'Mosasaurus', true),
(22, 'Tylosaurus', false),
(22, 'Halisaurus', false),
(22, 'Dakosaurus', false),

-- PARASAUROLOPHUS
(23, 'Parasaurolophus', true),
(23, 'Lambeosaurus', false),
(23, 'Charonosaurus', false),
(23, 'Olorotitan', false),

-- PLESIOSAURIO
(24, 'Plesiosaurio', true),
(24, 'Elasmosaurus', false),
(24, 'Cryptoclidus', false),
(24, 'Pistosaurus', false),

-- PTEROSAURIO
(25, 'Pterosaurio', true),
(25, 'Pteranodon', false),
(25, 'Dimorphodon', false),
(25, 'Quetzalcoatlus', false),

-- SAUROSUCHUS
(26, 'Saurosuchus', true),
(26, 'Postosuchus', false),
(26, 'Prestosuchus', false),
(26, 'Fasolasuchus', false),

-- STEGOSAURUS
(27, 'Stegosaurus', true),
(27, 'Kentrosaurus', false),
(27, 'Wuerhosaurus', false),
(27, 'Miragaia', false),

-- T-Rex
(28, 'T-Rex', true),
(28, 'Tarbosaurus', false),
(28, 'Daspletosaurus', false),
(28, 'Gorgosaurus', false),

-- TRICERATOP
(29, 'Triceratop', true),
(29, 'Centrosaurus', false),
(29, 'Einiosaurus', false),
(29, 'Pentaceratops', false),

-- VELOCIRAPTOR
(30, 'Velociraptor', true),
(30, 'Deinonychus', false),
(30, 'Utahraptor', false),
(30, 'Austroraptor', false),

-- MINIJUEGO 1 CATEGORIA 1 NIVEL 3
-- ANANSI
(31, 'Anansi', true),
(31, 'Iktomi', false),
(31, 'Loki', false),
(31, 'Jorōgumo', false),

-- CERBERO
(32, 'Cerbero', true),
(32, 'Garm', false),
(32, 'Anubis', false),
(32, 'Xolotl', false),

-- CENTAURO
(33, 'Centauro', true),
(33, 'Ipótane', false),
(33, 'Kinnara', false),
(33, 'Tikbalang', false),

-- FÉNIX
(34, 'Fénix', true),
(34, 'Bennu', false),
(34, 'Simurgh', false),
(34, 'Fenghuang', false),

-- GÁRGOLA
(35, 'Gárgola', true),
(35, 'Gólem', false),
(35, 'Gorgona', false),
(35, 'Pixiu', false),

-- GRIFO
(36, 'Grifo', true),
(36, 'Anzu', false),
(36, 'Peryton', false),
(36, 'Pégaso', false),

-- HYDRA
(37, 'Hydra', true),
(37, 'Ladon', false),
(37, 'Kaliya', false),
(37, 'Caronte', false),

-- KRAKEN
(38, 'Kraken', true),
(38, 'Cetus', false),
(38, 'Umibōzu', false),
(38, 'Akkorokamui', false),

-- LEVIATÁN
(39, 'Leviatán', true),
(39, 'Vritra', false),
(39, 'Rahab', false),
(39, 'Tiamat', false),

-- MANTÍCORA
(40, 'Mantícora', true),
(40, 'Esfinge', false),
(40, 'Gugalanna', false),
(40, 'Kamadhenu', false),

-- QUIMERA
(41, 'Quimera', true),
(41, 'Nue', false),
(41, 'Ammit', false),
(41, 'Pazuzu', false),

-- MEDUSA
(42, 'Medusa', true),
(42, 'Basilisco', false),
(42, 'Coccatrice', false),
(42, 'Lamia', false),

-- MINOTAURO
(43, 'Minotauro', true),
(43, 'Cernunnos', false),
(43, 'Bafomet', false),
(43, 'Sátiro', false),

-- NESSI
(44, 'Nessi', true),
(44, 'Champ', false),
(44, 'Ogopogo', false),
(44, 'Nahuelito', false),

-- YETI
(45, 'Yeti', true),
(45, 'Sasquatch', false),
(45, 'Yowie', false),
(45, 'Maricoxi', false),

-- MINIJUEGO 1 CATEGORÍA 2 NIVEL 1
-- ALASKA
(46, 'Alaska', true),
(46, 'Camerún', false),
(46, 'Francia', false),
(46, 'Mozambique', false),

-- BELICE
(47, 'Belice', true),
(47, 'Líbano', false),
(47, 'Túnez', false),
(47, 'Portugal', false),

-- CANADÁ 
(48, 'Canadá ', true),
(48, 'Filipinas', false),
(48, 'Grecia', false),
(48, 'Noruega', false),

-- COSTA RICA
(49, 'Costa Rica', true),
(49, 'Armenia', false),
(49, 'Bulgaria', false),
(49, 'Ecuador' , false),

-- EL SALVADOR 
(50, 'El Salvador', true),
(50, 'Bélgica', false),
(50, 'Suiza', false),
(50, 'Uruguay', false),

-- ESTADOS UNIDOS 
(51, 'Estados Unidos ', true),
(51, 'Australia', false),
(51, 'China' , false),
(51, 'Irán', false),

-- GROENLANDIA 
(52, 'Groenlandia ', true),
(52, 'Armenia', false),
(52, 'India', false),
(52, 'Madagascar', false),

-- GUATEMALA 
(53, 'Guatemala', true),
(53, 'Francia', false),
(53, 'Polonia', false),
(53, 'Zimbabue', false),

-- HONDURAS 
(54, 'Honduras', true),
(54, 'Angola', false),
(54, 'Camboya', false),
(54, 'Rumania', false),

-- MÉXICO 
(55, 'México', true),
(55, 'Vietnam', false),
(55, 'Myanmar', false),
(55, 'Somalia', false),

-- NICARAGUA 
(56, 'Nicaragua', true),
(56, 'Austria', false),
(56, 'Irlanda', false),
(56, 'Islandia', false),

-- PANAMÁ 
(57, 'Panamá', true),
(57, 'Cuba', false),
(57, 'Gambia', false),
(57, 'Indonesia', false),

-- MINIJUEGO 1 CATEGORÍA 2 NIVEL 2
-- ARGENTINA
(58, 'Argentina', true),
(58, 'India', false),
(58, 'Madagascar', false),
(58, 'Somalia', false),

-- ARMENIA
(59, 'Armenia', true),
(59, 'Costa Rica', false),
(59, 'Eitrea', false),
(59, 'Lesoto', false),

-- BOLIVIA
(60, 'Bolivia', true),
(60, 'Irán', false),
(60, 'Sudán', false),
(60, 'Tanzania', false),

-- BULGARIA
(61, 'Bulgaria', true),
(61, 'Costa Rica', false),
(61, 'Panamá', false),
(61, 'Sierra Leona', false),

-- CHIPRE 
(62, 'Chipre', true),
(62, 'Cuba', false),
(62, 'Etiopía', false),
(62, 'Puerto Rico', false),
-- COLOMBIA
(63, 'Colombia', true),
(63, 'Angola', false),
(63, 'Francia', false),
(63, 'Venezuela', false),

-- ECUADOR
(64, 'Ecuador', true),
(64, 'Gales', false),
(64, 'Kenia', false),
(64, 'Rumania', false),

-- ESLOVAQUIA
(65, 'Eslovaquia', true),
(65, 'Gambia', false),
(65, 'Panamá', false),
(65, 'Turquía', false),

-- ESTONIA
(66, 'Estonia', true),
(66, 'Irlanda', false),
(66, 'Islandia', false),
(66, 'Sri Lanka', false),

-- MONGOLIA
(67, 'Mongolia', true),
(67, 'Marruecos', false),
(67, 'Turquía', false),
(67, 'Zambia', false),

-- PARAGUAY
(68, 'Paraguay', true),
(68, 'Botsuana', false),
(68, 'Madagascar', false),
(68, 'Polonia', false),

-- PERÚ
(69, 'Perú', true),
(69, 'Argelia', false),
(69, 'Malí', false),
(69, 'Tailandia', false),

-- POLONIA
(70, 'Polonia', true),
(70, 'Francia', false),
(70, 'Surinam', false),
(70, 'Camboya', false),

-- RUMANIA
(71, 'Rumania', true),
(71, 'Ecuador', false),
(71, 'Lesoto', false),
(71, 'Honduras', false),

-- TURQUÍA
(72, 'Turquía', true),
(72, 'Eslovaquia', false),
(72, 'Gambia', false),
(72, 'Mongolia', false),

-- MINIJUEGO 1 CATEGORIA 2 NIVEL 3
-- CUBA
(73, 'Cuba', true),
(73, 'Islas Salomón', false),
(73, 'Japón', false),
(73, 'Indonesia', false),

-- CURAZAO
(74, 'Curazao', true),
(74, 'Anguila', false),
(74, 'Togo', false),
(74, 'Benín', false),

-- EGIPTO
(75, 'Egipto', true),
(75, 'Libia', false),
(75, 'Mauritania', false),
(75, 'Sudán', false),

-- ETIOPÍA
(76, 'Etiopía', true),
(76, 'Afganistán', false),
(76, 'Kenia', false),
(76, 'Nigeria', false),

-- FIJI
(77, 'Fiji', true),
(77, 'Croacia', false),
(77, 'Grecia', false),
(77, 'Omán', false),

-- ISLAS MARSHALL
(78, 'Islas Marshall', true),
(78, 'Laos', false),
(78, 'Bahamas', false),
(78, 'Filipinas', false),

-- MALAWI
(79, 'Malawi', true),
(79, 'Israel', false),
(79, 'Togo', false),
(79, 'Uzbekistán', false),

-- MALI
(80, 'Mali', true),
(80, 'Argelia', false),
(80, 'Mauritania', false),
(80, 'Pakistán', false),

-- NUEVA ZELANDA
(81, 'Nueva Zelanda', true),
(81, 'Japón', false),
(81, 'Reino Unido', false),
(81, 'Filipinas', false),

-- PAPÚA NUEVA GUINEA
(82, 'Papúa Nueva Guinea', true),
(82, 'Tailandia', false),
(82, 'Honduras', false),
(82, 'Eritrea', false),

-- REPÚBLICA DOMINICANA 
(83, 'República Dominicana', true),
(83, 'Bulgaria', false),
(83, 'Letonia', false),
(83, 'Costa de Marfil', false),

-- SAMOA
(84, 'Samoa', true),
(84, 'Antigua y Barbuda', false),
(84, 'Cabo Verde', false),
(84, 'Trinidad y Tobago', false),

-- SAN CRISTÓBAL Y NIEVES
(85, 'San Cristóbal y Nieves', true),
(85, 'Samoa', false),
(85, 'Trinidad y Tobago', false),
(85, 'Antigua y Barbuda', false),

-- TRINIDAD Y TOBAGO
(86, 'Trinidad y Tobago', true),
(86, 'Malasia', false),
(86, 'Omán', false),
(86, 'Guinea Ecuatorial', false),

-- Zambia
(87, 'Zambia', true),
(87, 'Irán', false),
(87, 'Austria', false),
(87, 'Mozambique', false),

-- MINIJUEGO 1 CATEGORÍA 3 NIVEL 1
-- ANAIS
(88, 'Anais', true),
(88, 'Bugs Bunny', false),
(88, 'Panini', false),
(88, 'My Melody', false),

-- AUSTIN
(89, 'Austin', true),
(89, 'Rocko', false),
(89, 'Kojiro', false),
(89, 'Buster Bunny', false),

-- BELLOTA
(90, 'Bellota', true),
(90, 'Margo', false),
(90, 'Yumi', false),
(90, 'Dora', false),

-- BOB ESPONJA
(91, 'Bob Esponja', true),
(91, 'Bloo', false),
(91, 'Bloody Bunny', false),
(91, 'Wall-E', false),

-- BURBUJA
(92, 'Burbuja', true),
(92, 'Toadette', false),
(92, 'Boo', false),
(92, 'Agnes', false),

-- DARWIN
(93, 'Darwin', true),
(93, 'Nemo', false),
(93, 'Flounder', false),
(93, 'Cleo', false),

-- DON CANGREJO
(94, 'Don Cangrejo', true),
(94, 'Señor Langosta', false),
(94, 'Tomatoa', false),
(94, 'Krabby', false),

-- GUMBALL
(95, 'Gumball', true),
(95, 'Oggy', false),
(95, 'Meowith', false),
(95, 'Doraemon', false),

-- JERRY
(96, 'Jerry', true),
(96, 'Mickey Mouse', false),
(96, 'Stuart little', false),
(96, 'Pixie', false),

-- PABLO
(97, 'Pablo', true),
(97, 'Piolín', false),
(97, 'Skipper', false),
(97, 'Pocoyó', false),

-- PATRICIO
(98, 'Patricio', true),
(98, 'Majin Buu', false),
(98, 'Patrick', false),
(98, 'Gorg', false),

-- RICHARD WATTERSON
(99, 'Richard Watterson', true),
(99, 'Max', false),
(99, 'Totoro', false),
(99, 'Baymax', false),

-- TASHA
(100, 'Tasha', true),
(100, 'Gloria', false),
(100, 'Muriel', false),
(100, 'Mamá de Dexter', false),

-- TOM
(101, 'Tom', true),
(101, 'Silvestre', false),
(101, 'Garfio', false),
(101, 'Estimpy', false),

-- TYRONE
(102, 'Tyrone', true),
(102, 'Elliot', false),
(102, 'Lumpy', false),
(102, 'Bambi', false),

-- MINIJUEGO 1 CATEGORÍA 3 NIVEL 2
-- DAPHNE 
(103, 'Daphne', true),
(103, 'Gwen', false),
(103, 'Kim Possible', false),
(103, 'Vicky', false),

-- DIPPER
(104, 'Dipper', true),
(104, 'Phineas', false),
(104, 'Chowder', false),
(104, 'Puffin', false),

-- GARNET
(105, 'Garnet', true),
(105, 'Marge Simpson', false),
(105, 'Mojo Mojo', false),
(105, 'Jasper', false),

-- GUNTER 
(106, 'Gunter', true),
(106, 'Chilly Willy', false),
(106, 'Wheezy', false),
(106, 'Wokalski', false),

-- JAKE
(107, 'Jake', true),
(107, 'Pluto', false),
(107, 'Catdog', false),
(107, 'Droopy', false),

-- MABEL GRAVITY
(108, 'Mabel', true),
(108, 'Lilo', false),
(108, 'Suzy', false),
(108, 'Star Butterfly', false),

-- PATO GRAVITY
(109, 'Pato', true),
(109, 'Waddles', false),
(109, 'Piglet', false),
(109, 'Spider-ham', false),

-- PERRY EL ORNITORRINCO
(110, 'Perry', true),
(110, 'Pato Lucas', false),
(110, 'Psyduck', false),
(110, 'Waddles El Pato', false),

-- PINKIE PIE
(111, 'Pinkie Pie', true),
(111, 'Yask', false),
(111, 'Maximus', false),
(111, 'Swift Wind', false),

-- RAINBOW DASH
(112, 'Rainbow Dash', true),
(112, 'Pegaso', false),
(112, 'Fluttershy', false),
(112, 'Altaria', false),

-- SCOOBY DOO
(113, 'Scooby Doo', true),
(113, 'Astro', false),
(113, 'Pluto', false),
(113, 'Marmaduke', false),

-- SOOS GRAVITY
(114, 'Soos', true),
(114, 'Patricio', false),
(114, 'Homero Simpson', false),
(114, 'Shrek', false),

-- STEVEN UNIVERSE
(115, 'Steven Universe', true),
(115, 'Russel', false),
(115, 'Clarence', false),
(115, 'Dexter', false),

-- TIO STAN
(116, 'Tío Stan', true),
(116, 'Gargamel', false),
(116, 'Eustace', false),
(116, 'El Grinch', false),

-- TWILIGHT SPARKLE
(117, 'Twilight Sparkle', true),
(117, 'Raryty', false),
(117, 'Keldeo', false),
(117, 'Applejack', false),

-- MINIJUEGO 1 CATEGORÍA 3 NIVEL 3
-- Bulbasaur
(118, 'Bulbasaur', true),
(118, 'Ivasaur', false),
(118, 'Turtwig', false),
(118, 'Nidoran', false),

-- CHIKORITA
(119, 'Chikorita', true),
(119, 'Snivy', false),
(119, 'Oddish', false),
(119, 'Sunkern', false),

-- EEVEE
(120, 'Eevee', true),
(120, 'Jolteon', false),
(120, 'Vulpix', false),
(120, 'Nickit', false),

-- GENGAR
(121, 'Gengar', true),
(121, 'Spiritomb', false),
(121, 'Mimikyu', false),
(121, 'Haunter', false),

-- GRENINJA
(122, 'Greninja', true),
(122, 'Frogadier', false),
(122, 'Inteleon', false),
(122, 'Zeraora', false),

-- LATIOS
(123, 'Latios', true),
(123, 'Latias', false),
(123, 'Mega Latios', false),
(123, 'Mega Latias', false),

-- MEWTWO
(124, 'Mewtwo', true),
(124, 'Mew', false),
(124, 'Lucario', false),
(124, 'Gallade', false),

-- MOLTRES
(125, 'Moltres', true),
(125, 'Ho-Oh', false),
(125, 'Talonflame', false),
(125, 'Lugia', false),

-- PIKACHU
(126, 'Pikachu', true),
(126, 'Raichu', false),
(126, 'Dedenne', false),
(126, 'Pawmot', false),

-- RALTS
(127, 'Ralts', true),
(127, 'Kirlia', false),
(127, 'Hatenna', false),
(127, 'Espurr', false),

-- SKAMORY
(128, 'Skamory', true),
(128, 'Fearow', false),
(128, 'Pidgeot', false),
(128, 'Aerodactyl', false),

-- SLAKING
(129, 'Slaking', true),
(129, 'Vigoroth', false),
(129, 'Oranguru', false),
(129, 'Pangoro', false),

-- SNORLAX
(130, 'Snorlax', true),
(130, 'Munchlax', false),
(130, 'Ursaluna', false),
(130, 'Iron Hands', false),

-- SPRIGATITO
(131, 'Sprigatito', true),
(131, 'Floragato', false),
(131, 'Glameow', false),
(131, 'Litten', false),

-- SQUIRTLE 
(132, 'Squirtle', true),
(132, 'Mudkip', false),
(132, 'Sobble', false),
(132, 'Gible', false);


-- -----------------------------------------

INSERT INTO Ayuda(id_pregunta, tipo, contenido, audio) VALUES
-- Ingreso de pistas textuales del Minijuego 1
-- MINIJUEGO 1 CATEGORÍA 1 NIVEL 1 
-- BÚHO NIVAL
(1, 'texto', 'Habita regiones donde la nieve cubre el paisaje durante gran parte del año. Su plumaje le permite mezclarse fácilmente con el entorno.', ''),
(1, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Pistas_N1_C1_M1/N1_C1_M1_BuhoNival.wav'),

-- CANGREJO
(2, 'texto', 'Su cuerpo está protegido por un caparazón duro que cambia a medida que crece. Durante ese proceso se vuelve más vulnerable y busca lugares seguros para esconderse.', ''),
(2, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Pistas_N1_C1_M1/N1_C1_M1_Cangrejo.wav'),

-- CAPIBARA
(3, 'texto', 'Es un animal muy tranquilo que suele convivir en grupos numerosos. La vida en comunidad le ayuda a detectar posibles peligros.', ''),
(3, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Pistas_N1_C1_M1/N1_C1_M1_Capibara.wav'),

-- COBRA
(4, 'texto', 'Aunque suele evitar a los seres humanos, puede defenderse si se siente amenazada. Antes de atacar, adopta una postura muy característica para advertir del peligro.', ''),
(4, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Pistas_N1_C1_M1/N1_C1_M1_Cobra.wav'),

-- COCODRILO
(5, 'texto', 'Posee una mandíbula extremadamente poderosa y una piel cubierta de gruesas escamas. Sus ojos y fosas nasales están ubicados de manera que puede vigilar sin salir completamente del agua.', ''),
(5, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Pistas_N1_C1_M1/N1_C1_M1_Cocodrilo.wav'),

-- COLIBRÍ
(6, 'texto', 'Sus alas se mueven tan rápido que apenas pueden distinguirse a simple vista. Gracias a ello puede alimentarse sin posarse sobre las flores.',''),
(6, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Pistas_N1_C1_M1/N1_C1_M1_Colibri.wav'),

-- LECHUZA
(7, 'texto', 'Sus plumas son tan suaves que puede volar sin hacer ruido. Gracias a ello, se acerca a sus presas sin ser detectada.',''),
(7, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Pistas_N1_C1_M1/N1_C1_M1_Lechuza.wav'),

-- MANTIS
(8, 'texto', 'Es una cazadora paciente que espera el momento exacto para actuar. Cuando detecta una presa cercana, reacciona con gran velocidad.',''),
(8, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Pistas_N1_C1_M1/N1_C1_M1_Mantis.wav'),

-- MEDUSA
(9, 'texto', 'Habita océanos y mares de todo el mundo, desplazándose al ritmo de las corrientes marinas. Puede encontrarse cerca de la superficie o en aguas más profundas según la especie.',''),
(9, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Pistas_N1_C1_M1/N1_C1_M1_Medusa.wav'),

-- ORNITORRINCO
(10, 'texto', 'Vive en ríos y lagunas de Australia. Tiene pico parecido al de un pato, cola como la de un castor y, aunque es mamífero, nace de un huevo.',''),
(10, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Pistas_N1_C1_M1/N1_C1_M1_Ornitorrinco.wav'),

-- PATO
(11, 'texto', 'Suele vivir acompañado de otros de su especie y se comunica mediante sonidos muy característicos.Es común verlo descansando cerca de cuerpos de agua dulce o nadando en grupo.',''),
(11, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Pistas_N1_C1_M1/N1_C1_M1_Pato.wav'),

-- PEZ LUNA
(12, 'texto', 'Con frecuencia se acerca a la superficie para calentarse con los rayos del sol. Su silueta redondeada lo hace fácil de distinguir entre otras especies marinas.',''),
(12, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Pistas_N1_C1_M1/N1_C1_M1_PezLuna.wav'),

-- PIGüINO
(13, 'texto', 'Aunque pertenece al grupo de las aves, no puede elevarse por el aire. En cambio, utiliza sus alas como si fueran aletas para desplazarse.',''),
(13, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Pistas_N1_C1_M1/N1_C1_M1_Pinguino.wav'),

-- TIBURÓN MARTILLO
(14, 'texto', 'Puede detectar movimientos y señales eléctricas producidas por otros animales. Esta habilidad le permite encontrar presas incluso cuando están ocultas.',''),
(14, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Pistas_N1_C1_M1/N1_C1_M1_TiburonMartillo.wav'),

-- TORTUGA MARINA
(15, 'texto','Su cuerpo está protegido por una estructura resistente que la acompaña toda la vida. A diferencia de otros animales, esta protección forma parte de su esqueleto.', ''),
(15, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_1_C1_M1/Pistas_N1_C1_M1/N1_C1_M1_Tortuga.wav'),

-- --------------------------------------------
-- MINIJUEGO 1 CATEGORÍA 1 NIVEL 2 -- TEXTO
-- ARCHAEOPTERYX
(16, 'texto', 'Podía desplazarse entre los árboles y realizar vuelos cortos. Es considerado una de las evidencias más importantes de la evolución de las aves.',''),
(16, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Pistas_N2_C1_M1/N2_C1_M1_Archaeopteryx.wav'),

-- CARNOTAURUS
(17, 'texto', 'Sobre sus ojos tenía dos prominencias que parecían pequeños cuernos. Este rasgo lo hace fácil de distinguir entre otros grandes cazadores.',''),
(17, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Pistas_N2_C1_M1/N2_C1_M1_Carnotaurus.wav'),

-- DIPLODOCUS
(18, 'texto', 'Poseía una cola extremadamente larga que utilizaba para defenderse.  Su cuerpo era tan grande que necesitaba consumir grandes cantidades de alimento.',''),
(18, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Pistas_N2_C1_M1/N2_C1_M1_Diplodocus.wav'),

-- ICTIOSAURIOS
(19, 'texto', 'Su forma recordaba mucho a la de algunos animales marinos actuales. Aunque parecían peces, en realidad eran reptiles.',''),
(19, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Pistas_N2_C1_M1/N2_C1_M1_Ictiosaurio.wav'),

-- IGUANODON
(20, 'texto', 'Poseía un pulgar muy especial que utilizaba para defenderse. Esta característica fue una de las claves para identificarlo.',''),
(20, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Pistas_N2_C1_M1/N2_C1_M1_Iguanodon.wav'),

-- MEGALODON
(21, 'texto', 'Sus enormes dientes son algunos de los fósiles más buscados y estudiados. Muchos lo consideran el tiburón más grande de todos los tiempos.',''),
(21, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Pistas_N2_C1_M1/N2_C1_M1_Megalodon.wav'),

-- MOSASAURUS
(22, 'texto', 'Podía alcanzar tamaños impresionantes y moverse con gran rapidez en el mar. Gracias a sus fuertes mandíbulas, ocupaba uno de los lugares más altos de la cadena alimenticia.',''),
(22, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Pistas_N2_C1_M1/N2_C1_M1_Mosasaurus.wav'),

-- PARASAUROLOPHUS
(23, 'texto', 'Era un dinosaurio herbívoro que recorría bosques y llanuras alimentándose de hojas, ramas y otras plantas. Vivía en grupos, lo que le ayudaba a protegerse de los depredadores y a desplazarse en busca de alimento.',''),
(23, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Pistas_N2_C1_M1/N2_C1_M1_Parasaurolophus.wav'),

-- PLESIOSAURIO
(24, 'texto', 'Habitó los océanos durante la época de los dinosaurios y pasaba toda su vida en el agua.Se alimentaba principalmente de peces y pequeños animales marinos.',''),
(24, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Pistas_N2_C1_M1/N2_C1_M1_Plesiosaurio.wav'),

-- PTEROSAURIO
(25, 'texto', 'Sus alas estaban formadas por una membrana de piel sostenida por un dedo muy alargado. Aunque convivió con los dinosaurios, no pertenecía a ese grupo.',''),
(25, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Pistas_N2_C1_M1/N2_C1_M1_Pterosaurio.wav'),

-- SAURUSUCHUS
(26, 'texto', 'Su aspecto recordaba al de un enorme cocodrilo de patas largas. Podía moverse con rapidez para sorprender a sus presas. Fue uno de los cazadores más temidos de su época.',''),
(26, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Pistas_N2_C1_M1/N2_C1_M1_Saurosuchus.wav'),

-- STEGOSAURUS
(27, 'texto', 'A lo largo de su espalda destacaban grandes placas óseas alineadas en dos filas. Su cola estaba equipada con largas púas capaces de causar graves heridas.',''),
(27, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Pistas_N2_C1_M1/N2_C1_M1_Stegosaurus.wav'),

-- T-REX
(28, 'texto', 'Poseía una mordida extraordinariamente poderosa y dientes del tamaño de cuchillos. Su olfato y visión le ayudaban a localizar alimento a grandes distancias.',''),
(28, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Pistas_N2_C1_M1/N2_C1_M1_T-rex.wav'),

-- TRICERATOPS
(29, 'texto', 'Poseía una gran estructura ósea detrás de la cabeza que protegía su cuello. Esta característica también servía para intimidar a posibles atacantes.',''),
(29, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Pistas_N2_C1_M1/N2_C1_M1_Triceratops.wav'),

-- VELOCIRAPTOR
(30, 'texto', 'En cada pata trasera tenía una gran garra curva especialmente diseñada para atacar. Además, su cuerpo estaba cubierto de plumas, aunque no podía volar.',''),
(30, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_2_C1_M1/Pistas_N2_C1_M1/N2_C1_M1_Velociraptor.wav'),

-- ----------------------------------------------------------------
-- MINIJUEGO 1 CATEGORÍA 1 NIVEL 3
-- ANANSI
(31, 'texto', 'Muchas historias lo presentan obteniendo conocimientos, objetos valiosos o ventajas mediante trucos y estrategias.',''),
(31, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Pistas_N3_C1_M1/N3_C1_M1_Anansi.wav'),

-- CENTAURO
(32, 'texto', 'Habita en los densos bosques y llanuras salvajes. Se le asocia tanto con la barbarie indomable de la naturaleza como con la profunda sabiduría de la astronomía y la medicina.',''),
(32, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Pistas_N3_C1_M1/N3_C1_M1_Centauro.wav'),

-- CERBERO
(33, 'texto', 'Si intentas esquivarlo, no te bastará con vigilar una sola dirección: este colosal can del inframundo te observa fijamente con tres cabezas a la vez.',''),
(33, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Pistas_N3_C1_M1/N3_C1_M1_Cerbero.wav'),

-- FÉNIX
(34, 'texto', 'Cuando esta majestuosa ave siente que su larga vida llega a su fin, se consume voluntariamente en su propio nido de llamas para resurgir de sus cenizas.',''),
(34, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Pistas_N3_C1_M1/N3_C1_M1_Fenix.wav'), 

-- GÁRGOLA
(35, 'texto', 'Durante las tormentas, sirve para canalizar el agua de lluvia lejos de los muros, pero al caer la noche, su mirada fija parece cobrar una inquietante vitalidad protectora.',''),
(35, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Pistas_N3_C1_M1/N3_C1_M1_Gargola.wav'),

-- GRIFO
(36, 'texto', 'Combina la visión panorámica y el ataque en picada del cazador aéreo más letal con la fuerza bruta y el rugido del depredador terrestre definitivo.',''),
(36, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Pistas_N3_C1_M1/N3_C1_M1_Grifo.wav'),

-- HYDRA
(37, 'texto', 'Habita en los pantanos oscuros y pestilentes de Lerna. El aire a su alrededor está cargado de un aliento venenoso que puede ser letal con solo respirarlo.',''),
(37, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Pistas_N3_C1_M1/N3_C1_M1_Hydra.wav'),

-- KRAKEN
(38, 'texto', 'Esta pesadilla de las leyendas nórdicas emerge desde los abismos de la fosa marina usando colosales tentáculos llenos de ventosas para arrastrar los barcos hacia el fondo.',''),
(38, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Pistas_N3_C1_M1/N3_C1_M1_Kraken.wav'),

-- LEVIATÁN
(39, 'texto', 'Sus escamas son tan densas e impenetrables que ninguna espada o arpón humano puede perforarlas. Simboliza el caos indomable del mar profundo.',''),
(39, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Pistas_N3_C1_M1/N3_C1_M1_Leviatan.wav'),

-- MANTÍCORA
(40, 'texto', 'Posee el cuerpo de un león rojizo, el rostro de un hombre con hileras de dientes afilados y una temible cola de escorpión capaz de lanzar espinas venenosas.',''),
(40, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Pistas_N3_C1_M1/N3_C1_M1_Manticora.wav'),

-- MEDUSA
(41, 'texto', 'No necesitas que te toque o te ataque con armas para derrotarte; su mayor peligro radica en un sentido biológico que los humanos usamos para interactuar día a día.',''),
(41, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Pistas_N3_C1_M1/N3_C1_M1_Medusa.wav'),

-- MINOTAURO
(42, 'texto', 'Se encuentra atrapado en el centro de una red interminable de pasillos oscuros, muros idénticos y callejones sin salida diseñados por el arquitecto Dédalo.',''),
(42, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Pistas_N3_C1_M1/N3_C1_M1_Minotauro.wav'),

-- NESSI
(43, 'texto', 'Es el críptido más escurridizo del mundo moderno. Ha protagonizado innumerables fotografías borrosas y lecturas de sonar, pero siempre logra evitar ser capturado formalmente.',''),
(43, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Pistas_N3_C1_M1/N3_C1_M1_Nessi.wav'),

-- QUIMERA
(44, 'texto', 'vaga por las regiones de Licia, dejando a su paso un rastro de cenizas, azufre y campos quemados debido a su temperamento destructivo.',''),
(44, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Pistas_N3_C1_M1/N3_C1_M1_Quimera.wav'),

-- YETI
(45, 'texto', ' Los lugareños evitan las cuevas de hielo por temor a sus ensordecedores rugidos. Se le conoce también como "el abominable hombre de las nieves".',''),
(45, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_1_M1/Nivel_3_C1_M1/Pistas_N3_C1_M1/N3_C1_M1_Yeti.wav'),
-- --------------------------------------------------------------
-- MINIJUEGO 1 CATEGORIA 2 NIVEL 1
-- ALASKA
(46, 'texto', 'Es conocida popularmente en el mundo del turismo y la aventura como "La Última Frontera".  Sus costas son el hogar de majestuosas ballenas jorobadas y enormes osos pardos que pescan salmón.',''),
(46, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Pistas_N1_C2_M1/N1_C2_M1_Alaska.wav'),

-- BELICE
(47, 'texto', 'En sus aguas cristalinas se encuentra la segunda barrera de arrecifes de coral más grande del mundo. Alberga el famoso "Gran Agujero Azul", un sumidero marítimo que atrae a buceadores de todo el planeta.',''),
(47, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Pistas_N1_C2_M1/N1_C2_M1_Belice.wav'),

-- CANADÁ
(48, 'texto', 'Su territorio alberga una inmensa cantidad de lagos naturales e imponentes bosques de pinos y abetos. El hockey sobre hielo es más que un simple deporte de invierno, es una verdadera pasión nacional.',''),
(48, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Pistas_N1_C2_M1/N1_C2_M1_Canada.wav'),

-- COSTA RICA
(49, 'texto', 'Los lugareños son conocidos cariñosamente en todo el mundo hispanohablante bajo el apodo de "ticos". Es un refugio vital para miles de especies de coloridas ranas, perezosos, tucanes y tortugas marinas.',''),
(49, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Pistas_N1_C2_M1/N1_C2_M1_CostaRica.wav'),

-- EL SALVADOR
(50, 'texto', 'Es geográficamente la nación más pequeña de Centroamérica, pero cuenta con una altísima densidad de población. A diferencia de la mayoría de sus vecinos de la región, este territorio no tiene salida al Mar Caribe.',''),
(50, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Pistas_N1_C2_M1/N1_C2_M1_ElSalvador.wav'),

-- ESTADOS UNIDOS
(51, 'texto', '.Su territorio se divide políticamente en cincuenta estados federados y un distrito de gobierno central. Es la cuna de industrias globales del entretenimiento como el cine de Hollywood y la música pop.',''),
(51, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Pistas_N1_C2_M1/N1_C2_M1_EstadosUnidos.wav'),

-- GROENLANDIA
(52, 'texto', 'Es considerada la isla más grande del planeta, ubicada entre los océanos Atlántico Norte e Ártico. A pesar de su cercanía geográfica con América del Norte, pertenece políticamente al Reino de Dinamarca.',''),
(52, 'audio','','/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Pistas_N1_C2_M1/N1_C2_M1_Groenlandia.wav'),

-- GUATEMALA
(53, 'texto', '.Su nombre indígena tiene un hermoso significado que se traduce como "tierra de muchos árboles". En su norteña y densa selva de Petén se encuentra Tikal, una de las mayores ciudades mayas de la historia.',''),
(53, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Pistas_N1_C2_M1/N1_C2_M1_Guatemala.wav'),

-- HONDURAS
(54, 'texto', 'Posee las Islas de la Bahía, un destino turístico mundial famoso por el buceo en arrecifes de coral. Su territorio es sumamente montañoso.',''),
(54, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Pistas_N1_C2_M1/N1_C2_M1_Honduras.wav'),

-- MÉXICO
(55, 'texto', 'Es una vibrante nación norteamericana famosa mundialmente por su gastronomía declarada patrimonio cultural. Fue el hogar de colosales civilizaciones prehispánicas, destacando principalmente los aztecas y los mayas.',''),
(55, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Pistas_N1_C2_M1/N1_C2_M1_Mexico.wav'),

-- NICARAGUA
(56, 'texto', 'Sus ciudades coloniales de Granada y León conservan una impresionante arquitectura de la época hispánica. Es la cuna del célebre y revolucionario poeta Rubén Darío, el máximo exponente del modernismo literario.',''),
(56, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Pistas_N1_C2_M1/N1_C2_M1_Nicaragua.wav'),

-- PANAMÁ
(57, 'texto', 'Es una nación con una silueta en forma de S que une físicamente a América Central con América del Sur. Es mundialmente famosa por una monumental obra de ingeniería humana que conecta el Atlántico con el Pacífico.',''),
(57, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_1_C2_M1/Pistas_N1_C2_M1/N1_C2_M1_Panamá.wav'),

-- -------------------------------------------------------------
-- MINIJUEGO 1 CATEGORIA 2 NIVEL 2
-- ARGENTINA
(58, 'texto', 'Posee una de las maravillas naturales más imponentes del mundo. Las majestuosas Cataratas del Iguazú se ubican en su frontera norte. Es un destino turístico obligatorio.',''),
(58, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Pistas_N2_C2_M1/N2_C2_M1_Argentina.wav'),

-- ARMENIA
(59, 'texto', 'El imponente y sagrado Monte Ararat domina visualmente su horizonte. Esta emblemática montaña es considerada el símbolo principal de su pueblo. Aparece con orgullo en el centro de su escudo de armas nacional.',''),
(59, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Pistas_N2_C2_M1/N2_C2_M1_Armenia.wav'),

-- BOLIVIA
(60, 'texto', 'Es uno de los dos países de Sudamérica que no tienen salida al mar. Su territorio alberga el desierto de sal continuo más grande del mundo. El Salar de Uyuni es una de sus atracciones visuales más impactantes.',''),
(60, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Pistas_N2_C2_M1/N2_C2_M1_Bolivia.wav'),

-- BULGARIA
(61, 'texto', 'Sus costas orientales son bañadas por las aguas templadas del Mar Negro. Para decir que "sí" con la cabeza, sus habitantes la mueven de lado a lado.',''),
(61, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Pistas_N2_C2_M1/N2_C2_M1_Bulgaria.wav'),

-- CHIPRE
(62, 'texto', 'Su mapa territorial aparece dibujado en el centro de su bandera oficial. Debajo de la silueta del país, resaltan dos ramas de olivo verdes. Estas ramas son un símbolo tradicional que representa el deseo de paz.',''),
(62, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Pistas_N2_C2_M1/N2_C2_M1_Chipre.wav'),

-- COLOMBIA
(63, 'texto', 'Es mundialmente reconocido por producir uno de los cafés más suaves del planeta. Su música tradicional incluye ritmos muy alegres como la cumbia y el vallenato.',''),
(63, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Pistas_N2_C2_M1/N2_C2_M1_Colombia.wav'),

-- ECUADOR
(64, 'texto', 'Es dueño del famoso y remoto archipiélago de las Islas Galápagos. Este lugar fue clave para que Charles Darwin desarrollara su teoría científica.',''),
(64, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Pistas_N2_C2_M1/N2_C2_M1_Ecuador.wav'),

-- ESLOVAQUIA
(65, 'texto', 'Su capital, Bratislava, limita directamente con otras dos naciones distintas. Es un gran productor de automóviles a nivel industrial en todo el continente. Su bandera cuenta con una cruz doble sobre tres colinas azules.',''),
(65, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Pistas_N2_C2_M1/N2_C2_M1_Eslovaquia.wav'),

-- ESTONIA
(66, 'texto', 'Fue el lugar de nacimiento de la famosa plataforma de llamadas Skype. El acceso a internet inalámbrico es considerado un derecho humano en sus leyes. Incluso las votaciones gubernamentales se pueden realizar desde la computadora.',''),
(66, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Pistas_N2_C2_M1/N2_C2_M1_Estonia.wav'),

-- MONGOLIA
(67, 'texto', 'Es un enorme país asiático situado entre las tierras de Rusia y China. Es famoso históricamente por ser el imperio continuo más grande del pasado.',''),
(67, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Pistas_N2_C2_M1/N2_C2_M1_Mongolia.wav'),

-- PARAGUAY
(68, 'texto', 'Es un país oficialmente bilingüe donde la lengua indígena es muy hablada. El guaraní no solo es el idioma cooficial, sino también su moneda nacional.',''),
(68, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Pistas_N2_C2_M1/N2_C2_M1_Paraguay.wav'),

-- PERÚ
(69, 'texto', 'Su gastronomía es reconocida a nivel mundial por su enorme variedad y sabor. El ceviche es su plato bandera preparado a base de pescado fresco refinado. Es la tierra de origen de la papa, con miles de variedades cultivadas.',''),
(69, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Pistas_N2_C2_M1/N2_C2_M1_Peru.wav'),

-- POLONIA 
(70, 'texto', 'Su territorio sufrió grandes devastaciones materiales durante la Segunda Guerra Mundial. Su capital, Varsovia, fue reconstruida de forma idéntica basándose en pinturas antiguas.',''),
(70, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Pistas_N2_C2_M1/N2_C2_M1_Polonia.wav'),

-- RUMANIA
(71, 'texto', 'Es un país del este europeo famoso por una región montañosa muy misteriosa. Transilvania es conocida mundialmente gracias a las leyendas de vampiros y castillos. La literatura inmortalizó estas tierras mediante la famosa novela de Drácula.',''),
(71, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Pistas_N2_C2_M1/N2_C2_M1_Rumania.wav'),

-- TURQUÍA
(72, 'texto', 'Es una fascinante nación transcontinental que une los continentes de Europa y Asia. Su ciudad más grande está dividida de forma natural por el estrecho del Bósforo.',''),
(72, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_2_C2_M1/Pistas_N2_C2_M1/N2_C2_M1_Turquía.wav'),
-- ---------------------------------------------------------------------
-- MINIJUEGO 1 CATEGORIA 2 NIVEL 3
-- CUBA
(73, 'texto', 'Su capital posee un casco histórico colonial que es Patrimonio de la Humanidad. Es una de las naciones más reconocidas por la producción de puros premium.',''),
(73, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Pistas_N3_C2_M1/N3_C2_M1_Cuba.wav'),

-- CURAZAO
(74, 'texto', 'El idioma local más hablado por sus habitantes se denomina papiamento. Es una lengua criolla que mezcla español, portugués, holandés y raíces africanas.',''),
(74, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Pistas_N3_C2_M1/N3_C2_M1_Curacao.wav'),

-- EGIPTO
(75, 'texto', 'Alberga las monumentales pirámides de Guiza, construidas en la antigüedad. La imponente y mística Gran Esfinge vigila sus milenarios desiertos de arena.',''),
(75, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Pistas_N3_C2_M1/N3_C2_M1_Egipto.wav'),

-- ETIOPÍA
(76, 'texto', 'Es considerada históricamente la tierra de origen de la planta del café. Los granos aromáticos de este cultivo se descubrieron originalmente en sus altas montañas.',''),
(76, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Pistas_N3_C2_M1/N3_C2_M1_Etiopia.wav'),

-- FIJI
(77, 'texto', 'El rugby es el deporte nacional absoluto y desata una pasión inmensa en su pueblo. Su selección nacional es una de las potencias más temidas en la modalidad de sietes.',''),
(77, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Pistas_N3_C2_M1/N3_C2_M1_Fiji.wav'),

-- ISLAS MARSHALL
(78, 'texto', 'Un famoso atolón de su territorio dio nombre a un popular traje de baño femenino. El Atolón de Bikini fue escenario de numerosas pruebas nucleares en el pasado.',''),
(78, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Pistas_N3_C2_M1/N3_C2_M1_Marshall.wav'),

-- MALAWI
(79, 'texto', 'La agricultura es la base de su economía, destacando la producción de tabaco y té. Su bandera nacional está compuesta por tres franjas horizontales: negra, roja y verde.',''),
(79, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Pistas_N3_C2_M1/N3_C2_M1_Malawi.wav'),

-- MALI
(80, 'texto', 'El majestuoso río Níger atraviesa su territorio sirviendo como principal ruta de transporte. La música tradicional de sus tierras ha influido fuertemente en el blues americano.',''),
(80, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Pistas_N3_C2_M1/N3_C2_M1_Mali.wav'),

-- NUEVA ZELANDA
(81, 'texto', 'El ave nativa no voladora llamada kiwi es el símbolo nacional más querido por todos. Este término también se utiliza a nivel internacional para referirse a sus habitantes',''),
(81, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Pistas_N3_C2_M1/N3_C2_M1_NuevaZelanda.wav'),

-- PAPÚA NUEVA GUINEA
(82, 'texto', 'Ocupa la mitad oriental de la segunda isla más grande de todo el planeta. Es considerado el país con la mayor diversidad lingüística de todo el mundo. En su territorio se hablan más de ochocientas lenguas indígenas diferentes.',''),
(82, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Pistas_N3_C2_M1/N3_C2_M1_PapuaNuevaGuinea.wav'),

-- REPÚBLICA DOMINICANA
(83, 'texto', 'Punta Cana es su destino turístico más famoso a nivel mundial por sus playas. Es el único lugar del mundo donde se extrae una preciosa piedra azul llamada larimar.',''),
(83, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Pistas_N3_C2_M1/N3_C2_M1_RepDominicana.wav'),

-- SAMOA
(84, 'texto', 'Debido a su posición geográfica, fue uno de los primeros países en recibir el año nuevo. En el año dos mil once cambiaron su zona horaria cruzando la línea internacional de cambio de fecha.',''),
(84, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Pistas_N3_C2_M1/N3_C2_M1_Samoa.wav'),

-- SAN CRISTOBAL Y NIEVES
(85, 'texto', 'su bandera nacional cuenta con dos estrellas blancas que representan a sus dos islas. Estas estrellas se ubican sobre una franja negra diagonal con bordes de color amarillo.',''),
(85, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Pistas_N3_C2_M1/N3_C2_M1_SanCristobalNieves.wav'),

-- TRINIDAD Y TOBAGO
(86, 'texto', 'Alberga el depósito natural de asfalto líquido más grande e importante del mundo entero. El Lago de Brea suministra este material para pavimentar autopistas en muchos países.',''),
(86, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Pistas_N3_C2_M1/N3_C2_M1_TrinidadTobago.wav'),

-- ZAMBIA
(87, 'texto', 'Su fauna salvaje es protegida en inmensos parques nacionales ideales para hacer safaris. El águila pescadora africana es su ave nacional y aparece dibujada en sus símbolos.',''),
(87, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_2_M1/Nivel_3_C2_M1/Pistas_N3_C2_M1/N3_C2_M1_Zambia.wav'),
-- -----------------------------------------------
-- MINIJUEGO 1 CATEGORIA 3 NIVEL 1
-- ANAIS
(88, 'texto', 'Es la hija menor de un enorme conejo holgazán y una gata con mucho temperamento. Su increíble astucia la convierte en la verdadera voz de la razón de su casa.',''),
(88, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Pistas_N1_C3_M1/N1_C3_M1_Anais.wav'),

-- AUSTIN
(89, 'texto', 'Es un tierno amiguito de color morado que resalta por su timidez en el grupo. Suele ser un poco reservado al principio, pero se une a cualquier juego imaginario. Usa una gorra con rayas y una playera a juego.',''),
(89, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Pistas_N1_C3_M1/N1_C3_M1_Austin.wav'),

-- BELLOTA
(90, 'texto', 'Esta pequeña defensora de la justicia fue creada con azúcar, flores y muchos celos. A diferencia de sus hermanas, no tiene paciencia y odia las cosas cursis.',''),
(90, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Pistas_N1_C3_M1/N1_C3_M1_Bellota.wav'),

-- BOB ESPONJA
(91, 'texto', 'Este personaje amarillo y muy poroso es el terror de su vecino calamar. Tiene como mascota a un caracol que maúlla y le fascina pescar medusas. Su risa es inconfundible y siempre está listo para un día de diversión.',''),
(91, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Pistas_N1_C3_M1/N1_C3_M1_BobEsponja.wav'),

-- BURBUJA
(92, 'texto', 'Esta pequeña heroína de Saltadilla puede hablar con las ardillas y otros seres. Aunque parece indefensa y llora con facilidad, su furia es de temer. Su color favorito es el celeste y siempre intenta ver el lado amable.',''),
(92, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Pistas_N1_C3_M1/N1_C3_M1_Burbuja.wav'),

-- DARWIN
(93, 'texto', 'Es extremadamente optimista, un poco ingenuo y tiene una piel de color naranja brillante. Vive aventuras surrealistas en Elmore y es incapaz de ocultar un secreto por mucho tiempo.',''),
(93, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Pistas_N1_C3_M1/N1_C3_M1_Darwin.wav'),

-- DON CANGREJO
(94, 'texto', 'Tiene una obsesión desmedida por el dinero y cuenta cada centavo en su oficina. Su mayor orgullo es una receta secreta que su rival verde intenta robar siempre.',''),
(94, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Pistas_N1_C3_M1/N1_C3_M1_DonCangrejo.wav'),

-- GUMBALL
(95, 'texto', 'Este felino parlante está profundamente enamorado de un cacahuate con astas de ciervo. Tiene una facilidad increíble para meterse en problemas junto a su hermano pez.',''),
(95, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Pistas_N1_C3_M1/N1_C3_M1_Gumball.wav'),

-- JERRY
(96, 'texto', 'Es un astuto y escurridizo roedor de color marrón que vive en las paredes de una casa. Su pasatiempo favorito es robar queso de la cocina sin ser atrapado. Siempre logra burlar los elaborados planes de su eterno enemigo felino.',''),
(96, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Pistas_N1_C3_M1/N1_C3_M1_Jerry.wav'),

-- PABLO
(97, 'texto', 'Este pequeño ave marina vive en una casa con un patio trasero muy especial. Su imaginación no tiene límites y se transforma en caballero, detective o astronauta.',''),
(97, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Pistas_N1_C3_M1/N1_C3_M1_Pablo.wav'),

-- PATRICIO
(98, 'texto', 'Este glotón personaje pasa el día durmiendo, comiendo helado o cazando medusas. Es el vecino incómodo de un calamar amargado que toca el clarinete.',''),
(98, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Pistas_N1_C3_M1/N1_C3_M1_Patricio.wav'),

-- RICHARD WATTERSON
(99, 'texto', 'Es un enorme y perezoso conejo rosado que pasa el día entero en el sofá. Carece por completo de un sentido de la responsabilidad y odia trabajar. Es el tierno pero despistado padre de una familia sumamente caótica.',''),
(99, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Pistas_N1_C3_M1/N1_C3_M1_Richard.wav'),

-- TASHA
(100, 'texto', 'Es una pequeña hipopótamo de color amarillo que viste un vestido florecido. Tiene un carácter fuerte, es muy segura de sí misma y le gusta mandar a los demás. Se unió un poco después al grupo de amigos que juega en el patio trasero.',''),
(100, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Pistas_N1_C3_M1/N1_C3_M1_Tasha.wav'),

-- TOM
(101, 'texto', 'A veces se alía con su enemigo cuando un peligro mayor amenaza el hogar. Camina en dos patas, vigila los platos de comida y odia compartir su espacio. Su nombre compone la primera mitad del título de una de las caricaturas más viejas.',''),
(101, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Pistas_N1_C3_M1/N1_C3_M1_Tom.wav'),

-- TYRONE
(102, 'texto', 'Es un simpático alce de color naranja con una gran cornamenta en su cabeza. Su personalidad es tranquila, analítica y un poco propensa a la preocupación. Viste una playera con rayas rojas y azules mientras juega en el jardín.',''),
(102, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_1_C3_M1/Pistas_N1_C3_M1/N1_C3_M1_Tyrone.wav'),
-- -----------------------------------------------
-- MINIJUEGO 1 CATEGORIA 3 NIVEL 2
-- DAPHNE
(103, 'texto', 'Siempre viste a la moda con un vestido lila, mallas rosas y zapatos de tacón. Es la compañera ideal de una chica inteligente con lentes y un joven de camisa blanca.',''),
(103, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Pistas_N2_C3_M1/N2_C3_M1_Daphne.wav'),

-- DIPPER
(104, 'texto', 'Es un chico inteligente, un poco paranoico y con una marca de nacimiento oculta en la frente. Está profundamente enamorado de la chica pelirroja que trabaja en la caja de la cabaña.',''),
(104, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Pistas_N2_C3_M1/N2_C3_M1_Dipper.wav'),

-- GARNET
(105, 'texto', 'Esta poderosa heroína posee tres ojos y combate usando un par de enormes guanteletes rojos. Es la figura materna y protectora de un niño mitad humano que heredó un escudo rosa.',''),
(105, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Pistas_N2_C3_M1/N2_C3_M1_Garnet.wav'),

-- GUNTER
(106, 'texto', 'Es un canino de color amarillo con la increíble habilidad mágica de estirar todo su cuerpo. Puede transformarse en una casa, un auto, un paracaídas o aumentar su tamaño a voluntad. ',''),
(106, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Pistas_N2_C3_M1/N2_C3_M1_Gunter.wav'),

-- JAKE
(107, 'texto', 'Este personaje es bastante relajado, le encanta tocar la viola y es un cocinero experto. Su debilidad son los sándwiches perfectos y dar consejos de vida basados en su madurez.',''),
(107, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Pistas_N2_C3_M1/N2_C3_M1_Jake.wav'),

-- MABEL
(108, 'texto', 'Es una niña de doce años extremadamente optimista, enérgica y llena de energía positiva. Es famosa por usar un suéter tejido diferente y sumamente colorido en cada día del año.',''),
(108, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Pistas_N2_C3_M1/N2_C3_M1_Mabel.wav'),

-- PATO GRAVITY
(109, 'texto', 'Este animalito es el compañero inseparable y más fiel de una niña con suéteres coloridos. Pasa sus días comiendo comida chatarra, rodando por el suelo y durmiendo en la cama.',''),
(109, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Pistas_N2_C3_M1/N2_C3_M1_Pato.wav'),

-- PERRY ORNITORRINCO
(110, 'texto', 'Es un ornitorrinco de color verde azulado que vive una doble vida como mascota de dos niños. Cuando nadie lo ve, se pone un sombrero café y se convierte en un hábil agente secreto.',''),
(110, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Pistas_N2_C3_M1/N2_C3_M1_Perry.wav'),

-- PINKIE PIE
(111, 'texto', 'Esta divertida criatura representa el Elemento de la Risa en el mágico reino de Equestria. Tiene una marca en su lomo que consiste en tres globos de helio, dos azules y uno amarillo.',''),
(111, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Pistas_N2_C3_M1/N2_C3_M1_PinkiePie.wav'),

-- RAINBOW DASH
(112, 'texto', 'La marca de su lomo es una nube blanca de la que sale un rayo de tres colores brillantes. Consiguió su habilidad especial al realizar por primera vez una espectacular ruptura sónica.',''),
(112, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Pistas_N2_C3_M1/N2_C3_M1_RainbowDash.wav'),

-- SCOOBY
(113, 'texto', 'Es un enorme perro de raza gran danés de color marrón con manchas negras en su lomo. Tiene un miedo terrible a los fantasmas y monstruos, a pesar de que se dedica a buscarlos.',''),
(113, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Pistas_N2_C3_M1/N2_C3_M1_Scooby.wav'),

-- SOOS
(114, 'texto', 'Siempre usa una playera de color verde olivo con un enorme signo de interrogación al centro. Considera a su jefe tacaño como una figura paterna y es el fan número uno de la tienda.',''),
(114, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Pistas_N2_C3_M1/N2_C3_M1_Soos.wav'),

-- STEVEN UNIVERSE
(115, 'texto', 'Este tierno personaje es el miembro más joven de un equipo de guerreras alienígenas místicas. Su arma principal es un escudo de energía rosa y posee la habilidad de curar con sus lágrimas.',''),
(115, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Pistas_N2_C3_M1/N2_C3_M1_StevenUniverse.wav'),

-- TIO STAN
(116, 'texto', 'Este personaje es el tío abuelo de unos gemelos de doce años que se quedan con él en el verano. Oculta una identidad secreta y un gigantesco laboratorio subterráneo debajo de su tienda.',''),
(116, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Pistas_N2_C3_M1/N2_C3_M1_TioStan.wav'),

-- TWILIGHT SPARKLE
(117, 'texto', 'Es una pony unicornio de color lila con una melena recta de tonos oscuros y rosas. Su pasión absoluta son los libros, el estudio de la magia y mantener todo perfectamente ordenado.',''),
(117, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_2_C3_M1/Pistas_N2_C3_M1/N2_C3_M1_TwilightSparkle.wav'),

-- ----------------------------------------
-- MINIJUEGO 1 CATEGORIA 3 NIVEL 3
-- BULBASAUR
(118, 'texto', 'Le fascina tomar siestas bajo la luz del sol para absorber energía en su capullo. Cuando está listo para evolucionar, el bulbo de su espalda empieza a parpadear y brillar',''),
(118, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Pistas_N3_C3_M1/N3_C3_M1_Bulbasaur.wav'),

-- chikorita
(119, 'texto', 'Esta dócil criatura utiliza la hoja de su cabeza para medir la temperatura y la humedad. De su cuerpo emana un dulce aroma que tiene el poder de calmar los ánimos de las batallas.',''),
(119, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Pistas_N3_C3_M1/N3_C3_M1_Chikorita.wav'),

-- EEVEE
(120, 'texto', 'Este tierno amigo destaca por ser la criatura con mayor número de evoluciones posibles. Dependiendo de las piedras elementales o del afecto, puede transformarse en ocho formas distintas.',''),
(120, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Pistas_N3_C3_M1/N3_C3_M1_Eevee.wav'),

-- GENGAR
(121, 'texto', 'Para conseguirlo en los juegos clásicos, era obligatorio realizar un intercambio con otro jugador. Es famoso por sus bromas pesadas y por asustar a los viajeros en las noches de luna llena.',''),
(121, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Pistas_N3_C3_M1/N3_C3_M1_Gengar.wav'),

-- GRENINJA
(122, 'texto', 'Este personaje destaca por su velocidad extrema y por moldear estrellas ninja hechas de agua. Se volvió mundialmente famoso por alcanzar una transformación única gracias al lazo con su entrenador.',''),
(122, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Pistas_N3_C3_M1/N3_C3_M1_Greninja.wav'),

-- LATIOS
(123, 'texto', 'Es un imponente monstruo legendario que tiene una silueta aerodinámica similar a la de un avión. Su cuerpo combina los colores azul y gris, mostrando un triángulo rojo impreso en su pecho. Pertenece al tipo Dragón y Psíquico.',''),
(123, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Pistas_N3_C3_M1/N3_C3_M1_Latios.wav'),

-- MEWTWO
(124, 'texto', 'Este espécimen de tipo Psíquico es considerado uno de los rivales más destructivos y serios. Fue el antagonista principal de la primera película animada, donde buscaba su propósito en el mundo.',''),
(124, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Pistas_N3_C3_M1/N3_C3_M1_Mewtwo.wav'),

-- MOLTRES
(125, 'texto', 'Este espécimen combina los tipos Fuego y Volador, controlando el calor con el batir de sus alas. Cuenta la leyenda que cura sus heridas sumergiéndose directamente en el magma de los volcanes.',''),
(125, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Pistas_N3_C3_M1/N3_C3_M1_Moltres.wav'),

-- PIKACHU
(126, 'texto', 'Este personaje de tipo Eléctrico almacena energía en sus mejillas para lanzar potentes descargas. Odia entrar a su esfera de captura y prefiere viajar siempre sobre el hombro de su amigo humanos.',''),
(126, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Pistas_N3_C3_M1/N3_C3_M1_Pikachu.wav'),

-- RALTS
(127, 'texto', 'Este tierno personaje pertenece al tipo Psíquico y Hada, siendo nativo de la región de Hoenn. Si siente vibraciones positivas en el ambiente se acerca, pero se esconde si nota hostilidad.',''),
(127, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Pistas_N3_C3_M1/N3_C3_M1_Ralts.wav'),

-- SKAMORY
(128, 'texto', 'Es un ave robusta cuyo cuerpo entero está cubierto por una armadura de acero grisáceo. El interior de sus alas es de un llamativo color rojo y sus plumas son afiladas como espadas.',''),
(128, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Pistas_N3_C3_M1/N3_C3_M1_Skarmory.wav'),

-- SLAKING
(129, 'texto', 'Aunque parece completamente inofensivo y dormilón, un solo golpe suyo puede ser devastador. Come la hierba que crece a su alrededor y luego se queda dormido por varios días seguidos.',''),
(129, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Pistas_N3_C3_M1/N3_C3_M1_Slaking.wav'),

-- SNORLAX
(130, 'texto', 'Su estómago es tan resistente que puede digerir alimentos descompuestos o veneno sin problemas. Para lograr despertarlo y quitarlo del camino, es obligatorio usar un artefacto llamado Pokéflauta.',''),
(130, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Pistas_N3_C3_M1/N3_C3_M1_Snorlax.wav'),

-- SPRIGATITO
(131, 'texto', 'Tiene una personalidad un poco caprichosa y se deprime si su entrenador le presta atención a otros. Al evolucionar, se pone de pie sobre dos patas y se convierte en un hábil mago conantifaz.',''),
(131, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Pistas_N3_C3_M1/N3_C3_M1_Sprigatito.wav'),

-- SQUIRTLE
(132, 'texto', 'Es el inicial de tipo Agua que el profesor de Kanto ofrece a los nuevos aventureros. Este personaje tiene la capacidad de retraer su cuello y extremidades para ocultarse por completo.',''),
(132, 'audio', '', '/Multimedia/Minijuegos/Minijuego_1/Categoria_3_M1/Nivel_3_C3_M1/Pistas_N3_C3_M1/N3_C3_M1_Squirtle.wav');


-- MINIJUEGO 2 - FOX JUMP!
-- CATEGORÍA 1: ANIMALES
-- NIVEL FÁCIL

INSERT INTO Pregunta (id_nivel, pregunta) VALUES

(10,'¿El elefante africano de sabana es el animal terrestre más grande y pesado del planeta?'),
(10,'¿Los leones africanos nunca viven solos y siempre permanecen en una manada?'),
(10,'¿El guepardo puede alcanzar velocidades cercanas a los 100 km/h?'),
(10,'¿Los osos polares están adaptados para vivir en las selvas tropicales del Amazonas?'),
(10,'¿Las jirafas desarrollaron su largo cuello para alcanzar las hojas más altas de los árboles?'),

(10,'¿Los camellos almacenan grandes cantidades de agua líquida dentro de sus jorobas?'),
(10,'¿El veneno de la mamba negra contiene neurotoxinas capaces de afectar rápidamente el sistema nervioso?'),
(10,'¿Los canguros utilizan su cola como apoyo para mantener el equilibrio al saltar?'),
(10,'¿Los osos perezosos bajan todos los días de los árboles para hacer sus necesidades?'),
(10,'¿El ornitorrinco es un mamífero que pone huevos?'),

(10,'¿Los dientes de los roedores nunca dejan de crecer durante toda su vida?'),
(10,'¿El lagarto cornudo puede lanzar sangre por los ojos como mecanismo de defensa?'),
(10,'¿Las hormigas respiran mediante espiráculos en lugar de pulmones?'),
(10,'¿El elefante es el único mamífero cuadrúpedo que posee cuatro rodillas?'),
(10,'¿Los cuernos de los rinocerontes están formados por hueso?'),

(10,'¿Los delfines necesitan salir a la superficie para respirar aire?'),
(10,'¿Los tiburones son mamíferos con un esqueleto completamente de hueso?'),
(10,'¿El pez payaso mantiene una relación de beneficio mutuo con las anémonas de mar?'),
(10,'¿Las estrellas de mar poseen un cerebro central de gran tamaño?'),
(10,'¿La ballena azul es el animal más grande que existe en la Tierra?'),

(10,'¿Los pulpos poseen tres corazones?'),
(10,'¿Las tortugas marinas pueden permanecer bajo el agua durante varios días sin respirar?'),
(10,'¿El cuerpo de las medusas está compuesto por más del 90% de agua?'),
(10,'¿El pez globo puede inflar su cuerpo para defenderse de los depredadores?'),
(10,'¿En los caballitos de mar el macho es quien incuba los huevos?'),

(10,'¿El gran tiburón blanco necesita nadar constantemente para poder respirar?'),
(10,'¿El pez pulmonado africano puede caminar largas distancias por el desierto usando sus aletas?'),
(10,'¿Las orcas pertenecen a la familia de los delfines?'),
(10,'¿El calamar gigante posee los ojos más grandes del reino animal?'),
(10,'¿Los arrecifes de coral son únicamente formaciones de roca sin organismos vivos?'),

(10,'¿Los pingüinos pueden volar largas distancias durante sus migraciones?'),
(10,'¿El colibrí es la única ave capaz de volar hacia atrás?'),
(10,'¿Los murciélagos son mamíferos que alimentan a sus crías con leche?'),
(10,'¿Los búhos cazan haciendo mucho ruido con sus alas?'),
(10,'¿Todas las aves nacen con la capacidad de volar desde que salen del huevo?'),

(10,'¿Las golondrinas pueden pasar meses enteros en el aire sin aterrizar?'),
(10,'¿El águila arpía posee garras que pueden ser más largas que las de un oso pardo?'),
(10,'¿Las abejas comunican la ubicación del alimento mediante una danza?'),
(10,'¿El pelícano almacena peces vivos durante varios días en la bolsa de su pico?'),
(10,'¿El cóndor de los Andes caza activamente grandes presas utilizando sus garras?'),

(10,'¿Los flamencos nacen con su característico color rosa?'),
(10,'¿El halcón peregrino puede superar los 320 km/h durante una picada?'),
(10,'¿El albatros viajero puede planear miles de kilómetros aprovechando las corrientes de aire?'),
(10,'¿Las mariposas monarca utilizan una brújula solar y receptores magnéticos para orientarse durante su migración?'),
(10,'¿La cola del pavo real macho sirve principalmente para camuflarse entre la vegetación?');

-- FOX JUMP!
-- CATEGORÍA 1: PERÍODOS
-- NIVEL INTERMEDIO
-- PERÍODO: TRIÁSICO

INSERT INTO Pregunta (id_nivel, pregunta) VALUES

(11,'¿Los primeros dinosaurios aparecieron durante el período Triásico?'),
(11,'¿Los primeros mamíferos aparecieron después de la extinción de los dinosaurios y nunca coexistieron con ellos?'),
(11,'¿Durante todo el período Triásico los continentes estaban unidos formando Pangea?'),
(11,'¿El período Triásico fue la última etapa de la Era Mesozoica?'),
(11,'¿El Coelophysis fue uno de los primeros dinosaurios carnívoros conocidos?'),

(11,'¿Al inicio del Triásico predominaban los cinodontos y dicinodontos sobre los dinosaurios?'),
(11,'¿Los primeros dinosaurios del Triásico ya podían cruzar océanos nadando largas distancias?'),
(11,'¿El Plateosaurus fue uno de los primeros grandes dinosaurios herbívoros?'),
(11,'¿El período Triásico terminó con una extinción masiva?'),
(11,'¿Los fitosaurios tenían las fosas nasales cerca de los ojos, a diferencia de los cocodrilos actuales?'),

(11,'¿Los cinodontos del Triásico presentaban indicios de pelaje y regulación parcial de la temperatura corporal?'),
(11,'¿El Herrerasaurus fue el primer dinosaurio completamente herbívoro de la historia?'),
(11,'¿El Sharovipteryx planeaba utilizando membranas sujetas principalmente a sus patas traseras?'),
(11,'¿Los primeros cocodrilomorfos eran reptiles terrestres bípedos y veloces?'),
(11,'¿El clima del Triásico fue completamente estable y sin variaciones extremas de temperatura?');

-- FOX JUMP!
-- CATEGORÍA 1: PERÍODOS
-- NIVEL INTERMEDIO
-- PERÍODO: JURÁSICO

INSERT INTO Pregunta (id_nivel, pregunta) VALUES

(11,'¿Los saurópodos alcanzaron su mayor diversidad durante el período Jurásico?'),
(11,'¿El Tyrannosaurus rex fue el depredador dominante del período Jurásico?'),
(11,'¿El Stegosaurus vivió durante el período Jurásico?'),
(11,'¿Durante el Jurásico comenzó la fragmentación del supercontinente Pangea?'),
(11,'¿Los pterosaurios dominaron los cielos durante el período Jurásico?'),

(11,'¿El Archaeopteryx vivió durante el período Jurásico y representa un importante vínculo evolutivo entre dinosaurios y aves?'),
(11,'¿El Brachiosaurus tenía las patas traseras más largas que las delanteras?'),
(11,'¿Los plesiosaurios se extinguieron al inicio del período Jurásico?'),
(11,'¿Durante el Jurásico los niveles del mar eran extremadamente bajos y predominaban los desiertos globales?'),
(11,'¿El Allosaurus fue el principal depredador de Norteamérica durante el Jurásico Superior?'),

(11,'¿El Ceratosaurus se diferenciaba del Allosaurus por tener una protuberancia ósea en el hocico?'),
(11,'¿El Amargasaurus habitó la Tierra durante el Jurásico Medio?'),
(11,'¿Los ammonites desaparecieron por completo al inicio del período Jurásico?'),
(11,'¿El Leedsichthys es considerado el pez óseo más grande que ha existido?'),
(11,'¿La aparición de las primeras praderas de césped provocó una gran diversificación de insectos polinizadores durante el Jurásico?');

-- FOX JUMP!
-- CATEGORÍA 1: PERÍODOS
-- NIVEL INTERMEDIO
-- PERÍODO: CRETÁCICO

INSERT INTO Pregunta (id_nivel, pregunta) VALUES

(11,'¿El período Cretácico terminó con una extinción masiva provocada por el impacto de un asteroide?'),
(11,'¿Durante todo el Cretácico no existían las plantas con flores?'),
(11,'¿El Triceratops vivió exclusivamente durante el período Jurásico?'),
(11,'¿El Velociraptor habitó únicamente el período Triásico?'),
(11,'¿Los mosasaurios eran reptiles marinos emparentados con los lagartos y no dinosaurios?'),

(11,'¿El Spinosaurus poseía adaptaciones para un estilo de vida semiacuático?'),
(11,'¿Los tiranosáuridos dominaron principalmente los ecosistemas del hemisferio sur, como África?'),
(11,'¿El Argentinosaurus vivió durante el período Cretácico?'),
(11,'¿El Ankylosaurus utilizaba su cola en forma de mazo como defensa contra los depredadores?'),
(11,'¿Las primeras aves del Cretácico convivieron con los últimos pterosaurios?'),

(11,'¿Los abelisáuridos reemplazaron a los tiranosáuridos como principales depredadores del hemisferio sur?'),
(11,'¿El Therizinosaurus utilizaba sus enormes garras exclusivamente para cazar mamíferos gigantes?'),
(11,'¿Durante el Cretácico ocurrió un importante evento de anoxia oceánica que afectó a numerosas especies marinas?'),
(11,'¿El Quetzalcoatlus tenía una envergadura comparable a la de un avión pequeño?'),
(11,'¿El hallazgo de Microvenator demuestra que el gigantismo fue la única estrategia evolutiva exitosa durante el Cretácico?');

-- FOX JUMP!
-- CATEGORÍA 1: SERES MITOLÓGICOS Y CRÍPTIDOS
-- NIVEL DIFÍCIL

INSERT INTO Pregunta (id_nivel, pregunta) VALUES

(12,'¿La Medusa de la mitología griega tenía serpientes por cabello y convertía en piedra a quien la mirara directamente?'),
(12,'¿La existencia del Bigfoot ha sido confirmada oficialmente por la comunidad científica mediante análisis de ADN?'),
(12,'¿El Kraken era una gigantesca criatura marina capaz de hundir barcos con sus tentáculos según el folclore escandinavo?'),
(12,'¿Anubis era representado con cabeza de lobo en la mitología egipcia?'),
(12,'¿El unicornio es descrito como un caballo con un cuerno y simboliza pureza en las tradiciones europeas?'),
(12,'¿Los primeros reportes del Chupacabras surgieron en Puerto Rico durante la década de 1990?'),
(12,'¿Las sirenas de la mitología griega original eran mitad mujer y mitad pez?'),

(12,'¿El dragón aparece en distintas culturas del mundo y en Oriente suele representar poder y buena fortuna?'),
(12,'¿La existencia del Monstruo del Lago Ness fue confirmada oficialmente por la ciencia?'),
(12,'¿El Minotauro tenía cuerpo de hombre y cabeza de toro y habitaba el Laberinto de Creta?'),
(12,'¿La Hidra de Lerna regeneraba dos cabezas por cada una que le era cortada?'),
(12,'¿La debilidad de los vampiros frente a la luz solar formaba parte del folclore europeo más antiguo?'),
(12,'¿El Fénix es un ave mítica capaz de renacer de sus propias cenizas?'),
(12,'¿La Esfinge griega devoraba a quienes no resolvían correctamente sus acertijos?'),
(12,'¿El Coco es un personaje del folclore exclusivo de España?'),

(12,'¿La Quimera era una criatura con cabeza de león, cuerpo de cabra y cola de serpiente?'),
(12,'¿El Mothman fue visto por primera vez en Japón durante la década de 1960?'),
(12,'¿Jörmungandr es la serpiente gigante que rodea la Tierra en la mitología nórdica?'),
(12,'¿El Wendigo está relacionado con el canibalismo en las leyendas de pueblos indígenas norteamericanos?'),
(12,'¿Los Tengu del folclore japonés siempre fueron considerados protectores benévolos de la humanidad?'),
(12,'¿Según la leyenda medieval, el Basilisco podía matar con la mirada o con su aliento?'),

(12,'¿El Golem de la tradición judía era una criatura de barro animada mediante magia?'),
(12,'¿El Kappa japonés se alimenta exclusivamente de peces?'),
(12,'¿El Leviatán aparece en la Biblia como una gigantesca criatura marina símbolo del caos?'),
(12,'¿La leyenda de La Llorona cuenta que ella misma ahogó a sus hijos?'),
(12,'¿La Banshee siempre aparece como una anciana vestida completamente de negro?'),
(12,'¿El Black Dog del folclore británico es considerado siempre un símbolo de buena suerte?'),

(12,'¿El Black Dog es descrito como un perro espectral de ojos brillantes que suele asociarse con malos presagios?'),
(12,'¿Sleipnir, el caballo de Odín, tenía seis patas?'),
(12,'¿Ammit devoraba el corazón de quienes no superaban el juicio de los muertos en la mitología egipcia?');

-- FOX JUMP!
-- CATEGORÍA 2: PLANTAS
-- NIVEL FÁCIL
-- TEMA: PARTES DE LA PLANTA

INSERT INTO Pregunta (id_nivel, pregunta) VALUES

(13,'¿La función principal de las raíces es absorber agua y nutrientes del suelo'),
(13,'¿El tallo sostiene las hojas, flores y frutos de la planta?'),
(13,'¿Todas las plantas terrestres obtienen su alimento absorbiéndolo directamente del suelo mediante las raíces?'),
(13,'¿Los pelos absorbentes se encuentran en el tallo para proteger a la planta de los insectos?'),
(13,'¿Las flores son las estructuras encargadas de la reproducción sexual en muchas plantas?'),

(13,'¿Los estomas son pequeños poros ubicados principalmente en el envés de las hojas que regulan el intercambio de gases?'),
(13,'¿El xilema transporta la savia elaborada desde las hojas hacia el resto de la planta?'),
(13,'¿Los sépalos forman parte de la estructura interna del pistilo?'),
(13,'¿Las raíces tuberosas, como la zanahoria, almacenan reservas de energía?'),
(13,'¿Los cotiledones son las primeras hojas que emergen de la semilla y alimentan a la plántula?'),

(13,'¿La cofia o caliptra protege la punta del tallo durante su crecimiento?'),
(13,'¿El pecíolo sostiene exclusivamente a las anteras dentro de una flor?'),
(13,'¿Las lenticelas permiten el intercambio de gases en los tallos leñosos con corteza?'),
(13,'¿La cutícula de la hoja es una capa viva que realiza activamente la división celular?'),
(13,'¿Los tricomas pueden secretar sustancias químicas de defensa en la epidermis de la planta?');

-- FOX JUMP!
-- CATEGORÍA 2: PLANTAS
-- NIVEL FÁCIL
-- TEMA: FOTOSÍNTESIS

INSERT INTO Pregunta (id_nivel, pregunta) VALUES

(13,'¿La fotosíntesis es el proceso mediante el cual las plantas producen su propio alimento?'),
(13,'¿Las plantas absorben oxígeno del aire para realizar la fotosíntesis?'),
(13,'¿La luz solar es indispensable para que las plantas realicen la fotosíntesis?'),
(13,'¿Las plantas liberan dióxido de carbono como principal producto de la fotosíntesis?'),
(13,'¿Las plantas realizan principalmente la fotosíntesis durante la noche?'),

(13,'¿La fotosíntesis transforma la energía de la luz solar en energía química almacenada en carbohidratos?'),
(13,'¿Las plantas son organismos autótrofos porque dependen de otros seres vivos para alimentarse?'),
(13,'¿Las raíces contienen altos niveles de clorofila para realizar la fotosíntesis bajo tierra?'),
(13,'¿La fase luminosa de la fotosíntesis ocurre en las mitocondrias?'),
(13,'¿La velocidad de la fotosíntesis permanece constante aunque cambie la temperatura ambiental?'),

(13,'¿Las plantas leñosas realizan el intercambio de gases en sus troncos mediante lenticelas?'),
(13,'¿Existen raíces aéreas capaces de absorber humedad directamente del aire?'),
(13,'¿Los cactus no realizan la fotosíntesis porque sus hojas se transformaron en espinas?'),
(13,'¿La principal función de la epidermis de la hoja es absorber la luz solar para la fotosíntesis?'),
(13,'¿Las nervaduras visibles de las hojas corresponden a los vasos conductores de la planta?');

-- FOX JUMP!
-- CATEGORÍA 2: PLANTAS
-- NIVEL INTERMEDIO
-- TEMA: DIFERENCIA POR ESTRUCTURA Y TAMAÑOS, REPRODUCCIÓN Y SEMILLAS

INSERT INTO Pregunta (id_nivel, pregunta) VALUES

(14,'¿Los arbustos se diferencian de los árboles porque tienen varios troncos delgados en lugar de uno principal?'),
(14,'¿Las plantas herbáceas desarrollan un tallo leñoso y duro que les permite vivir durante décadas?'),
(14,'¿En la reproducción sexual de las plantas es obligatoria la unión de una célula masculina y una femenina?'),
(14,'¿La reproducción asexual o vegetativa produce descendientes genéticamente idénticos a la planta madre?'),
(14,'¿Todas las plantas, incluidos musgos y helechos, producen semillas para propagarse?'),
(14,'¿El fruto de las plantas angiospermas protege la semilla y ayuda a su dispersión?'),
(14,'¿El cotiledón almacena nutrientes para el desarrollo inicial de la nueva planta?'),

(14,'¿Las plantas vasculares superiores poseen tejidos especializados llamados xilema y floema para transportar agua y nutrientes?'),
(14,'¿El tamaño máximo que puede alcanzar una planta depende únicamente de la cantidad de agua disponible en el suelo?'),
(14,'¿Las raíces de los árboles solo sirven para absorber agua y no brindan soporte ni anclaje?'),
(14,'¿La polinización consiste en el traslado del polen desde la antera hasta el estigma de una flor?'),
(14,'¿Las plantas dioicas poseen flores con órganos masculinos y femeninos en la misma flor?'),
(14,'¿Una semilla puede germinar en cualquier momento sin importar factores ambientales como la temperatura?'),
(14,'¿Las semillas de las gimnospermas, como los pinos, se desarrollan desnudas y no están encerradas en un fruto?'),

(14,'¿El crecimiento en grosor de los árboles es producido por un tejido llamado cambium?'),
(14,'¿Las plantas briófitas no pueden alcanzar gran tamaño porque carecen de un sistema vascular verdadero?'),
(14,'¿La doble fecundación es un proceso exclusivo de las plantas angiospermas?'),
(14,'¿La alternancia de generaciones implica una fase haploide y otra diploide en el ciclo de vida de las plantas?'),
(14,'¿La autopolinización garantiza una mayor variabilidad genética que la polinización cruzada?'),
(14,'¿La dormición es un mecanismo que impide que la semilla germine incluso cuando las condiciones externas son favorables?'),
(14,'¿El endospermo siempre permanece presente y visible en todas las semillas maduras de las dicotiledóneas?');

-- FOX JUMP!
-- CATEGORÍA 2: PLANTAS
-- NIVEL DIFÍCIL
-- TEMA: DURACIÓN DE VIDA

INSERT INTO Pregunta (id_nivel, pregunta) VALUES

(15,'¿Las plantas anuales completan su ciclo biológico en un solo año o temporada?'),
(15,'¿Una planta perenne muere inmediatamente después de florecer por primera vez?'),
(15,'¿Las plantas bianuales generalmente necesitan dos años o temporadas para completar su ciclo de vida?'),
(15,'¿Las plantas anuales vuelven a brotar de la misma raíz cada año sin necesidad de producir nuevas semillas?'),
(15,'¿El término "perenne" significa que una planta vive menos de seis meses?'),

(15,'¿Las plantas monocárpicas florecen y producen frutos una sola vez durante toda su vida antes de morir?'),
(15,'¿Una planta perenne caducifolia conserva sus hojas verdes durante todo el invierno?'),
(15,'¿Las plantas bianuales dedican el primer año principalmente al desarrollo vegetativo y el segundo a la reproducción?'),
(15,'¿El clima no influye en el ciclo de vida de una planta anual?'),
(15,'¿La senescencia vegetal es el proceso programado de envejecimiento y muerte de las células de una planta?'),

(15,'¿El bambú puede vivir durante décadas de forma vegetativa y luego florecer masivamente antes de morir?'),
(15,'¿Los árboles perennifolios conservan exactamente las mismas hojas durante toda su vida sin renovarlas?'),
(15,'¿Welwitschia mirabilis es una planta anual del desierto que produce muchas hojas nuevas cada primavera?'),
(15,'¿La vernalización es un periodo de frío prolongado necesario para que muchas plantas bianuales inicien la floración?'),
(15,'¿Las plantas policárpicas agotan todos sus meristemos apicales en su primera floración, impidiendo futuros brotes?');

INSERT INTO Pregunta (id_nivel, pregunta) VALUES

(15,'Las plantas suculentas almacenan agua en sus hojas, tallos o raíces para sobrevivir a largos periodos de sequía.'),
(15,'Las plantas insectívoras u carnívoras obtienen toda su energía exclusivamente de los insectos, sin realizar la fotosíntesis.'),
(15,'Los cactus no se consideran plantas suculentas porque tienen espinas en lugar de hojas carnosas. '),
(15,'Las plantas insectívoras suelen vivir en suelos que son muy ricos en nutrientes y minerales. '),
(15,'Todas las plantas trepadoras necesitan de un soporte externo, como un muro o un árbol, para poder elevarse. '),

(15,'El metabolismo CAM es una adaptación de muchas suculentas que les permite abrir sus estomas por la noche para evitar la pérdida de agua.'),
(15,'Las plantas trepadoras conocidas como "apoyantes" o "escandentes" carecen de órganos de sujeción activos y solo se apoyan en las ramas de otras.'),
(15,'El mecanismo de las trampas de caída en plantas insectívoras (como las plantas jarro) se activa por un movimiento muscular rápido de la planta.'),
(15,'Todas las plantas suculentas pertenecen estrictamente a la familia botánica de las Cactáceas. '),
(15,'Las plantas insectívoras digieren a sus presas utilizando enzimas digestivas o bacterias, absorbiendo los nutrientes a través de sus hojas modificadas.'),

(15,'Las plantas trepadoras epífitas verdaderas comienzan su vida en el suelo y luego pierden sus raíces subterráneas al trepar. '),
(15,'La suculencia celular puede presentarse de forma exclusiva en el tejido de las ventanas foliares, permitiendo que la luz filtre a zonas subterráneas de la hoja. '),
(15,'Los zarcillos de las trepadoras muestran tigmotropismo negativo, lo que significa que se alejan inmediatamente al entrar en contacto con un objeto sólido.'),
(15,'La cutícula gruesa y la reducción de la densidad de estomas en suculentas son adaptaciones anatómicas destinadas a potenciar la transpiración máxima diurna.'),
(15,'Ciertas plantas insectívoras han evolucionado hacia relaciones mutualistas donde ya no digieren insectos, sino que consumen las heces de pequeños mamíferos que usan sus jarros como retretes.');

-- FOX JUMP!
-- CATEGORÍA 3: HÁBITATS
-- NIVEL FÁCIL
-- TEMA: SERES BIÓTICOS

INSERT INTO Pregunta (id_nivel, pregunta) VALUES

(16,'¿Las bacterias y los hongos del suelo son seres bióticos porque transforman la materia orgánica en nutrientes?'),
(16,'¿Un tronco caído y seco se considera un factor abiótico porque ya no tiene funciones vitales?'),
(16,'¿La competencia entre dos especies de aves por utilizar el mismo nido es una interacción biótica?'),
(16,'¿Las plantas verdes son los únicos seres bióticos capaces de introducir energía nueva a los ecosistemas terrestres?'),
(16,'¿Un virus no forma parte de los componentes bióticos porque no puede reproducirse sin un hospedero?');

-- FOX JUMP!
-- CATEGORÍA 3: HÁBITATS
-- NIVEL FÁCIL
-- TEMA: SERES ABIÓTICOS

INSERT INTO Pregunta (id_nivel, pregunta) VALUES

(16,'¿El viento es un factor abiótico que puede modificar el crecimiento y la forma de las plantas?'),
(16,'¿Los factores abióticos únicamente afectan a las plantas y no a los animales?'),
(16,'¿El agua de lluvia siempre tiene un pH completamente neutro sin importar las condiciones atmosféricas?'),
(16,'¿La altitud es un factor abiótico porque modifica la presión atmosférica y la disponibilidad de oxígeno?'),
(16,'¿Los minerales de las rocas dejan de ser importantes para el ecosistema cuando las rocas se desgastan completamente?');

-- FOX JUMP!
-- CATEGORÍA 3: HÁBITATS
-- NIVEL FÁCIL
-- TEMA: AGUAS CONTINENTALES

INSERT INTO Pregunta (id_nivel, pregunta) VALUES

(16,'¿Las aguas continentales incluyen únicamente ríos y lagos superficiales?'),
(16,'¿Los ríos poseen una capacidad natural de autolimpieza gracias al movimiento constante de su agua?'),
(16,'¿Un lago de aguas tranquilas tiene la misma cantidad de oxígeno disponible que un río caudaloso?'),
(16,'¿Las aguas subterráneas se recargan principalmente mediante la filtración de la lluvia a través del suelo y las rocas?'),
(16,'¿Las aguas continentales tienen una salinidad completamente nula y carecen de sales minerales?');

-- FOX JUMP!
-- CATEGORÍA 3: HÁBITATS
-- NIVEL INTERMEDIO

INSERT INTO Pregunta (id_nivel, pregunta) VALUES

(17,'¿En las arenas del desierto, el calor extremo se concentra en la superficie del suelo, pero si un animal excava solo unos centímetros hacia abajo, el hábitat subterráneo mantiene una temperatura drásticamente más fresca.?'),
(17,'¿El hábitat de la sabana se caracteriza por tener una vegetación tan densa, alta y cerrada que impide casi por completo que los animales terrestres puedan divisar el horizonte.?'),
(17,'¿A medida que el hábitat terrestre se eleva en las altas montañas, el aire se vuelve más denso y pesado, lo que facilita que los animales terrestres absorban oxígeno con menos esfuerzo.?'),
(17,'¿El hábitat de la tundra ártica cuenta con una capa de suelo profundamente congelada todo el año que impide que los animales excavadores construyan madrigueras subterráneas profundas.?'),
(17,'¿El suelo de las selvas tropicales es uno de los hábitats terrestres más ricos y profundos en nutrientes del planeta debido a la inmensa cantidad de plantas y árboles que sostiene?'),

(17,'¿En ciertos hábitats de bosques templados y secos, los incendios forestales naturales son necesarios para limpiar el suelo y permitir que nuevas plantas crezcan, renovando el hábitat de los animales.?'),
(17,'¿En los hábitats desérticos de piedra y roca, el agua de las escasas lluvias se evapora en minutos, imposibilitando que existan depósitos naturales o grietas que retengan humedad. ?'),
(17,'¿El hábitat del interior de una cueva terrestre profunda mantiene una temperatura y humedad prácticamente idénticas durante todo el año, sin importar los cambios climáticos del exterior.?'),
(17,'¿En los hábitats terrestres de frío extremo y sequedad, como las mesetas heladas, los restos de plantas y animales se descomponen a una velocidad i?'),
(17,'¿Debido a la inmensa altura y densidad de las copas de los árboles, el suelo de la selva tropical es un hábitat permanentemente oscuro donde llega menos del 5% de la luz solar exterior. ?');

INSERT INTO Pregunta (id_nivel, pregunta) VALUES

(17,'¿En el hábitat marino, la luz del sol penetra con total claridad hasta los rincones más profundos del océano, permitiendo la existencia de plantas verdes en el fondo del mar.?'),
(17,'¿A medida que un animal desciende a zonas más profundas de un hábitat acuático, la presión del agua sobre su cuerpo aumenta de forma masiva debido al peso del líquido que tiene encima.?'),
(17,'¿Las aguas de los hábitats acuáticos tropicales (cálidas) retienen y disuelven una cantidad mucho mayor de oxígeno que las aguas frías de las zonas polares.?'),
(17,'¿Un estuario es un hábitat acuático único donde el agua cambia constantemente de salada a dulce debido a la mezcla diaria entre las mareas del mar y la corriente de un río.?'),
(17,'¿Cuando llega el invierno en hábitats acuáticos de agua dulce muy fríos, los lagos se congelan completamente desde el fondo hacia la superficie, atrapando a todos los peces.?'),

(17,'¿El Mar Muerto es un hábitat acuático tan extremadamente salado que la alta densidad de su agua impide que la mayoría de los peces y plantas comunes puedan sobrevivir en él.?'),
(17,'¿El hábitat del océano abierto (lejos de las costas y a nivel superficial) funciona como un desierto biológico con muy pocos nutrientes disponibles para iniciar la cadena alimenticia.?'),
(17,'¿En el hábitat de los ríos de montaña con corrientes muy rápidas, el agua suele estar estancada y carente de oxígeno debido al constante golpeteo contra las rocas.?'),
(17,'¿En las llanuras más profundas del océano, la temperatura del hábitat se mantiene constante justo por encima del punto de congelación, sin importar si en la superficie hay un clima tropical.?'),
(17,'¿Los arrecifes de coral necesitan habitar exclusivamente en aguas turbias y llenas de lodo flotante para protegerse de los rayos del sol.?'),

(17,'¿Las corrientes térmicas son "ríos invisibles" de aire caliente que suben desde el suelo terrestre hacia el cielo, sirviendo como autopistas que sostienen a las aves en el hábitat aéreo.?'),
(17,'¿En el hábitat aéreo, es mucho más fácil para un animal mantenerse volando si el aire es sumamente caliente y seco, ya que este tipo de aire ofrece mayor soporte a las alas.?'),
(17,'¿Los desfiladeros y cañones rocosos son hábitats aereoterrestres peligrosos pero muy utilizados porque el viento se comprime en ellos, aumentando su velocidad y fuerza de manera drástica.?'),
(17,'¿Cuando el hábitat aéreo se satura por completo de niebla densa o nubes bajas, las aves pierden totalmente la capacidad de orientarse y navegar de forma segura.?'),
(17,'¿El "dosel" (las copas unidas de los árboles más altos de un bosque) es un hábitat aereoterrestre suspendido donde la vida animal casi nunca toca el suelo firme de la tierra.?'),

(17,'¿El hábitat aéreo que se encuentra inmediatamente encima del océano abierto carece de corrientes térmicas hacia arriba, obligando a las aves marinas a aletear sin descanso para no caer.?'),
(17,'¿A medida que los animales ascienden más alto en el hábitat aéreo, se exponen a niveles de radiación solar y rayos ultravioleta mucho más dañinos que los que se reciben en el suelo terrestre.?'),
(17,'¿Las aves migratorias que viajan largas distancias eligen volar siempre dentro del centro de las tormentas eléctricas para aprovechar la energía y velocidad de los rayos a su favor.?'),
(17,'¿El hábitat aéreo puro es un entorno fisiológicamente seco, donde los animales pierden humedad corporal rápidamente a través de la respiración debido al viento constante.?'),
(17,'¿Los acantilados costeros rocosos son hábitats aereoterrestres ideales porque la colisión del viento marino contra la pared de piedra crea una corriente vertical ascendente constante y predecible.?');

-- FOX JUMP!
-- CATEGORÍA 3: HÁBITATS
-- NIVEL DIFICIL 

INSERT INTO Pregunta (id_nivel, pregunta) VALUES

(18,'¿El suelo de la tundra ártica permanece congelado casi todo el año, impidiendo que el agua de lluvia se filtre hacia las profundidades. ?'),
(18,'¿En el suelo de una selva tropical profunda, la luz solar es tan abundante y constante como en la copa de los árboles. ?'),
(18,'¿Los desiertos son hábitats donde la evaporación del agua es mucho mayor que la cantidad de lluvia que se recibe al año. ?'),
(18,'¿El suelo de los pastizales naturales es estéril y carece de nutrientes debido a los incendios constantes que ocurren en su superficie?'),
(18,'¿En los acantilados rocosos, el principal problema del hábitat es la acumulación excesiva de lodo y agua estancada.?');


INSERT INTO Pregunta (id_nivel, pregunta) VALUES
(18,'¿Los hábitats costeros cercanos al mar reciben una lluvia constante de partículas de sal arrastradas por el viento.?'),
(18,'¿En los bosques de alta montaña, la presión atmosférica es menor y los vientos son mucho más débiles que en los valles bajos.?'),
(18,'¿El suelo de un bosque templado durante el invierno entra en un estado de congelación que bloquea temporalmente la disponibilidad de agua líquida ?'),
(18,'¿. Los desiertos fríos, como el de Gobi, tienen las mismas temperaturas altas durante el día que el desierto del Sahara.?'),
(18,'¿. En los suelos de los bosques nublados, la descomposición de la materia orgánica es increíblemente rápida debido al calor del desierto.?');

INSERT INTO Pregunta (id_nivel, pregunta) VALUES
(18,'¿El agua de los lagos y ríos de agua dulce contiene la misma cantidad de sales minerales disueltas que el agua del océano. ?'),
(18,'¿A medida que se desciende a mayor profundidad en un océano o lago grande, la luz del sol disminuye hasta desaparecer por completo?'),
(18,'¿El agua tibia de los ríos tropicales retiene mucho más oxígeno disuelto que el agua helada de los ríos de montaña ?'),
(18,'¿En el fondo de los estanques y pantanos, el suelo suele quedarse sin oxígeno debido al estancamiento del agua y la descomposición.?'),
(18,'¿Las corrientes de agua en los ríos rápidos limpian constantemente las rocas del fondo, impidiendo que se acumule tierra o lodo suelto.?');

INSERT INTO Pregunta (id_nivel, pregunta) VALUES
(18,'¿El hábitat del manglar es una zona de transición donde el agua dulce de los ríos se mezcla constantemente con el agua salada del mar. ?'),
(18,'¿En los lagos congelados durante el invierno, todo el cuerpo de agua desde la superficie hasta el fondo se transforma en un bloque de hielo sólido.?'),
(18,'¿El océano abierto es un hábitat uniforme donde la temperatura del agua es exactamente la misma desde el ecuador hasta los polos.?'),
(18,'¿Las aguas de los arrecifes de coral son famosas por ser turbias, oscuras y llenas de lodo flotante.?'),
(18,'¿Las inundaciones periódicas en las llanuras fluviales cambian drásticamente el hábitat terrestre, convirtiéndolo temporalmente en un entorno acuático.?');

-- MINIJUEGO 2: FOX JUMP!
-- OPCION_RESPUESTA

-- CATEGORÍA 1: ANIMALES — NIVEL FÁCIL (IDs 133–177)
INSERT INTO Opcion_respuesta (id_pregunta, texto_opcion, es_correcta) VALUES
(133,'Verdadero',TRUE),
(134,'Falso',FALSE),
(135,'Verdadero',TRUE),
(136,'Falso',FALSE),
(137,'Verdadero',TRUE),
(138,'Falso',FALSE),
(139,'Verdadero',TRUE),
(140,'Verdadero',TRUE),
(141,'Falso',FALSE),
(142,'Verdadero',TRUE),
(143,'Verdadero',TRUE),
(144,'Verdadero',TRUE),
(145,'Verdadero',TRUE),
(146,'Falso',FALSE),
(147,'Falso',FALSE),
(148,'Verdadero',TRUE),
(149,'Falso',FALSE),
(150,'Verdadero',TRUE),
(151,'Falso',false),
(152,'Verdadero',TRUE),
(153,'Verdadero',TRUE),
(154,'Falso',FALSE),
(155,'Verdadero',TRUE),
(156,'Verdadero',TRUE),
(157,'Verdadero',True),
(158,'Verdadero',TRUE),
(159,'Falso',FALSE),
(160,'Verdadero',TRUE),
(161,'Verdadero',TRUE),
(162,'Falso',false),
(163,'Falso',FALSE),
(164,'Verdadero',TRUE),
(165,'Verdadero',TRUE),
(166,'Falso',FALSE),
(167,'Falso',FALSE),
(168,'Falso',FALSE),
(169,'Verdadero',TRUE),
(170,'Verdadero',TRUE),
(171,'Falso',FALSE),
(172,'Falso',FALSE),
(173,'Falso',FALSE),
(174,'Verdadero',TRUE),
(175,'Verdadero',TRUE),
(176,'Verdadero',TRUE),
(177,'Falso',FALSE);

-- CATEGORÍA 1: ANIMALES — NIVEL INTERMEDIO (IDs 178–222)
INSERT INTO Opcion_respuesta (id_pregunta, texto_opcion, es_correcta) VALUES
(178,'Verdadero',TRUE),
(179,'Falso',FALSE),
(180,'Verdadero',TRUE),
(181,'Falso',FALSE),
(182,'Verdadero',TRUE),
(183,'Verdadero',TRUE),
(184,'Falso',FALSE),
(185,'Verdadero',TRUE),
(186,'Verdadero',tRUE),
(187,'Verdadero',TRUE),
(188,'Verdadero',TRUE),
(189,'Falso',FALSE),
(190,'Verdadero',TRUE),
(191,'Verdadero',TRUE),
(192,'Falso',FALSE),
(193,'Verdadero',TRUE),
(194,'Falso',FALSE),
(195,'Verdadero',TRUE),
(196,'Verdadero',TRUE),
(197,'Verdadero',TRUE),
(198,'Verdadero',TRUE),
(199,'Falso',FALSE),
(200,'Falso',FALSE),
(201,'Falso',FALSE),
(202,'Verdadero',TRUE),
(203,'Verdadero',TRUE),
(204,'Falso',FALSE),
(205,'Falso',FALSE),
(206,'Verdadero',TRUE),
(207,'Falso',FALSE),
(208,'Verdadero',TRUE),
(209,'Falso',FALSE),
(210,'Falso',FALSE),
(211,'Falso',FALSE),
(212,'Verdadero',TRUE),
(213,'Verdadero',TRUE),
(214,'Falso',FALSE),
(215,'Verdadero',TRUE),
(216,'Verdadero',TRUE),
(217,'Verdadero',TRUE),
(218,'Verdadero',TRUE),
(219,'Falso',FALSE),
(220,'Verdadero',TRUE),
(221,'Verdadero',TRUE),
(222,'Falso',FALSE);

-- CATEGORÍA 1: ANIMALES — NIVEL DIFÍCIL (IDs 223–237)
INSERT INTO Opcion_respuesta (id_pregunta, texto_opcion, es_correcta) VALUES
(223,'Verdadero',TRUE),
(224,'Falso',FALSE),
(225,'Verdadero',TRUE),
(226,'Falso',FALSE),
(227,'Verdadero',TRUE),
(228,'Verdadero',TRUE),
(229,'Falso',FALSE),
(230,'Verdadero',TRUE),
(231,'Falso',FALSE),
(232,'Verdadero',TRUE),
(233,'Verdadero',TRUE),
(234,'Falso',FALSE),
(235,'Verdadero',TRUE),
(236,'Verdadero',TRUE),
(237,'Falso',FALSE),
(238,'Verdadero',TRUE),
(239,'Falso',FALSE),
(240,'Verdadero',TRUE),
(241,'Verdadero',True),
(242,'Falso',False),
(243,'Verdadero',True),
(244,'Verdadero',True),
(245,'Falso',False),
(246,'Verdadero',True),
(247,'Verdadero',TRUE),
(248,'Falso',False),
(249,'Falso',False),
(250,'Verdadero',True),
(251,'Falso',False),
(252,'Vedadero',True);

-- CATEGORÍA 2: PLANTAS — NIVEL FÁCIL (IDs 238–262)
INSERT INTO Opcion_respuesta (id_pregunta, texto_opcion, es_correcta) VALUES
(253,'Verdadero',TRUE),
(254,'Verdadero',TRUE),
(255,'Falso',FALSE),
(256,'Falso',FALSE),
(257,'Verdadero',TRUE),
(258,'Verdadero',TRUE),
(259,'Falso',FALSE),
(260,'Falso',FALSE),
(261,'Verdadero',TRUE),
(262,'Verdadero',TRUE),
(263,'Falso',FALSE),
(264,'Falso',FALSE),
(265,'Verdadero',TRUE),
(266,'Falso',FALSE),
(267,'Verdadero',TRUE),
(268,'Verdadero',TRUE),
(269,'Falso',FALSE),
(270,'Verdadero',TRUE),
(271,'Falso',FALSE),
(272,'Falso',FALSE),
(273,'Verdadero',TRUE),
(274,'Falso',FALSE),
(275,'Falso',FALSE),
(276,'Falso',FALSE),
(277,'Falso',FALSE),
(278,'Verdadero',TRUE),
(279,'Verdadero',True),
(280,'Falso',FALSE),
(281,'Falso',FALSE),
(282,'Falso',FALSE);

-- CATEGORÍA 2: PLANTAS — NIVEL INTERMEDIO (IDs 263–283)
INSERT INTO Opcion_respuesta (id_pregunta, texto_opcion, es_correcta) VALUES
(283,'Verdadero',TRUE),
(284,'Falso',FALSE),
(285,'Verdadero',TRUE),
(286,'Verdadero',TRUE),
(287,'Falso',FALSE),
(288,'Verdadero',TRUE),
(289,'Verdadero',TRUE),
(290,'Verdadero',True),
(291,'Falso',FALSE),
(292,'Falso',False),
(293,'Verdadero',True),
(294,'Falso',FALSE),
(295,'Falso',False),
(296,'Verdadero',TRUE),
(297,'Verdadero',True),
(298,'Verdadero',TRUE),
(299,'Verdadero',TRUE),
(300,'Verdadero',True),
(301,'Falso',false),
(302,'Verdadero',TRUE),
(303,'Falso',FALSE);

-- CATEGORÍA 2: PLANTAS — NIVEL DIFÍCIL (IDs 284–313)
INSERT INTO Opcion_respuesta (id_pregunta, texto_opcion, es_correcta) VALUES
(304,'Verdadero',TRUE),
(305,'Falso',FALSE),
(306,'Verdadero',TRUE),
(307,'Falso',FALSE),
(308,'Falso',FALSE),
(309,'Verdadero',TRUE),
(310,'Falso',FALSE),
(311,'Falso',FALSE),
(312,'Verdadero',TRUE),
(313,'Verdadero',TRUE),
(314,'Falso',FALSE),
(315,'Verdadero',TRUE),
(316,'Falso',FALSE),
(317,'Verdadero',TRUE),
(318,'Falso',FALSE),
(319,'Verdadero',TRUE),
(320,'Falso',FALSE),
(321,'Falso',FALSE),
(322,'Falso',FALSE),
(323,'Verdadero',TRUE),
(324,'Verdadero',TRUE),
(325,'Verdadero',TRUE),
(326,'Falso',FALSE),
(327,'Falso',FALSE),
(328,'Verdadero',TRUE),
(329,'Falso',FALSE),
(330,'Verdadero',TRUE),
(331,'Falso',FALSE),
(332,'Falso',FALSE),
(333,'Verdadero',TRUE);

-- CATEGORÍA 3: HÁBITATS — NIVEL FÁCIL (IDs 314–328)
INSERT INTO Opcion_respuesta (id_pregunta, texto_opcion, es_correcta) VALUES
(334,'Verdadero',TRUE),
(335,'Falso',FALSE),
(336,'Verdadero',TRUE),
(337,'Falso',FALSE),
(338,'Falso',FALSE),
(339,'Verdadero',TRUE),
(340,'Falso',FALSE),
(341,'Falso',FALSE),
(342,'Verdadero',TRUE),
(343,'Falso',FALSE),
(344,'Falso',FALSE),
(345,'Verdadero',TRUE),
(346,'Falso',FALSE),
(347,'Verdadero',TRUE),
(348,'Falso',FALSE);

-- CATEGORÍA 3: HÁBITATS — NIVEL INTERMEDIO (IDs 329–358)
INSERT INTO Opcion_respuesta (id_pregunta, texto_opcion, es_correcta) VALUES
(349,'Verdadero',TRUE),
(350,'Falso',FALSE),
(351,'Falso',FALSE),
(352,'Verdadero',TRUE),
(353,'Falso',FALSE),
(354,'Verdadero',TRUE),
(355,'Falso',FALSE),
(356,'Verdadero',TRUE),
(357,'Falso',FALSE),
(358,'Verdadero',TRUE),
(359,'Falso',FALSE),
(360,'Verdadero',TRUE),
(361,'Falso',FALSE),
(362,'Verdadero',TRUE),
(363,'Falso',FALSE),
(364,'Verdadero',TRUE),
(365,'Verdadero',TRUE),
(366,'Falso',FALSE),
(367,'Verdadero',TRUE),
(368,'Falso',FALSE),
(369,'Verdadero',TRUE),
(370,'Falso',FALSE),
(371,'Verdadero',TRUE),
(372,'Falso',FALSE),
(373,'Verdadero',TRUE),
(374,'Falso',FALSE),
(375,'Verdadero',TRUE),
(376,'Falso',FALSE),
(377,'Verdadero',TRUE),
(378,'Verdadero',TRUE);

-- CATEGORÍA 3: HÁBITATS — NIVEL DIFÍCIL (IDs 359–378)
INSERT INTO Opcion_respuesta (id_pregunta, texto_opcion, es_correcta) VALUES
(379,'Verdadero',TRUE),
(380,'Falso',FALSE),
(381,'Verdadero',TRUE),
(382,'Falso',FALSE),
(383,'Falso',FALSE),
(384,'Verdadero',TRUE),
(385,'Falso',FALSE),
(386,'Verdadero',TRUE),
(387,'Falso',FALSE),
(388,'Falso',FALSE),
(389,'Falso',FALSE),
(390,'Verdadero',TRUE),
(391,'Falso',FALSE),
(392,'Verdadero',TRUE),
(393,'Verdadero',TRUE),
(394,'Verdadero',TRUE),
(395,'Falso',FALSE),
(396,'Falso',FALSE),
(397,'Falso',FALSE),
(398,'Verdadero',TRUE);

-- CATEGORIA 1
-- NIVEL FACIL, INTERMEDIO Y DIFICIL 
INSERT INTO Ayuda (id_pregunta, tipo, contenido) VALUES
(133,'texto','El planteamiento afirma que posee el récord vigente como el animal terrestre más grande y pesado del planeta.'),
(134,'texto','El núcleo del enunciado radica en si todos los miembros de la especie se mantienen estrictamente dentro de un "orgullo" o manada.'),
(135,'texto','Plantea si este felino, conocido también con otro nombre corto, es efectivamente el corredor terrestre más rápido del mundo.'),
(136,'texto','Invita a evaluar si la fisonomía de este gran mamífero se condice con un entorno de calor y selva tropical.'),
(137,'texto','Relaciona el propósito único de su alimentación en las alturas con el desarrollo evolutivo de su anatomía.'),
(138,'texto','Cuestiona la composición real y la función biológica de las características protuberancias de este mamífero.'),
(139,'texto','Describe los fulminantes efectos fisiológicos provocados por la mordedura de esta veloz serpiente.'),
(140,'texto','Describe esta parte de su cuerpo como un muelle esencial para el equilibrio dinámico durante sus desplazamientos.'),
(141,'texto','Pone a prueba la frecuencia con la que esta criatura asume el riesgo de bajar a la superficie terrestre.'),
(142,'texto','El enunciado afirma que este mamífero recurre a la puesta de huevos para lograr multiplicarse.'),
(143,'texto','Vincula la necesidad biológica de morder madera de forma constante con el control de sus estructuras dentales.'),
(144,'texto','El enunciado afirma categóricamente que este animal expulsa chorros de sangre a través de los ojos.'),
(145,'texto','Sostiene que el oxígeno accede al organismo mediante conductos y diminutos poros llamados espiráculos.'),
(146,'texto','Plantea si la estructura ósea de sus patas delanteras se compone de codos o de rodillas verdaderas.'),
(147,'texto','El dilema gira en torno a si el material de este cuerno es de origen óseo o si está compuesto por otra proteína.'),
(148,'texto','Afirma que estos animales tienen la obligación biológica estricta de romper la superficie marina para respirar.'),
(149,'texto','Sostiene que su soporte anatómico interno está conformado por piezas de hueso macizo y denso.'),
(150,'texto','Plantea un intercambio de favores donde la protección se paga mediante labores de limpieza sanitaria.'),
(151,'texto','Pone a debate la existencia de un órgano pensante centralizado en medio del cuerpo de este equinodermo.'),
(152,'texto','La afirmación postula de manera directa que este cetáceo es, sin duda, el animal más grande del planeta Tierra.'),
(153,'texto', 'Plantea la distribución de oxígeno bajo el agua a partir de múltiples órganos cardíacos en un solo cuerpo.'),
(154,'texto', 'Evalúa si es posible para este espécimen prescindir del aire atmosférico por periodos de tiempo tan extensos.'),
(155,'texto','Destaca su supervivencia y movimientos en el ecosistema marino a pesar de carecer de fluido sanguíneo y cerebro.'),
(156,'texto','Sostiene que el resultado de esta acción es una esfera rígida y espinosa impracticable de tragar para un rival.'),
(157,'texto','El enunciado afirma que es el macho quien aloja, fertiliza y protege los óvulos dentro de una bolsa especializada.'),
(158,'texto','Dictamina que este pez se ve sentenciado a mantener un nado ininterrumpido toda su vida para no perecer ahogado.'),
(159,'texto','Plantea si el espécimen verdaderamente camina distancias desérticas como un lagarto gracias a sus pulmones.'),
(160,'texto','Asegura que este coloso marino es en realidad el integrante de mayor envergadura dentro de la familia de los delfines.'),
(161,'texto','El enunciado equipara directamente el diámetro de sus globos oculares con las dimensiones de un balón de fútbol.'),
(162,'texto','El dilema consiste en descifrar si estas estructuras son materia geológica o acumulaciones orgánicas de seres vivos.'),
(163,'texto','Plantea si la evolución adaptativa de sus extremidades superiores está orientada al desplazamiento por el aire o a la hidrodinámica marina.'),
(164,'texto','Analiza si la biomecánica de su aleteo le permite romper las reglas tradicionales del desplazamiento aéreo.'),
(165,'texto','Cuestiona la naturaleza taxonómica de este animal, dividida popularmente entre el mundo de las aves y el de los mamíferos.'),
(166,'texto','Pone a prueba si la estructura y el diseño de sus plumas actúan como un amplificador o como un amortiguador acústico frente al viento.'),
(167,'texto','Invita a evaluar si existen excepciones de desarrollo físico o especies enteras que rompan con esta afirmación generalizada.'),
(168,'texto','El dilema radica en si esta icónica ave migratoria posee la anatomía para subsistir continuamente en el aire o si se la confunde con otra especie.'),
(169,'texto','Describe la magnitud física y la letalidad de las extremidades de esta gigantesca ave rapaz.'),
(170,'texto','El planteamiento describe esta conducta interactiva bajo el concepto de "la danza" de las abejas.'),
(171,'texto','Plantea si la bolsa sirve como un almacén prolongado de víveres o si se limita a ser una herramienta de captura inmediata.'),
(172,'texto','Cuestiona si la naturaleza biológica de esta ave es de carácter cazador activo o si sus patas están diseñadas para la carroña.'),
(173,'texto','Pone a debatir si el color es heredado en el cascarón o si se adquiere de forma progresiva a través de factores externos como la dieta.'),
(174,'texto','Describe el extremo desempeño aerodinámico de este halcón al lanzarse en picada contra sus objetivos.'),
(175,'texto','Alude a la increíble eficiencia energética de este planeador de largas distancias.'),
(176,'texto','Describe los complejos mecanismos de navegación biológica internos que impiden que este lepidóptero pierda el rumbo.'),
(177,'texto','Plantea el dilema de si esta cola es un escudo protector para pasar inadvertido o una herramienta de cortejo sexual.'),
(178,'texto','El enunciado afirma que este nacimiento zoológico ocurrió de la mano de los arcosaurios primitivos durante el inicio de la Era Mesozoica.'),
(179,'texto','Afirma categóricamente que el fin de los grandes reptiles fue el requisito indispensable para el nacimiento de la contraparte con pelaje.'),
(180,'texto','Asegura que la totalidad de la corteza terrestre emergida formaba un bloque único bautizado por la ciencia con un prefijo que significa "todo".'),
(181,'texto','Ubica conceptualmente este periodo en la última posición de la fila, sirviendo como la despedida de su respectiva era geológica.'),
(182,'texto','El enunciado detalla que este reptil carnívoro se desplazaba erguido sobre sus dos extremidades traseras con gran velocidad.'),
(183,'texto','Señala que los cinodontos y dicinodontos eran, en realidad, los verdaderos soberanos en número durante los albores de este periodo.'),
(184,'texto','Afirma de manera contundente que la anatomía de estas primeras criaturas ya estaba perfectamente adaptada para surcar distancias marinas.'),
(185,'texto','Plantea que este género específico funcionó en la naturaleza como el antepasado directo de los futuros gigantes de la alimentación vegetal.'),
(186,'texto','Vincula la desaparición absoluta e inmediata de todos los dinosaurios de este periodo con el ascenso instantáneo de la megafauna mamífera.'),
(187,'texto','Sostiene que estos cazadores triásicos tenían sus fosas nasales localizadas a una distancia milimétrica de sus propios órganos visuales.'),
(188,'texto','El enunciado afirma que hacia el final del periodo, estos seres ya manifestaban indicios de pelaje y una termorregulación interna parcial.'),
(189,'texto','Afirma que este antiquísimo dinosaurio ostenta el título indiscutible de ser el primer comedor de vegetación puro en la historia de la vida.'),
(190,'texto','Especifica que las membranas delgadas utilizadas para surcar los cielos se encontraban ancladas principalmente a sus patas traseras.'),
(191,'texto','Detalla que a mediados de este periodo estos seres eran delgados, netamente terrestres y utilizaban exclusivamente dos extremidades para desplazarse rápido.'),
(192,'texto','Asegura que la geografía de Pangea propició una estabilidad climática perfecta y constante, erradicando los monzones del mapa meteorológico.'),
(193,'texto','El enunciado ubica el punto más alto de diversificación y esplendor de estos colosos de cola pesada justamente a lo largo de este periodo medio.'),
(194,'texto','Afirma de forma directa que este gigantesco carnívoro de mandíbulas trituradoras era el elemento más común en la cima de la cadena alimenticia de este periodo.'),
(195,'texto','Se enfoca en un dinosaurio caracterizado por placas óseas alineadas verticalmente en su espalda, confirmando su presencia en esta etapa de la historia.'),
(196,'texto','Sostiene que este fenómeno de división y deriva continental comenzó a manifestarse de manera gradual a medida que avanzaba este periodo en particular.'),
(197,'texto','Postula que este conjunto de seres vivos logró adueñarse de la bóveda celeste de forma absoluta durante toda la transición de este marco temporal.'),
(198,'texto','El texto sitúa cronológicamente la existencia de este eslabón perdido clave en los ecosistemas correspondientes a este periodo geológico.'),
(199,'texto','El enunciado detalla que este saurópodo tenía la extraña característica de poseer sus patas traseras mucho más largas y desarrolladas que las delanteras.'),
(200,'texto','Afirma que el hambre provocada por la escasez extrema de presas marinas exterminó a este grupo de depredadores al inicio de esta etapa geológica.'),
(201,'texto','Sostiene de forma contundente que los niveles globales del mar eran sumamente bajos durante los años que comprenden este periodo histórico.'),
(202,'texto','Postula que este terópodo en particular era el carnívoro alfa indiscutible y más exitoso de la región septentrional de América.'),
(203,'texto','Afirma que este carnívoro se diferenciaba físicamente del Allosaurus gracias a una pequeña protuberancia o cuerno situado en su hocico.'),
(204,'texto','Sitúa de forma explícita el hogar temporal de este saurópodo con espinas en el cuello dentro de la época correspondiente al ecuador cronológico de este periodo.'),
(205,'texto','El enunciado asegura que estos cefalópodos sufrieron un proceso de extinción total y absoluta justo al dar comienzo este periodo geológico.'),
(206,'texto','Sostiene que este pez de dimensiones colosales basaba su dieta exclusivamente en el filtrado de plancton a través de sus estructuras branquiales.'),
(207,'texto','Asegura que hacia las fases finales del periodo, la multiplicación de insectos voladores se disparó gracias a la repentina aparición de praderas de césped.'),
(208,'texto','El enunciado vincula directamente el cataclismo de un asteroide gigante con la extinción masiva que borró la hegemonía reptiliana.'),
(209,'texto','Afirma de forma tajante que a lo largo de todo este tiempo las angiospermas (plantas con flores) no consiguieron hacer acto de presencia.'),
(210,'texto','El texto postula que la existencia y el esplendor de esta famosa criatura acorazada ocurrieron de forma exclusiva en el periodo Jurásico.'),
(211,'texto','Sostiene formalmente que este hábil carnívoro de garras retráctiles habitó la Tierra de manera exclusiva durante las fases iniciales del Triásico Medio.'),
(212,'texto','Sostiene que estos gigantescos nadadores con aletas pertenecían en realidad a la familia de los lagartos modernos y no eran verdaderos dinosaurios.'),
(213,'texto','Detalla que este coloso lucía un rostro alargado de aspecto cocodriliano y extremidades densas perfectamente diseñadas para una vida semiacuática.'),
(214,'texto','Afirma de forma directa que estos imponentes depredadores colonizaron y gobernaron los ecosistemas correspondientes al hemisferio sur, como África.'),
(215,'texto','La afirmación sostiene que este gigantesco habitante de las antiguas tierras sudamericanas desarrolló su ciclo de vida de forma exclusiva durante el periodo Cretácico.'),
(216,'texto','Postula que este acorazado empleaba una pesada y compacta masa de hueso macizo en su cola a modo de mazo para fracturar las piernas de sus enemigos.'),
(217,'texto','Asegura que estas variedades aviares compartieron nichos ecológicos aéreos y compitieron de forma activa contra los últimos pterosaurios libres del planeta.'),
(218,'texto','Afirma que los abelisáuridos reemplazaron con éxito absoluto a los tiranosáuridos en las masas continentales del antiguo supercontinente de Gondwana.'),
(219,'texto','Asegura que este imponente animal empleaba sus garras de hasta un metro de longitud exclusivamente para cazar y destripar a los primeros mamíferos gigantes.'),
(220,'texto','El texto postula que a mitad del periodo aconteció un severo evento de falta de oxígeno disuelto, provocando extinciones masivas de incontables especies marinas.'),
(221,'texto','Sostiene que este gigante de los cielos poseía una envergadura de alas totalmente equivalente al tamaño real de un avión pequeño de pasajeros moderno.'),
(222,'texto','El enunciado afirma que el hallazgo de estos pequeños ejemplares demuestra de forma contundente que el gigantismo extremo fue la única vía evolutiva viable y exitosa.'),
(223,'texto','Es uno de los mitos más temidos y célebres de la antigua Grecia.'),
(224,'texto','Su rastro se compone principalmente de avistamientos borrosos e historias populares.'),
(225,'texto','Es la personificación del miedo ancestral a los peligros desconocidos del océano.'),
(226,'texto','Su iconografía tradicional destaca por tener rasgos de un cánido sagrado.'),
(227,'texto','Los relatos afirman que solo los corazones desprovistos de maldad podían acercarse a él.'),
(228,'texto','Las crónicas originales sitúan el origen de su histeria colectiva en una isla del Caribe.'),
(229,'texto','Originalmente, el mito griego las vinculaba a una fisionomía híbrida muy distinta a la de un pez.'),
(230,'texto','Su figura varía drásticamente según la mitología continental que lo describa.'),
(231,'texto','A pesar del escepticismo de la ciencia, sigue atrayendo a miles de entusiastas al norte del Reino Unido.'),
(232,'texto','Su reinado de terror terminó gracias a la astucia y valentía del héroe Teseo.'),
(233,'texto','Formó parte de la célebre lista de desafíos extremos impuestos al semidiós Hércules.'),
(234,'texto','Aspectos como su letalidad ante el amanecer no siempre formaron parte de los mitos primitivos.'),
(235,'texto','Está ligada perpetuamente a los ciclos de fuego, muerte y glorioso renacimiento.'),
(236,'texto','Su caída final ocurrió cuando un héroe trágico descifró su enigma más famoso.'),
(237,'texto','Aunque carece de una forma fija, infunde un temor universal en la infancia hispanohablante.'),
(238,'texto','Fue derrotada desde los aires por un héroe que cabalgaba una montura alada.'),
(239,'texto','Su leyenda urbana más famosa se concentra en el pueblo de Point Pleasant durante los años 60.'),
(240,'texto','Su destino final está sellado en una batalla a muerte contra el dios del trueno.'),
(241,'texto','Se dice que el aislamiento y la desesperación en los bosques pueden transformar a un hombre en este ser.'),
(242,'texto','Aunque hoy se les ve a veces como protectores, los mitos antiguos los retrataban como seres engañosos y peligrosos.'),
(243,'texto','Los mitos señalan que su invencibilidad se quebraba si se le obligaba a observar su propia imagen.'),
(244,'texto','La narrativa más célebre lo ubica recorriendo los callejones de la Praga del siglo XVI.'),
(245,'texto','Los relatos advierten que engañarlo para que haga una reverencia es la clave para debilitarlo.'),
(246,'texto','Su imponencia es descrita con gran detalle en los pasajes poéticos del Libro de Job.'),
(247,'texto','Forma parte de las identidades folclóricas más profundas y temidas de México y Centroamérica.'),
(248,'texto','Históricamente se decía que estaba vinculada a los linajes familiares más antiguos de Irlanda.'),
(249,'texto','Aunque algunas variantes lo ven como un guardián, la mayoría de los relatos le temen como un mal augurio.'),
(250,'texto','Un can espectral de ojos incandescentes que se materializa en los solitarios caminos británicos.'),
(251,'texto','Su origen es fruto de una de las tretas de transformación más bizarras del dios Loki.'),
(252,'texto','Una temible criatura del tribunal de Osiris que aguarda el veredicto final de los corazones.');

-- CATEGORIA 2
-- NIVEL FACIL, INTERMEDIO Y DIFICIL
INSERT INTO Ayuda (id_pregunta, tipo, contenido) VALUES
(253,'texto','Actúa como el sistema de tuberías inicial que recolecta los minerales del entorno.'),
(254,'texto','Es la vía de tránsito principal por donde viajan los recursos hacia arriba y hacia abajo.'),
(255,'texto','Esta idea confunde la materia prima inorgánica con el alimento real que la planta fabrica.'),
(256,'texto','Si estuvieran expuestas al aire libre en la superficie del tallo, se secarían rápido y no podrían absorber nada.'),
(257,'texto','Es la etapa previa indispensable para que la mayoría de las plantas generen semillas.'),
(258,'texto','Controlan el delicado equilibrio de la planta entre capturar aire y no morir deshidratada.'),
(259,'texto','Confunde el canal de entrada de la materia prima con el canal de reparto del producto final de azúcar.'),
(260,'texto','Ubica una pieza del escudo protector exterior en la zona más íntima e interna de la flor.'),
(261,'texto','Es una estrategia de ahorro energético vegetal que los humanos aprovechamos directamente en la cocina.'),
(262,'texto','Al germinar un frijol, corresponden a esas dos mitades carnosas que se abren al principio del brote.'),
(263,'texto','Protege una punta vital, pero en la zona que se adentra hacia las profundidades de la tierra, no en el aire.'),
(264,'texto','Si cortas el pecíolo de un árbol, verás caer hojas al suelo, no partes reproductivas florales.'),
(265,'texto','Su presencia asegura que las células vivas que están dentro del tronco no mueran asfixiadas.'),
(266,'texto','Es una cubierta protectora externa y acelular, por lo que carece de actividad reproductiva o metabólica viva.'),
(267,'texto','Son apéndices de la epidermis que actúan como la primera línea de guerra química del vegetal.'),
(268,'texto','Transforma elementos inertes del entorno en materia orgánica llena de energía utilizable.'),
(269,'texto','Si absorbieran este gas para su producción, estarían compitiendo directamente con nuestra propia respiración.'),
(270,'texto','En un escenario de oscuridad total y prolongada, esta función se apaga por completo y la planta muere de hambre.'),
(271,'texto','El enunciado plantea el intercambio gaseoso exactamente al revés de como beneficia a la atmósfera de la Tierra.'),
(272,'texto','Intentar operar este sistema a oscuras sería biológicamente imposible para las plantas comunes.'),
(273,'texto','Logra que la energía pura que viaja por el espacio quede atrapada en un formato almacenable y comestible.'),
(274,'texto','Las plantas son el inicio de las cadenas alimenticias precisamente porque no consumen a otros seres.'),
(275,'texto','El subsuelo es un entorno oscuro donde la maquinaria de captura solar resultaría inútil.'),
(276,'texto','Las células animales poseen mitocondrias, pero son totalmente incapaces de procesar la luz por carecer del organelo correcto.'),
(277,'texto','Ignora el hecho de que cada especie vegetal posee un rango climático ideal para trabajar a su máxima velocidad.'),
(278,'texto','Estas pequeñas aberturas en la corteza suplen la falta de estomas en las zonas donde no hay hojas verdes.'),
(279,'texto','Funcionan casi como redes de captura de niebla integradas directamente en el cuerpo expuesto de la planta.'),
(280,'texto','Siguen generando carbohidratos de forma autótrofa, pero reubicaron los talleres encargados de la tarea.'),
(281,'texto','Confunde la función de una ventana protectora con la de los obreros internos que procesan la energía solar.'),
(282,'texto','El enunciado afirma que estas líneas visibles son falsas o puramente estéticas, ignorando su rol circulatorio fundamental.'),
(283,'texto','Piensa si la presencia de múltiples ejes leñosos delgados en lugar de un gran tronco central es la característica clave para clasificarlos visualmente.'),
(284,'texto','Considera si el tallo de estas plantas se mantiene verde y tierno, o si se vuelve duro como el de los árboles con el paso de los años.'),
(285,'texto','Analiza si es posible hablar de reproducción sexual en el reino vegetal si no se produce la unión de una célula masculina y una femenina.'),
(286,'texto','Determina si el resultado de este proceso es un clon exacto del organismo original o si presenta diferencias genéticas espontáneas.'),
(287,'texto','Piensa si evolutivamente todas las plantas desarrollaron la compleja estructura protectora y nutritiva que llamamos semilla, o si algunas usan esporas.'),
(288,'texto','Considera si el fin biológico de esta envoltura es servir de barrera protectora para los embriones vegetales y facilitar que viajen lejos de la planta madre.'),
(289,'texto','Evalúa si esta sección funciona como un almacén o reserva alimenticia inicial para que el embrión rompa la cubierta de la semilla.'),
(290,'texto','Reflexiona si los nombres técnicos "xilema" y "floema" corresponden a estos tejidos especializados de transporte.'),
(291,'texto','Analiza si afirmar que un solo factor ambiental es el "único" responsable del tamaño máximo es correcto o una simplificación biológica.'),
(292,'texto','Determina si la función de absorber agua excluye mecánicamente la capacidad de la raíz para fijar sólidamente la planta al terreno.'),
(293,'texto','Considera si el trayecto exacto termina específicamente cuando el polen aterriza en la estructura receptiva femenina llamada estigma.'),
(294,'texto','Evalúa si las plantas dioicas son las que tienen ambos sexos conviviendo en la misma flor, o si esa descripción pertenece a las plantas hermafroditas.'),
(295,'texto','Piensa si una semilla podría activarse y crecer con éxito en cualquier condición extrema o si requiere un equilibrio de factores específicos.'),
(296,'texto','Analiza si las semillas de estas plantas se encuentran alojadas dentro de un fruto verdadero o si carecen de dicha cobertura carnosa o protectora derivada de un ovario.'),
(297,'texto','Recuerda si el nombre de este tejido celular especializado en generar nuevas capas de xilema y floema es el "cambium".'),
(298,'texto','Deduce si la ausencia de un sistema vascular real es el factor determinante que les impide físicamente erguirse y crecer como estructuras altas.'),
(299,'texto','Determina si este fenómeno es una característica evolutiva compartida por todas las plantas o si es exclusivo del grupo de las angiospermas (plantas con flores).'),
(300,'texto','Evalúa si la alternancia biológica significa pasar sucesivamente de una etapa celular haploide a una etapa diploide para completar el ciclo.'),
(301,'texto','Piensa si autofecundarse incrementa las combinaciones genéticas nuevas en la descendencia o si, por el contrario, tiende a uniformarla.'),
(302,'texto','Considera si este mecanismo bloquea activamente la germinación incluso si colocamos la semilla en un ambiente húmedo y con temperatura agradable.'),
(303,'texto','Analiza si el endospermo permanece siempre intacto y visible en todas las dicotiledóneas maduras, o si es consumido antes de que la semilla termine de madurar.'),
(304,'texto','No guardan energía en raíces para brotar al año siguiente; la continuidad de su estirpe depende exclusivamente de las semillas que dejen caer.'),
(305,'texto','Contrasta el concepto de una planta que renace de sus propias estructuras cada primavera con la idea de una muerte fulminante tras florecer.'),
(306,'texto','Su nombre suena muy similar al prefijo lingüístico que usamos para describir eventos que ocurren cada dos años.'),
(307,'texto','Determina si el marco estricto de "un solo año" le permite a la estructura subterránea permanecer latente y viable para el futuro lejano.'),
(308,'texto','Explora si el prefijo "per-" sugiere atravesar el tiempo de forma continua o si simplemente se limita a una breve y efímera temporada.'),
(309,'texto','Contrasta esta estrategia con la de un árbol que da frutos recurrentemente cada verano sin que eso dicte el fin de su existencia.'),
(310,'texto','Mantener hojas activas bajo la nieve consume valiosa agua; analiza si una planta con hojas "caducas" asumiría ese riesgo metabólico.'),
(311,'texto','Si emplearan su energía en florecer de inmediato en su primer verano, el concepto de ciclo "bianual" perdería su sentido estructural.'),
(312,'texto','Considera si la etiqueta temporal de una planta es una condena genética grabada en piedra o si muestra plasticidad según el entorno.'),
(313,'texto','Funciona como el equivalente botánico al envejecimiento programado y la apoptosis celular que experimentan los animales.'),
(314,'texto','Combina una longevidad notable en fase verde con un final de ciclo masivo, coordinado y destructivo para la planta madre.'),
(315,'texto','Existe un proceso discreto, paulatino y constante de descarte y renovación foliar a lo largo de los meses.'),
(316,'texto','Su desarrollo es extremadamente pausado y longevo, situándose en el extremo opuesto de la estrategia de las plantas efímeras.'),
(317,'texto','Sin experimentar este estímulo invernal previo, muchas plantas bienales continuarían generando únicamente hojas de forma indefinida.'),
(318,'texto','Su diseño evolutivo guarda cartuchos energéticos de reserva para asegurar que la floración de la temporada actual no dicte su muerte inmediata.'),
(319,'texto','Su anatomía interna está adaptada para operar como una esponja orgánica cuando ocurren precipitaciones esporádicas.'),
(320,'texto','El sol sigue siendo el motor de la cadena alimenticia para casi todo el reino vegetal; evalúa si este grupo rompe al 100% con esa regla básica.'),
(321,'texto','Las espinas de los cactus son, evolutivamente, hojas modificadas para minimizar la pérdida de agua, complementando su anatomía de reserva.'),
(322,'texto','La escasez crónica de nitrógeno y fósforo en el suelo fue, precisamente, el motor ambiental que forzó el desarrollo de sus trampas.'),
(323,'texto','Si carecen de un muro, una red o un tronco sobre el cual erguirse, la gran mayoría de estas plantas colapsarían y se verían obligadas a crecer de forma rastrera.'),
(324,'texto','Representa una de las innovaciones adaptativas más eficientes del reino vegetal para sobrevivir donde el agua es un recurso crítico.'),
(325,'texto','Su éxito para ganar altura depende por completo de "recostarse" o entrelazarse gradualmente sobre el volumen rígido de la vegetación circundante.'),
(326,'texto','Distingue conceptualmente entre una trampa que se cierra de golpe mediante impulsos (activa) y una estructura inmóvil que depende de la gravedad (pasiva).'),
(327,'texto','Evalúa si un rasgo puramente morfológico y utilitario puede estar limitado únicamente a un solo apellido o clado botánico.'),
(328,'texto','El tejido foliar modificado de estas plantas funciona simultáneamente como zona de captura y como órgano de absorción de nutrientes licuados.'),
(329,'texto','Su diseño anatómico está optimizado para capturar la humedad ambiental y los nutrientes del polvo atmosférico desde el dosel, sin tocar el lodo.'),
(330,'texto','Esta adaptación permite dirigir los haces de luz hacia las zonas clorofílicas profundas que están resguardadas de forma subterránea.'),
(331,'texto','Analiza qué tipo de tropismo (positivo o negativo) requiere un zarcillo para poder enrollarse fuertemente alrededor de la guía o rama que acaba de tocar.'),
(332,'texto','Considera si el fin último de estas adaptaciones estructurales en entornos áridos es acelerar el intercambio de gases o blindar las reservas hídricas.'),
(333,'texto','El nitrógeno presente en los excrementos frescos resulta mucho más fácil de asimilar para la planta que el proceso de disolver densos exoesqueletos de quitina.');



-- CATEGORIA 3
-- NIVEL FACIL, INTERMEDIO Y DIFICIL 
INSERT INTO Ayuda (id_pregunta, tipo, contenido) VALUES
(334,'texto','Si un elemento es capaz de consumir materia para transformarla en energía, por definición pertenece al mundo vivo.'),
(335,'texto','En la naturaleza, los restos orgánicos que están en proceso de descomposición no se vuelven mágicamente elementos químicos puros del entorno.'),
(336,'texto','Todo choque, alianza o dependencia entre animales o plantas entra siempre en la categoría de relaciones vivas.'),
(337,'texto','Amplía tu mente más allá de los árboles y el césped; la vida microscópica marina también inicia cadenas alimentarias enteras.'),
(338,'texto','No tener un metabolismo propio e independiente no los excluye de interactuar de forma biológica y destructiva en el ecosistema.'),
(339,'texto','Un factor sin vida puede moldear por completo la anatomía y el crecimiento de los seres vivos a lo largo del tiempo.'),
(340,'texto','Lo inerte es la base que sostiene todo; si el entorno físico cambia, tanto plantas como animales sufren las consecuencias.'),
(341,'texto','Ningún elemento de la naturaleza permanece químicamente puro al interactuar con el entorno dinámico de la Tierra.'),
(342,'texto','Los factores que determinan el clima y las condiciones de supervivencia de un lugar son netamente físicos.'),
(343,'texto','El desgaste de la materia inerte no significa su desaparición; es el inicio de la fertilidad del suelo.'),
(344,'texto','Los acuíferos subterráneos representan una de las mayores fuentes de agua de los continentes.'),
(345,'texto','Un cuerpo de agua que se mueve tiene dinámicas de purificación muy distintas a las de un pozo estancado.'),
(346,'texto','La agitación física de un río caudaloso mezcla los gases del aire de forma mucho más eficiente que la calma de un lago.'),
(347,'texto','Los pozos profundos no se llenan desde ríos lejanos, sino por el agua de lluvia que logra colarse directo desde la superficie.'),
(348,'texto','Existen incluso lagos continentales (como el Mar Muerto o lagos salados) que tienen más concentración de sal que el propio océano.'),
(349,'texto','El subsuelo del desierto actúa como un aislante térmico natural contra el clima hostil de la superficie.'),
(350,'texto','La falta de bosques densos o selvas cerradas es lo que define el paisaje de este ecosistema.'),
(351,'texto','La presión del aire disminuye drásticamente a medida que nos alejamos del nivel del mar.'),
(352,'texto','Los animales de este hábitat deben refugiarse entre las rocas o en la superficie, ya que cavar a profundidad es imposible.'),
(353,'texto','Las intensas lluvias tropicales limpian ("lavan") constantemente la capa superficial de la tierra.'),
(354,'texto','Tras un incendio natural controlado, el hábitat experimenta un rebrote de vegetación tierna que alimenta a los herbívoros.'),
(355,'texto','La evaporación requiere luz solar directa y viento; las grietas profundas bloquean ambos factores.'),
(356,'texto','Las corrientes de aire exterior apenas logran alterar las profundidades de estos sistemas subterráneos.'),
(357,'texto','El aire puro no descompone; son los microorganismos vivos los que realizan esa tarea en el hábitat.'),
(358,'texto','Para poder crecer en el suelo de la selva, las plantas bajas deben tener hojas gigantescas que capten el más mínimo destello de luz.'),
(359,'texto','La mayor parte del espacio habitable de los océanos de la Tierra carece por completo de luz solar.'),
(360,'texto','Las criaturas de las profundidades marinas están adaptadas físicamente para que sus cuerpos no colapsen ante fuerzas de compresión brutales.'),
(361,'texto','Las corrientes marinas frías son las que alimentan los ecosistemas más ricos del planeta.'),
(362,'texto','Cuando la marea sube, el hábitat se vuelve marino; cuando baja, predomina el agua dulce del río.'),
(363,'texto','Gracias a esta propiedad física única del agua, la vida acuática puede seguir nadando y sobreviviendo bajo el hielo invernal.'),
(364,'texto','Solo ciertos microorganismos ultraresistentes logran prosperar en este entorno líquido.'),
(365,'texto','En mitad del océano, encontrar comida es un reto de supervivencia constante que obliga a los animales a migrar miles de kilómetros.'),
(366,'texto','Los animales de corrientes rápidas necesitan ese flujo constante y fresco de agua altamente oxigenada para respirar adecuadamente.'),
(367,'texto','Es un hábitat de calma térmica total, sumido en un frío eterno de aproximadamente 2 grados C a 4 grados C.'),
(368,'texto','Su supervivencia está ligada directamente al proceso de fotosíntesis de pequeños organismos vegetales que habitan en sus tejidos.'),
(369,'texto','Estas corrientes desaparecen durante la noche porque el suelo terrestre se enfría al ocultarse el sol.'),
(370,'texto','El aire caliente tiende a expandirse y dispersarse, perdiendo la capacidad de empuje y resistencia vertical.'),
(371,'texto','El viento libre se ve obligado a acelerar cuando el espacio físico por el que transita se estrecha de repente.'),
(372,'texto','Pueden detectar sutiles variaciones en la presión del aire e incluso olores para saber en qué dirección se desplazan entre la niebla.'),
(373,'texto','Es el punto exacto de conexión e interacción constante entre el cielo abierto y la vegetación terrestre.'),
(374,'texto','El hábitat aéreo marino está en constante movimiento horizontal debido a las inmensas corrientes globales de viento del planeta.'),
(375,'texto','Las plumas y estructuras externas de las aves de gran altura están adaptadas para resistir un desgaste solar mucho más intenso.'),
(376,'texto','Las fuerzas físicas desatadas dentro de esas nubes oscuras superan por mucho las capacidades de resistencia de cualquier ser vivo en vuelo.'),
(377,'texto','El viento actúa como un deshumidificador natural sobre cualquier superficie húmeda expuesta a él.'),
(378,'texto','Es una combinación geométrica perfecta entre un elemento del hábitat terrestre (la roca) y un fenómeno del hábitat aéreo (el viento).'),
(379,'texto','El agua no puede bajar, por lo que el subsuelo profundo siempre está seco e inaccesible.'),
(380,'texto','Abajo predomina una penumbra constante y una humedad sofocante por la falta de ventilación.'),
(381,'texto','El déficit de agua es la regla climática que define a este entorno.'),
(382,'texto','Son de los suelos más fértiles del planeta, ideales para el crecimiento rápido de vegetación tras las lluvias.'),
(383,'texto','El verdadero reto de este entorno es la erosión constante y la escasez extrema de suelo blando.'),
(384,'texto','El terreno y la atmósfera inmediata están saturados de cloruro de sodio.'),
(385,'texto','El clima es mucho más extremo, hostil y cambiante que en las llanuras.'),
(386,'texto','La humedad se vuelve sólida y no puede ser absorbida hasta la primavera.'),
(387,'texto','Comparten la aridez con los desiertos cálidos, pero no el calor extremo todo el año.'),
(388,'texto','Las hojas caídas tardan mucho tiempo en degradarse, formando capas gruesas y esponjosas en la tierra.'),
(389,'texto','La diferencia química entre ambos ambientes es gigantesca y divide por completo la vida en el planeta.'),
(390,'texto','El fondo profundo es un entorno completamente oscuro e imposible de iluminar por el sol.'),
(391,'texto','Las corrientes cálidas y lentas suelen ser ambientes con menor concentración de oxígeno.'),
(392,'texto','Se genera un ambiente lamoso conocido como entorno anaeróbico.'),
(393,'texto','Es un hábitat dinámico donde nada que sea blando o suelto logra quedarse quieto.'),
(394,'texto','Es un entorno salobre que combina las reglas de dos mundos acuáticos diferentes.'),
(395,'texto','El fondo del lago permanece líquido y a una temperatura estable de unos 4°C.'),
(396,'texto','Hay mares tropicales cálidos y aguas polares que rozan el punto de congelación.'),
(397,'texto','La visibilidad bajo el agua en estas zonas es de las más altas del mundo de los océanos.'),
(398,'texto','Es un pulso natural que borra las fronteras entre la tierra firme y el agua de forma cíclica.');

-- MINIJUEGO 3 - MAULWURFRENNT
-- CATEGORÍA 1: OPERACIONES BASICAS
-- NIVEL FÁCIL
INSERT INTO Pregunta (id_nivel, pregunta) VALUES
(19, '¿15 + 8?'),
(19, '¿42 - 17?'),
(19, '¿20 + 10?'),
(19, '¿33 + 22?'),
(19, '¿70 - 35?'),
(19, '¿18 + 19?'),
(19, '¿90 - 45?'),
(19, '¿18 + 42?'),
(19, '¿65 - 15?'),
(19, '¿19 + 18?'),
(19, '¿12 + 25?'),
(19, '¿80 - 20?'),
(19, '¿2 + 2?'),
(19, '¿80 - 40?'),
(19, '¿76 - 19?');

-- MINIJUEGO 3 - MAULWURFRENNT
-- CATEGORÍA 1: OPERACIONES BASICAS
-- MULTIPLICACION Y DIVISION
-- NIVEL INTERMEDIO
INSERT INTO Pregunta (id_nivel, pregunta) VALUES
(20, '¿6 × 7?'),
(20, '¿72 ÷ 8?'),
(20, '¿9 × 5?'),
(20, '¿81 ÷ 9?'),
(20, '¿12 × 4?'),
(20, '¿56 ÷ 7?'),
(20, '¿8 × 11?'),
(20, '¿90 ÷ 10?'),
(20, '¿14 × 3?'),
(20, '¿63 ÷ 9 ?'),
(20, '¿84 ÷ 12?'),
(20, '¿13 × 5?'),
(20, '¿96 ÷ 8?'),
(20, '¿7 × 12?'),
(20, '¿72 ÷ 11?');

-- MINIJUEGO 3 - MAULWURFRENNT
-- CATEGORÍA 1
-- OPERACIONES COMBINADAS
-- NIVEL DIFICIL
INSERT INTO Pregunta (id_nivel, pregunta) VALUES
(21, '¿8 + 3 × 4?'),
(21, '¿25 - 6 ÷ 2?'),
(21, '¿(12 + 8) × 3?'),
(21, '¿50 - (18 + 7)?'),
(21, '¿7 × 5 + 12?'),
(21, '¿64 ÷ 8 + 9?'),
(21, '¿(15 - 5) × 6?'),
(21, '¿36 ÷ (3 × 2)?'),
(21, '¿20 + 4 × 7?'),
(21, '¿90 - 8 × 5?'),
(21, '¿(24 + 16) ÷ 5?'),
(21, '¿11 + 4 + 3?'),
(21, '¿20 × 3 - 5?'),
(21, '¿60 ÷ 5 + 8 × 2?'),
(21, '¿100 - (15 × 4)?');

-- MINIJUEGO 3 - MAULWURFRENNT
-- CATEGORÍA 2: OPERACIONES AVANZADAS
-- POTENCIAS
-- NIVEL FACIL
INSERT INTO Pregunta (id_nivel, pregunta) VALUES
(22, '¿2²?'),
(22, '¿3²?'),
(22, '¿4²?'),
(22, '¿5²?'),
(22, '¿7²?'),
(22, '¿8²?'),
(22, '¿9²?'),
(22, '¿10²?'),
(22, '¿2³?'),
(22, '¿7⁰?'),
(22, '¿5⁰?'),
(22, '¿1⁵?'),
(22, '¿4⁴?'),
(22, '¿5³?'),
(22, '¿6²?');

-- MINIJUEGO 3 - MAULWURFRENNT
-- CATEGORÍA 2: OPERACIONES AVANZADAS
-- RAICES
-- NIVEL INTERMEDIO
INSERT INTO Pregunta (id_nivel, pregunta) VALUES
(23, '¿√4?'),
(23, '¿√9?'),
(23, '¿√16?'),
(23, '¿√25?'),
(23, '¿√36?'),
(23, '¿√2 + 2?'),
(23, '¿√8 × 8?'),
(23, '¿√81?'),
(23, '¿√5 × 5?'),
(23, '¿√10 + 15?'),
(23, '¿√441?'),
(23, '¿√4 + 12?'),
(23, '¿√400?'),
(23, '¿√80 + 1?'),
(23, '¿√196?');

-- MINIJUEGO 3 - MAULWURFRENNT
-- CATEGORÍA 2: OPERACIONES AVANZADAS
-- ECUACIONES DE PRIMER GRADO
-- NIVEL DIFICIL
INSERT INTO Pregunta (id_nivel, pregunta) VALUES
(24, '¿X - 12 = 9?'),
(24, '¿4X = 36?'),
(24, '¿5X = 25?'),
(24, '¿X + 18 = 45?'),
(24, '¿X - 20 = 16?'),
(24, '¿6X = 54?'),
(24, '¿7X = 63?'),
(24, '¿X + 24 = 50?'),
(24, '¿X - 14 = 22?'),
(24, '¿8X = 64?'),
(24, '¿X + 5 = 12?'),
(24, '¿X - 8 = 10?'),
(24, '¿2X = 14?'),
(24, '¿3X = 21?'),
(24, '¿X + 11 = 40?');

-- MINIJUEGO 3 - MAULWURFRENNT
-- CATEGORÍA 3: MATEMATICOS
-- INVENTOS Y CONTRIBUCIONES 
-- NIVEL FACIL
INSERT INTO Pregunta (id_nivel, pregunta) VALUES
(25, '¿QUÉ TEORÍA DESARROLLÓ ALBERT EINSTEIN EN 1905?'),
(25, '¿QUÉ LEY FORMULÓ ISAAC NEWTON PARA EXPLICAR LA ATRACCIÓN ENTRE LOS CUERPOS?'),
(25, '¿CÓMO SE LLAMÓ LA CALCULADORA MECÁNICA INVENTADA POR BLAISE PASCAL?'),
(25, '¿QUÉ SISTEMA CREÓ RENÉ DESCARTES PARA UBICAR PUNTOS EN UN PLANO?'),
(25, '¿CUÁL ES LA OBRA MÁS FAMOSA DE AURELIO BALDOR?'),
(25, '¿QUÉ FAMOSO TEOREMA SE ATRIBUYE A PITÁGORAS?'),
(25, '¿QUÉ FENÓMENO EXPLICÓ EINSTEIN QUE AYUDÓ AL DESARROLLO DE LAS CELDAS SOLARES?'),
(25, '¿QUÉ INSTRUMENTO MEJORÓ ISAAC NEWTON PARA OBSERVAR EL CIELO?'),
(25, '¿QUÉ PRINCIPIO FÍSICO LLEVA EL NOMBRE DE PASCAL?'),
(25, '¿QUÉ DISCIPLINA MATEMÁTICA FUNDÓ RENÉ DESCARTES?'),
(25, '¿QUÉ ESCUELA FILOSÓFICA Y MATEMÁTICA FUNDÓ PITÁGORAS?'),
(25, '¿A QUÉ ÁREA MATEMÁTICA CONTRIBUYÓ BLAISE PASCAL JUNTO CON OTROS MATEMÁTICOS?'),
(25, '¿QUÉ LIBRO DE BALDOR ES AMPLIAMENTE UTILIZADO EN AMÉRICA LATINA?'),
(25, '¿QUÉ ESTUDIABAN LOS SEGUIDORES DE PITÁGORAS ADEMÁS DE LA FILOSOFÍA?'),
(25, '¿EN QUÉ CAMPO CIENTÍFICO REALIZÓ APORTES IMPORTANTES EINSTEIN ADEMÁS DE LA RELATIVIDAD?');

-- MINIJUEGO 3 - MAULWURFRENNT
-- CATEGORÍA 3: MATEMATICOS
-- FECHAS DE NACIMIENTO
-- NIVEL INTERMEDIO
INSERT INTO Pregunta (id_nivel, pregunta) VALUES
(26, '¿EN QUÉ AÑO NACIÓ ALBERT EINSTEIN?'),
(26, '¿EN QUÉ AÑO FALLECIÓ ALBERT EINSTEIN?'),
(26, '¿EN QUÉ AÑO NACIÓ ISAAC NEWTON?'),
(26, '¿EN QUÉ AÑO FALLECIÓ ISAAC NEWTON?'),
(26, '¿EN QUÉ AÑO NACIÓ BLAISE PASCAL?'),
(26, '¿EN QUÉ AÑO FALLECIÓ BLAISE PASCAL?'),
(26, '¿EN QUÉ AÑO NACIÓ RENÉ DESCARTES?'),
(26, '¿EN QUÉ AÑO FALLECIÓ RENÉ DESCARTES?'),
(26, '¿EN QUÉ AÑO NACIÓ AURELIO BALDOR?'),
(26, '¿EN QUÉ AÑO FALLECIÓ AURELIO BALDOR?'),
(26, '¿EN QUÉ SIGLO NACIÓ PITÁGORAS?'),
(26, '¿EN QUÉ SIGLO MURIÓ PITÁGORAS?'),
(26, '¿CUÁL DE LOS SIGUIENTES PERSONAJES NACIÓ PRIMERO EN LA HISTORIA?'),
(26, '¿CUÁL DE LOS SIGUIENTES PERSONAJES VIVIÓ MÁS AÑOS?'),
(26, '¿QUIÊN NACIÓ EN 1906?');

-- MINIJUEGO 3 - MAULWURFRENNT
-- CATEGORÍA 3: MATEMATICOS
-- FECHAS DESCUBRIMIENTOS
-- NIVEL DIFICIL
INSERT INTO Pregunta (id_nivel, pregunta) VALUES
(27, '¿EN QUÉ AÑO PUBLICÓ EINSTEIN LA TEORÍA DE LA RELATIVIDAD ESPECIAL?'),
(27, '¿EN QUÉ AÑO PRESENTÓ EINSTEIN LA TEORÍA DE LA RELATIVIDAD GENERAL?'),
(27, '¿EN QUÉ AÑO PUBLICÓ NEWTON LA OBRA PRINCIPIA MATHEMATICA?'),
(27, '¿EN QUÉ PERÍODO DESARROLLÓ NEWTON EL CÁLCULO?'),
(27, '¿EN QUÉ AÑO INVENTÓ BLAISE PASCAL LA PASCALINA?'),
(27, '¿EN QUÉ AÑO PUBLICÓ PASCAL SU TRABAJO SOBRE EL TRIÁNGULO ARITMÉTICO?'),
(27, '¿EN QUÉ AÑO PUBLICÓ RENÉ DESCARTES "LA GEOMETRÍA"?'),
(27, '¿EN QUÉ AÑO APARECIÓ LA PRIMERA EDICIÓN DE ÁLGEBRA DE BALDOR?'),
(27, '¿EN QUÉ SIGLO SE ATRIBUYE LA FORMULACIÓN DEL TEOREMA DE PITÁGORAS?'),
(27, '¿EN QUÉ AÑO SE FUNDÓ APROXIMADAMENTE LA ESCUELA PITÁGORICA?'),
(27, '¿QUÉ PUBLICACIÓN DE NEWTON APARECIÓ EN 1687?'),
(27, '¿QUÉ INVENTO DE PASCAL FUE CREADO EN 1642?'),
(27, '¿QUÉ OBRA DE DESCARTES INTRODUJO LA GEOMETRÍA ANALÍTICA EN 1637?'),
(27, '¿QUÉ LIBRO DE BALDOR SE PUBLICÓ EN 1941?'),
(27, '¿QUÉ TEORÍA DE EINSTEIN SE PUBLICÓ PRIMERO?');

-- MINIJUEGO 3 - MAULWURFRENNT
-- OPCION_RESPUESTA
-- CATEGORIA 1
-- NIVEL FACIL: sumas y restas
INSERT INTO Opcion_respuesta(id_pregunta, texto_opcion, es_correcta) VALUES
(399, '23', TRUE),
(399, '20', FALSE),
(399, '22', FALSE),
(399, '25', FALSE),
(399, '2', FALSE),

(400, '25', TRUE),
(400, '20', FALSE),
(400, '30', FALSE),
(400, '45', FALSE),
(400, '15', FALSE),

(401, '30', TRUE),
(401, '15', FALSE),
(401, '65', FALSE),
(401, '32', FALSE),
(401, '29', FALSE),

(402, '55', TRUE),
(402, '50', FALSE),
(402, '60', FALSE),
(402, '15', FALSE),
(402, '10', FALSE),

(403, '35', TRUE),
(403, '30', FALSE),
(403, '40', FALSE),
(403, '100', FALSE),
(403, '50', FALSE),

(404, '37', TRUE),
(404, '36', FALSE),
(404, '40', FALSE),
(404, '200', FALSE),
(404, '10', FALSE),

(405, '45', TRUE),
(405, '43', FALSE),
(405, '42', FALSE),
(405, '20', FALSE),
(405, '69', FALSE),

(406, '60', TRUE),
(406, '59', FALSE),
(406, '58', FALSE),
(406, '36', FALSE),
(406, '54', FALSE),

(407, '50', TRUE),
(407, '51', FALSE),
(407, '56', FALSE),
(407, '15', FALSE),
(407, '63', FALSE),

(408, '37', TRUE),
(408, '35', FALSE),
(408, '36', FALSE),
(408, '48', FALSE),
(408, '49', FALSE),

(409, '37', TRUE),
(409, '67', FALSE),
(409, '42', FALSE),
(409, '64', FALSE),
(409, '78', FALSE),

(410, '60', TRUE),
(410, '50', FALSE),
(410, '36', FALSE),
(410, '89', FALSE),
(410, '45', FALSE),

(411, '4', TRUE),
(411, '2', FALSE),
(411, '1', FALSE),
(411, '0', FALSE),
(411, '562', FALSE),

(412, '40', TRUE),
(412, '20', FALSE),
(412, '30', FALSE),
(412, '68', FALSE),
(412, '41', FALSE),

(413, '57', TRUE),
(413, '65', FALSE),
(413, '23', FALSE),
(413, '94', FALSE),
(413, '13', FALSE),

-- NIVEL INTERMEDIO: MULTIPLICACIONES Y DIVISIONES
(414, '42', TRUE),
(414, '40', FALSE),
(414, '41', FALSE),
(414, '33', FALSE),
(414, '69', FALSE),
(414, '-89', FALSE),

(415, '9', TRUE),
(415, '8', FALSE),
(415, '6', FALSE),
(415, '0', FALSE),
(415, '7', FALSE),
(415, '33', FALSE),

(416, '45', TRUE),
(416, '42', FALSE),
(416, '40', FALSE),
(416, '69', FALSE),
(416, '94', FALSE),
(416, '35', FALSE),

(417, '9', TRUE),
(417, '6', FALSE),
(417, '54', FALSE),
(417, '59', FALSE),
(417, '91', FALSE),
(417, '7', FALSE),

(418, '48', TRUE),
(418, '32', FALSE),
(418, '69', FALSE),
(418, '458', FALSE),
(418, '32', FALSE),
(418, '125', FALSE),

(419, '8', TRUE),
(419, '6', FALSE),
(419, '12', FALSE),
(419, '3', FALSE),
(419, '7', FALSE),
(419, '9', FALSE),

(420, '88', TRUE),
(420, '99', FALSE),
(420, '111', FALSE),
(420, '165', FALSE),
(420, '337', FALSE),
(420, '77', FALSE),

(421, '9', TRUE),
(421, '7', FALSE),
(421, '65', FALSE),
(421, '0', FALSE),
(421, '33', FALSE),
(421, '11', FALSE),

(422, '42', TRUE),
(422, '59', FALSE),
(422, '422', FALSE),
(422, '116', FALSE),
(422, '48', FALSE),
(422, '43', FALSE),

(423, '7', TRUE),
(423, '65', FALSE),
(423, '32', FALSE),
(423, '14', FALSE),
(423, '74', FALSE),
(423, '3', FALSE),

(424, '7', TRUE),
(424, '96', FALSE),
(424, '110', FALSE),
(424, '-98', FALSE),
(424, '65', FALSE),
(424, '6', FALSE),

(425, '90', TRUE),
(425, '96', FALSE),
(425, '110', FALSE),
(425, '33', FALSE),
(425, '486', FALSE),
(425, '82', FALSE),

(426, '12', TRUE),
(426, '15', FALSE),
(426, '98', FALSE),
(426, '-110', FALSE),
(426, '2', FALSE),
(426, '38', FALSE),

(427, '84', TRUE),
(427, '55', FALSE),
(427, '69', FALSE),
(427, '465', FALSE),
(427, '51', FALSE),
(427, '72', FALSE),

(428, '11', TRUE),
(428, '32', FALSE),
(428, '69', FALSE),
(428, '15', FALSE),
(428, '12', FALSE),
(428, '65', FALSE),

-- NIVEL DIFICIL: OPERACIONES COMBINADAS
(429, '20', TRUE),
(429, '33', FALSE),
(429, '44', FALSE),
(429, '65', FALSE),
(429, '65', FALSE),
(429, '-9', FALSE),
(429, '9', FALSE),

(430, '22', TRUE),
(430, '66', FALSE),
(430, '-6', FALSE),
(430, '-4', FALSE),
(430, '9.8', FALSE),
(430, '47', FALSE),
(430, '32', FALSE),

(431, '60', TRUE),
(431, '90', FALSE),
(431, '-4', FALSE),
(431, '78', FALSE),
(431, '52', FALSE),
(431, '125', FALSE),
(431, '-5', FALSE),

(432, '25', TRUE),
(432, '14', FALSE),
(432, '69', FALSE),
(432, '878', FALSE),
(432, '126', FALSE),
(432, '20', FALSE),
(432, '34', FALSE),

(433, '47', TRUE),
(433, '37', FALSE),
(433, '29', FALSE),
(433, '27', FALSE),
(433, '58', FALSE),
(433, '9', FALSE),
(433, '12', FALSE),

(434, '17', TRUE),
(434, '16', FALSE),
(434, '18', FALSE),
(434, '30', FALSE),
(434, '20', FALSE),
(434, '28', FALSE),
(434, '8', FALSE),

(435, '60', TRUE),
(435, '50', FALSE),
(435, '32', FALSE),
(435, '47', FALSE),
(435, '98', FALSE),
(435, '77', FALSE),
(435, '36', FALSE),

(436, '6', TRUE),
(436, '98', FALSE),
(436, '7', FALSE),
(436, '10', FALSE),
(436, '33', FALSE),
(436, '54', FALSE),
(436, '11', FALSE),

(437, '48', TRUE),
(437, '42', FALSE),
(437, '40', FALSE),
(437, '69', FALSE),
(437, '35', FALSE),
(437, '7', FALSE),
(437, '38', FALSE),

(438, '50', TRUE),
(438, '654', FALSE),
(438, '132', FALSE),
(438, '354', FALSE),
(438, '74', FALSE),
(438, '40', FALSE),
(438, '36', FALSE),

(439, '8', TRUE),
(439, '7', FALSE),
(439, '9', FALSE),
(439, '-98', FALSE),
(439, '12', FALSE),
(439, '35', FALSE),
(439, '11', FALSE),

(440, '18', TRUE),
(440, '10', FALSE),
(440, '15', FALSE),
(440, '32', FALSE),
(440, '654', FALSE),
(440, '21', FALSE),
(440, '20', FALSE),

(441, '55', TRUE),
(441, '50', FALSE),
(441, '60', FALSE),
(441, '-10', FALSE),
(441, '32', FALSE),
(441, '38', FALSE),
(441, '18', FALSE),

(442, '28', TRUE),
(442, '91', FALSE),
(442, '93', FALSE),
(442, '30', FALSE),
(442, '47', FALSE),
(442, '30', FALSE),
(442, '48', FALSE),

(443, '40', TRUE),
(443, '31', FALSE),
(443, '1465', FALSE),
(443, '-15', FALSE),
(443, '-44', FALSE),
(443, '65', FALSE),
(443, '-138', FALSE);

-- CATEGORIA 2
-- NIVEL FACIL: POTENCIAS
INSERT INTO Opcion_respuesta (id_pregunta,texto_opcion,es_correcta) VALUES
(444, '4', TRUE),
(444, '6', FALSE),
(444, '10', FALSE),
(444, '22', FALSE),
(444, '2', FALSE),

(445, '9', TRUE),
(445, '10', FALSE),
(445, '3', FALSE),
(445, '6', FALSE),
(445, '11', FALSE),

(446, '16', TRUE),
(446, '12', FALSE),
(446, '-7', FALSE),
(446, '64', FALSE),
(446, '15', FALSE),

(447, '25', TRUE),
(447, '321', FALSE),
(447, '31', FALSE),
(447, '34', FALSE),
(447, '98', FALSE),

(448, '49', TRUE),
(448, '32', FALSE),
(448, '65', FALSE),
(448, '165', FALSE),
(448, '87', FALSE),

(449, '64', TRUE),
(449, '16', FALSE),
(449, '98', FALSE),
(449, '165', FALSE),
(449, '65', FALSE),

(450, '81', TRUE),
(450, '61', FALSE),
(450, '685', FALSE),
(450, '16', FALSE),
(450, '87', FALSE),

(451, '100', TRUE),
(451, '32', FALSE),
(451, '16', FALSE),
(451, '984', FALSE),
(451, '654', FALSE),

(452, '8', TRUE),
(452, '9', FALSE),
(452, '16', FALSE),
(452, '35', FALSE),
(452, '12', FALSE),

(453, '1', TRUE),
(453, '35', FALSE),
(453, '36', FALSE),
(453, '48', FALSE),
(453, '49', FALSE),

(454, '1', TRUE),
(454, '32', FALSE),
(454, '2', FALSE),
(454, '0', FALSE),
(454, '-1', FALSE),

(455, '1', TRUE),
(455, '5', FALSE),
(455, '6', FALSE),
(455, '156', FALSE),
(455, '6', FALSE),

(456, '256', TRUE),
(456, '165', FALSE),
(456, '169', FALSE),
(456, '849', FALSE),
(456, '884', FALSE),

(457, '125', TRUE),
(457, '65', FALSE),
(457, '156', FALSE),
(457, '81', FALSE),
(457, '155', FALSE),

(458, '36', TRUE),
(458, '616', FALSE),
(458, '55', FALSE),
(458, '1', FALSE),
(458, '2', FALSE),

-- NIVEL INTERMEDIO: RAICES
(459, '2', TRUE),
(459, '3', FALSE),
(459, '1', FALSE),
(459, '69', FALSE),
(459, '2.3', FALSE),
(459, '4.2', FALSE),

(460, '3', TRUE),
(460, '5', FALSE),
(460, '3.6', FALSE),
(460, '9', FALSE),
(460, '0', FALSE),
(460, '47', FALSE),

(461, '4', TRUE),
(461, '6', FALSE),
(461, '3', FALSE),
(461, '8', FALSE),
(461, '15', FALSE),
(461, '9', FALSE),

(462, '5', TRUE),
(462, '16', FALSE),
(462, '89', FALSE),
(462, '35', FALSE),
(462, '20', FALSE),
(462, '7', FALSE),

(463, '6', TRUE),
(463, '9', FALSE),
(463, '1', FALSE),
(463, '5.9', FALSE),
(463, '0', FALSE),
(463, '5.2', FALSE),

(464, '2', TRUE),
(464, '4', FALSE),
(464, '6', FALSE),
(464, '31', FALSE),
(464, '2.4', FALSE),
(464, '3.5', FALSE),

(465, '8', TRUE),
(465, '65', FALSE),
(465, '16.6', FALSE),
(465, '15.5', FALSE),
(465, '337', FALSE),
(465, '125', FALSE),

(466, '9', TRUE),
(466, '189', FALSE),
(466, '3', FALSE),
(466, '8', FALSE),
(466, '9.01', FALSE),
(466, '8.23', FALSE),

(467, '5', TRUE),
(467, '15', FALSE),
(467, '65', FALSE),
(467, '851', FALSE),
(467, '69', FALSE),
(467, '25', FALSE),

(468, '5', TRUE),
(468, '16', FALSE),
(468, '61', FALSE),
(468, '89', FALSE),
(468, '2', FALSE),
(468, '7', FALSE),

(469, '21', TRUE),
(469, '23.6', FALSE),
(469, '41.98', FALSE),
(469, '25.5', FALSE),
(469, '22', FALSE),
(469, '26.1', FALSE),

(470, '4', TRUE),
(470, '11', FALSE),
(470, '6', FALSE),
(470, '3', FALSE),
(470, '12', FALSE),
(470, '4.9', FALSE),

(471, '20', TRUE),
(471, '23', FALSE),
(471, '56', FALSE),
(471, '89', FALSE),
(471, '1', FALSE),
(471, '24.63', FALSE),

(472, '9', TRUE),
(472, '19', FALSE),
(472, '8', FALSE),
(472, '10', FALSE),
(472, '5', FALSE),
(472, '48', FALSE),

(473, '14', TRUE),
(473, '15', FALSE),
(473, '68', FALSE),
(473, '19', FALSE),
(473, '23', FALSE),
(473, '15.26', FALSE),

-- NIVEL DIFICIL: ECUACIONES PRIMER GRADO
(474, '21', TRUE),
(474, '20', FALSE),
(474, '4', FALSE),
(474, '6', FALSE),
(474, '159', FALSE),
(474, '22.1', FALSE),
(474, '31', FALSE),

(475, '9', TRUE),
(475, '8', FALSE),
(475, '7', FALSE),
(475, '10', FALSE),
(475, '30', FALSE),
(475, '12', FALSE),
(475, '32', FALSE),

(476, '5', TRUE),
(476, '10', FALSE),
(476, '20', FALSE),
(476, '3', FALSE),
(476, '489', FALSE),
(476, '7', FALSE),
(476, '9', FALSE),

(477, '27', TRUE),
(477, '19', FALSE),
(477, '68', FALSE),
(477, '269', FALSE),
(477, '36', FALSE),
(477, '37', FALSE),
(477, '20', FALSE),

(478, '36', TRUE),
(478, '16', FALSE),
(478, '68', FALSE),
(478, '15', FALSE),
(478, '89', FALSE),
(478, '42', FALSE),
(478, '56', FALSE),

(479, '9', TRUE),
(479, '198', FALSE),
(479, '68', FALSE),
(479, '51', FALSE),
(479, '6', FALSE),
(479, '12', FALSE),
(479, '16', FALSE),

(480, '9', TRUE),
(480, '5', FALSE),
(480, '16', FALSE),
(480, '3', FALSE),
(480, '1', FALSE),
(480, '13', FALSE),
(480, '21', FALSE),

(481, '26', TRUE),
(481, '16', FALSE),
(481, '35', FALSE),
(481, '89', FALSE),
(481, '54', FALSE),
(481, '28', FALSE),
(481, '38', FALSE),

(482, '36', TRUE),
(482, '26', FALSE),
(482, '89', FALSE),
(482, '6', FALSE),
(482, '8', FALSE),
(482, '38', FALSE),
(482, '87', FALSE),

(483, '8', TRUE),
(483, '98', FALSE),
(483, '6', FALSE),
(483, '7', FALSE),
(483, '16', FALSE),
(483, '12', FALSE),
(483, '22', FALSE),

(484, '7', TRUE),
(484, '19', FALSE),
(484, '3', FALSE),
(484, '-4', FALSE),
(484, '64', FALSE),
(484, '-16', FALSE),
(484, '16', FALSE),

(485, '18', TRUE),
(485, '65', FALSE),
(485, '-41', FALSE),
(485, '-6', FALSE),
(485, '-7', FALSE),
(485, '-2', FALSE),
(485, '-16', FALSE),

(486, '7', TRUE),
(486, '15', FALSE),
(486, '3', FALSE),
(486, '-74', FALSE),
(486, '68', FALSE),
(486, '-1', FALSE),
(486, '-8', FALSE),

(487, '7', TRUE),
(487, '19', FALSE),
(487, '3', FALSE),
(487, '8', FALSE),
(487, '95', FALSE),
(487, '18', FALSE),
(487, '36', FALSE),

(488, '29', TRUE),
(488, '19', FALSE),
(488, '35', FALSE),
(488, '189', FALSE),
(488, '9', FALSE),
(488, '32', FALSE),
(488, '23', FALSE);

-- CATEGORIA 3
-- NIVEL FACIL: INVENTOS Y CONTRIBUCIONES
INSERT INTO Opcion_respuesta (id_pregunta,texto_opcion,es_correcta) VALUES
(489, 'Teoría de la Relatividad', TRUE),
(489, 'Teoría de la Evolución', FALSE),
(489, 'Teoría Celular', FALSE),
(489, 'Ley de los Gases', FALSE),
(489, 'Teoría Atómica', FALSE),

(490, 'Ley de Gravitación Universal', TRUE),
(490, 'Ley de Ohm', FALSE),
(490, 'Ley de Boyle', FALSE),
(490, 'Ley de Pascal', FALSE),
(490, 'Ley de Coulomb', FALSE),

(491, 'Pascalina', TRUE),
(491, 'Computadora Pascal', FALSE),
(491, 'Máquina Universal', FALSE),
(491, 'Calculón', FALSE),
(491, 'Ábaco Moderno', FALSE),

(492, 'Sistema de Coordenadas Cartesianas', TRUE),
(492, 'Sistema Métrico', FALSE),
(492, 'Sistema Decimal', FALSE),
(492, 'Sistema Binario', FALSE),
(492, 'Sistema Polar', FALSE),

(493, 'Álgebra de Baldor', TRUE),
(493, 'Geometría Moderna', FALSE),
(493, 'Matemática Básica', FALSE),
(493, 'Trigonometría Universal', FALSE),
(493, 'Cálculo Integral', FALSE),

(494, 'Teorema de Pitágoras', TRUE),
(494, 'Teorema de Tales', FALSE),
(494, 'Teorema Fundamental del Cálculo', FALSE),
(494, 'Teorema de Fermat', FALSE),
(494, 'Teorema de Bernoulli', FALSE),

(495, 'Efecto Fotoeléctrico', TRUE),
(495, 'Gravedad Universal', FALSE),
(495, 'Dilatación Térmica', FALSE),
(495, 'Reflexión de la Luz', FALSE),
(495, 'Movimiento Pendular', FALSE),

(496, 'Telescopio Reflector', TRUE),
(496, 'Microscopio', FALSE),
(496, 'Compás', FALSE),
(496, 'Barómetro', FALSE),
(496, 'Termómetro', FALSE),

(497, 'Principio de Pascal', TRUE),
(497, 'Principio de Arquímedes', FALSE),
(497, 'Principio de Bernoulli', FALSE),
(497, 'Principio de Inercia', FALSE),
(497, 'Principio de Conservación', FALSE),

(498, 'Geometría Analítica', TRUE),
(498, 'Álgebra Lineal', FALSE),
(498, 'Estadística', FALSE),
(498, 'Trigonometría', FALSE),
(498, 'Geometría Analítica', FALSE),

(499, 'Escuela Pitagórica', TRUE),
(499, 'Escuela de Atenas', FALSE),
(499, 'Academia Platónica', FALSE),
(499, 'Liceo Aristotélico', FALSE),
(499, 'Escuela Alejandrina', FALSE),

(500, 'Teoría de la Probabilidad', TRUE),
(500, 'Geometría Analítica', FALSE),
(500, 'Álgebra Moderna', FALSE),
(500, 'Trigonometría', FALSE),
(500, 'Cálculo Diferencial', FALSE),

(501, 'Álgebra de Baldor', TRUE),
(501, 'Geometría Práctica', FALSE),
(501, 'Matemática Aplicada', FALSE),
(501, 'Trigonometría Moderna', FALSE),
(501, 'Aritmética Superior', FALSE),

(502, 'Números y proporciones', TRUE),
(502, 'Astronomía únicamente', FALSE),
(502, 'Medicina únicamente', FALSE),
(502, 'Botánica y Zoología', FALSE),
(502, 'Química Experimental', FALSE),

(503, 'Física Cuántica', TRUE),
(503, 'Biología Molecular', FALSE),
(503, 'Geología', FALSE),
(503, 'Medicina', FALSE),
(503, 'Oceanografía', FALSE),

-- NIVEL INTERMEDIO: FECHAS DE NACIMIENTO
(504, '1879', TRUE),
(504, '1865', FALSE),
(504, '1885', FALSE),
(504, '1905', FALSE),
(504, '1000', FALSE),
(504, '1721', FALSE),

(505, '1955', TRUE),
(505, '1945', FALSE),
(505, '1965', FALSE),
(505, '1800', FALSE),
(505, '1475', FALSE),
(505, '1845', FALSE),

(506, '1643', TRUE),
(506, '1600', FALSE),
(506, '1650', FALSE),
(506, '1700', FALSE),
(506, '1625', FALSE),
(506, '1626', FALSE),

(507, '1727', TRUE),
(507, '1707', FALSE),
(507, '1740', FALSE),
(507, '1699', FALSE),
(507, '1755', FALSE),
(507, '1684', FALSE),

(508, '1623', TRUE),
(508, '1635', FALSE),
(508, '1654', FALSE),
(508, '1615', FALSE),
(508, '1700', FALSE),
(508, '1729', FALSE),

(509, '1662', TRUE),
(509, '1682', FALSE),
(509, '1700', FALSE),
(509, '1650', FALSE),
(509, '1600', FALSE),
(509, '1863', FALSE),

(510, '1596', TRUE),
(510, '1580', FALSE),
(510, '1605', FALSE),
(510, '1620', FALSE),
(510, '1575', FALSE),
(510, '1589', FALSE),

(511, '1650', TRUE),
(511, '1640', FALSE),
(511, '1675', FALSE),
(511, '1630', FALSE),
(511, '1660', FALSE),
(511, '1643', FALSE),

(512, '1906', TRUE),
(512, '1890', FALSE),
(512, '1915', FALSE),
(512, '1920', FALSE),
(512, '1885', FALSE),
(512, '1782', FALSE),

(513, '1978', TRUE),
(513, '1985', FALSE),
(513, '1990', FALSE),
(513, '89', FALSE),
(513, '1968', FALSE),
(513, '1489', FALSE),

(514, 'Siglo VI A. C.', TRUE),
(514, 'Siglo I A. C.', FALSE),
(514, 'Siglo II A. C.', FALSE),
(514, 'Siglo III A. C.', FALSE),
(514, 'Siglo IV A. C.', FALSE),
(514, 'Siglo VII A. C.', FALSE),

(515, 'Siglo V A. C.', TRUE),
(515, 'Siglo I A. C.', FALSE),
(515, 'Siglo II A. C.', FALSE),
(515, 'Siglo III A. C.', FALSE),
(515, 'Siglo IV A. C.', FALSE),
(515, 'Siglo VI A. C.', FALSE),

(516, 'PITÁGORAS', TRUE),
(516, 'EINSTEIN', FALSE),
(516, 'BALDOR', FALSE),
(516, 'PASCAL', FALSE),
(516, 'DESCARTES', FALSE),
(516, 'NEWTON', FALSE),

(517, 'TESLA', TRUE),
(517, 'PASCAL', FALSE),
(517, 'DESCARTES', FALSE),
(517, 'NEWTON', FALSE),
(517, 'BALDOR', FALSE),
(517, 'KARL MARX', FALSE),

(518, 'AURELIO BALDOR', TRUE),
(518, 'EINSTEIN', FALSE),
(518, 'NEWTON', FALSE),
(518, 'PASCAL', FALSE),
(518, 'DESCARTES', FALSE),
(518, 'MARIE CURIE', FALSE),

-- NIVEL DIFICIL: FECHAS DESCUBRIMIENTOS
(519, '1905', TRUE),
(519, '1895', FALSE),
(519, '1915', FALSE),
(519, '1920', FALSE),
(519, '1880', FALSE),
(519, '1780', FALSE),
(519, '1908', FALSE),

(520, '1915', TRUE),
(520, '1900', FALSE),
(520, '1905', FALSE),
(520, '1925', FALSE),
(520, '1898', FALSE),
(520, '1600', FALSE),
(520, '1850', FALSE),

(521, '1687', TRUE),
(521, '1670', FALSE),
(521, '1695', FALSE),
(521, '1701', FALSE),
(521, '1665', FALSE),
(521, '1501', FALSE),
(521, '1874', FALSE),

(522, '1665–1666', TRUE),
(522, '1640–1641', FALSE),
(522, '1700–1701', FALSE),
(522, '1687–1688', FALSE),
(522, '1675–1676', FALSE),
(522, '1665–1669', FALSE),
(522, '1701–1702', FALSE),

(523, '1642', TRUE),
(523, '1650', FALSE),
(523, '1630', FALSE),
(523, '1630', FALSE),
(523, '1672', FALSE),
(523, '1682', FALSE),
(523, '1825', FALSE),

(524, '1654', TRUE),
(524, '1642', FALSE),
(524, '1662', FALSE),
(524, '1637', FALSE),
(524, '1680', FALSE),
(524, '1642', FALSE),
(524, '1852', FALSE),

(525, '1637', TRUE),
(525, '1600', FALSE),
(525, '1650', FALSE),
(525, '1623', FALSE),
(525, '1642', FALSE),
(525, '1587', FALSE),
(525, '1782', FALSE),

(526, '1941', TRUE),
(526, '1930', FALSE),
(526, '1950', FALSE),
(526, '1960', FALSE),
(526, '1925', FALSE),
(526, '1954', FALSE),
(526, '1842', FALSE),

(527, 'SIGLO VI A. C.', TRUE),
(527, 'SIGLO II A. C.', FALSE),
(527, 'SIGLO I A. C.', FALSE),
(527, 'SIGLO III A. C.', FALSE),
(527, 'SIGLO IV A. C.', FALSE),
(527, 'SIGLO VII A. C.', FALSE),
(527, 'SIGLO X A. C.', FALSE),

(528, '530 A. C.', TRUE),
(528, '400 A. C.', FALSE),
(528, '650 A. C.', FALSE),
(528, '300 A. C.', FALSE),
(528, '450 A. C.', FALSE),
(528, '520 A. C.', FALSE),
(528, '410 A. C.', FALSE),

(529, 'Principia Mathematica', TRUE),
(529, 'Discurso del Método', FALSE),
(529, 'Álgebra de Baldor', FALSE),
(529, 'La Geometría', FALSE),
(529, 'Triángulo Aritmético', FALSE),
(529, 'Meditaciones Metafísicas', FALSE),
(529, 'Tratado sobre la Luz', FALSE),

(530, 'Pascalina', TRUE),
(530, 'Telescopio Reflector', FALSE),
(530, 'Máquina de Vapor', FALSE),
(530, 'Calculadora Electrónica', FALSE),
(530, 'Regla de Cálculo', FALSE),
(530, 'Barómetro de Mercurio', FALSE),
(530, 'Telégrafo Eléctrico', FALSE),

(531, 'LA GEOMETRÍA ', TRUE),
(531, 'PRINCIPIA MATHEMATICA', FALSE),
(531, 'ÁLGEBRA DE BALDOR', FALSE),
(531, 'EL MUNDO', FALSE),
(531, 'MEDITACIONES METAFÍSICAS', FALSE),
(531, 'DISCURSOS DEL MÉTODO', FALSE),
(531, 'LAS PASIONES DEL ALMA', FALSE),

(532, 'ÁLGEBRA DE BALDOR', TRUE),
(532, 'TRIGONOMETRÍA MODERNA', FALSE),
(532, 'MATEMÁTICA APLICADA', FALSE),
(532, 'GEOMETRÍA UNIVERSAL', FALSE),
(532, 'CÁLCULO SUPERIOR', FALSE),
(532, 'ARITMÉTICA MERCANTIL', FALSE),
(532, 'ELEMENTOS DE GEOMETRÍA', FALSE),

(533, 'RELATIVIDAD ESPECIAL', TRUE),
(533, 'GENERAL', FALSE),
(533, 'CUÁNTICA', FALSE),
(533, 'ELECTROMAGNÉTICA', FALSE),
(533, 'ATÓMICA', FALSE),
(533, 'EFECTO FOTOELÉCTRICO', FALSE),
(533, 'CAMPO UNIFICADO', FALSE);

-- A Y U D A 
-- CATEGORIA 3 
INSERT INTO Ayuda (id_pregunta,tipo,contenido) VALUES
(489, 'texto', 'Fue presentada a principios del siglo XX y cambió la manera en que se entendían el espacio y el tiempo.'),
(489, 'texto', 'Explica que las mediciones del tiempo y la distancia pueden variar dependiendo de la velocidad a la que se mueve un observador.'),
(489, 'texto', 'Su autor la publicó en 1905 y es una de las contribuciones científicas más importantes de Albert Einstein.'),

(490, 'texto', 'Describe una fuerza presente entre todos los cuerpos que poseen masa.'),
(490, 'texto', 'Ayuda a explicar por qué los planetas orbitan alrededor de las estrellas y por qué los objetos caen hacia la Tierra.'),
(490, 'texto', 'Fue formulada por Isaac Newton y establece que la atracción aumenta con la masa y disminuye con la distancia.'),

(491, 'texto', 'Fue uno de los primeros dispositivos mecánicos creados para facilitar operaciones matemáticas.'),
(491, 'texto', 'Utilizaba ruedas y engranajes para realizar cálculos de manera automática.'),
(491, 'texto', 'Su inventor fue Blaise Pascal y es considerada una precursora de las calculadoras modernas.'),

(492, 'texto', 'Permite ubicar con precisión cualquier punto dentro de una superficie plana.'),
(492, 'texto', 'Utiliza dos líneas numéricas perpendiculares que se cruzan en un punto llamado origen.'),
(492, 'texto', 'Fue desarrollado por René Descartes y es fundamental para la geometría analítica.'),

(493, 'texto', 'Es uno de los libros de matemáticas más conocidos y utilizados en América Latina.'),
(493, 'texto', 'Ha servido durante décadas como material de estudio para estudiantes de secundaria y bachillerato.'),
(493, 'texto', 'Su portada muestra a un personaje árabe y fue escrito por Aurelio Baldor.'),

(494, 'texto', 'Relaciona las medidas de los lados de un tipo especial de triángulo.'),
(494, 'texto', 'Se aplica únicamente a triángulos que poseen un ángulo recto.'),
(494, 'texto', 'Establece que el cuadrado de la hipotenusa es igual a la suma de los cuadrados de los otros dos lados.'),

(495, 'texto', 'Ocurre cuando cierta forma de energía interactúa con la superficie de algunos materiales.'),
(495, 'texto', 'Provoca la liberación de partículas presentes en los átomos al recibir luz adecuada.'),
(495, 'texto', 'Su explicación permitió avances en la física moderna y en tecnologías como los paneles solares.'),

(496, 'texto', 'Es un instrumento utilizado para observar objetos muy lejanos en el espacio.'),
(496, 'texto', 'En lugar de lentes principales, utiliza espejos para captar y concentrar la luz.'),
(496, 'texto', 'Isaac Newton desarrolló una version que solucionó problemas presentes en diseños anteriores.'),

(497, 'texto', 'Explica el comportamiento de los fluidos cuando se encuentran dentro de recipientes cerrados.'),
(497, 'texto', 'Es la base del funcionamiento de muchas máquinas hidráulicas utilizadas en la actualidad.'),
(497, 'texto', 'Establece que una presión aplicada en un punto se transmite por igual en todas las direcciones.'),

(498, 'texto', 'Es una rama matemática que une conceptos geométricos con expresiones numéricas.'),
(498, 'texto', 'Permite representar figuras y trayectorias mediante ecuaciones.'),
(498, 'texto', 'Fue desarrollada gracias al uso del sistema de coordenadas creado por René Descartes.'),

(499, 'texto', 'Fue una comunidad dedicada al estudio del conocimiento, los números y la filosofía.'),
(499, 'texto', 'Sus integrantes creían que las matemáticas explicaban gran parte del universo.'),
(499, 'texto', 'Fue fundada por Pitágoras en la antigua Grecia.'),

(500, 'texto', 'Estudia la posibilidad de que ocurra un determinado evento.'),
(500, 'texto', 'Se utiliza en juegos de azar, estadísticas, investigaciones y predicciones.'),
(500, 'texto', 'Blaise Pascal realizó aportes fundamentales para el desarrollo de esta área matemática.'),

(501, 'texto', 'Es una obra educativa que ha sido utilizada por generaciones de estudiantes.'),
(501, 'texto', 'Contiene explicaciones detalladas y una gran cantidad de ejercicios matemáticos.'),
(501, 'texto', 'Es el libro más famoso escrito por Aurelio Baldor y uno de los más vendidos en matemáticas.'),

(502, 'texto', 'Fueron temas de gran interés para una comunidad filosófica de la antigua Grecia.'),
(502, 'texto', 'Sus estudios buscaban descubrir patrones y relaciones presentes en la naturaleza.'),
(502, 'texto', 'Constituían una parte fundamental de las investigaciones realizadas por los seguidores de Pitágoras.'),

(503, 'texto', 'Estudia fenómenos que ocurren a escalas extremadamente pequeñas.'),
(503, 'texto', 'Sus principios son diferentes de los observados en la física clásica.'),
(503, 'texto', 'Albert Einstein realizó importantes aportes relacionados con esta área científica además de la relatividad.'),

-- NIVEL INTERMEDIO
(504, 'texto', 'Este acontecimiento ocurrió durante el siglo XIX, varias décadas antes de la Primera Guerra Mundial.'),
(504, 'texto', 'Corresponde al nacimiento de uno de los científicos más influyentes de la historia.'),
(504, 'texto', 'Ese año nació Albert Einstein en Alemania.'),

(505, 'texto', 'Este hecho ocurrió a mediados del siglo XX.'),
(505, 'texto', 'Sucedió después de que su protagonista revolucionara la física moderna.'),
(505, 'texto', 'En este año falleció Albert Einstein en Estados Unidos.'),

(506, 'texto', 'Ocurrió durante el siglo XVII, en una época de grandes avances científicos.'),
(506, 'texto', 'Corresponde al nacimiento de un científico inglés que estudió el movimiento y la gravedad.'),
(506, 'texto', 'Ese año nació Isaac Newton.'),

(507, 'texto', 'Este acontecimiento tenido lugar durante el siglo XVIII.'),
(507, 'texto', 'Ocurrió muchos años después de la publicación de Principia Mathematica.'),
(507, 'texto', 'En este año falleció Isaac Newton.'),

(508, 'texto', 'Sucedió en Francia durante el siglo XVII.'),
(508, 'texto', 'Corresponde al nacimiento de un matemático, físico e inventor.'),
(508, 'texto', 'Ese año nació Blaise Pascal.'),

(509, 'texto', 'Ocurrió en el siglo XVII.'),
(509, 'texto', 'Este científico murió relativamente joven después de realizar importantes aportes a las matemáticas.'),
(509, 'texto', 'En este año falleció Blaise Pascal.'),

(510, 'texto', 'Este hecho ocurrió a finales del siglo XVI.'),
(510, 'texto', 'Corresponde al nacimiento de un filósofo y matemático francés.'),
(510, 'texto', 'Ese año nació René Descartes.'),

(511, 'texto', 'Ocurrió durante la primera mitad del siglo XVII.'),
(511, 'texto', 'Sucedió después de que desarrollara importantes ideas para la geometría moderna.'),
(511, 'texto', 'En este año falleció René Descartes.'),

(512, 'texto', 'Este acontecimiento ocurrió al inicio del siglo XX.'),
(512, 'texto', 'Corresponde al nacimiento de un reconocido autor de libros de matemáticas.'),
(512, 'texto', 'Ese año nació Aurelio Baldor.'),

(513, 'texto', 'Ocurrió durante la segunda mitad del siglo XX.'),
(513, 'texto', 'Para entonces, sus libros ya eran utilizados en numerosos países latinoamericanos.'),
(513, 'texto', 'En este año falleció Aurelio Baldor.'),

(514, 'texto', 'Este acontecimiento tuvo lugar en la Antigua Grecia.'),
(514, 'texto', 'Ocurrió más de quinientos años antes del nacimiento de Cristo.'),
(514, 'texto', 'En este siglo nació Pitágoras.'),

(515, 'texto', 'Este hecho ocurrió en la época clásica de la civilización griega.'),
(515, 'texto', 'Sucedió aproximadamente un siglo después del momento en que nació el filósofo.'),
(515, 'texto', 'En este siglo murió Pitágoras.'),

(516, 'texto', 'Ambos personajes realizaron aportes fundamentales a la física.'),
(516, 'texto', 'Uno vivió en el siglo XVII y el otro en los siglos XIX y XX.'),
(516, 'texto', 'Entre Newton y Einstein, él nació primero.'),

(517, 'texto', 'Ambos científicos son reconocidos mundialmente por sus contribuciones al conocimiento.'),
(517, 'texto', 'Uno vivió 39 años y el otro más de 70 años.'),
(517, 'texto', 'Entre Pascal y Einstein, él vivió más tiempo.'),

(518, 'texto', 'Fue educador, escritor y matemático.'),
(518, 'texto', 'Nació durante los primeros años del siglo XX.'),
(518, 'texto', 'Es la persona que nació en 1906.'),

-- NIVEL DIFICIL
(519, 'texto', 'Este acontecimiento ocurrió a comienzos del siglo XX y transformó la comprensión del universo.'),
(519, 'texto', 'Fue parte de una serie de trabajos científicos que dieron fama mundial a su autor.'),
(519, 'texto', 'En este año Albert Einstein publicó la Teoría de la Relatividad Especial.'),

(520, 'texto', 'Ocurrió diez años después de uno de los descubrimientos más importantes de la física moderna.'),
(520, 'texto', 'Presentó una nueva explicación sobre la gravedad y el comportamiento del espacio-tiempo.'),
(520, 'texto', 'En este año Einstein presentó la Teoría de la Relatividad General.'),

(521, 'texto', 'Esta fecha está relacionada con una de las obras científicas más influyentes de la historia.'),
(521, 'texto', 'El libro estableció las bases de la mecánica clásica.'),
(521, 'texto', 'En este año Isaac Newton publicó Principia Mathematica.'),

(522, 'texto', 'Este trabajo fue realizado durante un período en que las universidades inglesas enfrentaban dificultades debido a una epidemia.'),
(522, 'texto', 'Su creador desarrolló nuevas herramientas matemáticas para estudiar el cambio y el movimiento.'),
(522, 'texto', 'Durante estos años Newton desarrolló el cálculo.'),

(523, 'texto', 'Esta fecha está relacionada con uno de los primeros dispositivos mecánicos para realizar cálculos.'),
(523, 'texto', 'El invento utilizaba ruedas dentadas para efectuar operaciones aritméticas.'),
(523, 'texto', 'En este año Blaise Pascal creó la Pascalina.'),

(524, 'texto', 'Esta fecha está relacionada con un importante aporte al estudio de las combinaciones y probabilidades.'),
(524, 'texto', 'Su autor presentó una organización numérica que hoy lleva su nombre.'),
(524, 'texto', 'En este año Blaise Pascal publicó el Triángulo de Pascal.'),

(525, 'texto', 'Esta obra marcó el nacimiento de una nueva rama de las matemáticas.'),
(525, 'texto', 'Introdujo el uso de coordenadas para representar figuras mediante ecuaciones.'),
(525, 'texto', 'En este año René Descartes publicó "La Geometría".'),

(526, 'texto', 'Desde su publicación se convirtió en uno de los textos de matemáticas más utilizados en América Latina.'),
(526, 'texto', 'Su contenido ha servido para la formación de millones de estudiantes.'),
(526, 'texto', 'En este año se publicó el libro "Álgebra de Baldor".'),

(527, 'texto', 'Fue un período en el que florecieron la filosofía, las matemáticas y la ciencia.'),
(527, 'texto', 'En esa época surgieron importantes pensadores como Sócrates, Platón y Aristóteles.'),
(527, 'texto', 'Pitágoras vivió durante la Antigua Grecia.'),

(528, 'texto', 'Es una ciudad ubicada en el sur de Alemania.'),
(528, 'texto', 'Allí nació uno de los científicos más importantes del siglo XX.'),
(528, 'texto', 'Albert Einstein nació en la ciudad de Ulm.'),

(529, 'texto', 'Es una pequeña localidad ubicada en Inglaterra.'),
(529, 'texto', 'Allí nació uno de los científicos más importantes de la historia.'),
(529, 'texto', 'Isaac Newton nació en Woolsthorpe.'),

(530, 'texto', 'Es una ciudad ubicada en el centro de Francia.'),
(530, 'texto', 'Allí nació un destacado matemático, físico e inventor del siglo XVII.'),
(530, 'texto', 'Blaise Pascal nació en Clermont-Ferrand.'),

(531, 'texto', 'Es una localidad francesa que actualmente lleva el nombre del filósofo.'),
(531, 'texto', 'Allí nació el creador de la geometría analítica.'),
(531, 'texto', 'René Descartes nació en La Haye en Touraine.'),

(532, 'texto', 'Es un país insular ubicado en el mar Caribe.'),
(532, 'texto', 'Allí nació el autor del famoso libro "Álgebra de Baldor".'),
(532, 'texto', 'Aurelio Baldor nació en Cuba.'),

(533, 'texto', 'Es una isla griega ubicada en el mar Egeo.'),
(533, 'texto', 'Allí nació uno de los matemáticos y filósofos más influyentes de la Antigüedad.'),
(533, 'texto', 'Pitágoras nació en la isla de Samos.');
