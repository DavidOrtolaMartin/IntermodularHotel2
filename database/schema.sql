
CREATE TABLE provincia (
    id_provincia INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);


CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_provincia INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    apellido1 VARCHAR(50) NOT NULL,
    apellido2 VARCHAR(50),
    email VARCHAR(100) NOT NULL UNIQUE,
    tlf1 VARCHAR(15) NOT NULL,
    tlf2 VARCHAR(15),
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    password VARCHAR(255) NOT NULL,

    CONSTRAINT fk_user_provincia
        FOREIGN KEY (id_provincia)
        REFERENCES provincia(id_provincia)
);



CREATE TABLE categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    precio INT NOT NULL,
    descripcion VARCHAR(255)
);


CREATE TABLE habitacion (
    id_habitacion INT AUTO_INCREMENT PRIMARY KEY,
    categoria_id INT NOT NULL,
    num_hab INT NOT NULL,

    CONSTRAINT fk_habitacion_categoria
        FOREIGN KEY (categoria_id)
        REFERENCES categoria(id_categoria)
);


CREATE TABLE reserva (
    id_reserva INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    hab_id INT NOT NULL,
    fecha_desde DATE NOT NULL,
    fecha_hasta DATE NOT NULL,
    pagado BOOLEAN,
    fecha_pagado DATE,

    CONSTRAINT fk_reserva_user
        FOREIGN KEY (user_id)
        REFERENCES users(id),

    CONSTRAINT fk_reserva_habitacion
        FOREIGN KEY (hab_id)
        REFERENCES habitacion(id_habitacion)
);

CREATE TABLE categoria_imagenes (
   id INT AUTO_INCREMENT PRIMARY KEY,
  
   categoria_id INT NOT NULL,
  
   url VARCHAR(500) NOT NULL,

   CONSTRAINT fk_categoria_imagen
       FOREIGN KEY (categoria_id)
       REFERENCES categoria(id_categoria)
       ON DELETE CASCADE
);
