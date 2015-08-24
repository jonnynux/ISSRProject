package legacy;

/**
 * Class for emulating legacy magaz
 *
 * @author jonny
 */
public class LegacyMagaz {

    public int orderProduct(String brand, String type, int quantity) {

        int numberToAdd = (int) (Math.random() * 30);
        double sumSub = Math.random();

        int available = ((sumSub > 0.5) ? quantity + numberToAdd : quantity - numberToAdd);

        if (available <= 0) {
            return 0;
        } else if (available > quantity) {
            return quantity;
        } else {
            return available;
        }
    }
}
