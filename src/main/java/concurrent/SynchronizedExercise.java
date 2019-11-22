package concurrent;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * jvm底层实现的互斥锁
 *
 * @author nickbi
 * @date create on 2019/11/22
 */
public class SynchronizedExercise {
	public static void main(String[] args) {
		final SynchronizedExercise synchronizedExercise = new SynchronizedExercise();
		final ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 5, 60, TimeUnit.SECONDS,
				new SynchronousQueue<>(), r -> {
			Thread thread = new Thread(r);
			thread.setName("synchronized exercise");
			return thread;
		});
		executor.execute(synchronizedExercise::printNumber);
		executor.execute(() -> synchronizedExercise.synchronizedWithBlock(1));
		executor.execute(() -> synchronizedExercise.synchronizedWithBlock(2));
		executor.execute(synchronizedExercise::synchronizedInterrupt);
		executor.shutdown();
	}

	private void synchronizedWithBlock(final int threadNum) {
		synchronized (this) {
			IntStream.range(0, 10).forEach(i -> System.out.println(String.format("thread task: %d, index: %d",
					threadNum, i)));
		}
	}

	private void printNumber() {
		IntStream.range(0, 10).forEach(i -> {
			System.out.println("unlock print index: " + i);
			if (i % 2 == 0) {
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		});
	}

	/**
	 * synchronize 无法中断
	 */
	private void synchronizedInterrupt() {
		synchronized (this) {
			for (int i = 0; i < 5; i++) {
				System.out.println("interrupt index: " + i);
				if (3 == i) {
					Thread.interrupted();
				}
			}

		}
	}

}
