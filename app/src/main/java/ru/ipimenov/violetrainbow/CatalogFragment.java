package ru.ipimenov.violetrainbow;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.ipimenov.violetrainbow.data.MainViewModel;
import ru.ipimenov.violetrainbow.data.Violet;
import ru.ipimenov.violetrainbow.utils.ContentUtils;
import ru.ipimenov.violetrainbow.utils.NetworkUtils;

public class CatalogFragment extends Fragment implements LifecycleObserver {

    private int catalog;
    private int page;

    private MainViewModel viewModel;

    public CatalogFragment(int catalog, int page) {
        this.catalog = catalog;
        this.page = page;
    }

    @SuppressLint("FragmentLiveDataObserve")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view,
                container, false);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        final VioletAdapter violetAdapter = new VioletAdapter();

        String content = NetworkUtils.getContentFromNetwork(catalog, page);
        ArrayList<Violet> violets = ContentUtils.getVioletsFromContent(content);

        if (violets != null && !violets.isEmpty()) {
            viewModel.deleteAllViolets();
            for (Violet violet : violets) {
                viewModel.insertViolet(violet);
            }
        }

        LiveData<List<Violet>> violetsFromLiveData = viewModel.getViolets();
        violetsFromLiveData.observe(this, new Observer<List<Violet>>() {
            @Override
            public void onChanged(List<Violet> violets) {
                violetAdapter.setViolets(violets);
            }
        });
//        List<Violet> violetList = violetsFromLiveData.getValue();

//        violetAdapter.setViolets(violetList);

        violetAdapter.setOnVioletThumbnailClickListener(new VioletAdapter.OnVioletThumbnailClickListener() {
            @Override
            public void onVioletThumbnailClick(int position) {
                Toast.makeText(getActivity(), "Нажата позиция " + position, Toast.LENGTH_SHORT).show();
            }
        });

        violetAdapter.setOnReachEndListener(new VioletAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                Toast.makeText(getActivity(), "Конец списка", Toast.LENGTH_SHORT).show();
            }
        });

//        LiveData<List<Violet>> violetsFromLiveData = viewModel.getViolets();
//        violetsFromLiveData.observe(getViewLifecycleOwner(), new Observer<List<Violet>>() {
//            @Override
//            public void onChanged(List<Violet> violets) {
//                violetAdapter.setViolets(violets);
//            }
//        });

        recyclerView.setAdapter(violetAdapter);

        return recyclerView;
    }

//    private void downloadData(int catalog, int page) {
//        String content = NetworkUtils.getContentFromNetwork(catalog, page);
//        ArrayList<Violet> violets = ContentUtils.getVioletsFromContent(content);
//        if (violets != null && !violets.isEmpty()) {
//            viewModel.deleteAllViolets();
//            for (Violet violet : violets) {
//                viewModel.insertViolet(violet);
//            }
//        }
//    }
}
