//
//  student.hpp
//  C867 Project
//
//  Created by Jeremy Greenwood on 12/17/18.
//  Copyright © 2018 Jeremy Greenwood. All rights reserved.
//

#ifndef student_hpp
#define student_hpp

#include <iostream>
#include "degree.h"
using std::string;

class Student {
    private:
        std::string student_ID,
                    first_name,
                    last_name,
                    email_addr;
        Degree      degree;
        int         age,
                    days_remaining[];
    public:
        Student(std::string, std::string, std::string, std::string, Degree, int, int[]);
        ~Student();
        std::string Student::get_ID(void);
        void Student::set_ID(std::string);
        std::string Student::get_lname(void);
        void Student::set_lname(std::string);
        std::string Student::get_fname(void);
        void Student::set_fname(std::string);
        std::string Student::get_email(void);
        void Student::set_email(std::string);
        Degree Student::get_Degree(void);
        void Student::set_Degree(Degree);
        int Student::get_age(void);
        void Student::set_age(int);
        int[] Student::get_days_rem(void);
        void Student::set_days_rem(int[]);
}

#endif /* student_hpp */
