package indiv.budin.entity.vo;

import java.io.Serializable;

/**
 * @author
 * @date 2022/10/25 11 53
 * discription
 */
public class BudinUserVO implements Serializable {
    BudinUserInfoVO budinUserInfoVO;
    String token;

    public BudinUserInfoVO getBudinUserInfoVO() {
        return budinUserInfoVO;
    }

    public void setBudinUserInfoVO(BudinUserInfoVO budinUserInfoVO) {
        this.budinUserInfoVO = budinUserInfoVO;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
