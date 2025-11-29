# DESCRIPCION
# Sistema de Gestión de Inventario de Cómputo
Este proyecto es una aplicación para gestionar el inventario de equipo de cómputo dentro de una organización. Permite llevar control del equipo asignado, generar documentación estandarizada y registrar mantenimiento o incidentes.
# Descripcion De Los Formatos:
# FOMI01 – Responsiva de Cómputo
Documento que formaliza la entrega de un equipo de cómputo a un colaborador. Incluye:
Datos del responsable
Características del equipo asignado
Condiciones de entrega
Firma del usuario y del área responsable
# FOMI02 – Formato de Mantenimiento
Documento para registrar servicios preventivos o correctivos. Incluye:
Datos del equipo
Tipo de servicio
Acciones realizadas
Recomendaciones
Firma del técnico responsable
# FOMI03 – Reporte de Daño
Formato para documentar incidentes o fallas. Incluye:
Fecha del incidente
Descripción del daño
Usuario afectado
Evidencia (opcional)
Firma del responsable del área de TI

# PROBLEMA IDENTIFICADO
Muchas organizaciones manejan inventarios mediante hojas de cálculo o herramientas aisladas, lo cual genera:
-Falta de visibilidad del stock en tiempo real
-Inconsistencias en cantidades y movimientos
-Errores manuales frecuentes
-Dificultad para auditar entradas y salidas
-Poca trazabilidad del historial de inventarios
Esto impacta directamente en costos, productividad y toma de decisiones.

# SOLUCION PROPUESTA
Se desarrolla una aplicación web RESTful basada en Spring Boot que permita:
-Registrar productos y categorías
-Controlar existencias en tiempo real
-Registrar entradas y salidas con trazabilidad
-Automáticas validaciones de stock
-Ofrecer endpoints seguros y estandarizados
-Integración futura con frontend o aplicaciones móviles
La solución puede desplegarse fácilmente en servicios como:
Railway, Render, AWS, Azure o infraestructura propia.
# Funcionalidades Principales
Gestión de Inventario de Cómputo: Alta, baja y edición de equipos.
Asignación de Equipos: Registro de responsables por equipo.
Historial de Movimientos: Control de asignaciones, devoluciones y cambios.
Generación de Formatos Oficiales:
FOMI01 – Responsiva de Cómputo
FOMI02 – Formato de Mantenimiento
FOMI03 – Reporte de Daño
Búsqueda y filtros avanzados para localizar equipos o documentos.

# ARQUITECTURA
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.example.inventario
│   │   │       ├── controller    # Controladores REST
│   │   │       ├── service       # Lógica de negocio
│   │   │       ├── repository    # Acceso a base de datos
│   │   │       ├── model         # Entidades JPA
│   │   │       └── InventarioApplication.java
│   │   └── resources
│   │       ├── application.properties
│   │       └── data.sql (opcional)
├── pom.xml
└── .github
    └── workflows
        └── ci.yml  # Pipeline de integración continua.
        
# TABLA DE CONTENIDOS (TOC)
Descripcion
Instalacion
