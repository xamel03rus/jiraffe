package jiraffe.core.utils;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.List;

public class Utils {

    public static int[] listIntToArray(List<Integer> list) {
        return list.stream().mapToInt((Integer v) -> v).toArray();
    }

    public static float[] listFloatToArray(List<Float> list) {
        int size = list != null ? list.size() : 0;
        float[] floatArr = new float[size];
        for (int i = 0; i < size; i++) {
            floatArr[i] = list.get(i);
        }
        return floatArr;
    }

    public static Vector2f[] listVector2fToArray(List<Vector2f> list) {
        int size = list != null ? list.size() : 0;
        Vector2f[] arr = new Vector2f[size];
        for (int i = 0; i < size; i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    public static Vector3f[] listVector3fToArray(List<Vector3f> list) {
        int size = list != null ? list.size() : 0;
        Vector3f[] arr = new Vector3f[size];
        for (int i = 0; i < size; i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

}
