package com.example.demo.VO;

import lombok.Data;

import java.io.Serializable;
@Data
public class SingleResultVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer uid;
    private Integer aid;
    private Integer articulation;
    private Integer lightNess;
    private Integer distortion;
    private Integer processMode;
    private String score;
    private Integer material;
}
