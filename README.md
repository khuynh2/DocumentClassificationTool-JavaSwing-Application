# Automatic Document Classification Tool

![interface](https://i.imgur.com/JPh34KA.png)


## Description
An automatic document classifier that can classify pdf document while attempt to lower the inaccuracy caused by the occurrences of vocabulary ambiguities. This is achieved by first applying the Latent Dirichlet Allocation (LDA) algorithm to the data extracted from a collection of pdf documents to identify potentially meaningful candidates for classification. Then these candidates are used to populate a document ontology. A searching application for documents is then build to utilize this ontology through an ontology reasoner. To tackle the language ambiguity problem, WordNet is used to find synonyms of the input of the application. By applying the ontology and WordNet to the classifier, the problem in processing natural language will be resolved. Finally, a database is built to store extracted information from the e-documents such as title, authors, and abstract. This information will be used to further enhance the accuracy of the document searching application.


## Technologies
- Owl API
- JWI (MIT Java Wordnet Interface)
- GROBID
- MALLET
- Java Swing
- SQLite

## User Manual

**Select Input Directory:**  get the directory where the user stores their pdf file
![interface](https://i.imgur.com/VGUcvhA.png)

**Classify:** Start the document classification process. Apply topic modeling to the contents of the pdf file, constructing Document Ontology, and find the Best authors for each topic.
               - Instruction area: Display systematic instruction on how to operate the application
   - Topic tab: display the topics discover by LDA
   - Document tab: display the location of each pdf file and the topic distribution rate per document
   - Best Author tab: display the best authors per each topic, if does not have enough information to find the best authors for a certain topic, the value "0" is displayed in the authors column of that topic
   - Doc Database tab: display some of the contents extracted from the pdf files stored in the database includes: title, authors
     
   ![interface](https://i.imgur.com/n9dWB4L.png)
   ![interface](https://i.imgur.com/HmijGYw.png)
   
   **Search:** This search engine can accept a string as input. When a string is inputted, all stop words and special character will be removed from the input string, then the string is split into multiple words. Then various search process will be applied to these words to calculate the final search result, which is displayed under the Search tab.
   
   ![interface](https://i.imgur.com/r1lfGLZ.png)
   
   The figure demonstrates the search result when input the string "morzy DATA! the mining". The search result tab displays documents and the score given by the search algorithm to those documents. The displayed result is ordered from highest score to lowest score.

