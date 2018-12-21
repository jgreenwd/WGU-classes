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
        std::string student_ID_,
                    first_name_,
                    last_name_,
                    email_address_;
        Degree      degree_program_;
        int         age_;
        int*        number_of_days_;
    public:

        Student(std::string, std::string, std::string, std::string, Degree, int, int*);
        ~Student();

        std::string getStudentID(void);
        void setStudentID(std::string);
        std::string getLastName(void);
        void setLastName(std::string);
        std::string getFirstName(void);
        void setFirstName(std::string);
        std::string getEmailAddress(void);
        void setEmailAddress(std::string);
        virtual void getDegreeProgram();
        virtual void setDegreeProgram();
        int getAge(void);
        void setAge(int);
        int* getNumberOfDays(void);
        void setNumberOfDays(int*);
    
        virtual void print();
    
};

#endif /* student_h */
