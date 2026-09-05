/**
 * SOLID - You can see the acronym in the first letters.
 * 
 * Single Responsibility Principle      SRP
 * Open / Closed Principle              OCP
 * Liskov Substituition Principle       LSP
 * Interface Segregation Principle      ISP
 * Dependency Inverson Principle        DIP
 * 
 * Principles in order of usefulness - 'SDLOI'
 * 
 * Single Responsibility Principle      SRP
 * Dependency Inverson Principle        DIP
 * Liskov Substituition Principle       LSP
 * Open / Closed Principle              OCP
 * Interface Segregation Principle      ISP
 * 
 */

import java.util.ArrayList;

public class ShapeApp {
    void main() {
        System.out.println("Hello, World");

        var shapes = new ArrayList<Shape>();

        shapes.add( new Circle() );
        shapes.add( new Square() );
        shapes.add( new Triangle() );
        shapes.add( new CardDealer() );

        shapes.forEach(Shape::draw);
    }

}