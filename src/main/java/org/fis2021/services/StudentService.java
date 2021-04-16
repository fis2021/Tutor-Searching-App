package org.fis2021.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.fis2021.exceptions.UsernameAlreadyExistsException;
import org.fis2021.models.Student;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class StudentService {
    private static ObjectRepository<Student> studentRepository;
    private static Nitrite database;

    public static void initStudent(){
        database = DatabaseService.getDatabase();
        studentRepository = database.getRepository(Student.class);
    }

    public static void addStudent(String nume, String facultate, String specializare, String nrMatricol, String username, String parola) throws UsernameAlreadyExistsException {
        checkStudentDoesNotAlreadyExist(username);
        studentRepository.insert(new Student(nume, facultate, specializare, nrMatricol, username, encodePassword(username,parola)));
    }

    private static void checkStudentDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        for (Student student : studentRepository.find()) {
            if (Objects.equals(username, student.getUsername()))
                throw new UsernameAlreadyExistsException(username);
        }
    }

    private static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
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
