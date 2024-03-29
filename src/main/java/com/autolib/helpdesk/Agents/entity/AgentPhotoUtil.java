package com.autolib.helpdesk.Agents.entity;

import com.autolib.helpdesk.common.DirectoryUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

@Component
public class AgentPhotoUtil {

    @Value("${al.ticket.content-path}")
    private String contentPath;

    public String createDefaultProfilePicture(String firstName, String lastName, String employeeId) {
        String profilePath = contentPath + "/" + DirectoryUtil.profilePhotosDirectory;
        String filename = employeeId + ".png";
        try {
            String text = " ";
            if (firstName != null && firstName.length() > 0)
                text = text + firstName.toUpperCase().charAt(0);
            if (lastName != null && lastName.length() > 0)
                text = text + lastName.toUpperCase().charAt(0);

            text = text + " ";

            BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = img.createGraphics();
            Font font = new Font("Arial", Font.PLAIN, 48);
            g2d.setFont(font);
            FontMetrics fm = g2d.getFontMetrics();
            int width = fm.stringWidth(text);
            int height = fm.getHeight();
            g2d.dispose();

            img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            g2d = img.createGraphics();
            g2d.setColor(new Color(33, 37, 41));
//            g2d.setColor(Color.WHITE);
            g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
            g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            g2d.setFont(font);
            fm = g2d.getFontMetrics();
            g2d.drawString(text, 0, fm.getAscent());
            g2d.dispose();
            File directory = new File(profilePath);
            System.out.println(directory.getAbsolutePath());
            if (!directory.exists()) {
                directory.mkdirs();
            }

            ImageIO.write(img, "png", new File(profilePath + filename));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return filename;
    }

    public byte[] getProfilePhoto(String fileName) {
        byte[] photo = null;

        try {
            String path = contentPath + "/_profile_photos/" + fileName + "";
            File file = null;
            try {
                file = new File(path);
                if (file.exists()) {
                    photo = Files.readAllBytes(file.toPath());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return photo;
    }
}