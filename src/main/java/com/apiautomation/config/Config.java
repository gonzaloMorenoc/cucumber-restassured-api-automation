package com.apiautomation.config;

import org.aeonbits.owner.Config.Sources;

@Sources({"classpath:config.properties"})
public interface Config extends org.aeonbits.owner.Config {
    
    @Key("base.url")
    @DefaultValue("https://gorest.co.in/public/v2")
    String baseUrl();
    
    @Key("access.token")
    String accessToken();
    
    @Key("timeout")
    @DefaultValue("30")
    int timeout();
}