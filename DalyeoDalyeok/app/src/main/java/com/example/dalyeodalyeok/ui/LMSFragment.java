package com.example.dalyeodalyeok.ui;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.dalyeodalyeok.R;
import com.example.dalyeodalyeok.SSLConnect;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LMSFragment extends Fragment {

    private String htmlPageUrl = "http://lms.pknu.ac.kr/ilos/main/main_form.acl";
    private String loginPageUrl = "http://lms.pknu.ac.kr/ilos/main/member/login_form.acl";
    private EditText LMSID;
    private EditText LMSPASSWORD;
    private TextView textviewHtmlDocument;
    private String htmlContentInStringFormat="";

    int cnt=0;

    String user = "";
    String password = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup contatiner, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_lms, contatiner, false);

        LMSID = (EditText)root.findViewById(R.id.LMS_ID);
        LMSPASSWORD = (EditText)root.findViewById(R.id.LMS_PASSWORD);

        textviewHtmlDocument= (TextView)root.findViewById(R.id.tvCrawling);
        textviewHtmlDocument.setMovementMethod(new ScrollingMovementMethod());

        Button htmlTitleButton = (Button)root.findViewById(R.id.btnCrawling);
        htmlTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println((cnt + 1) + "번째 파싱");
                user = LMSID.getText().toString();
                password = LMSPASSWORD.getText().toString();
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

                SSLConnect ssl = new SSLConnect();
                ssl.postHttps("https://lms.pknu.ac.kr/ilos/lo/login.acl", 1000, 1000);

                Connection.Response mainPageResponse = Jsoup.connect("https://lms.pknu.ac.kr/ilos/lo/login.acl")
                        .timeout(3000)
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36")
                        .method(Connection.Method.GET)
                        .execute();

                System.out.println("메인페이지 status code : " + mainPageResponse.statusMessage());

                // 로그인 페이지에서 얻은 쿠키
                Map<String, String> loginTryCookie = mainPageResponse.cookies();
                System.out.println("로그인 시도 쿠키 : " + loginTryCookie);

                // 로그인 페이지에서 로그인에 함께 전송하는 토큰 얻어내기
                Document mainPageDocument = mainPageResponse.parse();

                String reCaptcha = mainPageDocument.select("input#reCaptcha").val();
                String returnURL = mainPageDocument.select("input#returnURL").val();
                String challenge = mainPageDocument.select("input#challenge").val();
                String response = mainPageDocument.select("input#response").val();

                // Window, Chrome의 User Agent
                String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36";

                // 전송할 폼 데이터
                Map<String, String> data = new HashMap<>();
                data.put("usr_id", user); // 아이디
                data.put("usr_pwd", password); // 비밀번호
                data.put("reCaptcha", reCaptcha);
                data.put("returnURL", returnURL);
                data.put("challenge", challenge);
                data.put("response", response);

                // 로그인(POST)
                Connection.Response res = Jsoup.connect("https://lms.pknu.ac.kr/ilos/lo/login.acl")
                        .userAgent(userAgent)
                        .cookies(loginTryCookie)
                        .data(data)
                        .method(Connection.Method.POST)
                        .execute();

                System.out.println("응답 res : " + res);
                System.out.println("res 상태 메시지 : " + res.statusMessage());
                System.out.println("res 쿠키 : " + res.cookies());

                // 로그인 성공 후 얻은 쿠키
                // 쿠키 중 JSESSION이라는 값을 확인할 수 있다
                Map<String, String> loginCookie = res.cookies();
                System.out.println("쿠키 : " + loginCookie); // 세션 실종

                // LMS 마이페이지
                Document myPageDocument = Jsoup.connect("http://lms.pknu.ac.kr/ilos/mp/myinfo_form.acl")
                        .userAgent(userAgent)
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                        .header("Accept-Encoding", "gzip, deflate")
                        .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                        .header("Cache-Control", "no-cache")
                        .header("Connection", "keep-alive")
                        .header("Host", "lms.pknu.ac.kr")
                        .header("Pragma", "no-cache")
                        .header("Referer", "http://lms.pknu.ac.kr/ilos/main/main_form.acl")
                        .header("Upgrade-Insecure-Requests", "1")
                        .cookies(loginCookie)
                        .get();
                // 메인페이지
                mainPageDocument = Jsoup.connect("http://lms.pknu.ac.kr/ilos/main/main_form.acl")
                        .userAgent(userAgent)
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                        .header("Accept-Encoding", "gzip, deflate")
                        .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                        .header("Cache-Control", "no-cache")
                        .header("Connection", "keep-alive")
                        .header("Host", "lms.pknu.ac.kr")
                        .header("Pragma", "no-cache")
                        .header("Referer", "http://lms.pknu.ac.kr/ilos/main/main_form.acl")
                        .header("Upgrade-Insecure-Requests", "1")
                        .cookies(loginCookie)
                        .get();

                Map<String, String> reportData = new HashMap<>();
                reportData.put("KJ_YEAR", "2019");
                reportData.put("KJ_TERM", "3");
                reportData.put("KJ_KEY", "A20193100305101");
                reportData.put("returnURI", "/ilos/st/course/submain_form.acl");

                // 과제 페이지
                Document myReportDocument = Jsoup.connect("http://lms.pknu.ac.kr/ilos/st/course/report_list_form.acl")
                        .userAgent(userAgent)
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                        .header("Accept-Encoding", "gzip, deflate")
                        .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                        .header("Cache-Control", "no-cache")
                        .header("Connection", "keep-alive")
                        .header("Host", "lms.pknu.ac.kr")
                        .header("Pragma", "no-cache")
                        .header("Referer", "http://lms.pknu.ac.kr/ilos/main/main_form.acl")
                        .header("Upgrade-Insecure-Requests", "1")
                        .cookies(loginCookie)
                        .data(reportData)
                        .get();

                 System.out.println("문서 파싱 : " + myReportDocument);

                Elements myPageTd = myPageDocument.select("body div div div div div form div table tbody tr td");
                Elements myClass = mainPageDocument.select("body div div div div div div ol li em");
                Elements myReport = myReportDocument.select("table tbody tr td a div");

//                for (Element td : myPageTd) {
//                    String myName = td.text();
//                    // 태그의 속성도 추출 가능
//                    // String url = td.attr("abs:value");
//
//                    System.out.println(myName);
//                    htmlContentInStringFormat += myName.trim() + "\n";
//                }
//                for (Element td : myClass) {
//                    String className = td.text();
//                    // 태그의 속성도 추출 가능
//                    // String url = td.attr("abs:value");
//
//                    System.out.println(className);
//                    htmlContentInStringFormat += className.trim() + "\n";
//                }
                for (Element div : myReport) {
                    String report = div.text();

                    System.out.println(report);
                    htmlContentInStringFormat += report.trim() + "\n";
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
