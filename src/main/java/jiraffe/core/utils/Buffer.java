package jiraffe.core.utils;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Buffer {

    public static IntBuffer makeBuffer(int[] data)
    {
        IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
        buffer.put(data).flip();

        return buffer;
    }

    public static FloatBuffer makeBuffer(float[] data)
    {
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        buffer.put(data).flip();

        return buffer;
    }

    public static FloatBuffer makeBuffer(Vector2f[] data)
    {
        float[] v = new float[data.length * 2];

        FloatBuffer buffer = MemoryUtil.memAllocFloat(v.length);

        for(int i = 0; i < data.length; i++)
        {
            v[i * 2] = data[i].x;
            v[i * 2 + 1] = data[i].y;
        }

        buffer.put(v);
        buffer.flip();

        return buffer;
    }

    public static FloatBuffer makeBuffer(Vector3f[] data)
    {
        float[] v = new float[data.length * 3];

        FloatBuffer buffer = MemoryUtil.memAllocFloat(v.length);

        for(int i = 0; i < data.length; i++)
        {
            v[i * 3] = data[i].x;
            v[i * 3 + 1] = data[i].y;
            v[i * 3 + 2] = data[i].z;
        }

        buffer.put(v);
        buffer.flip();

        return buffer;
    }

    public static ByteBuffer makeBuffer(byte[] data)
    {
        return ByteBuffer.wrap(data);
    }

}
