package com.example.controller;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
public class pdf {
    // 利用模板生成pdf
    public static void pdfout(Map<String,Object> o) {
        // 模板路径
        String templatePath = "C:/Users/Lin/Desktop/codecode/djnum.pdf";
        // 生成的新文件路径
        String newPDFPath = "C:/Users/Lin/Desktop/codecode/testout2.pdf";

        PdfReader reader;
        FileOutputStream out;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        try {
            BaseFont bf = BaseFont.createFont("c://windows//fonts//simsun.ttc,1" , BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font FontChinese = new Font(bf, 5, Font.NORMAL);
            out = new FileOutputStream(newPDFPath);// 输出流
            reader = new PdfReader(templatePath);// 读取pdf模板
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();
            //文字类的内容处理
            Map<String,String> datemap = (Map<String,String>)o.get("datemap");
            form.addSubstitutionFont(bf);
            for(String key : datemap.keySet()){
                String value = datemap.get(key);
                form.setField(key,value);
            }

            stamper.setFormFlattening(true);// 如果为false，生成的PDF文件可以编辑，如果为true，生成的PDF文件不可以编辑
            stamper.close();
            Document doc = new Document();
            Font font = new Font(bf, 32);
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importPage);
            doc.close();

        } catch (IOException e) {
            System.out.println(e);
        } catch (DocumentException e) {
            System.out.println(e);
        }

    }

    public static void main(String[] args) {
        Map<String,String> map = new HashMap();
        final Random random = new Random();
        map.put("DJnum",random.nextInt(100000000)+"");
        map.put("name","Lin");
        map.put("type","原创");
        map.put("anthor","真");
        map.put("owen","宇");
        map.put("date","2020");
        map.put("souciDate","2019");
        map.put("applyer","将");
        map.put("shenheren","审核员");
        map.put("applytime","2036");

//        Map<String,String> map2 = new HashMap();
//        map2.put("img","C:Users//zh128//Desktop//111.jpg");

        Map<String,Object> o=new HashMap();
        o.put("datemap",map);

        pdfout(o);
    }


}
