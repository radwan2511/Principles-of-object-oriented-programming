#include "Wholesaler.h"
#include "Person.h"


Wholesaler::Wholesaler(std::string name,Grower* grower) : Person(name), grower(grower)
{
	//this->grower = grower;
}

FlowersBouquet* Wholesaler::acceptOrder(Person* person, std::vector<std::string> order)
{
	// TODO
	//step 1: Wholesaler Watson forwards the request to Grower Gray
	std::cout << getName() << " forwards the request to " << grower->getName() << "." << std::endl;
	FlowersBouquet* bouquet = grower->prepareOrder(order);

	//step 2:Grower Gray returns flowers to Wholesaler Watson.
	std::cout << grower->getName() << " returns flowers to " << getName() << "." << std::endl;

	delete grower;
	return bouquet;
}

std::string Wholesaler::getName() {
	return "Wholesaler " + Person::getName();
}



