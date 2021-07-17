package com.pina.mescoe_admin.TimetableImage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pina.mescoe_admin.R;

import java.io.File;

public class be_timetable_uploader extends AppCompatActivity {

    private final int REQ = 1;
    //    private Bitmap bitmap;
//    private EditText imageTitle;
    private Button comp1, comp2, compss;
    //    private EditText pdfTitle;
    private Uri pdfData;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private TextView pdftextview, checkimg;
    private String pdfName, title;
    private String value;


//    private DatabaseReference reference, dbRef;
//    private StorageReference storageReference;
//    private ImageView imageView;
//    String downloadUrl = "";
//    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_be_timetable_uploader);
        comp1 = findViewById(R.id.comp1);
        comp2 = findViewById(R.id.comp2);
        compss = findViewById(R.id.compss);
        pdftextview = findViewById(R.id.pdftextview);
        checkimg = findViewById(R.id.checkimg);
        CardView addpdf = findViewById(R.id.addpdf);

        addpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPDF();
            }
        });
        storageReference = FirebaseStorage.getInstance().getReference();
        comp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("BE COMP 1");
                if (pdfData == null) {
                    Toast.makeText(getApplicationContext(), "Please select pdf file", Toast.LENGTH_SHORT).show();
                } else {
                    uploadPDFfile("BE COMP 1/");
                }
            }

        });
        comp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("BE COMP 2");
                if (pdfData == null) {
                    Toast.makeText(getApplicationContext(), "Please select pdf file", Toast.LENGTH_SHORT).show();
                } else {
                    uploadPDFfile("BE COMP 2/");
                }
            }

        });
        compss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("BE COMP SS");
                if (pdfData == null) {
                    Toast.makeText(getApplicationContext(), "Please select pdf file", Toast.LENGTH_SHORT).show();
                } else {
                    uploadPDFfile("BE COMP SS/");
                }
            }

        });
    }

    private void uploadPDFfile(String dept) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading");
        progressDialog.show();


        StorageReference reference = storageReference.child(dept + pdfName + "-" + System.currentTimeMillis());
        reference.putFile(pdfData)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

//                                uploadPDF data=new uploadPDF(pdfTitle.getText().toString(),uri.toString());
                                databaseReference.setValue(uri.toString());

                                Toast.makeText(getApplicationContext(), "File Uploaded", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                pdftextview.setText("✔");
//                                pdfTitle.setText("");
                            }
                        });
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        float percent = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    }

                });
    }

    private void selectPDF() {
        Intent intent = new Intent();
        intent.setType("pdf/docs/ppt");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select file"), REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK && data != null && data.getData() != null) {
            pdfData = data.getData();
            if (pdfData.toString().startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = getApplicationContext().getContentResolver().query(pdfData, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        pdfName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (pdfData.toString().startsWith("file://")) {
                pdfName = new File(pdfData.toString()).getName();
            }
            pdftextview.setText(pdfName);
        }
    }


}