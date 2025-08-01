package HashMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class OverlappingIntervals {

	public static void main(String[] args) {
		int[][] intervals = {{10,16},{2,8},{1,6},{7,12}};
		int[][] intervals2 = {{1,5},{8,12},{15,24},{25,26}};
		int[] arr = {900, 940, 950, 1100, 1500, 1800};
		int[] dep = {910, 1200, 1120, 1130, 1900, 2000};
		int[] arr1 = {900, 1100, 1235};
		int[] dep1 = {1000, 1200, 1240};
		System.out.println(minPlatforms(arr, dep));
//		int[][] ranges = intervalIntersection(intervals, intervals2);
//		for (int[] s : ranges) {
//			for (int i : s) {
//				System.out.print(i + " ");
//			}
//			System.out.println();
//		}
	}
	
	public static int minPlatforms(int[] arr, int[] dep) {
		/*
		 * Bonus interview problem:
		 * Given arrival and departure times of all trains that reach a railway station.
		 * Find the minimum number of platforms required for the railway station so that no train waits
		 */
		/*
		 * The approach here is we will check each train's arrival time with
		 * the departure time of previous trains, if they overlap, we need another
		 * platform, if they don't we don't need one more platform. Min-heap will
		 * have the departure times of the trains who are currently on open platforms
		 * and we will check if the new train's arrival is greater than the previous 
		 * train's departure and keep on popping the trains and adding new departure times.
		 */
		PriorityQueue<Integer> departures = new PriorityQueue<>();
		int platforms = 0;
		departures.offer(dep[0]);
		for (int i = 1; i < arr.length; i++) {
			int next_arrival = arr[i];
			int curr_departure = dep[i];
			/*
			 * Checking each new arrival with the top of heap and keep on
			 * popping the heap while the departures are less than new arrival.
			 */
			while (!departures.isEmpty() && departures.peek() <= next_arrival) {
				departures.poll();
			}
			/*
			 * Add each departure on the heap considering the current train has 
			 * occupied this platform, and our platform count will be the max of 
			 * current count and the size of heap as heap indicates the number of 
			 * platforms that are required as all departure times are overlapping.
			 */
			departures.offer(curr_departure);
			platforms = Math.max(platforms, departures.size());
		}
		
		return platforms;
	}
	
	public static int findMinArrowShots(int[][] points) {
		/*
		 * Leet-code 452 (Medium)
		 */
		if (points.length == 0) return 0;
		
        Arrays.sort(points, (a,b) -> Integer.compare(a[1], b[1]));
		int arrows = 1;
		int lastShot = points[0][1];

		for (int i = 1; i < points.length; ++i) {
            int[] point = points[i];
            
			if (point[0] > lastShot) {
				arrows++;
				lastShot = point[1];
			}
		}
		return arrows;
    }
	
	public static int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
		/*
		 * Leet-code 986 (Medium)
		 */
		List<int[]> intersections = new ArrayList<int[]>();
		int first_ind = 0, second_ind = 0;
		while (first_ind < firstList.length && second_ind < secondList.length) {
			int[] first = firstList[first_ind];
			int[] second = secondList[second_ind];
			
			int start = Math.max(first[0], second[0]);
			int end = Math.min(first[1], second[1]);
			
			if (start <= end) {
				intersections.add(new int[]{start, end});
			}
			
			if (first[1] < second[1])
				first_ind++;
			else
				second_ind++;
			
		}
		return  intersections.toArray(new int[intersections.size()][]);
	}
	
	public static int[][] mergeIntervals(int[][] intervals) {
		/*
		 * Leet-code 56 (Medium)
		 */
		/*
		 * In this problem, we are merging all the intervals of the
		 * intervals array that is given, and adding them into the
		 * new array. We will need an index variable where we will
		 * add the current valid interval, and we will sort the intervals
		 * by their starting positions, and that would help in overlap check.
		 * We also need a current_merge interval that is in need of merging
		 * and we will check each current interval with this one for overlap.
		 */
		int[][] result = new int[intervals.length][2];
		int index = 0;
		Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
		int[] current_merge = intervals[0];
		
		for (int i = 1; i < intervals.length; i++) {
			/*
			 * To check the overlap, we need the current interval and the
			 * end, start of current interval and merge one, respectively.
			 */
			int[] current_int = intervals[i];
			int end1 = current_merge[1];
			int start2 = current_int[0], end2 = current_int[1];
				
			/*
			 * This condition check if the end time of merging interval is 
			 * greater than the start time of current interval, there's a overlap.
			 */
			if (end1 >= start2) {
				// We update our current merge with the new merged and don't add anything.
				current_merge[1] = Math.max(end1, end2);
			} else {
				/*
				 * If there is no overlap, then we can add our merged one because it would
				 * go first as the intervals are sorted, and then once we're done with this
				 * interval, we can update our current_merge with the current_int because
				 * this is gonna be the one we will be checking with future intervals for overlap.
				 */
				result[index++] = current_merge;
				current_merge = current_int;
			}
		}
		
		// In-case, we got out of the loop, we still have to add the last interval.
		result[index++] = current_merge;
		
		return Arrays.copyOf(result, index);
	}
	
	public static int meetingRooms2(int[][] intervals) {
		/*
		 * Leet-code 253 (Medium)
		 */
		/*
		 * This problem says return minimum number of meeting rooms.
		 * We can use min-heap here to keep track of the end times of
		 * the intervals in order to check the overlap, as we will sort
		 * the intervals with respect to their starting times, we don't
		 * need to check them to check the overlap.
		 */
		PriorityQueue<Integer> minheap = new PriorityQueue<Integer>();
		Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
		int rooms = 0;
		
		for (int[] interval : intervals) {
			/*
			 * Here, we are looping over all the intervals, and have another
			 * while loop, that will keep on running till the top end time
			 * of the heap is less than the current interval's start time
			 * because that indicates that previous meeting rooms session
			 * has ended and we just remove them from the heap.
			 */
			while (!minheap.isEmpty() && minheap.peek() <= interval[0]) {
				/*
				 * The heap is keeping track of the active meeting rooms
				 * of which we have not find any start time bigger than
				 * their end times, that would say meeting's over.
				 */
				minheap.poll();
			}
			
			/*
			 * At each iteration we add the end time to the min-heap, and update
			 * the rooms using max of current rooms we have and min-heap size,
			 * because min-heap has all the overlapping intervals which need
			 * their own separate rooms so that would be the min rooms needed.
			 */
			minheap.add(interval[1]);
			rooms = Math.max(rooms, minheap.size());	
		}
		return rooms;
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










