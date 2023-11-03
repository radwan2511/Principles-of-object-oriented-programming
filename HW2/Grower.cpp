#include "Grower.h"
#include "Person.h"
#include "FlowersBouquet.h"
//#include "Gardener.h"


Grower::Grower(std::string name, Gardener* gardener) : Person(name), gardener(gardener)
{
	//this->gardener = gardener;
}

FlowersBouquet* Grower::prepareOrder(std::vector<std::string> order)
{
	// TODO
	//step 1: Grower Gray forwards the request to Gardener Garett.
	std::cout << getName() << " forwards the request to " << gardener->getName() << "." << std::endl;
	FlowersBouquet* bouquet = gardener->prepareBouquet(order);

	// step 2: Gardener Garett returns flowers to Grower Gray.
	std::cout << gardener->getName() << " returns flowers to " << getName() << "." << std::endl;

	delete gardener;
	return bouquet;
}

std::string Grower::getName() {
	return "Grower " + Person::getName();
}



