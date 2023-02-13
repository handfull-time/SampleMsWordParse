package com.utime.msword;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static void main( String[] args ) throws FileNotFoundException, IOException {

        System.out.println( "Hello MsWord Parser!" );

        // final MsWordParse msWord = new MsWordParse("samplePoi.docx");

        // final List<DocElement> docList = msWord.getDocList();

        // for( DocElement element : docList) {
		// 	System.out.println(element);
		// }

        final Properties defaultProrpties = new Properties();

        final SpringApplication application = new SpringApplication(App.class);

		application.setDefaultProperties( defaultProrpties );
		
		application.run(args);
    }
}
