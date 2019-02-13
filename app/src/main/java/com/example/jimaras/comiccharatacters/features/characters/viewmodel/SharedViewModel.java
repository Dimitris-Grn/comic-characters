package com.example.jimaras.comiccharatacters.features.characters.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.jimaras.comiccharatacters.features.characters.model.CharacterDomain;

public class SharedViewModel extends ViewModel {

    private MutableLiveData<CharacterDomain.Results > selected_character = new MutableLiveData<>();

    public void select(CharacterDomain.Results character){
        selected_character.setValue(character);
    }


    public LiveData<CharacterDomain.Results> get_selected_character(){
        return selected_character;
    }
}
