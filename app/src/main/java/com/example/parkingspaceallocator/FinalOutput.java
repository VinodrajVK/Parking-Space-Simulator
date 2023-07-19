package com.example.parkingspaceallocator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;



public class FinalOutput extends AppCompatActivity {

    String [] spacesizes;
    int numspaces;
    public void intentinit(String str){
        Bundle b = new Bundle();
        b.putStringArray("SpaceSizes" , spacesizes);
        b.putInt("NumSpaces" , numspaces);
        b.putString("Work" , str);
        Intent intent = new Intent(FinalOutput.this , VehicleInput.class);
        intent.putExtras(b);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_output);
        Bundle b = this.getIntent().getExtras();
        spacesizes = b.getStringArray("SpaceSizes");
        numspaces = b.getInt("NumSpaces");
        Button nomng = findViewById(R.id.nom);
        Button poormng = findViewById(R.id.poorm);
        Button bestmng = findViewById(R.id.bestm);
        nomng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentinit("NO");
            }
        });
        poormng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentinit("POOR");
            }
        });
        bestmng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentinit("BEST");
            }
        });
    }
}