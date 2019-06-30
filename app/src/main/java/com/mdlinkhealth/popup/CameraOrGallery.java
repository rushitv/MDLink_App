package com.mdlinkhealth.popup;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;

import com.mdlinkhealth.R;

public class CameraOrGallery extends AlertDialog {

    public enum IMAGESOURCE {
        CAMERA,
        GALLERY
    }

    private LinearLayout llChooseCamera;
    private LinearLayout llChooseGallery;

    protected CameraOrGallery(@NonNull Context context) {
        super(context);
    }

    protected CameraOrGallery(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CameraOrGallery(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        setContentView(R.layout.camera_or_gallery_popup);
        llChooseCamera = findViewById(R.id.ll_choose_camera);
        llChooseGallery = findViewById(R.id.ll_choose_gallery);
        llChooseCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionChoose(IMAGESOURCE.CAMERA);
                dismiss();
            }
        });
        llChooseGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionChoose(IMAGESOURCE.GALLERY);
                dismiss();
            }
        });
    }

    protected void onActionChoose(IMAGESOURCE imagesource) {
    }
}
