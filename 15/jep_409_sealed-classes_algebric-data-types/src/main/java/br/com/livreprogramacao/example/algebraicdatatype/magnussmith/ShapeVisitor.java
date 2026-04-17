package br.com.livreprogramacao.example.algebraicdatatype.magnussmith;

import java.util.List;

// Algebraic Data Types and Pattern Matching with Java
// com.scottlogic.blog.

// Visitor interface
interface ShapeVisitor<T> {
    T visit(Circle circle);
    T visit(Rectangle rectangle);
    T visit(Triangle triangle);
    T visit(Pentagon pentagon);
}

sealed interface Shape permits Circle, Rectangle, Triangle, Pentagon {
    <T> T accept(ShapeVisitor<T> visitor);
}

record Circle(double radius) implements Shape {
    @Override
    public <T> T accept(ShapeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

record Rectangle(double width, double height) implements Shape {
    @Override
    public <T> T accept(ShapeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

record Triangle(double side1, double side2, double side3) implements Shape {
    @Override
    public <T> T accept(ShapeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

record Pentagon(double side) implements Shape {
    @Override
    public <T> T accept(ShapeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}

// Visitor for calculating area
class AreaCalculator implements ShapeVisitor<Double> {
    @Override
    public Double visit(Circle circle) {
        return Math.PI * circle.radius() * circle.radius();
    }

    @Override
    public Double visit(Rectangle rectangle) {
        return rectangle.width() * rectangle.height();
    }

    @Override
    public Double visit(Triangle triangle) {
        double s = (triangle.side1() + triangle.side2() + triangle.side3()) / 2;
        return Math.sqrt(s * (s - triangle.side1()) * (s - triangle.side2()) * (s - triangle.side3()));
    }

    @Override
    public Double visit(Pentagon pentagon) {
        return (0.25) * Math.sqrt(5 * (5 + 2 * Math.sqrt(5))) * pentagon.side() * pentagon.side();
    }
}

// Visitor for calculating perimeter
class PerimeterCalculator implements ShapeVisitor<Double> {
    @Override
    public Double visit(Circle circle) {
        return 2 * Math.PI * circle.radius();
    }

    @Override
    public Double visit(Rectangle rectangle) {
        return 2 * (rectangle.width() + rectangle.height());
    }

    @Override
    public Double visit(Triangle triangle) {
        return triangle.side1() + triangle.side2() + triangle.side3();
    }

    @Override
    public Double visit(Pentagon pentagon) {
        return 5 * pentagon.side();
    }
}

// Visitor for getting shape info
class InfoVisitor implements ShapeVisitor<String> {
    @Override
    public String visit(Circle circle) {
        return "Circle with radius: %.2f, area: %.2f, perimeter: %.2f".formatted(circle.radius(), new AreaCalculator().visit(circle), new PerimeterCalculator().visit(circle));
    }

    @Override
    public String visit(Rectangle rectangle) {
        return "Rectangle with width: %.2f , height: %.2f, area: %.2f, perimeter: %.2f".formatted(rectangle.width(), rectangle.height(), new AreaCalculator().visit(rectangle), new PerimeterCalculator().visit(rectangle));
    }

    @Override
    public String visit(Triangle triangle) {
        return "Triangle with sides: %.2f, %.2f, %.2f, area: %.2f, perimeter: %.2f".formatted(triangle.side1(), triangle.side2(), triangle.side3(), new AreaCalculator().visit(triangle), new PerimeterCalculator().visit(triangle));
    }

    @Override
    public String visit(Pentagon pentagon) {
        return "Pentagon with side: %.2f, area: %.2f, perimeter: %.2f".formatted(pentagon.side(), new AreaCalculator().visit(pentagon), new PerimeterCalculator().visit(pentagon));
    }
}

// Visitor for scaling shapes
class ScaleVisitor implements ShapeVisitor<Shape> {
    private final double scaleFactor;

    public ScaleVisitor(double scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    @Override
    public Shape visit(Circle circle) {
        return new Circle(circle.radius() * scaleFactor);
    }

    @Override
    public Shape visit(Rectangle rectangle) {
        return new Rectangle(rectangle.width() * scaleFactor, rectangle.height() * scaleFactor);
    }

    @Override
    public Shape visit(Triangle triangle) {
        return new Triangle(triangle.side1() * scaleFactor, triangle.side2() * scaleFactor, triangle.side3() * scaleFactor);
    }

    @Override
    public Shape visit(Pentagon pentagon) {
        return new Pentagon(pentagon.side() * scaleFactor);
    }
}

class Shapes {
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

