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

Roster::~Roster() {
    for(int i = lastElementIndex_; i > 0; --i) {
        delete classRosterArray[i];
        lastElementIndex_--;
    }
};

// TODO: something wrong with days[]
/* -------- E.3.a -------- */
void Roster::add(string studentID, string firstName, string lastName, string emailAddress,
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
void Roster::remove(string studentID) {
    bool present = false;
    for(int i = 0; i < lastElementIndex_; i++) {
        if (classRosterArray[i] != NULL) {
            if (classRosterArray[i]->getStudentID() == studentID) {
                present = true;
                classRosterArray[i] = nullptr;
                lastElementIndex_--;
            }
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
void Roster::printAverageDaysInCourse(string studentID) {
    try {
        for(int i = 0; i < lastElementIndex_; ++i) {
            if (studentID == classRosterArray[i]->getStudentID()) {
                int* days = classRosterArray[i]->getNumberOfDays();
                int avgDays = (days[0] + days[1] + days[2]) / 3;
                std::cout << "Average days in course for student " << studentID << ": " << avgDays << std::endl;
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
        for(int i = 0; i < lastElementIndex_; i++) {
            string email = classRosterArray[i]->getEmailAddress();
            
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
        for(int i = 0; i < lastElementIndex_; i++) {
            Degree deg = classRosterArray[i]->getDegreeProgram();
            if (deg == degreeProgram)
                classRosterArray[i]->print();
        }
    }
    catch (...) {
        std::cerr << "unknown error in Roster.printByDegreeProgram()";
    }
};

int Roster::size() {
    return lastElementIndex_;
}

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
    
    /* -------- F.4 -------- */
    classRoster.printAll();
    std::cout << std::endl;
    
    classRoster.printInvalidEmails();
    std::cout << std::endl;
    
    for(int i = 0; i < classRoster.size(); i++) {
        classRoster.printAverageDaysInCourse("A" + std::to_string(i+1));
    }
    std::cout << std::endl;
    
    classRoster.printByDegreeProgram(SOFTWARE);
    std::cout << std::endl;
    
//    classRoster.remove("A3");
//    classRoster.remove("A3");
    
    classRoster.~Roster();
    
    return 0;
};
