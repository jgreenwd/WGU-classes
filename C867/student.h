//
//  student.hpp
//  C867 Project
//
//  Created by Jeremy Greenwood on 12/17/18.
//  Copyright © 2018 Jeremy Greenwood. All rights reserved.
//

#ifndef student_h
#define student_h

#include <iostream>
#include "degree.h"
using std::string;

class Student {
    private:
        /* -------- D.1 -------- */
        string  student_ID_,
                first_name_,
                last_name_,
                email_address_;
        int     age_;
        int*    number_of_days_;
    
    protected:
        Degree  degree_;
    
    public:
        /* -------- CONSTRUCTOR D.2.c -------- */
        Student(string, string, string, string, int, int*, Degree);
    
        /* -------- DESTRUCTOR D.2.e -------- */
        ~Student();
    
        /* -------- ACCESSORS D.2.a -------- */
        string getStudentID(void);
        string getFirstName(void);
        string getLastName(void);
        string getEmailAddress(void);
        int getAge(void);
        int* getNumberOfDays(void);
    
        /* -------- MUTATORS D.2.b -------- */
        void setStudentID(string);
        void setFirstName(string);
        void setLastName(string);
        void setEmailAddress(string);
        void setAge(int);
        void setNumberOfDays(const int*);
    
        /* -------- D.2.f -------- */
        virtual Degree getDegreeProgram() = 0;
    
        /* -------- D.2.d -------- */
        virtual void print();
};

#endif /* student_h */
