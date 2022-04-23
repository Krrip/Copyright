package com.example.mapper;

import com.example.entity.Workinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

public interface WorkinfoMapper extends BaseMapper<Workinfo> {
    @Select("SELECT COUNT(zhengshu) zhengshu FROM  t_workinfo")
    String  asd();
}