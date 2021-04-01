package com.jaemisseo.proving;

import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**************************************************
 *
 * SimpleDateFormat never
 *	- It is not thread safe.
 *  - Never use DateFormat, SimpleDateFormat, Date, and Calendar classes. These terrible classes are all legacy now. They were supplanted years ago by the modern java.time classes defined in JSR 310. The java.time classes are thread-safe by design, using immutable objects
 *  - this hasn't been fixed in JDK8 per se. but JDK8 introduces the new java.time package, including DateTimeFormatter which is threadsafe.
 *
 **************************************************/
public class SimpleDateFormatIsNotThreadSafeTest {

	final static int threadPoolSize = 50;
	final static int threadCount = 10000;

	@Test
	public void SimpleDateFormatIsNotSafeTest() {
		final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

		final List<Class<? extends Exception>> occurableExceptions = Arrays.asList(NumberFormatException.class, ArrayIndexOutOfBoundsException.class);

		//Run Threads
		assertThrowWithThreads(occurableExceptions, threadPoolSize, threadCount, (count) -> {
			return format.parse("20150630");
		});
	}

	@Test
	public void SimpleDateFormatIsSafeTest() throws InterruptedException, ExecutionException {
		//Run Threads
		List<Date> results = runThreads(threadPoolSize, threadCount, (count) -> {
			return new SimpleDateFormat("yyyyMMdd").parse("20150630"); // 정상 동작시키기 위해서는 이와 같이 사용해야 함
		});
		assert results.size() == threadCount;
	}

	@Test
	public void synchronizedSimpleDateFormat() throws InterruptedException, ExecutionException {
		final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

		//Run Threads
		List<Date> results = runThreads(threadPoolSize, threadCount, (count) -> {
			Date result;
			synchronized(format) {
				result = format.parse("20150630");
			}
			return result;
		});

		assert results.size() == threadCount;
		assert results.stream().allMatch( (it) -> it instanceof Date );
	}

	@Test
	public void getDataInstance() throws InterruptedException, ExecutionException {
		//Run Threads
		List<Date> results = runThreads(threadPoolSize, threadCount, (count) -> {
			return SimpleDateFormat.getDateInstance(DateFormat.MEDIUM).parse("2015. 06. 30");
		});

		assert results.size() == threadCount;
		assert results.stream().allMatch( (it) -> it instanceof Date );
	}


	@Test
	public void makeThreadSafeTaskAsThreadLocal() throws InterruptedException, ExecutionException {
		//Setup threadLocal
		ThreadLocal<DateFormat> format = new ThreadLocal<DateFormat> () {
			@Override
			protected DateFormat initialValue() {
				return new SimpleDateFormat("yyyyMMdd");
			}
		};

		//Run Threads
		List<Date> results = runThreads(threadPoolSize, threadCount, (count) -> {
			return format.get().parse("20150630");
		});

		assert results.size() == threadCount;
		assert results.stream().allMatch( (it) -> it instanceof Date );
	}



	@FunctionalInterface
	public interface FunctionWithException<T, R> {
		R apply(T t) throws Exception;
	}

	private <T> List<T> runThreads(int threadPoolSize, int threadCount, FunctionWithException<Integer, T> fwe) throws InterruptedException, ExecutionException {
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

	private void assertThrowWithThreads(Class<? extends Exception> ocurrableExceptions, int threadPoolSize, int threadCount, FunctionWithException<Integer, ?> fwe) throws AssertionError {
		assertThrowWithThreads(Arrays.asList(ocurrableExceptions), threadPoolSize, threadCount, fwe);
	}
	private void assertThrowWithThreads(List<Class<? extends Exception>> ocurrableExceptions, int threadPoolSize, int threadCount, FunctionWithException<Integer, ?> fwe) throws AssertionError {
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