package ru.ipimenov.violetrainbow.data;

import androidx.room.Entity;
import androidx.room.Ignore;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "mini_violets")
public class MiniViolet extends Violet {

    public MiniViolet(@NotNull String violetName, String violetBreeder, String violetYear, String violetOverview, String violetThumbnailPath, String violetImagePath) {
        super(violetName, violetBreeder, violetYear, violetOverview, violetThumbnailPath, violetImagePath);
    }

    @Ignore
    public MiniViolet(Violet violet) {
        super(violet.getVioletName(), violet.getVioletBreeder(), violet.getVioletYear(), violet.getVioletOverview(), violet.getVioletThumbnailPath(), violet.getVioletImagePath());
    }
}
