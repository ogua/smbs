package com.oguses.cartcount.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.oguses.cartcount.Api.Apiclient.ApiClient;
import com.oguses.cartcount.ListitemsActivity;
import com.oguses.cartcount.MainActivity;
import com.oguses.cartcount.Models.Items;
import com.oguses.cartcount.Models.Responsemsg;
import com.oguses.cartcount.R;
import com.oguses.cartcount.Sharepreference.Sharepregmanager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;

public class Cartadapter  extends RecyclerView.Adapter<Cartadapter.ViewHolder>{
private List<Items> listItems;
private Context context;


public Cartadapter(List<Items> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
        }


    @NonNull
    @Override
    public Cartadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitems,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Cartadapter.ViewHolder holder, int position) {
        Items item = listItems.get(position);
        holder.prdname.setText(item.getName()+" ("+item.getQty()+"x)");
        holder.prprice.setText(item.getPrice());
        holder.prqnty.setText(item.getQty());
        holder.prxtototal.setText("Total Price "+item.getTotal());
        holder.removeitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = item.getId();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are You sure you ?");
                alertDialogBuilder.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Call<Responsemsg> delitem = ApiClient.getInstance().getApi().Deletefromcart(id);
                                delitem.enqueue(new retrofit2.Callback<Responsemsg>() {
                                    @Override
                                    public void onResponse(Call<Responsemsg> call, Response<Responsemsg> response) {
                                        if (response.isSuccessful()){
                                            Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(context, ListitemsActivity.class);
                                            i.putExtra("INTENT",item.getAppid());
                                            context.startActivity(i);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Responsemsg> call, Throwable t) {
                                        Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
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
        });

        Picasso.with(context.getApplicationContext())
                        .load(item.getImg())
                        .fit().centerCrop()
                        .into(holder.img, new Callback() {
                            @Override
                            public void onSuccess() {
                                holder.prodimgprogbar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                holder.prodimgprogbar.setVisibility(View.GONE);
                            }
                        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView img;
    public TextView prdname;
    public TextView prprice;
    public TextView prqnty;
    public TextView prxtototal;
    public ProgressBar prodimgprogbar;
    public ImageView removeitem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            removeitem = (ImageView) itemView.findViewById(R.id.removeitem);
            img = (CircleImageView) itemView.findViewById(R.id.imgproduct);
            prdname = (TextView) itemView.findViewById(R.id.prname);
            prprice = (TextView) itemView.findViewById(R.id.prprice);
            prqnty = (TextView) itemView.findViewById(R.id.prqnty);
            prxtototal = (TextView) itemView.findViewById(R.id.prxtototal);
            prodimgprogbar = (ProgressBar) itemView.findViewById(R.id.prodimgprogbar);
        }
    }
}