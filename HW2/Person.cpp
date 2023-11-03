#include "Person.h"
#include "Florist.h"
#include <iostream>
#include <vector>

Person::Person(std::string name) : name(name)
{}

std::string Person::getName()
{
	return name;
}

void Person::orderFlowers(Florist* florist, Person* person, std::vector<std::string> order)
{
	std::cout << getName() << " orders flowers to " << person->getName() << " from " << florist->getName() << ": ";
	std::cout<< order.at(0);
		for (int i = 1; i < order.size()-1; i++) {
		        std::cout<< ", " << order.at(i);
		    }
		std::cout<< ", " << order.at(order.size()-1) << "." << std::endl;

	florist->acceptOrder(person, order);

}


void Person::acceptFlowers(FlowersBouquet* bouquet)
{
	// step 1: Robin accepts the flowers: Roses, Violets, Gladiolus.
		std::cout << getName() << " accepts the flowers: ";
		std::vector<std::string> order = bouquet->getBouquet();
		std::cout<< order.at(0);
		for (int i = 1; i < order.size()-1; i++) {
		        std::cout<< ", " << order.at(i);
		    }
		std::cout<< ", " << order.at(order.size()-1) << "." << std::endl;
}
