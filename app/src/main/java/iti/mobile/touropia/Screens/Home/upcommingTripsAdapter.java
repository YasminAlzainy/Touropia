package iti.mobile.touropia.Screens.Home;

import android.content.Context;
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
import iti.mobile.touropia.R;


import java.util.List;

import iti.mobile.touropia.Model.Network.TripData;

public class upcommingTripsAdapter  extends RecyclerView.Adapter<upcommingTripsAdapter.MyViewHolder> {

    private List<TripData> tripList;
    private Context context;
    public upcommingTripsAdapter(List<TripData> upcommingTripList, Context applicationContext) {
        context=applicationContext;
        tripList=upcommingTripList;
    }

    @NonNull
    @Override
    public upcommingTripsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        // create a new view
        View view = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull upcommingTripsAdapter.MyViewHolder holder, int position) {

        TripData tripData=tripList.get(position);
        holder.tripNameTextView.setText( tripData.getName());
        holder.tripDateTextView.setText( tripData.getDate());
        holder.tripTimeTextView.setText( tripData.getTime());
        holder.tripDestinationTextView.setText( tripData.getTo());
        holder.tripSourceTextView.setText( tripData.getFrom());
//        Glide.with(context)
//                .load(List.get(position).getFlag())
//                .into(holder.countryFlagImageView);
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tripNameTextView;
        public TextView tripDateTextView;
        public TextView tripSourceTextView;
        public TextView tripTimeTextView;
        public TextView tripDestinationTextView;
        public ImageView tripImageView;
        public Button tripMenuButton;
        public MyViewHolder(final View view) {
            super(view);
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
                    popup.inflate(R.menu.upcommingtrip_menu);
                    popup.setGravity(Gravity.RIGHT);
                    popup.show();
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()){
                                case R.id.Delete:
                                    Toast.makeText(tripMenuButton.getContext(), "Delete", Toast.LENGTH_SHORT).show();
                                    return true;

                                case R.id.Start:
                                    Toast.makeText(tripMenuButton.getContext(), "Start", Toast.LENGTH_SHORT).show();
                                    return true;
                                case R.id.Notes:
                                    Toast.makeText(tripMenuButton.getContext(), "Notes", Toast.LENGTH_SHORT).show();
                                    return true;
                                case R.id.Edit:
                                    Toast.makeText(tripMenuButton.getContext(), "Edit", Toast.LENGTH_SHORT).show();
                                    return true;
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
