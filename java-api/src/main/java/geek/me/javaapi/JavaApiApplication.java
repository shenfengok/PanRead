package geek.me.javaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
@EnableCaching
public class JavaApiApplication {

	public static void main(String[] args) throws Exception {

//		SSLContext context = SSLContext.getInstance("TLS");
//		context.init(null, null, null);
//
//		SSLSocketFactory factory = (SSLSocketFactory) context.getSocketFactory();
//		SSLSocket socket = (SSLSocket) factory.createSocket();
//
//		String[] protocols = socket.getSupportedProtocols();
//
//		System.out.println("Supported Protocols: " + protocols.length);
//		for (int i = 0; i < protocols.length; i++) {
//			System.out.println(" " + protocols[i]);
//		}
//
//		protocols = socket.getEnabledProtocols();
//
//		System.out.println("Enabled Protocols: " + protocols.length);
//		for (int i = 0; i < protocols.length; i++) {
//			System.out.println(" " + protocols[i]);
//		}
//		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,SSLv3");
//		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		System.setProperty("https.protocols", "SSLv3");
		SpringApplication.run(JavaApiApplication.class, args);
	}

}
