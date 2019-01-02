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

Roster::Roster() {};

Roster::~Roster() {
    for (auto i: classRosterArray) {
        delete i;
    }
};

/* -------- E.3.a -------- */
void Roster::add(std::string studentID, std::string firstName, std::string lastName, std::string emailAddress,
                 int age, int daysInCourse1, int daysInCourse2, int daysInCourse3, Degree degreeProgram) {
    int days[3] {daysInCourse1, daysInCourse2, daysInCourse3};
    
    if (degreeProgram == SECURITY) {
        classRosterArray[size_] = new SecurityStudent(studentID, firstName, lastName, emailAddress, age, days);
    } else if (degreeProgram == NETWORK) {
        classRosterArray[size_] = new NetworkStudent(studentID, firstName, lastName, emailAddress, age, days);
    } else if (degreeProgram == SOFTWARE) {
        classRosterArray[size_] = new SoftwareStudent(studentID, firstName, lastName, emailAddress, age, days);
    } else {
        classRosterArray[size_] = new Student(studentID, firstName, lastName, emailAddress, age, days);
    }
    size_++;
};

/* -------- E.3.b -------- */
void Roster::remove(std::string studentID) {};

/* -------- E.3.c -------- */
void Roster::printAll() {
    for(auto i: classRosterArray)
        i->print();
};

/* -------- E.3.d -------- */
void Roster::printAverageDaysInCourse(std::string studentID) {};

/* -------- E.3.e -------- */
void Roster::printInvalidEmails() {};

/* -------- E.3.f -------- */
void Roster::printByDegreeProgram(Degree degreeProgram) {};

/* -------- F.x -------- */
int main() {
    /* -------- F.1 -------- */
    std::cout << "C867 - Scripting and Programming - Applications" << std::endl;
    std::cout << "C++ - Student ID#: 000917613 - Jeremy Greenwood" << std::endl << std::endl;
    
    /* -------- F.2 -------- */
    Roster classRoster;
    classRoster.printAll();
    
    return 0;
};



