package com.example.kevin.featurestudio13;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mListView;
    protected RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayoutManager = new LinearLayoutManager(this);
        mListView = (RecyclerView)findViewById(R.id.list_view);
        mListView.setLayoutManager(mLayoutManager);
        CustomeAdapter adapter = new CustomeAdapter(this);
        adapter.bindData(getData());
        mListView.setAdapter(adapter);


    }


    private ArrayList<String> getData(){
        ArrayList<String> data = new ArrayList<String>();
        for(int i = 0; i < 200;i++){
            data.add("text:"+i);
        }
        return data;
    }


}
