//
//  softwareStudent.cpp
//  C867 Project
//
//  Created by Jeremy Greenwood on 12/17/18.
//  Copyright © 2018 Jeremy Greenwood. All rights reserved.
//

#include "softwareStudent.h"

SoftwareStudent
::SoftwareStudent( std::string ID, std::string fname, std::string lname, std::string email, int age, int* days)
: Student(ID, fname, lname, email, age, days){};

// redundant constructor to comply with D.2.c
SoftwareStudent
::SoftwareStudent( std::string ID, std::string fname, std::string lname, std::string email, int age, int* days, Degree degree)
: Student(ID, fname, lname, email, age, days){};

SoftwareStudent::~SoftwareStudent() {};

Degree SoftwareStudent::getDegreeProgram() {
    return degree_;
};

void SoftwareStudent::print() {
    try {
        std::cout << getStudentID() << "\t" << "First Name: " << getFirstName() << "\tLast Name:" << getLastName() << "\tAge:" << getAge() << "\tdaysInCourse: {" << getNumberOfDays()[0] << "," << getNumberOfDays()[1] << ", " << getNumberOfDays()[2] << "}" << "\tDegree Program: Software" << std::endl;
    }
    catch (...) {
        std::cerr << "unknown error in SoftwareStudent.print()" << std::endl;
    }
}
