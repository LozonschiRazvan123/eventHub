package Service;

import java.io.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import Model.Event;
import Repository.EventRepository;

@Service
public class ReportService {
	
	@Autowired
    private EventRepository eventRepository;

	public byte[] generateReport(Long eventId, String format) throws Exception {
        Event event = getEventById(eventId);
        
        if ("pdf".equalsIgnoreCase(format)) {
            return generatePdfReport(event);
        } else if ("excel".equalsIgnoreCase(format)) {
            return generateExcelReport(event);
        } else {
            throw new IllegalArgumentException("Format necunoscut: " + format);
        }
    }
	
	public Event getEventById(Long eventId) {
	    return eventRepository.findById(eventId)
	                          .orElseThrow(() -> new RuntimeException("Event not found with ID: " + eventId));
	}


    // Generează raport PDF
	private byte[] generatePdfReport(Event event) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Adăugăm informațiile evenimentului în PDF
        document.add(new Paragraph("Event Report (PDF)"));
        document.add(new Paragraph("Event ID: " + event.getEventId()));  // Apel corect la metoda getEventId()
        document.add(new Paragraph("Event Name: " + event.getTitle()));
        document.add(new Paragraph("Event Description: " + event.getDescription()));
        document.add(new Paragraph("Location: " + event.getLocation()));
        document.add(new Paragraph("Max Tickets: " + event.getMaxTickets()));
        document.add(new Paragraph("Creation Date: " + event.getCreatedAt()));
        document.add(new Paragraph("Last Updated: " + event.getUpdatedAt()));
        document.add(new Paragraph("Event Date & Time: " + event.getCreatedAt()));  // Poți înlocui cu un câmp specific pentru data evenimentului

        document.close();
        return out.toByteArray();
    }

    // Generează raport Excel
    private byte[] generateExcelReport(Event event) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XSSFWorkbook workbook = new XSSFWorkbook();  // Crează un workbook Excel
        Sheet sheet = workbook.createSheet("Event Report");

        // Adăugăm titluri pentru raportul Excel
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Event Report (Excel)");
        headerRow.createCell(1).setCellValue("Event ID: " + event.getEventId());  // Apel corect la metoda getEventId()

        // Adăugăm detaliile evenimentului în Excel
        Row dataRow1 = sheet.createRow(1);
        dataRow1.createCell(0).setCellValue("Event Name");
        dataRow1.createCell(1).setCellValue(event.getTitle());

        Row dataRow2 = sheet.createRow(2);
        dataRow2.createCell(0).setCellValue("Event Description");
        dataRow2.createCell(1).setCellValue(event.getDescription());

        Row dataRow3 = sheet.createRow(3);
        dataRow3.createCell(0).setCellValue("Location");
        dataRow3.createCell(1).setCellValue(event.getLocation());

        Row dataRow4 = sheet.createRow(4);
        dataRow4.createCell(0).setCellValue("Max Tickets");
        dataRow4.createCell(1).setCellValue(event.getMaxTickets());

        Row dataRow5 = sheet.createRow(5);
        dataRow5.createCell(0).setCellValue("Creation Date");
        dataRow5.createCell(1).setCellValue(event.getCreatedAt().toString());

        Row dataRow6 = sheet.createRow(6);
        dataRow6.createCell(0).setCellValue("Last Updated");
        dataRow6.createCell(1).setCellValue(event.getUpdatedAt().toString());

        Row dataRow7 = sheet.createRow(7);
        dataRow7.createCell(0).setCellValue("Event Date & Time");
        dataRow7.createCell(1).setCellValue(event.getCreatedAt().toString());  // Poți înlocui cu un câmp specific pentru data evenimentului

        workbook.write(out);
        workbook.close();
        return out.toByteArray();
    }
}
