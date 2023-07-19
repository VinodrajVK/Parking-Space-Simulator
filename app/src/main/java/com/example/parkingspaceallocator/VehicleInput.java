package com.example.parkingspaceallocator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

class ParkSpaces {
    int size;
    int vehno;
    int fragment;
    boolean fill;
}

class Vehicle {
    int size;
    int num;
    boolean allot;
}
class Input {
    void reset(ParkSpaces spaces[],int n,Vehicle veh[],int vn) {
        for (int i = 0; i < n; i++) {
            spaces[i].fragment = spaces[i].size;
            spaces[i].fill = false;
        }
        for (int i = 0; i < vn; i++) {
            veh[i].allot = false;
        }
    }
}
class Allocate {
    Input ip = new Input();
    int findlarg(ParkSpaces spaces[], int n) {
        int large = 0;
        for (int i = 0; i < n; i++) {
            if (spaces[i].size > spaces[large].size && !spaces[i].fill)
                large = i;
        }
        return large;
    }

    void first_fit(ParkSpaces spaces[], int n, Vehicle veh[], int vehno) {
        ip.reset(spaces,n,veh,vehno);
        for (int i = 0; i < vehno; i++) {
            for (int j = 0; j < n; j++) {
                if (spaces[j].fragment >= veh[i].size && !spaces[j].fill) {
                    spaces[j].fragment -= veh[i].size;
                    spaces[j].vehno=veh[i].num;
                    spaces[j].fill = true;
                    veh[i].allot=true;
                    break;
                }
            }
        }
    }

    void best_fit(ParkSpaces spaces[], int n, Vehicle veh[], int vehno) {
        ip.reset(spaces,n,veh,vehno);
        int slot = -1,cur=999999;
        for (int i = 0; i < vehno; i++) {
            for (int j = 0; j < n; j++) {
                if (spaces[j].fragment >= veh[i].size && !spaces[j].fill) {
                    if((spaces[j].fragment-veh[i].size)<=cur)
                    {
                        cur=spaces[j].fragment-veh[i].size;
                        slot=j;
                    }
                }
            }
            spaces[slot].fragment -= veh[i].size;
            spaces[slot].vehno=veh[i].num;
            spaces[slot].fill = true;
            veh[i].allot=true;
        }
    }

    void worst_fit(ParkSpaces spaces[], int n, Vehicle veh[], int vehno) {
        ip.reset(spaces,n,veh,vehno);
        int slot = -1;
        for (int i = 0; i < vehno; i++) {
            slot = findlarg(spaces, n);
            if(!(spaces[i].fill)&&!(spaces[slot].size>=veh[i].size))
                continue;
            spaces[slot].fragment -= veh[i].size;
            spaces[slot].vehno=veh[i].num;
            spaces[slot].fill = true;
            veh[i].allot=true;
        }
    }
    boolean checkfull(ParkSpaces spaces[], int n){
        boolean flag = true;
        for(int i=0;i<n;i++){
            if(!spaces[i].fill){
                flag=false;
                break;
            }
        }
        return flag;
    }
}
public class VehicleInput extends AppCompatActivity {
    EditText[] sizeentries = new EditText[0];
    ParkSpaces [] spaces;
    Vehicle [] veh = new Vehicle[0];
    String [] spacesizes;
    String [] vehsizes= new String[0];
    int numspaces,numvehicles;

    Allocate alloc = new Allocate();
    public void print(LinearLayout linearLayout){
        linearLayout.removeAllViews();
        String s="";
        TextView[] opstr = new TextView[numspaces];
        for (int i = 0; i < numspaces; i++) {
            final TextView str = new TextView(VehicleInput.this);
            if(!spaces[i].fill) {
                s = "Space-"+(i+1)+"("+spaces[i].size+")"+" : Not Filled";
            }
            else
            {
                s = "Space-"+(i+1)+"("+spaces[i].size+")"+" Filled By Vehicle-"+(spaces[i].vehno)+"\nSpace Wasted : "+(spaces[i].fragment);
            }
            str.setText(s);
            str.setTextAppearance(this,android.R.style.TextAppearance_Large);
            linearLayout.addView(str);
            opstr[i] = str;
        }
        for (int j = 0; j < numvehicles; j++) {
            final TextView str = new TextView(VehicleInput.this);
            if(!veh[j].allot) {
                s = "Vehicle-"+(j+1)+" : Not Alloted";
                str.setText(s);
                str.setTextAppearance(this,android.R.style.TextAppearance_Large);
                linearLayout.addView(str);
                opstr[j] = str;
            }}
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_input);
        Bundle b = this.getIntent().getExtras();
        spacesizes = b.getStringArray("SpaceSizes");
        numspaces = b.getInt("NumSpaces");
        String work = b.getString("Work");
        ImageButton add = findViewById(R.id.addbutton);
        Button enter2 = findViewById(R.id.enter2);
        Button goback = findViewById(R.id.goback);
        enter2.setVisibility(View.INVISIBLE);
        goback.setVisibility(View.INVISIBLE);
        LinearLayout linearLayout = findViewById(R.id.linearlayout);
        LinearLayout linearLayoutop = findViewById(R.id.linearLayoutop);
        TextView heading = findViewById(R.id.heading);
        spaces = new ParkSpaces[numspaces];
        for(int i=0;i<numspaces;i++){
            spaces[i] = new ParkSpaces();
            spaces[i].size = Integer.parseInt(spacesizes[i]);
        }
        String headstr="";
        switch (work) {
            case "NO":
                headstr = "No Management";
                break;
            case "POOR":
                headstr = "Poor Management";
                break;
            case "BEST":
                headstr = "Best Management";
                break;
        }
        heading.setText(headstr);
        add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(alloc.checkfull(spaces,numspaces)){
                        Toast.makeText(getApplicationContext(), "All Parking Spaces Full", Toast.LENGTH_LONG).show();
                        sizeentries = Arrays.copyOf(sizeentries, sizeentries.length - 1);
                    }
                    else {
                        sizeentries = Arrays.copyOf(sizeentries , sizeentries.length + 1);
                        final EditText vsize = new EditText(VehicleInput.this);
                        vsize.setInputType(InputType.TYPE_CLASS_NUMBER);
                        vsize.setHint("Enter Size of Vehicle");
                        linearLayout.addView(vsize);
                        sizeentries[sizeentries.length - 1] = vsize;
                        enter2.setVisibility(View.VISIBLE);
                        goback.setVisibility(View.VISIBLE);
                    }
                }
            });

        enter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout.removeAllViews();
                vehsizes = Arrays.copyOf(vehsizes, sizeentries.length);
                numvehicles=veh.length;
                if(sizeentries[sizeentries.length-1].getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Vehicle Size not Entered,Add Vehicle again", Toast.LENGTH_LONG).show();
                    sizeentries = Arrays.copyOf(sizeentries, sizeentries.length - 1);
                }
                else {
                    for (int i = 0; i < sizeentries.length; i++) {
                        vehsizes[i] = sizeentries[i].getText().toString();
                    }
                    veh = Arrays.copyOf(veh , vehsizes.length);
                    for (int i = 0; i < veh.length; i++) {
                        veh[i] = new Vehicle();
                        veh[i].size = Integer.parseInt(vehsizes[i]);
                        veh[i].num = i + 1;
                    }

                    switch (work) {
                        case "NO":
                            alloc.worst_fit(spaces , numspaces , veh , veh.length);
                            print(linearLayoutop);
                            break;
                        case "POOR":
                            alloc.first_fit(spaces , numspaces , veh , veh.length);
                            print(linearLayoutop);
                            break;
                        case "BEST":
                            alloc.best_fit(spaces , numspaces , veh , veh.length);
                            print(linearLayoutop);
                            break;
                    }
                }
            }
        });
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(VehicleInput.this,FinalOutput.class);
                startActivity(i);
            }
        });
    }
}
