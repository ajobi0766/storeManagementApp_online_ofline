package com.appix.storemangaementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.appix.storemangaementapp.Model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowItems extends AppCompatActivity implements ListItemClickListener{
    RecyclerView recyclerView;
    ItemsAdapter adapter;

    FirebaseDatabase database;
    DatabaseReference myRef;
    SQLiteDatabaseHandler db;

    ArrayList<Store> arrayList_offline;
    public static ArrayList<Store> arrayList_online;
    ArrayList<String> arrayListStoreId;
    ProgressBar progress_show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items);

        getSupportActionBar().setTitle("تلاش کریں");
        db= new SQLiteDatabaseHandler(this);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");

        recyclerView = (RecyclerView) findViewById(R.id.rv_items);
        progress_show = (ProgressBar) findViewById(R.id.progress_show);
        getData();

    }

    private void getData() {
        arrayList_offline=new ArrayList<>();
        arrayList_online=new ArrayList<>();
        arrayListStoreId=new ArrayList<>();
        progress_show.setVisibility(View.VISIBLE);

        FirebaseUser userIdfirebase= FirebaseAuth.getInstance().getCurrentUser();

        Log.d("",""+userIdfirebase);
        if(userIdfirebase!=null){
            arrayList_offline =   db.getAllCountries();
            if(arrayList_offline.size()>0) {
                for (int i = 0; i < arrayList_offline.size(); i++) {
                    Store model = arrayList_offline.get(i);
                    myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("item" + arrayList_offline.get(i).id).setValue(model);
                    myRef.keepSynced(true);
                    adapter = new ItemsAdapter(getApplicationContext(), arrayList_offline, ShowItems.this);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(adapter);
                    progress_show.setVisibility(View.INVISIBLE);
                }
            }else {


                myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            Store store = snapshot.getValue(Store.class);
                            arrayList_online.add(store);

                        }
                        if(arrayList_offline.size() == 0){

                            for (int z = 0; z < arrayList_online.size(); z++) {

                                Store store1 = arrayList_online.get(z);

                                db.addCountry(store1);
                            }

                        }
                        adapter = new ItemsAdapter(getApplicationContext(), arrayList_online, ShowItems.this);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(adapter);
                        progress_show.setVisibility(View.INVISIBLE);
                    }

                    // }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }else {
            Log.d("",""+userIdfirebase);
            arrayList_offline=   db.getAllCountries();
            Log.d("",""+arrayList_offline);


            adapter = new ItemsAdapter(getApplicationContext(), arrayList_offline, ShowItems.this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(adapter);
            progress_show.setVisibility(View.INVISIBLE);

        }



//        if(arrayList.size()==0){
//            Toast.makeText(this, "Nothing is Stored...", Toast.LENGTH_SHORT).show();
//        }else{
//
//        adapter  = new ItemsAdapter(getApplicationContext(),arrayList,ShowItems.this);
//       recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
//        }

    }

    @Override
    public void onListItemClick(int position) {

        Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();

      int id= arrayList_offline.get(position).getId();
       String u_name= arrayList_offline.get(position).getName();
       String u_scale= arrayList_offline.get(position).getItem_scale();
       String u_price=arrayList_offline.get(position).getItem_price();
       String u_quantity=arrayList_offline.get(position).getItem_quantity();
       String u_qeematfrokht=arrayList_offline.get(position).getQeemat_frokht();
       float u_totalprice=arrayList_offline.get(position).getTotal_price();
        Intent intent=new Intent(ShowItems.this,AddActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("name",u_name);
        intent.putExtra("scale",u_scale);
        intent.putExtra("price",u_price);
        intent.putExtra("quantity",u_quantity);
        intent.putExtra("qeematfrokht",u_qeematfrokht);
        intent.putExtra("totalprice",u_totalprice);
        startActivity(intent);
        finish();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchmenu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return false;
            }
        });
        return true;
    }
}