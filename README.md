
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