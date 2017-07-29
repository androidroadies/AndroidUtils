package com.common.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import com.common.utils.R;

import butterknife.ButterKnife;

public abstract class BaseFragmentChat extends Fragment {

    protected BaseAppCompatActivity mBaseAppCompatActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResource(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    protected abstract int getLayoutResource();

    public void setToolbar(String title) {
//        ToolbarUtils.setToolbar((MainActivity) getActivity()
//                , ((MainActivity) getActivity()).getActionBarToolbar(),
//                ((MainActivity) getActivity()).getDrawerLayout(),
//                title
//        );
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mBaseAppCompatActivity = (BaseAppCompatActivity) context;
    }

    protected void setTitle(String title) {
        mBaseAppCompatActivity.getSupportActionBar().setTitle(title);
    }

    public void setInnerToolbar(String title) {
//        ToolbarUtils.setInnerToolbar((MainActivity) getActivity()
//                , ((MainActivity) getActivity()).getActionBarToolbar(),
//                ((MainActivity) getActivity()).getDrawerLayout(),
//                title
//        );
    }

    public void launchActivity(Intent intent) {
        launchActivity(intent, mBaseAppCompatActivity.DEFAULT_REQUEST_CODE);
    }

    protected void launchActivity(Intent intent, int requestCode) {
//        mBaseAppCompatActivity.launchActivity(intent, requestCode, finishCurrent);
        startActivityForResult(intent, requestCode);
    }

    public FragmentManager getSupportFragmentManager() {
        return getActivity().getSupportFragmentManager();
    }

    public void showDialogFragment(AppCompatDialogFragment appCompatDialogFragment) {
        ((BaseAppCompatActivity) getActivity()).showDialogFragment(appCompatDialogFragment);
    }

    public void loadFragment(Class fragment) {
        loadFragment(fragment, false);
    }

    public void loadFragment(Class fragment, boolean addToBackStack) {
        loadFragment(fragment, addToBackStack, null);
    }

    public void loadFragment(Class fragment, boolean addToBackStack, Bundle bundle) {
        ((BaseAppCompatActivity) getActivity()).loadFragment(fragment, addToBackStack, bundle);
    }

    public void addFragment(Class fragment, boolean addToBackStack, Bundle bundle) {
        ((BaseAppCompatActivity) getActivity()).addFragment(fragment, addToBackStack, bundle);
    }

    public void addFragment(int layoutId, Class fragment, boolean addToBackStack, Bundle bundle) {
        ((BaseAppCompatActivity) getActivity()).addFragment(layoutId, fragment, addToBackStack, bundle);
    }

    public void showAlertDialog(String message) {
        ((BaseAppCompatActivity) getActivity()).showAlertDialog(message);
    }

    public void showToast(String message) {
        ((BaseAppCompatActivity) getActivity()).showToast(message);
    }

    public void showAToast(String msg) { //"Toast toast" is declared in the class
        ((BaseAppCompatActivity) getActivity()).showAToast(msg);
    }

    public void showProgressDialog() {
        ((BaseAppCompatActivity) getActivity()).showProgressDialog();
    }

    public void showProgressDialog(String title) {
        showProgressDialog(title, ((BaseAppCompatActivity) getActivity()).getString(R.string.pls_wait));
    }

    public void showProgressDialog(String title, String message) {
        ((BaseAppCompatActivity) getActivity()).showProgressDialog(title, message);
    }

    public void hideProgressDialog() {

        try {
            ((BaseAppCompatActivity) getActivity()).hideProgressDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getEdString(EditText editText) {
        return editText.getText().toString();
    }

    public void hideKeyBoard() {
        ((BaseAppCompatActivity) getActivity()).hideKeyboard();
    }

    /**
     * Pop/Remove fragment from backstack
     *
     * @param fragmentClass fragment to be removed
     */
    public void popBackStack(Class fragmentClass) {
        getSupportFragmentManager()
                .popBackStack(fragmentClass.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    /**
     * Finish OnScreen Fragment if Proper data is Not Available
     *
     * Mainly used when a fragment which is solely dependent on Data to set to its views
     * & if proper data is not available then Finish the fragment with an alert
     *
     * @param fragmentClass target fragment
     */
    public void showAlertFinishFragment(final Class fragmentClass){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getContext());
        }
        builder.setTitle("Failed to Load")
                .setMessage("Unable to load this page, please try again later!")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // pop this fragment
                        popBackStack(fragmentClass);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onPause() {
        super.onPause();
        hideKeyBoard();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
