package ExpressionEval;

public class GenericStack<E> {
    private final int STACK_SIZE = 10;

    private int size = 0;

    private final E[] list = (E[]) new Object[STACK_SIZE];

    public int getSize() {
        return size;
    }

    // returns the object at the top of the stack
    public E peek() {
        for (int i = 0; i < STACK_SIZE; i++) {
            if (list[i + 1] == null) // if next number in the list is null then return current number at the top of stack
            {
                return list[i];
            }
        }
        return null;
    }

    // removes the object at the top of the stack
    public E pop() {
        if (isEmpty())
            return null;

        for (int i = 0; i < STACK_SIZE; i++) {
            if (list[i + 1] == null) {
                E popped = list[i];
                list[i] = null;
                return popped;
            }
        }
        size--;
        return null;
    }

    // adds an object to the top of the stack
    public void push(E o) {
        for (int i = 0; i < STACK_SIZE; i++) {
            if (list[i] == null) {
                list[i] = o;
                break;
            }
        }
        size++;
    }

    // checks if the stack is empty
    public boolean isEmpty() {
        return list[0] == null;
    }
}