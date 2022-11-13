package com.yuan.qo;


import com.yuan.domain.Coach;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCoachQo {
    private Coach oldCoach;
    private Coach newCoach;
}
