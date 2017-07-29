package com.common.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.common.utils.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseAppCompatActivity extends AppCompatActivity {

    /**
     * To pass from activity to getMenuResource() when no menu is to be shown
     */
    public static final int NO_MENU = 0;
    private String TAG = getClass().getSimpleName();
    private ProgressDialog mProgressDialog;
    private Toast toast;

    public int DEFAULT_REQUEST_CODE = 100;

    @Nullable
    @BindView(R.id.toolbar_common)
    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "@BaseActivity onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
    }

    protected abstract int getLayoutResource();

    protected abstract int getMenuResource();

    public Toolbar getActionBarToolbar() {
        setSupportActionBar(mToolbar);
        if (mToolbar != null) {
                mToolbar.setNavigationIcon(R.drawable.ic_attach);
            mToolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
        }
        return mToolbar;
    }

    public void showProgressDialog() {
        showProgressDialog("Loading");
    }

    public void showProgressDialog(String title) {
        showProgressDialog(title, "Please wait...");
    }

    public void showProgressDialog(String title, String message) {

        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this, R.style.ProgressDialogTheme);
        }
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.layout_progress_dialog);

        /*if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.setMessage(message);
        } else {
            mProgressDialog = ProgressDialog.show(this, title, message, true, false);
        }*/
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void showAlertDialog(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                })
                .create();
        alertDialog.setMessage(message);
        alertDialog.show();
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showAToast(String msg) { //"Toast toast" is declared in the class
        try {
            toast.getView().isShown();     // true if visible
            toast.setText(msg);
        } catch (Exception e) {         // invisible if exception
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        }
        toast.show();  //finally display it
    }

    public void showDialogFragment(AppCompatDialogFragment appCompatDialogFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(appCompatDialogFragment, appCompatDialogFragment.getClass().getSimpleName());
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void launchActivity(Intent intent) {
        launchActivity(intent, false);
    }

    public void launchActivity(Intent intent, boolean finishCurrent) {
        launchActivity(intent, DEFAULT_REQUEST_CODE, finishCurrent);
    }

    public void launchActivity(Intent intent, int requestCode, boolean finishCurrent) {
        if (requestCode != DEFAULT_REQUEST_CODE) {
            startActivityForResult(intent, requestCode);
        } else {
            if (finishCurrent) {
                finish();
            }
            startActivity(intent);
        }
    }

    public void loadFragment(Class fragmentClass) {
        loadFragment(fragmentClass, false);
    }

    public void loadFragment(Class fragmentClass, boolean addToBackStack) {
        loadFragment(fragmentClass, addToBackStack, null);
    }

    public void addFragment(Class fragmentClass, boolean addToBackStack) {
        addFragment(0, fragmentClass, addToBackStack, null);
    }

    public void addFragment(Class fragmentClass, boolean addToBackStack, Bundle bundle) {
        addFragment(0, fragmentClass, addToBackStack, bundle);
    }

    public void addFragment(int layoutId, Class fragmentClass, boolean addToBackStack) {
        addFragment(layoutId, fragmentClass, addToBackStack, null);
    }

    public void loadFragment(Class fragmentClass, boolean addToBackStack, Bundle bundle) {
        Fragment fragment = null;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
            if (bundle != null) {
                fragment.setArguments(bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (fragment != null) {
            if (addToBackStack) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(fragmentClass.getSimpleName())
                        .replace(R.id.frame, fragment, fragmentClass.getSimpleName())
                        .commit();
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame, fragment, fragmentClass.getSimpleName())
                        .commit();
            }
        }
    }

    public void addFragment(int layoutId, Class fragmentClass, boolean addToBackStack, Bundle bundle) {
        Fragment fragment = null;
        int resLayoutId;

        if (layoutId != 0) {
            resLayoutId = layoutId;
        } else {
            resLayoutId = R.id.frame;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
            if (bundle != null) {
                fragment.setArguments(bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (fragment != null) {
            if (addToBackStack) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(fragmentClass.getSimpleName())
                        .add(resLayoutId, fragment, fragmentClass.getSimpleName())
                        .commit();
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(resLayoutId, fragment, fragmentClass.getSimpleName())
                        .commit();
            }
        }
    }

    public void loadDialogFragment(Class dialogFragmentClass) {
        loadDialogFragment(dialogFragmentClass, null);
    }

    public void loadDialogFragment(Class dialogFragmentClass, Bundle bundle) {
        DialogFragment dialogFragment = null;

        try {
            dialogFragment = (DialogFragment) dialogFragmentClass.newInstance();
            if (bundle != null)
                dialogFragment.setArguments(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (dialogFragment != null) {
            dialogFragment.show(getSupportFragmentManager(), dialogFragmentClass.getSimpleName());
        }
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void hideKeyboard(Context context, IBinder binder) {
        InputMethodManager inputManager =
                (InputMethodManager) context.
                        getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(binder,
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideKeyboard();
    }

    @Override
    protected void onDestroy() {
        hideProgressDialog();
        super.onDestroy();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}
