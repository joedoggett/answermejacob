package com.joe.answermejacob.AnswerMeJacob.service;


import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "nonce",
        "userId",
        "userName",
        "success",
        "passPhrase"
})
@XmlRootElement(name = "NoncePass")
public class NoncePass {

    @XmlElement(required = true)
    protected String nonce;
    protected Integer userId;
    protected String userName;
    protected boolean success;
    @XmlElement(required = true)
    protected String passPhrase;

    /**
     * Gets the value of the nonce property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getNonce() {
        return nonce;
    }

    /**
     * Sets the value of the nonce property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setNonce(String value) {
        this.nonce = value;
    }

    /**
     * Gets the value of the userId property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setUserId(Integer value) {
        this.userId = value;
    }

    /**
     * Gets the value of the userName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the value of the userName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setUserName(String value) {
        this.userName = value;
    }

    /**
     * Gets the value of the success property.
     *
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the value of the success property.
     *
     */
    public void setSuccess(boolean value) {
        this.success = value;
    }

    /**
     * Gets the value of the passPhrase property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPassPhrase() {
        return passPhrase;
    }

    /**
     * Sets the value of the passPhrase property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPassPhrase(String value) {
        this.passPhrase = value;
    }

}
