
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
