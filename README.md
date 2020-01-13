# Back to the Future

Small and straightforward example on parallel processing using *futures* in Clojure.

![](https://media.giphy.com/media/zZeCRfPyXi9UI/giphy.gif)

The `core` namespace provides two functions to get the total amount of stars a particular account on Github has by fetching its repositories one by one using the Github API.

* `core/sum-stars`: Fetches the repositories of a certain account, one by one and sums up their stars.
* `core/sum-stars-async`: Fetches the repositories of a certain account in parallel using Clojure futures. Once all requests are done, the results are aggregated to generate the sum.

## Prerequisites

* Java 8
* Clojure 1.10.1
* Github OAuth token (optional)

### Java 8

It can be install using SDKMan by running:

```shell
$ curl -s "https://get.sdkman.io" | bash
```

Close the current terminal and open a new one to run:

```shell
$ source "$HOME/.sdkman/bin/sdkman-init.sh"
```

Do not forget to export `JAVA_HOME` in `.zshrc` or `.bashrc`, depending on the shell you are using:

```shell
# Add JAVA_HOME to .zshrc or .bashrc
export JAVA_HOME=$HOME/.sdkman/candidates/java/current
```

Now install Java 8 using the `sdk` command:

```shell
$ sdk install java 8.0.232.j9-adpt
```

### Clojure

Follow up the instructions to install it on the [official web page](https://www.clojure.org/guides/getting_started).

### Github OAuth token

This step is not required unless you want to execute unlimited requests to the endpoints used by this repository.

Follow up the instructions to generate a personal access token on the [official web page](https://help.github.com/en/github/authenticating-to-github/creating-a-personal-access-token-for-the-command-line).

Once you have your token, export it to your environment by executing:

```shell
$ export GITHUB_TOKEN=<redacted_token>
```

## Playing around

1. Clone the repository

   ```shell
   $ git clone https://github.com/jovannypcg/back-to-the-future-clj.git
   $ cd back-to-the-future-clj
   ```

2. Start a REPL

   ```shell
   $ clj
   ```

3. Require namespaces

   ```shell
   user=> (require 'core)
   user=> (require 'github)
   ```

4. Play around

   ```clojure
   user=> (core/sum-stars "github")
   6750
   ```

   ```clojure
   user=> (def username "jovannypcg")
   #'user/username
   user=> (def repos (github/repo-names username))
   #'user/repos
   
   user=> (core/sum-stars username repos)
   64
   user=> (core/sum-stars-async username repos)
   64
   ```