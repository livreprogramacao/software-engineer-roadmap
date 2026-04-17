package br.com.livreprogramacao.example.algebraicdatatype.magnussmith.patternmatching;

import java.util.List;

// Algebraic Data Types and Pattern Matching with Java
// com.scottlogic.blog.

public class Shapes {
    public static void main(String[] args) {
        List<Shape> shapes = List.of(new Circle(5), new Triangle(3, 3, 3), new Rectangle(3, 5), new Pentagon(5.6));

        InfoVisitor infoVisitor = new InfoVisitor();
        ScaleVisitor scaleVisitor = new ScaleVisitor(2);

        System.out.println("\nShapes:");
        shapes.stream().map(s -> s.accept(infoVisitor)).forEach(System.out::println);

        System.out.println("\nShapes scaled by 2:");
        shapes.stream().map(s -> s.accept(scaleVisitor)).map(s -> s.accept(infoVisitor)).forEach(System.out::println);
    }
}

