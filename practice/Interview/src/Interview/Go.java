/**
 * 
 */
package Interview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

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
