package main.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class Polinom {
    private TreeSet<Monom> pieces;

    private TreeMap<Integer, Float> createPolinom(String polinom) {
        TreeMap<Integer, Float> values = new TreeMap<>();
        float nr = 0;
        int grad = 0;
        String polinomToSplit = polinom.replaceAll(" ", "").replaceAll("\\*", "").replaceAll("-", "+-");
        String[] monoms = polinomToSplit.split("\\+");
        for (String monom : monoms) {
            if (monom.length() != 0) {
                String modifiedMonom = monom.replaceAll("^-x", "-1x").replaceAll("^x", "1x").replaceAll("x$", "x^1").replaceAll("^-?\\d+\\.?\\d*$", monom + "x^0").replaceAll("^\\d+$", monom + "x^0");
                String[] secondSplit = modifiedMonom.split("(x\\^)");

                if (secondSplit.length == 2) {
                    if (secondSplit[0].replaceAll("^-?\\d*\\.?\\d*", "").equals("") && secondSplit[1].replaceAll("^-?\\d+", "").equals("")) {
                        nr = Float.parseFloat(secondSplit[0]);
                        grad = Integer.parseInt(secondSplit[1]);
                    } else {
                        System.out.println("Polinomul introdus este incorect");
                        return new TreeMap<>();
                    }
                } else {
                    System.out.println("Polinomul introdus este incorect");
                    return new TreeMap<>();
                }
                removeGrad(values, grad, nr);
            }
        }
        if (grad == 0) {
            if (values.size() == 0 && monoms.length == 1) {
                values.put(grad, nr);
            }
        }
        return values;
    }

    public static Polinom addition(Polinom p1, Polinom p2) {
        Polinom p3;
        if (p2.toString().charAt(0) == '-') {
            p3 = new Polinom(p1.toString() + p2.toString());
        } else {
            p3 = new Polinom(p1.toString() + "+" + p2.toString());
        }

        return p3;
    }

    public static Polinom substraction(Polinom p1, Polinom p2) {
        Polinom p3, p4 = new Polinom(p2.toString());
        for (Monom piece : p4.getPieces()) {
            piece.setNumar(-piece.getNumar());
            piece.setGrad(piece.getGrad());
        }
        //System.out.println(p4);
        p3 = Polinom.addition(p1, p4);
        return p3;
    }

    public static Polinom multiplication(Polinom p1, Polinom p2) {
        Polinom p3 = new Polinom();
        TreeMap<Integer, Float> values = new TreeMap<>();
        float nr;
        int grad;
        //System.out.println(p3);
        for (Monom piece : p2.getPieces()) {
            for (Monom part : p1.getPieces()) {
                nr = piece.getNumar() * part.getNumar();
                grad = piece.getGrad() + part.getGrad();
                removeGrad(values, grad, nr);
            }
        }
        if (values.size() == 0) {
            values.put(0, 0f);
        }
        for (Map.Entry<Integer, Float> value : values.entrySet()) {
            p3.getPieces().add(new Monom(value.getValue(), value.getKey()));
        }
        return p3;
    }

    public static ArrayList<Polinom> division(Polinom p1, Polinom p2) {
        ArrayList<Polinom> list = new ArrayList<>();
        Polinom remainder = new Polinom(p1.toString());
        Polinom result = new Polinom("0");
        Polinom polinomToSubstract;
        int difference = remainder.getPieces().first().getGrad() - p2.getPieces().first().getGrad();
        float division;
        while (remainder.getPieces().size() > 0 && difference >= 0) {
            division = remainder.getPieces().first().getNumar() / p2.getPieces().first().getNumar();
            polinomToSubstract = new Polinom(String.format("%.2fx^%d", -division, difference));
            polinomToSubstract = Polinom.multiplication(p2, polinomToSubstract);
            remainder = Polinom.addition(remainder, polinomToSubstract);
            if (!result.getPieces().add(new Monom(division, difference))) {
                result.getPieces().last().setNumar(division);
            }
            if (remainder.getPieces().size() != 0 && (difference+p2.getPieces().first().getGrad() == remainder.getPieces().first().getGrad()) && remainder.getPieces().first().getNumar()<1) {
                remainder.getPieces().remove(remainder.getPieces().first());
            }
            if (remainder.getPieces().size() != 0) {
                difference = remainder.getPieces().first().getGrad() - p2.getPieces().first().getGrad();
            }

        }
        if (result.getPieces().size() != 1 && result.getPieces().last().getNumar() == 0) {
            result.getPieces().remove(new Monom());
        }
        if (remainder.getPieces().size() == 0) {
            remainder = new Polinom("0");
        }
        list.add(result);
        list.add(remainder);
        return list;
    }

    public static Polinom derivate(Polinom p1) {
        Polinom p3 = new Polinom();
        float nr;
        int grad;
        for (Monom piece : p1.getPieces()) {
            nr = piece.getNumar();
            grad = piece.getGrad();
            if (grad != 0) {
                p3.getPieces().add(new Monom(nr * grad, grad - 1));
            } else if (p3.getPieces().size() == 0) {
                p3.getPieces().add(new Monom(0, 0));
            }
        }

        //System.out.println(p3);
        return p3;
    }

    public static Polinom integrate(Polinom p1) {
        Polinom p3 = new Polinom();
        float nr;
        int grad;
        for (Monom piece : p1.getPieces()) {
            nr = piece.getNumar();
            grad = piece.getGrad();
            if (grad != 0) {
                p3.getPieces().add(new Monom(nr / (grad + 1), grad + 1));
            } else if (nr != 0) {
                p3.getPieces().add(new Monom(nr / (grad + 1), grad + 1));
            } else if (p3.getPieces().size() == 0) {
                p3.getPieces().add(new Monom(0, 0));
            }
        }

        //System.out.println(p3);
        return p3;
    }

    private static void removeGrad(TreeMap<Integer, Float> values, int grad, float nr) {
        if (nr != 0) {
            if (values.containsKey(grad)) {
                values.replace(grad, values.get(grad) + nr);
                if (values.get(grad) == 0) {
                    values.remove(grad);
                }
            } else {
                values.put(grad, nr);
            }
        }
    }

    public Polinom(String polinom) {
        this.pieces = new TreeSet<>();
        TreeMap<Integer, Float> values = createPolinom(polinom);
        for (Map.Entry<Integer, Float> value : values.entrySet()) {
            this.pieces.add(new Monom(value.getValue(), value.getKey()));
        }
    }

    public Polinom() {
        this.pieces = new TreeSet<>();
    }

    public TreeSet<Monom> getPieces() {
        return pieces;
    }

    @Override
    public String toString() {
//        ArrayList<String> show = new ArrayList<>();
//        show.add("");
        String man = "";
        for (Monom piece : pieces) {
            man = man + piece.toString();
            //show.add(show + piece.toString());
        }
        man = man.replaceAll("^\\+\\s", "").replaceAll("-", "- ").replaceAll("^-\\s", "-");

        return man;
    }

    public String toString0() {
//        ArrayList<String> show = new ArrayList<>();
//        show.add("");
        String man = "";
        for (Monom piece : pieces) {
            man = man + piece.toString0();
            //show.add(show + piece.toString());
        }
        man = man.replaceAll("^\\+\\s", "").replaceAll("-", "- ").replaceAll("^-\\s", "-");

        return man;
    }

}
