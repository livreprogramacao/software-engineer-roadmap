package br.com.livreprogramacao.example.algebraicdatatype.openjdk.expression;

public sealed interface Expr
        permits ConstantExpr, PlusExpr, TimesExpr, NegExpr {
}
