package swm.spring.springdi.ioc;

public class A {
    private B b;

    // IoC 전
    public A() {
        this.b = new B();
    }

    // IoC 후
    public A(B b) {
        this.b = b;
    }
}
