package com.uestc.spider.www;
import java.io.File;  
import java.io.FileOutputStream;  
import java.io.InputStream;  
import java.net.URL;  
import java.net.URLConnection;  
import java.util.ArrayList;  
import java.util.List;  
import java.util.regex.Matcher;  
import java.util.regex.Pattern;  
  
/*** 
 * java抓取网络图片 
 * @author uestc 
 * 
 */  
public class GetImage {  
  
    // 地址  
    public String URL ;  
    // 编码  
    private static final String ECODING = "UTF-8";  
    // 获取img标签正则  
    private static final String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";  
    // 获取src路径的正则  
    private static final String IMGSRC_REG = "http:\"?(.*?)(\"|>|\\s+)";   //待修改~~
  
      
//    public static void main(String[] args) throws Exception { 
//    	String url = "http://e.chengdu.cn/html/2014-08/22/content_485002.htm";
//        GetImage cm = new GetImage();  
//        //获得html文本内容  
//        String HTML = cm.getHTML(url);  
//        //获取图片标签  
//        List<String> imgUrl = cm.getImageUrl(HTML);  
//        //获取图片src地址  
//        List<String> imgSrc = cm.getImageSrc(imgUrl);  
//        //下载图片  
//        cm.Download(imgSrc);  
//    }  
      
//    public GetImage(String url){
//    	this.URL = url;
//    }  
    /*** 
     * 获取HTML内容 
     *  
     * @param url 
     * @return 
     * @throws Exception 
     */  
    private String getHTML(String url) throws Exception {  
        URL uri = new URL(url);  
        URLConnection connection = uri.openConnection();  
        InputStream in = connection.getInputStream();  
        byte[] buf = new byte[1024];  
        int length = 0;  
        StringBuffer sb = new StringBuffer();  
        while ((length = in.read(buf, 0, buf.length)) > 0) {  
            sb.append(new String(buf, ECODING));  
        }  
        in.close();  
        return sb.toString();  
    }  
  
    /*** 
     * 获取ImageUrl地址 
     *  
     * @param HTML 
     * @return 
     */  
    private List<String> getImageUrl(String HTML) {  
        Matcher matcher = Pattern.compile(IMGURL_REG).matcher(HTML);  
        List<String> listImgUrl = new ArrayList<String>();  
        while (matcher.find()) {  
            listImgUrl.add(matcher.group());  
        }  
        return listImgUrl;  
    }  
  
    /*** 
     * 获取ImageSrc地址 
     *  
     * @param listImageUrl 
     * @return 
     */  
    private List<String> getImageSrc(List<String> listImageUrl) {  
        List<String> listImgSrc = new ArrayList<String>();  
        for (String image : listImageUrl) {  
            Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(image);  
            while (matcher.find()) {  
                listImgSrc.add(matcher.group().substring(0, matcher.group().length() - 1));  
            }  
        }  
        return listImgSrc;  
    }  
  
    /*** 
     * 下载图片 
     *  
     * @param listImgSrc 
     */  
    private void Download(List<String> listImgSrc) {  
        try {  
            for (String url : listImgSrc) {  
                String imageName = url.substring(url.lastIndexOf("/")+1, url.length());  
                URL uri = new URL(url);  
                InputStream in = uri.openStream();  
                FileOutputStream fo = new FileOutputStream(new File(".\\image",imageName));  
                byte[] buf = new byte[1024];  
                int length = 0;  
//                System.out.println("开始下载:" + url);  
                while ((length = in.read(buf, 0, buf.length)) != -1) {  
                    fo.write(buf, 0, length);  
                }  
                in.close();  
                fo.close();  
//                System.out.println(imageName + "下载完成");  
            }  
        } catch (Exception e) {  
//            System.out.println("下载失败");  
        }  
    }  
  
   /* 整合
    * 
    * */ 
    public void getImage(String html){
//    	GetImage image = new GetImage();
        //获取图片标签  
        List<String> imgUrl = getImageUrl(html);  
        //获取图片src地址  
        List<String> imgSrc = getImageSrc(imgUrl);  
        //下载图片  
        Download(imgSrc);
    }
}  