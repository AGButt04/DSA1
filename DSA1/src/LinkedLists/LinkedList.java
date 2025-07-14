package LinkedLists;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class LinkedList {
	public static void main(String[] args) {
		Node head = new Node(11);
		Node two = new Node(22);
		Node three = new Node(33);
		Node four = new Node(44);
		Node five = new Node(55);
		Node six = new Node(66);
		Node tail = new Node(77);
		head.next = two; //two.random = head;
		two.next = three; //three.random = two;
		three.next = four; //four.random = three;
		four.next = five; //five.random = four;
		five.next = six; //tail.random = five;
		six.next = tail;
		Node walker = head;
		while (walker != null) {
			System.out.print(walker.val + "|");
			walker = walker.next;
		}
		System.out.println();
		Node head1 = swapPairs(head);
		Node walk = head1;
		while (walk != null) {
			System.out.print(walk.val + "|");
			walk = walk.next;
		}
		
		
	}
	
	public static class Node {
	    int val;
	    Node next;
	    Node random;

	    public Node(int val) {
	        this.val = val;
	        this.next = null;
	        this.random = null;
	    }
	}
	
	public static Node swapPairs(Node head) {
		if (head == null || head.next == null)
			return head;
		
		Node dummy = new Node(0);
		dummy.next = head;
		Node prev = dummy;
		while (prev != null && prev.next.next != null) {
			Node curr1 = prev.next;
			Node curr2 = curr1.next;
			
			Node ahead = curr2.next;
			prev.next = curr2;
			curr2.next = curr1;
			curr1.next = ahead;
			
			prev = curr1;
		}
		
		return dummy.next;
	}
	
	public static Node partitionList(Node head, int x) {
		Node before = new Node(0);
		Node after = new Node(0);
		Node beforeCurr = before;
		Node afterCurr = after;
		
		while (head != null) {
			if (head.val < x) {
				beforeCurr.next = head;
				beforeCurr = beforeCurr.next;
			} else {
				afterCurr.next = head;
				afterCurr = afterCurr.next;
			}
			head = head.next;
		}
		
		beforeCurr.next = after.next;
		after.next = null;
		return before.next;
	}
	

	public static Node removeDup(Node head) {
		if (head == null || head.next == null)
			return head;
		
		Node dummy = new Node(0);
		dummy.next = head;
		Node walker = dummy;
		while (walker.next != null && walker.next.next != null) {
			if (walker.next.val == walker.next.next.val) {
				int num = walker.next.val;
				while (walker.next != null && walker.next.val ==  num) {
					walker.next = walker.next.next;
				}
			} else
				walker = walker.next;
		}
		return dummy.next;
	}
	
	public static Node reverse1(Node head, int left, int right) {
		Node walker = head;
		Node prev = null;
		Node leftwalker = null;
		Node rightwalker = null;
		Node forward = null;
		for (int i = 1; i < left-1; i++) {
			walker = walker.next;
		}
		prev = walker;
		leftwalker = prev.next;
		prev.next = null;
		walker = leftwalker;
		for (int i = 0; i < right - left; i++) {
			walker = walker.next;
		}
		rightwalker = walker;
		forward = walker.next;
		rightwalker.next = null;
		rightwalker = reverseList(leftwalker);
		prev.next = rightwalker;
		leftwalker.next = forward;
		return head;
	}

	
	public static Node reverse(Node head, int left, int right) {
		Node dummy = new Node(0);
		dummy.next = head;
		Node prev = dummy;
		
		for (int i = 0; i < left-1; i++) {
			prev = prev.next;
		}
		Node curr = prev.next;
		for (int i = 0; i < right-left; i++) {
			Node temp = curr.next;
			curr.next = temp.next;
			temp.next = prev.next;
			prev.next = temp;
		}
		
		return dummy.next;
	}
	
	public static boolean loop(Node head) {
		Node walker = head;
		int count = 0;
		while (walker != null) {
			if (count > 10)
				return true;
			walker = walker.next;
			count++;
		}
		return false;
	}
	
	public static Node nthNode(Node head) {
		Node walker = head;
		while (walker.next != null)
			walker = walker.next;
		return walker;
	}
	
	public static void removeDuplicates(Node head) {
		Node walker = head;
		while (walker != null) {
			if (walker.val == walker.next.val) {
				walker.next = walker.next.next;
			}
			walker = walker.next;
		}
	}
	public static Node middleNode(Node head) {
		Node walker = head;
		int count = 0;
		while (count < 5/2) {
			walker = walker.next;
			count++;
		}
		return walker;
	}
	
	public boolean isPalindrome(Node head) {
        if (head == null || head.next == null) return true;

        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        Node rightHalf = reverseList(slow);

        Node p1 = head;
        Node p2 = rightHalf;
        while (p2 != null) {
            if (p1.val != p2.val) {
                return false;
            }
            p1 = p1.next;
            p2 = p2.next;
        }
        return true;
    }
	
	public static Node reverseList(Node head) {
		Node walker = head;
		Node prev = null;
		while (walker != null) {
			Node ahead = walker.next;
			walker.next = prev;
			prev = walker;
			walker = ahead;
		}
		return head = prev;
	}
	
	public static Node RandomCopy(Node head) {
		if (head == null)
			return null;
		HashMap<Node, Node> map = new HashMap<>();
		Node walker = head;
		while (walker != null) {
			map.put(walker, new Node(walker.val));
			walker = walker.next;
		}
		
		walker = head;
		while (walker != null) {
			Node newHead = map.get(walker);
			newHead.next = map.get(walker.next);
			newHead.random = map.get(walker.random);
			walker = walker.next;
		}
		return map.get(head);
	}
	
	public static Node copyList(Node head) {
		Node newHead = new Node(head.val);
		Node newCopy = newHead;
		Node walker = head.next;
		while (walker != null) {
			Node copy = new Node(walker.val);
			newCopy.next = copy;
			newCopy = copy;
			walker = walker.next;
		}
		return newHead;
	}
}
