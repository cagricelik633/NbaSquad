package com.example.nbasquad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import Controller.NbaPlayerAPI;
import Model.Player;
import View.PlayerAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListScreen extends AppCompatActivity {
    private TextView textViewResult;
    private RecyclerView recyclerView;
    private PlayerAdapter playerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView playerDetailName;
    private TextView playerDetailTeam;
    private TextView playerDetailPoint;
    private ImageView playerDetailImage;

    private Call<List<Player>> handleAPI() {
        //API
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://github.com/cagricelik633/h5200022muhammetcagricelik").addConverterFactory(GsonConverterFactory.create(gson)).build();

        NbaPlayerAPI nbaPlayerAPI = retrofit.create(NbaPlayerAPI.class);
        return nbaPlayerAPI.getPlayers();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_screen);

        //Hide Action Bar
        getSupportActionBar().hide();

        //Recycler view finding.
        recyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        
        //API
        Call<List<Player>> call =handleAPI();

        call.enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                if(!response.isSuccessful()){
                    return;
                }

                //Get list from github
                List<Player> players = response.body();
                playerAdapter = new PlayerAdapter(players);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(playerAdapter);

                playerAdapter.setOnItemClickListener(new PlayerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                        View popupView = inflater.inflate(R.layout.popup, null);

                        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

                        final PopupWindow popupWindow = new PopupWindow(popupView, width, height);

                        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                        playerDetailName = popupWindow.getContentView().findViewById(R.id.playerDetailName);
                        playerDetailTeam = popupWindow.getContentView().findViewById(R.id.playerDetailTeam);
                        playerDetailPoint = popupWindow.getContentView().findViewById(R.id.playerDetailPoint);
                        playerDetailImage = popupWindow.getContentView().findViewById(R.id.playerDetailImage);
                        if(players.get(position) != null){
                            Player temp = players.get(position);
                            String url = temp.getImage();
                            System.out.println(url);
                            playerDetailName.setText(temp.getName());
                            playerDetailTeam.setText(temp.getTeam());
                            playerDetailPoint.setText(Integer.toString(temp.getPoints()));
                            if(url != null) {
                                Glide.with(ListScreen.this).load(url).into(playerDetailImage);
                            }
                        }

                        popupView.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                popupWindow.dismiss();
                                return true;
                            }
                        });
                    }
                });

            }

            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {
                System.out.println("API FAILED");
            }
        });
    }
}