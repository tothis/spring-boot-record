package com.example.util;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author 李磊
 * @datetime 2020/3/8 2:22
 * @description
 */
public class ArrayUtil {

    /**
     * 合并多个数组
     *
     * @param arrays
     * @return
     */
    public static Object[] arrayMerge1(Object[]... arrays) {
        if (arrays == null || arrays.length == 0) return null;
        if (arrays.length == 1) return arrays[0];

        int count = 0;
        for (int i = 0; i < arrays.length; i++) {
            count += arrays[i].length;
        }
        Object[] result = new Object[count];
        for (int i = 0; i < arrays.length; i++) {
            Object[] array = arrays[i];
            for (int j = 0; j < array.length; j++) {
                result[arrays.length * i + j] = array[j];
            }
        }
        return result;
    }

    /**
     * 合并多个数组
     */
    public static Object[] arrayMerge2(Object[]... arrays) {
        // 数组长度
        int length = 0;
        // 目标数组的起始位置
        int index = 0;

        for (Object[] array : arrays) {
            length = length + array.length;
        }
        Object[] result = new Object[length];
        for (int i = 0; i < arrays.length; i++) {
            if (i > 0) {
                // i为0时 目标数组开始索引为0
                // i为1时 目标数组开始索引为为第一个数组长度
                // i为2时 目标数组开始索引为第一个数组长度+第二个数组长度
                index = index + arrays[i - 1].length;
            }
            System.arraycopy(arrays[i], 0
                    , result, index, arrays[i].length);
        }
        return result;
    }

    /**
     * 获取数组中某一段元素
     *
     * @param array
     * @param from
     * @param to
     * @return
     */
    public static Object[] slipArray1(Object[] array, int from, int to) {
        Object[] objects = new Object[to - from];
        for (int i = 0; i < objects.length; i++) {
            objects[i] = array[from + i];
        }
        return objects;
    }

    /**
     * 获取数组中某一段元素
     *
     * @param array
     * @param from
     * @param to
     * @return
     */
    public static Object[] slipArray2(Object[] array, int from, int to) {
        return Arrays.copyOfRange(array, from, to);
    }

    /**
     * 删除数组中指定下标元素
     *
     * @param array
     * @param index
     * @return
     */
    public static Object[] delete(Object array[], int index) {
        // 创建新的数组 长度是原来-1
        Object[] newarray = new Object[array.length - 1];
        // 将除了要删除的元素的其他 元素复制到新的数组
        for (int i = 0; i < newarray.length; i++) {
            // 需要删除下标之前的元素
            if (i < index) {
                newarray[i] = array[i];
            }
            // 之后的元素
            else {
                newarray[i] = array[i + 1];
            }
        }
        return newarray;
    }

    /**
     * 在数组指定下标插入元素
     *
     * @param array
     * @param index
     * @return
     */
    public static Object[] insert(Object array[], int index, Object element) {
        // 创建一个新的数组 长度是原来长度+1
        Object[] newarray = new Object[array.length + 1];
        // 把原数组中的数据赋值到新的数组
        for (int i = 0; i < array.length; i++) {
            newarray[i] = array[i];
        }

        for (int i = newarray.length - 1; i > index; i--) {
            newarray[i] = newarray[i - 1];
        }
        newarray[index] = element;
        return newarray;
    }

    /**
     * 数组类型转化
     *
     * @param array
     * @param targetType
     * @param <T>
     * @return
     */
    public static <T> T[] convertArray(Object[] array, Class<T> targetType) {
        if (targetType == null) {
            return (T[]) array;
        }
        if (array == null) {
            return null;
        }
        T[] targetArray = (T[]) Array.newInstance(targetType, array.length);
        try {
            System.arraycopy(array, 0
                    , targetArray, 0, array.length);
        } catch (ArrayStoreException e) {
            e.printStackTrace();
        }
        return targetArray;
    }

    public static void main(String[] args) {
        int index = 1;
        int value = 0;
        Integer[] array = {1, 2, 3};
        print(insert(array, index, value));
        print(delete(array, index));

        print(slipArray1(array, 1, 2));
        print(slipArray2(array, 1, 2));

        Object[][] arrays = {
                {"1", "2", "3"}
                , {"4", "5", "6"}
                , {"7", "8", "9"}
        };

        print(arrayMerge1(arrays));
        print(arrayMerge2(arrays));
    }

    public static <T> void print(T[] array) {
        Arrays.stream(array).forEach(System.out::println);
    }

}