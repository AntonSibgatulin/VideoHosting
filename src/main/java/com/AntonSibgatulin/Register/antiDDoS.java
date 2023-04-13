package com.AntonSibgatulin.Register;

public class antiDDoS {
    public boolean getantiDoss(long lastreg,long newreg){
        if(newreg-lastreg<4000L){
            return true;
        }else return false;
    }
}
