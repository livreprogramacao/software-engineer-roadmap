package br.com.livreprogramacao.example.algebraicdatatype.magnussmith.patternmatching;

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
