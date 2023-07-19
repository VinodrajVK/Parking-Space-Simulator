package com.example.parkingspaceallocator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.text.NumberFormat;
import java.util.ArrayList;

public class SpacesSizeInput extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spaces_size_input);
        Button enter = findViewById(R.id.button2);
        LinearLayout myLinearLayout = findViewById(R.id.Linearlayout);
        int numspaces = Integer.parseInt(getIntent().getStringExtra("numpark"));
        final EditText[] sizeentries = new EditText[numspaces];
        for (int i = 0; i < numspaces; i++) {
            final EditText spaces = new EditText(SpacesSizeInput.this);
            spaces.setInputType(InputType.TYPE_CLASS_NUMBER);
            spaces.setHint("Enter Size of Space-" + (i+1));
            myLinearLayout.addView(spaces);
            sizeentries[i] = spaces;
        }
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int flag=0,slot=-1;
                String spacesizes[] = new String[numspaces];
                for(int i=0;i<numspaces;i++)
                {
                    spacesizes[i]=sizeentries[i].getText().toString();
                }
                for(int i=0;i<numspaces;i++){
                    flag=0;
                    if(spacesizes[i].equals(""))
                    {
                        flag = 1;
                        slot=i;
                        break;
                    }
                }
                if(flag==1)
                {
                    sizeentries[slot].setError("Size not Entered!");
                }
                else {
                    Bundle b = new Bundle();
                    b.putStringArray("SpaceSizes" , spacesizes);
                    b.putInt("NumSpaces" , numspaces);
                    Intent intent = new Intent(SpacesSizeInput.this , FinalOutput.class);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }
        });


    }
}