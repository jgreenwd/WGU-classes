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
    try{
        int daysInCourse[3] {daysInCourse1, daysInCourse2, daysInCourse3};
        if (degreeProgram == 0) {
            SecurityStudent(studentID, firstName, lastName, emailAddress, age, daysInCourse);
        } else if (degreeProgram == 1) {
            NetworkStudent(studentID, firstName, lastName, emailAddress, age, daysInCourse);
        } else if (degreeProgram == 2) {
            SoftwareStudent(studentID, firstName, lastName, emailAddress, age, daysInCourse);
        } else {
            Student(studentID, firstName, lastName, emailAddress, age, daysInCourse);
        }
    }
    catch (...) {
        std::cerr << "Roster.add failed" << std::endl;
    }
};

void Roster::remove(std::string studentID) {};

void Roster::printAll() {};

void Roster::printDaysInCourse(std::string studentID) {};

void Roster::printInvalidEmails() {};

void Roster::printByDegreeProgram(int degreeProgram) {};

int main() {
    std::cout << "C867 - Scripting and Programming - Applications" << std::endl;
    std::cout << "C++ - Student ID#: 000917613 - Jeremy Greenwood" << std::endl << std::endl;
    
    Roster classRoster;
    
    std::vector<std::string> result;
    for(auto i: studentData) {
        try {
            std::string studentID, firstName, lastName, emailAddress;
            int age, daysInCourse1, daysInCourse2, daysInCourse3, daysInCourse[3];
            Degree degreeProgram;
            
            std::string temp;
            std::regex entry("[^,]+");
            std::sregex_iterator next(i.begin(), i.end(), entry), end;
            while (next != end) {
                std::smatch match = *next;
                temp += match.str() + " ";
                next++;
            }
            result.push_back(temp);
        }
        catch (...) {
            std::cout << "Invalid REGEX" << std::endl;
        }
    }
    
    return 0;
};
