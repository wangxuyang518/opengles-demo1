 attribute vec4 vPosition;
 attribute float point_size;
 attribute vec4 a_Color;

 varying vec4 v_Color;

 void main() {
     gl_Position = vPosition;
     gl_PointSize = point_size;
     v_Color=a_Color;
 }