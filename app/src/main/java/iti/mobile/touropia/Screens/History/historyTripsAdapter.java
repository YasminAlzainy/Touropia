package iti.mobile.touropia.Screens.History;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import iti.mobile.touropia.Model.Network.TripDTO;
import iti.mobile.touropia.Screens.Home.HomePresenterImpl;
import iti.mobile.touropia.Screens.Notes.NotesActivity;
import iti.mobile.touropia.R;


import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;

import iti.mobile.touropia.Model.Network.TripData;

public class historyTripsAdapter  extends RecyclerView.Adapter<historyTripsAdapter.MyViewHolder>{
    protected List<TripDTO> tripList;
    protected Context context;
    protected HistoryPresenterImpl presenter;

    public historyTripsAdapter(List<TripDTO> historyTripList, Context applicationContext,HistoryPresenterImpl presenter) {
        context=applicationContext;
        tripList=historyTripList;
        this.presenter=presenter;
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
        TripDTO trip=tripList.get(position);
        holder.tripNameTextView.setText( trip.getTrip_name());
        holder.tripDateTextView.setText(trip.getTrip_date());
        holder.tripTimeTextView.setText( trip.getTrip_time());
        holder.tripDestinationTextView.setText(trip.getTrip_end_point());
        holder.tripSourceTextView.setText(trip.getTrip_start_point());
      //  holder.Notes=trip.getTrip_note();
        holder.presenter=presenter;
        holder.Position=position;
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
        private String Notes;
        private HistoryPresenterImpl presenter;
        private int Position;
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
                                    //Toast.makeText(context, "Notes", Toast.LENGTH_SHORT).show();
                                     Intent intent=new Intent( context, NotesActivity.class);
                                     Bundle bundle=new Bundle();
                                     bundle.putString("Notes",Notes);
                                     bundle.putString("tripName",tripNameTextView.getText().toString());
                                     intent.putExtras(bundle);
                                     context.startActivity(intent);
                                     return true;
                                case R.id.Delete:
                                    //Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                                    System.out.println("Position"+Position);
                                    presenter.DeleteTrip(Position);

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
