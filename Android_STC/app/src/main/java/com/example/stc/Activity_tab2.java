package com.example.stc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class Activity_tab2 extends AppCompatActivity {
    private String  sp1,st1,sp4,st4,sgf,sgas4,sp0,sp02,sar;
    private double  p1,t1,p4,t4,gf,gas4,p0,p02,ar;
    private String  ssp0;
    private double  us,ms, ms2;
    double e = 1e-4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.output_tab2);

        sp1 = intent.getStringExtra("p1");
        st1 = intent.getStringExtra("t1");

        sp4 = intent.getStringExtra("p4");
        st4 = intent.getStringExtra("t4");

        sgf = intent.getStringExtra("gf");
        sgas4 = intent.getStringExtra("gas4");

        sp0 = intent.getStringExtra("p0");
        sp02 = intent.getStringExtra("p02");
        sar = intent.getStringExtra("ar");

        boolean bp0 = isEmpty(sp0);
        boolean bp02 = isEmpty(sp02);
        boolean bar = isEmpty(sar);

        p1 = Double.parseDouble(sp1);
        t1 = Double.parseDouble(st1);

        p4 = Double.parseDouble(sp4);
        t4 = Double.parseDouble(st4);

        gf = Double.parseDouble(sgf);
        gas4 = Double.parseDouble(sgas4);


        double [] ams = {0,0,0};


        ms = guessMs(t4, t1, gas4, gf);

        double amsE = gf*(1e6*p4)/(1e3*p1);
        double res = 1.0;
        while (res > 1e-5){
            ams[0] = achaMs(    ms      ,  t4, t1, gas4, gf);
            ams[1] = achaMs(ms*(1+e),  t4, t1, gas4, gf);
            ams[2] = achaMs(ms*(1-e),  t4, t1, gas4, gf);
            ms2 = ms*( 1. - 2*e*(ams[0]-amsE)/(ams[1]-ams[2]) );
            res = (Math.abs((ms2-ms)/ms2));
            ms = ms2;
        }

        us = ms*Math.pow( 1.4*(8314.5/28.966)*t1 , 0.5 );


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
            ssp0 = "pr: ";
        } else {
            stb.ps = stb.p5;
            ssp0 = "from reflected cond.:\npr=p5 : ";
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
                "pt:  " + strPressure(stn.pT) + " \n" +
                "Tt:  " + String.format("%.0f", stn.tT) + " K\n" +
                "rt:  " + strDensity(stn.rT) + " \n" +
                "ut:  " + String.format("%.0f", stn.uT) + " m/s\n" +
                "ht:  " + String.format("%.2f", stn.hT * 1e-6) + " MJ/kg"

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
                            " rp:  " + strDensity(stn.rpFA)           + " \n" +
                            "Mp:  " + String.format("%.2f", stn.MpFA) + " \n" +
                            " ----- At the Pitot: \n" +
                            "pt:  " + strPressure(stn.ptFA)           + " \n" +
                            "Tt:  " + String.format("%.0f", stn.ttFA) + " K\n" +
                            " rt:  " +  strDensity(stn.rtFA)
            );

        }

        if(bp02==false){
            p02 = Double.parseDouble(sp02);
            stn.pitot = p02 * 1e3; //

            stn.equilP();

            stn.frozenP();

            TextView frozenP = (TextView) findViewById(R.id.frozenP);
            frozenP.setText(
                    "pe:  " + strPressure(stn.pFP)                          + "\n" +
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
                            "Tt:  " + String.format("%.0f", stn.ttFP    )   + "K\n" +
                            " rt:  " + strDensity(stn.rtFP)
            );

        }


//// ------------------------------------------------------------------------------------------------

        TextView textDriver = (TextView) findViewById(R.id.condDriver2);
        textDriver.setText(
                "Gas: " + strDriver(gas4)                    + " \n"  +
                        "p4:  " + strPressure(1e6*p4)            + " \n"  +
                        "T4:  " + String.format("%.0f", t4 ) + " K"

        );


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
                "h2:  " + String.format("%.2f", stb.h2/1e6)     + " MJ/kg\n"+
                "s2:  " + String.format("%.2f", stb.s2/1e3)     + " kJ/(kg.K) \n"+
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
                        "Tr:  " + String.format("%.0f", stb.ts    ) + " K\n"+
                        "rr:  " + strDensity(stb.rs) + "\n" +
                        "hr:  " + String.format("%.2f", stb.hs/1e6) + " MJ/kg\n"+
                        "sr:  " + String.format("%.2f", stb.ss/1e3) + " kJ/(kg.K)\n"+
                        "ar:  " + String.format("%.0f", stb.as) + " m/s\n"+
                        "gr:  " + String.format("%.3f", stb.gs)

        );




    }



    public static boolean isEmpty(String str)
    {
        return str.length()==0;
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

    public double achaMs(double Ms, double t4, double t1, double gas4, double gf){
        double g1 = 1.39800;
        double a1 = Math.pow( g1*(8314.5/28.966)*t1 , 0.5 );
        double us = Ms*a1;
        double g4=1.66,a4=Math.pow( g4*(8314.5/2)*t4 , 0.5 );

        int i = (int) gas4;

        switch (i) {
            case 0:
                g4 = 1.66;
                a4 = Math.pow( g4*(8314.5/4.002)*t4 , 0.5 );
                break;

            case 1:
                g4 = 1.4;
                a4 = Math.pow( g4*(8314.5/28.966)*t4 , 0.5 );
                break;

            case 2:
                g4 = 1.4;
                a4 = Math.pow( g4*(8314.5/28.0134)*t4 , 0.5 );
                break;

            case 3:
                g4 = 1.41;
                a4 = Math.pow( g4*(8314.5/2.016)*t4 , 0.5 );
                break;

            case 4:
                g4 = 1.66;
                a4 = Math.pow( g4*(8314.5/39.948)*t4 , 0.5 );
                break;
            default:
                break;
        }

        double p21  = ( 2*g1*Ms*Ms - (g1-1) )/(g1+1);
        double u2a1 =  ((2/(g1+1))*us*(Ms*Ms - 1)/(Ms*Ms))/a1 ;
        return p21*Math.pow(1-(a1/a4)*0.5*(g4-1)*u2a1*Math.pow(gf,-(g4-1)/(g4*2)),-2*g4/(g4-1));

    }

    public String strDriver(double gas4){
        String s="0";

        int i = (int) gas4;

        switch (i) {
            case 0:
                s = String.format("Helium");
                break;

            case 1:
                s = String.format("Air");
                break;

            case 2:
                s = String.format("Nitrogen");
                break;

            case 3:
                s = String.format("Hydrogen");
                break;

            case 4:
                s = String.format("Argon");
                break;
            default:
                break;
        }

        return s;




    }



    public double guessMs(double t4, double t1, double gas4, double gf){
        double g1 = 1.398000;
        double a1 = Math.pow( g1*(8314.5/28.966)*t1 , 0.5 );
        double g4=1.66,a4=Math.pow( g4*(8314.5/2.)*t4 , 0.5 );

        int i = (int) gas4;

        switch (i) {
            case 0:
                g4 = 1.66;
                a4 = Math.pow( g4*(8314.5/4.002)*t4 , 0.5 );
                break;

            case 1:
                g4 = 1.4;
                a4 = Math.pow( g4*(8314.5/28.966)*t4 , 0.5 );
                break;

            case 2:
                g4 = 1.4;
                a4 = Math.pow( g4*(8314.5/28.0134)*t4 , 0.5 );
                break;

            case 3:
                g4 = 1.41;
                a4 = Math.pow( g4*(8314.5/2.016)*t4 , 0.5 );
                break;

            case 4:
                g4 = 1.66;
                a4 = Math.pow( g4*(8314.5/39.948)*t4 , 0.5 );
                break;
            default:
                break;
        }
        double guess = (a4/a1)*((g1+1)/(g4-1.))*Math.pow(gf,(g4-1)/(g4*2));
        return guess;
    }




}
