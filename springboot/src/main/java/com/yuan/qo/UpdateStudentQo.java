package com.yuan.qo;

import com.yuan.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStudentQo {
    private Student oldStudent;
    private Student newStudent;
}
