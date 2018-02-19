package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    // Variables that use to populate UI
    TextView descriptionTV, alsoKnownAsTV, originTV, ingredientsTV, alsoKnownAsLabelTV, ingredientsLabelTV, originLabelTV;


    Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        descriptionTV = (TextView) findViewById(R.id.description_tv);
        alsoKnownAsTV = (TextView) findViewById(R.id.also_known_tv);
        originTV = (TextView) findViewById(R.id.origin_tv);
        ingredientsTV = (TextView) findViewById(R.id.ingredients_tv);

        alsoKnownAsLabelTV = (TextView) findViewById(R.id.also_known_as_label);
        ingredientsLabelTV = (TextView) findViewById(R.id.ingredients_label);
        originLabelTV = (TextView) findViewById(R.id.origins_label);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        // check if there is no alsoKnownAs and hide it if not exists
        if (sandwich.getAlsoKnownAs().isEmpty()){
            alsoKnownAsLabelTV.setVisibility(View.GONE);
            alsoKnownAsTV.setVisibility(View.GONE);
        }

        // check if there is no Ingredients and hide it if not exists
        if (sandwich.getIngredients().isEmpty()){
            ingredientsLabelTV.setVisibility(View.GONE);
            ingredientsTV.setVisibility(View.GONE);
        }

        // check if there is no Place Of Origin and hide it if not exists
        if (sandwich.getPlaceOfOrigin().isEmpty()){
            originLabelTV.setVisibility(View.GONE);
            originTV.setVisibility(View.GONE);
        }

        // Set Description text
        descriptionTV.setText(sandwich.getDescription());

        // Set Also Known As text
        int alsoKnownAsSize = sandwich.getAlsoKnownAs().size();
        for (int i = 0; i < alsoKnownAsSize; i++){
            alsoKnownAsTV.append(sandwich.getAlsoKnownAs().get(i));
            if ((i+1) != alsoKnownAsSize){
                alsoKnownAsTV.append(", ");
            }
        }

        // Set place origins text
        originTV.setText(sandwich.getPlaceOfOrigin());

        // Set ingredients text
        int ingredientsSize = sandwich.getIngredients().size();
        for (int i = 0; i < ingredientsSize; i++){
            ingredientsTV.append(sandwich.getIngredients().get(i));
            if ((i+1) != ingredientsSize){
                ingredientsTV.append(", ");
            }
        }
    }
}
