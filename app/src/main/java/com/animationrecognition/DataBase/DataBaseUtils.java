package com.animationrecognition.DataBase;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * class for the database
 */
public class DataBaseUtils {

    //protected Boolean result = false;

    protected static final String IP = "http://120.78.200.80";

    protected InputStream is1;
    protected String text = "";
    protected String error = "";
    protected String userName;
    protected String password;
    protected String email;
    protected String id;
    protected String cId;

    protected String updateStatus = "ok";
    protected String loginStatus = "ok";
    protected String registerStatus = "ok";
    protected InputStream wikiStatus = null;
    protected InputStream communityStatus = null;
    protected InputStream showStatus = null;
    protected InputStream likeStatus = null;
    final DataBaseUtils db = this;

//
//    /**
//     * a constructor for the class
//     *
//     * @param updateStatus update status
//     * @param loginStatus login status
//     * @param registerStatus register status
//     */
//    public DataBaseUtils(String updateStatus, String loginStatus, String registerStatus) {
//        this.updateStatus = updateStatus;
//        this.loginStatus = loginStatus;
//        this.registerStatus = registerStatus;
//    }

    /**
     * method to get like Status
     *
     * @return likeStatus
     */
    public InputStream getLikeStatus() {
        return likeStatus;
    }

    /**
     * method to get show Status
     *
     * @return showStatus
     */
    public InputStream getShowStatus() {
        return showStatus;
    }

    /**
     * method to get community Status
     *
     * @return community Status
     */
    public InputStream getCommunityStatus() {
        return communityStatus;
    }

    /**
     * method to get the userName
     *
     * @return userName
     */
    public String getuserName() {
        return userName;
    }

    /**
     * method to get the email
     *
     * @return email
     */
    public String getemail() {
        return email;
    }


    /**
     * method to get updateStatus
     *
     * @return updateStatus
     */
    public String getUpdateStatus() {
        return updateStatus;
    }


    /**
     * method to get loginStatus
     *
     * @return loginStatus
     */
    public String getLoginStatus() {
        return loginStatus;
    }


    /**
     * method to get registerStatus
     *
     * @return registerStatus
     */
    public String getRegisterStatus() {
        return registerStatus;
    }

    /**
     * method to get wiki status
     *
     * @return wiki status
     */
    public InputStream getWikiStatus() {
        return wikiStatus;
    }

    /**
     * method to set the email
     *
     * @param email user's email
     */
    public void setemail(String email) {
        this.email = email;
    }

    /**
     * method to set the userName
     *
     * @param userName user's name
     */
    public void setuserName(String userName) {
        this.userName = userName;
    }

    /**
     * method to set the password
     *
     * @param password user's password
     */
    public void setpassword(String password) {
        this.password = password;
    }

    /**
     * method to get loginStatus
     *
     * @return id
     */

    public String getId() {
        return id;
    }

    /**
     * method to set the userName
     *
     * @param cId picture's Id
     */

    public void setcId(String cId) {
        this.cId = cId;
    }


    /**
     * method to get loginStatus
     *
     * @return cId
     */
    public String getcId() {
        return cId;
    }

    /**
     * method to set the userName
     *
     * @param id user's Id
     */

    public void setId(String id) {
        this.id = id;
    }


    /**
     * method to create a thread for login
     */
    public MyThread loginThread = new MyThread(new Work(loginStatus)) {
        /**
         * method to run the thread for login
         */
        public void run() {
            text = "";
            String url1 = IP + "/login.php";
            try {
                ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
                pairs.add(new BasicNameValuePair("txtEmail", email));
                pairs.add(new BasicNameValuePair("txtPassword", password));
                HttpParams params = null;
                HttpClient client = new DefaultHttpClient();
                params = client.getParams();
                //set timeout
                HttpConnectionParams.setConnectionTimeout(params, 5000);
                HttpConnectionParams.setSoTimeout(params, 5000);
                HttpPost post = new HttpPost(url1);
                post.setEntity(new UrlEncodedFormEntity(pairs));
                HttpResponse response = client.execute(post);
                is1 = response.getEntity().getContent();
               // result = true;
                BufferedReader reader;
                reader = new BufferedReader(new InputStreamReader(is1, "iso-8859-1"), 8);
                String line;
                while ((line = reader.readLine()) != null) {
                    text += line;
                }
                if(text.split(";").length == 3) {
                    loginStatus = text.split(";")[0];
                    setuserName(text.split(";")[1]);
                    setId(text.split(";")[2]);
//                    System.out.println("real_id" + text.split(";")[2]);
                }else {
                    loginStatus = text;
                }
            }
            catch (RuntimeException e){
                throw e;
            }
            catch (Exception e) {
                System.out.println("network exception");
                loginStatus = "Exception";
                e.printStackTrace();
            }
        }
    };


    /**
     * method to create a thread for register
     */
    public MyThread registerThread = new MyThread(new Work(registerStatus)) {
        /**
         * method to run the thread for register
         */
        public void run() {
            text = "";
            String url1 = IP + "/register.php";
            try {
                ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
                pairs.add(new BasicNameValuePair("txtUsername", userName));
                pairs.add(new BasicNameValuePair("txtEmail", email));
                pairs.add(new BasicNameValuePair("txtPassword", password));
                HttpClient client = new DefaultHttpClient();
                HttpParams params = null;
                params = client.getParams();
                HttpConnectionParams.setConnectionTimeout(params, 500);
                HttpConnectionParams.setSoTimeout(params, 500);
                HttpPost post = new HttpPost(url1);
                post.setEntity(new UrlEncodedFormEntity(pairs));
                HttpResponse response = client.execute(post);
                is1 = response.getEntity().getContent();
                //result = true;
                BufferedReader reader;

                reader = new BufferedReader(new InputStreamReader(is1, "iso-8859-1"), 8);
                String line;
                while ((line = reader.readLine()) != null) {
                    text += line;
                }
                registerStatus=text;
            } catch (RuntimeException e) {
                throw e;
            }
            catch (Exception e) {
                registerStatus = "Exception";
                e.printStackTrace();
            }
        }

    };

    /**
     * method to create a thread for update
     */
    public MyThread updateThread = new MyThread(new Work(updateStatus)) {
        /**
         * method to run the thread for update
         */
        public void run() {
            text = "";
            String url1 = IP + "/update.php";
            try {
                ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
                pairs.add(new BasicNameValuePair("txtUsername", userName));
                pairs.add(new BasicNameValuePair("txtEmail", email));
                pairs.add(new BasicNameValuePair("txtPassword", password));
                HttpClient client = new DefaultHttpClient();
                HttpParams params = null;
                params = client.getParams();
                HttpConnectionParams.setConnectionTimeout(params, 500);
                HttpConnectionParams.setSoTimeout(params, 500);
                HttpPost post = new HttpPost(url1);
                post.setEntity(new UrlEncodedFormEntity(pairs));
                HttpResponse response = client.execute(post);
                is1 = response.getEntity().getContent();
                //result = true;
                BufferedReader reader;
                reader = new BufferedReader(new InputStreamReader(is1, "iso-8859-1"), 8);
                String line;
                while ((line = reader.readLine()) != null) {
                    text += line;
                }
                updateStatus = text;
            } catch (RuntimeException e) {
                throw e;
            }
            catch (Exception e) {
                error += "\nClientProtocolException: " + e.getMessage();
                updateStatus = "Exception";
            }
        }
    };


    /**
     * class to create a thread for capture the content in a wiki page
     */
    public MyThread wikiThread = new MyThread(new Work(wikiStatus)) {
        /**
         * method to run the thread for capture the content in a wiki page
         */
        public void run() {
            InputStream is = null;
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(IP + "/wikiData.php");
//                System.out.printf("%s\n", db.getId());
                HttpResponse response = client.execute(get);
                is = response.getEntity().getContent();
            } catch (RuntimeException e) {
                throw e;
            }
            catch (IOException e) {
                e.printStackTrace();
            }finally {
                wikiStatus = is;
            }
        }
    };

    /**
     * class to create a thread for capture the content in a community page
     */
    public MyThread communityThread = new MyThread(new Work(communityStatus)) {
        /**
         * method to run the thread for capture the content in a community page
         */
        public void run() {
            InputStream is = null;
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(IP + "/query_subscribe.php?u_id=" + db.getId());
                System.out.println(IP + "/query_subscribe.php?u_id=" + db.getId());
                HttpResponse response = client.execute(get);
                is = response.getEntity().getContent();
            } catch (RuntimeException e) {
                throw e;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                communityStatus = is;
            }
        }
    };

    /**
     * class to create a thread for capture the content in a show page
     */
    public MyThread showThread = new MyThread(new Work(showStatus)) {
        /**
         * method to run the thread for capture the content in a show page
         */
        public void run() {
            InputStream is = null;
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(IP + "/query_upload.php?c_id=" + db.getcId());
                HttpResponse response = client.execute(get);
                is = response.getEntity().getContent();
            } catch (RuntimeException e) {
                throw e;
            }
            catch (IOException e) {
                e.printStackTrace();
            }finally {
                showStatus = is;
            }
        }
    };

    /**
     * class to create a thread for capture the content in a show page
     */
    public MyThread likeThread = new MyThread(new Work(likeStatus)) {
        /**
         * method to run the thread for capture the content in a show page
         */
        public void run() {
            InputStream is = null;
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(IP + "/query_like.php?c_id=" + db.getcId());

                HttpResponse response = client.execute(get);
                is = response.getEntity().getContent();
            } catch (RuntimeException e) {
                throw e;
            }
            catch (IOException e) {
                e.printStackTrace();
            }finally {
                likeStatus = is;
            }
        }
    };
}
