package com.yuan.controller;

import com.github.pagehelper.PageInfo;
import com.yuan.configure.isCheckToken;
import com.yuan.domain.ResultData;
import com.yuan.domain.Student;
import com.yuan.domain.SubjectApply;
import com.yuan.qo.StudentQo;
import com.yuan.qo.VacateQo;
import com.yuan.service.StudentService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService service;

    @Value("${Location.realPath}")
    private String realPath;
    @Value("${Location.mapperPath}")
    private String mapperPath;

//    @isCheckToken
    @RequestMapping("/list")
    public ResultData<PageInfo<Student>> list(StudentQo studentQo){
        return ResultData.success(service.list(studentQo));
    }

    @RequestMapping("/save")
    @isCheckToken
    public ResultData<String> save(@RequestBody Student student){return service.insert(student);}

    @RequestMapping("/update")
    @isCheckToken
    public ResultData<String> update(@RequestBody Student student){
        return service.updateStudent(student);
    }

    @RequestMapping("/delete")
    @isCheckToken
    public ResultData<String> delete(@RequestBody Student student){
        return service.delete(student);
    }

    @RequestMapping("/deleteList")
    @isCheckToken
    public ResultData<String> deleteList(@RequestBody List<Student> studentList){
        return service.deleteList(studentList);
    }

    @RequestMapping("/importExcel")
    @isCheckToken
    public ResultData<String> importExcel(MultipartFile file){
        return service.importExcel(file);
    }

    @RequestMapping("/exportExcel")
    @isCheckToken
    public void exportExcel(HttpServletResponse response){
        Workbook workbook = service.exportExcel();
        //设置响应头，告诉浏览器下载响应的内容
        response.setHeader("Content-Disposition","attachment;filename=student.xls");
        //获取响应输出流
        ServletOutputStream outputStream;
        try {
            outputStream = response.getOutputStream();
            //将workbook对象通过响应输出流写出
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/findStudent")
    public ResultData<List<Student>> findStudent(@RequestParam("keyWord") String keyWord) {
      return service.findStudent(keyWord);
    }

    @RequestMapping("/getUrl")
    public ResultData<String> getUrl(MultipartFile file) throws IOException {
        if (file == null)
            return ResultData.fail("失败");
        else {
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            fileName = UUID.randomUUID() + suffixName;
            file.transferTo(new File(realPath + fileName));
//            String url = "https://yuanshu.love" + mapperPath + fileName;
            String url = "http://localhost:8081/driveSchool" + mapperPath + fileName;
            return ResultData.success(url);
        }
    }

    @RequestMapping("/saveApply")
    public ResultData<String> saveApply(@RequestBody SubjectApply apply){
        return  service.saveApply(apply);
    }

    @RequestMapping("/getApplicationListById")
    public ResultData<List<SubjectApply>> getApplicationListById(Integer id){
        return  service.getApplicationListById(id);
    }

    @RequestMapping("/getList")
    public ResultData<PageInfo<SubjectApply>> getList(VacateQo qo){
        return  service.getList(qo);
    }

    @RequestMapping("/cancelApply")
    public ResultData<String> cancelApply(@RequestParam("id") Integer id){
        return  service.cancelApply(id);
    }

    @RequestMapping("/check")
    @isCheckToken
    public ResultData<String> check(@RequestBody SubjectApply apply){return  service.check(apply);}

}
