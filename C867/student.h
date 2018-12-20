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
        std::string student_ID,
                    first_name,
                    last_name,
                    email_addr;
        Degree      degree;
        int         age;
        int*        days_remaining;
    public:

        Student(std::string, std::string, std::string, std::string, Degree, int, int*);
        ~Student();

        std::string get_ID(void);
        void set_ID(std::string);
        std::string get_lname(void);
        void set_lname(std::string);
        std::string get_fname(void);
        void set_fname(std::string);
        std::string get_email(void);
        void set_email(std::string);
        Degree get_Degree(void);
        void set_Degree(Degree);
        int get_age(void);
        void set_age(int);
        int* get_days_rem(void);
        void set_days_rem(int*);
    
        virtual void print();
        virtual void getDegreeProgram();
};

#endif /* student_h */
