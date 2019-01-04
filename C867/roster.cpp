//
//  roster.cpp
//  C867 Project
//
//  Created by Jeremy Greenwood on 12/17/18.
//  Copyright © 2018 Jeremy Greenwood. All rights reserved.
//
#include <iostream>
#include <regex>
#include "degree.h"
#include "roster.h"

Roster::Roster() {};

Roster::~Roster() {};

/* -------- E.3.a -------- */
void Roster::add(string ID, string fName, string lName, string email, int age, int daysInCourse1, int daysInCourse2, int daysInCourse3, Degree degreeProgram) {
    int days[3] {daysInCourse1, daysInCourse2, daysInCourse3};
    
    switch (degreeProgram) {
        case SECURITY : classRosterArray_[lastElementIndex_++] = new SecurityStudent(ID, fName, lName, email, age, days); break;
        case NETWORK : classRosterArray_[lastElementIndex_++] = new NetworkStudent(ID, fName, lName, email, age, days); break;
        case SOFTWARE : classRosterArray_[lastElementIndex_++] = new SoftwareStudent(ID, fName, lName, email, age, days); break;
    }
};


/* -------- E.3.b -------- */
void Roster::remove(string studentID) {
    bool present = false;
    for(int i = 0; i < lastElementIndex_; i++) {
        if (classRosterArray_[i] != NULL) {
            if (classRosterArray_[i]->getStudentID() == studentID) {
                present = true;
                classRosterArray_[i] = nullptr;
                lastElementIndex_--;
            }
        }
    }
    present ? std::cout << studentID << " removed" << std::endl : std::cout << studentID << " not found" << std::endl;
};


/* -------- E.3.c -------- */
void Roster::printAll() {
    for(int i = 0; i < lastElementIndex_; i++) {
        if (classRosterArray_[i] != NULL)
            classRosterArray_[i]->print();
    }
    std::cout << std::endl;
};


/* -------- E.3.d -------- */
void Roster::printAverageDaysInCourse(string studentID) {
    for(int i = 0; i < lastElementIndex_; ++i) {
        if (studentID == classRosterArray_[i]->getStudentID()) {
            int* days = classRosterArray_[i]->getNumberOfDays();
            int avgDays = (days[0] + days[1] + days[2]) / 3;
            std::cout << "Average days in course for student " << studentID << ": " << avgDays << std::endl;
        }
    }
};


/* -------- E.3.e -------- */
void Roster::printInvalidEmails() {
    bool allAddressesValid {true};
    for(int i = 0; i < lastElementIndex_; i++) {
        string email = classRosterArray_[i]->getEmailAddress();
        
        std::regex entry("\\w+@[a-zA-Z_]+\\.");
        std::smatch match;
            
        if (!std::regex_search(email, match, entry)) {
            std::cout << email << std::endl;
            allAddressesValid = false;
        }
    }
};


/* -------- E.3.f -------- */
void Roster::printByDegreeProgram(Degree degreeProgram) {
    for(int i = 0; i < lastElementIndex_; i++) {
        Degree deg = classRosterArray_[i]->getDegreeProgram();
        if (deg == degreeProgram)
            classRosterArray_[i]->print();
    }
    std::cout << std::endl;
};

int Roster::size() {
    return lastElementIndex_;
}


///* ---------------- F.x ---------------- */

//int main() {
//  /*   -------- F.1 -------- */
//    std::cout << "C867 - Scripting and Programming - Applications" << std::endl;
//    std::cout << "C++ - Student ID#: 000917613 - Jeremy Greenwood" << std::endl << std::endl;

//  /*   -------- F.2 -------- */
//    Roster classRoster;
//    classRoster.add("A1","John","Smith","John1989@gm ail.com",20,30,35,40,SECURITY);
//    classRoster.add("A2","Suzan","Erickson","Erickson_1990@gmailcom",19,50,30,40,NETWORK);
//    classRoster.add("A3","Jack","Napoli","The_lawyer99yahoo.com",19,20,40,33,SOFTWARE);
//    classRoster.add("A4","Erin","Black","Erin.black@comcast.net",22,50,58,40,SECURITY);
//    classRoster.add("A5","Jeremy","Greenwood","jgre369@wgu.edu",41,30,30,30,SOFTWARE);
//
//  /*   -------- F.4 -------- */
//    classRoster.printAll();
//    classRoster.printInvalidEmails();
//    std::cout << std::endl;
//
//    for(int i = 0; i < classRoster.size(); i++) {
//        classRoster.printAverageDaysInCourse("A" + std::to_string(i+1));
//    }
//    std::cout << std::endl;
//
//    classRoster.printByDegreeProgram(SOFTWARE);
//
//    classRoster.remove("A3");
//    classRoster.remove("A3");
//
//    classRoster.~Roster();
//
//    return 0;
//};
