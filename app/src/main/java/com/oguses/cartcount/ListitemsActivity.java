package com.oguses.cartcount;

import static com.google.android.gms.vision.L.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.oguses.cartcount.Adapters.Cartadapter;
import com.oguses.cartcount.Api.Apiclient.ApiClient;
import com.oguses.cartcount.Models.Items;
import com.oguses.cartcount.Models.Productresponse;
import com.oguses.cartcount.Models.Totalcart;
import com.oguses.cartcount.Sharepreference.Sharepregmanager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListitemsActivity extends AppCompatActivity {

    String intentdata;
    private RecyclerView recyclerView;
    private Cartadapter adapter;
    private List<Items> listItems;
    private TextView grandtotal;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listitems);

        Intent intent = getIntent();
        intentdata = intent.getStringExtra("INTENT");

        getSupportActionBar().setTitle("Cart Items - REF: "+intentdata);

        grandtotal = (TextView) findViewById(R.id.grandtotal);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        loaditemsfromdatabase();
        loadtotalprice();
    }

    private void loadtotalprice() {
        Call<Totalcart> total = ApiClient.getInstance().getApi().totalprice(intentdata);
        total.enqueue(new Callback<Totalcart>() {
            @Override
            public void onResponse(Call<Totalcart> call, Response<Totalcart> response) {
                if (response.isSuccessful()){
                    grandtotal.setText(response.body().getTotal());
                }
            }

            @Override
            public void onFailure(Call<Totalcart> call, Throwable t) {

            }
        });
    }

    private void loaditemsfromdatabase() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Please Wait..");
        pd.show();

        listItems = new ArrayList<Items>();
        Call<ArrayList<Items>> cartitems = ApiClient.getInstance().getApi().cartitems(intentdata);
        cartitems.enqueue(new Callback<ArrayList<Items>>() {
            @Override
            public void onResponse(Call<ArrayList<Items>> call, Response<ArrayList<Items>> response) {
                if (response.isSuccessful()){
                    Log.e(TAG, "onResponse: "+response.body());
                    listItems.addAll(response.body());
                    adapter = new Cartadapter(listItems,ListitemsActivity.this);
                    recyclerView.setAdapter(adapter);
                    pd.hide();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Items>> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage() );
                Toast.makeText(ListitemsActivity.this, ""+ t.getMessage(), Toast.LENGTH_SHORT).show();
                pd.hide();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.clearcart:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListitemsActivity.this);
                alertDialogBuilder.setMessage("Are You sure you ?");
                alertDialogBuilder.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Sharepregmanager m = new Sharepregmanager(getApplicationContext());
                                m.storerandomnumber();
                                startActivity(new Intent(ListitemsActivity.this,MainActivity.class));
                            }
                        });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}