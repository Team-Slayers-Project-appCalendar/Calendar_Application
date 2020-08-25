package com.example.calendar_application;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import javax.security.auth.Destroyable;

import static androidx.core.content.ContextCompat.startActivity;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.MyViewHolder> implements EventHandling {

   static Context context;
   static ArrayList<Events> arrayList ;
    DBOpenHelper dbOpenHelper;
    Intent intent;
    static Events events;
   static int position,id,chaiyeko;
    public EventRecyclerAdapter(Context context, ArrayList<Events> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        events = null;
    }

    @NonNull
    @Override
    public EventRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_events_rowlayout,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventRecyclerAdapter.MyViewHolder holder, final int position) {
        events = arrayList.get(position);
        holder.Event.setText(events.getEVENT());
        holder.DateTxt.setText(events.getDATE());
        holder.Time.setText(events.getTIME());
        id = events.getID();

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCalendarEvent(events.getEVENT(), events.getDATE(), events.getTIME());
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    public static void sendData(){

        Intent inte = new Intent(context,IndividualData.class);
        context.startActivity(inte);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public void listener(int a) {

    }
    public static void kxababu(int position){
        events = arrayList.get(position);
        chaiyeko=events.getID();
        sendData();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder{
        TextView DateTxt, Event, Time;
        Button delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            DateTxt = itemView.findViewById(R.id.eventdate);
            Event = itemView.findViewById(R.id.eventname);
            Time = itemView.findViewById(R.id.eventime);
            delete = itemView.findViewById(R.id.delete);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   EventRecyclerAdapter.kxababu(getAdapterPosition());
                }
            });
        }
    }

    private void deleteCalendarEvent(String event,String date,String time) {
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        dbOpenHelper.deleteEvent(event,date,time,database);
        dbOpenHelper.close();
    }

}


