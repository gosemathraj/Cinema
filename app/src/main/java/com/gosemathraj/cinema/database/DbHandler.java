package com.gosemathraj.cinema.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.gosemathraj.cinema.models.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iamsparsh on 12/5/16.
 */
public class DbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "moviedb";
    private static final String TABLE_NAME = "movies";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_POSTERPATH = "poster_path";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_RELEASEDATE = "release_date";
    private static final String KEY_BACKDROPPATH = "backdrop_path";
    private static final String KEY_POPULARITY  = "popularity";
    private static final String KEY_VOTECOUNT = "vote_count";
    private static final String KEY_VOTEAVERAGE = "vote_average";

    public DbHandler(Context context){

        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_MOVIES_TABLE = "CREATE TABLE "+TABLE_NAME +"("+KEY_ID+" TEXT PRIMARY KEY,"+KEY_TITLE+" TEXT,"+
                KEY_POSTERPATH+" TEXT,"+KEY_OVERVIEW+" TEXT,"+KEY_RELEASEDATE+" TEXT,"+KEY_BACKDROPPATH+" TEXT,"+
                KEY_POPULARITY+" TEXT,"+KEY_VOTECOUNT+" TEXT,"+KEY_VOTEAVERAGE+" TEXT"+")";
        db.execSQL(CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void addMovie(Movie movie){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,movie.getId());
        values.put(KEY_TITLE,movie.getTitle());
        values.put(KEY_POSTERPATH,movie.getPoster_path());
        values.put(KEY_OVERVIEW,movie.getOverview());
        values.put(KEY_RELEASEDATE,movie.getRelease_date());
        values.put(KEY_BACKDROPPATH,movie.getBackdrop_path());
        values.put(KEY_POPULARITY,movie.getPopularity());
        values.put(KEY_VOTECOUNT,movie.getVote_count());
        values.put(KEY_VOTEAVERAGE,movie.getVote_average());

        db.insert(TABLE_NAME, null, values);
        db.close();

    }

    public List<Movie> getAllMovies(){

        List<Movie> movielist = new ArrayList<>();

        String query = "SELECT * FROM "+TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do{

                Movie m = new Movie();
                m.setId(cursor.getString(0));
                m.setTitle(cursor.getString(1));
                m.setPoster_path(cursor.getString(2));
                m.setOverview(cursor.getString(3));
                m.setRelease_date(cursor.getString(4));
                m.setBackdrop_path(cursor.getString(5));
                m.setPopularity(cursor.getString(6));
                m.setVote_count(cursor.getString(7));
                m.setVote_average(cursor.getString(8));

                movielist.add(m);
            }while(cursor.moveToNext());
        }

        return movielist;

    }

    public boolean isMoviePresent(String id){

        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID+" = "+id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.getCount() <= 0){

            cursor.close();
            return false;
        }

        cursor.close();
        return true;
    }

    public void deleteMovie(Movie movie){

        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NAME,KEY_ID+ " =?",new String[]{movie.getId()} );

        db.close();
    }
}
