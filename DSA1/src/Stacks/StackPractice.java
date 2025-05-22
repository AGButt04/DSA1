package Stacks;
import java.util.Stack;

public class StackPractice {
	public static void main(String[] args) {
		String[] tokens = {"4","13","5","/","+"};
		System.out.println(evalRPN(tokens));
	}
	
	public static int evalRPN(String[] tokens) {
		Stack<Integer> stack = new Stack<>();
		for (String s : tokens) {
			int sum = 0;
			int right = 0;
			int left = 0;
			switch(s) {
			case "+":
				sum = stack.pop() + stack.pop();
				stack.push(sum);
				break;
			case "-":
				right = stack.pop();
				left  = stack.pop();
				sum = left - right;
				stack.push(sum);
				break;
			case "/":
				right = stack.pop();
				left  = stack.pop();
				sum = left / right;
				stack.push(sum);
				break;
			case "*":
				sum = stack.pop() * stack.pop();
				stack.push(sum);
				break;
			default:
				stack.push(Integer.parseInt(s));
			}
		}
		return stack.pop();
	}
}
