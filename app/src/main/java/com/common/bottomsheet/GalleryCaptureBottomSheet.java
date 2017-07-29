package com.common.bottomsheet;

import android.content.Context;

import com.common.base.BaseBottomSheetDialogFragment;
import com.common.utils.R;

import butterknife.OnClick;


public class GalleryCaptureBottomSheet extends BaseBottomSheetDialogFragment {

    private onGalleryCaptureListener mOnGalleryCaptureListener;

    public void setmOnGalleryCaptureListener(onGalleryCaptureListener mOnGalleryCaptureListener) {
        this.mOnGalleryCaptureListener = mOnGalleryCaptureListener;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.bottom_sheet_gallery_capture;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onGalleryCaptureListener)
            mOnGalleryCaptureListener = (onGalleryCaptureListener) context;
    }

    @OnClick(R.id.btn_bottom_sheet_select_image_using_camera)
    void onCameraClick() {
        if (mOnGalleryCaptureListener != null)
            mOnGalleryCaptureListener.onCameraClick();
        dismiss();
    }

    @OnClick(R.id.btn_bottom_sheet_select_image_using_gallery)
    void onGalleryClick() {
        if (mOnGalleryCaptureListener != null)
            mOnGalleryCaptureListener.onGalleryClick();
        dismiss();
    }

    public interface onGalleryCaptureListener {
        void onCameraClick();

        void onGalleryClick();
    }
}
