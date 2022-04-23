package com.example.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
@TableName("t_workcheck")
public class Workcheck extends Model<Workcheck> {
    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
      * 管理员 
      */
    private String userid;

    /**
      * 审核日期 
      */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date checktime;

    /**
      * 审核结果 
      */
    private String checkresult;

    /**
      * 作品名 
      */
    private String workid;

    /**
      * 作品类型 
      */
    private String type;

    /**
      * 备注 
      */
    private String remind;



}