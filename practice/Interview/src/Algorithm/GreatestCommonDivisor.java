/**
 * 
 */
package Algorithm;

/**
 * @author LvHongbin
 *
 */
public class GreatestCommonDivisor extends MainTool{
	
	public static void main(String[] args) {
		MainTool m = new GreatestCommonDivisor(2);
		m.execute();
	} 

	public GreatestCommonDivisor(int num) {
		super(num);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public int result(int[] i) {
		// TODO Auto-generated method stub
		int a = i[0]%i[1];
		return a == 0 ? i[1] : result(new int[]{i[1],a});	
	}
}
