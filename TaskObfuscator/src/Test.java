public class Test {
    public static void main(String[] args) {
        int age = 19;
        String name = "Nikolai";
        int height = 181;
        System.out.println(testFunction(age, height));
        System.out.println(age + height);
    }
    public static int testFunction(int a, int b){
        return a + b;
    }
}
