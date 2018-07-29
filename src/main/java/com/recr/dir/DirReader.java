package com.recr.dir;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class DirReader {
    private String lineWithMatch = "";
    private Scanner keyboard;
    private String dirPath;

    public DirReader(String dirPath) {
        lineWithMatch = "";
        keyboard = new Scanner(System.in);
        this.dirPath = dirPath;
    }

    public Boolean shouldReadNextLine(){
        return lineWithMatch != ":quit";
    }

    public void readNextLine(){
        lineWithMatch = keyboard.nextLine();
    }

    public void printScanningResult(){
        try {
            FilesMatchRanking fmr = new FilesMatchRanking(lineWithMatch);
            Files.walkFileTree(Paths.get(dirPath), new FileVisitorImpl(fmr));
            fmr.takeTopFile().forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
