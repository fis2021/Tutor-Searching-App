package org.fis2021.services;

import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;
import org.fis2021.exceptions.UserNotFoundException;
import org.fis2021.exceptions.UsernameAlreadyExistsException;
import org.fis2021.models.Tutor;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class TutorService {

    private static ObjectRepository<Tutor> tutorRepository;

    public static void initTutor(){
        tutorRepository = DatabaseService.getDatabase().getRepository(Tutor.class);
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

    public static Tutor getTutor(String username) throws UserNotFoundException {
        Cursor<Tutor> cursor = tutorRepository.find(ObjectFilters.eq("username", username));
        for(Tutor tutor : cursor){
            return tutor;
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

    public static String getHashedUserPassword(String username) throws UserNotFoundException {
        return getTutor(username).getParola();
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
