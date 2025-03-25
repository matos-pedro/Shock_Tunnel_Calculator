package com.example.stc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;



public class Tab2 extends Fragment {
    private EditText ip1;
    private EditText igf;
    private EditText it1;
    private EditText ip4;
    private EditText it4;
    private EditText ip0;
    private EditText ip02;
    private EditText iar;
    private TextView textView;
    private Button botao1;
    private String  p1,t1,p4,t4,gf,p0,p02,ar,gas4;
    private Spinner spinner;
    private int igas4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView2 = inflater.inflate(R.layout.tab2, container, false);

        spinner = rootView2.findViewById(R.id.spinner);

        final List <String> categories = new ArrayList<String>();

        categories.add("Helium");
        categories.add("Air");
        categories.add("Nitrogen");
        categories.add("Hydrogen");
        categories.add("Argon");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        spinner.setAdapter(dataAdapter);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                igas4 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ip1 =  (EditText)rootView2.findViewById(R.id.inputp1);
        it1 =  (EditText)rootView2.findViewById(R.id.inputt1);

        ip4 =  (EditText)rootView2.findViewById(R.id.inputp4);
        it4 =  (EditText)rootView2.findViewById(R.id.inputt4);

        igf  =  (EditText)rootView2.findViewById(R.id.inputgf);

        ip0 =  (EditText)rootView2.findViewById(R.id.inputp0);
        ip02 = (EditText)rootView2.findViewById(R.id.inputp02);
        iar  = (EditText)rootView2.findViewById(R.id.inputar);

        botao1 = (Button)rootView2.findViewById(R.id.button1);

        botao1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                p1 = ip1.getText().toString();
                t1 = it1.getText().toString();

                p4 = ip4.getText().toString();
                t4 = it4.getText().toString();

                gf = igf.getText().toString();

                gas4 = Integer.toString(igas4);


                p0 = ip0.getText().toString();
                p02 = ip02.getText().toString();
                ar = iar.getText().toString();

                boolean bp1 = isEmpty(p1);
                boolean bt1 = isEmpty(t1);

                boolean bp4 = isEmpty(p4);
                boolean bt4 = isEmpty(t4);


                if (bp1 == false && bt1 == false  && bp4 == false && bt4 == false) {

                    Intent intent = new Intent(getActivity(), Activity_tab2.class);
                    String[] strNome = {"p1", "t1", "p4", "t4" , "gf",  "p0", "p02", "ar", "gas4"};
                    String[] valNome = {  p1,   t1,   p4,    t4,   gf,    p0,   p02,   ar,   gas4};

                    for (int i = 0; i <= 8; i++) {
                        intent.putExtra(strNome[i], valNome[i]);
                    }
                    startActivity(intent);

                }

            }


        });
        return rootView2;

    }



    public static boolean isEmpty(String str)
    {
        return str.length()==0;        //Correct way to check empty
    }

}



