package com.wulis.jsoup.website.dao;


import com.wulis.jsoup.website.model.Website;

public interface WebsiteMapper {

    /**
     * 查询扇贝网站信息
     *
     * @return
     */
    Website selectShanbayWebsite();

}