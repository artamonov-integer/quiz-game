package com.integer.quiz.gui.utils;

import com.haulmont.cuba.core.app.FileStorageService;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.gui.AppConfig;
import com.haulmont.cuba.gui.ServiceLocator;
import com.haulmont.cuba.gui.components.BoxLayout;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.Embedded;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PictureHelper {

    private Log logger = LogFactory.getLog(getClass());

    private Embedded pictureComponent;
    private BoxLayout pictureContainer;
    private int maxHeight;
    private int maxWidth;

    public PictureHelper(BoxLayout layout) {
        pictureContainer = layout;
    }

    public PictureHelper(BoxLayout layout, int maxHeigth, int maxWidth) {
        this(layout);
        this.maxHeight = maxHeigth;
        this.maxWidth = maxWidth;
    }

    public void initPictureControls() {
        pictureComponent = AppConfig.getFactory().createComponent(Embedded.NAME);
        pictureComponent.setType(Embedded.Type.IMAGE);
        pictureComponent.setVisible(false);
        pictureComponent.setAlignment(Component.Alignment.MIDDLE_CENTER);
        for (Component component : pictureContainer.getComponents()) {
            pictureContainer.remove(component);
        }
        pictureContainer.add(pictureComponent);
    }

    private void resizePictureNew(InputStream inputStream) {
        BufferedImage imageIO;
        try {
            imageIO = ImageIO.read(inputStream);
            int width = imageIO.getWidth();
            int height = imageIO.getHeight();

            if (((double) height / (double) width) > ((double) maxHeight / (double) maxWidth)) {
                pictureComponent.setHeight(maxHeight + "");
                pictureComponent.setWidth((width * maxHeight / height) + "");
            } else {
                pictureComponent.setWidth(maxWidth + "");
                pictureComponent.setHeight((height * maxWidth / width) + "");
            }
        } catch (IOException e) {
            logger.error(e.getStackTrace());
        }
    }

    public void refreshPicture(FileDescriptor pictureDescriptor) {
        if (pictureDescriptor != null) {
            pictureComponent.setVisible(true);
            FileStorageService fss = ServiceLocator.lookup(FileStorageService.NAME);
            byte[] buf;
            try {
                buf = fss.loadFile(pictureDescriptor);
            } catch (FileStorageException e) {
                logger.error("Error", e);
                return;
            }

            resizePictureNew(getStream(buf));
            pictureComponent.setSource(pictureDescriptor.getName(), getStream(buf));
            pictureComponent.setType(Embedded.Type.IMAGE);
            pictureComponent.setVisible(false);
            pictureComponent.setVisible(true);
        } else {
            pictureComponent.setVisible(false);
        }
    }

    /* No needs to be closed */
    private InputStream getStream(byte[] buf) {
        return new ByteArrayInputStream(buf);
    }
}