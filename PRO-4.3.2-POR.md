
# Code Smelling y Refactorización

## 1. **Revisión del código**

Para inspeccionar el código, lo único necesario es hacer clic derecho en la carpeta donde se encuentra el código, ir al apartado de Analyze y seleccionar Inspect Code.

![Analyze_code.png](IMAGENES/CODE%20SMELL/Analyze_code.png)

Entonces, hay que elegir los archivos que se quieren inspeccionar. En este caso, se elige la carpeta src del proyecto, para que se inspeccione todo el código.

![Elegir_archivos.png](IMAGENES/CODE%20SMELL/Elegir_archivos.png)

Una vez que lo ejecutas aparece una ventana con los resultados de la inspección como esta.

![Resultados_inspeccion.png](IMAGENES/CODE%20SMELL/Resultados_inspeccion.png)

Como se puede comprobar, existen varios tipos de problemas con el código. En este caso, se han encontrado los siguientes problemas:

- Unused declaration: Hay variables o funciones que se declaran pero no se utilizan en el código.

- Unresolved reference in KDoc: Hay referencias en la documentación del código que no se pueden resolver.

- Redundant qualifier name: Hay nombres de calificadores no necesarios en el código, por ejemplo, en EstadoTarea para la función getEstado, no es necesario poner el nombre de la clase antes del nombre del estado en sí, puesto que al estar en la misma clase tiene acceso a eso. 

- Unused symbol: Hay símbolos que no se usan o no se puede llegar a ellos en el código.

- Class member can have 'private' visibility: Como su nombre indica, hay ciertas variables y funciones que podrían ser privadas, ya que se utilizan en otra función de la misma clase y no se utilizan fuera de ella.

## 2. **Aplicación de refactorizaciones**

Para aplicar las refactorizaciones, se puede hacer de dos maneras. La primera es ir a la línea donde se encuentra el problema y hacer clic derecho. En el menú que aparece, hay que ir al apartado de Refactor y seleccionar la opción que se quiere aplicar.

![Refactor_clickDrc.png](IMAGENES/CODE%20SMELL/Refactor_clickDrc.png)

La segunda opción es ir al menú de la parte superior de la pantalla y seleccionar Refactor. En el menú que aparece, se puede elegir la opción que se quiere aplicar.

![Refactor_menu.png](IMAGENES/CODE%20SMELL/Refactor_menu.png)

Para el problema de Unused Symbol, vamos a usar la opción de safe delete de Refactor, eliminando así la función obtenerDesc() que no se utiliza de la clase Actividad.

![Unused_symbol.png](IMAGENES/CODE%20SMELL/Unused_symbol.png)

Para el problema de Redundant qualifier name, vamos a quitar la referencia a la clase en sí, ya que al estar en la misma clase no le hace falta, eliminando el nombre de la clase EstadoTarea en la función getEstado.

![Redundant_qualifier_name.png](IMAGENES/CODE%20SMELL/Redundant_qualifier_name.png)

Para el problema de Class member can have 'private' visibility, vamos a cambiar la visibilidad de las variables y funciones que se usan en las clases pero no fuera de ellas a private.

![Class_member_can_have_private_visibility.png](IMAGENES/CODE%20SMELL/Class_member_can_have_private_visibility.png)

## 3. **Desarrollo de pruebas**

He creado las pruebas unitarias usando esta vez JUnit, ya que es como se pide. He probado las clases cambiadas por la refactorización de esta práctica, comprobando que cambiar el código no ha afectado a la lógica del mismo.

Este es el resultado de las pruebas unitarias después de la refactorización, después de cada imagen voy a dejar un enlace a la clase que corresponde y al test:

![EstadoTareaTest_resultados.png](IMAGENES/CODE%20SMELL/EstadoTareaTest_resultados.png)

[EstadoTarea](https://github.com/PabloOstenero/TaskManager-POSTREY-2/blob/P4.3.2-POR/MEJORA-TASK/src/main/kotlin/dominio/EstadoTarea.kt)

[EstadoTareaTest](https://github.com/PabloOstenero/TaskManager-POSTREY-2/blob/P4.3.2-POR/MEJORA-TASK/src/test/kotlin/EstadoTareaTest.kt)

![UtilsTest_resultados.png](IMAGENES/CODE%20SMELL/UtilsTest_resultados.png)

[Utils](https://github.com/PabloOstenero/TaskManager-POSTREY-2/blob/P4.3.2-POR/MEJORA-TASK/src/main/kotlin/Utils.kt)

[UtilsTest](https://github.com/PabloOstenero/TaskManager-POSTREY-2/blob/P4.3.2-POR/MEJORA-TASK/src/test/kotlin/UtilsTest.kt)

![ActividadTest_resultados.png](IMAGENES/CODE%20SMELL/ActividadTest_resultados.png)

[Actividad](https://github.com/PabloOstenero/TaskManager-POSTREY-2/blob/P4.3.2-POR/MEJORA-TASK/src/main/kotlin/dominio/Actividad.kt)

[ActividadTest](https://github.com/PabloOstenero/TaskManager-POSTREY-2/blob/P4.3.2-POR/MEJORA-TASK/src/test/kotlin/ActividadTest.kt)

Como se puede comprobar en las imágenes, todas las clases han pasado las pruebas correctamente, por lo que los cambios realizados no han afectado a la lógica y funcionan igual.

## 4. **Respuesta a las preguntas**

[1]
1.a **¿Qué code smell y patrones de refactorización has aplicado?**

He eliminado la función obetenerDesc() de la clase Actividad, ya que no se usaba en ningún sitio. Este es un code smell de Unused Symbol, y el patrón de refactorización que he aplicado es Safe Delete.

He eliminado el nombre de la clase EstadoTarea en la función getEstado, ya que al estar en la misma clase no le hace falta. Este es un code smell de Redundant qualifier name, y el patrón de refactorización que he aplicado es Remove redundant qualifier.

He cambiado la visibilidad de las variables y funciones que se usan en las clases pero no fuera de ellas a private. Este es un code smell de Class member can have 'private' visibility, y el patrón de refactorización que he aplicado es Change member visibility.

1.b **Teniendo en cuenta aquella funcionalidad que tiene pruebas unitarias, selecciona un patrón de refactorización de los que has aplicado y que están cubierto por los test unitarios. ¿Porque mejora o no mejora tu código? Asegurate de poner enlaces a tu código**

Voy a seleccionar el patrón de Change member visibility, el cual mejora el código porque mejora la encapsulación de las variables y funciones, no permitiendo que cualquier otra clase lo use. Gracias a esto, el código es más seguro, ya que, por ejemplo si se cambia el nombre de una variable, no afecta a otras clases que no deberían usarla.


[2]
2.a Describe el proceso que sigues para asegurarte que la refactorización no afecta a código que ya tenias desarrollado.

Para asegurarme de que la refactorización no afecta al código, pruebo el programa otra vez para que no me dé errores, además de hacer pruebas unitarias para probar que todo funciona correctamente.


[3]
3.a ¿Que funcionalidad del IDE has usado para aplicar la refactorización seleccionada? Si es necesario, añade capturas de pantalla para identificar la funcionalidad.

Como ya pone en el primer apartado de este documento, he usado la opción de Refactor del IDE, que se puede encontrar haciendo clic derecho en la línea donde se encuentra el problema o en el menú de la parte superior de la pantalla. En este caso, he usado la opción de Change member visibility para cambiar la visibilidad de las variables y funciones que no se usan fuera de su clase a private.
