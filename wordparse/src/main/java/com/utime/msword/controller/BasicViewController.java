package com.utime.msword.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.utime.msword.service.MsWordParse;

@Controller
public class BasicViewController {
    
    final MsWordParse wordParse;

    BasicViewController( MsWordParse mwp ){
        this.wordParse = mwp;
    }

    @RequestMapping(value = {"", "/"})
    public String mainView(ModelMap model){

        model.addAttribute("data", "model-" + System.currentTimeMillis());

        return "MainView";
    }

    @RequestMapping("Parser.html")
    public String dataParser( ModelMap model, @RequestParam("wordFile") MultipartFile file ) throws IOException{
        if( file == null || file.isEmpty() ){
            model.addAttribute("data", "");
            return "MainView";
        }

        model.addAttribute( "data", this.wordParse.Parser(file.getInputStream()) );
        
        return "DataView";
    }
}
