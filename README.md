# Proyecto de Microservicios con Docker Compose

Este repositorio contiene dos microservicios que se despliegan y configuran de manera simultánea usando Docker Compose:

- **account-transaction-service**: Servicio responsable de la gestión de cuentas y transacciones.
- **customer-service**: Servicio encargado de la gestión de clientes, incluidos los scripts de inicialización de la base de datos.

---

## Estructura del Proyecto

```
PRUEBANNDATA/
├── account-transaction-service/
│   └── (Código y Dockerfile del servicio de transacciones)
├── customer-service/
│   ├── initdb/
│   │   └── 02-schema.sql       # Definición de tablas de `db_customer`
│   ├── .env
│   ├── docker-compose.yml      # Orquesta los contenedores de este servicio
│   └── README.md               # Este archivo
└── docker-compose.yml          # Compose raíz para levantar ambos servicios (opcional)
```

---

## Requisitos Previos

1. Docker instalado (versión ≥ 20.10)
2. Docker Compose instalado (versión ≥ 1.29)
3. Un archivo `.env` en cada servicio con las variables de entorno necesarias.

---

## Variables de Entorno

En `customer-service/.env` define:

```dotenv
POSTGRES_USER=postgres
POSTGRES_PASSWORD=123123
# Otras variables según tu configuración
```

En `account-transaction-service/.env` (si aplica), define las credenciales y URLs de conexión.

---

## Ejecución con Docker Compose

### Levantar un único servicio

```bash
cd customer-service
docker-compose up -d
```

```bash
cd account-transaction-service
docker-compose up -d
```

### Levantar ambos servicios desde la carpeta raíz

```bash
# Si existe un docker-compose.yml en la raíz que referencia ambos servicios
docker-compose up -d
```

Cada contenedor expondrá los puertos configurados en su `docker-compose.yml`. Por ejemplo, el servicio de clientes escuchará en `5432:5432` para PostgreSQL y en el puerto de la API que definas.

---

## Inicialización de la Base de Datos

El directorio `customer-service/initdb` contiene scripts que se ejecutan automáticamente la primera vez que se crea el volumen de datos de PostgreSQL:

1. **01-create-dbs.sh**: Crea las bases de datos (`db_customer`, `db_transaction`).
2. **02-schema.sql**: Define las tablas y relaciones para `db_customer`.

> **Importante:** Si cambias los scripts y necesitas volver a aplicarlos, elimina el volumen de datos con:
> ```bash
> docker-compose down -v
> ```
> y luego vuelve a levantar los contenedores.

---

## Estructura de Carpetas Detallada

### customer-service

- `initdb/`: Scripts de inicialización de PostgreSQL.
- `.env`: Variables de entorno específicas.
- `docker-compose.yml`: Orquesta el servicio de clientes.

### account-transaction-service

- Código fuente del microservicio.
- `Dockerfile`: Define la imagen del servicio.
- `.env`: Variables de entorno específicas.
- `docker-compose.yml`: Orquesta el servicio de transacciones.

---

## Comandos Útiles

- Ver logs:
  ```bash
  docker-compose logs -f
  ```
- Reiniciar un servicio:
  ```bash
  docker-compose restart <service_name>
  ```
- Parar y eliminar contenedores, redes y volúmenes:
  ```bash
  docker-compose down -v
  ```

---

## Licencia

Este proyecto está bajo la [MIT License](LICENSE).
