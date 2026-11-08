# GVPDact2_t4 — CulturaVibe en Spring Boot + Thymeleaf

Act2. Spring MVC — Vistas con Thymeleaf, DTOs y Manejo de Peticiones.
Programación Web, equipo 13.

**Autora:** Perla Danae Gallardo Vasquez (GVPD)
**Proyecto:** CulturaVibe, calendario de eventos culturales en México.
**Stack:** Java 17, Spring Boot 3.5.16, Maven, Spring Web, Thymeleaf.

Este proyecto es una copia del de la Actividad 1 (repositorio
[GVPDact1_t4](https://github.com/PerlaD391/GVPDact1_t4), que sigue
intacto en su propio repo y corriendo en el puerto 8081 del VPS). Sobre esa
copia se agregaron vistas del lado del servidor con Thymeleaf, una clase
DTO, manejo de parámetros (`@RequestParam`, `@PathVariable`,
`@ModelAttribute`), configuración externa con `@Value` y un endpoint POST
que recibe el DTO en JSON.

## Proyecto en vivo (VPS del equipo)

La Act2 corre en el puerto **8083** (la Act1 sigue en el **8081** y Ricardo,
LSR, usa el **8082**). Ambas actividades quedan accesibles al mismo tiempo.

Vistas Thymeleaf:

- http://13.140.226.55:8083/eventos (cartelera, con filtro por categoría)
- http://13.140.226.55:8083/eventos?categoria=Música (filtro con `@RequestParam`)
- http://13.140.226.55:8083/eventos/ev-1 (detalle con `@PathVariable`)
- http://13.140.226.55:8083/eventos/nuevo (formulario con `@ModelAttribute`)

API REST (la misma de la Act1, más el POST nuevo):

- http://13.140.226.55:8083/api/proyecto
- http://13.140.226.55:8083/api/eventos
- http://13.140.226.55:8083/api/eventos/ev-1
- `POST http://13.140.226.55:8083/api/eventos` (recibe un `EventoDTO` en JSON)

## Lo nuevo en esta actividad

| Requisito | Dónde está |
| --------- | ---------- |
| Dependencia Thymeleaf | `pom.xml` (`spring-boot-starter-thymeleaf`) |
| DTO | `src/main/java/mx/culturavibe/act2/dto/EventoDTO.java` |
| Controlador MVC (vistas) | `src/main/java/mx/culturavibe/act2/controlador/EventoVistaController.java` |
| Vistas | `src/main/resources/templates/eventos/` (`lista`, `detalle`, `formulario`, `creado`) |
| `@RequestParam` | `GET /eventos?categoria=Música` (filtra la lista) |
| `@PathVariable` | `GET /eventos/{id}` (detalle) |
| `@ModelAttribute` | `POST /eventos` (formulario de nuevo evento) |
| `@Value` | `culturavibe.nombre` y `culturavibe.autora` en `application.properties`, mostradas en el encabezado de todas las vistas |
| POST REST con DTO | `POST /api/eventos` con `@RequestBody EventoDTO` (regresa 201 con el evento creado) |

### El DTO

`EventoDTO` representa los datos de un evento para la vista y para la
petición: nombre, fecha, hora, sede, dirección, ciudad, estado, categoría,
precio, estado de venta, descripción, artistas (como texto separado por
comas, para que el formulario lo pueda capturar) y enlace de boletos.
Tiene dos métodos de conversión: `EventoDTO.desde(Evento)` y
`EventoDTO.toEvento(id)`, para pasar del DTO a la entidad y al revés sin
exponer la entidad directamente en la vista ni en la petición.

## Capturas de pantalla

### Vista con el listado (th:each funcionando)

![Listado de eventos](docs/lista.png)

### Filtro por categoría (@RequestParam)

![Filtro por categoría Música](docs/filtro-categoria.png)

### Detalle de un evento (@PathVariable)

![Detalle del evento ev-1](docs/detalle.png)

### Formulario con @ModelAttribute

![Formulario para agregar un evento](docs/formulario.png)

### Petición POST probada en Postman

La petición POST se probó en Postman enviando este JSON a
`http://13.140.226.55:8083/api/eventos`:

```json
{
  "nombre": "Obra de Teatro de Prueba",
  "fecha": "2026-12-15",
  "hora": "18:00",
  "sede": "Teatro Prueba",
  "direccion": "Calle Prueba 5",
  "ciudad": "Puebla",
  "estado": "PUE",
  "categoria": "Teatro",
  "precio": "$150",
  "estadoVenta": "onsale",
  "descripcion": "Obra creada por POST JSON",
  "artistas": "Compañía Prueba",
  "url": "https://www.ticketmaster.com.mx"
}
```

Respuesta: **201 Created** con el evento creado (id `ev-8`, con los
artistas ya como lista en el JSON de respuesta).

![POST probado en Postman](docs/postman-post.png)

También se puede probar con curl:

```bash
curl -X POST http://13.140.226.55:8083/api/eventos \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Obra de Teatro de Prueba","fecha":"2026-12-15","hora":"18:00","sede":"Teatro Prueba","direccion":"Calle Prueba 5","ciudad":"Puebla","estado":"PUE","categoria":"Teatro","precio":"$150","estadoVenta":"onsale","descripcion":"Obra creada por POST JSON","artistas":"Compañía Prueba","url":"https://www.ticketmaster.com.mx"}'
```

## Correr en local (para pruebas en tu máquina)

Requisitos: JDK 17 (Maven no hace falta instalarlo, el proyecto trae el
Maven Wrapper). El servidor levanta en `http://localhost:8083`.

```bash
./mvnw spring-boot:run
```

O compilar el jar:

```bash
./mvnw clean package
java -jar target/gvpdact2-t4-0.0.1-SNAPSHOT.jar
```

En local se prueban las mismas vistas con `http://localhost:8083`,
por ejemplo `http://localhost:8083/eventos`.

## Estructura

```
src/main/java/mx/culturavibe/act2/
├── CulturavibeAct2Application.java   # Arranque de Spring Boot
├── dto/
│   └── EventoDTO.java                # DTO para la vista y el POST
├── modelo/
│   ├── Evento.java                   # Evento de la cartelera
│   ├── Integrante.java               # Integrante del equipo
│   ├── ResumenDashboard.java         # Resumen del panel principal
│   └── ProximoEvento.java            # Próximo evento + días restantes
├── servicio/
│   ├── EventoService.java            # Catálogo (ahora editable: agregar, por categoría)
│   └── DashboardService.java         # Datos del panel principal
├── controlador/
│   ├── ProyectoController.java       # /api/proyecto (REST, Act1)
│   ├── EventoController.java         # /api/eventos (REST + POST con DTO)
│   ├── DashboardController.java      # /api/dashboard (REST, Act1)
│   ├── EventoVistaController.java    # /eventos (vistas Thymeleaf)
│   └── HomeController.java           # / redirige a /eventos
src/main/resources/
├── application.properties            # server.port=8083 + culturavibe.nombre/autora
└── templates/eventos/                # lista, detalle, formulario, creado
```

## Despliegue en el VPS del equipo

La Act1 **no se toca**: sigue corriendo en el puerto 8081. Esta versión va
a una carpeta distinta y a un puerto distinto.

```bash
# En el VPS (una sola vez)
sudo ufw allow 8083/tcp

# En tu máquina: compilar y subir el jar a una carpeta NUEVA
./mvnw clean package
scp target/gvpdact2-t4-0.0.1-SNAPSHOT.jar root@13.140.226.55:/opt/springboot-act2/

# En el VPS: correr en segundo plano (la Act1 sigue en 8081)
nohup java -jar /opt/springboot-act2/gvpdact2-t4-0.0.1-SNAPSHOT.jar > /opt/springboot-act2/app.log 2>&1 &

# Verificar desde fuera que AMBAS respondan al mismo tiempo
curl http://13.140.226.55:8081/api/proyecto
curl http://13.140.226.55:8083/eventos
```
