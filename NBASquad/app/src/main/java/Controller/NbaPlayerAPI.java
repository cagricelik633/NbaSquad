package Controller;

import java.util.List;

import Model.Player;
import retrofit2.Call;
import retrofit2.http.GET;

public interface NbaPlayerAPI {

    @GET("blob/main/nba.json")
    Call<List<Player>> getPlayers();

}
