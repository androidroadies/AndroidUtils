package com.common.bottomsheet;

import android.content.Context;


import com.common.base.BaseBottomSheetDialogFragment;
import com.common.utils.R;

import butterknife.OnClick;

public class SelectImageUsingBottomSheet extends BaseBottomSheetDialogFragment {

    /*@BindView(R.id.btn_record_video)
    CustomAppCompatButton btnRecordVideo;
    @BindView(R.id.btn_bottom_sheet_select_image_using_camera)
    CustomAppCompatButton btnBottomSheetSelectImageUsingCamera;*/

    private OnSelectUsingListener mOnSelectUsingListener;

    public void setmOnSelectUsingListener(OnSelectUsingListener mOnSelectUsingListener) {
        this.mOnSelectUsingListener = mOnSelectUsingListener;
    }

    /*@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResource(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }*/

    @Override
    public int getLayoutResource() {
        return R.layout.bottom_sheet_select_image_using;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSelectUsingListener)
            mOnSelectUsingListener = (OnSelectUsingListener) context;
    }

    @OnClick(R.id.btn_bottom_sheet_select_image_using_camera)
    void onCameraClick() {
        if (mOnSelectUsingListener != null)
            mOnSelectUsingListener.onCameraClick();
        dismiss();
    }

    @OnClick(R.id.btn_bottom_sheet_select_image_using_gallery)
    void onGalleryClick() {
        if (mOnSelectUsingListener != null)
            mOnSelectUsingListener.onGalleryClick();
        dismiss();
    }

    @OnClick(R.id.btn_record_video)
    void onRecordVideoClick() {
        if (mOnSelectUsingListener != null)
            mOnSelectUsingListener.onRecordVideoClick();
        dismiss();
    }

    /*@OnClick(R.id.ll_add_location)
    void onLocationClick() {
        if (mOnSelectUsingListener != null)
            mOnSelectUsingListener.onLocationClick();
        dismiss();
    }*/

    /*public void hideVideoLayout() {
        btnRecordVideo.setVisibility(View.GONE);
    }*/

    public interface OnSelectUsingListener {
        void onCameraClick();

        void onGalleryClick();

        void onRecordVideoClick();

        void onLocationClick();
    }
}
