package com.mdlinkhealth.chat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mdlinkhealth.BaseActivity;
import com.mdlinkhealth.R;
import com.mdlinkhealth.helper.StringHelper;
import com.mdlinkhealth.util.Constants;
import com.squareup.picasso.Picasso;

public class ImagePreviewActivity extends BaseActivity implements View.OnClickListener {
    Toolbar toolbar;
    ImageView imgPreview;
    Button btnCancel, btnSend;
    LinearLayout bottomButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        initToolbar();
        initView();
    }

    private void initView() {
        bottomButtons = findViewById(R.id.bottomButtons);
        imgPreview = findViewById(R.id.imagePreview);

        bottomButtons.setVisibility(View.GONE);
        if (!StringHelper.isEmptyOrNull(getIntent().getStringExtra(Constants.IMAGE_URI))) {
            bottomButtons.setVisibility(View.VISIBLE);
            String imagePath = getIntent().getStringExtra(Constants.IMAGE_URI);
            imgPreview.setImageBitmap(BitmapFactory.decodeFile(imagePath));
        } else if (!StringHelper.isEmptyOrNull(getIntent().getStringExtra(Constants.IMAGE_URL))) {
            Picasso.get().load(getIntent().getStringExtra(Constants.IMAGE_URL)).into(imgPreview);
        }

        btnCancel = findViewById(R.id.btnCancel);
        btnSend = findViewById(R.id.btnSend);
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent_white_180));
        setUpToolbar(toolbar, R.color.colorAccent);
        setToolbarTitle(getString(R.string.label_preview), R.color.colorAccent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                finish();
                break;
            case R.id.btnSend:
                Intent returnIntent = new Intent();
                returnIntent.putExtra(Constants.IMAGE_URI, getIntent().getStringExtra(Constants.IMAGE_URI));
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                break;
        }
    }
}
