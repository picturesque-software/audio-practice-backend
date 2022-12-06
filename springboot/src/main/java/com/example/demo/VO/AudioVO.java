package com.example.demo.VO;

import com.example.demo.entity.Audio;
import com.example.demo.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Data
public class AudioVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String url;
    private Integer processMode;
    private Integer algorithm;
    private Integer material;
    private String score;

    public AudioVO(Audio audio){
        BeanUtils.copyProperties(audio, this);
    }

}
