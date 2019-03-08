package com.wulis.jsoup.catalog.model;

import lombok.Data;

@Data
public class Catalog {
    private Integer id;

    private Integer webId;

    private String inputTime;

    private String url;

    private String title;

    private String barNumber;

    private Integer operatorBy;
}