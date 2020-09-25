package com.example.calendar_application;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Contacts;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.app.Notification;
import android.app.NotificationManager;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static android.content.Context.ALARM_SERVICE;
import static androidx.core.app.ActivityCompat.startActivityForResult;
import static java.lang.Integer.compare;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;

public class CalendarView extends LinearLayout {
    public static ImageView setImage;

    private ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarbHandler = new Handler();
    private boolean hasImageChanged = false;

    public static DBOpenHelper DBopenHelper;
    static int value;
    ImageButton NextButton, PreviousButton;
    TextView CurrentDate;
    GridView gridView;
    private static Context context;
    private static final int MAX_CALENDAR_DAYS = 42;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    int hours=0,minutes=0;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat( "MMMM yyyy", Locale.ENGLISH);
    SimpleDateFormat yearFormat = new SimpleDateFormat( "yyyy", Locale.ENGLISH);
    SimpleDateFormat eventDateFormate = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);

    MyGridAdapter myGridAdapter;
    AlertDialog alertDialog;
    static DBOpenHelper dbOpenHelper;
    int checked=0;
    List<Date> dates = new ArrayList<>();
    List<Events> eventsList = new ArrayList<>();

    public CalendarView(Context context) {
        super(context);
    }

    public CalendarView(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        CalendarView.context = context;
        InitializeLayout();
        SetUpCalendar();

        PreviousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, -1);

                SetUpCalendar();
            }
        });

        NextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, 1);
                SetUpCalendar();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                final View addView = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_newevent_layout, null);
                final EditText EventName = addView.findViewById(R.id.eventname);
                final EditText EventDescription = addView.findViewById(R.id.eventdescription);
                final EditText EventLocation = addView.findViewById(R.id.eventlocation);
                final TextView EventTime = addView.findViewById(R.id.eventtime);
                setImage =addView.findViewById(R.id.setImage);
                value= nextId()+1;
                ImageButton SetTime = addView.findViewById(R.id.seteventtime);
                Button AddEvent = addView.findViewById(R.id.addevent);
                SetTime.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        hours = calendar.get(Calendar.HOUR_OF_DAY);
                        minutes = calendar.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog = new TimePickerDialog(addView.getContext(), R.style.Theme_AppCompat_Dialog
                                , new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                c.set(Calendar.MINUTE,minute);
                                c.setTimeZone(TimeZone.getDefault());
                                SimpleDateFormat hformat = new SimpleDateFormat( "K:mm a",Locale.ENGLISH);
                                String event_Time = hformat.format(c.getTime());
                                EventTime.setText(event_Time);

                            }
                        },hours, minutes, false);
                        timePickerDialog.show();
                    }
                });
                final String date = eventDateFormate.format(dates.get(position));
                final String month = monthFormat.format(dates.get(position));
                final String year = yearFormat.format(dates.get(position));

                Button addImage = addView.findViewById(R.id.addImage);

                addImage.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                       //saving();
                        ((ClickImage)context).imageClick();
                        value = nextId()+1;
                    }

                });
                setImage.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        ((ClickImage)context).gallerySelect();
                    }
                });


                AddEvent.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final CheckBox ch;
                        ch = (CheckBox) addView.findViewById(R.id.alarm);
                        //notification reminder
                        if(ch.isChecked()) {
                            // Set notificationId & text
                            Intent intent = new Intent(context, AlarmReceiver.class);
                            intent.putExtra("notificationId", "1");
                            intent.putExtra("todo", EventName.getText().toString());

//                                                     getBroadcast(context, requestCode, intent, flags)

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                CharSequence name = "201";
                                String description = EventName.getText().toString();
                                int importance = NotificationManager.IMPORTANCE_HIGH;
                                NotificationChannel channel = new NotificationChannel("1", name, importance);
                                channel.setDescription(description);
                                // Register the channel with the system; you can't change the importance
                                // or other notification behaviors after this
                                NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
                                notificationManager.createNotificationChannel(channel);
                            }

                            AlarmManager alarm = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);
                            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0,
                                    intent, PendingIntent.FLAG_CANCEL_CURRENT);
                            // Create time.
                            Calendar startTime = Calendar.getInstance();
                            startTime.set(Calendar.HOUR_OF_DAY, hours);
                            startTime.set(Calendar.MINUTE, minutes);
                            startTime.set(Calendar.SECOND, 0);
                            long alarmStartTime = startTime.getTimeInMillis();
                            // Set alarm.
                            // set(type, milliseconds, intent)

                            alarm.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);
                        }
                        SaveEvent(EventName.getText().toString(),EventDescription.getText().toString(),EventLocation.getText().toString(),EventTime.getText().toString(),date,month,year,"mnt/sdcard/myapp/"+value+".jpg");

//                        Toast.makeText(context, ""+EventDescription.getText().toString(), Toast.LENGTH_LONG).show();

                        SetUpCalendar();
                       alertDialog.dismiss();
                    }
                });
                builder.setView(addView);
                alertDialog = builder.create();
                alertDialog.show();
            }
        });


        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String date = eventDateFormate.format(dates.get(position));
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                View showView = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_events_layout,null);
                RecyclerView recyclerView = showView.findViewById(R.id.EventsRV);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(showView.getContext());
                recyclerView .setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                EventRecyclerAdapter eventRecyclerAdapter = new EventRecyclerAdapter(showView.getContext()
                        ,CollectEventByDate(date));
                recyclerView.setAdapter(eventRecyclerAdapter);
                eventRecyclerAdapter.notifyDataSetChanged();
                builder.setView(showView);
                alertDialog =builder.create();
                alertDialog.show();
                return true;
            }
        });
    }

    static File gettingImage() {
        File directory = new File("mnt/sdcard/myapp");
        File photoImg;
        if (!directory.exists()) {
            directory.mkdir();
        }
        photoImg = new File(directory, nextId()+1+".jpg");
        return photoImg;
    }

    void saving(){
        Intent open_cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File fil = gettingImage();
        open_cam.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fil));
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

       ((Activity)context).startActivityForResult(open_cam,1);
       MainActivity ma = new MainActivity();
       ma.onActivityResult(100,1,null);

    }

    public void onCheckboxClicked(){
        if(checked==0){
            checked=1;
        }
        else{checked=0;}
    }

    public static int nextId(){
        int nxtId=0;
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor cursor = dbOpenHelper.getNextId(database);
        if(cursor.moveToNext()){
            nxtId = cursor.getInt(cursor.getColumnIndex(DBStructure.ID));
        }
        return nxtId;
    }
    //viewing saved event from db
    private ArrayList<Events> CollectEventByDate(String date){
        ArrayList<Events> arrayList = new ArrayList<>();
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor cursor = dbOpenHelper.ReadEvents(date,database);
        while (cursor.moveToNext()){
            int ids = cursor.getInt(cursor.getColumnIndex(DBStructure.ID));
            String id=ids+"";
            String event = cursor.getString(cursor.getColumnIndex(DBStructure.EVENT));
            String description ="";
            String location = "";
            String time = cursor.getString(cursor.getColumnIndex(DBStructure.TIME));
            String Date = cursor.getString(cursor.getColumnIndex(DBStructure.DATE));
            String month = cursor.getString(cursor.getColumnIndex(DBStructure.MONTH));
            String Year = cursor.getString(cursor.getColumnIndex(DBStructure.YEAR));
//            String imglocation = cursor.getString(cursor.getColumnIndex(DBStructure.IMAGELOCATION));
            Events events = new Events(id,event,description,location,time,Date,month,Year);
            arrayList.add(events);
        }
        cursor.close();
        dbOpenHelper.close();

        return arrayList;
    }
    public static ArrayList<String> collectData(String ID){
        ArrayList<String> arrayList = new ArrayList<>();
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor cursor = dbOpenHelper.Getdata(parseInt(ID),database);
        while (cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndex(DBStructure.ID));
            String event = cursor.getString(cursor.getColumnIndex(DBStructure.EVENT));
            String description =cursor.getString(cursor.getColumnIndex(DBStructure.DESCRIPTION));
            String location = cursor.getString(cursor.getColumnIndex(DBStructure.LOCATION));
            String time = cursor.getString(cursor.getColumnIndex(DBStructure.TIME));
            String Date = cursor.getString(cursor.getColumnIndex(DBStructure.DATE));
            String month = cursor.getString(cursor.getColumnIndex(DBStructure.MONTH));
            String Year = cursor.getString(cursor.getColumnIndex(DBStructure.YEAR));
            String imagelocation = cursor.getString(cursor.getColumnIndex(DBStructure.IMAGELOCATION));
            arrayList.add(event);
            arrayList.add(description);
            arrayList.add(location);
            arrayList.add(time);
            arrayList.add(Date);
            arrayList.add(month);
            arrayList.add(Year);
            arrayList.add(imagelocation);
        }
        cursor.close();
        dbOpenHelper.close();
        return arrayList;
    }


    public CalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void SaveEvent (String event,String description, String location, String time, String date, String month, String year,String imagelocation) {
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        dbOpenHelper.SaveEvent(event,description,location,time,date,month,year,imagelocation,database);
        dbOpenHelper.close();
        Toast.makeText(context, "Event Saved", Toast.LENGTH_SHORT).show();
    }

    private void InitializeLayout() {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_layout, this);
        NextButton = view.findViewById(R.id.nextBtn);
        PreviousButton = view.findViewById(R.id.previousBtn);
        CurrentDate = view.findViewById(R.id.current_Date);
        gridView = view.findViewById(R.id.gridview);
    }

    private void SetUpCalendar(){
        String currentDate = dateFormat.format(calendar.getTime());
        CurrentDate.setText(currentDate);
        dates.clear();
        Calendar monthCalendar = (Calendar) calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH,1);
        int FirstDayofMonth = monthCalendar.get(Calendar.DAY_OF_WEEK)-1;
        monthCalendar.add(Calendar.DAY_OF_MONTH, -FirstDayofMonth);
        CollectEventsPerMonth(monthFormat.format(calendar.getTime()),yearFormat.format(calendar.getTime()));

        while (dates.size() < MAX_CALENDAR_DAYS) {
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);

        }

        myGridAdapter = new MyGridAdapter(context,dates,calendar,eventsList);
        gridView.setAdapter(myGridAdapter);
    }

    private void CollectEventsPerMonth(String Month,String year){
        eventsList.clear();
        dbOpenHelper = new DBOpenHelper(context);

        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor cursor = dbOpenHelper.ReadEventsperMonth(Month,year,database);
        while (cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndex(DBStructure.ID));
            String event = cursor.getString(cursor.getColumnIndex(DBStructure.EVENT));
//            String image = cursor.getString(cursor.getColumnIndex(DBStructure.IMAGE));
            String description = cursor.getString(cursor.getColumnIndex(DBStructure.DESCRIPTION));
            String location = cursor.getString(cursor.getColumnIndex(DBStructure.LOCATION));
            String time = cursor.getString(cursor.getColumnIndex(DBStructure.TIME));
            String date = cursor.getString(cursor.getColumnIndex(DBStructure.DATE));
            String month = cursor.getString(cursor.getColumnIndex(DBStructure.MONTH));
            String Year = cursor.getString(cursor.getColumnIndex(DBStructure.YEAR));
            Events events = new Events(id,event,description,location,time,date,month,Year);
            eventsList.add(events);

        }
        cursor.close();
        dbOpenHelper.close();

    }

    public void setProgressBar(){
        progressBar = new ProgressDialog(context);
        progressBar.setCancelable(true);
        progressBar.setMessage("Please wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
        progressBarStatus = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressBarStatus < 100){
                    progressBarStatus += 30;

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    progressBarbHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });
                }
                if (progressBarStatus >= 100) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBar.dismiss();
                }

            }
        }).start();
    }


    public interface ClickImage{
     public void imageClick();
     public void gallerySelect();
    }
}