# Pull Request analyzer

Analyze information on a given organization's pull requests.

## Getting Started

Follow this readme to get started running pull-request-analyzer locally.

### Prerequisites

* Maven
* Java 1.8 jdk
* A Personal Access Token for GitHub's API. This one can be found in your github profile settings.
* An open port 8080

### Installing

1) Paste your GitHub username and Personal Access Token where indicated in application.yml. These are used to ensure you don't hit GitHub's rate limiter.

```
github:
  username: #YOUR USERNAME HERE
  personalAccessToken: #YOUR PERSONAL ACCESS TOKEN HERE
```

2) Maven install to build the project. Run the following command from the base pull-request-analyzer directory:
```
mvn install
```

3)Enter the 'target' directory that was just created and run the .jar file, using this command:
```
java -jar pull-request-analyzer-0.0.1-SNAPSHOT.jar
```

The program is now up and running! It should be accessible on port 8080.
You can look at week-over-week growth in pull request merges by hitting the 'pullrequests/{organization}/weekoverweek', endpoint e.g.
```
localhost:8080/pullrequests/ramda/weekoverweek
```

You can also compare 2 rolling week periods using the 'pullrequests/ramda/rollingweek' endpoint, e.g.
```
localhost:8080/pullrequests/ramda/rollingweek
```

In either case, you should receive JSON that compares the two time periods:
```
{
    "previousPeriod": {
        "periodStart": 1511310100745,
        "periodEnd": 1511914900744,
        "pullRequestsMerged": 4
    },
    "currentPeriod": {
        "periodStart": 1511914900745,
        "periodEnd": 1512519700744,
        "pullRequestsMerged": 3
    },
    "periodOverPeriodGrowth": "-25%"
}
```
## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Props to http://www.jsonschema2pojo.org/ for making it easy to create POJOs from json.
* Thanks mom and dad for everything.
* Thanks to the academy, for teaching me how to write acknowledgements.
* Props to Scott Frost for coaching Nebraska football.
