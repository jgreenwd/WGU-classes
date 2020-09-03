//
//  main.cpp
//  C867 Project
//
//  Created by Jeremy Greenwood on 12/17/18.
//  Copyright © 2018 Jeremy Greenwood. All rights reserved.
//

///* ---------------- F.x ---------------- */
#include <iostream>
#include <fstream>
#include <string>
#include <regex>
#include "roster.h"

/* ----------------- Local functions for parsing input file ----------------- */
int ishift(std::smatch &sm, std::sregex_iterator iter) {
    sm = *iter;
    return stoi(sm.str());
};

Degree dshift(std::smatch &sm, std::sregex_iterator iter) {
    sm = *iter;
    string smVal = sm.str();

    if (smVal == "SECURITY") return SECURITY;
    if (smVal == "NETWORK") return NETWORK;
    if (smVal == "SOFTWARE") return SOFTWARE;
    return SOFTWARE;
};

string sshift(std::smatch &sm, std::sregex_iterator iter) {
    sm = *iter;
    return sm.str();
};

int main() {
    /*   -------- F.1 -------- */
    std::cout << "C867 - Scripting and Programming - Applications" << std::endl;
    std::cout << "C++ - Student ID#: 000917613 - Jeremy Greenwood" << std::endl << std::endl;

    /*   -------- F.2 -------- */
    Roster classRoster;

    std::ifstream filein ("/Users/jgreenwd/Documents/Coding/Cpp.nosync/Temp/Temp/students.txt");

    if (filein.is_open()) {
        string line;
        while ( getline( filein, line )) {
            std::regex entry("[^,]+");
            std::sregex_iterator iter(line.begin(), line.end(), entry), end;
            std::smatch match = *iter++;
            
            string studentID = match.str();
            string firstName = sshift(match, iter++);
            string lastName = sshift(match, iter++);
            string emailAddress = sshift(match, iter++);
            int age = ishift(match, iter++);
            int daysInCourse1 = ishift(match, iter++);
            int daysInCourse2 = ishift(match, iter++);
            int daysInCourse3 = ishift(match, iter++);
            Degree degreeProgram = dshift(match,iter++);
            
            classRoster.add(studentID, firstName, lastName, emailAddress, age, daysInCourse1, daysInCourse2, daysInCourse3, degreeProgram);
        }
    } else {
        std::cout << "File not available.\n";
        return 0;
    }

    /*   -------- F.4 -------- */
    std::cout << "Print all on roster:\n";
    classRoster.printAll();
    std::cout << std::endl;
    
    std::cout << "Print all invalid emails:\n";
    classRoster.printInvalidEmails();
    std::cout << std::endl;

    std::cout << "Print average days in course for each student:\n";
    for(int i = 0; i < classRoster.size(); i++) {
        classRoster.printAverageDaysInCourse("A" + std::to_string(i+1));
    }
    std::cout << std::endl;

    std::cout << "Print all in SOFTWARE program:\n";
    classRoster.printByDegreeProgram(SOFTWARE);

    std::cout << "Attempt to remove student A3 twice:\n";
    classRoster.remove("A3");
    classRoster.remove("A3");

    classRoster.~Roster();

    return 0;
};
