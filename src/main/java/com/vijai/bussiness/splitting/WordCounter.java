package com.vijai.bussiness.splitting;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WordCounter {
    public int countWord(String word, File file) throws FileNotFoundException {
        int count = 0;
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            if (!scanner.hasNext()) {
                break;
            }
                String nextToken = scanner.next();

                if (nextToken.contains(word))
                    count++;
            }

        scanner.close();
        return count;
    }
}
