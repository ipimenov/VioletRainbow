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

import com.squareup.picasso.Picasso;

import ru.ipimenov.violetrainbow.data.FavouriteViolet;
import ru.ipimenov.violetrainbow.data.MainViewModel;
import ru.ipimenov.violetrainbow.data.Violet;
import ru.ipimenov.violetrainbow.utils.NetworkUtils;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageViewVioletImage;
    private TextView textViewVioletName;
    private TextView textViewVioletBreeder;
    private TextView textViewLabelVioletYear;
    private TextView textViewVioletYear;
    private TextView textViewVioletOverview;
    private ImageView imageViewAddToFavourite;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        imageViewVioletImage = findViewById(R.id.imageViewVioletImage);
        textViewVioletName = findViewById(R.id.textViewVioletName);
        textViewVioletBreeder = findViewById(R.id.textViewVioletBreeder);
        textViewLabelVioletYear = findViewById(R.id.textViewLabelVioletYear);
        textViewVioletYear = findViewById(R.id.textViewVioletYear);
        textViewVioletOverview = findViewById(R.id.textViewVioletOverview);
        imageViewAddToFavourite = findViewById(R.id.imageViewAddToFavourite);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("violetName") && intent.hasExtra("catalog")) {
            violetName = intent.getStringExtra("violetName");
            catalog = intent.getIntExtra("catalog", -1);
        } else {
            finish();
        }

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        violet = getVioletFromCatalog();
        Picasso.get().load(violet.getVioletImagePath()).into(imageViewVioletImage);
        textViewVioletName.setText(violet.getVioletName());
        textViewVioletBreeder.setText(violet.getVioletBreeder());
        if (!violet.getVioletYear().equals(" ")) {
            textViewVioletYear.setText(violet.getVioletYear());
        } else {
            textViewLabelVioletYear.setVisibility(View.GONE);
            textViewVioletYear.setVisibility(View.GONE);
        }
        textViewVioletOverview.setText(violet.getVioletOverview());
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
            imageViewAddToFavourite.setImageResource(R.drawable.favourite_add_to);
        } else {
            imageViewAddToFavourite.setImageResource(R.drawable.favourite_remove);
        }
    }
}
