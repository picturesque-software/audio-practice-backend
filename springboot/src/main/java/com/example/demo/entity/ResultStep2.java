package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.VO.SingleResultVO;
import com.example.demo.utils.ListToStringTypeHandler;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

@TableName(value = "result_step2", autoResultMap = true)
@Data
public class ResultStep2 implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer aid;
    private Integer uid;
    private Integer processMode;
    private Integer material;
    private Integer algorithm;
    private String score;

    @TableField(typeHandler = ListToStringTypeHandler.class)
    private List<String> audioOrder;

    public ResultStep2(SingleResultVO singleResultVO){
        BeanUtils.copyProperties(singleResultVO, this);
    }
    public ResultStep2(){

    }
}
