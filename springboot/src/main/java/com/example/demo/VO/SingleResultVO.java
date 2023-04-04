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

    private Integer algorithm;
    // quality 0,1,2（与参考接近）
    private Integer quality;
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
