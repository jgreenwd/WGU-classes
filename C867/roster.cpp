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

// TODO: something wrong with days[]
/* -------- E.3.a -------- */
void Roster::add(std::string studentID, std::string firstName, std::string lastName, std::string emailAddress,
                 int age, int daysInCourse1, int daysInCourse2, int daysInCourse3, Degree degreeProgram) {
    int days[] {daysInCourse1, daysInCourse2, daysInCourse3};
    
    if (degreeProgram == SECURITY) {
        classRosterArray[lastElementIndex_++] = new SecurityStudent(studentID, firstName, lastName, emailAddress, age, days);
    } else if (degreeProgram == NETWORK) {
        classRosterArray[lastElementIndex_++] = new NetworkStudent(studentID, firstName, lastName, emailAddress, age, days);
    } else if (degreeProgram == SOFTWARE) {
        classRosterArray[lastElementIndex_++] = new SoftwareStudent(studentID, firstName, lastName, emailAddress, age, days);
    } else {
        classRosterArray[lastElementIndex_++] = new Student(studentID, firstName, lastName, emailAddress, age, days);
    }
};


/* -------- E.3.b -------- */
void Roster::remove(std::string studentID) {
    bool present = false;
    for(int i = 0; i < lastElementIndex_; i++) {
        if (classRosterArray[i]->getStudentID() == studentID) {
            present = true;
            classRosterArray[i] = nullptr;
        }
    }
    present ? std::cout << studentID << " removed" << std::endl : std::cout << studentID << " not found" << std::endl;
};


/* -------- E.3.c -------- */
void Roster::printAll() {
    try {
        for(int i = 0; i < lastElementIndex_; i++) {
            if (classRosterArray[i] != NULL)
                classRosterArray[i]->print();
        }
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
                int avgDays = (i->getNumberOfDays()[0] + i->getNumberOfDays()[1] + i->getNumberOfDays()[2]) / 3;
                i->print();
                std::cout << "Average days in course: " << avgDays << std::endl;
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
void Roster::printByDegreeProgram(Degree degreeProgram) {
    try {
        for(auto i: classRosterArray) {
            Degree deg = i->getDegreeProgram();
            if (deg == degreeProgram)
                i->print();
        }
    }
    catch (...) {
        std::cerr << "unknown error in Roster.printByDegreeProgram()";
    }
};


/* -------- F.x -------- */
int main() {
    /* -------- F.1 -------- */
    std::cout << "C867 - Scripting and Programming - Applications" << std::endl;
    std::cout << "C++ - Student ID#: 000917613 - Jeremy Greenwood" << std::endl << std::endl;
    
    /* -------- F.2 -------- */
    Roster classRoster;
    classRoster.add("A1","John","Smith","John1989@gm ail.com",20,30,35,40,SECURITY);
    classRoster.add("A2","Suzan","Erickson","Erickson_1990@gmailcom",19,50,30,40,NETWORK);
    classRoster.add("A3","Jack","Napoli","The_lawyer99yahoo.com",19,20,40,33,SOFTWARE);
    classRoster.add("A4","Erin","Black","Erin.black@comcast.net",22,50,58,40,SECURITY);
    classRoster.add("A5","Jeremy","Greenwood","jgre369@wgu.edu",41,30,30,30,SOFTWARE);
    
    std::cout << std::endl;
    
    return 0;
};
