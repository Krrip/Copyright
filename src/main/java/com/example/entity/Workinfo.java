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
@TableName("t_workinfo")
public class Workinfo extends Model<Workinfo> {
    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
      * 作品名 
      */
    private String name;

    /**
      * 著作权人 
      */
    private String owen;

    /**
      * 作品类别 
      */
    private String type;

    /**
      * 作品创作完成时间 
      */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date date;

    /**
      * 首次发表日期 
      */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date souciDate;

    /**
      * 申请日期 
      */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date applytime;


    /**
      * 电子证书 
      */
    private String zhengshu;

    /**
      * 申请人 
      */
    private String applyer;

    /**
      * 图片 
      */
    private String picpath;

    /**
      * 作者 
      */
    private String author;


}