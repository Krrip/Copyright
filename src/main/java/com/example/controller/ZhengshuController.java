package com.example.controller;

import com.example.common.Result;
import com.example.entity.Zhengshu;
import com.example.service.ZhengshuService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/zhengshu")
public class ZhengshuController {
    @Resource
     private ZhengshuService zhengshuService;

    @PostMapping
    public Result<?> save(@RequestBody Zhengshu zhengshu) {
        return Result.success(zhengshuService.save(zhengshu));
    }

    @PutMapping
    public Result<?> update(@RequestBody Zhengshu zhengshu) {
        return Result.success(zhengshuService.updateById(zhengshu));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        zhengshuService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<Zhengshu> findById(@PathVariable Long id) {
        return Result.success(zhengshuService.getById(id));
    }

    @GetMapping
    public Result<List<Zhengshu>> findAll() {
        return Result.success(zhengshuService.list());
    }

    @GetMapping("/page")
    public Result<IPage<Zhengshu>> findPage(@RequestParam(required = false, defaultValue = "") String name,
                                           @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                           @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return Result.success(zhengshuService.page(new Page<>(pageNum, pageSize), Wrappers.<Zhengshu>lambdaQuery().like(Zhengshu::getWorkid, name)));
    }

}