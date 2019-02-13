package com.example.jimaras.comiccharatacters.base;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.jimaras.comiccharatacters.R;
import com.example.jimaras.comiccharatacters.databinding.ActivityMainBinding;
import com.example.jimaras.comiccharatacters.features.character_desc.Character_Desc_Fragment;
import com.example.jimaras.comiccharatacters.features.characters.view.CharacterFragment;
import com.example.jimaras.comiccharatacters.features.characters.viewmodel.SharedViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    SharedViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel.class);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.root_activity, new CharacterFragment())
                    .addToBackStack(null)
                    .commit();



        }

        sharedViewModel.get_selected_character().observe(this, results -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.root_activity, new Character_Desc_Fragment())
                    .addToBackStack(null)
                    .commit();

        });
    }
}
