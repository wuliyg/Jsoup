package com.wulis.jsoup.shanbay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wulis.jsoup.catalog.model.Catalog;
import com.wulis.jsoup.catalog.dao.CatalogMapper;
import com.wulis.jsoup.common.utils.ChangeWord;
import com.wulis.jsoup.common.utils.DownLoadFileUtils;
import com.wulis.jsoup.content.dao.ContentMapper;
import com.wulis.jsoup.content.model.dto.Content;
import com.wulis.jsoup.content.model.vo.ContentVo;
import com.wulis.jsoup.shanbay.service.ShanbayService;
import com.wulis.jsoup.website.dao.WebsiteMapper;
import com.wulis.jsoup.website.model.Website;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * Created by dell on 2018/12/4.
 */
@Slf4j
@Service
public class ShanbayServiceImpl implements ShanbayService {

    @Resource
    private WebsiteMapper websiteMapper;

    @Resource
    private CatalogMapper catalogMapper;

    @Resource
    private ContentMapper contentMapper;

    /**
     * 采集扇贝网信息并存库
     *
     * @return
     */
    @Override
    public JSONObject getEnglishWord() {
        JSONObject jsonObject = new JSONObject();
        try {
            //查询网站信息
            Website website = websiteMapper.selectShanbayWebsite();
            //开启线程获取章节信息并存库
            ShanbayCatalog shanbayCatalog = new ShanbayCatalog();
            shanbayCatalog.webId = website.getId();
            shanbayCatalog.url = "/wordbook/104791";
            shanbayCatalog.webSite = website.getAgreement() + "://" + website.getWebsite();
            shanbayCatalog.start();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return jsonObject;
    }

    /**
     * 根据网址获取章节信息
     *
     * @param webName
     * @return
     */
    @Override
    public List<Catalog> getShanbayCatalogList(String webName) {
        return catalogMapper.getShanbayCatalogList(webName);
    }

    /**
     * 获取单词列表信息
     *
     * @param contentVo
     * @return
     */
    @Override
    public JSONObject queryShanbayEnglishTablePage(ContentVo contentVo) {
        JSONObject jsonObject = new JSONObject();
        try {
            PageHelper pageHelper = new PageHelper();
            pageHelper.startPage(contentVo.getPage(), contentVo.getLimit());
            //根据章节名称查询单词列表
            List<Content> contents = contentMapper.queryShanbayEnglishTablePage(contentVo.getTitle());
            PageInfo<Content> pageInfo = new PageInfo<>(contents);
            //获取总记录数
            int total = (int) pageInfo.getTotal();
            jsonObject.put("count", total);
            jsonObject.put("data", contents);
            jsonObject.put("code", 0);
            jsonObject.put("msg", "查询成功!");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return jsonObject;
    }

    /**
     * 调用百度翻译接口下载音频
     *
     * @return
     */
    @Override
    public JSONObject getEnglishAndChineseVoice() {
        JSONObject jsonObject = new JSONObject();
        try {
            String url = this.getClass().getClassLoader().getResource("").getPath().substring(1) + "static/voice";
            List<Content> contents = contentMapper.queryByNoneEnglishOrChineseVoice();
            Iterator<Content> integer = contents.iterator();
            while (integer.hasNext()) {
                Content content = integer.next();
                try {
                    boolean downEn = DownLoadFileUtils.downloadByNIO("https://fanyi.baidu.com/gettts?lan=en&text=" + content.getEngWord() + "&spd=3&source=web", url, content.getEngWord() + "_en.mp3");
                    if (downEn) content.setEnglishVoiceUrl(content.getEngWord() + "_en.mp3");
                    //  boolean downZn = DownLoadFileUtils.downloadByNIO("https://fanyi.baidu.com/gettts?lan=zh&text=" + ChangeWord.changeEnToZnWord(content.getTranslate()) + "&spd=4&source=web", url, content.getEngWord() + "_zn.mp3");
                    // if (downZn) content.setEnglishVoiceUrl(content.getEngWord() + "_en.mp3");
                    contentMapper.updateEnglishAndChineseVoiceUrl(content);
                } catch (Exception e) {
                    log.error("----------获取音频：" + content.getEngWord() + "时出错----------");
                    log.error(e.getMessage());
                }
            }
            jsonObject.put("msg", "音频采集完毕");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return jsonObject;
    }

    /**
     * 采集扇贝网章节信息并存库
     */
    private class ShanbayCatalog extends Thread {
        private String webSite; //网址
        private String url;     //接口地址
        private int webId;

        public void run() {
            shanbayCatalog(webSite, url, webId);
        }

        void shanbayCatalog(String webSite, String url, int webId) {
            try {
                //获取html信息
                Document doc = Jsoup.connect(webSite + url).get();
                //解析html
                Elements elements = doc.select("#wordlist-");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String inputTime = simpleDateFormat.format(new Date());
                for (Element element : elements) {
                    try {
                        Element element_ = element.selectFirst("div").select("table > tbody > tr > td").first().selectFirst("a");
                        Element _element = element.selectFirst("div").select("table > tbody > tr > td").get(1);
                        String url_ = element_.attr("href");
                        String titel = element_.html();
                        String barNumber = _element.text().replaceAll("单词数：", "");
                        Catalog catalog = new Catalog();
                        catalog.setWebId(webId);
                        catalog.setInputTime(inputTime);
                        catalog.setUrl(url_);
                        catalog.setTitle(titel);
                        catalog.setBarNumber(barNumber);
                        catalog.setOperatorBy(0);
                        //插入章节信息
                        catalogMapper.insertShanbayCatalog(catalog);
                        //开启线程获取章节中单词信息并存库
                        ShanbayContent shanbayContent = new ShanbayContent();
                        shanbayContent.webSite = webSite;
                        shanbayContent.url = url_;
                        shanbayContent.cataId = catalog.getId();
                        shanbayContent.barNumber = Integer.parseInt(barNumber);
                        shanbayContent.start();
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    /**
     * 开启线程获取并插入单词信息
     */
    private class ShanbayContent extends Thread {
        private String webSite; //网址
        private String url;     //接口地址
        private int cataId;     //章节ID
        private int barNumber;  //总数

        public void run() {
            shanbayContent(webSite, url, cataId, barNumber);
        }

        void shanbayContent(String webSite, String url, int cataId, int barNumber) {
            int page = (int) Math.ceil((double) barNumber / (double) 20);
            for (int i = 1; i <= page; i++) {
                try {
                    //获取html信息
                    Document doc = Jsoup.connect(webSite + url + "?page=" + i).get();
                    //解析html
                    Elements elements = doc.body().select("tr.row");
                    List<Content> list = new ArrayList<>();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String inputTime = simpleDateFormat.format(new Date());
                    for (Element element : elements) {
                        String engWord = element.select("td.span2").select("strong").html();
                        String translate = element.select("td.span10").html();
                        Content content = new Content();
                        content.setEngWord(engWord);
                        content.setTranslate(translate);
                        content.setOperatorBy(0);
                        content.setCataId(cataId);
                        content.setInputTime(inputTime);
                        list.add(content);
                    }
                    //插入单词信息
                    contentMapper.insertShanbayContent(list);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }
    }
}
