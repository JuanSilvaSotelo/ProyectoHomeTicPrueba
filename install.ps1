```powershell
# install.ps1 - Script de Instalación para el Sistema de Ventas
# Este script automatiza la instalación y configuración del sistema de ventas, incluyendo:
# 1. Verificación e instalación de requisitos previos (JDK, Maven, Node.js, npm, MySQL).
# 2. Configuración de la base de datos (creación de DB, ejecución de scripts SQL, actualización de credenciales).
# 3. Construcción e inicio del backend (Spring Boot).
# 4. Instalación de dependencias e inicio del frontend (React).

Write-Host "Iniciando la instalación del Sistema de Ventas..."

# --- 1. Requisitos Previos ---
# Esta sección verifica la presencia de software esencial y proporciona instrucciones para su instalación si no se encuentran.

Write-Host "\n--- Verificando e instalando requisitos previos ---"

# Función para verificar e instalar software
# Parámetros:
# - Name: Nombre del software a verificar.
# - CheckCommand: Comando para verificar si el software está instalado.
# - InstallInstructions: Instrucciones para la instalación manual del software.
function Install-Software {
    param (
        [string]$Name,
        [string]$CheckCommand,
        [string]$InstallInstructions
    )
    Write-Host "Verificando $Name..."
    try {
        # Intenta ejecutar el comando de verificación. Si tiene éxito, el software está instalado.
        Invoke-Expression $CheckCommand | Out-Null
        Write-Host "$Name ya está instalado."
    } catch {
        # Si el comando falla, el software no está instalado. Muestra instrucciones y espera la confirmación del usuario.
        Write-Host "$Name no encontrado. Por favor, instálalo manualmente siguiendo estas instrucciones:"
        Write-Host $InstallInstructions
        Read-Host "Presiona Enter para continuar después de instalar $Name..."
    }
}

# JDK (Java Development Kit): Necesario para el backend de Spring Boot.
Install-Software -Name "JDK 17+" -CheckCommand "java -version" -InstallInstructions "Descarga e instala JDK 17 o superior desde https://www.oracle.com/java/technologies/downloads/"

# Maven: Herramienta de gestión de proyectos y construcción para el backend.
Install-Software -Name "Maven" -CheckCommand "mvn -version" -InstallInstructions "Descarga e instala Maven desde https://maven.apache.org/download.cgi y asegúrate de que esté en tu PATH."

# Node.js y npm: Necesario para el frontend de React.
Install-Software -Name "Node.js y npm" -CheckCommand "node -v; npm -v" -InstallInstructions "Descarga e instala Node.js (que incluye npm) desde https://nodejs.org/en/download/"

# MySQL Server: Base de datos para almacenar la información del sistema.
Write-Host "Verificando MySQL Server..."
Write-Host "Asegúrate de que MySQL Server esté instalado y en ejecución. Puedes descargarlo desde https://dev.mysql.com/downloads/mysql/"
Read-Host "Presiona Enter para continuar después de verificar MySQL Server..."

# --- 2. Configuración de la Base de Datos ---
# Esta sección solicita las credenciales de MySQL, crea la base de datos y ejecuta los scripts SQL.

Write-Host "\n--- Configurando la Base de Datos ---"

# Solicita al usuario el nombre de usuario y la contraseña de MySQL.
$dbUser = Read-Host "Ingresa tu nombre de usuario de MySQL (ej. root)"
$dbPass = Read-Host -AsSecureString "Ingresa tu contraseña de MySQL"
# Convierte la contraseña segura a texto plano para usarla con el comando mysql.
$dbPassPlain = [System.Runtime.InteropServices.Marshal]::PtrToStringAuto([System.Runtime.InteropServices.Marshal]::SecureStringToBSTR($dbPass))

Write-Host "Creando la base de datos 'ventas_db' y ejecutando scripts..."
try {
    # Crea la base de datos 'ventas_db' si no existe.
    mysql -u $dbUser -p$dbPassPlain -e "CREATE DATABASE IF NOT EXISTS ventas_db;"
    # Ejecuta el script de esquema para crear las tablas.
    mysql -u $dbUser -p$dbPassPlain ventas_db < ".\database\schema.sql"
    # Ejecuta el script de datos iniciales para poblar las tablas.
    mysql -u $dbUser -p$dbPassPlain ventas_db < ".\database\seed.sql"
    Write-Host "Base de datos configurada exitosamente."
} catch {
    # Manejo de errores si la configuración de la base de datos falla.
    Write-Error "Error al configurar la base de datos: $($_.Exception.Message)"
    exit 1
}

Write-Host "Actualizando credenciales de la base de datos en application.properties..."
# Define la ruta al archivo de propiedades de la aplicación Spring Boot.
$appPropertiesPath = ".\backend\ventas-backend\src\main\resources\application.properties"
# Actualiza el nombre de usuario y la contraseña de la base de datos en el archivo application.properties.
(Get-Content $appPropertiesPath) -replace 'spring.datasource.username=.*', "spring.datasource.username=$dbUser" `
                               -replace 'spring.datasource.password=.*', "spring.datasource.password=$dbPassPlain" `
                               | Set-Content $appPropertiesPath
Write-Host "Credenciales actualizadas."

# --- 3. Configuración y Ejecución del Backend ---
# Esta sección navega al directorio del backend, construye el proyecto y lo inicia.

Write-Host "\n--- Configurando y Ejecutando el Backend ---"

# Cambia el directorio actual al directorio del backend.
Set-Location -Path ".\backend\ventas-backend"

Write-Host "Construyendo el proyecto backend..."
try {
    # Ejecuta los comandos Maven para limpiar y construir el proyecto.
    mvn clean install
    Write-Host "Backend construido exitosamente."
} catch {
    # Manejo de errores si la construcción del backend falla.
    Write-Error "Error al construir el backend: $($_.Exception.Message)"
    exit 1
}

Write-Host "Iniciando el backend (esto puede tardar un momento)..."
# Inicia el backend de Spring Boot en un proceso separado.
Start-Process -FilePath "mvn" -ArgumentList "spring-boot:run" -NoNewWindow
Write-Host "Backend iniciado en http://localhost:8080"

# Vuelve al directorio raíz del proyecto.
Set-Location -Path "..".." # Volver al directorio raíz del proyecto

# --- 4. Configuración y Ejecución del Frontend ---
# Esta sección navega al directorio del frontend, instala las dependencias y lo inicia.

Write-Host "\n--- Configurando y Ejecutando el Frontend ---"

# Cambia el directorio actual al directorio del frontend.
Set-Location -Path ".\frontend\ventas-frontend"

Write-Host "Instalando dependencias del frontend..."
try {
    # Ejecuta npm install para instalar todas las dependencias del proyecto frontend.
    npm install
    Write-Host "Dependencias del frontend instaladas exitosamente."
} catch {
    # Manejo de errores si la instalación de dependencias del frontend falla.
    Write-Error "Error al instalar dependencias del frontend: $($_.Exception.Message)"
    exit 1
}

Write-Host "Iniciando el frontend..."
# Inicia la aplicación frontend de React en un proceso separado.
Start-Process -FilePath "npm" -ArgumentList "start" -NoNewWindow
Write-Host "Frontend iniciado en http://localhost:3000"

# Vuelve al directorio raíz del proyecto.
Set-Location -Path "..".." # Volver al directorio raíz del proyecto

Write-Host "\nInstalación completada. El backend y el frontend se están ejecutando en segundo plano."
Write-Host "Puedes acceder a la aplicación en http://localhost:3000"
Write-Host "Para detener las aplicaciones, puedes cerrar las ventanas de la terminal o usar el Administrador de Tareas."}]}}}
```