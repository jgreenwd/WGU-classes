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
    try {
        for(auto i: classRosterArray)
            i->print();
    }
    catch (...) {
        std::cerr << "unknown error in Roster.printAll()";
    }
};


/* -------- E.3.d -------- */
void Roster::printAverageDaysInCourse(std::string studentID) {
    try {
        for(auto i: classRosterArray) {
            if (studentID == i->getStudentID()) {
                int *days = i->getNumberOfDays();
                int avgDays = (days[0] + days[1] + days[2]) / 3;
                std::cout << avgDays << std::endl;
            }
        }
    }
    catch (...) {
        std::cerr << "unknown error in Roster.printAverageDaysInCourse()";
    }
};


/* -------- E.3.e -------- */
void Roster::printInvalidEmails() {
    try {
        bool allAddressesValid {true};
        for(auto i: classRosterArray) {
            std::string email = i->getEmailAddress();
            
            std::regex entry("\\w+@[a-zA-Z_]+\\.");
            std::smatch match;
            
            if (!std::regex_search(email, match, entry)) {
                std::cout << email << std::endl;
                allAddressesValid = false;
            }
            
            if (allAddressesValid)
                std::cout << "No invalid email addresses found" << std::endl;
        }
    }
    catch(...) {
        std::cerr << "unknown error in Roster.printInvalidEmails()";
    }
};


/* -------- E.3.f -------- */
void Roster::printByDegreeProgram(Degree degreeProgram) {};


/* -------- F.x -------- */
int main() {
    /* -------- F.1 -------- */
    std::cout << "C867 - Scripting and Programming - Applications" << std::endl;
    std::cout << "C++ - Student ID#: 000917613 - Jeremy Greenwood" << std::endl << std::endl;
    
    /* -------- F.2 -------- */
    Roster classRoster;
    classRoster.printAverageDaysInCourse("A1");
    
    return 0;
};



