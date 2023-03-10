package com.oguses.cartcount.Api.Apiservice;

import com.oguses.cartcount.Models.Items;
import com.oguses.cartcount.Models.Productresponse;
import com.oguses.cartcount.Models.Responsemsg;
import com.oguses.cartcount.Models.Totalcart;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Apiservice {


    @GET("show-product/{barcode}")
    Call<Productresponse> productinfo(
            @Path("barcode") String barcode
    );

    @FormUrlEncoded
    @POST("add-items-to-cart")
    Call<Responsemsg> additemtocart(
            @Field("barcodeid") String barcodeid,
            @Field("prname") String prname,
            @Field("prpx") String prpx,
            @Field("qty") String qty
    );

    @GET("show-cart-items/{appreff}")
    Call<ArrayList<Items>> cartitems(
            @Path("appreff") String appreff
    );

    @GET("get-cart-total/{appreff}")
    Call<Totalcart> totalprice(
            @Path("appreff") String appreff
    );

    @GET("delete-from-cart/{id}")
    Call<Responsemsg> Deletefromcart(
            @Path("id") String id
    );



//    @Multipart
//    @POST("OSMS/add-student")
//    Call<Success> addstudent(
//            @Part("first_name") RequestBody fname,
//            @Part("other_names") RequestBody other_names,
//            @Part("gender") RequestBody gender,
//            @Part("dateofbirth") RequestBody dateofbirth,
//            @Part("religion") RequestBody religion,
//            @Part("placeofbirth") RequestBody placeofbirth,
//            @Part("hometown") RequestBody hometown,
//            @Part("disability") RequestBody disability,
//            @Part("entrylevel") RequestBody entrylevel,
//            @Part("guidianfullname") RequestBody guidianfullname,
//            @Part("relationship") RequestBody relationship,
//            @Part("guidianoccupation") RequestBody onames,
//            @Part("mobnumber") RequestBody mobnumber,
//            @Part("employed") RequestBody employed,
//            @Part("nationality") RequestBody nationality,
//            @Part MultipartBody.Part image,
//            @Header("Authorization") String auth
//            );

}
