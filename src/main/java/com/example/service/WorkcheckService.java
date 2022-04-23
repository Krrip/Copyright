package com.example.service;

import com.example.dto.WorkCheckVo;
import com.example.entity.Workcheck;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.WorkcheckMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WorkcheckService extends ServiceImpl<WorkcheckMapper, Workcheck> {

    @Resource
    private WorkcheckMapper workcheckMapper;

    public List<WorkCheckVo> findAll() {
        return workcheckMapper.findAll();
    }
}