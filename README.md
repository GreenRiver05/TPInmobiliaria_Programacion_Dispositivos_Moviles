# 🏠 Inmobiliaria ULP — App Móvil para Propietarios

Aplicación móvil desarrollada para los propietarios registrados en la inmobiliaria, con el objetivo de gestionar sus inmuebles, contratos y credenciales de forma segura y eficiente.

---

## 📌 Funcionalidades mínimas implementadas

- 🔐 Login y logout de propietarios
- 👤 Ver y editar perfil personal
- 🔑 Cambiar contraseña desde perfil
- ❓ Resetear contraseña (“Me olvidé la contraseña”)
- 🏘️ Listar inmuebles del propietario
- ✅ Habilitar / Deshabilitar disponibilidad de un inmueble
- ➕ Agregar nuevo inmueble con foto (por defecto deshabilitado)
- 📄 Listar contratos por inmueble y visualizar pagos
- 🔒 Todas las funcionalidades (excepto login) requieren autenticación
- 🔐 El ID del propietario se recupera desde el token, no se envía explícitamente

---

## 🧩 Fragmentos implementados

- **Inicio**: Muestra ubicación de la inmobiliaria en mapa
- **Perfil**: Visualiza y edita datos del propietario (excepto ID y Email), se puede navegar hacia otro fragmente para cambiar contraseña
- **Inmuebles**: Lista propiedades del propietario y permite ver detalles y modificar si esta o no disponible
- **Agregar Inmueble**: Carga nueva propiedad con imagen
- **Contratos**: Lista inmuebles con contrato y muestra detalles, se puede navegar y ver el inquilino o los pagos de ese contrato
- **Logout**: Diálogo de confirmación para cerrar sesión

📱 *Extra*: Si el usuario agita el teléfono en la pantalla de login, se realiza una llamada directa a la inmobiliaria.

---

## 🎥 Videos demostrativos

### 🔹 Video 1 — Autenticación y navegación inicial
- Login
- Llamada a la inmobiliaria (agitando el dispositivo)
- Logout
- Inicio con mapa

🔗 [Ver en YouTube](https://youtu.be/1R-ADbn51fw)

---

### 🔹 Video 2 — Gestión de perfil e inmuebles
- Ver y editar perfil
- Listar inmuebles
- Habilitar / Deshabilitar inmueble
- Agregar nuevo inmueble con foto 🆕
- Listar contratos y pagos y su inquilino

🔗 [Ver en YouTube](https://youtu.be/owzjFIHd118)

---

### 🔹 Video 3 — Seguridad y recuperación de acceso
- Resetear contraseña por correo
- Cambiar contraseña desde perfil

🔗 [Ver en YouTube](https://www.youtube.com/watch?v=LINK_DEL_VIDEO_3)

---

## 🏫 Institución

Este proyecto fue desarrollado como parte del trayecto académico en la **Universidad de La Punta (ULP)**, San Luis, Argentina.

