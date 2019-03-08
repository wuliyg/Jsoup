package com.wulis.jsoup.catalog.dao;


import com.wulis.jsoup.catalog.model.Catalog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CatalogMapper {
    /**
     * 插入章节目录信息
     *
     * @param catalog
     */
    void insertShanbayCatalog(Catalog catalog);

    /**
     * 根据网站名称查询章节信息
     *
     * @param webName
     * @return
     */
    List<Catalog> getShanbayCatalogList(@Param(value = "webName") String webName);
}