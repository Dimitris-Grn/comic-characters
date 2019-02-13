package com.example.jimaras.comiccharatacters.features.characters.view;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.example.jimaras.comiccharatacters.features.characters.model.CharacterDomain;
import com.example.jimaras.comiccharatacters.rest.NetworkState;
import com.example.jimaras.comiccharatacters.rest.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class CharacterDataSource extends PageKeyedDataSource<Integer, CharacterDomain.Results> {

    private static final int INITIAL_OFFSET = 100;

    // Retry to fetch data
    private static final int TOTAL_RETRIES = 3;
    private int retryCount                 = 0;


    private MutableLiveData<NetworkState> networkState;
    private MutableLiveData<NetworkState> initialLoading;

    // Constructor
    CharacterDataSource() {
        this.networkState = new MutableLiveData<>();
        this.initialLoading = new MutableLiveData<>();
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public MutableLiveData getInitialLoading() {
        return initialLoading;
    }

    @Override
    public void loadInitial(@NonNull final LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, CharacterDomain.Results> callback) {
        Timber.e("LoadInitial");

        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);

        RestClient.getInstance().getApi().fetchInitCharacters()
                .enqueue(new Callback<CharacterDomain>() {
                    @Override
                    public void onResponse(@NonNull Call<CharacterDomain> call, @NonNull Response<CharacterDomain> response) {
                        if (response.body() != null) {
                            callback.onResult(response.body().data.results, null, INITIAL_OFFSET);

                            initialLoading.postValue(NetworkState.LOADED);
                            networkState.postValue(NetworkState.LOADED);

                        }else{
                            initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                        }
                    }

                    @Override
                    public void onFailure(Call<CharacterDomain> call, Throwable t) {
                        if (retryCount++ < TOTAL_RETRIES) {
                            call.clone().enqueue(this);
                        }else {
                            String errorMessage = ( t == null )? "unknown error" : t.getMessage();
                            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                        }
                    }
                });


    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, CharacterDomain.Results> callback) {
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, CharacterDomain.Results> callback) {
        Timber.e("LoadAfter");
        networkState.postValue(NetworkState.LOADING);

        RestClient.getInstance().getApi().fetchCharacters(params.key)
                .enqueue(new Callback<CharacterDomain>() {
                    @Override
                    public void onResponse(Call<CharacterDomain> call, Response<CharacterDomain> response) {
                        if (response.body() != null) {
                            Integer adjacentKey = params.key + 100;
                            //passing the loaded data and next page value
                            callback.onResult(response.body().data.results, adjacentKey);

                            networkState.postValue(NetworkState.LOADED);
                        }else{

                            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                        }
                    }

                    @Override
                    public void onFailure(Call<CharacterDomain> call, Throwable t) {
                        if (retryCount++ < TOTAL_RETRIES) {
                            call.clone().enqueue(this);
                        }else{
                            String errorMessage = (t == null) ? "unknown error" : t.getMessage();
                            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                        }
                    }
                });
    }
}
