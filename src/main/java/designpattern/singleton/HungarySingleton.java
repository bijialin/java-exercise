package designpattern.singleton;

/**
 * 饿汉式单例模式
 *
 * @author nickbi
 * @date create on 2019/12/2
 */
public class HungarySingleton {
	/**
	 * 自我实例化对象
	 */
	private static HungarySingleton singleton = new HungarySingleton();

	public HungarySingleton() {
	}

	public static HungarySingleton getInstance() {
		return singleton;
	}
}
