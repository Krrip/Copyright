package com.example.controller;

import cn.hutool.extra.tokenizer.engine.word.WordWord;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.Result;
import com.example.entity.User;
import com.example.entity.Workcheck;
import com.example.entity.Workinfo;
import com.example.mapper.WorkinfoMapper;
import com.example.service.WorkcheckService;
import com.example.service.WorkinfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jdk.nashorn.internal.runtime.ErrorManager;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.DataException;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/workinfo")
public class WorkinfoController {

    @Resource
    private WorkinfoService workinfoService;

    @Resource
    private WorkinfoMapper workinfoMapper;

    @Resource
    private WorkcheckService workcheckService;

    @GetMapping("/zhengshu")
    public String zhengshu(){
        return workinfoMapper.asd();
    }

    @GetMapping("/all")
    public List<Workinfo> All(){
        return workinfoService.list(null);
    }

    //新增作品
    @PostMapping
    public Result<?> save(@RequestBody Workinfo workinfo, HttpServletRequest request) {
        Map map = request.getParameterMap();
        System.out.println("map = " + map);
        final boolean save = workinfoService.save(workinfo);
        Workcheck workcheck = new Workcheck();
        //根据作品id查询
        final String workType = workinfo.getType();
        //默认设置未通过
        workcheck.setCheckresult("未通过");
        workcheck.setUserid(new Integer(1).toString());
        workcheck.setChecktime(new Date());
        workcheck.setWorkid(workinfo.getId().toString());
//        workcheck.setRemind("成功");
        workcheck.setType(workType);
        workcheckService.save(workcheck);
        return Result.success(save);
    }

    @PutMapping
    public Result<?> update(@RequestBody Workinfo workinfo) {
        return Result.success(workinfoService.updateById(workinfo));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        workinfoService.removeById(id);
        QueryWrapper<Workcheck> queryWrapper = new QueryWrapper();
        queryWrapper.eq("workid", id.toString());
        final Workcheck workcheck = workcheckService.getOne(queryWrapper);
        workcheckService.removeById(workcheck.getId());
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<Workinfo> findById(@PathVariable Long id) {
        return Result.success(workinfoService.getById(id));
    }

    @GetMapping
    public Result<List<Workinfo>> findAll() {
        return Result.success(workinfoService.list());
    }

    //查询，按数据库顺序排
    @GetMapping("/page")
    public Result<IPage<Workinfo>> findPage(@RequestParam(required = false, defaultValue = "") String name,
                                            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return Result.success(workinfoService.page(new Page<>(pageNum, pageSize), Wrappers.<Workinfo>lambdaQuery().like(Workinfo::getName, name)));
    }

//根据作品名生成PDF文件
    @GetMapping("/pdf/{username}")
    public String getByUserName(@PathVariable String username ,HttpServletRequest request ,HttpServletResponse response) throws Exception {
        QueryWrapper<Workinfo> query = new QueryWrapper<>();
        //相当where name=username
        query.eq("name", username);
        final Workinfo workinfo = workinfoService.getOne(query);
        //业务层代码
        String filePath =  workinfoService.generatorPdf(workinfo);
        downLoad(filePath,response,request);
        final int serverPort = request.getServerPort();
        final String serverName = request.getServerName();
        final String requestURI = request.getRequestURI();
        System.out.println("http://"+serverName+":"+serverPort+requestURI);
        workinfo.setZhengshu("http://"+serverName+":"+serverPort+requestURI);
        workinfoService.updateById(workinfo);
        return filePath;
    }


//无用
    @RequestMapping("/pdfUpload")
    public void downLoad(String filePath, HttpServletResponse response, HttpServletRequest request) throws Exception {
        boolean is = myDownLoad(filePath,"a.pdf","GBK",response,request);
        if(is)
            System.out.println("成功");
        else
            System.out.println("失败");

    }



    //下载方法
    public boolean myDownLoad(String filePath,String fileName, String encoding, HttpServletResponse response, HttpServletRequest request){
        File f = new File(filePath);
        if (!f.exists()) {
            try {
                response.sendError(404, "File not found!");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }


        String type = fileName.substring(fileName.lastIndexOf(".") + 1);
        //判断下载类型 xlsx 或 xls 现在只实现了xlsx、xls两个类型的文件下载
        Logger logger = null;
        if (type.equalsIgnoreCase("pdf") || type.equalsIgnoreCase("pdf")){
            response.setContentType("application/pdf;charset=UTF-8");
            final String userAgent = request.getHeader("USER-AGENT");
            try {
                if (StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "Edge")) {// IE浏览器
                    fileName = URLEncoder.encode(fileName, "UTF8");
                } else if (StringUtils.contains(userAgent, "Mozilla")) {// google,火狐浏览器
                    fileName = new String(fileName.getBytes(), "ISO8859-1");
                } else {
                    fileName = URLEncoder.encode(fileName, "UTF8");// 其他浏览器
                }
                response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage(), e);
                return false;
            }
            InputStream in = null;
            OutputStream out = null;
            try {

                //获取要下载的文件输入流
                in = new FileInputStream(filePath);
                int len = 0;
                //创建数据缓冲区
                byte[] buffer = new byte[1024];
                //通过response对象获取outputStream流
                out = response.getOutputStream();
                //将FileInputStream流写入到buffer缓冲区
                while((len = in.read(buffer)) > 0) {
                    //使用OutputStream将缓冲区的数据输出到浏览器
                    out.write(buffer,0,len);
                }
                //这一步走完，将文件传入OutputStream中后，页面就会弹出下载框

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                return false;
            } finally {
                try {
                    if (out != null)
                        out.close();
                    if(in!=null)
                        in.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            return true;
        }else {
            logger.error("不支持的下载类型！");
            return false;
        }
    }

}