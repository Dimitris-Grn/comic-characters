package com.example.jimaras.comiccharatacters.features.character_desc;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jimaras.comiccharatacters.R;
import com.example.jimaras.comiccharatacters.databinding.FragmentCharacterDescriptionBinding;
import com.example.jimaras.comiccharatacters.features.characters.viewmodel.SharedViewModel;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class Character_Desc_Fragment extends Fragment {

    SharedViewModel sharedViewModel;
    FragmentCharacterDescriptionBinding binding;

    public Character_Desc_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate( inflater,
                R.layout.fragment_character_description,
                container,
                false);

        sharedViewModel.get_selected_character().observe(this, results -> {
            Picasso.get().load(results.thumbnail.getImageUrl()).into(binding.characterImg);
            binding.characterTitle.setText(results.name);
            binding.characterDesc.setText(results.description);
            binding.copyright.setText(R.string.copyright);
        });


        return binding.getRoot();
    }
}
