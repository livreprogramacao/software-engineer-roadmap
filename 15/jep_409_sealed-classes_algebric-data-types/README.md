# README

lemma: "Make impossible state impossible." 

- [LinkedIn post](https://www.linkedin.com/posts/slissner_heres-my-favorite-functional-programming-ugcPost-7368542411259572227-UiSm?utm_source=share&utm_medium=member_desktop&rcm=ACoAAAGFx6kBHnkpQlhfgdUaoB0VdwLNQJHcGZo)


- [LinkedIn post](https://www.linkedin.com/search/results/all/?keywords=%22Algebraic%20Data%20Types%20%28ADTs%29%22&origin=GLOBAL_SEARCH_HEADER)

In computer programming, especially in functional programming and type theory, an algebraic data type (ADT) is a composite data type—a type formed by combining other types.

An algebraic data type is defined by two key constructions: a sum and a product. These are sometimes referred to as "OR" and "AND" types.

A sum type is a choice between possibilities. The value of a sum type can match one of several defined variants. For example, a type representing the state of a traffic light could be either Red, Amber, or Green. A shape type could be either a Circle (which stores a radius) or a Square (which stores a width). In formal terms, these variants are known as tagged unions or disjoint unions. Each variant has a name, called a constructor, which can also carry data. Enumerated types are a simple form of sum type where the constructors carry no data.

A product type combines types together. A value of a product type will contain a value for each of its component types. For example, a Point type might be defined to contain an x coordinate (an integer) and a y coordinate (also an integer). Formal examples of product types include tuples and records. The set of all possible values of a product type is the Cartesian product of the sets of its component types.

Values of algebraic data types are typically handled using pattern matching. This feature allows a programmer to check which constructor a value was made with and extract the data it contains in a convenient and type-safe way. 

- [algebraic data types Wikipedia post](https://en.wikipedia.org/wiki/Algebraic_data_type)



Stop writing `if (obj != null)` checks in every single method.

It's 2026, and we still spend hours debugging `NullPointerExceptions` that could have been caught at compile time. The solution isn't more tests; it's better type modeling.

Enter Algebraic Data Types (ADTs) and Pattern Matching. This isn't just functional programming jargon; it's the modern standard for clean, safe code across Java, Rust, TypeScript, and more.

💡 Did you know? Recent 2025 data suggests that Null Pointer Exceptions account for roughly 30% of all runtime crashes in Java-based production environments. One simple NPE in Google's Service Control system recently caused a 7-hour global outage. That's the cost of `null`.

ADTs let you model your data so that invalid states are unrepresentable. By using Enums (Sum Types) that carry data and Pattern Matching to handle them, you shift error detection from runtime to compile time.

🚀 Why make the switch?

✅ Exhaustiveness Checking: The compiler forces you to handle every possible case. No more forgotten `else` blocks.
✅ Type Safety: You can't accidentally pass a `null` where a value is expected.
✅ Readability: Logic flows naturally with `match` expressions instead of nested `if-else` ladders.

Whether you are adopting Sealed Classes in Java 21+, using Rust's powerful enums, or leveraging Discriminated Unions in TypeScript, ADTs are the key to reducing cognitive load and eliminating entire classes of bugs.

It's time to stop fighting the type system and start using it to your advantage.

How do you handle state modeling in your projects? Are you team `Optional` or team ADTs?

#CleanCode #TypeSafety #PatternMatching
#CleanCode,#TypeSafety,#PatternMatching,#SoftwareEngineering,#Java,#Rust,#TypeScript

Share your favorite ADT pattern in the comments below! 👇

- [LinkedIn post](https://www.linkedin.com/posts/rednithin_cleancode-typesafety-patternmatching-share-7438472939953508352-ZVnN?utm_source=share&utm_medium=member_desktop&rcm=ACoAAAGFx6kBHnkpQlhfgdUaoB0VdwLNQJHcGZo)






Over the past few months, Lead Developer Magnus Smith has penned a series of blogs exploring the various Java types and type systems. 
 
The series catalogues the various types in Java and Scala, exploring how they can be used to improve the readability and efficiency of your code. 
 
Swipe to see a summary of each blog and read through the links below: 
 
Algebraic Data Types and Pattern Matching: https://buff.ly/06bARXC
Variance in Generics, Phantom and Existential types: https://buff.ly/MWB3ap2
Intersection and Union types: https://buff.ly/UVnkprR
Functors and Monads: https://buff.ly/M8IduL5
Higher Kinded types: https://buff.ly/CAY7vk9

#Java #Scala #ADTs


- [LinkedIn post](https://www.linkedin.com/posts/java-types-ugcPost-7320356153169756160-pM8F?utm_source=share&utm_medium=member_desktop&rcm=ACoAAAGFx6kBHnkpQlhfgdUaoB0VdwLNQJHcGZo)
- [Algebraic Data Types and Pattern Matching with Java](https://blog.scottlogic.com/2025/01/20/algebraic-data-types-with-java.html)
- [code post](https://gist.githubusercontent.com/MagnusSmith/5ea4b7c85a862cfcfbb8dc4b67fc421d/raw/d5001bff0fee687902b9036efd566ee7ed86b4fd/Shapes.java)
- [code post](https://gist.githubusercontent.com/MagnusSmith/1299a2540158de978e7b66a2c1029f87/raw/591475e1f4f86aae8a34542ce4edde67373c954d/ShapesVisit.java)



Some quick reflections on my learnings from the past year(ish):

1. Data modelling
- The core aspect is structural modelling, which can often be expressed using Algebraic Data Types (ADTs) i.e. sum types and product types.
- Roughly speaking, sum types are unions (e.g., Scala3/TypeScript/Python union types, Scala3/Rust enums, Scala sealed traits, Protobuf oneofs) whereas product types correspond to structs and tuples.
- Not all programming languages provide native support for sum types. In such cases, sum types can be emulated using inheritance hierarchies or conditional branching.

2. Approaches to polymorphism
- In OOP, polymorphism is typically achieved through subtyping.
- Ad-hoc polymorphism is an alternative found in functional programming  e.g. type classes in Scala and traits in Rust. The main advantage imo is that it allows behavioural implementations to be decoupled from data model definitions.

3. Common patterns in async programming
- Callbacks.
- Java/Scala futures or JS promises as placeholders for results that do not yet exist.
- Kotlin/Go coroutines, C# tasks, Cats fibers as pieces of work that can be suspended and resumed.
- Actor model (Erlang/OTP, Akka) in which complex problems are decomposed into smaller pieces of work handled by independent computational entities, formally called actors, that coordinate via async message passing.

Looking forward to many more learnings in the new year!

- [LinkedIn post](https://www.linkedin.com/posts/dan-t-590142173_some-quick-reflections-on-my-learnings-from-share-7412147708645277697-jUm5?utm_source=share&utm_medium=member_desktop&rcm=ACoAAAGFx6kBHnkpQlhfgdUaoB0VdwLNQJHcGZo)



👉 The Evolution of State Modeling in Java

An object’s state can start out as a primitive:

int status = 2; // 1 = Pending, 2 = Shipped

This works, but the meaning lives only in comments and documentation. 

The compiler has no idea what 2 represents, and invalid values are easy to introduce.

-------------

To make the meaning explicit, we might switch to a string:

String status = "Shipped";

Now the value is readable, but it’s still unsafe. status can be anything, From a typo to a completely invalid value, So we end up writing defensive "if" checks everywhere.

-------------

Enums are the next step forward:

Status = Status.SHIPPED

enum Status { PENDING, SHIPPED }

They restrict the state to a fixed, meaningful set of values and move validation into the type system.

-------------

When states need different data or behavior, enums can’t express that well anymore.
Algebraic Data Types (ADTs) model each state explicitly in a closed hierarchy, with correctness enforced by the compiler.

OrderStatus status;

sealed interface OrderStatus permits Pending, Shipped, Delivered {}
record Pending() implements OrderStatus {}
record Shipped(String trackingNumber) implements OrderStatus {}
record Delivered(LocalDate deliveredDate) implements OrderStatus {}

OrderStatus status = new Shipped("ABC123");

String message = switch (status) {  //If you add a new state, the compiler forces you to handle it.
    case Pending p -> "Order is pending";
    case Shipped s -> "Tracking number: " + s.trackingNumber();
    case Delivered d -> "Delivered on: " + d.deliveredDate();
};

-------------
✍️
When defining a state, primitives and strings capture values.
Enums validate which values are allowed.
ADTs validate the entire shape of the state, making invalid states impossible to represent.

#java #modernJava

- [LinkedIn post](https://www.linkedin.com/posts/the-dilip-gehlot_java-modernjava-share-7422295769589776384-YC0N?utm_source=share&utm_medium=member_desktop&rcm=ACoAAAGFx6kBHnkpQlhfgdUaoB0VdwLNQJHcGZo)




𝗦𝘂𝗽𝗲𝗿𝗰𝗵𝗮𝗿𝗴𝗲 𝗬𝗼𝘂𝗿 𝗝𝗮𝘃𝗮 𝗗𝗮𝘁𝗮 𝗠𝗼𝗱𝗲𝗹𝘀! 

Are you looking to build more powerful, secure, and expressive APIs in Java? It's time to unlock the incredible synergy between two modern Java features: Records and Sealed Classes!

When used together, these features allow you to create elegant and immutable data models that are perfect for domain-driven design (DDD) and robust API development.

The ✨ Power Combo:

𝗥𝗲𝗰𝗼𝗿𝗱𝘀: Give you concise, transparent, and immutable data carriers, eliminating boilerplate code like getters, setters, equals(), and hashCode().

𝗦𝗲𝗮𝗹𝗲𝗱 𝗖𝗹𝗮𝘀𝘀𝗲𝘀: Allow you to explicitly declare which classes can extend or implement a particular class, giving you fine-grained control over your inheritance hierarchy.

By combining them, you can create what are effectively algebraic data types (ADTs). This means you can model complex domains with precision, ensuring at compile-time that you've handled all possible variations. The result? Code that is safer, more readable, and easier to maintain.

𝗪𝗵𝗮𝘁 𝗮𝗿𝗲 𝘆𝗼𝘂𝗿 𝘁𝗵𝗼𝘂𝗴𝗵𝘁𝘀 𝗼𝗻 𝘁𝗵𝗶𝘀 𝗽𝗼𝘄𝗲𝗿𝗳𝘂𝗹 𝗱𝘂𝗼? 𝗛𝗮𝘃𝗲 𝘆𝗼𝘂 𝘂𝘀𝗲𝗱 𝗥𝗲𝗰𝗼𝗿𝗱𝘀 𝗮𝗻𝗱 𝗦𝗲𝗮𝗹𝗲𝗱 𝗖𝗹𝗮𝘀𝘀𝗲𝘀 𝗶𝗻 𝘆𝗼𝘂𝗿 𝗽𝗿𝗼𝗷𝗲𝗰𝘁𝘀? 𝗦𝗵𝗮𝗿𝗲 𝘆𝗼𝘂𝗿 𝗲𝘅𝗽𝗲𝗿𝗶𝗲𝗻𝗰𝗲𝘀 𝗮𝗻𝗱 𝗳𝗮𝘃𝗼𝗿𝗶𝘁𝗲 𝘂𝘀𝗲 𝗰𝗮𝘀𝗲𝘀 𝗯𝗲𝗹𝗼𝘄! 👇

#Java #JavaDev #JDK #SoftwareDevelopment #Programming #APIDesign #DomainDrivenDesign #DDD #SealedClasses #JavaRecords #Coding #Tech


- [LinkedIn - 𝗦𝘂𝗽𝗲𝗿𝗰𝗵𝗮𝗿𝗴𝗲 𝗬𝗼𝘂𝗿 𝗝𝗮𝘃𝗮 𝗗𝗮𝘁𝗮 𝗠𝗼𝗱𝗲𝗹𝘀!](https://www.linkedin.com/feed/update/urn:li:groupPost:3983267-7360595372819279872?utm_source=share&utm_medium=member_desktop&rcm=ACoAAAGFx6kBHnkpQlhfgdUaoB0VdwLNQJHcGZo)
