package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.dto.PaymentBO;
import com.example.entity.Workcheck;
import com.example.entity.Workinfo;
import com.example.service.PayService;
import com.example.service.WorkcheckService;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.formula.SheetRangeAndWorkbookIndexFormatter;
import org.hibernate.jdbc.Work;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 *  支付宝支付，控制器
 *
 * @author wangziyang
 * */
@RestController
@RequestMapping(value = "/pay")
@AllArgsConstructor
public class PayController {

    private PayService payService;

    @Resource
    private WorkcheckService workcheckService;

    /**
     * 下单支付
     */
//    @GetMapping(value = "/confirm" , produces = {"text/html;charset=UTF-8"})
//    public Object pay (@RequestParam(required = false) PaymentBO bo) throws Exception {
//        //这个接口其实应该是post方式的，但是我这里图方便，直接以get方式访问，
//        //且返回格式是text/html，这样前端页面就能直接显示支付宝返回的html片段
//        //真实场景下由post方式请求，返回code、msg、data那种格式的标准结构，让前端拿到data里的
//        //html片段之后自行加载
//
//        return payService.pay(bo);
//    }
    @GetMapping(value = "/confirm/{id}/{total}", produces = {"text/html;charset=UTF-8"})
    public Object pay(@PathVariable int id, @PathVariable double total) throws Exception{
        return payService.pay(id ,total);
    }

    /**
     *  支付成功的回调
     * */
    @PostMapping(value = "/fallback")
    public Object fallback (HttpServletRequest request) {
        Map map = request.getParameterMap();
        //支付那个作品
        final Object subject = map.get("subject");
        final String s = Arrays.toString((Object[]) subject);
        final Object trade_status = map.get("trade_status");
        final String status = Arrays.toString((Object[]) trade_status);
//        System.out.println(status.substring(1,14));

        System.out.println(s.substring(1,3));
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("workid" ,s.substring(1, 3));
        Workcheck workcheck = workcheckService.getOne(queryWrapper);
        workcheck.setRemind(status.substring(1,14));
        workcheckService.updateById(workcheck);
        System.out.println(workcheck);

        return null;
    }
}
