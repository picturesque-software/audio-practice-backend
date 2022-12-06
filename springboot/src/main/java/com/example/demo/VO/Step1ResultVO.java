package com.example.demo.VO;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Step1ResultVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<SingleResultVO> scoreList;


}
