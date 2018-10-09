package com.zyh.blockchain.btc.connect;

import com.googlecode.jsonrpc4j.Base64;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.zyh.blockchain.btc.common.Config;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class BaseConnClient {

    protected JsonRpcHttpClient mJsonRpcHttpClient;

    protected Config mConfig;

    public BaseConnClient(Config mConfig) {
        this.mConfig = mConfig;
    }

    public void  initJsonRpcHttpClient()
    {
        String cred = Base64.encodeBytes((mConfig.getRpc_user() + ":" + mConfig.getRpc_password()).getBytes());
        Map<String,String> header = new HashMap<String, String>(1);
        header.put("Authorization","Basic " + cred);
        try {
            mJsonRpcHttpClient = new JsonRpcHttpClient(new URL("http://" + mConfig.getRpc_Address() + ":" + mConfig.getRpc_port()),header);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    public void setmConfig(Config mConfig) {
        this.mConfig = mConfig;
    }
}
