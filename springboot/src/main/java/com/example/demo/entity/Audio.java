package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@TableName("audio")
@Data
public class Audio {
    @TableId(type= IdType.AUTO)
    private Integer aid;
    private String name;
    private String url;
    private Integer processMode;
    private Integer algorithm;
    private Integer material;
    private String reverb1;
    private String reverb2;
}
