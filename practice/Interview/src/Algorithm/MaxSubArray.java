/**
 * 
 */
package Algorithm;

/**
 * @author LvHongbin
 *
 */
public class MaxSubArray {


	private int[] array = {};
	public static void main(String[] arg) {
		int[] array = {-10, -8, -4, -1, -3};
		MaxSubArray m = new MaxSubArray(array);
		long startTime = System.currentTimeMillis();
		int[] result = m.select();
		long endTime = System.currentTimeMillis();
		for(int i : result) {
			System.out.printf("%d\t" ,i);
		}
		System.out.printf("\nËùºÄÊ±¼ä£º %d ms", (endTime - startTime));
	}
	
	public MaxSubArray(int[] array) {
		this.array = array;
	}
	
	private int[] select() {
		int[] result = {0, 0, 0};
		int num = this.array.length - 1; 
		int max = -10000000;
		int sum = 0;
		int left = 0;
		for(int i=0; i<=num; i++) {
			sum += this.array[i];
			if(sum>=max) {
				result[0] = left;
				result[1] = i;
				result[2] = max = sum;
			}
			if(sum<0) {
				left = i+1;
				sum = 0;
			}
		}
		return result;
	}
}
