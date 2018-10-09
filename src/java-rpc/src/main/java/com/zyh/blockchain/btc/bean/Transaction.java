package com.zyh.blockchain.btc.bean;

public class Transaction {

   private String txid;

   private String blockhash;

   private String receiveAddress;

   private String sendAddress;

   private double amount;

   private boolean valid;

   private  int confirmations;

    public Transaction(){

    }
    public Transaction(String txid, String blockhash, String receiveAddress, String sendAddress, double amount, boolean valid, int confirmations) {
        this.txid = txid;
        this.blockhash = blockhash;
        this.receiveAddress = receiveAddress;
        this.sendAddress = sendAddress;
        this.amount = amount;
        this.valid = valid;
        this.confirmations = confirmations;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public String getBlockhash() {
        return blockhash;
    }

    public void setBlockhash(String blockhash) {
        this.blockhash = blockhash;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public int getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(int confirmations) {
        this.confirmations = confirmations;
    }
}
