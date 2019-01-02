//
//  networkStudent.cpp
//  C867 Project
//
//  Created by Jeremy Greenwood on 12/17/18.
//  Copyright © 2018 Jeremy Greenwood. All rights reserved.
//

#include "networkStudent.h"

NetworkStudent
::NetworkStudent( std::string ID, std::string fname, std::string lname, std::string email, int age, int* days)
: Student(ID, fname, lname, email, age, days){};

// redundant constructor to comply with D.2.c
NetworkStudent
::NetworkStudent( std::string ID, std::string fname, std::string lname, std::string email, int age, int* days, Degree degree)
: Student(ID, fname, lname, email, age, days){};

NetworkStudent::~NetworkStudent() {};

Degree NetworkStudent::getDegreeProgram() {
    return degree_;
};

void NetworkStudent::print() {
    int *days = getNumberOfDays();
    std::string daysInCourse = "{" + std::to_string(days[0]) + ", " + std::to_string(days[1]) + ", " + std::to_string(days[2]) + "}";
    std::cout << getStudentID() << "\t" << "First Name: " << getFirstName() << "\tLast Name:" << getLastName() << "\tAge:" << getAge() << "\tdaysInCourse: " << daysInCourse << "\tDegree Program: Network" << std::endl;
}

