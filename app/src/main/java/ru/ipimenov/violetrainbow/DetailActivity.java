package ru.ipimenov.violetrainbow;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import ru.ipimenov.violetrainbow.data.FavouriteViolet;
import ru.ipimenov.violetrainbow.data.MainViewModel;
import ru.ipimenov.violetrainbow.data.Violet;
import ru.ipimenov.violetrainbow.utils.NetworkUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String VIOLET_NAME = "violet_name";
    public static final String VIOLET_CATALOG = "violet_catalog";

    private ImageView imageViewVioletImageC;
    private TextView textViewVioletName;
    private TextView textViewVioletBreeder;
    private TextView textViewLabelVioletYear;
    private TextView textViewVioletYear;
    private TextView textViewLabelVioletOverview;
    private TextView textViewVioletOverview;
    private ImageView imageViewAddToFavouriteC;
    private ImageView imageViewAddToFavouriteBottomC;

    private String violetName;
    private int catalog;
    private MainViewModel viewModel;
    private Violet violet;
    private FavouriteViolet favouriteViolet;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_collapsing);

        setSupportActionBar((Toolbar) findViewById(R.id.toolBarC));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsingToolbarLayout);

        // Set title of Detail page
        imageViewVioletImageC = findViewById(R.id.imageViewVioletImageC);
        textViewVioletName = findViewById(R.id.textViewVioletName);
        textViewVioletBreeder = findViewById(R.id.textViewVioletBreeder);
        textViewLabelVioletYear = findViewById(R.id.textViewLabelVioletYear);
        textViewVioletYear = findViewById(R.id.textViewVioletYear);
        textViewLabelVioletOverview = findViewById(R.id.textViewLabelVioletOverview);
        textViewVioletOverview = findViewById(R.id.textViewVioletOverview);
        imageViewAddToFavouriteC = findViewById(R.id.imageViewAddToFavouriteC);
        imageViewAddToFavouriteBottomC = findViewById(R.id.imageViewAddToFavouriteBottomC);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(VIOLET_NAME) && intent.hasExtra(VIOLET_CATALOG)) {
            violetName = intent.getStringExtra(VIOLET_NAME);
            catalog = intent.getIntExtra(VIOLET_CATALOG, -1);
        } else {
            finish();
        }

        collapsingToolbar.setTitle(violetName);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        violet = getVioletFromCatalog();
        Picasso.get().load(violet.getVioletImagePath()).into(imageViewVioletImageC);
        textViewVioletName.setText(violet.getVioletName());
        textViewVioletBreeder.setText(violet.getVioletBreeder());
        if (!violet.getVioletYear().equals(" ")) {
            textViewVioletYear.setText(violet.getVioletYear());
        } else {
            textViewLabelVioletYear.setVisibility(View.GONE);
            textViewVioletYear.setVisibility(View.GONE);
        }
        if (!violet.getVioletOverview().equals(" ")) {
            textViewVioletOverview.setText(violet.getVioletOverview());
        } else {
            textViewLabelVioletOverview.setVisibility(View.GONE);
            textViewVioletOverview.setVisibility(View.GONE);
        }
        setFavourite();
    }

    private Violet getVioletFromCatalog() {
        switch (catalog) {
            case NetworkUtils.RU_UA_SELECTION:
                violet = viewModel.getRuUaVioletByVioletName(violetName);
                break;
            case NetworkUtils.FOREIGN_SELECTION:
                violet = viewModel.getForeignVioletByVioletName(violetName);
                break;
            case NetworkUtils.MINI_SELECTION:
                violet = viewModel.getMiniVioletByVioletName(violetName);
                break;
            case FavouriteActivity.FAVOURITE_CATALOG:
                violet = viewModel.getFavouriteVioletByVioletName(violetName);
                break;
            default:
                finish();
                break;
        }
        return violet;
    }

    public void onClickChangeFavourite(View view) {
        if (favouriteViolet == null) {
            viewModel.insertFavouriteViolet(new FavouriteViolet(violet));
            Toast.makeText(this, R.string.add_to_favourite, Toast.LENGTH_SHORT).show();
        } else {
            viewModel.deleteFavouriteViolet(favouriteViolet);
            Toast.makeText(this, R.string.remove_from_favourite, Toast.LENGTH_SHORT).show();
        }
        setFavourite();
    }

    private void setFavourite() {
        favouriteViolet = viewModel.getFavouriteVioletByVioletName(violet.getVioletName());
        if (favouriteViolet == null) {
            imageViewAddToFavouriteC.setImageResource(R.drawable.favourite_add_to);
            imageViewAddToFavouriteBottomC.setImageResource(R.drawable.favourite_add_to);
        } else {
            imageViewAddToFavouriteC.setImageResource(R.drawable.favourite_remove);
            imageViewAddToFavouriteBottomC.setImageResource(R.drawable.favourite_remove);
        }
    }
}
