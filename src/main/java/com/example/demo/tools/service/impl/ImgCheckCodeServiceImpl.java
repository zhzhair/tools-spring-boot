package com.example.demo.tools.service.impl;

import com.example.demo.tools.service.ImgCheckCodeService;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * @author Created by zhaozh01 on 2017/10/20.
 */
@Service
public class ImgCheckCodeServiceImpl extends ImgCheckCodeParams implements ImgCheckCodeService {

	@Override
    public String codeCreate(OutputStream outputStream){
        /*获取画笔*/
        BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
        /*填充图片区域和背景图片*/
        int grayIndex = GRAY;
        graphics2D.setColor(new Color(grayIndex, grayIndex, grayIndex));
        graphics2D.fillRect(0, 0, WIDTH, HEIGHT);

        /*画干扰点*/
        Color colorDots = new Color(new Random().nextInt(256),new Random().nextInt(256),new Random().nextInt(256));
        for (int i = 0; i < DOTS_SUM; i++) {
            int widthPoint = new Random().nextInt(WIDTH);
            int heightPoint = new Random().nextInt(HEIGHT);
            graphics2D.fillRect(widthPoint,heightPoint,DOTS_LEN,DOTS_LEN);
            graphics2D.setColor(colorDots);
        }
        /*填充字符串*/
        StringBuilder stringBuilder = new StringBuilder();
        Color colorCode = this.getColor();
        colorCode.brighter();
        for (int i = 0; i < CODE_COUNT; i++) {
            graphics2D.setFont(new Font(CHECK_CODE_FACE, Font.BOLD, FONT_SIZE));
            graphics2D.setColor(colorCode);
            String str = CHECK_CODE_STR.charAt(new Random().nextInt(CHECK_CODE_STR.length()))+"";
            int lr = MARGIN_LEFT+PADDING_RIGHT*i + this.getRandomNum(WAVE_LEFT_RIGHT);
            int tb = BOTTOM_TO_TOP + WAVE_BOTTOM_TOP;
            graphics2D.drawString(str, lr, tb);
            stringBuilder.append(str);
        }

        graphics2D.dispose();
        try {//将验证码图片作为输出流输出到html页面
            ImageIO.write(bufferedImage, "JPEG", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回较大字体的字符拼接的字符串
        return stringBuilder.toString();
    }

}
