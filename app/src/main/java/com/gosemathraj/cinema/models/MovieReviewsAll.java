package com.gosemathraj.cinema.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by iamsparsh on 11/5/16.
 */
public class MovieReviewsAll implements Parcelable{

    private String id;
    private String page;
    private List<MovieReviews> results;
    private String total_pages;
    private String total_results;

    public MovieReviewsAll(String id, String page, List<MovieReviews> results, String total_pages, String total_results) {
        this.id = id;
        this.page = page;
        this.results = results;
        this.total_pages = total_pages;
        this.total_results = total_results;
    }

    protected MovieReviewsAll(Parcel in) {
        id = in.readString();
        page = in.readString();
        total_pages = in.readString();
        total_results = in.readString();
        results = in.createTypedArrayList(MovieReviews.CREATOR);
    }

    public static final Creator<MovieReviewsAll> CREATOR = new Creator<MovieReviewsAll>() {
        @Override
        public MovieReviewsAll createFromParcel(Parcel in) {
            return new MovieReviewsAll(in);
        }

        @Override
        public MovieReviewsAll[] newArray(int size) {
            return new MovieReviewsAll[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<MovieReviews> getResults() {
        return results;
    }

    public void setMovieReviews(List<MovieReviews> results) {
        this.results = results;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(String total_pages) {
        this.total_pages = total_pages;
    }

    public String getTotal_results() {
        return total_results;
    }

    public void setTotal_results(String total_results) {
        this.total_results = total_results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(page);
        dest.writeString(total_pages);
        dest.writeString(total_results);
        dest.writeTypedList(results);
    }
}
