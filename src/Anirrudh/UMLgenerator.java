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
				if(!UMLgenerator.a.contains( str + "<|.. "  + UMLgenerator.classname )
						&& !UMLgenerator.a.contains
						( str + "<.. "  + UMLgenerator.classname + ":uses" ))
					UMLgenerator.a = UMLgenerator.a + str + " " + "<|.. " + " " + 
						UMLgenerator.classname +  "\n";
			}
		
	      
		 
	   }
     



	
}
class name extends VoidVisitorAdapter {

	 @Override
	 public void visit(ClassOrInterfaceDeclaration decl, Object arg)
	   {
	      // Make class extend Blah.
		 
		 List<ClassOrInterfaceType> visitlist = decl.getExtends();
		 if(visitlist==null)
			 return;
		 for (ClassOrInterfaceType citype : visitlist) {
				String str = citype.toString();
			
				UMLgenerator.a = UMLgenerator.a + str + " " + "<|--" + " " + UMLgenerator.classname + "\n";
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
    		//	System.out.println("k :"+k);
		        String[] tokens = str.split(delimiter);
		       // System.out.println("token0"+tokens[0]);
		        if(tokens[0]!=null)
		        {
		        if(UMLgenerator.list.contains(tokens[0]))//list contains all class names.
		        	//comparing it with tokens[0] to find if the class name matches.
		        	//this will check dependency in methods
		        	//System.out.println("list :"+Umlgen.list);
		        	UMLgenerator.a = UMLgenerator.a + tokens[0] +"<.. " + UMLgenerator.classname + "\n";
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
    	String check =  p.getType().toString();
    	//System.out.println("Interfacelist : "+Umlgen.interfacelist);
    //	System.out.println("Classname : "+Umlgen.classname);
    	
    	if(UMLgenerator.list.contains(check))
    	{
    		if(!UMLgenerator.a.contains(check + "<.. "  + UMLgenerator.classname + ":uses") 
    				&& UMLgenerator.ilist.contains(check) 
    				&& !UMLgenerator.ilist.contains(UMLgenerator.classname))//note
    	UMLgenerator.a = UMLgenerator.a + check + "<.. "  + UMLgenerator.classname + ":uses" + "\n";
    	}
    	}	
    	}
    	//System.out.println(n.toString());
    	//System.out.println("getmodifiers : "+n.getModifiers());
    
    	if(decl.getModifiers()==1)
    	{
    		UMLgenerator.a = UMLgenerator.a + UMLgenerator.classname + " : "+ "+" + decl.getName() + "("+ param +")" + ":" + decl.getType();
    		UMLgenerator.a = UMLgenerator.a + "\n";
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
    	
    		
    		if(UMLgenerator.a.contains(UMLgenerator.list.get(i) + " -- "  + UMLgenerator.classname ))
    			System.out.println("existing class");
    		else if(UMLgenerator.a.contains(" " + UMLgenerator.list.get(i) + " - \"1\" " + UMLgenerator.classname ) && flag == false)
    		{
    	
    			UMLgenerator.a.replace(UMLgenerator.list.get(i) + " - \"1\" " + UMLgenerator.classname, UMLgenerator.list.get(i) + "\"1\" - \"1\" " + UMLgenerator.classname);
    		}
    		
    		else if(UMLgenerator.a.contains(" "+UMLgenerator.list.get(i) + " - \"1\" " + UMLgenerator.classname ) && flag == true)
    		{
    			
    			UMLgenerator.a.replace(UMLgenerator.list.get(i) + " - \"1\" " + UMLgenerator.classname, UMLgenerator.list.get(i) + "\"*\" - \"1\" " + UMLgenerator.classname);
    		}
    		
    		else if(UMLgenerator.a.contains(UMLgenerator.list.get(i) + " - \"*\" " + UMLgenerator.classname ) && flag == false)
    		{
    			
    			UMLgenerator.a.replace(UMLgenerator.list.get(i) + " - \"*\" " + UMLgenerator.classname, UMLgenerator.list.get(i) + "\"1\" - \"*\" " + UMLgenerator.classname);
    		}
    	
    		else if(UMLgenerator.a.contains( UMLgenerator.list.get(i) + " - \"*\" " + UMLgenerator.classname ) && flag == true)
    		{
    			
    			UMLgenerator.a.replace(UMLgenerator.list.get(i) + " - \"*\" " + UMLgenerator.classname, UMLgenerator.list.get(i) + "\"*\" - \"*\" " + UMLgenerator.classname);
    		}
    		else
    		{
    			
    			if(flag==false)
    				UMLgenerator.a = UMLgenerator.a + UMLgenerator.classname + " - \"1\" "  + UMLgenerator.list.get(i) + "\n";
    			else
    				UMLgenerator.a = UMLgenerator.a + UMLgenerator.classname + " - \"*\" "  + UMLgenerator.list.get(i) + "\n";
    		}
    		
    		
    		
        	}
    			


    		
        	
    	
    	String k =decl.toString();
         k = k.replaceAll("[;]", "");
         String[] strs = k.split("\\s+");
        
         if(strs[0].equals("public"))
        	 strs[0]="+" ;
         if(strs[0].equals("private"))
         {    
        	 if(UMLgenerator.mlist.contains("set"+strs[2]) && UMLgenerator.mlist.contains("get"+strs[2]))
        	 strs[0]="+" ;
        	 else
        		 strs[0]="-" ;
        	 
         }
         if(strs[0].equals("protected"))
        	 strs[0]="#" ;
         if(strs.length >2 && (strs[0] == "+" || strs[0] == "-"))
         {
        	 UMLgenerator.a = UMLgenerator.a + UMLgenerator.classname + " : " + strs[0] + " " + strs[2] + " : " + strs[1];
        	 UMLgenerator.a = UMLgenerator.a + "\n";
         strs[2] = Character.toUpperCase(strs[2].charAt(0)) + strs[2].substring(1);
         String rep1 = "get" + strs[2];
         String rep2 = "set" + strs[2];
    
       //s = s.replaceAll(pattern, "");
         UMLgenerator.a = UMLgenerator.a.replaceAll( ".*"+rep1+".*(\r?\n|\r)?", "" );
         UMLgenerator.a = UMLgenerator.a.replaceAll( ".*"+rep2+".*(\r?\n|\r)?", "" );
       //System.out.println(s);
       //s = s.replace("^ClassA : +getMessage.*", "jhjh");
       ////s = s.replaceAll("^"+rep1+".*", "");
       
         }
         //System.out.println(Umlgen.s);
         
         super.visit(decl, arguement);
    }
    
    

}
class PlantumlTest {
	public  void umlCreator(String source , String path) {
		
		
		OutputStream png = null;
		try {
			png = new FileOutputStream(path+"//anirrudh.jpeg");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			SourceStringReader reader = new SourceStringReader(source);
		// Write the first image to "png"
		try {
			reader.generateImage(png);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Return a null string if no generation
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
    		System.out.println("x:"+par.toString());
    		
    		if(p != null)
    		p = p + "," + par.toString();
    		else 
    			p = par.toString();
    	String check =  par.getType().toString();
    	System.out.println("check :"+check);
    	if(UMLgenerator.list.contains(check))
    	{
    		if(!UMLgenerator.a.contains(check + "<.. "  + UMLgenerator.classname + ":uses")&& UMLgenerator.ilist.contains(check)&& !UMLgenerator.ilist.contains(UMLgenerator.classname))
    			UMLgenerator.a = UMLgenerator.a + check + "<.. "  + UMLgenerator.classname + ":uses" + "\n";
    	}
    	}	
    	}
    	UMLgenerator.a = UMLgenerator.a + UMLgenerator.classname + " : "+ "+" + decl.getName() + "("+ p +")" ;
        		
    	UMLgenerator.a = UMLgenerator.a + "\n";
    }
    
    


}



public class UMLgenerator 
{

public static  List<String> list = new ArrayList<String>();
public static  List<String> ilist = new ArrayList<String>();
public static  List<String> mlist = new ArrayList<String>();
public static  String a = "@startuml\n";
public static String classname;
public static void main(String[] args) throws IOException, ParseException 
{
		
File f = new File(args[0]);
a = a  + "skinparam classAttributeIconSize 0 \n";
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
					
					try {
					
						c = JavaParser.parse(input);
						
					}
						
					 finally {
				           
								input.close();
							
				        }
					String temp = c.toString();
					String lines[] = temp.split("\\r?\\n");
					String delimiters = "[ .,?!]+";
					String[] tokens = lines[0].split(delimiters);
					List types = c.getTypes();
					
					TypeDeclaration typeDec = (TypeDeclaration) types.get(0);
					classname = typeDec.getName();
					if(tokens[1].equals("interface"))
			        	a = a + "interface" + " " + classname + "\n";
			        if(tokens[1].equals("class"))
			        	 a = a + "class" + " " + classname + "\n";
			        new Interface().visit(c, null);
			        new name().visit(c, null);
			        new UMLmethod().visit(c, null);
			        new Field().visit(c, null);
			        new ConstructorFinder().visit(c, null);
				}
			}
			 a = a + "@enduml\n";
			    PlantumlTest p = new PlantumlTest();
			    p.umlCreator(a,args[0]);
			    System.out.println(a); 
		}
	}

}
