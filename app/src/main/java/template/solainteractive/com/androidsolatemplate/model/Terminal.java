package template.solainteractive.com.androidsolatemplate.model;

import com.google.gson.annotations.SerializedName;

import template.solainteractive.com.androidsolatemplate.Constants;

/**
 * Created by Sola on 09/03/2018.
 */

public class Terminal {
    @SerializedName(Constants.APIObject.TERMINAL_ID)
    String terminalId;
    @SerializedName(Constants.APIObject.TERMINAL_NAME)
    String terminalName;
    @SerializedName(Constants.APIObject.TERMINAL_LATITUDE)
    Double terminalLatitude;
    @SerializedName(Constants.APIObject.TERMINAL_LONGITUDE)
    Double terminalLongitude;
    @SerializedName(Constants.APIObject.TERMINAL_TYPE_ID)
    int terminalTypeId;
    @SerializedName(Constants.APIObject.TERMINAL_ADDRESS)
    String terminalAddress;
    @SerializedName(Constants.APIObject.TERMINAL_OPEN_TIME)
    String terminalOpenTime;
    @SerializedName(Constants.APIObject.TERMINAL_CLOSED_TIME)
    String terminalClosedTime;
    @SerializedName(Constants.APIObject.METADATA)
    String metadata;
    @SerializedName(Constants.APIObject.DESCRIPTION)
    String description;
    @SerializedName(Constants.APIObject.NETWORK_TYPE)
    String networkType;
    @SerializedName(Constants.APIObject.POSTAL_CODE)
    String postalCode;
    @SerializedName(Constants.APIObject.TERMINAL_ACTIVE_STATUS)
    String terminalActiveStatus;
    @SerializedName(Constants.APIObject.AVATAR_PICTURE)
    String avatarPicture;
    @SerializedName(Constants.APIObject.RATE_ID)
    int rateId;
    @SerializedName(Constants.APIObject.DAILY_CAP)
    int dailyCap;
    @SerializedName(Constants.APIObject.HOURLY_RATE)
    float hourlyRate;

    public String getTerminalId() {
        return terminalId;
    }

    public String getTerminalName() {return terminalName;}

    public Double getTerminalLatitude() {
        return terminalLatitude;
    }

    public Double getTerminalLongitude() { return terminalLongitude; }

    public int getTerminalTypeId() {
        return terminalTypeId;
    }

    public String getTerminalAddress() {
        return terminalAddress;
    }

    public String getTerminalOpenTime() {
        return terminalOpenTime;
    }

    public String getTerminalClosedTime() {
        return terminalClosedTime;
    }

    public String getMetadata() {
        return metadata;
    }

    public String getDescription() {
        return description;
    }

    public String getNetworkType() { return networkType; }

    public String getPostalCode() {
        return postalCode;
    }

    public String getTerminalActiveStatus() {
        return terminalActiveStatus;
    }

    public int getRateId() {
        return rateId;
    }

    public String getAvatarPicture() {
        return avatarPicture;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }

    public void setTerminalLatitude(Double terminalLatitude) {
        this.terminalLatitude = terminalLatitude;
    }

    public void setTerminalLongitude(Double terminalLongitude) {
        this.terminalLongitude = terminalLongitude;
    }

    public void setTerminalTypeId(int terminalTypeId) {
        this.terminalTypeId = terminalTypeId;
    }

    public void setTerminalAddress(String terminalAddress) {
        this.terminalAddress = terminalAddress;
    }

    public void setTerminalOpenTime(String terminalOpenTime) {
        this.terminalOpenTime = terminalOpenTime;
    }

    public void setTerminalClosedTime(String terminalClosedTime) {
        this.terminalClosedTime = terminalClosedTime;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setTerminalActiveStatus(String terminalActiveStatus) {
        this.terminalActiveStatus = terminalActiveStatus;
    }

    public void setAvatarPicture(String avatarPicture) {
        this.avatarPicture = avatarPicture;
    }

    public void setRateId(int rateId) {
        this.rateId = rateId;
    }

    public void setDailyCap(int dailyCap) {
        this.dailyCap = dailyCap;
    }

    public void setHourlyRate(float hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
}
