package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.common.Response;
import com.example.demo.entity.Audio;
import com.example.demo.entity.Material;

import java.util.List;

public interface AudioService extends IService<Audio> {
    Response<?> getAllAudiosByMaterialByProcessMode(Integer material, Integer processMode);
    List<Material> getAllMaterial();
    Audio getReferAudio(Integer id);
}
