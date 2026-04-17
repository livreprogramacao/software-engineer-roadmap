package br.com.livreprogramacao.example.algebraicdatatype.magnussmith.patternmatching;

record Triangle(double side1, double side2, double side3) implements Shape {
    @Override
    public <T> T accept(ShapeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
