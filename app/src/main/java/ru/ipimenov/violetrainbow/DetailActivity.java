package ru.ipimenov.violetrainbow;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.squareup.picasso.Picasso;

import ru.ipimenov.violetrainbow.data.FavouriteViolet;
import ru.ipimenov.violetrainbow.data.MainViewModel;
import ru.ipimenov.violetrainbow.data.Violet;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageViewVioletImage;
    private TextView textViewVioletName;
    private TextView textViewVioletBreeder;
    private TextView textViewVioletYear;
    private TextView textViewVioletOverview;
    private ImageView imageViewAddToFavourite;

    private String violetName;
    private MainViewModel viewModel;
    private Violet violet;
    private FavouriteViolet favouriteViolet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageViewVioletImage = findViewById(R.id.imageViewVioletImage);
        textViewVioletName = findViewById(R.id.textViewVioletName);
        textViewVioletBreeder = findViewById(R.id.textViewVioletBreeder);
        textViewVioletYear = findViewById(R.id.textViewVioletYear);
        textViewVioletOverview = findViewById(R.id.textViewVioletOverview);
        imageViewAddToFavourite = findViewById(R.id.imageViewAddToFavourite);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("violetName")) {
            violetName = intent.getStringExtra("violetName");
        } else {
            finish();
        }

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        violet = viewModel.getRuUaVioletByVioletName(violetName);

        Picasso.get().load(violet.getVioletImagePath()).into(imageViewVioletImage);
        textViewVioletName.setText(violet.getVioletName());
        textViewVioletBreeder.setText(violet.getVioletBreeder());
        textViewVioletYear.setText(violet.getVioletYear());
        textViewVioletOverview.setText(violet.getVioletOverview());
//        setFavourite();
    }

//    public void onClickChangeFavourite(View view) {
//        if (favouriteViolet == null) {
//            viewModel.insertFavouriteViolet(new FavouriteViolet(violet));
//            Toast.makeText(this, R.string.add_to_favourite, Toast.LENGTH_SHORT).show();
//        } else {
//            viewModel.deleteFavouriteViolet(favouriteViolet);
//            Toast.makeText(this, R.string.remove_from_favourite, Toast.LENGTH_SHORT).show();
//        }
//        setFavourite();
//    }

//    private void setFavourite() {
//        favouriteViolet = viewModel.getFavouriteVioletByVioletName(violet.getVioletName());
//        if (favouriteViolet == null) {
//            imageViewAddToFavourite.setImageResource(R.drawable.favourite_add_to);
//        } else {
//            imageViewAddToFavourite.setImageResource(R.drawable.favourite_remove);
//        }
//    }
}
