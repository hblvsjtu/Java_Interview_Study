/**
 * 
 */
package Algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author LvHongbin
 *
 */
public class MainTool {
	
	private int num  = 0;
	private int[] input;
	
	public MainTool(int num) {
		this.num = num;
	}
	

	public int[] input() {
		this.input = new int[num];
		System.out.println("Please input the arguements:");
		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
		try {
			String[] S = buffer.readLine().split(",");
			int i = 0;
			for(String s: S) {
				this.input[i] = Integer.parseInt(s);
				i++;
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				buffer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.input;
	}
	
	public int result(int[] i) {
		return 0;
	}
	
	public void execute() {
		Long startTime = System.currentTimeMillis();
		System.out.println("\nThe result is " + result(input()));
		Long stopTime = System.currentTimeMillis();
		System.out.println("The during time is " + (stopTime - startTime));
	}
}
