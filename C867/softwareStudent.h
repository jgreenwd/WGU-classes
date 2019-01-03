//
//  softwareStudent.hpp
//  C867 Project
//
//  Created by Jeremy Greenwood on 12/17/18.
//  Copyright © 2018 Jeremy Greenwood. All rights reserved.
//

#ifndef softwareStudent_h
#define softwareStudent_h

#include "student.h"

/* -------- D.3 -------- */
class SoftwareStudent : public Student {
    private:
        Degree degree_ = SOFTWARE;
    public:
        using Student::Student;
    
        // redundant constructor to comply with D.2.c
        SoftwareStudent(std::string, std::string, std::string, std::string, int, int*, Degree);
        ~SoftwareStudent();
    
        Degree getDegreeProgram() override;
        void print() override;
};

#endif /* softwareStudent_h */
