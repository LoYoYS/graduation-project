package com.yuan.controller;


import com.github.pagehelper.PageInfo;
import com.yuan.configure.isCheckToken;
import com.yuan.domain.Info;
import com.yuan.domain.Notice;
import com.yuan.domain.NumberCount;
import com.yuan.domain.ResultData;
import com.yuan.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private CommonService service;

    @Value("${Location.realPath}")
    private String realPath;
    @Value("${Location.mapperPath}")
    private String mapperPath;

    @RequestMapping("/list")
    public ResultData<PageInfo<Notice>> list(Integer page){return ResultData.success(service.list(page));}

    @RequestMapping("/getList")
    public ResultData<List<Notice>> getList(){return service.getList();}

    @RequestMapping("/getNotices")
    public ResultData<PageInfo<Notice>> getNotices(){return ResultData.success(service.getNotices());}

    @RequestMapping("/save")
    @isCheckToken
    public ResultData<String> save(@RequestBody Notice notice){ return service.save(notice);}

    @RequestMapping("/getNotice")
    public ResultData<Notice> getNotice(Integer id){ return service.getNotice(id);}

    @RequestMapping("/update")
    @isCheckToken
    public ResultData<String> update(@RequestBody Notice notice){ return service.update(notice);}

    @RequestMapping("/delete")
    @isCheckToken
    public ResultData<String> delete(Integer id){ return service.delete(id);}



    @RequestMapping("/getNumber")
//    @isCheckToken
    public ResultData<NumberCount> getNumber(){ return service.getNumber();}

    @RequestMapping("/getLogoUrl")
    @isCheckToken
    public ResultData<String> getLogoUrl(MultipartFile file) throws IOException {
        if(file==null)
            return ResultData.fail("失败");
        else{
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            fileName = UUID.randomUUID()+suffixName;
            file.transferTo(new File(realPath+fileName));
            String url="http://localhost:8081/driveSchool"+mapperPath+fileName;
            return ResultData.success(url);
        }}

    @RequestMapping("/getBackUrl")
    @isCheckToken
    public ResultData<String> getBackUrl(MultipartFile file) throws IOException {
        if(file==null)
            return ResultData.fail("失败");
        else{
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            fileName = UUID.randomUUID()+suffixName;
            file.transferTo(new File(realPath+fileName));
            String url="http://localhost:8081/driveSchool"+mapperPath+fileName;
            return ResultData.success(url);
        }}


    @RequestMapping("/saveInfo")
    @isCheckToken
    public ResultData<String> saveInfo(@RequestBody Info info){return service.saveInfo(info);}

    @RequestMapping("/getInfo")
    public ResultData<Info> getInfo(){return service.getInfo();}

}
