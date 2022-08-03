package com.koddev.chatapp;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.koddev.chatapp.Model.User;

public class write extends AppCompatActivity {

    private Button back_b; //chat
    private Button done_b; //chat
    private static SQLiteDatabase db, db2;
    /*private DB sqlDataBaseHelper, sqlDataBaseHelper2;
    private static final String DataBaseName = "DataBaseIt";
    private static final int DataBaseVersion = 1;*/
    TextView userName, userType;
    EditText title,context;
    String inputTitle, inputContext, inputClass, inputUser;
    Spinner forumClass;
    Boolean firstTime = true;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Posts");
    Posts post = new Posts();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        //getSupportActionBar().hide();

        userName=(TextView)findViewById(R.id.userName);
        userName=(TextView)findViewById(R.id.userName);

        inputUser=userName.getText().toString();
        TextView userjob = (TextView)findViewById(R.id.userType);

        //xuan delete
        /*sqlDataBaseHelper = new DB(this, DataBaseName, null, DataBaseVersion, "Users");
        db = sqlDataBaseHelper.getWritableDatabase(); // 開啟資料庫
        Cursor c2 = db.rawQuery("SELECT * FROM " + "Users" + " WHERE user_current = '" + "current" + "'", null);
        c2.moveToFirst();
        userName.setText(c2.getString(1));*/
        //userType.setText(c2.getString(5));
        //xuan add
        //get userid
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        assert firebaseUser != null;
        final String userid = firebaseUser.getUid();
        //Toast.makeText(write.this,post.getId(),Toast.LENGTH_SHORT).show();

        //find poster_name
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);

                    if (user.getId().equals(userid)) {
                        userName.setText(user.getUsername());
                        post.setPoster_name(user.getUsername());
                        userjob.setText(user.getJob());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                post.setId(Integer.toString((int)dataSnapshot.getChildrenCount()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //xuan delete
        /*String inp = "user_current = 'current'" ;
        String usertype = select("Users","user_job",1,inp)[0][0];
        userjob.setText(usertype);*/
        //xuan add



        forumClass=(Spinner)findViewById(R.id.spinner);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this
                ,R.array.search,android.R.layout.simple_dropdown_item_1line);
        forumClass.setAdapter(adapter1);
        forumClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (firstTime){
                    firstTime = false;
                    inputClass = "工作職缺";
                }
                else{
                    Toast.makeText(view.getContext(),parent.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                    inputClass=parent.getSelectedItem().toString();


                    // System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    //  System.out.println(inputClass);
                    //  System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        title=(EditText) findViewById(R.id.tittle);
        context=(EditText) findViewById(R.id.text);
        /*sqlDataBaseHelper2 = new DB(this, DataBaseName, null, DataBaseVersion, "Post");
        db2 = sqlDataBaseHelper2.getWritableDatabase(); // 開啟資料庫*/
        /*Cursor c = db2.rawQuery("SELECT * FROM " + "Post" , null);
        c.moveToFirst();*/

        back_b = (Button) findViewById(R.id.back_btn);
        back_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_f();
            }
        });
        done_b = (Button) findViewById(R.id.done_btn);
        done_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputTitle=title.getText().toString();
                inputContext=context.getText().toString();


                /*Cursor c = db.rawQuery("SELECT * FROM " + "Users" + " WHERE user_current = '" + "current" + "'", null);
                c.moveToFirst();*/
                /*ContentValues contentValues = new ContentValues();
                contentValues.put("poster_name",inputUser);
                contentValues.put("post_type",inputClass);
                contentValues.put("post_title",inputTitle);
                contentValues.put("post_text",inputContext);
                db.insert("Post",null,contentValues);*/
                //xuan delete
                /*String [] insert_key={"poster_name","post_type","post_title","post_text"};
                String [] insert_value={inputUser,inputClass,inputTitle,inputContext};
                insert("Post",insert_key,insert_value);*/
                //xuan add
                post.setPost_type(inputClass);
                post.setPost_title(inputTitle);
                post.setPost_text(inputContext);
                mDatabase.child(post.getId()).setValue(post);


                Toast.makeText(view.getContext(),"add a new page",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), forum.class);
                startActivity(intent);

            }
        });
    }
    private void open_f() {
        Intent intent = new Intent(this, forum.class);
        startActivity(intent);
    }

    /*public void insert(String DataBaseTable, String [] insert_key, String [] insert_value){
        ContentValues contentValues = new ContentValues();
        for(int i=0;i<insert_key.length;i++){
            contentValues.put(insert_key[i],insert_value[i]);
        }
        db.insert(DataBaseTable,null,contentValues);
    }

    public void update(String DataBaseTable, String [] update_key, String [] update_value, Integer update_id){
        ContentValues contentValues = new ContentValues();
        for(int i=0;i<update_key.length;i++){
            contentValues.put(update_key[i],update_value[i]);
        }
        db.update(DataBaseTable,contentValues,"_id="+update_id,null);
    }

    public String [][] select(String DataBaseTable, String select_key,Integer select_key_number,  String select_conditon){
        String[][] select_value;
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