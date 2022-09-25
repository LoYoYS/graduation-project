package com.yuan.utils;

import java.util.Random;

public class EmailUtil {

    public String getCode(){
        String code="";
        int [] codes = new int[3];
        Random random = new Random();
        //自动生成验证码
        for (int i = 0; i < 6; i++) {
            int num = random.nextInt(10) + 48;
            int uppercase = random.nextInt(26) + 65;
            int lowercase = random.nextInt(26) + 97;
            codes[0] = num;
            codes[1] = uppercase;
            codes[2] = lowercase;
            code += (char)codes[random.nextInt(3)];
        }
        return  code;
    }
}
