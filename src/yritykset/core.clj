(ns yritykset.core
  (:require [clojure.data.json :as json])
  (:require [clojure.string :as s])
  (:require [clj-http.client :as http])
  (:require [clojure.data.csv :as csv])
  (:require [clojure.java.io :as io]))

(defn get-company [id]
  (-> (http/get (str "http://avoindata.prh.fi/bis/v1/" id) {:throw-exceptions false})
      (:body)
      (json/read-str :key-fn keyword)))

(defn get-addresses [company]
  (-> (:results company)
      (first)
      (:addresses)))

(defn add-ids [addresses id name]
  (map #(assoc % :id id :name name) addresses))

(defn get-company-addresses [cmp]
  (let [[name id] cmp]
    (do (Thread/sleep 1000)
        (println name)
        (-> (get-company id)
            (get-addresses)
            (add-ids id name)))))

(defn -main [& args]
  (with-open [reader (io/reader "yritykset-in.csv")
              writer (io/writer "yritykset-out.csv")]
    (->> (csv/read-csv reader)
         (map get-company-addresses)
         (flatten)
         (filter #(nil? (:endDate %)))
         (filter :postCode)
         (filter #(= 2 (:type %)))
         (map #(select-keys % [:id :name :street :postCode :city]))
         (map vals)
         (csv/write-csv writer))))

