package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.VO.SingleResultVO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@TableName("result")
@Data
public class Result {
    private Integer aid;
    private Integer uid;
    private Integer articulation;
    private Integer lightNess;
    private Integer distortion;
    private String score;

    public Result(SingleResultVO singleResultVO){
        BeanUtils.copyProperties(singleResultVO, this);
    }
    public Result(){

    }
}
