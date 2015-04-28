package com.plusplus.i.jongerenparticipatieplatfrom.service;

import com.plusplus.i.jongerenparticipatieplatfrom.model.Account;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoDms;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoDmsDetailed;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoDossier;
import com.plusplus.i.jongerenparticipatieplatfrom.model.DtoDossierDetailed;
import com.plusplus.i.jongerenparticipatieplatfrom.model.Token;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Shenno on 12/04/2015.
 */
public interface JppService {
    @POST("/api/Account/Register")
    void registerAccount(@Body Account account, Callback<Account> created);

    @FormUrlEncoded
    @POST("/token")
    void getToken(@Field("grant_type") String grantType, @Field("username") String username, @Field("password") String password, Callback<Token> created);

    @GET("/api/dms/open/{dmId}")
    void getOpenDms(@Path("dmId") int dmId, Callback<List<DtoDms>> callback);

    @GET("/api/dms/{dmsId}")
    void getDms(@Path("dmsId") int dmsId, Callback<DtoDmsDetailed> callback);

    @GET("/api/dms/{dmsId}/dossiers")
    void getDossiers(@Path("dmsId") int dmsId, Callback<List<DtoDossier>> callBack);

    @GET("/api/dms/dossier/{dId}")
    void getDossier(@Path("dId") int dId, Callback<DtoDossierDetailed> callback);
}
