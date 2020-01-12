(ns core
  (:require github))

(defn- get-stars [username repo]
  "Grabs the star count from the provided repo"
  (->> repo
       (github/get-repo username)
       :stargazers-count))

(defn sum-stars
  ([username] (sum-stars username (github/repo-names username)))
  ([username repos]
   (->> repos
        (map #(get-stars username %))
        (reduce +))))
