package com.example.controller;

import com.example.common.Result;
import com.example.dto.WorkCheckVo;
import com.example.entity.User;
import com.example.entity.Workcheck;
import com.example.entity.Workinfo;
import com.example.mapper.WorkcheckMapper;
import com.example.service.UserService;
import com.example.service.WorkcheckService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.service.WorkinfoService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/api/workcheck")
public class WorkcheckController {
    @Resource
    private WorkcheckService workcheckService;

    @Resource
    private UserService userService;

    @Resource
    private WorkcheckMapper workcheckMapper;

//统计通过数
    @GetMapping("/tongguo")
    public String tongguo(){
        return workcheckMapper.asd();
    }

    @Resource
    private WorkinfoService workinfoService;

    @GetMapping("/all")
    public List<Workcheck> All(){
        return workcheckService.list(null);
    }
    @PostMapping
    public Result<?> save(@RequestBody Workcheck workcheck) {
        return Result.success(workcheckService.save(workcheck));
    }

    @PutMapping
    public Result<?> update(@RequestBody Workcheck workcheck) {
        return Result.success(workcheckService.updateById(workcheck));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        workcheckService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<Workcheck> findById(@PathVariable Long id) {
        return Result.success(workcheckService.getById(id));
    }

    @GetMapping
    public Result<List<Workcheck>> findAll() {
//        List<WorkCheckVo> workCheckVoList  = workcheckService.findAll();
        return Result.success(workcheckService.list());
    }

    @GetMapping("/page")
    public Result<IPage<Workcheck>> findPage(@RequestParam(required = false, defaultValue = "") String name,
                                             @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                             @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return Result.success(workcheckService.page(new Page<>(pageNum, pageSize), Wrappers.<Workcheck>lambdaQuery().like(Workcheck::getId, name)));
    }

//多表联查
    @GetMapping("/pageAll")
    public Result<IPage<WorkCheckVo>> findPages(@RequestParam(required = false, defaultValue = "") String name,
                                                @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                @RequestParam(required = false, defaultValue = "10") Integer pageSize){
        final IPage<Workcheck> page = workcheckService.page(new Page<>(pageNum, pageSize), Wrappers.<Workcheck>lambdaQuery().like(Workcheck::getId, name));
        return Result.success(page.convert(workcheck ->{
            //将workcheck的id设置给Vo
            WorkCheckVo workCheckVo = new WorkCheckVo();
            workCheckVo.setId(workcheck.getId());
            workCheckVo.setChecktime(workcheck.getChecktime());
            workCheckVo.setCheckresult(workcheck.getCheckresult());
            workCheckVo.setRemind(workcheck.getRemind());
            String userid = workcheck.getUserid();
            String workid = workcheck.getWorkid();
            final User user = userService.getById(userid);
            final Workinfo workinfo = workinfoService.getById(workid);
            workCheckVo.setType(workinfo.getType());
            workCheckVo.setUsername(user.getUsername());
            workCheckVo.setWorkName(workinfo.getName());
                     return workCheckVo;
                }
        ));
    }
}