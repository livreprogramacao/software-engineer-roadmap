package br.com.livreprogramacao.example.algebraicdatatype.magnussmith.patternmatching;

record Pentagon(double side) implements Shape {
    @Override
    public <T> T accept(ShapeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
