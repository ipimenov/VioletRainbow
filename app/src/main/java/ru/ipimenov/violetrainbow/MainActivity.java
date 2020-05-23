package ru.ipimenov.violetrainbow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import ru.ipimenov.violetrainbow.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayoutTabs;
    private Toolbar toolBar;
    private ViewPager viewPager;
//------------------
//    private Adapter adapter;
//    private CatalogFragment ru_uaFragment;
//    private CatalogFragment foreignFragment;
//    private CatalogFragment miniFragment;

//    private static String RU_UA_FRAGMENT_INSTANCE_NAME = "ru_uaFragment";
//    private static String FOREIGN_FRAGMENT_INSTANCE_NAME = "foreignFragment";
//    private static String MINI_FRAGMENT_INSTANCE_NAME = "miniFragment";
//    CatalogFragment ru_uaFragment = null;
//    CatalogFragment foreignFragment = null;
//    CatalogFragment miniFragment = null;
    //-------------------------
    @SuppressLint("StaticFieldLeak")
    public static ProgressBar progressBarLoading;

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
        setContentView(R.layout.activity_main);
        //--------------------------------------
//        adapter = new Adapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//        ru_uaFragment = new CatalogFragment(NetworkUtils.RU_UA_SELECTION, 1);
//        foreignFragment = new CatalogFragment(NetworkUtils.FOREIGN_SELECTION, 1);
//        miniFragment = new CatalogFragment(NetworkUtils.MINI_SELECTION, 1);
//        adapter.addFragment(ru_uaFragment, "Российско-Украинская селекция");
//        adapter.addFragment(foreignFragment, "Зарубежная селекция");
//        adapter.addFragment(miniFragment, "Миниатюры и трейлеры");
        viewPager = findViewById(R.id.viewPager);
//        viewPager.setAdapter(adapter);
        //---------------------------------------
        progressBarLoading = findViewById(R.id.progressBarLoading);
        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        setupViewPager(viewPager);

        tabLayoutTabs = findViewById(R.id.tabLayoutTabs);
        tabLayoutTabs.setTabTextColors(getResources().getColor(R.color.colorWhite), getResources().getColor(R.color.colorAccent));
        tabLayoutTabs.setupWithViewPager(viewPager);
    }

    // Добавляем фрагменты во вкладки
    private void setupViewPager(ViewPager viewPager) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        ru_uaFragment = (CatalogFragment) fragmentManager.findFragmentByTag(RU_UA_FRAGMENT_INSTANCE_NAME);
//        foreignFragment = (CatalogFragment) fragmentManager.findFragmentByTag(FOREIGN_FRAGMENT_INSTANCE_NAME);
//        miniFragment = (CatalogFragment) fragmentManager.findFragmentByTag(MINI_FRAGMENT_INSTANCE_NAME);
//        Adapter fragmentAdapter = new Adapter(fragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//        if (ru_uaFragment == null ) {
//            ru_uaFragment = new CatalogFragment(NetworkUtils.RU_UA_SELECTION, 1);
//        }
//        if (foreignFragment == null ) {
//            foreignFragment = new CatalogFragment(NetworkUtils.FOREIGN_SELECTION, 1);
//        }
//        if (miniFragment == null ) {
//            miniFragment = new CatalogFragment(NetworkUtils.MINI_SELECTION, 1);
//        }
//        fragmentAdapter.addFragment(ru_uaFragment, "Российско-Украинская селекция");
//        fragmentAdapter.addFragment(foreignFragment, "Зарубежная селекция");
//        fragmentAdapter.addFragment(miniFragment, "Миниатюры и трейлеры");
//        viewPager.setAdapter(fragmentAdapter);
        Adapter adapter = new Adapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//        CatalogFragment ru_uaFragment = new CatalogFragment(NetworkUtils.RU_UA_SELECTION, 1);
//        CatalogFragment foreignFragment = new CatalogFragment(NetworkUtils.FOREIGN_SELECTION, 1);
//        CatalogFragment miniFragment = new CatalogFragment(NetworkUtils.MINI_SELECTION, 1);
//        adapter.addFragment(ru_uaFragment, "Российско-Украинская селекция");
//        adapter.addFragment(foreignFragment, "Зарубежная селекция");
//        adapter.addFragment(miniFragment, "Миниатюры и трейлеры");
        adapter.addFragment(new CatalogFragment(NetworkUtils.RU_UA_SELECTION), "Российско-Украинская селекция");
        adapter.addFragment(new CatalogFragment(NetworkUtils.FOREIGN_SELECTION), "Зарубежная селекция");
        adapter.addFragment(new CatalogFragment(NetworkUtils.MINI_SELECTION), "Миниатюры и трейлеры");
        viewPager.setAdapter(adapter);
    }

    private static class Adapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public Adapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }
    }
}
