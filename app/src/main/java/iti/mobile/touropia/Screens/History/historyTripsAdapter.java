package iti.mobile.touropia.Screens.History;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import iti.mobile.touropia.NotesActivity;
import iti.mobile.touropia.R;


import java.util.List;

import iti.mobile.touropia.Model.Network.TripData;

public class historyTripsAdapter  extends RecyclerView.Adapter<historyTripsAdapter.MyViewHolder>{
    protected List<TripData> tripList;
    protected Context context;

    public historyTripsAdapter(List<TripData> historyTripList, Context applicationContext) {
        context=applicationContext;
        tripList=historyTripList;
    }

    @NonNull
    @Override
    public historyTripsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View view = (View) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);
        historyTripsAdapter.MyViewHolder viewHolder = new historyTripsAdapter.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull historyTripsAdapter.MyViewHolder holder, int position) {
        TripData tripData=tripList.get(position);
        holder.tripNameTextView.setText( tripData.getName());
        holder.tripDateTextView.setText( tripData.getDate());
        holder.tripTimeTextView.setText( tripData.getTime());
        holder.tripDestinationTextView.setText( tripData.getTo());
        holder.tripSourceTextView.setText( tripData.getFrom());
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView tripNameTextView;
        private TextView tripDateTextView;
        private TextView tripSourceTextView;
        private TextView tripTimeTextView;
        private TextView tripDestinationTextView;
        private ImageView tripImageView;
        private Button tripMenuButton;
        private Context context;
        private MyViewHolder(final View view) {
            super(view);
            context=view.getContext();
            tripNameTextView = view.findViewById(R.id.tripName);
            tripSourceTextView = view.findViewById(R.id.goFrom);
            tripDestinationTextView = view.findViewById(R.id.goTo);
            tripDateTextView = view.findViewById(R.id.Date);
            tripTimeTextView = view.findViewById(R.id.Time);
            tripMenuButton = view.findViewById(R.id.TripMenu);
            tripMenuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(tripMenuButton.getContext(), view);
                    popup.inflate(R.menu.historytrip_menu);
                    popup.setGravity(Gravity.RIGHT);
                    popup.show();
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.notes:
                                    Toast.makeText(context, "Notes", Toast.LENGTH_SHORT).show();
                                     Intent intent=new Intent( context, NotesActivity.class);
                                     intent.putExtra("Notes","hello from Notes");
                                     context.startActivity(intent);

                                     return true;
                                case R.id.Delete:
                                    Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                                    return  true;
                                default:
                                    return false;
                            }
                        }
                    });
                }
            });

        }
    }
}
