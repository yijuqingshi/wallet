package com.zyh.blockchain.btc.connect;

import com.fasterxml.jackson.databind.JsonNode;
import com.zyh.blockchain.btc.common.Config;

import java.util.List;


public class UsdtConnClient extends BtcConnClient {


    public static  final String mainAddress = "mnVW9oPYh8SvcHGviesPXLpGhPvhnkYksw";

    public static  final int propertyid = 2;

    public UsdtConnClient()
    {
        setmConfig(Config.mUsd_Config);
        initJsonRpcHttpClient();
    }


    @Override
    public double getBalance(String address) throws Throwable {

        JsonNode omni_getbalnace = mJsonRpcHttpClient.invoke("omni_getbalance", new Object[]{address, propertyid}, JsonNode.class);

        return omni_getbalnace.get("balance").asDouble();
    }


    @Override
    public String sendToAddress(String address, double count) throws Throwable {
           if (verifyAddress(address) && count > 0)
           {
              return  mJsonRpcHttpClient.invoke("omni_funded_send", new Object[]{mainAddress, address, propertyid, count, mainAddress}, String.class);
           }
           throw  new  Throwable(" verifyaddress is false or amount < 0");

    }



   public synchronized List getBlockTransactions(int index) throws Throwable {
       return mJsonRpcHttpClient.invoke("omni_listblocktransactions", new Object[]{index}, List.class);
   }


   public synchronized JsonNode getTransaction(String txid) throws Throwable {
       return  mJsonRpcHttpClient.invoke("omni_gettransaction",new Object[]{txid},JsonNode.class);
   }

    public static void main(String[] args)
    {
        try {
            UsdtConnClient usdtConnClient = new UsdtConnClient();
            String address = usdtConnClient.getNewAddress("yangwei");
            System.out.println(address);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
