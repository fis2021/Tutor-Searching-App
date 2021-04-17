package org.fis2021.services;

import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;
import org.fis2021.exceptions.UserNotFoundException;
import org.fis2021.exceptions.UsernameAlreadyExistsException;
import org.fis2021.models.Student;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StudentService {

    private static ObjectRepository<Student> studentRepository;

    public static void initStudent(){
        studentRepository = DatabaseService.getDatabase().getRepository(Student.class);
    }

    public static void addStudent(String nume, String facultate, String specializare, String nrMatricol, String username, String parola) throws UsernameAlreadyExistsException {
        checkStudentDoesNotAlreadyExist(username);
        studentRepository.insert(new Student(nume, facultate, specializare, nrMatricol, username, encodePassword(username,parola)));
    }

    private static void checkStudentDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        for (Student student : studentRepository.find()) {
            if (username.equals(student.getUsername()))
                throw new UsernameAlreadyExistsException(username);
        }
    }

    public static Student getStudent(String username) throws UserNotFoundException {
        Cursor<Student> cursor = studentRepository.find(ObjectFilters.eq("username", username));
        for(Student student : cursor){
            return student;
        }
        throw new UserNotFoundException(username);
    }

    public static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }

    public static String getHashedUserPassword(String username) throws UserNotFoundException{
        return getStudent(username).getParola();
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }
}
