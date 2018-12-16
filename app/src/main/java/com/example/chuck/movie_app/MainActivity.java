package com.example.chuck.movie_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.chuck.movie_app.data.MovieItem;
import com.example.chuck.movie_app.data.MovieList;
import com.example.chuck.movie_app.data.ResponseInfo;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView textView;
    EditText editText;
    MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.TextView);
        editText = findViewById(R.id.editText);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.removeItemAll();
                String query = editText.getText().toString();
                if(query.matches("")) {
                    Toast.makeText(getApplicationContext(), "검색어를 한 글자 이상 입력해주세요", Toast.LENGTH_LONG).show();
                } else {
                    requestMovieList(query);
                }
            }
        });

        if (AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MovieAdapter(getApplicationContext());

        recyclerView.setAdapter(adapter);

        // add recyclerView to ItemClickListener
        adapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MovieAdapter.ViewHolder holder, View view, int position) {
                MovieItem item = adapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, LinkPageActivity.class);
                intent.putExtra("url", item.getLink());
                startActivity(intent);
            }
        });
    }

    // request JSON with StringRequest of Volley
    public void requestMovieList(String q) {
        String url = AppHelper.host;
        String query = q;
        int display = 20;
        url += "?query=" + query + "&display=" + display;

        StringRequest request = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("response", response);

                    processResponse(response); // send JSON itself with processResponse
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("json response: ", "" + error.getMessage());
                }
            })
            // Add headers for using Naver API
            {
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("X-Naver-Client-Id", "XcBZjDi4kBTF6AjblV66");
                    headers.put("X-Naver-Client-Secret", "ewzKD436H5");
                    return headers;
                }
            };

        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
        Log.d("영화목록 요청 보냄", "");
    }

    // process JSON with Gson
    public void processResponse(String response) {
        Gson gson = new Gson();

        ResponseInfo info = gson.fromJson(response, ResponseInfo.class);
        MovieList movieList = gson.fromJson(response, MovieList.class);
        Log.d("영화 갯수", Integer.toString(movieList.items.size()));

        if(movieList.items.size() == 0) {
            println("검색 결과가 없습니다.");
        } else {
            for(int i = 0; i < movieList.items.size(); i++) {
                MovieItem movieItem = movieList.items.get(i);

                adapter.addItem(new MovieItem(movieItem.getTitle(), movieItem.getLink(),
                        movieItem.getImage(), movieItem.getSubtitle(), movieItem.getPubDate(),
                        movieItem.getDirector(), movieItem.getActor(), movieItem.getUserRating()));
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void println(String s) {
        textView.append(s + "\n");
    }
}
