package org.protaxiandroidapp.dto;

import org.protaxiandroidapp.entities.RequestTaxi;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by DIEGO on 09/06/2017.
 */
public class RequestTaxiDTO implements Serializable {

    private static final long serialVersionUID = -7822549020914834304L;

    private int id;

    private int clientId;

    private Double latOrigin;

    private Double lngOrigin;

    private String originAddress;

    private String originAddressNumber;

    private String originReference;

    private Double latDestination;

    private Double lngDestination;

    private String destinationReference;

    private String paymentTypeId;

    private String serviceTypeId;

    private Date requesDate;


    public RequestTaxiDTO() {
        super();
    }

    public RequestTaxiDTO(int id, int clientId, Double latOrigin, Double lngOrigin, String originAddress,
                          String originAddressNumber, String originReference, Double latDestination, Double lngDestination,
                          String destinationReference, String paymentTypeId, String serviceTypeId, Date requesDate) {
        super();
        this.id = id;
        this.clientId = clientId;
        this.latOrigin = latOrigin;
        this.lngOrigin = lngOrigin;
        this.originAddress = originAddress;
        this.originAddressNumber = originAddressNumber;
        this.originReference = originReference;
        this.latDestination = latDestination;
        this.lngDestination = lngDestination;
        this.destinationReference = destinationReference;
        this.paymentTypeId = paymentTypeId;
        this.serviceTypeId = serviceTypeId;
        this.requesDate = requesDate;
    }

    public RequestTaxiDTO(RequestTaxi requestTaxi) {
        this.clientId = requestTaxi.getClientId();
        this.latOrigin = requestTaxi.getLatOrigin();
        this.lngOrigin = requestTaxi.getLngOrigin();
        this.originAddress = requestTaxi.getOriginAddress();
        this.originAddressNumber = requestTaxi.getOriginAddressNumber();
        this.originReference = requestTaxi.getOriginReference();
        this.latDestination = requestTaxi.getLatDestination();
        this.lngDestination = requestTaxi.getLngDestination();
        this.destinationReference = requestTaxi.getDestinationReference();
        this.paymentTypeId = requestTaxi.getPaymentTypeId();
        this.serviceTypeId = requestTaxi.getServiceTypeId();
        this.requesDate = new Date();
    }

    public int getClientId() {
        return clientId;
    }
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    public Double getLatOrigin() {
        return latOrigin;
    }
    public void setLatOrigin(Double latOrigin) {
        this.latOrigin = latOrigin;
    }
    public Double getLngOrigin() {
        return lngOrigin;
    }
    public void setLngOrigin(Double lngOrigin) {
        this.lngOrigin = lngOrigin;
    }
    public String getOriginAddress() {
        return originAddress;
    }
    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }
    public String getOriginAddressNumber() {
        return originAddressNumber;
    }
    public void setOriginAddressNumber(String originAddressNumber) {
        this.originAddressNumber = originAddressNumber;
    }
    public String getOriginReference() {
        return originReference;
    }
    public void setOriginReference(String originReference) {
        this.originReference = originReference;
    }
    public Double getLatDestination() {
        return latDestination;
    }
    public void setLatDestination(Double latDestination) {
        this.latDestination = latDestination;
    }
    public Double getLngDestination() {
        return lngDestination;
    }
    public void setLngDestination(Double lngDestination) {
        this.lngDestination = lngDestination;
    }
    public String getDestinationReference() {
        return destinationReference;
    }
    public void setDestinationReference(String destinationReference) {
        this.destinationReference = destinationReference;
    }
    public String getPaymentTypeId() {
        return paymentTypeId;
    }
    public void setPaymentTypeId(String paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }
    public String getServiceTypeId() {
        return serviceTypeId;
    }
    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Date getRequesDate() {
        return requesDate;
    }
    public void setRequesDate(Date requesDate) {
        this.requesDate = requesDate;
    }
}
