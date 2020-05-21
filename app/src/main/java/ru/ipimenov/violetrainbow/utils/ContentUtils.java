package ru.ipimenov.violetrainbow.utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.ipimenov.violetrainbow.data.Violet;

public class ContentUtils {

    // Выражение для выделения в строку единичной фиалки
    private static final String REG_EXP_VIOLET = "image\">(.*?)</div></div>";
    // Выражение для получения id фотографии если нет фото, то = 0
//    private static final String REG_EXP_ID = "x\\[id_(.*?)\\]\"";
    // Выражение для получения полного названия (вместе с селекционером)
    private static final String REG_EXP_NAME_WITH_BREEDER = "<h3>(.*?)</h3>";
    // Выражение для получения селекционера
    private static final String REG_EXP_BREEDER = "\\((.*?)\\)";
    // Выражение для получения названия
    private static final String REG_EXP_NAME = "(.*?) \\(";
    // Выражение для получения года и описания
//    private static final String REG_EXP_YEAR_WITH_OVERVIEW = "</h3><p>(.*?)</p>";
    private static final String REG_EXP_YEAR_WITH_OVERVIEW = "<p>(.*?)</p>";
    // Выражение для получения ссылки на миниатюру
    private static final String REG_EXP_THUMBNAIL_PATH = "<img src=\"(.*?)\"";
    // Выражение для получения ссылки на оригинальную фотографию
    private static final String REG_EXP_IMAGE_PATH = "href=\"(.*?)\"";
    // Ссылка на заглушку
    private static final String PATH_TO_BLANC = "/images/imagesizer/russian-ukainian-selection/zaglushka-w.jpg";

//    private static int violetCounterId = 0;

    public static ArrayList<Violet> getVioletsFromContent(String content) { // content - резултат метода NetworkUtils.getContentFromNetwork(каталог, страница)
//        int violetCounterId = 0;
        ArrayList<Violet> result = new ArrayList<>();
        if (content == null) {
            return null;
        }

        Pattern patternViolet = Pattern.compile(REG_EXP_VIOLET);
        Matcher matcherViolet = patternViolet.matcher(content);
        while (matcherViolet.find()) {
            String violetItem = matcherViolet.group(1);

//            Pattern patternId = Pattern.compile(REG_EXP_ID);
//            Matcher matcherId = patternId.matcher(violetItem);
//            int violetId = Integer.parseInt(matcherId.find() ? matcherId.group(1) : "0");

            Pattern patternNameWithBreeder = Pattern.compile(REG_EXP_NAME_WITH_BREEDER);
            Matcher matcherNameWithBreeder = patternNameWithBreeder.matcher(violetItem);
            String nameWithBreeder = matcherNameWithBreeder.find() ? matcherNameWithBreeder.group(1) : " ";

            Pattern patternBreeder = Pattern.compile(REG_EXP_BREEDER);
            Matcher matcherBreeder = patternBreeder.matcher(nameWithBreeder);
            String violetBreeder = matcherBreeder.find() ? matcherBreeder.group(1) : " ";

            Pattern patternName = Pattern.compile(REG_EXP_NAME);
            Matcher matcherName = patternName.matcher(nameWithBreeder);
            String violetName = matcherName.find() ? matcherName.group(1) : nameWithBreeder;

            Pattern patternYearWithOverview = Pattern.compile(REG_EXP_YEAR_WITH_OVERVIEW);
            Matcher matcherYearWithOverview = patternYearWithOverview.matcher(violetItem);
            String yearWithOverview = matcherYearWithOverview.find() ? matcherYearWithOverview.group(1) : " ";

            String violetYear;
            String violetOverview;
            if (yearWithOverview != null && yearWithOverview.length() > 1) {
                violetYear = yearWithOverview.substring(4, 5).equals("г") ? yearWithOverview.substring(0, 6) : " ";
                if (!violetYear.equals(" ")) {
                    violetOverview = yearWithOverview.length() != 6 ? yearWithOverview.substring(7) : " ";
                } else {
                    violetOverview = yearWithOverview;
                }
            } else {
                violetYear = " ";
                violetOverview = " ";
            }

            Pattern patternThumbnailPath = Pattern.compile(REG_EXP_THUMBNAIL_PATH);
            Matcher matcherThumbnailPath = patternThumbnailPath.matcher(violetItem);
            String violetThumbnailPath = matcherThumbnailPath.find() ? NetworkUtils.getBaseUrl() + matcherThumbnailPath.group(1) : "";

            Pattern patternImagePath = Pattern.compile(REG_EXP_IMAGE_PATH);
            Matcher matcherImagePath = patternImagePath.matcher(violetItem);
            String violetImagePath = matcherImagePath.find() ? NetworkUtils.getBaseUrl() + matcherImagePath.group(1) : NetworkUtils.getBaseUrl() + PATH_TO_BLANC;

            Violet violet = null;
            if (violetName != null) {
                violet = new Violet(violetName, violetBreeder, violetYear, violetOverview, violetThumbnailPath, violetImagePath);
            }
//            violetCounterId++;
            result.add(violet);
//            Log.i("My", violet.getVioletCounterId() + "");
        }
        return result;
    }
}
