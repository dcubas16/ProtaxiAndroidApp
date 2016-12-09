package org.protaxiandroidapp.restful.entities;

/**
 * Created by DIEGO on 23/10/2016.
 */
public class Client {

    public int id;
    public int identityDocTypeId;
    public String identityDoc;
    public String email;
    public String nickName;
    public String photo;
    public String password;
    public String countryId;
    public String phoneNumber;

    public Client() {
    }

    public Client(int id, int identityDocTypeId, String identityDoc, String email, String nickName, String photo,
                  String password, String countryId, String phoneNumber) {
        super();
        this.id = id;
        this.identityDocTypeId = identityDocTypeId;
        this.identityDoc = identityDoc;
        this.email = email;
        this.nickName = nickName;
        this.photo = photo;
        this.password = password;
        this.countryId = countryId;
        this.phoneNumber = phoneNumber;
    }
}
