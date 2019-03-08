package com.wulis.jsoup.shanbay.controller;

import com.alibaba.fastjson.JSONObject;
import com.wulis.jsoup.content.model.vo.ContentVo;
import com.wulis.jsoup.shanbay.service.ShanbayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by dell on 2018/12/6.
 */
@Slf4j
@RestController
@RequestMapping(value = "shanbay")
public class ShanbayController {
    @Resource
    private ShanbayService shanbayService;

    /**
     * 采集扇贝网单词信息并存库
     *
     * @return
     */
    @RequestMapping(value = "save/word")
    @ResponseBody
    public JSONObject getEnglishWord() {
        return shanbayService.getEnglishWord();
    }

    /**
     * 调用百度翻译接口下载音频
     *
     * @return
     */
    @RequestMapping(value = "save/voice")
    @ResponseBody
    public JSONObject getEnglishAndChineseVoice() {
        return shanbayService.getEnglishAndChineseVoice();
    }

    /**
     * 获取单词列表信息
     *
     * @param contentVo
     * @return
     */
    @RequestMapping(value = "queryShanbayEnglishTablePage")
    @ResponseBody
    public JSONObject queryShanbayEnglishTablePage(ContentVo contentVo) {
        return shanbayService.queryShanbayEnglishTablePage(contentVo);
    }
}
