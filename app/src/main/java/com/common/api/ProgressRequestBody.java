package com.common.api;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class ProgressRequestBody extends RequestBody {
    private static final int DEFAULT_BUFFER_SIZE = 2048;
    private File mFile;
    private String mPath;
    private UploadCallbacks mListener;
    private String MEDIA_TYPE = "image/*";

    public ProgressRequestBody(final File file, final UploadCallbacks listener, String media_type) {
        mFile = file;
        mListener = listener;
        this.MEDIA_TYPE = media_type;
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse(MEDIA_TYPE);
    }

    @Override
    public long contentLength() throws IOException {
        return mFile.length();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        long fileLength = mFile.length();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        FileInputStream in = new FileInputStream(mFile);
        long uploaded = 0;

        try {
            int read;
            Handler handler = new Handler(Looper.getMainLooper());
            while ((read = in.read(buffer)) != -1) {

                // update progress on UI thread
                handler.post(new ProgressUpdater(uploaded, fileLength));

                uploaded += read;
                sink.write(buffer, 0, read);
            }
        } finally {
            in.close();
        }
    }

    public interface UploadCallbacks {
        void onProgressUpdate(int totalPer, int percentage);

        void onError();

        void onFinish();


    }

    private class ProgressUpdater implements Runnable {
        private long mUploaded;
        private long mTotal;

        public ProgressUpdater(long uploaded, long total) {
            mUploaded = uploaded;
            mTotal = total;
        }

        @Override
        public void run() {
            if ((int) (100 * mUploaded / mTotal) == mTotal-1) {
                mListener.onFinish();
            } else {
                mListener.onProgressUpdate((int) mTotal, (int) (100 * mUploaded / mTotal));
            }
        }
    }
}