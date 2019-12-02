### 1. 单例模式
什么是单例模式？简言之就是确保定义为单例模式的类在程序中有且只有一个实例。单例模式的特点：

+ 1.只有一个实例 （只能有一个对象被创建）
+ 2.自我实例化（类构造器私有）
+ 3.对外提供获取实例的静态方法



### 2.单例模式的实现
常见的单例模式实现方式有五种：
#### 2.1. 懒汉式
懒汉式（一般也称之为 饱汉式），具体代码实现如下：
```
public class Singleton {

    /**
     * 自我实例化
     */
    private static Singleton singleton;

    /**
     * 构造方法私有
     */
    private Singleton() {
        System.out.println("创建单例实例...");
    }

    /**
     * 对外提供获取实例的静态方法
     */
    public static Singleton getInstance() {
        if (null == singleton) {
            singleton = new Singleton();
        }
        return singleton;
    }
}
```

从代码实现中可以看到，实例并不是在一开始就是初始化的，而是在调用 getInstance()方法后才会产生单例，这种模式延迟初始化实例，但它并非是线程安全的。
```
public class SingleTonTest {

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
            executorService.execute(() -> System.out.println(Thread.currentThread().getName() + "::" + Singleton.getInstance()));
        }
    }
}
```

测试结果截图：

懒汉式是在运行时加载对象的，所以加载该单例类时会比较快，但是获取对象会比较慢。且这样做是线程不安全的，如果想要线程安全，可以在getInstance()方法加上synchronized 关键词修饰，但这样会让我们付出惨重的效率代价。
#### 2.2. 饿汉式
提前创建好实例对象，调用效率高，但无法延时加载，容易产生垃圾，线程安全。
```
public class Singleton {

    /**
     * 自我实例化
     */
    private static Singleton singleton = new Singleton();

    /**
     * 构造方法私有
     */
    private Singleton() {
        System.out.println("创建单例实例...");
    }

    /**
     * 对外提供获取实例的静态方法
     */
    public static Singleton getInstance() {
        return singleton;
    }
}
```


#### 2.3. 双重检查锁模式
```
public class Singleton {

    /**
     * 自我实例化，volatile修饰，保证线程间可见
     */
    private volatile static Singleton singleton;

    /**
     * 构造方法私有
     */
    private Singleton() {
        System.out.println("创建单例实例...");
    }

    /**
     * 对外提供获取实例的静态方法
     */
    public static Singleton getInstance() {
        // 第一次检查，避免不必要的实例
        if (singleton == null) {
            // 第二次检查，同步，避免产生多线程的问题
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
```

由于singleton=new Singleton()对象的创建在JVM中可能会进行重排序，在多线程访问下存在风险，使用volatile修饰signleton实例变量，能禁止指令重排，使得对象在多线程间可见，能够有效解决该问题。
双重检查锁定失败的问题并不归咎于 JVM 中的实现 bug，而是归咎于 Java 平台内存模型。内存模型允许所谓的“无序写入”，这也是这些习语失败的一个主要原因
#### 2.4. 静态内部类模式
```
public class Singleton {
    /**
     * 构造方法私有
     */
    private Singleton() {
        System.out.println("创建单例实例...");
    }

    private static class SingletonInner {
        private static Singleton instance = new Singleton();
    }

    private static Singleton getInstance() {
        return SingletonInner.singleton;
    }
}
```

这样写充分利用静态内部类的特点——初始化操作和外部类是分开的，只有首次调用getInstance()方法时，虚拟机才加载内部类（SingletonInner.class）并初始化instance， 保证对象的唯一性。
#### 2.5. 枚举单例模式
```
public enum Singleton {
    INSTANCE 
}
```

感觉异常简单，默认枚举类创建的对象都是单例的，且支持多线程。

### 3.单例模式总结
+ 1.单例模式优点在于：全局只会生成单个实例，所以能够节省系统资源，减少性能开销。然而也正是因为只有单个实例，导致该单例类职责过重，违背了“单一职责原则”，单例类也没有抽象方法，会导致比较难以扩展。
+ 2.以上所有单例模式中，推荐使用静态内部类的实现，非常直观，且保证线程安全。在《Effective Java》中推荐枚举类，但太简单了，导致代码的可读性比较差。
+ 3.单例模式是创建型模式，反序列化时需要重写readResovle()方法，以保证实例唯一。



原博客地址：www.developlee.top

