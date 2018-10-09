package com.zyh.blockchain.btc.utls;

import com.fasterxml.jackson.databind.JsonNode;
import com.zyh.blockchain.btc.ReceiveCoinListener;
import com.zyh.blockchain.btc.bean.Transaction;
import com.zyh.blockchain.btc.client.UsdtRpcClent;
import com.zyh.blockchain.btc.connect.UsdtConnClient;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class UsdtSannerBlockJob implements Runnable {


    private int nextSannerBlockCount = 684609;

    private UsdtConnClient mUsdtConnClient;

    public UsdtSannerBlockJob(UsdtConnClient mUsdtConnClient ) {
        this.mUsdtConnClient = mUsdtConnClient;
        mListeners = new LinkedHashSet<>();
    }

    public int getNextSannerBlockCount() {
        return nextSannerBlockCount;
    }

    public void setNextSannerBlockCount(int nextSannerBlockCount) {
        this.nextSannerBlockCount = nextSannerBlockCount;
    }

    public long getCheckInterval() {
        return checkInterval;
    }

    public void setCheckInterval(long checkInterval) {
        this.checkInterval = checkInterval;
    }

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    private long checkInterval = 7000;

    private boolean isStop = false;

    private Set<ReceiveCoinListener> mListeners;


    public boolean addReceiveListener(ReceiveCoinListener mReceiveCoinListener)
    {
         return  mListeners.add(mReceiveCoinListener);
    }

    public boolean removeReceiveListener(ReceiveCoinListener mReceiveCoinListener)
    {
         return  mListeners.remove(mReceiveCoinListener);
    }


    public void clearReceiveListener()
    {
          mListeners.clear();
    }

    @Override
    public void run() {


        while (!isStop && !Thread.interrupted())
        {
            long nextCheck = System.currentTimeMillis() + checkInterval;
            int blockCount = UsdtRpcClent.getInstance().getBlockCount();

            while (nextSannerBlockCount < blockCount) {
                try {

                    System.out.println( "blockCount = " + blockCount + "        nextSannerBlockCount = " + nextSannerBlockCount);
                    List<String> blockTransactions =  mUsdtConnClient.getBlockTransactions(nextSannerBlockCount + 1);
                    for (String txid : blockTransactions)
                    {
                        JsonNode transaction = mUsdtConnClient.getTransaction(txid);
                        JsonNode valid = transaction.get("valid");
                        JsonNode confirmations = transaction.get("confirmations");
                        JsonNode propertyid = transaction.get("propertyid");
                        if (valid != null && confirmations != null && propertyid != null)
                        {
                            if (transaction.get("valid").asBoolean() && transaction.get("confirmations").asInt(0) > 6 && transaction.get("propertyid").asInt() == UsdtConnClient.propertyid)
                            {

                                Transaction mTransaction1 = new Transaction();
                                mTransaction1.setBlockhash(transaction.get("blockhash").asText());
                                mTransaction1.setAmount(transaction.get("amount").asDouble());
                                mTransaction1.setConfirmations(transaction.get("confirmations").asInt(0));
                                mTransaction1.setReceiveAddress(transaction.get("referenceaddress").asText());
                                mTransaction1.setValid(transaction.get("valid").asBoolean());
                                mTransaction1.setTxid(transaction.get("txid").asText());
                                for (ReceiveCoinListener mCoinListener : mListeners)
                                {
                                    mCoinListener.receiveCoins(mTransaction1);
                                }

                            }
                        }

                        System.out.println( "blockhash = " + transaction.get("blockhash").asText());
                    }
                    nextSannerBlockCount = nextSannerBlockCount + 1;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    isStop = true;
                    break;
                }
            }
            try {
                Thread.sleep(Math.max(nextCheck - System.currentTimeMillis(),100));
            } catch (InterruptedException e) {
                e.printStackTrace();
                isStop = true;
                break;
            }
        }

    }



}
