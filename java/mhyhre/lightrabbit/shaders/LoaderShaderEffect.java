/*
 * Copyright (C) 2013-2015 Andrey Tulyakov
 * @mail: mhyhre@gmail.com
 */

package mhyhre.lightrabbit.shaders;

import android.opengl.GLES20;

import org.andengine.opengl.shader.PositionTextureCoordinatesShaderProgram;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.shader.constants.ShaderProgramConstants;
import org.andengine.opengl.shader.exception.ShaderProgramLinkException;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import mhyhre.lightrabbit.MainActivity;



public class LoaderShaderEffect extends ShaderProgram {

    public int theColorLocation = ShaderProgramConstants.LOCATION_INVALID;
    public int timeLocation = ShaderProgramConstants.LOCATION_INVALID;
    public int resolutionLocation = ShaderProgramConstants.LOCATION_INVALID; 
    
    
    private float timeValue = 0;

    public LoaderShaderEffect() {
        super(loadFromFile("shaders/base_vertex_shader.vs"), loadFromFile("shaders/loader_shader_copy.fs"));
    }

    
    public static String loadFromFile(String filename) {
        
        StringBuilder buf = new StringBuilder();
        InputStream inputStream = null;
        try {
            inputStream = MainActivity.Me.getAssets().open(filename);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        BufferedReader in = null;

        try {
            in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String str;

        try {
            while ((str = in.readLine()) != null) {
                buf.append(str);
                buf.append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }

    
    @Override
    protected void link(final GLState pGLState) throws ShaderProgramLinkException {

        GLES20.glBindAttribLocation(this.mProgramID, ShaderProgramConstants.ATTRIBUTE_POSITION_LOCATION, ShaderProgramConstants.ATTRIBUTE_POSITION);

        super.link(pGLState);

        PositionTextureCoordinatesShaderProgram.sUniformModelViewPositionMatrixLocation = this
                .getUniformLocation(ShaderProgramConstants.UNIFORM_MODELVIEWPROJECTIONMATRIX);
        //theColorLocation = this.getUniformLocation("theColor");
        timeLocation = this.getUniformLocation("time");
        resolutionLocation = this.getUniformLocation("resolution");
    }
    

    @Override
    public void bind(final GLState pGLState, final VertexBufferObjectAttributes pVertexBufferObjectAttributes) {
        super.bind(pGLState, pVertexBufferObjectAttributes);
        
        GLES20.glUniformMatrix4fv(PositionTextureCoordinatesShaderProgram.sUniformModelViewPositionMatrixLocation, 1, false,
                pGLState.getModelViewProjectionGLMatrix(), 0);
        
        timeValue += 0.01f;
        if(timeValue > 157.14f) {
            timeValue = 0.0f;
        }
        
        GLES20.glUniform1f(timeLocation, timeValue);    
        GLES20.glUniform2f(resolutionLocation, MainActivity.getWidth(), MainActivity.getHeight());  
    }
    
}