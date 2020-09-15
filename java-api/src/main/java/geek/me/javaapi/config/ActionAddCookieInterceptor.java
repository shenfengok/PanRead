package geek.me.javaapi.config;

import geek.me.javaapi.baidu.PcsConst;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static geek.me.javaapi.dto.form.ReNewForm.PCS_COOKIE;

@Component
public class ActionAddCookieInterceptor implements ClientHttpRequestInterceptor {


    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        HttpHeaders headers = request.getHeaders();

        headers.add("cookie", PcsConst.cookie);
        return execution.execute(request, body);
    }
}