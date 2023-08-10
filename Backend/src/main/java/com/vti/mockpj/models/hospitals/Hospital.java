package com.vti.mockpj.models.hospitals;

import com.vti.mockpj.models.doctors.D_Position;
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
public class Hospital {
    @Id
    private Long id;

    @Transient
    public static final String SEQUENCE_NAME = "hospital_sequence";

    private String avatar;

    private String hos_name;

    //Chuyên khoa
    private List<String> specialist;

    private String about;

    private List<String> listImage;

    //Chuyên khám
    private List<String> specializing;

    private String address;
}
