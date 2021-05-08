package Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rescuedrone.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import Model.Mission;

public class MissionListAdapter extends RecyclerView.Adapter<MissionListAdapter.MissionListViewHolder> {

    ArrayList<Mission> aList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onPushToDronehub(int position);
        void onViewIntel(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    Context context;

    public MissionListAdapter(ArrayList<Mission> aList, Context context) {
        this.aList = aList;
        this.context = context;
    }

    @NonNull
    @Override
    public MissionListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.mission_list_item, parent, false);
        return new MissionListViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MissionListViewHolder holder, int position) {

        Mission mission = aList.get(position);
        holder.title.setText(mission.getTitle());
        holder.location.setText(Double.toString(mission.getLocation().lat) + " " + Double.toString(mission.getLocation().lng));
        holder.description.setText(mission.getDescription());
        
    }

    @Override
    public int getItemCount() {
        return aList.size();
    }

    public static class MissionListViewHolder extends RecyclerView.ViewHolder {

        TextView title, location, description;
        MaterialButton btnViewMore, btnPushDronehub;

        public MissionListViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);


            title = itemView.findViewById(R.id.tv_title);
            location = itemView.findViewById(R.id.tv_location);
            description = itemView.findViewById(R.id.tv_description);
            btnViewMore = itemView.findViewById(R.id.btn_view_more);
            btnPushDronehub = itemView.findViewById(R.id.btn_push_dronehub);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            btnViewMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onViewIntel(position);
                        }
                    }
                }
            });

            btnPushDronehub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onPushToDronehub(position);
                        }
                    }
                }
            });



        }
    }
}
