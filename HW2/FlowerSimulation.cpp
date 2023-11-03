// FlowerSimulation.cpp : This file contains the 'main' function. Program execution begins and ends there.

using namespace std;

#include <iostream>
#include "Person.h"
#include "Florist.h"
#include "Grower.h"

int main()
{
	Grower* grower = new Grower("Gray",new Gardener("Garett"));
	Wholesaler* wholesaler = new Wholesaler("Watson",grower);
	std::vector<std::string> order = { "Roses", "Violets", "Gladiolus" };
    Florist* fred = new Florist("Fred",wholesaler, new FlowerArranger("Flora"), new DeliveryPerson("Dylan"));
    Person* chris = new Person("Chris");
    Person* robin = new Person("Robin");

    chris->orderFlowers(fred, robin, order);
    
	delete fred;
    delete chris;
    delete robin;
}
