package com.cqmike.wechat;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
 


import javax.servlet.http.HttpServletRequest;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
 /**
  * ��Ϣ��������
  * Created by xdp on 2016/1/26.
  */
 public class MessageHandlerUtil {
 
     /**
      * ����΢�ŷ���������XML��
      * @param request
      * @return map
      * @throws Exception
      */
     public static Map<String,String> parseXml(HttpServletRequest request) throws Exception {
         // ����������洢��HashMap��
         Map<String,String> map = new HashMap();
         // ��request��ȡ��������
         InputStream inputStream = request.getInputStream();
         System.out.println("��ȡ������");
         // ��ȡ������
         SAXReader reader = new SAXReader();
         Document document = reader.read(inputStream);
         // �õ�xml��Ԫ��
         Element root = document.getRootElement();
         // �õ���Ԫ�ص������ӽڵ�
         List<Element> elementList = root.elements();
 
         // ���������ӽڵ�
         for (Element e : elementList) {
             System.out.println(e.getName() + "|" + e.getText());
             map.put(e.getName(), e.getText());
         }
 
         // �ͷ���Դ
         inputStream.close();
         inputStream = null;
         return map;
     }
 
     // ������Ϣ���� ���췵����Ϣ
     public static String buildXml(Map<String,String> map) {
         String result;
         String msgType = map.get("MsgType").toString();
         System.out.println("MsgType:" + msgType);
         if(msgType.toUpperCase().equals("TEXT")){
             result = buildTextMessage(map, "�����Ƽ�-�����ҽ�");
         }else{
             String fromUserName = map.get("FromUserName");
             // ������΢�ź�
             String toUserName = map.get("ToUserName");
             result = String
                     .format(
                             "<xml>" +
                                     "<ToUserName><![CDATA[%s]]></ToUserName>" +
                                     "<FromUserName><![CDATA[%s]]></FromUserName>" +
                                     "<CreateTime>%s</CreateTime>" +
                                     "<MsgType><![CDATA[text]]></MsgType>" +
                                     "<Content><![CDATA[%s]]></Content>" +
                                     "</xml>",
                             fromUserName, toUserName, getUtcTime(),
                             "��ظ����¹ؼ��ʣ�\n�ı�\nͼƬ\n����\n��Ƶ\n����\nͼ��");
         }
 
         return result;
     }
 
     private static String getUtcTime() {
         Date dt = new Date();// �������Ҫ��ʽ,��ֱ����dt,dt���ǵ�ǰϵͳʱ��
         DateFormat df = new SimpleDateFormat("yyyyMMddhhmm");// ������ʾ��ʽ
         String nowTime = df.format(dt);
         long dd = (long) 0;
         try {
             dd = df.parse(nowTime).getTime();
         } catch (Exception e) {
 
         }
         return String.valueOf(dd);
     }
     
     public enum MessageType {
		 TEXT,//�ı���Ϣ
		 IMAGE,//ͼƬ��Ϣ
		 VOICE,//������Ϣ
		 VIDEO,//��Ƶ��Ϣ
		 SHORTVIDEO,//С��Ƶ��Ϣ
		 LOCATION,//����λ����Ϣ
		 LINK,//������Ϣ
		 EVENT//�¼���Ϣ
	}
	
	/**
	 * ������Ϣ���͹��췵����Ϣ
	 * @param map ��װ�˽��������Map
	 * @return responseMessage(��Ӧ��Ϣ)
	 */
	 public static String buildResponseMessage(Map map) {
		 //��Ӧ��Ϣ
		 String responseMessage = "";
	 	//�õ���Ϣ����
		 String msgType = map.get("MsgType").toString();
		 System.out.println("MsgType:" + msgType);
		 //��Ϣ����
		 MessageType messageEnumType = MessageType.valueOf(MessageType.class, msgType.toUpperCase());
		 switch (messageEnumType) {
		 	case TEXT:
		 		//�����ı���Ϣ
		 		responseMessage = handleTextMessage(map);
		 		break;
		 	default:
		 		break;
		 }
		 //������Ӧ��Ϣ
		 return responseMessage;
	}
	 
	 /**
	 	* ���յ��ı���Ϣ����
	 	* @param map ��װ�˽��������Map
		* @return
		*/
	 private static String handleTextMessage(Map<String, String> map) {
		 //��Ӧ��Ϣ
		 String responseMessage="";
		 // ��Ϣ����
		 String content = map.get("MsgType");
		 
		 	if(content.equals("text")){
		 		String msgText = map.get("Content")+ "��ӭ\n";
		 		
		 		//String s1=null,s2=null;
		 		//try {
		 		//	s1 = java.net.URLEncoder.encode(msgText, "UTF-8");
		 		//	System.out.println(s1);
		 		//	s2= java.net.URLEncoder.encode(s1,"UTF-8");
		 		//	System.out.println(s2);   
		 		//} catch (UnsupportedEncodingException e) {
			 	//	e.printStackTrace();
			 	//}
		 		
		 		//if(msgText.length()<500){
		 		//     msgText = msgText + "\n"+  "<a href=\"http://tsn.baidu.com/text2audio?lan=zh&vol=9&tok=24.002396bd370b62ad1144b520c9ad70c7.2592000.1486037637.282335-9158560&ctp=1&cuid=1&tex=" + s2 + "\" target=\"_blank\">�����������</a>";
		 		//}
		 		
		 		responseMessage = buildTextMessage(map, msgText);
		 	}
/*		 	case "ͼƬ":
		 		//ͨ���زĹ���ӿ��ϴ�ͼƬʱ�õ���media_id
		 		String imgMediaId = "dSQCiEHYB-pgi7ib5KpeoFlqpg09J31H28rex6xKgwWrln3HY0BTsoxnRV-xC_SQ";
		 		responseMessage = buildImageMessage(map, imgMediaId);
		 		break;
*/		 	if(content.equals("voice")){ 
		 		//ͨ���زĹ���ӿ��ϴ������ļ�ʱ�õ���media_id
		 		String voiceMediaId = "h3ul0TnwaRPut6Tl1Xlf0kk_9aUqtQvfM5Oq21unoWqJrwks505pkMGMbHnCHBBZ";
//		 		responseMessage = buildVoiceMessage(map,voiceMediaId);
		 		}
/*		 	case "ͼ��":
		 		responseMessage = buildNewsMessage(map);
		 		break;
		 	case "����":
		 		Music music = new Music();
		 		music.title = "����ӱ����־�� - ��������";
		 		music.description = "���Ӿ硶��ɽս�͡�����";
		 		music.musicUrl = "http://gacl.ngrok.natapp.cn/music/music.mp3";
		 		music.hqMusicUrl = "http://gacl.ngrok.natapp.cn/music/music.mp3";
		 		responseMessage = buildMusicMessage(map, music);
		 		break;
		 	case "��Ƶ":
		 		Video video = new Video();
		 		video.mediaId = "GqmIGpLu41rtwaY7WCVtJAL3ZbslzKiuLEXfWIKYDnHXGObH1CBH71xtgrGwyCa3";
		 		video.title = "Сƻ��";
		 		video.description = "Сƻ����Ц��Ƶ";
		 		responseMessage = buildVideoMessage(map, video);
		 		break;
		 	
		 	default:
		 		responseMessage = buildWelcomeTextMessage(map);
		 		break;
		 	*/
		 

		 //������Ӧ��Ϣ
		 return responseMessage;
	 }
	 
	 
	 /**
	 * �����ı���Ϣ
	 * @param map ��װ�˽��������Map
	 * @param content �ı���Ϣ����
	 * @return �ı���ϢXML�ַ���
	 */
	 private static String buildTextMessage(Map<String, String> map, String content) {
		 //���ͷ��ʺ�
		 String fromUserName = map.get("FromUserName");
		 // ������΢�ź�
		 String toUserName = map.get("ToUserName");
		 /**
		  * �ı���ϢXML���ݸ�ʽ
		  * <xml>
	 		<ToUserName><![CDATA[toUser]]></ToUserName>
			<FromUserName><![CDATA[fromUser]]></FromUserName>
			<CreateTime>1348831860</CreateTime>
			<MsgType><![CDATA[text]]></MsgType>
			<Content><![CDATA[this is a test]]></Content>
			<MsgId>1234567890123456</MsgId>
			</xml>
		  */
		 return String.format(
				 "<xml>" +
						 "<ToUserName><![CDATA[%s]]></ToUserName>" +
						 "<FromUserName><![CDATA[%s]]></FromUserName>" +
						 "<CreateTime>%s</CreateTime>" +
						 "<MsgType><![CDATA[text]]></MsgType>" +
	                     "<Content><![CDATA[%s]]></Content>" +
	              "</xml>",
	             fromUserName, toUserName, getMessageCreateTime(), content);
	 }
	 
	 private static String getMessageCreateTime(){
		long time = System.currentTimeMillis()/1000;
		//System.out.println(time);
		return String.format("%d", time);
	 }
	 
	
}
