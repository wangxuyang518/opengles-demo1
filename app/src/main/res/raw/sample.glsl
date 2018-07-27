 attribute vec4 vPosition;
 attribute float point_size;
 attribute vec4 a_Color;

 attribute vec2 vCoordinate;


 varying vec2 aCoordinate;
 uniform mat4 matrixl;//mate4 就是4*4 矩阵  mat4 变量类型

 void main() {
     gl_Position = vPosition;
     gl_PointSize = point_size;


     aCoordinate=vCoordinate;
 }