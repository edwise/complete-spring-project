Proyecto montado con Spring Boot, con los siguientes frameworks / libraries / funcionalidades:

 - Spring Boot: posibilidad de arrancar directamente con el plugin de maven o generar un war para despliegue en 
 tomcat o similar. Con 'actuator' activado.

 - Servicio completo RESTful con Spring 4 (Books)

 - Uso de HATEOAS en el servicio

 - Documentado servicio con Swagger

 - Capa de base de datos con Spring DATA mongoDB

 - Jacoco para la cobertura de tests (plugin para maven)

 - Lombok para evitar algo de código 'boilerplate'

 - Spring Exception Handling en los controllers
 
 - Validaciones en los entities, y envío de errores



Requisitos:

 - Maven (instalado y configurado)

 - mongoDB server (instalado y arrancado, en localhost y con el puerto por defecto)


Comandos

 - Arrancar directamente con el plugin de SpringBoot:
 
    ```
    mvn spring-boot:run
    ```
  
  
 - Generar war (así como generar informes de jacoco):
 
    ```
    mvn clean package
    ```


Urls de acceso:

 - Swagger check -> http://localhost:8080/api-docs

 - Swagger UI    -> http://localhost:8080/swagger/index.html

 - REST Books    -> http://localhost:8080/api/book

 - Jacoco        -> DIRECTORIO_PROYECTO/target/sites/jacoco/index.html

- Spring Boot actuator endpoints -> http://localhost:8080/env
                                    http://localhost:8080/beans
                                    http://localhost:8080/mappings
                                    ...

Fuentes:

 - http://www.spring.io/

 - http://www.mkyong.com/mongodb/spring-data-mongodb-auto-sequence-id-example/
 
 - https://github.com/martypitt/swagger-springmvc
 