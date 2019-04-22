# Java_Interview_Study

## 作者：冰红茶  
## 参考书籍：《Java面试宝典》第4版 《剑指Offer》
    
------    
    
之前一直再看类似于Java编程思想、核心技术等书籍，但是理论终归是理论，还需要看看市场上所需要掌握的技术到底是什么。所以后面将会以Java面试作为主线，加强面试能力和算法能力，希望有一个好的结果^_ ^

## 目录
## [一、Java程序设计基本概念](#1)
### [1.1 JVM](#1.1)
### [1.2 类型转换、i++和运算符](#1.2)
### [1.3 异常](#1.3)
## [二、传递与引用和循环](#2)
### [2.1 传值与引用](#2.1)
### [2.2 静态变量和私有变量](#2.2)
### [2.3 输入/输出流和序列化](#2.3)
### [2.4 典型的递归问题](#2.4)
## [三、Java内存管理](#3)
### [3.1 典型的递归问题](#3.1)
### [2.2 静态变量和私有变量](#3.2)
### [2.3 输入/输出流和序列化](#3.3)

        
------      
        
<h2 id='1'>一、Java程序设计基本概念</h2>
<h3 id='1.1'>1.1 JVM</h3>  
        
#### 1) ClassLoader
> - ClassLoader在JVM运行的时候加载Java核心的API和用户自定义的类；
>> - bootstrap classloader 启动类加载器
>> - classloader 用户定义加载器
>>> - ExtClassLoader 用来加载Java拓展API，也就是/lib/ext
>>> - AppClassLoader 用来加载用户自定义类
> - 加载流程：当运行一个程序的时候，JVM启动，运行bootstrap classloader，该ClassLoader加载核心API（ExtClassLoader和AppClassLoader）也在此时候被加载，然后调用ExtClassLoader加载拓展API，最后AppClassLoader加载CLASSPATH目录下定义的Class
> - JVM加载类的三个步骤
>> - 装载 找到相应的class，读入JVM
>> - 连接 分成三个阶段：验证class是否符合规格；准备，就是为类变量分配内存的同时设置默认初始值；解释，这里的解释是指根据类中的符号引用查找相应的实体，再把符号引用替换成一个直接引用的过程。
>> - 初始化 class文件的初始化
> - 类加载的这种关系称为双亲委派模式，需要注意的是他们之间不是继承关系，而是组合关系，在执行类加载的动作时，首先都是交给父类去加载，如果父类无法加载再交给子类去完成，直到调用用户自定义的类加载器去加载，如果全部都无法加载，就会抛出ClassNotFoundException。[出处](https://blog.csdn.net/caomiao2006/article/details/47735245)
> - 父类委托 超类被加载后，子类的Classloader没必要再加载一次，如果没有超类（即自己就是超类）时，就使用bootstrap classloader进行加载。原因有二：
>> - 这样做可以避免重复加载
>> - 考虑到安全的因素。 如果不采用这种方式，用户就可以自己加载String类来动态代替Java的核心API定义的类型，这样会存在很大的安全隐患。这由于采用了父类委托，String已经在启动的时候被加载，所以用户自定义类无法加载一个自定义的ClassLoader
>>>>>> ![图1-1 Java类加载器.png](https://github.com/hblvsjtu/Java_Interview_Study/blob/master/picture/%E5%9B%BE1-1%20Java%E7%B1%BB%E5%8A%A0%E8%BD%BD%E5%99%A8.png?raw=true)
                
> - 一些重要的方法
>> - loadClass 
                
                class loadClass(String name, boolean resolve)
>> - defineClass 将原始字节组成的数组转换成class对象，并且是final的无法被覆盖
>> - findSystemClass 从本地文件中寻找类文件，如果存在，则使用defineClass转换称class对象
                
                public class findClass(String name) {
                    byte[] data = loadClassData(name);
                    return defineClass(name, data, 0, data.length);
                }
>> - findClass Java的自定义加载器 loadClass默认实现调用这个新方法，寻找所有Classload的特殊代码。findClass（）用于写类加载逻辑、loadClass（）方法的逻辑里如果父类加载器加载失败则会调用自己的findClass（）方法完成加载，保证了双亲委派规则。如果不想打破双亲委派模型，那么只需要重写findClass方法即可。如果想打破双亲委派模型，那么就重写整个loadClass方法。[cqc__c的CSDN](https://blog.csdn.net/cqc__c/article/details/81583877)
>> - forName(name, initialize, loader)
                
                Class.forNmae("something", true, CALLCLASS.class.getClassLoader());
#### 2) Unicode
> - Java中字符只以一种形式存在，那就是Unicode
                
                char han = '永'；
                System.out.printf("%x", (short)han);

                // 0x6c38
> - JVM约定字符只分为两个部分，JVM内部和OS文件系统的，在JVM内部统一使用Unicode，当字符被输入输出的时候，就会进行编码转换。也就是说，编码转换只会发生在边界的地方。而I/O也分两大阵型，一个是面向字符，另一个是面向字节
                

面向类型 | 特点 |  使用范围  
-|-|-
字节 | 保证系统中文件的二进制内容和读入JVM内部的二进制内容一致，不进行编码转换 | 视频或者音频文件 InputStream和OutputStream类为后缀的类|
字符 | 保证系统中文件的字符和读入内存的字符一致，隐式地使用系统默认的编码方式做编码转换（而且这种编码方式无法自己选择） | 以Reader和Write为后缀的类 |
字节-字符 | Java中能指定转换编码的地方也就在字符和字节转换的地方 | InputStreamReader类和OutputStreamWriter类 |
                
        
<h3 id='1.2'>1.2 类型转换、i++和运算符</h3>  
        
#### 1) 类型转换
> - 数字和字节相加，会将数字进行隐式转换成字符
> - 数字和字节相减，会在编译的时候报错
> - 数字和字符相加，会将字符进行隐式转换成Ascii码再进行相加减
> - 系统默认的浮点类型是double,所以如果是float类型需要在数字后加上f标记进行转换
> - short后面不需要加S，会执行自动装箱
> - char后面也不需要加c,char向高级类型转换时会转化成Ascii码
> - 高级类型转低级类型需要强行转换，而且只能是针对数值类型而非包装类进行转换，否则会出错
                
                package Interview;

                /**
                 * @author LvHongbin
                 *
                 */
                public class Go {
                    
                    public static void main(String[] arg) {
                        System.out.println(1 + "0");
                        System.out.println("1" + 0);
                        System.out.println(1 + '0');
                        System.out.println('1' + 0);
                    }
                }

                // 控制台
                10
                10
                49
                49
#### 2) 包装类
> - Character、Boolean、Integer、Long、Float、Double共6个
> - String和Data本身就是类，所以没有包装类这一说法
> - 过渡型类型转换
                
                float f = 100.0f;
                Float F = new Float(f);
                double d = F.doubleValue(); 
> - 
#### 3) 中间缓存机制
> - 适用与自增的变量同时出现在等号左右两边
                
                int j1 = 0 ;
                int j2 = 0 ;
                int j3 = 0 ;
                for(int i =0; i<100; i++) {
                    j1 = j1++;
                    j2 = ++j2;
                }
                j3 = j3++ + j3++ + j3++ + ++j3;
                System.out.printf("j1 = %d, j2 = %d, j3 = %d", j1, j2, j3);

                // 控制台
                j1 = 0, j2 = 100, j3 = 7

                
                j1 = j1++;
                等效于
                temp = j1;
                j1 = j1 + 1;
                j1 = temp;

                j2 = ++j2;
                等效于
                j2= j2 + 1;
                temp = j2;
                j2 = temp;

                j3 = 0 + 1 + 2 + 4 = 7
#### 4) 三目运算符
> - 返回类型会进行自动匹配，而匹配的类型跟问号后的值相同
                
                System.out.println("value = "  + (true == false ? 10.9 : 9));

                // 控制台
                value = 9.0

#### 5) 移位操作符
> - 移位的位数先要进行模32运算
                
                System.out.println("value = "  + (32 >> 33));

                // 控制台
                value = 16

               
        
<h3 id='1.3'>1.3 异常</h3>  
        
#### 1) 异常类型
> - Throwable类衍生Error类和Exception类
>> - Error类程序无法靠自身解决，所以Java不作处理，所以是unchecked exception
>> - Exception也分为运行时错误（如被0除、空字符串、空句柄）和非运行时错误（如IOException），对于运行时错误，由于编译器无法在静态检查中检查出，所以是unchecked exception
> - Java编译器要求捕获或者声明所有非运行时错误
> - 针对
#### 2) 异常关键字
> - try、catch、finally分别有自己独立的代码块，相互之间不能访问，如果需要访问则可以把变量放在关键字代码块之前
> - throw 用在方法体内部
> - throws 用在方法体外部，用于声明可能会抛出的异常，里面可以不写catch
#### 3) finally
> - 即便try()代码块内部有return语句，如果存在finally关键字，那么finally里面的方法块会在return前执行
                
                /**
                 * 
                 */
                package Interview;

                /**
                 * @author LvHongbin
                 *
                 */
                public class Go {
                    
                    public static void main(String[] arg) {
                        System.out.println(fin());
                    }
                    
                    private static int fin() {
                        try {
                            System.out.println("I am try");
                            return "I am return";
                        }finally {
                            System.out.println("I am finally");
                        }
                    }
                }

                // 控制台
                I am try
                I am finally
                I am return

        
------      
        
<h2 id='2'>二、传递与引用</h2>
<h3 id='2.1'>2.1 传值与传引用</h3>  
        
#### 1) 规律
> - 不管Java参数类型是什么，一律传递的是参数的副本
> - 如果参数是值，那么传得就是值的副本
> - 如果参数是引用，那么传得就是引用的副本，也就是“被复制的钥匙”，这就意味着不能修改“原来的钥匙”
> - 这里跟C++不同，C++在传递引用的时候是真的传递引用，而Java是传递引用的副本。其实两者在方法里都可以改掉被引用的内容。
        
<h3 id='2.2'>2.2 静态变量和私有变量</h3>  
        
#### 1) 初始化
> - 定义在类中的变量会被赋予一个默认的值
#### 2) 静态方法
> - 静态方法访问外部变量或者方法时只能访问静态变量或者静态方法，否则会出错
        
<h3 id='2.3'>2.3 输入/输出流和序列化</h3>  
        
#### 1) 读入有很多数据的大文件
> - new StringBuffer(new InputStreamReader(new FileInputStream("file.name")));
#### 2) 从键盘输入数字的两种方法
> - 使用Scanner类
                
                Scanner scan = new Scanner(System.in);
                System.out.println("请输入第一个数字：");
                int i1 = scan.nextInt();
                System.out.println("请输入第二个数字：");
                int i2 = scan.nextInt();
                System.out.println("两数之和：" + (i1 + i2));
                scan.close();

                // 控制台
                请输入第一个数字：
                1
                请输入第二个数字：
                2
                两数之和：3

> - 使用BufferedReader类和InputStreamReader类
                
                int i3 = 0;
                int i4 = 0;
                BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
                try {
                    System.out.println("请输入第一个数字：");
                    i3 = Integer.parseInt(buffer.readLine());
                    System.out.println("请输入第二个数字：");
                    i4 = Integer.parseInt(buffer.readLine());   
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }finally {
                    System.out.println("两数之和：" + (i3 + i4));
                    try {
                        buffer.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }   

                // 控制台
                请输入第一个数字：
                1
                请输入第二个数字：
                2
                两数之和：3
> - StringBuffer是一个处理字符串的类，而BufferReader是一个I/O流
> - 字符流和字节流分类
>>>>>> ![图1-2 IO流分类.png](https://github.com/hblvsjtu/Java_Interview_Study/blob/master/picture/%E5%9B%BE1-2%20IO%E6%B5%81%E5%88%86%E7%B1%BB.png?raw=true)
#### 3) 序列化
> - Java的“对象序列化”能将一个实现了Serializable接口的对象转换成一组byte，这样日后要用的时候就可以把这些byte数据恢复成对象
> - 使用ObjectInputStream和ObjectOutputStream类
> - 调用readObject()方法和writeObject()方法
> - 主要是用于远程传递对象
        
<h3 id='2.4'>2.4 典型的递归问题</h3>  
        
#### 1) 斐波那契数列
                
                private static int fib(int i) {
                    return i<3 ? 1 : fib(i-1) + fib(i-2);
                }

        
------      
        
<h2 id='3'>三、Java内存管理</h2>
<h3 id='3.1'>3.1 典型的递归问题</h3>  
        
#### 1) 斐波那契数列
> - 