package com.transcation.layout.service;

/**
 * 执行结果
 */
public class ServiceInstanceResult<V> {
    /**
     * 执行的结果
     */
    private V result;
    /**
     * 结果状态
     */
    private ResultState resultState;
    private Exception ex;

    public ServiceInstanceResult(V result, ResultState resultState) {
        this(result, resultState, null);
    }

    public ServiceInstanceResult(V result, ResultState resultState, Exception ex) {
        this.result = result;
        this.resultState = resultState;
        this.ex = ex;
    }

    public static <V> ServiceInstanceResult<V> defaultResult() {
        return new ServiceInstanceResult<>(null, ResultState.DEFAULT);
    }

    @Override
    public String toString() {
        return "serviceResult{" +
                "result=" + result +
                ", resultState=" + resultState +
                ", ex=" + ex +
                '}';
    }

    public Exception getEx() {
        return ex;
    }

    public void setEx(Exception ex) {
        this.ex = ex;
    }

    public V getResult() {
        return result;
    }

    public void setResult(V result) {
        this.result = result;
    }

    public ResultState getResultState() {
        return resultState;
    }

    public void setResultState(ResultState resultState) {
        this.resultState = resultState;
    }
}
