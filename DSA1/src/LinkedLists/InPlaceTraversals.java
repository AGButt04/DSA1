package LinkedLists;

import java.util.Stack;

public class InPlaceTraversals {

	public static void main(String[] args) {
		ListNode head = new ListNode(1);
		ListNode two = new ListNode(2);
		ListNode three = new ListNode(3);
		ListNode four = new ListNode(4);
//		ListNode five = new ListNode(5);
		head.next = two; 
		two.next = three;
		three.next = four;
		four.next = null;
//		five.next = null;
		
		ListNode newHead = evenoddList(head);
		while (newHead != null) {
			System.out.print(newHead.val + " ");
			newHead = newHead.next;
		}
		System.out.println();

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
		
		ListNode(int x) {
			this.val = x;
			this.next = null;
		}
	}

}
