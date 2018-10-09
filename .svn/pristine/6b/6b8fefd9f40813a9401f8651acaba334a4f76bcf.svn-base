package com.zyh.blockchain.btc.client;

import com.zyh.blockchain.btc.ClientRpcInterface;
import com.zyh.blockchain.btc.ReceiveCoinListener;
import com.zyh.blockchain.btc.bean.Transaction;
import com.zyh.blockchain.btc.connect.BtcConnClient;
import com.zyh.blockchain.btc.common.Config;
import wf.bitcoin.javabitcoindrpcclient.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class BtcRpcClient implements ClientRpcInterface {

    private static BtcRpcClient mBtcWallet;

    private URL DEFAULT_JSONRPC_URL;

    private BitcoinJSONRPCClient mBitcoinJSONRPCClient;

    private BitcoinAcceptor mBitcoinAcceptor;

    private Set<ReceiveCoinListener> mListeners;

    private boolean isStart = false;

    private BtcConnClient mBtcConnClient;

    public static BtcRpcClient getInstanse() {
        if (mBtcWallet == null)
        {
            synchronized (BtcRpcClient.class)
            {
                if (mBtcWallet == null)
                {
                    mBtcWallet = new BtcRpcClient();
                }
            }
        }
        return  mBtcWallet;
    }

    private BtcRpcClient() {
        try {
            DEFAULT_JSONRPC_URL = new URL("http://" + Config.BTC_RPC_USER + ':' + Config.BTC_RPC_PASSWORD + "@" + Config.BTC_RPC_ADDRESS + ":" + Config.BTC_RPC_PORT  + "/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        mBtcConnClient = new BtcConnClient();
        mBtcConnClient.initJsonRpcHttpClient();
        mBitcoinJSONRPCClient = new BitcoinJSONRPCClient(DEFAULT_JSONRPC_URL);
        mListeners = new LinkedHashSet<ReceiveCoinListener>();
    }

    public String getNewAddress(String account) throws Throwable {
        return mBtcConnClient.getNewAddress(account);
    }

    public String getAddressPriKey(String address) throws Throwable {
        return mBtcConnClient.getAddressPriKey(address);
    }

    public Boolean wallteLock() {
        return mBtcConnClient.wallteLock();
    }

    public Boolean unlockWallte(String passphrase) {
        return mBtcConnClient.unlockWallte(passphrase);
    }

    public String sendToAddress(String address, double count) throws Throwable {
        return mBtcConnClient.sendToAddress(address,count);
    }

    public List<String> getAddressTransactionInfo(String address) {
        return null;
    }

    public double getBalance(String address) throws Throwable {
        return mBtcConnClient.getBalance(address);
    }


    public void addReceiveCoinListener(ReceiveCoinListener listener) {
        mListeners.add(listener);
    }


    public void removeReceiveCoinListener(ReceiveCoinListener listener) {
        mListeners.remove(listener);
    }

    public void startReceiveCoinListener(ReceiveCoinListener receiveCoinListener) {
        mListeners.add(receiveCoinListener);
        if (!isStart) {
            mBitcoinAcceptor = new BitcoinAcceptor(mBitcoinJSONRPCClient);
            for (ReceiveCoinListener mReceiveCoinListener:mListeners)
            {
                BitcoinPaymentListener bitcoinPaymentListener = new ConfirmedPaymentListener() {
                    @Override
                    public void confirmed(BitcoindRpcClient.Transaction transaction) {
                        Transaction mTransaction1 = new Transaction(transaction.txId(),transaction.blockHash(),transaction.address(),"",transaction.amount(),true,transaction.confirmations());
                        mReceiveCoinListener.receiveCoins(mTransaction1);
                    }
                };
                mBitcoinAcceptor.addListener(bitcoinPaymentListener);
            }
            new Thread(mBitcoinAcceptor).start();
            isStart =true;
        }

    }

    public void stopReceiveCoinListener() {
        if (isStart){
            mBitcoinAcceptor.stopAccepting();
            mBitcoinAcceptor = null;
            isStart = false;
        }
    }

    public void  clearReceiveCoinListener(){
        mListeners.clear();
    }

    public static void main(String[] args) {
        final  String address;
        try {
            //获取地址
            address = getInstanse().getNewAddress("yangwei");
            System.out.println( "NewAddress = " + address);

            //开始接收币监听
            getInstanse().startReceiveCoinListener(new ReceiveCoinListener() {
                @Override
                public void receiveCoins(Transaction transaction) {
                    if (transaction.getReceiveAddress().equals(address)){
                        System.out.println( "address " + address + "   接收到了" + transaction.getAmount() + "个btc,请调用远程接口修改用户余额..");
                        getInstanse().clearReceiveCoinListener();
                        getInstanse().stopReceiveCoinListener();
                    }
                }
            });

            //锁定钱包
            getInstanse().wallteLock();

            //解锁钱包
            getInstanse().unlockWallte("yangwei");
            //获取私钥
            System.out.println( "privkey = " + getInstanse().getAddressPriKey(address));

            //验证某地址是否为比特币地址
            getInstanse().mBtcConnClient.verifyAddress(address);

            //转账40btc到新地址
            String  txid = getInstanse().sendToAddress(address,40.222222);
            System.out.println("txid = " + txid);
            // 获取新地址的余额
            double balance = getInstanse().getBalance(address);
            System.out.println("balance = " + balance);

            ArrayList list = getInstanse().mBtcConnClient.generate(8);

            System.out.println("generate  = " + list.toString());
            // 获取新地址的余额
            double balance1 = getInstanse().getBalance(address);
            System.out.println("balance = " + balance1);

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

}
