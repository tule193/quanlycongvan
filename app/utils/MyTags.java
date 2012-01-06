/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import groovy.lang.Closure;
import java.io.PrintWriter;
import java.util.*;
import play.templates.FastTags;
import play.templates.GroovyTemplate.ExecutableTemplate;
import play.templates.JavaExtensions;
import utils.helpers.StringHelper;

/**
 *
 * @author to_viet_anh
 */
@FastTags.Namespace("my.tags")
public class MyTags extends FastTags {
    public static void _debug (Map<?, ?> args, Closure body, PrintWriter out, 
      ExecutableTemplate template, int fromLine){
       if(args.size()==1){
           out.print("<div class=\"debug\">"+StringHelper.debug(args.get("arg"))+"</div>");
       }
    }
    public static void _summary (Map<?, ?> args, Closure body, PrintWriter out, 
      ExecutableTemplate template, int fromLine){
       if(args.size()==2){

       }
    }
}
