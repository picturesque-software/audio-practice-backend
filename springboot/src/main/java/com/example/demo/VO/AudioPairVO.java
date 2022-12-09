package com.example.demo.VO;

import com.example.demo.entity.Audio;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class AudioPairVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<AudioVO> audioList;

    private AudioVO referAudio;

    public AudioPairVO(AudioVO audioVO1, AudioVO audioVO2, AudioVO referAudio) {
        this.audioList = new ArrayList<>();
        this.audioList.add(audioVO1);
        this.audioList.add(audioVO2);
        this.setReferAudio(referAudio);
    }
    public AudioPairVO(AudioVO audioVO1, AudioVO audioVO2) {
        this.audioList = new ArrayList<>();
        this.audioList.add(audioVO1);
        this.audioList.add(audioVO2);
    }
    public AudioPairVO(List<AudioVO> audioList) {
        this.audioList = audioList;
    }
    public AudioPairVO(List<AudioVO> audioList, AudioVO referAudio){
        this.audioList = audioList;
        this.referAudio = referAudio;
    }
    public AudioPairVO(){

    }
}
