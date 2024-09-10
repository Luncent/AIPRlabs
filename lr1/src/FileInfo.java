public class FileInfo {
    public int coincidences;
    public double sum;
    public FileInfo(int coincedences, double sum){
        this.coincidences =coincedences;
        this.sum = sum;
    }
    public FileInfo(){}

    public static FileInfo getMockObj(){
        return new FileInfo(-1,-1);
    }

    @Override
    public String toString() {
        return " coincidenсes:"+ coincidences+" sum:"+sum;
    }
}
