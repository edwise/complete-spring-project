[![Build status](https://circleci.com/gh/edwise/complete-spring-project.svg?style=shield)](https://circleci.com/gh/edwise/complete-spring-project)

Proyecto montado con Spring Boot y Java 11, con los siguientes frameworks / libraries / funcionalidades:

 - Spring Boot: versión 2. posibilidad de arrancar directamente con el plugin de maven o generar un war para despliegue en 
 tomcat o similar. Con 'actuator' activado.

 - Servicio completo RESTful con Spring 5 (Books)

 - Uso de HATEOAS en el servicio

 - Documentado servicio con Swagger 2 (Springfox)

 - Capa de base de datos con Spring DATA mongoDB

 - Jacoco para la cobertura de tests (plugin para maven)

 - Lombok para evitar código 'boilerplate'

 - Spring Exception Handling en los controllers
 
 - Validaciones en los entities, y envío de errores
 
 - Test de integración completos (mockeando el service)

 - Logs, con slfj4/logback configurados para escribir a fichero

 - Spring Security activo, con autenticación básica

 - Añadidas las developer tools de Spring Boot 2
 
 - Uso de flapdoodle para simular la base de datos en los tests de integración


Requisitos:

 - Maven (instalado y configurado)

 - mongoDB server (instalado y arrancado, en localhost y con el puerto por defecto)


Comandos

 - Arrancar directamente con el plugin de SpringBoot:
 
    ```
    mvn spring-boot:run
    ```
  
  
 - Generar war, ejecutando test unitarios (así como generar informes de jacoco):
 
    ```
    mvn clean package
    ```


 - Generar war, ejecutando solo test de integración:
 
    ```
    mvn clean verify -P integration-test
    ```

Usuarios de acceso:

 - Usuario para acceso a servicios rest (/api/*)    -> user1 : password1

 - Usuario para acceso a administración (/actuator/*)  -> admin : password1234


Urls de acceso:

 - Swagger check -> http://localhost:8080/v2/api-docs?group=books-api

 - Swagger UI    -> http://localhost:8080/swagger-ui.html

 - REST Books    -> http://localhost:8080/api/books/

 - Jacoco        -> DIRECTORIO_PROYECTO/target/sites/jacoco/index.html

 - Logs          -> DIRECTORIO_PROYECTO/logs/csp*.log

 - Spring Boot actuator endpoints:

     http://localhost:8080/actuator/health

     http://localhost:8080/actuator/info

     ...

Fuentes:

 - http://www.spring.io/

 - http://www.mkyong.com/mongodb/spring-data-mongodb-auto-sequence-id-example/
 
 - http://springfox.io/
 
 - http://www.petrikainulainen.net/programming/spring-framework/integration-testing-of-spring-mvc-applications-write-clean-assertions-with-jsonpath/
 
 - http://www.petrikainulainen.net/programming/maven/integration-testing-with-maven/
 
 - http://heidloff.net/article/usage-of-swagger-2-0-in-spring-boot-applications-to-document-apis/
 
 - http://docs.spring.io/spring-security/site/docs/4.0.x/reference/html/test-mockmvc.html
 
 - https://github.com/fakemongo/fongo
 
