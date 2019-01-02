//
//  roster.cpp
//  C867 Project
//
//  Created by Jeremy Greenwood on 12/17/18.
//  Copyright © 2018 Jeremy Greenwood. All rights reserved.
//
#include <iostream>
#include <vector>
#include <regex>
#include "degree.h"
#include "roster.h"

// add to Roster based on data parsed from studentData[] **note use of degreeProgram**
void Roster::add(std::string studentID, std::string firstName, std::string lastName, std::string emailAddress,
                 int age, int daysInCourse1, int daysInCourse2, int daysInCourse3, Degree degreeProgram) {
/*    int days[3] {daysInCourse1, daysInCourse2, daysInCourse3};
    
    if (degreeProgram == SECURITY) {
        classRosterArray[size_] = new SecurityStudent(studentID, firstName, lastName, emailAddress, age, days);
    } else if (degreeProgram == NETWORK) {
        classRosterArray[size_] = new NetworkStudent(studentID, firstName, lastName, emailAddress, age, days);
    } else if (degreeProgram == SOFTWARE) {
        classRosterArray[size_] = new SoftwareStudent(studentID, firstName, lastName, emailAddress, age, days);
    } else {
        classRosterArray[size_] = new Student(studentID, firstName, lastName, emailAddress, age, days);
    } */
};

void Roster::remove(std::string studentID) {
    
};

void Roster::printAll() {
    
};

void Roster::printAverageDaysInCourse(std::string studentID) {
    
};

void Roster::printInvalidEmails() {};

void Roster::printByDegreeProgram(Degree degreeProgram) {};

/*
int main() {
    std::cout << "C867 - Scripting and Programming - Applications" << std::endl;
    std::cout << "C++ - Student ID#: 000917613 - Jeremy Greenwood" << std::endl << std::endl;
    
    Roster roster;
    
    return 0;
};


*/
