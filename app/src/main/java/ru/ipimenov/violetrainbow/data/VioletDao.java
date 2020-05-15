package ru.ipimenov.violetrainbow.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface VioletDao {

    @Query("SELECT * FROM violets")
    LiveData<List<Violet>> getAllRuUaViolets();

    @Query("SELECT * FROM foreign_violets")
    LiveData<List<ForeignViolet>> getAllForeignViolets();

    @Query("SELECT * FROM mini_violets")
    LiveData<List<MiniViolet>> getAllMiniViolets();

    @Query("SELECT * FROM favourite_violets")
    LiveData<List<FavouriteViolet>> getAllFavouriteViolets();

    @Query("SELECT * FROM violets WHERE violetName == :violetName")
    Violet getRuUaVioletByVioletName(String violetName);

    @Query("SELECT * FROM foreign_violets WHERE violetName == :violetName")
    ForeignViolet getForeignVioletByVioletName(String violetName);

    @Query("SELECT * FROM mini_violets WHERE violetName == :violetName")
    MiniViolet getMiniVioletByVioletName(String violetName);

    @Query("SELECT * FROM favourite_violets WHERE violetName == :violetName")
    FavouriteViolet getFavouriteVioletByVioletName(String violetName);

    @Query("DELETE FROM violets")
    void deleteAllRuUaViolets();

    @Query("DELETE FROM foreign_violets")
    void deleteAllForeignViolets();

    @Query("DELETE FROM mini_violets")
    void deleteAllMiniViolets();

    @Query("DELETE FROM favourite_violets")
    void deleteAllFavouriteViolets();

    @Insert
    void insertRuUaViolet(Violet violet);

    @Insert
    void insertForeignViolet(ForeignViolet violet);

    @Insert
    void insertMiniViolet(MiniViolet violet);

    @Insert
    void insertFavouriteViolet(FavouriteViolet violet);

    @Delete
    void deleteRuUaViolet(Violet violet);

    @Delete
    void deleteForeignViolet(ForeignViolet violet);

    @Delete
    void deleteMiniViolet(MiniViolet violet);

    @Delete
    void deleteFavouriteViolet(FavouriteViolet violet);
}
