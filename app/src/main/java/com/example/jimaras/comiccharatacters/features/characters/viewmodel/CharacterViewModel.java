package com.example.jimaras.comiccharatacters.features.characters.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.example.jimaras.comiccharatacters.features.characters.model.CharacterDomain;
import com.example.jimaras.comiccharatacters.features.characters.view.CharacterDataSourceFactory;

import timber.log.Timber;

public class CharacterViewModel extends ViewModel {

    //creating livedata for PagedList  and PagedKeyedDataSource
    private LiveData networkState;
    private LiveData liveDataSource;

    private CharacterDomain.Results character;

    public CharacterViewModel(CharacterDomain.Results character) {
        this.character = character;
    }

    public CharacterViewModel() {
        //getting our data source factory
        CharacterDataSourceFactory characterDataSourceFactory = new CharacterDataSourceFactory();

        // Get NetworkState
        networkState = Transformations.switchMap(characterDataSourceFactory.getCharactersLiveData(), datasoure->datasoure.getNetworkState());

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                ( new PagedList.Config.Builder() )
                        .setEnablePlaceholders(false)
                        .setPageSize(100).build();

        //Building the paged list
        liveDataSource = (new LivePagedListBuilder(characterDataSourceFactory, pagedListConfig) ).build();


    }


    public LiveData getNetworkState() {
        return networkState;
    }

    public LiveData<PagedList<CharacterDomain.Results>> getLiveDataSource() {
        return liveDataSource;
    }

    /**
     * On Character Click
     */
    public void onCharacterClick(){

        Timber.e(String.valueOf(character.name));
    }

}
