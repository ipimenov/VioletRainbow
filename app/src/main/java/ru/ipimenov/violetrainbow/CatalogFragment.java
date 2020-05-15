package ru.ipimenov.violetrainbow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.ipimenov.violetrainbow.data.ForeignViolet;
import ru.ipimenov.violetrainbow.data.MainViewModel;
import ru.ipimenov.violetrainbow.data.MiniViolet;
import ru.ipimenov.violetrainbow.data.Violet;
import ru.ipimenov.violetrainbow.utils.ContentUtils;
import ru.ipimenov.violetrainbow.utils.NetworkUtils;

public class CatalogFragment extends Fragment {

    private int catalog;
    private int page;

    private MainViewModel viewModel;
    private VioletAdapter violetAdapter;
    private ArrayList<Violet> violets;

    public CatalogFragment(int catalog, int page) {
        this.catalog = catalog;
        this.page = page;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view,
                container, false);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        violetAdapter = new VioletAdapter();

        String content = NetworkUtils.getContentFromNetwork(catalog, page);
        violets = ContentUtils.getVioletsFromContent(content);

        if (violets != null && !violets.isEmpty()) {
            setVioletsToDatabase();
        }
        getVioletsFromDatabase();

        violetAdapter.setOnVioletThumbnailClickListener(new VioletAdapter.OnVioletThumbnailClickListener() {
            @Override
            public void onVioletThumbnailClick(int position) {
                Violet violet = violetAdapter.getViolets().get(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("violetName", violet.getVioletName());
                startActivity(intent);
            }
        });

        violetAdapter.setOnReachEndListener(new VioletAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                Toast.makeText(getActivity(), "Конец списка", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(violetAdapter);

        return recyclerView;
    }

    private void setVioletsToDatabase() {
        switch (catalog) {
            case NetworkUtils.RU_UA_SELECTION:
                viewModel.deleteAllRuUaViolets();
                for (Violet violet : violets) {
                    viewModel.insertRuUaViolet(violet);
                }
                break;
            case NetworkUtils.FOREIGN_SELECTION:
                viewModel.deleteAllForeignViolets();
                for (Violet violet : violets) {
                    viewModel.insertForeignViolet(new ForeignViolet(violet));
                }
                break;
            case NetworkUtils.MINI_SELECTION:
                viewModel.deleteAllMiniViolets();
                for (Violet violet : violets) {
                    viewModel.insertMiniViolet(new MiniViolet(violet));
                }
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
                        violetAdapter.setViolets(violets);
                    }
                });
                break;
            case NetworkUtils.FOREIGN_SELECTION:
                LiveData<List<ForeignViolet>> violetsFromLiveDataF = viewModel.getForeignViolets();
                violetsFromLiveDataF.observe(this, new Observer<List<ForeignViolet>>() {
                    @Override
                    public void onChanged(List<ForeignViolet> violets) {
                        List<Violet> violetsF = new ArrayList<Violet>(violets);
                        violetAdapter.setViolets(violetsF);
                    }
                });
                break;
            case NetworkUtils.MINI_SELECTION:
                LiveData<List<MiniViolet>> violetsFromLiveDataM = viewModel.getMiniViolets();
                violetsFromLiveDataM.observe(this, new Observer<List<MiniViolet>>() {
                    @Override
                    public void onChanged(List<MiniViolet> violets) {
                        List<Violet> violetsM = new ArrayList<Violet>(violets);
                        violetAdapter.setViolets(violetsM);
                    }
                });
                break;
        }
    }
}
