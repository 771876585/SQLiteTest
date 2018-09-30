package com.yezh.sqlite.sqlitetest.word;

/**
 * Created by yezh on 2018/9/30 11:39.
 */

public class Item {
    public static final int ITEM = 0; //判断是否是普通item
    public static final int SECTION = 1; //判断是否需要置顶悬停的item
    public final int type;//外部传入的类型，ITEM或者SECTION
    public final Person person;//外部传入的数据，
    public String sectionPosition;//头标记，一般用父数据的id标记
    public String listPosition;//集合标记，一般用自身的id标记

    public Item(int type, Person person){
        this.type = type;
        this.person = person;
    }

    public Person getPerson(){
        return person;
    }
}
