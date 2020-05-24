package ru.ipimenov.violetrainbow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.ipimenov.violetrainbow.data.ForeignViolet;
import ru.ipimenov.violetrainbow.data.MainViewModel;
import ru.ipimenov.violetrainbow.data.MiniViolet;
import ru.ipimenov.violetrainbow.data.Violet;
import ru.ipimenov.violetrainbow.utils.ContentUtils;
import ru.ipimenov.violetrainbow.utils.NetworkUtils;

public class CatalogFragment extends Fragment implements LoaderManager.LoaderCallbacks<String> {

    private int catalog;
    private int page = 1;

    private MainViewModel viewModel;
    private VioletAdapter violetAdapter;
    private ArrayList<Violet> violets;
    private RecyclerView recyclerView;

    private LoaderManager loaderManager;

    private ProgressBar progressBarLoading;
    private static boolean isLoading = false;

    public CatalogFragment(int catalog) {
        this.catalog = catalog;
//        this.page = page;
        this.setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        progressBarLoading = MainActivity.progressBarLoading;

        loaderManager = LoaderManager.getInstance(this);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getColumnCount()));

        violetAdapter = new VioletAdapter();

        downloadData();
        getVioletsFromDatabase();

        recyclerView.setAdapter(violetAdapter);

        violetAdapter.setOnVioletThumbnailClickListener(new VioletAdapter.OnVioletThumbnailClickListener() {
            @Override
            public void onVioletThumbnailClick(int position) {
                Violet violet = violetAdapter.getViolets().get(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(DetailActivity.VIOLET_NAME, violet.getVioletName());
                intent.putExtra(DetailActivity.VIOLET_CATALOG, catalog);
                startActivity(intent);
            }
        });

        violetAdapter.setOnReachEndListener(new VioletAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                if (!isLoading) {
                    downloadData();
                }
            }
        });

        return recyclerView;
    }

    private int getColumnCount() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = (int) (displayMetrics.widthPixels / displayMetrics.density);
        Log.i("My", "Ширина экрана в dp: " + width);
        return Math.max(width / 185, 2);
    }

    private void setVioletsToDatabase() {
        switch (catalog) {
            case NetworkUtils.RU_UA_SELECTION:
                if (page == 1) {
                    viewModel.deleteAllRuUaViolets();
//                    violetAdapter.clear();
                }
                for (Violet violet : violets) {
                    viewModel.insertRuUaViolet(violet);
                }
//                violetAdapter.addViolets(violets);
                page++;
                break;
            case NetworkUtils.FOREIGN_SELECTION:
                if (page == 1) {
                    viewModel.deleteAllForeignViolets();
//                    violetAdapter.clear();
                }
                for (Violet violet : violets) {
                    viewModel.insertForeignViolet(new ForeignViolet(violet));
                }
//                violetAdapter.addViolets(violets);
                page++;
                break;
            case NetworkUtils.MINI_SELECTION:
                if (page == 1) {
                    viewModel.deleteAllMiniViolets();
//                    violetAdapter.clear();
                }
                for (Violet violet : violets) {
                    viewModel.insertMiniViolet(new MiniViolet(violet));
                }
//                violetAdapter.addViolets(violets);
                page++;
                break;
        }
    }

    @SuppressLint("FragmentLiveDataObserve")
    private void getVioletsFromDatabase() {
        switch (catalog) {
            case NetworkUtils.RU_UA_SELECTION:
                LiveData<List<Violet>> violetsFromLiveDataR = viewModel.getRuUaViolets();
                violetsFromLiveDataR.observe(this, new Observer<List<Violet>>() {
                    @Override
                    public void onChanged(List<Violet> violets) {
//                        if (page == 1) {
                            violetAdapter.setViolets(violets);
//                        }
                    }
                });
                break;
            case NetworkUtils.FOREIGN_SELECTION:
                LiveData<List<ForeignViolet>> violetsFromLiveDataF = viewModel.getForeignViolets();
                violetsFromLiveDataF.observe(this, new Observer<List<ForeignViolet>>() {
                    @Override
                    public void onChanged(List<ForeignViolet> violets) {
                        List<Violet> violetsF = new ArrayList<Violet>(violets);
//                        if (page == 1) {
                            violetAdapter.setViolets(violetsF);
//                        }
                    }
                });
                break;
            case NetworkUtils.MINI_SELECTION:
                LiveData<List<MiniViolet>> violetsFromLiveDataM = viewModel.getMiniViolets();
                violetsFromLiveDataM.observe(this, new Observer<List<MiniViolet>>() {
                    @Override
                    public void onChanged(List<MiniViolet> violets) {
                        List<Violet> violetsM = new ArrayList<Violet>(violets);
//                        if (page == 1) {
                            violetAdapter.setViolets(violetsM);
//                        }
                    }
                });
                break;
        }
    }

    private void downloadData() {
        URL url = NetworkUtils.buildURL(catalog, page);
        Bundle bundle = new Bundle();
        bundle.putString("url", url.toString());
        loaderManager.restartLoader(catalog, bundle, this);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        NetworkUtils.ContentLoader contentLoader = new NetworkUtils.ContentLoader(Objects.requireNonNull(getContext()), args);
//        NetworkUtils.ContentLoader contentLoader = new NetworkUtils.ContentLoader(LOADER_ID, args);
        contentLoader.setOnStartLoadingListener(new NetworkUtils.ContentLoader.OnStartLoadingListener() {
            @Override
            public void onStartLoading() {
                progressBarLoading.setVisibility(View.VISIBLE);
                isLoading = true;
            }
        });
        return contentLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        violets = ContentUtils.getVioletsFromContent(data);
        if (violets != null && !violets.isEmpty()) {
            setVioletsToDatabase();
        }
        progressBarLoading.setVisibility(View.INVISIBLE);
        isLoading = false;
        loaderManager.destroyLoader(catalog);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
