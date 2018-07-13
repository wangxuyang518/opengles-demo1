 attribute vec4 vPosition;
 attribute float point_size;
 attribute vec4 a_Color;
 varying vec4 v_Color;

 uniform mat4 matrixl;//mate4 就是4*4 矩阵  mat4 变量类型

 void main() {
     gl_Position = vPosition*matrixl;
     gl_PointSize = point_size;
     v_Color=a_Color;
 }