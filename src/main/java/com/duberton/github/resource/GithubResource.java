package com.duberton.github.resource;

import com.duberton.github.model.vo.UserVo;
import com.duberton.github.service.GithubService;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/github")
@Produces(MediaType.APPLICATION_JSON)
public class GithubResource {

  private GithubService githubService;

  @Inject
  public GithubResource(GithubService githubService) {
    this.githubService = githubService;
  }

  @GET
  @Path("/user/{username}/repos")
  public Response getReposFromUser(final @PathParam("username") String username) {
    UserVo userVo = githubService.getReposFromUser(username);
    return Response.ok(userVo).build();
  }
}
