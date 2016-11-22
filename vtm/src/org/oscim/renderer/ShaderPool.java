package org.oscim.renderer;

import org.oscim.renderer.bucket.BitmapBucket;

/**
 * Created by xor on 16/07/01.
 */
public class ShaderPool {
    static private ShaderPool instance = new ShaderPool();

    static public ShaderPool getInstance() {
        return instance;
    }

    private PrimitiveShader primitiveShader;

    private ShaderPool() {
    }

    public void setupShaders() {
        if (primitiveShader == null) {
            primitiveShader = new PrimitiveShader("base_shader");
        }
    }

    public PrimitiveShader getPrimitiveShader() {
        return primitiveShader;
    }

    public BitmapBucket.Shader getTextureShader() {
        return BitmapBucket.Renderer.shader;
    }
}
