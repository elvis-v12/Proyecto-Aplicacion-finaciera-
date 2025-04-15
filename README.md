# Proyecto Aplicación Financiera 📱💰

Aplicación móvil desarrollada en Android para el seguimiento de gastos personales. Permite registrar ingresos, egresos, visualizar estadísticas y gestionar tu economía de forma simple y rápida.

---

## 🛠️ Tecnologías usadas

- **Lenguaje:** Kotlin
- **Framework:** Jetpack Compose
- **Gestión de dependencias:** Gradle + `libs.versions.toml`
- **Entorno de desarrollo:** Android Studio Hedgehog o superior
- **Compatibilidad mínima:** Android 8.0 (API 26)

---

## 🚀 Pasos para descargar y ejecutar el proyecto

### ✅ 1. Clonar el repositorio

```bash
git clone https://github.com/tu_usuario/Proyecto-Aplicacion-Financiera.git
cd Proyecto-Aplicacion-Financiera
```

### ✅ 2. Abrir el proyecto en Android Studio

1. Abre **Android Studio**.
2. Haz clic en **"Open"** y selecciona la carpeta del proyecto que clonaste.
3. Espera a que Android Studio configure el entorno.

---

### ✅ 3. Sincronizar Gradle

1. Android Studio debería sincronizar automáticamente las dependencias.
2. Si no lo hace, haz clic en **"Sync Now"** o ve al menú:  
   `File > Sync Project with Gradle Files`.

---

### ✅ 4. Verificar archivo `libs.versions.toml`

Asegúrate de que las versiones y librerías estén correctamente definidas.  
Ubicación:  

---

### ✅ 5. Configurar un emulador

1. Abre **Android Studio**.
2. Ve a **Tools > AVD Manager**.
3. Haz clic en **Create Virtual Device**.
4. Selecciona un dispositivo y la versión de Android, luego haz clic en **Next**.
5. Haz clic en **Finish** y ejecuta el emulador desde **AVD Manager**.

---


### ✅ 6. Ejecutar la aplicación

1. Haz clic en **Run ▶️** o presiona `Shift + F10`.
---

### ✅ 7. (Opcional) Limpieza y reconstrucción

Si aparecen errores al compilar, puedes limpiar y reconstruir el proyecto:

```bash
./gradlew clean
./gradlew build

