package com.example.stc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class Activity_tab1 extends AppCompatActivity {
    private String  sp1,st1,sus,sp0,sp02,sar;
    private double  p1,t1,us,p0,p02,ar;
    private String  ssp0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.output_tab1);

        sp1 = intent.getStringExtra("p1");
        st1 = intent.getStringExtra("t1");
        sus = intent.getStringExtra("us");
        sp0 = intent.getStringExtra("p0");
        sp02 = intent.getStringExtra("p02");
        sar = intent.getStringExtra("ar");

        boolean bp0 = isEmpty(sp0);
        boolean bp02 = isEmpty(sp02);
        boolean bar = isEmpty(sar);

        p1 = Double.parseDouble(sp1);
        t1 = Double.parseDouble(st1);
        us = Double.parseDouble(sus);


        // ------------------------------------------------------------------------------------------------

        ShockTube stb  = new ShockTube();
        stb.t1 = t1;
        stb.p1 = p1*1e3;
        stb.us = us;

        stb.shock12();
        stb.shock25();


        if(bp0==false){
            p0 = Double.parseDouble(sp0);
            stb.ps = p0*1e6;
            ssp0 = "p0: ";
        } else {
            stb.ps = stb.p5;
            ssp0 = "from reflected cond.:\np0=p5 : ";
        }

        stb.shock5S();

        ShockTunnel stn = new ShockTunnel();
        stn.ar = ar;
        stn.p0 = stb.ps;
        stn.t0 = stb.ts;
        stn.r0 = stb.rs;
        stn.g0 = stb.gs;
        stn.h0 = stb.hs;
        stn.s0 = stb.ss;

        stn.p_m1eq();
        stn.p_m1fr();

        TextView throatF = (TextView) findViewById(R.id.throat2);
        throatF.setText(
                "pth:  " + strPressure(stn.pT) + " \n" +
                "Tth:  " + String.format("%.0f", stn.tT) + " K\n" +
                "rth:  " + strDensity(stn.rT) + " \n" +
                "uth:  " + String.format("%.0f", stn.uT) + " m/s\n" +
                "hth:  " + String.format("%.2f", stn.hT * 1e-6) + " MJ/kg"

        );


        if(bar==false) {
            ar = Double.parseDouble(sar);
            stn.ar = ar;   //

            stn.frozenA();

            TextView frozenA = (TextView) findViewById(R.id.frozenA);
            frozenA.setText(
                    "pe:  " + strPressure(stn.pFA)                    + " \n" +
                            "Te:  " + String.format("%.0f", stn.tFA)  + " K\n" +
                            "re:  " + strDensity(stn.rFA)             + " \n" +
                            "Me:  " + String.format("%.2f", stn.MFA)  + " \n" +
                            "ue:  " + String.format("%.0f", stn.uFA)  + " m/s\n" +
                            " ----- Post Normal Shock: \n" +
                            "pp:  " + strPressure(stn.ppFA)           + " \n" +
                            "Tp:  " + String.format("%.0f", stn.tpFA) + " K\n" +
                            "rp:  " + strDensity(stn.rpFA)           + " \n" +
                            "Mp:  " + String.format("%.2f", stn.MpFA) + " \n" +
                            " ----- At the Pitot: \n" +
                            "pt:  " + strPressure(stn.ptFA)           + " \n" +
                            "Tt:  " + String.format("%.0f", stn.ttFA) + " K\n" +
                            "rt:  " +  strDensity(stn.rtFA)
            );

        }

        if(bp02==false){
            p02 = Double.parseDouble(sp02);
            stn.pitot = p02 * 1e3; //

            stn.equilP();

            stn.frozenP();

            TextView frozenP = (TextView) findViewById(R.id.frozenP);
            frozenP.setText(
                    "pe:  " + strPressure(stn.pFP)                          + "  \n" +
                            "Te:  " + String.format("%.0f", stn.tFP    )    + " K\n" +
                            "re:  " + strDensity(stn.rFP)                   + " \n" +
                            "Me:  " + String.format("%.2f", stn.MFP )       + " \n" +
                            "ue:  " + String.format("%.0f", stn.uFP )       + " m/s\n" +
                            "eq. A/A*: " + strAreaRatio( stn.arFP ) + " \n" +
                            " ----- Post Normal Shock: \n" +
                            "pp:  " + strPressure(stn.ppFP)                 + " \n" +
                            "Tp:  " + String.format("%.0f", stn.tpFP    )   + " K\n" +
                            "rp:  " + strDensity(stn.rpFP)                  + " \n" +
                            "Mp:  " + String.format("%.2f", stn.MpFP)       + " \n" +
                            " ----- At the Pitot: \n" +
                            "pt:  " + strPressure(stn.ptFP)                 + "\n" +
                            "t:  " + String.format("%.0f", stn.ttFP    )   + "K\n" +
                            "rt:  " + strDensity(stn.rtFP)
            );

        }



//// ------------------------------------------------------------------------------------------------

        TextView out11 = (TextView) findViewById(R.id.out11);
        out11.setText(
                "Ms: " + String.format("%.2f", stb.Ms)              + " \n" +
                "us:  " + String.format("%.0f", stb.us)     + " m/s\n" +
                "p1:  " + strPressure(stb.p1)               + " \n" +
                "T1:  " + String.format("%.0f", stb.t1    ) + " K\n" +
                "r1:  " + strDensity(stb.r1)                + " \n" +
                "h1:  " + String.format("%.2f", stb.h1/1e6) + " MJ/kg\n" +
                "s1:  " + String.format("%.2f", stb.s1/1e3) + " kJ/(kg.K)\n" +
                "a1:  " + String.format("%.0f", stb.a1)     + " m/s"

        );


        TextView out12 = (TextView) findViewById(R.id.out12);
        out12.setText(
                "p2:  " + strPressure(stb.p2)                   + " \n" +
                "T2:  " + String.format("%.0f", stb.t2    )     + " K\n" +
                "r2:  " + strDensity(stb.r2)                    + " \n" +
                "u2:  " + String.format("%.0f", stb.u2) + " m/s\n" +
                "h2:  " + String.format("%.2f", stb.h2/1e6)     + " MJ/kg\n"+
                "s2:  " + String.format("%.2f", stb.s2/1e3)     + " kJ/(kg.K)\n" +
                "a2:  " + String.format("%.0f", stb.a2) + " m/s\n" +
                "M2:  " + String.format("%.2f", stb.u2/stb.a2)

        );

        TextView out25 = (TextView) findViewById(R.id.out25);
        out25.setText(
                        "p5:  " + strPressure(stb.p5) +"\n" +
                        "T5:  " + String.format("%.0f", stb.t5    ) + " K\n"+
                        "r5:  " + strDensity(stb.r5) + "\n" +
                        "h5:  " + String.format("%.2f", stb.h5/1e6) + " MJ/kg\n"+
                        "s5:  " + String.format("%.2f", stb.s5/1e3) + " kJ/(kg.K)\n"+
                        "a5:  " + String.format("%.0f", stb.a5) + " m/s\n" +
                        "g5:  " + String.format("%.3f", stb.g5) + "\n" +
                        "Mr:  " + String.format("%.2f", (stb.u2 + stb.vr)/stb.a2 )


        );

        TextView out58 = (TextView) findViewById(R.id.out58);
        out58.setText(
                ssp0 + strPressure(stb.ps) +"\n" +
                "T0:  " + String.format("%.0f", stb.ts    ) + " K\n"+
                "r0:  " + strDensity(stb.rs) + "\n" +
                "h0:  " + String.format("%.2f", stb.hs/1e6) + " MJ/kg\n"+
                "s0:  " + String.format("%.2f", stb.ss/1e3) + " kJ/(kg.K)\n"+
                "a0:  " + String.format("%.0f", stb.as) + " m/s\n"+
                "g0:  " + String.format("%.3f", stb.gs)

        );




    }

    public static boolean isEmpty(String str)
    {
        return str.length()==0;        //Correct way to check empty
    }

    public String strDensity(double value){
        String s="0";
        if      (value <= 1e-3){ s = String.format("%.2f", value*1e6) + " mg/m3";  }
        else if (value <= 1   ){ s = String.format("%.2f", value*1e3) + " g/m3";  }
        else if (value >= 1   ){ s = String.format("%.2f", value    ) + " kg/m3";  }
        return s;

    }

    public String strPressure(double value){
        String s="0";
        if      (value <= 1e3){ s = String.format("%.2f", value     ) + " Pa";  }
        else if (value <= 1e6){ s = String.format("%.2f", value*1e-3) + " kPa";  }
        else if (value >= 1e6){ s = String.format("%.2f", value*1e-6) + " MPa";  }
        return s;

    }

    public String strAreaRatio(double value){
        String s="0";
        if      (value <  100){ s = String.format("%.2f", value    ) ;  }
        else if (value >= 100){ s = String.format("%.0f", value    ) ;  }
        return s;

    }






}
