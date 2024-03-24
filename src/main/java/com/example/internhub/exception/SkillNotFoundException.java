package com.example.internhub.exception;

public class SkillNotFoundException extends Exception{
    public SkillNotFoundException (String skillId)  {
        super("Skill id " + skillId + " not found.");
    }
}
