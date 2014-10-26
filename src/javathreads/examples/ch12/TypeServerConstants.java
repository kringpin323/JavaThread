package javathreads.examples.ch12;

// 信息类，几乎大多数的比较大型的框架或者java应用程序都维护这样一个信息类
// 在 Spring 中 BeanDefinition 维护一个xml的依赖关系
// H2 中 数据库引擎启动相关信息也会被维护其起来
public class TypeServerConstants {
    public final static byte WELCOME = 0;
    public final static byte GET_STRING_REQUEST = 1;
    public final static byte GET_STRING_RESPONSE = 2;
}
