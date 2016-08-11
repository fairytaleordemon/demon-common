package com.yuanbosu.common.encrypt;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.google.common.collect.Maps;

/**
 * 参考文章：https://github.com/Sea-Monster/rsa_javaweb_javascript/tree/master/
 * rsa_encrypt_decrypt_demo
 * 
 * @author suyuanbo 
 * 			RSA 非对称加密算法 应用场景，公司内安全需要走HTTPs，但是HTTPS证书没有审批完成，所有先使用RSA
 *         加密算法。
 * @content 需要配置源码中的js rsa.js使用。
 * 	如果还有问题请直接邮箱我 befairytale@163.com
 */
public class RSAUtils {

	// 加密算法的名称
	public static final String KEY_ALGORITHM = "RSA";

	// 公钥 和 私钥
	private static final String PUBLIC_KEY = "RSAPublicKey";
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	private static Map<String, Object> keyMap = Maps.newConcurrentMap();

	// 初始化公钥和私钥
	static {

		try {
			// 获取生成公钥的 keyPair
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
			// 初始化公钥的长度为1024，不可过长会影响效率
			keyPairGen.initialize(1024);
			// 生成对应的公钥串
			KeyPair keyPair = keyPairGen.generateKeyPair();

			// 公钥
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

			BigInteger m = publicKey.getModulus();
			System.out.println("Modulus:\n\t" + m.toString(16));
			BigInteger eBigInteger = publicKey.getPublicExponent();

			System.out.println("e:\n\t" + eBigInteger.toString(16));
			System.out.println("公钥：");
			System.out.println(Hex.encodeHex(publicKey.getEncoded()));

			// 私钥
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			System.out.println("私钥：");
			System.out.println(Hex.encodeHex(privateKey.getEncoded()));

			keyMap.put(PUBLIC_KEY, publicKey);
			keyMap.put(PRIVATE_KEY, privateKey);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取指数。 这里的Mod 值是bigint 的需要转换成String的，前端需要Mod 值进行Key的生成和加密。
	 * 
	 * @return
	 */
	public String getModulus() {
		RSAPublicKey publicKey = (RSAPublicKey) keyMap.get(PUBLIC_KEY);

		return publicKey.getModulus().toString(16);
	}

	/**
	 * 获取指数 这里生成的指数值也是bigint 的类型，需要转换成String，前段需要e的生成key进行加密。
	 * 
	 * @return
	 */
	public String getExponent() {
		RSAPublicKey publicKey = (RSAPublicKey) keyMap.get(PUBLIC_KEY);

		return publicKey.getPublicExponent().toString(16);
	}

	/**
	 * 解密<br>
	 * 用私钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data) throws Exception {

		// 对数据解密
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM, new BouncyCastleProvider());
		cipher.init(Cipher.DECRYPT_MODE, (Key) keyMap.get(PRIVATE_KEY));
		int blockSize = cipher.getBlockSize();// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
		// 加密块大小为127
		// byte,加密后为128个byte;因此共有2个加密块，第一个127
		// byte第二个为1个byte
		ByteArrayOutputStream bo = new ByteArrayOutputStream(64);
		int j = 0;
		while (data.length - j * blockSize > 0) {
			bo.write(cipher.doFinal(data, j * blockSize, blockSize));
			j++;
		}
		return bo.toByteArray();
	}

	/**
	 * 加密
	 * 
	 * @param key
	 *            加密的密钥 (也可以是公钥)
	 * @param data
	 *            待加密的明文数据
	 * @return 加密后的数据
	 */
	@SuppressWarnings("unused")
	private static byte[] encrypt(Key pk, byte[] data) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA",new BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, pk);
			int blockSize = cipher.getBlockSize();// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
			// 加密块大小为127
			// byte,加密后为128个byte;因此共有2个加密块，第一个127
			// byte第二个为1个byte
			int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
			int leavedSize = data.length % blockSize;
			int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
			byte[] raw = new byte[outputSize * blocksSize];
			int i = 0;
			while (data.length - i * blockSize > 0) {
				if (data.length - i * blockSize > blockSize)
					cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
				else
					cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
				// 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到
				// ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了
				// OutputSize所以只好用dofinal方法。
				i++;
			}
			return raw;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}
