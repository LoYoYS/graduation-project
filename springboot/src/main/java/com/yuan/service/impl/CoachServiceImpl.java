package com.yuan.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuan.domain.Coach;
import com.yuan.domain.ResultData;
import com.yuan.mapper.CarMapper;
import com.yuan.mapper.CoachMapper;
import com.yuan.mapper.UserMapper;
import com.yuan.qo.CoachQo;
import com.yuan.qo.UpdateCoachQo;
import com.yuan.service.CoachService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class CoachServiceImpl implements CoachService {

    @Autowired
    private CoachMapper coachMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CarMapper carMapper;

    @Override
    public PageInfo<Coach> getList(CoachQo qo) {
        PageHelper.startPage(qo.getCurrentPage(),qo.getSize());
        List<Coach> list = coachMapper.getList(qo);
        return new PageInfo<>(list);
    }

    @Override
    public ResultData<String> saveCoach(Coach coach) {
        Coach byId_number = coachMapper.findById_number(coach.getId_number());
        if (byId_number==null){
            Integer save = coachMapper.save(coach);
            carMapper.update(coach.getId(),coach.getCar());
            if(0<save)
                return ResultData.success("保存成功");
            return ResultData.fail("保存失败");
        }
        return ResultData.fail("教练档案已存在");
    }

    @Override
    public ResultData<String> updateCoach(UpdateCoachQo qo) {
        Coach oldCoach = qo.getOldCoach();
        Coach newCoach = qo.getNewCoach();
        carMapper.update(null, oldCoach.getCar());
        Integer update = coachMapper.update(newCoach);
        if(qo.getOldCoach().getIsBindWx()==1 && newCoach.getIsBindWx()==0){
            userMapper.updateUserRole(qo.getNewCoach().getId(),-1);
        }
        carMapper.update(newCoach.getId(), newCoach.getCar());
        if(0<update)
            return ResultData.success("修改成功！");
        return ResultData.fail("修改失败！");
    }

    @Override
    public ResultData<String> delete(Coach coach) {
        Integer delete = coachMapper.delete(coach);
        carMapper.update(null, coach.getCar());
        if(0<delete)
          return ResultData.success("删除成功！");
        return ResultData.fail("删除失败！");
    }

    @Override
    public ResultData<String> deleteList(List<Coach> list) {
        Integer integer = coachMapper.deleteList(list);
        carMapper.updateList(list);
        if(0<integer)
            return ResultData.success("删除成功！");
        return ResultData.fail("删除失败！");
    }

    @Override
    public ResultData<List<Coach>> getCoachList() {
        return ResultData.success(coachMapper.getCoachList());
    }

    @Override
    public ResultData<List<Coach>> getCoaches(String keyWord) {
        List<Coach> coaches = coachMapper.getCoaches(keyWord);
        return ResultData.success(coaches);
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
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 1 ; i <= lastRowNum ; i++){
                Row row = sheet.getRow(i);
                //获取行(Row)当中的每一个单元格中的数据
                String name = row.getCell(0).getStringCellValue();
                String sex = row.getCell(1).getStringCellValue();
                String id_number = row.getCell(2).getStringCellValue();
                String phone = row.getCell(3).getStringCellValue();
                String date = row.getCell(4).getStringCellValue();
                Coach coach = new Coach(null, name, sex, id_number, phone, date, null, 0);
                Coach find = coachMapper.findById_number(coach.getId_number());
                if (find!=null)
                    continue;
                coachMapper.save(coach);
            }
            return ResultData.success("导入数据成功！");
        } catch (IOException e) {
            e.printStackTrace();
            return ResultData.fail("导入数据失败！");
        }

    }

    @Override
    public Workbook exportExcel() {
        //先生成一个空Excel对象
        Workbook workbook = new HSSFWorkbook();
        //创建一个sheet(表格)
        Sheet sheet = workbook.createSheet("教练信息档案");
        //创建表头(标题行)
        Row row = sheet.createRow(0);
        //创建标题行中的每一个单元格
        row.createCell(0).setCellValue("姓名");
        row.createCell(1).setCellValue("性别");
        row.createCell(2).setCellValue("身份证号");
        row.createCell(3).setCellValue("电话号码");
        row.createCell(4).setCellValue("入职日期");
        List<Coach> coaches = coachMapper.selectAll();
        int index = 1;
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Coach coach : coaches) {
            //每一个教练对象占用一行
            //创建每一行
            Row row1 = sheet.createRow(index);
            //创建单元格，将教练对象各个数据存储到单元格中0
            row1.createCell(0).setCellValue(coach.getName());
            row1.createCell(1).setCellValue(coach.getSex());
            row1.createCell(2).setCellValue(coach.getId_number());
            row1.createCell(3).setCellValue(coach.getPhone());
//            row1.createCell(4).setCellValue(simpleDateFormat.format(coach.getDate()));
            row1.createCell(4).setCellValue(coach.getDate());
            index++;
        }

        return workbook;
    }

}
