package com.example.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
@TableName("t_zhengshu")
public class Zhengshu extends Model<Zhengshu> {
    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
      * 作品名 
      */
    private String workid;

    /**
      * 审核结果 
      */
    private String checkresult;

    /**
      * 电子证书 
      */
    private String zhengshu;

    /**
      * 支付状态 
      */
    private String pay;


}