package br.com.livreprogramacao.example.algebraicdatatype.magnussmith.pattern;

import java.util.List;

// Algebraic Data Types and Pattern Matching with Java
// com.scottlogic.blog.

class Shapes {

    static double area(Shape shape) {
        return switch (shape) {
            case Circle(double radius) -> Math.PI * radius * radius;
            case Rectangle(double width, double height) -> width * height;
            case Triangle(double side1, double side2, double side3) -> {
                double s = (side1 + side2 + side3) / 2;
                yield Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));
            }
            case Pentagon(double side) -> (0.25) * Math.sqrt(5 * (5 + 2 * Math.sqrt(5))) * side * side;
        };
    }

    static double perimeter(Shape shape) {
        return switch (shape) {
            case Circle(double radius) -> 2 * Math.PI * radius;
            case Rectangle(double width, double height) -> 2 * (width + height);
            case Triangle(double side1, double side2, double side3) -> side1 + side2 + side3;
            case Pentagon(double side) -> 5 * side;
        };
    }

    static String info(Shape shape) {
        return switch (shape) {
            case Circle c ->
                    "Circle with radius: %.2f, area: %.2f, perimeter: %.2f".formatted(c.radius(), area(c), perimeter(c));
            case Rectangle r ->
                    "Rectangle with width: %.2f , height: %.2f, area: %.2f, perimeter: %.2f".formatted(r.width(), r.height(), area(r), perimeter(r));
            case Triangle t ->
                    "Triangle with sides: %.2f, %.2f, %.2f, area: %.2f, perimeter: %.2f".formatted(t.side1(), t.side2(), t.side3(), area(t), perimeter(t));
            case Pentagon p ->
                    "Pentagon with side: %.2f, area: %.2f, perimeter: %.2f".formatted(p.side(), area(p), perimeter(p));
        };
    }

    static Shape scale(Shape shape, double scaleFactor) {
        return switch (shape) {
            case Circle c -> new Circle(c.radius() * scaleFactor);
            case Rectangle r -> new Rectangle(r.width() * scaleFactor, r.height() * scaleFactor);
            case Triangle t -> new Triangle(t.side1() * scaleFactor, t.side2() * scaleFactor, t.side3() * scaleFactor);
            case Pentagon p -> new Pentagon(p.side() * scaleFactor);
        };
    }

    public static void main(String[] args) {
        List<Shape> shapes = List.of(new Circle(5), new Triangle(3, 3, 3), new Rectangle(3, 5), new Pentagon(5.6));

        System.out.println("\nShapes: " + shapes);
        shapes.stream().map(Shapes::info).forEach(System.out::println);

        System.out.println("\nShapes : " + shapes + " scaled by 2");
        shapes.stream().map(s -> Shapes.scale(s, 2)).map(Shapes::info).forEach(System.out::println);
    }

}