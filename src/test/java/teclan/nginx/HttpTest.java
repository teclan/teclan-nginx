package teclan.nginx;

import java.io.IOException;

import org.junit.Test;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpTest {

	@Test
	public void requestTest() throws IOException, InterruptedException {

		OkHttpClient client = new OkHttpClient();

		String url = "http://localhost:800/50x.html";
		Request request = new Request.Builder().url(url).build();


		Response response = client.newCall(request).execute();
		System.out.println(response.body().string());

	}

}
