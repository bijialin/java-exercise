package designpattern.singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 懒汉式单例模式，线程不安全
 * 懒汉式是在运行时加载对象的，所以加载该单例类时会比较快，但是获取对象会比较慢。且这样做是线程不安全的，如果想要线程安全，
 * 可以在getInstance()方法加上synchronized 关键词修饰，但这样会让我们付出惨重的效率代价。
 *
 * @author nickbi
 * @date create on 2019/12/2
 */

public class LazySingleton {
	/**
	 * 私有变量
	 */
	private static LazySingleton singleton;

	/**
	 * 构造方法
	 */
	private LazySingleton() {
		System.out.println("lazy init ");
	}

	/**
	 * 实例静态获取方法
	 *
	 * @return LazySingleton
	 */
	public static LazySingleton getInstance() {
		if (null == singleton) {
			singleton = new LazySingleton();
		}
		return singleton;
	}


	/**
	 * 多线程模式下测试懒汉模式是否线程安全
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		/**
		 * 这里我图方便，直接用Executors创建线程池
		 * 阿里巴巴开发手册是不推荐这么做的
		 */
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 20; i++) {
			executorService.execute(() -> System.out.println(Thread.currentThread().getName() + "::" + LazySingleton.getInstance()));
		}
	}

}
