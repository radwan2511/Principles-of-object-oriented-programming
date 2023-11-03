#include "Gardener.h"
#include "Person.h"
#include "FlowersBouquet.h"

Gardener::Gardener(std::string name) : Person(name)
{

}

FlowersBouquet* Gardener::prepareBouquet(std::vector<std::string> order)
{
	// TODO
	// step 1: Gardener Garett prepares flowers
	std::cout << getName() << " prepares flowers" << "." << std::endl;
	FlowersBouquet* bouquet = new FlowersBouquet(order);
	return bouquet;
}

std::string Gardener::getName() {
	return "Gardener " + Person::getName();
}

