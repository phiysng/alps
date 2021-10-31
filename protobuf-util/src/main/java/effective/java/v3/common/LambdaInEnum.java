package effective.java.v3.common;

public class LambdaInEnum {
    public static void main(String[] args) {
        System.out.println(Operation.PLUS.apply(1, 2));
        System.out.println(Operation.MINUS.apply(1, 2));
        System.out.println(Operation.MULTIPLY.apply(1, 2));
        System.out.println(Operation.DIVIDE.apply(1, 2));
    }

}

