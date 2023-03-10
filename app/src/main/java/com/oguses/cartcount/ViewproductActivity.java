package com.oguses.cartcount;

import static com.google.android.gms.vision.L.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.oguses.cartcount.Api.Apiclient.ApiClient;
import com.oguses.cartcount.Models.Productresponse;
import com.oguses.cartcount.Models.Responsemsg;
import com.oguses.cartcount.Sharepreference.Sharepregmanager;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewproductActivity extends AppCompatActivity {


    private TextInputEditText prname,prqty;
    private TextInputEditText productprice;
    private Button addtocart;
    private CircleImageView primag;
    String intentdata,productid,storedref;
    private ProgressBar progressBar,imgprog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewproduct);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Product To Cart");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        intentdata = intent.getStringExtra("INTENT");

        prname = (TextInputEditText) findViewById(R.id.productname);
        productprice = (TextInputEditText) findViewById(R.id.prodpx);
        prqty = (TextInputEditText) findViewById(R.id.productqty);
        addtocart = (Button) findViewById(R.id.addtocart);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        imgprog = (ProgressBar) findViewById(R.id.imgprogress);

        primag = (CircleImageView) findViewById(R.id.productimg);


        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String name = prname.getText().toString().trim();
                String price = productprice.getText().toString().trim();
                String quantity = prqty.getText().toString().trim();

                if (name.isEmpty() || price.isEmpty() || quantity.isEmpty()){
                    Toast.makeText(ViewproductActivity.this, "All Fields are required!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ViewproductActivity.this, "Saving..", Toast.LENGTH_SHORT).show();
                }

                Call<Responsemsg> addtocart = ApiClient.getInstance().getApi().additemtocart(storedref,productid,price,quantity);
                addtocart.enqueue(new Callback<Responsemsg>() {
                    @Override
                    public void onResponse(Call<Responsemsg> call, Response<Responsemsg> response) {
                        if (response.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ViewproductActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewproductActivity.this);
                            alertDialogBuilder.setMessage(response.body().getMessage()+" Add Another Item");
                            alertDialogBuilder.setPositiveButton("YES",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                           startActivity(new Intent(ViewproductActivity.this,MainActivity.class));
                                           finish();
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

                        }
                    }

                    @Override
                    public void onFailure(Call<Responsemsg> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });

        LoadProductDetails();

    }

    private void LoadProductDetails() {

        //progressBar.setVisibility(View.VISIBLE);
        imgprog.setVisibility(View.VISIBLE);
        String barcode = intentdata;
        Call<Productresponse> fetchproduct = ApiClient.getInstance().getApi().productinfo(barcode);
        fetchproduct.enqueue(new Callback<Productresponse>() {
            @Override
            public void onResponse(Call<Productresponse> call, Response<Productresponse> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    prname.setText(response.body().getName());
                    //productprice.setText(Integer.parseInt(response.body().getAmnt()));
                    productid = String.valueOf(response.body().getId());
                    productprice.setText(response.body().getAmnt());
                    prqty.setText("1");
                   // Log.e(TAG, "getting data from onResponse: "+response.body().getImg());

                    Picasso.with(ViewproductActivity.this).load(response.body().getImg())
                            .fit()
                            .centerCrop()
                            .into(primag, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    imgprog.setVisibility(View.GONE);
                                }

                                @Override
                                public void onError() {
                                    imgprog.setVisibility(View.GONE);
                                }
                            });
                    //progressBar.setVisibility(View.GONE);
                }else{
                    Toast.makeText(ViewproductActivity.this, "Authorized Action", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Productresponse> call, Throwable t) {
                //progressBar.setVisibility(View.GONE);
                Log.e(TAG, "onFailure: "+t.getMessage().toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(this,MainActivity.class));
                break;

            case R.id.showcart:
                startActivity(new Intent(this,ListitemsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sharepregmanager m = new Sharepregmanager(ViewproductActivity.this);
        storedref = m.getitemref();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}