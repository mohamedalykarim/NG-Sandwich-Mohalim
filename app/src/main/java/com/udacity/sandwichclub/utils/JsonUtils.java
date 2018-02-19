package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        // the sandwich variable that will return
        Sandwich sandwich;

        // I'll use these variables to  sandwich
        String mainName, placeOfOrigin, description, image;
        List<String> alsoKnownAs = new ArrayList<>();
        List<String> ingredients = new ArrayList<>();

        try {
            // Main JSON Object of the Sandwich item
            JSONObject mainJsonObject = new JSONObject(json);

            // get the name object
            JSONObject nameJsonObject = mainJsonObject.getJSONObject("name");

            // get mainName
            mainName = nameJsonObject.getString("mainName");

            // get alsoKnownAs
            JSONArray alsoKnownAsJsonArray = nameJsonObject.getJSONArray("alsoKnownAs");
            for (int i=0; i<alsoKnownAsJsonArray.length();i++){
                alsoKnownAs.add(alsoKnownAsJsonArray.getString(i));
            }

            // get placeOfOrigin
            placeOfOrigin = mainJsonObject.getString("placeOfOrigin");

            // get description
            description = mainJsonObject.getString("description");

            // get image
            image = mainJsonObject.getString("image");

            // get ingredients
            JSONArray ingredientsJsonArray = mainJsonObject.getJSONArray("ingredients");
            for (int i=0; i<ingredientsJsonArray.length();i++){
                ingredients.add(ingredientsJsonArray.getString(i));
            }

            // initiate sandwich
            sandwich = new Sandwich(mainName,alsoKnownAs,placeOfOrigin,description,image,ingredients);



        } catch (JSONException e) {
            return null;
        }

        return sandwich;
    }
}
