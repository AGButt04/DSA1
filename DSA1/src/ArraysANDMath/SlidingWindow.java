package ArraysANDMath;

public class SlidingWindow {

	public static void main(String[] args) {
		int[] nums = {1,12,-5,-6,50,3};
		double avg = findMaxAverage(nums, 4);
		System.out.println(avg);
	}
	
	public static double findMaxAverage(int[] nums, int k) {
		double maxAvg = 0;
		for (int i = 0; i < k; i++) {
		    maxAvg += nums[i];
		}
		for (int i = k; i < nums.length; i++) {
			double currAvg = maxAvg;
			currAvg += nums[k] - nums[i - k] ;
			currAvg = currAvg / k;
			if (currAvg > maxAvg)
				maxAvg = currAvg;
		}
		
		return maxAvg;
	}

}
