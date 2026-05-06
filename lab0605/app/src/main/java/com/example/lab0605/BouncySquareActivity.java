package com.example.bouncysquare;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class BouncySquareActivity extends Activity
{
    private GLSurfaceView view;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        view = new GLSurfaceView(this);

        view.setRenderer(
                new SquareRenderer(this.getApplicationContext()));

        setContentView(view);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        view.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        view.onResume();
    }
}