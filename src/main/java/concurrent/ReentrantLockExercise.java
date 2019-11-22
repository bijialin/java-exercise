package concurrent;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @author nickbi
 * @date create on 2019/11/22
 */
public class ReentrantLockExercise {
	final ReentrantLock lock = new ReentrantLock();

	public static void main(String[] args) {
		final ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 5, 60, TimeUnit.SECONDS,
				new SynchronousQueue<>(), r -> {
			Thread thread = new Thread(r);
			thread.setName("synchronized exercise");
			return thread;
		});
		final ReentrantLockExercise exercise = new ReentrantLockExercise();

		executor.execute(() -> exercise.reentrantLockBlock(1));
		executor.execute(() -> exercise.reentrantLockBlock(2));
		executor.execute(() -> {
			try {
				exercise.reentrantLockInterrupt(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

	}

	private void reentrantLockBlock(final int taskNumber) {
		lock.lock();
		IntStream.range(0, 10).forEach(i -> System.out.println(String.format("thread task: %d, index: %d",
				taskNumber, i)));
		lock.unlock();
	}

	/**
	 * ReentrantLock 可中断
	 *
	 * @param taskNumber
	 * @throws InterruptedException
	 */
	private void reentrantLockInterrupt(final int taskNumber) throws InterruptedException {
		lock.lock();
		for (int i = 0; i < 5; i++) {
			System.out.println(String.format("thread task: %d, index: %d",
					taskNumber, i));
			if (3 == i) {
				lock.lockInterruptibly();
			}

		}
		lock.unlock();
	}
}
