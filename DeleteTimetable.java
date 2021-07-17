package com.pina.mescoe_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DeleteTimetable extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_timetable);


        findViewById(R.id.secomp1).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    findViewById(R.id.secomp1).setBackgroundColor(Color.RED);
                    rusure("SE COMP 1");
                }
                return false;
            }
        });
        findViewById(R.id.secomp2).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    findViewById(R.id.secomp2).setBackgroundColor(Color.RED);
                    rusure("SE COMP 2");
                }
                return false;
            }
        });
        findViewById(R.id.secompss).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    findViewById(R.id.secompss).setBackgroundColor(Color.RED);
                    rusure("SE COMP SS");
                }
                return false;
            }
        });
        findViewById(R.id.tecomp1).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    findViewById(R.id.tecomp1).setBackgroundColor(Color.RED);
                    rusure("TE COMP 1");
                }
                return false;
            }
        });
        findViewById(R.id.tecomp2).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    findViewById(R.id.tecomp2).setBackgroundColor(Color.RED);
                    rusure("TE COMP 2");
                }
                return false;
            }
        });
        findViewById(R.id.tecompss).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    findViewById(R.id.tecompss).setBackgroundColor(Color.RED);
                    rusure("TE COMP SS");
                }
                return false;
            }
        });
        findViewById(R.id.becomp1).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    findViewById(R.id.becomp1).setBackgroundColor(Color.RED);
                    rusure("BE COMP 1");
                }
                return false;
            }
        });
        findViewById(R.id.becomp2).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    findViewById(R.id.becomp2).setBackgroundColor(Color.RED);
                    rusure("BE COMP 2");
                }
                return false;
            }
        });
        findViewById(R.id.becompss).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    findViewById(R.id.becompss).setBackgroundColor(Color.RED);
                    rusure("BE COMP SS");
                }
                return false;
            }
        });

    }

    public void rusure(String string){
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(DeleteTimetable.this);
        builder.setTitle("Delete Timetable");
        builder.setMessage("Are you sure ?");
        builder.setIcon(R.drawable.ic_delete);
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteData(string);
                invisible();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                invisible();
                findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
            }
        });
        builder.create().show();
    }

    private void invisible() {
        findViewById(R.id.secomp1).setBackgroundResource(R.drawable.invisible_button);
        findViewById(R.id.secomp1).setBackgroundResource(R.drawable.invisible_button);
        findViewById(R.id.secompss).setBackgroundResource(R.drawable.invisible_button);
        findViewById(R.id.tecomp1).setBackgroundResource(R.drawable.invisible_button);
        findViewById(R.id.tecomp2).setBackgroundResource(R.drawable.invisible_button);
        findViewById(R.id.tecompss).setBackgroundResource(R.drawable.invisible_button);
        findViewById(R.id.becomp1).setBackgroundResource(R.drawable.invisible_button);
        findViewById(R.id.becomp2).setBackgroundResource(R.drawable.invisible_button);
        findViewById(R.id.becompss).setBackgroundResource(R.drawable.invisible_button);
    }

    ;

    private void deleteData(String dept) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(dept);
        reference.removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        findViewById(R.id.deletedsuccessfully).setVisibility(View.VISIBLE);
                        findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }
}