package com.udacity.sandwichclub;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        Intent intent = getIntent();
        int position = 0;
        if (intent == null) {
            closeOnError();
        } else {
            position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        }
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

        setTitle(sandwich.getMainName());
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if (TextUtils.isEmpty(sandwich.getPlaceOfOrigin())) {
            binding.placeOfOriginLabel.setVisibility(View.GONE);
            binding.originTv.setVisibility(View.GONE);
        } else {
            binding.placeOfOriginLabel.setVisibility(View.VISIBLE);
            binding.originTv.setVisibility(View.VISIBLE);
            binding.originTv.setText(sandwich.getPlaceOfOrigin());
        }

        if (TextUtils.isEmpty(JsonUtils.getStringFromList(sandwich.getAlsoKnownAs()))) {
            binding.alsoKnownAsLabel.setVisibility(View.GONE);
            binding.alsoKnownTv.setVisibility(View.GONE);
        } else {
            binding.alsoKnownAsLabel.setVisibility(View.VISIBLE);
            binding.alsoKnownTv.setVisibility(View.VISIBLE);
            binding.alsoKnownTv.setText(JsonUtils.getStringFromList(sandwich.getAlsoKnownAs()));
        }

        if (TextUtils.isEmpty(JsonUtils.getStringFromList(sandwich.getIngredients()))) {
            binding.ingredientsLabel.setVisibility(View.GONE);
            binding.ingredientsTv.setVisibility(View.GONE);
        } else {
            binding.ingredientsLabel.setVisibility(View.VISIBLE);
            binding.ingredientsTv.setVisibility(View.VISIBLE);
            binding.ingredientsTv.setText(JsonUtils.getStringFromList(sandwich.getIngredients()));
        }

        if (TextUtils.isEmpty(sandwich.getDescription())) {
            binding.descriptionLabel.setVisibility(View.GONE);
            binding.descriptionTv.setVisibility(View.GONE);
        } else {
            binding.descriptionLabel.setVisibility(View.VISIBLE);
            binding.descriptionTv.setVisibility(View.VISIBLE);
            binding.descriptionTv.setText(sandwich.getDescription());
        }

        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(android.R.drawable.ic_menu_report_image)
                .error(android.R.drawable.ic_delete)
                .into(binding.imageIv);
    }
}
