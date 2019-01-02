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

const std::string studentData[] = {
    "A1,John,Smith,John1989@gm ail.com,20,30,35,40,SECURITY",
    "A2,Suzan,Erickson,Erickson_1990@gmailcom,19,50,30,40,NETWORK",
    "A3,Jack,Napoli,The_lawyer99yahoo.com,19,20,40,33,SOFTWARE",
    "A4,Erin,Black,Erin.black@comcast.net,22,50,58,40,SECURITY",
    "A5,Jeremy,Greenwood,jgre369@wgu.edu,41,30,30,30,SOFTWARE"
};

int days1[3] {30,35,40},
    days2[3] {50,30,40},
    days3[3] {20,40,33},
    days4[3] {50,58,40},
    days5[3] {30,30,30};

class Roster {
    private:
        int size_ = 5;
        /* -------- E.1 -------- */
        Student* classRosterArray[5] = {
            new SecurityStudent("A1", "John", "Smith", "John1989@gmail.com", 20, days1),
            new NetworkStudent("A2", "Suzan", "Erickson", "Erickson_1990@gmail.com", 19, days2),
            new SoftwareStudent("A3", "Jack", "Napoli", "The_lawyer99@yahoo.com", 19, days3),
            new SecurityStudent("A4", "Erin", "Black", "Erin.black@comcast.net", 22, days4),
            new SoftwareStudent("A5", "Jeremy", "Greenwood", "jgre369@wgu.edu", 41, days5)
        };
    public:
        Roster();
        ~Roster();
    
        /* -------- E.3.a -------- */
        void add(std::string, std::string, std::string, std::string, int, int, int, int, Degree);
    
        /* -------- E.3.b -------- */
        void remove(std::string studentID);
    
        /* -------- E.3.c -------- */
        void printAll();
    
        /* -------- E.3.d -------- */
        void printAverageDaysInCourse(std::string studentID);
    
        /* -------- E.3.e -------- */
        void printInvalidEmails();
    
        /* -------- E.3.f -------- */
        void printByDegreeProgram(Degree);
};

#endif /* roster_h */
