package xinyi.com.myapplication;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Triangle {
    private Context context;
    private static final String TAG = "Triangle";

    private static final float[] TRIANGLE_VERTEX_COORD = {
             /*-0.5f, -0.5f, 0.25f, 0.75f,  // top
               0.5f, -0.5f, 0.75f, 0.75f,
              -0.5f, 0.5f, 0.25f, 0.25f,
              0.5f,  0.5f, 0.75f, 0.25f,*/
         /*   -0.5f, 0.5f,
            -0.5f, -0.5f,
            0.5f, 0.5f,
            0.5f, -0.5f,*/
            /*-0.5f, -0.5f,0.25f, 0.75f,
            0.5f, -0.5f,0.75f, 0.75f,
            0.5f,  0.5f,0.75f, 0.25f,
            -0.5f, 0.5f,0.25f, 0.25f,*/
            //X  Y  S  T
            -0.5f, -0.5f,0.2f, 0.2f,
            0.5f, -0.5f,0.8f, 0.2f,
            0.5f,  0.5f,0.8f, 0.8f,
            -0.5f, 0.5f,0.2f, 0.8f,
    };

    private FloatBuffer mTriangleCoordBuffer;

    private int mVertexShader, mFragmentShader;
    private static final String POSITION_NAME = "vPosition";
    private static final String COLOR_NAME = "vColor";
    private static final String COLOR_NAME1 = "a_Color";
    private static final String POINT_SIZE = "point_size";
    private int mPositionLocation, mFragColorLocation, mPOINT_SIZE, mP, vMCoordinate, vTexture;

    public Triangle(Context context) {
        this.context = context;

        // 数组转化为buffer，提高opengles性能
        mTriangleCoordBuffer = ByteBuffer.allocateDirect(TRIANGLE_VERTEX_COORD.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mTriangleCoordBuffer.put(TRIANGLE_VERTEX_COORD);
        // 设置从第一位开始读
        // 创建一个为空的顶点shader对象，与String绑定，然后编译返回Shader的引用
        mVertexShader = getShader(GLES20.GL_VERTEX_SHADER, TextResourceReader.readTextFileFromResource(context, R.raw.sample));
        //创建一个为空的片段shader对象，与String绑定，然后编译返回Shader的引用
        mFragmentShader = getShader(GLES20.GL_FRAGMENT_SHADER, TextResourceReader.readTextFileFromResource(context, R.raw.sample1));

        // 创建并连接program
        int program = ShaderHelper.linkProgram(mVertexShader, mFragmentShader);
        // 找到对应变量的index
        mPositionLocation = GLES20.glGetAttribLocation(program, POSITION_NAME);
        mFragColorLocation = GLES20.glGetAttribLocation(program, COLOR_NAME1);
        vMCoordinate = GLES20.glGetAttribLocation(program, "vCoordinate");
        vTexture = GLES20.glGetUniformLocation(program, "vTexture");

    }

    private int getShader(int type, String source) {
        int shader = GLES20.glCreateShader(type);//返回着色器内存的id
        GLES20.glShaderSource(shader, source);// 通过id 指定着色器的内容
        GLES20.glCompileShader(shader);//编译着色器

        // 获取编译的状态
        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS,
                compileStatus, 0);

        Log.v(TAG, "代码编译结果:" + "\n" + source
                + "\n:" + GLES20.glGetShaderInfoLog(shader));

        if (compileStatus[0] == 0) {
            Log.w(TAG, "编译失败!.");
            return 0;
        }

        return shader;
    }

    public void draw(float[] fl) {

        // GLES20.glUniformMatrix4fv(mP,1,false,fl,0)
        GLES20.glEnableVertexAttribArray(mPositionLocation);//顶点属性位置值作为参数，启用顶点属性；顶点属性默认是禁==
        mTriangleCoordBuffer.position(0);
        GLES20.glVertexAttribPointer(
                mPositionLocation, //属性的位置
                2,//每个顶点的分量
                GLES20.GL_FLOAT,//顶点的数据类型float 或者int
                false,
                16,
                mTriangleCoordBuffer);
        mTriangleCoordBuffer.position(2);

        GLES20.glEnableVertexAttribArray(vMCoordinate);//顶点属性位置值作为参数，启用顶点属性；顶点属性默认是禁用的
        GLES20.glUniform1f(vTexture, TextureHelper.loadTexture(context, R.mipmap.ic_launcher, true));//图片纹理
        GLES20.glVertexAttribPointer(
                vMCoordinate, //属性的位置
                2,//每个顶点的分量
                GLES20.GL_FLOAT,//顶点的数据类型float 或者int
                false,
                16,
                mTriangleCoordBuffer);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4);
        GLES20.glDisableVertexAttribArray(mPositionLocation);
        GLES20.glDisableVertexAttribArray(vMCoordinate);
    }
}
