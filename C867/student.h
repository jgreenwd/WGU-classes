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
        int         age_;
        int*        number_of_days_;
    public:

        Student(std::string, std::string, std::string, std::string, int, int*);
        ~Student();

        std::string getStudentID(void);
        std::string getFirstName(void);
        std::string getLastName(void);
        std::string getEmailAddress(void);
        int getAge(void);
        int* getNumberOfDays(void);
    
        void setStudentID(std::string);
        void setFirstName(std::string);
        void setLastName(std::string);
        void setEmailAddress(std::string);
        void setAge(int);
        void setNumberOfDays(int*);
    
        virtual void print();
        virtual Degree getDegreeProgram();
        virtual void setDegreeProgram();
};

#endif /* student_h */
