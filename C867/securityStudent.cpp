//
//  securityStudent.cpp
//  C867 Project
//
//  Created by Jeremy Greenwood on 12/17/18.
//  Copyright © 2018 Jeremy Greenwood. All rights reserved.
//

#include "securityStudent.h"

SecurityStudent::SecurityStudent(string ID, string fname, string lname, string email, int age, int* days, Degree degree)
: Student(ID, fname, lname, email, age, new int[3] {days[0],days[1],days[2]}, degree){};

SecurityStudent::~SecurityStudent() {};

Degree SecurityStudent::getDegreeProgram(){
    return degree_;
};

void SecurityStudent::print() {
    std::cout << getStudentID() << "\t" << "First Name: " << getFirstName() << "\tLast Name:" << getLastName() << "\tAge:" << getAge() << "\tdaysInCourse: {" << getNumberOfDays()[0] << "," << getNumberOfDays()[1] << "," << getNumberOfDays()[2] << "}" << "\tDegree Program: Security" << std::endl;
}
