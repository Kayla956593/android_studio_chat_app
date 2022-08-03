package com.koddev.chatapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;

//end

public class signin extends AppCompatActivity {
    private Button btn;
    RadioGroup r1,r2;
    RadioButton b1,b2; //position; gender

    FirebaseStorage storage;

    //xuan add begin
    private static final String DataBaseName = "DataBaseIt";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "Users";
    private SQLiteDatabase db;
    // private DB sqlDataBaseHelper;
    private String edit_user_mail,edit_user_password,edit_user_username,edit_user_phone,edit_user_address,edit_user_job,edit_user_gender;
    private EditText phone,address,job,gender;
    FirebaseAuth auth;
    DatabaseReference reference;
    FirebaseUser fuser;
    //FirebaseDatabase rootNode;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabase;
    //end
    public Uri selectedImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        //     getSupportActionBar().hide();

        //   name=(EditText) findViewById(R.id.name);
        //    mail=(EditText) findViewById(R.id.mail);
        phone=(EditText) findViewById(R.id.phone);
        address=(EditText) findViewById(R.id.address);
        job=(EditText) findViewById(R.id.phone);
        gender=(EditText) findViewById(R.id.address);
        auth = FirebaseAuth.getInstance();


        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                edit_user_mail= user.getMail();
                edit_user_password = user.getPassword();
                edit_user_username = user.getUsername();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //xuan add begin
        //  sqlDataBaseHelper = new DB(this,DataBaseName,null,DataBaseVersion,"Users");
        //  db = sqlDataBaseHelper.getWritableDatabase(); // 開啟資料庫

        mAuth = FirebaseAuth.getInstance();
        //end

        btn=(Button) findViewById(R.id.done_btn);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                edit_user_phone = phone.getText().toString();
                edit_user_address = address.getText().toString();

                r1=findViewById(R.id.rg);
                switch(r1.getCheckedRadioButtonId()){
                    case R.id.boss:
                        edit_user_job="老闆";
                        break;
                    case R.id.stu:
                        edit_user_job="學生";
                        break;
                    case R.id.eng:
                        edit_user_job="工程師";
                        break;
                }
                r2=findViewById(R.id.rg2);
                switch(r2.getCheckedRadioButtonId()){
                    case R.id.male:
                        edit_user_gender="男";
                        break;
                    case R.id.female:
                        edit_user_gender="女";
                        break;
                    case R.id.other:
                        edit_user_gender="其他";
                        break;
                }


                /*if(SqlAccountCheck(edit_user_name) == -1){
                    Toast.makeText(view.getContext(),"wrong!!",Toast.LENGTH_LONG).show();
                }
                else if(SqlAccountCheck(edit_user_name) > 0){
                    Toast.makeText(view.getContext(),"輸入帳號已存在!!",Toast.LENGTH_LONG).show();
                    Cursor c2 = db.rawQuery("SELECT * FROM " + "Users" + " WHERE user_name = '" + edit_user_name + "'", null);
                    c2.moveToFirst();
                    ContentValues values = new ContentValues();
                    values.put("user_current", "current");
                    db.update("Users", values, "_id = " + c2.getString(0) , null);
                    String conditon="user_name = "+edit_user_name;
                    Integer id = Integer.valueOf(select("Users","_id",1,conditon)[0][0]);
                    String [] update_key={"user_mail","user_phone","user_address","user_job","user_gender","user_current"};
                    String [] update_value={edit_user_mail,edit_user_phone,edit_user_address,
                            edit_user_job,edit_user_gender, "current"};

                    update("Users",update_key,update_value,id);

                }
                else {//88
                    long id;
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("user_name",edit_user_name);
                //    contentValues.put("user_mail",edit_user_mail);
                    contentValues.put("user_phone",edit_user_phone);
                    contentValues.put("user_address",edit_user_address);
                    contentValues.put("user_job",edit_user_job);
                    contentValues.put("user_gender",edit_user_gender);
                    contentValues.put("user_current","current");
                    id = db.insert("Users",null,contentValues);
                    Toast.makeText(view.getContext(),"帳號新增成功:"+id,Toast.LENGTH_LONG).show();
                    String [] insert_key={"user_name","user_mail","user_phone","user_address","user_job","user_gender","user_current"};
                    String [] insert_value={edit_user_name,edit_user_mail,edit_user_phone,edit_user_address,
                                edit_user_job,edit_user_gender, "current"};
                    insert("Users",insert_key,insert_value);

                }*/

                /*mCurrentUser = mAuth.getCurrentUser();
                //String user_id = mAuth.getCurrentUser().getUid();
                //Get all the values
                write_New_Data("Users","3",edit_user_name, edit_user_mail, edit_user_phone, edit_user_address,
                             edit_user_job, edit_user_gender,null,null,null);*/
                //end
                //openChat();
                //Toast.makeText(view.getContext(), edit_user_mail, Toast.LENGTH_SHORT).show();
                //Toast.makeText(view.getContext(), edit_user_password, Toast.LENGTH_SHORT).show();
                register();
            }
        });
        r1=findViewById(R.id.rg);
        r2=findViewById(R.id.rg2);



    }




    private void register(){

        // Uri selectedImage;

        FirebaseUser firebaseUser = auth.getCurrentUser();
        assert firebaseUser != null;
        String userid = firebaseUser.getUid();

        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("id", userid);
        hashMap.put("username", edit_user_username);
        hashMap.put("mail",edit_user_mail);
        hashMap.put("password",edit_user_password);
        hashMap.put("imageURL", "default");
        hashMap.put("status", "offline");
        hashMap.put("search", edit_user_username.toLowerCase());
        hashMap.put("job", edit_user_job);
        hashMap.put("phone", edit_user_phone);
        hashMap.put("address",edit_user_address);
        hashMap.put("gender",edit_user_gender);
        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Intent intent=new Intent(signin.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                }

            }
        });

//清 大頭貼
/*
        if(selectedImage != null) {
            StorageReference reference = storage.getReference().child("Profiles").child(auth.getUid());
            reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageUrl = uri.toString();

                                String uid = auth.getUid();
                                String phone = auth.getCurrentUser().getPhoneNumber();
                                // String name = binding.nameBox.getText().toString();

                                //  User user = new User(uid, name, phone, imageUrl);

                                database.getReference()
                                        .child("users")
                                        .child(uid)
                                        .setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                dialog.dismiss();
                                                Intent intent = new Intent(SetupProfileActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                            }
                        });
                    }
                }
            });
        } else {
            String uid = auth.getUid();
            String phone = auth.getCurrentUser().getPhoneNumber();

            User user = new User(uid, name, phone, "No Image");

            database.getReference()
                    .child("users")
                    .child(uid)
                    .setValue(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            dialog.dismiss();
                            Intent intent = new Intent(SetupProfileActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
        }*/
    }


    public void position(View v){
        int id=r1.getCheckedRadioButtonId();
        b1=findViewById(id);
    }
    public void gender(View v){
        int id2=r2.getCheckedRadioButtonId();
        b2=findViewById(id2);
    }

    public void openChat(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}