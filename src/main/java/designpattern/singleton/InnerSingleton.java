package designpattern.singleton;

/**
 * 静态内部类模式
 * 静态内部类的特点——初始化操作和外部类是分开的，只有首次调用getInstance()方法时，
 * 虚拟机才加载内部类（SingletonInner.class）并初始化instance，保证对象的唯一性。
 *
 * @author nickbi
 * @date create on 2019/12/2
 */
public class InnerSingleton {
	private InnerSingleton() {
		System.out.println("inner init ");
	}

	private static class SingletonInner {
		private static InnerSingleton instance = new InnerSingleton();
	}

	private static InnerSingleton getInstance() {
		return SingletonInner.instance;
	}
}
