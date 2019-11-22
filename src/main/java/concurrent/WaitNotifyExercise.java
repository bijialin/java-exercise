package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * wait notify exercise
 *
 * @author nickbi
 * @date create on 2019/11/22
 */
public class WaitNotifyExercise {
	int a = 1;

	public static void main(String[] args) {
		final WaitNotifyExercise exercise = new WaitNotifyExercise();
		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.execute(() -> exercise.startWait(1));
		executorService.execute(() -> exercise.startWait(2));
		executorService.execute(() -> exercise.endToNotify());
		//unlock
		executorService.execute(() -> exercise.unLockStartWait(2));
		executorService.execute(() -> exercise.unLockEndToNotify());
	}

	private synchronized void startWait(final int taskNumber) {
		try {
			a++;
			System.out.println(String.format("task %d a value: %d", taskNumber, a));
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private synchronized void endToNotify() {
		System.out.println("end to notify");
		notifyAll();
		System.out.println("a value: " + a);
	}

	private synchronized void unLockStartWait(final int taskNumber) {
		try {
			a++;
			System.out.println(String.format("task %d a value: %d", taskNumber, a));
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private synchronized void unLockEndToNotify() {
		System.out.println("end to notify");
		notifyAll();
		System.out.println("a value: " + a);
	}
}
