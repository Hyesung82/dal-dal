package com.example.dalyeodalyeok.ui;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.dalyeodalyeok.DbOpenHelper;
import com.example.dalyeodalyeok.R;
import com.example.dalyeodalyeok.SSLConnect;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LMSFragment extends Fragment {

    private String loginPageUrl = "http://lms.pknu.ac.kr/ilos/main/member/login_form.acl";
    private EditText LMSID;
    private EditText LMSPASSWORD;
    private static TextView textviewHtmlDocument;
    private static String htmlContentInStringFormat="";
    private static DbOpenHelper mDbOpenHelper;

    static private final String SHARE_NAME = "SHARE_PREF";
    static SharedPreferences sharedPref = null;
    static SharedPreferences.Editor editor = null;

    int cnt=0;
    static int classCnt = 0;

    static String user = "";
    static String password = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup contatiner, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_lms, contatiner, false);

        LMSID = (EditText)root.findViewById(R.id.LMS_ID);
        LMSPASSWORD = (EditText)root.findViewById(R.id.LMS_PASSWORD);

        textviewHtmlDocument= (TextView)root.findViewById(R.id.tvCrawling);
        textviewHtmlDocument.setMovementMethod(new ScrollingMovementMethod());

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = sharedPref.edit();

        Button htmlTitleButton = (Button)root.findViewById(R.id.btnCrawling);
        htmlTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println((cnt + 1) + "번째 파싱");
                user = LMSID.getText().toString();
                password = LMSPASSWORD.getText().toString();

                editor.putString("user", user);
                editor.putString("password", password);

                mDbOpenHelper = new DbOpenHelper(getContext());
                mDbOpenHelper.open();
                mDbOpenHelper.create();

                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                jsoupAsyncTask.execute();
                cnt++;

                FragmentManager fragmentManager = getFragmentManager();
                assert fragmentManager != null;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, new MyinfoFragment());
                fragmentTransaction.commit();
            }
        });

//        Button reloadButton = (Button)root.findViewById(R.id.btnReload);
//        reloadButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println((cnt + 1) + "번째 파싱");
//
//                mDbOpenHelper = new DbOpenHelper(getContext());
//                mDbOpenHelper.open();
//                mDbOpenHelper.create();
//
//                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
//                jsoupAsyncTask.execute();
//                cnt++;
//
//
//            }
//        });

        return root;
    }

    public static class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String htmlPageUrl = "http://lms.pknu.ac.kr/ilos/main/main_form.acl";
                Document doc = Jsoup.connect(htmlPageUrl).get();

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

                // Windows, Chrome의 User Agent
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
                System.out.println("쿠키 : " + loginCookie);

                // LMS 마이페이지
                Document myPageDocument = Jsoup.connect("http://lms.pknu.ac.kr/ilos/mp/myinfo_form.acl")
                        .userAgent(userAgent)
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

                String[] myClasses = new String[15];
                String[] classArr = new String[15];

                Elements myClassName = mainPageDocument.select("body div div div div div div ol li em");
                for (Element em : myClassName) {
                    String className = em.text();
                    String campus = className.split("]")[0];
                    if (campus.equals("[대연") || campus.equals("[용당") || campus.equals("[사이버")) {
                        myClasses[classCnt] = className.trim();
                        classArr[classCnt] = mainPageDocument.select("body div div div div div div ol li em").get(classCnt).attr("kj");
                        classCnt++;
                    }
                }

                System.out.println("수강 과목 수 : " + classCnt);
                System.out.println("수강 과목 : " + Arrays.toString(myClasses));
                System.out.println("수강 과목 array : ");
                for (int i = 0; i < classCnt; i++) {
                    System.out.println(classArr[i]);
                }

                // 수강과목(POST)
//                Map<String, String> reportData = new HashMap<>();
//                reportData.put("KJKEY", myClass1); // 객체지향프로그래밍 "A20193100305101"
//                reportData.put("returnData", "json");
//                reportData.put("returnURI", "/ilos/st/course/submain_form.acl");
//                reportData.put("encoding", "utf-8");

//                Document classPageDoc = Jsoup.connect("http://lms.pknu.ac.kr/ilos/st/course/eclass_room2.acl")
//                        .timeout(3000)
//                        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36")
//                        .data(reportData)
//                        .cookies(loginCookie)
//                        .ignoreContentType(true)
//                        .get();
//
//                System.out.println("수강과목 document : " + classPageDoc);

                Elements myPageTd = myPageDocument.select("body div div div div div form div table tbody tr td");
                Elements myClass = mainPageDocument.select("body div div div div div div ol li em");

                int eCount = 0;
                for (Element td : myPageTd) {
                    String myName = td.text();
                    // 태그의 속성도 추출 가능
                    // String url = td.attr("abs:value");
                    System.out.println(myName);
                    if (eCount == 1) {
                        htmlContentInStringFormat += myName.trim() + "\n";
                        editor.putString("userName", myName.trim());
                        editor.apply();
                    }
                    else if (eCount == 4) {
                        htmlContentInStringFormat += myName.trim() + "\n";
                        editor.putString("userPhone", myName.trim());
                        editor.apply();
                    }
                    else if (eCount == 6) {
                        htmlContentInStringFormat += myName.trim() + "\n";
                        editor.putString("userEmail", myName.trim());
                        editor.apply();
                    }
                    eCount++;
                }
//                for (Element td : myClass) {
//                    String className = td.text();
//                    // 태그의 속성도 추출 가능
//                    // String url = td.attr("abs:value");
//
//                    System.out.println(className);
//                    htmlContentInStringFormat += className.trim() + "\n";
//                }

                for (int i = 0; i < classCnt; i++) {
                    Connection.Response resClass = Jsoup.connect("http://lms.pknu.ac.kr/ilos/st/course/eclass_room2.acl")
                            .userAgent(userAgent)
                            .cookies(loginTryCookie)
                            //.data(reportData)
                            .data("KJKEY", classArr[i])
                            .data("returnData", "json")
                            .data("returnURI", "/ilos/st/course/submain_form.acl")
                            .data("encoding", "utf-8")
                            .data("returnURL", "/ilos/st/course/submain_form.acl")
                            .data("lectType", "0")
                            .cookies(loginCookie)
                            .ignoreContentType(true)
                            .method(Connection.Method.POST)
                            .execute();

                    System.out.println("응답 resClass : " + resClass);
                    System.out.println("resClass 상태 메시지 : " + resClass.statusMessage());

                    // 로그인 성공 후 얻은 쿠키
                    // 쿠키 중 JSESSION이라는 값을 확인할 수 있다
                    Map<String, String> classCookie = resClass.cookies();
                    System.out.println("resClass 쿠키 : " + classCookie);

                    Document myReportDocument1 = Jsoup.connect("http://lms.pknu.ac.kr/ilos/st/course/submain_form.acl")
                            .userAgent(userAgent)
                            .data("returnURL", "/ilos/st/course/submain_form.acl")
                            .data("lectType", "0")
                            .ignoreContentType(true)
                            .cookies(loginCookie)
                            //.data(reportData)
                            .data("KJKEY", classArr[i])
                            .data("returnData", "json")
                            .data("returnURI", "/ilos/st/course/submain_form.acl")
                            .data("encoding", "utf-8")
                            .get();

                    Elements myReport = myReportDocument1.select("div div div ol li em a");

                    for (Element div : myReport) {
                        String report = div.text();

                        if (report.charAt(0) == '[') {
                            System.out.println(report);
                            htmlContentInStringFormat += report.trim() + "\n";

                            if (!mDbOpenHelper.search(myClasses[i], report)) { // DB에 같은 과제가 없으면
                                mDbOpenHelper.insertColumn(myClasses[i], report.trim(), 0);
                                Log.d("DB 저장", "완료");
                            }
                        }
                    }
                }

                System.out.println("DB 출력 : ");
                showDatabase("_id");

                StringBuilder IDnPASSWORD = new StringBuilder();
                Map<String, ?> totalValue = sharedPref.getAll();
                for(Map.Entry<String, ?> entry : totalValue.entrySet()) {
                    IDnPASSWORD.append(entry.getKey().toString()).append(": ").append(entry.getValue().toString()).append("\r\n");
                }
                System.out.println(IDnPASSWORD);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            System.out.println("htmlDocument : " + htmlContentInStringFormat);
            if (htmlContentInStringFormat.equals("")) textviewHtmlDocument.setText("새로운 알림이 없습니다.");
            else textviewHtmlDocument.setText(htmlContentInStringFormat);
        }
    }

    public static void showDatabase(String sort) {
        Cursor iCursor = mDbOpenHelper.sortColumn(sort);
        Log.d("showDatabase", "DB Size: " + iCursor.getCount());
        while(iCursor.moveToNext()) {
            String tempIndex = iCursor.getString(iCursor.getColumnIndex("_id"));
            String tempSubject = iCursor.getString(iCursor.getColumnIndex("subject"));
            String tempReport = iCursor.getString(iCursor.getColumnIndex("report"));
            String tempChecked = iCursor.getString(iCursor.getColumnIndex("checked"));

            String result = tempIndex + tempSubject + tempReport + tempChecked;
            System.out.println(result);
        }
    }
}
