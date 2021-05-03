package Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rescuedrone.R;

import java.util.ArrayList;

import Model.Mission;

public class MissionListAdapter extends RecyclerView.Adapter<MissionListAdapter.MissionListViewHolder> {

    ArrayList<Mission> aList;
    Context context;

    public MissionListAdapter(ArrayList<Mission> aList, Context context) {
        this.aList = aList;
        this.context = context;
    }

    @NonNull
    @Override
    public MissionListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.mission_list_item, parent, false);
        return new MissionListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MissionListViewHolder holder, int position) {

        Mission mission = aList.get(position);
        holder.title.setText(mission.getTitle());
        holder.location.setText(mission.getLocation());
        holder.description.setText(mission.getDescription());

    }

    @Override
    public int getItemCount() {
        return aList.size();
    }

    public static class MissionListViewHolder extends RecyclerView.ViewHolder {

        TextView title, location, description;

        public MissionListViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            location = itemView.findViewById(R.id.tv_location);
            description = itemView.findViewById(R.id.tv_description);
        }
    }
}
