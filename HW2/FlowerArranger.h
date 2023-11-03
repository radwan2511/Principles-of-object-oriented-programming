#pragma once

#include "Person.h"
#include "FlowersBouquet.h"

class FlowerArranger : public Person
{
private:

public:
	FlowerArranger(std::string);
	void arrangeFlowers(FlowersBouquet*);
    std::string getName();
};
