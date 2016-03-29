package src.tp4;

public class Coscalc{
  public static void main(String[] args){
    System.out.print("{\n");
    for(int i=0;i<8;i++){
      System.out.print("{ ");
      for(int j=0;j<8;j++){
        double cos = Math.cos(((2*i+1)*j*Math.PI)/16);
        System.out.print(cos);
        if(j<7){
          System.out.print(", ");
        }
      }
      System.out.print(" }");
      if(i<7){
        System.out.print(",\n");
      }
    }
    System.out.print("\n}");
  }
}
