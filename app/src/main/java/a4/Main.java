package a4;

class Main {
  public static void main(String[] args) {
    Postfix.postfix(Tokenizer.readTokens("3 20 + *"));
    System.out.println("Calls from the command line:");
    System.out.println("    java Postfix <postfix-expr>");
    System.out.println("    java Calculate <infix-expr>");
  }
}