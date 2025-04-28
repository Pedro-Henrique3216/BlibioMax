package com.example.bibliomax.model;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PdfGenerator {

    public static void CreateInvoice(RentalOrder order) throws FileNotFoundException, DocumentException {

        File file = new File("C:\\Users\\Pedro Henrique\\Documents\\teste_nt_fiscal\\" + order.getId() + ".pdf");
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        Document document = new Document();
        PdfWriter.getInstance(document, fileOutputStream);

        document.open();

        document.add(new Paragraph("Nota Fiscal Nº: " + order.getId()));
        document.add(new Paragraph("Empresa: Bibliomax"));
        document.add(new Paragraph("Cliente: " + order.getPerson().getNome()));
        document.add(new Paragraph("Data de Emissão: " + order.getOrderDate()));

        document.add(new Paragraph("Itens:"));
        for (RentalItem item : order.getRentalBooks()) {
            document.add(new Paragraph(item.getId() + " - Qtd: " + 1 + " - R$" + item.getLateFee()));
        }
        document.add(new Paragraph("Total: R$" + order.getTotal()));


        document.close();
    }
}
