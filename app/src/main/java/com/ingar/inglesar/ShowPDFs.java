package com.ingar.inglesar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ShowPDFs extends AppCompatActivity {

    FloatingActionButton fab_upload, fab_home;
    ListView listView;
    DatabaseReference databaseReference;
    List<pdfClass> uploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pdfs);

        fab_upload=findViewById(R.id.fab_upload);
        fab_home=findViewById(R.id.fab_reg);

        listView=findViewById(R.id.listView);
        uploads= new ArrayList<>();
        
        //Crear Metodo
        viewAllFiles();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pdfClass pdfupload = uploads.get(i);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType("application/pdf");
                intent.setData(Uri.parse(pdfupload.getUrl()));
                startActivity(intent);
            }
        });

        fab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        fab_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UploadPDFs.class);
                startActivity(intent);
            }
        });

    }

    private void viewAllFiles() {

        databaseReference = FirebaseDatabase.getInstance().getReference("Uploads");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postsnapshot: snapshot.getChildren()){
                    pdfClass pdfClass = postsnapshot.getValue(com.ingar.inglesar.pdfClass.class);

                    uploads.add(pdfClass);
                }
                String[] Uploads = new String[uploads.size()];
                for(int i=0; i<Uploads.length;i++){
                    Uploads[i] = uploads.get(i).getName();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,Uploads){


                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView text = (TextView) view.findViewById(android.R.id.text1);
                        text.setTextColor(Color.BLACK);
                        text.setTextSize(22);

                        return view;
                    }
                };
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}