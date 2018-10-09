package com.zyh.blockchain.eth.account;

import com.zyh.blockchain.eth.common.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

public class PrivateKeyManager {
    private final Logger log = LogManager.getLogger(getClass());
    private static Web3j web3j = Web3j.build(new HttpService(Config.RPC_URL));

    /**
     * 导出私钥
     *
     * @param keystorePath 账号的keystore路径
     * @param password     密码
     */
    public String exportPrivateKey(String keystorePath, String password) {
        try {
            Credentials credentials = WalletUtils.loadCredentials(password,keystorePath);
            BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();
            log.info("exportPrivateKey : " + privateKey.toString(16));
            return privateKey.toString(16);
        } catch (IOException | CipherException e) {
            log.error("exportPrivateKey exception",e);
        }
        return null;
    }

    /**
     * 导入私钥
     *
     * @param privateKey 私钥
     * @param password   密码
     * @param directory  存储路径 默认测试网络WalletUtils.getTestnetKeyDirectory() 默认主网络 WalletUtils.getMainnetKeyDirectory()
     */
    public String importPrivateKey(BigInteger privateKey, String password, String directory) {
        ECKeyPair ecKeyPair = ECKeyPair.create(privateKey);
        try {
            String keystoreName = WalletUtils.generateWalletFile(password,ecKeyPair,new File(directory),true);
            log.info("keystore name " + keystoreName);
            return keystoreName;
        } catch (CipherException | IOException e) {
            log.error("importPrivateKey exception",e);
        }
        return null;
    }
}
