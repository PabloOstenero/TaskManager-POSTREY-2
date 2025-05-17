
# Pruebas y Depuración

1. **Selección del servicio**

Voy a utilizar el servicio ActividadService ya que realmente es el que más contenido tiene y el que tiene una mayor cantidad de posibles errores.

2. **Identificación de métodos**

Puesto que el servicio tiene muchos métodos, voy a centrarme en algunos que pueden ser interesantes. En este caso, los métodos que he elegido son:
- `agregarSubtarea()`
- `ususariosConActividades()`
- `filtrar()`
- `filtrarPorEstado()`

Estos métodos no eran públicos inicialmente porque por nuestra lógica todo se hacía desde una sola función, la cual llamaba al resto de métodos, pero por facilidad de las pruebas, he decidido cambiar esto, puesto que si no no podría comprobar ninguna excepto la principal, lo cual no me serviría de nada.

3. **Diseño de los casos de prueba**

 | Método                    | Caso de prueba                                                                  | Resultado esperado                                   |
 |---------------------------|---------------------------------------------------------------------------------|------------------------------------------------------|
 | agregarSubtarea()         | Agregar una subtarea a una tarea existente                                      | La subtarea se agrega correctamente a la tarea       |
 | agregarSubtarea()         | Agregar una subtarea a una tarea inexistente                                    | Se lanza un mensaje indicando que la tarea no existe |
 | ususariosConActividades() | Enlaza los usuarios con sus actividades correspondientes                        | Muestra un mensaje de confirmación                   |
 | usuariosConActividades()  | Ocurre un error                                                                 | Se muestra un mensaje de error                       |
 | filtrar()                 | Filtrar actividades por un criterio específico (en este caso filtra por estado) | Llama al filtro especificado (filtrarPorEstado())    |
 | filtrar()                 | No existe la opción indicada                                                    | Se lanza un error indicando que esa opción no existe |
 | filtrarPorEstado()        | Filtrar actividades por estado especificado                                     | Muestra las actividades filtradas                    |
 | filtrarPorEstado()        | No existe el estado indicado                                                    | Se lanza un error indicando que ese estado no existe |

4. **Ejecución y reporte de resultados**

Actualmente todos los tests pasan la inspección, aunque he tenido que cambiar parte del código para que esto ocurriese, ya que había algún fallo mal implementado, que para algún caso daba error o se mostraba algún mensaje que no debería.

Por ejemplo, en el método filtrar(), al pulsar el botón de salir, se mostraba un mensaje de error, cuando realmente no debería, ya que el usuario ha decidido salir y no filtrar nada.

La ejecución de los tests tarda aproximadamente 2 segundos, aunque esto depende de la máquina puesto que estoy usando un portátil antiguo y ya no tiene la misma capacidad que uno nuevo.
