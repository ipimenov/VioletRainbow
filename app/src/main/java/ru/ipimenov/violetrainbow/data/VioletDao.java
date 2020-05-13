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
    LiveData<List<Violet>> getAllViolets();

    @Query("SELECT * FROM violets WHERE violetName == :violetName")
    Violet getVioletByVioletName(String violetName);

    @Query("DELETE FROM violets")
    void deleteAllViolets();

    @Insert
    void insertViolet(Violet violet);

    @Delete
    void deleteViolet(Violet violet);
}
