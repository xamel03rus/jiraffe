package jiraffe.core.basis;

import jiraffe.core.graphic.Render;
import jiraffe.core.utils.Buffer;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Gizmo {

    private int vao;
    private int index;
    private int vertex;

    protected Vector3f[] vertices;
    protected int[] indexes;

    public int getVao()
    {
        return vao;
    }
    public void setVao(int id)
    {
        vao = id;
    }

    public void setVboVertices(int id)
    {
        vertex = id;
    }
    public int getVboVertices()
    {
        return vertex;
    }

    public void setVboIndexes(int id)
    {
        index = id;
    }
    public int getVboIndexes()
    {
        return index;
    }

    // getters data
    public Vector3f[] getVertices()
    {
        return vertices;
    }
    public int[] getIndexes()
    {
        return indexes;
    }

    public void init()
    {
        FloatBuffer vertexes = Buffer.makeBuffer(this.getVertices());
        IntBuffer indices = Buffer.makeBuffer(this.getIndexes());
        try {
            this.vao = glGenVertexArrays();
            glBindVertexArray(this.vao);

            this.vertex = Render.initBuffer(GL_ARRAY_BUFFER, GL_FLOAT, GL_STATIC_DRAW, vertexes, 0, 3);
            this.index = Render.initBuffer(GL_ELEMENT_ARRAY_BUFFER, 0, GL_STATIC_DRAW, indices, 0, 0);

            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
        }
        finally {
            if (vertexes != null) {
                MemoryUtil.memFree(vertexes);
            }
            if (indices != null) {
                MemoryUtil.memFree(indices);
            }
        }
    }

    public void render()
    {
        glBindVertexArray(this.vao);
        glEnableVertexAttribArray(0);
        glDrawElements(GL_LINE, 1, GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }

}
