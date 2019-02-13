package com.example.jimaras.comiccharatacters.features.characters.view;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

public class CharacterDataSourceFactory extends DataSource.Factory {
    //creating the mutable live data
    private MutableLiveData<CharacterDataSource> charactersLiveData;


    public CharacterDataSourceFactory() {
        this.charactersLiveData= new MutableLiveData<>();
    }

    @Override
    public DataSource create() {

        //getting our data source object
        CharacterDataSource characterDataSource = new CharacterDataSource();


        //posting the datasource to get the values
        charactersLiveData.postValue(characterDataSource);

        //returning the datasource
        return characterDataSource;

    }

    public MutableLiveData<CharacterDataSource> getCharactersLiveData() {
        return charactersLiveData;
    }

}
