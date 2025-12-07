package jiraffe.core.graphic;

import jiraffe.core.basis.Engine;
import jiraffe.core.basis.Scene;
import jiraffe.core.basis.GameObject;
import jiraffe.core.geometry.Mesh;
import jiraffe.core.geometry.Space;
import jiraffe.tools.CameraOperator;
import jiraffe.core.utils.Buffer;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Render {

    static Render instance;
    int currentShader = 0;
    Map<String, Shader> shaders = new HashMap<String, Shader>();

    public static Render getInstance() {
        if(instance == null) {
            instance = new Render();
            try {
                instance.init();
            } catch (Exception exception) {
                System.out.println("crash on creating shader: " + exception.getMessage());
            }
        }

        return instance;
    }

    public void init() throws Exception {
        Shader s = new Shader();
        s.setName("default");
        s.create();
        s.createVertexShader("/shaders/defaultVertex.vs");
        s.createFragmentShader("/shaders/defaultFragment.fs");
        s.link();

        shaders.put(s.getName(), s);
    }

    public void addShader(Shader shader)
    {
        shaders.put(shader.getName(), shader);
    }

    public Shader getShader(String name)
    {
        return shaders.get(name);
    }

    public void initVao(Mesh mesh)
    {
        FloatBuffer vertexes = Buffer.makeBuffer(mesh.getVertices());
        FloatBuffer normals = Buffer.makeBuffer(mesh.getNormals());
        IntBuffer indices = Buffer.makeBuffer(mesh.getIndexes());
        FloatBuffer textCoords = Buffer.makeBuffer(mesh.getTextureCoords());

        try {
            mesh.setVao(glGenVertexArrays());
            glBindVertexArray(mesh.getVao());

            mesh.setVboVertices(
                    initBuffer(GL_ARRAY_BUFFER, GL_FLOAT, mesh.getRenderType(), vertexes, 0, 3)
            );
            mesh.setVboTextureCoords(
                    initBuffer(GL_ARRAY_BUFFER, GL_FLOAT, mesh.getRenderType(), textCoords, 1, 2)
            );
            mesh.setVboNormals(
                    initBuffer(GL_ARRAY_BUFFER, GL_FLOAT, mesh.getRenderType(), normals, 2, 3)
            );
            mesh.setVboIndexes(
                    initBuffer(GL_ELEMENT_ARRAY_BUFFER, 0, mesh.getRenderType(), indices, 0, 0)
            );

            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
        }
        finally {
            if (vertexes != null) {
                MemoryUtil.memFree(vertexes);
            }
            if (normals != null) {
                MemoryUtil.memFree(normals);
            }
            if (textCoords != null) {
                MemoryUtil.memFree(textCoords);
            }
            if (indices != null) {
                MemoryUtil.memFree(indices);
            }
        }
    }

    public static int initBuffer(int bufferType, int varType, int renderType, FloatBuffer data, int index, int size)
    {
        int bufferId = glGenBuffers();
        glBindBuffer(bufferType, bufferId);
        glBufferData(bufferType, data, renderType);
        if(varType != 0)
            glVertexAttribPointer(index, size, varType, false, 0, 0);

        return bufferId;
    }

    public static int initBuffer(int bufferType, int varType, int renderType, IntBuffer data, int index, int size)
    {
        int bufferId = glGenBuffers();
        glBindBuffer(bufferType, bufferId);
        glBufferData(bufferType, data, renderType);
        if(varType != 0)
            glVertexAttribPointer(index, size, varType, false, 0, 0);

        return bufferId;
    }

    public void drawVao(Mesh mesh)
    {

        // TODO work with texture
/*        Texture texture = object.getMesh().getMaterial().getTexture();
        if (texture != null) {
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, texture.getID());
        }*/

        int count = mesh.getIndexes().length;

        glBindVertexArray(mesh.getVao());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindVertexArray(0);
        //glBindTexture(GL_TEXTURE_2D, 0);

    }

    /**
     * @param mode - GL_FILL, GL_LINE, GL_POINT
     */
    public void setPolygonMode(int mode)
    {
        switch(mode) {
            case 1:
                mode = GL_LINE;
                break;
            case 2:
                mode = GL_POINT;
                break;
            default:
                mode = GL_FILL;
        }

        glPolygonMode(GL_FRONT_AND_BACK, mode);
    }

    public void unbindShader()
    {
        GL20.glUseProgram(0);
    }

    public void renderScene(Scene scene)
    {
        Space.getInstance().setProjectionSpace(CameraOperator.getInstance().getCurrentCamera().getFOV(), Engine.getInstance().getCurrentWindow().getAspect(), 0.001f, 1000f);
        Space.getInstance().setViewSpace(CameraOperator.getInstance().getCurrentCamera());

        renderGameObjects(scene.getGameObjectList());
        this.unbindShader();
    }

    public void renderGameObjects(List<GameObject> gameObjects)
    {
        for(GameObject gameObject : gameObjects) {
            Space.getInstance().setModelSpace(gameObject);

            this.renderGameObject(gameObject);
            this.renderGameObjects(gameObject.getChilds());
        }
    }

    public void renderGameObject(GameObject gameObject)
    {
        Material material = gameObject.getMaterial();
        Mesh mesh = gameObject.getMesh();

        if(currentShader != material.getShader().programId) {
            currentShader = material.getShader().programId;
            material.getShader().bind();
            material.getShader().setUniform("material", material);
        }

        material.getShader().setUniform("viewSpace", Space.getInstance().getViewSpace());
        material.getShader().setUniform("projectionSpace", Space.getInstance().getProjectionSpace());
        material.getShader().setUniform("modelSpace", Space.getInstance().getModelSpace());

        if(mesh != null) {
            if(mesh.getVao() == 0)
                this.initVao(mesh);
            this.drawVao(mesh);
        }
    }

    public void clearBuffers()
    {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
    }

    public void clear()
    {
        glClearColor(0f, 0f, 0f, 0f);
    }

}
