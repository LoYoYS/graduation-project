package com.yuan.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wx_user {
    private Integer id;
    private String username;
    private Integer a_id;
    private Integer role=0;
    private String nickName;
    private String url;
}
