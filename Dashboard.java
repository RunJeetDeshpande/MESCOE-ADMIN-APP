package com.pina.mescoe_admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.google.firebase.auth.FirebaseAuth;
import com.pina.mescoe_admin.TimetableImage.be_timetable_uploader;
import com.pina.mescoe_admin.TimetableImage.se_timetable_uploader;
import com.pina.mescoe_admin.TimetableImage.te_timetable_uploader;

public class Dashboard extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        findViewById(R.id.delete_timetable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DeleteTimetable.class));
            }
        });

        findViewById(R.id.notice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Notice.class));
            }
        });
        findViewById(R.id.signOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
    }




    public void signOut(){

        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
        builder.setTitle("SIGN OUT");
        builder.setMessage("Are you sure ?");
        builder.setIcon(R.drawable.ic_exit_to_app_black_24dp);
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                firebaseAuth.signOut();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.se:
                startActivity(new Intent(getApplicationContext(), se_timetable_uploader.class));
                return true;
            case R.id.te:
                startActivity(new Intent(getApplicationContext(), te_timetable_uploader.class));
                return true;
            case R.id.be:
                startActivity(new Intent(getApplicationContext(), be_timetable_uploader.class));
                return true;
        }
        return false;
    }

}

