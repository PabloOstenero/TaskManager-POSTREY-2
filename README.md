
# Listing

En esta actividad vamos a instalar y utilizar Detekt, una herramienta de análisis estático para Kotlin.
Voy a hacer una guía de como instalarlo y vamos a utilizarlo para encontrar errores en el código del Task Manager.

1. **Instalando Detekt**

- Para instalar Detekt hay que añadir el plugin en el archivo `build.gradle`:

![detekt_plugin.png](IMAGENES/LISTING/detekt_plugin.png)

- En este mismo archivo se agrega la configuración básica de Detekt:

![detekt_config.png](IMAGENES/LISTING/detekt_config.png)

2. **Ejecutando Detekt**

- Para ejecutar Detekt, sólo tengo que usar el siguiente comando:

![detekt_comando.png](IMAGENES/LISTING/detekt_comando.png)

- Al ejecutar este comando, sale una pantalla con el resumen de los errores encontrados como esta:

![detekt_resumen.png](IMAGENES/LISTING/detekt_resumen.png)

3. **Identificando tipos de errores**

ERROR 1: `NestedBlockDepth`

![Error_NestedBlockDepth.png](IMAGENES/LISTING/Error_NestedBlockDepth.png)

[Commit antes de solucionar error NestedBlockDepth](https://github.com/PabloOstenero/TaskManager-POSTREY-2/commit/bd584b406c2528f70abc6187e7b127f90cd3a861)

Este es el commit anterior a la correción del error. En este commit, la función cargarActividades tiene muchas llaves anidadas, lo cual no es en si un fallo, pero puede llevar a una mayor dificultad en la lectura del código. Para solucionar este error, se debe reducir la cantidad de llaves mediante funciones que sean más pequeñas y cumplan una sola función.

![cargarActividades_antes.png](IMAGENES/LISTING/cargarActividades_antes.png)

Así quedaría la función cargarActividades después de la corrección:

![cargarActividades_despues.png](IMAGENES/LISTING/cargarActividades_despues.png)

