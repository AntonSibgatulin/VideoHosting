package com.AntonSibgatulin.Register;

import java.util.Random;

import com.AntonSibgatulin.Crypt.captchacrypt;

public class Captcha {
    public int equally;
    public int term1,term2;
public captchacrypt captcha;
    public int getTerm1() {
        return term1;
    }
    public int getTerm2(){
        return term2;
    }

    public void generatorCaptcha(){
                    if(equally>21){
                        term1 = equally/(2+new Random().nextInt(8));
                        term2 = equally-term1;
                    }
                    captcha = new captchacrypt(term1,term2);
    }


    public Captcha(int equally){
        this.equally = equally;
        generatorCaptcha();
    }
}
