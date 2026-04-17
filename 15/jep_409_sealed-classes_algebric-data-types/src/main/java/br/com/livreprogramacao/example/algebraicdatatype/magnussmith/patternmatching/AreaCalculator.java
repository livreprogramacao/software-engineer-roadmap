package br.com.livreprogramacao.example.algebraicdatatype.magnussmith.patternmatching;

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
