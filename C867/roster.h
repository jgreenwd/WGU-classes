//
//  roster.hpp
//  C867 Project
//
//  Created by Jeremy Greenwood on 12/17/18.
//  Copyright © 2018 Jeremy Greenwood. All rights reserved.
//

#ifndef roster_h
#define roster_h

#include "networkStudent.h"
#include "securityStudent.h"
#include "softwareStudent.h"

/* -------- A -------- */
class Roster {
private:
    int lastElementIndex_ = 0;
    
    /* -------- E.1 -------- */
    Student* classRosterArray_[];
    
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
    void printAverageDaysInCourse(string studentID);
    
    /* -------- E.3.e -------- */
    void printInvalidEmails();
    
    /* -------- E.3.f -------- */
    void printByDegreeProgram(Degree);
    
    int size();
};

#endif /* roster_h */
