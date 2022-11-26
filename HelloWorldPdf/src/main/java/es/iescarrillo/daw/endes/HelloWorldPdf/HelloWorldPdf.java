package es.iescarrillo.daw.endes.HelloWorldPdf;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.File;
import java.io.IOException;

/**
 * Simple Hello World example.
 */
public class HelloWorldPdf {
    
    public static final String ruta = "PDF/hello_world.pdf";
    
    public static void main(String args[]) throws IOException {
        File archivo = new File(ruta);
        archivo.getParentFile().mkdirs();
        new HelloWorldPdf().createPdf(ruta);
    }
    
    public void createPdf(String ruta) throws IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(ruta);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
        
        // Initialize document
        Document documentoPdf = new Document(pdf);

        //Add paragraph to the document
        documentoPdf.add(new Paragraph("Hello World!"));

        //Close document
        documentoPdf.close();
    }
}
