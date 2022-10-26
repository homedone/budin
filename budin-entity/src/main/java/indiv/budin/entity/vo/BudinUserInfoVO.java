package indiv.budin.entity.vo;

import java.io.Serializable;

/**
 * @author
 * @date 2022/10/25 22 18
 * discription
 */
public class BudinUserInfoVO implements Serializable {
    private String userId;
    private String userAccount;
    public String userNickname;
    public String email;
    public String telephone;
    public Integer storageSize;
    public String description;

    public BudinUserInfoVO() {

    }

    public BudinUserInfoVO(String userAccount, String userNickname, String email, String telephone) {
        this.userAccount = userAccount;
        this.userNickname = userNickname;
        this.email = email;
        this.telephone = telephone;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(Integer storageSize) {
        this.storageSize = storageSize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
