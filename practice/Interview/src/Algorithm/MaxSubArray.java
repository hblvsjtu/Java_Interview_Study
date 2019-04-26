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
		int[] array = {1,3,5,7,9,8,6,-4,2,0};
		MaxSubArray m = new MaxSubArray(array);
		for(int i : m.select()) {
			System.out.println(i);
		}
	}
	
	public MaxSubArray(int[] array) {
		this.array = array;
	}
	
	private int[] select() {
		int[] result = {0, 0, 0};
		int num = this.array.length - 1; 
		int max = 0;
		int sum = 0;
		for(int left=0; left<=num; left++) {
			for(int right=num; right>=left; right--) {
				for(int k=left; k<=right; k++) {
					sum += this.array[k];
				}
				if(sum>=max) {
					result[0] = left;
					result[1] = right;
					result[2] = max = sum;
				}
				sum = 0;
			}
		}
		return result;
	}
}
