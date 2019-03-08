package com.wulis.jsoup.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Created by Herman on 2019/3/7.
 */
@Slf4j
public class DownLoadFileUtils {
    /**
     * 使用NIO下载文件， 需要 jdk 1.7+
     *
     * @param url 请求路径
     * @param saveDir 保存路径
     * @param fileName 保存名称
     */
    public static boolean downloadByNIO(String url, String saveDir, String fileName) {
        try (InputStream ins = new URL(url).openStream()) {
            Path target = Paths.get(saveDir, fileName);
            Files.createDirectories(target.getParent());
            Files.copy(ins, target, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
