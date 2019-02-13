package com.example.jimaras.comiccharatacters.features.characters.view;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jimaras.comiccharatacters.databinding.ItemNetworkStateBinding;
import com.example.jimaras.comiccharatacters.databinding.SingleItemCharacterBinding;
import com.example.jimaras.comiccharatacters.features.characters.model.CharacterDomain;
import com.example.jimaras.comiccharatacters.rest.NetworkState;
import com.squareup.picasso.Picasso;


public class CharacterAdapter extends PagedListAdapter<CharacterDomain.Results, RecyclerView.ViewHolder> {

    private static final int TYPE_PROGRESS = 0;
    private static final int TYPE_ITEM = 1;
    private NetworkState networkState;
    private Context ctx;
    private OnCharacterClickListener listener;


    CharacterAdapter(Context ctx, OnCharacterClickListener listener) {
        super(DIFF_CALLBACK);
        this.ctx      = ctx;
        this.listener = listener;
    }



    private static DiffUtil.ItemCallback<CharacterDomain.Results> DIFF_CALLBACK = new DiffUtil.ItemCallback<CharacterDomain.Results>() {
        @Override
        public boolean areItemsTheSame(@NonNull CharacterDomain.Results oldResults, @NonNull CharacterDomain.Results newResults) {
            return oldResults.id == newResults.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull CharacterDomain.Results oldResults, @NonNull CharacterDomain.Results newResults) {
            return oldResults.equals(newResults);
        }
    };


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(viewType == TYPE_PROGRESS) {
            ItemNetworkStateBinding  headerBinding = ItemNetworkStateBinding.inflate(layoutInflater, parent, false);
            NetworkStateItemViewHolder viewHolder = new NetworkStateItemViewHolder(headerBinding);

            return viewHolder;
        }else{
            SingleItemCharacterBinding characterBinding = SingleItemCharacterBinding.inflate(layoutInflater, parent, false);
            CharacterViewHolder viewHolder              = new CharacterViewHolder(characterBinding);

            return viewHolder;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int pos) {


        if(holder instanceof CharacterViewHolder) {
            CharacterDomain.Results character = getItem(pos);

            ((CharacterViewHolder) holder).bindTo(character);


            ((CharacterViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCharacterClick(character);
                }
            });

        }else{
            ((NetworkStateItemViewHolder) holder).bindView(networkState);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return TYPE_PROGRESS;
        } else {
            return TYPE_ITEM;
        }
    }

    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.LOADED) {
            return true;
        } else {
            return false;
        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }


    class CharacterViewHolder extends RecyclerView.ViewHolder{
        private SingleItemCharacterBinding binding;


        CharacterViewHolder(SingleItemCharacterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }



        public void bindTo(CharacterDomain.Results character){
            
            binding.characterName.setText(character.name);
            Picasso.get().load(character.thumbnail.getImageUrl()).into(binding.characterThumb);

        }
    }

    /*
     * We define A custom ViewHolder for the progressView
     */
    public class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {
        private ItemNetworkStateBinding binding;

        public NetworkStateItemViewHolder(ItemNetworkStateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(NetworkState networkState) {
            if (networkState != null && networkState.getStatus() == NetworkState.Status.RUNNING) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.progressBar.setVisibility(View.GONE);
            }

            if (networkState != null && networkState.getStatus() == NetworkState.Status.FAILED) {
                //binding.errorMsg.setVisibility(View.VISIBLE);
                //binding.errorMsg.setText(networkState.getMsg());
            } else {
                //binding.errorMsg.setVisibility(View.GONE);
            }
        }
    }
}
