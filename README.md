# Pedidos - Ejercicio técnico

## Consideraciones generales

 - Se utilizó java 8 y las posibilidades de código a partir de dicha versión.
 - Gradle para build y run con dependencias de maven.
 - El IDE utilizado es Intellij IDEA versión para la comunidad.
 - Se utilizó lombok y micronaut por lo que hay que configurar el IDE para que tenga AnnotationProcessor activo. A su vez para la compilación es requerido lombok [plugin activo](https://projectlombok.org/setup/intellij).

## 1

### Modelo
Para representar el POJO de Pedidos utilice lombok. De esta manera la clase queda simple a la vista. Los campos son los del enunciado.
Agregue una interface Persistable para identificar al modelo de dominio con una clase que posee id. Esto fue realizado para poder establecer una key luego en memcached.
Se agregaron algunos mapeos con java persistence api para representar el modelo a nivel persistencia. No se implemento la persistencia pero se definio la estructura de esta manera. El idPedido es probable que sea autoincremental, como el enunciado no decía nada al respecto, no se agregó la annotation @GeneratedValue.

### DAO
#### Clases dadas por implementadas
En el enunciado se mencionó que se tomen como implementadas las clases BumexMemcached y PedidosDAO por lo que se agregaron en el paquete org.navent.et.dao.implementadas solo a modo definicion. En BumexMemcached se definió posible implementación de Singleton para su uso.
#### Diseño propio
Agregue un paquete util que tiene un KeyManager para generar key al impactar en Memcached. En KeyManager se hace uso del dominio que implemente Persistable (ver la nota que hice en [Modelo](# Modelo)
El pedido tiene un id Integer por lo que la implementación de KeyManager utilizada sería IntegerKeyManager.
Agregué un ProxyDAO interface para definir la interacción del DAO de acuerdo al enunciado. En la implementación se hace uso de las clases BumexMemcached y PedidosDAO.
#### Service
Lo anterior mencionado fue para cumplir con la consigna en el diseño del servicio PedidosService. Se realizó la interface y su implementación PedidosServiceImpl. Para el manejo de excepciones de agregó una excepción propia. Los métodos son intentan ser inmutables. El argumento no será modificado sino que se devolverá un resultado con la modificación o busqueda realizada.

#### Notas
En todas las capas se utilizó el estandar JSR330 con javax.inject. Solo a modo de proponer el diseño.

## 2
Teniendo en cuenta que la tabla Pedido tenga muchos registros y columnas habría que:
- Definir correctamente los tipos de datos según la necesidad concreta. Por ejemplo los numéricos tener en cuenta en qué rangos pueden manejarse para utilizar menos bits por valor si no es necesario.
- Generar índices para los campos involucrados en consultas dentro de where, aunque es bueno considerar dejar de lado los campos que tienen un alto número de nulls ya que puede incrementar el espacio del índice en algunos motores.
- Utilizar pooles de conexión a nivel aplicación
- Retornar en las consultas, en select los campos que se necesitan realmente para disminuir accesos I/O.
- Intentar utilizar DTOs para los servicios involucrados, retornando los campos necesarios para evitar responses con mucha información y que la red no se sobrecargue.
- Generar tablas con datos sumarizados o un cubo y modificar la responsabilidad de aplicaciones que interactuan con los pedidos. Para lo que es aplicaciones de consultas gerenciales utilizar posiblemente un Data Warehouse.
- En caso de alto tráfico si se necesita utilizar muchas conexiones al motor se puede considerar en hacer réplicas de la base de datos y tener un balanceador de carga. (Implica complejidad de gestión de los datos como contra)
- Para los BLOB sería posible utilizar una tabla aparte que referencie a la tabla Pedidos y se maneje a nivel aplicación su obtención de manera retardada (lazy) En la página de MySQL se recomienda incluso considerar utilizar otro motor, en diferente instancia, para algún campo BLOB debido al costo que tiene obtener información de dicho campo y que tendría consecuencias de performance a nivel lectura e implicancias en la red. Incluso se menciona que que se puede requerir a nivel físico una lectura secuencial grande que es más conveniente en discos tradicionales que en los de estado sólido (SDD).

*Nota:* Estoy acostumbrado a trabajar con MySQL.

## 3
### Ejecución
Desde el root del proyecto ejecutar: <br>
`./gradlew run` <br>
Luego abrir ./front/index.html.

Para la implementación de este enunciado se realizó otro módulo en el proyecto llamado front. Allí está el html index.html para el formulario del pedido y un js, pedidoForm.js que tiene la lógica de validaciones, muestra de mensajes e interacción mediante AJAX con el backend. Se optó por bootstrap, jquery (slim version) y vanilla Javascript.
El backend es el proyecto del enunciado 1 al que se le agregó un controller utilizando el framework micronaut. El controller es casi dummy, tiene un random para poder tener casos de éxito y de fallos y poder ver cómo reacciona el form ante dichos casos. Aproveché este ejercicio técnico para probar muy rudimentariamente el framework.
Se realizaron las validaciones solo a nivel cliente. Se podría haber importado javax.validation, definir las constraint en el modelo Pedido y con hibernate-validator realizar las validaciones en el controller.

 


