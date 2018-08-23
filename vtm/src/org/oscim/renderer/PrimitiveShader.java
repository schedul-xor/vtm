package org.oscim.renderer;

/**
 * Created by xor on 15/08/23.
 */
public class PrimitiveShader extends GLShader {
    public int uMVP, uColor, aPos;

    public PrimitiveShader(String shaderFile) {
        if (!create(shaderFile))
            return;

        uColor = getUniform("u_color");
        aPos = getAttrib("a_pos");
        uMVP = getUniform("u_mvp");
    }

    public void set(GLViewport v) {
        useProgram();
        GLState.enableVertexArrays(aPos, -1);
    }
}
