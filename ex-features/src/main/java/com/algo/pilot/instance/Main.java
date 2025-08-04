package com.algo.pilot.instance;

public class Main {
    public static void main(String[] args){
        Shape shape = new Triangle();
        Triangle triangle = new Triangle();
        IsoscelesTriangle isoscelesTriangle = new IsoscelesTriangle();
        Shape nonspecificShape = null;

        assert (shape instanceof Shape);
        assert(shape instanceof Shape);
        assert(triangle instanceof Shape);
        assert(isoscelesTriangle instanceof Shape);
        assert (nonspecificShape instanceof Shape);

        assert(shape instanceof Triangle);
        assert(triangle instanceof Triangle);
        assert(isoscelesTriangle instanceof Triangle);
//        assertFalse(nonspecificShape instanceof Triangle);

//        assertFalse(shape instanceof IsoscelesTriangle);
//        assertFalse(triangle instanceof IsoscelesTriangle);
        assert(isoscelesTriangle instanceof IsoscelesTriangle);
//        assertFalse(nonspecificShape instanceof IsoscelesTriangle);
    }
}
