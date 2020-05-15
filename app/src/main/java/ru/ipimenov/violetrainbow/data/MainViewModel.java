package ru.ipimenov.violetrainbow.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {

    private static VioletDatabase database;
    private LiveData<List<Violet>> ruUaViolets;
    private LiveData<List<ForeignViolet>> foreignViolets;
    private LiveData<List<MiniViolet>> miniViolets;
    private LiveData<List<FavouriteViolet>> favouriteViolets;

    public MainViewModel(@NonNull Application application) {
        super(application);
        database = VioletDatabase.getInstance(getApplication());
        ruUaViolets = database.violetDao().getAllRuUaViolets();
        foreignViolets = database.violetDao().getAllForeignViolets();
        miniViolets = database.violetDao().getAllMiniViolets();
        favouriteViolets = database.violetDao().getAllFavouriteViolets();
    }

    public LiveData<List<Violet>> getRuUaViolets() {
        return ruUaViolets;
    }

    public LiveData<List<ForeignViolet>> getForeignViolets() {
        return foreignViolets;
    }

    public LiveData<List<MiniViolet>> getMiniViolets() {
        return miniViolets;
    }

    public LiveData<List<FavouriteViolet>> getFavouriteViolets() {
        return favouriteViolets;
    }

    public Violet getRuUaVioletByVioletName(String violetName) {
        try {
            return new GetRuUaVioletByNameTask().execute(violetName).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ForeignViolet getForeignVioletByVioletName(String violetName) {
        try {
            return new GetForeignVioletByNameTask().execute(violetName).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public MiniViolet getMiniVioletByVioletName(String violetName) {
        try {
            return new GetMiniVioletByNameTask().execute(violetName).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public FavouriteViolet getFavouriteVioletByVioletName(String violetName) {
        try {
            return new GetFavouriteVioletByNameTask().execute(violetName).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteAllRuUaViolets() {
        new DeleteAllRuUaVioletsTask().execute();
    }

    public void deleteAllForeignViolets() {
        new DeleteAllForeignVioletsTask().execute();
    }

    public void deleteAllMiniViolets() {
        new DeleteAllMiniVioletsTask().execute();
    }

    public void deleteAllFavouriteViolets() {
        new DeleteAllFavouriteVioletsTask().execute();
    }

    public void insertRuUaViolet(Violet violet) {
        new InsertRuUaVioletTask().execute(violet);
    }

    public void insertForeignViolet(ForeignViolet violet) {
        new InsertForeignVioletTask().execute(violet);
    }

    public void insertMiniViolet(MiniViolet violet) {
        new InsertMiniVioletTask().execute(violet);
    }

    public void insertFavouriteViolet(FavouriteViolet violet) {
        new InsertFavouriteVioletTask().execute(violet);
    }

    public void deleteRuUaViolet(Violet violet) {
        new DeleteRuUaVioletTask().execute(violet);
    }

    public void deleteForeignViolet(ForeignViolet violet) {
        new DeleteForeignVioletTask().execute(violet);
    }

    public void deleteMiniViolet(MiniViolet violet) {
        new DeleteMiniVioletTask().execute(violet);
    }

    public void deleteFavouriteViolet(FavouriteViolet violet) {
        new DeleteFavouriteVioletTask().execute(violet);
    }

    private static class DeleteFavouriteVioletTask extends AsyncTask<FavouriteViolet, Void, Void> {

        @Override
        protected Void doInBackground(FavouriteViolet... violets) {
            if (violets != null && violets.length > 0) {
                database.violetDao().deleteFavouriteViolet(violets[0]);
            }
            return null;
        }
    }

    private static class DeleteMiniVioletTask extends AsyncTask<MiniViolet, Void, Void> {

        @Override
        protected Void doInBackground(MiniViolet... violets) {
            if (violets != null && violets.length > 0) {
                database.violetDao().deleteMiniViolet(violets[0]);
            }
            return null;
        }
    }

    private static class DeleteForeignVioletTask extends AsyncTask<ForeignViolet, Void, Void> {

        @Override
        protected Void doInBackground(ForeignViolet... violets) {
            if (violets != null && violets.length > 0) {
                database.violetDao().deleteForeignViolet(violets[0]);
            }
            return null;
        }
    }

    private static class InsertFavouriteVioletTask extends AsyncTask<FavouriteViolet, Void, Void> {

        @Override
        protected Void doInBackground(FavouriteViolet... violets) {
            if (violets != null && violets.length > 0) {
                database.violetDao().insertFavouriteViolet(violets[0]);
            }
            return null;
        }
    }

    private static class InsertMiniVioletTask extends AsyncTask<MiniViolet, Void, Void> {

        @Override
        protected Void doInBackground(MiniViolet... violets) {
            if (violets != null && violets.length > 0) {
                database.violetDao().insertMiniViolet(violets[0]);
            }
            return null;
        }
    }

    private static class InsertForeignVioletTask extends AsyncTask<ForeignViolet, Void, Void> {

        @Override
        protected Void doInBackground(ForeignViolet... violets) {
            if (violets != null && violets.length > 0) {
                database.violetDao().insertForeignViolet(violets[0]);
            }
            return null;
        }
    }

    private static class DeleteAllFavouriteVioletsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            database.violetDao().deleteAllFavouriteViolets();
            return null;
        }
    }

    private static class DeleteAllMiniVioletsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            database.violetDao().deleteAllMiniViolets();
            return null;
        }
    }

    private static class DeleteAllForeignVioletsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            database.violetDao().deleteAllForeignViolets();
            return null;
        }
    }

    private static class GetFavouriteVioletByNameTask extends AsyncTask<String, Void, FavouriteViolet> {

        @Override
        protected FavouriteViolet doInBackground(String... strings) {
            if (strings != null && strings.length > 0) {
                database.violetDao().getFavouriteVioletByVioletName(strings[0]);
            }
            return null;
        }
    }

    private static class GetMiniVioletByNameTask extends AsyncTask<String, Void, MiniViolet> {

        @Override
        protected MiniViolet doInBackground(String... strings) {
            if (strings != null && strings.length > 0) {
                database.violetDao().getMiniVioletByVioletName(strings[0]);
            }
            return null;
        }
    }

    private static class GetForeignVioletByNameTask extends AsyncTask<String, Void, ForeignViolet> {

        @Override
        protected ForeignViolet doInBackground(String... strings) {
            if (strings != null && strings.length > 0) {
                database.violetDao().getForeignVioletByVioletName(strings[0]);
            }
            return null;
        }
    }

    private static class DeleteRuUaVioletTask extends AsyncTask<Violet, Void, Void> {

        @Override
        protected Void doInBackground(Violet... violets) {
            if (violets != null && violets.length > 0) {
                database.violetDao().deleteRuUaViolet(violets[0]);
            }
            return null;
        }
    }

    private static class InsertRuUaVioletTask extends AsyncTask<Violet, Void, Void> {

        @Override
        protected Void doInBackground(Violet... violets) {
            if (violets != null && violets.length > 0) {
                database.violetDao().insertRuUaViolet(violets[0]);
            }
            return null;
        }
    }

    private static class DeleteAllRuUaVioletsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            database.violetDao().deleteAllRuUaViolets();
            return null;
        }
    }

    private static class GetRuUaVioletByNameTask extends AsyncTask<String, Void, Violet> {

        @Override
        protected Violet doInBackground(String... strings) {
            if (strings != null && strings.length > 0) {
                return database.violetDao().getRuUaVioletByVioletName(strings[0]);
            }
            return null;
        }
    }
}
