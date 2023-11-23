package com.example.internhub.services;

import com.example.internhub.entities.Document;
import com.example.internhub.entities.WorkDay;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

//@Service
public class ArrayStringService {

    public String getStringFromWorkDayArray(WorkDay[] workDay) {
        try {
            List<String> workDayString = new ArrayList<>();
            for (WorkDay day : workDay) {
                workDayString.add(day.name());
            }
            return String.join(",", workDayString);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public String getStringFromDocumentsArray(Document[] documents) {
        try {
            List<String> documentString = new ArrayList<>();
            for (Document document : documents) {
                documentString.add(document.name());
            }
            return String.join(",", documentString);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
