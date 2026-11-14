# CitasPegyBackend

La aplicación administra citas y administradores sin conexión a una base de datos.
Los datos se guardan únicamente en memoria y se pierden al cerrar la aplicación;
los datos que existan en SQL Server no se importan ni se modifican.

## Ejecutar

Se requiere Java 11 o superior. Desde la raíz del proyecto, en PowerShell:

```powershell
New-Item -ItemType Directory -Force out | Out-Null
javac -encoding UTF-8 -d out (Get-ChildItem app -Recurse -Filter *.java | ForEach-Object { $_.FullName })
java -cp out Application
```

## Comprobar

```powershell
javac -encoding UTF-8 -cp out -d out (Get-ChildItem tests -Recurse -Filter *.java | ForEach-Object { $_.FullName })
java -cp out RepositoriosEnMemoriaTest
```
