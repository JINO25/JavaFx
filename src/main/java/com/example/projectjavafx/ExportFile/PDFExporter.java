package com.example.projectjavafx.ExportFile;
import com.example.projectjavafx.DB.DBConnect;
import com.example.projectjavafx.Models.Invoice;
import com.example.projectjavafx.data;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.itextpdf.kernel.pdf.PdfName.BaseFont;

public class PDFExporter {
    public void exportPDF(Stage stage) throws SQLException {
        DBConnect dbConnect = new DBConnect();
        List<Invoice> list = dbConnect.getInvoice(DBConnect.orderId);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(stage);



        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String date = dateTimeFormatter.format(now);

        int totalBill=0;
        if (file != null) {
            try {
                // Initialize PDF writer and document
                PdfWriter writer = new PdfWriter(file.getAbsolutePath());
                PdfDocument pdfDoc = new PdfDocument(writer);
                Document document = new Document(pdfDoc);

                String fontPath = "C:\\Windows\\Fonts\\arial.ttf"; // Replace with your font path
                PdfFont font = PdfFontFactory.createFont(fontPath,"Identity-H");


                Paragraph title = new Paragraph("Hoá Đơn")
                        .setFont(font)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFontSize(20);
                document.add(title);


                Paragraph employeeAndDate = new Paragraph("Nhân viên: " + data.userName + "\t\t" + date)
                        .setFont(font)
                        .setTextAlignment(TextAlignment.LEFT)
                        .setMarginTop(10);
                document.add(employeeAndDate);


                float[] columnWidths = {200, 100, 100};
                Table table = new Table(columnWidths);
                table.setFont(font);
                table.addCell("Tên Sản Phẩm");
                table.addCell("Số Lượng");
                table.addCell("Giá");


                for (Invoice invoice : list) {
                    table.addCell(invoice.getName());
                    table.addCell(String.valueOf(invoice.getQuantity()));
                    table.addCell(invoice.getPrice()+" VNĐ");
                    totalBill=invoice.getTotal();
                    date=invoice.getDate();
                }

                document.add(table);


                Paragraph total = new Paragraph("Tổng tiền: " + totalBill+ " VNĐ")
                        .setFont(font)
                        .setTextAlignment(TextAlignment.RIGHT)
                        .setMarginTop(20);
                document.add(total);

                document.close();
                System.out.println("PDF created successfully!");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
