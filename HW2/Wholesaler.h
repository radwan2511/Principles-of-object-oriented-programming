#pragma once

#include "Person.h"
#include "Grower.h"
#include "FlowersBouquet.h"
#include <iostream>
#include <vector>

class Wholesaler : public Person

{
private:
	Grower* grower;
public:
    Wholesaler(std::string,Grower*);
    FlowersBouquet* acceptOrder(Person*, std::vector<std::string>);
    std::string getName();
};
