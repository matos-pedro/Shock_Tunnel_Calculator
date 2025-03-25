package com.example.stc;
// App_99727449
class ShockTube{
    double t1,r1,p1,h1,a1,s1,us;
    double t2,r2,p2=0,h2=0,s2,u2,a2,Ms;
    double t5,r5,p5=0,h5=0,s5,u5,a5,vr=0,g5;
    double ps,ts,rs,hs,ss=s5,as,gs;
    double rtolNS = 1e-7; //used in normal shock 12 and 25
    double rtolPM = 5e-7;
    double rtolM  = 1e-7;

    void shock12(){
        //double tt,double pp,double uss
        double res=1;
        int j = 2;
        gasState gas=new gasState(t1,1.0,p1,1.0,1.0,1.0,1.0);
        gas.r_pt(); r1 = gas.r;
        gas.s_pr(); s1 = gas.s;
        gas.a_ps(); a1 = gas.a;
        gas.h_pr(); h1 = gas.h;

        r2 = r1*10;

        while(res>rtolNS){
            //processo iterativo para estabelecer 2

            p2 = p1 +  r1*us*us*(1.0 - r1/r2 );
            h2 = h1 +  0.5*us*us*(1.0 - Math.pow((r1/r2),2.0) );

            gas.p = p2; gas.h = h2;
            gas.r_ph(); r2 = gas.r;
            res = Math.abs(  (p1+r1*us*us*(1.0 - r1/r2) - p2)/p2  );
        }

        gas.h = h2; gas.p = p2; gas.r = r2;
        gas.t_pr(); t2 = gas.t;
        u2 = us*(1.-r1/r2);
        gas.s_pr(); s2 = gas.s;
        gas.a_ps(); a2 = gas.a;
        Ms = us/a1;

    }

    void shock25(){
        double res=1;
        int j = 5;
        gasState gas=new gasState(t2,r2,p2,1.0,s2,h2,a2);

        r5 = r2*10;
        while(res>rtolNS){
            vr = u2/(r5/r2  -  1.);
            //processo iterativo para estabelecer 2

            p5 = p2 +  r2*(vr + u2)*(vr + u2)*( 1. - r2/r5 );
            h5 = h2 + 0.5*(p5 - p2)*( 1./r2 + 1./r5 );

            gas.p = p5; gas.h = h5;
            gas.r_ph(); r5 = gas.r;
            res = Math.abs(  (p2 +  r2*(vr + u2)*(vr + u2)*( 1. - r2/r5 ) - p5)/p5  );
        }


        gas.h = h5; gas.p = p5; gas.r = r5;
        gas.h_pr(); g5 = gas.gamm;
        gas.t_pr(); t5 = gas.t;


        u5 = (r2/r5)*(u2+vr) - vr;

        gas.s_pr(); s5 = gas.s;
        gas.a_ps(); a5 = gas.a;


    }

    void shock5S(){
        gasState gas=new gasState(t5,r5,p5,1.0,s5,h5,a5);
        int j = 8;
        ss = s5;
        gas.s = ss; gas.p = ps;
        gas.r_ps(); rs = gas.r;
        gas.t_pr(); ts = gas.t;
        gas.h_pr(); hs = gas.h; gs = gas.gamm;
        gas.a_ps(); as = gas.a;
    }
}

class ShockTunnel{
    double ar,pitot,p0,t0,r0,g0,h0,s0;
    double ps,ts,rs,hs,ss,as,gs;
    double rt=0,ht=0,at,pt,pt2,tt,ut,mt,gt,st,alpha=0.1;
    double ute,rte,utf,rtf;
    double rza;
    double rtolNS = 1e-6;
    double rtolPM = 5e-5;
    double rtolM  = 1e-4;
    double mex=5.0;
    double pFA,tFA,rFA,MFA,uFA,  ppFA,tpFA,rpFA,spFA,MpFA,   ptFA,ttFA,rtFA      ;
    double pFP,tFP,rFP,MFP,uFP,  ppFP,tpFP,rpFP,spFP,MpFP,   ptFP,ttFP,rtFP,arFP ;
    double pEA,tEA,rEA,MEA,uEA,  ppEA,tpEA,rpEA,spEA,MpEA,   ptEA,ttEA,rtEA      ;
    double pEP,tEP,rEP,MEP,uEP,  ppEP,tpEP,rpEP,spEP,MpEP,   ptEP,ttEP,rtEP,arEP ;
    double pT, tT, rT, uT, hT;

    void   p_m1eq(){
        double res = 1.0;
        double mtn = 0  ;
        double mth = 1. ;
        gasState gas=new gasState(1.0,1.0,1.0,1.0,1.0,1.0,1.0);

        pt    = p0;
        gas.s = s0;

        while( res > rtolPM) {
            gas.p = pt; gas.r_ps();
            rt=gas.r; gas.h_pr();
            ht=gas.h; gas.a_ps();
            mtn = Math.sqrt(2.0*(h0-ht))/gas.a;
            pt2 = pt*(1. + alpha*(mtn-mth)/mth );
            res = (Math.abs((pt2-pt)/pt));
            pt = pt2;
        }
        ute = Math.sqrt( 2*(h0 - ht));
        rte = rt;
    }

    void   p_m1fr(){
        double res = 1.0;
        double mtn = 0  ;
        double mth = 1.  ;
        gasState gas=new gasState(1.0,1.0,1.0,1.0,1.0,1.0,1.0);

        pt = p0*Math.pow( ( 1. + 0.5*(g0-1.) ), -g0/(g0-1.) );
        tt = t0/( 1. + 0.5*(g0-1.) );
        rtf = r0*Math.pow( ( 1. + 0.5*(g0-1.) ),  -1/(g0-1.) ) ;
        utf = Math.sqrt( g0*pt/rtf   );

        pT = pt; tT = tt; rT = rtf; uT = utf;
        hT = h0 - 0.5*utf*utf;

    }

    double m_a(double area){
        double    res = 1.0;
        double [] arn = {0,0,0};
        double e = 1e-4;
        double mea=0,mea2;

        mea = 3;

        while (res > rtolM){
            arn[0] = (1/(mea      ))*Math.pow(2*(1/(g0+1))*(1+0.5*(g0-1.)*(mea)*(mea))            ,0.5*(g0+1)/(g0-1));
            arn[1] = (1/(mea*(1+e)))*Math.pow(2*(1/(g0+1))*(1+0.5*(g0-1.)*(mea*(1+e))*(mea*(1+e))),0.5*(g0+1)/(g0-1));
            arn[2] = (1/(mea*(1-e)))*Math.pow(2*(1/(g0+1))*(1+0.5*(g0-1.)*(mea*(1-e))*(mea*(1-e))),0.5*(g0+1)/(g0-1));
            mea2 = mea*( 1. - 2*e*(arn[0]-area)/(arn[1]-arn[2]) );
            res = (Math.abs((mea2-mea)/mea));
            mea = mea2;
        }

        return mea;

    }

    void  frozenA(){
        gasState gas=new gasState(1.0,1.0,1.0,1.0,1.0,1.0,1.0);
        double res = 1.0;
        double res2 = 1.0;
        double me,me2,pe,te,re,ue,he;
        double pps=0,tps,rps=0,rps2,hps=0,gps,ups,mps,sps=0,aps,pto,tto,rto;
        double arn [] = {0,0,0} ;
        double e= 1e-4;

        me = 5.0;

        while (res > rtolM){
            arn[0] = (1/(me      ))*Math.pow(2*(1/(g0+1))*(1+0.5*(g0-1.)*(me)*(me))            ,0.5*(g0+1)/(g0-1));
            arn[1] = (1/(me*(1+e)))*Math.pow(2*(1/(g0+1))*(1+0.5*(g0-1.)*(me*(1+e))*(me*(1+e))),0.5*(g0+1)/(g0-1));
            arn[2] = (1/(me*(1-e)))*Math.pow(2*(1/(g0+1))*(1+0.5*(g0-1.)*(me*(1-e))*(me*(1-e))),0.5*(g0+1)/(g0-1));
            me2 = me*( 1. - 2*e*(arn[0]-ar)/(arn[1]-arn[2]) );
            res = (Math.abs((me2-me)/me));
            me = me2;
        }

        pe = p0*Math.pow( ( 1. + 0.5*(g0-1.)*me*me ), -g0/(g0-1.) );
        te = t0/( 1. + 0.5*(g0-1.)*me*me );
        re = r0*Math.pow( ( 1. + 0.5*(g0-1.)*me*me ),  -1/(g0-1.) ) ;
        ue = me*Math.sqrt( g0*pe/re   );
        he = h0 - 0.5*Math.pow(ue, 2);

        rps = 10.*re;
        gas.t  = t0;

        while(res2>rtolNS){
            pps = pe +   re*ue*ue*(1.0 - re/rps );
            hps = he +  0.5*ue*ue*(1.0 - Math.pow((re/rps),2.0) );

            gas.h = hps;
            gas.p = pps;
            gas.r_ph();
            rps2 = gas.r;
            res2 = Math.abs(  (rps2 - rps)/rps );
            rps = rps2;
        }

        gas.t_pr(); tps = gas.t;
        gas.h_pr(); gps = gas.gamm;


        gas.s_pr(); sps = gas.s;
        gas.a_ps(); aps = gas.a;

        ups = Math.sqrt(2*(h0-hps)); mps = ups/aps;

        pto = pps*Math.pow( ( 1. + 0.5*(gps-1.)*mps*mps ), +gps/(gps-1.) );
        tto = tps*Math.pow( ( 1. + 0.5*(gps-1.)*mps*mps ), +1 );
        rto = rps*Math.pow( ( 1. + 0.5*(gps-1.)*mps*mps ), +1/(gps-1.) );

        pFA=pe;
        tFA=te;
        rFA=re;
        MFA=me;
        uFA=ue;

        ppFA=pps;
        tpFA=tps;
        rpFA=rps;
        spFA=sps;
        MpFA = mps;

        ptFA=pto;
        ttFA=tto;
        rtFA=rto;

    }

    void   frozenP(){
        gasState gas=new gasState(1.0,1.0,1.0,1.0,1.0,1.0,1.0);
        double aeq,res = 1.0;
        double res2 = 1.0;
        double e = 1e-2;
        double [] pitotC = {0,0,0};
        double me=0,ae=0,ae2,pe=0,te=0,re=0,ue=0,he=0;
        double pps=0,tps=0,rps=0,rps2,hps=0,gps=0,ups,mps=0,sto,sps=0,aps,pto=0,tto,rto;

        ae = arEP;

        while (res > rtolM){
            pitotC[0] = auxFP(ae)[0];
            pitotC[1] = auxFP(ae*(1+e))[0];
            pitotC[2] = auxFP(ae*(1-e))[0];
            ae2 = ae*( 1. - 2*e*(pitotC[0]-pitot)/(pitotC[1]-pitotC[2]) );
            res = (Math.abs((ae2-ae)/ae));
            ae = ae2;
        }

        pto = auxFP(ae)[0];

        pe = auxFP(ae)[1];
        te = auxFP(ae)[2];
        re = auxFP(ae)[3];
        me = auxFP(ae)[4];
        ue = auxFP(ae)[5];

        pps = auxFP(ae)[6];
        tps = auxFP(ae)[7];
        rps = auxFP(ae)[8];
        sps = auxFP(ae)[9];
        mps = auxFP(ae)[10];
        gps = auxFP(ae)[11];

        pto = pps*Math.pow( ( 1. + 0.5*(gps-1.)*mps*mps ), +gps/(gps-1.) );
        tto = tps*Math.pow( ( 1. + 0.5*(gps-1.)*mps*mps ), +1 );
        rto = rps*Math.pow( ( 1. + 0.5*(gps-1.)*mps*mps ), +1/(gps-1.) );
        //p_m1();
        aeq = ae;

        pFP=pe;
        tFP=te;
        rFP=re;
        MFP=me;
        uFP=ue;

        ppFP=pps;
        tpFP=tps;
        rpFP=rps;
        spFP=sps;
        MpFP= mps;

        ptFP=pto;
        ttFP=tto;
        rtFP=rto;

        arFP = aeq;

    }

    void   equilP(){
        gasState gas=new gasState(1.0,1.0,1.0,1.0,1.0,1.0,1.0);
        double res = 1.0;
        double res2 = 1.0;
        double e = 1e-4;
        double [] pitotC = {0,0,0};
        double pe2;
        double he,he2,me=0,pe=0,ae,te=0,re=0,ue=0,ar2,aeq;
        double pps=0,tps=0,rps=0,rps2,hps=0,gps=0,ups=0,mps=0,sps=0,aps,pto=0,tto,rto;

        pe = (1e-3)*pitot*Math.pow(((g0+1)*(g0+1)*mex*mex)/(4*g0*mex*mex-2*(g0-1)), -g0/(g0-1))*(g0+1)/(1-g0+2*g0*mex*mex);

        while (res > rtolM){
            pitotC[0] = auxEP(pe)[0];
            pitotC[1] = auxEP(pe*(1+e))[0];
            pitotC[2] = auxEP(pe*(1-e))[0];
            pe2 = pe*( 1. - 2*e*(pitotC[0]-pitot)/(pitotC[1]-pitotC[2]) );
            res = (Math.abs((pe2-pe)/pe));
            pe = pe2;
        }

        pto = auxEP(pe)[0];

        re = auxEP(pe)[1];
        ue = auxEP(pe)[2];

        arEP = rte*ute/(re*ue);

    }

    double[]   auxFP(double ag){
        gasState gas=new gasState(1.0,1.0,1.0,1.0,1.0,1.0,1.0);
        double res = 1.0;
        double res2 = 1.0;
        double e = 1e-4;
        double [] pgg = {0,0,0};
        double he,he2,me=0,pe=0,ae,te=0,re=0,ue=0,ar2,aeq;
        double pps=0,tps=0,rps=0,rps2,hps=0,gps,ups=0,mps,sps=0,aps,pto=0,tto,rto;


        me = m_a(ag);
        pe = p0*Math.pow( ( 1. + 0.5*(g0-1.)*me*me ), -g0/(g0-1.) );
        te = t0/( 1. + 0.5*(g0-1.)*me*me );
        re = r0*Math.pow( ( 1. + 0.5*(g0-1.)*me*me ),  -1/(g0-1.) ) ;
        ue = me*Math.sqrt( g0*pe/re   );
        he = h0 - 0.5*Math.pow(ue, 2);

        rps = 10.*re;
        gas.t  = t0;

        while(res2>rtolNS){
            pps = pe +   re*ue*ue*(1.0 - re/rps );
            hps = he +  0.5*ue*ue*(1.0 - Math.pow((re/rps),2.0) );

            gas.h = hps;
            gas.p = pps;
            gas.r_ph();
            rps2 = gas.r;
            res2 = Math.abs(  (rps2 - rps)/rps );
            rps = rps2;
        }
        res2 = 1.0;

        gas.t_pr(); tps = gas.t;
        gas.h_pr(); gps = gas.gamm;
        gas.s_pr(); sps = gas.s;
        gas.a_ps(); aps = gas.a;

        ups = Math.sqrt(2*(h0-hps));
        mps = ups/aps;

        pto = pps*Math.pow( ( 1. + 0.5*(gps-1.)*mps*mps ), +gps/(gps-1.) );

        double [] data = {pto,pe,te,re,me,ue,pps,tps,rps,sps,mps,gps};

        return data;


    }

    double[]   auxEP(double pg){
        gasState gas=new gasState(1.0,1.0,1.0,1.0,1.0,1.0,1.0);
        double res = 1.0;
        double res2 = 1.0;
        double [] pgg = {0,0,0};
        double he,he2,me=0,pe=0,ae,te=0,re=0,ue=0,ar2,aeq;
        double pps=0,tps=0,rps=0,rps2,hps=0,gps,ups=0,mps,sps=0,aps,pto=0,tto,rto;

        pe = pg;
        gas.s = s0;

        gas.p = pe;
        gas.r_ps(); re = gas.r;
        gas.h_pr(); he = gas.h;
        gas.t_pr(); te = gas.t;

        ue = Math.sqrt(2*(h0-he));

        gas.a_ps(); ae = gas.a;
        me = ue/ae;

        rps = 10.*re;
        gas.t = t0;

        while(res2>rtolNS){
            pps = pe +   re*ue*ue*(1.0 - re/rps );
            hps = he +  0.5*ue*ue*(1.0 - Math.pow((re/rps),2.0) );

            gas.h = hps;
            gas.p = pps;
            gas.r_ph();
            rps2 = gas.r;

            res2 = Math.abs(  (rps2 - rps)/rps );
            rps = rps2;
        }

        gas.t_pr(); tps = gas.t;
        gas.h_pr(); gps = gas.gamm;
        gas.s_pr(); sps = gas.s;
        gas.a_ps(); aps = gas.a;
        ups = Math.sqrt(2*(h0-hps)); mps = ups/aps;

        ups = Math.sqrt(2*(h0-hps)); mps = ups/aps;
        pto = pps*Math.pow( ( 1. + 0.5*(gps-1.)*mps*mps ), +gps/(gps-1.) );

        double [] data = {pto,re,ue};

        return data;


    }


}

class gasState {
    double t,r,p,e,s,h,a,gamm=0;
    boolean error;
    double rtolSPR = 1e-5;
    double rtolRPH = 1e-5;

    gasState(double tt,double rr, double pp, double ee, double ss, double hh, double aa) {
        t=tt;r=rr;p=pp;e=ee;s=ss;h=hh;a=aa;error=false;
    }

    int s_pr() {//Determines entropy iteratively as functions of p and r
        double s0,p0,t0,r0,h0,gascon,rrn,aggn,ggn,temp;
        double ss,rref;
        double rn [] = new double[3];
        double res=1.0;
        int i,l,ic,it;

        s0=6779.2;p0=p;r0=r;

        rref = r;
        ss = s0;

        it = 0;
        while(res > rtolSPR && it < 50){

            s =          ss; r_ps(); rn[0] = r;
            s = (1+1e-7)*ss; r_ps(); rn[1] = r;
            s = (1-1e-7)*ss; r_ps(); rn[2] = r;

            ss = s*( 1. - 2e-7*(rn[0]-rref)/(rn[1]-rn[2]) );
            res = (Math.abs((ss-s)/s));
            it = it + 1;
        }

        s = Math.max(ss,s);

        return(0);
    }

    int r_ph() {//Determines density iteratively as functions of p and h
        double s0,p0,t0,r0,h0,gascon,rrn,aggn,ggn,href;
        double rr[]=new double[51];
        double gg[]=new double[51];
        int j,l,ic;

        s0=6779.2;p0=101330.0;t0=273.15;r0=1.292;h0=156873;gascon=287.06;
        rr[1]= 1.0*(p/t)*(t0/p0);
        rr[2]= 0.9*rr[1];
        href=h;

        for(ic=1;ic<=100;ic++) {        //Bracket solution;
            for(j=1;j<=2;j++) {
                r=rr[j]*r0;
                h_pr();
                gg[j]=(h-href)/href;
            }

            if(gg[1]*gg[2]>0.0) {rr[1]*=2.;rr[2]*=0.5;}
            else break;
        }
        if(ic>=10) {error=true;return(0);}

        for(j=1;j<=50;j++) {           //Regula Falsi
            rrn=(rr[2]*gg[1]-rr[1]*gg[2])/(gg[1]-gg[2]);
            r=rrn*r0;
            h_pr();
            ggn=(h-href)/href;
            aggn=Math.abs(ggn);
            if(aggn<0.1) break;
            if(ggn*gg[1]>0.0) {rr[1]=rrn;gg[1]=ggn;}
            else {rr[2]=rrn;gg[2]=ggn;}
        }

        for(j=3;j<=50;j++) {             //Secant Method
            rr[j]=rr[j-1]-gg[j-1]*(rr[j-1]-rr[j-2])/(gg[j-1]-gg[j-2]);
            r=rr[j]*r0;
            h_pr();
            gg[j]=(h-href)/href;
            if(Math.abs(gg[j])<5.e-4) break;
        }

        h=href;error=false;
        return(0);
    }

    int r_pt() {//Determines density iteratively as functions of p and t (old airtbn routine)
        double s0,p0,t0,r0,h0,gascon,rrn,aggn,ggn,temp;
        double rr[]=new double[51];
        double gg[]=new double[51];
        int j,l,ic;

        s0=6779.2;p0=101330.0;t0=273.15;r0=1.292;h0=156873;gascon=287.06;
        rr[1]=p*t0/t/p0;
        rr[2]=0.9*rr[1];
        temp=t;

        for(ic=1;ic<=10;ic++) {        //Bracket solution;
            for(j=1;j<=2;j++) {
                r=rr[j]*r0;
                t_pr();
                gg[j]=(t-temp)/temp;
            }
            if(gg[1]*gg[2]>0.0) {rr[1]*=2.;rr[2]*=0.5;}
            else break;
        }
        if(ic>=10) {error=true;return(0);}

        for(j=1;j<=50;j++) {           //Regula Falsi
            rrn=(rr[2]*gg[1]-rr[1]*gg[2])/(gg[1]-gg[2]);
            r=rrn*r0;
            t_pr();
            ggn=(t-temp)/temp;
            aggn=Math.abs(ggn);
            if(aggn<0.1) break;
            if(ggn*gg[1]>0.0) {rr[1]=rrn;gg[1]=ggn;}
            else {rr[2]=rrn;gg[2]=ggn;}
        }

        for(j=3;j<=50;j++) {             //Secant Method
            rr[j]=rr[j-1]-gg[j-1]*(rr[j-1]-rr[j-2])/(gg[j-1]-gg[j-2]);
            r=rr[j]*r0;
            t_pr();
            gg[j]=(t-temp)/temp;
            if(Math.abs(gg[j])<5.e-4) break;
        }
        t=temp;error=false;
        return(0);
    }

    int r_ps() {//Determines density as a function or pressure and entropy  (old tgas5.f)
        double p0 = 101330.0,r0 = 1.292,s0 = 6779.2,gascon = 287.6;
        double x,snon,z,delts,rratio,y,zm;

        x = Math.log(p/p0)/Math.log(10);
        snon = Math.log(s/gascon)/Math.log(10.);
        z = x - snon;

        if (snon < 1.23) {
            delts = s - s0;
            rratio = (Math.log(p/p0))/(Math.log(10.)) / 1.4-delts / (3.5*gascon);
            r = r0*Math.exp(rratio);
            return(0);
        }
        if (snon < 1.42) {              // line 10
            y=-17.2119+54.9354*snon;
            y+=(-1.99776+3.17884*snon)*z;
            y+=(-46.9831-0.866580*z+12.1069*snon)*snon*snon;
            y+=(0.158567-0.103055*snon-0.00152322*z)*z*z;
            r=Math.pow(10.,y)*r0;
            return(0);
        }
        if (snon < 1.592 ) {  // line 20
            y=280.393-595.834*snon+(-17.5934+24.9706*snon)*z+(421.767-8.85461*z-99.4515*snon)*snon*snon+(0.36189-0.255458*snon-0.00296892*z)*z*z;
            y/=1.0+Math.exp(-15.0*(x+54.179-86.947*snon+33.583*snon*snon));
            y+=-278.074+611.791*snon+(13.7528-19.2394*snon)*z+(-442.909+7.10425*z+105.869*snon)*snon*snon+(-0.197269+0.149708*snon+0.00119153*z)*z*z;
            r=Math.pow(10,y)*r0;
            return(0);
        }
        if (snon<1.70) { // line 30
            zm = 7.5269*snon-14.9366;
            if (z>=zm) { // line 40
                y=-50.1859+63.1564*snon+(2.39925-1.47883*snon)*z-19.8852*snon*snon+(0.466791-0.306926*snon)*z*z;
                y/=1.0+Math.exp(-2.0*(z+11.16*snon-18.203));
                y+=39.7149-48.0988*snon+(-1.17821+1.27522*snon)*z+14.5552*snon*snon+(-0.250279+0.158399*snon)*z*z;
            }
            else {
                y=-68.5292+82.3834*snon+(-1.24942+0.831615*snon)*z-24.524*snon*snon+(-0.113019+0.0746719*snon)*z*z;
                y/=1.0+Math.exp(-2.0*(z+33.976*snon-49.659));
                y+=110.732-133.968*snon+(0.37583+0.277887*snon)*z+40.3018*snon*snon+(0.118506-0.0698812*snon)*z*z;
            }
            r=Math.pow(10,y)*r0;
            return(0);
        }
        if (snon<1.80) {  // line 50
            zm = 7.5269*snon-14.9366;
            if(z<zm) {
                y=-72.8463+94.2375*snon+(4.04979-2.45467*snon)*z-30.2865*snon*snon+(0.12872-0.0863721*snon)*z*z;
                y/=1.0+Math.exp(-2.0*(z+13.4576*snon-18.36));
                y+=62.2639-78.884*snon+(-2.19123+1.84533*snon)*z+24.8413*snon*snon+(-0.0876105+0.0555047*snon)*z*z;
            }
            else {
                y=60.5677-70.8307*snon+(-2.95622+1.70504*snon)*z+20.7549*snon*snon+(-0.270962+0.180057*snon)*z*z;
                y/=1.0+Math.exp(-2.0*(z+16.822*snon-29.139));
                y+=-29.9578+38.9998*snon+(2.90256-1.25088*snon)*z-12.6641*snon*snon+(0.0613867-0.0517059*snon)*z*z;
            }
            r=Math.pow(10,y)*r0;
            return(0);
        }
        if(snon<1.90) { // line 70
            y=-639.514+697.458*snon+(-100.154+106.701*snon)*z+(-190.745-28.6323*z)*snon*snon+(-0.481471+0.200348*snon-0.00643371*z)*z*z;
            y/=1.0+Math.exp(-2.0*(z+35.275*snon-58.624));
            y+=623.124-677.571*snon+(91.2811-95.548*snon)*z+(184.603+25.4274*z)*snon*snon+(0.697635-0.327916*snon+0.00490838*z)*z*z;
            r=Math.pow(10,y)*r0;
            return(0);
        }
        if(snon<2.00) {      // line 80
            y=-113.217+124.304*snon+(4.41505-2.4853*snon)*z-34.2370*snon*snon+(-0.0784297+0.0121459*snon)*z*z;
            y/=1.0+Math.exp(-2.00*(z+20.884*snon-36.54));
            y+=14.0088-15.8855*snon+(0.171245+0.432352*snon)*z+4.42836*snon*snon+(-0.00954417+0.00892335*snon)*z*z;
            r=Math.pow(10,y)*r0;
            return(0);
        }
        if(snon<=2.10) {   // line 90
            y=(65.2920-62.8154*snon)+(2.10906-1.11759*snon)*z+15.0026*snon*snon+(0.271716-0.14431*snon)*z*z;
            y/=1.0+Math.exp(-5.00*(z+28.284*snon-53.185));
            y+=-36.2767+38.634*snon+(1.48507-0.341824*snon)*z-10.4718*snon*snon+(-0.0325437+0.0122032*snon)*z*z;
            r=Math.pow(10,y)*r0;
            return(0);
        }
        if(snon>2.10) {  // line 100
            y=-127.867+85.8453*snon+(-22.8808+11.3034*snon)*z-11.234*snon*snon+(-1.56005+0.779397*snon)*z*z;
            y/=1.0+Math.exp(-2.00*(z+15.048*snon-26.307));
            y+=186.938-145.261*snon+(22.3883-10.4974*snon)*z+26.0284*snon*snon+(1.85949-0.898218*snon)*z*z;
            r=Math.pow(10,y)*r0;
            return(0);
        }
        return(0);
    }

    int a_ps() {//speed of sound as a function of pressure and entropy (old tgas7.f)

        double p0 = 101330,	s0 = 6779.2, r0 = 1.292, a0 = 331.3613, gascon = 287.06;
        double x,snon,y,z,zm,delts,asqlog;

        x=(Math.log(p/p0))/(Math.log(10));
        snon=(Math.log(s/gascon))/(Math.log(10));
        z = x - snon;

        if(snon < 1.23) {
            delts = s-s0;
            asqlog = Math.log(1.4*p0/r0)+(Math.log(p/p0)+delts/gascon)/3.5;
            a = Math.exp(asqlog/2.0);
            return(0);}
        else if(snon <= 1.4) y = (-.138377-8.84138*snon) + ((2.6105-3.16535*snon)*z) + ((11.0866+.988389*z-3.25761*snon)*snon*snon) + ((-.100224+.0662193*snon+.00082061*z)*z*z);
        else if(snon < 1.595) y = (131.057-288.847*snon) + ((-5.04887+7.73862*snon)*z) + ((210.147-2.88963*z-50.396*snon)*snon*snon) + ((.0548031-.0439459*snon-.000210202*z)*z*z) + ( (-133.465+284.739*snon) + ((7.57389-10.7749*snon)*z) + ((-202.362+3.8313*z+47.9075*snon)*snon*snon) + ((-.153453+.108531*snon+.00097931*z)*z*z))/(1.0+(Math.exp(-15.0*(x+54.179-86.947*snon+33.583*snon*snon))));
        else if(snon < 1.693) {
            zm = -9.842*snon+14.19;
            if(z <= zm) y = (-61.3548+78.0742*snon) + ((2.08524-1.21609*snon)*z) + (-24.3686*snon*snon) + ((.0877563-.0546311*snon)*z*z) + ( (20.7952-27.1591*snon) + ((-.743673+.408312*snon)*z) + (8.68124*snon*snon) + ((-.093592+.0532328*snon)*z*z))/(1.0+(Math.exp(-2.0*(z+38.785*snon-57.157))));
            else y = (3.37056-4.87016*snon) + ((-.385754+.287192*snon)*z) + (2.02041*snon*snon) + ((-.00463144+.00830832*snon)*z*z);}
        else if(snon <1.80) {
            zm = -1.917*snon+0.092;
            if( z <= zm) y = (-80.4927+76.3739*snon) + ((-9.381+5.72104*snon)*z) + ( -16.3435*snon*snon) + ((-.748578+.450043*snon)*z*z) + ((83.4054-85.8837*snon) + ((4.84197-3.11188*snon)*z) + (21.1196*snon*snon) + ((.233945-.159099*snon)*z*z))/(1.0+(Math.exp(-2.0*(z+7.874*snon-7.569))));
            else y = (-4.73308+4.69363*snon) + ((.0943798+.00354953*snon)*z) + (-.798293*snon*snon) + ((.0202561-.00873036*snon)*z*z);}
        else if(snon < 1.9) y = (-660.574+738.042*snon) + ((-87.7589+97.894*snon)*z) + ((-206.156-27.3753*z)*snon*snon) + (-.0214028*z*z) + ((665.014-743.416*snon) + ((88.7679-99.0508*snon)*z) + ((208.117+27.7374*z)*snon*snon) + (.0281148*z*z))/(1.0+(Math.exp(-2.0*(z+35.275*snon-58.624))));
        else if(snon < 2.0) y = (-5.93554-7.79929*snon) + ((-7.23618+3.31162*snon)*z) + (5.06381*snon*snon) + ((-.5377735+.246865*snon)*z*z) + ((39.526-29.0994*snon) + ((6.24136-2.70007*snon)*z) + (5.42786*snon*snon) + ((.595235-.267771*snon)*z*z))/(1.0+(Math.exp(-2.0*(z+13.6751*snon-20.1676))));
        else if(snon < 2.1) y = (70.2453-73.4732*snon) + (-.586844*z) + ((19.5548+.181521*z)*snon*snon) + (.00736786*z*z) + ((-16.961+18.0169*snon) + ( -.365779*z) + ((-4.75063+.107583*z)*snon*snon) + (.00201803*z*z))/(1.0+(Math.exp(-5.0*(z+28.284*snon-53.185))));
        else y = (-112.793+135.296*snon) + (2.74295*z) + ((-40.8171-1.29257*z)*snon*snon) + (-.225652*z*z) + ((257.057-283.199*snon) + ( -5.84656*z) + ((79.1574+2.17312*z)*snon*snon) + (.335658*z*z))/(1.0+(Math.exp(-2.0*(z+15.048*snon-26.307))));

        a = Math.pow(10,y)*a0;
        return(0);
    }

    int h_pr() {//enthalpy as a function of pressure and density (old tgas4.f)

        double r0 = 1.292, p0 = 101330;
        double y,x,z1,hhigh=0.0,hlow,yhigh=0.0,ylow=0.0,ym=0.0,rho;
        int jflag=0,iflag=0;

        rho=r;
        y = Math.log(rho/r0)/Math.log(10);
        x = Math.log(p/p0)/Math.log(10);

        if (Math.abs(y+4.5) < .045 ) {
            iflag=0;jflag=-1;ym=y;y=-4.5+.045;yhigh=y;rho = Math.pow(10,y)*r0;
        }
        else if (Math.abs(y+0.5) < .005 ) {
            iflag=1;jflag=-1;ym=y;y=-0.5+.005;yhigh=y;rho = Math.pow(10,y)*r0;
        }
        else {iflag=-1;jflag=-1;}

        for (;;) {
            z1=x-y;
            if (y> -0.5) {
                if (z1 <= 0.1) gamm=1.4017;
                else if (z1 <= 1.05) gamm=(-96.7488+.205296*y)+((269.927-1.92887*y)*z1)+((.378392-.324965*z1-.00361036*y)*y*y)+((-246.711+1.54416*y+74.8760*z1)*z1*z1)+((98.1502-.205488*y)+((-269.913+1.93052*y)*z1)+((-.378527+.324832*z1+.00366182*y)*y*y)+((246.63-1.54646*y-74.898*z1)*z1*z1))/(1.0-(Math.exp(-26.59865+1.564631*y+23.12926*z1-1.360543*y*z1)));
                else if (z1 <= 1.6) gamm=(-.267593-.187457*y)+((5.07693+.272286*y)*z1)+((.0104541-.0142211*z1+.000638962*y)*y*y)+((-5.0852-.0781935*y+1.58711*z1)*z1*z1)+((2.87969+.39009*y)+((-8.06179-.55125*y)*z1)+((-.0101903+.0135906*z1-.000897772*y)*y*y)+((7.29592+.183861*y-2.15153*z1)*z1*z1))/(1.0+(Math.exp(182.8573-34.28596*y-151.786*z1+29.7621*y*z1)));
                else if (z1<=2.3) gamm=(.921537-.23967*y)+((1.30714+.34299*y)*z1)+((-.0218847+.0136691*z1-.000490274*y)*y*y)+((-1.20916-.110206*y+.308792*z1)*z1*z1)+((-6.77089-.0690476*y)+((8.18168-.0952708*y)*z1)+((.0298487-.0178706*z1+.000628419*y)*y*y)+((-3.07662+.0660408*y+.33859*z1)*z1*z1))/(1.0+(Math.exp(159.16669+39.76192*y-79.66199*z1-16.667*y*z1)));
                else error=true;}

            else if (y > -4.5) {
                if (z1 <= 0.1) gamm=1.399;
                else if (z1 <= 0.95) gamm=(-133.083-9.98707*y)+((394.734+23.581*y)*z1)+((1.43957-1.43175*z1+.0000177068*y)*y*y)+((-384.712-13.6367*y+124.325*z1)*z1*z1)+((134.486+9.99122*y)+((-394.719-23.5853*y)*z1)+((-1.43799+1.43039*z1+.000144367*y)*y*y)+((384.616+13.6318*y-124.348*z1)*z1*z1))/(1.0+(Math.exp(-21.41444+1.381584*y+20.39473*z1-1.315789*y*z1)));
                else if (z1 <=1.5) gamm=(-7.36684-1.13247*y)+((24.7879+1.99625*y)*z1)+((-.049163+.0416673*z1-.000658149*y)*y*y)+((-23.299-.859418*y+7.19016*z1)*z1*z1)+((-2.42647+.557912*y)+((-2.03055-1.22031*y)*z1)+((.0374866-.0339278*z1+.000521042*y)*y*y)+((7.75414+.608488*y-3.68326*z1)*z1*z1))/(1.0+(Math.exp(80.77385-12.73807*y-65.47623*z1+11.90475*y*z1)));
                else if (z1 <= 2.00) gamm=(.43152-.283857*y)+((2.27791+.399159*y)*z1)+((-.0129444+.00878724*z1-.000160583*y)*y*y)+((-1.84314-.128136*y+.445362*z1)*z1*z1)+((-10.3883-.358718*y)+((13.5068+.187268*y)*z1)+((-.00428184-.000952016*z1-.0000410506*y)*y*y)+((-5.63894-.00145626*y+.739915*z1)*z1*z1))/(1.0+(Math.exp(294.9221+13.6866*y-155.9335*z1-3.787766*y*z1)));
                else if (z1 <= 2.5) gamm=(-3.77766-.553738*y)+((6.60834+.487181*y)*z1)+((-.0211045+.00967277*z1-.00021942*y)*y*y)+((-2.94754-.102365*y+.43962*z1)*z1*z1)+((40.5813+3.25692*y)+((-47.9583-2.5366*y)*z1)+((.0906436-.0347578*z1+.00100077*y)*y*y)+((18.904+.494114*y-2.48554*z1)*z1*z1))/(1.0+(Math.exp(534.718+74.95657*y-221.9822*z1-30.17229*y*z1)));
                else error=true;}

            else if ( z1 > 0.1) {
                if (z1 <= 0.85) gamm=(253.908+101.491*y)+((-387.199-154.304*y)*z1)+((7.28532-8.04378*z1-.00182577*y)*y*y)+((98.6233+46.3763*y+21.8994*z1)*z1*z1)+((-252.423-101.445*y)+((387.21+154.298*y)*z1)+((-7.2773+8.04277*z1+.00228399*y)*y*y)+((-98.7576-46.3883*y-21.9438*z1)*z1*z1))/(1.0-(Math.exp(-11.0+2.0*y+11.0*z1-2.0*y*z1)));
                else if (z1 <= 1.30) gamm=(-10.5745-1.9369*y)+((30.7202+3.35578*y)*z1)+((-.0779965+.066879*z1-.000986882*y)*y*y)+((-26.0637-1.42391*y+7.23223*z1)*z1*z1)+((-18.6342+.0241997*y)+((32.0880-.746914*y)*z1)+((.0375161-.0410125*z1+.000574637*y)*y*y)+((-16.9985+.539041*y+2.56253*z1)*z1*z1))/(1.0+(Math.exp(276.8567+21.52383*y-216.4837*z1-13.94837*y*z1)));
                else if (z1 <= 1.95) gamm=(.617584-.24069*y)+((1.95904+.341644*y)*z1)+((-.0101073+.00677631*z1-.000115922*y)*y*y)+((-1.68951-.110932*y+.426058*z1)*z1*z1)+((-13.4222-.543713*y)+((18.1528+.395928*y)*z1)+((-.00741105+.00167768*z1-.00000332714*y)*y*y)+((-7.97425-.0580593*y+1.12448*z1)*z1*z1))/(1.0+(Math.exp(86.77803-8.370349*y-40.74084*z1+7.407405*y*z1)));
                else if (z1 <= 2.6) gamm=(-8.32595-.350219*y)+((13.6455+.35935*y)*z1)+((-.00370109+.00330836*z1+.000110018*y)*y*y)+((-6.49007-.0838594*y+1.02443*z1)*z1*z1)+((-30.8441-1.4951*y)+((30.0585+.91965*y)*z1)+((-.0360024+.0102522*z1-.00046876*y)*y*y)+((-9.33522-.135228*y+.892634*z1)*z1*z1))/(1.0+(Math.exp(88.00047-16.79356*y-33.33353*z1+8.465574*y*z1)));
                else error=true; }

            else gamm=1.3986;

            /* Out of Range */
            if(error) return(0);

            h=gamm/(gamm-1.0)*p/rho;

            /* Immediate Exit */
            if(iflag==-1 || jflag==1) return(0);

            /* Exit with Calculation */
            if(jflag==0) {hlow=h;h=hlow+(hhigh-hlow)/(yhigh-ylow)*(ym-ylow);return(0) ;}

            /* Iterate */
            y=-4.5-0.045;
            if(iflag==1) y=-0.5-0.005;
            hhigh=h;ylow=y;rho=Math.pow(10,y)*r0;jflag=0;}

    }

    int t_pr() {//temperature as a function of pressure and density (old tgas3.f)

        double r0 = 1.292,p0 = 101330,t0 = 273.15,gascon = 287.06;
        double y,x,tlow,thigh=0.,ylow=0.,yhigh=0.,ym=0.,rsave=0.,rho,z1,tnon;
        int iflag,jflag=0;

        rho=r;
        y=(Math.log(rho/r0))/(Math.log(10));
        x=(Math.log(p/p0))/(Math.log(10));


        if (Math.abs(y+4.5)<.045){iflag=0;rsave= rho;ym = y;y= -4.5+.045;yhigh = y;rho = Math.pow(10.0,y)*r0;jflag = -1;}
        else if (Math.abs(y+0.5)<.005){iflag = 1;rsave = rho;ym = y;y = -0.5 + .005;yhigh =y;rho = Math.pow(10.0,y)*r0;jflag=-1;}
        else iflag = -1;

        for(;;) {
            z1 = x-y;
            if (y>-0.5){			// goto 190
                if (z1<= .25) {
                    t = p/(rho * gascon);}
                else if(z1 <= 1) {
                    tnon = (-.00154141+.000658337*y) + ((.982201-.00385028*y)*z1) + ((.000123111-.00040821*z1+.0000213592*y)*y*y) + ((.0377441+.00456963*y-.0235172*z1)*z1*z1);
                    t = Math.pow(10,tnon)*t0;}
                else if(z1<=1.45) {
                    tnon = (.806492+.0991293*y) + ((-1.70742-.228264*y)*z1) + ((.005035-.00613927*z1+.000169824*y)*y*y) + ((3.02351+.131574*y-1.12755*z1)*z1*z1) + ((-.11793-.212207*y) + ((1.36524+.405886*y)*z1) + ((-.018823+.0165486*z1-.0005114*y)*y*y) + ((-2.10926-.189881*y+.879806*z1)*z1*z1))/(1.0+(Math.exp(195.9604-42.69391*y-173.4931*z1+37.62898*y*z1)));
                    t = Math.pow(10, tnon)*t0;}
                else {
                    if(z1>2.3) error=true;
                    tnon = (-1.66249-.0891113*y) + ((4.11648+.0878093*y)*z1) + ((-.00309742+.00199879*z1+.0000685472*y)*y*y) + ((-1.84445-.00750324*y+.305784*z1)*z1*z1) + ((11.1555+1.321*y) + ((-17.1236-1.2919*y)*z1) + ((.0628124-.0307949*z1+.00157743*y)*y*y) + ((8.63804+.307809*y-1.42634*z1)*z1*z1))/(1.0+(Math.exp(133.0611+8.979635*y-72.65298*z1-2.449009*y*z1)));
                    t = Math.pow(10, tnon)*t0;}}
            else if (y > -4.5) {  			// goto 140
                if (z1 <= 0.25) {
                    t = p / (rho*gascon);}
                else if( z1 <= 0.95) {
                    tnon = (.020391+.0076731*y) + ((.848581-.0293086*y)*z1) + ((.000840269-.00147701*z1+.0000313687*y)*y*y) + ((0.267251+.0237262*y-.141973*z1)*z1*z1);
                    t = (Math.pow(10.0,tnon)*t0);}
                else if (z1 <= 1.45) {
                    tnon = (-5.12404-.2847*y) + ((15.4532+.452475*y)*z1) + ((-.0122881+.00856845*z1-.000325256*y)*y*y) + ((-13.5181-.168725*y+4.18451*z1)*z1*z1) + ((7.52564+.835238*y) + ((-19.5558-1.23393*y)*z1) + ((.033451-.0234269*z1+.000481788*y)*y*y) + ((17.1779+.454628*y-5.09936*z1)*z1*z1))/(1.0+(Math.exp(61.48442-18.28123*y-54.68755*z1+15.625*y*z1)));
                    t = Math.pow(10, tnon)*t0;}
                else if (z1 <= 2.05) {
                    tnon = (-12.3779-1.14728*y) + ((24.1382+1.38957*y)*z1) + ((-.0363693+.0224265*z1-.000323888*y)*y*y) + ((-14.2844-.406553*y+2.8752*z1)*z1*z1) + ((4.40782+1.33046*y) + ((-11.545-1.59892*y)*z1) + ((.053058-.0310376*z1+.00047765*y)*y*y) + ((8.57309+.471274*y-1.96233*z1)*z1*z1))/(1.0+(Math.exp(140.75-6.499992*y-77.5*z1+5.0*y*z1)));
                    t = Math.pow(10, tnon)*t0;}
                else {
                    if (z1>2.5) error=true;
                    tnon = (-12.7244-1.66684*y) + ((17.2708+1.45307*y)*z1) + ((-.0364515+.0190463*z1+.000480787*y)*y*y) + ((-6.97208-.304323*y+.967524*z1)*z1*z1) + ((7.7133+.50834*y) + ((-9.8211-.449138*y)*z1) + ((-.000941787-.00240293*z1-.00082845*y)*y*y) + ((4.1653+.0963923*y-.588807*z1)*z1*z1))/(1.0+(Math.exp(-1092.654-305.312*y+465.6243*z1+131.2498*y*z1)));
                    t = Math.pow(10, tnon)*t0;}}
            else if (z1 > 0.25) { 			 // goto 100
                if (z1 <= 0.95) {
                    tnon = (.123718+.0108623*y) + ((.224239-.0824608*y)*z1) + ((-.00117615-.00187566*z1-.000119155*y)*y*y) +((1.18397+.0648520*y-.55263*z1)*z1*z1);
                    t = Math.pow(10.0,tnon) * t0;}
                else if (z1 <= 1.4) {
                    tnon = (-8.12952-.828637*y) + ((22.6904+1.41132*y)*z1) + ((-.0298633+.0270066*z1-.000228103*y)*y*y) + ((-19.1806-.578875*y+5.6258*z1)*z1*z1) + ((-3.99845+.226369*y) + ((2.52876-.728448*y)*z1) + ((.0109769-.0183819*z1-.00015138*y)*y*y) + ((2.99238+.39144*y-2.04463*z1)*z1*z1))/(1.0+(Math.exp(-38.87015-29.08228*y+40.70557*z1+26.82347*y*z1)));
                    t = Math.pow(10.0,tnon) * t0;}
                else if (z1 <= 1.95) {
                    tnon = (-19.8573-1.67225*y) + ((37.6159+2.10964*y)*z1) + ((-.0340174+.0231717*z1-.0000980257*y)*y*y) + ((-22.2215-.644596*y+4.40486*z1)*z1*z1) + ((-5.36809+.241201*y) + ((-1.25881-.862744*y)*z1) + ((-.00379774-.00781335*z1-.000380005*y)*y*y) + ((5.58609+.378963*y-1.81566*z1)*z1*z1))/(1.0+(Math.exp(20.8-25.6*y+1.0*z1+18.0*y*z1)));
                    t = Math.pow(10.0,tnon) * t0;}
                else {
                    if (z1>2.6) error=true;
                    tnon = (-23.3271-1.89958*y) + ((32.144+1.68622*y)*z1) + ((-.0442123+.0282629*z1+.000663272*y)*y*y) + ((-13.8645-.340976*y+2.04466*z1)*z1*z1) + ((8.35474+1.71347*y) + ((-16.0715-1.63139*y)*z1) + ((.0414641-.0230068*z1+.0000153246*y)*y*y) + ((8.70275+.360966*y-1.46166*z1)*z1*z1))/(1.0+(Math.exp(111.5884-6.452606*y-53.37863*z1+2.026986*y*z1)));
                    t = Math.pow(10.0,tnon) * t0;}}
            else t = p / (rho * gascon);

            if(iflag==-1 || jflag==1) return(0);
            if(jflag==0) {
                tlow = t;
                t = tlow + (thigh - tlow) / (yhigh - ylow) * (ym - ylow);
                rho = rsave;
                return(0);}
            thigh = t;y = -4.5 - .045;
            if(iflag==1) y = -0.5 - 0.005;
            ylow = y;
            rho = Math.pow(10,y) * rho;
            jflag = 0;}
    }

}




