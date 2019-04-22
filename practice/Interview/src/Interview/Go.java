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
		try {
			System.out.println(fin());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		Scanner scan = new Scanner(System.in);
		System.out.println("请输入第一个数字：");
		int i1 = scan.nextInt();
		System.out.println("请输入第二个数字：");
		int i2 = scan.nextInt();
		System.out.println("两数之和：" + (i1 + i2));
//		scan.close();
		
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
	}
	
	private static String fin() throws Exception {
		try {
			System.out.println("I am try");
			return "I am return";
		}finally {
			System.out.println("I am finally");
		}
	}
}
