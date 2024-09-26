package Email;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class EmailSender {
	public static void sendAdhocEmail(String[] string, String toSubject, String toBody, String PDF) throws Exception {
		
		// Create an HTTP client
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// Prepare the HTTP POST request
		HttpPost httpPost = new HttpPost("https://test1api.yosicare.com/dashboard/patient/v1/automationQAEmail");
		// Create JSON object with the request body
		JSONObject json = new JSONObject();
		json.put("to_email", string);
		json.put("to_subject", toSubject);
		json.put("to_body", toBody);
		json.put("PDF", PDF);
		// Set the request body
		StringEntity entity = new StringEntity(json.toString());
		httpPost.setEntity(entity);
		// Set content type header
		httpPost.setHeader("Content-Type", "application/json");
		// Execute the request
		CloseableHttpResponse response = httpClient.execute(httpPost);
		// Parse the response
		String responseJson = EntityUtils.toString(response.getEntity());
		JSONObject responseObject = new JSONObject(responseJson);
		// Log the response (you can handle it as needed)
		System.out.println("This is the response: " + responseObject.toString());
		// Close the response and the client
		response.close();
		httpClient.close();
	}


	public static void main(String[] args) {
		try {
			String[] emails = { "anbarasan.v@yosicare.com", "ranjith.uthay@yosicare.com" };
			sendAdhocEmail(emails, "SALES DEMO AUTOMATION SCRIPT CONFIRMATION","Sales Demo Regression Testing Completed",null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


