package advanced_java.Chap12.Page370.test;

import java.util.List;
//test1
class MixForLoops {
    public static void main(String[] args) {
        List<Integer> nums = List.of(1, 2, 3, 4, 5);
        String output = "";

        for (Integer num : nums)
            output += nums + " ";  // append the current number

        System.out.println(output);
//       output: 1 2 3 4 5
    }
}
//
//test2
//
//class MixFor {
//    public static void main(String[] args) {
//        List<Integer> nums = List.of(1, 2, 3, 4, 5);
//        String output = "";
//
//        for (int i = 1; i < nums.size(); i++) {
//            output += nums.get(i) + " ";
//        }
//
//        System.out.println(output);
////        output: 2 3 4 5
//    }
//}
//// teset3
//
//class ForLoops {
//    public static void main(String [] args) {
//        List<Integer> nums = List.of(1, 2, 3, 4, 5);
//        String output = "";
//
//        for (int i = 0; i <= nums.length; i++)
//            output += nums.get(i) + " ";
//
//
//
//        System.out.println(output);
//    }
//}
//
////test4
//class Loops {
//    public static void main(String [] args) {
//        List<Integer> nums = List.of(1, 2, 3, 4, 5);
//        String output = "";
//
//        for (int i = 0; i <= nums.size(); i++)
//            output += nums.get(i) + " ";
//
//
//
//        System.out.println(output);
//    }
//}
