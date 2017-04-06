
public enum EdgeType {
	EXTENDS, // TheEconomy extends ConcreteSubject => ConcreteSubject <|-- TheEconomy
	IMPLEMENTS, // ConcreteSubject implements Subject =>  Subject <|.. ConcreteSubject
	COMPOSITON, // ConcreteSubject has collection<Observer> => ConcreteSubject 1 - * Observer
	ASSOCIATION // ..>
}
