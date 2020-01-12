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

(defn sum-stars-async
  ([username] (sum-stars-async username (github/repo-names username)))
  ([username repos]
   (let [stars (for [repo repos]
                 (future (get-stars username repo)))]
     (->> stars
          (map deref)
          (reduce +)))))
