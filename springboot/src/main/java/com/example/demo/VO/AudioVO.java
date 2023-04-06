package com.example.demo.VO;

import com.example.demo.entity.Audio;
import com.example.demo.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Data
public class AudioVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer aid;
    private String name;
    private String url;
    private Integer processMode;
    private Integer algorithm;
    private Integer material;
    private String score;
    private String reverb1;
    private String reverb2;

    public AudioVO(Audio audio){
        BeanUtils.copyProperties(audio, this);
        this.url = getUrl(this.material, this.processMode, this.algorithm, 0);
        this.reverb1 = getUrl(this.material, this.processMode, this.algorithm, 1);
        this.reverb2 = getUrl(this.material, this.processMode, this.algorithm, 2);
    }

    public AudioVO(){

    }

    String getUrl(Integer material, Integer processMode, Integer algorithm, Integer reverb) {
        String baseUrl = "https://audio-practice.oss-cn-hangzhou.aliyuncs.com/audios/";
        if (material == 1) {
            baseUrl += "%E6%9E%AA%E5%A3%B0";
        } else if (material == 2) {
            baseUrl += "%E8%84%9A%E6%AD%A5";
        } else if (material == 3) {
            baseUrl += "%E7%94%B7%E5%A3%B0";
        } else {
            baseUrl += "%E5%A5%B3%E5%A3%B0";
        }
        if (processMode == 0) {
            return baseUrl + "_reverb_" + reverb.toString() + ".wav";
        } else {
            return baseUrl + "_HRTF" + (processMode == 1 ? "_front" : "_back") + algorithm + "_reverb_" + reverb.toString() + ".wav";
        }
    }

}
