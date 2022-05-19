package com.example.login;

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Scanner;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void ReadingFile(){
        try{
            File hamlet = new File("src/androidTest/java/com/example/login/hamlet.txt");
            Scanner scanner = new Scanner(hamlet);
            while (scanner.hasNext()){
                String data = scanner.nextLine();
                System.out.println(data);
            }
            scanner.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println();
    }
}