
## 1. **Comentarios KDoc**

Aquí voy a poner un enlace a las clases a las que les he añadido documentación KDoc:

- [RepoUsuarios](https://github.com/PabloOstenero/TaskManager-POSTREY-2/blob/4.4.1-POR/MEJORA-TASK/src/main/kotlin/accesoDatos/RepoUsuarios.kt)

- [RepoActividades](https://github.com/PabloOstenero/TaskManager-POSTREY-2/blob/4.4.1-POR/MEJORA-TASK/src/main/kotlin/accesoDatos/RepoActividades.kt)

- [ActividadService](https://github.com/PabloOstenero/TaskManager-POSTREY-2/blob/4.4.1-POR/MEJORA-TASK/src/main/kotlin/servicios/ActividadService.kt)

## 2. **Generación con Dokka**

Para generar la documentación con Dokka, he seguido los siguientes pasos:

He añadido la dependencia de Dokka en el archivo build.gradle:

![build_gradle_dokka.png](IMAGENES/DOCUMENTACION%20CON%20KDOC%20Y%20DOKKA/build_gradle_dokka.png)

Una vez hecho esto, hay que ejecutar el comando para la creación del sitio web de la documentación:

![Comando_dokka.png](IMAGENES/DOCUMENTACION%20CON%20KDOC%20Y%20DOKKA/Comando_dokka.png)

La documentación se genera en la carpeta build/dokka/html del proyecto. En mi caso, el resultado es el siguiente:

![dokka_resultado.png](IMAGENES/DOCUMENTACION%20CON%20KDOC%20Y%20DOKKA/dokka_resultado.png)

Por seguir los pasos de la práctica, he cambiado la ruta del sitio web de la documentación a la carpeta doc.

Igualmente, aquí voy a dejar un enlace a la carpeta donde se encuentra la documentación generada por Dokka:

[Documentación generada por Dokka](https://github.com/PabloOstenero/TaskManager-POSTREY-2/tree/4.4.1-POR/doc/dokka/html)
