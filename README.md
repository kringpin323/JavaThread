JavaThread，Java线程（第三版）
==========
此 Project 是Java线程第三版的课本实例，添加了个人阅读书籍和代码的注释

在2014年8月到2014年10月期间，为了了解更多的Java线程知识，认真阅读了本书

记录收获如下：

1. 生成线程的主要方式，底层接口（Runnable，Future，Callable），Thread的生命周期
2. 线程间数据同步机制：从 synchronized volatile 到 lock， condition， 以及 atomic变量
3. 同步技巧：Atomic变量和Thread局部变量
4. 死锁检测（引用树递归遍历），Lock饥饿（这个需要在看一次）
5. 使用BlockingQueue处理消费者和生产者问题，Collection Class的race condition
6. 线程调度优先级
7. 线程池的优点
8. Thread 和 IO ，传统IO服务器，non-Blocking IO 服务器（也是看了这章更加坚定我去看Java IO 和 Java NIO 的决心）
9. Thread stack stack-frame heap 之间的关系
10. 多处理器计算机的并行化循环------一些并行化技巧

总结：有所收获

By David_Lin

2014/10/29