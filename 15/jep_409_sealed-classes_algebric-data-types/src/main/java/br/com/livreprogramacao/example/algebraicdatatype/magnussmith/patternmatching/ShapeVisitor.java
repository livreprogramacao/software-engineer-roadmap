package br.com.livreprogramacao.example.algebraicdatatype.magnussmith.patternmatching;

// Visitor interface
interface ShapeVisitor<T> {
    T visit(Circle circle);

    T visit(Rectangle rectangle);

    T visit(Triangle triangle);

    T visit(Pentagon pentagon);
}
