package org.fis2021.services;

import org.dizitart.no2.Nitrite;

import static org.fis2021.services.FileSystemService.getPathToFile;

public class DatabaseService {
        private static Nitrite database;

        public static void initDatabase() {
            database = Nitrite.builder()
                    .filePath(getPathToFile("registration.db").toFile())
                    .openOrCreate("test", "test");
        }

    public static Nitrite getDatabase(){
        return database;
    }
}
