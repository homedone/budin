package indiv.budin.demo.server.api;

public class People {
    public People() {

    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String sex;
    public Address address;

    @Override
    public String toString() {
        return "People{" +
                "sex='" + sex + '\'' +
                ", address=" + address.toString() +
                '}';
    }
}
