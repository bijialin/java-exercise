package concurrent;

import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * Executor 管理多个异步任务的执行，而无需程序员显式地管理线程的生命周期。这里的异步是指多个任务的执行互不干扰，不需要进行同步操作。
 * <p>
 * 主要有三种 Executor：
 * <p>
 * CachedThreadPool：一个任务创建一个线程；
 * FixedThreadPool：所有任务只能使用固定大小的线程；
 * SingleThreadExecutor：相当于大小为 1 的 FixedThreadPool。
 *
 * @author nickbi
 * @date create on 2019/11/22
 */
public class ExecutorServiceExercise {

	public static void main(String[] args) {
		ExecutorServiceExercise.exeCachedThreadPool();
		ExecutorServiceExercise.exeFixedThreadPool();
	}


	private static void exeCachedThreadPool() {
		final ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.execute(() -> {
			System.out.println("cachedThreadPool start");
			IntStream.range(0, 5).forEach(i -> System.out.println(String.format("index: %d,thread: %s", i,
					Thread.currentThread())));
			;
			System.out.println("cachedThreadPool end");
		});
		executorService.shutdown();
	}

	private static void exeFixedThreadPool() {
		final ExecutorService executorService = Executors.newFixedThreadPool(2);
		executorService.execute(() -> {
			System.out.println("fixedThreadPool start ");
			IntStream.range(0, 5).forEach(i -> System.out.println(String.format("index: %d,thread: %s", i,
					Thread.currentThread())));
			System.out.println("fixedThreadPool end");
		});
		executorService.shutdown();
	}

}
