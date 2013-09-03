package com.integer.quiz.service;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

@Service(QuizImageService.NAME)
public class QuizImageServiceBean implements QuizImageService{

    private static final int IMG_WIDTH_L = 640;
    private static final int IMG_HEIGHT_L = 360;

    private static final int IMG_WIDTH_M = 854;
    private static final int IMG_HEIGHT_M = 480;

    private static final int IMG_WIDTH_H = 1280;
    private static final int IMG_HEIGHT_H = 720;

    @Override
    public Boolean saveImage(File file,File newFile,int quality,String ext){
        BufferedImage resizeImage = null;
        try {
            BufferedImage originalImage = ImageIO.read(file);
            int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
            resizeImage = resizeImage(originalImage, type, quality);
            ImageIO.write(resizeImage, ext, newFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    private static BufferedImage resizeImage(BufferedImage originalImage, int type, int quality){
        int widht;
        int height;
        if(quality==10){
            widht = IMG_WIDTH_L;
            height = IMG_HEIGHT_L;
        }else if(quality==20){
            widht = IMG_WIDTH_M;
            height = IMG_HEIGHT_M;
        }
        else{
            widht = IMG_WIDTH_H;
            height = IMG_HEIGHT_H;
        }
        BufferedImage resizedImage = new BufferedImage(widht, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, widht, height, null);
        g.dispose();

        return resizedImage;
    }

    /*private static BufferedImage resizeImageWithHint(BufferedImage originalImage, int type){

        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        return resizedImage;
    }*/
}
