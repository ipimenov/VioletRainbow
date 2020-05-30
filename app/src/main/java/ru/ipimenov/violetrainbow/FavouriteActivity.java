package ru.ipimenov.violetrainbow;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.ipimenov.violetrainbow.data.FavouriteViolet;
import ru.ipimenov.violetrainbow.data.MainViewModel;
import ru.ipimenov.violetrainbow.data.Violet;

public class FavouriteActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFavouriteViolets;
    private VioletAdapter violetAdapter;
    private MainViewModel viewModel;
    public static final int FAVOURITE_CATALOG = 3;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.itemMain:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.itemFavourite:
                Intent intentToFavourite = new Intent(this, FavouriteActivity.class);
                startActivity(intentToFavourite);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        Toolbar toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        recyclerViewFavouriteViolets = findViewById(R.id.recyclerViewFavouriteViolets);
        recyclerViewFavouriteViolets.setLayoutManager(new GridLayoutManager(this, 2));
        violetAdapter = new VioletAdapter();
        recyclerViewFavouriteViolets.setAdapter(violetAdapter);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        LiveData<List<FavouriteViolet>> favouriteViolets = viewModel.getFavouriteViolets();
        favouriteViolets.observe(this, new Observer<List<FavouriteViolet>>() {
            @Override
            public void onChanged(List<FavouriteViolet> favouriteViolets) {
                List<Violet> violets = new ArrayList<Violet>(favouriteViolets);
                violetAdapter.setViolets(violets);
            }
        });

        violetAdapter.setOnVioletThumbnailClickListener(new VioletAdapter.OnVioletThumbnailClickListener() {
            @Override
            public void onVioletThumbnailClick(int position) {
                Violet violet = violetAdapter.getViolets().get(position);
                Intent intent = new Intent(FavouriteActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.VIOLET_NAME, violet.getVioletName());
                intent.putExtra(DetailActivity.VIOLET_CATALOG, FAVOURITE_CATALOG);
                startActivity(intent);
            }
        });
    }
}
