package br.com.livreprogramacao.example.algebraicdatatype.openjdk.geometry;

import br.com.livreprogramacao.example.algebraicdatatype.openjdk.polar.Circle;
import br.com.livreprogramacao.example.algebraicdatatype.openjdk.quad.Rectangle;
import br.com.livreprogramacao.example.algebraicdatatype.openjdk.quad.simple.Square;

public abstract sealed class Shape
        permits Circle,
                Rectangle,
                Square,
        WeirdShape {
}
