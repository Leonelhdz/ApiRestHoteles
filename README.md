# API REST SPRINGBOOT 
## HOTELES ANDALUCIA

Esta es una API REST para gestionar información sobre hoteles en la provincia Andaluza.


## Funcionalidades

- Obtener todos los hoteles disponibles.
- Obtener un hotel específico por su ID.
- Crear un nuevo hotel.
- Actualizar la información de un hotel existente.
- Eliminar un hotel.
- Buscar hoteles por ciudad.
- Buscar hoteles por provincia.
- Buscar hoteles por nombre o dirección.
- Buscar hoteles por categoría.
- Encontrar el hotel más cercano a una ubicación específica.
- 
## Requisitos

- Java JDK 11 o superior
- Maven (opcional, si estás usando Maven)
- IntelliJ IDEA (opcional, si estás usando IntelliJ)

## Instalación

1. Clona este repositorio: `git clone https://github.com/tu-usuario/tu-proyecto.git`
2. Importa el proyecto en tu IDE favorito.
3. Ejecuta la aplicación desde tu IDE o usando Maven: `mvn spring-boot:run`

## Uso

La API REST ofrece endpoints para realizar operaciones CRUD (crear, leer, actualizar, eliminar) sobre hoteles. 
A continuación se muestran algunos ejemplos de uso:
### Obtener todos los hoteles
`GET /v1/hoteles/hoteles`
#### Ejemplo del resultado obtenido

### Obtener un hotel por su ID: 
`GET /v1/hoteles/hoteles/{id}`

### Crear un nuevo hotel: 
`POST /v1/hoteles/hoteles`

Envía un objeto JSON con los detalles del hotel en el cuerpo de la solicitud.
### Actualizar un hotel existente: 
`PUT /v1/hoteles/hoteles/{id}`

### Eliminar un hotel existente: 
`DELETE /v1/hoteles/hoteles/{id}`

### Buscar hoteles por ciudad
GET /v1/hoteles/ciudad/{ciudad}

### Buscar hoteles por provincia
GET /v1/hoteles/provincia/{provincia}

### Buscar hoteles por nombre o dirección
GET /v1/hoteles/buscar

### Buscar hoteles por categoría
GET /v1/hoteles/categoria/{categoria}

### Encontrar el hotel más cercano
GET /v1/hoteles/hotel-cercano?latitud={latitud}&longitud={longitud}

## Conclusiones

En conclusión, este proyecto ofrece una API REST completa y funcional para la gestión de información sobre hoteles. A través de esta API, los usuarios pueden realizar diversas operaciones, como agregar nuevos hoteles, actualizar la información de hoteles existentes, eliminar hoteles y buscar hoteles por diferentes criterios como ciudad, provincia, nombre, dirección y categoría.

Además, se ha implementado un mecanismo de seguridad mediante tokens de acceso, lo que garantiza que solo los usuarios autorizados puedan realizar modificaciones en la base de datos de hoteles. Esto proporciona una capa adicional de seguridad a la aplicación.

Por último, la funcionalidad de encontrar el hotel más cercano a una ubicación específica añade valor a la API, especialmente para aplicaciones de reserva de hoteles basadas en la ubicación.

En resumen, esta API proporciona una solución completa y robusta para la gestión de información de hoteles, lo que la hace adecuada para su uso en una variedad de aplicaciones web o móviles relacionadas con la industria hotelera.