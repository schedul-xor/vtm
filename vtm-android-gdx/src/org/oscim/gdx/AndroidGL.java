/*
 * Copyright 2013 Hannes Janetzek
 *
 * This file is part of the OpenScienceMap project (http://www.opensciencemap.org).
 *
 * This program is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.oscim.gdx;

import android.annotation.SuppressLint;
import android.opengl.GLES30;

import org.oscim.backend.GL;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

@SuppressLint("NewApi")
public class AndroidGL implements GL {

    @Override
    public void attachShader(int program, int shader) {
        GLES30.glAttachShader(program, shader);
    }

    @Override
    public void bindAttribLocation(int program, int index, String name) {
        GLES30.glBindAttribLocation(program, index, name);
    }

    @Override
    public void bindBuffer(int target, int buffer) {
        GLES30.glBindBuffer(target, buffer);
    }

    @Override
    public void bindFramebuffer(int target, int framebuffer) {
        GLES30.glBindFramebuffer(target, framebuffer);
    }

    @Override
    public void bindRenderbuffer(int target, int renderbuffer) {
        GLES30.glBindRenderbuffer(target, renderbuffer);
    }

    @Override
    public void blendColor(float red, float green, float blue, float alpha) {
        GLES30.glBlendColor(red, green, blue, alpha);
    }

    @Override
    public void blendEquation(int mode) {
        GLES30.glBlendEquation(mode);
    }

    @Override
    public void blendEquationSeparate(int modeRGB, int modeAlpha) {
        GLES30.glBlendEquationSeparate(modeRGB, modeAlpha);
    }

    @Override
    public void blendFuncSeparate(int srcRGB, int dstRGB, int srcAlpha, int dstAlpha) {
        GLES30.glBlendFuncSeparate(srcRGB, dstRGB, srcAlpha, dstAlpha);
    }

    @Override
    public void bufferData(int target, int size, Buffer data, int usage) {
        GLES30.glBufferData(target, size, data, usage);
    }

    @Override
    public void bufferSubData(int target, int offset, int size, Buffer data) {
        GLES30.glBufferSubData(target, offset, size, data);
    }

    @Override
    public int checkFramebufferStatus(int target) {
        return GLES30.glCheckFramebufferStatus(target);
    }

    @Override
    public void compileShader(int shader) {
        GLES30.glCompileShader(shader);
    }

    @Override
    public int createProgram() {
        return GLES30.glCreateProgram();
    }

    @Override
    public int createShader(int type) {
        return GLES30.glCreateShader(type);
    }

    @Override
    public void deleteBuffers(int n, IntBuffer buffers) {
        GLES30.glDeleteBuffers(n, buffers);
    }

    @Override
    public void deleteFramebuffers(int n, IntBuffer framebuffers) {
        GLES30.glDeleteFramebuffers(n, framebuffers);
    }

    @Override
    public void deleteProgram(int program) {
        GLES30.glDeleteProgram(program);
    }

    @Override
    public void deleteRenderbuffers(int n, IntBuffer renderbuffers) {
        GLES30.glDeleteRenderbuffers(n, renderbuffers);
    }

    @Override
    public void deleteShader(int shader) {
        GLES30.glDeleteShader(shader);
    }

    @Override
    public void detachShader(int program, int shader) {
        GLES30.glDetachShader(program, shader);
    }

    @Override
    public void disableVertexAttribArray(int index) {
        GLES30.glDisableVertexAttribArray(index);
    }

    @Override
    public void drawElements(int mode, int count, int type, int offset) {
        GLES30.glDrawElements(mode, count, type, offset);
    }

    @Override
    public void drawElementsInstanced(int mode, int count, int type, int offset, int primCount) {
        GLES30.glDrawElementsInstanced(mode, count, type, offset, primCount);
    }

    @Override
    public void enableVertexAttribArray(int index) {
        GLES30.glEnableVertexAttribArray(index);
    }

    @Override
    public void framebufferRenderbuffer(int target, int attachment, int renderbuffertarget,
                                        int renderbuffer) {
        GLES30.glFramebufferRenderbuffer(target, attachment, renderbuffertarget, renderbuffer);
    }

    @Override
    public void framebufferTexture2D(int target, int attachment, int textarget, int texture,
                                     int level) {
        GLES30.glFramebufferTexture2D(target, attachment, textarget, texture, level);
    }

    @Override
    public void genBuffers(int n, IntBuffer buffers) {
        GLES30.glGenBuffers(n, buffers);
    }

    @Override
    public void generateMipmap(int target) {
        GLES30.glGenerateMipmap(target);
    }

    @Override
    public void genFramebuffers(int n, IntBuffer framebuffers) {
        GLES30.glGenFramebuffers(n, framebuffers);
    }

    @Override
    public void genRenderbuffers(int n, IntBuffer renderbuffers) {
        GLES30.glGenRenderbuffers(n, renderbuffers);
    }

    @Override
    public String getActiveAttrib(int program, int index, IntBuffer size, Buffer type) {
        return GLES30.glGetActiveAttrib(program, index, size, (IntBuffer) type);
    }

    @Override
    public String getActiveUniform(int program, int index, IntBuffer size, Buffer type) {
        //return GLES30.glGetActiveUniform(program, index, bufsize, length, size, type, name);
        throw new UnsupportedOperationException("missing implementation");
    }

    @Override
    public void getAttachedShaders(int program, int maxcount, Buffer count, IntBuffer shaders) {
        throw new UnsupportedOperationException("missing implementation");
        //GLES30.glGetAttachedShaders(program, maxcount, count, shaders);
    }

    @Override
    public int getAttribLocation(int program, String name) {
        return GLES30.glGetAttribLocation(program, name);
    }

    @Override
    public void getBooleanv(int pname, Buffer params) {
        throw new UnsupportedOperationException("missing implementation");
        //GLES30.glGetBooleanv(pname, params);
    }

    @Override
    public void getBufferParameteriv(int target, int pname, IntBuffer params) {
        GLES30.glGetBufferParameteriv(target, pname, params);

    }

    @Override
    public void getFloatv(int pname, FloatBuffer params) {
        GLES30.glGetFloatv(pname, params);

    }

    @Override
    public void getFramebufferAttachmentParameteriv(int target, int attachment, int pname,
                                                    IntBuffer params) {
        GLES30.glGetFramebufferAttachmentParameteriv(target, attachment, pname, params);
    }

    @Override
    public void getProgramiv(int program, int pname, IntBuffer params) {
        GLES30.glGetProgramiv(program, pname, params);

    }

    @Override
    public String getProgramInfoLog(int program) {
        return GLES30.glGetProgramInfoLog(program);
    }

    @Override
    public void getRenderbufferParameteriv(int target, int pname, IntBuffer params) {
        GLES30.glGetRenderbufferParameteriv(target, pname, params);

    }

    @Override
    public void getShaderiv(int shader, int pname, IntBuffer params) {
        GLES30.glGetShaderiv(shader, pname, params);

    }

    @Override
    public String getShaderInfoLog(int shader) {
        return GLES30.glGetShaderInfoLog(shader);
    }

    @Override
    public void getShaderPrecisionFormat(int shadertype, int precisiontype, IntBuffer range,
                                         IntBuffer precision) {
        GLES30.glGetShaderPrecisionFormat(shadertype, precisiontype, range, precision);
    }

    @Override
    public void getShaderSource(int shader, int bufsize, Buffer length, String source) {
        throw new UnsupportedOperationException("missing implementation");
    }

    @Override
    public void getTexParameterfv(int target, int pname, FloatBuffer params) {
        GLES30.glGetTexParameterfv(target, pname, params);

    }

    @Override
    public void getTexParameteriv(int target, int pname, IntBuffer params) {
        GLES30.glGetTexParameteriv(target, pname, params);

    }

    @Override
    public void getUniformfv(int program, int location, FloatBuffer params) {
        GLES30.glGetUniformfv(program, location, params);

    }

    @Override
    public void getUniformiv(int program, int location, IntBuffer params) {
        GLES30.glGetUniformiv(program, location, params);

    }

    @Override
    public int getUniformLocation(int program, String name) {
        return GLES30.glGetUniformLocation(program, name);
    }

    @Override
    public void getVertexAttribfv(int index, int pname, FloatBuffer params) {
        GLES30.glGetVertexAttribfv(index, pname, params);

    }

    @Override
    public void getVertexAttribiv(int index, int pname, IntBuffer params) {
        GLES30.glGetVertexAttribiv(index, pname, params);

    }

    @Override
    public void getVertexAttribPointerv(int index, int pname, Buffer pointer) {
        //GLES30.glGetVertexAttribPointerv(index, pname, pointer);
        throw new UnsupportedOperationException("missing implementation");
    }

    @Override
    public boolean isBuffer(int buffer) {
        return GLES30.glIsBuffer(buffer);
    }

    @Override
    public boolean isEnabled(int cap) {
        return GLES30.glIsEnabled(cap);
    }

    @Override
    public boolean isFramebuffer(int framebuffer) {
        return GLES30.glIsFramebuffer(framebuffer);
    }

    @Override
    public boolean isProgram(int program) {
        return GLES30.glIsProgram(program);
    }

    @Override
    public boolean isRenderbuffer(int renderbuffer) {
        return GLES30.glIsRenderbuffer(renderbuffer);
    }

    @Override
    public boolean isShader(int shader) {
        return GLES30.glIsShader(shader);
    }

    @Override
    public boolean isTexture(int texture) {
        return GLES30.glIsTexture(texture);
    }

    @Override
    public void linkProgram(int program) {
        GLES30.glLinkProgram(program);

    }

    @Override
    public void releaseShaderCompiler() {
        GLES30.glReleaseShaderCompiler();

    }

    @Override
    public void renderbufferStorage(int target, int internalformat, int width, int height) {
        GLES30.glRenderbufferStorage(target, internalformat, width, height);

    }

    @Override
    public void sampleCoverage(float value, boolean invert) {
        GLES30.glSampleCoverage(value, invert);

    }

    @Override
    public void shaderBinary(int n, IntBuffer shaders, int binaryformat, Buffer binary, int length) {
        GLES30.glShaderBinary(n, shaders, binaryformat, binary, length);

    }

    @Override
    public void shaderSource(int shader, String string) {
        GLES30.glShaderSource(shader, string);

    }

    @Override
    public void stencilFuncSeparate(int face, int func, int ref, int mask) {
        GLES30.glStencilFuncSeparate(face, func, ref, mask);

    }

    @Override
    public void stencilMaskSeparate(int face, int mask) {
        GLES30.glStencilMaskSeparate(face, mask);

    }

    @Override
    public void stencilOpSeparate(int face, int fail, int zfail, int zpass) {
        GLES30.glStencilOpSeparate(face, fail, zfail, zpass);

    }

    @Override
    public void texParameterfv(int target, int pname, FloatBuffer params) {
        GLES30.glTexParameterfv(target, pname, params);

    }

    @Override
    public void texParameteri(int target, int pname, int param) {
        GLES30.glTexParameteri(target, pname, param);

    }

    @Override
    public void texParameteriv(int target, int pname, IntBuffer params) {
        GLES30.glTexParameteriv(target, pname, params);

    }

    @Override
    public void uniform1f(int location, float x) {
        GLES30.glUniform1f(location, x);

    }

    @Override
    public void uniform1fv(int location, int count, FloatBuffer v) {
        GLES30.glUniform1fv(location, count, v);

    }

    @Override
    public void uniform1i(int location, int x) {
        GLES30.glUniform1i(location, x);

    }

    @Override
    public void uniform1iv(int location, int count, IntBuffer v) {
        GLES30.glUniform1iv(location, count, v);

    }

    @Override
    public void uniform2f(int location, float x, float y) {
        GLES30.glUniform2f(location, x, y);

    }

    @Override
    public void uniform2fv(int location, int count, FloatBuffer v) {
        GLES30.glUniform2fv(location, count, v);

    }

    @Override
    public void uniform2i(int location, int x, int y) {
        GLES30.glUniform2i(location, x, y);

    }

    @Override
    public void uniform2iv(int location, int count, IntBuffer v) {
        GLES30.glUniform2iv(location, count, v);

    }

    @Override
    public void uniform3f(int location, float x, float y, float z) {
        GLES30.glUniform3f(location, x, y, z);

    }

    @Override
    public void uniform3fv(int location, int count, FloatBuffer v) {
        GLES30.glUniform3fv(location, count, v);

    }

    @Override
    public void uniform3i(int location, int x, int y, int z) {
        GLES30.glUniform3i(location, x, y, z);

    }

    @Override
    public void uniform3iv(int location, int count, IntBuffer v) {
        GLES30.glUniform3iv(location, count, v);

    }

    @Override
    public void uniform4f(int location, float x, float y, float z, float w) {
        GLES30.glUniform4f(location, x, y, z, w);
    }

    @Override
    public void uniform4fv(int location, int count, FloatBuffer v) {
        GLES30.glUniform4fv(location, count, v);
    }

    @Override
    public void uniform4i(int location, int x, int y, int z, int w) {
        GLES30.glUniform4i(location, x, y, z, w);

    }

    @Override
    public void uniform4iv(int location, int count, IntBuffer v) {
        GLES30.glUniform4iv(location, count, v);

    }

    @Override
    public void uniformMatrix2fv(int location, int count, boolean transpose, FloatBuffer value) {
        GLES30.glUniformMatrix2fv(location, count, transpose, value);

    }

    @Override
    public void uniformMatrix3fv(int location, int count, boolean transpose, FloatBuffer value) {
        GLES30.glUniformMatrix3fv(location, count, transpose, value);

    }

    @Override
    public void uniformMatrix4fv(int location, int count, boolean transpose, FloatBuffer value) {
        GLES30.glUniformMatrix4fv(location, count, transpose, value);

    }

    @Override
    public void useProgram(int program) {
        GLES30.glUseProgram(program);

    }

    @Override
    public void validateProgram(int program) {
        GLES30.glValidateProgram(program);

    }

    @Override
    public void vertexAttrib1f(int indx, float x) {
        GLES30.glVertexAttrib1f(indx, x);

    }

    @Override
    public void vertexAttrib1fv(int indx, FloatBuffer values) {
        GLES30.glVertexAttrib1fv(indx, values);

    }

    @Override
    public void vertexAttrib2f(int indx, float x, float y) {
        GLES30.glVertexAttrib2f(indx, x, y);

    }

    @Override
    public void vertexAttrib2fv(int indx, FloatBuffer values) {
        GLES30.glVertexAttrib2fv(indx, values);

    }

    @Override
    public void vertexAttrib3f(int indx, float x, float y, float z) {
        GLES30.glVertexAttrib3f(indx, x, y, z);

    }

    @Override
    public void vertexAttrib3fv(int indx, FloatBuffer values) {
        GLES30.glVertexAttrib3fv(indx, values);

    }

    @Override
    public void vertexAttrib4f(int indx, float x, float y, float z, float w) {
        GLES30.glVertexAttrib4f(indx, x, y, z, w);

    }

    @Override
    public void vertexAttrib4fv(int indx, FloatBuffer values) {
        GLES30.glVertexAttrib4fv(indx, values);

    }

    @Override
    public void vertexAttribPointer(int indx, int size, int type, boolean normalized, int stride,
                                    Buffer ptr) {
        GLES30.glVertexAttribPointer(indx, size, type, normalized, stride, ptr);
    }

    @Override
    public void vertexAttribPointer(int indx, int size, int type, boolean normalized, int stride,
                                    int offset) {
        // FIXME check implementation!
        GLES30.glVertexAttribPointer(indx, size, type, normalized, stride, offset);
        //throw new UnsupportedOperationException("missing implementation");
    }

    @Override
    public void vertexAttribDivisor(int indx, int divisor) {
        GLES30.glVertexAttribDivisor(indx, divisor);
    }

    @Override
    public void activeTexture(int texture) {
        GLES30.glActiveTexture(texture);

    }

    @Override
    public void bindTexture(int target, int texture) {
        GLES30.glBindTexture(target, texture);

    }

    @Override
    public void blendFunc(int sfactor, int dfactor) {
        GLES30.glBlendFunc(sfactor, dfactor);

    }

    @Override
    public void clear(int mask) {
        GLES30.glClear(mask);

    }

    @Override
    public void clearColor(float red, float green, float blue, float alpha) {
        GLES30.glClearColor(red, green, blue, alpha);

    }

    @Override
    public void clearDepthf(float depth) {
        GLES30.glClearDepthf(depth);

    }

    @Override
    public void clearStencil(int s) {
        GLES30.glClearStencil(s);

    }

    @Override
    public void colorMask(boolean red, boolean green, boolean blue, boolean alpha) {
        GLES30.glColorMask(red, green, blue, alpha);

    }

    @Override
    public void compressedTexImage2D(int target, int level, int internalformat, int width,
                                     int height, int border, int imageSize, Buffer data) {
        throw new UnsupportedOperationException("missing implementation");

    }

    @Override
    public void compressedTexSubImage2D(int target, int level, int xoffset, int yoffset,
                                        int width, int height, int format, int imageSize, Buffer data) {
        throw new UnsupportedOperationException("missing implementation");

    }

    @Override
    public void copyTexImage2D(int target, int level, int internalformat, int x, int y,
                               int width, int height, int border) {
        GLES30.glCopyTexImage2D(target, level, internalformat, x, y, width, height, border);
    }

    @Override
    public void copyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y,
                                  int width, int height) {
        GLES30.glCopyTexSubImage2D(target, level, xoffset, yoffset, x, y, width, height);
    }

    @Override
    public void cullFace(int mode) {
        GLES30.glCullFace(mode);

    }

    @Override
    public void deleteTextures(int n, IntBuffer textures) {
        GLES30.glDeleteTextures(n, textures);

    }

    @Override
    public void depthFunc(int func) {
        GLES30.glDepthFunc(func);

    }

    @Override
    public void depthMask(boolean flag) {
        GLES30.glDepthMask(flag);

    }

    @Override
    public void depthRangef(float zNear, float zFar) {
        GLES30.glDepthRangef(zNear, zFar);

    }

    @Override
    public void disable(int cap) {
        GLES30.glDisable(cap);

    }

    @Override
    public void drawArrays(int mode, int first, int count) {
        GLES30.glDrawArrays(mode, first, count);

    }

    @Override
    public void drawElements(int mode, int count, int type, Buffer indices) {
        GLES30.glDrawElements(mode, count, type, indices);

    }

    @Override
    public void enable(int cap) {
        GLES30.glEnable(cap);

    }

    @Override
    public void finish() {
        GLES30.glFinish();

    }

    @Override
    public void flush() {
        GLES30.glFlush();

    }

    @Override
    public void frontFace(int mode) {
        GLES30.glFrontFace(mode);

    }

    @Override
    public void genTextures(int n, IntBuffer textures) {
        GLES30.glGenTextures(n, textures);

    }

    @Override
    public int getError() {
        return GLES30.glGetError();
    }

    @Override
    public void getIntegerv(int pname, IntBuffer params) {
        GLES30.glGetIntegerv(pname, params);

    }

    @Override
    public String getString(int name) {
        return GLES30.glGetString(name);
    }

    @Override
    public void hint(int target, int mode) {
        GLES30.glHint(target, mode);
    }

    @Override
    public void lineWidth(float width) {
        GLES30.glLineWidth(width);

    }

    @Override
    public void pixelStorei(int pname, int param) {
        GLES30.glPixelStorei(pname, param);

    }

    @Override
    public void polygonOffset(float factor, float units) {
        GLES30.glPolygonOffset(factor, units);

    }

    @Override
    public void readPixels(int x, int y, int width, int height, int format, int type,
                           Buffer pixels) {
        GLES30.glReadPixels(x, y, width, height, format, type, pixels);
    }

    @Override
    public void scissor(int x, int y, int width, int height) {
        GLES30.glScissor(x, y, width, height);
    }

    @Override
    public void stencilFunc(int func, int ref, int mask) {
        GLES30.glStencilFunc(func, ref, mask);
    }

    @Override
    public void stencilMask(int mask) {
        GLES30.glStencilMask(mask);
    }

    @Override
    public void stencilOp(int fail, int zfail, int zpass) {
        GLES30.glStencilOp(fail, zfail, zpass);
    }

    @Override
    public void texImage2D(int target, int level, int internalformat, int width, int height,
                           int border, int format, int type, Buffer pixels) {
        GLES30.glTexImage2D(target, level, internalformat, width, height, border, format, type,
                pixels);
    }

    @Override
    public void texParameterf(int target, int pname, float param) {
        GLES30.glTexParameterf(target, pname, param);
    }

    @Override
    public void texSubImage2D(int target, int level, int xoffset, int yoffset, int width,
                              int height, int format, int type, Buffer pixels) {
        GLES30
                .glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);

    }

    @Override
    public void viewport(int x, int y, int width, int height) {
        GLES30.glViewport(x, y, width, height);
    }

}
