package HashMap;

import java.util.Arrays;

public class OverlappingIntervals {

	public static void main(String[] args) {
		int[][] intervals = {{0,30},{5,10},{15,20}};
		int[][] intervals2 = {{1,2},{2,3}};
		System.out.println(eraseOverlapIntervals(intervals));
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










