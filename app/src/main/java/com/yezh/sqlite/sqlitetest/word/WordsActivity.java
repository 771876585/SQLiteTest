package com.yezh.sqlite.sqlitetest.word;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yezh.sqlite.sqlitetest.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class WordsActivity extends AppCompatActivity implements WordsView.onTouchChangedListener{

    private TextView tv_dialog;
    ListView listView;
    WordsView wordsView;
    List<Person> list;
    TextView comment_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        wordsView = (WordsView)findViewById(R.id.wordsView);
        listView = (ListView)findViewById(R.id.listview);
        wordsView.setOnTouchChangedListener(this);
        tv_dialog = (TextView)findViewById(R.id.tv_dialog);
        wordsView.setTextDialog(tv_dialog);
        comment_bar = (TextView)findViewById(R.id.comment_bar);
        initData();
        getWordsList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(WordsActivity.this, list.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                wordsView.setTouchIndex(list.get(firstVisibleItem).getHeaderWord());

                comment_bar.setText(list.get(firstVisibleItem).getHeaderWord());
            }
        });

        MyAdapter myAdapter = new MyAdapter(this, list);
        listView.setAdapter(myAdapter);
    }

    private void getWordsList(){
        List<String> wordsList = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            wordsList.add(list.get(i).getHeaderWord());
        }

        Set set = new LinkedHashSet();
        set.addAll(wordsList);
        wordsList.clear();
        wordsList.addAll(set);

        wordsView.setWords(wordsList);

    }

    private void initData(){
        list = new ArrayList<>();
        list.add(new Person("大卫"));
        list.add(new Person("艾哎"));
        list.add(new Person("123454"));
        list.add(new Person("张三"));
        list.add(new Person("你好1"));
        list.add(new Person("你好2"));
        list.add(new Person("你好3"));
        list.add(new Person("你好4"));
        list.add(new Person("你好5"));
        list.add(new Person("你好6"));
        list.add(new Person("你好7"));
        list.add(new Person("你好8"));
        list.add(new Person("你好9"));
        list.add(new Person("你好11"));
        list.add(new Person("你好22"));
        list.add(new Person("你好33"));
        list.add(new Person("你好44"));
        list.add(new Person("你好55"));
        list.add(new Person("你好66"));
        list.add(new Person("你好77"));
        list.add(new Person("你好88"));
        list.add(new Person("你好99"));
        list.add(new Person("你好00"));
        list.add(new Person("你好123"));
        list.add(new Person("你好234"));
        list.add(new Person("你好345"));
        list.add(new Person("你好456"));
        list.add(new Person("你好567"));
        list.add(new Person("张三1"));
        list.add(new Person("张三2"));
        list.add(new Person("张三3"));
        list.add(new Person("张三4"));
        list.add(new Person("张三5"));
        list.add(new Person("张三6"));
        list.add(new Person("张三7"));
        list.add(new Person("张三8"));
        list.add(new Person("张三9"));
        list.add(new Person("张三0"));
        list.add(new Person("张三11"));
        list.add(new Person("张三12"));
        list.add(new Person("张三23"));
        list.add(new Person("张三34"));
        list.add(new Person("张三22"));
        list.add(new Person("张三33"));
        list.add(new Person("张三45"));
        list.add(new Person("张三44"));
        list.add(new Person("张三55"));
        list.add(new Person("张三56"));
        list.add(new Person("张三66"));
        list.add(new Person("张三67"));
        list.add(new Person("张三77"));
        list.add(new Person("张三78"));
        list.add(new Person("张三88"));
        list.add(new Person("张三89"));
        list.add(new Person("张三99"));
        list.add(new Person("张三90"));
        list.add(new Person("张三123"));
        list.add(new Person("张三234"));
        list.add(new Person("12345"));
        list.add(new Person("qiu"));
        list.add(new Person("mmm"));
        list.add(new Person("tttt"));
        list.add(new Person("艾"));


        Collections.sort(list, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                //根据拼音进行排序
                // 获取ascii值
                int lhs_ascii = o1.getHeaderWord().charAt(0);
                int rhs_ascii = o2.getHeaderWord().charAt(0);
                // 判断若不是字母，则排在字母之后
                if (lhs_ascii < 65 || lhs_ascii > 90)
                    return 1;
                else if (rhs_ascii < 65 || rhs_ascii > 90)
                    return -1;
                else
                    return o1.getPinyin().compareTo(o2.getPinyin());

            }
        });
    }

    @Override
    public void touchChanged(String s) {
        tv_dialog.setText(s);
        updateListView(s);
    }

    private void updateListView(String words){
        for(int i = 0; i < list.size(); i++){
            String headerword = list.get(i).getHeaderWord();
            if(words.equals(headerword)){
                listView.setSelection(i);
                return;
            }
        }
    }
}
