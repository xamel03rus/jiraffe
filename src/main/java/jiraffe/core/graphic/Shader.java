package jiraffe.core.graphic;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import org.lwjgl.system.MemoryStack;

import java.io.InputStream;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Shader{

    protected String name;
    protected int programId;
    protected int vertexShader;
    protected int geometryShader;
    protected int fragmentShader;

    protected Map<String, Integer> uniforms = new HashMap<>();

    public void create() throws Exception {

        try {
            programId = GL20.glCreateProgram();
            if (programId == 0) {
                throw new Exception("Could not create Shader Program");
            }
        } catch (Exception ex) {
            System.out.println("Could not create Shader Program");
        }
    }

    public String getName()
    {
        return name;
    }

    public void setName(String n)
    {
        name = n;
    }

    public void delete() {
        unbind();
        if (programId != 0) {
            GL20.glDeleteProgram(programId);
        }
    }

    public void createVertexShader(String fileName) throws Exception {
        vertexShader = createShader(GL20.GL_VERTEX_SHADER, this.loadFromResource(fileName));
    }

    public void createFragmentShader(String fileName) throws Exception {
        fragmentShader = createShader(GL20.GL_FRAGMENT_SHADER, this.loadFromResource(fileName));
    }

    public void createGeometryShader(String fileName) throws Exception {
        geometryShader = createShader(GL32.GL_GEOMETRY_SHADER, this.loadFromResource(fileName));
    }

    public int createShader(int shaderType,String code) throws Exception
    {
        int shaderId = GL20.glCreateShader(shaderType);
        if (shaderId == 0) {
            throw new Exception("Error creating shader. Type: " + shaderType);
        }

        GL20.glShaderSource(shaderId, code);
        GL20.glCompileShader(shaderId);

        if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == 0) {
            throw new Exception("Error compiling Shader code: " + GL20.glGetShaderInfoLog(shaderId, 1024));
        }

        GL20.glAttachShader(programId, shaderId);

        return shaderId;
    }

    public void bind() {
        GL20.glUseProgram(programId);
    }

    public void unbind() {
        GL20.glUseProgram(0);
    }

    public void link() throws Exception {

        GL20.glLinkProgram(programId);
        if (GL20.glGetProgrami(programId, GL20.GL_LINK_STATUS) == 0) {
            throw new Exception("Error linking Shader code: " + GL20.glGetProgramInfoLog(programId, 1024));
        }

        if (vertexShader != 0) {
            GL20.glDetachShader(programId, vertexShader);
        }
        if (geometryShader != 0) {
            GL20.glDetachShader(programId, geometryShader);
        }
        if (fragmentShader != 0) {
            GL20.glDetachShader(programId, fragmentShader);
        }

        GL20.glValidateProgram(programId);
        if (GL20.glGetProgrami(programId, GL20.GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + GL20.glGetProgramInfoLog(programId, 1024));
        }

        this.createUniforms();
    }

    public void createUniform(String uniformName) throws Exception {
        int uniformLocation = GL20.glGetUniformLocation(programId,
                uniformName);
        if (uniformLocation < 0) {
            throw new Exception("Could not find uniform:" +
                    uniformName);
        }
        uniforms.put(uniformName, uniformLocation);
    }

    public void createMaterialUniform(String uniformName) throws Exception {
        createUniform(uniformName + ".color");
    }

    public void setUniform(String uniformName, Matrix4f value) {
        // Dump the matrix into a float buffer
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            value.get(fb);
            GL20.glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
        }
    }

    public void setUniform(String uniformName, int value) {
        GL20.glUniform1i(uniforms.get(uniformName), value);
    }

    public void setUniform(String uniformName, float value) {
        GL20.glUniform1f(uniforms.get(uniformName), value);
    }

    public void setUniform(String uniformName, Vector3f value) {
        GL20.glUniform3f(uniforms.get(uniformName), value.x, value.y, value.z);
    }

    public void setUniform(String uniformName, Vector4f value) {
        GL20.glUniform4f(uniforms.get(uniformName), value.x, value.y, value.z, value.w);
    }

    public void setUniform(String uniformName, Material material) {
        setUniform(uniformName + ".color", material.getColor());
    }

    private void createUniforms() throws Exception {
        this.createUniform("projectionSpace");
        this.createUniform("viewSpace");
        this.createUniform("modelSpace");
        this.createMaterialUniform("material");
    }

    private String loadFromResource(String fileName) throws Exception
    {
        String result = "";
        try (InputStream in = getClass().getResourceAsStream(fileName);
             Scanner scanner = new Scanner(in, "UTF-8")) {
            result = scanner.useDelimiter("\\A").next();
        }

        return result;
    }
}
