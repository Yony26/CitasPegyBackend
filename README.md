# Citas Peggy Backend

Aplicacion Java de consola con Maven y SQL Server. La agenda de citas consulta y guarda datos en `dbo.Appointments`, `dbo.Services` y `dbo.Specialists`. La administracion de usuarios se mantiene en memoria y sus datos se pierden al cerrar la aplicacion.

## Preparar SQL Server

1. En SQL Server Management Studio, crea una base de datos llamada `CitasPegy`.
2. Selecciona esa base de datos y ejecuta el archivo `Base de datos tercer momento.sql`.
3. **Advertencia:** el script empieza eliminando las tablas del mismo nombre. Ejecutalo solamente sobre una base de datos de desarrollo que puedas reiniciar.
4. Habilita conexiones TCP/IP y autenticacion SQL Server, y confirma el puerto y las credenciales de tu instancia.

El script incluye citas de prueba. El alta desde la aplicacion requiere que el cliente, el especialista y el servicio existan; ademas, el especialista y el servicio deben pertenecer al mismo negocio.

## Configuracion

Para usar autenticacion de Windows, la aplicacion lee `DB_AUTH=windows` y requiere la DLL nativa de autenticacion de Microsoft JDBC. Ejecuta una vez lo siguiente en PowerShell (en la terminal donde vayas a iniciar la app):

```powershell
$authZip = Join-Path $env:TEMP "mssql-jdbc_auth.zip"
$authDir = Join-Path $env:TEMP "mssql-jdbc_auth"
Invoke-WebRequest "https://github.com/microsoft/mssql-jdbc/releases/download/v12.8.1/mssql-jdbc_auth.zip" -OutFile $authZip
Expand-Archive $authZip $authDir -Force
$env:PATH = "$(Join-Path $authDir 'x64');$env:PATH"
$env:DB_AUTH = "windows"
$env:DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=CitasPegy;encrypt=true;trustServerCertificate=true"
.\mvnw.cmd exec:java
```

La DLL extraida debe coincidir con la arquitectura de Java. Con la autenticacion de Windows, no configures `DB_USER` ni `DB_PASSWORD`. Si abres otra terminal, vuelve a ejecutar estas instrucciones para establecer sus variables de entorno.

Para autenticacion SQL, usa las variables:

| Variable | Valor |
| --- | --- |
| `DB_URL` | URL JDBC completa; por defecto `jdbc:sqlserver://localhost:1433;databaseName=CitasPegy;encrypt=true;trustServerCertificate=true` |
| `DB_USER` | Usuario SQL Server |
| `DB_PASSWORD` | Contrasena SQL Server |

En PowerShell, configura las variables solo para la terminal actual:

```powershell
$env:DB_USER = "sa"
$env:DB_PASSWORD = "tu contrasena"
$env:DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=CitasPegy;encrypt=true;trustServerCertificate=true"
```

Reemplaza `sa`, la contrasena, el servidor y el puerto por los de tu instalacion.

## Compilar y ejecutar

Requiere JDK 11 o superior. El wrapper incluido descarga Maven 3.9.9 automaticamente la primera vez:

```powershell
.\mvnw.cmd clean compile
.\mvnw.cmd exec:java
```

El menu principal permite administrar citas y usuarios. La base de datos asigna el codigo de reserva `CP-####` automaticamente.
