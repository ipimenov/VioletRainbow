package ru.ipimenov.violetrainbow.data;

import androidx.room.Entity;
import androidx.room.Ignore;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "foreign_violets")
public class ForeignViolet extends Violet {

    public ForeignViolet(@NotNull String violetName, String violetBreeder, String violetYear,
                         String violetOverview, String violetThumbnailPath, String violetImagePath) {
        super(violetName, violetBreeder, violetYear, violetOverview, violetThumbnailPath, violetImagePath);
    }

    @Ignore
    public ForeignViolet(Violet violet) {
        super(violet.getVioletName(), violet.getVioletBreeder(), violet.getVioletYear(),
                violet.getVioletOverview(), violet.getVioletThumbnailPath(), violet.getVioletImagePath());
    }
}
