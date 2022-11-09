package indiv.budin.entity.vo;


import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudinUserRegisterVO implements Serializable {
    private String userAccount;

    private String email;

    private String password;

    private String userNickname;

    private String code;
}
