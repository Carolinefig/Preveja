package com.example.preveja;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class CarregaPrevisao extends AsyncTaskLoader<String> {

    private String mQueryString;
    CarregaPrevisao(Context context, String queryString) {
        super(context);
        mQueryString = queryString;
    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.buscaInfosPrevisao(mQueryString);
    }
}
