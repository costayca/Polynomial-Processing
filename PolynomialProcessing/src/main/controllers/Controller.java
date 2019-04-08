package main.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import main.model.Monom;
import main.model.Polinom;

import java.util.ArrayList;

public class Controller {

    @FXML
    private HBox division;

    @FXML
    private TextField result;

    @FXML
    private TextField remainder;

    @FXML
    private TextField p1;

    @FXML
    private TextField p2;

    public Controller() {
    }

    private boolean alertNull(String s1, int nr) {
        if (s1.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("String too short");
            alert.setContentText(String.format("Polynomial #%d must contain a string", nr));

            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean alertZero(Polinom pol1, int nr) {
        if (pol1.getPieces().size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid String");
            alert.setContentText(String.format("Polynomial #%d doesn't contain a valid string", nr));

            alert.showAndWait();
            return false;
        }
        return true;
    }

    public void doTwoPolynomialOperation(int operationNumber) {
        division.setVisible(false);
        String s1 = p1.getText();
        String s2 = p2.getText();
        if (alertNull(s1, 1) && alertNull(s2, 2)) {
            Polinom pol1 = new Polinom(s1);
            Polinom pol2 = new Polinom(s2);
            if (alertZero(pol1, 1) && alertZero(pol2, 2)) {
                switch (operationNumber) {
                    case 1:
                        result.setText(Polinom.addition(pol1, pol2).toString());
                        break;
                    case 2:
                        result.setText(Polinom.substraction(pol1, pol2).toString());
                        break;
                    case 3:
                        result.setText(Polinom.multiplication(pol1, pol2).toString());
                        break;
                    case 4:
                        boolean ok = true;
                        for (Monom monom : pol2.getPieces()) {
                            if (monom.getNumar() == 0) {
                                ok = false;
                                break;
                            }
                        }
                        if (ok) {
                            division.setVisible(true);
                            ArrayList<Polinom> list = Polinom.division(pol1, pol2);
                            result.setText(list.get(0).toString());
                            remainder.setText(list.get(1).toString());
                        } else {
                            alertZero(new Polinom(), 2);
                        }
                        break;
                }


            }
        }
    }

    public void doOnePolynomialOperation(int operationNumber) {
        division.setVisible(false);
        String s1 = p1.getText();
        if (alertNull(s1, 1)) {
            Polinom pol1 = new Polinom(s1);
            if (alertZero(pol1, 1)) {
                switch (operationNumber) {
                    case 1:
                        result.setText(Polinom.derivate(pol1).toString());
                        break;
                    case 2:
                        result.setText(Polinom.integrate(pol1).toString());
                        break;
                }
            }
        }
    }

    public void handleAddition(ActionEvent event) {
        doTwoPolynomialOperation(1);
    }

    public void handleSubtraction(ActionEvent event) {
        doTwoPolynomialOperation(2);
    }

    public void handleMultiplication(ActionEvent event) {
        doTwoPolynomialOperation(3);
    }

    public void handleDivision(ActionEvent event) {
        doTwoPolynomialOperation(4);
    }

    public void handleDerivation(ActionEvent event) {
        doOnePolynomialOperation(1);
    }

    public void handleIntegration(ActionEvent event) {
        doOnePolynomialOperation(2);
    }
}
