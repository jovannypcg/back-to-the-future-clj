(ns core
  (:require github))

(defn- get-star-count [username repo]
  "Grabs the star count from the provided repo"
  (->> repo
       (github/get-repo username)
       :stargazers-count))

(defn sum-stars
  "Synchronously sums the stars of the provided repositories."
  ([username] (sum-stars username (github/repo-names username)))
  ([username repos]
   (->> repos
        (map #(get-star-count username %))
        (reduce +))))

(defn sum-stars-async
  "Asynchronously sums the stars of the provided repositories."
  ([username] (sum-stars-async username (github/repo-names username)))
  ([username repos]
   (let [stars (for [repo repos]
                 (future (get-star-count username repo)))]
     (->> stars
          (map deref)
          (reduce +)))))
