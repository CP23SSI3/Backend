package com.example.internhub.validators;

import com.example.internhub.entities.WorkDay;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class EnumWorkDayValidator implements ConstraintValidator<EnumWorkDayConstraint, List<String>> {
    @Override
    public void initialize(EnumWorkDayConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(List<String> workDayList, ConstraintValidatorContext constraintValidatorContext) {
        if (workDayList == null) return true;
        try {
            if (workDayList.size() > 0) {
                for (String workDay
                        : workDayList) {
                    WorkDay.valueOf(workDay.toLowerCase());
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
