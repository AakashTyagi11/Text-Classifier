//Aakash Tyagi

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;


public class Multinomial {
	
	public static void readfile()
	{
		try {
			
	// Reading files and Copying data to the structures to be processed. 
		File traindata = new File("traindata.txt"); 
		File trainlabels = new File("trainlabels.txt");
		
		Scanner s = new Scanner(traindata); 
		Scanner s2 = new Scanner(trainlabels); 
		
		List<String> class1future= new ArrayList<String>();
		List<String> class0wise = new ArrayList<String>();
		Set<String> distinct= new HashSet<String>();
		
		double priorclass1=0.0;
		double priorclass0=0.0;
		
		String[] temp1 = null;
		String[] temp5 = null;
		
		int countofall=0;
		int countofclass1=0;
		
		
		   while(s.hasNextLine() && s2.hasNextLine() )
		    {
			  String currentLinedata = s.nextLine();
			  String currentLinelabel = s2.nextLine();
			  
			  if(currentLinelabel.equals("1"))
			   {
				 countofclass1++;
			   }
					  
			    temp1=currentLinedata.split(" ");
			      
			   for(String a: temp1)
			    {	
				  distinct.add(a);
						      
			   if(currentLinelabel.equals("1"))
				{
				  //String[] temp2=currentLinedata.split(" ");
				  class1future.add(a);
				}
			   else
			    {
			      class0wise.add(a);							 
			    }
						  
			    	}
					  
			     countofall++;
			 
			    
		        }   // Reading Ends Here.
		     
		
	      // Implementing Multinomial Algorithm. 
		 
		 //prior probabilities
		    
		 priorclass1=(double)countofclass1/countofall;
		 priorclass0=(double)(countofall-countofclass1)/countofall;
		 
		 //Conditional Probabilities
		 		 
		 Map<String,Double> mapclass1=new HashMap<String,Double>();  
		 Map<String,Double> mapclass0=new HashMap<String,Double>();  
		 
		 //All Conditional probabilities For class 1 in mapclass1 (Happen in Future)
		 //& class 0 in mapclass0 (Wise Saying)
		 
			 for(String a:distinct)
			 {
				 double temp2=0.0;
				 double temp3=0.0;
				 int ctr1=0;
				 int ctr2=0;
				 //System.out.println("value of class 1 ");
				 ctr1=Collections.frequency(class1future, a);
				//System.out.println("Frequency of 1 "+ " "+ a+" "+ctr1);
				 
				 temp2= (double)(ctr1+1)/(class1future.size()+distinct.size());
				 
				 mapclass1.put(a, temp2);
				//System.out.println("value of class 1 " + temp2);
				 
				 ctr2=Collections.frequency(class0wise, a);
				//System.out.println("Frequency of 0  "+ " "+ a+" "+ctr2);
				 
				 temp3= (double)(ctr2+1)/(class0wise.size()+distinct.size());
				 
				// System.out.println("value of class 0 "+ temp3);
				 mapclass0.put(a, temp3);
				 
			  }      // Training of the Classifier ended.
		  		 
		     // Test The Data
			
		 
			 File testdata = new File("traindata.txt"); 
			 Scanner s3 = new Scanner(testdata); 
			 
			 File testinglabels = new File("trainlabels.txt"); 
			 Scanner s5 = new Scanner(testinglabels); 
			
			
			 File file = new File("result.txt");
			 Scanner s4 = new Scanner(file); 
			 
			 FileWriter fileWriter = new FileWriter(file);
			
		 
			 while(s3.hasNextLine() )
			 	{
				  String currentLinedata = s3.nextLine();
				  temp5=currentLinedata.split(" ");
				  double prob1=priorclass1;
				  double prob0=priorclass0;
					  
				  for(String a: temp5)
					{
					 if(mapclass1.containsKey(a))
					  {
						prob1=(double)(prob1*mapclass1.get(a));
									
					   }
							  
					 if(mapclass0.containsKey(a))
					   {
						 prob0=(double)(prob0*mapclass0.get(a));
									
					   }
						 }
				  
					  if(prob1>=prob0)
					   {
						 System.out.println( currentLinedata +" :  Class 1");
						 fileWriter.write("1");
						 fileWriter.write("\r\n");
					    }
							  
					  else
					   {
						  System.out.println( currentLinedata +" :  Class 0");
						  fileWriter.write("0");
						  fileWriter.write("\r\n");
					   }
					 
				  
			 }
			 	 
			    fileWriter.flush();
							 	
			 	int mismatches=0;
			 	double accuracy=0.0;
			 	int total=0;
			 	while(s5.hasNextLine() && s4.hasNextLine())
				 {
				   String currentlinedata = s5.nextLine();
				   String currentLineresult = s4.nextLine();
					if(!currentlinedata.trim().equals(currentLineresult.trim()))
					 {
					   mismatches++;
									
				     }
				 total++;
				 }	
			 	 
			 	accuracy=(double)(total-mismatches)*100/total;
			 	 
			 	fileWriter.write("Accuracy(in percent)= "
			 			+ "(" +total+" - " +mismatches+")*100/"+total+" = "+accuracy+" % \r\n");
			 	fileWriter.write("Files Used for Training : traindata.txt, trainlabels.txt\r\n");
			 			
			 	fileWriter.write("File used for testing : " + testdata.getName()+"\r\n");
			 	fileWriter.write("Matching results with the file :  " + testinglabels.getName());
			 	
			 	
			 	
			 	fileWriter.close();
			 														 
		    	}
		              //Program logic Ends
		
			    catch(FileNotFoundException e)
				  {
					System.out.println("File Not Found");
				  }
			    catch(IOException e)
				  {
				    System.out.println("IO Exception");
				  }
						    
			}

			public static void main(String[] args) 
				{
					// Main Method
				    // Calling the static method.
					readfile();			
				}
		
		   }
