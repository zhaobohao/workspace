package com.grpc.client;

import com.grpc.client.service.HelloService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeoutException;

public class MyClient {
    public static boolean done;

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        try {
            Semaphore semaphore = new Semaphore(8);
            for (int i = 0; i < 100; i++) {

                semaphore.acquire();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HelloService service = new HelloService();
                        try {
                            service.hello("tomcate ,,,,,");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (TimeoutException e) {
                            e.printStackTrace();
                        }
                        semaphore.release();
                    }
                }).start();
                System.out.println("i----------------------=" + i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();

        }

    }
}
