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

    private float[]models=new float[16];//4 * 4 矩阵

    private float[]mvp=new float[16];//4 * 4 矩阵
    public MyRender(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES20.glClearColor(1f, 1f, 1, 1.0f);//设置背景颜色
        mTriangle = new Triangle(context);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);//视图显示区域
        int screenNum = context.getResources().getConfiguration().orientation;

        Matrix.setIdentityM(models,0);//models 产生一个单位矩阵
        Matrix.rotateM(models,0,30f,1f,0f,0f);//旋转
        Matrix.translateM(models,0,0,0,-5);//移动

        float w=(float)width/(float) (height+72);
        Matrix.perspectiveM(vs,0,60,w,2f, 10f);//得到透视矩阵
        Matrix.multiplyMM(mvp, 0, vs, 0, models, 0);//俩个矩阵相乘

      //  Matrix.rotateM(mvp,0,-60f,1f,0f,0f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //清除深度缓冲与颜色缓冲
        GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        mTriangle.draw(mvp);
    }
}
