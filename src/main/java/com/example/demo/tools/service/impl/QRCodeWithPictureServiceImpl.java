package com.example.demo.tools.service.impl;

import com.example.demo.tools.service.QRCodeWithPictureService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.util.Hashtable;

@Service
public class QRCodeWithPictureServiceImpl implements QRCodeWithPictureService {

	private final String CHARSET = "utf-8";
    private final String FORMAT_NAME = "JPG";
    // 二维码尺寸  
    private final int QRCODE_SIZE = 300;
    // LOGO宽度  
    private final int WIDTH = 60;
    // LOGO高度  
    private final int HEIGHT = 60;

    private BufferedImage createImage(String content, String imgPath, boolean needCompress) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000  : 0xFFFFFFFF);
            }
        }
        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }
        // 插入图片  
        this.insertImage(image, imgPath, needCompress);
        return image;
    }

    /**
     * 插入LOGO
     * @param source 维码图片
     * @param imgPath LOGO图片地址
     * @param needCompress 是否压缩
     */
    private void insertImage(BufferedImage source, String imgPath,
            boolean needCompress) throws Exception {
        File file = new File(imgPath);
        if (!file.exists()) {
            System.err.println(""+imgPath+"   该文件不存在！");
            return;
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO  
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图  
            g.dispose();
            src = image;
        }
        // 插入LOGO  
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 生成二维码(内嵌LOGO)
     * @param content 内容
     * @param imgPath LOGO地址
     * @param output 输出流
     * @param needCompress 是否压缩LOGO
     */
    @Override
    public void encode(String content, String imgPath, OutputStream output, boolean needCompress) {
        BufferedImage image = null;
        try {
            image = this.createImage(content, imgPath, needCompress);
            ImageIO.write(image, FORMAT_NAME, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//  public picture void main(String[] args) throws Exception {
//  String text = "aiyoumygod00000";  
//  encode(text, "c:/df.jpg", "c:/a/", true);  
//}
//
///**
//* 生成二维码(内嵌LOGO)
//* @param content 内容
//* @param imgPath LOGO地址
//* @param destPath 存放目录
//* @param needCompress 是否压缩LOGO
//* @throws Exception
//*/
//public picture void encode(String content, String imgPath, String destPath,
//		boolean needCompress) throws Exception {  
//	BufferedImage image = createImage(content, imgPath, needCompress);  
//	mkdirs(destPath);  
//	String file = new Random().nextInt(99999999)+".jpg";  
//	ImageIO.write(image, FORMAT_NAME, new File(destPath+"/"+file));  
//}
//    /**
//     * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
//     * @author lanyuan Email: mmm333zzz520@163.com
//     * @date 2013-12-11 上午10:16:36
//     * @param destPath 存放目录
//     */
//    private picture void mkdirs(String destPath) {
//        File file =new File(destPath);      
//        //当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)  
//        if (!file.exists() && !file.isDirectory()) {  
//            file.mkdirs();  
//        }  
//    } 
}  
