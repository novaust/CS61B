package gh2;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

public class GuitarHero {
    public static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    public static final int KEYS_NUMBER = KEYBOARD.length();

    public static void main(String[] args) {
        GuitarString[] guitarStrings = new GuitarString[KEYS_NUMBER];

        for (int i = 0; i < KEYS_NUMBER; i++) {
            double frequency = 440 * Math.pow(2, (i - 24) / 12.0);
            guitarStrings[i] = new GuitarString(frequency);
        }

        while (true) {
            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = KEYBOARD.indexOf(key);
                if (index >= 0)
                    guitarStrings[index].pluck();
            }

            /* compute the superposition of samples */
            double sample = 0.0;
            for (GuitarString g : guitarStrings)
                sample += g.sample();

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (GuitarString g : guitarStrings)
                g.tic();
        }
    }
}
