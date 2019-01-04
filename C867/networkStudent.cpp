//
//  networkStudent.cpp
//  C867 Project
//
//  Created by Jeremy Greenwood on 12/17/18.
//  Copyright © 2018 Jeremy Greenwood. All rights reserved.
//

#include "networkStudent.h"

NetworkStudent::NetworkStudent(string ID, string fname, string lname, string email, int age, int* days, Degree degree)
: Student(ID, fname, lname, email, age, new int[3] {days[0],days[1],days[2]}, degree){};

NetworkStudent::~NetworkStudent(){};

Degree NetworkStudent::getDegreeProgram() {
    return degree_;
};

void NetworkStudent::print() {
    int* days = getNumberOfDays();
    std::cout << getStudentID() << "\t" << "First Name: " << getFirstName() << "\tLast Name:" << getLastName() << "\tAge:" << getAge() << "\tdaysInCourse: {" << days[0] << "," << days[1] << "," << days[2] << "}" << "\tDegree Program: Network" << std::endl;
}
