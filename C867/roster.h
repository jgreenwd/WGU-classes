//
//  roster.hpp
//  C867 Project
//
//  Created by Jeremy Greenwood on 12/17/18.
//  Copyright © 2018 Jeremy Greenwood. All rights reserved.
//

#ifndef roster_h
#define roster_h

#include "student.h"
#include "networkStudent.h"
#include "securityStudent.h"
#include "softwareStudent.h"
using std::string;

const string studentData[] = {
    "A1,John,Smith,John1989@gm ail.com,20,30,35,40,SECURITY",
    "A2,Suzan,Erickson,Erickson_1990@gmailcom,19,50,30,40,NETWORK",
    "A3,Jack,Napoli,The_lawyer99yahoo.com,19,20,40,33,SOFTWARE",
    "A4,Erin,Black,Erin.black@comcast.net,22,50,58,40,SECURITY",
    "A5,Jeremy,Greenwood,jgre369@wgu.edu,41,30,30,30,SOFTWARE"
};

class Roster {
    private:
        int lastElementIndex_ = 0;
    
        /* -------- E.1 -------- */
        Student* classRosterArray[];
    
    public:
        Roster();
        ~Roster();
    
        /* -------- E.3.a -------- */
        void add(string, string, string, string, int, int, int, int, Degree);
    
        /* -------- E.3.b -------- */
        void remove(const string studentID);
    
        /* -------- E.3.c -------- */
        void printAll();
    
        /* -------- E.3.d -------- */
        void printAverageDaysInCourse(const string studentID);
    
        /* -------- E.3.e -------- */
        void printInvalidEmails();
    
        /* -------- E.3.f -------- */
        void printByDegreeProgram(const Degree);
    
        int size();
};

#endif /* roster_h */
