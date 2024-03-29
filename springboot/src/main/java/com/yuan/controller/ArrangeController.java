package com.yuan.controller;


import com.yuan.configure.isCheckToken;
import com.yuan.domain.Arrange;
import com.yuan.domain.Interval;
import com.yuan.domain.ResultData;
import com.yuan.service.ArrangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/arrange")
public class ArrangeController {
    
    @Autowired
    private ArrangeService service;

    @RequestMapping("/list")
//    @isCheckToken
    public ResultData<List<Arrange>> arrange(Integer id, String date){return service.getArrangeList(id,date);}

    @RequestMapping("/saveArrange")
    @isCheckToken
    public ResultData<String> saveArrange(@RequestBody List<Arrange> list){
        return service.saveArrangeList(list);
    }

    @RequestMapping("/updateArrange")
    @isCheckToken
    public ResultData<String> updateArrange(@RequestBody Arrange arrange){
        return service.updateArrange(arrange);
    }

    @RequestMapping("/updateInterval")
    @isCheckToken
    public ResultData<String> updateInterval(@RequestBody List<Interval> list){
        return service.updateInterval(list);
    }

    @RequestMapping("/deleteArrange")
    @isCheckToken
    public ResultData<String> deleteArrange(@RequestBody List<Arrange> list){return service.deleteArrange(list);
    }

    @RequestMapping("/deleteArrangeAll")
    @isCheckToken
    public ResultData<String> deleteArrangeAll(@RequestParam("id") Integer id){
        return service.deleteArrangeAll(id);
    }

    @RequestMapping("/deleteArrangeList")
    @isCheckToken
    public ResultData<String> deleteArrangeList(@RequestBody List<Integer> list){
        return service.deleteArrangeList(list);
    }
    @RequestMapping("/getArranges")
    public ResultData<List<Arrange>> getArranges(Integer id, String date){return service.getArranges(id,date);
    }
}
