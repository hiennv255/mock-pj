package com.vti.mockpj.models.doctors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Doctor {
    @Id
    private Long id;

    @Transient
    public static final String SEQUENCE_NAME = "doctor_sequence";

    private String avatar;

    private String fullName;

    //Chuyên khoa
    private List<String> specialist;

    private D_Position position;

    private String about;

    private List<String> listImage;

    private String workPlace;

    private String experience;
}
