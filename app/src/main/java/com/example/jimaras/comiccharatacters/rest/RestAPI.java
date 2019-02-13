package com.example.jimaras.comiccharatacters.rest;

import com.example.jimaras.comiccharatacters.features.characters.model.CharacterDomain;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface RestAPI {

    @GET("characters?orderBy=name&limit=100")
    Call<CharacterDomain> fetchCharacters(@Query("offset") int offset);
    @GET("characters?orderBy=name&limit=100")
    Call<CharacterDomain> fetchInitCharacters();

}
