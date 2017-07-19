package example;

import com.squareup.okhttp.*;

/**
 * @author Maxim Kropachev
 */
public class Tester {
    public static void main(String[] args) {
        new Tester().start();
    }

    public void start(){
        for(int i=0;i<1;i++){
            new Thread(new TestThread((10000000L*i))).start();
        }
    }

    public class TestThread implements Runnable {
        private final Long startindex;

        public TestThread(Long startindex) {
            this.startindex = startindex;
        }

        @Override
        public void run() {
            try {
                OkHttpClient client = new OkHttpClient();


                MediaType mediaType = MediaType.parse("application/json");
                for (int i = 0; i < 100000; i++) {
                    RequestBody body = RequestBody.create(mediaType, String.format("{\r\n\"jsonrpc\": \"2.0\",\r\n  \"method\": \"invoke\",\r\n  \"params\": {\r\n    \"type\": 1,\r\n    \"chaincodeID\":{\r\n        \"name\": \"TableExample\"\r\n    },\r\n    \"CtorMsg\": {\r\n    \t\t\"function\":\"insert\",\r\n        \"args\": [\"%s\",\"test%s\"]\r\n    }\r\n  },\r\n  \"id\": 2\r\n}", i, i));
                    Request request = new Request.Builder()
                            .url("http://localhost:7050/chaincode")
                            .post(body)
                            .addHeader("content-type", "application/json")
                            .addHeader("cache-control", "no-cache")
                            .build();

                    Response response = client.newCall(request).execute();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
