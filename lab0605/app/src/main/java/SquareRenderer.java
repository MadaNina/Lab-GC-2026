package com.example.bouncysquare;

import android.content.Context;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

public class SquareRenderer implements Renderer
{
    private Square mSquare;
    private Context context;

    public SquareRenderer(Context context)
    {
        this.context = context;
        this.mSquare = new Square();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
        gl.glClearColor(0, 0, 0, 1);

        gl.glEnable(GL10.GL_DEPTH_TEST);

        int resid = R.drawable.hedly;

        mSquare.createTexture(gl, context, resid);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();

        float ratio = (float) width / height;

        gl.glOrthof(-ratio, ratio, -1, 1, 1, -1);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void onDrawFrame(GL10 gl)
    {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT |
                GL10.GL_DEPTH_BUFFER_BIT);

        gl.glLoadIdentity();

        mSquare.draw(gl);
    }
}
