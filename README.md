## Dropwizard-mock-feign

> POC to exercise the combination of DropWizard + Feign + WireMock

An API that acts as a Github proxy. 

#### Endpoints

Returns all the public repos from the specified username

> http://localhost:9000/api/github/user/{username}/repos

#### Notes

- There's a Postman collection in the ```./etc``` directory
- Github has a rate limit of 50 requests per not-authorized user, so in case the API starts returning errors it might be related to this
- The workaround for the above scenario is to perform the requests using Basic Authentication (which will be deprecated soon):

**UserClient.java**

```
@Headers({
    "Content-Type: application/json",
    "Accept: application/json"
    "Authorization: Basic base64AuthToken"
})
```  