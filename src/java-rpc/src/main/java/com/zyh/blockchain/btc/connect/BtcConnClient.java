package com.zyh.blockchain.btc.connect;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zyh.blockchain.btc.common.Config;

import java.util.*;

public class BtcConnClient extends BaseConnClient{


    public BtcConnClient() {
        super(Config.mBtc_Config);
    }

    public String getNewAddress(String account) throws Throwable {

        return  mJsonRpcHttpClient.invoke("getnewaddress",new Object[]{account},Object.class).toString();

    }

    public String getAddressPriKey(String address) throws Throwable {

        return  mJsonRpcHttpClient.invoke("dumpprivkey",new Object[]{address},Object.class).toString();

    }

    public Boolean wallteLock()  {

        try {
            mJsonRpcHttpClient.invoke("walletlock",new Object[]{},Object.class);
            return  true;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    public Boolean unlockWallte(String passphrase) {
        try {
            mJsonRpcHttpClient.invoke("walletpassphrase",new Object[]{passphrase,90},Object.class);
            return true;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    public String sendToAddress(String address, double count) throws Throwable {

        return  mJsonRpcHttpClient.invoke("sendtoaddress",new Object[]{address,count},Object.class).toString();
    }

    public List<String> getAddressTransactionInfo(String address) {
        return null;
    }


    public double getBalance(String address) throws Throwable {
        double getbalance = 0;
        List<Map<String,Object>> unspent = listUnspent(address);

        for (Map<String,Object> map : unspent)
        {
              getbalance = getbalance + (Double) map.get("amount");
        }
        return getbalance;
    }


    public List listUnspent(String address,int comfirm_start,int comfirm_end) throws Throwable {

        List<String> address1 = new ArrayList<String>();
        address1.add(address);
        return mJsonRpcHttpClient.invoke("listunspent",new Object[]{comfirm_start,comfirm_end,address1},ArrayList.class);

    }

    public List listUnspent(String address) throws Throwable {
        List<String> address1 = new ArrayList<String>();
        address1.add(address);
        return mJsonRpcHttpClient.invoke("listunspent",new Object[]{6,99999999,address1}, ArrayList.class);

    }


    public boolean verifyAddress(String address) throws Throwable {
        ObjectNode mObjectNode = mJsonRpcHttpClient.invoke("validateaddress",new Object[]{address},ObjectNode.class);
        return  mObjectNode.get("isvalid").asBoolean();
    }


    public ArrayList generate(int number) throws Throwable {
        return mJsonRpcHttpClient.invoke("generate",new Object[]{number},ArrayList.class);
    }


    public int getBlockCount() throws Throwable {
          return mJsonRpcHttpClient.invoke("getblockcount",new Object[]{},Integer.class);
    }
}
