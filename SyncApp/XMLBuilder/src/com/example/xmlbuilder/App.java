package com.example.xmlbuilder;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
 
public class App
{
    public static void showDirs(File dir)
    {
    	if(dir.listFiles()!=null){
	        for(File f:dir.listFiles())
	        {
	            System.out.println(f.getAbsolutePath());
	            if(f.isDirectory())
	            {
	                showDirs(f);
	            }
	        }
    	}
    }
}