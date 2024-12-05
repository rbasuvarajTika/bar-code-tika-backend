package com.tika.barcode.utility;

import java.io.ByteArrayOutputStream;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Component
public class PdfGenerator {

    private final SpringTemplateEngine templateEngine;

    public PdfGenerator(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public void generatePdf(String templateName, Map<String, Object> data, OutputStream os) throws Exception {
        try{
        	Context context = new Context();
       
        context.setVariables(data);

        String htmlContent = templateEngine.process(templateName, context);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(os);
        }catch(Exception e) {
        	System.out.print(e);
        }
    }
}