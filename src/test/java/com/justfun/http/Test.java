package com.justfun.http;

public class Test {

	public static void main(String[] args) {
		
		String xml = "<FroadMallApiRequest>" +
  "<body>" +
    "<starttime>20160812</starttime>" +
	"<endtime>20161212</endtime>" +
	"<orderstat>2</orderstat>" +
	"<classtype>1</classtype>" +
	"</body>" +
  "<system>" +
    "<requestTime>2016-08-15 15:48:22</requestTime>" +
    "<signMsg>F5D72715AB130B1464AA177D76B46741</signMsg>" +
    "<signType>1</signType>" +
    "<merchantPwd>14E851BD0ADBC301EA6C0AF7C0AE8D20</merchantPwd>" +
    "<merchantNo>o2obill</merchantNo>" +
    "<busiCode>10006</busiCode>" +
    "<version>1.0</version>" +
  "</system>" +
"</FroadMallApiRequest>";
		
			System.out.println(MultiThreadHttpClient.postMethod("https://grayvmall.ubank365.com/api/vm/froadmallapi.do", xml));
	}
}
