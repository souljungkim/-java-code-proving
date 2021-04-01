package com.jaemisseo.test.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class JaemisseoTestUtil {

    @FunctionalInterface
    protected interface FunctionWithException<T, R> {
        R apply(T t) throws Exception;
    }

    protected <T> List<T> runThreads(int threadPoolSize, int threadCount, FunctionWithException<Integer, T> fwe) throws InterruptedException, ExecutionException {
        ExecutorService exec = Executors.newFixedThreadPool(threadPoolSize);
        List<Future<T>> futures = new ArrayList<Future<T>>();
        List<T> results = new ArrayList<T>();
        try {
            // Count만큼 수행
            for (int i = 0; i< threadCount; i++) {
                final int count = i + 1;
                Callable<T> newCallable = new Callable<T>(){
                    @Override
                    public T call() throws Exception {
                        System.out.println("[" +count+ "] " + Thread.currentThread().getName());
                        return fwe.apply(count);
                    }
                };
                futures.add( exec.submit(newCallable) );
            }
            // 결과 출력
            for (Future<T> future : futures) {
                results.add( future.get() );
            }

        } catch (InterruptedException ie) {
            throw ie;
        } catch (ExecutionException ee) {
            throw ee;
        } finally {
            exec.shutdown();
        }
        return results;
    }



    protected void assertThrowWithThreads(Class<? extends Exception> ocurrableExceptions, int threadPoolSize, int threadCount, FunctionWithException<Integer, ?> fwe) throws AssertionError {
        List<Class<? extends Exception>> occurableExceptions = Arrays.asList(ocurrableExceptions);
        assertThrowWithThreads(occurableExceptions, threadPoolSize, threadCount, fwe);
    }

    protected void assertThrowWithThreads(List<Class<? extends Exception>> ocurrableExceptions, int threadPoolSize, int threadCount, FunctionWithException<Integer, ?> fwe) throws AssertionError {
        try {
            runThreads(threadPoolSize, threadCount, fwe);

        }catch (ExecutionException ee) {
            ee.printStackTrace();
            boolean anyMatched = ocurrableExceptions.stream().anyMatch( (it) -> ee.getCause().getClass().isAssignableFrom(it) );
            if (!anyMatched)
                throw new AssertionError();

        }catch(Exception e){
            e.printStackTrace();
            boolean anyMatched = ocurrableExceptions.stream().anyMatch( (it) -> e.getCause().getClass().isAssignableFrom(it) );
            if (!anyMatched)
                throw new AssertionError();
        }
    }

}
