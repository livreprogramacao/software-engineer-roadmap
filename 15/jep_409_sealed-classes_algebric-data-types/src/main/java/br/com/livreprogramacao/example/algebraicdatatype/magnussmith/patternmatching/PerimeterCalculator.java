package br.com.livreprogramacao.example.algebraicdatatype.magnussmith.patternmatching;

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
