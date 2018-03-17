package be.yorian.myEtherProject;

import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthCoinbase;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import be.yorian.myEtherProject.helper.ServerHelper;
import rx.Subscription;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException, IOException, ExecutionException, CipherException
    {
        System.out.println( "let's start this new project" );
        
        ServerHelper helper = new ServerHelper();
        Web3j web3Server = helper.startserver();
        EthCoinbase coinBase = web3Server.ethCoinbase().send();
        System.out.println("coinbase: " + coinBase.getAddress());
        Web3ClientVersion web3ClientVersion = web3Server.web3ClientVersion().send();
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
        System.out.println( "client: "+clientVersion);
        Credentials creds = WalletUtils.loadCredentials("vdou4ept","/Users/ringo/Library/Ethereum/geth/data/02/keystore/UTC--2018-02-23T20-48-49.446642000Z--f31e8623a6afa9190b433f4b9728faec5d6b19b8");
		BigInteger min = new BigInteger("20000000000");
		BigInteger max = new BigInteger("4300000");
        GreeterO contract = GreeterO.load(
        		"0x5e6d3bd77c23fe4fc552f6d3ef13a7e15b647585", web3Server, creds , min, max);
        System.out.println( "contractaddress: "+contract.getContractAddress());
        Utf8String message = contract.greet().get();
        System.out.println("Message returned by Contract.greet(): " + message.toString());
        
        
//        Utf8String rewardReasonMsg = new Utf8String("dag ringo!");
//        Uint256 value = new Uint256(max);
//        Future<TransactionReceipt> setGreeting = contract.setGreeting(rewardReasonMsg, value);
//        TransactionReceipt transactionReceipt = setGreeting.get();
//        System.out.println("transaction: " + transactionReceipt.getTransactionHash());
        
        EthGetTransactionReceipt transactionReceipt = web3Server.ethGetTransactionReceipt("0x89e152c1f14074d63bcee8d3c1875aaa598b371292b40aba607d5114fab9d7f7").sendAsync().get();

        if (transactionReceipt.getTransactionReceipt().isPresent()) {
        	String contractAddress = transactionReceipt.getTransactionReceipt().get().getContractAddress();
        	System.out.println("address: "+contractAddress);
        } else {
        	System.out.println("shit happens");
        }
        System.out.println("ok");

    }

	
}
