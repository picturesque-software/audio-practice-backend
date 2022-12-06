package com.example.demo.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Questionnaire;
import com.example.demo.mapper.QuestionnaireMapper;
import com.example.demo.service.QuestionnaireService;
import org.springframework.stereotype.Service;

@Service("questionnaireService")
public class QuestionnaireServiceImpl extends ServiceImpl<QuestionnaireMapper, Questionnaire> implements QuestionnaireService {
}
