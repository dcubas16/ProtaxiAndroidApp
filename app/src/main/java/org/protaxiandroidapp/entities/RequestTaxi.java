package org.protaxiandroidapp.entities;

/**
 * Created by DIEGO on 12/05/2017.
 */
public class RequestTaxi {

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


    public RequestTaxi(int clientId, Double latOrigin, Double lngOrigin, String originAddress, String originAddressNumber, String originReference, Double latDestination, Double lngDestination, String destinationReference, String paymentTypeId, String serviceTypeId) {
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
    }

    public RequestTaxi(Double latOrigin, Double lngOrigin, String originAddress, String originAddressNumber, String originReference, Double latDestination, Double lngDestination, String destinationReference, String paymentTypeId, String serviceTypeId) {
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
    }

    public RequestTaxi() {
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

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
