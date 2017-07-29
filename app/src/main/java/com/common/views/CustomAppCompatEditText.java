package com.common.views;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;


import com.common.utils.R;

import java.util.HashMap;
import java.util.Map;


public class CustomAppCompatEditText extends AppCompatEditText {

    /*
     * Caches typefaces based on their file path and name, so that they don't have to be created
     * every time when they are referenced.
     */
    private static Map<String, Typeface> mTypefaces;

    public CustomAppCompatEditText(Context context) {
        super(context);
    }

    public CustomAppCompatEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public CustomAppCompatEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomFont(context, attrs);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        InputConnection conn = super.onCreateInputConnection(outAttrs);
        outAttrs.imeOptions &= ~EditorInfo.IME_FLAG_NO_ENTER_ACTION;
        return conn;
    }

    /**
     * Apply Custom Fonts
     * @param context context
     * @param attrs attribute set
     */
    private void setCustomFont(Context context, AttributeSet attrs) {
        if (mTypefaces == null) {
            mTypefaces = new HashMap<String, Typeface>();
        }

        // prevent exception in Android Studio / ADT interface builder
        if (this.isInEditMode()) {
            return;
        }

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.montserrat);
        if (array != null) {
            int font = array.getInteger(R.styleable.montserrat_font, 1);
            final String typefaceAssetPath = "fonts/" + getResources().getStringArray(R.array.montserrat)[font];

            Typeface typeface = null;

            if (mTypefaces.containsKey(typefaceAssetPath)) {
                typeface = mTypefaces.get(typefaceAssetPath);
            } else {
                AssetManager assets = context.getAssets();
                typeface = Typeface.createFromAsset(assets, typefaceAssetPath);
                mTypefaces.put(typefaceAssetPath, typeface);
            }

            setTypeface(typeface);
            array.recycle();
        }
    }

}
