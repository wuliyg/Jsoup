package com.wulis.jsoup.website.model;

import lombok.Data;

@Data
public class Website {
    private Integer id;

    private String inputTime;

    private String website;

    private String agreement;

    private String describe;

    private Integer operatorBy;
}