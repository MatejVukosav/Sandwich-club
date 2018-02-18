package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson( String json ) {
        Sandwich sandwich = new Sandwich();
        String description = null;
        List<String> alsoKnownAs = new ArrayList<>();
        List<String> ingredients = new ArrayList<>(  );
        String image = null;
        String mainName = null;
        String placeOfOrigin = null;
        try {
            JSONObject sandwichObject = new JSONObject( json );
            JSONObject name = sandwichObject.getJSONObject( "name" );
            description = sandwichObject.getString( "description" );
            JSONArray alsoKnownAsJsonArray = name.getJSONArray( "alsoKnownAs" );
            for ( int alsoKnownAsPosition = 0; alsoKnownAsPosition < alsoKnownAsJsonArray.length(); alsoKnownAsPosition++ ) {
                alsoKnownAs.add( alsoKnownAsJsonArray.getString( alsoKnownAsPosition ) );
            }
            image = sandwichObject.getString( "image" );
            JSONArray ingredientsJsonArray = sandwichObject.getJSONArray( "ingredients" );
            for ( int ingredientPosition = 0; ingredientPosition < ingredientsJsonArray.length(); ingredientPosition++ ) {
                ingredients.add( ingredientsJsonArray.getString( ingredientPosition ) );
            }
            mainName = name.getString( "mainName" );
            placeOfOrigin = sandwichObject.getString( "placeOfOrigin" );
        } catch ( JSONException e ) {
            e.printStackTrace();
        }
        sandwich.setDescription( description );
        sandwich.setAlsoKnownAs( alsoKnownAs );
        sandwich.setImage( image );
        sandwich.setIngredients( ingredients );
        sandwich.setMainName( mainName );
        sandwich.setPlaceOfOrigin( placeOfOrigin );

        return sandwich;
    }
}
