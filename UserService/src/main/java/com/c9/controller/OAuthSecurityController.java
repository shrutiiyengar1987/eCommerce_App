package com.c9.controller;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.c9.dto.AddUserInfo;
import com.okta.spring.config.OktaClientProperties;
import com.okta.spring.config.OktaOAuth2Properties;

import reactor.core.publisher.Mono;
@Controller
public class OAuthSecurityController {

	 private static final String STATE = "state";
	    private static final String SCOPES = "scopes";
	    private static final String OKTA_BASE_URL = "oktaBaseUrl";
	    private static final String OKTA_CLIENT_ID = "oktaClientId";
	    private static final String REDIRECT_URI = "redirectUri";
	    private static final String ISSUER_URI = "issuerUri";
	private final OktaOAuth2Properties oktaOAuth2Properties;

    private final OktaClientProperties oktaClientProperties;
    
	public OAuthSecurityController(OktaOAuth2Properties oktaOAuth2Properties,
			OktaClientProperties oktaClientProperties) {
		super();
		this.oktaOAuth2Properties = oktaOAuth2Properties;
		this.oktaClientProperties = oktaClientProperties;
	}

	@Autowired
    private OAuth2AuthorizedClientService authorizedClientService;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private HttpHeaders headers;
	@Value("${okta.uri}")
    private String url;
	@Value("${okta.api-token}")
	private String apiToken;
	
	@GetMapping("/")
    public String home() {
        return "home";
    }
	 @GetMapping(value = "/login")
	    public String login(HttpServletRequest request,
	                              @RequestParam(name = "state", required = false) String state,Model model) {

	        // if we don't have the state parameter redirect
	        if (state == null) {
	            //return "redirect:"+oktaOAuth2Properties.getRedirectUri();
	        	//System.out.println(oktaOAuth2Properties.getRedirectUri());
	        	return oktaOAuth2Properties.getRedirectUri();
	        	
	        }

	        // configuration for Okta Signin Widget
	       
	       model.addAttribute(STATE, state);
	       model.addAttribute(SCOPES, oktaOAuth2Properties.getScopes());
	       model.addAttribute(OKTA_BASE_URL, oktaClientProperties.getOrgUrl());
	       model.addAttribute(OKTA_CLIENT_ID, oktaOAuth2Properties.getClientId());
	       model.addAttribute(REDIRECT_URI,
	            request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +
	            request.getContextPath() + oktaOAuth2Properties.getRedirectUri()
	        );
	       model.addAttribute(ISSUER_URI, oktaOAuth2Properties.getIssuer());
	        return "login";
	    }
	

    @GetMapping({"/index"})
    public String index(Model model, OAuth2AuthenticationToken authentication) {
    	//System.out.println("auth"+authentication);
        OAuth2AuthorizedClient authorizedClient = this.getAuthorizedClient(authentication);
        model.addAttribute("userName", authentication.getName());
        model.addAttribute("clientName", authorizedClient.getClientRegistration().getClientName());
        return "index";
    }
    
    @RequestMapping(value="/add-user",method=RequestMethod.POST,produces={MediaType.APPLICATION_JSON_VALUE},consumes=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String addUser(@RequestBody AddUserInfo addUserInfo){
  	  String newUserInfo=null;
  	  URI uri = UriComponentsBuilder.fromUriString(url).queryParam("activate", "true")
  				.build().encode().toUri();
  	 headers.add("Accept", "application/json");
  		headers.add("Content-Type", "application/json");
  		headers.add("Authorization", apiToken);

  		HttpEntity<AddUserInfo> entity = new HttpEntity<>(addUserInfo, headers);
  		System.out.println(uri);
  		System.out.println(entity.getBody());
  		try{
  		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity,String.class );
  		newUserInfo=response.getBody();
  		}catch (Exception e) {
  			return e.getMessage()+"and error is"+e;
  		}
  		
  		return newUserInfo;

    }

    @GetMapping("/userinfo")
    public String userinfo(Model model, OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient authorizedClient = this.getAuthorizedClient(authentication);
        Map userAttributes = Collections.emptyMap();
        String userInfoEndpointUri = authorizedClient.getClientRegistration()
            .getProviderDetails().getUserInfoEndpoint().getUri();
        if (!StringUtils.isEmpty(userInfoEndpointUri)) {	// userInfoEndpointUri is optional for OIDC Clients
            userAttributes = WebClient.builder()
                .filter(oauth2Credentials(authorizedClient))
                .build()
                .get()
                .uri(userInfoEndpointUri)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        }
        model.addAttribute("userAttributes", userAttributes);
        return "userinfo";
    }

    @GetMapping("/post-logout")
    public String logout() {
        return "logout";
    }

    @GetMapping("/403")
    public String error403() {
        return "403";
    }

    private OAuth2AuthorizedClient getAuthorizedClient(OAuth2AuthenticationToken authentication) {
        return this.authorizedClientService.loadAuthorizedClient(
            authentication.getAuthorizedClientRegistrationId(), authentication.getName());
    }

    private ExchangeFilterFunction oauth2Credentials(OAuth2AuthorizedClient authorizedClient) {
        return ExchangeFilterFunction.ofRequestProcessor(
            clientRequest -> {
                ClientRequest authorizedRequest = ClientRequest.from(clientRequest)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + authorizedClient.getAccessToken().getTokenValue())
                    .build();
                return Mono.just(authorizedRequest);
            });
    }

}
