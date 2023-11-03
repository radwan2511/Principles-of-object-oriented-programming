#include "Florist.h"
#include "Person.h"
#include "FlowerArranger.h"
#include "Wholesaler.h"
#include "DeliveryPerson.h"
#include "FlowersBouquet.h"

Florist::Florist(std::string name, Wholesaler* wholesaler, FlowerArranger* flowerArranger,DeliveryPerson* deliveryPerson) : Person(name) , wholesaler(wholesaler), flowerArranger(flowerArranger), deliveryPerson(deliveryPerson)
{
	//this->wholesaler = wholesaler;
	//this->flowerArranger = flowerArranger;
	//this->deliveryPerson = deliveryPerson;
}

void Florist::acceptOrder(Person* person, std::vector<std::string> order)
{
	// TODO
	// step 1: Florist Fred forwards request to Wholesaler Watson.

	std::cout << getName() << " forwards request to " << wholesaler->getName() << "." << std::endl;
	FlowersBouquet* bouquet = wholesaler->acceptOrder(person,order);

	// step 2: Wholesaler Watson returns flowers to Florist Fred.
	std::cout << wholesaler->getName() << " returns flowers to " << getName() << "." << std::endl;

	// step 3: Florist Fred request flowers arrangement from Flower Arranger Flora.
	std::cout << getName() << " request flowers arrangement from " << flowerArranger->getName() << "." << std::endl;

	flowerArranger->arrangeFlowers(bouquet);

	// step 4: Flower Arranger Flora returns arranged flowers to Florist Fred.
	std::cout << flowerArranger->getName() << " returns arranged flowers to " << getName() << "." << std::endl;

	// step 5: Florist Fred forwards flowers to Delivery Person Dylan.
	std::cout << getName() << " forwards flowers to " << deliveryPerson->getName() << "." << std::endl;
	deliveryPerson->deliver(person, bouquet);

	delete wholesaler;
	delete flowerArranger;
	delete deliveryPerson;
	delete bouquet;

}

std::string Florist::getName() {
	return "Florist " + Person::getName();
}
