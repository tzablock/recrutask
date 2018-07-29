package com.recr;

import com.recr.dir.DirReader;

public class Start {
    public static void main(String[] args) {
        if(args.length != 1){
            throw new RuntimeException("Application should get only one argument with path to checked folder.");
        }
        final String inPath = args[0];
        DirReader dirReader = new DirReader(inPath);
        int f = 1;
        while (dirReader.shouldReadNextLine()){
            if (f != 1){
                dirReader.printScanningResult();
            } else {
                f--;
            }
            dirReader.readNextLine();
        }
    }
}
