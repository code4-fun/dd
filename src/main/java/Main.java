import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Main {
    static List<Character> charArray;
    static StringBuilder result;

    /**
     *  Метод main().
     *
     *  @param args input parameters.
     */
    public static void main(String[] args) {
        try(FileReader fr = new FileReader("C://string.txt")){
            charArray = new ArrayList<>();
            int n;
            while((n = fr.read()) != -1){
                charArray.add((char)n);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        if(Main.process()){
            System.out.println(result.toString());
        } else{
            System.out.println("Wrong string format.");
        }
    }

    /**
     *  Метод выполняет посимвольный перебор входящей строки.
     *  Параллельно ищет ошибки в строке и если их находит
     *  возвращает false и прекращает работу.
     *
     *  @return true if input string can qualify as valid.
     */
    private static boolean process(){
        Stack<Character> brackets = new Stack<>();
        Stack<Integer> numbers = new Stack<>();
        StringBuilder tempSb = new StringBuilder();
        StringBuilder enclosedBrackets = new StringBuilder();
        result = new StringBuilder();
        int count = 0;

        if(charArray.isEmpty()){
            return true;
        }

        for (char item : charArray) {
            count++;

            if(item == '['){
                brackets.push(item);

                if(tempSb.toString().matches("\\d+")){
                    numbers.push(Integer.parseInt(tempSb.toString()));
                    tempSb.setLength(0);
                    continue;
                } else if(tempSb.toString().equals("")){
                    numbers.push(1);
                    tempSb.setLength(0);
                    continue;
                } else {
                    return false;
                }
            }

            if(item == ']'){
                if(brackets.empty()){
                    return false;
                }
                char tmp = brackets.pop();
                if(tmp != '['){
                    return false;
                }

                if(tempSb.toString().matches("^[A-Za-z]+$")){
                    if(brackets.empty() && enclosedBrackets.length() == 0){
                        int index = numbers.pop();
                        for(int i = 0; i < index; i++){
                            result.append(tempSb);
                        }
                        tempSb.setLength(0);
                        continue;
                    } else{
                        enclosedBrackets.append(tempSb);
                        String str = enclosedBrackets.toString();
                        int index = numbers.pop();
                        for(int i = 0; i < index - 1; i++){
                            enclosedBrackets.append(str);
                        }

                        if(brackets.empty()){
                            result.append(enclosedBrackets);
                        }

                        tempSb.setLength(0);
                        continue;
                    }
                } else if(tempSb.toString().equals("")){
                    return false;
                } else {
                    return false;
                }

            }

            tempSb.append(item);

            if(count >= charArray.size()){
                if(tempSb.length() != 0){
                    if(tempSb.toString().matches("^[A-Za-z]+$")){
                        result.append(tempSb);
                    } else{
                        return false;
                    }
                }
            }
        }
        return brackets.empty();
    }
}
