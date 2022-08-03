package com.koddev.chatapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//import android.support.annotation.NonNull;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import androidx.annotation.NonNull;
//import com.koddev.chatapp.Notifications.Token;

public class forum extends AppCompatActivity implements MyRecyclerViewAdapter.OnItemClickHandler {
    //private Button btn1;
    private ImageView btn2; //chat
    private ImageView  btn3; //profile
    private ImageView  btn4;//tmp

    private static String[] classType={"程式問題","工作職缺","競賽資訊"};
    private Button btn_work,btn_code,btn_contest,btn_all;
    private int classIndex=0;
    private Button write;
    private ImageView c; //cookie
    private Button addc; // add cookie
    private RecyclerView rvRecyclerView;
    private MyRecyclerViewAdapter adapter;
    private List<writing> list;
    /*private static final String DataBaseName = "DataBaseIt";
    private static final int DataBaseVersion = 1;
    private static SQLiteDatabase db;
    private DB sqlDataBaseHelper;*/
    public static String[] poster_name,post_type,post_title,post_text;

    private TabLayout out;
    private ViewPager page;
    private SearchView mSearchView;
    private MenuItem searchItem;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Posts");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        //getSupportActionBar().hide();

        /* out=findViewById(R.id.forumClass);
        page=findViewById(R.id.view);

        out.setupWithViewPager(page);

        vpAdapter v=new vpAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        v.addFragment(new one(),"全部");
        v.addFragment(new two(),"工作\n職缺");
        v.addFragment(new three(),"程式\n問題");
        v.addFragment(new four(),"競賽\n資訊");
        page.setAdapter(v);*/

        MyRecyclerViewAdapter.OnItemClickHandler a=this::onItemClick;

        btn_all=(Button)findViewById(R.id.one) ;
        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classIndex=0;
                initView();
                initData(classIndex);
                adapter = new MyRecyclerViewAdapter(list,a);
                rvRecyclerView.setAdapter(adapter);
            }
        });

        btn_work=(Button)findViewById(R.id.two) ;
        btn_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classIndex=1;
                initView();
                initData(classIndex);
                adapter = new MyRecyclerViewAdapter(list,a);
                rvRecyclerView.setAdapter(adapter);
            }
        });

        btn_code=(Button)findViewById(R.id.three) ;
        btn_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classIndex=2;
                initView();
                initData(classIndex);
                adapter = new MyRecyclerViewAdapter(list,a);
                rvRecyclerView.setAdapter(adapter);
            }
        });

        btn_contest=(Button)findViewById(R.id.four) ;
        btn_contest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classIndex=3;
                initView();
                initData(classIndex);
                adapter = new MyRecyclerViewAdapter(list,a);
                rvRecyclerView.setAdapter(adapter);

                //if(list.size()>0)list.clear();
                /*
                if(rvRecyclerView.getChildCount()>0){
                    rvRecyclerView.removeAllViews();
                }*/
                //Toast.makeText(forum.this,"in",Toast.LENGTH_SHORT).show();


            }
        });

        btn2 = (ImageView) findViewById(R.id.chat_b);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_c();
            }
        });/*
        btn3 = (ImageView) findViewById(R.id.profile_b);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  open_p();
            }
        });*/
        btn4 = (ImageView) findViewById(R.id.tmp_page);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_tmp();
            }
        });

        //進入"撰寫文章"頁面
       write = (Button) findViewById(R.id.write_b);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_w();
            }
        });

        // 進入"收藏"頁面
        c = (ImageView) findViewById(R.id.cookie);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_cookie();
            }
        });


        rvRecyclerView = (RecyclerView) findViewById(R.id.forumRecyclerView);
        list = new ArrayList<>();
        initView();
        initData(classIndex);//資料放進list

        //xuan delete
        /*rvRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));//控制佈局為LinearLayout或者是GridView或者是瀑布流佈局
        adapter = new MyRecyclerViewAdapter(list,this);
        rvRecyclerView.setAdapter(adapter);*/

        // 設定item及item中控制元件的點選事件
        //adapter.setOnItemClickListener(MyItemClickListener);

        /*
        mSearchView = (SearchView) findViewById(R.id.inputSearch);
        mSearchView.setQuery("",false);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });*/

        //xuan delete
        /*mSearchView = (SearchView) findViewById(R.id.inputSearch);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //adapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText.toString());
                //Toast.makeText(getBaseContext(),  newText, Toast.LENGTH_LONG).show();
                return true;
            }
        });*/

    }//look line ok

    private void initView() {


        //f(list.size()>0)list.clear();
        if(list.size()>0)list.clear();

        if(rvRecyclerView.getChildCount()>0){
            rvRecyclerView.removeAllViews();
        }

    }
    @SuppressLint("Range")
    private void initData(int index) { //put the all context
        list = new ArrayList<>();

        //xuan delete
        /*sqlDataBaseHelper = new DB(this, DataBaseName, null, DataBaseVersion, "Post");
        db = sqlDataBaseHelper.getWritableDatabase(); // 開啟資料庫

        Cursor c = db.rawQuery("SELECT * FROM " + "Post",null);
        poster_name = new String[c.getCount()];
        post_type = new String[c.getCount()];
        post_title = new String[c.getCount()];
        post_text = new String[c.getCount()];
        c.moveToFirst();

        //Toast.makeText(forum.this,c.getCount(),Toast.LENGTH_SHORT).show();

        if (index==0){
            for(int i=0;i<c.getCount();i++){
                poster_name[i] = c.getString(1);
                post_type[i] = c.getString(2);
                post_title[i] = c.getString(3);
                post_text[i] = c.getString(4);
                //Toast.makeText(forum.this,poster_name[i],Toast.LENGTH_SHORT).show();
                c.moveToNext();
                //Toast.makeText(forum.this,post_type[i],Toast.LENGTH_SHORT).show();
                list.add(new writing(poster_name[i],post_title[i],post_text[i],post_type[i]));
            }
        }

        else {
            //int value=0;
            String tmp;
            for(int i=0;i<c.getCount();i++){
                poster_name[i] = c.getString(1);
                post_type[i] = c.getString(2);
                post_title[i] = c.getString(3);
                post_text[i] = c.getString(4);
                //c.moveToNext();

                //tmp = post_type[i];
                //Toast.makeText(forum.this,tmp+" "+classType[index-1],Toast.LENGTH_SHORT).show();

                /*if(tmp == "程式問題" ){
                    //value++;
                    //Toast.makeText(forum.this,poster_name[i]+post_type[i]+post_title[i]+post_text[i],Toast.LENGTH_SHORT).show();
                    list.add(new writing(poster_name[i],post_title[i],post_text[i],post_type[i]));
                }*/

        //cursor.getString(0).equals(example)

                /*if(post_type[i]!=null && c.getString(2).equals(classType[index-1]) ){
                    //value++;
                    //Toast.makeText(forum.this,post_type[i],Toast.LENGTH_SHORT).show();
                    list.add(new writing(poster_name[i],post_title[i],post_text[i],post_type[i]));
                }
                c.moveToNext();

            }//可以耶//?? who are you?大美旅<3//我刷個牙//OK

        }*/

        //xuan add
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Posts post = snapshot.getValue(Posts.class);
                    if (index==0){
                        list.add(new writing(post.getPoster_name(),post.getPost_title(),post.getPost_text(),post.getPost_type()));
                    }
                    else {
                        String tmp;
                        if(post.getPost_type()!=null && post.getPost_type().equals(classType[index-1]) ){
                            list.add(new writing(post.getPoster_name(),post.getPost_title(),post.getPost_text(),post.getPost_type()));
                        }
                    }
                    //Toast.makeText(forum.this,Integer.toString(list.size()),Toast.LENGTH_SHORT).show();
                }
                adapter_list();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Toast.makeText(forum.this,Integer.toString(list.size()),Toast.LENGTH_SHORT).show();

        // Toast.makeText(forum.this,post_title[2],Toast.LENGTH_SHORT).show();
        /*
        SearchView login=(SearchView)findViewById(R.id.inputSearch);

        login.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {

                String s=login.getQuery().toString();
                login.setQuery(s,false);
                adapter.getFilter().filter(n);

            }

        });

        mSearchView = (SearchView) findViewById(R.id.searchView);
        mSearchView.setQuery(s,false);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });*/


    }

    //xuan add
    private void adapter_list() {
        rvRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));//控制佈局為LinearLayout或者是GridView或者是瀑布流佈局
        adapter = new MyRecyclerViewAdapter(list,this);
        rvRecyclerView.setAdapter(adapter);

        mSearchView = (SearchView) findViewById(R.id.inputSearch);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //adapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText.toString());
                //Toast.makeText(getBaseContext(),  newText, Toast.LENGTH_LONG).show();
                return true;
            }
        });

    }


    @Override //點擊後看你想要什麼
    public void onItemClick(View v, writing pos) {

        // Toast.makeText(v.getContext(),"i am clicked by u.222 " ,Toast.LENGTH_SHORT).show();
        //以下兩行是切換到POST
        Intent intent = new Intent(getApplicationContext(), Post.class);
        Bundle bundle = new Bundle();
        bundle.putString("Name",pos.getUserNmae());
        bundle.putString("Title",pos.getWritingTitle());
        bundle.putString("Context",pos.getWritingContext());
        bundle.putString("WritingType",pos.getWritingType());
        intent.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
        startActivity(intent);

    }

    private void open_tmp() {
        Intent intent = new Intent(this, tmp.class);
        startActivity(intent);
    }

    private void open_cookie() {
        Intent intent = new Intent(this, cookie.class);
        startActivity(intent);
    }


    private void open_c() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void open_p() {
        Intent intent = new Intent(this, Post.class);
        startActivity(intent);
    }

    private void open_w() {
        Intent intent = new Intent(this, write.class);
        startActivity(intent);
    }



    /*public String [][] select(String DataBaseTable, String select_key,Integer select_key_number,  String select_conditon){
        String[][] select_value;
        String [] key_Users={"user_name","user_mail","user_phone","user_address",
                "user_job","user_gender","user_current",
                "user_favorite","user_photo","user_background"};
        String [] key_Post={"poster_name","post_type","post_title","post_text","post_document","post_photo"};
        Cursor c;
        if(select_conditon != null){
            c = db.rawQuery("SELECT "+select_key+" FROM " + DataBaseTable +" WHERE "+ select_conditon ,null);
        }
        else{
            c = db.rawQuery("SELECT "+select_key+" FROM " + DataBaseTable,null);
        }
        c.moveToFirst();
        select_value = new String[c.getCount()][select_key_number];
        for(int i=0;i<c.getCount();i++){
            for(int j=0;j<select_key_number;j++){
                select_value[i][j] = c.getString(j);
            }
        }
        return select_value;
    }*/


}