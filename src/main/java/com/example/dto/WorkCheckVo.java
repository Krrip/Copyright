package com.example.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_workcheck")
public class WorkCheckVo extends Model<WorkCheckVo> {
    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
      * 管理员 
      */
    private String username;

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
    private String workName;

    /**
      * 作品类型 
      */
    private String type;

    /**
      * 备注 
      */
    private String remind;



}