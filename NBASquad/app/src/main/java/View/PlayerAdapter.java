package View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nbasquad.R;

import java.util.List;

import Model.Player;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {
    private List<Player> playerList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public PlayerAdapter(List<Player> list) {
        this.playerList = list;
    }

    public static class PlayerViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ImageView playerImage;
        public TextView playerName;
        public TextView playerTeam;
        public TextView playerPoint;

        public PlayerViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            Context context = itemView.getContext();
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            playerImage = itemView.findViewById(R.id.playerImage);
            playerName = itemView.findViewById(R.id.playerName);
            playerTeam = itemView.findViewById(R.id.playerTeam);
            playerPoint = itemView.findViewById(R.id.playerPoint);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            listener.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_detail, parent, false);
        PlayerViewHolder pvh = new PlayerViewHolder(view, onItemClickListener);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player player = playerList.get(position);
        if(player != null){
            holder.playerName.setText(player.getName());
            holder.playerTeam.setText(player.getTeam());
            holder.playerPoint.setText(Integer.toString(player.getPoints()));
        }
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }
}
