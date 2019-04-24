package iti.mobile.touropia.Screens.EditTrip;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import iti.mobile.touropia.Model.Network.LatLng;
import iti.mobile.touropia.Model.Network.TripDTO;
import iti.mobile.touropia.R;
import iti.mobile.touropia.Screens.Home.HomeActivity;
import iti.mobile.touropia.Screens.Home.HomePresenterImpl;
import maes.tech.intentanim.CustomIntent;

public class EditTripActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
    //private DatabaseReference mDatabase;
    String userId;
    private TripDTO trip;
    private String key;   //no need

    private EditPresenterImpl presenter;

    private int mYear, mMonth, mDay, mHour, mMinute;

    private int mYearBack, mMonthBack, mDayBack, mHourBack, mMinuteBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);
        tripDTO = new TripDTO();
      /*  Spinner repeatSpinner = findViewById(R.id.reapetSpinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.Repeated, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeatSpinner.setAdapter(spinnerAdapter);
        repeatSpinner.setOnItemSelectedListener(this);*/
        Intent intent = getIntent();


        userId = intent.getStringExtra("userId");
        trip = (TripDTO) intent.getSerializableExtra("Trip");
       // key = intent.getStringExtra("key");

        System.out.println("EditTrip" + trip.getTrip_name());

        //create object
        presenter = new EditPresenterImpl(userId);


////////--------------- intialize ---------------------------------------------------
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
        final CardView layout = findViewById(R.id.linearBack);
        //----------------
        //  mDatabase = FirebaseDatabase.getInstance().getReference();
//-----------------------
        //set values to be edited
        tripName.setText(trip.getTrip_name());   //object trip
        latLangFrom=trip.getLatLangFrom();
        latLangTo=trip.getlatLangTo();
        tripDTO.setKey(trip.getKey());

      /*  if (trip.getTrip_note() != null) {
            trip_note.setText(trip.getTrip_note().indexOf(1));
        }*/


        round.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    layout.setVisibility(View.VISIBLE);
                    tripDTOBack = new TripDTO();
                    roundTrip = true;
//                    Toast.makeText(AddTrip.this, roundTrip + "", Toast.LENGTH_SHORT).show();
                } else {
                    roundTrip = false;
                    layout.setVisibility(View.GONE);
                    //                  Toast.makeText(AddTrip.this, roundTrip + "", Toast.LENGTH_SHORT).show();

                }
            }
        });

////////////////////////------------------------------------------------------------


        Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_key));
        PlacesClient placesClient = Places.createClient(this);

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment_from);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        autocompleteFragment.setText(trip.getTrip_start_point());
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.


                from = place.getName();

                latLangFrom = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude); //place.getLatLng();
                latLangToBack = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude); //place.getLatLng();

                // latLangToBack = place.getLatLng();

                // tripDTO.setlatLangFrom(place.getLatLng());

                tripDTO.setlatLangFrom(latLangFrom);


            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.


                Toast.makeText(EditTripActivity.this, "Sorry an error happened ... Please try again", Toast.LENGTH_SHORT).show();
            }
        });

        AutocompleteSupportFragment autocompleteFragment_to = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment_to);
        autocompleteFragment_to.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteFragment_to.setText(trip.getTrip_end_point());

        //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        autocompleteFragment_to.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                //set langlat2 and name here
                //Toast.makeText(AddTrip.this, place.getLatLng().toString(), Toast.LENGTH_SHORT).show();

                to = place.getName();
                /*latLangTo = place.getLatLng();
                latLangFromBack = place.getLatLng();
                tripDTO.setlatLangTo(latLangTo);
            */

                latLangTo = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude); //place.getLatLng();
                latLangFromBack = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude); //place.getLatLng();

                // latLangToBack = place.getLatLng();

                // tripDTO.setlatLangFrom(place.getLatLng());

                tripDTO.setlatLangTo(latLangTo);

            }

            @Override
            public void onError(Status status) {

                Toast.makeText(getApplicationContext(), "Sorry an error happened ... Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void time(View view) {
        Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
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

        // Launch Time Picker Dialog
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
        //Toast.makeText(this, item, Toast.LENGTH_SHORT).show();

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

    public void EditTrip(View view) {

        if (roundTrip) {
            if (ValidateDataRound()) {

                //   String id = mDatabase.push().getKey();
                // FirebaseDatabase database = FirebaseConnection.getConnection();
                // mDatabase = database.getReference("trips").child(userId);

                //  mDatabase.child(id).setValue(tripDTO);
                // mDatabase.child(id + 1).setValue(tripDTOBack);

                //calling EditFunction in EditPresenterImpl
                presenter.EditTrip(tripDTO);

                Toast.makeText(this, " Your Trip Edited successfully ", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                CustomIntent.customType(this, "right-to-left");

            } else {
                Toast.makeText(this, "enter round method", Toast.LENGTH_SHORT).show();
            }


        } else {
            if (ValidateData()) {

                //String id = mDatabase.push().getKey();
                //FirebaseDatabase database = FirebaseConnection.getConnection();
                //mDatabase = database.getReference("trips").child(userId);
                //mDatabase.child(id).setValue(tripDTO);

                //Edit
                presenter.EditTrip(tripDTO);
                Toast.makeText(this, " Your Trip Edited successfully ", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
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
        //note = trip_note.getText().toString();
        tripDTO.setTrip_status(true);
        boolean validate;
        tripDTO.setTrip_name(trip_Name);
        if (from != null) {
            tripDTO.setTrip_start_point(from);

        } else {
            tripDTO.setTrip_start_point(trip.getTrip_start_point());
        }

        if (to != null) {
            tripDTO.setTrip_end_point(to);

        } else {
            tripDTO.setTrip_end_point(trip.getTrip_start_point());
        }
        if (latLangFrom != null) {
            tripDTO.setlatLangFrom(latLangFrom);

        } else {
            tripDTO.setlatLangFrom(trip.getLatLangFrom());
        }
        if (latLangTo != null) {
            tripDTO.setlatLangTo(latLangTo);

        } else {
            tripDTO.setlatLangTo(trip.getlatLangTo());
        }

        if (trip_time != null) {
            tripDTO.setTrip_time(trip_time);

        } else {
            tripDTO.setTrip_time(trip.getTrip_time());

        }

        if (trip_date != null) {
            tripDTO.setTrip_date(trip_date);

        } else {
            tripDTO.setTrip_date(trip.getTrip_date());

        }





  /*      if (trip_Name.matches("") || from == null || to == null || trip_time == null || trip_date == null) {
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
*/
////////////////////////////////////
        return true;
    }


    private boolean ValidateDataRound() {

        trip_Name = tripName.getText().toString();
        currentCalendar.setTimeInMillis(System.currentTimeMillis());
        tripDTO.setTrip_name(trip_Name);
        //note = trip_note.getText().toString();
        tripDTO.setTrip_status(true);
        boolean validate;


        if (trip_Name.matches("") || from == null || to == null || trip_time == null || trip_date == null || trip_dateBack == null
                || trip_timeBack == null) {
            validate = false;


        } else {


            if (myCalendar.before(currentCalendar)  || myCalendarBack.before(currentCalendar)) {
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
