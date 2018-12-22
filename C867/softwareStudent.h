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

class SoftwareStudent : public Student {
    private:
        Degree degree_ = SOFTWARE;
    public:
        SoftwareStudent(std::string, std::string, std::string, std::string, int, int*);
        virtual ~SoftwareStudent();
        virtual Degree getDegreeType();
};

#endif /* softwareStudent_h */
