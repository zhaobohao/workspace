package com.kyro;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

/**
 * 基于kyro的序列化/反序列化工具
 *
 * @author eguid
 */
class kryoSerializer implements Serializer {

    // 由于kryo不是线程安全的，所以每个线程都使用独立的kryo
    final ThreadLocal<Kryo> kryoLocal = new ThreadLocal<Kryo>() {
        @Override
        protected Kryo initialValue() {
            Kryo kryo = new Kryo();
            /**
             * 不要轻易改变这里的配置！更改之后，序列化的格式就会发生变化，
             * 上线的同时就必须清除 Redis 里的所有缓存，
             * 否则那些缓存再回来反序列化的时候，就会报错
             */
            //支持对象循环引用（否则会栈溢出）
            kryo.setReferences(true);//默认值就是 true，添加此行的目的是为了提醒维护者，不要改变这个配置
            //不强制要求注册类（注册行为无法保证多个 JVM 内同一个类的注册编号相同；而且业务系统中大量的 Class 也难以一一注册）
            kryo.setRegistrationRequired(false);//默认值就是 false，添加此行的目的是为了提醒维护者，不要改变这个配置
            //Fix the NPE bug when deserializing Collections.
            kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
            //kryo.register(ct);
            // kryo.register(Date.class);
            return kryo;
        }
    };
    final ThreadLocal<Output> outputLocal = new ThreadLocal<Output>();
    final ThreadLocal<Input> inputLocal = new ThreadLocal<Input>();
    private Class<?> ct = null;

    public kryoSerializer(Class<?> ct) {
        this.ct = ct;
    }

    public Class<?> getCt() {
        return ct;
    }

    public void setCt(Class<?> ct) {
        this.ct = ct;
    }

    @Override
    public void serialize(Object obj, byte[] bytes) {
        Kryo kryo = getKryo();
        Output output = getOutput(bytes);
        kryo.writeObject(output, obj);
        output.flush();
    }

    @Override
    public void serialize(Object obj, byte[] bytes, int offset, int count) {
        Kryo kryo = getKryo();
        Output output = getOutput(bytes, offset, count);
        kryo.writeObjectOrNull(output, obj, obj.getClass());
        output.flush();
    }

    /**
     * 获取kryo
     *
     * @return
     */
    private Kryo getKryo() {
        return kryoLocal.get();
    }

    /**
     * 获取Output并设置初始数组
     *
     * @param bytes
     * @return
     */
    private Output getOutput(byte[] bytes) {
        Output output = null;
        if ((output = outputLocal.get()) == null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//    注意new output的时候设置buffersize的大小，如果buffersize太小会报出unregistered class ID 的错误
            output = new Output(byteArrayOutputStream, 100000000);
            outputLocal.set(output);
        }
        if (bytes != null) {
            output.setBuffer(bytes);
        }
        return output;
    }

    /**
     * 获取Output
     *
     * @param bytes
     * @return
     */
    private Output getOutput(byte[] bytes, int offset, int count) {
        Output output = null;
        if ((output = outputLocal.get()) == null) {
            output = new Output();
            outputLocal.set(output);
        }
        if (bytes != null) {
            output.writeBytes(bytes, offset, count);
        }
        return output;
    }

    /**
     * 获取Input
     *
     * @param bytes
     * @param offset
     * @param count
     * @return
     */
    private Input getInput(byte[] bytes, int offset, int count) {
        Input input = null;
        if ((input = inputLocal.get()) == null) {
            input = new Input();
            inputLocal.set(input);
        }
        if (bytes != null) {
            input.setBuffer(bytes, offset, count);
        }
        return input;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(byte[] bytes, int offset, int count) {
        Kryo kryo = getKryo();
        Input input = getInput(bytes, offset, count);
        return (T) kryo.readObjectOrNull(input, ct);
    }

    @Override
    public <T> T deserialize(byte[] bytes) {
        return deserialize(bytes, 0, bytes.length);
    }

    /**
          * 将对象【及类型】序列化为 String
          * 利用了 Base64 编码
          *
          * @param bytes 任意对象
          * @return 序列化后的字符串
          */
    public static  String writeToString(byte[] bytes) {
        
            return new String(Base64.getEncoder().encode(bytes));
        
    }
 /**
      * 将 String 反序列化为原对象
      * 利用了 Base64 编码
      *
      * @param str writeToString 方法序列化后的字符串
      * @return 原对象
      */
  public static byte[] readFromString(String str) {
      return Base64.getDecoder().decode(str);
    }

}