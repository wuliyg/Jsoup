package com.wulis.jsoup.common.pageController;

import com.wulis.jsoup.catalog.model.Catalog;
import com.wulis.jsoup.shanbay.service.ShanbayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Herman on 2019/3/7.
 */
@Slf4j
@Controller
@RequestMapping(value = "shanbay")
public class PageController {
    @Resource
    private ShanbayService shanbayService;

    /**
     * 跳转单词列表首页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "read")
    public String readEnglishWord(Model model) {
        //获取扇贝网章节信息
        List<Catalog> catalogList = shanbayService.getShanbayCatalogList("扇贝");
        model.addAttribute("cata", catalogList);
        return "shanbay/english";
    }
}
