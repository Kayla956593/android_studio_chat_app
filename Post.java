package com.koddev.chatapp;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

//import com.example.a1234.entities.writing;
//import com.koddev.chatapp.entities.writing;


public class Post extends AppCompatActivity {

    private Button back_b,favo;
    private TextView userName,userType,postType,postTittle,postContent,postid;

    private static final String DataBaseName = "DataBaseIt";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "Post";
    private SQLiteDatabase db,db2;
    //private DB sqlDataBaseHelper,sqlDataBaseHelper2;
    public static String[] poster_name,post_type,post_title,post_text;
    private List<writing> list;
    boolean sw = FALSE;
    String post_ids;
    final String[] f = new String[1];



    private FirebaseAuth mAuth;
    private DatabaseReference PostDB = FirebaseDatabase.getInstance().getReference("Posts");
    private DatabaseReference UserDB = FirebaseDatabase.getInstance().getReference("Users");
    Posts post = new Posts();
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
       // getSupportActionBar().hide();



        Bundle bundle = getIntent().getExtras();
        String Name = bundle.getString("Name");
        String Type = bundle.getString("WritingType");
        String Title = bundle.getString("Title");
        String Context = bundle.getString("Context");


        //這裡有用到 // post DB 去抓文章標題、內容...等 //user DB 去抓使用者名稱跟職位

        //xuan delete
        /*sqlDataBaseHelper = new DB(this,DataBaseName,null,DataBaseVersion,"Post");
        db = sqlDataBaseHelper.getWritableDatabase();
        sqlDataBaseHelper2 = new DB(this, DataBaseName, null, DataBaseVersion, "Users");
        db2 = sqlDataBaseHelper2.getWritableDatabase(); // 開啟資料庫


        String inp = "user_current = 'current'" ;
        String usertype = select("Users","user_job",1,inp)[0][0];*/

        userName = (TextView) findViewById(R.id.posterName);
        userType = (TextView) findViewById(R.id.posterType);
        postType = (TextView) findViewById(R.id.postType);
        postTittle = (TextView) findViewById(R.id.post_title);
        postContent=(TextView) findViewById(R.id.post_content);

        //xuan delete
        //userType.setText(usertype);
        //xuan add
        userType.setText("");

        userName.setText(Name);
        postType.setText(Type);
        postTittle.setText(Title);
        postContent.setText(Context);

        //xuan delete
        /*Cursor c = db.rawQuery("SELECT * FROM " + "Post",null);
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
        }*/

        // Toast.makeText(Post.this,post_title[2],Toast.LENGTH_SHORT).show();
        /*String input = "poster_name = "+Name+" AND "+"post_type = "+Type+" AND "+"post_title = "+Title+" AND "
                +"post_text = "+Context;*/
        // String a="Test";


        //Toast.makeText(Post.this,post_ids,Toast.LENGTH_SHORT).show();


        // 這裡有用到  // 抓取現在post的id 跟匯入user的favo 資訊，判斷文章是否有被典藏過(id比對)
        // setActivated set 餅乾初始的顏色 // 已蒐藏 red;未收藏 gray

        //xuan delete
        String input = "post_title = '"  + Title+ "'";
        //post_ids = select("Post","_id",1,input)[0][0];
        favo = (Button) findViewById(R.id.cookie_black);

        String in = "user_current = 'current'" ;
        //f[0] = select("Users","user_favorite",1,in)[0][0];

        //xuan add
        //get userid
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        assert firebaseUser != null;
        userid = firebaseUser.getUid();

        //set post_ids
        Query query = PostDB
                .orderByChild("post_title")
                .equalTo(Title);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Posts post2 = snapshot.getValue(Posts.class);
                    post.setId(post2.getId());
                    post_ids = post2.getId();
                    //Toast.makeText(Post.this,post.getId(),Toast.LENGTH_SHORT).show();
                    favorite();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Toast.makeText(Post.this,post.getId(),Toast.LENGTH_SHORT).show();



        //xuan delete
        /*if(f[0] !=null) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println(f[0]);
            System.out.println("=====================================================");
            String[] line = f[0].split(",");

            for (int i = 0; i < line.length; i++) {
                if (line[i].equals(post_ids)) {
                    favo.setActivated(true);
                    sw=TRUE;
                    break;
                }
            }
        }*/

        //這裡有用到 // 點擊收藏會進行的行為 // update user 的 favo
        favo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 /*用來清空favorite
                    String input = "user_current = 'current'" ;
                    Integer id = Integer.valueOf(select("Users","_id",1,input)[0][0]);
                    String [] update_key={"user_favorite"};
                    String [] update_value={null};
                    update("Users",update_key,update_value,id);*/

                if(sw==FALSE){
                    favo.setActivated(true);

                    final String[] favorite = new String[1];
                    //xuan delete
                    /*String newf = null;
                    String input = "user_current = 'current'" ;
                    favorite[0] = select("Users","user_favorite",1,input)[0][0];
                    Integer id = Integer.valueOf(select("Users","_id",1,input)[0][0]);
                    Integer pass = 0 ;

                    if(favorite[0] == null ){
                        newf = post_ids;
                    }
                    else {

                        newf = favorite[0] + "," + post_ids;

                    }
                    String [] update_key={"user_favorite"};
                    String [] update_value={newf};
                    update("Users",update_key,update_value,id);

                    //Toast.makeText(Post.this,newf,Toast.LENGTH_SHORT).show();
                    Toast.makeText(Post.this,"加入收藏",Toast.LENGTH_SHORT).show();
                    Toast.makeText(Post.this,newf,Toast.LENGTH_SHORT).show();
                    sw = TRUE;*/

                    //xuan add
                    Query query2 = UserDB
                            .orderByChild("id")
                            .equalTo(userid);
                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                User user = snapshot.getValue(User.class);
                                favorite[0] = user.getFavorite();
                                final String[] newf = new String[1];
                                Query query = PostDB
                                        .orderByChild("post_title")
                                        .equalTo(Title);
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            Posts post2 = snapshot.getValue(Posts.class);
                                            post_ids = post2.getId();
                                            if(favorite[0] == null ){
                                                newf[0] = "k,";
                                                newf[0] += post_ids;
                                            }
                                            else if(favorite[0]=="k,"){
                                                newf[0] = post_ids;
                                            }
                                            else {
                                                newf[0] = favorite[0] + "," + post_ids;
                                            }
                                        }
                                        HashMap hashMap = new HashMap();
                                        hashMap.put("favorite",newf[0]);
                                        UserDB.child(userid).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                            @Override
                                            public void onSuccess(Object o) {
                                                Toast.makeText(Post.this,"加入收藏",Toast.LENGTH_SHORT).show();
                                                Toast.makeText(Post.this,newf[0],Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    sw = TRUE;
                }
                else if(sw==TRUE){
                    favo.setActivated(false);
                    //xuan delete
                    /*String favorite,newf;
                    String input = "user_current = 'current'" ;
                    favorite = select("Users","user_favorite",1,input)[0][0];
                    Integer id = Integer.valueOf(select("Users","_id",1,input)[0][0]);
                    Integer pass = 0 ;
                    String[] split_line = favorite.split(",");
                    newf = null ;
                    // Toast.makeText(Post.this,post_ids,Toast.LENGTH_SHORT).show();

                    for(int i = 0 ; i < split_line.length;i++) {
                        if(split_line[i].equals(post_ids)){
                            continue;
                        }
                        else{
                            if(newf==null){
                                newf = split_line[i] + ",";
                            }
                            else {
                                if (i + 1 < split_line.length) {
                                    newf += split_line[i] + ",";
                                } else {
                                    newf += split_line[i];
                                }
                            }
                        }
                    }

                    String [] update_key={"user_favorite"};
                    String [] update_value={newf};
                    update("Users",update_key,update_value,id);
                    //Toast.makeText(Post.this,newf,Toast.LENGTH_SHORT).show();
                    Toast.makeText(Post.this,"移除收藏",Toast.LENGTH_SHORT).show();
                    Toast.makeText(Post.this,newf,Toast.LENGTH_SHORT).show();
                    sw = FALSE;*/

                    //xuan add
                    final String[] favorite = new String[1];
                    Query query2 = UserDB
                            .orderByChild("id")
                            .equalTo(userid);
                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                User user = snapshot.getValue(User.class);
                                favorite[0] = user.getFavorite();
                                final String[] newf = new String[1];
                                String[] split_line = favorite[0].split(",");

                                newf[0]=null;
                                Query query = PostDB
                                        .orderByChild("post_title")
                                        .equalTo(Title);
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            Posts post2 = snapshot.getValue(Posts.class);
                                            post_ids = post2.getId();


                                                for (int i = 0; i < split_line.length; i++) {
                                                    if (split_line[i].equals(post_ids)) {
                                                        continue;
                                                    } else {
                                                        if (newf[0] == null) {
                                                            if (i + 2 < split_line.length) {
                                                                newf[0] = "k,";
                                                            } else {
                                                                newf[0] = "k";
                                                            }
                                                        } else {
                                                            if (i + 1 < split_line.length) {
                                                                newf[0] += split_line[i] + ",";
                                                            } else {
                                                                newf[0] += split_line[i];
                                                            }
                                                        }
                                                    }
                                                }


                                        }
                                        HashMap hashMap = new HashMap();
                                        hashMap.put("favorite",newf[0]);
                                        UserDB.child(userid).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                            @Override
                                            public void onSuccess(Object o) {
                                                Toast.makeText(Post.this,"移除收藏",Toast.LENGTH_SHORT).show();
                                                Toast.makeText(Post.this,newf[0],Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    sw = FALSE;
                }
            }
        });

        back_b = (Button) findViewById(R.id.back_btn);
        back_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_f();
            }
        });
    }

    private void open_f() {
        Intent intent = new Intent(this, forum.class);
        startActivity(intent);
    }


    //xuan add
    private void favorite() {
        Query query2 = UserDB
                .orderByChild("id")
                .equalTo(userid);
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    f[0] = user.getFavorite();
                    if(f[0] !=null) {
                        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        System.out.println(f[0]);
                        System.out.println("=====================================================");
                        String[] line = f[0].split(",");

                        for (int i = 0; i < line.length; i++) {
                            if (line[i].equals(post_ids)) {
                                favo.setActivated(true);
                                sw=TRUE;
                                break;
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Toast.makeText(Post.this,post_ids,Toast.LENGTH_SHORT).show();
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
    }
    public void update(String DataBaseTable, String [] update_key, String [] update_value, Integer update_id){
        ContentValues contentValues = new ContentValues();
        for(int i=0;i<update_key.length;i++){
            contentValues.put(update_key[i],update_value[i]);
        }
        db.update(DataBaseTable,contentValues,"_id="+update_id,null);
    }*/

}