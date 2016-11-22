package org.oscim.renderer;

import org.oscim.renderer.GLShader;
import org.oscim.renderer.GLState;
import org.oscim.renderer.GLViewport;

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
