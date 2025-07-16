package LinkedLists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class InPlaceTraversals {

	public static void main(String[] args) {
		ListNode head = new ListNode(1);
		ListNode two = new ListNode(2);
		ListNode three = new ListNode(3);
		ListNode four = new ListNode(4);
		ListNode five = new ListNode(5);
		ListNode six = new ListNode(6);
		ListNode seven = new ListNode(7);
		head.next = two; head.random = four;
		two.next = three; two.random = head;
		three.next = four; three.random = five;
		four.next = five; four.random = seven;
		five.next = six; five.random = three;
		six.next = seven; six.random = five;
		seven.next = null; seven.random = head;
		
		ListNode walker = head;
		ListNode newHead = randomCopyList(head);
		while (walker != null) {
			System.out.println(walker.val + ","  + walker.random.val);
			walker = walker.next;
		}
		System.out.println("Copy:");
		while (newHead != null) {
			System.out.println(newHead.val + ","  + newHead.random.val);
			newHead = newHead.next;
		}
	}
	
	public static ListNode randomCopyList(ListNode head) {
		/*
		 * Leet-code 138 (Medium)
		 */
		/*
		 * This one is tricky in sense as there are random pointers
		 * involved but we can use HashMap for that.
		 */
		/*
		 * Using HashMap to store the copy node of the original list's node.
		 * dummy pointer will point to the head of the new list.
		 * current will make the new list as starting from dummy.
		 * walker will iterate over the original list to create copies.
		 */
		HashMap<ListNode, ListNode> copies = new HashMap<>();
		ListNode dummy = new ListNode(0);
		ListNode current = dummy;
		ListNode walker = head;
		while (walker != null) {
			/*
			 * We create a new list node and copy in the walker's value.
			 * put the original node and its copy in the HashMap.
			 * attach the links for the new copy list and move walker.
			 */
			ListNode node = new ListNode(walker.val);
			copies.put(walker, node);
			current.next = node;
			current = current.next;
			walker = walker.next;
		}
		
		walker = head;
		while (walker != null) {
			/*
			 * Now we can attach the random pointers.
			 * We can access the copy node from map and 
			 * also access the copy of node's random node
			 * and attach the links in between. This will make
			 * sure that our copy node is being connected to the 
			 * random node of the copy list not the original.
			 */
			ListNode copy = copies.get(walker);
			if (walker.random != null)
				copy.random = copies.get(walker.random);
			else
				copy.random = null;
			walker = walker.next;
		}
		// return the head of the copy list.
		return dummy.next;
	}
	
	public static ListNode[] splitListToParts(ListNode head, int k) {
		/*
		 * Leet-code 725 (medium)
		 */
		/*
		 * This one splits the linked list in k parts.
		 * We create a nodes array new the length is k.
		 * We check the edge cases and just return the array accordingly.
		 */
		ListNode[] nodes = new ListNode[k];
		if (head == null || head.next == null || k == 0) {
			nodes[0] = head;
			return nodes;
		}
		/*
		 * Then we count the length of the array as needed to find 
		 * how many nodes to assign to each part of the split.
		 */
		int length = 0;
		int[] iterations = new int[k];
		ListNode walker = head;
		while (walker != null) {
			length++;
			walker = walker.next;
		}
		/*
		 * If the length is smaller than k that means the whole list
		 * will be divided in part of length 1 and remaining nulls;
		 */
		if (length <= k) {
			walker = head;
			for (int i = 0; i < k && walker != null; i++) {
				nodes[i] = walker;
				ListNode temp = walker.next;
				walker.next = null;
				walker = temp;
			}
			return nodes;
		}
		/*
		 * We then find the number of minimum nodes that can be assigned
		 * to each part (parts), and then just add the number to each index
		 * in iterations array which will help when adding nodes. If there are
		 * remaining nodes that will be each divided to the starting parts of the iterations.
		 */
		int parts = length / k;
		for (int i = 0; i < k; i++) {
			iterations[i] = parts;
		}
		int remainder = length % k;
		for (int i = 0; i < remainder; i++) {
			iterations[i] = iterations[i] + 1;
		}
		walker = head;
		/*
		 * Then we iterate over the list k times to create partitions
		 */
		for (int i = 0; i < k; i++) {
			/*
			 * Each time we iterate over the array iterations[i] times
			 * and just add the nodes into the nodes list, and using prev
			 * to break the link between partitioning nodes to avoid bugs.
			 */
			int counter = 0;
			int stoplimit = iterations[i];
			ListNode part = walker;
			ListNode prev = null;
			while (counter < stoplimit && walker != null) {
				prev = walker;
				walker = walker.next;
				counter++;
			}
			prev.next = null;
			nodes[i] = part;
		}
		return nodes;
	}
	
	public static ListNode deleteDuplicates(ListNode head) {
		/*
		 * Leet-code 82
		 */
		if (head == null || head.next == null)
			return head;
		
		ListNode dummy = new ListNode(0);
		ListNode prev = dummy;
		ListNode walker = head;
		while (walker != null && walker.next != null) {
			if (walker.val != walker.next.val) {
				prev.next = walker;
				prev = walker;
				walker = walker.next;
				continue;
			} else {
				while (walker.next != null && walker.val == walker.next.val) {
					walker = walker.next;
				}
				walker = walker.next;
				prev.next = walker;
			}
		}
		
		return dummy.next;
	}
	
	public static ListNode partitionList(ListNode head, int x) {
		/*
		 * Leet-code 86 (Medium)
		 */
		if (head == null || head.next == null)
			return head;
		ListNode dummy1 = new ListNode(0);
		ListNode dummy2 = new ListNode(0);
		ListNode prev1 = dummy1;
		ListNode prev2 = dummy2;
		ListNode walker = head;
		while (walker != null) {
			if (walker.val < x) {
				prev1.next = walker;
				prev1 = prev1.next;
			} else {
				prev2.next = walker;
				prev2 = prev2.next;
			}
			walker = walker.next;
		}
		prev2.next = null;
		prev1.next = dummy2.next;
		
		return dummy1.next;
	}
	
	public static ListNode addtwonumbers(ListNode l1, ListNode l2) {
		/*
		 * Leet-code 445 (Medium)
		 */
		/*
		 * Using stack to add elements for both lists, left-to-right.
		 * Then Popping the stack and adding elements and creating another
		 * lists with the sum. Have to take care of carry.
		 */
		Stack<Integer> st1 = new Stack<>();
		Stack<Integer> st2 = new Stack<>();
		ListNode walker1 = l1;
		ListNode walker2 = l2;
		while (walker1 != null) {
			st1.add(walker1.val);
			walker1 = walker1.next;
		}
		
		while (walker2 != null) {
			st2.add(walker2.val);
			walker2 = walker2.next;
		}
		
		
		ListNode newhead = new ListNode(0);
		ListNode curr = newhead;
		int carry = 0;
		while (!st1.isEmpty() && !st2.isEmpty()) {
			int num1 = st1.pop();
			int num2 = st2.pop();
			int sum = num1 + num2;
			sum += carry;
			if (sum > 9) {
				carry = sum / 10;
				sum = sum % 10;
			} else
				carry = 0;
			ListNode newNode = new ListNode(sum);
			curr.next = newNode;
			curr = curr.next;
		}
		if (st1.isEmpty()) {
			while (!st2.isEmpty()) {
				int num = st2.pop();
				if (carry > 0) {
					num += carry;
					carry = num / 10;
					num = num % 10;
				}
				ListNode node = new ListNode(num);
				curr.next = node;
				curr = curr.next;
			}
		} else if (st2.isEmpty()) {
			while (!st1.isEmpty()) {
				int num = st1.pop();
				if (carry > 0) {
					num += carry;
					carry = num / 10;
					num = num % 10;
				}
				ListNode node = new ListNode(num);
				curr.next = node;
				curr = curr.next;
			}
		}
		
		if (carry > 0) {
			ListNode carryNode = new ListNode(carry);
			curr.next = carryNode;
		}
		// As the list was build right-to-left, we have to
		// reverse it in order to get the right output.
		return reverseList(newhead.next);
	}
	
	public static ListNode evenoddList(ListNode head) {
		/*
		 * Leet-code 328 (medium)
		 */
		/*
		 * The idea is having 2 nodes pointing at 1st and 2nd
		 * nodes of the list and just start connecting them accordingly.
		 * Just keep track of the last node of the odd nodes' walker.
		 */
		ListNode head2 = head.next;
		ListNode walker1 = head;
		ListNode walker2 = head2;
		
		// Keep going until there are one node present in front of both pointers
		while (walker1.next != null && walker2.next != null) {
			walker1.next = walker1.next.next;
			walker2.next = walker2.next.next;
			walker1 = walker1.next;
			walker2 = walker2.next;
		}
		
		// At the end, we know that walker1 pointer points to the last node
		// of the odd nodes so we just connect it to the head of even nodes.
		walker1.next = head2;
		
		// Return the head.
		return head;
	}
	
	public static ListNode swapPairs(ListNode head) {
		/*
		 * Leet-code 24 (medium)
		 */
		
		if (head == null || head.next == null)
			return head;
		
		ListNode dummy = new ListNode(0);
		ListNode prev = dummy;
		ListNode curr = head;
		while (curr != null && curr.next != null) {
			ListNode curr2 = curr.next;
			ListNode ahead = curr2.next;
			prev.next = curr2;
			curr.next = ahead;
			curr2.next = curr;
			curr = ahead;
			prev = prev.next.next;
		}
		return dummy.next;
	}
	
	public static ListNode reverseBetween(ListNode head, int left, int right) {
		/*
		 * Leet-code 92
		 */
		// Step 1: Find the node at left position.
		ListNode prevNode = null;
		ListNode current = head;
		for (int i = 1; i < left; i++) {
		    prevNode = current;
		    current = current.next;
		}
		
		// Step 2: Reverse the range using three pointers
		ListNode walker = current;
		ListNode prev = null;
		for (int i = left; i <= right; i++) {
			ListNode ahead = walker.next;
			walker.next = prev;
			prev = walker;
			walker = ahead;
		}
		
		// Step 3: Reconnect 
		current.next = walker;
		if (prevNode != null)
			prevNode.next = prev;
		else
			head = prev;
				
		return head;
	}
	
	public static ListNode reverseList(ListNode head) {
		/*
		 * Leet-code 206
		 */
		// Reversing the list using previous and walker nodes
		ListNode prev = null;
		ListNode walker = head;
		while (walker != null) {
			ListNode ahead = walker.next;
			walker.next = prev;
			prev = walker;
			walker = ahead;
		}
		return prev;
	}
	
	public static ListNode reverseListR(ListNode head) {
		/*
		 * Leet-code 206 (Recursively)
		 */
		if (head == null || head.next == null)
			return head;
		
		ListNode newHead = reverseListR(head.next);
		head.next.next = head;
		head.next = null;
		
		return newHead;
	}
	
	private static class ListNode {
		int val;
		ListNode next;
		ListNode random;
		
		ListNode(int x) {
			this.val = x;
			this.next = null;
			this.random = null;
		}
	}

}
