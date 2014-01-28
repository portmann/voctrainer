package com.lisilab.io.datashop;

/*
 * Carnegie Mellon University, Human Computer Interaction Institute
 * Copyright 2009
 * All Rights Reserved
 */

/**
 * Holds a subset of data from get transactions request to the DataShop web services.
 * Expecting to get 5 columns:
 *     problem hierarchy, problem name, step name, outcome and input.
 */
public class TransactionDataSubset implements Comparable {

    private String problemHierarchy;
    private String problem;
    private String step;
    private String outcome;
    private String input;

    public static TransactionDataSubset createTransaction(String row) {
        String[] rowArray = row.split("\t");
        if (rowArray.length != 5) {
            return null;
        }
        String ph = rowArray[0];
        String p = rowArray[1];
        String step = rowArray[2];
        String outcome = rowArray[3];
        String input = rowArray[4];
        return new TransactionDataSubset(ph, p, step, outcome, input);
    }

    public TransactionDataSubset(String problemHierarchy, String problem, String step,
            String outcome, String input) {
        this.problemHierarchy = problemHierarchy.replace(',', ' ');
        this.problem = problem.replace(',', ' ');
        this.step = step.replace(',', ' ');
        this.outcome = outcome;
        this.input = input;
    }

    public String getProblemHierarchy() {
        return problemHierarchy;
    }
    public String getProblem() {
        return problem;
    }
    public String getStep() {
        return step;
    }
    public String getOutcome() {
        return outcome;
    }
    public String getInput() {
        return input;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(problemHierarchy);
        buffer.append(" Problem ");
        buffer.append(problem);
        buffer.append(" Step ");
        buffer.append(step);
        buffer.append(" : ");
        buffer.append(outcome);
        buffer.append(" : ");
        buffer.append(input);

        return buffer.toString();
    }

    public int compareTo(Object obj) {
        TransactionDataSubset otherObject = (TransactionDataSubset)obj;

        int value = 0;

        value = objectCompareTo(this.getProblemHierarchy(), otherObject.getProblemHierarchy());
        if (value != 0) { return value; }

        value = objectCompareTo(this.getProblem(), otherObject.getProblem());
        if (value != 0) { return value; }

        value = objectCompareTo(this.getStep(), otherObject.getStep());
        if (value != 0) { return value; }

        value = objectCompareTo(this.getOutcome(), otherObject.getOutcome());
        if (value != 0) { return value; }

        value = objectCompareTo(this.getInput(), otherObject.getInput());
        if (value != 0) { return value; }

        return value;
    }

    protected <T> int objectCompareTo(Comparable<T> one, Comparable<T> two) {
        if ((one != null) && (two != null)) {
            return one.compareTo((T)two);
        } else if (one != null) {
            return 1;
        } else if (two != null) {
            return -1;
        }
        return 0;
    }
}