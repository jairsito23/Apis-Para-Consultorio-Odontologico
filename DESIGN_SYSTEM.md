# Consultorio Odontológico - Design System Document

## 1. Arquitectura General

### 1.1 Microservicios
| Servicio | Puerto | Base Path | Base de Datos |
|----------|--------|-----------|---------------|
| **Auth Service** | 8081 | `/api/auth` | `db_auth` |
| **Paciente Service** | 8082 | `/api/pacientes` | `db_pacientes` |
| **Odontólogo Service** | 8083 | `/api/odontologos` | `db_odontologos` |
| **Cita Service** | 8084 | `/api/citas` | `db_citas` |
| **Historial Service** | 8085 | `/api/historiales` | `db_historiales` |

**Stack:** Java 17, Spring Boot 3.2.5 (Web, Data JPA, Security, Validation), JJWT, Lombok, MySQL en `localhost:3306`.

### 1.2 API Gateway (Caddy)
- **Puerto**: 8000
- **Routing**: Path-based (`/api/auth*`, `/api/pacientes*`, `/api/odontologos*`, `/api/citas*`, `/api/historiales*`)
- **Headers de seguridad**: `X-Content-Type-Options: nosniff`, `X-Frame-Options: DENY`, `Referrer-Policy: strict-origin-when-cross-origin`
- **Log**: `caddy_gateway.log`
- **CORS**: no está configurado ni en Caddy ni en los servicios (ver 4.3)

### 1.3 Arranque
```powershell
.\start-all.ps1   # levanta los 5 servicios y Caddy
.\stop-all.ps1    # mata todos los procesos java y caddy
```

---

## 2. Endpoints API

Todas las rutas se consumen a través del gateway: `http://localhost:8000`.

### 2.1 Autenticación (`/api/auth`)
| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| POST | `/api/auth/register` | Registro de usuario | No |
| POST | `/api/auth/login` | Inicio de sesión, retorna JWT | No |

**Payload Register:**
```json
{
  "username": "string (3-50)",
  "email": "string (email válido)",
  "password": "string (mín. 6)",
  "rol": "ADMIN | ODONTOLOGO | RECEPCIONISTA"
}
```

**Response Register (201):**
```json
{
  "mensaje": "Usuario registrado exitosamente"
}
```

**Payload Login:**
```json
{
  "username": "string",
  "password": "string"
}
```

**Response Login (200):**
```json
{
  "token": "jwt_token",
  "tipo": "Bearer",
  "username": "string",
  "rol": "ADMIN | ODONTOLOGO | RECEPCIONISTA"
}
```

---

### 2.2 Pacientes (`/api/pacientes`)
| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| GET | `/api/pacientes` | Listar todos los pacientes | Bearer (ver 4.2) |
| GET | `/api/pacientes/{id}` | Obtener paciente por ID | Bearer (ver 4.2) |
| POST | `/api/pacientes` | Crear nuevo paciente | Bearer (ver 4.2) |
| PUT | `/api/pacientes/{id}` | Actualizar paciente | Bearer (ver 4.2) |
| DELETE | `/api/pacientes/{id}` | Eliminar paciente | Bearer (ver 4.2) |

**Payload Paciente:**
```json
{
  "nombre": "string",
  "apellido": "string",
  "dni": "string",
  "telefono": "string (opcional)",
  "email": "string (opcional)",
  "direccion": "string (opcional)",
  "fechaNacimiento": "YYYY-MM-DD (opcional)"
}
```

**Response Paciente:**
```json
{
  "id": "number",
  "nombre": "string",
  "apellido": "string",
  "dni": "string",
  "telefono": "string",
  "email": "string",
  "direccion": "string",
  "fechaNacimiento": "YYYY-MM-DD"
}
```

**Response DELETE:** `{ "mensaje": "Paciente eliminado exitosamente" }`

---

### 2.3 Odontólogos (`/api/odontologos`)
| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| GET | `/api/odontologos` | Listar todos los odontólogos | Bearer (ver 4.2) |
| GET | `/api/odontologos/{id}` | Obtener odontólogo por ID | Bearer (ver 4.2) |
| POST | `/api/odontologos` | Crear nuevo odontólogo | Bearer (ver 4.2) |
| PUT | `/api/odontologos/{id}` | Actualizar odontólogo | Bearer (ver 4.2) |
| DELETE | `/api/odontologos/{id}` | Eliminar odontólogo | Bearer (ver 4.2) |

**Payload Odontólogo:**
```json
{
  "nombre": "string",
  "apellido": "string",
  "matricula": "string",
  "especialidad": "string (opcional)",
  "telefono": "string (opcional)"
}
```

**Response Odontólogo:**
```json
{
  "id": "number",
  "nombre": "string",
  "apellido": "string",
  "matricula": "string",
  "especialidad": "string",
  "telefono": "string"
}
```

**Response DELETE:** `{ "mensaje": "Odontólogo eliminado exitosamente" }`

---

### 2.4 Citas (`/api/citas`)
| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| GET | `/api/citas` | Listar todas las citas | Bearer (ver 4.2) |
| GET | `/api/citas/{id}` | Obtener cita por ID | Bearer (ver 4.2) |
| GET | `/api/citas/paciente/{pacienteId}` | Citas de un paciente | Bearer (ver 4.2) |
| GET | `/api/citas/odontologo/{odontologoId}` | Citas de un odontólogo | Bearer (ver 4.2) |
| POST | `/api/citas` | Crear nueva cita | Bearer (ver 4.2) |
| PUT | `/api/citas/{id}` | Actualizar cita | Bearer (ver 4.2) |
| DELETE | `/api/citas/{id}` | Eliminar cita | Bearer (ver 4.2) |

**Payload Cita:**
```json
{
  "fechaHora": "YYYY-MM-DDTHH:mm:ss",
  "motivo": "string",
  "notas": "string (opcional)",
  "estado": "string (solo se aplica en PUT)",
  "pacienteId": "number",
  "odontologoId": "number"
}
```

**Response Cita:**
```json
{
  "id": "number",
  "fechaHora": "YYYY-MM-DDTHH:mm:ss",
  "estado": "PROGRAMADA | COMPLETADA | CANCELADA",
  "motivo": "string",
  "notas": "string",
  "pacienteId": "number",
  "odontologoId": "number"
}
```

**Estados válidos:** `PROGRAMADA`, `COMPLETADA`, `CANCELADA`

- En POST el `estado` enviado se ignora: toda cita nace como `PROGRAMADA`.
- En PUT el `estado` es opcional y no distingue mayúsculas de minúsculas.
- No hay filtros por query string (ni por fecha ni por estado); solo las rutas por paciente y por odontólogo.

**Response DELETE:** `{ "mensaje": "Cita eliminada exitosamente" }`

---

### 2.5 Historiales Clínicos (`/api/historiales`)
| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| GET | `/api/historiales` | Listar todos los historiales | Bearer (ver 4.2) |
| GET | `/api/historiales/{id}` | Obtener historial por ID | Bearer (ver 4.2) |
| GET | `/api/historiales/paciente/{pacienteId}` | Historiales de un paciente | Bearer (ver 4.2) |
| POST | `/api/historiales` | Crear nuevo historial | Bearer (ver 4.2) |
| PUT | `/api/historiales/{id}` | Actualizar historial | Bearer (ver 4.2) |
| DELETE | `/api/historiales/{id}` | Eliminar historial | Bearer (ver 4.2) |

**Payload Historial:**
```json
{
  "fecha": "YYYY-MM-DD",
  "diagnostico": "string",
  "tratamiento": "string",
  "observaciones": "string (opcional)",
  "pacienteId": "number",
  "odontologoId": "number"
}
```

**Response Historial:**
```json
{
  "id": "number",
  "fecha": "YYYY-MM-DD",
  "diagnostico": "string",
  "tratamiento": "string",
  "observaciones": "string",
  "pacienteId": "number",
  "odontologoId": "number"
}
```

**Response DELETE:** `{ "mensaje": "Historial clínico eliminado exitosamente" }`

---

## 3. Modelos de Datos

### 3.1 Usuario (Auth)
```typescript
type Rol = 'ADMIN' | 'ODONTOLOGO' | 'RECEPCIONISTA';

interface Usuario {
  id: number;
  username: string;  // unique, max 50
  email: string;     // unique, max 100
  password: string;  // hash BCrypt, nunca se devuelve
  rol: Rol;
}
```

### 3.2 Paciente
```typescript
interface Paciente {
  id: number;
  nombre: string;           // max 100
  apellido: string;         // max 100
  dni: string;              // unique, max 20
  telefono?: string;        // max 20
  email?: string;           // max 100
  direccion?: string;       // max 200
  fechaNacimiento?: string; // YYYY-MM-DD
}
```
La entidad guarda además `fechaRegistro` (se asigna al crear), pero la API no lo devuelve.

### 3.3 Odontólogo
```typescript
interface Odontologo {
  id: number;
  nombre: string;        // max 100
  apellido: string;      // max 100
  matricula: string;     // unique, max 30
  especialidad?: string; // max 100
  telefono?: string;     // max 20
}
```

### 3.4 Cita
```typescript
type EstadoCita = 'PROGRAMADA' | 'COMPLETADA' | 'CANCELADA';

interface Cita {
  id: number;
  fechaHora: string;     // ISO local YYYY-MM-DDTHH:mm:ss (sin zona horaria)
  estado: EstadoCita;
  motivo: string;        // max 255
  notas?: string;
  pacienteId: number;
  odontologoId: number;
}
```

### 3.5 Historial Clínico
```typescript
interface HistorialClinico {
  id: number;
  fecha: string;          // YYYY-MM-DD
  diagnostico: string;
  tratamiento: string;
  observaciones?: string;
  pacienteId: number;
  odontologoId: number;
}
```

### 3.6 Relaciones
Cada servicio tiene su propia base de datos, así que `pacienteId` y `odontologoId` son números sin llave foránea. El backend no comprueba que el paciente o el odontólogo existan al crear una cita o un historial; el frontend debe ofrecer selectores cargados desde `/api/pacientes` y `/api/odontologos` y resolver los nombres por su cuenta.

---

## 4. Autenticación y Autorización

### 4.1 Flujo de Autenticación
1. Usuario hace POST a `/api/auth/login` con credenciales
2. Backend retorna JWT (HS256, expira en 24 h) junto con `username` y `rol`
3. Frontend almacena token, `username` y `rol`
4. Requests subsiguientes incluyen header: `Authorization: Bearer <token>`

El token solo lleva el `username` como `subject`. El rol no viaja en el token: el frontend lo conoce únicamente por la respuesta del login.

### 4.2 Protección de Rutas (estado real)
- **Públicas**: `/api/auth/register`, `/api/auth/login`
- **Pacientes, odontólogos, citas e historiales**: los cuatro servicios validan el token si llega, pero su `SecurityConfig` usa `.anyRequest().permitAll()`. Hoy responden igual sin token.

Para que la protección sea real hay que cambiar `permitAll()` por `authenticated()` en esos cuatro servicios. El frontend debe enviar siempre el header para no romperse cuando se haga ese cambio.

No hay autorización por rol en el backend. Cualquier restricción por rol que haga el frontend (ocultar menús o botones) es solo visual.

### 4.3 CORS
No hay configuración de CORS. Un frontend servido desde otro origen (por ejemplo Vite en `http://localhost:5173`) será bloqueado por el navegador. Opciones:
- Proxy de desarrollo en Vite hacia `http://localhost:8000`
- Servir el build del frontend desde el mismo Caddy (`:8000`)
- Añadir headers CORS en el Caddyfile

---

## 5. Frontend - Estructura Sugerida

### 5.1 Rutas de la Aplicación
| Ruta | Componente | Descripción | Auth |
|------|------------|-------------|------|
| `/login` | LoginPage | Formulario de inicio de sesión | No |
| `/register` | RegisterPage | Registro de usuario con rol | No |
| `/dashboard` | Dashboard | Citas del día y totales | Sí |
| `/pacientes` | PacientesList | Listado y gestión de pacientes | Sí |
| `/pacientes/nuevo` | PacienteForm | Crear paciente | Sí |
| `/pacientes/:id` | PacienteDetail | Ver/editar paciente, sus citas e historial | Sí |
| `/odontologos` | OdontologosList | Listado y gestión de odontólogos | Sí |
| `/odontologos/nuevo` | OdontologoForm | Crear odontólogo | Sí |
| `/odontologos/:id` | OdontologoDetail | Ver/editar odontólogo y su agenda | Sí |
| `/citas` | CitasList | Listado/agenda de citas | Sí |
| `/citas/nueva` | CitaForm | Crear cita | Sí |
| `/citas/:id` | CitaDetail | Ver/editar cita, cambiar estado | Sí |
| `/historiales` | HistorialesList | Listado de historiales clínicos | Sí |
| `/historiales/nuevo` | HistorialForm | Crear historial | Sí |
| `/historiales/:id` | HistorialDetail | Ver/editar historial | Sí |

### 5.2 Estados Globales (State Management)
- **Auth State**: username, rol, token, isAuthenticated
- **Pacientes State**: list, selected, loading, error
- **Odontólogos State**: list, selected, loading, error
- **Citas State**: list, filters (paciente, odontólogo, estado, fecha), selected, loading, error
- **Historiales State**: list, filters (paciente), selected, loading, error

Los filtros de citas por estado y fecha se aplican en el cliente, porque la API no los ofrece.

---

## 6. Componentes UI Reutilizables

### 6.1 Formularios
- `InputField` - label, error, required, type
- `SelectField` - options, placeholder (rol, estado)
- `EntitySelect` - selector con búsqueda de paciente u odontólogo
- `DatePicker` - formato YYYY-MM-DD (fechaNacimiento, fecha de historial)
- `DateTimePicker` - formato YYYY-MM-DDTHH:mm:ss (fechaHora de cita)
- `TextArea` - notas, diagnóstico, tratamiento, observaciones
- `Button` - variant (primary, secondary, danger), loading, disabled

### 6.2 Tablas/Listados
- `DataTable` - columns, data, pagination (en cliente), sorting, actions
- `EmptyState` - illustration, message, action button
- `LoadingSkeleton` - placeholder durante fetch

### 6.3 Feedback
- `Toast/Notification` - success, error, warning, info
- `Modal/Dialog` - confirmaciones de borrado, formularios
- `Badge` - estados de cita (PROGRAMADA, COMPLETADA, CANCELADA) y rol de usuario

### 6.4 Layout
- `Header` - logo, username, rol, logout
- `Sidebar` - navegación principal
- `PageContainer` - padding, max-width
- `Card` - contenedor con sombra/border

---

## 7. Diseño Visual (Tokens)

### 7.1 Colores
```css
/* Primarios */
--color-primary: #0d9488;       /* Teal 600 */
--color-primary-hover: #0f766e; /* Teal 700 */
--color-primary-light: #ccfbf1; /* Teal 100 */

/* Semánticos */
--color-success: #16a34a;      /* Green 600 */
--color-warning: #ea580c;      /* Orange 600 */
--color-danger: #dc2626;       /* Red 600 */
--color-info: #0284c7;         /* Sky 600 */

/* Neutros */
--color-bg: #f8fafc;           /* Slate 50 */
--color-surface: #ffffff;      /* White */
--color-border: #e2e8f0;       /* Slate 200 */
--color-text: #1e293b;         /* Slate 800 */
--color-text-muted: #64748b;   /* Slate 500 */

/* Estados de cita */
--color-badge-programada: #e0f2fe;  /* Sky 100 */
--color-badge-completada: #dcfce7;  /* Green 100 */
--color-badge-cancelada: #fee2e2;   /* Red 100 */
```

### 7.2 Tipografía
```css
--font-family: 'Inter', -apple-system, sans-serif;
--font-size-xs: 0.75rem;   /* 12px */
--font-size-sm: 0.875rem;  /* 14px */
--font-size-base: 1rem;    /* 16px */
--font-size-lg: 1.125rem;  /* 18px */
--font-size-xl: 1.25rem;   /* 20px */
--font-size-2xl: 1.5rem;   /* 24px */
--font-weight-normal: 400;
--font-weight-medium: 500;
--font-weight-semibold: 600;
--font-weight-bold: 700;
```

### 7.3 Espaciado
```css
--space-1: 0.25rem;  /* 4px */
--space-2: 0.5rem;   /* 8px */
--space-3: 0.75rem;  /* 12px */
--space-4: 1rem;     /* 16px */
--space-5: 1.25rem;  /* 20px */
--space-6: 1.5rem;   /* 24px */
--space-8: 2rem;     /* 32px */
--space-10: 2.5rem;  /* 40px */
--space-12: 3rem;    /* 48px */
```

### 7.4 Border Radius
```css
--radius-sm: 0.25rem;  /* 4px */
--radius-md: 0.375rem; /* 6px */
--radius-lg: 0.5rem;   /* 8px */
--radius-xl: 0.75rem;  /* 12px */
--radius-full: 9999px;
```

### 7.5 Sombras
```css
--shadow-sm: 0 1px 2px 0 rgb(0 0 0 / 0.05);
--shadow-md: 0 4px 6px -1px rgb(0 0 0 / 0.1);
--shadow-lg: 0 10px 15px -3px rgb(0 0 0 / 0.1);
```

---

## 8. Validaciones Frontend

### 8.1 Reglas que ya exige el backend
**Registro**
- `username`: required, 3-50 chars, único
- `email`: required, formato email, único
- `password`: required, min 6 chars
- `rol`: required, uno de `ADMIN`, `ODONTOLOGO`, `RECEPCIONISTA`

**Login**
- `username`, `password`: required

**Paciente**
- `nombre`: required, max 100
- `apellido`: required, max 100
- `dni`: required, max 20, único
- `telefono`, `email`, `direccion`, `fechaNacimiento`: opcionales, sin validación de formato

**Odontólogo**
- `nombre`: required, max 100
- `apellido`: required, max 100
- `matricula`: required, max 30, único
- `especialidad`, `telefono`: opcionales

**Cita**
- `fechaHora`: required
- `motivo`: required
- `pacienteId`, `odontologoId`: required
- `estado`: si se envía en PUT, debe ser un estado válido

**Historial Clínico**
- `fecha`: required
- `diagnostico`: required
- `tratamiento`: required
- `pacienteId`, `odontologoId`: required

### 8.2 Reglas adicionales recomendadas (solo frontend)
- `telefono`: patrón numérico según país
- `email` de paciente: formato email
- `fechaNacimiento`: no futura
- `fechaHora` de cita: no en el pasado al crear
- `fecha` de historial: no futura
- `pacienteId`, `odontologoId`: elegidos de un selector, nunca escritos a mano
- `motivo`: max 255 (límite de la columna)
- `telefono`: max 20, `direccion`: max 200 (límites de columna)

---

## 9. Manejo de Errores

### 9.1 Comportamiento actual del backend
Solo `odontologo-service` tiene un `GlobalExceptionHandler`. Formato de error en ese servicio:
```json
{ "error": "mensaje" }
```

| Situación | odontologo-service | Resto de servicios |
|-----------|--------------------|--------------------|
| ID inexistente | 400 `{ "error": "... no encontrado con id: N" }` | 500 (error por defecto de Spring) |
| Duplicado (matrícula, DNI, username, email) | 400 `{ "error": "Ya existe ..." }` | 500 |
| Validación de campos | 400 (formato por defecto de Spring) | 400 (formato por defecto de Spring) |
| Estado o rol inválido | 400 | 500 |

No hay respuestas 404: un recurso inexistente llega como 400 o 500 según el servicio.

En `auth-service` la ruta `/error` no está en la lista pública, así que es probable que los errores de registro y login lleguen al cliente como 401 o 403 en lugar de 400 o 500. No está comprobado con el servicio en marcha; conviene verificarlo antes de programar los mensajes del login.

### 9.2 Códigos HTTP y acción en el frontend
| Código | Significado | Acción Frontend |
|--------|-------------|-----------------|
| 200 | OK | Mostrar datos |
| 201 | Created | Toast success, redirect/refresh |
| 400 | Bad Request | Mostrar `error` o errores de validación |
| 401 | Unauthorized | Redirect a /login, limpiar token |
| 403 | Forbidden | En login/registro: credenciales o datos inválidos. En el resto: toast sin permisos |
| 500 | Server Error | Toast genérico; en GET/PUT/DELETE por ID tratar como "no encontrado" |
| 502 | Bad Gateway | Caddy no alcanza el servicio: toast "servicio no disponible" |

### 9.3 Interceptor Axios/Fetch
- Auto-agregar `Authorization` header si hay token
- Manejar 401 global: logout + redirect
- Leer `error` o `mensaje` del body cuando exista
- Retry para 502 (opcional): el servicio puede estar arrancando

---

## 10. Configuración de Entorno

### 10.1 Variables de Entorno (Frontend)
```env
VITE_API_BASE_URL=http://localhost:8000
VITE_APP_NAME=Consultorio Odontológico
```

### 10.2 Puertos Backend (Referencia)
```env
AUTH_SERVICE_PORT=8081
PACIENTE_SERVICE_PORT=8082
ODONTOLOGO_SERVICE_PORT=8083
CITA_SERVICE_PORT=8084
HISTORIAL_SERVICE_PORT=8085
GATEWAY_PORT=8000
MYSQL_PORT=3306
```

Los valores reales viven en el `application.properties` de cada servicio. Los cinco comparten el mismo `app.jwt.secret`; si se cambia en uno hay que cambiarlo en todos.

---

## 11. Testing Strategy

### 11.1 Unit Tests
- Servicios API (mock fetch)
- Utilidades de formateo/validación (fechas, fechaHora)
- Hooks personalizados (useAuth, usePacientes, useOdontologos, useCitas, useHistoriales)

### 11.2 Integration Tests
- Flujos completos: login → crear paciente → crear odontólogo → agendar cita → registrar historial
- Filtros de citas por paciente, odontólogo, estado y fecha
- Estados de carga/error

### 11.3 E2E Tests (Cypress/Playwright)
- Login/Logout
- CRUD Pacientes
- CRUD Odontólogos
- CRUD Citas y cambio de estado
- CRUD Historiales por paciente
- Validaciones de formularios

---

## 12. Checklist de Implementación

- [ ] Setup proyecto (Vite/Next.js + TypeScript + Tailwind)
- [ ] Resolver CORS (proxy de Vite o headers en Caddy)
- [ ] Configurar API client (Axios/Fetch wrapper + interceptors)
- [ ] Auth: Login, Register, Logout, Protected Routes
- [ ] Layout: Header, Sidebar, PageContainer
- [ ] Pacientes: List, Create, Edit, Delete, Detail
- [ ] Odontólogos: List, Create, Edit, Delete, Detail
- [ ] Citas: List (con filtros), Create, Edit, Delete, Detail, cambio de estado
- [ ] Historiales: List (por paciente), Create, Edit, Delete, Detail
- [ ] Formularios con validación (React Hook Form + Zod/Yup)
- [ ] Toast notifications
- [ ] Loading states y skeletons
- [ ] Manejo de errores global
- [ ] Responsive design (mobile-first)
- [ ] Tests unitarios y E2E
- [ ] CI/CD pipeline
- [ ] Backend: cambiar `permitAll()` por `authenticated()` en los cuatro servicios de datos
- [ ] Backend: copiar `GlobalExceptionHandler` a los otros cuatro servicios
