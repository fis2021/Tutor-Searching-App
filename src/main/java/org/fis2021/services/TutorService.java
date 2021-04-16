package org.fis2021.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.fis2021.exceptions.UsernameAlreadyExistsException;
import org.fis2021.models.Tutor;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class TutorService {
    private static ObjectRepository<Tutor> tutorRepository;
    private static Nitrite database;

    public static void init(){
        database = DatabaseService.getDatabase();
        tutorRepository = database.getRepository(Tutor.class);
    }

    public static void addTutor(String nume, String username, String parola, String materie, String specializare) throws UsernameAlreadyExistsException {
        checkTutorDoesNotAlreadyExist(username);
        tutorRepository.insert(new Tutor(nume, username, encodePassword(username,parola), materie, specializare));
    }

    private static void checkTutorDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        for (Tutor tutor : tutorRepository.find()) {
            if (Objects.equals(username, tutor.getUsername()))
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
