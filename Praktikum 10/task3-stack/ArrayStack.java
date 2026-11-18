import java.util.ArrayList;

public class ArrayStack<T> {
    private final int maxsize;
    private final ArrayList<T> items;

    public ArrayStack(int maxsize) {
        if (maxsize <= 0) {
            throw new ArrayStackException("Stack size must be positive");
        }
        this.maxsize = maxsize;
        this.items = new ArrayList<>();
    }

    public void push(T item) {
        if (items.size() == maxsize) {
            throw new ArrayStackException("Overflow Error");
        }
        items.add(item);
    }

    public T pop() {
        if (isEmpty()) {
            throw new ArrayStackException("Underflow Error");
        }
        return items.remove(items.size() - 1);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public static class ArrayStackException extends RuntimeException {
        public ArrayStackException(String message) {
            super(message);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Uji ArrayStack<Integer> ===");
        ArrayStack<Integer> numbers = new ArrayStack<>(3);
        numbers.push(1);
        numbers.push(2);
        numbers.push(3);
        System.out.println("Pop 1: " + numbers.pop());
        System.out.println("Pop 2: " + numbers.pop());
        System.out.println("Pop 3: " + numbers.pop());

        System.out.println("\n=== Uji ArrayStack<String> ===");
        ArrayStack<String> texts = new ArrayStack<>(2);
        texts.push("PBO");
        texts.push("Collection");
        System.out.println("Pop 1: " + texts.pop());
        System.out.println("Pop 2: " + texts.pop());

        System.out.println("\n=== Uji Overflow Error ===");
        try {
            ArrayStack<Integer> overflowStack = new ArrayStack<>(3);
            overflowStack.push(10);
            overflowStack.push(20);
            overflowStack.push(30);
            overflowStack.push(40); 
        } catch (ArrayStackException e) {
            System.out.println("Exception tertangkap: " + e.getMessage());
        }

        System.out.println("\n=== Uji Underflow Error ===");
        try {
            ArrayStack<String> underflowStack = new ArrayStack<>(2);
            underflowStack.push("Data");
            underflowStack.pop(); 
            underflowStack.pop();
        } catch (ArrayStackException e) {
            System.out.println("Exception tertangkap: " + e.getMessage());
        }
    }
}