package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.VO.SingleResultVO;
import com.example.demo.utils.ListToStringTypeHandler;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

@TableName(value = "result_step5", autoResultMap = true)
@Data
public class ResultStep5 implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer aid;
    private Integer uid;
    private Integer processMode;
    private Integer material;
    private String score;


    public ResultStep5(SingleResultVO singleResultVO){
        BeanUtils.copyProperties(singleResultVO, this);
    }
    public ResultStep5(){

    }
}
