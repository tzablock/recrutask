package com.recr.dir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class FilesMatchRanking {
    private Map<String, Double> filesContentComp;
    private List<String> words;
    private static final int TOP_NUM = 10;

    public FilesMatchRanking(String machWords) {
        filesContentComp = new HashMap<>();
        this.words = splitStrToWords(machWords);
    }

    public void checkWordsInFile(Path fPath){
        try {
            Stream<String> lines = Files.lines(fPath);
            long wFound = lines.flatMap(
                                          l -> splitStrToWords(l).stream()
                                                                 .filter(
                                                                         w -> words.contains(w)
                                                                 ).map(
                                                                         w -> words.indexOf(w)
                                                                 )
                                 ).distinct().count();
            if (wFound != 0){
                filesContentComp.put(fPath.getFileName().toString(),(double)wFound/words.size());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public List<String> takeTopFile() {
        if (filesContentComp.isEmpty()){
            return Collections.singletonList("no matches found");
        } else {
            return filesContentComp.entrySet().stream()
                                              .sorted(
                                                      Map.Entry.comparingByKey()
                                              ).limit(TOP_NUM)
                                              .map(
                                                      e -> e.getKey() + ": " + (e.getValue()*100) + " % "
                                              ).collect(toList());
        }
    }

    private List<String> splitStrToWords(String wordsStr) {
        return Arrays.stream(wordsStr.split(" ")).map(String::trim).filter(w -> !w.isEmpty()).collect(toList());
    }
}
