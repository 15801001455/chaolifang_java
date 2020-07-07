package com.chaolifang;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

//@SpringBootTest
public class CryTests {

    @Test
    public void testBase64(){
        String str = "hello";
        String base64Encoded = Base64.encodeToString(str.getBytes());
        String str2 = Base64.decodeToString(base64Encoded);
        Assert.assertEquals(str,str2);
    }

    /**
     * 这一段描述是从张开涛老师的博客里面获取到的 https://www.iteye.com/blog/jinnianshilongnian-2021439
     * 散列算法一般用于生成数据的摘要信息，是一种不可逆的算法，一般适合存储密码之类的数据，常见的散列算法如MD5、SHA等。一般进行散列时最好提供一个salt（盐），比如加密密码“admin”，产生的散列值是“21232f297a57a5a743894a0e4a801fc3”，可以到一些md5解密网站很容易的通过散列值得到密码“admin”，即如果直接对密码进行散列相对来说破解更容易，此时我们可以加一些只有系统知道的干扰数据，如用户名和ID（即盐）；这样散列的对象是“密码+用户名+ID”，这样生成的散列值相对来说更难破解。
     */
    @Test
    public void testSaltUseMd5(){
        String str = "hello";
        String salt = "123";
        String md5 = new Md5Hash(str,salt).toString();//86fcb4c0551ea48ede7df5ed9626eee7
        System.out.println(md5);
        //update jyc 这个md5值虽然加盐了 但是我通过这个网站https://www.cmd5.com/ 还是能反解出来原值是hello123只不过加了盐 不知道hello123哪部分是盐值哪部分是原始密码,但是对于程序员能看到数据库的还是能推算出密码!!!
        System.out.println(Base64.encodeToString(md5.getBytes())); //base64编码解码也能通过程序或者网站http://tool.chinaz.com/Tools/Base64.aspx进行解码或者编码
        System.out.println(Hex.encodeToString(md5.getBytes()));
    }

    @Test
    public void testSaltUseSha(){
        String str = "hello";
        String salt = "123";
        String md5 = new Sha1Hash(str,salt).toString();
        System.out.println(md5);//f0a139408f7b134c66342e3d1cf4870a293c11c3
        System.out.println(Base64.encodeToString(md5.getBytes()));//ZjBhMTM5NDA4ZjdiMTM0YzY2MzQyZTNkMWNmNDg3MGEyOTNjMTFjMw==
        System.out.println(Hex.encodeToString(md5.getBytes()));
    }
}
