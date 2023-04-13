package com.AntonSibgatulin.Main;

import java.util.Scanner;

public class Test {
    public static void main(String[]args) {

        System.out.println("Hello,world");

    }

    public static double round(double value){
        return Double.valueOf(String.format("%.3f",value).replaceAll(",","."));
    }


}
