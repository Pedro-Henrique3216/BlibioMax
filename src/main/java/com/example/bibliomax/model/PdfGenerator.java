package com.example.bibliomax.model;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.FileNotFoundException;


public class PdfGenerator {

    public static void CreateInvoice(Pedido order) throws FileNotFoundException {

        PdfWriter pdfWriter = new PdfWriter(new File("C:\\Users\\Pedro Henrique\\Documents\\teste_nt_fiscal\\" + order.getId() + ".pdf"));

        PdfDocument pdfDocument = new PdfDocument(pdfWriter);

        Document document = new Document(pdfDocument);

        document.add(new Paragraph("Nota Fiscal Nº: " + order.getId()));
        document.add(new Paragraph("Empresa: Bibliomax" ));
        document.add(new Paragraph("Cliente: " + order.getPessoa().getNome()));
        document.add(new Paragraph("Data de Emissão: " + order.getDatePedido()));

        document.add(new Paragraph("Itens:"));
        for (ItensPedido item : order.getItensPedido()) {
            document.add(new Paragraph(item.getId() + " - Qtd: " + item.getQuantidade() + " - R$" + item.getValor()));
        }
        document.add(new Paragraph("Total: R$" + order.getValor()));

        document.close();
    }

}
