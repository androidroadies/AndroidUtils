package com.common.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;


import com.common.utils.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseDialogFragment extends AppCompatDialogFragment {

    @Nullable
    @BindView(R.id.linearLayout)
    LinearLayout llProgressBar;

    private BaseAppCompatActivity mBaseAppCompatActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mBaseAppCompatActivity = (BaseAppCompatActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResource(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public abstract int getLayoutResource();

    public void showToast(String message) {
        ((BaseAppCompatActivity) getActivity()).showToast(message);
    }

    public void showAlertDialog(String message) {
        ((BaseAppCompatActivity) getActivity()).showAlertDialog(message);
    }

    public void showProgressDialog() {
        llProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressDialog() {
        llProgressBar.setVisibility(View.GONE);
    }

    public void launchActivity(Intent intent) {
        launchActivity(intent, mBaseAppCompatActivity.DEFAULT_REQUEST_CODE);
    }

    protected void launchActivity(Intent intent, int requestCode) {
//        mBaseAppCompatActivity.launchActivity(intent, requestCode, finishCurrent);
        startActivityForResult(intent, requestCode);
    }

    public void hideKeyBoard() {
        ((BaseAppCompatActivity) getActivity()).hideKeyboard();
    }

    public void setRoundedWindowToView() {
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.rounded_corner_dialog);
        }
    }
}
