package com.example.service;

import com.example.entity.Workinfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.WorkinfoMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class WorkinfoService extends ServiceImpl<WorkinfoMapper, Workinfo> {

    @Resource
    private WorkinfoMapper workinfoMapper;

    public String generatorPdf(Workinfo workinfo) {
        SimpleDateFormat simple = new SimpleDateFormat("yyyy年MM月dd日");

        Map<String,String> map = new HashMap();
        final Random random = new Random();
        map.put("DJnum",random.nextInt(100000000)+"");
        map.put("name", workinfo.getName());
        map.put("type",workinfo.getType());
        map.put("anthor",workinfo.getAuthor());
        map.put("owen",workinfo.getOwen());
        map.put("date",simple.format(workinfo.getDate()));
        map.put("souciDate", simple.format(workinfo.getSouciDate()));
        map.put("applyer",workinfo.getApplyer());
//        map.put("shenheren","王小五");
        map.put("applytime",simple.format(workinfo.getApplytime()));
        Map<String,Object> o=new HashMap();
        o.put("datemap",map);
        return pdfout(o);
    }

    // 利用模板生成pdf
    public static String pdfout(Map<String,Object> o) {
        // 模板路径
        String templatePath = "C:/Users/Lin/Desktop/bishecode/登记证书完整证书3.pdf";
        // 生成的新文件路径
        String newPDFPath = "C:/Users/Lin/Desktop/codecode/"+System.currentTimeMillis()+".pdf";

        PdfReader reader;
        FileOutputStream out;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        try {
            BaseFont bf = BaseFont.createFont("C://windows//fonts//simsun.ttc,1" , BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
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
return newPDFPath;
    }

}