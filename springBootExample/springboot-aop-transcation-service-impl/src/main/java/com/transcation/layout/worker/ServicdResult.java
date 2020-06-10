package com.transcation.layout.worker;

/**
 * 执行结果
 */
public class ServicdResult<V> {
    /**
     * 执行的结果
     */
    private V result;
    /**
     * 结果状态
     */
    private ResultState resultState;
    private Exception ex;

    public ServicdResult(V result, ResultState resultState) {
        this(result, resultState, null);
    }

    public ServicdResult(V result, ResultState resultState, Exception ex) {
        this.result = result;
        this.resultState = resultState;
        this.ex = ex;
    }

    public static <V> ServicdResult<V> defaultResult() {
        return new ServicdResult<>(null, ResultState.DEFAULT);
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
