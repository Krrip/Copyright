package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.dto.WorkCheckVo;
import com.example.entity.Workcheck;
import org.apache.ibatis.annotations.Select;


import java.util.List;

public interface WorkcheckMapper extends BaseMapper<Workcheck> {


    @Select("\n" +
            "SELECT t_workcheck.id,t_user.username , t_workinfo.`name`,t_workcheck.checktime,t_workcheck.checkresult,t_workinfo.type,t_workcheck.remind FROM t_workcheck\n" +
            "\n" +
            "INNER JOIN t_user\n" +
            "\n" +
            "ON t_workcheck.userid = t_user.id\n" +
            "\n" +
            "INNER JOIN t_workinfo\n" +
            "\n" +
            "ON t_workcheck.workid = t_workinfo.id")
    List<WorkCheckVo> findAll();

    @Select("select * from t_workcheck where workid = #{id};")
    List<Workcheck> byidfindworkid();

    @Select("SELECT COUNT(checkresult) checkresult  FROM  t_workcheck WHERE checkresult = '通过'")
    String asd();
}