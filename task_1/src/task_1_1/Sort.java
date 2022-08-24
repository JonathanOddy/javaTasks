package task_1_1;

public class Sort {

    public static void bubble(byte[] args) {

        byte variable;

        for (int i = 0; i < args.length; i++) {
            for (int j = 0; j < args.length - 1 - i; j++) {
                if (args[j] > args[j + 1]) {
                    variable = args[j];
                    args[j] = args[j+1];
                    args[j+1] = variable;

                }
            }
        }
    };

    public static void bubble(short[] args) {

        short variable;

        for (int i = 0; i < args.length; i++) {
            for (int j = 0; j < args.length - 1 - i; j++) {
                if (args[j] > args[j + 1]) {
                    variable = args[j];
                    args[j] = args[j+1];
                    args[j+1] = variable;

                }
            }
        }
    };

    public static void bubble(int[] args) {

        int variable;

        for (int i = 0; i < args.length; i++) {
            for (int j = 0; j < args.length - 1 - i; j++) {
                if (args[j] > args[j+1]) {
                    variable = args[j];
                    args[j] = args[j+1];
                    args[j+1] = variable;
                }
            }
        }
    };

    public static void bubble(long[] args) {

        long variable;

        for (int i = 0; i < args.length; i++) {
            for (int j = 0; j < args.length - 1 - i; j++) {
                if (args[j] > args[j+1]) {
                    variable = args[j];
                    args[j] = args[j+1];
                    args[j+1] = variable;
                }
            }
        }
    };

    public static void bubble(float[] args) {

        float variable;

        for (int i = 0; i < args.length; i++) {
            for (int j = 0; j < args.length - 1 - i; j++) {
                if (args[j] > args[j+1]) {
                    variable = args[j];
                    args[j] = args[j+1];
                    args[j+1] = variable;
                }
            }
        }
    };

    public static void bubble(double[] args) {

        double variable;

        for (int i = 0; i < args.length; i++) {
            for (int j = 0; j < args.length - 1 - i; j++) {
                if (args[j] > args[j+1]) {
                    variable = args[j];
                    args[j] = args[j+1];
                    args[j+1] = variable;
                }
            }
        }
    };

}
