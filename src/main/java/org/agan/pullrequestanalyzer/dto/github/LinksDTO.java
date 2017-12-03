package org.agan.pullrequestanalyzer.dto.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * Describes the "_links" object returned from the GitHub API.
 * Example:
     "_links": {
         "self": {
            "href": "https://api.github.com/repos/octocat/Hello-World/pulls/1347"
         },
         "html": {
            "href": "https://github.com/octocat/Hello-World/pull/1347"
         },
         "issue": {
            "href": "https://api.github.com/repos/octocat/Hello-World/issues/1347"
         },
         "comments": {
            "href": "https://api.github.com/repos/octocat/Hello-World/issues/1347/comments"
         },
         "review_comments": {
            "href": "https://api.github.com/repos/octocat/Hello-World/pulls/1347/comments"
         },
         "review_comment": {
            "href": "https://api.github.com/repos/octocat/Hello-World/pulls/comments{/number}"
         },
         "commits": {
            href": "https://api.github.com/repos/octocat/Hello-World/pulls/1347/commits"
         },
         "statuses": {
            "href": "https://api.github.com/repos/octocat/Hello-World/statuses/6dcb09b5b57875f334f61aebed695e2e4193db5e"
         }
     }

 * NOTE: This object "flattens"  the links, removing the nested href object.
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LinksDTO {
    private String self;
    private String html;
    private String issue;
    private String comments;
    private String review_comments;
    private String review_comment;
    private String commits;
    private String statuses;

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getReview_comments() {
        return review_comments;
    }

    public void setReview_comments(String review_comments) {
        this.review_comments = review_comments;
    }

    public String getReview_comment() {
        return review_comment;
    }

    public void setReview_comment(String review_comment) {
        this.review_comment = review_comment;
    }

    public String getCommits() {
        return commits;
    }

    public void setCommits(String commits) {
        this.commits = commits;
    }

    public String getStatuses() {
        return statuses;
    }

    public void setStatuses(String statuses) {
        this.statuses = statuses;
    }

    // Setter used during deserialization from GitHub
    @JsonProperty("self")
    public void setSelf(LinkDTO self) {
        this.self = self.getHref();
    }

    @JsonProperty("html")
    public void setHtml(LinkDTO html) {
        this.html = html.getHref();
    }

    @JsonProperty("issue")
    public void setIssue(LinkDTO issue) {
        this.issue = issue.getHref();
    }

    @JsonProperty("comments")
    public void setComments(LinkDTO comments) {
        this.comments = comments.getHref();
    }

    @JsonProperty("review_comments")
    public void setReview_comments(LinkDTO review_comments) {
        this.review_comments = review_comments.getHref();
    }

    @JsonProperty("review_comment")
    public void setReview_comment(LinkDTO review_comment) {
        this.review_comment = review_comment.getHref();
    }

    @JsonProperty("commits")
    public void setCommits(LinkDTO commits) {
        this.commits = commits.getHref();
    }

    @JsonProperty("statuses")
    public void setStatuses(LinkDTO statuses) {
        this.statuses = statuses.getHref();
    }
}
