# Java_Interview_Study

## 作者：冰红茶  
## 参考书籍：《Java面试宝典》第4版
    
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
### [3.2 clone](#3.2)
### [3.3 输入/输出流和序列化](#3.3)
## [四、面向对象](#3)
### [4.1 类和对象](#4.1)
### [4.2 集合类](#4.2)
### [4.3 构造函数](#4.3)
### [4.4 多态](#4.4)
### [4.5 继承](#4.5)
## [五、字符串](#5)
### [5.1 基础问题](#5.1)
### [5.2 string和StringBuffer和StringBuilder比较](#5.2)
### [5.3 字符串](#5.3)
## [六、操作系统、数据库和网络](#6)
### [6.1 操作系统](#6.1)
### [6.2 数据库](#6.2)
### [6.3 网络](#6.3)

        
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
<h3 id='3.1'>3.1 垃圾收集</h3>  
        
#### 1) Java垃圾收集机制
> - 优势：Java不像C和C++，不要求程序员显式地分配内存，释放内存。避免很多潜在的问题，Java在创建对象时会自动分配内存，并当该对象的引用不存在时释放这块内存
> - 工作机制：Java使用对象表来将软指针映射为对象的引用。之所以称为“软指针”，因为这些指针并不是直接指向对象，而是指向对象的引用。使用软指针，Java的垃圾收集器可以以单独的线程在后台存在，并依次检查每一个对象，通过更改对象表项来标记对象、移除对象、移动对象或者检查对象。
> - 调用System类中的静态gc()方法可以运行垃圾收集器，但不能保证立即回收指定对象，只是向JVM发出回收垃圾的申请而已。具体的原理请参考[深入理解Java虚拟机]()
> - 调用protected void finalize() throws Throwable或者进行重写。在finalize()方法返回之后，对象消失，垃圾收集开始执行。属于一种强制执行垃圾回收的方法。
> - 判断一块内存空间是否符合垃圾收集器收集的标准，只有两条原则
>> - 给对象赋予了null，而且再也没有被调用
>> - 给对象赋予了新值，即重新分配了内存，那么旧的内存可以被回收
> - 养成良好的编程习惯，让引用变量退出活动域后，自动设置为null，暗示垃圾手机器来收集该对象。注意，局部变量不再使用的时候，没有必要显式设置为null，对于这些变量的引用将随着方法的退出而自动清除。
#### 2) Java中的内存泄露
> - 在Java中，内存泄露就是存在一些被分配的对象，这些对象有两个特点：
>> - 对象是可达的，即在有向图中，存在通路可以与其相连
>> - 对象是无用的，即程序以后不会再使用这些对象
> - 本质上是占用着内存却不会再被使用的对象
>>>>>> ![图3-1 Java内存分配.png](https://github.com/hblvsjtu/Java_Interview_Study/blob/master/picture/%E5%9B%BE3-1%20Java%E5%86%85%E5%AD%98%E5%88%86%E9%85%8D.png?raw=true)
<h3 id='3.2'>3.2 clone</h3>  
        
#### 1) 特点
> - clone()是Object的一个方法，任何类都自动继承拥有这个类
> - 但是继承而来的clone()方法类不能直接调用，需要显式添加“implements Cloneable”作为标记
> - “implements Cloneable”只是起一个标记的作用，不需要重写或者覆盖clone()方法，这是由Javac或者Java决定的
#### 2) 检验类型
> - clone()方法对对象是否属于cloneable类型是要进行检验
> - 证据就是类必须实现Cloneable接口，但是这个接口的实现只是作为标记，不起实质上的作用
#### 3) 深拷贝的定义
> - x.clone() != x
> - x.clone().getClass() == x.getClass()
> - x.equal(x.clone()) == true;

        
------      
        
<h2 id='4'>四、面向对象</h2>
<h3 id='4.1'>4.1 类和对象</h3>  
        
#### 1) equal
> - 貌似只要是不同的对象就不相等
> - 除非你重写equal方法
                
                Go go1 = new Go();
                Go go2 = new Go();
                Object go3 = new Go();
                System.out.println(go1 == go2);
                System.out.println(go1.equals(go2));
                System.out.println(go1.equals(go1.clone()));
                System.out.println(go2 == go3);
                System.out.println(go3.equals(go2));
                System.out.println(go2.equals(go3));

                // 控制台
                false
                false
                false
                false
                false
                false
#### 2) 创建对象的几种方式
> - new关键字
> - clone()方法
> - 反序列化 ObjectInputStream()
> - 运用反射的方法，调用java.lang.Class或者java.lang.reflect.constructor类
#### 3) 嵌套类innner class
> - 静态内部类意味着：
>> - 创建一个static内部类的对象，不需要一个外部类对象
>> - 不能从一个static内部类对象中访问外部类对象
> - 匿名内部类不能继承其他类，但一个内部类可以作为一个接口，有另一个内部类实现
                
<h3 id='4.2'>4.2 集合类</h3>  
        
#### 1) 分类
> - Collection 类似于数组
>> - List 以特定的次序储存元素，取出时未必按照原来的顺序
>>> - ArrayList 擅长随机访问元素，如果需要在中间插入、移动、删除元素则会花费较多的时间
>>> - LinkedList 不擅长随机访问元素，如果需要在中间插入、移动、删除元素则会花费较少的时间。实现了Queue接口，可以当成Queue来用
>>> - Vector 不进行边界检查，可以确切知道它所持有的对象隶属什么类别，Vector总比ArrayList慢
>> - Set 每个值只保存一个对象，不能还有重复的元素，顺序是随机的
>>> - HashSet 使用散列函数
>>> - TreeSet 使用红黑树
>>> - LinkedHashSet 使用链表结合散列函数
>> - Queue 先进先出 使用offer()加入元素;使用remove()和poll()都可以获取头并移除头元素，但是不同的是如果队列为空，那么remove()会抛出异常，而poll()会返回null;使用peek()和element()可以检索头元素但不移除，不同的是如果队列为空，那么element()会抛出异常，而peek()会返回null;
>>> - PriorityQueue
> - Map 类似于键名对 不允许有重复值
>> - HashMap 适用于快速查找 允许一个null键和多个null值，线程同步时需要额外使用synchronize
>> - HashTable 不允许一个null键和多个null值，比HashMap慢。而且他是线程同步的，不需要额外使用synchronize
>> - HashTree 用来维护排序状态，比较玄学
>> - TreeMap 适用于已经排好序的的序列
#### 2) 特点
> - List、Set、Map将所有对象都是为Object
> - Collection、List、Set、Map都是接口，不能实例化。只有他们的子类才能创建始类
> - Collections是一个帮助类，它提供一系列静态的方法实现对各种集合的搜索、排序、线程完全化操作，如
> - 迭代器iterator也需要接受泛型参数，否则会报编译错误
                
                List<Integer> list = new ArrayList<Integer>();
                list.add(1);
                list.add(9);
                list.add(6);
                list.add(3);
                for(int i: list) {
                    System.out.print(i);
                }
                Collections.sort(list);
                for(int i: list) {
                    System.out.print(i);
                }

                //控制台
                19631369
<h3 id='4.3'>4.3 构造函数</h3>  
        
#### 1) 知识点
> - 创建类时，静态类先行，然后到实例域、构造函数
> - 普通方法名可以跟构造函数名重名，但是区别在于构造函数没有返回值
        
<h3 id='4.4'>4.4 多态</h3>  
        
#### 1) 含义
> - 允许子类型的指针赋值给超类类型的指针
> - 也就是说超类可以根据当前赋值给它的子类的特性进行运作。
> - 一个重要的目的是：接口重用
> - 设计一个接口往往比设计一大堆类来实现这个接口难很多
#### 2) 需要注意的点
> - 重载指的是同一个类中相同方法名不同参数
> - 覆盖一个方法必须签名和返回类型都相同，而且是发生在超类和子类之间
> - 静态方法不能被覆盖，首选超类最顶层的方法
> - 重载和覆盖的区别
> - null可以强行转换为其他所有类型，如(String)null，但是返回值为null
> - 使用多态的时候如果父类要用子类的方法，则需要将父类进行强制转换，否则方法不可见。结论是如果你要一个实例执行某个方法，那么这个方法一定在某个类中存在，而且这个类必须是这个实例的类型，超类必须经过强制类型转
                
                // Father类
                /**
                 * 
                 */
                package Interview;

                /**
                 * @author LvHongbin
                 *
                 */
                public class Father {

                }

                // Son 类
                /**
                 * 
                 */
                package Interview;

                /**
                 * @author LvHongbin
                 *
                 */
                public class Son extends Father{
                    
                    
                    public String getField(String name) {
                        return name;
                    }

                }

                // 执行代码
                Father father = new Son();
                System.out.println(((Son)father).getField("egg"));

                //控制台
                egg

            
<h3 id='4.5'>4.5 继承</h3>  
        
#### 1) 含义
> - 继承会破坏封装性，因为会将超类的实现细节暴露给子类
> - 本质上是“白盒式代码复用”
#### 2) super
> - 必须放在首位，否则会出错
> - 关于super的用法在[Java_Study 5.1 覆盖](https://github.com/hblvsjtu/Java_Study#5.1)中有详细的说明
#### 3) 不能继承的情况
> - 匿名内部类不能继承其他类，但一个内部类可以作为一个接口，有另一个内部类实现
            
<h3 id='4.6'>4.6 抽象类与接口</h3>  
        
#### 1) 抽象类
> - 只能作为其他类的基类
> - 不能直接被实例化，即不能使用new
> - 抽象类中允许有抽象的方法和非抽象的方法
> - 抽象类不能同时又是final的
> - 如果一个非抽象类从抽象类中产生，那么必须通过覆盖来实现所有继承而来的抽象
#### 2) 接口
> - 接口是一种更加高级的抽象
> - 默认修饰符是public 
> - 方法默认是public abstract
> - 实例域默认是public static final

        
------      
        
<h2 id='5'>五、字符串</h2>
<h3 id='5.1'>5.1 基础问题</h3>  
        
#### 1) equal
> - 如果是字符串，则比较内容是否一致
> - 如果是对象，则比较所有的成员的值是否一致
#### 2) string创建对象
> - 字符串字面量储存在常量池(constant pool)中
> - 字符串的 “+” 号连接操作也在常量池(constant pool)中
> - 只有发生赋值的时候才会创建对象，如“string s = "a" + "b" + "c";”过程中只发生一次对象创建的过程
        
<h3 id='5.2'>5.2 常量池</h3>  
        
#### 1) Constant Pool
> - 他是一个由数组组成的表
> - 常量池在Java的方法区中，具体可以看[1.1 运行时数据区域](https://github.com/hblvsjtu/JVM_Study#1.1)
> - string.intern()方法可以将有对象创建的字符串添加到常量池中，并返回常量池中的地址
                
                 String s1 = "Hello";
                 String s2 = "Hello";
                 String s3 = "Hel" + "lo";
                 String s4 = "Hel" + new String("lo");
                 String s5 = new String("Hello");
                 String s6 = s5.intern();
                 String s7 = "H";
                 String s8 = "ello";
                 String s9 = s7 + s8;
                           
                 System.out.println(s1 == s2);  // true
                 System.out.println(s1 == s3);  // true 在编译期间JVM可以做优化在常量池中寻扎并返回“Hello”的地址
                 System.out.println(s1 == s4);  // false new String("lo")是一个对象 是一个不可预知的结果，不能在编译期间做优化
                 System.out.println(s1 == s9);  // false 变量也是一个不可预知的结果，不能在编译期间做优化
                 System.out.println(s4 == s5);  // false
                 System.out.println(s1 == s6);  // true

        
<h3 id='5.2'>5.2 string和StringBuffer和StringBuilder比较</h3>  
        
#### 4) string和StringBuffer和StringBuilder
> - 运行速度快慢为：StringBuilder > StringBuffer > String
> - 在线程安全上，StringBuilder是线程不安全的，而StringBuffer是线程安全的
> - string的合并操作可能经历多次对象的创建,但简单的认为+号的效率小于StringBuffer和StringBuilder是错误的，因为当涉及到在编译器期能确定的字符串用+号进行连接的时候，只创建一个对象而且在编译期就能被JVM优化
> - StringBuffer的操作只涉及一次的对象创建和多次的内存开辟
> - [适用情况](https://www.cnblogs.com/su-feng/p/6659064.html)
>> - String：适用于少量的字符串操作的情况，在编译期能确定字符串值的情况下，使用＋号的效率最高
>> - StringBuilder：适用于单线程下在字符缓冲区进行大量操作的情况
>> - StringBuffer：适用多线程下在字符缓冲区进行大量操作的情况
> - 其他注意事项
>> - 避免使用“+=”来构造字符串，因为每次都会创建新的对象。而原来的对象就会变成垃圾(如果没有其他引用的时候)。这样如果进行循环就会产生n个对象，从而造成内存泄露
>> - 不要使用new创建String，因为string str = new string("string");这一句中创建引用1个，常量池中对象一个，堆heap中1个，合计两个对象
>> - 声明StringBuffer的时候指定capacity，不要使用默认值(18)，
>> - new StringBuffer("string"),它本身并不创建对象，而是改变对象本身
            
<h3 id='5.3'>5.3 正则表达式</h3>  
        
#### 1) 删除/挑选所需要的字符
> - replaceAll(^targetReg, ""); //使用空格排除非目标字符
> - split(^targetReg) 打散成数组后再合并

        
------      
        
<h2 id='6'>六、操作系统、数据库和网络</h2>
<h3 id='6.1'>6.1 操作系统</h3>  
        
#### 1) 要点
> - [32位程序的寻址能力是2^32，也就是4G。对于32位程序只能申请到4G的内存。而且这4G内存中，在windows下有2G，linux下有1G是保留给内核态使用，用户态无法访问。故只能分配2G、3G的内存使用。](https://www.cnblogs.com/reskai/p/7528627.html) 
> - 进程 最短作业时间（在同一时刻比较多个进程的剩余时间，优先运行最小剩余时间的进程）
>>>>>> ![图6-1 最短作业时间.png](https://github.com/hblvsjtu/Java_Interview_Study/blob/master/picture/%E5%9B%BE6-1%20%E6%9C%80%E7%9F%AD%E4%BD%9C%E4%B8%9A%E6%97%B6%E9%97%B4.png?raw=true) 
#### 2) 进程
> - 三种状态
>> - 就绪
>> - 执行
>> - 阻塞
> - 哲学家就餐问题：哲学家围成一圈，左右手旁边放有待编号的叉子，拿叉子的规则：每人先拿起编号较小的，然后再拿起编号较大的，用餐完后先放下编号高的叉子，再放下编号小的叉子。从而第5位哲学家就不能使用任何一个叉子。这个故事可以告诉我们多个进程分别争取2个资源，但资源总数只有n个的时候，最多n-1个进程同时参与而不发生死锁。
#### 3) 线程
> - 概念：线程指在程序执行过程中，能够执行程序代码中的一个执行单位，每个程序至少都有一个线程，也就是程序本身
> - 四种状态
>> - 运行
>> - 就绪
>> - 挂起
>> - 结束
> - sleep()和wait()的区别
>> - sleep()使线程停止一段时间，在sleep时间完成后未必马上执行，要看优先级
>> - wait则使同步对象进入等待状态，直至被唤醒或者等待时间到
> - win32系统中线程的三种基本模式
>> - 单线程
>> - 单元线程 为完成一个任务多个线程进行合作，但是线程间不共享资源
>> - 自由线程 为完成一个任务多个线程进行合作，但是线程间共享资源
> - 调用线程的run()方法只会得到同步的结果，start()方法才是异步
> - synchronized和Lock的区别：Lock可以完成synchronized的所有功能实现，主要不同点在于Lock有比synchronized更精确的线程语义和更好的性能。具体可以看[四、Lock锁](https://github.com/hblvsjtu/JavaMultiThreadProgramming_Study#4)
#### 4) 串行化或者叫序列化
> - 对象的持续性：对象的寿命随着生成该对象的程序终止而终止。而有时候需要将对象的状态保存下来，在需要的时候再将对象恢复。对象这种能记录自己状态以便将来再生的能力叫做对象的持续性(persistence)
> - 对象通过描述自己状态的数值来记录自己，这个过程称为对象的串行化（serialization）
> - 串行化只能保存对象的非静态成员变量，而且变量的任何修饰符都不能保存
> - transient关键字用来表示一个域不是该对象串行化的一部分，比如一些只有瞬时状态的对象，如Tread和FileInputStream
                    
<h3 id='6.2'>6.2 数据库</h3>  
        
#### 1) 数据库理论问题
> - 概念
>> - 函数依赖：若在一张表中，在属性（或属性组）X的值确定的情况下，必定能确定属性Y的值，那么就可以说Y函数依赖于X，写作 X → Y。也就是说，在数据表中，不存在任意两条记录，它们在X属性（或属性组）上的值相同，而在Y属性上的值不同。这也就是“函数依赖”名字的由来，
>> - 码：设 K 为某表中的一个属性或属性组，若除 K 之外的所有属性都完全函数依赖于 K（这个“完全”不要漏了），那么我们称 K 为候选码，简称为码。在实际中我们通常可以理解为：假如当 K 确定的情况下，该表除 K 之外的所有属性的值也就随之确定，那么 K 就是码。一张表中可以有超过一个码。
>> - 非主属性：包含在任何一个码中的属性成为主属性。除了主属性以外的就是非主属性。
>> - 超键(super key):在关系中能唯一标识元组的属性集称为关系模式的超键
>> - 候选键(candidate key):不含有多余属性的超键称为候选键
>> - 主键(primary key):用户选作元组标识的一个候选键程序主键
>> - 外键(foreign key)如果关系模式R1中的某属性集不是R1的主键，而是另一个关系R2的主键则该属性集是关系模式R1的外键。
> - 数据库模式的四个范式 [————逃离地球的小小呆](https://blog.csdn.net/gui951753/article/details/79609874 )
>> - 第一范式1NF：关系模式R中所有属性的值域中每一个值都是不可分解的值。首先是第一范式（1NF）。符合1NF的关系（你可以理解为数据表。“关系模式”和“关系”的区别，类似于面向对象程序设计中”类“与”对象“的区别。”关系“是”关系模式“的一个实例，你可以把”关系”理解为一张带数据的表，而“关系模式”是这张数据表的表结构。1NF的定义为：符合1NF的关系中的每个属性都不可再分。表1所示的情况，就不符合1NF的要求。范式一强调数据表的原子性。
>> - 第二范式2NF：关系模式R为第一范式，而且R中每一个非主属性完全函数依赖于R的某个候选键（注：如果A是关系模式R候选键的一个属性，则称A是R的主属性，否则A是R的非主属性）。每个表必须有一个(而且仅有一个)数据元素为主关键字(Primary key),其他数据元素与主关键字一一对应。通常称这种关系为函数依赖(Functional dependence)关系，即表中其他数据元素都依赖于主关键字,或称该数据元素惟一地被主关键字所标识。
>> - 第三范式3NF：关系模式R是第二范式，且每个非主属性都不传递依赖于R的候选键。表中的所有数据元素不但要能惟一地被主关键字所标识,而且它们之间还必须相互独立,不存在其他的函数关系。也就是说，对于一个满足2nd NF 的数据结构来说，表中有可能存在某些数据元素依赖于其他非关键字数据元素的现象,必须消除。 
>> - BCNF范式：它构建在第三范式的基础上，如果关系模型R是第一范式，且每个属性都不传递依赖于R的候选键，那么称R为BCNF的模式。
>> - 第四范式4NF：关系模式R中假设D是R上的多值依赖集合，如果D中的成立多值非平凡依赖X->Y，X必是R的超键
>> - 数据库事务
> - 原子性
> - 一致性
> - 独立性 两个以上的十五不会出现交错执行的状态
> - 持久性
> - 储存过程和函数的区别：储存过程指的是用户自定义的一系列SQL语句的集合，设计特定表或其他对象的任务。函数指的是数据库已定义的方法，他接收参数并返回某种类型的值，而且不涉及特定用户表。
#### 2) 优化数据库查询速度
> - 
>>>>>> ![图6-2 优化数据库查询速度.png](https://github.com/hblvsjtu/Java_Interview_Study/blob/master/picture/%E5%9B%BE6-2%20%E4%BC%98%E5%8C%96%E6%95%B0%E6%8D%AE%E5%BA%93%E6%9F%A5%E8%AF%A2%E9%80%9F%E5%BA%A6.png?raw=true) 
#### 3) SQL语句示范
> - 例子
>>>>>> ![图6-3 SQL语句示范问题.png](https://github.com/hblvsjtu/Java_Interview_Study/blob/master/picture/%E5%9B%BE6-3%20SQL%E8%AF%AD%E5%8F%A5%E7%A4%BA%E8%8C%83%E9%97%AE%E9%A2%98.png?raw=true) 
> - 答案
>>>>>> ![图6-4 SQL语句示范答案.png](https://github.com/hblvsjtu/Java_Interview_Study/blob/master/picture/%E5%9B%BE6-4%20SQL%E8%AF%AD%E5%8F%A5%E7%A4%BA%E8%8C%83%E7%AD%94%E6%A1%88.png?raw=true) 
            
<h3 id='6.3'>6.3 网络</h3>  
        
请参考[Computer_Networking_Study](https://github.com/hblvsjtu/Computer_Networking_Study)
#### 1) NAT 网络地址转换
> - 将一个IP地址域映射到另一个IP地址域的技术，从而为终端主机提供透明路由。包括：
>> - 静态网络地址转换
>> - 动态网络地址转换
>> - 网络地址及端口转换
>> - 动态网络地址及端口转换
>> - 端口映射
> - 常用于私有地址与公有地址的转换，已解决IP地址匮乏的问题
#### 2) IP地址
> - A类地址是由1B网络地址和3B主机地址组成，最高为必须为0，即第一段数字范围为1-127
> - B类地址是由2B网络地址和2B主机地址组成，最高为必须为10，即第一段数字范围为128-191
> - C类地址是由3B网络地址和1B主机地址组成，最高为必须为110，即第一段数字范围为192-223
> - D类地址是由4B网络地址和0B主机地址组成，最高为必须为1110，即第一段数字范围为224-239
#### 3) 子网掩码
> - 用于判断任意两台计算机的IP地址是否属于同一子网络的根据
> - 最简单的理解是两台计算机各自的IP与子网掩码进行AND与与运算后如果得出的结果相同，则说明处于同一网络
> - 255.255.255.(255-n), n代表对应网段有多少个ip
#### 4) ICMP协议
> - 它是TCP/IP协议族的一个子协议，用于IP主机、路由器之间传递控制消息
#### 5) ping原理
> - 向指定的IP地址发送一定长度的数据包，按照约定，若指定IP地址存在的话，会返回同样大小的数据包，如果在特定的时间内没有返回的话，就是“超时”，就认为指定的IP不存在。由于ping遵顼ICMP协议，有些防火墙软件会屏蔽ICMP协议。所以该结果只能作为参考。
> - 