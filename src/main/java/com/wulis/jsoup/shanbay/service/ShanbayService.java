package com.wulis.jsoup.shanbay.service;

import com.alibaba.fastjson.JSONObject;
import com.wulis.jsoup.catalog.model.Catalog;
import com.wulis.jsoup.content.model.vo.ContentVo;

import java.util.List;

/**
 * Created by dell on 2018/12/4.
 */
public interface ShanbayService {

    /**
     * 采集扇贝网单词信息并存库
     *
     * @return
     */
    JSONObject getEnglishWord();

    /**
     * 根据网站名称获取章节列表
     *
     * @param webName
     * @return
     */
    List<Catalog> getShanbayCatalogList(String webName);

    /**
     * 获取单词列表信息
     *
     * @param contentVo
     * @return
     */
    JSONObject queryShanbayEnglishTablePage(ContentVo contentVo);

    /**
     * 调用百度翻译接口下载音频
     *
     * @return
     */
    JSONObject getEnglishAndChineseVoice();
}
