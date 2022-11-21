package com.yuan.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuan.domain.ResultData;
import com.yuan.domain.Student;
import com.yuan.domain.Subject;
import com.yuan.mapper.StudentMapper;
import com.yuan.mapper.SubjectMapper;
import com.yuan.mapper.UserMapper;
import com.yuan.qo.StudentQo;
import com.yuan.service.StudentService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public PageInfo<Student> list(StudentQo studentQo) {
        PageHelper.startPage(studentQo.getCurrentPage(),studentQo.getSize());
        List<Student> list = studentMapper.list(studentQo);
        return new PageInfo<>(list);
    }

    @Override
    public ResultData<String> insert(Student student) {
        Student s = studentMapper.findAllById_number(student);
        if (s != null)
            return ResultData.fail("学员档案已经存在");
        else{
            Integer insert = studentMapper.insert(student);
            Integer insert1 = subjectMapper.insert(student);
            if (0<insert&&0<insert1)
                return ResultData.success("添加成功!");
            else
                return ResultData.fail("添加失败!");
        }
    }

    @Override
    public ResultData<String> updateStudent(Student student) {
        //  更新学员信息
        Integer integer = studentMapper.updateStudent(student);
        Integer update = subjectMapper.update(student);
        if(student.getIsBindWx()==0)
            userMapper.updateUserRole(student.getId(),1);
        if(0<integer && 0<update)
            return ResultData.success("修改成功");
        return ResultData.fail("修改失败");
    }

    @Override
    public ResultData<String> delete(Student student) {
        Integer delete = studentMapper.delete(student);
        subjectMapper.delete(student);
        if(0<delete)
            return ResultData.success("删除成功");
        else
            return ResultData.fail("删除失败");
    }

    @Override
    public ResultData<String> deleteList(List<Student> studentList) {
        Integer integer = subjectMapper.deleteList(studentList);
        studentMapper.deleteList(studentList);
        if(0<integer)
            return ResultData.success("删除成功!");
        else
            return ResultData.fail("删除失败!");
    }

    @Override
    public ResultData<String> importExcel(MultipartFile file){
        //获取读取上传文件的输入流
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
            //以Excel的方式读取文件并解析  借助工具: poi -- pom.xml引进
            Workbook workbook = new HSSFWorkbook(inputStream);
            //解析数据
            //1.获取第一张sheet  getSheet(sheetName): 根据sheet的名字获取sheet
            // getSheetAt(sheetIndex): 根据sheet的下标获取sheet，以0开始
            Sheet sheet = workbook.getSheetAt(0);
            //2.循环获取sheet中的每一行
            //获取最后一行的下标
            int lastRowNum = sheet.getLastRowNum();
            //从第二行开始遍历，第一行为表头标题行，非数据
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Subject subject = new Subject(0, 0, 0, 0);
            for (int i = 1 ; i <= lastRowNum ; i++){
                Row row = sheet.getRow(i);
                //获取行(Row)当中的每一个单元格中的数据
                String name = row.getCell(0).getStringCellValue();
                String sex = row.getCell(1).getStringCellValue();
                String id_number = row.getCell(2).getStringCellValue();
                String phone = row.getCell(3).getStringCellValue();
                String date = row.getCell(4).getStringCellValue();
                String type = row.getCell(5).getStringCellValue();
                Date parse = simpleDateFormat.parse(date);
                Student student = new Student(null, name, sex, id_number, phone, parse, type, subject,0);
                Student find = studentMapper.findAllById_number(student);
                if (find!=null)
                    continue;
                studentMapper.insert(student);
                subjectMapper.insert(student);

            }
            return ResultData.success("导入数据成功！");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return ResultData.fail("导入数据失败！");
        }

    }

    @Override
    public Workbook exportExcel() {
        //先生成一个空Excel对象
        Workbook workbook = new HSSFWorkbook();
        //创建一个sheet(表格)
        Sheet sheet = workbook.createSheet("学员信息档案");
        //创建表头(标题行)
        Row row = sheet.createRow(0);
        //创建标题行中的每一个单元格
        row.createCell(0).setCellValue("姓名");
        row.createCell(1).setCellValue("性别");
        row.createCell(2).setCellValue("身份证号");
        row.createCell(3).setCellValue("电话号码");
        row.createCell(4).setCellValue("报名日期");
        row.createCell(5).setCellValue("驾照类型");
        List<Student> students = studentMapper.selectAll();
        int index = 1;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Student student : students) {
            //每一个学员对象占用一行
            //创建每一行
            Row row1 = sheet.createRow(index);
            //创建单元格，将学员对象各个数据存储到单元格中0
            row1.createCell(0).setCellValue(student.getName());
            row1.createCell(1).setCellValue(student.getSex());
            row1.createCell(2).setCellValue(student.getId_number());
            row1.createCell(3).setCellValue(student.getPhone());
            row1.createCell(4).setCellValue(simpleDateFormat.format(student.getDate()));
            row1.createCell(5).setCellValue(student.getType());
            index++;
        }

        return workbook;
    }

    @Override
    public ResultData<List<Student>> findStudent(String keyWord) {
        List<Student> student = studentMapper.findStudent(keyWord);
        return ResultData.success(student);
    }
}
