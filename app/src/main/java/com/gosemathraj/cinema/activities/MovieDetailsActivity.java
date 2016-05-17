package com.gosemathraj.cinema.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.gosemathraj.cinema.R;
import com.gosemathraj.cinema.fragments.Fragment_Movies_Details;
import com.gosemathraj.cinema.models.Movie;

/**
 * Created by iamsparsh on 16/5/16.
 */
public class MovieDetailsActivity extends AppCompatActivity {

    private Movie m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
