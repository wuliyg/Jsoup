package com.wulis.jsoup.content.dao;

import com.wulis.jsoup.content.model.dto.Content;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContentMapper {

    /**
     * 插入单词信息
     *
     * @param list
     */
    void insertShanbayContent(List<Content> list);

    /**
     * 根据章节查询单词列表
     *
     * @param title
     * @return
     */
    List<Content> queryShanbayEnglishTablePage(@Param(value = "title") String title);

    /**
     * 查询没有音频的单词
     *
     * @return
     */
    List<Content> queryByNoneEnglishOrChineseVoice();

    /**
     * 更新中文语音和英文语音地址
     *
     * @param content
     */
    void updateEnglishAndChineseVoiceUrl(Content content);
}