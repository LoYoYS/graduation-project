package com.yuan.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

        private Integer id;
        private String username;
        private String password;
        private String email;
        private Integer role;
        private String token;
        private String nickName;
        private String url;

}
