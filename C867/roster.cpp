//
//  roster.cpp
//  C867 Project
//
//  Created by Jeremy Greenwood on 12/17/18.
//  Copyright © 2018 Jeremy Greenwood. All rights reserved.
//
#include <iostream>
#include "degree.h"
#include "roster.h"

void Roster::add(std::string studentID, std::string firstName, std::string lastName, std::string emailAddress,
                 int age, int daysInCourse1, int daysInCourse2, int daysInCourse3, Degree degreeProgram) {
};
void Roster::remove(std::string studentID) {};
void Roster::printAll() {};
void Roster::printDaysInCourse(std::string studentID) {};
void Roster::printInvalidEmails() {};
void Roster::printByDegreeProgram(int degreeProgram) {};

int main() {
    std::cout << "C867 - Scripting and Programming - Applications" << std::endl;
    std::cout << "C++ - Student ID#: 000917613 - Jeremy Greenwood" << std::endl << std::endl;
    
    //Student n(ID, fname, lname, email, degree, age, arr);
    //n.print();
    
    Roster classRoster;
    for(auto i: studentData) {
        std::string studentID, firstName, lastName, emailAddress;
        int age, daysInCourse1, daysInCourse2, daysInCourse3;
        Degree degreeProgram;
        
        studentID = i.substr(0,i.find(','));
        i.erase(0, studentID.size() + 1);
        firstName = i.substr(0,i.find(','));
        i.erase(0, firstName.size() + 1);
        lastName = i.substr(0, i.find(','));
        i.erase(0, lastName.size() + 1);
        emailAddress = i.substr(0, i.find(','));
        i.erase(0, emailAddress.size() + 1);
        age = stoi(i.substr(0, i.find(',')));
        i.erase(0, std::to_string(age).size() + 1);
        daysInCourse1 = stoi(i.substr(0, i.find(',')));
        i.erase(0, std::to_string(daysInCourse1).size() + 1);
        daysInCourse2 = stoi(i.substr(0, i.find(',')));
        i.erase(0, std::to_string(daysInCourse2).size() + 1);
        daysInCourse3 = stoi(i.substr(0, i.find(',')));
        i.erase(0, std::to_string(daysInCourse3).size() + 1);
        
        
        std::cout << studentID << "\t" << firstName << "\t" << lastName << "\t\t" << age << "\t" << daysInCourse1 << "\t" << daysInCourse2 << "\t" << daysInCourse3 << "\t" << i << std::endl;
        
    }
   
    return 0;
};
