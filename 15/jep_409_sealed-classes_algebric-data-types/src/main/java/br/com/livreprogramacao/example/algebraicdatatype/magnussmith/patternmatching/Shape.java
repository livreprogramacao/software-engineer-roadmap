package br.com.livreprogramacao.example.algebraicdatatype.magnussmith.patternmatching;

sealed interface Shape permits Circle, Rectangle, Triangle, Pentagon {
    <T> T accept(ShapeVisitor<T> visitor);
}
