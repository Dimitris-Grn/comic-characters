package com.example.jimaras.comiccharatacters.features.characters.view;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jimaras.comiccharatacters.R;
import com.example.jimaras.comiccharatacters.databinding.FragmentCharacterBinding;
import com.example.jimaras.comiccharatacters.features.characters.model.CharacterDomain;
import com.example.jimaras.comiccharatacters.features.characters.viewmodel.CharacterViewModel;
import com.example.jimaras.comiccharatacters.features.characters.viewmodel.SharedViewModel;
import com.example.jimaras.comiccharatacters.rest.NetworkState;

/**
 * A simple {@link Fragment} subclass.
 */
public class CharacterFragment extends Fragment implements OnCharacterClickListener{

    private FragmentCharacterBinding binding;
    private SharedViewModel sharedViewModel;


    public CharacterFragment() {
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
        binding = DataBindingUtil.inflate( inflater,
                                            R.layout.fragment_character,
                                            container,
                                false);

        // Recycler Manager Layout
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        binding.characterRv.setLayoutManager(layoutManager);
        binding.characterRv.setHasFixedSize(true);

        // CharacterViewModel
        CharacterViewModel characterViewModel = ViewModelProviders.of(this).get(CharacterViewModel.class);
        final CharacterAdapter adapter        = new CharacterAdapter(getActivity(),this);

        // Observers
        characterViewModel.getLiveDataSource().observe(this, results -> {
            //in case of any changes
            //submitting the items to adapter
            adapter.submitList(results);

        });

        characterViewModel.getNetworkState().observe(this, networkState  -> {
            adapter.setNetworkState((NetworkState) networkState);
        });

        binding.characterRv.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onCharacterClick(CharacterDomain.Results character) {
        sharedViewModel.select(character);
    }

}
