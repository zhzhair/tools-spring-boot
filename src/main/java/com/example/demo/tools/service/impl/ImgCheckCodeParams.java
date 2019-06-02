package com.example.demo.tools.service.impl;

import java.awt.*;
import java.util.Random;

public class ImgCheckCodeParams {
    Integer GRAY = 30;
    Integer WIDTH = 80;
    Integer HEIGHT = 45;
    Integer DOTS_SUM = 50;
    Integer DOTS_LEN = 2;
    Integer CODE_COUNT = 4;
    Integer FONT_SIZE = 24;
    String CHECK_CODE_STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    String CHECK_CODE_FACE = "Times New Roman";
    Integer WAVE_LEFT_RIGHT = 6;
    Integer WAVE_BOTTOM_TOP = 5;
    Integer MARGIN_LEFT = 2;
    Integer PADDING_RIGHT = 20;
    Integer BOTTOM_TO_TOP = 25;

    int getRandomNum(int n){
        float rand = new Random().nextInt(n);
        float fnum = -0.5f + rand/n;
        return Math.round(fnum * n);
    }

    Color getColor(){
        int n = new Random().nextInt(10);
        switch (n){
            case 0 : return new Color(255, 246, 4);
            case 1 : return new Color(255, 201, 5);
            case 2 : return new Color(255, 121, 10);
            case 3 : return new Color(208, 255, 147);
            case 4 : return new Color(1, 255, 204);
            case 5 : return new Color(19, 255, 60);
            case 6 : return new Color(179, 255, 11);
            case 7 : return new Color(255, 149, 232);
            case 8 : return new Color(181, 173, 254);
            default : return new Color(255, 249, 206);
        }
    }
}
