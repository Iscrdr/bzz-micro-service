package com.bzz.cloud.framework.auth2.token;

//@Component
public class TokenScheduledTask {
    /*private static Logger logger = LoggerFactory.getLogger(TokenScheduledTask.class);

    public final static long ONE_Minute = 60 * 1000 * 60 * 20;

    @Autowired
    private AuthRemoteClient authRemoteClient;

    *//**
     * 刷新Token
     *//*
    @Scheduled(fixedDelay = ONE_Minute)
    public void reloadApiToken() {
        String token = this.getToken();
        while (StringUtils.isBlank(token)) {
            try {
                Thread.sleep(1000);
                token = this.getToken();
            } catch (InterruptedException e) {
                logger.error("", e);
            }
        }
        System.setProperty("fangjia.auth.token", token);
    }

    public String getToken() {
        AuthQuery query = new AuthQuery();
        query.setAccessKey("1");
        query.setSecretKey("1");
        ResponseData response = authRemoteClient.auth(query);
        return response.getData() == null ? "" : response.getData().toString();
    }*/
}
