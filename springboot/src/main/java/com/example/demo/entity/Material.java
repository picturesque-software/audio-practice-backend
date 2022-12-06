package com.example.demo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("material")
@Data
public class Material {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String name;
}
