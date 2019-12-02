package designpattern.singleton;

/**
 * 双重检查锁模式
 *
 * @author nickbi
 * @date create on 2019/12/2
 */
public class DoubleCheckSingleton {
	/**
	 * 自我实例化、线程可见
	 */
	private static volatile DoubleCheckSingleton singleton = new DoubleCheckSingleton();

	public DoubleCheckSingleton() {
	}

	public static DoubleCheckSingleton getInstance() {
		return singleton;
	}

}
