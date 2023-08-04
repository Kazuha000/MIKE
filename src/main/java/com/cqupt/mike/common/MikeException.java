package com.cqupt.mike.common;

public class MikeException extends RuntimeException {

    public MikeException() {
    }

    public MikeException(String message) {
        super(message);
    }

    /**
     * 丢出一个异常
     *
     * @param message
     */
    public static void fail(String message) {
        throw new MikeException(message);
    }

}
