package com.udacity.sandwichclub.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String DESCRIPTION = "description";
    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";


    public static Sandwich parseSandwichJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        Sandwich sandwich = new Sandwich();
        String description = null;
        List<String> alsoKnownAs = new ArrayList<>();
        List<String> ingredients = new ArrayList<>();
        String image = null;
        String mainName = null;
        String placeOfOrigin = null;
        try {
            JSONObject sandwichObject = new JSONObject(json);
            description = sandwichObject.optString(DESCRIPTION);

            JSONObject name = sandwichObject.getJSONObject(NAME);
            if (name != null) {
                mainName = name.optString(MAIN_NAME);
                alsoKnownAs = extractToList(name.getJSONArray(ALSO_KNOWN_AS));
            }
            image = sandwichObject.optString(IMAGE);
            ingredients = extractToList(sandwichObject.getJSONArray(INGREDIENTS));
            placeOfOrigin = sandwichObject.optString(PLACE_OF_ORIGIN);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sandwich.setDescription(description);
        sandwich.setAlsoKnownAs(alsoKnownAs);
        sandwich.setImage(image);
        sandwich.setIngredients(ingredients);
        sandwich.setMainName(mainName);
        sandwich.setPlaceOfOrigin(placeOfOrigin);

        return sandwich;
    }

    private static @NonNull
    List<String> extractToList(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>();
        if (jsonArray != null) {
            for (int position = 0; position < jsonArray.length(); position++) {
                list.add(jsonArray.getString(position));
            }
        }
        return list;
    }

    public static String getStringFromList(List<String> list) {
        StringBuilder builder = new StringBuilder();
        int itemCount = 0;
        for (String item : list) {
            builder.append(item);
            itemCount++;
            if (itemCount < list.size()) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }

}
