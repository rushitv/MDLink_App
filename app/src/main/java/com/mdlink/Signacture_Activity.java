package com.mdlink;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Signacture_Activity extends AppCompatActivity {

    private CanvasView customCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signacture_);
        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);

    }
    public void clearCanvas(View v) {
        customCanvas.clearCanvas();
    }
}
