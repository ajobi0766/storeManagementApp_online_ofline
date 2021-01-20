package com.appix.storemangaementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity{

    SQLiteDatabaseHandler db;
    Button btn_add,btn_delete;
    EditText ed_name,ed_scale,ed_price,ed_totalquantity,ed_qeematFrokht;
    String name,scale,price,total_quantity,qeematfrokht;
    float total_price;
    TextView tv_totalprice;
    String[] country = { "  KG  ", "  DOZEN  ",};

    int  id;
    Float total_quantity_;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<Store> arrayList_online;
    ArrayList<Store> arrayList_offline;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().hide();



        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");
        myRef.keepSynced(true);
        Log.d("",""+ShowItems.arrayList_online);
        btn_add=(Button)findViewById(R.id.btn_add);
        btn_delete=(Button)findViewById(R.id.btn_delete);
        ed_name=(EditText)findViewById(R.id.item_name);
        ed_scale=(EditText)findViewById(R.id.item_scale);
        ed_price=(EditText)findViewById(R.id.item_price);
        ed_totalquantity=(EditText)findViewById(R.id.total_quantity);
        ed_qeematFrokht=(EditText)findViewById(R.id.qeemat_farokht);

        tv_totalprice=(TextView)findViewById(R.id.tv_totalprice);

        if(getIntent().getExtras()!=null){

            id=getIntent().getIntExtra("id",-5);
            name=getIntent().getStringExtra("name");
            scale=getIntent().getStringExtra("scale");
            price=getIntent().getStringExtra("price");
            total_quantity=getIntent().getStringExtra("quantity");
            qeematfrokht=getIntent().getStringExtra("qeematfrokht");
            total_price=getIntent().getFloatExtra("totalprice",-0.2f);

            Log.d("",""+total_price);
            ed_name.setText(name);
            ed_scale.setText(scale);
            ed_price.setText(price);
            ed_totalquantity.setText(total_quantity);
            ed_qeematFrokht.setText(qeematfrokht);
            tv_totalprice.setText(""+total_price);

            btn_add.setText("تبدیل کریں");
            btn_delete.setVisibility(View.VISIBLE);
        }



        db= new SQLiteDatabaseHandler(this);


         firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null) {


            myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    arrayList_online = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        Store store = snapshot.getValue(Store.class);
                        arrayList_online.add(store);

                    }

                }

                // }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        if(!TextUtils.isEmpty(ed_name.getText().toString())){
            ed_name.setImeOptions(EditorInfo.IME_ACTION_DONE);
        }

        if(!TextUtils.isEmpty(ed_scale.getText().toString())){
            ed_scale.setImeOptions(EditorInfo.IME_ACTION_DONE);
        }
        if(!TextUtils.isEmpty(ed_price.getText().toString())){
            ed_price.setImeOptions(EditorInfo.IME_ACTION_DONE);
        }
        if(!TextUtils.isEmpty(ed_totalquantity.getText().toString())){
            ed_totalquantity.setImeOptions(EditorInfo.IME_ACTION_DONE);
        }
        if(!TextUtils.isEmpty(ed_qeematFrokht.getText().toString())){
            ed_qeematFrokht.setImeOptions(EditorInfo.IME_ACTION_DONE);
        }
        ed_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Toast.makeText(AddActivity.this, "after del", Toast.LENGTH_SHORT).show();
                String name = ed_name.getText().toString();
                if(!TextUtils.isEmpty(name)) {
                    ed_name.setImeOptions(EditorInfo.IME_ACTION_DONE);
                }


            }
        });
        ed_scale.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String sc = ed_scale.getText().toString();
                if(!TextUtils.isEmpty(sc)) {
                    ed_scale.setImeOptions(EditorInfo.IME_ACTION_DONE);
                }


            }
        });
        ed_qeematFrokht.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String qee = ed_qeematFrokht.getText().toString();
                if(!TextUtils.isEmpty(qee)) {
                    ed_qeematFrokht.setImeOptions(EditorInfo.IME_ACTION_DONE);
                }


            }
        });



        ed_totalquantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(ed_price.getText().toString().equals("")){
                    ed_price.setError("enter price before get total");
                    return;
                }
                float unit_price= Float.parseFloat(ed_price.getText().toString());

                String quantity= ed_totalquantity.getText().toString();
                if(TextUtils.isEmpty(quantity)){
                    return;
                }
                 total_quantity_= Float.valueOf(quantity);

                Log.d("",""+unit_price);
                total_price=total_quantity_*unit_price;

                tv_totalprice.setText(""+total_price);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Toast.makeText(AddActivity.this, "after del", Toast.LENGTH_SHORT).show();
                String quantity= ed_totalquantity.getText().toString();
                if(quantity.equals("")){
                    tv_totalprice.setText("");
                }
                if(!TextUtils.isEmpty(quantity)) {
                    ed_totalquantity.setImeOptions(EditorInfo.IME_ACTION_DONE);
                }


            }
        });

        ed_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String price_= ed_price.getText().toString();


                String quantity= ed_totalquantity.getText().toString();
                if(TextUtils.isEmpty(price_)){
                    return;
                }
                if(TextUtils.isEmpty(quantity)){
                    return;
                }
                total_quantity_= Float.valueOf(quantity);
                float unit_price= Float.parseFloat(price_);
                Log.d("",""+unit_price);

                total_price=total_quantity_*unit_price;

                tv_totalprice.setText(""+total_price);

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String quantity_price= ed_price.getText().toString();
                if(quantity_price.equals("")){
                    tv_totalprice.setText("");
                }

                if(!TextUtils.isEmpty(quantity_price)) {
                    ed_price.setImeOptions(EditorInfo.IME_ACTION_DONE);
                }



            }
        });

//        ed_name.setOnKeyListener(new View.OnKeyListener() {
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
////                    Toast.makeText(HelloFormStuff.this, edittext.getText(), Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//                return false;
//            }
//        });



         btn_add.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            name = ed_name.getText().toString();
                                            scale = ed_scale.getText().toString();
                                            price = ed_price.getText().toString();
                                            total_quantity = ed_totalquantity.getText().toString();
                                            qeematfrokht = ed_qeematFrokht.getText().toString();

                                            if (TextUtils.isEmpty(name)) {
                                                ed_name.setError("نام درج کریں");
                                                return;
                                            }
                                            if (TextUtils.isEmpty(scale)) {
                                                ed_scale.setError("ناپنے کا پیمانہ");
                                                return;
                                            }
                                            if (TextUtils.isEmpty(price)) {
                                                ed_price.setError("قیمت درج کریں");
                                                return;
                                            }
                                            if (TextUtils.isEmpty(total_quantity)) {
                                                ed_totalquantity.setError("کل قیمت درج کریں");
                                                return;
                                            }
                                            if (TextUtils.isEmpty(qeematfrokht)) {
                                                ed_qeematFrokht.setError("قیمت فروخت");
                                                return;
                                            }
//                 float unit_price= Float.parseFloat(price);
//
//                 float quantity= Float.parseFloat(total_quantity);
//
//                 Log.d("",""+unit_price);
//
//                 total_price=quantity*unit_price;
//
//                 tv_totalprice.setText(""+total_price);

                                            if (btn_add.getText().toString().equals("تبدیل کریں")) {

                                                Store store = new Store(id,name, scale, price, total_quantity, total_price,qeematfrokht);

                                                Log.d("", "" + store);
                                                db.updateCountry(store);
                                          //      myRef.child(id).setValue(store);
                                                Toast.makeText(AddActivity.this, "کامیابی کی ساتھ تبدیل ہو گیا...", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(AddActivity.this, ShowItems.class);
                                                startActivity(intent);
                                                finish();
                                            } else {

                                                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                                if (firebaseUser != null) {
                                                    arrayList_offline = new ArrayList<>();

                                                    arrayList_offline=db.getAllCountries();

                                                    if(arrayList_offline.size() == 0){

                                                        for (int z = 0; z < arrayList_online.size(); z++) {
                                                        Store store1 = arrayList_online.get(z);
                                                        db.addCountry(store1);
                                                    }

                                                    }

//
                                                        int next_id = arrayList_online.size() + 1;
                                                        Store store = new Store(next_id, name, scale, price, total_quantity, total_price,qeematfrokht);
                                                        Log.d("", "" + store);
                                                        db.addCountry(store);


                                                } else {
                                                    arrayList_offline = new ArrayList<>();
                                                    arrayList_offline = db.getAllCountries();
                                                    if(arrayList_offline.size() == 0){

                                                        Store store = new Store(1,name, scale, price, total_quantity, total_price,qeematfrokht);
                                                        Log.d("", "" + store);
                                                        db.addCountry(store);
                                                    }else {
                                                      int id = arrayList_offline.size()+1;

                                                        Store store = new Store(id, name, scale, price, total_quantity, total_price,qeematfrokht);
                                                        Log.d("", "" + store);
                                                        db.addCountry(store);
                                                    }


                                                }
                                                Toast.makeText(AddActivity.this, "کامیابی کے ساتھ شامل کیا گیا ..", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(AddActivity.this, ShowItems.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                    });





         btn_delete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 AlertDialog.Builder builder1 = new AlertDialog.Builder(AddActivity.this);
                 builder1.setMessage("کیا آپ واقعے ختم کرنا چاھتے ہیں..!");
                 builder1.setCancelable(true);
                 builder1.setPositiveButton(
                         "جی ہاں",
                         new DialogInterface.OnClickListener() {
                             public void onClick(DialogInterface dialog, int id_) {
                                 if(id>0){
                                    // float t_price= Float.parseFloat(tv_totalprice.getText().toString());
                                    // Store store = new Store(id,ed_name.getText().toString(), scale, ed_price.getText().toString(), ed_totalquantity.getText().toString(),total_price );
                                     Store store = new Store(id,ed_name.getText().toString(), scale, ed_price.getText().toString(), ed_totalquantity.getText().toString(),total_price,ed_qeematFrokht.getText().toString());
                                     db.deleteCountry(store);
                                     Log.d("",""+store);
                                     if(firebaseUser!=null) {

                                         myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("item" + id).removeValue();
                                     }

                                     Toast.makeText(AddActivity.this, "کامیابی کے ساتھ مٹا دیا گیا ..", Toast.LENGTH_SHORT).show();
                                     Intent intent=new Intent(AddActivity.this,ShowItems.class);
                                     startActivity(intent);
                                     finish();
                                 }
                                 dialog.cancel();
                             }
                         });

                 builder1.setNegativeButton(
                         "نہیں",
                         new DialogInterface.OnClickListener() {
                             public void onClick(DialogInterface dialog, int id) {
                                 dialog.cancel();
                             }
                         });
                 builder1.show();
//                 AlertDialog alert11 = builder1.create();
//                 alert11.show();
                 
                 

             }
         });

         }

    }





