package com.ssafy.goumunity.common.util;

import static org.springframework.util.ObjectUtils.isEmpty;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageUtils {

    public ImageUtils() {
        throw new IllegalStateException("do not create this class");
    }

    public static BufferedImage resizeImage(File image, int width, int height)
            throws IOException, InterruptedException {
        if (isEmpty(image)) {
            return null;
        }

        BufferedImage inputImage = ImageIO.read(image);
        BufferedImage outputImage = new BufferedImage(width, height, inputImage.getType());

        Graphics2D graphics2D = outputImage.createGraphics();
        graphics2D.drawImage(inputImage, 0, 0, width, height, null);
        graphics2D.dispose();

        return outputImage;
    }
}
