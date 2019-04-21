package iti.mobile.touropia.Screens.AddTrip;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import iti.mobile.touropia.Model.Network.FirebaseConnection;
import iti.mobile.touropia.Model.Network.TripDTO;
import iti.mobile.touropia.R;
import iti.mobile.touropia.Model.Network.LatLng;
import iti.mobile.touropia.Screens.Home.HomeActivity;
import maes.tech.intentanim.CustomIntent;

public class AddTrip extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String trip_Name;
    String from;
    String to;
    LatLng latLangFrom;
    LatLng latLangTo;
    LatLng latLangFromBack;
    LatLng latLangToBack;
    String trip_time;
    String trip_date;
    String trip_timeBack;
    String trip_dateBack;
    boolean roundTrip = false;
    ArrayList<String> note;
    Calendar currentCalendar;
    Calendar myCalendar;
    Calendar myCalendarBack;
    Button btnDatePicker, btnTimePicker;
    Button btnDatePickerBack, btnTimePickerBack;
    TripDTO tripDTO;
    TripDTO tripDTOBack;
    EditText tripName;
    SwitchCompat round;
    EditText trip_note;
    private static final String TAG = "Object";
    private DatabaseReference mDatabase;
    String userId;

    private int mYear, mMonth, mDay, mHour, mMinute;

    private int mYearBack, mMonthBack, mDayBack, mHourBack, mMinuteBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        tripDTO = new TripDTO();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        userId = bundle.getString("userId");


        myCalendar = Calendar.getInstance();
        myCalendarBack = Calendar.getInstance();
        currentCalendar = Calendar.getInstance();
        note = new ArrayList<>();
        btnDatePicker = findViewById(R.id.btn_date);
        btnTimePicker = findViewById(R.id.btn_time);
        btnDatePickerBack = findViewById(R.id.btn_dateBack);
        btnTimePickerBack = findViewById(R.id.btn_timeBack);

        trip_note = findViewById(R.id.note);
        tripName = findViewById(R.id.name);
        round = findViewById(R.id.round);
        final LinearLayout layout = findViewById(R.id.linearBack);

        mDatabase = FirebaseDatabase.getInstance().getReference();


        round.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    layout.setVisibility(View.VISIBLE);
                    tripDTOBack = new TripDTO();
                    roundTrip = true;
                } else {
                    roundTrip = false;
                    layout.setVisibility(View.GONE);

                }
            }
        });


        Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_key));
        PlacesClient placesClient = Places.createClient(this);

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment_from);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.

                from = place.getName();

                latLangFrom = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude); //place.getLatLng();
                latLangToBack = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude); //place.getLatLng();


                tripDTO.setlatLangFrom(latLangFrom);


            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.


                Toast.makeText(AddTrip.this, "Sorry an error happened ... Please try again", Toast.LENGTH_SHORT).show();
            }
        });

        AutocompleteSupportFragment autocompleteFragment_to = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment_to);
        autocompleteFragment_to.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        autocompleteFragment_to.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.

                to = place.getName();


                latLangTo = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude); //place.getLatLng();
                latLangFromBack = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude); //place.getLatLng();


                tripDTO.setlatLangTo(latLangTo);

            }

            @Override
            public void onError(Status status) {

                Toast.makeText(AddTrip.this, "Sorry an error happened ... Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void time(View view) {
        Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {


                        trip_time = hourOfDay + ":" + minute;
                        myCalendar.set(Calendar.HOUR_OF_DAY, mHour);
                        myCalendar.set(Calendar.MINUTE, mMinute - 1);
                        myCalendar.set(Calendar.SECOND, 59);


                    }
                }, mHour, mMinute, false);

        timePickerDialog.show();
    }

    public void getTimeBack(View view) {

        Calendar c = Calendar.getInstance();
        mHourBack = c.get(Calendar.HOUR_OF_DAY);
        mMinuteBack = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {


                        trip_timeBack = hourOfDay + ":" + minute;
                        myCalendarBack.set(Calendar.HOUR_OF_DAY, mHour);
                        myCalendarBack.set(Calendar.MINUTE, mMinute - 1);
                        myCalendarBack.set(Calendar.SECOND, 59);


                    }
                }, mHourBack, mMinuteBack, false);

        timePickerDialog.show();
    }


    public void getDateBack(View view) {
        Calendar c = Calendar.getInstance();
        mYearBack = c.get(Calendar.YEAR);
        mMonthBack = c.get(Calendar.MONTH);
        mDayBack = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {


                        trip_dateBack = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        myCalendarBack.set(Calendar.YEAR, year);
                        myCalendarBack.set(Calendar.MONTH, monthOfYear);
                        myCalendarBack.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    }
                }, mYearBack, mMonthBack, mDayBack);
        datePickerDialog.show();
    }

    public void date(View view) {
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {


                        trip_date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        tripDTO.setRepeate(item);


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // send defult no repeat ;
        tripDTO.setRepeate("noRepeat");

    }

    public void addNote(View view) {
        String stringNote;
        stringNote = trip_note.getText().toString();
        if (stringNote.matches("")) {
            Toast.makeText(this, "please add note first to add another one ", Toast.LENGTH_SHORT).show();

        } else {
            note.add(stringNote);
            trip_note.setText("");
            Toast.makeText(this, "your note is added .. add another one if you want ", Toast.LENGTH_SHORT).show();

        }
    }

    public void addTrip(View view) {

        if (roundTrip) {
            if (ValidateDataRound()) {

                String id = mDatabase.push().getKey();
                FirebaseDatabase database = FirebaseConnection.getConnection();
                mDatabase = database.getReference("trips").child(userId);

                mDatabase.child(id).setValue(tripDTO);
                mDatabase.child(id + 1).setValue(tripDTOBack);
                Toast.makeText(this, " Your Trip saved ", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AddTrip.this, HomeActivity.class);
                startActivity(intent);
                CustomIntent.customType(this, "right-to-left");

            } else {
                Toast.makeText(this, "enter round method", Toast.LENGTH_SHORT).show();
            }


        } else {
            if (ValidateData()) {

                String id = mDatabase.push().getKey();
                FirebaseDatabase database = FirebaseConnection.getConnection();
                mDatabase = database.getReference("trips").child(userId);
                mDatabase.child(id).setValue(tripDTO);
                Toast.makeText(this, " Your Trip saved ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddTrip.this, HomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userId", userId);
                intent.putExtras(bundle);
                startActivity(intent);
                CustomIntent.customType(this, "right-to-left");

            } else {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private boolean ValidateData() {

        trip_Name = tripName.getText().toString();
        currentCalendar.setTimeInMillis(System.currentTimeMillis());
        tripDTO.setTrip_name(trip_Name);
        tripDTO.setTrip_status(true);
        boolean validate;

        if (trip_Name.matches("") || from == null || to == null || trip_time == null || trip_date == null) {
            validate = false;


        } else {


            if (myCalendar.compareTo(currentCalendar) <= 0) {
                validate = false;
                Toast.makeText(this, "cannot insert passed time", Toast.LENGTH_SHORT).show();

            } else {

                validate = true;
                tripDTO.setTrip_name(trip_Name);
                tripDTO.setTrip_time(trip_time);
                tripDTO.setTrip_date(trip_date);
                tripDTO.setTrip_start_point(from);
                tripDTO.setTrip_end_point(to);
                tripDTO.setTrip_note(note);
            }

        }


        return validate;
    }


    private boolean ValidateDataRound() {

        trip_Name = tripName.getText().toString();
        currentCalendar.setTimeInMillis(System.currentTimeMillis());
        tripDTO.setTrip_name(trip_Name);
        tripDTO.setTrip_status(true);
        boolean validate;


        if (trip_Name.matches("") || from == null || to == null || trip_time == null || trip_date == null || trip_dateBack == null
                || trip_timeBack == null) {
            validate = false;


        } else {


            if (myCalendar.compareTo(currentCalendar) <= 0 || myCalendarBack.compareTo(currentCalendar) <= 0) {
                validate = false;
                Toast.makeText(this, "cannot insert passed time", Toast.LENGTH_SHORT).show();

            } else {


                validate = true;
                tripDTO.setTrip_name(trip_Name);
                tripDTO.setTrip_time(trip_time);
                tripDTO.setTrip_date(trip_date);
                tripDTO.setTrip_start_point(from);
                tripDTO.setTrip_end_point(to);
                tripDTO.setTrip_note(note);

                tripDTOBack.setTrip_name(trip_Name + " Back");
                tripDTOBack.setTrip_time(trip_timeBack);
                tripDTOBack.setTrip_date(trip_dateBack);
                tripDTOBack.setTrip_start_point(to);
                tripDTOBack.setTrip_end_point(from);
                tripDTOBack.setlatLangTo(latLangToBack);
                tripDTOBack.setlatLangFrom(latLangFromBack);
                tripDTOBack.setTrip_note(note);
                tripDTOBack.setTrip_status(true);

            }

        }


        return validate;
    }


}
