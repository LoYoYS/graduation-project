package com.yuan.controller;

import com.github.pagehelper.PageInfo;
import com.yuan.configure.isCheckToken;
import com.yuan.domain.Coach;
import com.yuan.domain.ResultData;
import com.yuan.qo.CoachQo;
import com.yuan.qo.UpdateCoachQo;
import com.yuan.service.CoachService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;


@RestController
@RequestMapping("/coach")
public class CoachController {

    @Autowired
    private CoachService coachService;

    @RequestMapping("/list")
    @isCheckToken
    public ResultData<PageInfo<Coach>> list(CoachQo qo){
        System.out.println(qo);
        return ResultData.success(coachService.getList(qo));
    }

    @RequestMapping("/saveCoach")
    @isCheckToken
    public ResultData<String> saveCoach(@RequestBody Coach coach){
        return coachService.saveCoach(coach);
    }

    @RequestMapping("/updateCoach")
    @isCheckToken
    public ResultData<String> updateCoach(@RequestBody UpdateCoachQo qo){
        return coachService.updateCoach(qo);
    }

    @RequestMapping("/delete")
    @isCheckToken
    public ResultData<String> delete(@RequestBody Coach coach){
        return coachService.delete(coach);
    }

    @RequestMapping("/deleteList")
    @isCheckToken
    public ResultData<String> deleteList(@RequestBody List<Coach> list){
        return coachService.deleteList(list);
    }

    @RequestMapping("/getCoachList")
    @isCheckToken
    public ResultData<List<Coach>> list(){
        return coachService.getCoachList();
    }

    @RequestMapping("/getCoaches")
    public ResultData<List<Coach>> getCoaches(@RequestParam("keyWord")String keyWord){
        return coachService.getCoaches(keyWord);
    }

    @RequestMapping("/importExcel")
    @isCheckToken
    public ResultData<String> importExcel(MultipartFile file) throws IOException, ParseException {
        return coachService.importExcel(file);
    }

    @RequestMapping("/exportExcel")
    @isCheckToken
    public void exportExcel(HttpServletResponse response) throws IOException {
        Workbook workbook = coachService.exportExcel();
        //设置响应头，告诉浏览器下载响应的内容
        response.setHeader("Content-Disposition","attachment");
        //获取响应输出流
        ServletOutputStream outputStream = response.getOutputStream();
        //将workbook对象通过响应输出流写出
        workbook.write(outputStream);
    }

}
