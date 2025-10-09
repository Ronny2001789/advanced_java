package Chap13.Page435_437;

// Laundry class that might throw clothing-related exceptions
public class Laundry {

    // This method can throw these exceptions
    public void doLaundry() throws PantsException, LingerieException, TeeShirtException {
        System.out.println("Starting laundry...");

        String clothingType = "tshirt"; // change to "pants", "lingerie", or "tshirt"

        switch (clothingType) {
            case "pants":
                throw new PantsException();
            case "lingerie":
                throw new LingerieException();
            case "tshirt":
                throw new TeeShirtException();
            default:
                System.out.println("No exception for this clothing type.");
        }
    }
}
