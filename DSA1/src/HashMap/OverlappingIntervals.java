package HashMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OverlappingIntervals {

	public static void main(String[] args) {
		int[][] intervals = {{1,2},{3,5},{6,7},{8,10},{12,16}};
		int[] intervals2 = {4,8};
//		System.out.println(eraseOverlapIntervals(intervals));
		int[][] ranges = insert(intervals, intervals2);
		for (int[] s : ranges) {
			for (int i : s) {
				System.out.print(i + " ");
			}
			System.out.println();
		}
	}
	
	public static int[][] insert(int[][] intervals, int[] newInterval) {
		/*
		 * Leet-code 57 (Medium)
		 */
		/*
		 * Here, we are adding a new interval to the list of intervals,
		 * and if the interval overlap, we also have to merge it with others.
		 */
		// Edge case, if there is no element in the array, just return new interval.
		if (intervals.length == 0) return new int[][] {newInterval};
		
		/*
		 * Here, we initialize a new intervals matrix that we will populate
		 * as we go through the problem and start adding valid intervals.
		 * i will be the index where we will put the intervals
		 * added is a checker flag which will indicate whether we have added
		 * the new interval into our new intervals matrix yet or no.
		 */
		int[][] newIntervals = new int[intervals.length+1][2];
		int i = 0;
		boolean added = false;
		for (int[] interval : intervals) {
			/*
			 * For each interval in the original array, we will check
			 * the three cases, which will tell us what to do.
			 */
			/*
			 * If the ending position of current interval is less than
			 * the starting position of the new interval that means the 
			 * current interval is less than new one and there's no overlap
			 * as their starting ends are apart so we just add it to the array.
			 */
			if (interval[1] < newInterval[0]) {
				newIntervals[i++] = interval;

			} else if (interval[0] > newInterval[1]) {
				/*
				 * Else if the starting position of current interval is greater than
				 * the ending of new interval, that means current interval is greater
				 * than the new interval and no overlap, and first we have to add new interval.
				 * We see the checkered flag and if false, add it the matrix and then current.
				 */
				if (!added) {
					newIntervals[i++] = newInterval;
					added = true;
				}
				newIntervals[i++] = interval;
				
			} else {
				/*
				 * Now, this case says that there is an overlap, so here we just merge the 
				 * two overlapping intervals and update our new interval with the new merged
				 * interval, which will now be checked for all three cases again in the loop.
				 */
				int start = Math.min(interval[0], newInterval[0]);
				int end = Math.max(interval[1], newInterval[1]);
				int[] newInt = new int[] {start, end};
				newInterval = newInt;
			}
		}
		/*
		 * After we exit the loop, we check did we add the new interval yet?
		 * If we did not, even after iterating the whole array, then we just
		 * add it to the end, probably it was the biggest or became the biggest after merging
		 */
		if (!added)
			newIntervals[i++] = newInterval;
		
		// We just return the copy of the new array as a 2-D ARRAY.
		return Arrays.copyOf(newIntervals, i);
	}
	
	public static List<String> summaryRanges(int[] nums) {
		/*
		 * Leet-code 228
		 */
		/*
		 * In this problem, we are returning all the ranges
		 * that are one space apart from one another.
		 */
		// If there is only element, just return that as a list.
		if (nums.length==1) return List.of(String.valueOf(nums[0]));
		List<String> ranges = new ArrayList<>();
		int i = 0;
		int n = nums.length;
		while (i < n) {
			/*
			 * Here, we are checking each number with its incoming 
			 * neighbors, and we use another loop, where we skip the
			 * elements where the distance between them is just one.
			 */
			int start = nums[i];
			
			while (i < n - 1 && nums[i+1]-nums[i] == 1) {
				i++;
			}
			/*
			 * This condition checks if we did not move at all meaning
			 * the next number was difference > 1. And if it was not,
			 * then we know the start element is where we started from
			 * and end is where the loop terminated and we add that to list.
			 */
			if (start == nums[i]) {
				ranges.add(String.valueOf(nums[i]));
			} else {
				ranges.add(start + "->" + nums[i]);
			}
			i++;
		}
		
		return ranges;
	}
	
	public static boolean meetingRooms(int[][] intervals) {
		/*
		 * Leet-code 252
		 */
		for (int i = 0; i < intervals.length; i++) {
			for (int j = i+1; j < intervals.length; j++) {
				int start1 = intervals[i][0], end1 = intervals[i][1];
				int start2 = intervals[j][0], end2 = intervals[j][1];
				
				if (start1 < end2 && end1 > start2)
					return true;
			}
		}
		return false;
	}
	
	public static int eraseOverlapIntervals(int[][] intervals) {
		/*
		 * Leet-code 435 (Medium)
		 */
		Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
		int overlaps = 0;
		int[] last_checked = intervals[0];
		
		for (int i = 1; i < intervals.length; i++) {
			int[] current_interval = intervals[i];
			int start1 = last_checked[0], end1 = last_checked[1];
			int start2 = current_interval[0], end2 = current_interval[1];
			
			if (start1 < end2 && end1 > start2)
				overlaps++;
			else
				last_checked = current_interval;
		}
		return overlaps;
	}
}










