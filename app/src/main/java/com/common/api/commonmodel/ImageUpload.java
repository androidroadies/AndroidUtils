package com.common.api.commonmodel;

import java.io.File;

/**
 * Created by ln-404 on 20/6/17.
 */

public class ImageUpload {
    private File imageFile;
    private boolean isFromCamera;

    public ImageUpload(File imageFile, boolean isFromCamera) {
        this.imageFile = imageFile;
        this.isFromCamera = isFromCamera;
    }

    public File getImageFile() {
        return imageFile;
    }

    public boolean isFromCamera() {
        return isFromCamera;
    }
}
