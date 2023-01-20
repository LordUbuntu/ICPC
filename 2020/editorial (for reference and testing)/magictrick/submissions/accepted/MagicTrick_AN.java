import java.util.*;

public class MagicTrick_AN {
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    String input = s.next();
    Set<Character> inputSet = new HashSet<>();
    for (char c : input.toCharArray()) inputSet.add(c);
    if (input.length() == inputSet.size()) {
      System.out.println(1);
    } else {
      System.out.println(0);
    }
  }
}
