import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    static int[] numbersOfSymbols = new int[100];
    static double[] probabilitiesOfSymbols = new double[100];
    static File file = new File("Text.txt");
    static Scanner in;
    static double sum = 0;
    static double numberOfAll = 0;
    static String []values = new String[500];
    static int midpoint;
    static String[] Encoded;
    static String encoded = "";
    static String decoded = "";
    static String output = "";
    static String[] cutCodes = new String[1000];
    static StringBuilder sb = new StringBuilder();
    static String [] hString= new String[7000];
    static String stringLine="";
    static String [] hRows= new String[5000];
    static String calculationRow="";
    static String []hr= new String[5000];
    static String []hError= new String[5000];
    static String []hCorrect = new String[5000];
    static String humError="";
    static String humFix = "";

    static char[] allSymbols = {'a', 'b', 'c', 'd', 'e', 'f' , 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p','q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', ' ', '.', ',', '-','A','B','C','D','E','F','G','H','I','J','K','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9'};

    static {
        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static String line;

    public static void main(String args[]) throws FileNotFoundException {

        while (in.hasNext()) {
            line = in.next();
            for (int i = 0; i < line.length(); i++) {
                Check(line.charAt(i),allSymbols);
            }
        }

        for (int i = 0; i < 65; i++) {
            numberOfAll += numbersOfSymbols[i];
        }

        sum = numberOfAll;

        for (int i = 0; i < 65; i++) {
            probabilitiesOfSymbols[i] = numbersOfSymbols[i] / sum;
        }



        // Part 2
        //Joining arrays which were lower and upper cases


        double temp = 0;
        int temp1 = 0;
        char temp2 = 0;
        for (int i = 0; i<65; i++) {
            for (int j = 1; j < (65 - i); j++) {
                if (probabilitiesOfSymbols[j - 1] < probabilitiesOfSymbols[j]) {
                    temp = probabilitiesOfSymbols[j - 1];
                    temp1=numbersOfSymbols[j-1];
                    temp2=allSymbols[j-1];
                    probabilitiesOfSymbols[j - 1] = probabilitiesOfSymbols[j];
                    numbersOfSymbols[j-1] = numbersOfSymbols[j];
                    allSymbols[j-1] = allSymbols[j];
                    probabilitiesOfSymbols[j] = temp;
                    numbersOfSymbols[j] = temp1;
                    allSymbols[j] = temp2;
                }
            }
        }

        for(int i = 0; i < 200; i++){
            values[i] = "";
        }

        int summary=0;
        for(int i=0;i<65;++i)
        {

            summary=summary+numbersOfSymbols[i];
        }

        double diff=0;
        int sum1 = 0;
        double minDiff=1000;
        int mid=0;
        for(int i=0;i<65;++i)
        {
            sum1=sum1+numbersOfSymbols[i];
            diff=Math.abs(((double)sum/2)-(double)sum1);
            if(diff<minDiff)
            {
                minDiff=diff;
                mid=i;
            }
        }

        values = getBin(mid, numbersOfSymbols,values,0,65);

        //Part 3
        File reader = new File("Text.txt");
        Scanner sc = new Scanner(reader);
        String lineReader=sc.nextLine();

        for(int i=0;i<lineReader.length();++i)
        {
            for(int j=0;j<65;++j)
            {
                if(lineReader.charAt(i)==allSymbols[j])
                {
                    encoded=encoded+values[j];
                    break;
                }
            }
        }


        for(int i=0;i<encoded.length();++i)
        {
            decoded=decoded+encoded.charAt(i);
            for(int j=0;j<65;++j)
            {
                if(decoded.equals(values[j]))
                {
                    output=output+allSymbols[j];
                    decoded="";
                    break;
                }
            }
        }

        //System.out.println(encoded.length());
        //Part 4
        int counter=0;
        int counterFour=0;

        for(int i=0;i<encoded.length();++i)
        {
            stringLine=stringLine+encoded.charAt(i);
            counter++;
            if(counter==4)
            {
                hString[counterFour]=stringLine;
                stringLine="";
                counter=0;
                //System.out.println(hString[counterFour]);
                counterFour++;

            }
        }

        int r1, r2, r3;
        int count = 0;
        String humString="";

        for(int i=0;i<counterFour;i++) {
            humString = hString[i];

            r1=(int) ((Character.getNumericValue(humString.charAt(0)))^Character.getNumericValue(humString.charAt(1))^Character.getNumericValue(humString.charAt(2)));
            r2=(int) ((Character.getNumericValue(humString.charAt(1)))^Character.getNumericValue(humString.charAt(2))^Character.getNumericValue(humString.charAt(3)));
            r3=(int) ((Character.getNumericValue(humString.charAt(0)))^Character.getNumericValue(humString.charAt(1))^Character.getNumericValue(humString.charAt(3)));

            String str1 = Integer.toString(r1);
            String str2 = Integer.toString(r2);
            String str3 = Integer.toString(r3);
            
            r1=0;
            r2=0;
            r3=0;

            hString[i]=hString[i]+str1+str2+str3;

            humString = "";
            count++;
        }
        

        for(int i=0;i<counterFour;i++) {
            Random rand = new Random();
            int iteration = rand.nextInt(6) + 0;
            hError[i] = addError(hString[i],iteration);
            humError += hError[i];
            System.out.println(i+")Iteration: ");
            System.out.println("Original: ");
            System.out.println(hString[i]);
            System.out.println("Error Added: ");
            System.out.println(hError[i]);
            System.out.println();
        }

        hCorrect = hError;

        int s1, s2, s3;
        String fixError = "";
        for(int i=0;i<counterFour;i++) {
        	fixError = hCorrect[i];
        	
        	s1=(int) ((Character.getNumericValue(fixError.charAt(4)))^Character.getNumericValue(fixError.charAt(0))^Character.getNumericValue(fixError.charAt(1) ^ Character.getNumericValue(fixError.charAt(2))));
        	s2=(int) ((Character.getNumericValue(fixError.charAt(5)))^Character.getNumericValue(fixError.charAt(1))^Character.getNumericValue(fixError.charAt(2) ^ Character.getNumericValue(fixError.charAt(3))));
        	s3=(int) ((Character.getNumericValue(fixError.charAt(6)))^Character.getNumericValue(fixError.charAt(0))^Character.getNumericValue(fixError.charAt(1) ^ Character.getNumericValue(fixError.charAt(3))));

        	if(s1 == 0 && s2 == 0 && s3 == 0) {
                //No error
            }

        	if(s1 == 0 && s2 == 0 && s3 == 1){
        		if(fixError.charAt(6) == '1'){
                    fixError = replace(fixError,6,'0');
        		}else {
                    fixError = replace(fixError, 6, '1');
                }
        	}
        	
        	if(s1 == 0 && s2 == 1 && s3 == 0){
        		if(fixError.charAt(5) == '1'){
                    fixError=replace(fixError,5,'0');
        		}else {
                    fixError=replace(fixError, 5, '1');
                }
        	}
            
        	if(s1 == 0 && s2 == 1 && s3 == 1){
        		if(fixError.charAt(3) == '1'){
                    fixError=replace(fixError,3,'0');
        		}else {
                    fixError=replace(fixError, 3, '1');
                }
        	}
        	
        	if(s1 == 1 && s2 == 0 && s3 == 0){
        		if(fixError.charAt(4) == '1'){
                    fixError=replace(fixError,4,'0');
        		}else {
                    fixError=replace(fixError, 4, '1');
                }
        	}
        	
        	if(s1 == 1 && s2 == 0 && s3 == 1){
        		if(fixError.charAt(0) == '1'){
                    fixError=replace(fixError,0,'0');;
        		}else {
                    fixError=replace(fixError, 0, '1');
                }
        	}
        	
        	if(s1 == 1 && s2 == 1 && s3 == 0){
        		if(fixError.charAt(2) == '1'){
                    fixError=replace(fixError,2,'0');
        		}else {
                    fixError=replace(fixError, 2, '1');
                }
        	}
        	
        	if(s1 == 1 && s2 == 1 && s3 == 1){
        		if(fixError.charAt(1) == '1'){
                    fixError=replace(fixError,1,'0');
        		}else {
                    fixError=replace(fixError, 1, '1');
                }
        	}

        	hCorrect[i] = fixError;

            s1=0;
            s2=0;
            s3=0;
        }

        for(int i = 0; i<counterFour;i++){
            System.out.println(i+")Iteration: ");
            System.out.println("Fixed: ");
            System.out.println(hCorrect[i]);
            System.out.println();
        }
    }

    public static void Check(char letterForCheck, char[] allSymbols){
        for(int i = 0; i<65; i++){
            if(letterForCheck == allSymbols[i]){
                numbersOfSymbols[i]++;
            }
        }
    }

    public static String[] getBin(int midpoint, int []sortedCounter, String []binar, int up, int down)
    {
        int upcounter=0;
        int downcounter=0;
        for(int i=up;i<=midpoint;++i)
        {
            binar[i]=binar[i]+1;
            upcounter++;
        }

        for(int i=midpoint+1;i<down;++i)
        {
            binar[i]=binar[i]+0;
            downcounter++;
        }

        if(upcounter>1)
        {
            int sum=0;
            for(int i=up;i<=midpoint;++i)
            {
                sum=sum+sortedCounter[i];
            }
            double diff=0;
            int sum1=0;
            double minDiff=1000;
            int mid=0;
            for(int i=up;i<=midpoint;++i)
            {
                sum1=sum1+sortedCounter[i];
                diff=Math.abs(((double)sum/2)-(double)sum1);
                if(diff<minDiff)
                {
                    minDiff=diff;
                    mid=i;
                }
            }
            getBin(mid,sortedCounter,binar,up,midpoint+1);
        }
        if(downcounter>1)
        {
            int sum=0;
            for(int i=midpoint+1;i<down;++i)
            {
                sum=sum+sortedCounter[i];
            }
            double diff=0;
            int sum1=0;
            double minDiff=1000;
            int mid=0;
            for(int i=midpoint+1;i<down;++i)
            {
                sum1=sum1+sortedCounter[i];
                diff=Math.abs(((double)sum/2)-(double)sum1);
                if(diff<minDiff)
                {
                    minDiff=diff;
                    mid=i;
                }
            }
            getBin(mid,sortedCounter,binar,midpoint+1,down);
        }
        return binar;
    }

    public static String addError(String error, int iteration){
        String errorString = "";
        for(int i = 0; i<7; i++){
            if(iteration!=i)
            {
                errorString += error.charAt(i);
            }else{
                if(error.charAt(iteration) == '0'){
                    errorString += '1';
                }else{
                    errorString += '0';
                }
            }
        }
        return errorString;
    }


    public static String replace(String str, int index, char replace){
        if(str==null){
            return str;
        }else if(index<0 || index>=str.length()){
            return str;
        }
        char[] chars = str.toCharArray();
        chars[index] = replace;
        return String.valueOf(chars);
    }
}

