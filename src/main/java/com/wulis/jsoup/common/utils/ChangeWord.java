package com.wulis.jsoup.common.utils;

/**
 * Created by Herman on 2019/3/7.
 */
public class ChangeWord {
    public static String changeEnToZnWord(String word) {
        word = word.replaceAll("adj.", ";形容词：");
        word = word.replaceAll("adv.", ";副词：");
        word = word.replaceAll("art.", ";冠词：");
        word = word.replaceAll("aux.", ";助动词：");
        word = word.replaceAll("c.", ";可数名词：");
        word = word.replaceAll("conj.", ";连词：");
        word = word.replaceAll("int.", ";感叹词：");
        word = word.replaceAll("interj.", ";感叹词：");
        word = word.replaceAll("n.", ";名词：");
        word = word.replaceAll("num.", ";数词：");
        word = word.replaceAll("prep.", ";介词：");
        word = word.replaceAll("pron.", ";代词：");
        word = word.replaceAll("u.", ";不可数名词：");
        word = word.replaceAll("v.", ";动词：");
        word = word.replaceAll("vi.", ";不及物动词：");
        word = word.replaceAll("vt.", ";及物动词：");
        return word;
    }
}
