package com.example.parkingspaceallocator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SpacesNumberEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spaces_number_entry);
        EditText number = findViewById(R.id.editTextNumber);
        Button go = findViewById(R.id.button);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String parkspace = number.getText().toString();
                if(parkspace.equals(""))
                {
                    number.setError("Number not Entered!");
                }
                else {
                    int parknum = Integer.parseInt(parkspace);
                    Intent intent = new Intent(SpacesNumberEntry.this , SpacesSizeInput.class);
                    intent.putExtra("numpark" , parkspace);
                    startActivity(intent);
                }
            }
        });

    }
}