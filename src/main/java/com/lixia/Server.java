package com.lixia;

import javax.annotation.Resource;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.*;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;
import java.io.StringReader;
import java.util.List;

@WebServiceProvider
@ServiceMode(value = Service.Mode.PAYLOAD)
public class Server implements Provider<Source> {

    private WebServiceContext wsContext;

    @Resource
    public void setContext(WebServiceContext context) {
        this.wsContext = context;
    }

    public Source invoke(Source request) {
        MessageContext mc = wsContext.getMessageContext();
        String query = (String) mc.get(MessageContext.QUERY_STRING);
        String path = (String) mc.get(MessageContext.PATH_INFO);
        // System.out.println("Query: " + query);
        // System.out.println("PATH: " + path);

        int targetSum = 0;
        int[] source = null;
        String[] subQueries = query.split("&");
        for (String subQuery : subQueries) {
            String[] pair = subQuery.split("=");
            String key = pair[0];
            String value = pair[1];
            if (key.trim().equalsIgnoreCase("target")) {
                targetSum = new Double(Double.valueOf(value) * 100).intValue();
            } else if (key.trim().equalsIgnoreCase("source")) {
                String[] _source = value.split(",");
                source = new int[_source.length];
                for (int i = 0; i < source.length; i++) {
                    source[i] = new Double(Double.valueOf(_source[i]) * 100).intValue();
                }
            }
        }

        System.out.println("target = $" + (targetSum / 100.0));
        System.out.print("source = ");
        for (int i = 0; i < source.length; i++) {
            System.out.print("$" + source[i] / 100.0 + " + ");
        }
        System.out.println("");
        TargetSumFinder.find(source, targetSum);

        return new StreamSource(new StringReader("<p>Hello There!</p>"));
    }

    public static void main(String[] args) throws InterruptedException {

        String address = "http://127.0.0.1:8080/";
        Endpoint.create(HTTPBinding.HTTP_BINDING, new Server()).publish(address);

        System.out.println("Service running at " + address);
        System.out.println("Type [CTRL]+[C] to quit!");

        Thread.sleep(Long.MAX_VALUE);
    }

    private void parseQuery() {

    }
}