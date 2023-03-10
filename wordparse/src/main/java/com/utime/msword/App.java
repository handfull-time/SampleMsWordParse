package com.utime.msword;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.utime.msword.objects.DocElement;

public class App {
    public static void main( String[] args ) throws FileNotFoundException, IOException {

        System.out.println( "Hello MsWord Parser!" );

        final MsWordParse msWord = new MsWordParse("samplePoi.docx");

        final List<DocElement> docList = msWord.getDocList();

        for( DocElement element : docList) {
			System.out.println(element);
		}
    }
}
