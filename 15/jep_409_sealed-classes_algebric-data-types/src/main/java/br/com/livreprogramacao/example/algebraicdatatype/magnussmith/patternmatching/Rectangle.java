package br.com.livreprogramacao.example.algebraicdatatype.magnussmith.patternmatching;

record Rectangle(double width, double height) implements Shape {
    @Override
    public <T> T accept(ShapeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
