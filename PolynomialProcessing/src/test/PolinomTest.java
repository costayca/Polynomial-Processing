package test;

import main.model.Polinom;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

class PolinomTest {
    private Polinom new1A = new Polinom("-x^5 -x^4 -2*x^3 + 3x^2 - x +5x-50 +x + 237 + 3x^4 -2x^3 +0x^6");
    private Polinom new2A = new Polinom("-2x^6+x^2 + 3x + 2 -3x^5");
    private Polinom new1B = new Polinom("3x^5 +x^3+2x+4");
    private Polinom new2B = new Polinom("x^3+3x^2+1");

    @Test
    void addition() {
        Polinom testA = new Polinom("-2x^6 - 4x^5 + 2x^4 - 4x^3 + 4x^2 + 8x + 189");
        Polinom testB = new Polinom("3x^5 + 2x^3 + 3x^2 + 2x + 5");
        assertEquals(Polinom.addition(new1A, new2A).toString(), testA.toString());
        assertEquals(Polinom.addition(new1B, new2B).toString(), testB.toString());
    }

    @Test
    void substraction() {
        Polinom testA = new Polinom("2x^6 + 2x^5 + 2x^4 - 4x^3 + 2x^2 + 2x + 185 ");
        Polinom testB = new Polinom("3x^5 - 3x^2 + 2x + 3 ");
        assertEquals(Polinom.substraction(new1A, new2A).toString(), testA.toString());
        assertEquals(Polinom.substraction(new1B, new2B).toString(), testB.toString());
    }

    @Test
    void multiplication() {
        Polinom testA = new Polinom("2x^11 - x^10 + 2x^9 + 6x^8 - 20x^7 - 390x^6 - 561x^5 - 5x^4 + 6x^3 + 208x^2 + 571x + 374 ");
        Polinom testB = new Polinom("3x^8 + 9x^7 + x^6 + 6x^5 + 2x^4 + 11x^3 + 12x^2 + 2x + 4 ");
        assertEquals(Polinom.multiplication(new1A, new2A).toString(), testA.toString());
        assertEquals(Polinom.multiplication(new1B, new2B).toString(), testB.toString());
    }

    @Test
    void division() {
        Polinom testA = new Polinom("0");
        Polinom testAR = new Polinom("-x^5 + 2x^4 - 4x^3 + 3x^2 + 5x + 187");
        Polinom testB = new Polinom("3x^2-9x+28");
        Polinom testBR = new Polinom("-87x^2+11x-24");
        ArrayList<Polinom> list = Polinom.division(new1A, new2A);
        assertEquals(list.get(0).toString(), testA.toString());
        assertEquals(list.get(1).toString(), testAR.toString());
        list = Polinom.division(new1B, new2B);
        assertEquals(list.get(0).toString(), testB.toString());
        assertEquals(list.get(1).toString(), testBR.toString());
    }

    @Test
    void derivate() {
        Polinom testA = new Polinom("-5x^4 + 8x^3 - 12x^2 + 6x + 5");
        Polinom testB = new Polinom("15x^4+3x^2+2");
        assertEquals(Polinom.derivate(new1A).toString(), testA.toString());
        assertEquals(Polinom.derivate(new1B).toString(), testB.toString());
    }

    @Test
    void integrate() {
        Polinom testA = new Polinom("-0.17x^6 + 0.40x^5 - x^4 + x^3 + 2.50x^2 + 187x");
        Polinom testB = new Polinom("0.50x^6 + 0.25x^4 + x^2 + 4x");
        assertEquals(Polinom.integrate(new1A).toString(), testA.toString());
        assertEquals(Polinom.integrate(new1B).toString(), testB.toString());
    }
}