package com.example.internhub.services;

import com.example.internhub.entities.Document;
import com.example.internhub.entities.WorkDay;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

//@Service
public class ArrayStringService {

    public String getStringFromWorkDayArray(List<String> workDay) {
        try {
            if(workDay==null || workDay.size() == 0) return null;
            for (String day : workDay) {
                Document.valueOf(day);
            }
            return String.join(",", workDay);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown data for work day types' enum");
        }
    }

    public String getStringFromDocumentArray(List<String> documents) {
        try {
            if(documents==null || documents.size() == 0) return null;
            for (String document : documents) {
                Document.valueOf(document);
            }
            return String.join(",", documents);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown data for document types' enum");
        }
    }

}
