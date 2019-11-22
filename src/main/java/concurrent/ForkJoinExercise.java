package concurrent;

import java.awt.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author nickbi
 * @date create on 2019/11/22
 */
public class ForkJoinExercise extends RecursiveTask<Integer> {

	private final int threshold = 5;
	private int first;
	private int last;

	public ForkJoinExercise(int first, int last) {
		this.first = first;
		this.last = last;
	}




	@Override
	protected Integer compute() {
		int result = 0;
		if (last - first <= threshold) {
			for (int i = first; i <= last; i++) {
				result += i;
			}
		} else {
			int middle = first + (last - first) / 2;
			final ForkJoinExercise leftTask = new ForkJoinExercise(first, middle);
			final ForkJoinExercise rightTask = new ForkJoinExercise(middle + 1, last);
			leftTask.fork();
			rightTask.fork();
			result = leftTask.join() + rightTask.join();
		}
		return result;

	}

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		final ForkJoinExercise exercise = new ForkJoinExercise(1, 10000);
		final ForkJoinPool forkJoinPool = new ForkJoinPool();
		final ForkJoinTask<Integer> submit = forkJoinPool.submit(exercise);
		System.out.println(submit.get());
	}
}
