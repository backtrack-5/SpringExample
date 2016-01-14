# SpringExample

This is a simple OAuth2 workflow (Spring boot application). Here I have separated the Authorization server and resource server.  
Added JDBC to store the Oauth2 tokens.



### Example Spring Oauth2 application 
* Using JDBC token store 
* Separated Authorization server and Resource server. 


### Usage

Run both Auth server and resource server. 

#### to get the Auth tokens

`http://localhost:8081/oauth/token/grant_type=client_credentials&client_id=client&client_secret=secret`

Response 
```
{ "access_token"  : "...",
  "token_type"    : "...",
  "expires_in"    : "...",
  "scope"         : "..."
}
```
#### to access the resource 

```
http://localhost:8080/
Authorization: Bearer 5cf0732b-6bbb-40c7-8fab-dcfefcc2fcfe
```




