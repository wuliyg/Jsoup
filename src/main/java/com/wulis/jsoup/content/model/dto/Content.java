package com.wulis.jsoup.content.model.dto;

import lombok.Data;

@Data
public class Content {
    private Integer id;
    private String inputTime;
    private Integer cataId;
    private String engWord;
    private String translate;
    private String englishVoiceUrl;
    private String chineseVoiceUrl;
    private Integer operatorBy;
}