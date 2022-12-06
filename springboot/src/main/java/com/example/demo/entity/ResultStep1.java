package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.VO.SingleResultVO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@TableName("result_step1")
@Data
public class ResultStep1 {
    private Integer aid;
    private Integer uid;
    private Integer processMode;
    private Integer material;
    private String score;

    public ResultStep1(SingleResultVO singleResultVO){
        BeanUtils.copyProperties(singleResultVO, this);
    }
}
