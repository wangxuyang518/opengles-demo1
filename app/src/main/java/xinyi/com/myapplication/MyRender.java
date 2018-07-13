package xinyi.com.myapplication;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyRender implements GLSurfaceView.Renderer {
    private Triangle mTriangle;
    private Context context;

    private float[]vs=new float[16];//4 * 4 矩阵

    public MyRender(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(1f, 1f, 1f, 1.0f);//设置背景颜色
        mTriangle = new Triangle(context);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);//视图显示区域
        int screenNum = context.getResources().getConfiguration().orientation;
        if (screenNum==1) {
            float w=(float)width/(float) (height+72);
            Matrix.orthoM(vs, 0, -w, w, -1f, 1f, -1f, 1f);
        }else {
            float w=(float)(height+72)/(float) width;
            Matrix.orthoM(vs, 0, -1f, 1f, -w, w, -1f, 1f);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //清除深度缓冲与颜色缓冲
        GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        mTriangle.draw(vs);
    }
}
