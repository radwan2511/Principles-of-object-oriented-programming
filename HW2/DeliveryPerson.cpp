#include "DeliveryPerson.h"
#include "Person.h"


DeliveryPerson::DeliveryPerson(std::string name) : Person(name)
{
}

void DeliveryPerson::deliver(Person* person, FlowersBouquet* order)
{
	// TODO
	//step 1: Delivery Person Dylan delivers flowers Robin.
	std::cout << getName() << " delivers flowers " << person->getName() << "." << std::endl;

	// step 2: Robin accepts the flowers: Roses, Violets, Gladiolus.
	person->acceptFlowers(order);

}

std::string DeliveryPerson::getName() {
	return "Delivery Person " + Person::getName();
}



