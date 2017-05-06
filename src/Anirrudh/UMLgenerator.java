package Anirrudh;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.visitor.VoidVisitorAdapter;
import net.sourceforge.plantuml.SourceStringReader;

class Interface extends VoidVisitorAdapter
{



    
	 @Override
	 public void visit(ClassOrInterfaceDeclaration declare, Object arguement)
	   {
		  List<ClassOrInterfaceType> visitlist = declare.getImplements();
		 // Creating a list for implements.
		 if(visitlist==null)
			 return;
		 for (ClassOrInterfaceType citype : visitlist) {
		// System.out.printl("comparison made between visit list and defined list 	 
				String str = citype.toString();
				UMLgenerator.ilist.add(str);
				if(!UMLgenerator.first.contains( str + "<|.. "  + UMLgenerator.classname )
						&& !UMLgenerator.first.contains
//if(!UMLgenerator.first.contains( str + "<|.. "  + UMLgenerator.classname )
						//if( !UMLgenerator.first.contains)						
						( str + "<.. "  + UMLgenerator.classname + ":uses" ))
					UMLgenerator.first = UMLgenerator.first + str + " " + "<|.. " + " " + 
						UMLgenerator.classname +  "\n";
			}
		
	      
		 
	   }
     



	
}
class name extends VoidVisitorAdapter {

	 @Override
	 public void visit(ClassOrInterfaceDeclaration decl, Object arguement)
	   {
	     
		 // making the class to display the class name 
		 List<ClassOrInterfaceType> visitlist = decl.getExtends();
		 if(visitlist==null)
			 return;
		 for (ClassOrInterfaceType citype : visitlist) {
				String str = citype.toString();
/*				forEach(c -> {
		            String oldName = c.getName().getIdentifier();
		            String newName = "Abstract" + oldName;
		            System.out.println("Renaming class " + oldName + " into " + newName);
		            c.getName().setIdentifier(newName);*/		
				UMLgenerator.first = UMLgenerator.first + str + " " + "<|--" + " " + UMLgenerator.classname + "\n";
			}
	      
	      
	   }
     
}
class UMLmethod extends VoidVisitorAdapter {


    
    @Override
    public void visit(MethodDeclaration decl, Object arguement) {
    
    	if(decl.getName()!=null)
    		UMLgenerator.mlist.add(decl.getName().toLowerCase());
    	String param= "";
    
    	if (decl.getBody() !=null && decl.getBody().getStmts()!=null) {
    	
    		for(Statement s : decl.getBody().getStmts())
    		{
    			if(s!=null)
    			{
    			String str = s.toString();
    			String delimiter = "[ .,?!]+";
    			/*
    			 *  try {
                new VoidVisitorAdapter<Object>() {
                    @Override
                    public void visit(MethodCallExpr n, Object arg) {
                        super.visit(n, arg);
                        System.out.println(" [L " + n.getBeginLine() + "] " + n);
                    }
      
    }
    			 */
    		//
		        String[] tokens = str.split(delimiter);
		       // System.out.println("token0"+tokens[0]);
		        if(tokens[0]!=null)
		        {
		        if(UMLgenerator.list.contains(tokens[0]))
		        	/*list contains all classes names.
		        	//comparing it with tokens[0] to find  class name matches.
		        	this will check dependency in methods*/
		        	UMLgenerator.first = UMLgenerator.first + tokens[0] +"<.. " + UMLgenerator.classname + "\n";
		        }
    			}
    		}
    		
    	}
    
    	if(decl.getParameters()!=null)
    	{
    	for(Parameter p : decl.getParameters())
    	{
    		if(param != "")
        		param = param + "," + p.toString();
        		else 
        			param = p.toString();
    	String ch =  p.getType().toString();
    	//System.out.println("Interfacelist : "+Umlgen.interfacelist);
    //	System.out.println("Classname : "+Umlgen.classname);
    	
    	if(UMLgenerator.list.contains(ch))
    	{
    		if(!UMLgenerator.first.contains(ch + "<.. "  + UMLgenerator.classname + ":uses") 
    				&& UMLgenerator.ilist.contains(ch) 
    				&& !UMLgenerator.ilist.contains(UMLgenerator.classname))//note
    	UMLgenerator.first = UMLgenerator.first + ch + "<.. "  + UMLgenerator.classname + ":uses" + "\n";
    	}
    	}	
    	}
    	/*if(!UMLgenerator.first.contains(ch + "<.. "  + UMLgenerator.classname + ":uses") 
    	if( UMLgenerator.ilist.contains(ch)) 
    				&& !UMLgenerator.ilist.contains(UMLgenerator.classname))//note*/
    	//System.out.println(n.toString());
    	//System.out.println("getmodifiers : "+n.getModifiers());
    
    	if(decl.getModifiers()==1)
    	{
    		UMLgenerator.first = UMLgenerator.first + UMLgenerator.classname + " : "+ "+" + decl.getName() + "("+ param +")" + ":" + decl.getType();
    		UMLgenerator.first = UMLgenerator.first + "\n";
    	}
    }
    
    


}
class Field extends VoidVisitorAdapter {


    @Override
    public void visit (FieldDeclaration decl, Object arguement) {
    	int i;
    	
    	String str = decl.getType().toString();
    	boolean flag =false;
    	System.out.println(UMLgenerator.classname);
    	// class
    	System.out.println(UMLgenerator.list);
    	if(UMLgenerator.list.contains(str))
        	{
    		if(str.contains("Collection"))
    		{
    		   i= UMLgenerator.list.indexOf(str)-1;
    		 flag =true;
    		}
    		else 
    			i = UMLgenerator.list.indexOf(str);
    	
    		
    		if(UMLgenerator.first.contains(UMLgenerator.list.get(i) + " -- "  + UMLgenerator.classname ))
    			System.out.println("existing class");
    		else if(UMLgenerator.first.contains(" " + UMLgenerator.list.get(i) + " - \"1\" " + UMLgenerator.classname ) && flag == false)
    		{
    	// one to one 
    			
    			UMLgenerator.first.replace(UMLgenerator.list.get(i) + " - \"1\" " + UMLgenerator.classname, UMLgenerator.list.get(i) + "\"1\" - \"1\" " + UMLgenerator.classname);
    		}
    		
    		else if(UMLgenerator.first.contains(" "+UMLgenerator.list.get(i) + " - \"1\" " + UMLgenerator.classname ) && flag == true)
    		{
    			
    			UMLgenerator.first.replace(UMLgenerator.list.get(i) + " - \"1\" " + UMLgenerator.classname, UMLgenerator.list.get(i) + "\"*\" - \"1\" " + UMLgenerator.classname);
    		}
    		
    		// one to many and many to one
    		else if(UMLgenerator.first.contains(UMLgenerator.list.get(i) + " - \"*\" " + UMLgenerator.classname ) && flag == false)
    		{
    			
    			UMLgenerator.first.replace(UMLgenerator.list.get(i) + " - \"*\" " + UMLgenerator.classname, UMLgenerator.list.get(i) + "\"1\" - \"*\" " + UMLgenerator.classname);
    		}
    	
    		
    		else if(UMLgenerator.first.contains( UMLgenerator.list.get(i) + " - \"*\" " + UMLgenerator.classname ) && flag == true)
    		{
    			
    			UMLgenerator.first.replace(UMLgenerator.list.get(i) + " - \"*\" " + UMLgenerator.classname, UMLgenerator.list.get(i) + "\"*\" - \"*\" " + UMLgenerator.classname);
    		}
    	//multiple dependencies check 
    		else
    		{
    			
    			if(flag==false)
    				UMLgenerator.first = UMLgenerator.first + UMLgenerator.classname + " - \"1\" "  + UMLgenerator.list.get(i) + "\n";
    			else
    				UMLgenerator.first = UMLgenerator.first + UMLgenerator.classname + " - \"*\" "  + UMLgenerator.list.get(i) + "\n";
    		}
    		
    		
    		
        	}
    			


    		
        	
    	
    	String k =decl.toString();
         k = k.replaceAll("[;]", "");
         String[] strs = k.split("\\s+");
        
         if(strs[0].equals("public"))
        	 strs[0]="+" ; 
         // public variable
         if(strs[0].equals("private"))
         {    
        	 if(UMLgenerator.mlist.contains("set"+strs[2]) 
        			 && UMLgenerator.mlist.contains("get"+strs[2]))
        	 strs[0]="+" ;
        	 else
        		 strs[0]="-" ;
        	 
         }//checking private varibale
         /* if(UMLgenerator.mlist.contains("set"+strs[2]) 
          * if (UMLgenerator.mlist.contains("get"+strs[2]))*/
         //private variable
         if(strs[0].equals("protected"))
        	 strs[0]="#" ;
         if(strs.length >2 && (strs[0] == "+" || strs[0] == "-"))
         {
        	 UMLgenerator.first = UMLgenerator.first + UMLgenerator.classname + " : " + strs[0] + " " + strs[2] + " : " + strs[1];
        	 UMLgenerator.first = UMLgenerator.first + "\n";
         strs[2] = Character.toUpperCase(strs[2].charAt(0)) + strs[2].substring(1);
         String rep1 = "get" + strs[2];
         String rep2 = "set" + strs[2];
//protected variable    
      //s = s.replaceAll(pattern, "");
         UMLgenerator.first = UMLgenerator.first.replaceAll( ".*"+rep1+".*(\r?\n|\r)?", "" );
         UMLgenerator.first = UMLgenerator.first.replaceAll( ".*"+rep2+".*(\r?\n|\r)?", "" );
      
       
         }
         //System.out.println(Umlgen.s);
         /*if(args.length < 2) {
			System.out.println("Error: Invalid parameters!");
			System.out.println("Correct usage: java -jar <jarname.jar> <classpath> <outputfilename>");
			System.out.println("Note:\t\tClasspath has to be relative.\n\t\toutfilename should not contain extension");*/
         
         super.visit(decl, arguement);
    }
    
    

}
class PlantumlTest {
	public  void umlCreator(String source , String path) {
		
		
		OutputStream png = null;
		try {
			png = new FileOutputStream(path+"//test.jpeg");
		} catch (FileNotFoundException excep) {
		
			excep.printStackTrace();
		}
			SourceStringReader reader = new SourceStringReader(source);
		// initiate the image 
		try {
			reader.generateImage(png);
		} catch (IOException excep) {
			
			excep.printStackTrace();
		}
		// Return nothing if empty
		//graph viz required 
	}
}
class ConstructorFinder extends VoidVisitorAdapter {


    
    @Override
    public void visit(ConstructorDeclaration decl, Object arguement) {
    	String p =null;
    	if(decl.getParameters()!=null)
    	{
    	for(Parameter par : decl.getParameters())
    	{	System.out.println("classname:"+UMLgenerator.classname);
 // constuctor 
    	System.out.println("x:"+par.toString());
    		// s.o.p ("" + .)
    		if(p != null)
    		p = p + "," + par.toString();
    		else 
    			p = par.toString();
    	String csfin =  par.getType().toString();
    	System.out.println("check :"+csfin);
    	// check string
    	if(UMLgenerator.list.contains(csfin))
    	{
    		if(!UMLgenerator.first.contains(csfin + "<.. "  + UMLgenerator.classname + ":uses")&& UMLgenerator.ilist.contains(csfin)&& !UMLgenerator.ilist.contains(UMLgenerator.classname))
    			UMLgenerator.first = UMLgenerator.first + csfin + "<.. "  + UMLgenerator.classname + ":uses" + "\n";
    	}
    	}	
    	}
    	UMLgenerator.first = UMLgenerator.first + UMLgenerator.classname + " : "+ "+" + decl.getName() + "("+ p +")" ;
        		
    	UMLgenerator.first = UMLgenerator.first + "\n";
    }
    
    


}



public class UMLgenerator 
{

public static  List<String> list = new ArrayList<String>();
public static  List<String> ilist = new ArrayList<String>();
public static  List<String> mlist = new ArrayList<String>();
/*
 * listsprivate List<FieldDeclaration> fields;
	private List<MethodDeclaration> methods;
	private List<ConstructorDeclaration> constructors;
 * 
 */
public static  String first = "@startuml\n";
public static String classname;

//firist = first + skip graphviz
public static void main(String[] args) throws IOException, ParseException 
{
		
File f = new File(args[0]);
first = first  + "skinparam classAttributeIconSize 0 \n";
int i = 0;
File[] listf = f.listFiles();
while ( i < listf.length ) 
{
		       
		       String  s= listf[i].getName();
		       s = s.replaceAll(".java", "");
		       list.add(s);
		       s="Collection<"+s+">";
		       list.add(s);
		       i++;
		       //System.out.println("print------");
		       //checking arguments and ignoring if not reaching the constraint
		       
		     }
		System.out.println("listin main"+list);
		File directory = new File(args[0]);
		File[] directoryListing = directory.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing){
				if(child.getName().contains(".java")) {
					FileInputStream input = new FileInputStream(child.getAbsolutePath());
					CompilationUnit c;
					
					// file stream
					
					try {
					
						c = JavaParser.parse(input);
						//japa
						
					}
						
					 finally {
				           
								input.close();
							
				        }
					String temp = c.toString();
					String lines[] = temp.split("\\r?\\n");
					String delimiters = "[ .,?!]+";
					String[] tokens = lines[0].split(delimiters);
					List types = c.getTypes();
					//delimitters set
					
					TypeDeclaration typeDec = (TypeDeclaration) types.get(0);
					classname = typeDec.getName();
					if(tokens[1].equals("interface"))
						first = first + "interface" + " " + classname + "\n";
					// first += "interface"
					// +Classname
			        if(tokens[1].equals("class"))
			        	first = first + "class" + " " + classname + "\n";
		// first += class+..+
			        new Interface().visit(c, null);
			        new name().visit(c, null);
			        new UMLmethod().visit(c, null);
			        new Field().visit(c, null);
			        new ConstructorFinder().visit(c, null);
				}
			}
			first = first + "@enduml\n";
			    PlantumlTest p = new PlantumlTest();
			    p.umlCreator(first,args[0]);
			    System.out.println(first); 
		}
	}

//e
}