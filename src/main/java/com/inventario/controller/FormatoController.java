package com.inventario.controller;

import com.inventario.model.Equipo;
import com.inventario.service.EquipoService;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.itextpdf.layout.borders.Border;

@Controller
public class FormatoController {

    private final EquipoService equipoService;

    public FormatoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    // Paso 1: buscar equipo para generar FO-MI-01
    @GetMapping("/generar/fo-mi-01")
    public String buscarParaResguardo(Model model, Principal principal) {

        String correo = principal.getName();

        model.addAttribute("equipos",
                equipoService.obtenerEquiposSegunUsuario(correo));

        return "buscar-resguardo";
    }

    // Paso 2: generar el PDF
    @GetMapping("/generar/pdf/resguardo")
    public void generarResguardo(@RequestParam Long id,
                             HttpServletResponse response,
                             Principal principal) throws IOException {
        Equipo equipo = equipoService.buscarPorId(id)
        .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        String correo = principal.getName();

        // Verificamos que el equipo pertenezca al inventario del usuario
        boolean autorizado = equipoService
                .obtenerEquiposSegunUsuario(correo)
                .stream()
                .anyMatch(e -> e.getId().equals(id));

        if(!autorizado){
            throw new RuntimeException("No autorizado para generar este formato");
        }

        String usuarioEntrega = correo;
                             
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"FO-MI-01_" + equipo.getCodigo() + ".pdf\"");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.LETTER);
        document.setMargins(70, 50, 50, 50);

        // Título
        document.add(new Paragraph("FORMATO DE RESGUARDO DE EQUIPO DE CÓMPUTO")
            .setBold()
            .setFontSize(16)
            .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("FO-MI-01")
            .setBold()
            .setFontSize(14)
            .setTextAlignment(TextAlignment.CENTER)
            .setMarginBottom(20));

        // Tabla de datos
        float[] columnWidths = {150, 350};
        Table table = new Table(columnWidths);
        table.setWidth(500);

        table.addCell(crearCelda("Código del equipo:"));
        table.addCell(crearCelda(equipo.getCodigo()));

        table.addCell(crearCelda("Tipo de equipo:"));
        table.addCell(crearCelda(equipo.getTipo()));

        table.addCell(crearCelda("Marca:"));
        table.addCell(crearCelda(equipo.getMarca()));

        table.addCell(crearCelda("Modelo:"));
        table.addCell(crearCelda(equipo.getModelo()));

        table.addCell(crearCelda("No. de serie:"));
        table.addCell(crearCelda(
                equipo.getSerie() != null ? equipo.getSerie() : "N/A"
        ));

        table.addCell(crearCelda("Inventario:"));
        table.addCell(crearCelda(
                equipo.getInventario() != null
                        ? equipo.getInventario().getDescripcion()
                        : "N/A"
        ));

        document.add(table);

        // Texto final
        document.add(new Paragraph("\n\n")
            .setFixedLeading(14));
        document.add(new Paragraph("Recibí de conformidad el equipo descrito arriba, comprometiéndome a darle el uso adecuado y a reportar cualquier falla.")
            .setFontSize(11)
            .setTextAlignment(TextAlignment.JUSTIFIED));

        document.add(new Paragraph("\n\n\n"));
        
        // Líneas de firma
        Table firmas = new Table(2);
        firmas.setWidth(500);

        // Encabezados
        firmas.addCell(crearCeldaFirma("ENTREGA"));
        firmas.addCell(crearCeldaFirma("RECIBE"));

        // Nombres automáticos
        firmas.addCell(crearCeldaFirma("Nombre: " + usuarioEntrega));
        firmas.addCell(crearCeldaFirma("Nombre: " +
                (equipo.getResponsable() != null ? equipo.getResponsable() : "N/A")));

        // Puestos automáticos
        firmas.addCell(crearCeldaFirma("Cargo: " +
                (equipo.getPuesto() != null ? equipo.getPuesto() : "N/A")));
        firmas.addCell(crearCeldaFirma("Cargo: " +
                (equipo.getPuesto() != null ? equipo.getPuesto() : "N/A")));

        // Fechas automáticas
        String fechaHoy = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        firmas.addCell(crearCeldaFirma("Fecha: " + fechaHoy));
        firmas.addCell(crearCeldaFirma("Fecha: " + fechaHoy));

        // Líneas de firma
        firmas.addCell(crearCeldaFirma("Firma: ______________________________"));
        firmas.addCell(crearCeldaFirma("Firma: ______________________________"));

        document.add(firmas);

        document.close();
    }

    private Cell crearCelda(String texto) {
        Cell cell = new Cell();
        cell.add(new Paragraph(texto).setFontSize(11));
        cell.setPadding(8);
        return cell;
    }

    private Cell crearCeldaFirma(String texto) {
        Cell cell = new Cell();
        cell.add(new Paragraph(texto).setFontSize(10).setTextAlignment(TextAlignment.CENTER));
        cell.setBorder(Border.NO_BORDER);
        cell.setPaddingBottom(30);
        return cell;
    }

    @GetMapping("/generar/fo-mi-02")
    public String buscarParaMantenimiento(Model model, Principal principal){

    String correo = principal.getName();

    model.addAttribute("equipos",
            equipoService.obtenerEquiposSinGarantia(correo));

     return "buscar-mantenimiento";
    }

    @GetMapping("/generar/fo-mi-03")
    public String buscarParaFomi03(Model model, Principal principal) {

        String correo = principal.getName();

        model.addAttribute("equipos",
                equipoService.obtenerEquiposSegunUsuario(correo));

        return "buscar-dañado";
    }

    @GetMapping("/generar/pdf/fo-mi-03")
    public void generarFomi03(@RequestParam Long id,
                            HttpServletResponse response,
                            Principal principal) throws IOException {

        Equipo equipo = equipoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        String correo = principal.getName();

        boolean autorizado = equipoService
                .obtenerEquiposSegunUsuario(correo)
                .stream()
                .anyMatch(e -> e.getId().equals(id));

        if (!autorizado) {
            throw new RuntimeException("No autorizado");
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "attachment; filename=\"FO-MI-03_" + equipo.getCodigo() + ".pdf\"");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.LETTER);
        document.setMargins(70, 50, 50, 50);

        // Título
        document.add(new Paragraph("FORMATO DE BAJA / REPORTE DE EQUIPO")
                .setBold()
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER));

        document.add(new Paragraph("FO-MI-03")
                .setBold()
                .setFontSize(14)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20));

        // Tabla
        float[] columnWidths = {150, 350};
        Table table = new Table(columnWidths);
        table.setWidth(500);

        table.addCell(crearCelda("Código:"));
        table.addCell(crearCelda(equipo.getCodigo()));

        table.addCell(crearCelda("Tipo:"));
        table.addCell(crearCelda(equipo.getTipo()));

        table.addCell(crearCelda("Marca:"));
        table.addCell(crearCelda(equipo.getMarca()));

        table.addCell(crearCelda("Modelo:"));
        table.addCell(crearCelda(equipo.getModelo()));

        table.addCell(crearCelda("Serie:"));
        table.addCell(crearCelda(
                equipo.getSerie() != null ? equipo.getSerie() : "N/A"));

        table.addCell(crearCelda("Motivo de baja:"));
        table.addCell(crearCelda("__________________________________"));

        document.add(table);

        document.add(new Paragraph("\nObservaciones:\n\n_____________________________\n\n")
                .setFontSize(11));

        // Firmas
        Table firmas = new Table(2);
        firmas.setWidth(500);

        String fechaHoy = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        firmas.addCell(crearCeldaFirma("RESPONSABLE"));
        firmas.addCell(crearCeldaFirma("AUTORIZA"));

        firmas.addCell(crearCeldaFirma("Nombre: " + correo));
        firmas.addCell(crearCeldaFirma("Nombre: __________________"));

        firmas.addCell(crearCeldaFirma("Fecha: " + fechaHoy));
        firmas.addCell(crearCeldaFirma("Fecha: " + fechaHoy));

        firmas.addCell(crearCeldaFirma("Firma: __________________"));
        firmas.addCell(crearCeldaFirma("Firma: __________________"));

        document.add(firmas);

        document.close();
    }
}