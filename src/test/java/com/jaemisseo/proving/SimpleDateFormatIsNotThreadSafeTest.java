package com.jaemisseo.proving;

import com.jaemisseo.test.util.JaemisseoTestUtil;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**************************************************
 *
 * SimpleDateFormat is not thread safe.
 *
 *	- Someone says: "It is not thread safe."
 *  - Someone says: "Never use DateFormat, SimpleDateFormat, Date, and Calendar classes. These terrible classes are all legacy now. They were supplanted years ago by the modern java.time classes defined in JSR 310. The java.time classes are thread-safe by design, using immutable objects"
 *  - Someone says: "this hasn't been fixed in JDK8 per se. but JDK8 introduces the new java.time package, including DateTimeFormatter which is threadsafe."
 *
 **************************************************/
public class SimpleDateFormatIsNotThreadSafeTest extends JaemisseoTestUtil {

	final static int threadPoolSize = 50;
	final static int threadCount = 10000;


	/**************************************************
	 *
	 * [Not Thread Safe] case
	 * 		- All of threads use only one instance
	 *
	 **************************************************/
	@Test
	public void not_threadSafe() {
		//Define - only one instance
		final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

		//Define - occurable exceptions
		final List<Class<? extends Exception>> occurableExceptions = Arrays.asList(
				NumberFormatException.class,
				ArrayIndexOutOfBoundsException.class
		);

		//Assert - Throw during running threads
		assertThrowWithThreads(occurableExceptions, threadPoolSize, threadCount, (count) -> {
			return format.parse("20150630");
		});
	}



	/**************************************************
	 *
	 * [Thread Safe] case
	 *		- use new everytime
	 *		- use synchronized
	 *		- use method - SimpleDateFormat.getDateInstance(..)
	 *		- use threadLocal
	 *
	 **************************************************/
	@Test
	public void threadSafe_every_new_instance() throws InterruptedException, ExecutionException {
		//Run Threads
		List<Date> results = runThreads(threadPoolSize, threadCount, (count) -> {
			return new SimpleDateFormat("yyyyMMdd").parse("20150630"); // 정상 동작시키기 위해서는 이와 같이 사용해야 함
		});

		//Assert
		assert results.size() == threadCount;
		assert results.stream().allMatch( (it) -> it instanceof Date );
	}

	@Test
	public void threadSafe_synchronized() throws InterruptedException, ExecutionException {
		//Define - only one instance
		final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

		//Run Threads
		List<Date> results = runThreads(threadPoolSize, threadCount, (count) -> {
			Date result;
			synchronized(format) {
				result = format.parse("20150630");
			}
			return result;
		});

		//Assert
		assert results.size() == threadCount;
		assert results.stream().allMatch( (it) -> it instanceof Date );
	}

	@Test
	public void threadSafe_getDateInstance() throws InterruptedException, ExecutionException {
		//Run Threads
		List<Date> results = runThreads(threadPoolSize, threadCount, (count) -> {
			return SimpleDateFormat.getDateInstance(DateFormat.MEDIUM).parse("2015. 06. 30");
		});

		//Assert
		assert results.size() == threadCount;
		assert results.stream().allMatch( (it) -> it instanceof Date );
	}


	@Test
	public void threadSafe_threadLocal() throws InterruptedException, ExecutionException {
		//Setup threadLocal
		ThreadLocal<DateFormat> format = new ThreadLocal<DateFormat> () {
			@Override protected DateFormat initialValue() {
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

}