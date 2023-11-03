#include "FlowerArranger.h"
#include "Person.h"
#include "FlowersBouquet.h"

FlowerArranger::FlowerArranger(std::string name) : Person(name)
{

}

void FlowerArranger::arrangeFlowers(FlowersBouquet* order)
{
	// TODO
	// step 1:Flower Arranger Flora arranges flowers.
	std::cout << getName() << " arranges flowers." << std::endl;
	order->arrange();
}

std::string FlowerArranger::getName() {
	return "Flower Arranger " + Person::getName();
}





