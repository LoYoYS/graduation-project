package com.yuan.qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordQo {
    private Integer id;
    private String oldPassword;
    private String newPassword;
    private String again;
}
