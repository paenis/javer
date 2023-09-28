public class Quadratic {
    double a;
    double b;
    double c;

    public Quadratic() {
        a = 0;
        b = 0;
        c = 0;
    }

    public void setA(double a) {
        this.a = a;
    }

    public void setB(double b) {
        this.b = b;
    }

    public void setC(double c) {
        this.c = c;
    }

    public void setCoefficients(double a, double b, double c) {
        setA(a);
        setB(b);
        setC(c);
    }

    public static Quadratic sum(Quadratic q1, Quadratic q2) {
        Quadratic sum = new Quadratic(); // i love garbage collector
        sum.setCoefficients(q1.a + q2.a, q1.b + q2.b, q1.c + q2.c);
        return sum;
    }

    public static Quadratic scale(double scale, Quadratic q) {
        Quadratic scaled = new Quadratic();
        scaled.setCoefficients(scale * q.a, scale * q.b, scale * q.c);
        return scaled;
    }
}
