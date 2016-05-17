package com.gosemathraj.cinema.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.gosemathraj.cinema.R;
import com.gosemathraj.cinema.adapters.InnerRecyclerViewCustomAdapter;
import com.gosemathraj.cinema.adapters.RecyclerViewCustomAdapter;
import com.gosemathraj.cinema.fragments.Fragment_All_Movies;
import com.gosemathraj.cinema.fragments.Fragment_Movies_Details;
import com.gosemathraj.cinema.fragments.Fragment_Movies_Home;
import com.gosemathraj.cinema.models.Movie;

public class MainActivity extends AppCompatActivity implements InnerRecyclerViewCustomAdapter.OnAdapterItemClickedListener,RecyclerViewCustomAdapter.OnSeeMoreClickedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        fragmentManager = getSupportFragmentManager();
        Fragment f1 = fragmentManager.findFragmentById(R.id.parent_framelayout);
        if(f1 == null){
            fragmentManager.beginTransaction().add(R.id.parent_framelayout,new Fragment_Movies_Home()).commit();
        }

        setNavDrawer();
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onInnerItemClicked(Movie m) {

        Fragment_Movies_Details fragment_movies_details = (Fragment_Movies_Details) getSupportFragmentManager().findFragmentById(R.id.movie_details_fragment);
        if(fragment_movies_details == null){

            Intent intent = new Intent(this, MovieDetailsActivity.class);
            intent.putExtra("movie",m);
            startActivity(intent);
        }else{
            Movie x = m;
            fragment_movies_details.updateLayout(m);
        }
    }

    public void setNavDrawer() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                navigationView.setCheckedItem(item.getItemId());
                drawerLayout.closeDrawers();

                switch(item.getItemId()){

                    case R.id.home:
                        fragmentManager.beginTransaction().replace(R.id.parent_framelayout,new Fragment_Movies_Home()).addToBackStack("null").commit();
                        break;

                    case R.id.favourites:

                        Bundle bundle_three = new Bundle();
                        bundle_three.putString("variable", "favourites");
                        Fragment_All_Movies fragment_all_movies_three = new Fragment_All_Movies();
                        fragment_all_movies_three.setArguments(bundle_three);
                        fragmentManager.beginTransaction().replace(R.id.parent_framelayout, fragment_all_movies_three).addToBackStack("back").commit();
                        break;


                    case R.id.watched:
                        Toast.makeText(getApplicationContext(),"watched is clicked",Toast.LENGTH_LONG).show();
                        break;

                    case R.id.rate:
                        Bundle bundle_one = new Bundle();
                        bundle_one.putString("variable", "Highest rated");
                        Fragment_All_Movies fragment_all_movies_one = new Fragment_All_Movies();
                        fragment_all_movies_one.setArguments(bundle_one);
                        fragmentManager.beginTransaction().replace(R.id.parent_framelayout, fragment_all_movies_one).addToBackStack("back").commit();
                        break;

                    case R.id.popularity:
                        Bundle bundle_two = new Bundle();
                        bundle_two.putString("variable", "Popular");
                        Fragment_All_Movies fragment_all_movies_two = new Fragment_All_Movies();
                        fragment_all_movies_two.setArguments(bundle_two);
                        fragmentManager.beginTransaction().replace(R.id.parent_framelayout,fragment_all_movies_two).addToBackStack("back").commit();

                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onseemoreclicked(String s) {

        Bundle bundle = new Bundle();
        bundle.putString("variable",s);
        Fragment_All_Movies fragment_all_movies = new Fragment_All_Movies();
        fragment_all_movies.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.parent_framelayout,fragment_all_movies).addToBackStack("null").commit();
    }
}
