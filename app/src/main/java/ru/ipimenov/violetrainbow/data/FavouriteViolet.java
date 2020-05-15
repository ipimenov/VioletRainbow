package ru.ipimenov.violetrainbow.data;

import androidx.room.Entity;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "favourite_violets")
public class FavouriteViolet extends Violet {
    public FavouriteViolet(@NotNull String violetName, String violetBreeder, String violetYear, String violetOverview, String violetThumbnailPath, String violetImagePath) {
        super(violetName, violetBreeder, violetYear, violetOverview, violetThumbnailPath, violetImagePath);
    }
}
