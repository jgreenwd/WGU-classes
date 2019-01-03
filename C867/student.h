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

class Student {
    private:
        /* -------- D.1 -------- */
        std::string student_ID_,
                    first_name_,
                    last_name_,
                    email_address_;
        int         age_;
        int*        number_of_days_;
    
    public:
        /* -------- CONSTRUCTOR D.2.c -------- */
        Student(std::string, std::string, std::string, std::string, int, int*);
        /* -------- DESTRUCTOR D.2.e -------- */
        ~Student();
    
        /* -------- ACCESSORS D.2.a -------- */
        std::string getStudentID(void);
        std::string getFirstName(void);
        std::string getLastName(void);
        std::string getEmailAddress(void);
        int getAge(void);
        const int* getNumberOfDays(void);
    
        /* -------- MUTATORS D.2.b -------- */
        void setStudentID(std::string);
        void setFirstName(std::string);
        void setLastName(std::string);
        void setEmailAddress(std::string);
        void setAge(int);
        void setNumberOfDays(const int*);
    
        /* -------- D.2.f -------- */
        virtual Degree getDegreeProgram();
        virtual void setDegreeProgram();
    
        /* -------- D.2.d -------- */
        virtual void print();
};

#endif /* student_h */
