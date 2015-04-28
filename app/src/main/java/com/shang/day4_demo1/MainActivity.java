package com.shang.day4_demo1;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends ActionBarActivity {
    //yyyyyyyyyyyyyyyyyyyyyyyy
    //xxxxxxxxxxxxxxxxxxxxxI
    private ArrayAdapter<String> adapter;
    private PullToRefreshListView lv;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.arg1)
            {
                case 0:
                    List<String>list1 =new ArrayList<String>();
                    for(int i=0; i<10;i++)
                    {
                        list1.add("你好"+i);
                    }
                    adapter.addAll(list1);

                    //调用刷新完成
                     lv.onRefreshComplete();
                     break;


                case 1 :
                    List<String>list2 =new ArrayList<String>();
                    for(int i=0; i<10;i++)
                    {
                        list2.add("你好"+i+page*10);
                    }
                   adapter.addAll(list2);
                    //调用刷新完成
                    lv.onRefreshComplete();
                    break;
            }


        }
    };
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         lv= (PullToRefreshListView) findViewById(R.id.refresh);

        final List<String> list =new ArrayList<String>();
        for(int i=1;i<50;i++)
        {
            list.add("第"+i+"条数据");
        }
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);

        lv.setAdapter(adapter);
        lv.setMode(PullToRefreshBase.Mode.BOTH);//设置模式。  上下都可以拉
        // 一端拉动模式下，用onRefreshListener
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {

             /*   List<String>list1 =new ArrayList<String>();
                for(int i=0; i<10;i++)
                {
                    list1.add("你好"+i);
                }
                 list.addAll(list1);
                adapter.notifyDataSetChanged();
*/
            }
        });
        //两端都可以拉动 用onRefreshListener2
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
               //下拉
               adapter.clear();
             Message msg=handler.obtainMessage();
                msg.arg1=0;
                page=0;
                handler.sendMessageDelayed(msg,1000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                   page++;
                handler.sendEmptyMessageDelayed(1,1000);


            }
        });
    }




}
