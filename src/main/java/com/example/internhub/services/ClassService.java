package com.example.internhub.services;

import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@Service
public class ClassService {

//    public static Object mergePersons(Object obj1, Object obj2) throws Exception {
//        Field[] allFields = obj1.getClass().getDeclaredFields();
//        for (Field field : allFields) {
//            if (Modifier.isPublic(field.getModifiers()) && field.isAccessible() && field.get(obj1) == null && field.get(obj2) != null) {
//                field.set(obj1, field.get(obj2));
//            }
//        }
//        return obj1;
//    }

//    public Object mergeClass(Object obj1, Object obj2) throws IllegalAccessException {
//        Field[] allFields = obj1.getClass().getDeclaredFields();
//        System.out.println("merge class");
//        for (Field field : allFields) {
//            field.setAccessible(true);
////            System.out.println(field.get(obj2));
//            if (field.get(obj2) != null) {
//                field.set(obj1, field.get(obj2));
//            }
//        }
//        return obj1;
//    }

    public Object mergeClass(Object obj1, Object obj2) throws IllegalAccessException {
        Field[] allFieldsObj1 = obj1.getClass().getDeclaredFields();
//        Field[] allFieldsObj2 = obj2.getClass().getDeclaredFields();
//        System.out.println("merge class");
        for (Field field : allFieldsObj1) {
            field.setAccessible(true);
//            System.out.println(field.get(obj2));
//            if (field.get(obj2) != null) {
            try {
                field.set(obj1, field.get(obj2));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
//            }
        }
        return obj1;
    }
}

