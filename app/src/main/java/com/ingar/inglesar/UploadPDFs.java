package com.ingar.inglesar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadPDFs extends AppCompatActivity {

    Button upload_btn, viewPDF_btn;
    EditText pdf_name;

    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdfs);

        upload_btn=findViewById(R.id.upload_btn);
        pdf_name=findViewById(R.id.name);
        viewPDF_btn=findViewById(R.id.show_btn);

        //Database
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("Uploads");

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecFiles();
            }
        });
    }

    private void selecFiles(){
        Intent intent= new Intent();
        intent.setType("applicaction/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccione el archivo PDF..."), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            UploadFiles(data.getData());
        }
    }

    private void UploadFiles(Uri data){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Cargando...");
        progressDialog.show();

        StorageReference reference= storageReference.child("Uploads/" +System.currentTimeMillis()+ ".pdf");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isComplete());
                        Uri url = uriTask.getResult();

                        pdfClass pdfClass = new pdfClass(pdf_name.getText().toString(), url.toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(pdfClass);

                        Toast.makeText(UploadPDFs.this, "Archivo Subido!!", Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress = (100.0* snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        progressDialog.setMessage("Cargado:"+(int)progress+"%");
                    }
                });

        viewPDF_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShowPDFs.class);
                startActivity(intent);
            }
        });
    }
}