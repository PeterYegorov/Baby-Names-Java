
import org.apache.commons.csv.*;
import edu.duke.*;

public class BabyNames {
    public void printNames() {
        FileResource fr = new FileResource();
        for (CSVRecord rec : fr.getCSVParser(false)) {
            System.out.println("Name " + rec.get(0) + " Gender " + rec.get(1) +
                " Num born " + rec.get(2));
        }
    }
    
    public int totalBirths(int year) {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        int girlNames = 0;
        int boyNames = 0;
        FileResource fr = new FileResource("us_babynames_by_year/yob"
            + year + ".csv");
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            if(rec.get(1).equals("M")) {
                totalBoys += numBorn;
                ++boyNames;
            }
            else if (rec.get(1).equals("F")) {
                totalGirls += numBorn;
                ++girlNames;
            }
        }
        System.out.println("Year:" + year + " Births:" + totalBirths + " Girls:" + totalGirls + " Boys:" + totalBoys + " Girl Names:" + girlNames + " Boy names:" + boyNames);
        return totalBirths;
    }
    
    /*public int totalGirls() {
        int totalGirls = 0;
        FileResource fr = new FileResource();
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals("F")) {
                int numBorn = Integer.parseInt(rec.get(2));
                totalGirls += numBorn;
            }
        }
        return totalGirls;
    }*/
    
    // Get the popularity rank of a gender-specific name in a specified year
    public int getRank(int year, String name, String gender) {
        int rank = 1;
        FileResource fr = new FileResource("us_babynames_by_year/yob"
            + year + ".csv" );
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                if(rec.get(0).equals(name))
                    return rank;
                ++rank;
            }
        }
        return -1;
    }
    
    // Get a gender-specific name of a specified popularity rank in a specified year
    public String getName(int year, int rank, String gender) {
        String name = "NO NAME";
        int recNum = 1;
        FileResource fr = new FileResource("us_babynames_by_year/yob"
            + year + ".csv" );
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                if(recNum == rank)
                    return rec.get(0);
                ++recNum;
            }
        }
        return name;
    }
    
    // Get a name of same popularity as another name in another year
    public String whatIsNameInYear(String name, int yearBorn, int yearOfInterest, String gender) {
        int rankOfNameInYearBorn = getRank(yearBorn, name, gender);
        if (rankOfNameInYearBorn == -1)
            return "NAME " + name + " IS NOT FOUND";
        String nameIfbornInYearOfInterest = getName(yearOfInterest, rankOfNameInYearBorn, gender);
        if (nameIfbornInYearOfInterest.equals("NO NAME"))
            return "RANK ERROR: NAME " + name + " HAS A RANK NOT PRESENT IN A YEAR OF INTEREST"; 
        //System.out.println( name + " born in " + yearBorn + " would be " + nameIfbornInYearOfInterest + " in " + yearOfInterest);
        return name + " born in " + yearBorn + " would be " + nameIfbornInYearOfInterest + " in " + yearOfInterest;
    }
    
    // Get specified name's highest rank year
    public int yearOfHighestRank(String name, String gender) {
        int year = 1880;
        int rank = getRank(1880, name, gender);
        int currentRank = -1;
        for (int i = 1881; i < 2015; ++i) {
            currentRank = getRank(i, name, gender);
            //System.out.println("Year: " + i + " Name: " + name + " Current rank: " + currentRank + " Rank: " + rank);
            if (rank == -1 || (currentRank != -1 && currentRank < rank)) {
                rank = currentRank;
                year = i;
            }
        }
        return year;
    }
    
    // Get an average rank of a specified name
    public double getAverageRank(String name, String gender) {
        double sum = getRank(1880, name, gender);
        int currentRank = 0;
        for (int i = 1881; i < 2015; ++i) {
            currentRank = getRank(i, name, gender);
            if (currentRank != -1)
                sum += currentRank;
        }
        return sum / 135;
    }
    
    // Get a number of babies born with names more popular than specified
    public int getTotalBirthsRankedHigher(int year, String name, String gender) {
        int total = 0;
        int rank = getRank(year, name, gender);
        FileResource fr = new FileResource("us_babynames_by_year/yob"
            + year + ".csv" );
        int currentLine = 1;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                if (currentLine == rank)
                    break;
                total += Integer.parseInt(rec.get(2));
                ++currentLine;
            }
        }
        return total;
    }
    
    /*public static void main(String[] args) {
        BabyNames bn = new BabyNames();
        System.out.println(bn.getRank(2014, "Sofia", "F")); // 12
        System.out.println(bn.getRank(2014, "Sofia", "M")); // 8634
        System.out.println(bn.getRank(2014, "Alexander", "F")); // 4336
        System.out.println(bn.getRank(2014, "Alexander", "M")); // 8
        System.out.println(bn.getRank(2014, "Lmao", "M")); // -1
        
        System.out.println(bn.getName(2014, 2, "M")); // Liam
        System.out.println(bn.getName(2014, 5, "F")); // Ava
        System.out.println(bn.getName(2014, 300000, "M")); // NO NAME
        
        String s = bn.whatIsNameInYear("Emma", 2014, 1900, "F"); // Mary
        String d = bn.whatIsNameInYear("Avery", 2014, 1900, "F"); // Emma
        System.out.println(bn.whatIsNameInYear("lol", 2014, 1900, "F")); // Name lol is not found
        
        System.out.println(bn.yearOfHighestRank("Emma", "F")); // 2008
        System.out.println(bn.getAverageRank("Emma", "F")); // 
        System.out.println(bn.getTotalBirthsRankedHigher(2014, "Sophia", "F")); // 40473
        
        bn.totalBirths(1900);
        bn.totalBirths(1905);
        System.out.println(bn.getRank(1960, "Emily", "F"));
        System.out.println(bn.getRank(1971, "Frank", "M"));
        System.out.println(bn.getName(1980, 350, "F"));
        System.out.println(bn.getName(1982, 450, "M"));
        String dd = bn.whatIsNameInYear("Susan", 1972, 2014, "F");
        String bb = bn.whatIsNameInYear("Owen", 1974, 2014, "M");
        System.out.println(bn.yearOfHighestRank("Genevieve", "F"));
        System.out.println(bn.yearOfHighestRank("Mich", "M"));
        System.out.println(bn.getAverageRank("Susan", "F"));
        System.out.println(bn.getAverageRank("Robert", "M"));
        System.out.println(bn.getTotalBirthsRankedHigher(1990, "Emily", "F"));
        System.out.println(bn.getTotalBirthsRankedHigher(1990, "Drew", "M"));
    }*/
}
