//package com.godcoder.myhome.api;
//
//import java.net.http.HttpClient;
//
//public class GetApiRequest {
//
//    public void get(String requestURL) {
//        try {
//            HttpClient client = HttpClient.newBuilder().build(); // HttpClient 생성
//            HttpGet getRequest = new HttpGet(requestURL); //GET 메소드 URL 생성
//            getRequest.addHeader("x-api-key", RestTestCommon.API_KEY); //KEY 입력
//
//            HttpResponse response = client.execute(getRequest);
//
//            //Response 출력
//            if (response.getStatusLine().getStatusCode() == 200) {
//                ResponseHandler<String> handler = new BasicResponseHandler();
//                String body = handler.handleResponse(response);
//                System.out.println(body);
//            } else {
//                System.out.println("response is error : " + response.getStatusLine().getStatusCode());
//            }
//
//        } catch (Exception e){
//            System.err.println(e.toString());
//        }
//    }
//}
