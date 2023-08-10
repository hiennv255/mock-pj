package com.vti.mockpj.models.clinics;

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
public class Clinic {
    @Id
    private Long id;

    @Transient
    public static final String SEQUENCE_NAME = "clinic_sequence";

    private String avatar;

    private String clinic_name;

    private String about;

    private List<String> listImage;

    private List<String> services;

    private String address;
}
