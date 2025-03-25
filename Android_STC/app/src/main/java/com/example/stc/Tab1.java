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


public class Tab1 extends Fragment {
    private EditText ip1;
    private EditText it1;
    private EditText ius;
    private EditText ip0;
    private EditText ip02;
    private EditText iar;
    private TextView textView;
    private Button botao1;
    private String  p1,t1,us,p0,p02,ar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1, container, false);


        ip1 =  (EditText)rootView.findViewById(R.id.inputp1);
        it1 =  (EditText)rootView.findViewById(R.id.inputt1);
        ius =  (EditText)rootView.findViewById(R.id.inputus);
        ip0 =  (EditText)rootView.findViewById(R.id.inputp0);
        ip02 = (EditText)rootView.findViewById(R.id.inputp02);
        iar  = (EditText)rootView.findViewById(R.id.inputar);
        botao1 = (Button)rootView.findViewById(R.id.button1);

        botao1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                p1 = ip1.getText().toString();
                t1 = it1.getText().toString();
                us = ius.getText().toString();
                p0 = ip0.getText().toString();
                p02 = ip02.getText().toString();
                ar = iar.getText().toString();

                boolean bp1 = isEmpty(p1);
                boolean bt1 = isEmpty(t1);
                boolean bus = isEmpty(us);

                if (bp1 == false && bt1 == false && bus == false) {

                    Intent intent = new Intent(getActivity(), Activity_tab1.class);
                    String[] strNome = {"p1", "t1", "us", "p0", "p02", "ar"};
                    String[] valNome = {p1, t1, us, p0, p02, ar};

                    for (int i = 0; i <= 5; i++) {
                        intent.putExtra(strNome[i], valNome[i]);
                    }
                    startActivity(intent);

                }

            }


        });
        return rootView;

    }


    public static boolean isEmpty(String str)
    {
        return str.length()==0;        //Correct way to check empty
    }

}



