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
            description = sandwichObject.getString("description");

            JSONObject name = sandwichObject.getJSONObject("name");
            if (name != null) {
                mainName = name.getString("mainName");
                alsoKnownAs = extractToList(name.getJSONArray("alsoKnownAs"));
            }
            image = sandwichObject.getString("image");
            ingredients = extractToList(sandwichObject.getJSONArray("ingredients"));
            placeOfOrigin = sandwichObject.getString("placeOfOrigin");
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
