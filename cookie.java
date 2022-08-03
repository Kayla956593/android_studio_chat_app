package com.koddev.chatapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class cookie extends AppCompatActivity implements MyRecyclerViewAdapter.OnItemClickHandler {
    private Button done_b; //chat
    private RecyclerView rvRecyclerView;
    private MyRecyclerViewAdapter recycle_adapter;

    private List<writing> list;
    Boolean firstTime = true;

    //清
    String inputTime, inputClass;
    /*private DB sqlDataBaseHelper,sqlDataBaseHelper2;
    private static final String DataBaseName = "DataBaseIt";
    private static final int DataBaseVersion = 1;
    private static SQLiteDatabase db,db2;*/
    public static String[] poster_name,post_type,post_title,post_text;
    private static String[] classType={"工作職缺","程式問題","競賽資訊"};
    //private static String[] classType={"久->近","近->久","工作職缺","程式問題","競賽資訊"};
    private int classIndex=0,classtime = 0 ;
    String post_ids;
    //end

    private FirebaseAuth mAuth;
    private DatabaseReference PostDB = FirebaseDatabase.getInstance().getReference("Posts");
    private DatabaseReference UserDB = FirebaseDatabase.getInstance().getReference("Users");
    final String[] f_id = new String[1];
    public int public_index=0,public_time=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookie);
//        getSupportActionBar().hide();

        MyRecyclerViewAdapter.OnItemClickHandler b=this::onItemClick;
        Spinner spinner2=findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.time,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                inputTime=adapterView.getSelectedItem().toString();
                if(inputTime.equals("遠->近")){
                    classtime = 0 ;

                }
                else{
                    classtime = 1;
                }
                initView();
                initData(classIndex,classtime);
                recycle_adapter = new MyRecyclerViewAdapter(list,b);
                rvRecyclerView.setAdapter(recycle_adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //返回
        done_b = (Button) findViewById(R.id.back_btn);
        done_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_f();
            }
        });


        rvRecyclerView = (RecyclerView) findViewById(R.id.forumRecyclerView);
        list = new ArrayList<>();
        /*initView();
        initData(classIndex,classtime);//資料放進list*/

        /*rvRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));//控制佈局為LinearLayout或者是GridView或者是瀑布流佈局
        recycle_adapter = new MyRecyclerViewAdapter(list,this);
        rvRecyclerView.setAdapter(recycle_adapter);*/

        //清 start
        MyRecyclerViewAdapter.OnItemClickHandler a=this::onItemClick;

        Spinner spinner=(Spinner)findViewById(R.id.spinner);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this
                ,R.array.date,android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (firstTime){firstTime = false;}
                else{
                    //Toast.makeText(view.getContext(),parent.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                    inputClass=parent.getSelectedItem().toString();

                    String[] classType={"工作職缺","程式問題","競賽資訊"};
                    if(inputClass.equals(classType[0])){
                        classIndex=1;
                        Toast.makeText(view.getContext(),"有進來工作職缺",Toast.LENGTH_SHORT).show();
                    }
                    else if(inputClass.equals(classType[1])){
                        classIndex=2;
                        Toast.makeText(view.getContext(),"有進來程式問題",Toast.LENGTH_SHORT).show();
                    }
                    else if(inputClass.equals(classType[2])){
                        classIndex=3;
                        Toast.makeText(view.getContext(),"有進來競賽資訊",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        classIndex=0;
                    }

                    initView();
                    initData(classIndex,classtime);
                    recycle_adapter = new MyRecyclerViewAdapter(list,a);
                    rvRecyclerView.setAdapter(recycle_adapter);

                    // switch_spinner(inputClass);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }
/*
    private void switch_spinner(String spin) {
        //String[] classType={"工作職缺","程式問題","競賽資訊"};
        if(inputClass==classType[0]){ classIndex=0;}
        else if(inputClass==classType[1]){ classIndex=1;}
        else if(inputClass==classType[2]){ classIndex=2;}

        initView();
        initData(classIndex);
        //adapter = new MyRecyclerViewAdapter(list,a);
        rvRecyclerView.setAdapter(adapter);
    }*/




    private void initView() {
        rvRecyclerView = (RecyclerView) findViewById(R.id.cookieRecyclerView);
        if(list.size()>0)list.clear();

        if(rvRecyclerView.getChildCount()>0){
            rvRecyclerView.removeAllViews();
        }
    }
    private void open_f() {

        Intent intent = new Intent(this, forum.class);
        startActivity(intent);
    }


    @SuppressLint("Range")
    private void initData(int index,int time) { //put the all context
        list = new ArrayList<>();

        //xuan delete
        /*sqlDataBaseHelper = new DB(this, DataBaseName, null, DataBaseVersion, "Post");
        db = sqlDataBaseHelper.getWritableDatabase(); // 開啟"Post"資料庫

        sqlDataBaseHelper2 = new DB(this, DataBaseName, null, DataBaseVersion, "Users");
        db2 = sqlDataBaseHelper2.getWritableDatabase(); // 開啟資料庫

        //將user favorite的post_id抓出來
        String f_id =null;
        String in = "user_current = 'current'" ;

        f_id = select("Users","user_favorite",1,in)[0][0];
        Cursor c = db.rawQuery("SELECT * FROM " + "Post",null);
        poster_name = new String[c.getCount()];
        post_type = new String[c.getCount()];
        post_title = new String[c.getCount()];
        post_text = new String[c.getCount()];
        c.moveToFirst();

        for(int i=0;i<c.getCount();i++){
            poster_name[i] = c.getString(1);
            post_type[i] = c.getString(2);
            post_title[i] = c.getString(3);
            post_text[i] = c.getString(4);
            //Toast.makeText(forum.this,poster_name[i],Toast.LENGTH_SHORT).show();
            c.moveToNext();
            //Toast.makeText(forum.this,post_type[i],Toast.LENGTH_SHORT).show();
        }


    //遠->近
    if(time == 0) {
        if (index == 0) {
            if (f_id != null) {
                String[] split_line = f_id.split(",");
                for (int i = 0; i < split_line.length; i++) {
                    int k = Integer.valueOf(split_line[i]) - 1;
                    list.add(new writing(poster_name[k], post_title[k], post_text[k], post_type[k]));
                }
            }
        } else {
            if (f_id != null) {
                String[] split_line = f_id.split(",");
                for (int i = 0; i < split_line.length; i++) {
                    int k = Integer.valueOf(split_line[i]) - 1;
                    if (post_type[k] != null && post_type[k].equals(classType[index - 1])) {
                        list.add(new writing(poster_name[k], post_title[k], post_text[k], post_type[k]));
                    }
                    c.moveToNext();
                }
            }
        }
    }
    // 近->遠
    else{
        if (index == 0) {
            if (f_id != null) {
                String[] split_line = f_id.split(",");
                for (int i = split_line.length - 1; i >=0 ; i--) {
                    int k = Integer.valueOf(split_line[i]) - 1;
                    list.add(new writing(poster_name[k], post_title[k], post_text[k], post_type[k]));
                }
            }
        } else {
            if (f_id != null) {
                String[] split_line = f_id.split(",");
                for (int i = split_line.length -1 ; i >= 0 ; i--) {
                    int k = Integer.valueOf(split_line[i]) - 1;
                    if (post_type[k] != null && post_type[k].equals(classType[index - 1])) {
                        list.add(new writing(poster_name[k], post_title[k], post_text[k], post_type[k]));
                    }
                    c.moveToNext();
                }
            }
        }

    }*/
        //xuan add
        //get userid
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        assert firebaseUser != null;
        String userid = firebaseUser.getUid();

        public_index=index;
        public_time=time;

        Query query2 = UserDB
                .orderByChild("id")
                .equalTo(userid);
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    f_id[0] = user.getFavorite();
                    sert();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override //點擊後看你想要什麼
    public void onItemClick(View v, writing pos) {

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

    //xuan add
    private void sert(){
        PostDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String []poster_name = new String[(int)dataSnapshot.getChildrenCount()];
                final String []post_type = new String[(int)dataSnapshot.getChildrenCount()];
                final String []post_title = new String[(int)dataSnapshot.getChildrenCount()];
                final String []post_text = new String[(int)dataSnapshot.getChildrenCount()];
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Posts post = snapshot.getValue(Posts.class);
                    poster_name[Integer.valueOf(post.getId())]=post.getPoster_name();
                    post_type[Integer.valueOf(post.getId())]=post.getPost_type();
                    post_title[Integer.valueOf(post.getId())]=post.getPost_title();
                    post_text[Integer.valueOf(post.getId())]=post.getPost_text();
                    //Toast.makeText(cookie.this,post_title[Integer.valueOf(post.getId())],Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(cookie.this,f_id[0],Toast.LENGTH_SHORT).show();
                if(public_time == 0) {
                    if (public_index == 0) {
                        if (f_id[0] != null) {
                            String[] split_line = f_id[0].split(",");

                            for (int i = 1; i < split_line.length; i++) {

                                //int k = Integer.valueOf(split_line[i]) ;
                                //清
                                int k =Integer.parseInt(split_line[i].trim().equals("")?"0":split_line[i].trim());

                                list.add(new writing(poster_name[k], post_title[k], post_text[k], post_type[k]));
                                Toast.makeText(cookie.this,post_title[k],Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        if (f_id[0] != null) {
                            String[] split_line = f_id[0].split(",");
                            for (int i = 1; i < split_line.length; i++) {

                                //int k = Integer.valueOf(split_line[i]) ;
                                //清
                                int k =Integer.parseInt(split_line[i].trim().equals("")?"0":split_line[i].trim());

                                if (post_type[k] != null && post_type[k].equals(classType[public_index - 1])) {
                                    list.add(new writing(poster_name[k], post_title[k], post_text[k], post_type[k]));
                                    Toast.makeText(cookie.this,post_title[k],Toast.LENGTH_SHORT).show();
                                }
                                //c.moveToNext();
                            }
                        }
                    }
                }
                // 近->遠
                else{
                    if (public_index == 0) {
                        if (f_id[0] != null) {
                            String[] split_line = f_id[0].split(",");
                            for (int i = split_line.length - 1; i >0 ; i--) {

                                //int k = Integer.valueOf(split_line[i]) ;
                                //清
                                int k =Integer.parseInt(split_line[i].trim().equals("")?"0":split_line[i].trim());

                                list.add(new writing(poster_name[k], post_title[k], post_text[k], post_type[k]));
                            }
                        }
                    } else {
                        if (f_id[0] != null) {
                            String[] split_line = f_id[0].split(",");
                            for (int i = split_line.length -1 ; i > 0 ; i--) {

                                //int k = Integer.valueOf(split_line[i]) ;
                                //清
                                int k =Integer.parseInt(split_line[i].trim().equals("")?"0":split_line[i].trim());

                                if (post_type[k] != null && post_type[k].equals(classType[public_index - 1])) {
                                    list.add(new writing(poster_name[k], post_title[k], post_text[k], post_type[k]));
                                }
                                //c.moveToNext();
                            }
                        }
                    }

                }
                //call list function
                adapter_list();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void adapter_list(){
        rvRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));//控制佈局為LinearLayout或者是GridView或者是瀑布流佈局
        recycle_adapter = new MyRecyclerViewAdapter(list,this);
        rvRecyclerView.setAdapter(recycle_adapter);
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
    /*
    //清 copy from forum.java

    *///end
}