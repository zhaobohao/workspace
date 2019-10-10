package com.proto;

import com.google.protobuf.InvalidProtocolBufferException;
import com.proto.java.StudentEntity;
import org.testng.annotations.Test;

public class StudentEntityTest {


    @Test
    public void protocTest() {
        StudentEntity.Student.Builder builder = StudentEntity.Student.newBuilder();
        builder.setId(1).setName("new Student").setNickname("baddy");
        StudentEntity.Student student = builder.build();
        System.out.println("befor serierial " + student.toString());

        for (byte b : student.toByteArray()) {
            System.out.print(b);
        }
        System.out.println();

        byte[] b = student.toByteArray();
        try {
            StudentEntity.Student parseStudent = StudentEntity.Student.parseFrom(b);
            System.out.println(parseStudent.toString());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }


    }


}
