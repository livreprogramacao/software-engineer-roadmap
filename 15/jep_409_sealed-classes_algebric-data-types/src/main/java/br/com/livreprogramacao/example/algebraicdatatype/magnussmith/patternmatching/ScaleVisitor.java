package br.com.livreprogramacao.example.algebraicdatatype.magnussmith.patternmatching;

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
