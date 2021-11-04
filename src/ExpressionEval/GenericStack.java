package ExpressionEval;

public class GenericStack<E> {
    private int STACK_SIZE = 10;

    private E[] list = (E[]) new Object[STACK_SIZE];

    public int getSize(){
        return STACK_SIZE;
    }


    public E peek(){
        for (int i = 0; i < STACK_SIZE; i++) {
            if (list[i + 1] == null) // if next number in the list is null then return current number at the top of stack
            {
                return list[i];
            }
        }
        return null;
    }

    public E pop(){
        if (isEmpty())
            return null;

        for (int i = 0; i < STACK_SIZE; i++) {
            if (list[i + 1] == null)
            {
                E popped = list[i];
                list[i] = null;
                return popped;
            }
        }

        return null;
    }

    public void push(E o){
        for (int i = 0; i < STACK_SIZE; i++) {
            if (list[i] == null) {
                list[i] = o;
                break;
            }
        }
    }

    public boolean isEmpty(){
        if (list[0] == null) {
            return true;
        }
        return false;
    }
}