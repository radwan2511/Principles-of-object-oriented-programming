#pragma once

#include "Person.h"
#include "FlowersBouquet.h"

class DeliveryPerson : public Person
{
private:

public:
	DeliveryPerson(std::string);
	void deliver(Person*, FlowersBouquet*);
	std::string getName();
};
