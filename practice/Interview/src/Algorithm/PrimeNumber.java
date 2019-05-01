/**
 * 
 */
package Algorithm;

import java.util.ArrayList;

/**
 * @author LvHongbin
 *
 */
public class PrimeNumber extends MainTool{
	
	private int num = 0;
	public static void main(String[] args) {
		MainTool m = new PrimeNumber(1);
		m.execute();
	} 

	public PrimeNumber(int num) {
		super(num);
		// TODO Auto-generated constructor stub
	}
	
	public int result(int[] i) {
		ArrayList<Integer> list =new ArrayList<Integer>();
		for(int a = 2; a<=i[0]; a++) {
			list.add(a);
		}
		find(list);
		return this.num;
	}
	
	public ArrayList<Integer> find(ArrayList<Integer> list) {
		int a = list.size();
		if(a == 0) return null;
		for(int i = 1; i<list.size(); i++) {
			if(list.get(i) % list.get(0) == 0) {
				list.remove(i);
			}
		}
		System.out.print(list.remove(0) + "\t");
		this.num++;
		return find(list);
	}
}
