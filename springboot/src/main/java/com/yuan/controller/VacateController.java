package com.yuan.controller;


import com.github.pagehelper.PageInfo;
import com.yuan.configure.isCheckToken;
import com.yuan.domain.ResultData;
import com.yuan.domain.Vacate;
import com.yuan.qo.VacateQo;
import com.yuan.service.VacateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("/vacate")
public class VacateController {

    @Autowired
    private VacateService service;

//    @isCheckToken
    @RequestMapping("/list")
    public ResultData<PageInfo<Vacate>> list(VacateQo qo){
        return service.list(qo);
    }

    @isCheckToken
    @RequestMapping("/check")
    public ResultData<String> check(@RequestParam("id") Integer id,@RequestParam("status") Integer status){
        return service.check(id,status);
    }

    @isCheckToken
    @RequestMapping("/accessAll")
    public ResultData<String> accessAll(@RequestBody List<Integer> list){
        return service.accessAll(list);
    }

    @isCheckToken
    @RequestMapping("/refuseAll")
    public ResultData<String> refuseAll(@RequestBody List<Integer> list){
        return service.refuseAll(list);
    }

    @RequestMapping("/submitVacate")
    public ResultData<String> submitVacate(@RequestBody Vacate vacate){
        return service.submit(vacate);
    }

    @RequestMapping("/getList")
    public ResultData<List<Vacate>> getList(@RequestParam("id") Integer id){
        return service.getList(id);
    }

    @RequestMapping("/cancel")
    public ResultData<String> cancel(@RequestParam("id") Integer id){
        return service.cancel(id);
    }

    @RequestMapping("/confirm")
    public ResultData<String> confirm(@RequestParam("id") Integer id){
        return service.confirm(id);
    }

}
