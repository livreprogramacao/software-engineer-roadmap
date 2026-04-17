package br.com.livreprogramacao.example.algebraicdatatype.openjdk.quad;

import br.com.livreprogramacao.example.algebraicdatatype.openjdk.geometry.Shape;

public sealed class Rectangle extends Shape
        permits TransparentRectangle, FilledRectangle {
}
