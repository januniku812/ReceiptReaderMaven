import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * Uploads an image for receipt OCR and gets the result in JSON.
 * Required dependencies: org.apache.httpcomponents:httpclient:4.5.13 and org.apache.httpcomponents:httpmime:4.5.13
 */
public class RecieptReaderMaven {

    public static void main(String[] args) throws Exception {
        String receiptOcrEndpoint = "https://ocr.asprise.com/api/v1/receipt"; // Receipt OCR API endpoint
        File imageFile = new File("C:\\Users\\jshar\\Ideaproject2\\recieptReaderMaven\\src\\main\\java\\ikeareceipt.jpg");

        System.out.println("=== Java Receipt OCR Demo - Need help? Email support@asprise.com ===");

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(receiptOcrEndpoint);
            post.setEntity(MultipartEntityBuilder.create()
                    .addTextBody("client_id", "TEST")       // Use 'TEST' for testing purpose
                    .addTextBody("recognizer", "auto")      // can be 'US', 'CA', 'JP', 'SG' or 'auto'
                    .addTextBody("ref_no", "ocr_java_123'") // optional caller provided ref code
                    .addPart("file", new FileBody(imageFile))    // the image file
                    .build());

            try (CloseableHttpResponse response = client.execute(post)) {
                String result = EntityUtils.toString(response.getEntity());
                System.out.println(result); // Receipt OCR result in JSONon
//                JSONObject jsonObject = new JSONObject(response.getEntity().toString());
                JSONObject jsonObject = new JSONObject(result);
                String jsonobjtext = jsonObject.getJSONArray("receipts").getJSONObject(0).getString("ocr_text");
                System.out.println("JSON OBJECT ocr text:" + jsonobjtext);
            }
        }
    }
}