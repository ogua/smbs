package com.oguses.cartcount.Sharepreference;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Random;


public class Sharepregmanager {

    private static final String SHARE_PRE_MANAGER = "my_share_pre";
    private static Sharepregmanager minstance;
    private Context mcontext;


    public Sharepregmanager(Context mcontext){
        this.mcontext = mcontext;
    }


    //syncoenises to get a singletin object
    public static synchronized Sharepregmanager getInstance(Context mcontext){
        if (minstance == null){
            minstance = new Sharepregmanager(mcontext);
        }
        return minstance;
    }



//    public void Saveuser(User user){
//        SharedPreferences sharepre = mcontext.getSharedPreferences(SHARE_PRE_MANAGER, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharepre.edit();
//        editor.putString("id", user.getId());
//        editor.putString("name", user.getName());
//        editor.putString("email", user.getEmail());
//        editor.apply();
//    }


    public boolean Islogedin(){
        SharedPreferences sharepre = mcontext.getSharedPreferences(SHARE_PRE_MANAGER, Context.MODE_PRIVATE);
        if (sharepre.getString("id","null") != null) {
            return true;
        }

        return false;
    }


    public void Logout(){
        SharedPreferences sharepre = mcontext.getSharedPreferences(SHARE_PRE_MANAGER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharepre.edit();
        editor.clear();
        editor.apply();
    }


    public void storetoken(String token){
        SharedPreferences sharepre = mcontext.getSharedPreferences(SHARE_PRE_MANAGER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharepre.edit();
        editor.putString("token", token);
        editor.apply();
    }


    public String gettoken(){
        SharedPreferences sharepre = mcontext.getSharedPreferences(SHARE_PRE_MANAGER, Context.MODE_PRIVATE);
        return sharepre.getString("token","");
    }



    public void storerandomnumber(){
        Random r = new Random(System.currentTimeMillis());
        int rnumber = ((1 + r.nextInt(2)) * 10000 + r.nextInt(1000));
        SharedPreferences sharepre = mcontext.getSharedPreferences(SHARE_PRE_MANAGER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharepre.edit();
        editor.putString("itemref", String.valueOf(rnumber));
        editor.apply();
    }


    public String getitemref(){
        SharedPreferences sharepre = mcontext.getSharedPreferences(SHARE_PRE_MANAGER, Context.MODE_PRIVATE);
        return sharepre.getString("itemref","");
    }



    //returning a user object
//    public User getUserInfo(){
//        SharedPreferences sharepre = mcontext.getSharedPreferences(SHARE_PRE_MANAGER, Context.MODE_PRIVATE);
//        return new User(
//                sharepre.getString("id",""),
//                sharepre.getString("name",""),
//                sharepre.getString("email","")
//        );
//    }




}
