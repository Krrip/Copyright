package com.example.service;

import com.example.entity.Zhengshu;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.ZhengshuMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ZhengshuService extends ServiceImpl<ZhengshuMapper, Zhengshu> {

    @Resource
    private ZhengshuMapper zhengshuMapper;

}