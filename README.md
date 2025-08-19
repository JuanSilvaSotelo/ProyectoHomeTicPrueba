# Proyecto Técnica - Sistema de Ventas

Este proyecto es un sistema de ventas completo que consta de un backend desarrollado con Spring Boot y un frontend desarrollado con React.

## Estructura del Proyecto

El repositorio está organizado de la siguiente manera:

```
Proyecto_tecnica/
├── backend/                  # Contiene el código fuente del backend (Spring Boot)
│   └── ventas-backend/
├── database/                 # Contiene los scripts SQL para la base de datos
│   ├── schema.sql            # Esquema de la base de datos
│   └── seed.sql              # Datos de ejemplo para la base de datos
└── frontend/                 # Contiene el código fuente del frontend (React)
    └── ventas-frontend/
```

## Requisitos Previos

Antes de comenzar, asegúrate de tener instalado lo siguiente:

*   **Java Development Kit (JDK) 17 o superior**: Necesario para el backend de Spring Boot.
    *   [Descargar JDK](https://www.oracle.com/java/technologies/downloads/)
*   **Maven 3.8.x o superior**: Herramienta de gestión de proyectos para el backend.
    *   [Descargar Maven](https://maven.apache.org/download.cgi)
*   **Node.js 14.x o superior y npm (Node Package Manager)**: Necesario para el frontend de React.
    *   [Descargar Node.js](https://nodejs.org/en/download/)
*   **MySQL Server 8.x o superior**: Base de datos utilizada por el backend.
    *   [Descargar MySQL Community Server](https://dev.mysql.com/downloads/mysql/)
*   **Un cliente MySQL (opcional, pero recomendado)**: Como MySQL Workbench, DBeaver, o la línea de comandos para gestionar la base de datos.

## Configuración de la Base de Datos

1.  **Crear la Base de Datos**: Crea una base de datos MySQL llamada `ventas_db`.

    ```sql
    CREATE DATABASE ventas_db;
    ```

2.  **Ejecutar Scripts SQL**: Navega al directorio `database/` y ejecuta los scripts `schema.sql` y `seed.sql` en tu base de datos `ventas_db`.

    ```bash
    # Ejemplo usando el cliente MySQL de línea de comandos
    mysql -u tu_usuario -p ventas_db < schema.sql
    mysql -u tu_usuario -p ventas_db < seed.sql
    ```

3.  **Configurar Credenciales**: Abre el archivo `backend/ventas-backend/src/main/resources/application.properties` y actualiza las credenciales de la base de datos si son diferentes a las predeterminadas.

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/ventas_db?useSSL=false&serverTimezone=UTC
    spring.datasource.username=root
    spring.datasource.password=tu_contraseña
    spring.jpa.properties.hibernate.jdbc.time_zone=UTC
    ```

## Configuración y Ejecución del Backend

1.  **Navegar al Directorio del Backend**: Abre tu terminal y navega al directorio `backend/ventas-backend`.

    ```bash
    cd backend/ventas-backend
    ```

2.  **Construir el Proyecto**: Compila el proyecto Maven.

    ```bash
    mvn clean install
    ```

3.  **Ejecutar el Backend**: Inicia la aplicación Spring Boot.

    ```bash
    mvn spring-boot:run
    ```

    El backend se ejecutará en `http://localhost:8080` por defecto.

## Configuración y Ejecución del Frontend

1.  **Navegar al Directorio del Frontend**: Abre una nueva terminal y navega al directorio `frontend/ventas-frontend`.

    ```bash
    cd frontend/ventas-frontend
    ```

2.  **Instalar Dependencias**: Instala las dependencias de Node.js.

    ```bash
    npm install
    ```

3.  **Ejecutar el Frontend**: Inicia la aplicación React.

    ```bash
    npm start
    ```

    El frontend se ejecutará en `http://localhost:3000` por defecto.

## Uso de la Aplicación

Una vez que tanto el backend como el frontend estén en ejecución, puedes acceder a la aplicación en tu navegador a través de `http://localhost:3000`.

## Solución de Problemas Comunes

*   **Problemas de Conexión a la Base de Datos**: Asegúrate de que MySQL Server esté en ejecución y que las credenciales en `application.properties` sean correctas.
*   **Problemas de Zona Horaria**: Si experimentas discrepancias de 5 horas en las fechas, asegúrate de que `serverTimezone=UTC` esté configurado en la URL de la base de datos en `application.properties` y que `spring.jpa.properties.hibernate.jdbc.time_zone=UTC` también esté presente. Además, verifica que la JVM esté utilizando UTC como su zona horaria predeterminada (esto se configuró en `pom.xml`).
*   **Puerto en Uso**: Si alguna de las aplicaciones no se inicia debido a que el puerto ya está en uso, puedes cambiar el puerto en `application.properties` para el backend o en el archivo `.env` (o similar) para el frontend.