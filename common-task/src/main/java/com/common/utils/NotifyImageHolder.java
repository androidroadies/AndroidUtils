/*
 * NotifyImageHolder.java
 * By: Lee Hounshell
 *
 * This Interface allows notify of an Activity via callback after the image is fully loaded.
 * Activities that need to be informed when a 'NotifyImageView' changes should implement this.
 */

package com.common.utils;

public interface NotifyImageHolder {
    public void notifyImageChanged(final NotifyImageView thePosterImage, final int width, final int height);
}
