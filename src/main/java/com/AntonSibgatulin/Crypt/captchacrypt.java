package com.AntonSibgatulin.Crypt;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class captchacrypt  {
   public  BufferedImage buf = new BufferedImage(70,15,BufferedImage.TYPE_INT_RGB);

public captchacrypt(int i,int j){

    buf.getGraphics().drawLine(2,2,35,15);
    buf.getGraphics().drawLine(6,2,35,8);


    //buf.getGraphics().drawLine(2,15,35,8);
     buf.getGraphics().setColor(Color.WHITE);

    buf.getGraphics().drawString(i+"+"+j,2,12);
//System.out.println(encodeToString(buf,"png"));
}
public String getCaptchaString(){

    return encodeToString(buf,"png");
}

    public static String encodeToString(BufferedImage image, String type) {
        String base64String = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, type, bos);
           
           // BASE64Encoder encoder = new BASE64Encoder();
            base64String = Base64.getEncoder().encodeToString(bos.toByteArray());
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64String;
    }
}
