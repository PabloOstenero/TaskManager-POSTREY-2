
[1]
1.a ¿Que herramienta has usado, y para que sirve?

He usado Detekt, una herramienta de análisis estático para Kotlin.

Esta herramienta sirve para detectar errores en el código, identificar problemas de estilo como los nombres de los paquetes, los nombres de las variables o el uso de un salto de línea al final de cada archivo para cumplir con las convenciones de Kotlin.
También ayuda a detectar errores de diseño, como tener algún parámetro que no se utiliza.

1.b ¿Cuales son sus características principales?

- Detección de problemas de estilo y convenciones de código.
- Configuración personalizable.
- Integración con herramientas de construcción como Gradle.
- Generación de informes en HTML y XML.

1.c ¿Qué beneficios obtengo al utilizar dicha herramienta?

Esta herramienta ayuda a mejorar la calidad del código, ya que por las cosas que detecta puedes mejorar la legibilidad de los archivos y la lógica del código.

[2]
2.a De los errores/problemas que la herramienta ha detectado y te ha ayudado a solucionar, ¿cual es el que te ha parecido que ha mejorado más tu código?

Creo que de los errores que he solucionado, el que más ha mejorado mi código ha sido `NestedBlockDepth`, ya que, al reducir el número de llaves anidadas, permite que el código sea más legible y escalable, puesto que si necesitas cambiar la lógica de la función, puedes hacerlo por partes en lugar de tener que intentar saber donde debes hacer los cambios en una función gigantesca.

2.b ¿La solución que se le ha dado al error/problema la has entendido y te ha parecido correcta?

Sí, la solución ha sido correcta, ya que dividiendo la función en varias funciones más pequeñas, se consigue hacer lo que ya he dicho, qu el código sea más legible y que a la hora de revisarlo o cambiarlo sea mucho más cómodo y fácil.

2.c ¿Por qué se ha producido ese error/problema?

El error se había producido porque la función no tenía una responsabilidad única, sino que recogía los datos y los manipulaba, mientras que ahora recoge los datos y los va pasando a otra función para que los maneje.

[3]
3.a ¿Que posibilidades de configuración tiene la herramienta?

Detekt permite hacer muchas configuraciones, como activar o desactivar reglas específicas de análisis y su severidad, excluir archivos a la hora de hacer el análisis, crear informes en HTML o XML o ignorar errores ya reportados anteriormente.

3.b De esas posibilidades de configuración, ¿cuál has configurado para que sea distinta a la que viene por defecto?

He usado la opción baseline, haciendo que los errores que ya se han mostrado no vuelvan a aparecer, permitiendo ver claramente errores nuevos que se hayan podido producir durante la refactorización del código.

3.c Pon un ejemplo de como ha impactado en tu código, enlazando al código anterior al cambio, y al posterior al cambio,

Hay muchos ejemplos en el README, pero por ejemplo me ha ayudado a detectar alguna variable que no se utilizaba, como la variable privada consola de la clase UsuariosService, o la variable e de la enum class EtiquetasTareas, creada inicialmente para mostrar un string con el nombre de la etiqueta que al final no se utiliza para nada.

[4]
4 ¿Qué conclusiones sacas después del uso de estas herramientas?

Es una herramienta muy útil para la mejora de la legibilidad del código sobre todo, siendo capaz de identificar algún fallo, pero realmente está mas hecho para seguir convenciones y lo ya dicho, que el código sea flexible y legible.