package LinkedLists;

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
		
		ListNode newHead = swapPairs(head);
		while (newHead != null) {
			System.out.print(newHead.val + " ");
			newHead = newHead.next;
		}
		System.out.println();

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
