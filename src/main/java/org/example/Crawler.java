package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;

public class Crawler {
   private HashSet<String> urlLink; // so that I cannot visit the same Url again//
    int MAX_DEPTH=2; //limiting my depth search//
    public Connection connection;
    public Crawler(){ //Initialized the constructor
        //Initializing the connection at line no.14 in singleton creation//
        //here setting up the connection to MySQL//
        connection=DatabaseConnection.getConnection();  //and here calling the method of get connection// //That will help us to write queries from java to MySQL//
        urlLink= new HashSet<>();
    }
    //let's create a recursive method that will call over the internet
    public void getPageTextAndLinks(String url,int depth) throws SQLException{ //at which Url we are visiting and at which depth we are//
        if(!urlLink.contains(url)){
            if(urlLink.add(url)){
                System.out.println(url);

            }try {
                Document document = Jsoup.connect(url).timeout(5000).get(); //gets the html page as a document that is parsing and 5000 means 5 seconds //
                //get the text of the page//
                //either of the two will happen//
                String text= document.text().length()<500 ? document.text():document.text().substring(0,499); // now we are checking whether the text length of the document is < 500 then we will be storing as it is else we will be cutting it to first 500 characters//

                System.out.println(text);
                //Insert the Data into pages table//                          //here we have added three question marks because we are yet to decide what things will go there//
               PreparedStatement preparedStatement=connection.prepareStatement("Insert into pages values(?,?,?)"); //preparestatement helps us to write only the insertion commands i.e. inserting into the tables//
                //it is showing the red line because we haven't handed the sql exception//
                preparedStatement.setString(1, document.title());
               preparedStatement.setString(2,url);
               preparedStatement.setString(3,text);
               preparedStatement.executeUpdate();

                //now we will be incrementing depth//
                depth++;
                if(depth>MAX_DEPTH){
                    return;
                }
                //depth is not greater than max-depth, so now we will
                //continue to its elements will get all the connected elements by using these code mentioned below//
                //get hyperlinks available on the current page//
                Elements availableLinksOnPage = document.select("a[href]");
                //run method recursively for every links available on current page//
                for (Element currentLink: availableLinksOnPage){ // here we are runnning recursively to get the elements//
                    getPageTextAndLinks(currentLink.attr("abs:href"),depth);
                }
            }catch (IOException ioException){
                ioException.printStackTrace();
            }
//            catch(SQLException sqlException){
//                sqlException.printStackTrace();
//            }
        }
    }

    public static void main(String[] args) throws SQLException {
        Crawler crawler=new Crawler();
        crawler.getPageTextAndLinks("https://www.javatpoint.com/", 0);
    }
}