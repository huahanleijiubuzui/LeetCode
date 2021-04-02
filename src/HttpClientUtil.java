import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by huahan on 2017/6/7.
 */

/**
 * HttpClient提供了几个通过HTTP connection有效地流出内容的类。这些类的实例与POST和PUT这样的entity enclosing requests有关，为了把这些实体内容放进即将发出的请求中
 * <p>
 * HttpClient提供的这几个类大多数都是数据容器，像字符串，字节数组，输入流和文件对应的：StringEntity，ByteArrayEntity，InputStreamEntity和FileEntity
 */
public class HttpClientUtil {

    //json格式提交
    public static final int CONTENT_TYPE_JSON = 1;
    //表单提交
    public static final int CONTENT_TYPE_FORM = 2;
    //文件上传
    public static final int CONTENT_TYPE_FILE = 3;

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    //网络连接超时毫秒
    private static final int CONNECT_TIMEOUT_ = 5000;
    //Socket连接数据读取超时毫秒
    private static final int SO_TIMEOUT_ = 5000;
    //边界
    private static final String BOUNDARY = "========7d4a6d158c9";

    //请求头配置
    public static final RequestConfig requestConfig;

    static {
        requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(CONNECT_TIMEOUT_)
                .setConnectTimeout(CONNECT_TIMEOUT_)
                .setSocketTimeout(SO_TIMEOUT_)
                .build();
    }

    public static String get(String url, Map<String, String> params) {
        HttpGet httpGet = buildHttpGet(url, params);
        return execute(httpGet);
    }

    public static String getWithHeader(String url, Map<String, String> headers, Map<String, String> params) {
        HttpGet httpGet = buildHttpGet(url, params);
        for (Map.Entry<String, String> e : headers.entrySet()) {
            httpGet.addHeader(e.getKey(), e.getValue());
        }
        return execute(httpGet);
    }


    /**
     * 构建HttpGet
     *
     * @param url
     * @param params
     * @return
     */
    private static HttpGet buildHttpGet(String url, Map<String, String> params) {

        String requestUrl = url;
        try {

            URI uri = new URI(url);
            URIBuilder uriBuilder = new URIBuilder().setScheme(uri.getScheme())
                    .setHost(uri.getHost())
                    .setPath(uri.getPath())
                    .setPort(uri.getPort())
                    .setCharset(StandardCharsets.UTF_8);
            if (null != params && !params.isEmpty()) {
                for (String key : params.keySet()) {
                    uriBuilder.setParameter(key, params.get(key));
                }
            }
            requestUrl = uriBuilder.build().toString();

        } catch (URISyntaxException e) {
            System.out.println(e);
            e.printStackTrace();
        }

        HttpGet httpGet = new HttpGet(requestUrl);
        httpGet.setConfig(requestConfig);

        return httpGet;
    }

    /**
     * 普通POST提交
     *
     * @param url
     * @param params
     * @return
     */
    public static String post(String url, Map<String, Object> params) {
        return post(url, null, params);
    }

    /**
     * 普通 带header POST提交
     *
     * @param url
     * @param header
     * @param params
     * @return
     */
    public static String post(String url, Map<String, String> header, Map<String, Object> params) {
        HttpPost httpPost = buildHttpPost(url, header, params, CONTENT_TYPE_FORM);
        return execute(httpPost);
    }

    /**
     * 提交JSON格式数据
     *
     * @param url
     * @param jsonString
     * @return
     */
    public static String postJSON(String url, Map<String, String> header, String jsonString) {
        if (null == header || header.isEmpty()) {
            header = new HashMap<>();
        }
        header.put("Content-Type", ContentType.APPLICATION_JSON.getMimeType());
        HttpPost httpPost = buildHttpPostJSON(url, header, jsonString);
        return execute(httpPost);
    }

    /**
     * 提交JSON格式数据
     *
     * @param url
     * @param params
     * @return
     */
    public static String postJSON(String url, Map<String, String> header, Map<String, Object> params) {
        if (null == header || header.isEmpty()) {
            header = new HashMap<>();
        }
        header.put("Content-Type", ContentType.APPLICATION_JSON.getMimeType());
        HttpPost httpPost = buildHttpPost(url, header, params, CONTENT_TYPE_JSON);
        return execute(httpPost);
    }

    /**
     * @param url
     * @param header
     * @param params
     * @param paramType
     * @return
     */
    private static HttpPost buildHttpPost(String url, Map<String, String> header, Map<String, Object> params, int paramType) {

        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);

        /*
        设置Header
         */
        setHeader(httpPost, header);

        /*
        设置参数信息
         */
        switch (paramType) {
            //JSON
            case CONTENT_TYPE_JSON:
                setJSONEntity(httpPost, params);
                break;
            //FORM
            case CONTENT_TYPE_FORM:
                setFormEntity(httpPost, params);
                break;
            //MULTIPART_FORM

        }

        return httpPost;
    }


    /**
     * 构建POST JSON HttpPost
     *
     * @param url
     * @param header
     * @param jsonString
     * @return
     */
    private static HttpPost buildHttpPostJSON(String url, Map<String, String> header, String jsonString) {

        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        //设置header
        setHeader(httpPost, header);
        //设置实体
        setJSONEntity(httpPost, jsonString);

        return httpPost;
    }

    /**
     * 设置 Header
     *
     * @param httpPost
     * @param header
     */
    private static void setHeader(HttpPost httpPost, Map<String, String> header) {
        /*
        设置头信息
         */
        if (null != header && !header.isEmpty()) {
            Set<String> headerKey = header.keySet();
            Iterator<String> iterator = headerKey.iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                httpPost.setHeader(key, header.get(key).toString());
            }
        }
    }

    /*
    POST提交内容提为文本内容
    StringEntity
    */
    private static void setJSONEntity(HttpPost httpPost, Map<String, Object> params) {
        StringEntity stringEntity = new StringEntity(JSONObject.toJSONString(params), StandardCharsets.UTF_8);
        stringEntity.setContentEncoding(StandardCharsets.UTF_8.name());
        stringEntity.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        httpPost.setEntity(stringEntity);
    }

    /*
    POST提交内容提为文本内容
    StringEntity
    */
    private static void setJSONEntity(HttpPost httpPost, String jsonString) {
        StringEntity stringEntity = new StringEntity(jsonString, StandardCharsets.UTF_8);
        stringEntity.setContentEncoding(StandardCharsets.UTF_8.name());
        stringEntity.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        httpPost.setEntity(stringEntity);
    }

    /*
    正常POST请求表单提交
    UrlEncodedFormEntity
     */
    private static void setFormEntity(HttpPost httpPost, Map<String, Object> params) {
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        Set<String> keySet = params.keySet();
        Iterator<String> iterator = keySet.iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            Object value = params.get(key);
            if (null != value) {
                formParams.add(new BasicNameValuePair(key, value.toString()));
            }
        }
        UrlEncodedFormEntity uefEntity = null;
        try {
            uefEntity = new UrlEncodedFormEntity(formParams, StandardCharsets.UTF_8.name());
            httpPost.setEntity(uefEntity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行请求 返回结果
     *
     * @param request
     * @return
     */
    private static String execute(HttpUriRequest request) {

        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();

        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        CloseableHttpResponse response = null;
        try {

            response = httpClient.execute(request);
            // logger.info("http result={}", response);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

                inputStream = response.getEntity().getContent();

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8.name()));
                String line = null;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                return stringBuilder.toString();
            }
        } catch (IOException e) {
            logger.error("", e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    logger.error("", e);
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("", e);
                }
            }
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error("", e);
                }
            }
        }
        return null;
    }

    // 31umE658
    public static void main(String[] args) {
       /* String url = "http://192.168.253.122:9135/acms/user/queryUsersByEmails";
        String result = postJSON(url, null, "{\"emails\":\"youxiang@meicai.cn \",\"pageSize\":5,\"page\":1}");
        System.out.println(result);*/
        List L1 = new ArrayList();
        List<String> list = Collections.synchronizedList(L1);
        for(int i=0; i< 10; i++) {
           new Thread(()->{
               checkEmployCode(list);
           }).start();
       }
    }

    public static void checkEmployCode(List list) {
        while (true) {
            String code = getEmployCode(31);
            String url = "https://www.axnjf.com/financing/enterprise/checkEmplNoIsVaild.do";
            Map<String,Object> map = new HashMap<>();
            map.put("employeeNo",code);
            String json = HttpClientUtil.post(url, map);
            JSONObject jsonObject = JSONObject.parseObject(json);
            String flag = jsonObject.getString("flag");
            if("false".equalsIgnoreCase(flag)) {
                list.add(code);
                System.out.println("~~~~~~~~~~~~~~~~恭喜获得验证码~~~~~~~~~~~~~~~~："+code);
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 获取推荐码 省代码2+2位小写字母+4位数字+大写字母组合
     * @return
     */
    public static String getEmployCode(int areaCode) {
        return areaCode+getRandomChar(2,1)+getRandomChar(4,2);
    }

    public static final String[] lowArray = new String[]{"a","b",};

    /**
     * 大写字母：65~90
     * 小写字母：97~122
     *
     * @param length
     * @param type  1-小写字母 2-大写字母
     * @return
     */
    public static String getRandomChar(int length, int type) {
        String str = "";
        Random random = new Random();
        for(int i=0;i<length;i++){
            if(type == 1) {
                str= str+(char) (random.nextInt(26)+97); //生成随机大写字母
            } else {
                String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
                //输出字母还是数字
                if( "char".equalsIgnoreCase(charOrNum) ) {
                    //输出是大写字母还是小写字母
                    str= str+(char) (Math.random ()*26+65); //生成随机小写字母
                } else if( "num".equalsIgnoreCase(charOrNum) ) {
                    str += String.valueOf(random.nextInt(10));
                }
            }
        }
        return str;
    }


    String s = "{\n" +
            "            \"c_areacode\": \"110000\",\n" +
            "            \"c_areaid\": \"0002\",\n" +
            "            \"c_areaname\": \"北京\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"100000\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"120000\",\n" +
            "            \"c_areaid\": \"0021\",\n" +
            "            \"c_areaname\": \"天津\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"300000\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"130000\",\n" +
            "            \"c_areaid\": \"0040\",\n" +
            "            \"c_areaname\": \"河北\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"050000\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"140000\",\n" +
            "            \"c_areaid\": \"0235\",\n" +
            "            \"c_areaname\": \"山西\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"030000\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"150000\",\n" +
            "            \"c_areaid\": \"0377\",\n" +
            "            \"c_areaname\": \"内蒙古\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"210000\",\n" +
            "            \"c_areaid\": \"0500\",\n" +
            "            \"c_areaname\": \"辽宁\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"110000\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"220000\",\n" +
            "            \"c_areaid\": \"0629\",\n" +
            "            \"c_areaname\": \"吉林\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"130000\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"230000\",\n" +
            "            \"c_areaid\": \"0707\",\n" +
            "            \"c_areaname\": \"黑龙江\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"310000\",\n" +
            "            \"c_areaid\": \"0861\",\n" +
            "            \"c_areaname\": \"上海\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"200000\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"320000\",\n" +
            "            \"c_areaid\": \"0881\",\n" +
            "            \"c_areaname\": \"江苏\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"210000\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"330000\",\n" +
            "            \"c_areaid\": \"1011\",\n" +
            "            \"c_areaname\": \"浙江\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"310000\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"340000\",\n" +
            "            \"c_areaid\": \"1124\",\n" +
            "            \"c_areaname\": \"安徽\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"230000\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"350000\",\n" +
            "            \"c_areaid\": \"1262\",\n" +
            "            \"c_areaname\": \"福建\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"350000\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"360000\",\n" +
            "            \"c_areaid\": \"1366\",\n" +
            "            \"c_areaname\": \"江西\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"330000\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"370000\",\n" +
            "            \"c_areaid\": \"1489\",\n" +
            "            \"c_areaname\": \"山东\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"250000\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"410000\",\n" +
            "            \"c_areaid\": \"1664\",\n" +
            "            \"c_areaname\": \"河南\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"450000\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"420000\",\n" +
            "            \"c_areaid\": \"1859\",\n" +
            "            \"c_areaname\": \"湖北\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"430000\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"430000\",\n" +
            "            \"c_areaid\": \"1989\",\n" +
            "            \"c_areaname\": \"湖南\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"410000\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"440000\",\n" +
            "            \"c_areaid\": \"2139\",\n" +
            "            \"c_areaname\": \"广东\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"510000\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"450000\",\n" +
            "            \"c_areaid\": \"2301\",\n" +
            "            \"c_areaname\": \"广西\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"530000\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"460000\",\n" +
            "            \"c_areaid\": \"2439\",\n" +
            "            \"c_areaname\": \"海南\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"570000\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"500000\",\n" +
            "            \"c_areaid\": \"2469\",\n" +
            "            \"c_areaname\": \"重庆\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"400000\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"510000\",\n" +
            "            \"c_areaid\": \"2510\",\n" +
            "            \"c_areaname\": \"四川\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"610000\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"520000\",\n" +
            "            \"c_areaid\": \"2731\",\n" +
            "            \"c_areaname\": \"贵州\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"550000\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"530000\",\n" +
            "            \"c_areaid\": \"2832\",\n" +
            "            \"c_areaname\": \"云南\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"650000\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"540000\",\n" +
            "            \"c_areaid\": \"2986\",\n" +
            "            \"c_areaname\": \"西藏\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"850000\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"610000\",\n" +
            "            \"c_areaid\": \"3068\",\n" +
            "            \"c_areaname\": \"陕西\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"710000\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"620000\",\n" +
            "            \"c_areaid\": \"3196\",\n" +
            "            \"c_areaname\": \"甘肃\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"730000\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"630000\",\n" +
            "            \"c_areaid\": \"3309\",\n" +
            "            \"c_areaname\": \"青海\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"810000\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"640000\",\n" +
            "            \"c_areaid\": \"3362\",\n" +
            "            \"c_areaname\": \"宁夏\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"750000\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"650000\",\n" +
            "            \"c_areaid\": \"3395\",\n" +
            "            \"c_areaname\": \"新疆\",\n" +
            "            \"c_parentid\": \"0001\",\n" +
            "            \"c_zipcode\": \"830000\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"710000\",\n" +
            "            \"c_areaid\": \"3511\",\n" +
            "            \"c_areaname\": \"台湾\",\n" +
            "            \"c_parentid\": \"0001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"810000\",\n" +
            "            \"c_areaid\": \"3797\",\n" +
            "            \"c_areaname\": \"香港\",\n" +
            "            \"c_parentid\": \"0001\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"c_areacode\": \"820000\",\n" +
            "            \"c_areaid\": \"3819\",\n" +
            "            \"c_areaname\": \"澳门\",\n" +
            "            \"c_parentid\": \"0001\"\n" +
            "        }";

}
