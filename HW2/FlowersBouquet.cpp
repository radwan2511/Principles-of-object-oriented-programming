#include "FlowersBouquet.h"
#include <iostream>
#include <vector>

FlowersBouquet::FlowersBouquet(std::vector<std::string> bouquet) : bouquet(bouquet), is_arranged(false)
{
	//this->bouquet = bouquet;
	//this->is_arranged = false;
}

void FlowersBouquet::arrange()
{
	// TODO
	this->is_arranged = true;
}

std::vector<std::string> FlowersBouquet::getBouquet()
{
	return this->bouquet;
}

