package ru.ipimenov.violetrainbow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.ipimenov.violetrainbow.data.Violet;
import ru.ipimenov.violetrainbow.utils.ContentUtils;
import ru.ipimenov.violetrainbow.utils.NetworkUtils;

public class CatalogFragment extends Fragment {

    private int catalog;
    private int page;

    public CatalogFragment(int catalog, int page) {
        this.catalog = catalog;
        this.page = page;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        String content = NetworkUtils.getContentFromNetwork(catalog, page);
        ArrayList<Violet> violets = ContentUtils.getVioletsFromContent(content);

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view,
                container, false);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        VioletAdapter violetAdapter = new VioletAdapter();

        violetAdapter.setViolets(violets);

        recyclerView.setAdapter(violetAdapter);

        return recyclerView;
    }
}
