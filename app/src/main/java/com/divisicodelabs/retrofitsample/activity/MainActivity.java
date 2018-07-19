package com.divisicodelabs.retrofitsample.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.widget.Toast;

import com.divisicodelabs.retrofitsample.R;
import com.divisicodelabs.retrofitsample.adapter.MoviesAdapter;
import com.divisicodelabs.retrofitsample.model.Movie;
import com.divisicodelabs.retrofitsample.model.MoviesResponse;
import com.divisicodelabs.retrofitsample.rest.ApiClient;
import com.divisicodelabs.retrofitsample.rest.ApiInterface;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private final static String API_KEY = "e53249564abad5cf3b4c348de0c26aee";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (API_KEY.isEmpty()){
            Toast.makeText(getApplicationContext(),"please obtain your API_KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
            return;
        }
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_movie);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        retrofit2.Call<MoviesResponse> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(retrofit2.Call<MoviesResponse> call, Response<MoviesResponse> response) {
                int statusCode = response.code();
                List<Movie> movies = response.body().getResults();
                recyclerView.setAdapter(new MoviesAdapter(movies,R.layout.list_item_movie,getApplicationContext()));
//                Log.d(TAG, "Number of movies received: " + movies.size());

            }

            @Override
            public void onFailure(retrofit2.Call<MoviesResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }
}
