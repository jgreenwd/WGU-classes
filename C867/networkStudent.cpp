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
    try {
        std::cout << getStudentID() << "\t" << "First Name: " << getFirstName() << "\tLast Name:" << getLastName() << "\tAge:" << getAge() << "\tdaysInCourse: {" << getNumberOfDays()[0] << "," << getNumberOfDays()[1] << ", " << getNumberOfDays()[2] << "}" << "\tDegree Program: Network" << std::endl;
    }
    catch (...) {
        std::cerr << "unknown error in NetworkStudent.print()" << std::endl;
    }}
