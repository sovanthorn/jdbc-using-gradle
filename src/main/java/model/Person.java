package model;

import lombok.*;
import lombok.experimental.Accessors;

//@Getter
//@Setter
//@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Builder
@Accessors(chain = true)
public class Person {
    private int id;
    private String fullName;
    private String gender;
    private String email;
    private String address;

}
