package Chap13.Page435_437;

// Main class to run the laundry and handle exceptions
public class WashingMachine {
    public static void main(String[] args) {
        Laundry laundry = new Laundry();

        String[] clothes = {"pants", "lingerie", "tshirt"};

        for (String type : clothes) {
            try {
                laundry.doLaundry();
            } catch (TeeShirtException tex) {
                // Recovery from TeeShirtException
                System.out.println("Caught TeeShirtException: " + tex);
            } catch (LingerieException lex) {
                // Recovery from LingerieException
                System.out.println("Caught LingerieException: " + lex);
            } catch (ClothingException cex) {
                // Recovery from all other ClothingExceptions
                System.out.println("Caught ClothingException: " + cex);
            }

            System.out.println("Laundry process complete.");
        }
    }
}
