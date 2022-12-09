package com.example.demo.VO;

import com.example.demo.entity.Audio;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

@Data
public class SingleResultVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer uid;
    // aid
    private Integer aid;
    private Integer articulation;
    private Integer lightNess;
    private Integer distortion;
    private Integer processMode;
    private List<Integer> order;
    private Integer material;
    private Integer referAid;
    private String score;

    public SingleResultVO(AudioVO audioVO){
        BeanUtils.copyProperties(audioVO, this);
    }
    public SingleResultVO(){

    }
}
