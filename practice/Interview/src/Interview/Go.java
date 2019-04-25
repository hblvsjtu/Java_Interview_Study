/**
 * 
 */
package Interview;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author LvHongbin
 *
 */
public class Go implements Cloneable{
	
	public static void main(String[] arg) throws CloneNotSupportedException {
		System.out.println(1 + "0");
		System.out.println("1" + 0);
		System.out.println(1 + '0');
		System.out.println('1' + 0);
		System.out.println((char)49);
		int j1 = 0 ;
		int j2 = 0 ;
		int j3 = 0 ;
		int t = "abc".length();
		float f = 100.0f;
        Float F = new  Float(f);
        double d = F.doubleValue(); 
		for(int i = 0; i<100; i++) {
			j1 = j1++;
			j2 = ++j2;
		}
		j3 = j3++ + j3++ + j3++ + ++j3;
		System.out.printf("j1 = %d, j2 = %d, j3 = %d\n", j1, j2, j3);
		System.out.println(d);
		System.out.println("value = "  + (true == false ? 10.9 : 9));
		System.out.println("value = "  + (32 >> 33));
		System.out.println(fib(4));
		Go go1 = new Go();
		Go go2 = new Go();
		Object go3 = new Go();
		System.out.println(go1 == go2);
		System.out.println(go1.equals(go2));
		System.out.println(go1.equals(go1.clone()));
		System.out.println(go2 == go3);
		System.out.println(go3.equals(go2));
		System.out.println(go2.equals(go3));
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
		Father father = new Son();
		System.out.println(((Son)father).getField("egg"));
		BigInteger b1 = new BigInteger("1");
		BigInteger b2 = new BigInteger("2");
		BigInteger b3 = new BigInteger("0");
		b3 = b3.add(b1);
		b3 = b3.add(b2);
		System.out.println(b3.toString());
		
	}
	
	private static String fin() throws Exception {
		try {
			System.out.println("I am try");
			return "I am return";
		}finally {
			System.out.println("I am finally");
		}
	}
	
	private static int fib(int i) {
		return i<3 ? 1 : fib(i-1) + fib(i-2);
	}
	
}
