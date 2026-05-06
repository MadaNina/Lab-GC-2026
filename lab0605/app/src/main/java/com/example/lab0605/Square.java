package com.example.bouncysquare;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Square
{
    private FloatBuffer mVertexBuffer;
    public FloatBuffer mTextureBuffer;

    private int[] textures = new int[1];

    private byte indices[] =
            {
                    0, 1, 2,
                    1, 2, 3
            };

    // Distorted square
    float vertices[] =
            {
                    -1.0f, -0.7f,
                    1.0f, -0.30f,
                    -1.0f,  0.70f,
                    1.0f,  0.30f,
            };

    // Repeating texture
    float[] textureCoords =
            {
                    0.0f , 2.0f,
                    2.0f , 2.0f,
                    0.0f , 0.0f,
                    2.0f , 0.0f
            };

    float texIncrease = 0.01f;

    private ByteBuffer mIndexBuffer;

    public Square()
    {
        ByteBuffer vbb =
                ByteBuffer.allocateDirect(vertices.length * 4);

        vbb.order(ByteOrder.nativeOrder());

        mVertexBuffer = vbb.asFloatBuffer();

        mVertexBuffer.put(vertices);

        mVertexBuffer.position(0);

        ByteBuffer tbb =
                ByteBuffer.allocateDirect(textureCoords.length * 4);

        tbb.order(ByteOrder.nativeOrder());

        mTextureBuffer = tbb.asFloatBuffer();

        mTextureBuffer.put(textureCoords);

        mTextureBuffer.position(0);

        mIndexBuffer =
                ByteBuffer.allocateDirect(indices.length);

        mIndexBuffer.put(indices);

        mIndexBuffer.position(0);
    }

    public void createTexture(GL10 gl,
                              Context contextRegf,
                              int resource)
    {
        Bitmap image =
                BitmapFactory.decodeResource(
                        contextRegf.getResources(),
                        resource);

        gl.glGenTextures(1, textures, 0);

        gl.glBindTexture(
                GL10.GL_TEXTURE_2D,
                textures[0]);

        GLUtils.texImage2D(
                GL10.GL_TEXTURE_2D,
                0,
                image,
                0);

        gl.glTexParameterf(
                GL10.GL_TEXTURE_2D,
                GL10.GL_TEXTURE_MIN_FILTER,
                GL10.GL_LINEAR);

        gl.glTexParameterf(
                GL10.GL_TEXTURE_2D,
                GL10.GL_TEXTURE_MAG_FILTER,
                GL10.GL_LINEAR);

        image.recycle();
    }

    public void draw(GL10 gl)
    {
        gl.glFrontFace(GL10.GL_CCW);

        gl.glVertexPointer(
                2,
                GL10.GL_FLOAT,
                0,
                mVertexBuffer);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glEnable(GL10.GL_TEXTURE_2D);

        gl.glEnable(GL10.GL_BLEND);

        gl.glBlendFunc(
                GL10.GL_ONE,
                GL10.GL_SRC_COLOR);

        gl.glBindTexture(
                GL10.GL_TEXTURE_2D,
                textures[0]);

        // Animated texture
        for(int i = 0; i < textureCoords.length; i++)
        {
            textureCoords[i] += texIncrease;
        }

        mTextureBuffer.clear();
        mTextureBuffer.put(textureCoords);
        mTextureBuffer.position(0);

        gl.glTexCoordPointer(
                2,
                GL10.GL_FLOAT,
                0,
                mTextureBuffer);

        gl.glEnableClientState(
                GL10.GL_TEXTURE_COORD_ARRAY);

        gl.glDrawElements(
                GL10.GL_TRIANGLES,
                indices.length,
                GL10.GL_UNSIGNED_BYTE,
                mIndexBuffer);

        gl.glDisableClientState(
                GL10.GL_VERTEX_ARRAY);

        gl.glDisableClientState(
                GL10.GL_TEXTURE_COORD_ARRAY);
    }
}
