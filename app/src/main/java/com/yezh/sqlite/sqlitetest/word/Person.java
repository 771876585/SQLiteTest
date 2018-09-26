package com.yezh.sqlite.sqlitetest.word;

/**
 * Created by yezh on 2018/9/19 18:11.
 */

public class Person {
    private String name;
    private String pinyin;
    private String headerWord;

    public Person(String name){
        this.name = name;
//        this.pinyin = PinyinUtils.getPinYin(name);
        this.pinyin = PinyinUtil.getInstance().getPinyin(name);
//        this.headerWord = this.pinyin.substring(0, 1).toUpperCase();
        String head = pinyin.substring(0, 1).toUpperCase();
        if(head.charAt(0) < 65 || head.charAt(0) > 90){
            this.headerWord = "#";
        }else{
            this.headerWord = head;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getHeaderWord() {
        return headerWord;
    }

    public void setHeaderWord(String headerWord) {
        this.headerWord = headerWord;
    }
}
