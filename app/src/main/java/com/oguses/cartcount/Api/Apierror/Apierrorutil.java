package com.oguses.cartcount.Api.Apierror;

import com.oguses.cartcount.Api.Apiclient.ApiClient;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class Apierrorutil {

    public static Apierrormodel parceError(Response<?> response){
        Converter<ResponseBody, Apierrormodel> converter =
                ApiClient.retrofit
                .responseBodyConverter(Apierrormodel.class, new Annotation[0]);

        Apierrormodel error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new Apierrormodel();
        }

        return error;
    }
}
