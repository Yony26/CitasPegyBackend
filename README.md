# CitasPegyBackend

## SQL Server local

La aplicación guarda las citas y los administradores en SQL Server mediante JDBC y
autenticación integrada de Windows. Al iniciar, crea la base `Citapegy` si no existe
y crea las tablas e índices necesarios. La cuenta de Windows que ejecuta la aplicación
debe tener permiso para crear bases de datos la primera vez y acceso de lectura/escritura
después.

La configuración predeterminada es:

- Servidor: `LAPTOP-ON7D89Q9\SQLEXPRESS`
- Base de datos: `Citapegy`

Puedes cambiarla con las variables de entorno `CITASPEGY_DB_SERVER` y
`CITASPEGY_DB_NAME`.

### Requisito para autenticación de Windows

Además de Java 11 o superior y Maven, descarga la versión 12.8.1 del Microsoft JDBC
Driver para SQL Server y copia `mssql-jdbc_auth-12.8.1.x64.dll` (o la variante `x86`
si tu JVM es de 32 bits) desde su carpeta `auth` a una carpeta incluida en `PATH`.
La arquitectura de la DLL debe coincidir con la de Java.

### Ejecutar

Desde la raíz del proyecto:

```powershell
mvn clean compile
mvn exec:java
```

La conexión cifra el tráfico y confía en el certificado del servidor local, adecuado
para una instancia de desarrollo. Para producción, configura un certificado confiable.