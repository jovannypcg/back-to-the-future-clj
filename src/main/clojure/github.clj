(ns github
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]))

(defonce token (System/getenv "GITHUB_TOKEN"))
(defonce base-url "https://api.github.com")
(defonce default-options {:accept :json :content-type :json :headers {"Authorization" (str "token " token)}})

(defn- keyword* [^String attr]
  "Replaces underscores with dashes in the incoming attribute representing a JSON key."
  (-> attr
      (clojure.string/replace #"_" "-")
      keyword))

(defn- parse [^String json-str]
  "Converts the incoming string representing a JSON response into a clojure data structure."
  (-> json-str
      (json/read-str :key-fn keyword*)))

(defn- user-repos [^String username]
  "Fetches the public repositories of the given user."
  (-> (str base-url "/users/" username "/repos")
      (client/get default-options)
      :body
      parse))

(defn repo-names [^String username]
  (->> username
      user-repos
      (map :name)))

(defn get-repo [username repo]
  (-> (str base-url "/repos/" username "/" repo)
      (client/get default-options)
      :body
      parse))
