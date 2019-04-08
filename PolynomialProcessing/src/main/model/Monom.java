package main.model;

public class Monom implements Comparable {
    private float numar;
    private int grad;

    public Monom(float numar, int grad) {
        this.grad = grad;
        this.numar = numar;
    }

    public Monom() {
        new Monom(0f, 0);
    }

    public float getNumar() {
        return numar;
    }

    public void setNumar(float numar) {
        this.numar = numar;
    }

    public int getGrad() {
        return grad;
    }

    public void setGrad(int grad) {
        this.grad = grad;
    }

    @Override
    public int compareTo(Object o) {
        return Integer.compare(((Monom) o).grad, this.grad);
    }

    @Override
    public String toString() {
        if (Math.floor(numar) != numar) {
            if (numar >= 0) {
                if (grad == 0) {
                    return String.format("+ %.2f ", numar);
                } else if (grad == 1) {
                    return String.format("+ %.2fx ", numar);
                } else {
                    return String.format("+ %.2fx^%d ", numar, grad);
                }
            } else {
                if (grad == 0) {
                    return String.format("%.2f ", numar);
                } else if (grad == 1) {
                    return String.format("%.2fx ", numar);
                } else {
                    return String.format("%.2fx^%d ", numar, grad);
                }
            }
        } else {
            return toString0();
        }
    }

    public String toString0() {
        if (numar >= 0) {
            if (numar == 1) {
                if (grad == 0) {
                    return String.format("+ %.0f ", numar);
                } else if (grad == 1) {
                    return String.format("+ x ");
                } else {
                    return String.format("+ x^%d ", grad);
                }
            } else {
                if (grad == 0) {
                    return String.format("+ %.0f ", numar);
                } else if (grad == 1) {
                    return String.format("+ %.0fx ", numar);
                } else {
                    return String.format("+ %.0fx^%d ", numar, grad);
                }
            }
        } else {
            if (numar == -1) {
                if (grad == 0) {
                    return String.format("%.0f ", numar);
                } else if (grad == 1) {
                    return String.format("-x ");
                } else {
                    return String.format("-x^%d ", grad);
                }
            } else {
                if (grad == 0) {
                    return String.format("%.0f ", numar);
                } else if (grad == 1) {
                    return String.format("%.0fx ", numar);
                } else {
                    return String.format("%.0fx^%d ", numar, grad);
                }
            }
        }
    }
}
