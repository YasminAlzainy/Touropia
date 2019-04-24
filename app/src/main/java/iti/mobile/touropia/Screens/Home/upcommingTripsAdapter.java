package iti.mobile.touropia.Screens.Home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import iti.mobile.touropia.FloatNote;
import iti.mobile.touropia.Screens.EditTrip.EditTripActivity;
import iti.mobile.touropia.Model.Network.LatLng;
import iti.mobile.touropia.Model.Network.TripDTO;
import iti.mobile.touropia.R;


import java.util.List;

import iti.mobile.touropia.Screens.Notes.NotesActivity;

public class upcommingTripsAdapter  extends RecyclerView.Adapter<upcommingTripsAdapter.MyViewHolder> {

    private List<TripDTO> tripList;
    private Context context;
    protected HomePresenterImpl presenter;
    public upcommingTripsAdapter(List<TripDTO> upcommingTripList, Context applicationContext,HomePresenterImpl presenter) {
        context=applicationContext;
        tripList=upcommingTripList;
        this.presenter=presenter;
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

        TripDTO trip=tripList.get(position);
        holder.tripNameTextView.setText( trip.getTrip_name());
        holder.tripDateTextView.setText( trip.getTrip_date());
        holder.tripTimeTextView.setText( trip.getTrip_time());
        holder.tripDestinationTextView.setText( trip.getTrip_end_point());
        holder.tripSourceTextView.setText( trip.getTrip_start_point());
        holder.presenter=this.presenter;
        holder.Position=position;
      //  holder.Notes=trip.getTrip_note();
        holder.source=trip.getLatLangFrom();
        holder.Destination=trip.getlatLangTo();
        holder.trip=trip;

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
        private Context context;
        private String Notes;
        private HomePresenterImpl presenter;
        private int Position;
        private LatLng source;
        private LatLng Destination;
        private TripDTO trip;

        public MyViewHolder(final View view) {
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
                    popup.inflate(R.menu.upcommingtrip_menu);
                    popup.setGravity(Gravity.RIGHT);
                    popup.show();
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()){
                                case R.id.Delete:
                                    //Toast.makeText(tripMenuButton.getContext(), "Delete", Toast.LENGTH_SHORT).show();
                                    presenter.DeleteTrip(Position);
                                    return true;
                                case R.id.Start:
                                    //Toast.makeText(tripMenuButton.getContext(), "Start", Toast.LENGTH_SHORT).show();
                                    Intent MapIntent = new Intent(android.content.Intent.ACTION_VIEW,
                                            Uri.parse("http://maps.google.com/maps?saddr="+source.getLatitude()+","+source.getLongitude()+"&daddr="+Destination.getLatitude()+","+Destination.getLongitude()) );
                                    trip.setTrip_status(false);
                                    presenter.EditTrip(trip,Position);
                                    context.startService(new Intent(context, FloatNote.class));
                                    context.startActivity(MapIntent);
                                    return true;
                                case R.id.Notes:
                                    Intent intent=new Intent( context, NotesActivity.class);
                                    Bundle bundle=new Bundle();
                                    bundle.putString("Notes",Notes);
                                    bundle.putString("tripName",tripNameTextView.getText().toString());
                                    intent.putExtras(bundle);
                                    context.startActivity(intent);
                                   // Toast.makeText(tripMenuButton.getContext(), "Notes", Toast.LENGTH_SHORT).show();
                                    return true;
                                case R.id.Edit:
                                    Intent EditIntent=new Intent( context, EditTripActivity.class);

                                    EditIntent.putExtra("userId",presenter.getUserId());
                                    EditIntent.putExtra("Trip", trip);
                                    EditIntent.putExtra("key",presenter.getKeys().get(Position));

                                    context.startActivity(EditIntent);
                                    //Toast.makeText(tripMenuButton.getContext(), "Edit", Toast.LENGTH_SHORT).show();

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
