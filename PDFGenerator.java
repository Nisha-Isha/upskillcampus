import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.util.Date;

public class PDFGenerator {

    public static void generateBill(String items, int total) {
        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("bill.pdf"));

            doc.open();

            // 🔥 Title
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Food Delivery Invoice", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            doc.add(title);

            doc.add(new Paragraph(" ")); // space
            doc.add(new Paragraph("Date: " + new Date()));
            doc.add(new Paragraph("Order ID: " + System.currentTimeMillis()));
            doc.add(new Paragraph(" "));

            Image logo = Image.getInstance(PDFGenerator.class.getResource("/logo.png"));
            logo.scaleToFit(100, 100);
            logo.setAlignment(Element.ALIGN_CENTER);
            doc.add(logo);

            // 🔥 Table
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);

            table.addCell("Item Details");
            table.addCell(items);

            table.addCell("Total Amount");
            table.addCell("₹ " + total);

            doc.add(table);

            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("Thank you for ordering!"));

            doc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}