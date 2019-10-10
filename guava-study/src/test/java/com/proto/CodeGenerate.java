package com.proto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CodeGenerate {


    public static void main(String[] args) {
        String protoFile = "student-entity.proto";//
        String strCmd = "protoc.exe --proto_path=D:/workspace/guava-study/src/test/java/com/proto --java_out=D:/workspace/guava-study/src/test/java D:/workspace/guava-study/src/test/java/com/proto/" + protoFile;

        try {
            Runtime rt = Runtime.getRuntime();
            String[] commands = {"system.exe", "-get t"};
            Process proc = rt.exec(strCmd);
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(proc.getInputStream()));
            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(proc.getErrorStream()));// read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }// read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}