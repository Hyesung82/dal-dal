package com.example.dalyeodalyeok.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.dalyeodalyeok.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LMSFragment extends Fragment {

    private String htmlPageUrl = "http://lms.pknu.ac.kr/ilos/main/main_form.acl";
    private String loginPageUrl = "http://lms.pknu.ac.kr/ilos/main/member/login_form.acl";
    private TextView textviewHtmlDocument;
    private String htmlContentInStringFormat="";

    int cnt=0;

    String user = "";
    String password = "";



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup contatiner, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_lms, contatiner, false);

        textviewHtmlDocument= (TextView)root.findViewById(R.id.tvCrawling);
        textviewHtmlDocument.setMovementMethod(new ScrollingMovementMethod());

        Button htmlTitleButton = (Button)root.findViewById(R.id.btnCrawling);
        htmlTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println((cnt + 1) + "번째 파싱");
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                jsoupAsyncTask.execute();
                cnt++;
            }
        });

        return root;
    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect(htmlPageUrl).get();

                Elements titles = doc.select("title");
                System.out.println("-----------------------------------------");
                for(Element e: titles) {
                    System.out.println("title: " + e.text());
                    htmlContentInStringFormat += e.text().trim() + "\n";
                }

                titles = doc.select("body div div div div h2");
                System.out.println("-------------------------------------------");
                for(Element e: titles) {
                    System.out.println("title: " + e.text());
                    htmlContentInStringFormat += e.text().trim() + "\n";
                }

                Connection.Response mainPageResponse = Jsoup.connect(htmlPageUrl)
                        .timeout(5000)
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                        .header("Accept-Encoding", "gzip, deflate")
                        .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                        .header("Cache-Control", "no-cache")
                        .header("Connection", "keep-alive")
                        .header("Cookie", "_language_=ko; JSESSIONID=HzZinSRhObwWZ0UrRKZcwLmv1V2Zb3lVZuZ0vLfZ4TbSKEpayJZpI0yrjG768PyL.LMSWEB02_servlet_engine2; _ga=GA1.3.659021635.1546482517; ncook_20181226094521=done; ncook_20181121082127=done")
                        .header("Host", "lms.pknu.ac.kr")
                        .header("Pragma", "no-cache")
                        .header("Upgrade-Insecure-Requests", "1")
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36")
                        .method(Connection.Method.GET)
                        .execute();

//                Connection.Response loginPageResponse = Jsoup.connect(loginPageUrl)
//                        .timeout(5000)
//                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
//                        .header("Accept-Encoding", "gzip, deflate, br")
//                        .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
//                        .header("Cache-Control", "no-cache")
//                        .header("Connection", "keep-alive")
//                        .header("Content-Length", "69")
//                        .header("Content-Type", "application/x-www-form-urlencoded")
//                        .header("Cookie", "_language_=ko; JSESSIONID=hrLqBMKmKpFA8Aa8YmO1xoENo69PdkJyR25rE7m6GXTvshZdyP3yXS9xF09IFq8S.LMSWEB02_servlet_engine1; _ga=GA1.3.659021635.1546482517; ncook_20181226094521=done; ncook_20181121082127=done")
//                        .header("Host", "lms.pknu.ac.kr")
//                        .header("Origin", "http://lms.pknu.ac.kr")
//                        .header("Pragma", "no-cache")
//                        .header("Referer", "http://lms.pknu.ac.kr/ilos/main/member/login_form.acl")
//                        .header("Sec-Fetch-Mode", "navigate")
//                        .header("Sec-Fetch-Site", "cross-site")
//                        .header("Sec-Fetch-User", "?1")
//                        .header("Upgrade-Insecure-Requests", "1")
//                        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36")
//                        .method(Connection.Method.GET)
//                        .execute();

                // 로그인 페이지에서 얻은 쿠키
                Map<String, String> loginTryCookie = mainPageResponse.cookies();
                //Map<String, String> loginTryCookie = loginPageResponse.cookies();

                // 로그인 페이지에서 로그인에 함께 전송하는 토큰 얻어내기
                Document mainPageDocument = mainPageResponse.parse();
//                Document loginPageDocument = loginPageResponse.parse();

//                String reCaptcha = loginPageDocument.select("input.reCaptcha").val();
//                String returnURL = loginPageDocument.select("input.returnURL").val();
//                String challenge = loginPageDocument.select("input.response").val();
                String reCaptcha = mainPageDocument.select("input.reCaptcha").val();
                String returnURL = mainPageDocument.select("input.returnURL").val();
                String challenge = mainPageDocument.select("input.response").val();

                // Window, Chrome의 User Agent
                String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36";

                // 전송할 폼 데이터
                Map<String, String> data = new HashMap<>();
                data.put("loginId", ""); // 아이디
                data.put("password", ""); // 비밀번호
                data.put("rememberLoginId", "1");
                data.put("reCaptcha", reCaptcha);
                data.put("returnURL", returnURL);
                data.put("challenge", challenge);


                Connection.Response response = Jsoup.connect(htmlPageUrl)
                        .userAgent(userAgent)
                        .timeout(5000)
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                        .header("Accept-Encoding", "gzip, deflate")
                        .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                        .header("Cache-Control", "no-cache")
                        .header("Connection", "keep-alive")
                        .header("Cookie", "_language_=ko; JSESSIONID=HzZinSRhObwWZ0UrRKZcwLmv1V2Zb3lVZuZ0vLfZ4TbSKEpayJZpI0yrjG768PyL.LMSWEB02_servlet_engine2; _ga=GA1.3.659021635.1546482517; ncook_20181226094521=done; ncook_20181121082127=done")
                        .header("Host", "lms.pknu.ac.kr")
                        .header("Pragma", "no-cache")
                        .header("Upgrade-Insecure-Requests", "1")
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36")
                        .cookies(loginTryCookie)
                        .data(data)
                        .method(Connection.Method.POST)
                        .execute();
                // 로그인(POST)
//                Connection.Response response = Jsoup.connect(loginPageUrl)
//                        .userAgent(userAgent)
//                        .timeout(5000)
//                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
//                        .header("Accept-Encoding", "gzip, deflate, br")
//                        .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
//                        .header("Cache-Control", "no-cache")
//                        .header("Connection", "keep-alive")
//                        .header("Content-Length", "69")
//                        .header("Content-Type", "application/x-www-form-urlencoded")
//                        .header("Cookie", "_language_=ko; JSESSIONID=hrLqBMKmKpFA8Aa8YmO1xoENo69PdkJyR25rE7m6GXTvshZdyP3yXS9xF09IFq8S.LMSWEB02_servlet_engine1; _ga=GA1.3.659021635.1546482517; ncook_20181226094521=done; ncook_20181121082127=done")
//                        .header("Host", "lms.pknu.ac.kr")
//                        .header("Origin", "http://lms.pknu.ac.kr")
//                        .header("Pragma", "no-cache")
//                        .header("Referer", "http://lms.pknu.ac.kr/ilos/main/member/login_form.acl")
//                        .header("Sec-Fetch-Mode", "navigate")
//                        .header("Sec-Fetch-Site", "cross-site")
//                        .header("Sec-Fetch-User", "?1")
//                        .header("Upgrade-Insecure-Requests", "1")
//                        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36")
//                        .cookies(loginTryCookie)
//                        .data(data)
//                        .method(Connection.Method.POST)
//                        .execute();

                // 로그인 성공 후 얻은 쿠키
                // 쿠키 중 TSESSION이라는 값을 확인할 수 있다
                Map<String, String> loginCookie = response.cookies();

                // LMS 마이페이지
                Document myPageDocument = Jsoup.connect("http://lms.pknu.ac.kr/ilos/mp/myinfo_form.acl")
                        .userAgent(userAgent)
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                        .header("Accept-Encoding", "gzip, deflate")
                        .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                        .header("Cache-Control", "no-cache")
                        .header("Connection", "keep-alive")
                        .header("Cookie", "_language_=ko; JSESSIONID=HzZinSRhObwWZ0UrRKZcwLmv1V2Zb3lVZuZ0vLfZ4TbSKEpayJZpI0yrjG768PyL.LMSWEB02_servlet_engine2; _ga=GA1.3.659021635.1546482517; ncook_20181226094521=done; ncook_20181121082127=done")
                        .header("Host", "lms.pknu.ac.kr")
                        .header("Pragma", "no-cache")
                        .header("Upgrade-Insecure-Requests", "1")
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36")
//                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
//                        .header("Accept-Encoding", "gzip, deflate, br")
//                        .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
//                        .header("Cache-Control", "no-cache")
//                        .header("Connection", "keep-alive")
//                        .header("Content-Length", "69")
//                        .header("Content-Type", "application/x-www-form-urlencoded")
//                        .header("Cookie", "_language_=ko; JSESSIONID=hrLqBMKmKpFA8Aa8YmO1xoENo69PdkJyR25rE7m6GXTvshZdyP3yXS9xF09IFq8S.LMSWEB02_servlet_engine1; _ga=GA1.3.659021635.1546482517; ncook_20181226094521=done; ncook_20181121082127=done")
//                        .header("Host", "lms.pknu.ac.kr")
//                        .header("Origin", "http://lms.pknu.ac.kr")
//                        .header("Pragma", "no-cache")
//                        .header("Referer", "http://lms.pknu.ac.kr/ilos/main/member/login_form.acl")
//                        .header("Sec-Fetch-Mode", "navigate")
//                        .header("Sec-Fetch-Site", "cross-site")
//                        .header("Sec-Fetch-User", "?1")
//                        .header("Upgrade-Insecure-Requests", "1")
//                        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36")
                        .cookies(loginCookie)
                        .get();

                Elements myPageTd = myPageDocument.select("body div div div div div form div table tbody tr td");

                for (Element td : myPageTd) {
                    String myName = td.text();
                    // 태그의 속성도 추출 가능
                    // String url = td.attr("abs:value");

                    System.out.println(myName);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            textviewHtmlDocument.setText(htmlContentInStringFormat);
        }
    }
}
