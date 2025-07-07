# 🛒 Carrito de Compras - Proyecto Java

Interfaz gráfica avanzada en Java Swing con enfoque MDI, patrón MVC, DAO y principios SOLID.

---

## 📌 Descripción General

El sistema permite gestionar productos, usuarios y carritos de compras mediante una interfaz gráfica de tipo MDI. Los usuarios pueden buscar productos, agregarlos al carrito, visualizar el subtotal y gestionar su compra. La aplicación soporta múltiples idiomas y formatos culturales, adaptándose al idioma seleccionado.

---

## 🧱 Arquitectura del Sistema

- **MVC (Modelo–Vista–Controlador):** Separación clara entre lógica, datos e interfaz.
- **DAO (Data Access Object):** Desacopla el acceso a datos de la lógica del negocio, usando interfaces e implementaciones en memoria.
- **Interfaces gráficas con Swing:** Múltiples ventanas internas (`JInternalFrame`) dentro de una principal (`JDesktopPane`).

---

## 🧠 Principios SOLID Aplicados

- **SRP (Responsabilidad única):** Cada clase tiene un propósito claro (ej. controladores, DAOs, utilitarios).
- **OCP (Abierto/Cerrado):** Se pueden extender funciones sin modificar código existente.
- **DIP (Inversión de dependencias):** Se programó contra interfaces y no implementaciones concretas.

---

## 🌍 Internacionalización

- Soporte multilenguaje mediante `ResourceBundle`.
- Archivos `.properties` disponibles:  
  - Español: `mensajes_es_EC.properties`  
  - Inglés: `mensajes_en_US.properties`  
  - Francés: `mensajes_fr_FR.properties`
- Formato cultural aplicado con `NumberFormat` y `DateFormat` según el idioma.

---

## 🖥️ Componentes de Interfaz Usados

- `JDesktopPane`, `JInternalFrame` (interfaz MDI)
- `JTable`, `JComboBox`, `JMenu`, `JOptionPane` (componentes avanzados)
- Íconos y menús adaptados a la selección de idioma.

---

## ✅ Objetivos Alcanzados

-	Aplicar el patrón MVC en el diseño de sistemas con interfaces gráficas.
-	Implementar el patrón DAO para desacoplar la lógica de acceso a datos.
-	Aplicar al menos tres principios SOLID en el diseño orientado a objetos.
-	Utilizar JDesktopPane y JInternalFrame para construir una GUI de tipo MDI.
-	Emplear componentes avanzados de Swing: JTable, JComboBox, JMenu, JOptionPane, entre otros.
-	Soportar múltiples idiomas mediante ResourceBundle.
-	Aplicar NumberFormat y DateFormat para dar formato a datos según el idioma.
---

## 🚀 Cómo Ejecutar

1. Abre el proyecto en tu IntelliJ IDEA.
2. Ejecuta la clase `Main.java`.
3. Interactúa con la interfaz gráfica y cambia de idioma desde el menú superior.

---

## 👨‍💻 Autor

Pedro Pesántez  
