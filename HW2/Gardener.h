#pragma once

#include "Person.h"
#include "FlowersBouquet.h"

class Gardener : public Person
{
private:

public:
	Gardener(std::string);
	FlowersBouquet* prepareBouquet(std::vector<std::string>);
    std::string getName();
};
